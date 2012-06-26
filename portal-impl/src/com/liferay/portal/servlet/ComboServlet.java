/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Edward Han
 * @author Zsigmond Rab
 */
public class ComboServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			doService(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected void doService(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String contextPath = PortalUtil.getPathContext();

		String[] modulePaths = request.getParameterValues("m");

		if ((modulePaths == null) || (modulePaths.length == 0)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		Set<String> modulePathsSet = new HashSet<String>(modulePaths.length);

		for (String path : modulePaths) {
			modulePathsSet.add(path);
		}

		modulePaths = modulePathsSet.toArray(new String[modulePathsSet.size()]);

		Arrays.sort(modulePaths);

		String modulePathsString = null;

		byte[][] bytesArray = null;

		if (!PropsValues.COMBO_CHECK_TIMESTAMP) {
			modulePathsString = Arrays.toString(modulePaths);

			bytesArray = (byte[][])_bytesArrayPortalCache.get(
				modulePathsString);
		}

		String firstModulePath = modulePaths[0];

		String extension = FileUtil.getExtension(firstModulePath);

		if (bytesArray == null) {
			String p = ParamUtil.getString(request, "p");

			String minifierType = ParamUtil.getString(request, "minifierType");

			if (Validator.isNull(minifierType)) {
				minifierType = "js";

				if (extension.equalsIgnoreCase(_CSS_EXTENSION)) {
					minifierType = "css";
				}
			}

			if (!minifierType.equals("css") && !minifierType.equals("js")) {
				minifierType = "js";
			}

			int length = modulePaths.length;

			bytesArray = new byte[length][];

			for (String modulePath : modulePaths) {
				if (!validateModuleExtension(modulePath)) {
					response.setHeader(
						HttpHeaders.CACHE_CONTROL,
						HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);

					return;
				}

				byte[] bytes = new byte[0];

				if (Validator.isNotNull(modulePath)) {
					modulePath = StringUtil.replaceFirst(
						p.concat(modulePath), contextPath, StringPool.BLANK);

					if (getFile(modulePath) == null) {
						response.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);

						return;
					}

					bytes = getFileContent(
						request, response, modulePath, minifierType);
				}

				bytesArray[--length] = bytes;
			}

			if ((modulePathsString != null) &&
				!PropsValues.COMBO_CHECK_TIMESTAMP) {

				_bytesArrayPortalCache.put(modulePathsString, bytesArray);
			}
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		if (extension.equalsIgnoreCase(_CSS_EXTENSION)) {
			contentType = ContentTypes.TEXT_CSS;
		}

		response.setContentType(contentType);

		ServletResponseUtil.write(response, bytesArray);
	}

	protected File getFile(String path) throws IOException {
		ServletContext servletContext = getServletContext();

		String basePath = ServletContextUtil.getRealPath(
			servletContext, _JAVASCRIPT_DIR);

		if (basePath == null) {
			return null;
		}

		basePath = StringUtil.replace(
			basePath, CharPool.BACK_SLASH, CharPool.SLASH);

		File baseDir = new File(basePath);

		if (!baseDir.exists()) {
			return null;
		}

		String filePath = ServletContextUtil.getRealPath(servletContext, path);

		if (filePath == null) {
			return null;
		}

		filePath = StringUtil.replace(
			filePath, CharPool.BACK_SLASH, CharPool.SLASH);

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

	protected byte[] getFileContent(
			HttpServletRequest request, HttpServletResponse response,
			String path, String minifierType)
		throws IOException {

		String fileContentKey = path.concat(StringPool.QUESTION).concat(
			minifierType);

		FileContentBag fileContentBag =
			(FileContentBag)_fileContentBagPortalCache.get(fileContentKey);

		if ((fileContentBag != null) && !PropsValues.COMBO_CHECK_TIMESTAMP) {
			return fileContentBag._fileContent;
		}

		File file = getFile(path);

		if ((fileContentBag != null) && PropsValues.COMBO_CHECK_TIMESTAMP) {
			long elapsedTime =
				System.currentTimeMillis() - fileContentBag._lastModified;

			if ((file != null) &&
				(elapsedTime <= PropsValues.COMBO_CHECK_TIMESTAMP_INTERVAL) &&
				(file.lastModified() == fileContentBag._lastModified)) {

				return fileContentBag._fileContent;
			}
			else {
				_fileContentBagPortalCache.remove(fileContentKey);
			}
		}

		if (file == null) {
			fileContentBag = _EMPTY_FILE_CONTENT_BAG;
		}
		else {
			String stringFileContent = FileUtil.read(file);

			if (!StringUtil.endsWith(path, _CSS_MINIFIED_SUFFIX) &&
				!StringUtil.endsWith(path, _JAVASCRIPT_MINIFIED_SUFFIX)) {

				if (minifierType.equals("css")) {
					String cssRealPath = file.getAbsolutePath();

					try {
						stringFileContent = DynamicCSSUtil.parseSass(
							request, cssRealPath, stringFileContent);
					}
					catch (Exception e) {
						_log.error(
							"Unable to parse SASS on CSS " + cssRealPath, e);

						if (_log.isDebugEnabled()) {
							_log.debug(stringFileContent);
						}

						response.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					}

					stringFileContent = MinifierUtil.minifyCss(
						stringFileContent);
				}
				else if (minifierType.equals("js")) {
					stringFileContent = MinifierUtil.minifyJavaScript(
						stringFileContent);
				}
			}

			fileContentBag = new FileContentBag(
				stringFileContent.getBytes(StringPool.UTF8),
				file.lastModified());
		}

		if (PropsValues.COMBO_CHECK_TIMESTAMP) {
			int timeToLive =
				(int)(PropsValues.COMBO_CHECK_TIMESTAMP_INTERVAL / Time.SECOND);

			_fileContentBagPortalCache.put(
				fileContentKey, fileContentBag, timeToLive);
		}

		return fileContentBag._fileContent;
	}

	protected boolean validateModuleExtension(String moduleName)
		throws Exception {

		boolean validModuleExtension = false;

		String[] fileExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.COMBO_ALLOWED_FILE_EXTENSIONS, StringPool.COMMA);

		for (int i = 0; i < fileExtensions.length; i++) {
			if (StringPool.STAR.equals(fileExtensions[i]) ||
				StringUtil.endsWith(moduleName, fileExtensions[i])) {

				validModuleExtension = true;

				break;
			}
		}

		return validModuleExtension;
	}

	private static final String _CSS_EXTENSION = "css";

	private static final String _CSS_MINIFIED_SUFFIX = "-min.css";

	private static final FileContentBag _EMPTY_FILE_CONTENT_BAG =
		new FileContentBag(new byte[0], 0);

	private static final String _JAVASCRIPT_DIR = "html/js";

	private static final String _JAVASCRIPT_MINIFIED_SUFFIX = "-min.js";

	private static Log _log = LogFactoryUtil.getLog(ComboServlet.class);

	private PortalCache _bytesArrayPortalCache = SingleVMPoolUtil.getCache(
		ComboServlet.class.getName());
	private PortalCache _fileContentBagPortalCache = SingleVMPoolUtil.getCache(
		FileContentBag.class.getName());

	private static class FileContentBag implements Serializable {

		public FileContentBag(byte[] fileContent, long lastModifiedTime) {
			_fileContent = fileContent;
			_lastModified = lastModifiedTime;
		}

		private byte[] _fileContent;
		private long _lastModified;

	}

}