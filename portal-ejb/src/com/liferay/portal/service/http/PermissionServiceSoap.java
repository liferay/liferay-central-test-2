/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.service.PermissionServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="PermissionServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionServiceSoap {
	public static void checkPermission(java.lang.String groupId,
		java.lang.String name, java.lang.String primKey)
		throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, name, primKey);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static boolean hasGroupPermission(java.lang.String groupId,
		java.lang.String actionId, java.lang.String resourceId)
		throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasGroupPermission(groupId,
					actionId, resourceId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static boolean hasUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String actionId,
		java.lang.String[] resourceIds,
		com.liferay.portal.kernel.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasUserPermissions(userId,
					groupId, actionId, resourceIds, permissionCheckerBag);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setGroupPermissions(java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(groupId, actionIds,
				resourceId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, java.lang.String groupId,
		java.lang.String[] actionIds, java.lang.String resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(className, classPK,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setOrgGroupPermissions(java.lang.String organizationId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId) throws RemoteException {
		try {
			PermissionServiceUtil.setOrgGroupPermissions(organizationId,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setRolePermission(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setRolePermission(roleId, groupId, name,
				typeId, scope, primKey, actionId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId) throws RemoteException {
		try {
			PermissionServiceUtil.setUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void unsetRolePermission(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String primKey, java.lang.String actionId)
		throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermission(roleId, groupId, name,
				typeId, scope, primKey, actionId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void unsetRolePermissions(java.lang.String roleId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String typeId, java.lang.String scope,
		java.lang.String actionId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermissions(roleId, groupId, name,
				typeId, scope, actionId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void unsetUserPermissions(java.lang.String userId,
		java.lang.String groupId, java.lang.String[] actionIds,
		java.lang.String resourceId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionServiceSoap.class);
}