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

	public void addEquityLogs(long userId, java.lang.String className,
		long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.addEquityLogs(userId, className, classPK,
			actionId);
	}

	public void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.checkEquityLogs();
	}

	public void deactivateEquityLogs(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.deactivateEquityLogs(assetEntryId);
	}

	public void incrementSocialEquityAssetEntry_IQ(long assetEntryId, double k,
		double b) {
		_socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(assetEntryId,
			k, b);
	}

	public void incrementSocialEquityUser_CQ(java.lang.String id, double k,
		double b) {
		_socialEquityLogLocalService.incrementSocialEquityUser_CQ(id, k, b);
	}

	public void incrementSocialEquityUser_PQ(java.lang.String id, double k,
		double b) {
		_socialEquityLogLocalService.incrementSocialEquityUser_PQ(id, k, b);
	}

	public void incrementUser_CQ(long userId, double k, double b) {
		_socialEquityLogLocalService.incrementUser_CQ(userId, k, b);
	}

	public void incrementUser_PQ(long userId, double k, double b) {
		_socialEquityLogLocalService.incrementUser_PQ(userId, k, b);
	}

	public void updateSocialEquityAssetEntry_IQ(long assetEntryId, double k,
		double b) throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.updateSocialEquityAssetEntry_IQ(assetEntryId,
			k, b);
	}

	public void updateSocialEquityUser_CQ(java.lang.String id, double k,
		double b)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.updateSocialEquityUser_CQ(id, k, b);
	}

	public void updateSocialEquityUser_PQ(java.lang.String id, double k,
		double b)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityLogLocalService.updateSocialEquityUser_PQ(id, k, b);
	}

	public SocialEquityLogLocalService getWrappedSocialEquityLogLocalService() {
		return _socialEquityLogLocalService;
	}

	private SocialEquityLogLocalService _socialEquityLogLocalService;
}