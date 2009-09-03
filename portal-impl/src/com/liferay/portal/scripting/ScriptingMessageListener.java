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

import java.util.Formatter;

/**
 * <a href="ScriptingMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ScriptingMessageListener implements MessageListener {

	public ScriptingMessageListener(Scripting scripting) {
		if (scripting == null) {
			throw new IllegalArgumentException("Scripting is null!");
		}
		_scripting = scripting;
	}

	public void receive(Message message) {

		Object payload = message.getPayload();
		String responseDestination = message.getResponseDestinationName();
		ScriptingResultContainer resultContainer =
			new ScriptingResultContainer();
		ScriptingRequest scriptingRequest = null;
		ScriptingException scriptingException = null;
		try {
			// there must always be a payload having the request
			if (payload == null) {
				scriptingException =
					new ScriptingException(MISSING_REQUEST_ERROR);
				_log.error(MISSING_REQUEST_ERROR);
			}
			// check for being the right request type
			else if (!ScriptingRequest.class.isAssignableFrom(
				payload.getClass())) {
				String errorMessage =
					new Formatter().format(
						WRONG_REQUEST_ERROR, ScriptingRequest.class.getName(),
						payload.getClass().getName()).
					toString();

				scriptingException = new ScriptingException(errorMessage);
				_log.error(errorMessage);
			}
			else {

				scriptingRequest =ScriptingRequest.class.cast(payload);
				resultContainer.setResult(scriptingRequest.execute(_scripting));
			}
		}
		catch (ScriptingException ex) {
			scriptingException = new ScriptingException(EXECUTE_ERROR);
			_log.error(EXECUTE_ERROR, ex);

		}
		finally {
			if (Validator.isNotNull(responseDestination) &&
				scriptingRequest != null && scriptingRequest.hasReturnValue()) {

				Message responseMessage =
					MessageBusUtil.createResponseMessage(message);
				resultContainer.setException(scriptingException);
				responseMessage.setPayload(resultContainer);
				MessageBusUtil.sendMessage(
					responseDestination, responseMessage);
			}

		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ScriptingMessageListener.class);
	private static String MISSING_REQUEST_ERROR = "Missing workflow request.";
	private static String WRONG_REQUEST_ERROR =
		"Payload type is not from expected type [%s], but was [%s]";
	private static String EXECUTE_ERROR = "Unable to execute request.";

	private Scripting _scripting;

}