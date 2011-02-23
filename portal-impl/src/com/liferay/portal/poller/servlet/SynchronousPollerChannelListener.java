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

package com.liferay.portal.poller.servlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Edward Han
 */
public class SynchronousPollerChannelListener implements ChannelListener {

	public SynchronousPollerChannelListener(
		long companyId, JSONObject pollerResponseHeader, long userId) {

		_companyId = companyId;
		_pollerResponseHeader = pollerResponseHeader;
		_userId = userId;
	}

	public synchronized void channelListenerRemoved(long channelId) {
		_complete = true;

		this.notify();
	}

	public synchronized void notificationEventsAvailable(long channelId) {
		_complete = true;

		this.notify();
	}

	public synchronized String getNotificationBatch(long timeout)
		throws ChannelException {

		try {
			if (!_complete) {
				this.wait(timeout);
			}
		}
		catch (InterruptedException ie) {
		}

		try {
			Thread.sleep(PropsValues.POLLER_NOTIFICATION_BATCH_WAIT);
		}
		catch (InterruptedException ie) {
		}

		List<NotificationEvent> notificationEvents =
			ChannelHubManagerUtil.getNotificationEvents(
				_companyId, _userId, true);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(_pollerResponseHeader);

		for (NotificationEvent notificationEvent : notificationEvents) {
			jsonArray.put(notificationEvent.toJSONObject());
		}

		return jsonArray.toString();
	}

	private long _companyId;
	private boolean _complete;
	private JSONObject _pollerResponseHeader;
	private long _userId;
}