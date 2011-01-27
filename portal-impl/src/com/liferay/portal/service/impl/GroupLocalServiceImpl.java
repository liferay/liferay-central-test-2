/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.util.UniqueList;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Wesley Gong
 */
public class GroupLocalServiceImpl extends GroupLocalServiceBaseImpl {

	public GroupLocalServiceImpl() {
		initImportLARFile();
	}

	public Group addGroup(
			long userId, String className, long classPK, long liveGroupId,
			String name, String description, int type, String friendlyURL,
			boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Group

		User user = userPersistence.findByPrimaryKey(userId);
		className = GetterUtil.getString(className);
		long classNameId = PortalUtil.getClassNameId(className);
		String friendlyName = name;

		long groupId = 0;

		while (true) {
			groupId = counterLocalService.increment();

			User screenNameUser = userPersistence.fetchByC_SN(
				user.getCompanyId(), String.valueOf(groupId));

			if (screenNameUser == null) {
				break;
			}
		}

		boolean staging = isStaging(serviceContext);

		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			className = Group.class.getName();
			classNameId = groupClassNameId;
			classPK = groupId;
		}
		else if (!(Validator.isNotNull(name) &&
			name.equals(GroupConstants.USER_PERSONAL_COMMUNITY))) {

			name = String.valueOf(classPK);
		}

		if (className.equals(Organization.class.getName()) && staging) {
			classPK = liveGroupId;
		}

		long parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

		if (className.equals(Layout.class.getName())) {
			Layout layout = layoutLocalService.getLayout(classPK);

			parentGroupId = layout.getGroupId();
		}

		friendlyURL = getFriendlyURL(
			user.getCompanyId(), groupId, classNameId, classPK, friendlyName,
			friendlyURL);

		if (staging) {
			name = name.concat(" (Staging)");
			friendlyURL = friendlyURL.concat("-staging");
		}

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			validateName(groupId, user.getCompanyId(), name);
		}

		validateFriendlyURL(
			user.getCompanyId(), groupId, classNameId, classPK, friendlyURL);

		Group group = groupPersistence.create(groupId);

		group.setCompanyId(user.getCompanyId());
		group.setCreatorUserId(userId);
		group.setClassNameId(classNameId);
		group.setClassPK(classPK);
		group.setParentGroupId(parentGroupId);
		group.setLiveGroupId(liveGroupId);
		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

		groupPersistence.update(group, false);

		// Layout sets

		layoutSetLocalService.addLayoutSet(groupId, true);

		layoutSetLocalService.addLayoutSet(groupId, false);

		if ((classNameId == groupClassNameId) && !user.isDefaultUser()) {

			// Resources

			resourceLocalService.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);

			// Community roles

			Role role = roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.COMMUNITY_OWNER);

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});

			// User

			userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {userId});

			// Asset

			if (serviceContext != null) {
				updateAsset(
					userId, group, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames());
			}
		}
		else if (className.equals(Organization.class.getName()) &&
				 !user.isDefaultUser()) {

			// Resources

			resourceLocalService.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);
		}

		return group;
	}

	public Group addGroup(
			long userId, String className, long classPK, String name,
			String description, int type, String friendlyURL, boolean active,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addGroup(
			userId, className, classPK, GroupConstants.DEFAULT_LIVE_GROUP_ID,
			name, description, type, friendlyURL, active, serviceContext);
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.addGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroups(long userId, long[] groupIds)
		throws SystemException {

		userPersistence.addGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkCompanyGroup(long companyId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		int count = groupPersistence.countByC_C_C(
			companyId, classNameId, companyId);

		if (count == 0) {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			groupLocalService.addGroup(
				defaultUserId, Company.class.getName(), companyId, null, null,
				0, null, true, null);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkSystemGroups(long companyId)
		throws PortalException, SystemException {

		for (Group group : groupFinder.findBySystem(companyId)) {
			_systemGroupsMap.put(companyId + group.getName(), group);
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (String name : systemGroups) {
			Group group = _systemGroupsMap.get(companyId + name);

			try {
				if (group == null) {
					group = groupPersistence.findByC_N(companyId, name);
				}
			}
			catch (NoSuchGroupException nsge) {
				String className = null;
				long classPK = 0;
				int type = GroupConstants.TYPE_COMMUNITY_OPEN;
				String friendlyURL = null;

				if (name.equals(GroupConstants.CONTROL_PANEL)) {
					type = GroupConstants.TYPE_COMMUNITY_PRIVATE;
					friendlyURL = GroupConstants.CONTROL_PANEL_FRIENDLY_URL;
				}
				else if (name.equals(GroupConstants.GUEST)) {
					friendlyURL = "/guest";
				}
				else if (name.equals(GroupConstants.USER_PERSONAL_COMMUNITY)) {
					className = GroupConstants.USER_PERSONAL_COMMUNITY;
					classPK = defaultUserId;
					type = GroupConstants.TYPE_COMMUNITY_PRIVATE;
					friendlyURL =
						GroupConstants.USER_PERSONAL_COMMUNITY_FRIENDLY_URL;
				}

				group = groupLocalService.addGroup(
					defaultUserId, className, classPK, name, null, type,
					friendlyURL, true, null);
			}

			if (group.isControlPanel()) {
				LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
					group.getGroupId(), true);

				if (layoutSet.getPageCount() == 0) {
					addControlPanelLayouts(group);
				}
			}

			if (group.getName().equals(GroupConstants.GUEST)) {
				LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
					group.getGroupId(), false);

				if (layoutSet.getPageCount() == 0) {
					addDefaultGuestPublicLayouts(group);
				}
			}

			_systemGroupsMap.put(companyId + name, group);
		}
	}

	public void deleteGroup(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (PortalUtil.isSystemGroup(group.getName())) {
			throw new RequiredGroupException(String.valueOf(groupId));
		}

		// Layout branches

		layoutSetBranchLocalService.deleteLayoutSetBranches(groupId, true);
		layoutSetBranchLocalService.deleteLayoutSetBranches(groupId, false);

		// Layout sets

		try {
			layoutSetLocalService.deleteLayoutSet(groupId, true);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		try {
			layoutSetLocalService.deleteLayoutSet(groupId, false);
		}
		catch (NoSuchLayoutSetException nslse) {
		}

		// Group roles

		userGroupRoleLocalService.deleteUserGroupRolesByGroupId(groupId);

		// User group roles

		userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByGroupId(
			groupId);

		// Membership requests

		membershipRequestLocalService.deleteMembershipRequests(
			group.getGroupId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			group.getCompanyId(), BlogsEntry.class.getName(),
			group.getGroupId());
		subscriptionLocalService.deleteSubscriptions(
			group.getCompanyId(), JournalArticle.class.getName(),
			group.getGroupId());

		/// Teams

		teamLocalService.deleteTeams(group.getGroupId());

		// Staging

		unscheduleStaging(group);

		if (group.hasStagingGroup()) {
			deleteGroup(group.getStagingGroup().getGroupId());
		}

		// Asset

		if (group.isCommunity()) {
			assetEntryLocalService.deleteEntry(Group.class.getName(), groupId);
		}

		// Blogs

		blogsEntryLocalService.deleteEntries(groupId);
		blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);

		// Bookmarks

		bookmarksFolderLocalService.deleteFolders(groupId);

		// Calendar

		calEventLocalService.deleteEvents(groupId);

		// Document library

		dlAppLocalService.deleteAll(groupId);

		// Image gallery

		igFolderLocalService.deleteFolders(groupId);

		// Journal

		journalArticleLocalService.deleteArticles(groupId);
		journalTemplateLocalService.deleteTemplates(groupId);
		journalStructureLocalService.deleteStructures(groupId);

		// Message boards

		mbBanLocalService.deleteBansByGroupId(groupId);
		mbCategoryLocalService.deleteCategories(groupId);
		mbStatsUserLocalService.deleteStatsUsersByGroupId(groupId);

		// Polls

		pollsQuestionLocalService.deleteQuestions(groupId);

		// Shopping

		shoppingCartLocalService.deleteGroupCarts(groupId);
		shoppingCategoryLocalService.deleteCategories(groupId);
		shoppingCouponLocalService.deleteCoupons(groupId);
		shoppingOrderLocalService.deleteOrders(groupId);

		// Software catalog

		scFrameworkVersionLocalService.deleteFrameworkVersions(groupId);
		scProductEntryLocalService.deleteProductEntries(groupId);

		// Tasks

		tasksProposalLocalService.deleteProposals(groupId);

		// Wiki

		wikiNodeLocalService.deleteNodes(groupId);

		// Resources

		Iterator<Resource> itr = resourceFinder.findByC_P(
			group.getCompanyId(), String.valueOf(groupId)).iterator();

		while (itr.hasNext()) {
			Resource resource = itr.next();

			resourceLocalService.deleteResource(resource);
		}

		if (!group.isStagingGroup() &&
			(group.isCommunity() || group.isOrganization())) {

			resourceLocalService.deleteResource(
				group.getCompanyId(), Group.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, group.getGroupId());
		}

		// Group

		groupPersistence.remove(group);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public Group getCompanyGroup(long companyId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Company.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, companyId);
	}

	public List<Group> getCompanyGroups(long companyId, int start, int end)
		throws SystemException {

		return groupPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyGroupsCount(long companyId) throws SystemException {
		return groupPersistence.countByCompanyId(companyId);
	}

	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchGroupException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.findByC_F(companyId, friendlyURL);
	}

	@ThreadLocalCachable
	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return groupPersistence.findByPrimaryKey(groupId);
	}

	public Group getGroup(long companyId, String name)
		throws PortalException, SystemException {

		Group group = _systemGroupsMap.get(companyId + name);

		if (group != null) {
			return group;
		}

		return groupPersistence.findByC_N(companyId, name);
	}

	public List<Group> getGroups(long[] groupIds)
		throws PortalException, SystemException {

		List<Group> groups = new ArrayList<Group>(groupIds.length);

		for (long groupId : groupIds) {
			Group group = getGroup(groupId);

			groups.add(group);
		}

		return groups;
	}

	public Group getLayoutGroup(long companyId, long plid)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Layout.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, plid);
	}

	public Group getLayoutPrototypeGroup(long companyId, long layoutPrototypeId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(LayoutPrototype.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, layoutPrototypeId);
	}

	public Group getLayoutSetPrototypeGroup(
			long companyId, long layoutSetPrototypeId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(LayoutSetPrototype.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, layoutSetPrototypeId);
	}

	public List<Group> getLiveGroups() throws SystemException {
		return groupFinder.findByLiveGroups();
	}

	public List<Group> getNoLayoutsGroups(
			String className, boolean privateLayout, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return groupFinder.findByNoLayouts(
			classNameId, privateLayout, start, end);
	}

	public List<Group> getNullFriendlyURLGroups() throws SystemException {
		return groupFinder.findByNullFriendlyURL();
	}

	public Group getOrganizationGroup(long companyId, long organizationId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(Organization.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, organizationId);
	}

	public List<Group> getOrganizationsGroups(
		List<Organization> organizations) {

		List<Group> organizationGroups = new ArrayList<Group>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			Group group = organization.getGroup();

			organizationGroups.add(group);
		}

		return organizationGroups;
	}

	public List<Group> getOrganizationsRelatedGroups(
			List<Organization> organizations)
		throws SystemException {

		List<Group> organizationGroups = new ArrayList<Group>();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			List<Group> groups = organizationPersistence.getGroups(
				organization.getOrganizationId());

			organizationGroups.addAll(groups);
		}

		return organizationGroups;
	}

	public List<Group> getRoleGroups(long roleId) throws SystemException {
		return rolePersistence.getGroups(roleId);
	}

	public Group getStagingGroup(long liveGroupId)
		throws PortalException, SystemException {

		return groupPersistence.findByLiveGroupId(liveGroupId);
	}

	public Group getUserGroup(long companyId, long userId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(User.class);

		return groupPersistence.findByC_C_C(companyId, classNameId, userId);
	}

	public Group getUserGroupGroup(long companyId, long userGroupId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(UserGroup.class);

		return groupPersistence.findByC_C_C(
			companyId, classNameId, userGroupId);
	}

	public List<Group> getUserGroups(long userId)
		throws PortalException, SystemException {

		return getUserGroups(userId, false);
	}

	public List<Group> getUserGroups(long userId, boolean inherit)
		throws PortalException, SystemException {

		return getUserGroups(
			userId, inherit, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Group> getUserGroups(
			long userId, boolean inherit, int start, int end)
		throws PortalException, SystemException {

		if (inherit) {
			User user = userPersistence.findByPrimaryKey(userId);

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(userId));

			return search(
				user.getCompanyId(), null, null, groupParams, start, end);
		}
		else {
			return userPersistence.getGroups(userId);
		}
	}

	public List<Group> getUserGroups(long userId, int start, int end)
		throws PortalException, SystemException {

		return getUserGroups(userId, false, start, end);
	}

	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException, SystemException {

		List<Group> userGroupGroups = new ArrayList<Group>();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			Group group = userGroup.getGroup();

			userGroupGroups.add(group);
		}

		return userGroupGroups;
	}

	public List<Group> getUserGroupsRelatedGroups(List<UserGroup> userGroups)
		throws SystemException {

		List<Group> userGroupGroups = new ArrayList<Group>();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			List<Group> groups = userGroupPersistence.getGroups(
				userGroup.getUserGroupId());

			userGroupGroups.addAll(groups);
		}

		return userGroupGroups;
	}

	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<Group> userOrgsGroups = new UniqueList<Group>();

		List<Organization> userOrgs =
			organizationLocalService.getUserOrganizations(
				userId, true, start, end);

		for (Organization organization : userOrgs) {
			userOrgsGroups.add(0, organization.getGroup());

			if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
				for (Organization ancestorOrganization :
						organization.getAncestors()) {

					userOrgsGroups.add(0, ancestorOrganization.getGroup());
				}
			}
		}

		return userOrgsGroups;
	}

	public boolean hasRoleGroup(long roleId, long groupId)
		throws SystemException {

		return rolePersistence.containsGroup(roleId, groupId);
	}

	public boolean hasStagingGroup(long liveGroupId) throws SystemException {
		if (groupPersistence.fetchByLiveGroupId(liveGroupId) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws SystemException {

		if (groupFinder.countByG_U(groupId, userId) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<Group> search(
			long companyId, long[] classNameIds, String name,
			String description,	LinkedHashMap<String, Object> params, int start,
			int end)
		throws SystemException {

		return search(
			companyId, classNameIds, name, description, params, start, end,
			null);
	}

	public List<Group> search(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_C_N_D(
			companyId, classNameIds, name, realName, description, params, start,
			end, obc);
	}

	public List<Group> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end)
		throws SystemException {

		return search(companyId, name, description, params, start, end, null);
	}

	public List<Group> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		String realName = getRealName(companyId, name);

		return groupFinder.findByC_N_D(
			companyId, name, realName, description, params, start, end, obc);
	}

	@ThreadLocalCachable
	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_N_D(
			companyId, name, realName, description, params);
	}

	@ThreadLocalCachable
	public int searchCount(
			long companyId, long[] classNameIds, String name,
			String description, LinkedHashMap<String, Object> params)
		throws SystemException {

		String realName = getRealName(companyId, name);

		return groupFinder.countByC_C_N_D(
			companyId, classNameIds, name, realName, description, params);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.setGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.removeGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserGroups(long userId, long[] groupIds)
		throws SystemException {

		userGroupRoleLocalService.deleteUserGroupRoles(userId, groupIds);

		userPersistence.removeGroups(userId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void updateAsset(
			long userId, Group group, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), Group.class.getName(),
			group.getGroupId(), null, assetCategoryIds, assetTagNames, false,
			null, null, null, null, null, group.getDescriptiveName(),
			group.getDescription(), null, null, 0, 0, null, false);
	}

	public Group updateFriendlyURL(long groupId, String friendlyURL)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (group.isUser()) {
			User user = userPersistence.findByPrimaryKey(group.getClassPK());

			friendlyURL = StringPool.SLASH + user.getScreenName();

			if (group.getFriendlyURL().equals(friendlyURL)) {
				return group;
			}
		}

		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, group.getClassNameId(),
			group.getClassPK(), StringPool.BLANK, friendlyURL);

		validateFriendlyURL(
			group.getCompanyId(), group.getGroupId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		group.setFriendlyURL(friendlyURL);

		groupPersistence.update(group, false);

		return group;
	}

	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		group.setTypeSettings(typeSettings);

		groupPersistence.update(group, false);

		return group;
	}

	public Group updateGroup(
			long groupId, String name, String description, int type,
			String friendlyURL, boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String className = group.getClassName();
		long classNameId = group.getClassNameId();
		long classPK = group.getClassPK();
		friendlyURL = getFriendlyURL(
			group.getCompanyId(), groupId, classNameId, classPK,
			StringPool.BLANK, friendlyURL);

		if ((classNameId <= 0) || className.equals(Group.class.getName())) {
			validateName(group.getGroupId(), group.getCompanyId(), name);
		}
		else {
			name = String.valueOf(classPK);
		}

		if (PortalUtil.isSystemGroup(group.getName()) &&
			!group.getName().equals(name)) {

			throw new RequiredGroupException();
		}

		validateFriendlyURL(
			group.getCompanyId(), group.getGroupId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

		groupPersistence.update(group, false);

		// Asset

		if ((serviceContext != null) && group.isCommunity()) {
			User user = null;

			try {
				user = userPersistence.findByPrimaryKey(
					group.getCreatorUserId());

			}
			catch (NoSuchUserException nsue1) {
				try {
					user = userPersistence.findByPrimaryKey(
						serviceContext.getUserId());
				}
				catch (NoSuchUserException nsue2) {
					user = userLocalService.getDefaultUser(
						group.getCompanyId());
				}
			}

			updateAsset(
				user.getUserId(), group, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		return group;
	}

	public Group updateWorkflow(
			long groupId, boolean workflowEnabled, int workflowStages,
			String workflowRoleNames)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"workflowEnabled", String.valueOf(workflowEnabled));

		if (workflowEnabled) {
			if (workflowStages < PropsValues.TASKS_DEFAULT_STAGES) {
				workflowStages = PropsValues.TASKS_DEFAULT_STAGES;
			}

			if (Validator.isNull(workflowRoleNames)) {
				workflowRoleNames = PropsValues.TASKS_DEFAULT_ROLE_NAMES;
			}

			typeSettingsProperties.setProperty(
				"workflowStages", String.valueOf(workflowStages));
			typeSettingsProperties.setProperty(
				"workflowRoleNames", workflowRoleNames);
		}

		group.setTypeSettings(group.getTypeSettings());

		groupPersistence.update(group, false);

		if (!workflowEnabled) {
			tasksProposalLocalService.deleteProposals(groupId);
		}

		return group;
	}

	protected void addControlPanelLayouts(Group group)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		String friendlyURL = getFriendlyURL(
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.CONTROL_PANEL_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTROL_PANEL, false,
			friendlyURL, serviceContext);
	}

	protected void addDefaultGuestPublicLayoutByProperties(Group group)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());
		String friendlyURL = getFriendlyURL(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			0, PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_TEMPLATE_ID, false);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsKeys.DEFAULT_GUEST_PUBLIC_LAYOUT_COLUMN + i);

			layoutTypePortlet.addPortletIds(
				0, StringUtil.split(portletIds), columnId, false);
		}

		layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		boolean updateLayoutSet = false;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID)) {

			layoutSet.setThemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.
					DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID)) {

			layoutSet.setColorSchemeId(
				PropsValues.
					DEFAULT_GUEST_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID)) {

			layoutSet.setWapThemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID)) {

			layoutSet.setWapColorSchemeId(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (updateLayoutSet) {
			layoutSetLocalService.updateLayoutSet(layoutSet);
		}
	}

	protected void addDefaultGuestPublicLayouts(Group group)
		throws PortalException, SystemException {

		if (publicLARFile != null) {
			addDefaultGuestPublicLayoutsByLAR(group, publicLARFile);
		}
		else {
			addDefaultGuestPublicLayoutByProperties(group);
		}
	}

	protected void addDefaultGuestPublicLayoutsByLAR(Group group, File larFile)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		layoutLocalService.importLayouts(
			defaultUserId, group.getGroupId(), false, parameterMap, larFile);
	}

	protected String getFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyName, String friendlyURL)
		throws PortalException, SystemException {

		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNull(friendlyURL)) {
			friendlyURL = StringPool.SLASH + getFriendlyURL(friendlyName);

			String originalFriendlyURL = friendlyURL;

			for (int i = 1;; i++) {
				try {
					validateFriendlyURL(
						companyId, groupId, classNameId, classPK, friendlyURL);

					break;
				}
				catch (GroupFriendlyURLException gfurle) {
					int type = gfurle.getType();

					if (type == GroupFriendlyURLException.DUPLICATE) {
						friendlyURL = originalFriendlyURL + i;
					}
					else {
						friendlyURL = StringPool.SLASH + classPK;

						break;
					}
				}
			}
		}

		return friendlyURL;
	}

	protected String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizer.normalize(friendlyURL);
	}

	protected String getRealName(long companyId, String name)
		throws SystemException {

		if (Validator.isNull(name)) {
			return name;
		}

		String realName = name;

		try {
			Company company = companyLocalService.getCompany(companyId);

			Account account = company.getAccount();

			String companyName = account.getName();

			name = StringUtil.replace(
				name, StringPool.PERCENT, StringPool.BLANK);

			if (companyName.indexOf(name) != -1) {
				realName = StringPool.PERCENT + GroupConstants.GUEST +
					StringPool.PERCENT;
			}
		}
		catch (PortalException pe) {
		}

		return realName;
	}

	protected void initImportLARFile() {
		String publicLARFileName = PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading public LAR file " + publicLARFileName);
		}

		if (Validator.isNotNull(publicLARFileName)) {
			publicLARFile = new File(publicLARFileName);

			if (!publicLARFile.exists()) {
				_log.error(
					"Public LAR file " + publicLARFile + " does not exist");

				publicLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using public LAR file " + publicLARFileName);
				}
			}
		}
	}

	protected boolean isStaging(ServiceContext serviceContext) {
		if (serviceContext != null) {
			return ParamUtil.getBoolean(serviceContext, "staging");
		}

		return false;
	}

	protected void unscheduleStaging(Group group) {
		try {

			// Remote publishing

			String groupName = StagingUtil.getSchedulerGroupName(
				DestinationNames.LAYOUTS_REMOTE_PUBLISHER, group.getGroupId());

			SchedulerEngineUtil.unschedule(groupName);

			long liveGroupId = 0;
			long stagingGroupId = 0;

			if (group.isStagingGroup()) {
				liveGroupId = group.getLiveGroupId();

				stagingGroupId = group.getGroupId();
			}
			else if (group.hasStagingGroup()) {
				liveGroupId = group.getGroupId();

				stagingGroupId = group.getStagingGroup().getGroupId();
			}

			if ((liveGroupId != 0) && (stagingGroupId != 0)) {

				// Publish to live

				groupName = StagingUtil.getSchedulerGroupName(
					DestinationNames.LAYOUTS_LOCAL_PUBLISHER, liveGroupId);

				SchedulerEngineUtil.unschedule(groupName);

				// Copy from live

				groupName = StagingUtil.getSchedulerGroupName(
					DestinationNames.LAYOUTS_LOCAL_PUBLISHER, stagingGroupId);

				SchedulerEngineUtil.unschedule(groupName);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to unschedule events for group: " + group.getGroupId());
		}
	}

	protected void validateFriendlyURL(
			long companyId, long groupId, long classNameId, long classPK,
			String friendlyURL)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (company.isSystem()) {
			return;
		}

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new GroupFriendlyURLException(exceptionType);
		}

		Group group = groupPersistence.fetchByC_F(companyId, friendlyURL);

		if ((group != null) && (group.getGroupId() != groupId)) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.DUPLICATE);
		}

		String groupIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(groupIdFriendlyURL)) {
			long groupClassNameId = PortalUtil.getClassNameId(Group.class);

			if (((classNameId != groupClassNameId) &&
				 (!groupIdFriendlyURL.equals(String.valueOf(classPK))) &&
				 (!PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC)) ||
				((classNameId == groupClassNameId) &&
				 (!groupIdFriendlyURL.equals(String.valueOf(groupId))))) {

				GroupFriendlyURLException gfurle =
					new GroupFriendlyURLException(
						GroupFriendlyURLException.POSSIBLE_DUPLICATE);

				gfurle.setKeywordConflict(groupIdFriendlyURL);

				throw gfurle;
			}
		}

		String screenName = friendlyURL.substring(1);

		User user = userPersistence.fetchByC_SN(companyId, screenName);

		if (user != null) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);

			if ((classNameId == userClassNameId) &&
				(classPK == user.getUserId())) {
			}
			else {
				throw new GroupFriendlyURLException(
					GroupFriendlyURLException.DUPLICATE);
			}
		}

		if (StringUtil.count(friendlyURL, StringPool.SLASH) > 1) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.TOO_DEEP);
		}
	}

	protected void validateName(long groupId, long companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(CharPool.STAR) != -1)) {

			throw new GroupNameException();
		}

		try {
			Group group = groupFinder.findByC_N(companyId, name);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchGroupException nsge) {
		}
	}

	protected File publicLARFile;

	private static Log _log = LogFactoryUtil.getLog(
		GroupLocalServiceImpl.class);

	private Map<String, Group> _systemGroupsMap = new HashMap<String, Group>();

}