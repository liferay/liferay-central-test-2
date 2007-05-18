/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.sample.ruby.portlet;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jruby.RubyException;
import org.jruby.exceptions.RaiseException;

/**
 * <a href="RubyPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class RubyPortlet extends GenericPortlet {

	public void init() throws PortletException {
		editFile = getInitParameter("edit-file");
		helpFile = getInitParameter("help-file");
		viewFile = getInitParameter("view-file");
		actionFile = getInitParameter("action-file");

		globalFiles = _split(getInitParameter("global-files"));

		BSFManager.registerScriptingEngine(
			"ruby", "org.jruby.javasupport.bsf.JRubyEngine",
			new String[]{"rb"});

		_manager = new BSFManager();

	}

	public void doDispatch(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		String jspPage = req.getParameter("rubyFile");

		if (jspPage != null) {
			include(jspPage, req, res);
		}
		else {
			super.doDispatch(req, res);
		}
	}

	public void doEdit(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		if (req.getPreferences() == null) {
			super.doEdit(req, res);
		}
		else {
			include(editFile, req, res);
		}
	}

	public void doHelp(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		include(helpFile, req, res);
	}

	public void doView(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		include(viewFile, req, res);
	}

	public void processAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {
		include(actionFile, req, res);
	}

	protected void include(String path, PortletRequest req, PortletResponse res)
		throws IOException, PortletException {

		InputStream is = getPortletContext().getResourceAsStream(path);

		if (is == null) {
			_log.error(path + " is not a valid ruby file");
			return;
		}

		try {
			String script = _getGlobalScript() + _readAll(is);

			_manager.declareBean(
				"portletConfig", getPortletConfig(), PortletConfig.class);
			_manager.declareBean(
				"portletContext", getPortletContext(), PortletContext.class);
			_manager.declareBean(
				"userInfo", req.getAttribute(PortletRequest.USER_INFO),
				Map.class);
			_manager.declareBean(
				"preferences", req.getPreferences(), PortletPreferences.class);

			if (req instanceof ActionRequest) {
				_manager.declareBean(
					"actionRequest", req, ActionRequest.class);
			}
			else if (req instanceof RenderRequest) {
				_manager.declareBean(
					"renderRequest", req, RenderRequest.class);
			}

			if (res instanceof ActionResponse) {
				_manager.declareBean(
					"actionResponse", res, ActionResponse.class);
			}
			else if (res instanceof RenderResponse) {
				_manager.declareBean(
					"renderResponse", res, RenderResponse.class);
			}

			_manager.exec("ruby", "(java)", 1, 1, script);

		}
		catch (BSFException e) {
			String message =
				"The script at " + path + " or one of the global files has "
					+ "errors: ";
			if (e.getTargetException() instanceof RaiseException) {
				RubyException re =
					((RaiseException) e.getTargetException()).getException();
				message +=
					re.message + " (" + re.getMetaClass().toString() + ")";
				_log.error(message);
				_log.debug("Ruby exception:", e.getTargetException());
			}
			else {
				_log.error(message, e.getTargetException());
			}
		}
		finally {
			is.close();
		}
	}

	private String _getGlobalScript() throws IOException {
		StringBuffer script = new StringBuffer();
		for (int i = 0; i < globalFiles.length; i++) {

			InputStream is =
				getPortletContext().getResourceAsStream(globalFiles[i]);

			if (is == null) {
				_log.warn("Global file '" + globalFiles[i] + "' not found");
			}
			try {
				script.append(_readAll(is));
			} finally {
				is.close();
			}

		}
		return script.toString();
	}

	private String _readAll(InputStream is) throws IOException {
		byte[] bytes = new byte[is.available()];

		is.read(bytes);

		return new String(bytes);
	}

	private String[] _split(String listString) {
		List result = new ArrayList();
		StringTokenizer st = new StringTokenizer(listString, ",");
		while (st.hasMoreTokens()) {
			String item = st.nextToken();
			if ((item != null && item.trim().length() > 0)) {
				result.add(item.trim());
			}
		}
		return (String[]) result.toArray(new String[0]);
	}

	protected String editFile;
	protected String helpFile;
	protected String viewFile;
	protected String actionFile;
	protected String[] globalFiles;

	private static Log _log = LogFactory.getLog(RubyPortlet.class);

	private BSFManager _manager;
}