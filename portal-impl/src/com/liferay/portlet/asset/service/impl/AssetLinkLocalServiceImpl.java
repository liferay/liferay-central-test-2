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
 * <a href="AssetLinkLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetLinkLocalServiceImpl extends AssetLinkLocalServiceBaseImpl {

	public AssetLink addLink(
			long userId, long entryId1, long entryId2, int typeId, int weight)
		throws PortalException, SystemException {

		Date now = new Date();
		User user = userLocalService.getUser(userId);

		long linkId = counterLocalService.increment();

		AssetLink link = assetLinkPersistence.create(linkId);

		link.setCompanyId(user.getCompanyId());
		link.setUserId(userId);
		link.setUserName(user.getFullName());
		link.setModifiedDate(now);
		link.setEntryId1(entryId1);
		link.setEntryId2(entryId2);
		link.setTypeId(typeId);
		link.setWeight(weight);

		assetLinkPersistence.update(link, false);

		return link;
	}

	public void deleteLink(long linkId)
		throws PortalException, SystemException {

		assetLinkPersistence.remove(linkId);
	}

	public void deleteLinks(long entryId)
		throws PortalException, SystemException {

		assetLinkPersistence.removeByE2(entryId);
		assetLinkPersistence.removeByE1(entryId);
	}

	public void deleteLinks(long linkId1, long linkId2)
		throws PortalException, SystemException {

		assetLinkPersistence.removeByE_E(linkId1, linkId2);
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