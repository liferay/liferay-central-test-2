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

package com.liferay.portal.kernel.scripting.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
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
		if (payload == null) {
			_log.error("Receive a message has no payload!");
			return;
		}
		if (ScriptingRequest.class.isAssignableFrom(payload.getClass()) ==
			false) {
			_log.error(
				"Receive a message has a wrong payload type:" +
				payload.getClass() + " needs:" + ScriptingRequest.class);
			return;
		}

		ScriptingRequest scriptingRequest =
			ScriptingRequest.class.cast(payload);

		ScriptingRequest.Command command = scriptingRequest.getCommand();
		if (command == null) {
			_log.error("Missing Command in payload!");
			return;
		}

		Set<String> allowedClasses = scriptingRequest.getAllowedClasses();
		Map<String, Object> inputObjects = scriptingRequest.getInputObjects();
		Set<String> outputNames = scriptingRequest.getOutputNames();
		String language = scriptingRequest.getLanguage();
		String script = scriptingRequest.getScript();

		PortletConfig portletConfig = scriptingRequest.getPortletConfig();
		PortletContext portletContext = scriptingRequest.getPortletContext();
		PortletRequest portletRequest = scriptingRequest.getPortletRequest();
		PortletResponse portletResponse = scriptingRequest.getPortletResponse();

		try {
			switch (command) {
				case CLEAR_CACHE:
					_scripting.clearCache(language);
					break;
				case EXEC:
					_scripting.exec(
						allowedClasses, inputObjects, language, script);
					break;
				case EVAL:
					Map<String,Object> evalResult = _scripting.eval(
						allowedClasses, inputObjects, outputNames, language,
						script);
					MessageBusUtil.sendMessage(
						message.getResponseDestinationName(), evalResult);
					break;
				case GET_PORTLET_OBJECTS:
					Map<String,Object> portletObjects =
						_scripting.getPortletObjects(
							portletConfig, portletContext, portletRequest,
							portletResponse);
					MessageBusUtil.sendMessage(
						message.getResponseDestinationName(), portletObjects);
					break;
				case GET_SUPPORTED_LANGUAGES:
					Set<String> supportedLauguages =
						_scripting.getSupportedLanguages();
					Message responseMessage=
						MessageBusUtil.createResponseMessage(
							message, supportedLauguages);
					MessageBusUtil.sendMessage(
						message.getResponseDestinationName(), responseMessage);
					break;
			}
		}
		catch (ScriptingException se) {
			_log.error("Failed to do local scripting command:" + command, se);
		}

	}

	private Scripting _scripting;
	private static Log _log =
		LogFactoryUtil.getLog(ScriptingMessageListener.class);

}