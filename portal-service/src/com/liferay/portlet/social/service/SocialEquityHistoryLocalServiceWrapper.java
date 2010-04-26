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
 * <a href="SocialEquityHistoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquityHistoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryLocalService
 * @generated
 */
public class SocialEquityHistoryLocalServiceWrapper
	implements SocialEquityHistoryLocalService {
	public SocialEquityHistoryLocalServiceWrapper(
		SocialEquityHistoryLocalService socialEquityHistoryLocalService) {
		_socialEquityHistoryLocalService = socialEquityHistoryLocalService;
	}

	public com.liferay.portlet.social.model.SocialEquityHistory addSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.addSocialEquityHistory(socialEquityHistory);
	}

	public com.liferay.portlet.social.model.SocialEquityHistory createSocialEquityHistory(
		long equityHistoryId) {
		return _socialEquityHistoryLocalService.createSocialEquityHistory(equityHistoryId);
	}

	public void deleteSocialEquityHistory(long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityHistoryLocalService.deleteSocialEquityHistory(equityHistoryId);
	}

	public void deleteSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityHistoryLocalService.deleteSocialEquityHistory(socialEquityHistory);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialEquityHistory getSocialEquityHistory(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistory(equityHistoryId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> getSocialEquityHistories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistories(start,
			end);
	}

	public int getSocialEquityHistoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.getSocialEquityHistoriesCount();
	}

	public com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.updateSocialEquityHistory(socialEquityHistory);
	}

	public com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistoryLocalService.updateSocialEquityHistory(socialEquityHistory,
			merge);
	}

	public SocialEquityHistoryLocalService getWrappedSocialEquityHistoryLocalService() {
		return _socialEquityHistoryLocalService;
	}

	private SocialEquityHistoryLocalService _socialEquityHistoryLocalService;
}