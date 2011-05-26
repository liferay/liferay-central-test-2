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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.service.base.AssetLinkLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
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

		if (AssetLinkConstants.isTypeBi(type)) {
			long linkId2 = counterLocalService.increment();

			AssetLink link2 = assetLinkPersistence.create(linkId2);

			link2.setCompanyId(user.getCompanyId());
			link2.setUserId(user.getUserId());
			link2.setUserName(user.getFullName());
			link2.setCreateDate(now);
			link2.setEntryId1(entryId2);
			link2.setEntryId2(entryId1);
			link2.setType(type);
			link2.setWeight(weight);

			assetLinkPersistence.update(link2, false);
		}

		return link;
	}

	public void deleteLink(AssetLink link) throws SystemException {
		if (AssetLinkConstants.isTypeBi(link.getType())) {
			try {
				assetLinkPersistence.removeByE_E_T(
					link.getEntryId2(), link.getEntryId1(), link.getType());
			}
			catch (NoSuchLinkException nsle) {
			}
		}

		assetLinkPersistence.remove(link);
	}

	public void deleteLink(long linkId)
		throws PortalException, SystemException {

		AssetLink link = assetLinkPersistence.findByPrimaryKey(linkId);

		deleteLink(link);
	}

	public void deleteLinks(long entryId) throws SystemException {
		for (AssetLink link : assetLinkPersistence.findByE1(entryId)) {
			deleteLink(link);
		}

		for (AssetLink link : assetLinkPersistence.findByE2(entryId)) {
			deleteLink(link);
		}
	}

	public void deleteLinks(long entryId1, long entryId2)
		throws SystemException {

		List<AssetLink> links = assetLinkPersistence.findByE_E(
			entryId1, entryId2);

		for (AssetLink link : links) {
			deleteLink(link);
		}
	}

	public List<AssetLink> getDirectLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkPersistence.findByE1_T(entryId, typeId);
	}

	public List<AssetLink> getLinks(long entryId, int typeId)
		throws SystemException {

		List<AssetLink> e1Links = assetLinkPersistence.findByE1(entryId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2(entryId);

		List<AssetLink> links = new ArrayList<AssetLink>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	public List<AssetLink> getReverseLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkPersistence.findByE2_T(entryId, typeId);
	}

	public void updateLinks(
			long userId, long entryId, long[] linkEntryIds, int typeId)
		throws SystemException, PortalException {

		if (linkEntryIds == null) {
			return;
		}

		List<AssetLink> links = getLinks(entryId, typeId);

		for (AssetLink link : links) {
			if (((link.getEntryId1() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId2())) ||
				((link.getEntryId2() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId1()))) {

				deleteLink(link);
			}
		}

		for (long assetLinkEntryId : linkEntryIds) {
			if (assetLinkEntryId != entryId) {
				AssetLink link = assetLinkPersistence.fetchByE_E_T(
					entryId, assetLinkEntryId,typeId);

				if (link == null) {
					addLink(userId, entryId, assetLinkEntryId, typeId, 0);
				}
			}
		}
	}

}