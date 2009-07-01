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

package com.liferay.util.bridges.scripting;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.scripting.ScriptExecutionException;
import com.liferay.portal.scripting.ScriptingUtil;
import com.liferay.portal.scripting.UnsupportedLanguageException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * <a href="ScriptingPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alberto Montero
 *
 */
public class ScriptingPortlet extends GenericPortlet {

	public void init() {
		actionFile = getInitParameter("action-file");
		editFile = getInitParameter("edit-file");
		helpFile = getInitParameter("help-file");
		resourceFile = getInitParameter("resource-file");
		scriptingLanguage = getInitParameter("scripting-language");
		viewFile = getInitParameter("view-file");

		globalFiles = StringUtil.split(getInitParameter("global-files"));

	}

	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String file = getFileName(renderRequest);

		if (file != null) {
			include(file, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editFile, renderRequest, renderResponse);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		include(helpFile, renderRequest, renderResponse);
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		include(viewFile, renderRequest, renderResponse);
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		include(actionFile, actionRequest, actionResponse);
	}

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		Object errorMsg = SessionErrors.get(
			renderRequest, "scriptExecutionError");

		try {
			if (Validator.isNotNull(errorMsg)) {
				showErrorMessage(
					renderRequest, renderResponse,
					((Exception)errorMsg).getMessage());

				return;
			}

			super.render(renderRequest, renderResponse);

			errorMsg = SessionErrors.get(renderRequest, "scriptExecutionError");

			if (Validator.isNotNull(errorMsg)) {
				showErrorMessage(
					renderRequest, renderResponse,
					((Exception)errorMsg).getMessage());

				return;
			}
		}
		catch (SystemException se) {
			throw new PortletException(se);
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		include(resourceFile, resourceRequest, resourceResponse);
	}

	protected void declareBeans(
			InputStream is, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException, ScriptExecutionException,
			UnsupportedLanguageException {

		declareBeans(
			new String(FileUtil.getBytes(is)), portletRequest, portletResponse);
	}

	protected void declareBeans(
			String code, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException, ScriptExecutionException,
			UnsupportedLanguageException {

		StringBuilder sb = new StringBuilder();

		sb.append(getGlobalScript());
		sb.append(code);

		String script = sb.toString();

		PortletConfig portletConfig = getPortletConfig();
		PortletContext portletContext = getPortletContext();

		Map[] portletObjects =
			ScriptingUtil.getPortletObjects(
				portletConfig, portletContext, portletRequest, portletResponse);

		Map<String, Object> inputObjects = portletObjects[0];
		Map<String, Class> inputObjectTypes = portletObjects[1];

		long executionTime = - System.currentTimeMillis();
		ScriptingUtil.execUnconstrained(
			inputObjects, inputObjectTypes, scriptingLanguage, script);
		executionTime += System.currentTimeMillis();
		if (portletResponse instanceof RenderResponse) {
			((RenderResponse)portletResponse).getPortletOutputStream().write((code.hashCode() + " " + executionTime + "ms").getBytes());
		}
		_log.error(code.hashCode() + " " + executionTime + "ms");

	}

	protected String getFileName(RenderRequest renderRequest) {
		return renderRequest.getParameter("file");
	}

	protected String getGlobalScript() throws IOException {
		StringBuilder sb = new StringBuilder();

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
					sb.append(new String(FileUtil.getBytes(is)));
					sb.append(StringPool.NEW_LINE);
				}
			}
			finally {
				is.close();
			}
		}

		return sb.toString();
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException {

		InputStream is = getPortletContext().getResourceAsStream(path);

		if (is == null) {
			_log.error(
				path + " is not a valid " + scriptingLanguage + " file");

			return;
		}

		try {
			declareBeans(is, portletRequest, portletResponse);
		}
		catch (ScriptExecutionException see) {
			SessionErrors.add(portletRequest, "scriptExecutionError", see);
		}
		catch (UnsupportedLanguageException ule) {
			SessionErrors.add(portletRequest, "scriptExecutionError", ule);
		}
		finally {
			is.close();
		}
	}

	protected void showErrorMessage(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String errorMessage)
		throws IOException, SystemException, PortalException {

		boolean showDetailedInformation;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		showDetailedInformation = PortalPermissionUtil.contains(
			permissionChecker, ActionKeys.CONFIGURATION);

		renderResponse.resetBuffer();
		renderResponse.setContentType("text/html");
		OutputStream os = renderResponse.getPortletOutputStream();

		StringBuilder sb = new StringBuilder();

		if (showDetailedInformation) {
			User user = PortalUtil.getUser(renderRequest);
			sb.append("<span class='portlet-msg-error'>");
			sb.append(
				LanguageUtil.get(user.getLocale(), "error-running-script"));
			sb.append("</span>");
			sb.append("<pre>");
			sb.append(HtmlUtil.escape(errorMessage));
			sb.append("</pre>");
		}
		else {
			sb.append("<span class='portlet-msg-error'>");
			sb.append(
				LanguageUtil.get(
					LocaleUtil.getDefault(),
					"an-error-occurred.-please-contact-an-administrator"));
			sb.append("</span>");
		}

		os.write(sb.toString().getBytes());
	}

	protected String actionFile;
	protected String editFile;
	protected String helpFile;
	protected String resourceFile;
	protected String scriptingLanguage;
	protected String viewFile;

	protected String[] globalFiles;

	private static Log _log = LogFactoryUtil.getLog(ScriptingPortlet.class);

}