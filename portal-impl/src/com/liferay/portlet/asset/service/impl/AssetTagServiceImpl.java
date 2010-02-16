/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.util.Iterator;
import java.util.List;

/**
 * <a href="AssetTagServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
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
		throws SystemException {

		return assetTagLocalService.search(
			groupId, name, tagProperties, start, end);
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