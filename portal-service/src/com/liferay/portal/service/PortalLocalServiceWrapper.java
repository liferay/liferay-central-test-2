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
 * This class is a wrapper for {@link PortalLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortalLocalService
 * @generated
 */
public class PortalLocalServiceWrapper implements PortalLocalService {
	public PortalLocalServiceWrapper(PortalLocalService portalLocalService) {
		_portalLocalService = portalLocalService;
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _portalLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_portalLocalService.setIdentifier(identifier);
	}

	public PortalLocalService getWrappedPortalLocalService() {
		return _portalLocalService;
	}

	public void setWrappedPortalLocalService(
		PortalLocalService portalLocalService) {
		_portalLocalService = portalLocalService;
	}

	private PortalLocalService _portalLocalService;
}