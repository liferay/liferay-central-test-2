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
 * <a href="SocialEquityLogLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquityLogLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogLocalService
 * @generated
 */
public class SocialEquityLogLocalServiceWrapper
	implements SocialEquityLogLocalService {
	public SocialEquityLogLocalServiceWrapper(
		SocialEquityLogLocalService socialEquityLogLocalService) {
		_socialEquityLogLocalService = socialEquityLogLocalService;
	}

	public com.liferay.portlet.social.model.SocialEquityLog addSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.addSocialEquityLog(socialEquityLog);
	}

	public com.liferay.portlet.social.model.SocialEquityLog createSocialEquityLog(
		long equityLogId) {
		return _socialEquityLogLocalService.createSocialEquityLog(equityLogId);
	}

	public void deleteSocialEquityLog(long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deleteSocialEquityLog(equityLogId);
	}

	public void deleteSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deleteSocialEquityLog(socialEquityLog);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialEquityLog getSocialEquityLog(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLog(equityLogId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> getSocialEquityLogs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLogs(start, end);
	}

	public int getSocialEquityLogsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.getSocialEquityLogsCount();
	}

	public com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.updateSocialEquityLog(socialEquityLog);
	}

	public com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLogLocalService.updateSocialEquityLog(socialEquityLog,
			merge);
	}

	public void addEquityLogs(long userId, long assetEntryId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, assetEntryId,
			actionId);
	}

	public void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.checkEquityLogs();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(assetEntryId);
	}

	public SocialEquityLogLocalService getWrappedSocialEquityLogLocalService() {
		return _socialEquityLogLocalService;
	}

	private SocialEquityLogLocalService _socialEquityLogLocalService;
}