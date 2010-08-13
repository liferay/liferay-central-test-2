/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.liveusers.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.liveusers.LiveUsers;

/**
 * @author Brian Wing Shun Chan
 */
public class LiveUsersMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doCommandSignIn(JSONObject jsonObject) throws Exception {
		long companyId = jsonObject.getLong("companyId");
		long userId = jsonObject.getLong("userId");
		String sessionId = jsonObject.getString("sessionId");
		String remoteAddr = jsonObject.getString("remoteAddr");
		String remoteHost = jsonObject.getString("remoteHost");
		String userAgent = jsonObject.getString("userAgent");

		LiveUsers.signIn(
			companyId, userId, sessionId, remoteAddr, remoteHost, userAgent);
	}

	protected void doCommandSignOut(JSONObject jsonObject) throws Exception {
		long companyId = jsonObject.getLong("companyId");
		long userId = jsonObject.getLong("userId");
		String sessionId = jsonObject.getString("sessionId");

		LiveUsers.signOut(companyId, userId, sessionId);
	}

	protected void doReceive(Message message) throws Exception {
		String payload = (String)message.getPayload();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(payload);

		String command = jsonObject.getString("command");

		if (command.equals("signIn")) {
			doCommandSignIn(jsonObject);
		}
		else if (command.equals("signOut")) {
			doCommandSignOut(jsonObject);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiveUsersMessageListener.class);

}