/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.util.bridges.bsf;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

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
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BaseBSFPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseBSFPortlet extends GenericPortlet {

	public void init() throws PortletException {
		editFile = getInitParameter("edit-file");
		helpFile = getInitParameter("help-file");
		viewFile = getInitParameter("view-file");
		actionFile = getInitParameter("action-file");
		resourceFile = getInitParameter("resource-file");
		globalFiles = StringUtil.split(getInitParameter("global-files"));

		BSFManager.registerScriptingEngine(
			getScriptingEngineLanguage(), getScriptingEngineClassName(),
			new String[] {getScriptingEngineExtension()});

		bsfManager = new BSFManager();
	}

	public void doDispatch(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		String file = req.getParameter(getFileParam());

		if (file != null) {
			include(file, req, res);
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

	public void serveResource(ResourceRequest req, ResourceResponse res)
		throws IOException, PortletException {

		include(resourceFile, req, res);
	}

	protected void declareBeans(
			InputStream is, PortletRequest req, PortletResponse res)
		throws BSFException, IOException {

		declareBeans(new String(FileUtil.getBytes(is)), req, res);
	}

	protected void declareBeans(
			String code, PortletRequest req, PortletResponse res)
		throws BSFException, IOException {

		StringMaker sm = new StringMaker();

		sm.append(getGlobalScript());
		sm.append(code);

		String script = sm.toString();

		PortletConfig portletConfig = getPortletConfig();
		PortletContext portletContext = getPortletContext();
		PortletPreferences preferences = req.getPreferences();
		Map<String, String> userInfo = (Map<String, String>)req.getAttribute(
			PortletRequest.USER_INFO);

		bsfManager.declareBean(
			"portletConfig", portletConfig, PortletConfig.class);
		bsfManager.declareBean(
			"portletContext", portletContext, PortletContext.class);
		bsfManager.declareBean(
			"preferences", preferences, PortletPreferences.class);
		bsfManager.declareBean("userInfo", userInfo, Map.class);

		if (req instanceof ActionRequest) {
			bsfManager.declareBean("actionRequest", req, ActionRequest.class);
		}
		else if (req instanceof RenderRequest) {
			bsfManager.declareBean("renderRequest", req, RenderRequest.class);
		}
		else if (req instanceof ResourceRequest) {
			bsfManager.declareBean("resourceRequest", req, ResourceRequest.class);
		}

		if (res instanceof ActionResponse) {
			bsfManager.declareBean("actionResponse", res, ActionResponse.class);
		}
		else if (res instanceof RenderResponse) {
			bsfManager.declareBean("renderResponse", res, RenderResponse.class);
		}
		else if (res instanceof ResourceResponse) {
			bsfManager.declareBean(
				"resourceResponse", res, ResourceResponse.class);
		}

		bsfManager.exec(getScriptingEngineLanguage(), "(java)", 1, 1, script);
	}

	protected String getGlobalScript() throws IOException {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < globalFiles.length; i++) {
			InputStream is = getPortletContext().getResourceAsStream(
				globalFiles[i]);

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Global file " + globalFiles[i] + " does not exist");
				}
			}

			try {
				if (is != null) {
					sm.append(new String(FileUtil.getBytes(is)));
					sm.append(StringPool.NEW_LINE);
				}
			}
			finally {
				is.close();
			}
		}

		return sm.toString();
	}

	protected abstract String getFileParam();

	protected abstract String getScriptingEngineClassName();

	protected abstract String getScriptingEngineExtension();

	protected abstract String getScriptingEngineLanguage();

	protected void include(String path, PortletRequest req, PortletResponse res)
		throws IOException, PortletException {

		InputStream is = getPortletContext().getResourceAsStream(path);

		if (is == null) {
			_log.error(
				path + " is not a valid " + getScriptingEngineLanguage() +
					" file");

			return;
		}

		try {
			declareBeans(is, req, res);
		}
		catch (BSFException bsfe) {
			logBSFException(bsfe, path);
		}
		finally {
			is.close();
		}
	}

	protected abstract void logBSFException(BSFException bsfe, String path);

	protected String editFile;
	protected String helpFile;
	protected String viewFile;
	protected String actionFile;
	protected String resourceFile;
	protected String[] globalFiles;
	protected BSFManager bsfManager;

	private static Log _log = LogFactory.getLog(BaseBSFPortlet.class);

}