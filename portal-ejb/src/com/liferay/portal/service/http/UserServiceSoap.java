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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="UserServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserServiceSoap {
	public static void addGroupUsers(long groupId, java.lang.String[] userIds)
		throws RemoteException {
		try {
			UserServiceUtil.addGroupUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void addRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.addRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void addUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.addUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap addUser(
		java.lang.String companyId, boolean autoUserId,
		java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
					autoUserId, userId, autoPassword, password1, password2,
					passwordReset, emailAddress, new java.util.Locale(locale),
					firstName, middleName, lastName, nickName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, organizationId, locationId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap addUser(
		java.lang.String companyId, boolean autoUserId,
		java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
					autoUserId, userId, autoPassword, password1, password2,
					passwordReset, emailAddress, new java.util.Locale(locale),
					firstName, middleName, lastName, nickName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, organizationId, locationId, sendEmail);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRoleUser(java.lang.String roleId,
		java.lang.String userId) throws RemoteException {
		try {
			UserServiceUtil.deleteRoleUser(roleId, userId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteUser(java.lang.String userId)
		throws RemoteException {
		try {
			UserServiceUtil.deleteUser(userId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap[] getGroupUsers(
		long groupId) throws RemoteException {
		try {
			java.util.List returnValue = UserServiceUtil.getGroupUsers(groupId);

			return com.liferay.portal.model.UserSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap[] getRoleUsers(
		java.lang.String roleId) throws RemoteException {
		try {
			java.util.List returnValue = UserServiceUtil.getRoleUsers(roleId);

			return com.liferay.portal.model.UserSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByEmailAddress(companyId,
					emailAddress);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap getUserById(
		java.lang.String userId) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserById(userId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap getUserByDisplayUserId(
		java.lang.String displayUserId) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByDisplayUserId(displayUserId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasGroupUser(long groupId, java.lang.String userId)
		throws RemoteException {
		try {
			boolean returnValue = UserServiceUtil.hasGroupUser(groupId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean hasRoleUser(java.lang.String roleId,
		java.lang.String userId) throws RemoteException {
		try {
			boolean returnValue = UserServiceUtil.hasRoleUser(roleId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void setGroupUsers(long groupId, java.lang.String[] userIds)
		throws RemoteException {
		try {
			UserServiceUtil.setGroupUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void setRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.setRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void setUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.setUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetGroupUsers(long groupId, java.lang.String[] userIds)
		throws RemoteException {
		try {
			UserServiceUtil.unsetGroupUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.unsetRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds) throws RemoteException {
		try {
			UserServiceUtil.unsetUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateActive(
		java.lang.String userId, boolean active) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateActive(userId,
					active);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateAgreedToTermsOfUse(
		java.lang.String userId, boolean agreedToTermsOfUse)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateAgreedToTermsOfUse(userId,
					agreedToTermsOfUse);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updatePassword(
		java.lang.String userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updatePassword(userId,
					password1, password2, passwordReset);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void updatePortrait(java.lang.String userId, byte[] bytes)
		throws RemoteException {
		try {
			UserServiceUtil.updatePortrait(userId, bytes);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateUser(
		java.lang.String userId, java.lang.String password,
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
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateUser(userId,
					password, emailAddress, languageId, timeZoneId, greeting,
					resolution, comments, firstName, middleName, lastName,
					nickName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, smsSn, aimSn, icqSn, jabberSn,
					msnSn, skypeSn, ymSn, jobTitle, organizationId, locationId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceSoap.class);
}