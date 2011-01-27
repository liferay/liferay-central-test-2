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

import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHub;
import com.liferay.portal.kernel.notifications.ChannelHubManager;
import com.liferay.portal.kernel.notifications.DuplicateChannelHubException;
import com.liferay.portal.kernel.notifications.UnknownChannelHubException;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.listener.ChannelListener;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Edward Han
 */
public class ChannelHubManagerImpl implements ChannelHubManager {
	public void confirmDelivery(long companyId, long userId, String uuid)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.confirmDelivery(userId, uuid);
	}

	public Channel createChannel(long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.createChannel(userId);
	}

	public ChannelHub createChannelHub(long companyId) throws ChannelException {
		synchronized (this) {
			if (_channelHubs.containsKey(companyId)) {
				throw new DuplicateChannelHubException(companyId);
			}

			ChannelHub hub = _channelHubPrototype.clone(companyId);

			_channelHubs.put(companyId, hub);

			return hub;
		}
	}

	public void destroyChannel(long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.destroyChannel(userId);
	}

	public void destroyChannelHub(long companyId) throws ChannelException {
		ChannelHub hub = _channelHubs.remove(companyId);

		if (hub != null) {
			hub.destroy();
		}
	}

	public void flush() throws ChannelException {
        Lock readLock = _channelHubsRWLock.readLock();

        try {
            readLock.lock();

            for (ChannelHub channelHub : _channelHubs.values()) {
                channelHub.flush();
            }
        }
        finally {
            readLock.unlock();
        }
	}

	public void flush(long companyId) throws ChannelException {
		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.flush();
	}

	public void flush(long companyId, long userId, long timestamp)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.flush(userId, timestamp);
	}

	public Channel getChannel(long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getChannel(userId);
	}

	public ChannelHub getChannelHub(long companyId) throws ChannelException {
		Lock readLock = _channelHubsRWLock.readLock();

		try {
			readLock.lock();

			ChannelHub hub = _channelHubs.get(companyId);

			if (hub == null) {
				throw new UnknownChannelHubException(companyId);
			}

			return hub;
		}
		finally {
			readLock.unlock();
		}
	}

	public List<NotificationEvent> getNotificationEvents(
			long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getNotificationEvents(userId);
	}

	public List<NotificationEvent> getNotificationEvents(
			long compnayId, long userId, boolean flush)
		throws ChannelException {

		return getChannelHub(compnayId).getNotificationEvents(userId, flush);
	}

	public Collection<Long> getUserIds(long companyId) throws ChannelException {
		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getUserIds();
	}

	public void registerChannelListener(
			long companyId, long userId, ChannelListener listener)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.registerChannelListener(userId, listener);
	}

	public void removeTransientNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.removeTransientNotificationEvents(
			userId, notificationEvents);
	}

	public void removeTransientNotificationEventsByUuid(
			long companyId, long userId, Collection<String> uuids)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.removeTransientNotificationEventsByUuid(userId, uuids);
	}

	public void sendNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.sendNotificationEvent(userId, notificationEvent);
	}

	public void sendNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.sendNotificationEvents(userId, notificationEvents);
	}

	public void setChannelHubPrototype(ChannelHub channelHubPrototype) {
		_channelHubPrototype = channelHubPrototype;
	}

	public void unregisterChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.unregisterChannelListener(userId, channelListener);
	}

	private Map<Long, ChannelHub> _channelHubs =
        new ConcurrentHashMap<Long, ChannelHub>();
	private ReadWriteLock _channelHubsRWLock = new ReentrantReadWriteLock();
	private ChannelHub _channelHubPrototype;
}