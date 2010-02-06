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

package com.liferay.portlet.announcements.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
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
 */
public class AnnouncementsUtil {

	public static LinkedHashMap<Long, long[]> getAnnouncementScopes(long userId)
		throws PortalException, SystemException {

		LinkedHashMap<Long, long[]> scopes = new LinkedHashMap<Long, long[]>();

		// General announcements

		scopes.put(new Long(0), new long[] {0});

		// Personal announcements

		scopes.put(_USER_CLASS_NAME_ID, new long[] {userId});

		// Community announcements

		List<Group> groupsList = new ArrayList<Group>();

		List<Group> groups = GroupLocalServiceUtil.getUserGroups(userId, true);

		if (groups.size() > 0) {
			scopes.put(_GROUP_CLASS_NAME_ID, _getGroupIds(groups));

			groupsList.addAll(groups);
		}

		// Organization announcements

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(userId, true);

		if (organizations.size() > 0) {
			scopes.put(
				_ORGANIZATION_CLASS_NAME_ID,
				_getOrganizationIds(organizations));

			for (Organization organization : organizations) {
				groupsList.add(organization.getGroup());
			}
		}

		// Role announcements

		if (groupsList.size() > 0) {
			List<Role> roles = RoleLocalServiceUtil.getUserRelatedRoles(
				userId, groupsList);

			roles = ListUtil.copy(roles);

			for (Group group : groupsList) {
				roles.addAll(
					RoleLocalServiceUtil.getUserGroupRoles(
						userId, group.getGroupId()));
				roles.addAll(
					RoleLocalServiceUtil.getUserGroupGroupRoles(
						userId, group.getGroupId()));
			}

			if (roles.size() > 0) {
				scopes.put(_ROLE_CLASS_NAME_ID, _getRoleIds(roles));
			}
		}

		// User group announcements

		List<UserGroup> userGroups =
			UserGroupLocalServiceUtil.getUserUserGroups(userId);

		if (userGroups.size() > 0) {
			scopes.put(_USER_GROUP_CLASS_NAME_ID, _getUserGroupIds(userGroups));
		}

		return scopes;
	}

	private static long[] _getGroupIds(List<Group> groups) {
		long[] groupIds = new long[groups.size()];

		int i = 0;

		for (Group group : groups) {
			groupIds[i++] = group.getGroupId();
		}

		return groupIds;
	}

	private static long[] _getOrganizationIds(
		List<Organization> organizations) {

		long[] organizationIds = new long[organizations.size()];

		int i = 0;

		for (Organization organization : organizations) {
			organizationIds[i++] = organization.getOrganizationId();
		}

		return organizationIds;
	}

	private static long[] _getRoleIds(List<Role> roles) {
		long[] roleIds = new long[roles.size()];

		int i = 0;

		for (Role role : roles) {
			roleIds[i++] = role.getRoleId();
		}

		return roleIds;
	}

	private static long[] _getUserGroupIds(List<UserGroup> userGroups) {
		long[] userGroupIds = new long[userGroups.size()];

		int i = 0;

		for (UserGroup userGroup : userGroups) {
			userGroupIds[i++] = userGroup.getUserGroupId();
		}

		return userGroupIds;
	}

	private static long _GROUP_CLASS_NAME_ID = PortalUtil.getClassNameId(
		Group.class.getName());

	private static long _ORGANIZATION_CLASS_NAME_ID = PortalUtil.getClassNameId(
		Organization.class.getName());

	private static long _ROLE_CLASS_NAME_ID = PortalUtil.getClassNameId(
		Role.class.getName());

	private static long _USER_CLASS_NAME_ID = PortalUtil.getClassNameId(
		User.class.getName());

	private static long _USER_GROUP_CLASS_NAME_ID = PortalUtil.getClassNameId(
		UserGroup.class.getName());

}