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

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.PermissionsListFilter;
import com.liferay.portal.security.permission.PermissionsListFilterFactory;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourceLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.ResourceComparator;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="ResourceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 *
 */
public class ResourceLocalServiceImpl extends ResourceLocalServiceBaseImpl {

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			long primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			communityPermissions, guestPermissions);
	}

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		validate(companyId, name, false);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		// Guest

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		addResource(
			companyId, name, ResourceConstants.SCOPE_GROUP,
			String.valueOf(guestGroup.getGroupId()));

		// Group

		if ((groupId > 0) && (guestGroup.getGroupId() != groupId)) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

			long defaultUserId = userLocalService.getDefaultUserId(
				companyId);

			PermissionsListFilter permissionsListFilter =
				PermissionsListFilterFactory.getInstance();

			// Permissions

			List<Permission> permissionsList =
				permissionLocalService.addPermissions(
					companyId, name, resource.getResourceId(), false);

			List<Permission> userPermissionsList =
				permissionsListFilter.filterUserPermissions(
					companyId, groupId, userId, name, primKey, false,
					permissionsList);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

				// Owner permissions

				Role ownerRole = roleLocalService.getRole(
					companyId, RoleConstants.OWNER);

				rolePersistence.addPermissions(
					ownerRole.getRoleId(), userPermissionsList);
			}
			else {

				// User permissions

				if ((userId > 0) && (userId != defaultUserId)) {
					userPersistence.addPermissions(userId, userPermissionsList);
				}
			}

			// Community permissions

			if (groupId > 0) {
				Group group = groupPersistence.findByPrimaryKey(groupId);

				if (communityPermissions == null) {
					communityPermissions = new String[0];
				}

				List<Permission> communityPermissionsList =
					permissionLocalService.getPermissions(
						companyId, communityPermissions,
						resource.getResourceId());

				communityPermissionsList =
					permissionsListFilter.filterCommunityPermissions(
						companyId, groupId, userId, name, primKey, false,
						communityPermissionsList);

				if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
					Role role = null;

					if (group.isLayout()) {
						Layout layout = layoutLocalService.getLayout(
							group.getClassPK());

						group = layout.getGroup();
					}

					if (group.isCommunity()) {
						role = roleLocalService.getRole(
							companyId, RoleConstants.COMMUNITY_MEMBER);
					}
					else if (group.isOrganization()) {
						role = roleLocalService.getRole(
							companyId, RoleConstants.ORGANIZATION_MEMBER);
					}
					else if (group.isUser() || group.isUserGroup()) {
						role = roleLocalService.getRole(
							companyId, RoleConstants.POWER_USER);
					}

					rolePersistence.addPermissions(
						role.getRoleId(), communityPermissionsList);
				}
				else {
					groupPersistence.addPermissions(
						groupId, communityPermissionsList);
				}
			}

			// Guest permissions

			if (guestPermissions == null) {
				guestPermissions = new String[0];
			}

			List<Permission> guestPermissionsList =
				permissionLocalService.getPermissions(
					companyId, guestPermissions, resource.getResourceId());

			guestPermissionsList = permissionsListFilter.filterGuestPermissions(
				companyId, groupId, userId, name, primKey, false,
				guestPermissionsList);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

				// Guest permissions

				Role guestRole = roleLocalService.getRole(
					companyId, RoleConstants.GUEST);

				rolePersistence.addPermissions(
					guestRole.getRoleId(), guestPermissionsList);
			}
			else {
				userPersistence.addPermissions(
					defaultUserId, guestPermissionsList);
			}
		}
	}

	public Resource addResource(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		long codeId = resourceCode.getCodeId();

		Resource resource = resourcePersistence.fetchByC_P(
			codeId, primKey, false);

		if (resource == null) {
			long resourceId = counterLocalService.increment(
				Resource.class.getName());

			resource = resourcePersistence.create(resourceId);

			resource.setCodeId(codeId);
			resource.setPrimKey(primKey);

			try {
				resourcePersistence.update(resource, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {codeId=" + codeId + ", primKey=" +
							primKey + "}");
				}

				resource = resourcePersistence.fetchByC_P(
					codeId, primKey, false);

				if (resource == null) {
					throw se;
				}
			}
		}

		return resource;
	}

	public void addResources(
			long companyId, long groupId, String name, boolean portletActions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, 0, name, null, portletActions, false, false);
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			long primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			portletActions, addCommunityPermissions, addGuestPermissions);
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		validate(companyId, name, portletActions);

		logAddResources(name, primKey, stopWatch, 1);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		logAddResources(name, primKey, stopWatch, 2);

		if (groupId > 0) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		logAddResources(name, primKey, stopWatch, 3);

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

			logAddResources(name, primKey, stopWatch, 4);

			// Permissions

			List<Permission> permissionsList =
				permissionLocalService.addPermissions(
					companyId, name, resource.getResourceId(), portletActions);

			logAddResources(name, primKey, stopWatch, 5);

			PermissionsListFilter permissionsListFilter =
				PermissionsListFilterFactory.getInstance();

			List<Permission> userPermissionsList =
				permissionsListFilter.filterUserPermissions(
					companyId, groupId, userId, name, primKey,
					portletActions, permissionsList);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

				// Owner permissions

				Role ownerRole = roleLocalService.getRole(
					companyId, RoleConstants.OWNER);

				rolePersistence.addPermissions(
					ownerRole.getRoleId(), userPermissionsList);
			}
			else {

				// User permissions

				long defaultUserId = userLocalService.getDefaultUserId(
					companyId);

				if ((userId > 0) && (userId != defaultUserId)) {
					userPersistence.addPermissions(userId, userPermissionsList);
				}
			}

			logAddResources(name, primKey, stopWatch, 6);

			// Community permissions

			if ((groupId > 0) && addCommunityPermissions) {
				addCommunityPermissions(
					companyId, groupId, userId, name, resource, portletActions);
			}

			logAddResources(name, primKey, stopWatch, 7);

			// Guest permissions

			if (addGuestPermissions) {

				// Don't add guest permissions when you've already added
				// community permissions and the given community is the guest
				// community.

				addGuestPermissions(
					companyId, groupId, userId, name, resource, portletActions);
			}

			logAddResources(name, primKey, stopWatch, 8);
		}
	}

	public void deleteResource(long resourceId) throws SystemException {
		try {
			Resource resource = resourcePersistence.findByPrimaryKey(
				resourceId);

			deleteResource(resource);
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	public void deleteResource(Resource resource) throws SystemException {

		// Permissions

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resource.getResourceId());

		for (Permission permission : permissions) {
			orgGroupPermissionPersistence.removeByPermissionId(
				permission.getPermissionId());
		}

		permissionPersistence.removeByResourceId(resource.getResourceId());

		// Resource

		resourcePersistence.remove(resource);
	}

	public void deleteResource(
			long companyId, String name, int scope, long primKey)
		throws PortalException, SystemException {

		deleteResource(companyId, name, scope, String.valueOf(primKey));
	}

	public void deleteResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		try {
			Resource resource = getResource(companyId, name, scope, primKey);

			deleteResource(resource.getResourceId());
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	public void deleteResources(String name) throws SystemException {
		List<Resource> resources = resourceFinder.findByName(name);

		for (Resource resource : resources) {
			deleteResource(resource);
		}
	}

	public long getLatestResourceId() throws SystemException {
		List<Resource> resources = resourcePersistence.findAll(
			0, 1, new ResourceComparator());

		if (resources.size() == 0) {
			return 0;
		}
		else {
			Resource resource = resources.get(0);

			return resource.getResourceId();
		}
	}

	public Resource getResource(long resourceId)
		throws PortalException, SystemException {

		return resourcePersistence.findByPrimaryKey(resourceId);
	}

	public Resource getResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		return resourcePersistence.findByC_P(resourceCode.getCodeId(), primKey);
	}

	public List<Resource> getResources() throws SystemException {
		return resourcePersistence.findAll();
	}

	public void updateResources(
			long companyId, long groupId, String name, long primKey,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		updateResources(
			companyId, groupId, name, String.valueOf(primKey),
			communityPermissions, guestPermissions);
	}

	public void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		Role role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		permissionService.setRolePermissions(
			role.getRoleId(), groupId, guestPermissions,
			resource.getResourceId());

		Group group = groupLocalService.getGroup(groupId);

		if (group.isLayout()) {
			Layout layout = layoutLocalService.getLayout(
				group.getClassPK());

			group = layout.getGroup();
		}

		if (group.isCommunity()) {
			role = roleLocalService.getRole(
				companyId, RoleConstants.COMMUNITY_MEMBER);
		}
		else if (group.isOrganization()) {
			role = roleLocalService.getRole(
				companyId, RoleConstants.ORGANIZATION_MEMBER);
		}
		else if (group.isUser() || group.isUserGroup()) {
			role = roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);
		}

		if (communityPermissions == null) {
			communityPermissions = new String[0];
		}

		permissionService.setRolePermissions(
			role.getRoleId(), groupId, communityPermissions,
			resource.getResourceId());
	}

	protected void addCommunityPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long resourceId = resource.getResourceId();
		String primKey = resource.getPrimKey();

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 1);

		List<String> actions = null;

		if (portletActions) {
			actions =
				ResourceActionsUtil.getPortletResourceCommunityDefaultActions(
					name);
		}
		else {
			actions =
				ResourceActionsUtil.getModelResourceCommunityDefaultActions(
					name);
		}

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 2);

		String[] actionIds = actions.toArray(new String[actions.size()]);

		List<Permission> communityPermissionsList =
			permissionLocalService.getPermissions(
				group.getCompanyId(), actionIds, resourceId);

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 3);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		communityPermissionsList =
			permissionsListFilter.filterCommunityPermissions(
				companyId, groupId, userId, name, primKey, portletActions,
				communityPermissionsList);

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 4);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role role = null;

			if (group.isLayout()) {
				Layout layout = layoutLocalService.getLayout(
					group.getClassPK());

				group = layout.getGroup();
			}

			if (group.isCommunity()) {
				role = roleLocalService.getRole(
					companyId, RoleConstants.COMMUNITY_MEMBER);
			}
			else if (group.isOrganization()) {
				role = roleLocalService.getRole(
					companyId, RoleConstants.ORGANIZATION_MEMBER);
			}
			else if (group.isUser() || group.isUserGroup()) {
				role = roleLocalService.getRole(
					companyId, RoleConstants.POWER_USER);
			}

			rolePersistence.addPermissions(
				role.getRoleId(), communityPermissionsList);
		}
		else {
			groupPersistence.addPermissions(groupId, communityPermissionsList);
		}

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 5);
	}

	protected void addGuestPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceGuestDefaultActions(
				name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceGuestDefaultActions(
				name);
		}

		String[] actionIds = actions.toArray(new String[actions.size()]);

		List<Permission> guestPermissionsList =
			permissionLocalService.getPermissions(
				companyId, actionIds, resource.getResourceId());

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		guestPermissionsList =
			permissionsListFilter.filterGuestPermissions(
				companyId, groupId, userId, name, resource.getPrimKey(),
				portletActions, guestPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role guestRole = roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			rolePersistence.addPermissions(
				guestRole.getRoleId(), guestPermissionsList);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			userPersistence.addPermissions(defaultUserId, guestPermissionsList);
		}
	}

	protected void logAddCommunityPermissions(
		long groupId, String name, long resourceId, StopWatch stopWatch,
		int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Adding community permissions block " + block + " for " + groupId +
				" " + name + " " + resourceId + " takes " +
					stopWatch.getTime() + " ms");
	}

	protected void logAddResources(
		String name, String primKey, StopWatch stopWatch, int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Adding resources block " + block + " for " + name + " " + primKey +
				" takes " + stopWatch.getTime() + " ms");
	}

	protected void validate(
			long companyId, String name, boolean portletActions)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceActions(
				companyId, name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceActions(name);
		}

		if (actions.size() == 0) {
			throw new ResourceActionsException(
				"There are no actions associated with the resource " + name);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ResourceLocalServiceImpl.class);

}