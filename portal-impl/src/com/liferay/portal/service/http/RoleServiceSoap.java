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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.RoleServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.RoleServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.RoleSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Role}, that is translated to a
 * {@link com.liferay.portal.model.RoleSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleServiceHttp
 * @see       com.liferay.portal.model.RoleSoap
 * @see       com.liferay.portal.service.RoleServiceUtil
 * @generated
 */
public class RoleServiceSoap {
	public static void addUserRoles(long userId, long[] roleIds)
		throws RemoteException {
		try {
			RoleServiceUtil.addUserRoles(userId, roleIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRole(long roleId) throws RemoteException {
		try {
			RoleServiceUtil.deleteRole(roleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap[] getGroupRoles(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getGroupRoles(groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap getRole(long roleId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Role returnValue = RoleServiceUtil.getRole(roleId);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap getRole(long companyId,
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.Role returnValue = RoleServiceUtil.getRole(companyId,
					name);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap[] getUserGroupGroupRoles(
		long userId, long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserGroupGroupRoles(userId,
					groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap[] getUserGroupRoles(
		long userId, long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserGroupRoles(userId,
					groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap[] getUserRelatedRoles(
		long userId, com.liferay.portal.model.GroupSoap[] groups)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserRelatedRoles(userId,
					com.liferay.portal.model.impl.GroupModelImpl.toModels(
						groups));

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RoleSoap[] getUserRoles(long userId)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserRoles(userId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited) throws RemoteException {
		try {
			boolean returnValue = RoleServiceUtil.hasUserRole(userId,
					companyId, name, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited) throws RemoteException {
		try {
			boolean returnValue = RoleServiceUtil.hasUserRoles(userId,
					companyId, names, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetUserRoles(long userId, long[] roleIds)
		throws RemoteException {
		try {
			RoleServiceUtil.unsetUserRoles(userId, roleIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RoleServiceSoap.class);
}