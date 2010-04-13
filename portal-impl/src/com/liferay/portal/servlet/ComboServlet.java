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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.SystemProperties;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ComboServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class ComboServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();

		if (parameterMap.size() == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		byte[] bytes = null;

		File cacheFile = getCacheFile(request);

		if (cacheFile.exists()) {
			bytes = FileUtil.getBytes(cacheFile);
		}
		else {
			StringBundler sb = new StringBundler(parameterMap.size());

			for (String modulePath : parameterMap.keySet()) {
				File file = getFile(modulePath);

				if (file != null) {
					String moduleContent = FileUtil.read(file);

					sb.append(moduleContent);
				}
			}

			String content = sb.toString();

			if (Validator.isNotNull(content)) {
				bytes = content.getBytes();

				FileUtil.write(cacheFile, bytes);
			}
			else {
				bytes = new byte[0];
			}
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		String firstModulePath =
			(String)request.getParameterNames().nextElement();

		String extension = FileUtil.getExtension(firstModulePath);

		if (extension.equalsIgnoreCase(_CSS_EXTENSION)) {
			contentType = ContentTypes.TEXT_CSS;
		}

		response.setContentType(contentType);

		ServletResponseUtil.write(response, bytes);
	}

	protected File getCacheFile(HttpServletRequest request) throws IOException {
		StringBundler sb = new StringBundler(5);

		sb.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		long lastModified = 0;

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String modulePath = enu.nextElement();

			File file = getFile(modulePath);

			if (file != null) {
				lastModified += file.lastModified();
			}
		}

		if (lastModified > 0) {
			sb.append(StringPool.AMPERSAND);
			sb.append(lastModified);
		}

		String cacheFileName = _TEMP_DIR.concat(
			CacheKeyGeneratorUtil.getCacheKey(
				ComboServlet.class.getName(), sb.toString()));

		return new File(cacheFileName);
	}

	protected File getFile(String path) throws IOException {
		ServletContext servletContext = getServletContext();

		String basePath = ServletContextUtil.getRealPath(
			servletContext, _JAVASCRIPT_DIR);

		if (basePath == null) {
			return null;
		}

		basePath = StringUtil.replace(
			basePath, StringPool.BACK_SLASH, StringPool.SLASH);

		File baseDir = new File(basePath);

		if (!baseDir.exists()) {
			return null;
		}

		String filePath = ServletContextUtil.getRealPath(servletContext, path);

		if (filePath == null) {
			return null;
		}

		filePath = StringUtil.replace(
			filePath, StringPool.BACK_SLASH, StringPool.SLASH);

		File file = new File(filePath);

		if (!file.exists()) {
			return null;
		}

		String baseCanonicalPath = baseDir.getCanonicalPath();
		String fileCanonicalPath = file.getCanonicalPath();

		if (fileCanonicalPath.indexOf(baseCanonicalPath) == 0) {
			return file;
		}

		return null;
	}

	private static final String _CSS_EXTENSION = "css";

	private static final String _JAVASCRIPT_DIR = "html/js";

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) + "/liferay/combo/";

}