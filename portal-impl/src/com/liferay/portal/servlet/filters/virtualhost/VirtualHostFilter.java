/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webserver.WebServerServlet;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This filter is used to provide virtual host functionality.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class VirtualHostFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();

		String contextPath = PortalUtil.getPathContext();

		String proxyPath = PortalUtil.getPathProxy();

		if (!contextPath.isEmpty() && !proxyPath.isEmpty() &&
			contextPath.startsWith(proxyPath)) {

			contextPath = contextPath.substring(proxyPath.length());
		}

		_contextPath = contextPath;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		String uri = request.getRequestURI();

		for (String extension : PropsValues.VIRTUAL_HOSTS_IGNORE_EXTENSIONS) {
			if (uri.endsWith(extension)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isDocumentFriendlyURL(
			HttpServletRequest request, long groupId, String friendlyURL)
		throws PortalException {

		if (friendlyURL.startsWith(_PATH_DOCUMENTS) &&
			WebServerServlet.hasFiles(request)) {

			String path = HttpUtil.fixPath(request.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 2) {
				try {
					LayoutLocalServiceUtil.getFriendlyURLLayout(
						groupId, false, friendlyURL);
				}
				catch (NoSuchLayoutException nsle) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nsle, nsle);
					}

					return true;
				}
			}
			else {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidFriendlyURL(String friendlyURL) {
		friendlyURL = StringUtil.toLowerCase(friendlyURL);

		if (PortalInstances.isVirtualHostsIgnorePath(friendlyURL) ||
			friendlyURL.startsWith(_PATH_MODULE_SLASH) ||
			friendlyURL.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING_SLASH) ||
			friendlyURL.startsWith(_PRIVATE_USER_SERVLET_MAPPING_SLASH) ||
			friendlyURL.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING_SLASH)) {

			return false;
		}

		if (LayoutImpl.hasFriendlyURLKeyword(friendlyURL)) {
			return false;
		}

		int code = LayoutImpl.validateFriendlyURL(friendlyURL, false);

		if ((code > -1) &&
			(code != LayoutFriendlyURLException.ENDS_WITH_SLASH)) {

			return false;
		}

		return true;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected boolean isValidRequestURL(StringBuffer requestURL) {
		if (requestURL == null) {
			return false;
		}

		String url = requestURL.toString();

		for (String extension : PropsValues.VIRTUAL_HOSTS_IGNORE_EXTENSIONS) {
			if (url.endsWith(extension)) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(request);

		String originalFriendlyURL = request.getRequestURI();

		String friendlyURL = originalFriendlyURL;

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (!friendlyURL.equals(StringPool.SLASH) && !_contextPath.isEmpty() &&
			(friendlyURL.length() > _contextPath.length()) &&
			friendlyURL.startsWith(_contextPath) &&
			(friendlyURL.charAt(_contextPath.length()) == CharPool.SLASH)) {

			friendlyURL = friendlyURL.substring(_contextPath.length());
		}

		int pos = friendlyURL.indexOf(CharPool.SEMICOLON);

		if (pos != -1) {
			friendlyURL = friendlyURL.substring(0, pos);
		}

		String i18nLanguageId = _findLanguageId(friendlyURL);

		if (i18nLanguageId != null) {
			friendlyURL = friendlyURL.substring(i18nLanguageId.length());
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING, StringPool.BLANK);

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		if (!friendlyURL.equals(StringPool.SLASH) &&
			!isValidFriendlyURL(friendlyURL)) {

			_log.debug("Friendly URL is not valid");

			if (i18nLanguageId != null) {
				int offset =
					originalFriendlyURL.length() - friendlyURL.length() -
						i18nLanguageId.length();

				if (!originalFriendlyURL.regionMatches(
						offset, i18nLanguageId, 0, i18nLanguageId.length())) {

					String forwardURL = originalFriendlyURL;

					if (offset > 0) {
						String prefix = originalFriendlyURL.substring(
							0, offset);

						forwardURL = prefix.concat(i18nLanguageId);
					}
					else {
						forwardURL = i18nLanguageId;
					}

					forwardURL = forwardURL.concat(friendlyURL);

					RequestDispatcher requestDispatcher =
						_servletContext.getRequestDispatcher(forwardURL);

					requestDispatcher.forward(request, response);

					return;
				}
			}

			processFilter(
				VirtualHostFilter.class.getName(), request, response,
				filterChain);

			return;
		}

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (_log.isDebugEnabled()) {
			_log.debug("Layout set " + layoutSet);
		}

		if (layoutSet == null) {
			processFilter(
				VirtualHostFilter.class.getName(), request, response,
				filterChain);

			return;
		}

		try {
			Map<String, String[]> parameterMap = request.getParameterMap();

			String parameters = StringPool.BLANK;

			if (!parameterMap.isEmpty()) {
				parameters = HttpUtil.parameterMapToString(parameterMap);
			}

			LastPath lastPath = new LastPath(
				_contextPath, friendlyURL, parameters);

			request.setAttribute(WebKeys.LAST_PATH, lastPath);

			StringBundler forwardURL = new StringBundler(5);

			if (i18nLanguageId != null) {
				forwardURL.append(i18nLanguageId);
			}

			if (originalFriendlyURL.startsWith(
					PropsValues.WIDGET_SERVLET_MAPPING)) {

				forwardURL.append(PropsValues.WIDGET_SERVLET_MAPPING);

				friendlyURL = StringUtil.replaceFirst(
					friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING,
					StringPool.BLANK);
			}

			if (friendlyURL.equals(StringPool.SLASH) ||
				(PortalUtil.getPlidFromFriendlyURL(companyId, friendlyURL) <=
					0)) {

				Group group = layoutSet.getGroup();

				if (isDocumentFriendlyURL(
						request, group.getGroupId(), friendlyURL)) {

					processFilter(
						VirtualHostFilter.class.getName(), request, response,
						filterChain);

					return;
				}

				if (group.isGuest() && friendlyURL.equals(StringPool.SLASH) &&
					!layoutSet.isPrivateLayout()) {

					String homeURL = PortalUtil.getRelativeHomeURL(request);

					if (Validator.isNotNull(homeURL)) {
						friendlyURL = homeURL;
					}
				}
				else {
					if (layoutSet.isPrivateLayout()) {
						if (group.isUser()) {
							forwardURL.append(_PRIVATE_USER_SERVLET_MAPPING);
						}
						else {
							forwardURL.append(_PRIVATE_GROUP_SERVLET_MAPPING);
						}
					}
					else {
						forwardURL.append(_PUBLIC_GROUP_SERVLET_MAPPING);
					}

					forwardURL.append(group.getFriendlyURL());
				}
			}

			String forwardURLString = friendlyURL;

			if (forwardURL.index() > 0) {
				forwardURL.append(friendlyURL);

				forwardURLString = forwardURL.toString();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Forward to " + forwardURLString);
			}

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(forwardURLString);

			requestDispatcher.forward(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			processFilter(
				VirtualHostFilter.class.getName(), request, response,
				filterChain);
		}
	}

	private String _findLanguageId(String friendlyURL) {
		if (friendlyURL.isEmpty() ||
			(friendlyURL.charAt(0) != CharPool.SLASH)) {

			return null;
		}

		String lowerCaseLanguageId = friendlyURL;

		int index = friendlyURL.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			lowerCaseLanguageId = friendlyURL.substring(0, index);
		}

		lowerCaseLanguageId = StringUtil.toLowerCase(lowerCaseLanguageId);

		Map<String, String> languageIds = I18nServlet.getLanguageIdsMap();

		String languageId = languageIds.get(lowerCaseLanguageId);

		if (languageId == null) {
			return null;
		}

		return languageId;
	}

	private static final String _PATH_DOCUMENTS = "/documents/";

	private static final String _PATH_MODULE_SLASH =
		Portal.PATH_MODULE + StringPool.SLASH;

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING_SLASH =
		_PRIVATE_GROUP_SERVLET_MAPPING + StringPool.SLASH;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING_SLASH =
		_PRIVATE_USER_SERVLET_MAPPING + StringPool.SLASH;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING_SLASH =
		_PUBLIC_GROUP_SERVLET_MAPPING + StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualHostFilter.class);

	private String _contextPath;
	private ServletContext _servletContext;

}