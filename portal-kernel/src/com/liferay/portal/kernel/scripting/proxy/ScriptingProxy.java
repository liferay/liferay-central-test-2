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

package com.liferay.portal.kernel.scripting.proxy;

import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.messaging.ScriptingRequest;
import java.util.HashMap;
import java.util.HashSet;
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
public class ScriptingProxy implements Scripting {

	public void clearCache(String language) throws ScriptingException {
		ScriptingRequest scriptingRequest =
			new ScriptingRequest(
			ScriptingRequest.Command.CLEAR_CACHE, language, null);
		MessageBusUtil.sendMessage(
			DestinationNames.SCRIPTING, scriptingRequest);
	}

	public Map<String, Object> eval(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		Set<String> outputNames, String language, String script)
		throws ScriptingException {
		ScriptingRequest scriptingRequest =
			new ScriptingRequest(
			ScriptingRequest.Command.EVAL, language, script, allowedClasses,
			inputObjects, outputNames);
		Message message = new Message();
		message.setResponseDestinationName(DestinationNames.SCRIPTING_RESPONSE);
		message.setPayload(scriptingRequest);
		ClusterLinkUtil.setForwardMessage(message);
		try {
			Map<String, Object> result =
				(Map<String, Object>) MessageBusUtil.sendSynchronousMessage(
				DestinationNames.SCRIPTING, message);
			return result;
		}
		catch (MessageBusException ex) {
			throw new ScriptingException(ex);
		}
	}

	public void exec(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		String language, String script) throws ScriptingException {
		ScriptingRequest scriptingRequest =
			new ScriptingRequest(
				ScriptingRequest.Command.EXEC, language, script, allowedClasses,
				inputObjects, null);
		MessageBusUtil.sendMessage(
			DestinationNames.SCRIPTING, scriptingRequest);
	}

	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {
		ScriptingRequest scriptingRequest =
			new ScriptingRequest(
			ScriptingRequest.Command.GET_PORTLET_OBJECTS, portletConfig,
			portletContext, portletRequest, portletResponse);
		Message message = new Message();
		message.setResponseDestinationName(DestinationNames.SCRIPTING_RESPONSE);
		message.setPayload(scriptingRequest);
		ClusterLinkUtil.setForwardMessage(message);
		try {
			Map<String, Object> result =
				(Map<String, Object>) MessageBusUtil.sendSynchronousMessage(
				DestinationNames.SCRIPTING, message);
			return result;
		}
		catch (MessageBusException ex) {
			_log.error("Failed to get portlet objects!", ex);
			return new HashMap<String, Object>();
		}
	}

	public Set<String> getSupportedLanguages() {
		ScriptingRequest scriptingRequest =
			new ScriptingRequest(
			ScriptingRequest.Command.GET_SUPPORTED_LANGUAGES);
		Message message = new Message();
		message.setResponseDestinationName(DestinationNames.SCRIPTING_RESPONSE);
		message.setPayload(scriptingRequest);
		ClusterLinkUtil.setForwardMessage(message);
		try {
			Set<String> result =
				(Set<String>) MessageBusUtil.sendSynchronousMessage(
					DestinationNames.SCRIPTING, message);
			return result;
		}
		catch (MessageBusException ex) {
			_log.error("Failed to get supported languages!", ex);
			return new HashSet<String>();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ScriptingProxy.class);

}