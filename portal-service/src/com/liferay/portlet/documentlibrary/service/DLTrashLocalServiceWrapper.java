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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLTrashLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashLocalService
 * @generated
 */
@ProviderType
public class DLTrashLocalServiceWrapper implements DLTrashLocalService,
	ServiceWrapper<DLTrashLocalService> {
	public DLTrashLocalServiceWrapper(DLTrashLocalService dlTrashLocalService) {
		_dlTrashLocalService = dlTrashLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlTrashLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DLTrashLocalService getWrappedDLTrashLocalService() {
		return _dlTrashLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDLTrashLocalService(
		DLTrashLocalService dlTrashLocalService) {
		_dlTrashLocalService = dlTrashLocalService;
	}

	@Override
	public DLTrashLocalService getWrappedService() {
		return _dlTrashLocalService;
	}

	@Override
	public void setWrappedService(DLTrashLocalService dlTrashLocalService) {
		_dlTrashLocalService = dlTrashLocalService;
	}

	private DLTrashLocalService _dlTrashLocalService;
}