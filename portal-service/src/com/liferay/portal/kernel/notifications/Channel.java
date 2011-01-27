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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.listener.ChannelListener;

import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 */
public interface Channel {

	public Channel clone(long companyId, long userId);

	public void cleanup() throws ChannelException;

	public void close() throws ChannelException;

	public void confirmDelivery(String uuid) throws ChannelException;

	public void confirmDelivery(Collection<String> uuids)
		throws ChannelException;

	public void flush() throws ChannelException;

	public void flush(long timestamp) throws ChannelException;

	public List<NotificationEvent> getNotificationEvent()
		throws ChannelException;

	public List<NotificationEvent> getNotificationEvent(boolean flush)
		throws ChannelException;

	public long getUserId();

	public void init() throws ChannelException;

	public void registerChannelListener(ChannelListener channelListener);

	public void removeTransientNotificationEvents(
		Collection<NotificationEvent> notificationEvents);

	public void removeTransientNotificationEventsByUuid(
		Collection<String> channelEventUuids);

	public void sendNotificationEvent(NotificationEvent notificationEvent)
		throws ChannelException;

	public void sendNotificationEvents(
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException;

	public void unregisterChannelListener(ChannelListener channelListener);
}