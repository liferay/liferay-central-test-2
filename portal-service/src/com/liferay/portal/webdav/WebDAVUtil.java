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

package com.liferay.portal.webdav;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="WebDAVUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class WebDAVUtil {

	public static final Namespace DAV_URI = SAXReaderUtil.createNamespace(
		"D", "DAV:");

	public static final int SC_MULTI_STATUS = 207;

	public static final int SC_LOCKED = 423;

	public static final String TOKEN_PREFIX = "opaquelocktoken:";

	public static void addStorage(WebDAVStorage storage) {
		_instance._addStorage(storage);
	}

	public static void deleteStorage(WebDAVStorage storage) {
		_instance._deleteStorage(storage);
	}

	public static long getCompanyId(String path) throws WebDAVException {
		String[] pathArray = getPathArray(path);

		return getCompanyId(pathArray);
	}

	public static long getCompanyId(String[] pathArray) throws WebDAVException {
		try {
			String webId = getWebId(pathArray);

			Company company = CompanyLocalServiceUtil.getCompanyByWebId(webId);

			return company.getCompanyId();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static long getDepth(HttpServletRequest request) {
		String value = GetterUtil.getString(request.getHeader("Depth"));

		if (_log.isDebugEnabled()) {
			_log.debug("\"Depth\" header is " + value);
		}

		if (value.equals("0")) {
			return 0;
		}
		else {
			return -1;
		}
	}

	public static String getDestination(
		HttpServletRequest request, String rootPath) {

		String headerDestination = request.getHeader("Destination");
		String[] pathSegments = StringUtil.split(headerDestination, rootPath);

		String destination = pathSegments[pathSegments.length - 1];

		destination =  StringUtil.replace(
			destination, StringPool.SLASH, _TEMP_SLASH);
		destination = HttpUtil.decodeURL(destination, true);
		destination =  StringUtil.replace(
			destination, _TEMP_SLASH, StringPool.SLASH);

		if (_log.isDebugEnabled()) {
			_log.debug("Destination " + destination);
		}

		return destination;
	}

	public static long getGroupId(String path) throws WebDAVException {
		String[] pathArray = getPathArray(path);

		return getGroupId(pathArray);
	}

	public static long getGroupId(String[] pathArray) throws WebDAVException {
		try {
			if (pathArray.length <= 1) {
				return 0;
			}

			long companyId = getCompanyId(pathArray);

			String name = pathArray[1];

			try {
				Group group = GroupLocalServiceUtil.getGroup(companyId, name);

				return group.getGroupId();
			}
			catch (NoSuchGroupException nsge) {
			}

			try {
				Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
					companyId, StringPool.SLASH + name);

				return group.getGroupId();
			}
			catch (NoSuchGroupException nsge) {
			}

			User user = UserLocalServiceUtil.getUserByScreenName(
				companyId, name);

			Group group = user.getGroup();

			return group.getGroupId();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static String getLockUuid(HttpServletRequest request)
		throws WebDAVException {

		String token = StringPool.BLANK;

		String value = GetterUtil.getString(request.getHeader("If"));

		if (_log.isDebugEnabled()) {
			_log.debug("\"If\" header is " + value);
		}

		if (value.contains("(<DAV:no-lock>)")) {
			if (_log.isWarnEnabled()) {
				_log.warn("Lock tokens can never be <DAV:no-lock>");
			}

			throw new WebDAVException();
		}

		int beg = value.indexOf(TOKEN_PREFIX);

		if (beg >= 0) {
			beg += TOKEN_PREFIX.length();

			if (beg < value.length()) {
				int end = value.indexOf(">", beg);

				token = GetterUtil.getString(value.substring(beg, end));
			}
		}

		return token;
	}

	public static String[] getPathArray(String path) {
		return getPathArray(path, false);
	}

	public static String[] getPathArray(String path, boolean fixTrailing) {
		path = HttpUtil.fixPath(path, true, fixTrailing);

		return StringUtil.split(path, StringPool.SLASH);
	}

	public static String getResourceName(String[] pathArray) {
		if (pathArray.length <= 3) {
			return StringPool.BLANK;
		}
		else {
			return pathArray[pathArray.length - 1];
		}
	}

	public static WebDAVStorage getStorage(String token) {
		return _instance._getStorage(token);
	}

	public static Collection<String> getStorageTokens() {
		return _instance._getStorageTokens();
	}

	public static long getTimeout(HttpServletRequest request) {
		final String TIME_PREFIX = "Second-";

		long timeout = 0;

		String value = GetterUtil.getString(request.getHeader("Timeout"));

		if (_log.isDebugEnabled()) {
			_log.debug("\"Timeout\" header is " + value);
		}

		int index = value.indexOf(TIME_PREFIX);

		if (index >= 0) {
			index += TIME_PREFIX.length();

			if (index < value.length()) {
				timeout = GetterUtil.getLong(value.substring(index));
			}
		}

		return timeout * Time.SECOND;
	}

	public static String getWebId(String path) throws WebDAVException {
		String[] pathArray = getPathArray(path);

		return getWebId(pathArray);
	}

	public static String getWebId(String[] pathArray) throws WebDAVException {
		if (pathArray.length > 0) {
			String webId = pathArray[0];

			return webId;
		}
		else {
			throw new WebDAVException();
		}
	}

	public static boolean isOverwrite(HttpServletRequest request) {
		return _instance._isOverwrite(request);
	}

	private WebDAVUtil() {
		_storageMap = new TreeMap<String, WebDAVStorage>();
	}

	private void _addStorage(WebDAVStorage storage) {
		_storageMap.put(storage.getToken(), storage);
	}

	private void _deleteStorage(WebDAVStorage storage) {
		if (storage != null) {
			_storageMap.remove(storage.getToken());
		}
	}

	private WebDAVStorage _getStorage(String token) {
		return _storageMap.get(token);
	}

	private Collection<String> _getStorageTokens() {
		return _storageMap.keySet();
	}

	private boolean _isOverwrite(HttpServletRequest request) {
		String value = GetterUtil.getString(request.getHeader("Overwrite"));

		if (value.equalsIgnoreCase("F") || !GetterUtil.getBoolean(value)) {
			return false;
		}
		else {
			return true;
		}
	}

	private static final String _TEMP_SLASH = "_LIFERAY_TEMP_SLASH_";

	private static Log _log = LogFactoryUtil.getLog(WebDAVUtil.class);

	private static WebDAVUtil _instance = new WebDAVUtil();

	private Map<String, WebDAVStorage> _storageMap;

}