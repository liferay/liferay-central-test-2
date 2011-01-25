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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.Theme;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ThemeHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ThemeHelper {

	public static final String TEMPLATE_EXTENSION_FTL = "ftl";

	public static final String TEMPLATE_EXTENSION_VM = "vm";

	public static String getRequestPath(HttpServletRequest request) {
		return getRequestPath(null, request);
	}

	public static String getRequestPath(
		ServletContext servletContext, HttpServletRequest request) {

		String requestPath = request.getPathInfo();

		String requestURI = request.getRequestURI();

		if (Validator.isNull(requestPath) && Validator.isNotNull(requestURI)) {
			requestPath = requestURI;
		}

		if (!requestPath.startsWith(StringPool.SLASH)) {
			requestPath = StringPool.SLASH.concat(requestPath);
		}

		if (servletContext != null) {
			String servletContextName = GetterUtil.getString(
				servletContext.getServletContextName());

			if (Validator.isNotNull(servletContextName) &&
				requestPath.startsWith(
					StringPool.SLASH.concat(servletContextName))) {

				requestPath = requestPath.substring(
					servletContextName.length() + 1);
			}
		}

		return requestPath;
	}

	public static String getResourcePath(
		ServletContext servletContext, Theme theme, String path) {

		StringBundler sb = new StringBundler(9);

		String themeContextName = GetterUtil.getString(
			theme.getServletContextName());

		sb.append(themeContextName);

		String servletContextName = GetterUtil.getString(
			servletContext.getServletContextName());

		int start = 0;

		if (path.startsWith(StringPool.SLASH)) {
			start = 1;
		}

		int end = path.lastIndexOf(CharPool.PERIOD);

		String extension = theme.getTemplateExtension();

		if (extension.equals(TEMPLATE_EXTENSION_FTL)) {
			sb.append(theme.getFreeMarkerTemplateLoader());
			sb.append(theme.getTemplatesPath());

			if (Validator.isNotNull(servletContextName)) {
				sb.append(StringPool.SLASH);
				sb.append(servletContextName);
			}

			sb.append(StringPool.SLASH);
			sb.append(path.substring(start, end));
			sb.append(StringPool.PERIOD);
			sb.append(TEMPLATE_EXTENSION_FTL);

			return sb.toString();
		}
		else if (extension.equals(TEMPLATE_EXTENSION_VM)) {
			sb.append(theme.getVelocityResourceListener());
			sb.append(theme.getTemplatesPath());

			if (Validator.isNotNull(servletContextName)) {
				sb.append(StringPool.SLASH);
				sb.append(servletContextName);
			}

			sb.append(StringPool.SLASH);
			sb.append(path.substring(start, end));
			sb.append(StringPool.PERIOD);
			sb.append(TEMPLATE_EXTENSION_VM);

			return sb.toString();
		}
		else {
			return path;
		}
	}

	public static boolean resourceExists(
			ServletContext servletContext, Theme theme, String path)
		throws Exception {

		if (Validator.isNull(path)) {
			return false;
		}

		String resourcePath = getResourcePath(servletContext, theme, path);

		String extension = theme.getTemplateExtension();

		if (extension.equals(TEMPLATE_EXTENSION_FTL)) {
			return FreeMarkerEngineUtil.resourceExists(resourcePath);
		}
		else if (extension.equals(TEMPLATE_EXTENSION_VM)) {
			return VelocityEngineUtil.resourceExists(resourcePath);
		}
		else {
			URL url = null;

			if (theme.isWARFile()) {
				ServletContext themeServletContext = servletContext.getContext(
					theme.getContextPath());

				url = themeServletContext.getResource(resourcePath);
			}
			else {
				url = servletContext.getResource(resourcePath);
			}

			if (url == null) {
				return false;
			}
			else {
				return true;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeHelper.class);

}