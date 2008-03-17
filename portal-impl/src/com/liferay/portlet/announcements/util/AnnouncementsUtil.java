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

package com.liferay.portlet.announcements.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="AnnouncementsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class AnnouncementsUtil {

	public static LinkedHashMap<Long,Long[]> getAnnouncementScopes(
			long userId)
		throws PortalException, SystemException {

		LinkedHashMap<Long,Long[]> scopes =
			new LinkedHashMap<Long,Long[]>();

		// General announcements

		scopes.put(new Long(0), new Long[] {new Long(0)});

		// User's personal announcements

		scopes.put(
			_userClassNameId, new Long[] {new Long(userId)});

		// User's UserGroup announcements

		List<UserGroup> usersUserGroups =
			UserGroupLocalServiceUtil.getUserUserGroups(userId);

		Long[] usersUserGroupsIds = getUserGroupIds(usersUserGroups);

		if (usersUserGroupsIds.length > 0) {
			scopes.put(_userGroupClassNameId, usersUserGroupsIds);
		}

		// User's Community announcements

		List<Group> usersGroups = GroupLocalServiceUtil.getUserGroups(userId);

		List<Group> groupList = new ArrayList<Group>();

		Long[] usersGroupsIds = getGroupIds(usersGroups);

		if (usersGroupsIds.length > 0) {
			scopes.put(_communityClassNameId, usersGroupsIds);
			groupList.addAll(usersGroups);
		}

		// User's Organizations announcements

		List<Organization> usersOrgs =
			OrganizationLocalServiceUtil.getUserOrganizations(userId);

		Long[] usersOrganizationsIds = getOrganizationIds(usersOrgs);

		if (usersOrganizationsIds.length > 0) {
			scopes.put(
				_organizationClassNameId, usersOrganizationsIds);

			for (Organization org : usersOrgs) {
				groupList.add(org.getGroup());
			}
		}

		// User's Role announcements

		if (groupList.size() > 0) {
			List<Role> usersRoles =
				RoleLocalServiceUtil.getUserRelatedRoles(userId, groupList);

			for (Group group : groupList) {
				usersRoles.addAll(RoleLocalServiceUtil.getUserGroupRoles(
					userId, group.getGroupId()));
			}

			Long[] usersRolesIds = getRoleIds(usersRoles);

			if (usersRolesIds.length > 0) {
				scopes.put(_roleClassNameId, usersRolesIds);
			}
		}

		return scopes;
	}

	protected static Long[] getRoleIds(List<Role> usersRoles) {
		Long[] ids = new Long[usersRoles.size()];

		int i = 0;

		for (Role role : usersRoles) {
			ids[i++] = new Long(role.getRoleId());
		}

		return ids;
	}

	protected static Long[] getUserGroupIds(List<UserGroup> usersUserGroups) {
		Long[] ids = new Long[usersUserGroups.size()];

		int i = 0;

		for (UserGroup userGroup : usersUserGroups) {
			ids[i++] = new Long(userGroup.getUserGroupId());
		}

		return ids;
	}

	protected static Long[] getGroupIds(List<Group> usersGroups) {
		Long[] ids = new Long[usersGroups.size()];

		int i = 0;

		for (Group group : usersGroups) {
			ids[i++] = new Long(group.getGroupId());
		}

		return ids;
	}

	protected static Long[] getOrganizationIds(List<Organization> usersOrgs) {
		Long[] ids = new Long[usersOrgs.size()];

		int i = 0;

		for (Organization org : usersOrgs) {
			ids[i++] = new Long(org.getOrganizationId());
		}

		return ids;
	}

	private static long _userClassNameId = PortalUtil.getClassNameId(User.class.getName());
	private static long _roleClassNameId = PortalUtil.getClassNameId(Role.class.getName());
	private static long _userGroupClassNameId = PortalUtil.getClassNameId(UserGroup.class.getName());
	private static long _organizationClassNameId = PortalUtil.getClassNameId(Organization.class.getName());
	private static long _communityClassNameId = PortalUtil.getClassNameId(Group.class.getName());

}
