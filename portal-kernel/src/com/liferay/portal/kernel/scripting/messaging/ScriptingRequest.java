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

import java.io.Serializable;
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
public class ScriptingRequest implements Serializable {

	public static enum Command {

		CLEAR_CACHE, EVAL, EXEC, GET_PORTLET_OBJECTS, GET_SUPPORTED_LANGUAGES;
	}

	public ScriptingRequest(Command command) {
		_command = command;
	}

	public ScriptingRequest(Command command, String language, String script) {
		_command = command;
		_language = language;
		_script = script;
	}

	public ScriptingRequest(
		Command command, String language, String script,
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		Set<String> outputNames) {
		_command = command;
		_language = language;
		_script = script;
		_allowedClasses = allowedClasses;
		_inputObjects = inputObjects;
		_outputNames = outputNames;
	}

	public ScriptingRequest(
		Command command, PortletConfig portletConfig,
		PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		_command = command;
		_portletConfig = portletConfig;
		_portletContext = portletContext;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
	}

	public Set<String> getAllowedClasses() {
		return _allowedClasses;
	}

	public Command getCommand() {
		return _command;
	}

	public Map<String, Object> getInputObjects() {
		return _inputObjects;
	}

	public String getLanguage() {
		return _language;
	}

	public Set<String> getOutputNames() {
		return _outputNames;
	}

	public PortletConfig getPortletConfig() {
		return _portletConfig;
	}

	public PortletContext getPortletContext() {
		return _portletContext;
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	public PortletResponse getPortletResponse() {
		return _portletResponse;
	}

	public String getScript() {
		return _script;
	}

	public void setAllowedClasses(Set<String> allowedClasses) {
		_allowedClasses = allowedClasses;
	}

	public void setCommand(Command command) {
		_command = command;
	}

	public void setInputObjects(Map<String, Object> inputObjects) {
		_inputObjects = inputObjects;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public void setOutputNames(Set<String> outputNames) {
		_outputNames = outputNames;
	}

	public void setPortletConfig(PortletConfig portletConfig) {
		_portletConfig = portletConfig;
	}

	public void setPortletContext(PortletContext portletContext) {
		_portletContext = portletContext;
	}

	public void setPortletRequest(PortletRequest portletRequest) {
		_portletRequest = portletRequest;
	}

	public void setPortletResponse(PortletResponse portletResponse) {
		_portletResponse = portletResponse;
	}

	public void setScript(String script) {
		_script = script;
	}

	private Set<String> _allowedClasses;
	private Command _command;
	private Map<String, Object> _inputObjects;
	private String _language;
	private Set<String> _outputNames;
	private PortletConfig _portletConfig;
	private PortletContext _portletContext;
	private PortletRequest _portletRequest;
	private PortletResponse _portletResponse;
	private String _script;

}