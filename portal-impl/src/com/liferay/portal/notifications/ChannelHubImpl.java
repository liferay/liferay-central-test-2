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

package com.liferay.portal.notifications;

import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHub;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.notifications.DuplicateChannelException;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.UnknownChannelException;

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
 * @author Brian Wing Shun Chan
 */
public class ChannelHubImpl implements ChannelHub {

	public ChannelHubImpl(long companyId) {
		_companyId = companyId;
	}

	public void cleanUp() throws ChannelException {
		Lock readLock = _readWriteLock.readLock();

		try {
			readLock.lock();

			for (Channel channel : _channels.values()) {
				channel.cleanUp();
			}
		}
		finally {
			readLock.unlock();
		}
	}

	public void cleanUp(long userId) throws ChannelException {
		Channel channel = getChannel(userId);

		channel.cleanUp();
	}

	public ChannelHub clone(long companyId) {
		ChannelHubImpl channelHubImpl = new ChannelHubImpl(companyId);

		channelHubImpl.setChannelPrototype(_channel);

		return channelHubImpl;
	}

	public void confirmDelivery(
			long userId, Collection<String> notificationEventUuids)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.confirmDelivery(notificationEventUuids);
	}

	public void confirmDelivery(long userId, String notificationEventUuid)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.confirmDelivery(notificationEventUuid);
	}

	public Channel createChannel(long userId) throws ChannelException {
		Lock writeLock = _readWriteLock.writeLock();

		try {
			writeLock.lock();

			if (_channels.containsKey(userId)) {
				throw new DuplicateChannelException(
					"Channel already exists with user id " + userId);
			}

			Channel channel = _channel.clone(_companyId, userId);

			_channels.put(userId, channel);

			channel.init();

			return channel;
		}
		finally {
			writeLock.unlock();
		}
	}

	public void destroy() throws ChannelException {
		Lock writeLock = _readWriteLock.writeLock();

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
		Lock writeLock = _readWriteLock.writeLock();

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
		Lock readLock = _readWriteLock.readLock();

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

	public void flush(long userId) throws ChannelException {
		Channel channel = getChannel(userId);

		channel.flush();
	}

	public void flush(long userId, long timestamp) throws ChannelException {
		Channel channel = getChannel(userId);

		channel.flush(timestamp);
	}

	public Channel getChannel(long userId) throws ChannelException {
		Lock readLock = _readWriteLock.readLock();

		try {
			readLock.lock();

			Channel channel = _channels.get(userId);

			if (channel == null) {
				throw new UnknownChannelException(
					"No channel exists with user id " + userId);
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

		return channel.getNotificationEvents();
	}

	public List<NotificationEvent> getNotificationEvents(
			long userId, boolean flush)
		throws ChannelException {

		Channel channel = getChannel(userId);

		return channel.getNotificationEvents(flush);
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
			long userId, Collection<String> notificationEventUuids)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.removeTransientNotificationEventsByUuid(notificationEventUuids);
	}

	public void sendNotificationEvent(
			long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.sendNotificationEvent(notificationEvent);
	}

	public void sendNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.sendNotificationEvents(notificationEvents);
	}

	public void setChannelPrototype(Channel channel) {
		_channel = channel;
	}

	public void unregisterChannelListener(
			long userId, ChannelListener channelListener)
		throws ChannelException {

		Channel channel = getChannel(userId);

		channel.unregisterChannelListener(channelListener);
	}

	private Channel _channel;
	private Map<Long, Channel> _channels = new HashMap<Long, Channel>();
	private long _companyId;
	private ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}