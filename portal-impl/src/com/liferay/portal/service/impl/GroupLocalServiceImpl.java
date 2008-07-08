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

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.base.GroupLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.GroupNameComparator;
import com.liferay.util.Normalizer;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="GroupLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class GroupLocalServiceImpl extends GroupLocalServiceBaseImpl {

	public GroupLocalServiceImpl() {
		initImportLARFile();
	}

	public Group addGroup(
			long userId, String className, long classPK, String name,
			String description, int type, String friendlyURL, boolean active)
		throws PortalException, SystemException {

		return addGroup(
			userId, className, classPK, GroupImpl.DEFAULT_LIVE_GROUP_ID, name,
			description, type, friendlyURL, active);
	}

	public Group addGroup(
			long userId, String className, long classPK, long liveGroupId,
			String name, String description, int type, String friendlyURL,
			boolean active)
		throws PortalException, SystemException {

		// Group

		User user = userPersistence.findByPrimaryKey(userId);
		className = GetterUtil.getString(className);
		long classNameId = PortalUtil.getClassNameId(className);

		long groupId = counterLocalService.increment();

		friendlyURL = getFriendlyURL(groupId, classPK, friendlyURL);

		if ((classNameId <= 0) || (classPK <= 0)) {
			validateName(groupId, user.getCompanyId(), name);
		}

		validateFriendlyURL(
			groupId, user.getCompanyId(), classNameId, classPK, friendlyURL);

		Group group = groupPersistence.create(groupId);

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

		groupPersistence.update(group, false);

		// Layout sets

		layoutSetLocalService.addLayoutSet(groupId, true);

		layoutSetLocalService.addLayoutSet(groupId, false);

		if ((classNameId <= 0) && (classPK <= 0) && !user.isDefaultUser()) {

			// Resources

			resourceLocalService.addResources(
				group.getCompanyId(), 0, 0, Group.class.getName(),
				group.getGroupId(), false, false, false);

			// Community roles

			Role role = roleLocalService.getRole(
				group.getCompanyId(), RoleImpl.COMMUNITY_OWNER);

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});

			// User

			userLocalService.addGroupUsers(
				group.getGroupId(), new long[] {userId});
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

	public void addRoleGroups(long roleId, long[] groupIds)
		throws SystemException {

		rolePersistence.addGroups(roleId, groupIds);

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		userPersistence.addGroups(userId, groupIds);

		User user = userPersistence.findByPrimaryKey(userId);

		Role role = rolePersistence.findByC_N(
			user.getCompanyId(), RoleImpl.COMMUNITY_MEMBER);

		for (int i = 0; i < groupIds.length; i++) {
			long groupId = groupIds[i];

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, new long[] {role.getRoleId()});
		}

		PermissionCacheUtil.clearCache();
	}

	public void checkSystemGroups(long companyId)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (int i = 0; i < systemGroups.length; i++) {
			Group group = null;

			try {
				group = groupFinder.findByC_N(companyId, systemGroups[i]);
			}
			catch (NoSuchGroupException nsge) {
				String friendlyURL = null;

				if (systemGroups[i].equals(GroupImpl.GUEST)) {
					friendlyURL = "/guest";
				}

				group = addGroup(
					defaultUserId, null, 0, systemGroups[i], null,
					GroupImpl.TYPE_COMMUNITY_OPEN, friendlyURL, true);
			}

			if (group.getName().equals(GroupImpl.GUEST)) {
				LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
					group.getGroupId(), false);

				if (layoutSet.getPageCount() == 0) {
					addDefaultGuestPublicLayouts(group);
				}
			}
		}
	}

	public void deleteGroup(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (PortalUtil.isSystemGroup(group.getName())) {
			throw new RequiredGroupException();
		}

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

		// Role

		try {
			Role role = roleLocalService.getGroupRole(
				group.getCompanyId(), groupId);

			roleLocalService.deleteRole(role.getRoleId());
		}
		catch (NoSuchRoleException nsre) {
		}

		// Group roles

		userGroupRoleLocalService.deleteUserGroupRolesByGroupId(groupId);

		// Membership requests

		membershipRequestLocalService.deleteMembershipRequests(
			group.getGroupId());

		// Blogs

		blogsEntryLocalService.deleteEntries(groupId);
		blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);

		// Bookmarks

		bookmarksFolderLocalService.deleteFolders(groupId);

		// Calendar

		calEventLocalService.deleteEvents(groupId);

		// Document library

		dlFolderLocalService.deleteFolders(groupId);

		// Image gallery

		igFolderLocalService.deleteFolders(groupId);

		// Journal

		journalArticleLocalService.deleteArticles(groupId);
		journalTemplateLocalService.deleteTemplates(groupId);
		journalStructureLocalService.deleteStructures(groupId);

		// Message boards

		mbBanLocalService.deleteBansByGroupId(groupId);
		mbCategoryLocalService.deleteCategories(groupId);
		mbStatsUserLocalService.deleteStatsUserByGroupId(groupId);

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

		groupPersistence.remove(groupId);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchGroupException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		return groupPersistence.findByC_F(companyId, friendlyURL);
	}

	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return groupPersistence.findByPrimaryKey(groupId);
	}

	public Group getGroup(long companyId, String name)
		throws PortalException, SystemException {

		return groupFinder.findByC_N(companyId, name);
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

	public List<Group> getUserGroups(long userId) throws SystemException {
		return userPersistence.getGroups(userId);
	}

	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups) {
		List<Group> userGroupGroups = new ArrayList<Group>();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			Group group = userGroup.getGroup();

			userGroupGroups.add(group);
		}

		return userGroupGroups;
	}

	public boolean hasRoleGroup(long roleId, long groupId)
		throws SystemException {

		return rolePersistence.containsGroup(roleId, groupId);
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
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end)
		throws SystemException {

		return groupFinder.findByC_N_D(
			companyId, name, description, params, start, end, null);
	}

	public List<Group> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (obc == null) {
			obc = new GroupNameComparator(true);
		}

		return groupFinder.findByC_N_D(
			companyId, name, description, params, start, end, obc);
	}

	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return groupFinder.countByC_N_D(companyId, name, description, params);
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

		friendlyURL = getFriendlyURL(groupId, group.getClassPK(), friendlyURL);

		validateFriendlyURL(
			group.getGroupId(), group.getCompanyId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		group.setFriendlyURL(friendlyURL);

		groupPersistence.update(group, false);

		return group;
	}

	public Group updateGroup(
			long groupId, String name, String description, int type,
			String friendlyURL, boolean active)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long classNameId = group.getClassNameId();
		long classPK = group.getClassPK();
		friendlyURL = getFriendlyURL(groupId, classPK, friendlyURL);

		if ((classNameId <= 0) || (classPK <= 0)) {
			validateName(group.getGroupId(), group.getCompanyId(), name);
		}

		if (PortalUtil.isSystemGroup(group.getName()) &&
			!group.getName().equals(name)) {

			throw new RequiredGroupException();
		}

		validateFriendlyURL(
			group.getGroupId(), group.getCompanyId(), group.getClassNameId(),
			group.getClassPK(), friendlyURL);

		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setActive(active);

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

	public Group updateWorkflow(
			long groupId, boolean workflowEnabled, int workflowStages,
			String workflowRoleNames)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Properties props = group.getTypeSettingsProperties();

		props.setProperty("workflowEnabled", String.valueOf(workflowEnabled));

		if (workflowEnabled) {
			if (workflowStages < PropsValues.TASKS_DEFAULT_STAGES) {
				workflowStages = PropsValues.TASKS_DEFAULT_STAGES;
			}

			if (Validator.isNull(workflowRoleNames)) {
				workflowRoleNames = PropsValues.TASKS_DEFAULT_ROLE_NAMES;
			}

			props.setProperty("workflowStages", String.valueOf(workflowStages));
			props.setProperty("workflowRoleNames", workflowRoleNames);
		}

		group.setTypeSettings(group.getTypeSettings());

		groupPersistence.update(group, false);

		if (!workflowEnabled) {
			tasksProposalLocalService.deleteProposals(groupId);
		}

		return group;
	}

	protected void addDefaultGuestPublicLayoutByProperties(Group group)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());
		String friendlyURL = getFriendlyURL(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		Layout layout = layoutLocalService.addLayout(
			defaultUserId, group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL);

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
			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
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
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});

		LayoutLocalServiceUtil.importLayouts(
			defaultUserId, group.getGroupId(), false, parameterMap, larFile);
	}

	protected String getFriendlyURL(String friendlyURL) {
		friendlyURL = GetterUtil.getString(friendlyURL);

		return Normalizer.normalizeToAscii(friendlyURL.trim().toLowerCase());
	}

	protected String getFriendlyURL(
		long groupId, long classPK, String friendlyURL) {

		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNull(friendlyURL)) {
			if (classPK > 0) {
				friendlyURL = StringPool.SLASH + classPK;
			}
			else {
				friendlyURL = StringPool.SLASH + groupId;
			}
		}

		return friendlyURL;
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

	protected void validateFriendlyURL(
			long groupId, long companyId, long classNameId, long classPK,
			String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new GroupFriendlyURLException(exceptionType);
		}

		try {
			Group group = groupPersistence.findByC_F(companyId, friendlyURL);

			if (group.getGroupId() != groupId) {
				throw new GroupFriendlyURLException(
					GroupFriendlyURLException.DUPLICATE);
			}
		}
		catch (NoSuchGroupException nsge) {
		}

		String groupIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(groupIdFriendlyURL)) {
			if (((classPK > 0) &&
				 (!groupIdFriendlyURL.equals(String.valueOf(classPK)))) ||
				((classPK == 0) &&
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
			long userClassNameId = PortalUtil.getClassNameId(
				User.class.getName());

			if ((classNameId == userClassNameId) &&
				(classPK == user.getUserId())) {
			}
			else {
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
			Group group = groupFinder.findByC_N(companyId, name);

			if ((groupId <= 0) || (group.getGroupId() != groupId)) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchGroupException nsge) {
		}
	}

	protected File publicLARFile;

	private static Log _log = LogFactory.getLog(GroupLocalServiceImpl.class);

}