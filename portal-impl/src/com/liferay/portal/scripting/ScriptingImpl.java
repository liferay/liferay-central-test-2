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

import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.portal.scripting.javascript.JavaScriptExecutor;
import com.liferay.portal.scripting.groovy.GroovyExecutor;
import com.liferay.portal.scripting.python.PythonExecutor;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceResponse;
import javax.portlet.RenderResponse;
import javax.portlet.ActionResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ActionRequest;

/**
 * <a href="ScriptingImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 *
 */
public class ScriptingImpl implements Scripting {

	public ScriptingImpl() {
		_scriptingExecutors.put("python", new PythonExecutor());
		_scriptingExecutors.put("groovy", new GroovyExecutor());
		_scriptingExecutors.put("javascript", new JavaScriptExecutor());
		_scriptingExecutors.put("ruby", new RubyExecutor());
	}

	public void clearCache(String language) {
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		if (Validator.isNull(language)) {
			throw new UnsupportedLanguageException(
				"No scripting language specified");
		}

		try {
			ScriptingExecutor executor = _scriptingExecutors.get(language);

			if (executor == null) {
				String msg =
					"Scripting language '" + language + "' is not supported.";

				if (_log.isWarnEnabled()) {
					_log.warn(msg);
				}

				throw new UnsupportedLanguageException(msg);
			}

			return executor.eval(
				allowedClasses, inputObjects, outputNames, script);
		}
		catch (Exception e) {
			throw new ScriptingException(_getErrorInfo(script, e), e);
		}
	}

	public void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script)
		throws ScriptingException {

		eval(allowedClasses, inputObjects, null, language, script);
	}

	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletPreferences preferences = portletRequest.getPreferences();

		Map<String, String> userInfo =
			(Map<String, String>)portletRequest.getAttribute(
				PortletRequest.USER_INFO);

		Map<String, Object> objects = new HashMap<String, Object>();

		objects.put("portletConfig", portletConfig);

		objects.put("portletContext", portletContext);

		objects.put("preferences", preferences);

		objects.put("userInfo", userInfo);

		if (portletRequest instanceof ActionRequest) {
			objects.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			objects.put("renderRequest", portletRequest);
		}
		else if (portletRequest instanceof ResourceRequest) {
			objects.put("resourceRequest", portletRequest);
		}
		else {
			objects.put("portletRequest", portletRequest);
		}

		if (portletResponse instanceof ActionResponse) {
			objects.put("actionResponse", portletResponse);
		}
		else if (portletResponse instanceof RenderResponse) {
			objects.put("renderResponse", portletResponse);
		}
		else if (portletResponse instanceof ResourceResponse) {
			objects.put("resourceResponse", portletResponse);
		}
		else {
			objects.put("portletResponse", portletResponse);
		}

		return objects;
	}

	public Set<String> getSupportedLanguages() {
		return _scriptingExecutors.keySet();
	}

	private String _getErrorInfo(String code, Exception e) {
		StringBuilder sb = new StringBuilder();

		sb.append(e.getMessage());
		sb.append("\n");

		try{
			StringReader buffer = new StringReader(code);
			LineNumberReader lnReader = new LineNumberReader(buffer);

			String line;

			while ((line = lnReader.readLine()) != null){
				sb.append("Line ");
				sb.append(lnReader.getLineNumber());
				sb.append(": ");
				sb.append(line);
				sb.append("\n");
			}
		}
		catch (IOException ioe) {
			sb = new StringBuilder();

			sb.append(e.getMessage());
			sb.append("\n");
			sb.append(code);
		}

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(ScriptingImpl.class);

	private Map<String, ScriptingExecutor> _scriptingExecutors =
		new HashMap<String, ScriptingExecutor>();

}