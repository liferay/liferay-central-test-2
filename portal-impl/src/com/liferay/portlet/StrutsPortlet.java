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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;

/**
 * <a href="StrutsPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StrutsPortlet extends LiferayPortlet {

	public void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, aboutAction);

		include(renderRequest, renderResponse);
	}

	public void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, configAction);

		include(renderRequest, renderResponse);
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editAction);

			include(renderRequest, renderResponse);
		}
	}

	public void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editDefaultsAction);

			include(renderRequest, renderResponse);
		}
	}

	public void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editGuestAction);

			include(renderRequest, renderResponse);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, helpAction);

		include(renderRequest, renderResponse);
	}

	public void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_STRUTS_ACTION, previewAction);

		include(renderRequest, renderResponse);
	}

	public void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, printAction);

		include(renderRequest, renderResponse);
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, viewAction);

		include(renderRequest, renderResponse);
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		aboutAction = getInitParameter("about-action");
		configAction = getInitParameter("config-action");
		editAction = getInitParameter("edit-action");
		editDefaultsAction = getInitParameter("edit-defaults-action");
		editGuestAction = getInitParameter("edit-guest-action");
		helpAction = getInitParameter("help-action");
		previewAction = getInitParameter("preview-action");
		printAction = getInitParameter("print-action");
		viewAction = getInitParameter("view-action");

		copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"), true);

		_portletConfig = (PortletConfigImpl)portletConfig;
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String path = actionRequest.getParameter("struts_action");

		if (Validator.isNotNull(path)) {

			// Call processAction of com.liferay.portal.struts.PortletAction

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			try {
				permissionChecker.setValues(actionRequest);

				PortletRequestProcessor processor =
					_getPortletRequestProcessor(actionRequest);

				processor.process(actionRequest, actionResponse, path);
			}
			catch (ServletException se) {
				throw new PortletException(se);
			}
			finally {
				permissionChecker.resetValues();
			}
		}

		if (copyRequestParameters) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		resourceRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, viewAction);

		// Call serveResource of com.liferay.portal.struts.PortletAction

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			permissionChecker.setValues(resourceRequest);

			PortletRequestProcessor processor =
				_getPortletRequestProcessor(resourceRequest);

			processor.process(resourceRequest, resourceResponse);
		}
		catch (ServletException se) {
			throw new PortletException(se);
		}
		finally {
			permissionChecker.resetValues();
		}
	}

	protected void include(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		// Call render of com.liferay.portal.struts.PortletAction

		Map<String, Object> strutsAttributes = null;

		if (_portletConfig.isWARFile()) {
			strutsAttributes = StrutsUtil.removeStrutsAttributes(
				getPortletContext(), renderRequest);
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			permissionChecker.setValues(renderRequest);

			PortletRequestProcessor processor =
				_getPortletRequestProcessor(renderRequest);

			processor.process(renderRequest, renderResponse);
		}
		catch (ServletException se) {
			throw new PortletException(se);
		}
		finally {
			permissionChecker.resetValues();

			if (_portletConfig.isWARFile()) {
				StrutsUtil.setStrutsAttributes(renderRequest, strutsAttributes);
			}
		}

		if (copyRequestParameters) {
			PortalUtil.clearRequestParameters(renderRequest);
		}
	}

	private PortletRequestProcessor _getPortletRequestProcessor(
		PortletRequest portletRequest) {

		return (PortletRequestProcessor)getPortletContext().getAttribute(
			WebKeys.PORTLET_STRUTS_PROCESSOR);
	}

	protected String aboutAction;
	protected String configAction;
	protected String editAction;
	protected String editDefaultsAction;
	protected String editGuestAction;
	protected String helpAction;
	protected String previewAction;
	protected String printAction;
	protected String viewAction;
	protected boolean copyRequestParameters;

	private PortletConfigImpl _portletConfig;

}