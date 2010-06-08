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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialEquityAssetEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialEquityAssetEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryLocalService
 * @generated
 */
public class SocialEquityAssetEntryLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry addSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry createSocialEquityAssetEntry(
		long equityAssetEntryId) {
		return getService().createSocialEquityAssetEntry(equityAssetEntryId);
	}

	public static void deleteSocialEquityAssetEntry(long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityAssetEntry(equityAssetEntryId);
	}

	public static void deleteSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry getSocialEquityAssetEntry(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityAssetEntry(equityAssetEntryId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> getSocialEquityAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityAssetEntries(start, end);
	}

	public static int getSocialEquityAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityAssetEntriesCount();
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry updateSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityAssetEntry(socialEquityAssetEntry);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry updateSocialEquityAssetEntry(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSocialEquityAssetEntry(socialEquityAssetEntry, merge);
	}

	public static double getInformationEquity(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInformationEquity(assetEntryId);
	}

	public static SocialEquityAssetEntryLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityAssetEntryLocalService)PortalBeanLocatorUtil.locate(SocialEquityAssetEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialEquityAssetEntryLocalService service) {
		_service = service;
	}

	private static SocialEquityAssetEntryLocalService _service;
}