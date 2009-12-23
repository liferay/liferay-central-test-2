/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.DuplicateUserGroupException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredUserGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserGroupNameException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.lar.UserIdStrategy;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.UserGroupLocalServiceBaseImpl;
import com.liferay.portlet.enterpriseadmin.util.UserIndexer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="UserGroupLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 */
public class UserGroupLocalServiceImpl extends UserGroupLocalServiceBaseImpl {

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException, SystemException {

		groupPersistence.addUserGroups(groupId, userGroupIds);

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Role role = rolePersistence.findByC_N(
			group.getCompanyId(), RoleConstants.COMMUNITY_MEMBER);

		for (long userGroupId : userGroupIds) {
			userGroupGroupRoleLocalService.addUserGroupGroupRoles(
				userGroupId, groupId, new long[] {role.getRoleId()});
		}

		PermissionCacheUtil.clearCache();
	}

	public UserGroup addUserGroup(
			long userId, long companyId, String name, String description)
		throws PortalException, SystemException {

		// User Group

		validate(0, companyId, name);

		long userGroupId = counterLocalService.increment();

		UserGroup userGroup = userGroupPersistence.create(userGroupId);

		userGroup.setCompanyId(companyId);
		userGroup.setParentUserGroupId(
			UserGroupConstants.DEFAULT_PARENT_USER_GROUP_ID);
		userGroup.setName(name);
		userGroup.setDescription(description);

		userGroupPersistence.update(userGroup, false);

		// Group

		groupLocalService.addGroup(
			userId, UserGroup.class.getName(), userGroup.getUserGroupId(),
			String.valueOf(userGroupId), null, 0, null, true, null);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, userId, UserGroup.class.getName(),
			userGroup.getUserGroupId(), false, false, false);

		return userGroup;
	}

	public void clearUserUserGroups(long userId) throws SystemException {
		userPersistence.clearUserGroups(userId);

		PermissionCacheUtil.clearCache();
	}

	public void copyUserGroupLayouts(long userGroupId, long userIds[])
		throws PortalException, SystemException {

		for (long userId : userIds) {
			if (!userGroupPersistence.containsUser(userGroupId, userId)) {
				copyUserGroupLayouts(userGroupId, userId);
			}
		}
	}

	public void copyUserGroupLayouts(long userGroupIds[], long userId)
		throws PortalException, SystemException {

		for (long userGroupId : userGroupIds) {
			if (!userGroupPersistence.containsUser(userGroupId, userId)) {
				copyUserGroupLayouts(userGroupId, userId);
			}
		}
	}

	public void copyUserGroupLayouts(long userGroupId, long userId)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupLocalService.getUserGroup(userGroupId);
		User user = userPersistence.findByPrimaryKey(userId);

		Map<String, String[]> parameterMap = getLayoutTemplatesParameters();

		if (userGroup.hasPrivateLayouts()) {
			long sourceGroupId = userGroup.getGroup().getGroupId();
			long targetGroupId = user.getGroup().getGroupId();

			byte[] bytes = layoutLocalService.exportLayouts(
				sourceGroupId, true, parameterMap, null, null);

			UnsyncByteArrayInputStream ubais = new UnsyncByteArrayInputStream(
				bytes);

			layoutLocalService.importLayouts(
				userId, targetGroupId, true, parameterMap, ubais);
		}

		if (userGroup.hasPublicLayouts()) {
			long sourceGroupId = userGroup.getGroup().getGroupId();
			long targetGroupId = user.getGroup().getGroupId();

			byte[] bytes = layoutLocalService.exportLayouts(
				sourceGroupId, false, parameterMap, null, null);

			UnsyncByteArrayInputStream ubais = new UnsyncByteArrayInputStream(
				bytes);

			layoutLocalService.importLayouts(
				userId, targetGroupId, false, parameterMap, ubais);
		}
	}

	public void deleteUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		if (userLocalService.getUserGroupUsersCount(userGroupId, true) > 0) {
			throw new RequiredUserGroupException();
		}

		// Users

		clearUserUserGroups(userGroupId);

		// Group

		Group group = userGroup.getGroup();

		groupLocalService.deleteGroup(group.getGroupId());

		// User group roles

		userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(
			userGroupId);

		// Resources

		resourceLocalService.deleteResource(
			userGroup.getCompanyId(), UserGroup.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, userGroup.getUserGroupId());

		// User Group

		userGroupPersistence.remove(userGroupId);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public UserGroup getUserGroup(long userGroupId)
		throws PortalException, SystemException {

		return userGroupPersistence.findByPrimaryKey(userGroupId);
	}

	public UserGroup getUserGroup(long companyId, String name)
		throws PortalException, SystemException {

		return userGroupPersistence.findByC_N(companyId, name);
	}

	public List<UserGroup> getUserGroups(long companyId)
		throws SystemException {

		return userGroupPersistence.findByCompanyId(companyId);
	}

	public List<UserGroup> getUserGroups(long[] userGroupIds)
		throws PortalException, SystemException {

		List<UserGroup> userGroups = new ArrayList<UserGroup>(
			userGroupIds.length);

		for (long userGroupId : userGroupIds) {
			UserGroup userGroup = getUserGroup(userGroupId);

			userGroups.add(userGroup);
		}

		return userGroups;
	}

	public List<UserGroup> getUserUserGroups(long userId)
		throws SystemException {

		return userPersistence.getUserGroups(userId);
	}

	public boolean hasGroupUserGroup(long groupId, long userGroupId)
		throws SystemException {

		return groupPersistence.containsUserGroup(groupId, userGroupId);
	}

	public List<UserGroup> search(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return userGroupFinder.findByC_N_D(
			companyId, name, description, params, start, end, obc);
	}

	public int searchCount(
			long companyId, String name, String description,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		return userGroupFinder.countByC_N_D(
			companyId, name, description, params);
	}

	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws PortalException, SystemException {

		copyUserGroupLayouts(userGroupIds, userId);

		userPersistence.setUserGroups(userId, userGroupIds);

		try {
			UserIndexer.updateUsers(new long[] {userId});
		}
		catch (SearchException se) {
			_log.error("Indexing " + userId, se);
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws SystemException {

		userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(
			userGroupIds, groupId);

		groupPersistence.removeUserGroups(groupId, userGroupIds);

		PermissionCacheUtil.clearCache();
	}

	public UserGroup updateUserGroup(
			long companyId, long userGroupId, String name,
			String description)
		throws PortalException, SystemException {

		validate(userGroupId, companyId, name);

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		userGroup.setName(name);
		userGroup.setDescription(description);

		userGroupPersistence.update(userGroup, false);

		return userGroup;
	}

	protected Map<String, String[]> getLayoutTemplatesParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {PortletDataHandlerKeys.
				LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[] {PortletDataHandlerKeys.
				PORTLETS_MERGE_MODE_ADD_TO_BOTTOM});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	protected void validate(long userGroupId, long companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(StringPool.COMMA) != -1) ||
			(name.indexOf(StringPool.STAR) != -1)) {

			throw new UserGroupNameException();
		}

		try {
			UserGroup userGroup = userGroupFinder.findByC_N(companyId, name);

			if (userGroup.getUserGroupId() != userGroupId) {
				throw new DuplicateUserGroupException();
			}
		}
		catch (NoSuchUserGroupException nsuge) {
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserGroupLocalServiceImpl.class);

}