/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.RolePermission;
import com.liferay.portal.service.permission.UserGroupPermission;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserUtil;

import java.util.List;
import java.util.Locale;

/**
 * <a href="UserServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 *
 */
public class UserServiceImpl extends PrincipalBean implements UserService {

	public void addGroupUsers(long groupId, String[] userIds)
		throws PortalException, SystemException {

		if ((userIds != null) && (userIds.length > 0)) {
			checkPermission(groupId, userIds);

			UserLocalServiceUtil.addGroupUsers(groupId, userIds);
		}
	}

	public void addRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.addRoleUsers(roleId, userIds);
	}

	public void addUserGroupUsers(String userGroupId, String[] userIds)
		throws PortalException, SystemException {

		UserGroupPermission.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.addUserGroupUsers(userGroupId, userIds);
	}

	public User addUser(
			String companyId, boolean autoUserId, String userId,
			boolean autoPassword, String password1, String password2,
			boolean passwordReset, String emailAddress, Locale locale,
			String firstName, String middleName, String lastName,
			String nickName, int prefixId, int suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId)
		throws PortalException, SystemException {

		return addUser(
			companyId, autoUserId, userId, autoPassword, password1, password2,
			passwordReset, emailAddress, locale, firstName, middleName,
			lastName, nickName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			true);
	}

	public User addUser(
			String companyId, boolean autoUserId, String userId,
			boolean autoPassword, String password1, String password2,
			boolean passwordReset, String emailAddress, Locale locale,
			String firstName, String middleName, String lastName,
			String nickName, int prefixId, int suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		if (!company.isStrangers()) {
			checkPermission(
				userId, organizationId, locationId, ActionKeys.ADD_USER);
		}

		String creatorUserId = null;

		try {
			creatorUserId = getUserId();
		}
		catch (PrincipalException pe) {
		}

		return UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail);
	}

	public void deleteRoleUser(String roleId, String userId)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		UserLocalServiceUtil.deleteRoleUser(roleId, userId);
	}

	public void deleteUser(String userId)
		throws PortalException, SystemException {

		if (getUserId().equals(userId)) {
			throw new RequiredUserException();
		}

		checkPermission(userId, ActionKeys.DELETE);

		UserLocalServiceUtil.deleteUser(userId);
	}

	public List getGroupUsers(long groupId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.getGroupUsers(groupId);
	}

	public List getRoleUsers(String roleId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.getRoleUsers(roleId);
	}

	public User getUserByEmailAddress(String companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			companyId, emailAddress);

		checkPermission(user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public User getUserById(String userId)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserById(userId);

		checkPermission(user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	public boolean hasGroupUser(long groupId, String userId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.hasGroupUser(groupId, userId);
	}

	public boolean hasRoleUser(String roleId, String userId)
		throws PortalException, SystemException {

		return UserLocalServiceUtil.hasRoleUser(roleId, userId);
	}

	public void setGroupUsers(long groupId, String[] userIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setGroupUsers(groupId, userIds);
	}

	public void setRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setRoleUsers(roleId, userIds);
	}

	public void setUserGroupUsers(String userGroupId, String[] userIds)
		throws PortalException, SystemException {

		UserGroupPermission.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.setUserGroupUsers(userGroupId, userIds);
	}

	public void unsetGroupUsers(long groupId, String[] userIds)
		throws PortalException, SystemException {

		if ((userIds != null) && (userIds.length > 0)) {
			checkPermission(groupId, userIds);

			UserLocalServiceUtil.unsetGroupUsers(groupId, userIds);
		}
	}

	public void unsetRoleUsers(String roleId, String[] userIds)
		throws PortalException, SystemException {

		RolePermission.check(getPermissionChecker(), roleId, ActionKeys.UPDATE);

		UserLocalServiceUtil.unsetRoleUsers(roleId, userIds);
	}

	public void unsetUserGroupUsers(String userGroupId, String[] userIds)
		throws PortalException, SystemException {

		UserGroupPermission.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		UserLocalServiceUtil.unsetUserGroupUsers(userGroupId, userIds);
	}

	public User updateActive(String userId, boolean active)
		throws PortalException, SystemException {

		if (getUserId().equals(userId) && !active) {
			throw new RequiredUserException();
		}

		checkPermission(userId, ActionKeys.DELETE);

		return UserLocalServiceUtil.updateActive(userId, active);
	}

	public User updateAgreedToTermsOfUse(
			String userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updateAgreedToTermsOfUse(
			userId, agreedToTermsOfUse);
	}

	public User updatePassword(
			String userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updatePassword(
			userId, password1, password2, passwordReset);
	}

	public void updatePortrait(String userId, byte[] bytes)
		throws PortalException, SystemException {

		checkPermission(userId, ActionKeys.UPDATE);

		UserLocalServiceUtil.updatePortrait(userId, bytes);
	}

	public User updateUser(
			String userId, String password, String emailAddress,
			String languageId, String timeZoneId, String greeting,
			String resolution, String comments, String firstName,
			String middleName, String lastName, String nickName,
			int prefixId, int suffixId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, String smsSn, String aimSn,
			String icqSn, String jabberSn, String msnSn, String skypeSn,
			String ymSn, String jobTitle, String organizationId,
			String locationId)
		throws PortalException, SystemException {

		checkPermission(userId, organizationId, locationId, ActionKeys.UPDATE);

		return UserLocalServiceUtil.updateUser(
			userId, password, emailAddress, languageId, timeZoneId, greeting,
			resolution, comments, firstName, middleName, lastName, nickName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle,
			organizationId, locationId);
	}

	protected void checkPermission(String userId, String actionId)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		checkPermission(
			userId, user.getOrganization().getOrganizationId(),
			user.getLocation().getOrganizationId(), actionId);
	}

	protected void checkPermission(
			String userId, String organizationId, String locationId,
			String actionId)
		throws PortalException, SystemException {

		UserPermission.check(
			getPermissionChecker(), userId, organizationId, locationId,
			actionId);
	}

	protected void checkPermission(long groupId, String[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermission.check(
				getPermissionChecker(), groupId, ActionKeys.UPDATE);
		}
		catch (PrincipalException pe) {

			// Allow users to join and leave open communities

			boolean hasPermission = false;

			User user = getUser();

			if ((userIds.length == 1) &&
				(user.getUserId().equals(userIds[0]))) {

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