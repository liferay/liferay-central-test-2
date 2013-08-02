/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.servlet.UserResolver;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServlet extends JSONServlet {

	@Override
	public void destroy() {
		_jsonWebServiceServiceAction.destroy();

		super.destroy();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (PortalUtil.isMultipartRequest(request)) {
			UploadServletRequest uploadServletRequest =
				new UploadServletRequestImpl(request);

			request = uploadServletRequest;
		}

		String path = GetterUtil.getString(request.getPathInfo());

		if (!path.equals(StringPool.SLASH) && !path.equals(StringPool.BLANK)) {
			Locale locale = PortalUtil.getLocale(request, response, true);

			LocaleThreadLocal.setThemeDisplayLocale(locale);

			super.service(request, response);

			return;
		}

		String uri = request.getRequestURI();

		int pos = uri.indexOf("/secure/");

		if (pos != -1) {
			uri = uri.substring(0, pos) + uri.substring(pos + 7);

			String queryString = request.getQueryString();

			if (queryString != null) {
				uri = uri.concat(StringPool.QUESTION).concat(queryString);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect from secure to public");
			}

			response.sendRedirect(uri);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Servlet context " + request.getContextPath());
		}

		String apiPath = PortalUtil.getPathMain() + "/portal/api/jsonws";

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String contextPath = PropsValues.PORTAL_CTX;

		if (servletContext.getContext(contextPath) != null) {
			if (!contextPath.equals(StringPool.SLASH) &&
				apiPath.startsWith(contextPath)) {

				apiPath = apiPath.substring(contextPath.length());
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
				apiPath);

			requestDispatcher.forward(request, response);
		}
		else {
			String servletContextPath = ContextPathUtil.getContextPath(
				servletContext);

			String redirectPath =
				"/api/jsonws?contextPath=" +
					HttpUtil.encodeURL(servletContextPath);

			response.sendRedirect(redirectPath);
		}
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction(
			ContextPathUtil.getContextPath(servletContext), classLoader);

		_jsonWebServiceServiceAction.setServletContext(servletContext);

		return _jsonWebServiceServiceAction;
	}

	@Override
	protected void resolveRemoteUser(HttpServletRequest request)
		throws Exception {

		UserResolver userResolver = new UserResolver(request);

		CompanyThreadLocal.setCompanyId(userResolver.getCompanyId());

		request.setAttribute("companyId", userResolver.getCompanyId());

		User user = userResolver.getUser();

		if (user != null) {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			request.setAttribute("user", user);
			request.setAttribute("userId", user.getUserId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServlet.class);

	private JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}