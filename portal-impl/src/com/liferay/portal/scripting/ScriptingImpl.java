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
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

/**
 * <a href="ScriptingImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 *
 */
public class ScriptingImpl implements Scripting {

	public ScriptingImpl() {
		_scriptingWrappers.put("python", new PythonWrapper());
		_scriptingWrappers.put("groovy", new GroovyWrapper());
		_scriptingWrappers.put("javascript", new JavaScriptWrapper());
		_scriptingWrappers.put("ruby", new RubyWrapper());
	}

	public Map[] getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletPreferences preferences = portletRequest.getPreferences();
		Map<String, String> userInfo =
			(Map<String, String>)portletRequest.getAttribute(
				PortletRequest.USER_INFO);

		Map<String, Object> input = new HashMap<String, Object>();
		Map<String, Class> types = new HashMap<String, Class>();

		input.put("portletConfig", portletConfig);
		types.put("portletConfig", PortletConfig.class);

		input.put("portletContext", portletContext);
		types.put("portletContext", PortletContext.class);

		input.put("preferences", preferences);
		types.put("preferences", PortletPreferences.class);

		input.put("userInfo", userInfo);
		types.put("userInfo", Map.class);

		input.put("portletRequest", portletRequest);
		types.put("portletRequest", PortletRequest.class);

		if (portletRequest instanceof ActionRequest) {
			input.put("actionRequest", portletRequest);
			types.put("actionRequest", ActionRequest.class);
		}
		else if (portletRequest instanceof RenderRequest) {
			input.put("renderRequest", portletRequest);
			types.put("renderRequest", RenderRequest.class);
		}
		else if (portletRequest instanceof ResourceRequest) {
			input.put("resourceRequest", portletRequest);
			types.put("resourceRequest", ResourceRequest.class);
		}

		input.put("portletResponse", portletResponse);
		types.put("portletResponse", PortletResponse.class);

		if (portletResponse instanceof ActionResponse) {
			input.put("actionResponse", portletResponse);
			types.put("actionResponse", ActionResponse.class);
		}
		else if (portletResponse instanceof RenderResponse) {
			input.put("renderResponse", portletResponse);
			types.put("renderResponse", RenderResponse.class);
		}
		else if (portletResponse instanceof ResourceResponse) {
			input.put("resourceResponse", portletResponse);
			types.put("resourceResponse", ResourceResponse.class);
		}

		return new Map[] {input, types};
	}

	public Set<String> getSupportedLanguages() {
		return _scriptingWrappers.keySet();
	}

	public Map<String, Object> eval(
			Map<String, Object> inputObjects, String lang,
			Set<String> outputObjectNames, String script)
		throws ScriptExecutionException, UnsupportedLanguageException {

		return eval(
			inputObjects, new HashMap<String, Class>(), lang, outputObjectNames,
			script);
	}

	public Map<String, Object> eval(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, String lang,
			Set<String> outputObjectNames, String script)
		throws ScriptExecutionException, UnsupportedLanguageException {

		if (Validator.isNull(lang)) {
			throw new UnsupportedLanguageException(
				"No scripting language specified");
		}

		try {
			ScriptingWrapper wrapper = _scriptingWrappers.get(lang);

			if (wrapper == null) {
				String msg =
					"Scripting language '" + lang + "' is not supported.";

				_log.warn(msg);

				throw new UnsupportedLanguageException(msg);
			}

			return wrapper.eval(
				inputObjects, inputObjectTypes, outputObjectNames, script);
		} catch (Exception e) {
			throw new ScriptExecutionException(_getErrorInfo(script, e), e);
		}
	}

	public void exec(
			Map<String, Object> inputObjects, String lang, String script)
		throws ScriptExecutionException, UnsupportedLanguageException {

		eval(
			inputObjects, new HashMap<String, Class>(), lang, _EMPTY_SET,
			script);

	}

	public void exec(
			Map<String, Object> inputObjects,
			Map<String, Class> inputObjectTypes, String lang, String script)
		throws ScriptExecutionException, UnsupportedLanguageException {

		eval(inputObjects, inputObjectTypes, lang, _EMPTY_SET, script);
	}

	private String _getErrorInfo(String code, Exception e) {
		StringBuilder sb = new StringBuilder();

		sb.append(e.getMessage());
		sb.append("\n");

		LineNumberReader lnReader;

		try{
			StringReader buffer = new StringReader(code);
			lnReader = new LineNumberReader(buffer);

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

	private Map<String, ScriptingWrapper> _scriptingWrappers =
		new HashMap<String, ScriptingWrapper>();

	private static Log _log = LogFactoryUtil.getLog(
		ScriptingImpl.class);

	private static final HashSet<String> _EMPTY_SET = new HashSet<String>();

}