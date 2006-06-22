/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.service.spring.UserGroupServiceUtil;
import com.liferay.portal.shared.util.StackTraceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="UserGroupServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserGroupServiceSoap {
	public static boolean addGroupUserGroups(java.lang.String groupId,
		java.lang.String[] userGroupIds) throws RemoteException {
		try {
			boolean returnValue = UserGroupServiceUtil.addGroupUserGroups(groupId,
					userGroupIds);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.UserGroupModel addUserGroup(
		java.lang.String name, java.lang.String description)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
					description);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteUserGroup(java.lang.String userGroupId)
		throws RemoteException {
		try {
			UserGroupServiceUtil.deleteUserGroup(userGroupId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.UserGroupModel getUserGroup(
		java.lang.String userGroupId) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(userGroupId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static boolean unsetGroupUserGroups(java.lang.String groupId,
		java.lang.String[] userGroupIds) throws RemoteException {
		try {
			boolean returnValue = UserGroupServiceUtil.unsetGroupUserGroups(groupId,
					userGroupIds);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.UserGroupModel updateUserGroup(
		java.lang.String userGroupId, java.lang.String name,
		java.lang.String description) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
					name, description);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(UserGroupServiceSoap.class);
}