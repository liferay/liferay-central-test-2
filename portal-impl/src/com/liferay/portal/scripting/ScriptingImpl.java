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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.portal.kernel.servlet.StringServletOutputStream;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PySyntaxError;

/**
 * <a href="ScriptingImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class ScriptingImpl implements Scripting {

	public void addScriptionExecutor(
		String language, ScriptingExecutor scriptingExecutor) {

		_scriptingExecutors.put(language, scriptingExecutor);
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
		objects.put("preferences", portletRequest.getPreferences());

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

	public void setScriptingExecutors(
		Map<String, ScriptingExecutor> scriptingExecutors) {

		for (Map.Entry<String, ScriptingExecutor> entry :
				scriptingExecutors.entrySet()) {

			_scriptingExecutors.put(entry.getKey(), entry.getValue());
		}
	}

	protected String getErrorMessage(Exception e) {
		String message = e.getMessage();

		if (e instanceof PySyntaxError) {
			PySyntaxError pySyntaxError = (PySyntaxError)e;

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			PrintStream ps = new PrintStream(
				new StringServletOutputStream(unsyncByteArrayOutputStream));

			Py.displayException(
				pySyntaxError.type, pySyntaxError.value,
				pySyntaxError.traceback, new PyFile(ps));

			message = unsyncByteArrayOutputStream.toString();
		}

		return message;
	}

	protected String getErrorMessage(String script, Exception e) {
		StringBuilder sb = new StringBuilder();

		sb.append(getErrorMessage(e));
		sb.append(StringPool.NEW_LINE);

		try{
			LineNumberReader lineNumberReader = new LineNumberReader(
				new UnsyncStringReader(script));

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

			sb.append(getErrorMessage(e));
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	private Map<String, ScriptingExecutor> _scriptingExecutors =
		new ConcurrentHashMap<String, ScriptingExecutor>();

}