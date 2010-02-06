/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="TunnelServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Weisser
 * @author Brian Wing Shun Chan
 */
public class TunnelServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ObjectInputStream ois = new ObjectInputStream(
			request.getInputStream());

		Object returnObj = null;

		try {
			ObjectValuePair<HttpPrincipal, MethodWrapper> ovp =
				(ObjectValuePair<HttpPrincipal, MethodWrapper>)
					ois.readObject();

			HttpPrincipal httpPrincipal = ovp.getKey();
			MethodWrapper methodWrapper = ovp.getValue();

			if (!isValidRequest(methodWrapper)) {
				return;
			}

			long companyId = PortalInstances.getCompanyId(request);

			if (Validator.isNotNull(httpPrincipal.getLogin())) {
				User user = null;

				try {
					user = UserLocalServiceUtil.getUserByEmailAddress(
						companyId, httpPrincipal.getLogin());
				}
				catch (NoSuchUserException nsue) {
				}

				if (user == null) {
					try {
						user = UserLocalServiceUtil.getUserByScreenName(
							companyId, httpPrincipal.getLogin());
					}
					catch (NoSuchUserException nsue) {
					}
				}

				if (user == null) {
					try {
						user = UserLocalServiceUtil.getUserById(
							GetterUtil.getLong(httpPrincipal.getLogin()));
					}
					catch (NoSuchUserException nsue) {
					}
				}

				if (user != null) {
					PrincipalThreadLocal.setName(user.getUserId());

					PermissionChecker permissionChecker =
						PermissionCheckerFactoryUtil.create(user, true);

					PermissionThreadLocal.setPermissionChecker(
						permissionChecker);
				}
			}

			if (returnObj == null) {
				returnObj = MethodInvoker.invoke(methodWrapper);
			}
		}
		catch (InvocationTargetException ite) {
			returnObj = ite.getCause();

			if (!(returnObj instanceof PortalException)) {
				ite.printStackTrace();

				returnObj = new SystemException();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (returnObj != null) {
			ObjectOutputStream oos = new ObjectOutputStream(
				response.getOutputStream());

			oos.writeObject(returnObj);

			oos.flush();
			oos.close();
		}
	}

	protected boolean isValidRequest(MethodWrapper methodWrapper) {
		String className = methodWrapper.getClassName();

		if (className.contains(".service.") &&
			className.endsWith("ServiceUtil") &&
			!className.endsWith("LocalServiceUtil")) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TunnelServlet.class);

}