/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="UserServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link UserService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserService
 * @generated
 */
public class UserServiceUtil {
	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addGroupUsers(groupId, userIds);
	}

	public static void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addOrganizationUsers(organizationId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUser(companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, openId, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	public static com.liferay.portal.model.User addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUser(companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, openId, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, addresses,
			emailAddresses, phones, websites, announcementsDelivers,
			serviceContext);
	}

	public static void deletePortrait(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortrait(userId);
	}

	public static void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUser(userId);
	}

	public static long getDefaultUserId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultUserId(companyId);
	}

	public static long[] getGroupUserIds(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUserIds(groupId);
	}

	public static long[] getOrganizationUserIds(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUserIds(organizationId);
	}

	public static long[] getRoleUserIds(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUserIds(roleId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByScreenName(companyId, screenName);
	}

	public static long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdByEmailAddress(companyId, emailAddress);
	}

	public static long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasGroupUser(groupId, userId);
	}

	public static boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRoleUser(roleId, userId);
	}

	public static boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRoleUser(companyId, name, userId, inherited);
	}

	public static void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetGroupUsers(groupId, userIds);
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetOrganizationUsers(organizationId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAgreedToTermsOfUse(userId, agreedToTermsOfUse);
	}

	public static void updateEmailAddress(long userId,
		java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateEmailAddress(userId, password, emailAddress1, emailAddress2);
	}

	public static com.liferay.portal.model.User updateLockout(long userId,
		boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLockout(userId, lockout);
	}

	public static void updateOpenId(long userId, java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateOpenId(userId, openId);
	}

	public static void updateOrganizations(long userId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateOrganizations(userId, organizationIds);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePassword(userId, password1, password2, passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updatePortrait(userId, bytes);
	}

	public static void updateReminderQuery(long userId,
		java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateReminderQuery(userId, question, answer);
	}

	public static void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateScreenName(userId, screenName);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUser(userId, oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, serviceContext);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUser(userId, oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, addresses, emailAddresses, phones, websites,
			announcementsDelivers, serviceContext);
	}

	public static UserService getService() {
		if (_service == null) {
			_service = (UserService)PortalBeanLocatorUtil.locate(UserService.class.getName());
		}

		return _service;
	}

	public void setService(UserService service) {
		_service = service;
	}

	private static UserService _service;
}