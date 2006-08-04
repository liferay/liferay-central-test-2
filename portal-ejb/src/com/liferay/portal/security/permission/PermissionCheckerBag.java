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

package com.liferay.portal.security.permission;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.GroupServiceUtil;
import com.liferay.portal.service.spring.PermissionLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.RoleServiceUtil;
import com.liferay.portal.service.spring.UserServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.StringPool;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.List;
import java.util.Map;

/**
 * <a href="PermissionCheckerBag.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionCheckerBag implements Serializable {

	public PermissionCheckerBag() {
	}

	public PermissionCheckerBag(String userId, List userOrgs,
								List userOrgGroups, List userUserGroupGroups) {

		_userId = userId;
		_userOrgs = userOrgs;
		_userOrgGroups = userOrgGroups;
		_userUserGroupGroups = userUserGroupGroups;
	}

	public List getUserOrgs() {
		return _userOrgs;
	}

	public List getUserOrgGroups() {
		return _userOrgGroups;
	}

	public List getUserUserGroupGroups() {
		return _userUserGroupGroups;
	}

	public boolean hasGroup(String groupId)
		throws PortalException, SystemException {

		String key = groupId;

		Boolean value = (Boolean)_hasGroup.get(key);

		if (value == null) {
			value = new Boolean(
				GroupLocalServiceUtil.hasUserGroup(_userId, groupId));

			_hasGroup.put(key, value);
		}

		return value.booleanValue();
	}

	public boolean isCompanyAdmin(String companyId)
		throws PortalException, RemoteException, SystemException {

		String key = companyId;

		Boolean value = (Boolean)_isCompanyAdmin.get(key);

		if (value == null) {
			Role adminRole = RoleServiceUtil.getRole(
				companyId, Role.ADMINISTRATOR);

			value = new Boolean(
				UserServiceUtil.hasRoleUser(adminRole.getRoleId(), _userId));

			_isCompanyAdmin.put(key, value);
		}

		return value.booleanValue();
	}

	public boolean isCommunityAdmin(
			String companyId, String groupId, String name)
		throws PortalException, RemoteException, SystemException {

		String key =
			companyId + StringPool.PIPE + groupId + StringPool.PIPE + name;

		Boolean value = (Boolean)_isCommunityAdmin.get(key);

		if (value == null) {
			value = new Boolean(isCommunityAdminImpl(companyId, groupId, name));

			_isCommunityAdmin.put(key, value);
		}

		return value.booleanValue();
	}

	protected boolean isCommunityAdminImpl(
			String companyId, String groupId, String name)
		throws PortalException, RemoteException, SystemException {

		if (groupId == null) {
			return false;
		}

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				companyId, Group.class.getName(), Resource.TYPE_CLASS,
				Resource.SCOPE_INDIVIDUAL, groupId);

			if (PermissionLocalServiceUtil.hasUserPermission(
					_userId, ActionKeys.ADMINISTRATE,
					resource.getResourceId())) {

				return true;
			}
		}
		catch (NoSuchResourceException nsre) {
		}

		try {
			Group group = GroupServiceUtil.getGroup(groupId);

			if (group.getClassName().equals(User.class.getName()) &&
				group.getClassPK().equals(_userId)) {

				return true;
			}
		}
		catch (NoSuchGroupException nsge) {
		}

		return false;
	}

	private String _userId;
	private List _userOrgs;
	private List _userOrgGroups;
	private List _userUserGroupGroups;
	private Map _hasGroup = CollectionFactory.getHashMap();
	private Map _isCompanyAdmin = CollectionFactory.getHashMap();
	private Map _isCommunityAdmin = CollectionFactory.getHashMap();

}