/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.base.AssetLinkLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetLinkLocalServiceImpl extends AssetLinkLocalServiceBaseImpl {

	public AssetLink addLink(
			long userId, long entryId1, long entryId2, int type, int weight)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		long linkId = counterLocalService.increment();

		AssetLink link = assetLinkPersistence.create(linkId);

		link.setCompanyId(user.getCompanyId());
		link.setUserId(user.getUserId());
		link.setUserName(user.getFullName());
		link.setCreateDate(now);
		link.setEntryId1(entryId1);
		link.setEntryId2(entryId2);
		link.setType(type);
		link.setWeight(weight);

		assetLinkPersistence.update(link, false);

		return link;
	}

	public void deleteLink(long linkId)
		throws PortalException, SystemException {

		assetLinkPersistence.remove(linkId);
	}

	public void deleteLinks(long entryId) throws SystemException {
		assetLinkPersistence.removeByE1(entryId);
		assetLinkPersistence.removeByE2(entryId);
	}

	public void deleteLinks(long entryId1, long entryId2)
		throws SystemException {

		assetLinkPersistence.removeByE_E(entryId1, entryId2);
	}

	public List<AssetLink> getLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkPersistence.findByE1_T(entryId, typeId);
	}

	public List<AssetLink> getReverseLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkPersistence.findByE2_T(entryId, typeId);
	}

}