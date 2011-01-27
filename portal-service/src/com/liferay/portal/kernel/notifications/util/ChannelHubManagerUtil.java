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

package com.liferay.portal.kernel.notifications.util;

import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHub;
import com.liferay.portal.kernel.notifications.ChannelHubManager;
import com.liferay.portal.kernel.notifications.event.NotificationEvent;
import com.liferay.portal.kernel.notifications.listener.ChannelListener;

import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 */
public class ChannelHubManagerUtil {

	public void setChannelHubManager(ChannelHubManager channelHubManager) {
		_channelHubManager = channelHubManager;
	}

	public static void confirmDelivery(long companyId, long userId, String uuid)
		throws ChannelException {

		_channelHubManager.confirmDelivery(companyId, userId, uuid);
	}

	public static Channel createChannel(long companyId, long userId)
		throws ChannelException {

		return _channelHubManager.createChannel(companyId, userId);
	}

	public static ChannelHub createChannelHub(long companyId)
		throws ChannelException {

		return _channelHubManager.createChannelHub(companyId);
	}

	public static void destroyChannel(long companyId, long userId)
		throws ChannelException {

		_channelHubManager.destroyChannel(companyId, userId);
	}

	public static void destroyChannelHub(long companyId)
		throws ChannelException {

		_channelHubManager.destroyChannelHub(companyId);
	}

	public static void flush() throws ChannelException {
		_channelHubManager.flush();
	}

	public static void flush(long companyId) throws ChannelException {
		_channelHubManager.flush(companyId);
	}

	public static void flush(long companyId, long userId, long timestamp)
		throws ChannelException {

		_channelHubManager.flush(companyId, userId, timestamp);
	}

	public static Channel getChannel(long companyId, long userId)
		throws ChannelException {

		return _channelHubManager.getChannel(companyId, userId);
	}

	public static List<NotificationEvent> getNotificationEvents(
			long compnayId, long userId)
		throws ChannelException {

		return _channelHubManager.getNotificationEvents(compnayId, userId);
	}

	public static List<NotificationEvent> getNotificationEvents(
			long compnayId, long userId, boolean flush)
		throws ChannelException {

		return _channelHubManager.getNotificationEvents(
			compnayId, userId, flush);
	}

	public static ChannelHub getChannelHub(long companyId)
		throws ChannelException {

		return _channelHubManager.getChannelHub(companyId);
	}

	public static Collection<Long> getUserIds(long companyId)
		throws ChannelException {

		return _channelHubManager.getUserIds(companyId);
	}

	public static void registerChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		_channelHubManager.registerChannelListener(
			companyId, userId, channelListener);
	}

	public static void removeTransientNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		_channelHubManager.removeTransientNotificationEvents(
			companyId, userId, notificationEvents);
	}

	public static void removeTransientNotificationEventsByUuid(
			long companyId, long userId, Collection<String> uuids)
		throws ChannelException {

		_channelHubManager.removeTransientNotificationEventsByUuid(
			companyId, userId, uuids);
	}

	public static void sendNotificationEvent(
		long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		_channelHubManager.sendNotificationEvent(
			companyId, userId, notificationEvent);
	}

	public static void sendNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		_channelHubManager.sendNotificationEvents(
			companyId, userId, notificationEvents);
	}

	public static void unregisterChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		_channelHubManager.unregisterChannelListener(
			companyId, userId, channelListener);
	}

	private static ChannelHubManager _channelHubManager;
}