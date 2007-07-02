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
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.service.permission.PermissionCacheUtil;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCartLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="GroupLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class GroupLocalServiceImpl extends GroupLocalServiceBaseImpl {

	public Group addGroup(
			long userId, String className, long classPK, String name,
			String description, String type, String friendlyURL, boolean active)
		throws PortalException, SystemException {

		return addGroup(
			userId, className, classPK, GroupImpl.DEFAULT_LIVE_GROUP_ID, name,
			description, type, friendlyURL, active);
	}

	public Group addGroup(
			long userId, String className, long classPK, long liveGroupId,
			String name, String description, String type, String friendlyURL,
			boolean active)
		throws PortalException, SystemException {

		// Group

		User user = UserUtil.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		if ((classNameId <= 0) || (classPK <= 0)) {
			validateName(0, user.getCompanyId(), name);
		}

		friendlyURL = getFriendlyURL(classNameId, friendlyURL);

		validateFriendlyURL(0, user.getCompanyId(), friendlyURL);

		long groupId = CounterLocalServiceUtil.increment();

		if ((classNameId > 0) && (classPK > 0)) {
			name = String.valueOf(groupId);
		}

		Group group = GroupUtil.create(groupId);

		group.setCompanyId(user.getCompanyId());
		group.setCreatorUserId(userId);
		group.setClassNameId(classNameId);
		group.setClassPK(classPK);
		group.setParentGroupId(GroupImpl.DEFAULT_PARENT_GROUP_ID);
		group.setLiveGroupId(liveGroupId);
		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

		GroupUtil.update(group);

		// Layout sets

		LayoutSetLocalServiceUtil.addLayoutSet(groupId, true);

		LayoutSetLocalServiceUtil.addLayoutSet(groupId, false);

		if ((classNameId <= 0) && (classPK <= 0) && !user.isDefaultUser()) {

			// Resources

			ResourceLocalServiceUtil.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);

			// Community roles

			Role role = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleImpl.COMMUNITY_OWNER);

			UserGroupRoleLocalServiceUtil.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});

			// User

			UserLocalServiceUtil.addGroupUsers(
				group.getGroupId(), new long[] {userId});
		}

		return group;
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.addGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		UserUtil.addGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void checkSystemGroups(long companyId)
		throws PortalException, SystemException {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			companyId);

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (int i = 0; i < systemGroups.length; i++) {
			Group group = null;

			try {
				group = GroupFinder.findByC_N(companyId, systemGroups[i]);
			}
			catch (NoSuchGroupException nsge) {
				String friendlyURL = null;

				if (systemGroups[i].equals(GroupImpl.GUEST)) {
					friendlyURL = "/guest";
				}

				group = addGroup(
					defaultUserId, null, 0, systemGroups[i], null, null,
					friendlyURL, true);
			}

			if (group.getName().equals(GroupImpl.GUEST)) {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					group.getGroupId(), false);

				if (layoutSet.getPageCount() == 0) {
					addDefaultLayouts(group);
				}
			}
		}
	}

	public void deleteGroup(long groupId)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		if (PortalUtil.isSystemGroup(group.getName())) {
			throw new RequiredGroupException();
		}

		// Layout sets

		try {
			LayoutSetLocalServiceUtil.deleteLayoutSet(groupId, true);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		try {
			LayoutSetLocalServiceUtil.deleteLayoutSet(groupId, false);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		// Role

		try {
			Role role = RoleLocalServiceUtil.getGroupRole(
				group.getCompanyId(), groupId);

			RoleLocalServiceUtil.deleteRole(role.getRoleId());
		}
		catch (NoSuchRoleException nsre) {
		}

		// Group roles

		UserGroupRoleLocalServiceUtil.deleteUserGroupRolesByGroupId(groupId);

		// Blogs

		BlogsEntryLocalServiceUtil.deleteEntries(groupId);

		// Bookmarks

		BookmarksFolderLocalServiceUtil.deleteFolders(groupId);

		// Calendar

		CalEventLocalServiceUtil.deleteEvents(groupId);

		// Document library

		DLFolderLocalServiceUtil.deleteFolders(groupId);

		// Image gallery

		IGFolderLocalServiceUtil.deleteFolders(groupId);

		// Journal

		JournalArticleLocalServiceUtil.deleteArticles(groupId);

		// Message boards

		MBBanLocalServiceUtil.deleteBansByGroupId(groupId);
		MBCategoryLocalServiceUtil.deleteCategories(groupId);
		MBStatsUserLocalServiceUtil.deleteStatsUserByGroupId(groupId);

		// Polls

		PollsQuestionLocalServiceUtil.deleteQuestions(groupId);

		// Shopping

		ShoppingCartLocalServiceUtil.deleteGroupCarts(groupId);

		// Wiki

		WikiNodeLocalServiceUtil.deleteNodes(groupId);

		// Resources

		Iterator itr = ResourceFinder.findByC_P(
			group.getCompanyId(), String.valueOf(groupId)).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			ResourceLocalServiceUtil.deleteResource(resource);
		}

		if ((group.getClassNameId() <= 0) && (group.getClassPK() <= 0)) {
			ResourceLocalServiceUtil.deleteResource(
				group.getCompanyId(), Group.class.getName(),
				ResourceImpl.SCOPE_INDIVIDUAL, group.getGroupId());
		}

		// Group

		GroupUtil.remove(groupId);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchGroupException();
		}

		return GroupUtil.findByC_F(companyId, friendlyURL);
	}

	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return GroupUtil.findByPrimaryKey(groupId);
	}

	public Group getGroup(long companyId, String name)
		throws PortalException, SystemException {

		return GroupFinder.findByC_N(companyId, name);
	}

	public Group getOrganizationGroup(long companyId, long organizationId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Organization.class);

		return GroupUtil.findByC_C_C(companyId, classNameId, organizationId);
	}

	public List getOrganizationsGroups(List organizations)
		throws PortalException, SystemException {

		List organizationGroups = new ArrayList();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = (Organization)organizations.get(i);

			Group group = organization.getGroup();

			organizationGroups.add(group);
		}

		return organizationGroups;
	}

	public List getRoleGroups(long roleId)
		throws PortalException, SystemException {

		return RoleUtil.getGroups(roleId);
	}

	public Group getStagingGroup(long liveGroupId)
		throws PortalException, SystemException {

		return GroupUtil.findByLiveGroupId(liveGroupId);
	}

	public Group getUserGroup(long companyId, long userId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(User.class);

		return GroupUtil.findByC_C_C(companyId, classNameId, userId);
	}

	public Group getUserGroupGroup(long companyId, long userGroupId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(UserGroup.class);

		return GroupUtil.findByC_C_C(companyId, classNameId, userGroupId);
	}

	public List getUserGroupsGroups(List userGroups)
		throws PortalException, SystemException {

		List userGroupGroups = new ArrayList();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = (UserGroup)userGroups.get(i);

			Group group = userGroup.getGroup();

			userGroupGroups.add(group);
		}

		return userGroupGroups;
	}

	public boolean hasRoleGroup(long roleId, long groupId)
		throws PortalException, SystemException {

		return RoleUtil.containsGroup(roleId, groupId);
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws SystemException {

		if (GroupFinder.countByG_U(groupId, userId) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public List search(
			long companyId, String name, String description,
			LinkedHashMap params, int begin, int end)
		throws SystemException {

		return GroupFinder.findByC_N_D(
			companyId, name, description, params, begin, end);
	}

	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap params)
		throws SystemException {

		return GroupFinder.countByC_N_D(companyId, name, description, params);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.setGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void setUserGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		UserUtil.setGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.removeGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		UserUtil.removeGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public Group updateGroup(
			long groupId, String name, String description, String type,
			String friendlyURL, boolean active)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		long classNameId = group.getClassNameId();
		long classPK = group.getClassPK();
		friendlyURL = getFriendlyURL(classNameId, friendlyURL);

		if ((classNameId <= 0) || (classPK <= 0)) {
			validateName(group.getGroupId(), group.getCompanyId(), name);
		}

		if (PortalUtil.isSystemGroup(group.getName()) &&
			!group.getName().equals(name)) {

			throw new RequiredGroupException();
		}

		validateFriendlyURL(
			group.getGroupId(), group.getCompanyId(), friendlyURL);

		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

		GroupUtil.update(group);

		return group;
	}

	protected void addDefaultLayouts(Group group)
		throws PortalException, SystemException {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			group.getCompanyId());
		String name = PropsUtil.get(PropsUtil.DEFAULT_GUEST_LAYOUT_NAME);

		String friendlyURL = StringPool.BLANK;

		if (Validator.isNotNull(group.getFriendlyURL())) {
			friendlyURL = PropsUtil.get(PropsUtil.DEFAULT_GUEST_FRIENDLY_URL);
		}

		Layout layout = LayoutLocalServiceUtil.addLayout(
			defaultUserId, group.getGroupId(), false,
			LayoutImpl.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
			StringPool.BLANK, LayoutImpl.TYPE_PORTLET, false, friendlyURL);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String layoutTemplateId = PropsUtil.get(
			PropsUtil.DEFAULT_GUEST_LAYOUT_TEMPLATE_ID);

		layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsUtil.DEFAULT_GUEST_LAYOUT_COLUMN + i);

			layoutTypePortlet.addPortletIds(
				0, StringUtil.split(portletIds), columnId, false);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected String getFriendlyURL(long classNameId, String friendlyURL)
		throws PortalException, SystemException {

		if (classNameId > 0) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);

			if (classNameId == userClassNameId) {
				return StringPool.BLANK;
			}
		}

		return friendlyURL;
	}

	protected void validateFriendlyURL(
			long groupId, long companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNotNull(friendlyURL)) {
			int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

			if (exceptionType != -1) {
				throw new GroupFriendlyURLException(exceptionType);
			}

			try {
				Group group = GroupUtil.findByC_F(companyId, friendlyURL);

				if ((groupId <= 0) || (group.getGroupId() != groupId)) {
					throw new GroupFriendlyURLException(
						GroupFriendlyURLException.DUPLICATE);
				}
			}
			catch (NoSuchGroupException nsge) {
			}

			String screenName = friendlyURL;

			if (screenName.startsWith(StringPool.SLASH)) {
				screenName = friendlyURL.substring(1, friendlyURL.length());
			}

			User user = UserUtil.fetchByC_SN(companyId, screenName);

			if (user != null) {
				throw new GroupFriendlyURLException(
					GroupFriendlyURLException.DUPLICATE);
			}
		}
	}

	protected void validateName(long groupId, long companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(StringPool.COMMA) != -1) ||
			(name.indexOf(StringPool.STAR) != -1)) {

			throw new GroupNameException();
		}

		try {
			Group group = GroupFinder.findByC_N(companyId, name);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchGroupException nsge) {
		}
	}

}