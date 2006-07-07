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
 *
 */
public class ResourceLocalServiceImpl implements ResourceLocalService {

	public Resource addResource(
			String companyId, String name, String typeId, String scope,
			String primKey)
		throws PortalException, SystemException {

		Resource resource = null;

		try {
			resource = ResourceUtil.findByC_N_T_S_P(
				companyId, name, typeId, scope, primKey);
		}
		catch (NoSuchResourceException nsre) {
			String resourceId = Long.toString(CounterServiceUtil.increment(
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

		validate(companyId, name, portletActions);

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

		if (groupId != null) {
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
				companyId, name, resource.getResourceId(), portletActions);

			// User permissions

			if (userId != null) {
				UserUtil.addPermissions(userId, permissions);
			}

			// Community permissions

			if ((groupId != null) && addCommunityPermissions) {
				addCommunityPermissions(
					groupId, name, resource.getResourceId(), portletActions);
			}

			// Guest permissions

			if (addGuestPermissions) {
				addGuestPermissions(
					guestGroup.getGroupId(), name, resource.getResourceId(),
					portletActions);
			}
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

		Group group = GroupUtil.findByPrimaryKey(groupId);

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

		String[] actionIds = (String[])actions.toArray(new String[0]);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		GroupUtil.addPermissions(groupId, permissions);
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