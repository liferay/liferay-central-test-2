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
 * <a href="SocialEquitySettingLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquitySettingLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySettingLocalService
 * @generated
 */
public class SocialEquitySettingLocalServiceWrapper
	implements SocialEquitySettingLocalService {
	public SocialEquitySettingLocalServiceWrapper(
		SocialEquitySettingLocalService socialEquitySettingLocalService) {
		_socialEquitySettingLocalService = socialEquitySettingLocalService;
	}

	public com.liferay.portlet.social.model.SocialEquitySetting addSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.addSocialEquitySetting(socialEquitySetting);
	}

	public com.liferay.portlet.social.model.SocialEquitySetting createSocialEquitySetting(
		long equitySettingId) {
		return _socialEquitySettingLocalService.createSocialEquitySetting(equitySettingId);
	}

	public void deleteSocialEquitySetting(long equitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquitySettingLocalService.deleteSocialEquitySetting(equitySettingId);
	}

	public void deleteSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquitySettingLocalService.deleteSocialEquitySetting(socialEquitySetting);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialEquitySetting getSocialEquitySetting(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.getSocialEquitySetting(equitySettingId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> getSocialEquitySettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.getSocialEquitySettings(start,
			end);
	}

	public int getSocialEquitySettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.getSocialEquitySettingsCount();
	}

	public com.liferay.portlet.social.model.SocialEquitySetting updateSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.updateSocialEquitySetting(socialEquitySetting);
	}

	public com.liferay.portlet.social.model.SocialEquitySetting updateSocialEquitySetting(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquitySettingLocalService.updateSocialEquitySetting(socialEquitySetting,
			merge);
	}

	public SocialEquitySettingLocalService getWrappedSocialEquitySettingLocalService() {
		return _socialEquitySettingLocalService;
	}

	private SocialEquitySettingLocalService _socialEquitySettingLocalService;
}