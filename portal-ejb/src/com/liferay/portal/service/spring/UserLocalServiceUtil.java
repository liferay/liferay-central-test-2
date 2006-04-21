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

package com.liferay.portal.service.spring;

/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserLocalServiceUtil {
	public static boolean addGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.addGroupUsers(groupId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean addRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.addRoleUsers(roleId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User addUser(
		java.lang.String companyId, boolean autoUserId,
		java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, java.lang.String prefixId,
		java.lang.String suffixId, boolean male, int birthdayMonth,
		int birthdayDay, int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.addUser(companyId, autoUserId, userId,
				autoPassword, password1, password2, passwordReset,
				emailAddress, locale, firstName, middleName, lastName,
				nickName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, organizationId, locationId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User addUser(
		java.lang.String companyId, boolean autoUserId,
		java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, java.lang.String prefixId,
		java.lang.String suffixId, boolean male, int birthdayMonth,
		int birthdayDay, int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.addUser(companyId, autoUserId, userId,
				autoPassword, password1, password2, passwordReset,
				emailAddress, locale, firstName, middleName, lastName,
				nickName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, organizationId, locationId, sendEmail);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int authenticateByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.authenticateByEmailAddress(companyId,
				emailAddress, password);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int authenticateByUserId(java.lang.String companyId,
		java.lang.String userId, java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.authenticateByUserId(companyId, userId,
				password);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean authenticateForJAAS(java.lang.String userId,
		java.lang.String encPwd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.authenticateForJAAS(userId, encPwd);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.util.KeyValuePair decryptUserId(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.decryptUserId(companyId, userId, password);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean deleteRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.deleteRoleUser(roleId, userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteUser(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();
			userLocalService.deleteUser(userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.lang.String encryptUserId(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.encryptUserId(userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User getDefaultUser(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getDefaultUser(companyId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getGroupUsers(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getGroupUsers(groupId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getPermissionUsers(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId,
		com.liferay.portlet.enterpriseadmin.search.UserSearchTerms searchTerms,
		int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getPermissionUsers(companyId, groupId,
				name, primKey, actionId, searchTerms, begin, end);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getPermissionUsersCount(java.lang.String companyId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		com.liferay.portlet.enterpriseadmin.search.UserSearchTerms searchTerms)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getPermissionUsersCount(companyId, groupId,
				name, primKey, actionId, searchTerms);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getRoleUsers(roleId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getUserByEmailAddress(companyId,
				emailAddress);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User getUserById(
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getUserById(userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User getUserById(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getUserById(companyId, userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.lang.String getUserId(java.lang.String companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.getUserId(companyId, emailAddress);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasGroupUser(java.lang.String groupId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.hasGroupUser(groupId, userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean hasRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.hasRoleUser(roleId, userId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.Map params, boolean andSearch, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.search(companyId, firstName, middleName,
				lastName, emailAddress, active, params, andSearch, begin, end,
				obc);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.Map params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.searchCount(companyId, firstName,
				middleName, lastName, emailAddress, active, params, andSearch);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void sendPassword(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();
			userLocalService.sendPassword(companyId, emailAddress, remoteAddr,
				remoteHost, userAgent);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void setGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();
			userLocalService.setGroupUsers(groupId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void setRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();
			userLocalService.setRoleUsers(roleId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean unsetGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.unsetGroupUsers(groupId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static boolean unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.unsetRoleUsers(roleId, userIds);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User updateActive(
		java.lang.String userId, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.updateActive(userId, active);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		java.lang.String userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.updateAgreedToTermsOfUse(userId,
				agreedToTermsOfUse);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User updateLastLogin(
		java.lang.String userId, java.lang.String loginIP)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.updateLastLogin(userId, loginIP);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User updatePassword(
		java.lang.String userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.updatePassword(userId, password1,
				password2, passwordReset);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void updatePortrait(java.lang.String userId, byte[] bytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();
			userLocalService.updatePortrait(userId, bytes);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.User updateUser(
		java.lang.String userId, java.lang.String password,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String resolution, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName,
		java.lang.String prefixId, java.lang.String suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String msnSn, java.lang.String ymSn,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			UserLocalService userLocalService = UserLocalServiceFactory.getService();

			return userLocalService.updateUser(userId, password, emailAddress,
				languageId, timeZoneId, greeting, resolution, comments,
				firstName, middleName, lastName, nickName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
				icqSn, msnSn, ymSn, jobTitle, organizationId, locationId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}