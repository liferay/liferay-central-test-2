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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="ScriptingMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ScriptingMessageListener implements MessageListener {

	public ScriptingMessageListener(Scripting scripting) {
		_scripting = scripting;
	}

	public void receive(Message message) {
		ScriptingResultContainer scriptingResultContainer =
			new ScriptingResultContainer();

		try {
			Object payload = message.getPayload();

			if (payload == null) {
				throw new ScriptingException("Payload is null");
			}
			else if (!ScriptingRequest.class.isAssignableFrom(
						payload.getClass())) {

				throw new ScriptingException(
					"Payload " + payload.getClass() + " is not of type " +
						ScriptingRequest.class.getName());
			}
			else {
				ScriptingRequest scriptingRequest = (ScriptingRequest)payload;

				Object result = scriptingRequest.execute(_scripting);

				scriptingResultContainer.setResult(result);
			}
		}
		catch (ScriptingException se) {
			_log.error(se, se);

			scriptingResultContainer.setScriptingException(se);
		}
		finally {
			String responseDestination = message.getResponseDestinationName();

			if (Validator.isNotNull(responseDestination)) {
				Message responseMessage = MessageBusUtil.createResponseMessage(
					message);

				responseMessage.setPayload(scriptingResultContainer);

				MessageBusUtil.sendMessage(
					responseDestination, responseMessage);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ScriptingMessageListener.class);

	private Scripting _scripting;

}