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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.liveusers.LiveUsers;

/**
 * <a href="LiveUsersMessageListener.java.html"><b><i>View Source</i></b></a>
 *
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

	protected void doCommandSignIn(JSONObject jsonObj) throws Exception {
		long companyId = jsonObj.getLong("companyId");
		long userId = jsonObj.getLong("userId");
		String sessionId = jsonObj.getString("sessionId");
		String remoteAddr = jsonObj.getString("remoteAddr");
		String remoteHost = jsonObj.getString("remoteHost");
		String userAgent = jsonObj.getString("userAgent");

		LiveUsers.signIn(
			companyId, userId, sessionId, remoteAddr, remoteHost, userAgent);
	}

	protected void doCommandSignOut(JSONObject jsonObj) throws Exception {
		long companyId = jsonObj.getLong("companyId");
		long userId = jsonObj.getLong("userId");
		String sessionId = jsonObj.getString("sessionId");

		LiveUsers.signOut(companyId, userId, sessionId);
	}

	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObj = (JSONObject)message.getPayload();

		String command = jsonObj.getString("command");

		if (command.equals("signIn")) {
			doCommandSignIn(jsonObj);
		}
		else if (command.equals("signOut")) {
			doCommandSignOut(jsonObj);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiveUsersMessageListener.class);

}