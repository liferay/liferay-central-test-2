/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigImpl;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * <a href="PortletAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletAction extends Action {

	public static String getForwardKey(HttpServletRequest request) {
		PortletConfigImpl portletConfig =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return PortalUtil.getPortletNamespace(portletConfig.getPortletId()) +
			WebKeys.PORTLET_STRUTS_FORWARD;
	}

	public static String getForwardKey(PortletRequest portletRequest) {
		PortletConfigImpl portletConfig =
			(PortletConfigImpl)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return PortalUtil.getPortletNamespace(portletConfig.getPortletId()) +
			WebKeys.PORTLET_STRUTS_FORWARD;
	}

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		Boolean strutsExecute = (Boolean)request.getAttribute(
			WebKeys.PORTLET_STRUTS_EXECUTE);

		if ((strutsExecute != null) && strutsExecute.booleanValue()) {
			return strutsExecute(mapping, form, request, response);
		}
		else if (portletRequest instanceof RenderRequest) {
			return render(
				mapping, form, portletConfig, (RenderRequest)portletRequest,
				(RenderResponse)portletResponse);
		}
		else {
			serveResource(
				mapping, form, portletConfig, (ResourceRequest)portletRequest,
				(ResourceResponse)portletResponse);

			return mapping.findForward(ActionConstants.COMMON_NULL);
		}
	}

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		return super.execute(mapping, form, request, response);
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Forward to " + getForward(renderRequest));
		}

		return mapping.findForward(getForward(renderRequest));
	}

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String resourceId = resourceRequest.getResourceID();

		if (Validator.isNotNull(resourceId)) {
			PortletContext portletContext = portletConfig.getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher(resourceId);

			if (portletRequestDispatcher != null) {
				portletRequestDispatcher.forward(
					resourceRequest, resourceResponse);
			}
		}
	}

	protected String getForward(PortletRequest portletRequest) {
		return getForward(portletRequest, null);
	}

	protected String getForward(
		PortletRequest portletRequest, String defaultValue) {

		String forward = (String)portletRequest.getAttribute(
			getForwardKey(portletRequest));

		if (forward == null) {
			return defaultValue;
		}
		else {
			return forward;
		}
	}

	protected void setForward(PortletRequest portletRequest, String forward) {
		portletRequest.setAttribute(getForwardKey(portletRequest), forward);
	}

	protected ModuleConfig getModuleConfig(PortletRequest portletRequest) {
		return (ModuleConfig)portletRequest.getAttribute(Globals.MODULE_KEY);
	}

	protected MessageResources getResources() {
		ServletContext servletContext = getServlet().getServletContext();

		return (MessageResources)servletContext.getAttribute(
			Globals.MESSAGES_KEY);
	}

	protected MessageResources getResources(HttpServletRequest request) {
		return getResources();
	}

	protected MessageResources getResources(PortletRequest portletRequest) {
		return getResources();
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		sendRedirect(actionRequest, actionResponse, null);
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String redirect)
		throws IOException {

		if (SessionErrors.isEmpty(actionRequest)) {
			String successMessage = ParamUtil.getString(
				actionRequest, "successMessage");

			SessionMessages.add(
				actionRequest, "request_processed", successMessage);
		}

		if (Validator.isNull(redirect)) {
			redirect = ParamUtil.getString(actionRequest, "redirect");
		}

		if (Validator.isNotNull(redirect)) {

			// LPS-1928

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			if ((BrowserSnifferUtil.isIe(request)) &&
				(BrowserSnifferUtil.getMajorVersion(request) == 6.0) &&
				(redirect.contains(StringPool.POUND))) {

				String redirectToken = "&#";

				if (!redirect.contains(StringPool.QUESTION)) {
					redirectToken = StringPool.QUESTION + redirectToken;
				}

				redirect = StringUtil.replace(
					redirect, StringPool.POUND, redirectToken);
			}

			actionResponse.sendRedirect(redirect);
		}
	}

	protected boolean redirectToLogin(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		if (actionRequest.getRemoteUser() == null) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			SessionErrors.add(request, PrincipalException.class.getName());

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			actionResponse.sendRedirect(themeDisplay.getURLSignIn());

			return true;
		}
		else {
			return false;
		}
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = true;

	private static Log _log = LogFactoryUtil.getLog(PortletAction.class);

}