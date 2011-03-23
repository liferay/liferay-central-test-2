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
 * This class is a wrapper for {@link DDMStructureService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureService
 * @generated
 */
public class DDMStructureServiceWrapper implements DDMStructureService {
	public DDMStructureServiceWrapper(DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, java.lang.String structureKey, boolean autoStructureKey,
		java.lang.String name, java.lang.String description,
		java.lang.String xsd, java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.addStructure(groupId, structureKey,
			autoStructureKey, name, description, xsd, storageType,
			serviceContext);
	}

	public void deleteStructure(long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureService.deleteStructure(groupId, structureKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.getStructure(groupId, structureKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, java.lang.String structureKey, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureService.updateStructure(groupId, structureKey,
			name, description, xsd, storageType, serviceContext);
	}

	public DDMStructureService getWrappedDDMStructureService() {
		return _ddmStructureService;
	}

	public void setWrappedDDMStructureService(
		DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	private DDMStructureService _ddmStructureService;
}