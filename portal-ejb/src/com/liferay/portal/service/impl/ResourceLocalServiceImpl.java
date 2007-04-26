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
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.base.ResourceLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrgGroupPermissionUtil;
import com.liferay.portal.service.persistence.PermissionUtil;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.comparator.ResourceComparator;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 *
 */
public class ResourceLocalServiceImpl extends ResourceLocalServiceBaseImpl {

	public void addModelResources(
			String companyId, long groupId, long userId, String name,
			long primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			communityPermissions, guestPermissions);
	}

	public void addModelResources(
			String companyId, long groupId, long userId, String name,
			String primKey, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		validate(companyId, name, false);

		// Company

		addResource(companyId, name, ResourceImpl.SCOPE_COMPANY, companyId);

		// Guest

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		addResource(
			companyId, name, ResourceImpl.SCOPE_GROUP,
			String.valueOf(guestGroup.getGroupId()));

		// Group

		if ((groupId > 0) && (guestGroup.getGroupId() != groupId)) {
			addResource(
				companyId, name, ResourceImpl.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, ResourceImpl.SCOPE_INDIVIDUAL, primKey);

			// Permissions

			List permissions = PermissionLocalServiceUtil.addPermissions(
				companyId, name, resource.getResourceId(), false);

			// User permissions

			if (userId > 0) {
				UserUtil.addPermissions(userId, permissions);
			}

			// Community permissions

			if ((groupId > 0) && (communityPermissions != null)) {
				addModelPermissions(
					groupId, resource.getResourceId(), communityPermissions);
			}

			// Guest permissions

			if (guestPermissions != null) {

				// Don't add guest permissions when you've already added
				// community permissions and the given community is the guest
				// community.

				if ((groupId <= 0) || (guestGroup.getGroupId() != groupId)) {
					addModelPermissions(
						guestGroup.getGroupId(), resource.getResourceId(),
						guestPermissions);
				}
			}
		}
	}

	public Resource addResource(
			String companyId, String name, String scope, String primKey)
		throws PortalException, SystemException {

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(
				companyId, name, scope);

		Resource resource = ResourceUtil.fetchByC_P(
			resourceCode.getCodeId(), primKey);

		if (resource == null) {
			long resourceId = CounterLocalServiceUtil.increment(
				Resource.class.getName());

			resource = ResourceUtil.create(resourceId);

			resource.setCodeId(resourceCode.getCodeId());
			resource.setPrimKey(primKey);

			ResourceUtil.update(resource);
		}

		return resource;
	}

	public void addResources(
			String companyId, long groupId, String name, boolean portletActions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, 0, name, null, portletActions, false, false);
	}

	public void addResources(
			String companyId, long groupId, long userId, String name,
			long primKey, boolean portletActions,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			portletActions, addCommunityPermissions, addGuestPermissions);
	}

	public void addResources(
			String companyId, long groupId, long userId, String name,
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
			companyId, name, ResourceImpl.SCOPE_COMPANY, companyId);

		logAddResources(name, primKey, stopWatch, 2);

		// Guest

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		addResource(
			companyId, name, ResourceImpl.SCOPE_GROUP,
			String.valueOf(guestGroup.getGroupId()));

		logAddResources(name, primKey, stopWatch, 3);

		// Group

		if ((groupId > 0) && (guestGroup.getGroupId() != groupId)) {
			addResource(
				companyId, name, ResourceImpl.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		logAddResources(name, primKey, stopWatch, 4);

		if (primKey != null) {

			// Individual

			Resource resource = addResource(
				companyId, name, ResourceImpl.SCOPE_INDIVIDUAL, primKey);

			logAddResources(name, primKey, stopWatch, 5);

			// Permissions

			List permissions = PermissionLocalServiceUtil.addPermissions(
				companyId, name, resource.getResourceId(), portletActions);

			logAddResources(name, primKey, stopWatch, 6);

			// User permissions

			if (userId > 0) {
				UserUtil.addPermissions(userId, permissions);
			}

			logAddResources(name, primKey, stopWatch, 7);

			// Community permissions

			if ((groupId > 0) && addCommunityPermissions) {
				addCommunityPermissions(
					groupId, name, resource.getResourceId(), portletActions);
			}

			logAddResources(name, primKey, stopWatch, 8);

			// Guest permissions

			if (addGuestPermissions) {

				// Don't add guest permissions when you've already added
				// community permissions and the given community is the guest
				// community.

				if ((groupId > 0) && addCommunityPermissions) {
					if (guestGroup.getGroupId() == groupId) {
						addGuestPermissions = false;
					}
				}

				if (addGuestPermissions) {
					addGuestPermissions(
						guestGroup.getGroupId(), name, resource.getResourceId(),
						portletActions);
				}
			}

			logAddResources(name, primKey, stopWatch, 9);
		}
	}

	public void deleteResource(long resourceId)
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
			String companyId, String name, String scope, long primKey)
		throws PortalException, SystemException {

		deleteResource(companyId, name, scope, String.valueOf(primKey));
	}

	public void deleteResource(
			String companyId, String name, String scope, String primKey)
		throws PortalException, SystemException {

		try {
			Resource resource = getResource(companyId, name, scope, primKey);

			deleteResource(resource.getResourceId());
		}
		catch (NoSuchResourceException nsre) {
			_log.warn(nsre);
		}
	}

	public void deleteResources(String name)
		throws PortalException, SystemException {

		Iterator itr = ResourceFinder.findByName(name).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			deleteResource(resource);
		}
	}

	public long getLatestResourceId()
		throws PortalException, SystemException {

		List list = ResourceUtil.findAll(0, 1, new ResourceComparator());

		if (list.size() == 0) {
			return 0;
		}
		else {
			Resource resource = (Resource)list.get(0);

			return resource.getResourceId();
		}
	}

	public Resource getResource(long resourceId)
		throws PortalException, SystemException {

		return ResourceUtil.findByPrimaryKey(resourceId);
	}

	public List getResources() throws SystemException {
		return ResourceUtil.findAll();
	}

	public Resource getResource(
			String companyId, String name, String scope, String primKey)
		throws PortalException, SystemException {

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(
				companyId, name, scope);

		return ResourceUtil.findByC_P(resourceCode.getCodeId(), primKey);
	}

	protected void addCommunityPermissions(
			long groupId, String name, long resourceId, boolean portletActions)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Group group = GroupUtil.findByPrimaryKey(groupId);

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 1);

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

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 2);

		String[] actionIds = (String[])actions.toArray(new String[0]);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 3);

		GroupUtil.addPermissions(groupId, permissions);

		logAddCommunityPermissions(groupId, name, resourceId, stopWatch, 4);
	}

	protected void addGuestPermissions(
			long groupId, String name, long resourceId, boolean portletActions)
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
			long groupId, long resourceId, String[] actionIds)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		List permissions = PermissionLocalServiceUtil.getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		GroupUtil.addPermissions(groupId, permissions);
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
			throw new ResourceActionsException(
				"There are no actions associated with the resource " + name);
		}
	}

	private static Log _log = LogFactory.getLog(ResourceLocalServiceImpl.class);

}