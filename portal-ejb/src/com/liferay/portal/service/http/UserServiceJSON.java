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

import com.liferay.portal.service.UserServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="UserServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the <code>com.liferay.portal.service.UserServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is difficult
 * for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>, that
 * is translated to a <code>org.json.JSONArray</code>. If the method in the service
 * utility returns a <code>com.liferay.portal.model.User</code>, that is translated
 * to a <code>org.json.JSONObject</code>. Methods that JSON cannot safely use are
 * skipped. The logic for the translation is encapsulated in <code>com.liferay.portal.service.http.UserJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript. See
 * <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a reference
 * of how that portlet uses the generated JavaScript in <code>portal-web/docroot/html/js/service.js</code>
 * to call the backend services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserServiceUtil
 * @see com.liferay.portal.service.http.UserJSONSerializer
 *
 */
public class UserServiceJSON {
	public static void addGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addGroupUsers(groupId, userIds);
	}

	public static void addRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addUserGroupUsers(userGroupId, userIds);
	}

	public static JSONObject addUser(java.lang.String companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean passwordReset,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
				autoPassword, password1, password2, passwordReset,
				autoScreenName, screenName, emailAddress,
				new java.util.Locale(locale), firstName, middleName, lastName,
				nickName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, organizationId, locationId, sendEmail);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.deleteUser(userId);
	}

	public static JSONArray getGroupUsers(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = UserServiceUtil.getGroupUsers(groupId);

		return UserJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = UserServiceUtil.getRoleUsers(roleId);

		return UserJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject getUserByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByEmailAddress(companyId,
				emailAddress);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getUserById(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserById(userId);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getUserByScreenName(java.lang.String companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByScreenName(companyId,
				screenName);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static boolean hasGroupUser(long groupId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = UserServiceUtil.hasGroupUser(groupId, userId);

		return returnValue;
	}

	public static boolean hasRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = UserServiceUtil.hasRoleUser(roleId, userId);

		return returnValue;
	}

	public static void setGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setGroupUsers(groupId, userIds);
	}

	public static void setRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static JSONObject updateActive(java.lang.String userId,
		boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateActive(userId,
				active);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateAgreedToTermsOfUse(java.lang.String userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateAgreedToTermsOfUse(userId,
				agreedToTermsOfUse);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updatePassword(java.lang.String userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updatePassword(userId,
				password1, password2, passwordReset);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static void updatePortrait(java.lang.String userId, byte[] bytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.updatePortrait(userId, bytes);
	}

	public static JSONObject updateUser(java.lang.String userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String resolution, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateUser(userId,
				password, screenName, emailAddress, languageId, timeZoneId,
				greeting, resolution, comments, firstName, middleName,
				lastName, nickName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, smsSn, aimSn, icqSn, jabberSn,
				msnSn, skypeSn, ymSn, jobTitle, organizationId, locationId);

		return UserJSONSerializer.toJSONObject(returnValue);
	}
}