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

package com.liferay.portlet.dynamicdatamapping.service;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureEntryService
 * @generated
 */
public class DDMStructureEntryServiceWrapper implements DDMStructureEntryService {
	public DDMStructureEntryServiceWrapper(
		DDMStructureEntryService ddmStructureEntryService) {
		_ddmStructureEntryService = ddmStructureEntryService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry addStructureEntry(
		long groupId, java.lang.String structureId, boolean autoStrucureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryService.addStructureEntry(groupId,
			structureId, autoStrucureId, name, description, xsd, serviceContext);
	}

	public void deleteStructureEntry(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryService.deleteStructureEntry(groupId, structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getStructureEntry(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryService.getStructureEntry(groupId, structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry updateStructureEntry(
		long groupId, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryService.updateStructureEntry(groupId,
			structureId, name, description, xsd, serviceContext);
	}

	public DDMStructureEntryService getWrappedDDMStructureEntryService() {
		return _ddmStructureEntryService;
	}

	public void setWrappedDDMStructureEntryService(
		DDMStructureEntryService ddmStructureEntryService) {
		_ddmStructureEntryService = ddmStructureEntryService;
	}

	private DDMStructureEntryService _ddmStructureEntryService;
}