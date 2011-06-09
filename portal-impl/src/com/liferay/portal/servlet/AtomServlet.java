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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.protocol.server.Provider;
import org.apache.abdera.protocol.server.servlet.AbderaServlet;

/**
 * @author Igor Spasic
 */
public class AtomServlet extends AbderaServlet {

	protected Provider createProvider() {
		return null;
	}

	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException {

		try {
			User user = null;

			long companyId = ParamUtil.getLong(request, "companyId");

			String remoteUser = request.getRemoteUser();

			if (_log.isDebugEnabled()) {
				_log.debug("Remote user " + remoteUser);
			}

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				user = UserLocalServiceUtil.getUserById(userId);

				if (companyId == 0) {
					companyId = user.getCompanyId();
				}
			}
			else {
				if (companyId == 0) {
					companyId = PortalInstances.getCompanyId(request);
				}

				if (companyId != 0) {
					user = UserLocalServiceUtil.getDefaultUser(companyId);
				}
			}

			if (user != null) {
				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user, true);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);

				//AtomUtil.saveUserInRequest(request, user);
			}

			CompanyThreadLocal.setCompanyId(companyId);

			super.service(request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AtomServlet.class);

}