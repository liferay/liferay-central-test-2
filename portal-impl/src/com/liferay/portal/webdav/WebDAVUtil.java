/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.webdav;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Namespace;

/**
 * <a href="WebDAVUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class WebDAVUtil {

	public static final Namespace DAV_URI = Namespace.get("D", "DAV:");

	public static final int SC_MULTI_STATUS = 207;

	public static String fixPath(String path) {
		if (path.endsWith(StringPool.SLASH)) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
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

		if (_log.isInfoEnabled()) {
			_log.info("\"Depth\" header is " + value);
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

	public static String[] getPathArray(String path) {
		return getPathArray(path, false);
	}

	public static String[] getPathArray(String path, boolean fixPath) {
		if (fixPath) {
			path = fixPath(path);
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1, path.length());
		}

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

	public static String getStorageClass(String token) {
		return _instance._getStorageClass(token);
	}

	public static String getStorageToken(String className) {
		return _instance._getStorageToken(className);
	}

	public static Collection<String> getStorageTokens() {
		return _instance._getStorageTokens();
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

	public static boolean isEnabled(String storageClassName) {
		return _instance._isEnabled(storageClassName);
	}

	public static boolean isOverwrite(HttpServletRequest request) {
		String value = GetterUtil.getString(request.getHeader("Overwrite"));

		if (value.equalsIgnoreCase("F") || !GetterUtil.getBoolean(value)) {
			return false;
		}
		else {
			return true;
		}
	}

	private WebDAVUtil() {
		_storageMap = new HashMap<String, String>();

		String[] tokens = PropsUtil.getArray(PropsKeys.WEBDAV_STORAGE_TOKENS);

		for (String token: tokens) {
			String className = PropsUtil.get(
				PropsKeys.WEBDAV_STORAGE_CLASS, new Filter(token));

			if (Validator.isNotNull(className)) {
				_storageMap.put(className, token);
			}
		}
	}

	private String _getStorageClass(String token) {
		if (_storageMap.containsValue(token)) {
			for (String key : _storageMap.keySet()) {
				if (_storageMap.get(key).equals(token)) {
					return key;
				}
			}
		}

		return null;
	}

	private String _getStorageToken(String className) {
		return _storageMap.get(className);
	}

	private Collection<String> _getStorageTokens() {
		return _storageMap.values();
	}

	private boolean _isEnabled(String storageClassName) {
		return _storageMap.containsKey(storageClassName);
	}

	private static Log _log = LogFactory.getLog(WebDAVUtil.class);

	private static WebDAVUtil _instance = new WebDAVUtil();

	private final Map<String, String> _storageMap;

}