/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.struts;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletRequestDispatcherImpl;

import java.io.IOException;

import java.lang.reflect.Constructor;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletRequestProcessor extends TilesRequestProcessor {

	public static PortletRequestProcessor getInstance(
			ActionServlet servlet, ModuleConfig moduleConfig)
		throws ServletException {

		try {
			String className = PropsValues.STRUTS_PORTLET_REQUEST_PROCESSOR;

			Class<?> clazz = Class.forName(className);

			Constructor<?> constructor = clazz.getConstructor(
				new Class[] {
					ActionServlet.class, ModuleConfig.class
				}
			);

			PortletRequestProcessor portletReqProcessor =
				(PortletRequestProcessor)constructor.newInstance(
					new Object[] {
						servlet, moduleConfig
					}
				);

			return portletReqProcessor;
		}
		catch (Exception e) {
			_log.error(e);

			return new PortletRequestProcessor(servlet, moduleConfig);
		}
	}

	public PortletRequestProcessor(
			ActionServlet actionServlet, ModuleConfig moduleConfig)
		throws ServletException {

		init(actionServlet, moduleConfig);
	}

	public void process(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String path)
		throws IOException, ServletException {

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		ActionMapping mapping = processMapping(request, response, path);

		if (mapping == null) {
			return;
		}

		if (!processRoles(request, response, mapping, true)) {
			return;
		}

		ActionForm form = processActionForm(request, response, mapping);

		processPopulate(request, response, form, mapping);

		if (!processValidateAction(request, response, form, mapping)) {
			return;
		}

		PortletAction action =
			(PortletAction)processActionCreate(request, response, mapping);

		if (action == null) {
			return;
		}

		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)actionRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		try {
			if (action.isCheckMethodOnProcessAction()) {
				if (!PortalUtil.isMethodPost(actionRequest)) {
					String currentURL = PortalUtil.getCurrentURL(actionRequest);

					if (_log.isWarnEnabled()) {
						_log.warn(
							"This URL can only be invoked using POST: " +
								currentURL);
					}

					throw new PrincipalException(currentURL);
				}
			}

			action.processAction(
				mapping, form, portletConfigImpl, actionRequest,
				actionResponse);
		}
		catch (Exception e) {
			String exceptionId =
				WebKeys.PORTLET_STRUTS_EXCEPTION + StringPool.PERIOD +
					portletConfigImpl.getPortletId();

			actionRequest.setAttribute(exceptionId, e);
		}

		String forward = (String)actionRequest.getAttribute(
			PortletAction.getForwardKey(actionRequest));

		if (forward != null) {
			String queryString = StringPool.BLANK;

			int pos = forward.indexOf(StringPool.QUESTION);

			if (pos != -1) {
				queryString = forward.substring(pos + 1, forward.length());
				forward = forward.substring(0, pos);
			}

			ActionForward actionForward = mapping.findForward(forward);

			if ((actionForward != null) && (actionForward.getRedirect())) {
				String forwardPath = actionForward.getPath();

				if (forwardPath.startsWith(StringPool.SLASH)) {
					LiferayPortletURL forwardURL =
						(LiferayPortletURL)actionResponseImpl.createRenderURL();

					forwardURL.setParameter("struts_action", forwardPath);

					StrutsURLEncoder.setParameters(forwardURL, queryString);

					forwardPath = forwardURL.toString();
				}

				actionResponse.sendRedirect(forwardPath);
			}
		}
	}

	public void process(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, ServletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			renderResponse);

		process(request, response);
	}

	public void process(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, ServletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		process(request, response);
	}

	protected void doForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		doInclude(uri, request, response);
	}

	protected void doInclude(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		PortletConfigImpl portletConfig =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletRequestDispatcherImpl portletRequestDispatcher =
			(PortletRequestDispatcherImpl)portletContext.getRequestDispatcher(
				StrutsUtil.TEXT_HTML_DIR + uri);

		try {
			if (portletRequestDispatcher == null) {
				_log.error(uri + " is not a valid include");
			}
			else {
				portletRequestDispatcher.include(
					portletRequest, portletResponse, true);
			}
		}
		catch (PortletException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof ServletException) {
				throw (ServletException)cause;
			}
			else {
				_log.error(cause, cause);
			}
		}
	}

	protected ActionForm processActionForm(
		HttpServletRequest request, HttpServletResponse response,
		ActionMapping mapping) {

		ActionForm form = super.processActionForm(request, response, mapping);

		if (form instanceof InitializableActionForm) {
			InitializableActionForm initForm = (InitializableActionForm)form;

			initForm.init(request, response, mapping);
		}

		return form;
	}

	protected ActionForward processActionPerform(
			HttpServletRequest request, HttpServletResponse response,
			Action action, ActionForm form, ActionMapping mapping)
		throws IOException, ServletException {

		PortletConfigImpl portletConfig =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		String exceptionId =
			WebKeys.PORTLET_STRUTS_EXCEPTION + StringPool.PERIOD +
				portletConfig.getPortletId();

		Exception e = (Exception)request.getAttribute(exceptionId);

		if (e != null) {
			return processException(request, response, e, form, mapping);
		}
		else {
			return super.processActionPerform(
				request, response, action, form, mapping);
		}
	}

	protected void processForwardConfig(
			HttpServletRequest request, HttpServletResponse response,
			ForwardConfig forward)
		throws IOException, ServletException {

		if (forward == null) {
			_log.error("Forward does not exist");
		}
		else {

			// Don't render a null path. This is useful if you're sending a file
			// in an exclusive window state.

			if (forward.getPath().equals(ActionConstants.COMMON_NULL)) {
				return;
			}
		}

		super.processForwardConfig(request, response, forward);
	}

	public ActionMapping processMapping(
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException {

		if (path == null) {
			return null;
		}

		ActionMapping mapping = super.processMapping(request, response, path);

		if (mapping == null) {
			String msg = getInternal().getMessage("processInvalid");

			_log.error("User ID " + request.getRemoteUser());
			_log.error("Current URL " + PortalUtil.getCurrentURL(request));
			_log.error("Referer " + request.getHeader("Referer"));
			_log.error("Remote address " + request.getRemoteAddr());

			_log.error(msg + " " + path);
		}

		return mapping;
	}

	protected HttpServletRequest processMultipart(HttpServletRequest request) {

		// Disable Struts from automatically wrapping a multipart request

		return request;
	}

	protected String processPath(
		HttpServletRequest request, HttpServletResponse response) {

		String path = request.getParameter("struts_action");

		if (_log.isDebugEnabled()) {
			_log.debug("Getting request parameter path " + path);
		}

		if (Validator.isNull(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Getting request attribute path " + path);
			}

			path = (String)request.getAttribute(WebKeys.PORTLET_STRUTS_ACTION);
		}

		if (path == null) {
			PortletConfigImpl portletConfig =
				(PortletConfigImpl)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			_log.error(
				portletConfig.getPortletName() +
					" does not have any paths specified");
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing path " + path);
			}
		}

		return path;
	}

	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping)
		throws IOException, ServletException {

		return processRoles(request, response, mapping, false);
	}

	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping, boolean action)
		throws IOException, ServletException {

		long companyId = PortalUtil.getCompanyId(request);

		String path = mapping.getPath();

		try {
			PortletConfigImpl portletConfig =
				(PortletConfigImpl)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletConfig.getPortletId());

			if (portlet == null) {
				return false;
			}

			String strutsPath = path.substring(
				1, path.lastIndexOf(StringPool.SLASH));

			if (!strutsPath.equals(portlet.getStrutsPath())) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"The struts path " + strutsPath + " does not belong " +
							"to portlet " + portlet.getPortletId() + ". " +
								"Check the definition in liferay-portlet.xml");
				}

				throw new PrincipalException();
			}
			else if (portlet.isActive()) {
				if (PortalUtil.isAllowAddPortletDefaultResource(
						request, portlet)) {

					PortalUtil.addPortletDefaultResource(request, portlet);
				}

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				Layout layout = themeDisplay.getLayout();
				PermissionChecker permissionChecker =
					themeDisplay.getPermissionChecker();

				if (!PortletPermissionUtil.contains(
						permissionChecker, layout.getPlid(), portlet,
						ActionKeys.VIEW)) {

					throw new PrincipalException();
				}
			}
			else if (!portlet.isActive()) {
				ForwardConfig forwardConfig =
					mapping.findForward(_PATH_PORTAL_PORTLET_INACTIVE);

				if (!action) {
					processForwardConfig(request, response, forwardConfig);
				}

				return false;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}

			ForwardConfig forwardConfig =
				mapping.findForward(_PATH_PORTAL_PORTLET_ACCESS_DENIED);

			if (!action) {
				processForwardConfig(request, response, forwardConfig);
			}

			return false;
		}

		return true;
	}

	protected boolean processValidateAction(
		HttpServletRequest request, HttpServletResponse response,
		ActionForm form, ActionMapping mapping) {

		if (form == null) {
			return true;
		}

		if (request.getAttribute(Globals.CANCEL_KEY) != null) {
			return true;
		}

		if (!mapping.getValidate()) {
			return true;
		}

		ActionErrors errors = form.validate(mapping, request);

		if ((errors == null) || errors.isEmpty()) {
			return true;
		}

		if (form.getMultipartRequestHandler() != null) {
			form.getMultipartRequestHandler().rollback();
		}

		String input = mapping.getInput();

		if (input == null) {
			_log.error("Validation failed but no input form is available");

			return false;
		}

		request.setAttribute(Globals.ERROR_KEY, errors);

		// Struts normally calls internalModuleRelativeForward which breaks
		// if called inside processAction

		request.setAttribute(PortletAction.getForwardKey(request), input);

		return false;
	}

	private static final String _PATH_PORTAL_PORTLET_ACCESS_DENIED =
		"/portal/portlet_access_denied";

	private static final String _PATH_PORTAL_PORTLET_INACTIVE =
		"/portal/portlet_inactive";

	private static Log _log = LogFactoryUtil.getLog(
		PortletRequestProcessor.class);

}