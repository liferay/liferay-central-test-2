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

package com.liferay.portlet.social.service;


/**
 * <a href="SocialEquityAssetEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquityAssetEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryLocalService
 * @generated
 */
public class SocialEquityAssetEntryLocalServiceWrapper
	implements SocialEquityAssetEntryLocalService {
	public SocialEquityAssetEntryLocalServiceWrapper(
		SocialEquityAssetEntryLocalService socialEquityAssetEntryLocalService) {
		_socialEquityAssetEntryLocalService = socialEquityAssetEntryLocalService;
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry addSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.addSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry createSocialEquityAssetEntry(
		long equityAssetEntryId) {
		return _socialEquityAssetEntryLocalService.createSocialEquityAssetEntry(equityAssetEntryId);
	}

	public void deleteSocialEquityAssetEntry(long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityAssetEntryLocalService.deleteSocialEquityAssetEntry(equityAssetEntryId);
	}

	public void deleteSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityAssetEntryLocalService.deleteSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry getSocialEquityAssetEntry(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.getSocialEquityAssetEntry(equityAssetEntryId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> getSocialEquityAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.getSocialEquityAssetEntries(start,
			end);
	}

	public int getSocialEquityAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.getSocialEquityAssetEntriesCount();
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry updateSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.updateSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry updateSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.updateSocialEquityAssetEntry(socialEquityAssetEntry,
			merge);
	}

	public double getInformationEquity(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntryLocalService.getInformationEquity(assetEntryId);
	}

	public SocialEquityAssetEntryLocalService getWrappedSocialEquityAssetEntryLocalService() {
		return _socialEquityAssetEntryLocalService;
	}

	private SocialEquityAssetEntryLocalService _socialEquityAssetEntryLocalService;
}