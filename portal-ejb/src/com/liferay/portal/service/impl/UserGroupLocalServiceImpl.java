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
import com.liferay.portal.DuplicateUserGroupException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredUserGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserGroupNameException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.UserGroupLocalService;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * <a href="UserGroupLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class UserGroupLocalServiceImpl implements UserGroupLocalService {

	public boolean addGroupUserGroups(
			String groupId, String[] userGroupIds)
		throws PortalException, SystemException {

		return GroupUtil.addUserGroups(groupId, userGroupIds);
	}

	public UserGroup addUserGroup(
			String userId, String companyId, String name, String description)
		throws PortalException, SystemException {

		// User Group

		validate(null, companyId, name);

		String userGroupId = Long.toString(CounterServiceUtil.increment(
			UserGroup.class.getName()));

		UserGroup userGroup = UserGroupUtil.create(userGroupId);

		userGroup.setCompanyId(companyId);
		userGroup.setParentUserGroupId(UserGroup.DEFAULT_PARENT_USER_GROUP_ID);
		userGroup.setName(name);
		userGroup.setDescription(description);

		UserGroupUtil.update(userGroup);

		// Group

		GroupLocalServiceUtil.addGroup(
			userId, UserGroup.class.getName(),
			userGroup.getPrimaryKey().toString(), null, null, null, null);

		// Resources

		ResourceLocalServiceUtil.addResources(
			companyId, null, userId, UserGroup.class.getName(),
			userGroup.getPrimaryKey().toString(), false, false, false);

		return userGroup;
	}

	public void deleteUserGroup(String userGroupId)
		throws PortalException, SystemException {

		UserGroup userGroup = UserGroupUtil.findByPrimaryKey(userGroupId);

		if (UserGroupUtil.containsUsers(userGroup.getUserGroupId())) {
			throw new RequiredUserGroupException();
		}

		// Group

		Group group = userGroup.getGroup();

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			userGroup.getCompanyId(), UserGroup.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			userGroup.getPrimaryKey().toString());

		// User Group

		UserGroupUtil.remove(userGroupId);
	}

	public UserGroup getUserGroup(String userGroupId)
		throws PortalException, SystemException {

		return UserGroupUtil.findByPrimaryKey(userGroupId);
	}

	public List getUserUserGroups(String userId)
		throws PortalException, SystemException {

		return UserUtil.getUserGroups(userId);
	}

	public boolean hasGroupUserGroup(String groupId, String userGroupId)
		throws PortalException, SystemException {

		return GroupUtil.containsUserGroup(groupId, userGroupId);
	}

	public List search(
			String companyId, String name, String description, Map params,
			int begin, int end)
		throws SystemException {

		return UserGroupFinder.findByC_N_D(
			companyId, name, description, params, begin, end);
	}

	public int searchCount(
			String companyId, String name, String description, Map params)
		throws SystemException {

		return UserGroupFinder.countByC_N_D(
			companyId, name, description, params);
	}

	public boolean unsetGroupUserGroups(String groupId, String[] userGroupIds)
		throws PortalException, SystemException {

		return GroupUtil.removeUserGroups(groupId, userGroupIds);
	}

	public UserGroup updateUserGroup(
			String companyId, String userGroupId, String name,
			String description)
		throws PortalException, SystemException {

		validate(userGroupId, companyId, name);

		UserGroup userGroup = UserGroupUtil.findByPrimaryKey(userGroupId);

		userGroup.setName(name);
		userGroup.setDescription(description);

		UserGroupUtil.update(userGroup);

		return userGroup;
	}

	protected void validate(String userGroupId, String companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(StringPool.COMMA) != -1) ||
			(name.indexOf(StringPool.STAR) != -1)) {

			throw new UserGroupNameException();
		}

		try {
			UserGroup userGroup = UserGroupFinder.findByC_N(companyId, name);

			if ((userGroupId == null) ||
				!userGroup.getUserGroupId().equals(userGroupId)) {

				throw new DuplicateUserGroupException();
			}
		}
		catch (NoSuchUserGroupException nsuge) {
		}
	}

}