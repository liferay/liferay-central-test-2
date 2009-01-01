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

package com.liferay.util.bridges.javascript;

import com.liferay.util.bridges.bsf.BaseBSFPortlet;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <a href="JavaScriptPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class JavaScriptPortlet extends BaseBSFPortlet {

	protected void declareBeans(
			String code, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append(getGlobalScript());
		sb.append(code);

		String script = sb.toString();

		PortletConfig portletConfig = getPortletConfig();
		PortletContext portletContext = getPortletContext();
		PortletPreferences preferences = portletRequest.getPreferences();
		Map<String, String> userInfo =
			(Map<String, String>)portletRequest.getAttribute(
				PortletRequest.USER_INFO);

		Context context = Context.enter();

		try {
			Scriptable scope = context.initStandardObjects();

			Object out = Context.javaToJS(System.out, scope);

			ScriptableObject.putProperty(scope, "out", out);

			ScriptableObject.putProperty(
				scope, "portletConfig", Context.javaToJS(portletConfig, scope));
			ScriptableObject.putProperty(
				scope, "portletContext",
				Context.javaToJS(portletContext, scope));
			ScriptableObject.putProperty(
				scope, "preferences", Context.javaToJS(preferences, scope));
			ScriptableObject.putProperty(
				scope, "userInfo", Context.javaToJS(userInfo, scope));

			if (portletRequest instanceof ActionRequest) {
				ScriptableObject.putProperty(
					scope, "actionRequest",
					Context.javaToJS(portletRequest, scope));
			}
			else if (portletRequest instanceof RenderRequest) {
				ScriptableObject.putProperty(
					scope, "renderRequest",
					Context.javaToJS(portletRequest, scope));
			}
			else if (portletRequest instanceof ResourceRequest) {
				ScriptableObject.putProperty(
					scope, "resourceRequest",
					Context.javaToJS(portletRequest, scope));
			}

			if (portletResponse instanceof ActionResponse) {
				ScriptableObject.putProperty(
					scope, "actionResponse",
					Context.javaToJS(portletResponse, scope));
			}
			else if (portletResponse instanceof RenderResponse) {
				ScriptableObject.putProperty(
					scope, "renderResponse",
					Context.javaToJS(portletResponse, scope));
			}
			else if (portletResponse instanceof ResourceResponse) {
				ScriptableObject.putProperty(
					scope, "resourceResponse",
					Context.javaToJS(portletResponse, scope));
			}

			context.evaluateString(scope, script, "script", 1, null);
		}
		finally {
			Context.exit();
		}
	}

	protected String getFileParam() {
		return _FILE_PARAM;
	}

	protected String getScriptingEngineClassName() {
		return _SCRIPTING_ENGINE_CLASS_NAME;
	}

	protected String getScriptingEngineExtension() {
		return _SCRIPTING_ENGINE_EXTENSION;
	}

	protected String getScriptingEngineLanguage() {
		return _SCRIPTING_ENGINE_LANGUAGE;
	}

	private static final String _FILE_PARAM = "javaScriptFile";

	private static final String _SCRIPTING_ENGINE_CLASS_NAME =
		"org.apache.bsf.engines.javascript.JavaScriptEngine";

	private static final String _SCRIPTING_ENGINE_EXTENSION = "js";

	private static final String _SCRIPTING_ENGINE_LANGUAGE = "javascript";

}