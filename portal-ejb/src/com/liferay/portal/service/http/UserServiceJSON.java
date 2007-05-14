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
	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addGroupUsers(groupId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(long groupId, long userGroupId,
		long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.addUserGroupUsers(groupId, userGroupId, userIds);
	}

	public static JSONObject addUser(long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long organizationId, long locationId,
		boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
				autoPassword, password1, password2, autoScreenName, screenName,
				emailAddress, new java.util.Locale(locale), firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, organizationId,
				locationId, sendEmail);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(long userId)
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

	public static JSONArray getRoleUsers(long roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = UserServiceUtil.getRoleUsers(roleId);

		return UserJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByEmailAddress(companyId,
				emailAddress);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getUserById(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserById(userId);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByScreenName(companyId,
				screenName);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = UserServiceUtil.hasGroupUser(groupId, userId);

		return returnValue;
	}

	public static boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		boolean returnValue = UserServiceUtil.hasRoleUser(roleId, userId);

		return returnValue;
	}

	public static void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setGroupUsers(groupId, userIds);
	}

	public static void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(long groupId, long userGroupId,
		long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.unsetUserGroupUsers(groupId, userGroupId, userIds);
	}

	public static JSONObject updateActive(long userId, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateActive(userId,
				active);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateAgreedToTermsOfUse(userId,
				agreedToTermsOfUse);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updatePassword(userId,
				password1, password2, passwordReset);

		return UserJSONSerializer.toJSONObject(returnValue);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		UserServiceUtil.updatePortrait(userId, bytes);
	}

	public static JSONObject updateUser(long userId, java.lang.String password,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String skypeSn, java.lang.String ymSn,
		java.lang.String jobTitle, long organizationId, long locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.User returnValue = UserServiceUtil.updateUser(userId,
				password, screenName, emailAddress, languageId, timeZoneId,
				greeting, comments, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				smsSn, aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle,
				organizationId, locationId);

		return UserJSONSerializer.toJSONObject(returnValue);
	}
}