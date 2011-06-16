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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.PermissionServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.PermissionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.PermissionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Permission}, that is translated to a
 * {@link com.liferay.portal.model.PermissionSoap}. Methods that SOAP cannot
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
 * @see       PermissionServiceHttp
 * @see       com.liferay.portal.model.PermissionSoap
 * @see       com.liferay.portal.service.PermissionServiceUtil
 * @generated
 */
public class PermissionServiceSoap {
	public static void checkPermission(long groupId, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkPermission(long groupId, java.lang.String name,
		long primKey) throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, name, primKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey) throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, name, primKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId) throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasGroupPermission(groupId,
					actionId, resourceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId) throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasUserPermission(userId,
					actionId, resourceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasUserPermissions(long userId, long groupId,
		com.liferay.portal.model.ResourceSoap[] resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasUserPermissions(userId,
					groupId,
					com.liferay.portal.model.impl.ResourceModelImpl.toModels(
						resources), actionId, permissionCheckerBag);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(groupId, actionIds,
				resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId) throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(className, classPK,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setOrgGroupPermissions(organizationId,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId) throws RemoteException {
		try {
			PermissionServiceUtil.setRolePermission(roleId, groupId, name,
				scope, primKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setRolePermissions(roleId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetRolePermission(long roleId, long groupId,
		long permissionId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermission(roleId, groupId,
				permissionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermission(roleId, groupId, name,
				scope, primKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermissions(roleId, groupId, name,
				scope, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.unsetUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionServiceSoap.class);
}