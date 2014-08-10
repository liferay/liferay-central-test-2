/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.notifications.NotificationEvent;

import java.util.List;

/**
 * @author Edward Han
 */
public class SynchronousPollerChannelListener implements ChannelListener {

	public SynchronousPollerChannelListener() {
	}

	@Deprecated
	public SynchronousPollerChannelListener(
		long companyId, long userId,
		JSONObject pollerResponseHeaderJSONObject) {

		_companyId = companyId;
		_userId = userId;
		_pollerResponseHeaderJSONObject = pollerResponseHeaderJSONObject;
	}

	@Override
	public synchronized void channelListenerRemoved(long channelId) {
		_complete = true;

		this.notify();
	}

	@Deprecated
	public synchronized String getNotificationEvents(long timeout)
		throws ChannelException {

		return getNotificationEvents(
			_companyId, _userId, _pollerResponseHeaderJSONObject, timeout);
	}

	public synchronized String getNotificationEvents(
			long companyId, long userId,
			JSONObject pollerResponseHeaderJSONObject, long timeout)
		throws ChannelException {

		try {
			if (!_complete) {
				this.wait(timeout);
			}
		}
		catch (InterruptedException ie) {
		}

		List<NotificationEvent> notificationEvents =
			ChannelHubManagerUtil.fetchNotificationEvents(
				companyId, userId, true);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(pollerResponseHeaderJSONObject);

		for (NotificationEvent notificationEvent : notificationEvents) {
			jsonArray.put(notificationEvent.toJSONObject());
		}

		return jsonArray.toString();
	}

	@Override
	public synchronized void notificationEventsAvailable(long channelId) {
		_complete = true;

		this.notify();
	}

	private long _companyId;
	private boolean _complete;
	private JSONObject _pollerResponseHeaderJSONObject;
	private long _userId;

}