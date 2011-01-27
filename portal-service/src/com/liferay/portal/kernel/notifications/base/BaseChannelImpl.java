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

package com.liferay.portal.kernel.notifications.base;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.listener.ChannelListener;

import java.util.List;
import java.util.Set;

/**
 * @author Edward Han
 */
public abstract class BaseChannelImpl implements Channel {
	protected BaseChannelImpl(long companyId, long usedId) {
		_companyId = companyId;

		_userId = usedId;
	}

	public void cleanup() throws ChannelException {
		long currentTime = System.currentTimeMillis();

		synchronized (this) {
			if (currentTime > _nextCleanupTime) {
				doCleanup();
			}

			_nextCleanupTime = currentTime + _cleanupInterval;
		}
	}

	public void close() throws ChannelException {
		flush();
	}

	public long getCompanyId() {
		return _companyId;
	}

	public List<NotificationEvent> getNotificationEvent()
		throws ChannelException {

		return getNotificationEvent(true);
	}

	public long getUserId() {
		return _userId;
	}

	public void registerChannelListener(ChannelListener channelListener) {
		_channelListeners.add(channelListener);
	}

	public void setCleanupInterval(long cleanupInterval) {
		_cleanupInterval = cleanupInterval;
	}

	public void unregisterChannelListener(ChannelListener channelListener) {
		_channelListeners.remove(channelListener);

		channelListener.listenerRemoved(_userId);
	}

	protected abstract void doCleanup() throws ChannelException;

	protected void notifyChannelListeners()
		throws ChannelException {

		for (ChannelListener channelListener : _channelListeners) {
			channelListener.eventsAvailable(_userId);
		}

		_channelListeners.clear();
	}

	private Set<ChannelListener> _channelListeners =
		new ConcurrentHashSet<ChannelListener>();

	private long _companyId;
	private long _cleanupInterval;
	private long _nextCleanupTime;
	private long _userId;
}