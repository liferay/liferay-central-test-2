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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.mail.service.spring.MailServiceUtil;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserIdException;
import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.ReservedUserIdException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserIdValidator;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserPortraitException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalFinder;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.ContactUtil;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.PermissionUserFinder;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ContactLocalServiceUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.UserIdMapperLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalService;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.addressbook.service.spring.ABContactLocalServiceUtil;
import com.liferay.portlet.addressbook.service.spring.ABListLocalServiceUtil;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFileRankLocalServiceUtil;
import com.liferay.portlet.enterpriseadmin.search.UserSearchTerms;
import com.liferay.portlet.messageboards.service.spring.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.shopping.service.spring.ShoppingCartLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.KeyValuePair;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.mail.MailMessage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserLocalServiceImpl implements UserLocalService {

	public boolean addGroupUsers(String groupId, String[] userIds)
		throws PortalException, SystemException {

		return GroupUtil.addUsers(groupId, userIds);
	}

	public boolean addRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		return RoleUtil.addUsers(roleId, userIds);
	}

	public User addUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId)
		throws PortalException, SystemException {

		return addUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, true);
	}

	public User addUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail)
		throws PortalException, SystemException {

		// User

		userId = userId.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		boolean alwaysAutoUserId = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.USERS_ID_ALWAYS_AUTOGENERATE));

		if (alwaysAutoUserId) {
			autoUserId = true;
		}

		validate(
			companyId, autoUserId, userId, autoPassword, password1, password2,
			emailAddress, firstName, lastName, organizationId, locationId);

		validateOrganizations(companyId, organizationId, locationId);

		if (autoUserId) {
			userId =
				companyId + "." +
				Long.toString(CounterServiceUtil.increment(
					User.class.getName() + "." + companyId));
		}

		if (autoPassword) {
			password1 = PwdToolkitUtil.generate();
		}

		int passwordsLifespan = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.PASSWORDS_LIFESPAN));

		Date expirationDate = null;

		if (passwordsLifespan > 0) {
			expirationDate = new Date(
				System.currentTimeMillis() + Time.DAY * passwordsLifespan);
		}

		User defaultUser = getDefaultUser(companyId);

		String fullName = User.getFullName(firstName, middleName, lastName);

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
		user.setPassword(PwdEncryptor.encrypt(password1));
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordExpirationDate(expirationDate);
		user.setPasswordReset(passwordReset);
		user.setEmailAddress(emailAddress);
		user.setLanguageId(locale.toString());
		user.setTimeZoneId(defaultUser.getTimeZoneId());
		user.setGreeting(greeting);
		user.setResolution(defaultUser.getResolution());
		user.setActive(true);

		UserUtil.update(user);

		// Resources

		if (creatorUserId == null) {
			creatorUserId = user.getUserId();
		}

		ResourceLocalServiceUtil.addResources(
			companyId, null, creatorUserId, User.class.getName(),
			user.getPrimaryKey().toString(), false, false, false);

		// Mail

		if (user.hasCompanyMx()) {
			MailServiceUtil.addUser(
				userId, password1, firstName, middleName, lastName,
				emailAddress);
		}

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		String contactId = userId;

		Contact contact = ContactUtil.create(contactId);

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(user.getUserId());
		contact.setUserName(User.getFullName(firstName, middleName, lastName));
		contact.setCreateDate(now);
		contact.setModifiedDate(now);
		contact.setAccountId(user.getCompanyId());
		contact.setParentContactId(Contact.DEFAULT_PARENT_CONTACT_ID);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setNickName(nickName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		if (Validator.isNotNull(organizationId) &&
			Validator.isNotNull(locationId)) {

			UserUtil.setOrganizations(
				userId, new String[] {organizationId, locationId});
		}

		// Group

		GroupLocalServiceUtil.addGroup(
			user.getUserId(), User.class.getName(),
			user.getPrimaryKey().toString(), null, null);

		// Default groups

		List groups = new ArrayList();

		String[] defaultGroupNames = AdminUtil.getDefaultGroupNames(companyId);

		for (int i = 0; i < defaultGroupNames.length; i++) {
			try {
				Group group = GroupFinder.findByC_N_2(
					companyId, defaultGroupNames[i]);

				groups.add(group);
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		UserUtil.setGroups(userId, groups);

		// Default roles

		List roles = new ArrayList();

		String[] defaultRoleNames = AdminUtil.getDefaultRoleNames(companyId);

		for (int i = 0; i < defaultRoleNames.length; i++) {
			try {
				Role role = RoleFinder.findByC_N_2(
					companyId, defaultRoleNames[i]);

				roles.add(role);
			}
			catch (NoSuchRoleException nsge) {
			}
		}

		UserUtil.setRoles(userId, roles);

		// Email

		if (sendEmail) {
			sendEmail(user, password1);
		}

		return user;
	}

	public int authenticateByEmailAddress(
			String companyId, String emailAddress, String password)
		throws PortalException, SystemException {

		return authenticate(companyId, emailAddress, password, true);
	}

	public int authenticateByUserId(
			String companyId, String userId, String password)
		throws PortalException, SystemException {

		return authenticate(companyId, userId, password, false);
	}

	public boolean authenticateForJAAS(String userId, String encPwd)
		throws PortalException, SystemException {

		try {
			userId = userId.trim().toLowerCase();

			User user = UserUtil.findByPrimaryKey(userId);

			if (user.getPassword().equals(encPwd)) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return false;
	}

	public KeyValuePair decryptUserId(
			String companyId, String userId, String password)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		try {
			userId = Encryptor.decrypt(company.getKeyObj(), userId);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		String liferayUserId = userId;

		try {
			PrincipalFinder principalFinder = (PrincipalFinder)InstancePool.get(
				PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));

			liferayUserId = principalFinder.toLiferay(userId);
		}
		catch (Exception e) {
		}

		User user = UserUtil.findByPrimaryKey(liferayUserId);

		try {
			password = Encryptor.decrypt(company.getKeyObj(), password);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		String encPwd = PwdEncryptor.encrypt(password);

		if (user.getPassword().equals(encPwd)) {
			if (user.isPasswordExpired()) {
				user.setPasswordReset(true);

				UserUtil.update(user);
			}

			return new KeyValuePair(userId, password);
		}
		else {
			throw new PrincipalException();
		}
	}

	public boolean deleteRoleUser(String roleId, String userId)
		throws PortalException, SystemException {

		return RoleUtil.removeUser(roleId, userId);
	}

	public void deleteUser(String userId)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.USERS_DELETE))) {
			throw new RequiredUserException();
		}

		User user = UserUtil.findByPrimaryKey(userId);

		// Group

		Group group = user.getGroup();

		if (group != null) {
			GroupLocalServiceUtil.deleteGroup(group.getGroupId());
		}

		// Portrait

		ImageLocalUtil.remove(userId);

		// Portlet preferences

		PortletPreferencesLocalServiceUtil.deleteAllByUser(userId);

		// Old passwords

		PasswordTrackerLocalServiceUtil.deletePasswordTrackers(userId);

		// External user ids

		UserIdMapperLocalServiceUtil.deleteUserIdMappers(userId);

		// Address book

		ABContactLocalServiceUtil.deleteAll(userId);
		ABListLocalServiceUtil.deleteAll(userId);

		// Document library

		DLFileRankLocalServiceUtil.deleteFileRanks(userId);

		// Message boards

		MBMessageFlagLocalServiceUtil.deleteFlags(userId);

		// Shopping cart

		ShoppingCartLocalServiceUtil.deleteUserCarts(userId);

		// Mail

		MailServiceUtil.deleteUser(userId);

		// Contact

		ContactLocalServiceUtil.deleteContact(userId);

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			user.getCompanyId(), User.class.getName(), Resource.TYPE_CLASS,
			Resource.SCOPE_INDIVIDUAL, user.getPrimaryKey().toString());

		// User

		UserUtil.remove(userId);
	}

	public String encryptUserId(String userId)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		String liferayUserId = userId;

		try {
			PrincipalFinder principalFinder = (PrincipalFinder)InstancePool.get(
				PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));

			liferayUserId = principalFinder.toLiferay(userId);
		}
		catch (Exception e) {
		}

		User user = UserUtil.findByPrimaryKey(liferayUserId);

		Company company = CompanyUtil.findByPrimaryKey(user.getCompanyId());

		try {
			return Encryptor.encrypt(company.getKeyObj(), userId);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}
	}

	public User getDefaultUser(String companyId)
		throws PortalException, SystemException {

		return UserUtil.findByPrimaryKey(User.getDefaultUserId(companyId));
	}

	public List getGroupUsers(String groupId)
		throws PortalException, SystemException {

		return GroupUtil.getUsers(groupId);
	}

	public List getPermissionUsers(
			String companyId, String groupId, String name, String primKey,
			String actionId, UserSearchTerms searchTerms, int begin, int end)
		throws PortalException, SystemException {

		int orgGroupPermissionsCount =
			PermissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return PermissionUserFinder.findByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, searchTerms, begin, end);
		}
		else {
			return PermissionUserFinder.findByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, searchTerms, begin,
				end);
		}
	}

	public int getPermissionUsersCount(
			String companyId, String groupId, String name, String primKey,
			String actionId, UserSearchTerms searchTerms)
		throws PortalException, SystemException {

		int orgGroupPermissionsCount =
			PermissionUserFinder.countByOrgGroupPermissions(
				companyId, name, primKey, actionId);

		if (orgGroupPermissionsCount > 0) {
			return PermissionUserFinder.countByUserAndOrgGroupPermission(
				companyId, name, primKey, actionId, searchTerms);
		}
		else {
			return PermissionUserFinder.countByPermissionAndRole(
				companyId, groupId, name, primKey, actionId, searchTerms);
		}
	}

	public List getRoleUsers(String roleId)
		throws PortalException, SystemException {

		return RoleUtil.getUsers(roleId);
	}

	public User getUserByEmailAddress(
			String companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		return UserUtil.findByC_EA(companyId, emailAddress);
	}

	public User getUserById(String userId)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		return UserUtil.findByPrimaryKey(userId);
	}

	public User getUserById(String companyId, String userId)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		return UserUtil.findByC_U(companyId, userId);
	}

	public String getUserId(String companyId, String emailAddress)
		throws PortalException, SystemException {

		emailAddress = emailAddress.trim().toLowerCase();

		User user = UserUtil.findByC_EA(companyId, emailAddress);

		return user.getUserId();
	}

	public boolean hasGroupUser(String groupId, String userId)
		throws PortalException, SystemException {

		return GroupUtil.containsUser(groupId, userId);
	}

	public boolean hasRoleUser(String roleId, String userId)
		throws PortalException, SystemException {

		return RoleUtil.containsUser(roleId, userId);
	}

	public List search(
			String companyId, String firstName, String middleName,
			String lastName, String emailAddress, boolean active, Map params,
			boolean andSearch, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return UserFinder.findByC_FN_MN_LN_EA_A(
			companyId, firstName, middleName, lastName, emailAddress, active,
			params, andSearch, begin, end, obc);
	}

	public int searchCount(
			String companyId, String firstName, String middleName,
			String lastName, String emailAddress, boolean active, Map params,
			boolean andSearch)
		throws SystemException {

		return UserFinder.countByC_FN_MN_LN_EA_A(
			companyId, firstName, middleName, lastName, emailAddress, active,
			params, andSearch);
	}

	public void sendPassword(
			String companyId, String emailAddress, String remoteAddr,
			String remoteHost, String userAgent)
		throws PortalException, SystemException {

		if (!AdminUtil.getEmailPasswordSentEnabled(companyId)) {
			return;
		}

		emailAddress = emailAddress.trim().toLowerCase();

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new UserEmailAddressException();
		}

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		User user = UserUtil.findByC_EA(companyId, emailAddress);

		/*if (user.hasCompanyMx()) {
			throw new SendPasswordException();
		}*/

		if (PwdEncryptor.PASSWORDS_ENCRYPTED) {
			user.setPassword(PwdToolkitUtil.generate());
			user.setPasswordEncrypted(false);
			user.setPasswordReset(GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PASSWORDS_CHANGE_ON_FIRST_USE)));

			UserUtil.update(user);
		}

		try {
			String fromName = AdminUtil.getEmailFromName(companyId);
			String fromAddress = AdminUtil.getEmailFromAddress(companyId);

			String toName = user.getFullName();
			String toAddress = user.getEmailAddress();

			String subject = AdminUtil.getEmailPasswordSentSubject(companyId);
			String body = AdminUtil.getEmailPasswordSentBody(companyId);

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
					user.getUserId(),
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
					user.getUserId(),
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

	public void setGroupUsers(String groupId, String[] userIds)
		throws PortalException, SystemException {

		GroupUtil.setUsers(groupId, userIds);
	}

	public void setRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		RoleUtil.setUsers(roleId, userIds);
	}

	public boolean unsetGroupUsers(String groupId, String[] userIds)
		throws PortalException, SystemException {

		return GroupUtil.removeUsers(groupId, userIds);
	}

	public boolean unsetRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		return RoleUtil.removeUsers(roleId, userIds);
	}

	public User updateActive(String userId, boolean active)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		User user = UserUtil.findByPrimaryKey(userId);

		user.setActive(active);

		UserUtil.update(user);

		return user;
	}

	public User updateAgreedToTermsOfUse(
			String userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		user.setAgreedToTermsOfUse(agreedToTermsOfUse);

		UserUtil.update(user);

		return user;
	}

	public User updateLastLogin(String userId, String loginIP)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		user.setLastLoginDate(user.getLoginDate());
		user.setLastLoginIP(user.getLoginIP());
		user.setLoginDate(new Date());
		user.setLoginIP(loginIP);
		user.setFailedLoginAttempts(0);

		UserUtil.update(user);

		return user;
	}

	public User updatePassword(
			String userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		validatePassword(userId, password1, password2);

		User user = UserUtil.findByPrimaryKey(userId);

		String oldEncPwd = user.getPassword();

		if (!user.isPasswordEncrypted()) {
			oldEncPwd = PwdEncryptor.encrypt(user.getPassword());
		}

		String newEncPwd = PwdEncryptor.encrypt(password1);

		int passwordsLifespan = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.PASSWORDS_LIFESPAN));

		Date expirationDate = null;

		if (passwordsLifespan > 0) {
			expirationDate = new Date(
				System.currentTimeMillis() + Time.DAY * passwordsLifespan);
		}

		if (user.hasCompanyMx()) {
			MailServiceUtil.updatePassword(userId, password1);
		}

		user.setPassword(newEncPwd);
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordExpirationDate(expirationDate);
		user.setPasswordReset(passwordReset);

		UserUtil.update(user);

		PasswordTrackerLocalServiceUtil.trackPassword(userId, oldEncPwd);

		return user;
	}

	public void updatePortrait(String userId, byte[] bytes)
		throws PortalException, SystemException {

		userId = userId.trim().toLowerCase();

		long imageMaxSize = GetterUtil.getLong(
			PropsUtil.get(PropsUtil.USERS_IMAGE_MAX_SIZE));

		if ((imageMaxSize > 0) &&
			((bytes == null) || (bytes.length > imageMaxSize))) {

			throw new UserPortraitException();
		}

		ImageLocalUtil.put(userId, bytes);
	}

	public User updateUser(
			String userId, String password, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String firstName,
			String middleName, String lastName, String nickName,
			String prefixId, String suffixId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, String smsSn, String aimSn,
			String icqSn, String msnSn, String skypeSn, String ymSn,
			String jobTitle, String organizationId, String locationId)
		throws PortalException, SystemException {

		// User

		userId = userId.trim().toLowerCase();
		emailAddress = emailAddress.trim().toLowerCase();
		Date now = new Date();

		validate(userId, emailAddress, firstName, lastName, smsSn);

		User user = UserUtil.findByPrimaryKey(userId);

		validateOrganizations(user.getCompanyId(), organizationId, locationId);

		user.setModifiedDate(now);

		if (!emailAddress.equals(user.getEmailAddress())) {

			// test@test.com -> test@liferay.com

			if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
				MailServiceUtil.addUser(
					userId, password, firstName, middleName, lastName,
					emailAddress);
			}

			// test@liferay.com -> bob@liferay.com

			else if (user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
				MailServiceUtil.updateEmailAddress(userId, emailAddress);
			}

			// test@liferay.com -> test@test.com

			else if (user.hasCompanyMx() && !user.hasCompanyMx(emailAddress)) {
				MailServiceUtil.deleteEmailAddress(userId);
			}

			user.setEmailAddress(emailAddress);
		}

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);
		user.setGreeting(greeting);
		user.setResolution(resolution);
		user.setComments(comments);

		UserUtil.update(user);

		// Contact

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			new ContactBirthdayException());

		String contactId = userId;

		Contact contact = null;

		try {
			contact = ContactUtil.findByPrimaryKey(contactId);
		}
		catch (NoSuchContactException nsce) {
			contact = ContactUtil.create(contactId);

			contact.setCompanyId(user.getCompanyId());
			contact.setUserId(StringPool.BLANK);
			contact.setUserName(StringPool.BLANK);
			contact.setCreateDate(now);
			contact.setAccountId(user.getCompanyId());
			contact.setParentContactId(Contact.DEFAULT_PARENT_CONTACT_ID);
		}

		contact.setModifiedDate(now);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setNickName(nickName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setAimSn(aimSn);
		contact.setIcqSn(icqSn);
		contact.setMsnSn(msnSn);
		contact.setSkypeSn(skypeSn);
		contact.setYmSn(ymSn);
		contact.setJobTitle(jobTitle);

		ContactUtil.update(contact);

		// Organization and location

		if (Validator.isNotNull(organizationId) &&
			Validator.isNotNull(locationId)) {

			UserUtil.setOrganizations(
				userId, new String[] {organizationId, locationId});
		}

		return user;
	}

	protected int authenticate(
			String companyId, String login, String password,
			boolean byEmailAddress)
		throws PortalException, SystemException {

		login = login.trim().toLowerCase();

		if (byEmailAddress) {
			if (!Validator.isEmailAddress(login)) {
				throw new UserEmailAddressException();
			}
		}
		else {
			if (Validator.isNull(login)) {
				throw new UserIdException();
			}
		}

		if (Validator.isNull(password)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}

		int authResult = Authenticator.FAILURE;

		if (byEmailAddress) {
			authResult = AuthPipeline.authenticateByEmailAddress(
				PropsUtil.getArray(
					PropsUtil.AUTH_PIPELINE_PRE), companyId, login, password);
		}
		else {
			authResult = AuthPipeline.authenticateByUserId(
				PropsUtil.getArray(
					PropsUtil.AUTH_PIPELINE_PRE), companyId, login, password);
		}

		User user = null;

		try {
			if (byEmailAddress) {
				user = UserUtil.findByC_EA(companyId, login);
			}
			else {
				user = UserUtil.findByC_U(companyId, login);
			}
		}
		catch (NoSuchUserException nsue) {
			return Authenticator.DNE;
		}

		if (!user.isPasswordEncrypted()) {
			user.setPassword(PwdEncryptor.encrypt(user.getPassword()));
			user.setPasswordEncrypted(true);
			user.setPasswordReset(GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PASSWORDS_CHANGE_ON_FIRST_USE)));

			UserUtil.update(user);
		}
		else if (user.isPasswordExpired()) {
			user.setPasswordReset(true);

			UserUtil.update(user);
		}

		if (authResult == Authenticator.SUCCESS) {
			if (GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK))) {

				String encPwd = PwdEncryptor.encrypt(password);

				if (user.getPassword().equals(encPwd)) {
					authResult = Authenticator.SUCCESS;
				}
				else {
					authResult = Authenticator.FAILURE;
				}
			}
		}

		if (authResult == Authenticator.SUCCESS) {
			if (byEmailAddress) {
				authResult = AuthPipeline.authenticateByEmailAddress(
					PropsUtil.getArray(
						PropsUtil.AUTH_PIPELINE_POST), companyId, login,
						password);
			}
			else {
				authResult = AuthPipeline.authenticateByUserId(
					PropsUtil.getArray(
						PropsUtil.AUTH_PIPELINE_POST), companyId, login,
						password);
			}
		}

		if (authResult == Authenticator.FAILURE) {
			try {
				if (byEmailAddress) {
					AuthPipeline.onFailureByEmailAddress(PropsUtil.getArray(
						PropsUtil.AUTH_FAILURE), companyId, login);
				}
				else {
					AuthPipeline.onFailureByUserId(PropsUtil.getArray(
						PropsUtil.AUTH_FAILURE), companyId, login);
				}

				int failedLoginAttempts = user.getFailedLoginAttempts();

				user.setFailedLoginAttempts(++failedLoginAttempts);

				UserUtil.update(user);

				int maxFailures = GetterUtil.get(PropsUtil.get(
					PropsUtil.AUTH_MAX_FAILURES_LIMIT), 0);

				if ((failedLoginAttempts >= maxFailures) &&
					(maxFailures != 0)) {

					if (byEmailAddress) {
						AuthPipeline.onMaxFailuresByEmailAddress(
							PropsUtil.getArray(
								PropsUtil.AUTH_MAX_FAILURES), companyId, login);
					}
					else {
						AuthPipeline.onMaxFailuresByUserId(
							PropsUtil.getArray(
								PropsUtil.AUTH_MAX_FAILURES), companyId, login);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return authResult;
	}

	protected void sendEmail(User user, String password)
		throws PortalException, SystemException {

		if (!AdminUtil.getEmailUserAddedEnabled(user.getCompanyId())) {
			return;
		}

		try {
			String companyId = user.getCompanyId();

			Company company = CompanyUtil.findByPrimaryKey(companyId);

			String fromName = AdminUtil.getEmailFromName(companyId);
			String fromAddress = AdminUtil.getEmailFromAddress(companyId);

			String toName = user.getFullName();
			String toAddress = user.getEmailAddress();

			String subject = AdminUtil.getEmailUserAddedSubject(companyId);
			String body = AdminUtil.getEmailUserAddedBody(companyId);

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
					user.getUserId(),
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
					user.getUserId(),
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
			String userId, String emailAddress, String firstName,
			String lastName, String smsSn)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new UserEmailAddressException();
		}
		else if (!User.isDefaultUser(userId)) {
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

			String[] reservedEmailAddresses =
				AdminUtil.getReservedEmailAddresses(user.getCompanyId());

			for (int i = 0; i < reservedEmailAddresses.length; i++) {
				if (emailAddress.equalsIgnoreCase(reservedEmailAddresses[i])) {
					throw new ReservedUserEmailAddressException();
				}
			}
		}

		if (!User.isDefaultUser(userId)) {
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
			String companyId, boolean autoUserId, String userId,
			boolean autoPassword, String password1, String password2,
			String emailAddress, String firstName, String lastName,
			String organizationId, String locationId)
		throws PortalException, SystemException {

		if (!autoUserId) {
			if (Validator.isNull(userId)) {
				throw new UserIdException();
			}

			UserIdValidator userIdValidator = (UserIdValidator)InstancePool.get(
				PropsUtil.get(PropsUtil.USERS_ID_VALIDATOR));

			if (userIdValidator != null) {
				if (!userIdValidator.validate(userId, companyId)) {
					throw new UserIdException();
				}
			}

			String[] anonymousNames = PrincipalSessionBean.ANONYMOUS_NAMES;

			for (int i = 0; i < anonymousNames.length; i++) {
				if (userId.equalsIgnoreCase(anonymousNames[i])) {
					throw new UserIdException();
				}
			}

			String[] companyIds = PortalInstances.getCompanyIds();

			for (int i = 0; i < companyIds.length; i++) {
				if (userId.indexOf(companyIds[i]) != -1) {
					throw new UserIdException();
				}
			}

			try {
				User user = UserUtil.findByPrimaryKey(userId);

				if (user != null) {
					throw new DuplicateUserIdException();
				}
			}
			catch (NoSuchUserException nsue) {
			}

			String[] reservedUserIds =
				AdminUtil.getReservedUserIds(companyId);

			for (int i = 0; i < reservedUserIds.length; i++) {
				if (userId.equalsIgnoreCase(reservedUserIds[i])) {
					throw new ReservedUserIdException();
				}
			}
		}

		if (!autoPassword) {
			if (!password1.equals(password2)) {
				throw new UserPasswordException(
					UserPasswordException.PASSWORDS_DO_NOT_MATCH);
			}
			else if (!PwdToolkitUtil.validate(password1) ||
					 !PwdToolkitUtil.validate(password2)) {

				throw new UserPasswordException(
					UserPasswordException.PASSWORD_INVALID);
			}
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

			String[] reservedEmailAddresses =
				AdminUtil.getReservedEmailAddresses(companyId);

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
			String companyId, String organizationId, String locationId)
		throws PortalException, SystemException {

		if (Validator.isNull(organizationId) && Validator.isNull(locationId) &&
			!GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.ORGANIZATIONS_REQUIRED))) {

			return;
		}

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		Organization location =
			OrganizationUtil.findByPrimaryKey(locationId);

		if (!location.getParentOrganizationId().equals(
				organization.getOrganizationId())) {

			throw new OrganizationParentException();
		}
	}

	protected void validatePassword(
			String userId, String password1, String password2)
		throws PortalException, SystemException {

		if (!password1.equals(password2)) {
			throw new UserPasswordException(
				UserPasswordException.PASSWORDS_DO_NOT_MATCH);
		}
		else if (!PwdToolkitUtil.validate(password1) ||
				 !PwdToolkitUtil.validate(password2)) {

			throw new UserPasswordException(
				UserPasswordException.PASSWORD_INVALID);
		}
		else if (!PasswordTrackerLocalServiceUtil.isValidPassword(
					userId, password1)) {

			throw new UserPasswordException(
				UserPasswordException.PASSWORD_ALREADY_USED);
		}
	}

	private static Log _log = LogFactory.getLog(UserLocalServiceImpl.class);

}