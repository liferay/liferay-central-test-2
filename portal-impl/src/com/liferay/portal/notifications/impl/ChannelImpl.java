/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.notifications.impl;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.base.BaseChannelImpl;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.event.NotificationEventComparator;
import com.liferay.portal.kernel.notifications.util.NotificationEventFactoryUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.usernotifications.model.UserNotificationEvent;
import com.liferay.portlet.usernotifications.service.UserNotificationEventLocalService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Edward Han
 */
public class ChannelImpl extends BaseChannelImpl {

	public ChannelImpl() {
		super(CompanyConstants.SYSTEM, 0);
	}

	public ChannelImpl(long companyId, long usedId) {
		super(companyId, usedId);
	}

	public Channel clone(long companyId, long userId) {
		ChannelImpl channel = new ChannelImpl(companyId, userId);

		channel.setUserNotificationEventLocalService(
			_userNotificationEventLocalService);

		return channel;
	}

    public void confirmDelivery(String uuid) throws ChannelException {
        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

            NotificationEvent event = _unconfirmedNotificationEvents.remove(
				uuid);

            if (event != null) {
                _userNotificationEventLocalService.
					deleteUserNotificationEvent(event.getUuid());
            }
        }
        catch (Exception e) {
            throw new ChannelException(
				"Failed to confirm delivery for " + uuid , e);
        }
        finally {
            writeLock.unlock();
        }
    }

	public void confirmDelivery(Collection<String> uuids)
		throws ChannelException {

        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

			for (String uuid : uuids) {
				_unconfirmedNotificationEvents.remove(uuid);
			}

			_userNotificationEventLocalService.deleteUserNotificationEvents(
				uuids);
        }
        catch (Exception e) {
            throw new ChannelException(
				"Failed to confirm delivery for user " + getUserId() , e);
        }
        finally {
            writeLock.unlock();
        }
	}

	public void flush() throws ChannelException {
        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

            _notificationEvents.clear();
        }
        finally {
            writeLock.unlock();
        }
    }

	public void flush(long timestamp) throws ChannelException {
        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

			Iterator<NotificationEvent> eventItr =
				_notificationEvents.iterator();

            while (eventItr.hasNext()) {
				NotificationEvent event = eventItr.next();

				if (event.getTimestamp() < timestamp) {
					eventItr.remove();
				}
			}
        }
        finally {
            writeLock.unlock();
        }
	}

	public List<NotificationEvent> getNotificationEvent(boolean flush)
		throws ChannelException {

        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

            List<NotificationEvent> notificationEvents =
				new ArrayList<NotificationEvent>(_notificationEvents.size() +
					_unconfirmedNotificationEvents.size());

            long currentTime = System.currentTimeMillis();

            for (NotificationEvent notificationEvent : _notificationEvents) {
                boolean remove = evaluateRemoval(
					notificationEvent, currentTime);

				if (!remove) {
					notificationEvents.add(notificationEvent);
				}
				else {
					break;
				}
            }

			if (flush) {
				_notificationEvents.clear();
			}
			else if (_notificationEvents.size() != notificationEvents.size()) {
				_notificationEvents.retainAll(notificationEvents);
			}

			Collection<String> invalidNotificationEvent =
				new ArrayList<String>(_unconfirmedNotificationEvents.size());

			Iterator<Map.Entry<String, NotificationEvent>> unconfirmedEvtIter =
                _unconfirmedNotificationEvents.entrySet().iterator();

            while (unconfirmedEvtIter.hasNext()) {
                Map.Entry<String, NotificationEvent> event =
					unconfirmedEvtIter.next();

				boolean remove = evaluateRemoval(event.getValue(), currentTime);

                if (remove) {
					invalidNotificationEvent.add(event.getValue().getUuid());

					unconfirmedEvtIter.remove();
				}
                else {
					notificationEvents.add(event.getValue());
                }
            }

			if (invalidNotificationEvent.size() > 0) {
				try {
					_userNotificationEventLocalService.
						deleteUserNotificationEvents(invalidNotificationEvent);
				}
				catch (Exception e) {
					throw new ChannelException(e);
				}
			}

            return notificationEvents;
        }
        finally {
            writeLock.unlock();
        }
    }

	public UserNotificationEventLocalService getUserNotificationEventLocalService() {
		return _userNotificationEventLocalService;
	}

	public void init() throws ChannelException {
        try {
            Collection<UserNotificationEvent> persistedNotificationEvents =
				_userNotificationEventLocalService.getUserNotificationEvents(
					getCompanyId(), getUserId());

			Collection<String> invalidNotificationEvents =
				new ArrayList<String>(_unconfirmedNotificationEvents.size());

			long currentTime = System.currentTimeMillis();

            for (UserNotificationEvent persistedNotificationEvent :
				persistedNotificationEvents) {

                try {
                    JSONObject payload = JSONFactoryUtil.createJSONObject(
						persistedNotificationEvent.getPayload());

					NotificationEvent notificationEvent =
						NotificationEventFactoryUtil.createChannelEvent(
							persistedNotificationEvent.getTimestamp(),
							persistedNotificationEvent.getType(), payload);

					notificationEvent.setDeliveryRequired(
						persistedNotificationEvent.getDeliverBy());

					boolean remove = evaluateRemoval(
						notificationEvent, currentTime);

					if (!remove) {
						_unconfirmedNotificationEvents.put(
							notificationEvent.getUuid(), notificationEvent);
					}
					else {
						invalidNotificationEvents.add(
							notificationEvent.getUuid());
					}
                }
                catch (JSONException e) {
                    if (_log.isErrorEnabled()) {
                        _log.error(e);
                    }

					invalidNotificationEvents.add(
						persistedNotificationEvent.getUuid());
                }

            }

			if (invalidNotificationEvents.size() > 0) {
				_userNotificationEventLocalService.deleteUserNotificationEvents(
					invalidNotificationEvents);
			}
        }
        catch (Exception e) {
            throw new ChannelException(
				"Unable to init channel " + getUserId(), e);
        }
    }

	public void removeTransientNotificationEvents(
		Collection<NotificationEvent> notificationEvents) {

		Lock writeLock = _channelRWLock.writeLock();

		try {
			writeLock.lock();

			_notificationEvents.removeAll(notificationEvents);
		}
		finally {
			writeLock.unlock();
		}
	}

	public void removeTransientNotificationEventsByUuid(
		Collection<String> channelEventUuids) {

		Lock writeLock = _channelRWLock.writeLock();

		try {
			writeLock.lock();

			Collection<String> uuids = new ArrayList<String>(channelEventUuids);

			Iterator<NotificationEvent> channelEvents =
				_notificationEvents.iterator();

			while (channelEvents.hasNext()) {
				NotificationEvent event = channelEvents.next();

				if (uuids.remove(event.getUuid())) {
					channelEvents.remove();
				}
			}
		}
		finally {
			writeLock.unlock();
		}
	}

	public void sendNotificationEvent(NotificationEvent notificationEvent)
        throws ChannelException {

        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

			long currentTime = System.currentTimeMillis();

			doLocalStoreChannelEvent(notificationEvent, currentTime);

			boolean requiresPersistent =
				evaluateRequiresPersistent(notificationEvent);

			if (requiresPersistent) {
				_userNotificationEventLocalService.addUserNotificationEvent(
					getCompanyId(), getUserId(), notificationEvent);
			}
		}
        catch (Exception e) {
            throw new ChannelException("Unable to send event", e);
        }
        finally {
            writeLock.unlock();
        }

        notifyChannelListeners();
    }

	public void sendNotificationEvents(
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

        Lock writeLock = _channelRWLock.writeLock();

        try {
            writeLock.lock();

            long currentTime = System.currentTimeMillis();

			List<NotificationEvent> persistedNotificationEvents =
				new ArrayList<NotificationEvent>(notificationEvents.size());

			for (NotificationEvent notificationEvent : notificationEvents) {
				doLocalStoreChannelEvent(notificationEvent, currentTime);

				boolean requiresPersistent =
					evaluateRequiresPersistent(notificationEvent);

				if (requiresPersistent) {
					persistedNotificationEvents.add(notificationEvent);
				}
			}

			_userNotificationEventLocalService.addUserNotificationEvents(
				getCompanyId(), getUserId(), persistedNotificationEvents);
        }
        catch (Exception e) {
            throw new ChannelException("Unable to send event", e);
        }
        finally {
            writeLock.unlock();
        }

        notifyChannelListeners();
	}

	public void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	@Override
    protected void doCleanup() throws ChannelException {
        Lock lock = _channelRWLock.writeLock();

        try {
            lock.lock();

            Iterator<NotificationEvent> eventsIter =
				_notificationEvents.iterator();

            long currentTime = System.currentTimeMillis();

            while (eventsIter.hasNext()) {
                NotificationEvent notificationEvent = eventsIter.next();

				boolean remove = evaluateRemoval(
					notificationEvent, currentTime);

                if (remove) {
                    eventsIter.remove();
                }
            }

			Collection<String> invalidEventUuids =
				new ArrayList<String>(_unconfirmedNotificationEvents.size());

            Iterator<Map.Entry<String, NotificationEvent>> notificationEvents =
                _unconfirmedNotificationEvents.entrySet().iterator();

            while (notificationEvents.hasNext()) {
                Map.Entry<String, NotificationEvent> event =
					notificationEvents.next();

				boolean remove = evaluateRemoval(event.getValue(), currentTime);

                if (remove) {
                    invalidEventUuids.add(event.getKey());

                    notificationEvents.remove();
                }
            }

			_userNotificationEventLocalService.deleteUserNotificationEvents(
				invalidEventUuids);
        }
        catch (Exception e) {
            throw new ChannelException(
				"Unable to complete cleanup of channel" + getUserId(), e);
        }
        finally {
            lock.unlock();
        }
    }

	protected void doLocalStoreChannelEvent(
			NotificationEvent notificationEvent, long currentTime)
		throws ChannelException {

		boolean invalid = evaluateRemoval(notificationEvent, currentTime);

		if (invalid) {
			return;
		}

		if (notificationEvent.isDeliveryRequired()) {
			_unconfirmedNotificationEvents.put(
				notificationEvent.getUuid(), notificationEvent);
		}
		else {
			_notificationEvents.add(notificationEvent);
		}
	}

	protected boolean evaluateRemoval(
		NotificationEvent notificationEvent, long currentTime) {

		return ((notificationEvent.getDeliverBy() != 0) &&
			(notificationEvent.getDeliverBy() <= currentTime));
	}

	protected boolean evaluateRequiresPersistent(NotificationEvent event) {
		return event.isDeliveryRequired();
	}

	private ReadWriteLock _channelRWLock = new ReentrantReadWriteLock();

	private Comparator<NotificationEvent> _notificationEventComparator =
		new NotificationEventComparator();

	private SortedSet<NotificationEvent> _notificationEvents =
		new TreeSet<NotificationEvent>(_notificationEventComparator);

	private LinkedHashMap<String, NotificationEvent> _unconfirmedNotificationEvents =
		new LinkedHashMap<String, NotificationEvent>();

	private UserNotificationEventLocalService _userNotificationEventLocalService;

    private static final Log _log = LogFactoryUtil.getLog(ChannelImpl.class);
}