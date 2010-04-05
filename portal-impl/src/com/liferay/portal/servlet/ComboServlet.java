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

import com.liferay.portal.cache.key.JavaMD5CacheKeyGenerator;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
		throws IOException, ServletException {

		Map<String, String[]> parameterMap = request.getParameterMap();

		int totalParameters = parameterMap.size();

		if (totalParameters == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		String content = null;

		String cacheFileName = getCacheFileName(request);

		File cacheDataFile = new File(cacheFileName);

		if (cacheDataFile.exists()) {
			content = FileUtil.read(cacheDataFile);
		}
		else {
			_sb = new StringBundler(totalParameters);

			for (String modulePath : parameterMap.keySet()) {
				File file = getFile(request, modulePath);

				if (file != null) {
					String moduleContent = FileUtil.read(file);

					_sb.append(moduleContent);
				}
			}

			content = _sb.toString();

			if (Validator.isNotNull(content)) {
				FileUtil.write(cacheDataFile, content);
			}
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		String firstModuleName =
			(String)request.getParameterNames().nextElement();

		String extension = FileUtil.getExtension(firstModuleName);

		if ((extension != null) &&
			extension.equalsIgnoreCase(_CSS_EXTENSION)) {

			contentType = ContentTypes.TEXT_CSS;
		}

		response.setContentType(contentType);

		PrintWriter pw = response.getWriter();

		pw.write(content);

		pw.close();
	}

	protected String getCacheFileName(HttpServletRequest request)
		throws IOException {

		StringBundler sb = new StringBundler(5);

		String requestURI = request.getRequestURI();

		sb.append(requestURI);

		String queryString = request.getQueryString();

		if (queryString != null) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		long modifiedTimes = 0;

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String modulePath = enu.nextElement();

			File file = getFile(request, modulePath);

			if (file != null) {
				modifiedTimes += file.lastModified();
			}
		}

		sb.append(StringPool.AMPERSAND);
		sb.append(modifiedTimes);

		String cacheFileName = sb.toString();

		JavaMD5CacheKeyGenerator cacheKeyGenerator =
			new JavaMD5CacheKeyGenerator();

		String cacheKey = cacheKeyGenerator.getCacheKey(cacheFileName);

		StringBundler sbPath = new StringBundler(2);

		sbPath.append(_tempDir);
		sbPath.append(cacheKey);

		return sbPath.toString();
	}

	protected File getFile(
			HttpServletRequest request, String modulePath)
		throws IOException {

		ServletContext servletContext = getServletContext();

		String basePath = ServletContextUtil.getRealPath(
			servletContext, _ALLOY_DIR);

		String realPath = ServletContextUtil.getRealPath(
			servletContext, modulePath);

		if ((basePath == null) || (realPath == null)) {
			return null;
		}

		basePath = StringUtil.replace(
			basePath, StringPool.BACK_SLASH, StringPool.SLASH);

		realPath = StringUtil.replace(
			realPath, StringPool.BACK_SLASH, StringPool.SLASH);

		File baseDir = new File(basePath);
		File file = new File(realPath);

		if (baseDir.exists() && file.exists()) {
			String rawBasePath = baseDir.getCanonicalPath();
			String rawFilePath = file.getCanonicalPath();

			if (rawFilePath.indexOf(rawBasePath) == 0) {
				return file;
			}
		}

		return null;
	}

	private static final String _ALLOY_DIR = "html/js/aui";

	private static final String _CSS_EXTENSION = "css";

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/combo/portal/";

	private StringBundler _sb;
	private String _tempDir = _TEMP_DIR;

}