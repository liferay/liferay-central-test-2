/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivitySettingService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingService
 * @generated
 */
@ProviderType
public class SocialActivitySettingServiceWrapper
	implements SocialActivitySettingService,
		ServiceWrapper<SocialActivitySettingService> {
	public SocialActivitySettingServiceWrapper(
		SocialActivitySettingService socialActivitySettingService) {
		_socialActivitySettingService = socialActivitySettingService;
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityDefinition getActivityDefinition(
		long groupId, java.lang.String className, int activityType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySettingService.getActivityDefinition(groupId,
			className, activityType);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityDefinition> getActivityDefinitions(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySettingService.getActivityDefinitions(groupId,
			className);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> getActivitySettings(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySettingService.getActivitySettings(groupId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _socialActivitySettingService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getJSONActivityDefinitions(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySettingService.getJSONActivityDefinitions(groupId,
			className);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivitySettingService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void updateActivitySetting(long groupId, java.lang.String className,
		int activityType,
		com.liferay.portlet.social.model.SocialActivityCounterDefinition activityCounterDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySettingService.updateActivitySetting(groupId, className,
			activityType, activityCounterDefinition);
	}

	@Override
	public void updateActivitySetting(long groupId, java.lang.String className,
		boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySettingService.updateActivitySetting(groupId, className,
			enabled);
	}

	@Override
	public void updateActivitySettings(long groupId,
		java.lang.String className, int activityType,
		java.util.List<com.liferay.portlet.social.model.SocialActivityCounterDefinition> activityCounterDefinitions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySettingService.updateActivitySettings(groupId,
			className, activityType, activityCounterDefinitions);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SocialActivitySettingService getWrappedSocialActivitySettingService() {
		return _socialActivitySettingService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSocialActivitySettingService(
		SocialActivitySettingService socialActivitySettingService) {
		_socialActivitySettingService = socialActivitySettingService;
	}

	@Override
	public SocialActivitySettingService getWrappedService() {
		return _socialActivitySettingService;
	}

	@Override
	public void setWrappedService(
		SocialActivitySettingService socialActivitySettingService) {
		_socialActivitySettingService = socialActivitySettingService;
	}

	private SocialActivitySettingService _socialActivitySettingService;
}