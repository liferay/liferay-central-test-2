/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Scott Lee
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UserServiceImpl extends UserServiceBaseImpl {

	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
		}
		catch (PrincipalException pe) {

			// Allow any user to join open communities

			boolean hasPermission = false;

			if (userIds.length == 0) {
				hasPermission = true;
			}
			else if (userIds.length == 1) {
				User user = getUser();

				if (user.getUserId() == userIds[0]) {
					Group group = groupPersistence.findByPrimaryKey(groupId);

					if (user.getCompanyId() == group.getCompanyId()) {
						int type = group.getType();

						if (type == GroupConstants.TYPE_COMMUNITY_OPEN) {
							hasPermission = true;
						}
					}
				}
			}

			if (!hasPermission) {
				throw new PrincipalException();
			}
		}

		userLocalService.addGroupUsers(groupId, userIds);
	}

	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.ASSIGN_MEMBERS);

		validateOrganizationUsers(userIds);

		userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId,
			ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addRoleUsers(roleId, userIds);
	}

	public void addTeamUsers(long teamId, long[] userIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addTeamUsers(teamId, userIds);
	}

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	public User addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		long creatorUserId = 0;

		try {
			creatorUserId = getUserId();
		}
		catch (PrincipalException pe) {
		}

		if ((creatorUserId != 0) || !company.isStrangers()) {
			if (!PortalPermissionUtil.contains(
					getPermissionChecker(), ActionKeys.ADD_USER) &&
				!UserPermissionUtil.contains(
					getPermissionChecker(), 0, organizationIds,
					ActionKeys.ADD_USER)) {

				throw new PrincipalException();
			}
		}

		if (creatorUserId == 0) {
			if (!company.isStrangersWithMx() &&
				company.hasCompanyMx(emailAddress)) {

				throw new ReservedUserEmailAddressException();
			}
		}

		return userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	public User addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail, List<Address> addresses,
			List<EmailAddress> emailAddresses, List<Phone> phones,
			List<Website> websites,
			List<AnnouncementsDelivery> announcementsDelivers,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = addUser(
			companyId, autoPassword, password1, password2, autoScreenName,
			screenName, emailAddress, facebookId, openId, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		EnterpriseAdminUtil.updateAddresses(
			Contact.class.getName(), user.getContactId(), addresses);

		EnterpriseAdminUtil.updateEmailAddresses(
			Contact.class.getName(), user.getContactId(), emailAddresses);

		EnterpriseAdminUtil.updatePhones(
			Contact.class.getName(), user.getContactId(), phones);

		EnterpriseAdminUtil.updateWebsites(
			Contact.class.getName(), user.getContactId(), websites);

		updateAnnouncementsDeliveries(user.getUserId(), announcementsDelivers);

		return user;
	}

	public void deletePortrait(long userId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.deletePortrait(userId);
	}

	public void deleteRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.deleteRoleUser(roleId, userId);
	}

	public void deleteUser(long userId)
		throws PortalException, SystemException {

		if (getUserId() == userId) {
			throw new RequiredUserException();
		}

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		userLocalService.deleteUser(userId);
	}

	public long getDefaultUserId(long companyId)
		throws PortalException, SystemException {

		return userLocalService.getDefaultUserId(companyId);
	}

	public long[] getGroupUserIds(long groupId) throws SystemException {
		return userLocalService.getGroupUserIds(groupId);
	}

	public long[] getOrganizationUserIds(long organizationId)
		throws SystemException {

		return userLocalService.getOrganizationUserIds(organizationId);
	}

	public long[] getRoleUserIds(long roleId) throws SystemException {
		return userLocalService.getRoleUserIds(roleId);
	}

	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = userLocalService.getUserByEmailAddress(
			companyId, emailAddress);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public User getUserById(long userId)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(userId);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = userLocalService.getUserByScreenName(
			companyId, screenName);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public long getUserIdByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		return user.getUserId();
	}

	public long getUserIdByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = getUserByScreenName(companyId, screenName);

		return user.getUserId();
	}

	public boolean hasGroupUser(long groupId, long userId)
		throws SystemException {

		return userLocalService.hasGroupUser(groupId, userId);
	}

	public boolean hasRoleUser(long roleId, long userId)
		throws SystemException {

		return userLocalService.hasRoleUser(roleId, userId);
	}

	public boolean hasRoleUser(
			long companyId, String name, long userId, boolean inherited)
		throws PortalException, SystemException {

		return userLocalService.hasRoleUser(companyId, name, userId, inherited);
	}

	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.setRoleUsers(roleId, userIds);
	}

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
		}
		catch (PrincipalException pe) {

			// Allow any user to leave open and restricted communities

			boolean hasPermission = false;

			if (userIds.length == 0) {
				hasPermission = true;
			}
			else if (userIds.length == 1) {
				User user = getUser();

				if (user.getUserId() == userIds[0]) {
					Group group = groupPersistence.findByPrimaryKey(groupId);

					if (user.getCompanyId() == group.getCompanyId()) {
						int type = group.getType();

						if ((type == GroupConstants.TYPE_COMMUNITY_OPEN) ||
							(type ==
								GroupConstants.TYPE_COMMUNITY_RESTRICTED)) {

							hasPermission = true;
						}
					}
				}
			}

			if (!hasPermission) {
				throw new PrincipalException();
			}
		}

		userLocalService.unsetGroupUsers(groupId, userIds);
	}

	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId,
			ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetRoleUsers(roleId, userIds);
	}

	public void unsetTeamUsers(long teamId, long[] userIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetTeamUsers(teamId, userIds);
	}

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public User updateActive(long userId, boolean active)
		throws PortalException, SystemException {

		if ((getUserId() == userId) && !active) {
			throw new RequiredUserException();
		}

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		return userLocalService.updateActive(userId, active);
	}

	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateAgreedToTermsOfUse(
			userId, agreedToTermsOfUse);
	}

	public void updateEmailAddress(
			long userId, String password, String emailAddress1,
			String emailAddress2)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateEmailAddress(
			userId, password, emailAddress1, emailAddress2);
	}

	public User updateLockout(long userId, boolean lockout)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		return userLocalService.updateLockoutById(userId, lockout);
	}

	public void updateOpenId(long userId, String openId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateOpenId(userId, openId);
	}

	public void updateOrganizations(long userId, long[] organizationIds)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateOrganizations(userId, organizationIds);
	}

	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	public void updatePortrait(long userId, byte[] bytes)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updatePortrait(userId, bytes);
	}

	public void updateReminderQuery(
			long userId, String question, String answer)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateReminderQuery(userId, question, answer);
	}

	public void updateScreenName(long userId, String screenName)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateScreenName(userId, screenName);
	}

	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, organizationIds, ActionKeys.UPDATE);

		long curUserId = getUserId();

		if (curUserId == userId) {
			User user = userPersistence.findByPrimaryKey(userId);

			screenName = screenName.trim().toLowerCase();

			if (!screenName.equalsIgnoreCase(user.getScreenName())) {
				validateScreenName(user, screenName);
			}

			emailAddress = emailAddress.trim().toLowerCase();

			if (!emailAddress.equalsIgnoreCase(user.getEmailAddress())) {
				validateEmailAddress(user, emailAddress);
			}
		}

		if (groupIds != null) {
			groupIds = checkGroups(userId, groupIds);
		}

		if (organizationIds != null) {
			organizationIds = checkOrganizations(userId, organizationIds);
		}

		if (roleIds != null) {
			roleIds = checkRoles(userId, roleIds);
		}

		if (userGroupRoles != null) {
			userGroupRoles = checkUserGroupRoles(userId, userGroupRoles);
		}

		return userLocalService.updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			reminderQueryQuestion, reminderQueryAnswer, screenName,
			emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, serviceContext);
	}

	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			List<AnnouncementsDelivery> announcementsDelivers,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			reminderQueryQuestion, reminderQueryAnswer, screenName,
			emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);

		EnterpriseAdminUtil.updateAddresses(
			Contact.class.getName(), user.getContactId(), addresses);

		EnterpriseAdminUtil.updateEmailAddresses(
			Contact.class.getName(), user.getContactId(), emailAddresses);

		EnterpriseAdminUtil.updatePhones(
			Contact.class.getName(), user.getContactId(), phones);

		EnterpriseAdminUtil.updateWebsites(
			Contact.class.getName(), user.getContactId(), websites);

		updateAnnouncementsDeliveries(user.getUserId(), announcementsDelivers);

		return user;
	}

	protected long[] checkGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		// Add back any groups that the administrator does not have the rights
		// to remove and check that he has the permission to add any new group

		List<Group> oldGroups = groupLocalService.getUserGroups(userId);
		long[] oldGroupIds = new long[oldGroups.size()];

		for (int i = 0; i < oldGroups.size(); i++) {
			Group group = oldGroups.get(i);

			if (!ArrayUtil.contains(groupIds, group.getGroupId()) &&
				!GroupPermissionUtil.contains(
					getPermissionChecker(), group.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				groupIds = ArrayUtil.append(groupIds, group.getGroupId());
			}

			oldGroupIds[i] = group.getGroupId();
		}

		for (long groupId : groupIds) {
			if (!ArrayUtil.contains(oldGroupIds, groupId)) {
				GroupPermissionUtil.check(
					getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return groupIds;
	}

	protected long[] checkOrganizations(long userId, long[] organizationIds)
		throws PortalException, SystemException {

		// Add back any organizations that the administrator does not have the
		// rights to remove and check that he has the permission to add any new
		// organization

		List<Organization> oldOrganizations =
			organizationLocalService.getUserOrganizations(userId);
		long[] oldOrganizationIds = new long[oldOrganizations.size()];

		for (int i = 0; i < oldOrganizations.size(); i++) {
			Organization organization = oldOrganizations.get(i);

			if (!ArrayUtil.contains(
					organizationIds, organization.getOrganizationId()) &&
				!OrganizationPermissionUtil.contains(
					getPermissionChecker(), organization.getOrganizationId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				organizationIds = ArrayUtil.append(
					organizationIds, organization.getOrganizationId());
			}

			oldOrganizationIds[i] = organization.getOrganizationId();
		}

		for (long organizationId : organizationIds) {
			if (!ArrayUtil.contains(oldOrganizationIds, organizationId)) {
				OrganizationPermissionUtil.check(
					getPermissionChecker(), organizationId,
					ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return organizationIds;
	}

	protected long[] checkRoles(long userId, long[] roleIds)
		throws PrincipalException, SystemException {

		// Add back any roles that the administrator does not have the rights to
		// remove and check that he has the permission to add any new role

		List<Role> oldRoles = roleLocalService.getUserRoles(userId);
		long[] oldRoleIds = new long[oldRoles.size()];

		for (int i = 0; i < oldRoles.size(); i++) {
			Role role = oldRoles.get(i);

			if (!ArrayUtil.contains(roleIds, role.getRoleId()) &&
				!RolePermissionUtil.contains(
					getPermissionChecker(), role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				roleIds = ArrayUtil.append(roleIds, role.getRoleId());
			}

			oldRoleIds[i] = role.getRoleId();
		}

		for (long roleId : roleIds) {
			if (!ArrayUtil.contains(oldRoleIds, roleId)) {
				RolePermissionUtil.check(
					getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return roleIds;
	}

	protected List<UserGroupRole> checkUserGroupRoles(
			long userId, List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		// Add back any group roles that the administrator does not have the
		// rights to remove

		List<UserGroupRole> oldUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole oldUserGroupRole : oldUserGroupRoles) {
			if (!userGroupRoles.contains(oldUserGroupRole) &&
				(!GroupPermissionUtil.contains(
					getPermissionChecker(), oldUserGroupRole.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS) ||
				!RolePermissionUtil.contains(
					getPermissionChecker(), oldUserGroupRole.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS))) {

				userGroupRoles.add(oldUserGroupRole);
			}
		}

		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (!oldUserGroupRoles.contains(userGroupRole)) {

				if (!GroupPermissionUtil.contains(
						getPermissionChecker(), userGroupRole.getGroupId(),
						ActionKeys.ASSIGN_MEMBERS) ||
					!RolePermissionUtil.contains(
						getPermissionChecker(), userGroupRole.getRoleId(),
						ActionKeys.ASSIGN_MEMBERS)) {

					throw new PrincipalException();
				}
			}
		}

		return userGroupRoles;
	}

	protected void updateAnnouncementsDeliveries(
			long userId, List<AnnouncementsDelivery> announcementsDeliveries)
		throws PortalException, SystemException {

		for (AnnouncementsDelivery announcementsDelivery :
				announcementsDeliveries) {

			announcementsDeliveryService.updateDelivery(
				userId, announcementsDelivery.getType(),
				announcementsDelivery.getEmail(),
				announcementsDelivery.getSms(),
				announcementsDelivery.getWebsite());
		}
	}

	protected void validateEmailAddress(User user, String emailAddress)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!EnterpriseAdminUtil.hasUpdateEmailAddress(
				permissionChecker, user)) {

			throw new UserEmailAddressException();
		}

		if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
			Company company = companyPersistence.findByPrimaryKey(
				user.getCompanyId());

			if (!company.isStrangersWithMx()) {
				throw new ReservedUserEmailAddressException();
			}
		}
	}

	protected void validateScreenName(User user, String screenName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!EnterpriseAdminUtil.hasUpdateScreenName(permissionChecker, user)) {
			throw new UserScreenNameException();
		}
	}

	protected void validateOrganizationUsers(long[] userIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!PropsValues.ORGANIZATIONS_ASSIGNMENT_STRICT ||
			permissionChecker.isCompanyAdmin()) {

			return;
		}

		List<Organization> organizations =
			organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId());

		for (long userId : userIds) {
			boolean allowed = false;

			for (Organization organization : organizations) {
				boolean manageUsers = OrganizationPermissionUtil.contains(
					permissionChecker, organization, ActionKeys.MANAGE_USERS);
				boolean manageSuborganizations =
					OrganizationPermissionUtil.contains(
						permissionChecker, organization,
						ActionKeys.MANAGE_SUBORGANIZATIONS);

				if (!manageUsers && !manageSuborganizations) {
					continue;
				}

				boolean inherited = false;
				boolean includeSpecifiedOrganization = false;

				if (manageUsers && manageSuborganizations) {
					inherited = true;
					includeSpecifiedOrganization = true;
				}
				else if (!manageUsers && manageSuborganizations) {
					inherited = true;
					includeSpecifiedOrganization = false;
				}

				if (organizationLocalService.hasUserOrganization(
						userId, organization.getOrganizationId(), inherited,
						false, includeSpecifiedOrganization)) {

					allowed = true;

					break;
				}
			}

			if (!allowed) {
				throw new PrincipalException();
			}
		}
	}

}