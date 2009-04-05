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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePermissionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionLocalServiceImpl
	extends ResourcePermissionLocalServiceBaseImpl {

	public List<String> getAvailableResourcePermissionActionIds(
			long resourceId, long roleId, String name, List<String> actionIds)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByR_R(resourceId, roleId);

		if (resourcePermission == null) {
			return Collections.EMPTY_LIST;
		}

		List<String> availableActionIds = new ArrayList<String>(
			actionIds.size());

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			if (hasActionId(resourcePermission, resourceAction)) {
				availableActionIds.add(actionId);
			}
		}

		return availableActionIds;
	}

	public List<ResourcePermission> getRoleResourcePermissions(long roleId)
		throws SystemException {

		return resourcePermissionPersistence.findByRoleId(roleId);
	}

	public boolean hasActionId(
		ResourcePermission resourcePermission, ResourceAction resourceAction) {

		long actionIds = resourcePermission.getActionIds();
		long bitwiseValue = resourceAction.getBitwiseValue();

		if ((actionIds & bitwiseValue) == bitwiseValue) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasResourcePermission(
			long resourceId, long roleId, String name, String actionId)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByR_R(resourceId, roleId);

		if (resourcePermission == null) {
			return false;
		}

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		if (hasActionId(resourcePermission, resourceAction)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasScopeResourcePermission(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		List<Resource> resources = resourcePersistence.findByCodeId(
			resourceCode.getCodeId());

		for (Resource resource : resources) {
			if (hasResourcePermission(
					resource.getResourceId(), roleId, name, actionId)) {

				return true;
			}
		}

		return false;
	}

	public void setResourcePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		if (scope == ResourceConstants.SCOPE_COMPANY) {

			// Remove group permission

			unsetResourcePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_GROUP,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {

			// Remove company permission

			unsetResourcePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_COMPANY,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			throw new NoSuchResourcePermissionException();
		}

		Resource resource = resourceLocalService.addResource(
			companyId, name, scope, primKey);

		long resourceId = resource.getResourceId();

		updateResourcePermission(
			roleId, new String[] {actionId}, resource.getResourceId(),
			ResourcePermissionConstants.OPERATOR_ADD);

		PermissionCacheUtil.clearCache();
	}

	public void setResourcePermissions(
			long roleId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		updateResourcePermission(
			roleId, actionIds, resourceId,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	public void unsetResourcePermission(
			long roleId, long resourceId, String actionId)
		throws PortalException, SystemException {

		updateResourcePermission(
			roleId, new String[] {actionId}, resourceId,
			ResourcePermissionConstants.OPERATOR_REMOVE);

		PermissionCacheUtil.clearCache();
	}

	public void unsetResourcePermissions(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		List<Resource> resources = resourcePersistence.findByCodeId(
			resourceCode.getCodeId());

		for (Resource resource : resources) {
			updateResourcePermission(
				roleId, new String[] {actionId}, resource.getResourceId(),
				ResourcePermissionConstants.OPERATOR_REMOVE);
		}

		PermissionCacheUtil.clearCache();
	}

	protected void updateResourcePermission(
			long roleId, String[] actionIds, long resourceId, int operator)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByR_R(resourceId, roleId);

		if (resourcePermission == null) {
			if (operator == ResourcePermissionConstants.OPERATOR_REMOVE) {
				return;
			}

			long resourcePermissionId = counterLocalService.increment(
				ResourcePermission.class.getName());

			resourcePermission = resourcePermissionPersistence.create(
				resourcePermissionId);

			resourcePermission.setResourceId(resourceId);
			resourcePermission.setRoleId(roleId);
		}

		long actionIdsLong = resourcePermission.getActionIds();

		if (operator == ResourcePermissionConstants.OPERATOR_SET) {
			actionIdsLong = 0;
		}

		Resource resource = resourcePersistence.findByPrimaryKey(resourceId);

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(
					resource.getName(), actionId);

			if ((operator == ResourcePermissionConstants.OPERATOR_ADD) ||
				(operator == ResourcePermissionConstants.OPERATOR_SET)) {

				actionIdsLong |= resourceAction.getBitwiseValue();
			}
			else {
				actionIdsLong =
					actionIdsLong & (~resourceAction.getBitwiseValue());
			}
		}

		resourcePermission.setActionIds(actionIdsLong);

		resourcePermissionPersistence.update(resourcePermission, false);

		PermissionCacheUtil.clearCache();
	}

}