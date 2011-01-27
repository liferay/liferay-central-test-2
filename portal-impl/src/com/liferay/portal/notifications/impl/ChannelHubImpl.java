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
import com.liferay.portal.kernel.notifications.DuplicateChannelException;
import com.liferay.portal.kernel.notifications.UnknownChannelException;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.listener.ChannelListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Edward Han
 */
public class ChannelHubImpl implements ChannelHub {
	public ChannelHubImpl(long companyId) {
		_companyId = companyId;
	}

	public void cleanup(long userId) throws ChannelException {
        Channel channel = getChannel(userId);

		channel.cleanup();
	}

	public void cleanupAll() throws ChannelException {
        Lock readLock = _channelsRWLock.readLock();

        try {
            readLock.lock();

            for (Channel channel : _channels.values()) {
                channel.cleanup();
            }
        }
        finally {
            readLock.unlock();
        }
	}

	public ChannelHub clone(long companyId) {
		ChannelHubImpl channelHub = new ChannelHubImpl(companyId);

		channelHub.setChannelPrototype(_channelPrototype);

		return channelHub;
	}

	public void confirmDelivery(long userId, String uuid)
		throws ChannelException {

		Channel channel = getChannel(userId);

        channel.confirmDelivery(uuid);
	}

	public void confirmDelivery(long userId, Collection<String> uuids)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.confirmDelivery(uuids);
	}

	public Channel createChannel(long userId)
		throws ChannelException {

        Lock writeLock = _channelsRWLock.writeLock();

        try {
            writeLock.lock();

            if (_channels.containsKey(userId)) {
                throw new DuplicateChannelException(userId);
            }

			Channel channel = _channelPrototype.clone(_companyId, userId);

            _channels.put(userId, channel);

            channel.init();

			return channel;
        }
        finally {
            writeLock.unlock();
        }
    }

    public void destroy() throws ChannelException {
        Lock writeLock = _channelsRWLock.writeLock();

        try {
            writeLock.lock();

            for (Channel channel : _channels.values()) {
                channel.close();
            }

			_channels.clear();
        }
        finally {
            writeLock.unlock();
        }
    }

	public Channel destroyChannel(long userId) throws ChannelException {
		Lock writeLock = _channelsRWLock.writeLock();

		try {
			writeLock.lock();

			Channel channel = _channels.remove(userId);

			if (channel != null) {
				channel.close();
			}

			return channel;
		}
		finally {
			writeLock.unlock();
		}
	}

	public void flush() throws ChannelException {
        Lock readLock = _channelsRWLock.readLock();

        try {
            readLock.lock();

            for (Channel channel : _channels.values()) {
				channel.flush();
			}
        }
        finally {
            readLock.unlock();
        }
	}

	public void flush(long userId) throws ChannelException  {
		Channel channel = getChannel(userId);

		channel.flush();
	}

	public void flush(long userId, long timestamp)
		throws ChannelException  {

		Channel channel = getChannel(userId);

		channel.flush(timestamp);
	}

	public Channel getChannel(long userId) throws ChannelException {
        Lock readLock = _channelsRWLock.readLock();

        try {
            readLock.lock();

            Channel channel = _channels.get(userId);

			if (channel == null) {
				throw new UnknownChannelException(userId);
			}

			return channel;
        }
        finally {
            readLock.unlock();
        }
    }

	public long getCompanyId() {
		return _companyId;
	}

	public List<NotificationEvent> getNotificationEvents(long userId)
		throws ChannelException {

		Channel channel = getChannel(userId);

		return channel.getNotificationEvent();
	}

	public List<NotificationEvent> getNotificationEvents(
			long userId, boolean flush)
		throws ChannelException {

		Channel channel = getChannel(userId);

		return channel.getNotificationEvent(flush);
	}

	public Collection<Long> getUserIds() {
        return Collections.unmodifiableSet(_channels.keySet());
    }

	public void registerChannelListener(
			long userId, ChannelListener channelListener)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.registerChannelListener(channelListener);
	}

	public void removeTransientNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.removeTransientNotificationEvents(notificationEvents);
	}

	public void removeTransientNotificationEventsByUuid(
			long userId, Collection<String> uuids)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.removeTransientNotificationEventsByUuid(uuids);
	}

	public void sendNotificationEvent(
			long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.sendNotificationEvent(notificationEvent);
	}

	public void sendNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvent)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.sendNotificationEvents(notificationEvent);
	}

	public void setChannelPrototype(Channel channelPrototype) {
		_channelPrototype = channelPrototype;
	}

	public void unregisterChannelListener(
			long userId, ChannelListener channelListener)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.unregisterChannelListener(channelListener);
	}

	private Map<Long, Channel> _channels = new HashMap<Long, Channel>();
	private Channel _channelPrototype;
	private ReadWriteLock _channelsRWLock = new ReentrantReadWriteLock();
	private long _companyId;

}