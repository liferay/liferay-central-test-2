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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link QuartzLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       QuartzLocalService
 * @generated
 */
public class QuartzLocalServiceWrapper implements QuartzLocalService {
	public QuartzLocalServiceWrapper(QuartzLocalService quartzLocalService) {
		_quartzLocalService = quartzLocalService;
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _quartzLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_quartzLocalService.setIdentifier(identifier);
	}

	public void checkQuartzTables()
		throws com.liferay.portal.kernel.exception.SystemException {
		_quartzLocalService.checkQuartzTables();
	}

	public QuartzLocalService getWrappedQuartzLocalService() {
		return _quartzLocalService;
	}

	public void setWrappedQuartzLocalService(
		QuartzLocalService quartzLocalService) {
		_quartzLocalService = quartzLocalService;
	}

	private QuartzLocalService _quartzLocalService;
}