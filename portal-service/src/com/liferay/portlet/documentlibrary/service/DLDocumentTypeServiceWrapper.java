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

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLDocumentTypeService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLDocumentTypeService
 * @generated
 */
public class DLDocumentTypeServiceWrapper implements DLDocumentTypeService {
	public DLDocumentTypeServiceWrapper(
		DLDocumentTypeService dlDocumentTypeService) {
		_dlDocumentTypeService = dlDocumentTypeService;
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType addDocumentType(
		long groupId, java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeService.addDocumentType(groupId, name,
			description, ddmStructureIds, serviceContext);
	}

	public void deleteDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeService.deleteDocumentType(documentTypeId);
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType getDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeService.getDocumentType(documentTypeId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDocumentTypes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeService.getDocumentTypes(groupId, start, end);
	}

	public void updateDocumentType(long documentTypeId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeService.updateDocumentType(documentTypeId, name,
			description, ddmStructureIds, serviceContext);
	}

	public DLDocumentTypeService getWrappedDLDocumentTypeService() {
		return _dlDocumentTypeService;
	}

	public void setWrappedDLDocumentTypeService(
		DLDocumentTypeService dlDocumentTypeService) {
		_dlDocumentTypeService = dlDocumentTypeService;
	}

	private DLDocumentTypeService _dlDocumentTypeService;
}