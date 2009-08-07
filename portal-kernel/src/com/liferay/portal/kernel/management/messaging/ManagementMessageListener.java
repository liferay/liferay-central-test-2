/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.management.messaging;

import com.liferay.portal.kernel.management.ManagementService;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

import java.util.HashMap;

/**
 * <a href="ManagementMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ManagementMessageListener implements MessageListener {

	public void receive(Message message) {

		Exception exception = null;
		String responseDestination = message.getResponseDestinationName();
		Message responseMessage = MessageBusUtil.createResponseMessage(message);
		try {
			String language =
				message.getString(ManagementService.LANGUAGE_KEY);
			String script = message.getString(ManagementService.SCRIPT_KEY);
			ScriptingUtil.exec(
				null, new HashMap<String, Object>(), language, script);
		}
		catch (Exception ex) {
			exception = ex;
		}
		finally {
			if (exception != null) {
				responseMessage.setPayload(exception);
			}
			else {
				responseMessage.setPayload(new Object());
			}
			MessageBusUtil.sendMessage(responseDestination, responseMessage);
		}
	}

}