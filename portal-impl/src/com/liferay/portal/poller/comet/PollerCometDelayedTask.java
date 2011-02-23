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

package com.liferay.portal.poller.comet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.poller.comet.CometSession;

import java.util.List;

/**
 * @author Edward Han
 */
public class PollerCometDelayedTask {
	public PollerCometDelayedTask(
		CometSession cometSession, JSONObject pollerResponseHeader) {

		_cometSession = cometSession;
		_pollerResponseHeader = pollerResponseHeader;
	}

	public void doTask() throws Exception{
		long companyId = _cometSession.getCometRequest().getCompanyId();
		long userId = _cometSession.getCometRequest().getUserId();

		List<NotificationEvent> notificationEvents =
			ChannelHubManagerUtil.getNotificationEvents(
				companyId, userId, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_pollerResponseHeader != null) {
			jsonArray.put(_pollerResponseHeader);
		}

		for (NotificationEvent notificationEvent : notificationEvents) {
			jsonArray.put(notificationEvent.toJSONObject());
		}

		_cometSession.getCometResponse().writeData(jsonArray.toString());

		ChannelHubManagerUtil.removeTransientNotificationEvents(
			companyId, userId, notificationEvents);

		_cometSession.close();
	}

	private CometSession _cometSession;
	private JSONObject _pollerResponseHeader;
}