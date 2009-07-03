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
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.scripting.groovy.GroovyExecutor;
import com.liferay.portal.scripting.javascript.JavaScriptExecutor;
import com.liferay.portal.scripting.python.PythonExecutor;
import com.liferay.portal.scripting.ruby.RubyExecutor;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * <a href="ScriptingImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 *
 */
public class ScriptingImpl implements Scripting {

	public void afterPropertiesSet() {
		_scriptingExecutors.put(GroovyExecutor.LANGUAGE, new GroovyExecutor());
		_scriptingExecutors.put(
			JavaScriptExecutor.LANGUAGE, new JavaScriptExecutor());
		_scriptingExecutors.put(PythonExecutor.LANGUAGE, new PythonExecutor());
		_scriptingExecutors.put(RubyExecutor.LANGUAGE, new RubyExecutor());
	}

	public void clearCache(String language) throws ScriptingException {
		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		scriptingExecutor.clearCache();
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		try {
			return scriptingExecutor.eval(
				allowedClasses, inputObjects, outputNames, script);
		}
		catch (Exception e) {
			throw new ScriptingException(getErrorMessage(script, e), e);
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

		Map<String, Object> objects = new HashMap<String, Object>();

		objects.put("portletConfig", portletConfig);
		objects.put("portletContext", portletContext);
		objects.put("portletPreferences", portletRequest.getPreferences());

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

		objects.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		return objects;
	}

	public Set<String> getSupportedLanguages() {
		return _scriptingExecutors.keySet();
	}

	protected String getErrorMessage(String script, Exception e) {
		StringBuilder sb = new StringBuilder();

		sb.append(e.getMessage());
		sb.append(StringPool.NEW_LINE);

		try{
			LineNumberReader lineNumberReader = new LineNumberReader(
				new StringReader(script));

			while (true) {
				String line = lineNumberReader.readLine();

				if (line == null) {
					break;
				}

				sb.append("Line ");
				sb.append(lineNumberReader.getLineNumber());
				sb.append(": ");
				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}
		}
		catch (IOException ioe) {
			sb = new StringBuilder();

			sb.append(e.getMessage());
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(ScriptingImpl.class);

	private Map<String, ScriptingExecutor> _scriptingExecutors =
		new HashMap<String, ScriptingExecutor>();

}