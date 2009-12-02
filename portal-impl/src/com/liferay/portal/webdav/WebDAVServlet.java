/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webdav.methods.Method;
import com.liferay.portal.webdav.methods.MethodFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WebDAVServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class WebDAVServlet extends HttpServlet {

	public void service(
		HttpServletRequest request, HttpServletResponse response) {

		int status = HttpServletResponse.SC_PRECONDITION_FAILED;

		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (_log.isDebugEnabled()) {
			_log.debug("User agent " + userAgent);
		}

		try {
			if (isIgnoredResource(request)) {
				status = HttpServletResponse.SC_NOT_FOUND;

				return;
			}

			WebDAVStorage storage = getStorage(request);

			// Set the path only if it has not already been set. This works
			// if and only if the servlet is not mapped to more than one URL.

			if (storage.getRootPath() == null) {
				storage.setRootPath(getRootPath(request));
			}

			// Permission checker

			PermissionChecker permissionChecker = null;

			String remoteUser = request.getRemoteUser();

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				User user = UserLocalServiceUtil.getUserById(userId);

				permissionChecker = PermissionCheckerFactoryUtil.create(
					user, true);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}

			// Get the method instance

			Method method = MethodFactory.create(request);

			// Process the method

			WebDAVRequest webDavRequest = new WebDAVRequestImpl(
				storage, request, response, userAgent, permissionChecker);

			status = method.process(webDavRequest);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			response.setStatus(status);

			if (_log.isInfoEnabled()) {
				_log.info(
					request.getMethod() + " " + request.getRequestURI() + " " + 
						status);
			}
		}
	}

	protected String getRootPath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		sb.append(WebDAVUtil.fixPath(request.getContextPath()));
		sb.append(WebDAVUtil.fixPath(request.getServletPath()));

		return sb.toString();
	}

	protected WebDAVStorage getStorage(HttpServletRequest request)
		throws WebDAVException {

		String[] pathArray = WebDAVUtil.getPathArray(
			request.getPathInfo(), true);

		WebDAVStorage storage = null;

		if (pathArray.length == 1) {
			storage = (WebDAVStorage)InstancePool.get(
				CompanyWebDAVStorageImpl.class.getName());
		}
		else if (pathArray.length == 2) {
			storage = (WebDAVStorage)InstancePool.get(
				GroupWebDAVStorageImpl.class.getName());
		}
		else if (pathArray.length >= 3) {
			storage = WebDAVUtil.getStorage(pathArray[2]);
		}

		if (storage == null) {
			throw new WebDAVException(
				"Invalid WebDAV path " + request.getPathInfo());
		}

		return storage;
	}

	protected boolean isIgnoredResource(HttpServletRequest request) {
		String[] pathArray = WebDAVUtil.getPathArray(
			request.getPathInfo(), true);

		if ((pathArray == null) || (pathArray.length <= 0)) {
			return true;
		}

		String resourceName = pathArray[pathArray.length - 1];

		for (String ignore : PropsValues.WEBDAV_IGNORE) {
			if (ignore.equals(resourceName)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping over " + request.getMethod() + " " +
							request.getPathInfo());
				}

				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(WebDAVServlet.class);

}