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

import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserUtil;

import java.util.List;
import java.util.Locale;

/**
 * <a href="UserServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Scott Lee
 *
 */
public class UserServiceImpl extends PrincipalBean implements UserService {

	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		if ((userIds != null) && (userIds.length > 0)) {
			checkUpdatePermission(groupId, userIds);

			UserLocalServiceUtil.addGroupUsers(groupId, userIds);
		}
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		//PasswordPolicyPermissionUtil.check(
		//	getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		UserLocalServiceUtil.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.addRoleUsers(roleId, userIds);
	}

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		if (!UserGroupPermissionUtil.contains(
				getPermissionChecker(), userGroupId, ActionKeys.UPDATE) &&
			!UserGroupPermissionUtil.contains(
				getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_USERS)) {

			throw new PrincipalException();
		}

		UserLocalServiceUtil.addUserGroupUsers(userGroupId, userIds);
	}

	public User addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long organizationId, long locationId,
			boolean sendEmail)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		if (!company.isStrangers()) {
			checkPermission(0, organizationId, locationId, ActionKeys.ADD_USER);
		}

		long creatorUserId = 0;

		try {
			creatorUserId = getUserId();
		}
		catch (PrincipalException pe) {
		}

		return UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			sendEmail);
	}

	public void deleteRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		UserLocalServiceUtil.deleteRoleUser(roleId, userId);
	}

	public void deleteUser(long userId)
		throws PortalException, SystemException {

		if (getUserId() == userId) {
			throw new RequiredUserException();
		}

		checkPermission(userId, ActionKeys.DELETE);

		UserLocalServiceUtil.deleteUser(userId);
	}

	public long getDefaultUserId(long companyId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.getDefaultUserId(companyId);
	}

	public List getGroupUsers(long groupId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.getGroupUsers(groupId);
	}

	public List getRoleUsers(long roleId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.getRoleUsers(roleId);
	}

	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			companyId, emailAddress);

		checkPermission(user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public User getUserById(long userId)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserById(userId);

		checkPermission(user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByScreenName(
			companyId, screenName);

		checkPermission(user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public boolean hasGroupUser(long groupId, long userId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.hasGroupUser(groupId, userId);
	}

	public boolean hasRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.hasRoleUser(roleId, userId);
	}

	public void setGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setGroupUsers(groupId, userIds);
	}

	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setRoleUsers(roleId, userIds);
	}

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setUserGroupUsers(userGroupId, userIds);
	}

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		if ((userIds != null) && (userIds.length > 0)) {
			checkUnsetPermission(groupId, userIds);

			UserLocalServiceUtil.unsetGroupUsers(groupId, userIds);
		}
	}

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		//PasswordPolicyPermissionUtil.check(
		//	getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		UserLocalServiceUtil.unsetPasswordPolicyUsers(
			passwordPolicyId, userIds);
	}

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.unsetRoleUsers(roleId, userIds);
	}

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		if (!UserGroupPermissionUtil.contains(
				getPermissionChecker(), userGroupId, ActionKeys.UPDATE) &&
			!UserGroupPermissionUtil.contains(
				getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_USERS)) {

			throw new PrincipalException();
		}

		UserLocalServiceUtil.unsetUserGroupUsers(userGroupId, userIds);
	}

	public User updateActive(long userId, boolean active)
		throws PortalException, SystemException {

		if ((getUserId() == userId) && !active) {
			throw new RequiredUserException();
		}

		checkPermission(userId, ActionKeys.DELETE);

		return UserLocalServiceUtil.updateActive(userId, active);
	}

	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updateAgreedToTermsOfUse(
			userId, agreedToTermsOfUse);
	}

	public User updateLockout(long userId, boolean lockout)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.DELETE);

		return UserLocalServiceUtil.updateLockoutById(userId, lockout);
	}

	public void updateOrganizations(
			long userId, long organizationId, long locationId)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		UserLocalServiceUtil.updateOrganizations(
			userId, organizationId, locationId);
	}

	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updatePassword(
			userId, password1, password2, passwordReset);
	}

	public void updatePortrait(long userId, byte[] bytes)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		UserLocalServiceUtil.updatePortrait(userId, bytes);
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

		checkPermission(userId, organizationId, locationId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updateUser(
			userId, password, screenName, emailAddress, languageId, timeZoneId,
			greeting, comments, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, smsSn,
			aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle,
			organizationId, locationId);
	}

	protected void checkPermission(long userId, String actionId)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		checkPermission(
			userId, user.getOrganization().getOrganizationId(),
			user.getLocation().getOrganizationId(), actionId);
	}

	protected void checkPermission(
			long userId, long organizationId, long locationId, String actionId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, organizationId, locationId,
			actionId);
	}

	protected void checkUnsetPermission(long groupId, long[] userIds)
		throws PortalException, SystemException {

		User user = getUser();

		Role ownerRole = RoleLocalServiceUtil.getRole(
			user.getCompanyId(), RoleImpl.COMMUNITY_OWNER);

		if (!UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), groupId, ownerRole.getRoleId())) {

			Role adminRole = RoleLocalServiceUtil.getRole(
				user.getCompanyId(), RoleImpl.COMMUNITY_ADMINISTRATOR);

			for (int i = 0; i < userIds.length; i++) {
				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						userIds[i], groupId, ownerRole.getRoleId()) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						userIds[i], groupId, adminRole.getRoleId()) ) {

					throw new PrincipalException();
				}
			}
		}

		checkUpdatePermission(groupId, userIds);
	}

	protected void checkUpdatePermission(long groupId, long[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.UPDATE);
		}
		catch (PrincipalException pe) {

			// Allow users to join and leave open communities

			boolean hasPermission = false;

			long userId = getUserId();

			if ((userIds.length == 1) && (userId == userIds[0])) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.getType().equals(GroupImpl.TYPE_COMMUNITY_OPEN)) {
					hasPermission = true;
				}
			}

			if (!hasPermission) {
				throw new PrincipalException();
			}
		}
	}

}