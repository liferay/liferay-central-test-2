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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.poller.comet.BaseCometHandler;
import com.liferay.portal.kernel.poller.comet.CometHandler;
import com.liferay.portal.kernel.poller.comet.CometRequest;
import com.liferay.portal.kernel.poller.comet.CometSession;
import com.liferay.portal.poller.PollerRequestHandlerUtil;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class PollerCometHandler extends BaseCometHandler {

	public CometHandler clone() {
		return new PollerCometHandler();
	}

	public void receiveData(String data) {
	}

	protected void doDestroy() throws Exception {
		if (_channelListener != null) {
			ChannelHubManagerUtil.unregisterChannelListener(
				_companyId, _userId, _channelListener);
		}
	}

	protected void doInit(CometSession cometSession) throws Exception {
		CometRequest cometRequest = cometSession.getCometRequest();

		_companyId = cometRequest.getCompanyId();
		_userId = cometRequest.getUserId();

		String pollerRequestString = cometRequest.getParameter("pollerRequest");

		JSONObject pollerResponseHeaderJSONObject =
			PollerRequestHandlerUtil.processRequest(
				cometRequest.getPathInfo(), pollerRequestString);

		if (pollerResponseHeaderJSONObject != null) {
			 _channelListener = new PollerCometChannelListener(
				 cometSession, pollerResponseHeaderJSONObject);

			ChannelHubManagerUtil.registerChannelListener(
				_companyId, _userId, _channelListener);
		}
		else {
			cometSession.close();
		}
	}

	private ChannelListener _channelListener;
	private long _companyId;
	private long _userId;

}