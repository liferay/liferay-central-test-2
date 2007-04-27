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

package com.liferay.portal.service;

/**
 * <a href="UserServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.UserService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.UserServiceFactory</code> is responsible for
 * the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserService
 * @see com.liferay.portal.service.UserServiceFactory
 *
 */
public class UserServiceUtil {
	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.addGroupUsers(groupId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean passwordReset,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId, boolean sendEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.addUser(companyId, autoPassword, password1,
			password2, passwordReset, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail);
	}

	public static void deleteRoleUser(java.lang.String roleId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.deleteUser(userId);
	}

	public static java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.getGroupUsers(groupId);
	}

	public static java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.getRoleUsers(roleId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.getUserByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.hasGroupUser(groupId, userId);
	}

	public static boolean hasRoleUser(java.lang.String roleId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.hasRoleUser(roleId, userId);
	}

	public static void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.setGroupUsers(groupId, userIds);
	}

	public static void setRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.updateAgreedToTermsOfUse(userId, agreedToTermsOfUse);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();
		userService.updatePortrait(userId, bytes);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		UserService userService = UserServiceFactory.getService();

		return userService.updateUser(userId, password, screenName,
			emailAddress, languageId, timeZoneId, greeting, comments,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, icqSn,
			jabberSn, msnSn, skypeSn, ymSn, jobTitle, organizationId, locationId);
	}
}