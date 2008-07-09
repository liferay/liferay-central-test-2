/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.liveusers.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.liveusers.LiveUsers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LiveUsersMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LiveUsersMessageListener implements MessageListener {

	public void receive(Object message) {
		throw new UnsupportedOperationException();
	}

	public void receive(String message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	public void doReceive(String message) throws Exception {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		String command = jsonObj.getString("command");

		if (command.equals("signIn")) {
			doCommandSignIn(jsonObj);
		}
		else if (command.equals("signOut")) {
			doCommandSignOut(jsonObj);
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

	private static Log _log = LogFactory.getLog(LiveUsersMessageListener.class);

}