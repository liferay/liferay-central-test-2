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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.base.AssetTagServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetPermission;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;
import com.liferay.util.Autocomplete;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 */
public class AssetTagServiceImpl extends AssetTagServiceBaseImpl {

	public AssetTag addTag(
			String name, String[] tagProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_TAG);

		return assetTagLocalService.addTag(
			getUserId(), name, tagProperties, serviceContext);
	}

	public void deleteTag(long tagId) throws PortalException, SystemException {
		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.DELETE);

		assetTagLocalService.deleteTag(tagId);
	}

	public List<AssetTag> getGroupsTags(long[] groupIds)
		throws PortalException, SystemException {

		return filterTags(
			assetTagLocalService.getGroupsTags(groupIds));
	}

	public List<AssetTag> getGroupTags(long groupId)
		throws PortalException, SystemException {

		return filterTags(
			assetTagLocalService.getGroupTags(groupId));
	}

	public AssetTag getTag(long tagId) throws PortalException, SystemException {
		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.VIEW);

		return assetTagLocalService.getTag(tagId);
	}

	public List<AssetTag> getTags(long groupId, long classNameId, String name)
		throws PortalException, SystemException {

		return filterTags(
			assetTagLocalService.getTags(groupId, classNameId, name));
	}

	public List<AssetTag> getTags(String className, long classPK)
		throws PortalException, SystemException {

		return filterTags(assetTagLocalService.getTags(className, classPK));
	}

	public void mergeTags(long fromTagId, long toTagId)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), fromTagId, ActionKeys.VIEW);

		AssetTagPermission.check(
			getPermissionChecker(), toTagId, ActionKeys.UPDATE);

		assetTagLocalService.mergeTags(fromTagId, toTagId);
	}

	public JSONArray search(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws PortalException, SystemException {

		List<AssetTag> tags = assetTagLocalService.search(
			groupId, name, tagProperties, start, end);

		tags = filterTags(tags);

		return Autocomplete.listToJson(tags, "name", "name");
	}

	public AssetTag updateTag(
			long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.UPDATE);

		return assetTagLocalService.updateTag(
			getUserId(), tagId, name, tagProperties, serviceContext);
	}

	protected List<AssetTag> filterTags(List<AssetTag> tags)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		tags = ListUtil.copy(tags);

		Iterator<AssetTag> itr = tags.iterator();

		while (itr.hasNext()) {
			AssetTag tag = itr.next();

			if (!AssetTagPermission.contains(
					permissionChecker, tag, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tags;
	}

}