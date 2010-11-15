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

	public PortalLocalService getWrappedPortalLocalService() {
		return _portalLocalService;
	}

	public void setWrappedPortalLocalService(
		PortalLocalService portalLocalService) {
		_portalLocalService = portalLocalService;
	}

	private PortalLocalService _portalLocalService;
}