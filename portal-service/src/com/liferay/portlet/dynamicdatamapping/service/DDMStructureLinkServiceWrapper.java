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
 * This class is a wrapper for {@link DDMStructureLinkService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureLinkService
 * @generated
 */
public class DDMStructureLinkServiceWrapper implements DDMStructureLinkService {
	public DDMStructureLinkServiceWrapper(
		DDMStructureLinkService ddmStructureLinkService) {
		_ddmStructureLinkService = ddmStructureLinkService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkService.addStructureLink(classNameId, classPK,
			structureId, serviceContext);
	}

	public void deleteStructureLink(long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLinkService.deleteStructureLink(structureLinkId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink getStructureLink(
		long classNameId, long classPK, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkService.getStructureLink(classNameId, classPK,
			structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateStructureLink(
		long structureLinkId, long classNameId, long classPK, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkService.updateStructureLink(structureLinkId,
			classNameId, classPK, structureId);
	}

	public DDMStructureLinkService getWrappedDDMStructureLinkService() {
		return _ddmStructureLinkService;
	}

	public void setWrappedDDMStructureLinkService(
		DDMStructureLinkService ddmStructureLinkService) {
		_ddmStructureLinkService = ddmStructureLinkService;
	}

	private DDMStructureLinkService _ddmStructureLinkService;
}