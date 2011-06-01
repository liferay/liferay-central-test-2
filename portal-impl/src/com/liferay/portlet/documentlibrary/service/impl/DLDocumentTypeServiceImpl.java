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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.base.DLDocumentTypeServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLDocumentTypePermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLDocumentTypeServiceImpl extends DLDocumentTypeServiceBaseImpl {

	public DLDocumentType addDocumentType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlDocumentTypeLocalService.addDocumentType(
			getUserId(), groupId, name, description, ddmStructureIds,
			serviceContext);
	}

	public void deleteDocumentType(long documentTypeId)
		throws PortalException, SystemException {

		DLDocumentTypePermission.check(
			getPermissionChecker(), documentTypeId, ActionKeys.DELETE);

		dlDocumentTypeLocalService.deleteDocumentType(documentTypeId);
	}

	public DLDocumentType getDocumentType(long documentTypeId)
		throws PortalException, SystemException {

		DLDocumentTypePermission.check(
			getPermissionChecker(), documentTypeId, ActionKeys.VIEW);

		return dlDocumentTypeLocalService.getDocumentType(documentTypeId);
	}

	public List<DLDocumentType> getDocumentTypes(
			long groupId, int start, int end)
		throws SystemException {

		return dlDocumentTypePersistence.filterFindByGroupId(
			groupId, start, end);
	}

	public int getDocumentTypesCount(long groupId)throws SystemException {
		return dlDocumentTypePersistence.filterCountByGroupId(groupId);
	}

	public void updateDocumentType(
			long documentTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLDocumentTypePermission.check(
			getPermissionChecker(), documentTypeId, ActionKeys.UPDATE);

		dlDocumentTypeLocalService.updateDocumentType(
			documentTypeId, name, description, ddmStructureIds, serviceContext);
	}

}