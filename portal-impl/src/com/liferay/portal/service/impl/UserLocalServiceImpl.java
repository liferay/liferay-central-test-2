/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.ReservedUserScreenNameException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserPortraitException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.service.base.UserLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.Normalizer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.rmi.RemoteException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Raymond Augé
 *
 */
public class UserLocalServiceImpl extends UserLocalServiceBaseImpl {

	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		groupPersistence.addUsers(groupId, userIds);

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Role role = rolePersistence.findByC_N(
			group.getCompanyId(), RoleImpl.COMMUNITY_MEMBER);

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});
		}

		PermissionCacheUtil.clearCache();
	}

	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		organizationPersistence.addUsers(organizationId, userIds);

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		Group group = organization.getGroup();

		long groupId = group.getGroupId();

		Role role = rolePersistence.findByC_N(
			group.getCompanyId(), RoleImpl.ORGANIZATION_MEMBER);

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});
		}

		PermissionCacheUtil.clearCache();
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws SystemException {

		passwordPolicyRelLocalService.addPasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		rolePersistence.addUsers(roleId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		copyUserGroupLayouts(userGroupId, userIds);

		userGroupPersistence.addUsers(userGroupId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public User addUser(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] organizationIds,
			boolean sendEmail)
		throws PortalException, SystemException {

		// User

		Company company = companyPersistence.findByPrimaryKey(companyId);
		screenName = getScreenName(screenName);
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		if (PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE) {
			autoScreenName = true;
		}

		long userId = counterLocalService.increment();

		validate(
			companyId, userId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, firstName, lastName,
			organizationIds);

		if (autoPassword) {
			password1 = PwdToolkitUtil.generate();
		}

		if (autoScreenName) {
			ScreenNameGenerator screenNameGenerator =
				(ScreenNameGenerator)InstancePool.get(
					PropsValues.USERS_SCREEN_NAME_GENERATOR);

			try {
				screenName = screenNameGenerator.generate(
					companyId, userId, emailAddress);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		User defaultUser = getDefaultUser(companyId);

		String fullName = UserImpl.getFullName(firstName, middleName, lastName);

		String greeting = LanguageUtil.format(
			companyId, locale, "welcome-x", " " + fullName);

		User user = userPersistence.create(userId);

		user.setCompanyId(companyId);
		user.setCreateDate(now);
		user.setModifiedDate(now);
		user.setDefaultUser(false);
		user.setContactId(counterLocalService.increment());
		user.setPassword(PwdEncryptor.encrypt(password1));
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordReset(false);
		user.setScreenName(screenName);
		user.setEmailAddress(emailAddress);
		user.setLanguageId(locale.toString());
		user.setTimeZoneId(defaultUser.getTimeZoneId());
		user.setGreeting(greeting);
		user.setActive(true);

		userPersistence.update(user, false);

		// Resources

		String creatorUserName = StringPool.BLANK;

		if (creatorUserId <= 0) {
			creatorUserId = user.getUserId();

			// Don't grab the full name from the User object because it doesn't
			// have a corresponding Contact object yet

			//creatorUserName = user.getFullName();
		}
		else {
			User creatorUser = userPersistence.findByPrimaryKey(creatorUserId);

			creatorUserName = creatorUser.getFullName();
		}

		resourceLocalService.addResources(
			companyId, 0, creatorUserId, User.class.getName(), user.getUserId(),
			false, false, false);

		// Mail

		if (user.hasCompanyMx()) {
			try {
				mailService.addUser(
					userId, password1, firstName, middleName, lastName,
					emailAddress);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}
		}

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		Contact contact = contactPersistence.create(user.getContactId());

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(creatorUserId);
		contact.setUserName(creatorUserName);
		contact.setCreateDate(now);
		contact.setModifiedDate(now);
		contact.setAccountId(company.getAccountId());
		contact.setParentContactId(ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact, false);

		// Organizations

		updateOrganizations(userId, organizationIds);

		// Group

		groupLocalService.addGroup(
			user.getUserId(), User.class.getName(), user.getUserId(), null,
			null, 0, StringPool.SLASH + screenName, true);

		// Default groups

		String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_GROUP_NAMES, StringPool.NEW_LINE,
			PropsValues.ADMIN_DEFAULT_GROUP_NAMES);

		long[] groupIds = new long[defaultGroupNames.length];

		for (int i = 0; i < defaultGroupNames.length; i++) {
			try {
				Group group = groupFinder.findByC_N(
					companyId, defaultGroupNames[i]);

				groupIds[i] = group.getGroupId();
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		groupLocalService.addUserGroups(userId, groupIds);

		// Default roles

		List<Role> roles = new ArrayList<Role>();

		String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_ROLE_NAMES, StringPool.NEW_LINE,
			PropsValues.ADMIN_DEFAULT_ROLE_NAMES);

		for (int i = 0; i < defaultRoleNames.length; i++) {
			try {
				Role role = roleFinder.findByC_N(
					companyId, defaultRoleNames[i]);

				roles.add(role);
			}
			catch (NoSuchRoleException nsge) {
			}
		}

		userPersistence.setRoles(userId, roles);

		// Default user groups

		List<UserGroup> userGroups = new ArrayList<UserGroup>();

		String[] defaultUserGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_USER_GROUP_NAMES);

		for (int i = 0; i < defaultUserGroupNames.length; i++) {
			try {
				UserGroup userGroup = userGroupFinder.findByC_N(
					companyId, defaultUserGroupNames[i]);

				userGroups.add(userGroup);
			}
			catch (NoSuchUserGroupException nsuge) {
			}
		}

		userPersistence.setUserGroups(userId, userGroups);

		// Email

		if (sendEmail) {
			try {
				sendEmail(user, password1);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return user;
	}

	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, emailAddress, password, CompanyConstants.AUTH_TYPE_EA,
			headerMap, parameterMap);
	}

	public int authenticateByScreenName(
			long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, screenName, password, CompanyConstants.AUTH_TYPE_SN,
			headerMap, parameterMap);
	}

	public int authenticateByUserId(
			long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, String.valueOf(userId), password,
			CompanyConstants.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public long authenticateForBasic(
			long companyId, String authType, String login, String password)
		throws PortalException, SystemException {

		try {
			User user = null;

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				user = getUserByEmailAddress(companyId, login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = getUserByScreenName(companyId, login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				user = getUserById(companyId, GetterUtil.getLong(login));
			}

			String userPassword = user.getPassword();

			if (!user.isPasswordEncrypted()) {
				userPassword = PwdEncryptor.encrypt(userPassword);
			}

			String encPassword = PwdEncryptor.encrypt(password);

			if (userPassword.equals(password) ||
				userPassword.equals(encPassword)) {

				return user.getUserId();
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return 0;
	}

	public boolean authenticateForJAAS(long userId, String encPassword) {
		try {
			User user = userPersistence.findByPrimaryKey(userId);

			if (user.isDefaultUser()) {
				_log.error(
					"The default user should never be allowed to authenticate");

				return false;
			}

			String password = user.getPassword();

			if (user.isPasswordEncrypted()) {
				if (password.equals(encPassword)) {
					return true;
				}

				if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD) {
					encPassword = PwdEncryptor.encrypt(encPassword, password);

					if (password.equals(encPassword)) {
						return true;
					}
				}
			}
			else {
				if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD) {
					if (password.equals(encPassword)) {
						return true;
					}
				}

				password = PwdEncryptor.encrypt(password);

				if (password.equals(encPassword)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return false;
	}

	public void checkLockout(User user)
		throws PortalException, SystemException {

		if (PortalLDAPUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			return;
		}

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if (passwordPolicy.isLockout()) {

			// Reset failure count

			Date now = new Date();
			int failedLoginAttempts = user.getFailedLoginAttempts();

			if (failedLoginAttempts > 0) {
				long failedLoginTime = user.getLastFailedLoginDate().getTime();
				long elapsedTime = now.getTime() - failedLoginTime;
				long requiredElapsedTime =
					passwordPolicy.getResetFailureCount() * 1000;

				if ((requiredElapsedTime != 0) &&
					(elapsedTime > requiredElapsedTime)) {

					user.setLastFailedLoginDate(null);
					user.setFailedLoginAttempts(0);
				}
			}

			// Reset lockout

			if (user.isLockout()) {
				long lockoutTime = user.getLockoutDate().getTime();
				long elapsedTime = now.getTime() - lockoutTime;
				long requiredElapsedTime =
					passwordPolicy.getLockoutDuration() * 1000;

				if ((requiredElapsedTime != 0) &&
					(elapsedTime > requiredElapsedTime)) {

					user.setLockout(false);
					user.setLockoutDate(null);
				}
			}

			if (user.isLockout()) {
				throw new UserLockoutException();
			}
		}
	}

	public void checkLoginFailure(User user) throws SystemException {
		Date now = new Date();

		int failedLoginAttempts = user.getFailedLoginAttempts();

		user.setLastFailedLoginDate(now);
		user.setFailedLoginAttempts(++failedLoginAttempts);

		userPersistence.update(user, false);
	}

	public void checkLoginFailureByEmailAddress(
			long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		checkLoginFailure(user);
	}

	public void checkLoginFailureById(long userId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		checkLoginFailure(user);
	}

	public void checkLoginFailureByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = getUserByScreenName(companyId, screenName);

		checkLoginFailure(user);
	}

	public void checkPasswordExpired(User user)
		throws PortalException, SystemException {

		if (PortalLDAPUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			return;
		}

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		// Check if password has expired

		if (isPasswordExpired(user)) {
			int graceLoginCount = user.getGraceLoginCount();

			if (graceLoginCount < passwordPolicy.getGraceLimit()) {
				user.setGraceLoginCount(++graceLoginCount);

				userPersistence.update(user, false);
			}
			else {
				throw new PasswordExpiredException();
			}
		}

		// Check if warning message should be sent

		if (isPasswordExpiringSoon(user)) {
			user.setPasswordReset(true);

			userPersistence.update(user, false);
		}

		// Check if user should be forced to change password on first login

		if (passwordPolicy.isChangeable() &&
			passwordPolicy.isChangeRequired()) {

			if (user.getLastLoginDate() == null) {
				boolean passwordReset = false;

				if (passwordPolicy.isChangeable() &&
					passwordPolicy.isChangeRequired()) {

					passwordReset = true;
				}

				user.setPasswordReset(passwordReset);

				userPersistence.update(user, false);
			}
		}
	}

	public void clearOrganizationUsers(long organizationId)
		throws PortalException, SystemException {

		organizationPersistence.clearUsers(organizationId);

		PermissionCacheUtil.clearCache();
	}

	public void clearUserGroupUsers(long userGroupId)
		throws PortalException, SystemException {

		userGroupPersistence.clearUsers(userGroupId);

		PermissionCacheUtil.clearCache();
	}

	public KeyValuePair decryptUserId(
			long companyId, String name, String password)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		try {
			name = Encryptor.decrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		long userId = GetterUtil.getLong(name);

		User user = userPersistence.findByPrimaryKey(userId);

		try {
			password = Encryptor.decrypt(company.getKeyObj(), password);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		String encPassword = PwdEncryptor.encrypt(password);

		if (user.getPassword().equals(encPassword)) {
			if (isPasswordExpired(user)) {
				user.setPasswordReset(true);

				userPersistence.update(user, false);
			}

			return new KeyValuePair(name, password);
		}
		else {
			throw new PrincipalException();
		}
	}

	public void deletePasswordPolicyUser(long passwordPolicyId, long userId)
		throws SystemException {

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			passwordPolicyId, User.class.getName(), userId);
	}

	public void deleteRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		rolePersistence.removeUser(roleId, userId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUser(long userId)
		throws PortalException, SystemException {

		if (!PropsValues.USERS_DELETE) {
			throw new RequiredUserException();
		}

		User user = userPersistence.findByPrimaryKey(userId);

		// Group

		Group group = user.getGroup();

		groupLocalService.deleteGroup(group.getGroupId());

		// Portrait

		ImageLocalUtil.deleteImage(user.getPortraitId());

		// Password policy relation

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			User.class.getName(), userId);

		// Old passwords

		passwordTrackerLocalService.deletePasswordTrackers(userId);

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(userId);

		// External user ids

		userIdMapperLocalService.deleteUserIdMappers(userId);

		// Announcements

		announcementsDeliveryLocalService.deleteDeliveries(userId);

		// Blogs

		blogsStatsUserLocalService.deleteStatsUserByUserId(userId);

		// Document library

		dlFileRankLocalService.deleteFileRanks(userId);

		// Expando

		expandoValueLocalService.deleteValues(User.class.getName(), userId);

		// Message boards

		mbBanLocalService.deleteBansByBanUserId(userId);
		mbMessageFlagLocalService.deleteFlags(userId);
		mbStatsUserLocalService.deleteStatsUserByUserId(userId);

		// Shopping cart

		shoppingCartLocalService.deleteUserCarts(userId);

		// Social

		socialActivityLocalService.deleteUserActivities(userId);
		socialRequestLocalService.deleteReceiverUserRequests(userId);
		socialRequestLocalService.deleteUserRequests(userId);

		// Mail

		try {
			mailService.deleteUser(userId);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// Contact

		contactLocalService.deleteContact(user.getContactId());

		// Resources

		resourceLocalService.deleteResource(
			user.getCompanyId(), User.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, user.getUserId());

		// Group roles

		userGroupRoleLocalService.deleteUserGroupRolesByUserId(userId);

		// User

		userPersistence.remove(userId);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public String encryptUserId(String name)
		throws PortalException, SystemException {

		long userId = GetterUtil.getLong(name);

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		try {
			return Encryptor.encrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}
	}

	public User getDefaultUser(long companyId)
		throws PortalException, SystemException {

		User userModel = _defaultUsers.get(companyId);

		if (userModel == null) {
			userModel = userPersistence.findByC_DU(companyId, true);

			_defaultUsers.put(companyId, userModel);
		}

		return userModel;
	}

	public long getDefaultUserId(long companyId)
		throws PortalException, SystemException {

		User user = getDefaultUser(companyId);

		return user.getUserId();
	}

	public List<User> getGroupUsers(long groupId)
		throws PortalException, SystemException {

		return groupPersistence.getUsers(groupId);
	}

	public int getGroupUsersCount(long groupId) throws SystemException {
		return groupPersistence.getUsersSize(groupId);
	}

	public int getGroupUsersCount(long groupId, boolean active)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", new Long(groupId));

		return searchCount(group.getCompanyId(), null, active, params);
	}

	public List<User> getNoAnnouncementsDeliveries(String type)
		throws SystemException {

		return userFinder.findByNoAnnouncementsDeliveries(type);
	}

	public List<User> getOrganizationUsers(long organizationId)
		throws PortalException, SystemException {

		return organizationPersistence.getUsers(organizationId);
	}

	public int getOrganizationUsersCount(long organizationId)
		throws SystemException {

		return organizationPersistence.getUsersSize(organizationId);
	}

	public int getOrganizationUsersCount(long organizationId, boolean active)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgs", new Long(organizationId));

		return searchCount(organization.getCompanyId(), null, active, params);
	}

	public List<User> getPermissionUsers(
			long companyId, long groupId, String name, String primKey,
			String actionId, String firstName, String middleName,
			String lastName, String emailAddress, boolean andOperator,
			int start, int end)
		throws SystemException {

		int orgGroupPermissionsCount =
			permissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return permissionUserFinder.findByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, firstName, middleName,
				lastName, emailAddress, andOperator, start, end);
		}
		else {
			return permissionUserFinder.findByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, firstName,
				middleName, lastName, emailAddress, andOperator, start, end);
		}
	}

	public int getPermissionUsersCount(
			long companyId, long groupId, String name, String primKey,
			String actionId, String firstName, String middleName,
			String lastName, String emailAddress, boolean andOperator)
		throws SystemException {

		int orgGroupPermissionsCount =
			permissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return permissionUserFinder.countByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, firstName, middleName,
				lastName, emailAddress, andOperator);
		}
		else {
			return permissionUserFinder.countByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, firstName,
				middleName, lastName, emailAddress, andOperator);
		}
	}

	public List<User> getRoleUsers(long roleId)
		throws PortalException, SystemException {

		return rolePersistence.getUsers(roleId);
	}

	public int getRoleUsersCount(long roleId) throws SystemException {
		return rolePersistence.getUsersSize(roleId);
	}

	public int getRoleUsersCount(long roleId, boolean active)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(
			roleId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", new Long(roleId));

		return searchCount(role.getCompanyId(), null, active, params);
	}

	public List<User> getSocialUsers(
			long userId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialRelation", new Long[] {userId});

		return search(
			user.getCompanyId(), null, null, params, start, end, obc);
	}

	public List<User> getSocialUsers(
			long userId, int type, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialRelationType", new Long[] {userId, new Long(type)});

		return search(user.getCompanyId(), null, null, params, start, end, obc);
	}

	public List<User> getSocialUsers(
			long userId1, long userId2, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialMutualRelation", new Long[] {userId1, userId2});

		return search(
			user1.getCompanyId(), null, null, params, start, end, obc);
	}

	public List<User> getSocialUsers(
			long userId1, long userId2, int type, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"socialMutualRelationType",
			new Long[] {userId1, new Long(type), userId2, new Long(type)});

		return search(
			user1.getCompanyId(), null, null, params, start, end, obc);
	}

	public int getSocialUsersCount(long userId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialRelation", new Long[] {userId});

		return searchCount(user.getCompanyId(), null, null, params);
	}

	public int getSocialUsersCount(long userId, int type)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialRelationType", new Long[] {userId, new Long(type)});

		return searchCount(user.getCompanyId(), null, null, params);
	}

	public int getSocialUsersCount(long userId1, long userId2)
		throws PortalException, SystemException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("socialMutualRelation", new Long[] {userId1, userId2});

		return searchCount(user1.getCompanyId(), null, null, params);
	}

	public int getSocialUsersCount(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"socialMutualRelationType",
			new Long[] {userId1, new Long(type), userId2, new Long(type)});

		return searchCount(user1.getCompanyId(), null, null, params);
	}

	public List<User> getUserGroupUsers(long userGroupId)
		throws PortalException, SystemException {

		return userGroupPersistence.getUsers(userGroupId);
	}

	public int getUserGroupUsersCount(long userGroupId) throws SystemException {
		return userGroupPersistence.getUsersSize(userGroupId);
	}

	public int getUserGroupUsersCount(long userGroupId, boolean active)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersUserGroups", new Long(userGroupId));

		return searchCount(userGroup.getCompanyId(), null, active, params);
	}

	public User getUserByContactId(long contactId)
		throws PortalException, SystemException {

		return userPersistence.findByContactId(contactId);
	}

	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		return userPersistence.findByC_EA(companyId, emailAddress);
	}

	public User getUserById(long userId)
		throws PortalException, SystemException {

		return userPersistence.findByPrimaryKey(userId);
	}

	public User getUserById(long companyId, long userId)
		throws PortalException, SystemException {

		return userPersistence.findByC_U(companyId, userId);
	}

	public User getUserByPortraitId(long portraitId)
		throws PortalException, SystemException {

		return userPersistence.findByPortraitId(portraitId);
	}

	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		screenName = getScreenName(screenName);

		return userPersistence.findByC_SN(companyId, screenName);
	}

	public long getUserIdByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		User user = userPersistence.findByC_EA(companyId, emailAddress);

		return user.getUserId();
	}

	public long getUserIdByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		screenName = getScreenName(screenName);

		User user = userPersistence.findByC_SN(companyId, screenName);

		return user.getUserId();
	}

	public boolean hasGroupUser(long groupId, long userId)
		throws SystemException {

		return groupPersistence.containsUser(groupId, userId);
	}

	public boolean hasOrganizationUser(long organizationId, long userId)
		throws SystemException {

		return organizationPersistence.containsUser(organizationId, userId);
	}

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws SystemException {

		return passwordPolicyRelLocalService.hasPasswordPolicyRel(
			passwordPolicyId, User.class.getName(), userId);
	}

	public boolean hasRoleUser(long roleId, long userId)
		throws SystemException {

		return rolePersistence.containsUser(roleId, userId);
	}

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws SystemException {

		return userGroupPersistence.containsUser(userGroupId, userId);
	}

	public boolean isPasswordExpired(User user)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if (passwordPolicy.getExpireable()) {
			Date now = new Date();

			if (user.getPasswordModifiedDate() == null) {
				user.setPasswordModifiedDate(now);

				userPersistence.update(user, false);
			}

			long passwordStartTime = user.getPasswordModifiedDate().getTime();
			long elapsedTime = now.getTime() - passwordStartTime;

			if (elapsedTime > (passwordPolicy.getMaxAge() * 1000)) {
				return true;
			}
			else {
				return false;
			}
		}

		return false;
	}

	public boolean isPasswordExpiringSoon(User user)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if (passwordPolicy.isExpireable()) {
			Date now = new Date();

			if (user.getPasswordModifiedDate() == null) {
				user.setPasswordModifiedDate(now);

				userPersistence.update(user, false);
			}

			long timeModified = user.getPasswordModifiedDate().getTime();
			long passwordExpiresOn =
				(passwordPolicy.getMaxAge() * 1000) + timeModified;

			long timeStartWarning =
				passwordExpiresOn - (passwordPolicy.getWarningTime() * 1000);

			if (now.getTime() > timeStartWarning) {
				return true;
			}
			else {
				return false;
			}
		}

		return false;
	}

	public List<User> search(
			long companyId, String keywords, Boolean active,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return userFinder.findByKeywords(
			companyId, keywords, active, params, start, end, obc);
	}

	public List<User> search(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			Boolean active, LinkedHashMap<String, Object> params,
			boolean andSearch, int start, int end, OrderByComparator obc)
		throws SystemException {

		return userFinder.findByC_FN_MN_LN_SN_EA_A(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, active, params, andSearch, start, end, obc);
	}

	public int searchCount(
			long companyId, String keywords, Boolean active,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return userFinder.countByKeywords(companyId, keywords, active, params);
	}

	public int searchCount(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			Boolean active, LinkedHashMap<String, Object> params,
			boolean andSearch)
		throws SystemException {

		return userFinder.countByC_FN_MN_LN_SN_EA_A(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, active, params, andSearch);
	}

	public void sendPassword(
			long companyId, String emailAddress, String remoteAddr,
			String remoteHost, String userAgent)
		throws PortalException, SystemException {

		try {
			doSendPassword(
				companyId, emailAddress, remoteAddr, remoteHost, userAgent);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		rolePersistence.setUsers(roleId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		copyUserGroupLayouts(userGroupId, userIds);

		userGroupPersistence.setUsers(userGroupId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId);

		groupPersistence.removeUsers(groupId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		Group group = organization.getGroup();

		long groupId = group.getGroupId();

		userGroupRoleLocalService.deleteUserGroupRoles(userIds, groupId);

		organizationPersistence.removeUsers(organizationId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetPasswordPolicyUsers(
			long passwordPolicyId, long[] userIds)
		throws SystemException {

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		rolePersistence.removeUsers(roleId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetRoleUsers(long roleId, List<User> users)
		throws PortalException, SystemException {

		rolePersistence.removeUsers(roleId, users);

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		userGroupPersistence.removeUsers(userGroupId, userIds);

		PermissionCacheUtil.clearCache();
	}

	public User updateActive(long userId, boolean active)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setActive(active);

		userPersistence.update(user, false);

		return user;
	}

	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setAgreedToTermsOfUse(agreedToTermsOfUse);

		userPersistence.update(user, false);

		return user;
	}

	public User updateCreateDate(long userId, Date createDate)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setCreateDate(createDate);

		userPersistence.update(user, false);

		return user;
	}

	public User updateLastLogin(long userId, String loginIP)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date lastLoginDate = user.getLoginDate();

		if (lastLoginDate == null) {
			lastLoginDate = new Date();
		}

		user.setLoginDate(new Date());
		user.setLoginIP(loginIP);
		user.setLastLoginDate(lastLoginDate);
		user.setLastLoginIP(user.getLoginIP());
		user.setLastFailedLoginDate(null);
		user.setFailedLoginAttempts(0);

		userPersistence.update(user, false);

		return user;
	}

	public User updateLockout(User user, boolean lockout)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if ((passwordPolicy == null) || !passwordPolicy.isLockout()) {
			return user;
		}

		Date lockoutDate = null;

		if (lockout) {
			lockoutDate = new Date();
		}

		user.setLockout(lockout);
		user.setLockoutDate(lockoutDate);

		if (!lockout) {
			user.setLastFailedLoginDate(lockoutDate);
			user.setFailedLoginAttempts(0);
		}

		userPersistence.update(user, false);

		return user;
	}

	public User updateLockoutByEmailAddress(
			long companyId, String emailAddress, boolean lockout)
		throws PortalException, SystemException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		return updateLockout(user, lockout);
	}

	public User updateLockoutById(long userId, boolean lockout)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		return updateLockout(user, lockout);
	}

	public User updateLockoutByScreenName(
			long companyId, String screenName, boolean lockout)
		throws PortalException, SystemException {

		User user = getUserByScreenName(companyId, screenName);

		return updateLockout(user, lockout);
	}

	public User updateModifiedDate(long userId, Date modifiedDate)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setModifiedDate(modifiedDate);

		userPersistence.update(user, false);

		return user;
	}

	public void updateOrganizations(
			long userId, long[] newOrganizationIds)
		throws PortalException, SystemException {

		List<Organization> oldOrganizations = userPersistence.getOrganizations(
			userId);

		List<Long> oldOrganizationIds = new ArrayList<Long>(
			oldOrganizations.size());

		for (int i = 0; i < oldOrganizations.size(); i++) {
			Organization oldOrganization = oldOrganizations.get(i);

			long oldOrganizationId = oldOrganization.getOrganizationId();

			oldOrganizationIds.add(oldOrganizationId);

			if (!ArrayUtil.contains(newOrganizationIds, oldOrganizationId)) {
				unsetOrganizationUsers(oldOrganizationId, new long[] {userId});
			}
		}

		for (int i = 0; i < newOrganizationIds.length; i++) {
			long newOrganizationId = newOrganizationIds[i];

			if (!oldOrganizationIds.contains(newOrganizationId)) {
				addOrganizationUsers(newOrganizationId, new long[] {userId});
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		return updatePassword(
			userId, password1, password2, passwordReset, false);
	}

	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset, boolean silentUpdate)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		// Use silentUpdate so that imported user passwords are not exported
		// or validated

		if (!silentUpdate) {
			validatePassword(user.getCompanyId(), userId, password1, password2);
		}

		String oldEncPwd = user.getPassword();

		if (!user.isPasswordEncrypted()) {
			oldEncPwd = PwdEncryptor.encrypt(user.getPassword());
		}

		String newEncPwd = PwdEncryptor.encrypt(password1);

		if (user.hasCompanyMx()) {
			try {
				mailService.updatePassword(userId, password1);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}
		}

		user.setPassword(newEncPwd);
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordReset(passwordReset);
		user.setPasswordModifiedDate(new Date());
		user.setGraceLoginCount(0);

		if (!silentUpdate) {
			user.setPasswordModified(true);
		}

		try {
			userPersistence.update(user, false);
		}
		catch (ModelListenerException mle) {
			String msg = GetterUtil.getString(mle.getCause().getMessage());

			if (PortalLDAPUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
				String passwordHistory = PrefsPropsUtil.getString(
					user.getCompanyId(), PropsUtil.LDAP_ERROR_PASSWORD_HISTORY);

				if (msg.indexOf(passwordHistory) != -1) {
					throw new UserPasswordException(
						UserPasswordException.PASSWORD_ALREADY_USED);
				}
			}

			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}

		if (!silentUpdate) {
			user.setPasswordModified(false);
		}

		passwordTrackerLocalService.trackPassword(userId, oldEncPwd);

		return user;
	}

	public User updatePasswordManually(
			long userId, String password, boolean passwordEncrypted,
			boolean passwordReset, Date passwordModifiedDate)
		throws PortalException, SystemException {

		// This method should only be used to manually massage data

		User user = userPersistence.findByPrimaryKey(userId);

		user.setPassword(password);
		user.setPasswordEncrypted(passwordEncrypted);
		user.setPasswordReset(passwordReset);
		user.setPasswordModifiedDate(passwordModifiedDate);

		userPersistence.update(user, false);

		return user;
	}

	public void updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setPasswordReset(passwordReset);

		userPersistence.update(user, false);
	}

	public void updatePortrait(long userId, byte[] bytes)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long imageMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.USERS_IMAGE_MAX_SIZE));

		if ((imageMaxSize > 0) &&
			((bytes == null) || (bytes.length > imageMaxSize))) {

			throw new UserPortraitException();
		}

		long portraitId = user.getPortraitId();

		if (portraitId <= 0) {
			portraitId = counterLocalService.increment();

			user.setPortraitId(portraitId);
		}

		ImageLocalUtil.updateImage(portraitId, bytes);
	}

	public void updateScreenName(long userId, String screenName)
		throws PortalException, SystemException {

		// User

		User user = userPersistence.findByPrimaryKey(userId);

		screenName = getScreenName(screenName);

		validateScreenName(user.getCompanyId(), userId, screenName);

		user.setScreenName(screenName);

		userPersistence.update(user, false);

		// Group

		Group group = groupLocalService.getUserGroup(
			user.getCompanyId(), userId);

		group.setFriendlyURL(StringPool.SLASH + screenName);

		groupPersistence.update(group, false);
	}

	public User updateUser(
			long userId, String oldPassword, boolean passwordReset,
			String screenName, String emailAddress, String languageId,
			String timeZoneId, String greeting, String comments,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String smsSn, String aimSn, String facebookSn,
			String icqSn, String jabberSn, String msnSn, String mySpaceSn,
			String skypeSn, String twitterSn, String ymSn, String jobTitle,
			long[] organizationIds)
		throws PortalException, SystemException {

		String newPassword1 = StringPool.BLANK;
		String newPassword2 = StringPool.BLANK;

		return updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			screenName, emailAddress, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, organizationIds);
	}

	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset, String screenName,
			String emailAddress, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle,
			long[] organizationIds)
		throws PortalException, SystemException {

		// User

		String password = oldPassword;
		screenName = getScreenName(screenName);
		emailAddress = emailAddress.trim().toLowerCase();
		aimSn.trim().toLowerCase();
		facebookSn.trim().toLowerCase();
		icqSn.trim().toLowerCase();
		jabberSn.trim().toLowerCase();
		msnSn.trim().toLowerCase();
		mySpaceSn.trim().toLowerCase();
		skypeSn.trim().toLowerCase();
		twitterSn.trim().toLowerCase();
		ymSn.trim().toLowerCase();
		Date now = new Date();

		validate(userId, screenName, emailAddress, firstName, lastName, smsSn);

		if (Validator.isNotNull(newPassword1) ||
			Validator.isNotNull(newPassword2)) {

			updatePassword(userId, newPassword1, newPassword2, passwordReset);

			password = newPassword1;
		}

		User user = userPersistence.findByPrimaryKey(userId);
		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		user.setModifiedDate(now);

		if (user.getContactId() <= 0) {
			user.setContactId(counterLocalService.increment());
		}

		user.setPasswordReset(passwordReset);
		user.setScreenName(screenName);

		if (!emailAddress.equalsIgnoreCase(user.getEmailAddress())) {

			// test@test.com -> test@liferay.com

			try {
				if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
					mailService.addUser(
						userId, password, firstName, middleName, lastName,
						emailAddress);
				}

				// test@liferay.com -> bob@liferay.com

				else if (user.hasCompanyMx() &&
						 user.hasCompanyMx(emailAddress)) {

					mailService.updateEmailAddress(userId, emailAddress);
				}

				// test@liferay.com -> test@test.com

				else if (user.hasCompanyMx() &&
						 !user.hasCompanyMx(emailAddress)) {

					mailService.deleteEmailAddress(userId);
				}
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}

			user.setEmailAddress(emailAddress);
		}

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);
		user.setGreeting(greeting);
		user.setComments(comments);

		userPersistence.update(user, false);

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		long contactId = user.getContactId();

		Contact contact = null;

		try {
			contact = contactPersistence.findByPrimaryKey(contactId);
		}
		catch (NoSuchContactException nsce) {
			contact = contactPersistence.create(contactId);

			contact.setCompanyId(user.getCompanyId());
			contact.setUserName(StringPool.BLANK);
			contact.setCreateDate(now);
			contact.setAccountId(company.getAccountId());
			contact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		}

		contact.setModifiedDate(now);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setAimSn(aimSn);
		contact.setFacebookSn(facebookSn);
		contact.setIcqSn(icqSn);
		contact.setJabberSn(jabberSn);
		contact.setMsnSn(msnSn);
		contact.setMySpaceSn(mySpaceSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);
		contact.setYmSn(ymSn);
		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact, false);

		// Organizations

		updateOrganizations(userId, organizationIds);

		// Group

		Group group = groupLocalService.getUserGroup(
			user.getCompanyId(), userId);

		group.setFriendlyURL(StringPool.SLASH + screenName);

		groupPersistence.update(group, false);

		// Announcements

		announcementsDeliveryLocalService.getUserDeliveries(user.getUserId());

		// Permission cache

		PermissionCacheUtil.clearCache();

		return user;
	}

	protected int authenticate(
			long companyId, String login, String password, String authType,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		login = login.trim().toLowerCase();

		long userId = GetterUtil.getLong(login);

		// User input validation

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			if (!Validator.isEmailAddress(login)) {
				throw new UserEmailAddressException();
			}
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			if (Validator.isNull(login)) {
				throw new UserScreenNameException();
			}
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			if (Validator.isNull(login)) {
				throw new UserIdException();
			}
		}

		if (Validator.isNull(password)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}

		int authResult = Authenticator.FAILURE;

		// Pre-authentication pipeline

		String[] authPipelinePre =
			PropsUtil.getArray(PropsUtil.AUTH_PIPELINE_PRE);

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			authResult = AuthPipeline.authenticateByEmailAddress(
				authPipelinePre, companyId, login, password, headerMap,
				parameterMap);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			authResult = AuthPipeline.authenticateByScreenName(
				authPipelinePre, companyId, login, password, headerMap,
				parameterMap);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			authResult = AuthPipeline.authenticateByUserId(
				authPipelinePre, companyId, userId, password, headerMap,
				parameterMap);
		}

		// Get user

		User user = null;

		try {
			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				user = userPersistence.findByC_EA(companyId, login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = userPersistence.findByC_SN(companyId, login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				user = userPersistence.findByC_U(
					companyId, GetterUtil.getLong(login));
			}
		}
		catch (NoSuchUserException nsue) {
			return Authenticator.DNE;
		}

		if (user.isDefaultUser()) {
			_log.error(
				"The default user should never be allowed to authenticate");

			return Authenticator.DNE;
		}

		if (!user.isPasswordEncrypted()) {
			user.setPassword(PwdEncryptor.encrypt(user.getPassword()));
			user.setPasswordEncrypted(true);

			userPersistence.update(user, false);
		}

		// Check password policy to see if the is account locked out or if the
		// password is expired

		checkLockout(user);

		checkPasswordExpired(user);

		// Authenticate against the User_ table

		if (authResult == Authenticator.SUCCESS) {
			if (PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK) {
				String encPassword = PwdEncryptor.encrypt(
					password, user.getPassword());

				if (user.getPassword().equals(encPassword)) {
					authResult = Authenticator.SUCCESS;
				}
				else if (GetterUtil.getBoolean(PropsUtil.get(
							PropsUtil.AUTH_MAC_ALLOW))) {

					try {
						MessageDigest digester = MessageDigest.getInstance(
							PropsUtil.get(PropsUtil.AUTH_MAC_ALGORITHM));

						digester.update(login.getBytes("UTF8"));

						String shardKey =
							PropsUtil.get(PropsUtil.AUTH_MAC_SHARED_KEY);

						encPassword = Base64.encode(
							digester.digest(shardKey.getBytes("UTF8")));

						if (password.equals(encPassword)) {
							authResult = Authenticator.SUCCESS;
						}
						else {
							authResult = Authenticator.FAILURE;
						}
					}
					catch (NoSuchAlgorithmException nsae) {
						throw new SystemException(nsae);
					}
					catch (UnsupportedEncodingException uee) {
						throw new SystemException(uee);
					}
				}
				else {
					authResult = Authenticator.FAILURE;
				}
			}
		}

		// Post-authentication pipeline

		if (authResult == Authenticator.SUCCESS) {
			String[] authPipelinePost =
				PropsUtil.getArray(PropsUtil.AUTH_PIPELINE_POST);

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = AuthPipeline.authenticateByEmailAddress(
					authPipelinePost, companyId, login, password, headerMap,
					parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = AuthPipeline.authenticateByScreenName(
					authPipelinePost, companyId, login, password, headerMap,
					parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = AuthPipeline.authenticateByUserId(
					authPipelinePost, companyId, userId, password, headerMap,
					parameterMap);
			}
		}

		// Execute code triggered by authentication failure

		if (authResult == Authenticator.FAILURE) {
			try {
				String[] authFailure =
					PropsUtil.getArray(PropsUtil.AUTH_FAILURE);

				if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					AuthPipeline.onFailureByEmailAddress(
						authFailure, companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					AuthPipeline.onFailureByScreenName(
						authFailure, companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
					AuthPipeline.onFailureByUserId(
						authFailure, companyId, userId, headerMap,
						parameterMap);
				}

				// Let LDAP handle max failure event

				if (!PortalLDAPUtil.isPasswordPolicyEnabled(
						user.getCompanyId())) {

					PasswordPolicy passwordPolicy = user.getPasswordPolicy();

					int failedLoginAttempts = user.getFailedLoginAttempts();
					int maxFailures = passwordPolicy.getMaxFailure();

					if ((failedLoginAttempts >= maxFailures) &&
						(maxFailures != 0)) {

						String[] authMaxFailures =
							PropsUtil.getArray(PropsUtil.AUTH_MAX_FAILURES);

						if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
							AuthPipeline.onMaxFailuresByEmailAddress(
								authMaxFailures, companyId, login, headerMap,
								parameterMap);
						}
						else if (authType.equals(
									CompanyConstants.AUTH_TYPE_SN)) {

							AuthPipeline.onMaxFailuresByScreenName(
								authMaxFailures, companyId, login, headerMap,
								parameterMap);
						}
						else if (authType.equals(
									CompanyConstants.AUTH_TYPE_ID)) {

							AuthPipeline.onMaxFailuresByUserId(
								authMaxFailures, companyId, userId, headerMap,
								parameterMap);
						}
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return authResult;
	}

	protected void copyUserGroupLayouts(long userGroupId, long userId)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupLocalService.getUserGroup(userGroupId);
		User user = getUserById(userId);

		Map<String, String[]> parameterMap = getLayoutTemplatesParameters();

		if (userGroup.hasPrivateLayouts()) {
			long sourceGroupId = userGroup.getGroup().getGroupId();
			long targetGroupId = user.getGroup().getGroupId();

			byte[] bytes = layoutLocalService.exportLayouts(
				sourceGroupId, true, parameterMap, null, null);

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

			layoutLocalService.importLayouts(
				userId, targetGroupId, true, parameterMap, bais);
		}

		if (userGroup.hasPublicLayouts()) {
			long sourceGroupId = userGroup.getGroup().getGroupId();
			long targetGroupId = user.getGroup().getGroupId();

			byte[] bytes = layoutLocalService.exportLayouts(
				sourceGroupId, false, parameterMap, null, null);

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

			layoutLocalService.importLayouts(
				userId, targetGroupId, false, parameterMap, bais);
		}
	}

	protected void copyUserGroupLayouts(long userGroupId, long userIds[])
		throws PortalException, SystemException {

		for (long userId : userIds) {
			if (!userGroupPersistence.containsUser(userGroupId, userId)) {
				copyUserGroupLayouts(userGroupId, userId);
			}
		}
	}

	protected void doSendPassword(
			long companyId, String emailAddress, String remoteAddr,
			String remoteHost, String userAgent)
		throws IOException, PortalException, SystemException {

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.COMPANY_SECURITY_SEND_PASSWORD) ||
			!PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_ENABLED)) {

			return;
		}

		emailAddress = emailAddress.trim().toLowerCase();

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new UserEmailAddressException();
		}

		Company company = companyPersistence.findByPrimaryKey(companyId);

		User user = userPersistence.findByC_EA(companyId, emailAddress);

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		/*if (user.hasCompanyMx()) {
			throw new SendPasswordException();
		}*/

		String newPassword = null;

		if (!PwdEncryptor.PASSWORDS_ENCRYPTION_ALGORITHM.equals(
				PwdEncryptor.TYPE_NONE)) {

			newPassword = PwdToolkitUtil.generate();

			boolean passwordReset = false;

			if (passwordPolicy.getChangeable() &&
				passwordPolicy.getChangeRequired()) {

				passwordReset = true;
			}

			user.setPassword(PwdEncryptor.encrypt(newPassword));
			user.setPasswordUnencrypted(newPassword);
			user.setPasswordEncrypted(true);
			user.setPasswordReset(passwordReset);

			userPersistence.update(user, false);
		}
		else {
			newPassword = user.getPassword();
		}

		String fromName = PrefsPropsUtil.getString(
			companyId, PropsUtil.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsUtil.ADMIN_EMAIL_FROM_ADDRESS);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		String subject = PrefsPropsUtil.getContent(
			companyId, PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			companyId, PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_BODY);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REMOTE_ADDRESS$]",
				"[$REMOTE_HOST$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$USER_AGENT$]",
				"[$USER_ID$]",
				"[$USER_PASSWORD$]",
				"[$USER_SCREENNAME$]"
			},
			new String[] {
				fromAddress,
				fromName,
				company.getVirtualHost(),
				remoteAddr,
				remoteHost,
				toAddress,
				toName,
				HtmlUtil.escape(userAgent),
				String.valueOf(user.getUserId()),
				newPassword,
				user.getScreenName()
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REMOTE_ADDRESS$]",
				"[$REMOTE_HOST$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$USER_AGENT$]",
				"[$USER_ID$]",
				"[$USER_PASSWORD$]",
				"[$USER_SCREENNAME$]"
			},
			new String[] {
				fromAddress,
				fromName,
				company.getVirtualHost(),
				remoteAddr,
				remoteHost,
				toAddress,
				toName,
				HtmlUtil.escape(userAgent),
				String.valueOf(user.getUserId()),
				newPassword,
				user.getScreenName()
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	protected Map<String, String[]> getLayoutTemplatesParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.ADD_AS_NEW_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	protected String getScreenName(String screenName) {
		return Normalizer.normalizeToAscii(screenName.trim().toLowerCase());
	}

	protected void sendEmail(User user, String password)
		throws IOException, PortalException, SystemException {

		if (!PrefsPropsUtil.getBoolean(
				user.getCompanyId(),
				PropsUtil.ADMIN_EMAIL_USER_ADDED_ENABLED)) {

			return;
		}

		long companyId = user.getCompanyId();

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String fromName = PrefsPropsUtil.getString(
			companyId, PropsUtil.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsUtil.ADMIN_EMAIL_FROM_ADDRESS);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		String subject = PrefsPropsUtil.getContent(
			companyId, PropsUtil.ADMIN_EMAIL_USER_ADDED_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			companyId, PropsUtil.ADMIN_EMAIL_USER_ADDED_BODY);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$USER_ID$]",
				"[$USER_PASSWORD$]",
				"[$USER_SCREENNAME$]"
			},
			new String[] {
				fromAddress,
				fromName,
				company.getVirtualHost(),
				toAddress,
				toName,
				String.valueOf(user.getUserId()),
				password,
				user.getScreenName()
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]",
				"[$USER_ID$]",
				"[$USER_PASSWORD$]",
				"[$USER_SCREENNAME$]"
			},
			new String[] {
				fromAddress,
				fromName,
				company.getVirtualHost(),
				toAddress,
				toName,
				String.valueOf(user.getUserId()),
				password,
				user.getScreenName()
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	protected void validate(
			long userId, String screenName, String emailAddress,
			String firstName, String lastName, String smsSn)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (!user.getScreenName().equalsIgnoreCase(screenName)) {
			validateScreenName(user.getCompanyId(), userId, screenName);
		}

		validateEmailAddress(emailAddress);

		if (!user.isDefaultUser()) {
			try {
				if (!user.getEmailAddress().equalsIgnoreCase(emailAddress)) {
					if (userPersistence.findByC_EA(
							user.getCompanyId(), emailAddress) != null) {

						throw new DuplicateUserEmailAddressException();
					}
				}
			}
			catch (NoSuchUserException nsue) {
			}

			String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
				user.getCompanyId(), PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES,
				StringPool.NEW_LINE,
				PropsValues.ADMIN_RESERVED_EMAIL_ADDRESSES);

			for (int i = 0; i < reservedEmailAddresses.length; i++) {
				if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
					throw new ReservedUserEmailAddressException();
				}
			}

			if (Validator.isNull(firstName)) {
				throw new ContactFirstNameException();
			}
			else if (Validator.isNull(lastName)) {
				throw new ContactLastNameException();
			}
		}

		if (Validator.isNotNull(smsSn) && !Validator.isEmailAddress(smsSn)) {
			throw new UserSmsException();
		}
	}

	protected void validate(
			long companyId, long userId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, String firstName, String lastName,
			long[] organizationIds)
		throws PortalException, SystemException {

		if (!autoScreenName) {
			validateScreenName(companyId, userId, screenName);
		}

		if (!autoPassword) {
			PasswordPolicy passwordPolicy =
				passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

			PwdToolkitUtil.validate(
				companyId, 0, password1, password2, passwordPolicy);
		}

		validateEmailAddress(emailAddress);

		try {
			User user = userPersistence.findByC_EA(companyId, emailAddress);

			if (user != null) {
				throw new DuplicateUserEmailAddressException();
			}
		}
		catch (NoSuchUserException nsue) {
		}

		String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES,
			StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_EMAIL_ADDRESSES);

		for (int i = 0; i < reservedEmailAddresses.length; i++) {
			if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
				throw new ReservedUserEmailAddressException();
			}
		}

		if (Validator.isNull(firstName)) {
			throw new ContactFirstNameException();
		}
		else if (Validator.isNull(lastName)) {
			throw new ContactLastNameException();
		}
	}

	protected void validateEmailAddress(String emailAddress)
		throws PortalException {

		if (!Validator.isEmailAddress(emailAddress) ||
			emailAddress.startsWith("root@") ||
			emailAddress.startsWith("postmaster@")) {

			throw new UserEmailAddressException();
		}
	}

	protected void validatePassword(
			long companyId, long userId, String password1, String password2)
		throws PortalException, SystemException {

		if (Validator.isNull(password1) || Validator.isNull(password2)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}

		if (!password1.equals(password2)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORDS_DO_NOT_MATCH);
		}

		PasswordPolicy passwordPolicy =
			passwordPolicyLocalService.getPasswordPolicyByUserId(userId);

		PwdToolkitUtil.validate(
			companyId, userId, password1, password2, passwordPolicy);
	}

	protected void validateScreenName(
			long companyId, long userId, String screenName)
		throws PortalException, SystemException {

		if (Validator.isNull(screenName)) {
			throw new UserScreenNameException();
		}

		ScreenNameValidator screenNameValidator =
			(ScreenNameValidator)InstancePool.get(
				PropsValues.USERS_SCREEN_NAME_VALIDATOR);

		if (screenNameValidator != null) {
			if (!screenNameValidator.validate(companyId, screenName)) {
				throw new UserScreenNameException();
			}
		}

		if (Validator.isNumber(screenName) &&
			!screenName.equals(String.valueOf(userId))) {

			throw new UserScreenNameException();
		}

		for (char c : screenName.toCharArray()) {
			if ((!Validator.isChar(c)) && (!Validator.isDigit(c)) &&
				(c != CharPool.DASH) && (c != CharPool.PERIOD) &&
				(c != CharPool.UNDERLINE)) {

				throw new UserScreenNameException();
			}
		}

		String[] anonymousNames = PrincipalBean.ANONYMOUS_NAMES;

		for (int i = 0; i < anonymousNames.length; i++) {
			if (screenName.equalsIgnoreCase(anonymousNames[i])) {
				throw new UserScreenNameException();
			}
		}

		User user = userPersistence.fetchByC_SN(companyId, screenName);

		if (user != null) {
			throw new DuplicateUserScreenNameException();
		}

		String friendlyURL = StringPool.SLASH + screenName;

		Group group = groupPersistence.fetchByC_F(companyId, friendlyURL);

		if (group != null) {
			throw new DuplicateUserScreenNameException();
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new UserScreenNameException(
				new GroupFriendlyURLException(exceptionType));
		}

		String[] reservedScreenNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_RESERVED_SCREEN_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_SCREEN_NAMES);

		for (int i = 0; i < reservedScreenNames.length; i++) {
			if (screenName.equalsIgnoreCase(reservedScreenNames[i])) {
				throw new ReservedUserScreenNameException();
			}
		}
	}

	private static Log _log = LogFactory.getLog(UserLocalServiceImpl.class);

	private static Map<Long, User> _defaultUsers =
		new ConcurrentHashMap<Long, User>();

}