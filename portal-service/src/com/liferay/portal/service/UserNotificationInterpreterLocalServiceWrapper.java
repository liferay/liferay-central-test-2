/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link UserNotificationInterpreterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationInterpreterLocalService
 * @generated
 */
public class UserNotificationInterpreterLocalServiceWrapper
	implements UserNotificationInterpreterLocalService,
		ServiceWrapper<UserNotificationInterpreterLocalService> {
	public UserNotificationInterpreterLocalServiceWrapper(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userNotificationInterpreterLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userNotificationInterpreterLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public UserNotificationInterpreterLocalService getWrappedUserNotificationInterpreterLocalService() {
		return _userNotificationInterpreterLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedUserNotificationInterpreterLocalService(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	@Override
	public UserNotificationInterpreterLocalService getWrappedService() {
		return _userNotificationInterpreterLocalService;
	}

	@Override
	public void setWrappedService(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	private UserNotificationInterpreterLocalService _userNotificationInterpreterLocalService;
}