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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityInterpreterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityInterpreterLocalService
 * @generated
 */
public class SocialActivityInterpreterLocalServiceWrapper
	implements SocialActivityInterpreterLocalService {
	public SocialActivityInterpreterLocalServiceWrapper(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		_socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _socialActivityInterpreterLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_socialActivityInterpreterLocalService.setIdentifier(identifier);
	}

	public void addActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		_socialActivityInterpreterLocalService.addActivityInterpreter(activityInterpreter);
	}

	public void deleteActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		_socialActivityInterpreterLocalService.deleteActivityInterpreter(activityInterpreter);
	}

	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _socialActivityInterpreterLocalService.interpret(activity,
			themeDisplay);
	}

	public SocialActivityInterpreterLocalService getWrappedSocialActivityInterpreterLocalService() {
		return _socialActivityInterpreterLocalService;
	}

	public void setWrappedSocialActivityInterpreterLocalService(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		_socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	private SocialActivityInterpreterLocalService _socialActivityInterpreterLocalService;
}