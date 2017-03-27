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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordSetVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionService
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionServiceWrapper
	implements DDLRecordSetVersionService,
		ServiceWrapper<DDLRecordSetVersionService> {
	public DDLRecordSetVersionServiceWrapper(
		DDLRecordSetVersionService ddlRecordSetVersionService) {
		_ddlRecordSetVersionService = ddlRecordSetVersionService;
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getLatestRecordSetVersion(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersionService.getLatestRecordSetVersion(recordSetId);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getRecordSetVersion(
		long recordSetVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersionService.getRecordSetVersion(recordSetVersionId);
	}

	@Override
	public int getRecordSetVersionsCount(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersionService.getRecordSetVersionsCount(recordSetId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddlRecordSetVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> getRecordSetVersions(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSetVersionService.getRecordSetVersions(recordSetId,
			start, end, orderByComparator);
	}

	@Override
	public DDLRecordSetVersionService getWrappedService() {
		return _ddlRecordSetVersionService;
	}

	@Override
	public void setWrappedService(
		DDLRecordSetVersionService ddlRecordSetVersionService) {
		_ddlRecordSetVersionService = ddlRecordSetVersionService;
	}

	private DDLRecordSetVersionService _ddlRecordSetVersionService;
}