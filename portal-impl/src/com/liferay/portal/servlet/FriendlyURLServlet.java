/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class FriendlyURLServlet extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_private = GetterUtil.getBoolean(
			servletConfig.getInitParameter("private"));
		_user = GetterUtil.getBoolean(
			servletConfig.getInitParameter("user"));
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		// Do not set the entire full main path. See LEP-456.

		//String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);
		String mainPath = Portal.PATH_MAIN;

		String friendlyURLPath = null;

		if (_private) {
			if (_user) {
				friendlyURLPath = PortalUtil.getPathFriendlyURLPrivateUser();
			}
			else {
				friendlyURLPath = PortalUtil.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			friendlyURLPath = PortalUtil.getPathFriendlyURLPublic();
		}

		request.setAttribute(
			WebKeys.FRIENDLY_URL, friendlyURLPath + request.getPathInfo());

		String redirect = mainPath;

		try {
			redirect = getRedirect(
				request, request.getPathInfo(), mainPath,
				request.getParameterMap());

			if (request.getAttribute(WebKeys.LAST_PATH) == null) {
				LastPath lastPath = new LastPath(
					friendlyURLPath, request.getPathInfo(),
					request.getParameterMap());

				request.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsle, request, response);

			return;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		if (Validator.isNull(redirect)) {
			redirect = mainPath;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if (redirect.startsWith(StringPool.SLASH)) {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			response.sendRedirect(redirect);
		}
	}

	protected String getRedirect(
			HttpServletRequest request, String path, String mainPath,
			Map<String, String[]> params)
		throws Exception {

		if (Validator.isNull(path)) {
			return mainPath;
		}

		if (!path.startsWith(StringPool.SLASH)) {
			return mainPath;
		}

		// Group friendly URL

		String friendlyURL = null;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
		}
		else {
			if (path.length() > 1) {
				friendlyURL = path.substring(0, path.length());
			}
		}

		if (Validator.isNull(friendlyURL)) {
			return mainPath;
		}

		long companyId = PortalInstances.getCompanyId(request);

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, friendlyURL);
		}
		catch (NoSuchGroupException nsge) {
		}

		if (group == null) {
			String screenName = friendlyURL.substring(1);

			if (_user || !Validator.isNumber(screenName)) {
				try {
					User user = UserLocalServiceUtil.getUserByScreenName(
						companyId, screenName);

					group = user.getGroup();
				}
				catch (NoSuchUserException nsue) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No user exists with friendly URL " + screenName);
					}
				}
			}
			else {
				long groupId = GetterUtil.getLong(screenName);

				try {
					group = GroupLocalServiceUtil.getGroup(groupId);
				}
				catch (NoSuchGroupException nsge) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"No group exists with friendly URL " + groupId +
								". Try fetching by screen name instead.");
					}

					try {
						User user = UserLocalServiceUtil.getUserByScreenName(
							companyId, screenName);

						group = user.getGroup();
					}
					catch (NoSuchUserException nsue) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"No user or group exists with friendly URL " +
									groupId);
						}
					}
				}
			}
		}

		if (group == null) {
			return mainPath;
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			friendlyURL = path.substring(pos, path.length());
		}

		Map<String, Object> requestContext = new HashMap<String, Object>();

		requestContext.put("request", request);

		return PortalUtil.getLayoutActualURL(
			group.getGroupId(), _private, mainPath, friendlyURL, params,
			requestContext);
	}

	private static Log _log = LogFactoryUtil.getLog(FriendlyURLServlet.class);

	private boolean _private;
	private boolean _user;

}