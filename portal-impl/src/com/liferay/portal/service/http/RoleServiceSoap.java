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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.RoleServiceUtil;

import java.rmi.RemoteException;

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

	public static com.liferay.portal.model.RoleSoap getGroupRole(
		long companyId, long groupId) throws RemoteException {
		try {
			com.liferay.portal.model.Role returnValue = RoleServiceUtil.getGroupRole(companyId,
					groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
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