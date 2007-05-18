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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.OrganizationParentException;
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
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserIdMapperLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.base.UserLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.ContactUtil;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.PermissionUserFinder;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCartLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

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

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class UserLocalServiceImpl extends UserLocalServiceBaseImpl {

	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		GroupUtil.addUsers(groupId, userIds);

		// Community roles

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleImpl.COMMUNITY_MEMBER);

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			UserGroupRoleLocalServiceUtil.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});
		}
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyRelLocalServiceUtil.addPasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RoleUtil.addUsers(roleId, userIds);
	}

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupUtil.addUsers(userGroupId, userIds);
	}

	public User addUser(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long organizationId,
			long locationId, boolean sendEmail)
		throws PortalException, SystemException {

		// User

		Company company = CompanyUtil.findByPrimaryKey(companyId);
		screenName = screenName.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		boolean alwaysAutoScreenName = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));

		if (alwaysAutoScreenName) {
			autoScreenName = true;
		}

		validate(
			companyId, autoPassword, password1, password2, autoScreenName,
			screenName, emailAddress, firstName, lastName, organizationId,
			locationId);

		validateOrganizations(companyId, organizationId, locationId);

		if (autoPassword) {
			password1 = PwdToolkitUtil.generate();
		}

		long userId = CounterLocalServiceUtil.increment();

		if (autoScreenName) {
			ScreenNameGenerator screenNameGenerator =
				(ScreenNameGenerator)InstancePool.get(
					PropsUtil.get(PropsUtil.USERS_SCREEN_NAME_GENERATOR));

			try {
				screenName = screenNameGenerator.generate(companyId, userId);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		User defaultUser = getDefaultUser(companyId);

		String fullName = UserImpl.getFullName(firstName, middleName, lastName);

		String greeting = null;

		try {
			greeting =
				LanguageUtil.get(companyId, locale, "welcome") + ", " +
					fullName + "!";
		}
		catch (LanguageException le) {
			greeting = "Welcome, " + fullName + "!";
		}

		User user = UserUtil.create(userId);

		user.setCompanyId(companyId);
		user.setCreateDate(now);
		user.setModifiedDate(now);
		user.setDefaultUser(false);
		user.setContactId(CounterLocalServiceUtil.increment());
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

		UserUtil.update(user);

		// Resources

		String creatorUserName = StringPool.BLANK;

		if (creatorUserId <= 0) {
			creatorUserId = user.getUserId();

			// Don't grab the full name from the User object because it doesn't
			// have a corresponding Contact object yet

			//creatorUserName = user.getFullName();
		}
		else {
			User creatorUser = UserUtil.findByPrimaryKey(creatorUserId);

			creatorUserName = creatorUser.getFullName();
		}

		ResourceLocalServiceUtil.addResources(
			companyId, 0, creatorUserId, User.class.getName(), user.getUserId(),
			false, false, false);

		// Mail

		if (user.hasCompanyMx()) {
			try {
				MailServiceUtil.addUser(
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

		Contact contact = ContactUtil.create(user.getContactId());

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(creatorUserId);
		contact.setUserName(creatorUserName);
		contact.setCreateDate(now);
		contact.setModifiedDate(now);
		contact.setAccountId(company.getAccountId());
		contact.setParentContactId(ContactImpl.DEFAULT_PARENT_CONTACT_ID);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		UserUtil.clearOrganizations(userId);

		if (organizationId > 0) {
			UserUtil.addOrganization(userId, organizationId);
		}

		if (locationId  > 0) {
			UserUtil.addOrganization(userId, locationId);
		}

		// Group

		GroupLocalServiceUtil.addGroup(
			user.getUserId(), User.class.getName(), user.getUserId(), null,
			null, null, null, true);

		// Default groups

		List groups = new ArrayList();

		String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_GROUP_NAMES);

		for (int i = 0; i < defaultGroupNames.length; i++) {
			try {
				Group group = GroupFinder.findByC_N(
					companyId, defaultGroupNames[i]);

				groups.add(group);
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		UserUtil.setGroups(userId, groups);

		// Default roles

		List roles = new ArrayList();

		String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_ROLE_NAMES);

		for (int i = 0; i < defaultRoleNames.length; i++) {
			try {
				Role role = RoleFinder.findByC_N(
					companyId, defaultRoleNames[i]);

				roles.add(role);
			}
			catch (NoSuchRoleException nsge) {
			}
		}

		UserUtil.setRoles(userId, roles);

		// Default user groups

		List userGroups = new ArrayList();

		String[] defaultUserGroupNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES);

		for (int i = 0; i < defaultUserGroupNames.length; i++) {
			try {
				UserGroup userGroup = UserGroupFinder.findByC_N(
					companyId, defaultUserGroupNames[i]);

				userGroups.add(userGroup);
			}
			catch (NoSuchUserGroupException nsuge) {
			}
		}

		UserUtil.setUserGroups(userId, userGroups);

		// Email

		if (sendEmail) {
			sendEmail(user, password1);
		}

		return user;
	}

	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password,
			Map headerMap, Map parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, emailAddress, password, CompanyImpl.AUTH_TYPE_EA,
			headerMap, parameterMap);
	}

	public int authenticateByScreenName(
			long companyId, String screenName, String password, Map headerMap,
			Map parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, screenName, password, CompanyImpl.AUTH_TYPE_SN,
			headerMap, parameterMap);
	}

	public int authenticateByUserId(
			long companyId, long userId, String password, Map headerMap,
			Map parameterMap)
		throws PortalException, SystemException {

		return authenticate(
			companyId, String.valueOf(userId), password,
			CompanyImpl.AUTH_TYPE_ID, headerMap, parameterMap);
	}

	public boolean authenticateForJAAS(long userId, String encPwd)
		throws PortalException, SystemException {

		try {
			User user = UserUtil.findByPrimaryKey(userId);

			if (user.isDefaultUser()) {
				_log.error(
					"The default user should never be allowed to authenticate");

				return false;
			}

			String password = user.getPassword();

			if (password.equals(encPwd)) {
				return true;
			}
			else if (!GetterUtil.getBoolean(PropsUtil.get(
						PropsUtil.PORTAL_JAAS_STRICT_PASSWORD))) {

				encPwd = Encryptor.digest(encPwd);

				if (password.equals(encPwd)) {
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

	public void checkLoginFailure(User user)
		throws PortalException, SystemException {

		Date now = new Date();

		int failedLoginAttempts = user.getFailedLoginAttempts();

		user.setLastFailedLoginDate(now);
		user.setFailedLoginAttempts(++failedLoginAttempts);

		UserUtil.update(user);
	}

	public void checkLoginFailureByEmailAddress(
			long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			companyId, emailAddress);

		checkLoginFailure(user);
	}

	public void checkLoginFailureById(long userId)
		throws PortalException, SystemException {

		User user = this.getUserById(userId);

		checkLoginFailure(user);
	}

	public void checkLoginFailureByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByScreenName(
			companyId, screenName);

		checkLoginFailure(user);
	}

	public KeyValuePair decryptUserId(
			long companyId, String name, String password)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		try {
			name = Encryptor.decrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		long userId = GetterUtil.getLong(name);

		User user = UserUtil.findByPrimaryKey(userId);

		try {
			password = Encryptor.decrypt(company.getKeyObj(), password);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		String encPwd = PwdEncryptor.encrypt(password);

		if (user.getPassword().equals(encPwd)) {
			if (isPasswordExpired(user)) {
				user.setPasswordReset(true);

				UserUtil.update(user);
			}

			return new KeyValuePair(name, password);
		}
		else {
			throw new PrincipalException();
		}
	}

	public void deletePasswordPolicyUser(long passwordPolicyId, long userId)
		throws PortalException, SystemException {

		PasswordPolicyRelLocalServiceUtil.deletePasswordPolicyRel(
			passwordPolicyId, User.class.getName(), userId);
	}

	public void deleteRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		RoleUtil.removeUser(roleId, userId);
	}

	public void deleteUser(long userId)
		throws PortalException, SystemException {

		if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.USERS_DELETE))) {
			throw new RequiredUserException();
		}

		User user = UserUtil.findByPrimaryKey(userId);

		// Group

		Group group = user.getGroup();

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		// Portrait

		ImageLocalUtil.deleteImage(user.getPortraitId());

		// Password policy relation

		PasswordPolicyRelLocalServiceUtil.deletePasswordPolicyRel(
			User.class.getName(), userId);

		// Old passwords

		PasswordTrackerLocalServiceUtil.deletePasswordTrackers(userId);

		// External user ids

		UserIdMapperLocalServiceUtil.deleteUserIdMappers(userId);

		// Document library

		DLFileRankLocalServiceUtil.deleteFileRanks(userId);

		// Message boards

		MBBanLocalServiceUtil.deleteBansByBanUserId(userId);
		MBMessageFlagLocalServiceUtil.deleteFlags(userId);
		MBStatsUserLocalServiceUtil.deleteStatsUserByUserId(userId);

		// Shopping cart

		ShoppingCartLocalServiceUtil.deleteUserCarts(userId);

		// Mail

		try {
			MailServiceUtil.deleteUser(userId);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// Contact

		ContactLocalServiceUtil.deleteContact(user.getContactId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			user.getCompanyId(), User.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, user.getUserId());

		// Group roles

		UserGroupRoleLocalServiceUtil.deleteUserGroupRolesByUserId(userId);

		// User

		UserUtil.remove(userId);
	}

	public String encryptUserId(String name)
		throws PortalException, SystemException {

		long userId = GetterUtil.getLong(name);

		User user = UserUtil.findByPrimaryKey(userId);

		Company company = CompanyUtil.findByPrimaryKey(user.getCompanyId());

		try {
			return Encryptor.encrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}
	}

	public User getDefaultUser(long companyId)
		throws PortalException, SystemException {

		return UserUtil.findByC_DU(companyId, true);
	}

	public long getDefaultUserId(long companyId)
		throws PortalException, SystemException {

		User user = UserUtil.findByC_DU(companyId, true);

		return user.getUserId();
	}

	public List getGroupUsers(long groupId)
		throws PortalException, SystemException {

		return GroupUtil.getUsers(groupId);
	}

	public List getPermissionUsers(
			long companyId, long groupId, String name, String primKey,
			String actionId, String firstName, String middleName,
			String lastName, String emailAddress, boolean andOperator,
			int begin, int end)
		throws PortalException, SystemException {

		int orgGroupPermissionsCount =
			PermissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return PermissionUserFinder.findByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, firstName, middleName,
				lastName, emailAddress, andOperator, begin, end);
		}
		else {
			return PermissionUserFinder.findByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, firstName,
				middleName, lastName, emailAddress, andOperator, begin, end);
		}
	}

	public int getPermissionUsersCount(
			long companyId, long groupId, String name, String primKey,
			String actionId, String firstName, String middleName,
			String lastName, String emailAddress, boolean andOperator)
		throws PortalException, SystemException {

		int orgGroupPermissionsCount =
			PermissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return PermissionUserFinder.countByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, firstName, middleName,
				lastName, emailAddress, andOperator);
		}
		else {
			return PermissionUserFinder.countByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, firstName,
				middleName, lastName, emailAddress, andOperator);
		}
	}

	public List getRoleUsers(long roleId)
		throws PortalException, SystemException {

		return RoleUtil.getUsers(roleId);
	}

	public User getUserByContactId(long contactId)
		throws PortalException, SystemException {

		return UserUtil.findByContactId(contactId);
	}

	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		return UserUtil.findByC_EA(companyId, emailAddress);
	}

	public User getUserById(long userId)
		throws PortalException, SystemException {

		return UserUtil.findByPrimaryKey(userId);
	}

	public User getUserById(long companyId, long userId)
		throws PortalException, SystemException {

		return UserUtil.findByC_U(companyId, userId);
	}

	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		screenName = screenName.trim().toLowerCase();

		return UserUtil.findByC_SN(companyId, screenName);
	}

	public long getUserIdByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		User user = UserUtil.findByC_EA(companyId, emailAddress);

		return user.getUserId();
	}

	public long getUserIdByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		screenName = screenName.trim().toLowerCase();

		User user = UserUtil.findByC_SN(companyId, screenName);

		return user.getUserId();
	}

	public boolean hasGroupUser(long groupId, long userId)
		throws PortalException, SystemException {

		return GroupUtil.containsUser(groupId, userId);
	}

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws PortalException, SystemException {

		return PasswordPolicyRelLocalServiceUtil.hasPasswordPolicyRel(
			passwordPolicyId, User.class.getName(), userId);
	}

	public boolean hasRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		return RoleUtil.containsUser(roleId, userId);
	}

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws PortalException, SystemException {

		return UserGroupUtil.containsUser(userGroupId, userId);
	}

	public boolean isPasswordExpired(User user)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if (passwordPolicy.getExpireable()) {
			Date now = new Date();

			if (user.getPasswordModifiedDate() == null) {
				user.setPasswordModifiedDate(now);

				UserUtil.update(user);
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

	public List search(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			boolean active, LinkedHashMap params, boolean andSearch, int begin,
			int end, OrderByComparator obc)
		throws SystemException {

		return UserFinder.findByC_FN_MN_LN_SN_EA_A(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, active, params, andSearch, begin, end, obc);
	}

	public int searchCount(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress,
			boolean active, LinkedHashMap params, boolean andSearch)
		throws SystemException {

		return UserFinder.countByC_FN_MN_LN_SN_EA_A(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, active, params, andSearch);
	}

	public void sendPassword(
			long companyId, String emailAddress, String remoteAddr,
			String remoteHost, String userAgent)
		throws PortalException, SystemException {

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

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		User user = UserUtil.findByC_EA(companyId, emailAddress);

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		/*if (user.hasCompanyMx()) {
			throw new SendPasswordException();
		}*/

		if (PwdEncryptor.PASSWORDS_ENCRYPTED) {
			user.setPassword(PwdToolkitUtil.generate());
			user.setPasswordEncrypted(false);
			user.setPasswordReset(
				passwordPolicy.getChangeable() &&
				passwordPolicy.getChangeRequired());

			UserUtil.update(user);
		}

		try {
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
					"[$USER_PASSWORD$]"
				},
				new String[] {
					fromAddress,
					fromName,
					company.getPortalURL(),
					remoteAddr,
					remoteHost,
					toAddress,
					toName,
					userAgent,
					String.valueOf(user.getUserId()),
					user.getPassword()
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
					"[$USER_PASSWORD$]"
				},
				new String[] {
					fromAddress,
					fromName,
					company.getPortalURL(),
					remoteAddr,
					remoteHost,
					toAddress,
					toName,
					userAgent,
					String.valueOf(user.getUserId()),
					user.getPassword()
				});

			InternetAddress from = new InternetAddress(fromAddress, fromName);

			InternetAddress to = new InternetAddress(toAddress, toName);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void setGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		GroupUtil.setUsers(groupId, userIds);
	}

	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RoleUtil.setUsers(roleId, userIds);
	}

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupUtil.setUsers(userGroupId, userIds);
	}

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(userIds, groupId);

		GroupUtil.removeUsers(groupId, userIds);
	}

	public void unsetPasswordPolicyUsers(
			long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyRelLocalServiceUtil.deletePasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RoleUtil.removeUsers(roleId, userIds);
	}

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupUtil.removeUsers(userGroupId, userIds);
	}

	public User updateActive(long userId, boolean active)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		user.setActive(active);

		UserUtil.update(user);

		return user;
	}

	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		user.setAgreedToTermsOfUse(agreedToTermsOfUse);

		UserUtil.update(user);

		return user;
	}

	public User updateLastLogin(long userId, String loginIP)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		user.setLoginDate(new Date());
		user.setLoginIP(loginIP);
		user.setLastLoginDate(user.getLoginDate());
		user.setLastLoginIP(user.getLoginIP());
		user.setLastFailedLoginDate(null);
		user.setFailedLoginAttempts(0);

		UserUtil.update(user);

		return user;
	}

	public User updateLockout(User user, boolean lockout)
		throws PortalException, SystemException {

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

		UserUtil.update(user);

		return user;
	}

	public User updateLockoutByEmailAddress(
			long companyId, String emailAddress, boolean lockout)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			companyId, emailAddress);

		return updateLockout(user, lockout);
	}

	public User updateLockoutById(long userId, boolean lockout)
		throws PortalException, SystemException {

		User user = this.getUserById(userId);

		return updateLockout(user, lockout);
	}

	public User updateLockoutByScreenName(
			long companyId, String screenName, boolean lockout)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByScreenName(
			companyId, screenName);

		return updateLockout(user, lockout);
	}

	public User updatePassword(
			long userId, String password1, String password2,
				boolean passwordReset)
		throws PortalException, SystemException {

		Date now = new Date();

		validatePassword(userId, password1, password2);

		User user = UserUtil.findByPrimaryKey(userId);

		String oldEncPwd = user.getPassword();

		if (!user.isPasswordEncrypted()) {
			oldEncPwd = PwdEncryptor.encrypt(user.getPassword());
		}

		String newEncPwd = PwdEncryptor.encrypt(password1);

		if (user.hasCompanyMx()) {
			try {
				MailServiceUtil.updatePassword(userId, password1);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}
		}

		user.setPassword(newEncPwd);
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordReset(passwordReset);
		user.setPasswordModifiedDate(now);
		user.setGraceLoginCount(0);

		UserUtil.update(user);

		PasswordTrackerLocalServiceUtil.trackPassword(userId, oldEncPwd);

		return user;
	}

	public void updatePortrait(long userId, byte[] bytes)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		long imageMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.USERS_IMAGE_MAX_SIZE));

		if ((imageMaxSize > 0) &&
			((bytes == null) || (bytes.length > imageMaxSize))) {

			throw new UserPortraitException();
		}

		long portraitId = user.getPortraitId();

		if (portraitId <= 0) {
			portraitId = CounterLocalServiceUtil.increment();

			user.setPortraitId(portraitId);
		}

		ImageLocalUtil.updateImage(portraitId, bytes);
	}

	public User updateUser(
			long userId, String password, String screenName,
			String emailAddress, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String icqSn, String jabberSn,
			String msnSn, String skypeSn, String ymSn, String jobTitle,
			long organizationId, long locationId)
		throws PortalException, SystemException {

		// User

		screenName = screenName.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		validate(userId, screenName, emailAddress, firstName, lastName, smsSn);

		User user = UserUtil.findByPrimaryKey(userId);
		Company company = CompanyUtil.findByPrimaryKey(user.getCompanyId());

		validateOrganizations(user.getCompanyId(), organizationId, locationId);

		user.setModifiedDate(now);

		if (user.getContactId() <= 0) {
			user.setContactId(CounterLocalServiceUtil.increment());
		}

		user.setScreenName(screenName);

		if (!emailAddress.equals(user.getEmailAddress())) {

			// test@test.com -> test@liferay.com

			try {
				if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
					MailServiceUtil.addUser(
						userId, password, firstName, middleName, lastName,
						emailAddress);
				}

				// test@liferay.com -> bob@liferay.com

				else if (user.hasCompanyMx() &&
						 user.hasCompanyMx(emailAddress)) {

					MailServiceUtil.updateEmailAddress(userId, emailAddress);
				}

				// test@liferay.com -> test@test.com

				else if (user.hasCompanyMx() &&
						 !user.hasCompanyMx(emailAddress)) {

					MailServiceUtil.deleteEmailAddress(userId);
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

		UserUtil.update(user);

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		long contactId = user.getContactId();

		Contact contact = null;

		try {
			contact = ContactUtil.findByPrimaryKey(contactId);
		}
		catch (NoSuchContactException nsce) {
			contact = ContactUtil.create(contactId);

			contact.setCompanyId(user.getCompanyId());
			contact.setUserName(StringPool.BLANK);
			contact.setCreateDate(now);
			contact.setAccountId(company.getAccountId());
			contact.setParentContactId(ContactImpl.DEFAULT_PARENT_CONTACT_ID);
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
		contact.setIcqSn(icqSn);
		contact.setJabberSn(jabberSn);
		contact.setMsnSn(msnSn);
		contact.setSkypeSn(skypeSn);
		contact.setYmSn(ymSn);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		UserUtil.clearOrganizations(userId);

		if (organizationId  > 0) {
			UserUtil.addOrganization(userId, organizationId);
		}

		if (locationId  > 0) {
			UserUtil.addOrganization(userId, locationId);
		}

		return user;
	}

	protected int authenticate(
			long companyId, String login, String password, String authType,
			Map headerMap, Map parameterMap)
		throws PortalException, SystemException {

		login = login.trim().toLowerCase();

		long userId = GetterUtil.getLong(login);

		if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
			if (!Validator.isEmailAddress(login)) {
				throw new UserEmailAddressException();
			}
		}
		else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
			if (Validator.isNull(login)) {
				throw new UserScreenNameException();
			}
		}
		else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
			if (Validator.isNull(login)) {
				throw new UserIdException();
			}
		}

		if (Validator.isNull(password)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}

		int authResult = Authenticator.FAILURE;

		String[] authPipelinePre =
			PropsUtil.getArray(PropsUtil.AUTH_PIPELINE_PRE);

		if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
			authResult = AuthPipeline.authenticateByEmailAddress(
				authPipelinePre, companyId, login, password, headerMap,
				parameterMap);
		}
		else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
			authResult = AuthPipeline.authenticateByScreenName(
				authPipelinePre, companyId, login, password, headerMap,
				parameterMap);
		}
		else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
			authResult = AuthPipeline.authenticateByUserId(
				authPipelinePre, companyId, userId, password, headerMap,
				parameterMap);
		}

		User user = null;

		try {
			if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
				user = UserUtil.findByC_EA(companyId, login);
			}
			else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
				user = UserUtil.findByC_SN(companyId, login);
			}
			else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
				user = UserUtil.findByC_U(companyId, GetterUtil.getLong(login));
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

		// Check user account lockout

		checkLockout(user);

		if (!user.isPasswordEncrypted()) {
			user.setPassword(PwdEncryptor.encrypt(user.getPassword()));
			user.setPasswordEncrypted(true);

			UserUtil.update(user);
		}

		// Check password expiration

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if (isPasswordExpired(user)) {
			int graceLoginCount = user.getGraceLoginCount();

			if (graceLoginCount < passwordPolicy.getGraceLimit()) {
				user.setGraceLoginCount(++graceLoginCount);

				UserUtil.update(user);
			}
			else {
				user.setPasswordReset(true);

				UserUtil.update(user);
			}
		}

		if (passwordPolicy.getChangeable() &&
			passwordPolicy.getChangeRequired()) {

			// Force user to change password on first login

			if (user.getLastLoginDate() == null) {
				boolean passwordReset = false;

				if (passwordPolicy.getChangeable() &&
					passwordPolicy.getChangeRequired()) {

					passwordReset = true;
				}

				user.setPasswordReset(passwordReset);

				UserUtil.update(user);
			}
		}

		if (authResult == Authenticator.SUCCESS) {
			if (GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK))) {

				String encPwd = PwdEncryptor.encrypt(password);

				if (user.getPassword().equals(encPwd)) {
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

						encPwd = Base64.encode(
							digester.digest(shardKey.getBytes("UTF8")));

						if (password.equals(encPwd)) {
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

		if (authResult == Authenticator.SUCCESS) {
			String[] authPipelinePost =
				PropsUtil.getArray(PropsUtil.AUTH_PIPELINE_POST);

			if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
				authResult = AuthPipeline.authenticateByEmailAddress(
					authPipelinePost, companyId, login, password, headerMap,
					parameterMap);
			}
			else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
				authResult = AuthPipeline.authenticateByScreenName(
					authPipelinePost, companyId, login, password, headerMap,
					parameterMap);
			}
			else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
				authResult = AuthPipeline.authenticateByUserId(
					authPipelinePost, companyId, userId, password, headerMap,
					parameterMap);
			}
		}

		if (authResult == Authenticator.FAILURE) {
			try {
				String[] authFailure =
					PropsUtil.getArray(PropsUtil.AUTH_FAILURE);

				if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
					AuthPipeline.onFailureByEmailAddress(
						authFailure, companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
					AuthPipeline.onFailureByScreenName(
						authFailure, companyId, login, headerMap, parameterMap);
				}
				else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
					AuthPipeline.onFailureByUserId(
						authFailure, companyId, userId, headerMap,
						parameterMap);
				}

				int failedLoginAttempts = user.getFailedLoginAttempts();
				int maxFailures = passwordPolicy.getMaxFailure();

				if ((failedLoginAttempts >= maxFailures) &&
					(maxFailures != 0)) {

					String[] authMaxFailures =
						PropsUtil.getArray(PropsUtil.AUTH_MAX_FAILURES);

					if (authType.equals(CompanyImpl.AUTH_TYPE_EA)) {
						AuthPipeline.onMaxFailuresByEmailAddress(
							authMaxFailures, companyId, login, headerMap,
							parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_SN)) {
						AuthPipeline.onMaxFailuresByScreenName(
							authMaxFailures, companyId, login, headerMap,
							parameterMap);
					}
					else if (authType.equals(CompanyImpl.AUTH_TYPE_ID)) {
						AuthPipeline.onMaxFailuresByUserId(
							authMaxFailures, companyId, userId, headerMap,
							parameterMap);
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return authResult;
	}

	protected void sendEmail(User user, String password)
		throws PortalException, SystemException {

		if (!PrefsPropsUtil.getBoolean(
				user.getCompanyId(),
				PropsUtil.ADMIN_EMAIL_USER_ADDED_ENABLED)) {

			return;
		}

		try {
			long companyId = user.getCompanyId();

			Company company = CompanyUtil.findByPrimaryKey(companyId);

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
					"[$USER_PASSWORD$]"
				},
				new String[] {
					fromAddress,
					fromName,
					company.getPortalURL(),
					toAddress,
					toName,
					String.valueOf(user.getUserId()),
					password
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
					"[$USER_PASSWORD$]"
				},
				new String[] {
					fromAddress,
					fromName,
					company.getPortalURL(),
					toAddress,
					toName,
					String.valueOf(user.getUserId()),
					password
				});

			InternetAddress from = new InternetAddress(fromAddress, fromName);

			InternetAddress to = new InternetAddress(toAddress, toName);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void validate(
			long userId, String screenName, String emailAddress,
			String firstName, String lastName, String smsSn)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		if (!user.getScreenName().equals(screenName)) {
			validateScreenName(user.getCompanyId(), screenName);
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new UserEmailAddressException();
		}
		else if (!user.isDefaultUser()) {
			try {
				if (!user.getEmailAddress().equals(emailAddress)) {
					if (UserUtil.findByC_EA(
							user.getCompanyId(), emailAddress) != null) {

						throw new DuplicateUserEmailAddressException();
					}
				}
			}
			catch (NoSuchUserException nsue) {
			}

			String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
				user.getCompanyId(), PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES);

			for (int i = 0; i < reservedEmailAddresses.length; i++) {
				if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
					throw new ReservedUserEmailAddressException();
				}
			}
		}

		if (!user.isDefaultUser()) {
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
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, String firstName, String lastName,
			long organizationId, long locationId)
		throws PortalException, SystemException {

		if (!autoScreenName) {
			validateScreenName(companyId, screenName);
		}

		if (!autoPassword) {
			PasswordPolicy passwordPolicy =
				PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(
					companyId);

			PwdToolkitUtil.validate(0, password1, password2, passwordPolicy);
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new UserEmailAddressException();
		}
		else {
			try {
				User user = UserUtil.findByC_EA(companyId, emailAddress);

				if (user != null) {
					throw new DuplicateUserEmailAddressException();
				}
			}
			catch (NoSuchUserException nsue) {
			}

			String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
				companyId, PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES);

			for (int i = 0; i < reservedEmailAddresses.length; i++) {
				if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
					throw new ReservedUserEmailAddressException();
				}
			}
		}

		if (Validator.isNull(firstName)) {
			throw new ContactFirstNameException();
		}
		else if (Validator.isNull(lastName)) {
			throw new ContactLastNameException();
		}
	}

	protected void validateOrganizations(
			long companyId, long organizationId, long locationId)
		throws PortalException, SystemException {

		boolean organizationRequired = GetterUtil.getBoolean(PropsUtil.get(
			PropsUtil.ORGANIZATIONS_PARENT_ORGANIZATION_REQUIRED));

		boolean locationRequired = GetterUtil.getBoolean(PropsUtil.get(
			PropsUtil.ORGANIZATIONS_LOCATION_REQUIRED));

		if (locationRequired) {
			organizationRequired = true;
		}

		Organization organization = null;

		if (organizationRequired || (organizationId  > 0)) {
			organization = OrganizationUtil.findByPrimaryKey(organizationId);
		}

		Organization location = null;

		if (locationRequired || (locationId > 0)) {
			location = OrganizationUtil.findByPrimaryKey(locationId);
		}

		if ((organization != null) && (location != null)) {
			if (location.getParentOrganizationId() !=
					organization.getOrganizationId()) {

				throw new OrganizationParentException();
			}
		}
	}

	protected void validatePassword(
			long userId, String password1, String password2)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.getPasswordPolicyByUserId(
				userId);

		PwdToolkitUtil.validate(userId, password1, password2, passwordPolicy);
	}

	protected void validateScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		if (Validator.isNull(screenName)) {
			throw new UserScreenNameException();
		}

		ScreenNameValidator screenNameValidator =
			(ScreenNameValidator)InstancePool.get(
				PropsUtil.get(PropsUtil.USERS_SCREEN_NAME_VALIDATOR));

		if (screenNameValidator != null) {
			if (!screenNameValidator.validate(companyId, screenName)) {
				throw new UserScreenNameException();
			}
		}

		String[] anonymousNames = PrincipalSessionBean.ANONYMOUS_NAMES;

		for (int i = 0; i < anonymousNames.length; i++) {
			if (screenName.equalsIgnoreCase(anonymousNames[i])) {
				throw new UserScreenNameException();
			}
		}

		User user = UserUtil.fetchByC_SN(companyId, screenName);

		if (user != null) {
			throw new DuplicateUserScreenNameException();
		}

		String friendlyURL = StringPool.SLASH + screenName;

		Group group = GroupUtil.fetchByC_F(companyId, friendlyURL);

		if (group != null) {
			throw new DuplicateUserScreenNameException();
		}

		String[] reservedScreenNames = PrefsPropsUtil.getStringArray(
			companyId, PropsUtil.ADMIN_RESERVED_SCREEN_NAMES);

		for (int i = 0; i < reservedScreenNames.length; i++) {
			if (screenName.equalsIgnoreCase(reservedScreenNames[i])) {
				throw new ReservedUserScreenNameException();
			}
		}
	}

	private static Log _log = LogFactory.getLog(UserLocalServiceImpl.class);

}