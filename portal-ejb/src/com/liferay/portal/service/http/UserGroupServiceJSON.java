/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.UserGroupServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="UserGroupServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserGroupServiceJSON {
	public static void addGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserGroupServiceUtil.addGroupUserGroups(groupId, userGroupIds);
	}

	public static JSONObject addUserGroup(java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
				description);

		return UserGroupJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteUserGroup(java.lang.String userGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserGroupServiceUtil.deleteUserGroup(userGroupId);
	}

	public static JSONObject getUserGroup(java.lang.String userGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(userGroupId);

		return UserGroupJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray getUserUserGroups(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = UserGroupServiceUtil.getUserUserGroups(userId);

		return UserGroupJSONSerializer.toJSONArray(returnValue);
	}

	public static void unsetGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserGroupServiceUtil.unsetGroupUserGroups(groupId, userGroupIds);
	}

	public static JSONObject updateUserGroup(java.lang.String userGroupId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
				name, description);

		return UserGroupJSONSerializer.toJSONObject(returnValue);
	}
}