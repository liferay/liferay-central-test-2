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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureServiceImpl
	extends DDMStructureServiceBaseImpl {

	public DDMStructure addStructure(
			long groupId, String structureKey, boolean autoStructureKey,
			String name, String description, String xsd, String storageType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE_ENTRY);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, structureKey, autoStructureKey, name,
			description, xsd, storageType, serviceContext);
	}

	public void deleteStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.DELETE);

		ddmStructureLocalService.deleteStructure(
			groupId, structureKey);
	}

	public DDMStructure getStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.VIEW);

		return ddmStructurePersistence.findByG_S(groupId, structureKey);
	}

	public DDMStructure updateStructure(
			long groupId, String structureKey, String name, String description,
			String xsd, String storageType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			groupId, structureKey, name, description, xsd, storageType,
			serviceContext);
	}

}