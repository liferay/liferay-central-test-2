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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrgGroupPermissionUtil;
import com.liferay.portal.service.persistence.PermissionUtil;
import com.liferay.portal.service.persistence.ResourceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.PermissionLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalService;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Wilson S. Man
 *
 */
public class ResourceLocalServiceImpl implements ResourceLocalService {

	public void addModelResources(
			String companyId, String groupId, String userId, String name,
			String primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		validate(companyId, name, false);

		// Company

		addResource(
			companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_COMPANY,
			companyId);

		// Guest

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, Group.GUEST);

		addResource(
			companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_GROUP,
			guestGroup.getGroupId());

		// Group

		if ((groupId != null) && !guestGroup.getGroupId().equals(groupId)) {
			addResource(
				companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_GROUP,
				groupId);
		}

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
				primKey);

			// Permissions

			List permissions = PermissionLocalServiceUtil.addPermissions(
				companyId, name, resource.getResourceId(), false);

			// User permissions

			if (userId != null) {
				UserUtil.addPermissions(userId, permissions);
			}

			// Community permissions

			if ((groupId != null) && communityPermissions != null) {
				addModelPermissions(
					groupId, resource.getResourceId(), communityPermissions);
			}

			// Guest permissions

			if (guestPermissions != null) {

				// Don't add guest permissions when you've already added
				// community permissions and the given community is the guest
				// community.

				if ((groupId == null) ||
					!guestGroup.getGroupId().equals(groupId)) {

					addModelPermissions(
						guestGroup.getGroupId(), resource.getResourceId(),
						guestPermissions);
				}
			}
		}
	}

	public Resource addResource(
			String companyId, String name, String typeId, String scope,
			String primKey)
		throws PortalException, SystemException {

		Resource resource = ResourceUtil.fetchByC_N_T_S_P(
			companyId, name, typeId, scope, primKey);

		if (resource == null) {
			String resourceId = Long.toString(CounterLocalServiceUtil.increment(
				Resource.class.getName()));

			resource = ResourceUtil.create(resourceId);
			resource.setCompanyId(companyId);
			resource.setName(name);
			resource.setTypeId(typeId);
			resource.setScope(scope);
			resource.setPrimKey(primKey);

			ResourceUtil.update(resource);
		}

		return resource;
	}

	public void addResources(
			String companyId, String groupId, String name,
			boolean portletActions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, null, name, null, portletActions, false, false);
	}

	public void addResources(
			String companyId, String groupId, String userId, String name,
			String primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		validate(companyId, name, portletActions);

		start = logAddResources(name, primKey, start, 1);

		// Company

		addResource(
			companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_COMPANY,
			companyId);

		start = logAddResources(name, primKey, start, 2);

		// Guest

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, Group.GUEST);

		addResource(
			companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_GROUP,
			guestGroup.getGroupId());

		start = logAddResources(name, primKey, start, 3);

		// Group

		if ((groupId != null) && !guestGroup.getGroupId().equals(groupId)) {
			addResource(
				companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_GROUP,
				groupId);
		}

		start = logAddResources(name, primKey, start, 4);

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
				primKey);

			start = logAddResources(name, primKey, start, 5);

			// Permissions

			List permissions = PermissionLocalServiceUtil.addPermissions(
				companyId, name, resource.getResourceId(), portletActions);

			start = logAddResources(name, primKey, start, 6);

			// User permissions

			if (userId != null) {
				UserUtil.addPermissions(userId, permissions);
			}

			start = logAddResources(name, primKey, start, 7);

			// Community permissions

			if ((groupId != null) && addCommunityPermissions) {
				addCommunityPermissions(
					groupId, name, resource.getResourceId(), portletActions);
			}

			start = logAddResources(name, primKey, start, 8);

			// Guest permissions

			if (addGuestPermissions) {

				// Don't add guest permissions when you've already added
				// community permissions and the given community is the guest
				// community.

				if ((groupId != null) && addCommunityPermissions) {
					if (guestGroup.getGroupId().equals(groupId)) {
						addGuestPermissions = false;
					}
				}

				if (addGuestPermissions) {
					addGuestPermissions(
						guestGroup.getGroupId(), name, resource.getResourceId(),
						portletActions);
				}
			}

			start = logAddResources(name, primKey, start, 9);
		}
	}

	public void deleteResource(String resourceId)
		throws PortalException, SystemException {

		try {
			Resource resource = ResourceUtil.findByPrimaryKey(resourceId);

			deleteResource(resource);
		}
		catch (NoSuchResourceException nsre) {
			_log.warn(nsre);
		}
	}

	public void deleteResource(Resource resource)
		throws PortalException, SystemException {

		// Permissions

		Iterator itr = PermissionUtil.findByResourceId(
			resource.getResourceId()).iterator();

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();

			OrgGroupPermissionUtil.removeByPermissionId(
				permission.getPermissionId());
		}

		PermissionUtil.removeByResourceId(resource.getResourceId());

		// Resource

		ResourceUtil.remove(resource.getResourceId());
	}

	public void deleteResource(
			String companyId, String name, String typeId, String scope,
			String primKey)
		throws PortalException, SystemException {

		try {
			Resource resource =
				getResource(companyId, name, typeId, scope, primKey);

			deleteResource(resource.getResourceId());
		}
		catch (NoSuchResourceException nsre) {
			_log.warn(nsre);
		}
	}

	public void deleteResources(String name)
		throws PortalException, SystemException {

		Iterator itr = ResourceUtil.findByName(name).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			deleteResource(resource);
		}
	}

	public Resource getResource(String resourceId)
		throws PortalException, SystemException {

		return ResourceUtil.findByPrimaryKey(resourceId);
	}

	public Resource getResource(
			String companyId, String name, String typeId, String scope,
			String primKey)
		throws PortalException, SystemException {

		return ResourceUtil.findByC_N_T_S_P(
			companyId, name, typeId, scope, primKey);
	}

	protected void addCommunityPermissions(
			String groupId, String name, String resourceId,
			boolean portletActions)
		throws PortalException, SystemException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		Group group = GroupUtil.findByPrimaryKey(groupId);

		start = logAddCommunityPermissions(groupId, name, resourceId, start, 1);

		List actions = null;

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

		start = logAddCommunityPermissions(groupId, name, resourceId, start, 2);

		String[] actionIds = (String[])actions.toArray(new String[0]);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		start = logAddCommunityPermissions(groupId, name, resourceId, start, 3);

		GroupUtil.addPermissions(groupId, permissions);

		start = logAddCommunityPermissions(groupId, name, resourceId, start, 4);
	}

	protected void addGuestPermissions(
			String groupId, String name, String resourceId,
			boolean portletActions)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		List actions = null;

		if (portletActions) {
			actions =
				ResourceActionsUtil.getPortletResourceGuestDefaultActions(name);
		}
		else {
			actions =
				ResourceActionsUtil.getModelResourceGuestDefaultActions(name);
		}

		String[] actionIds = (String[])actions.toArray(new String[0]);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		GroupUtil.addPermissions(groupId, permissions);
	}

	protected void addModelPermissions(
			String groupId, String resourceId, String[] actionIds)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		GroupUtil.addPermissions(groupId, permissions);
	}

	protected long logAddCommunityPermissions(
		String groupId, String name, String resourceId, long start, int block) {

		if (!_log.isDebugEnabled()) {
			return 0;
		}

		long end = System.currentTimeMillis();

		_log.debug(
			"Adding community permissions block " + block + " for " + groupId +
				" " + name + " " + resourceId + " takes " + (end - start) +
					" ms");

		return end;
	}

	protected long logAddResources(
		String name, String primKey, long start, int block) {

		if (!_log.isDebugEnabled()) {
			return 0;
		}

		long end = System.currentTimeMillis();

		_log.debug(
			"Adding resources block " + block + " for " + name + " " + primKey +
				" takes " + (end - start) + " ms");

		return end;
	}

	protected void validate(
			String companyId, String name, boolean portletActions)
		throws PortalException, SystemException {

		List actions = null;

		if (portletActions) {
			actions =
				ResourceActionsUtil.getPortletResourceActions(companyId, name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceActions(name);
		}

		if (actions.size() == 0) {
			throw new ResourceActionsException();
		}
	}

	private static Log _log = LogFactory.getLog(ResourceLocalServiceImpl.class);

}