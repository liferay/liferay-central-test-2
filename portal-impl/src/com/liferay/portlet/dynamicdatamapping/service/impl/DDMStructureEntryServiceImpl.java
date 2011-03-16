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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructureEntryPermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureEntryServiceImpl
	extends DDMStructureEntryServiceBaseImpl {

	public DDMStructureEntry addStructureEntry(
			long groupId, String structureKey, boolean autoStrucureId,
			String name, String description, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE_ENTRY);

		return ddmStructureEntryLocalService.addStructureEntry(
			getUserId(), groupId, structureKey, autoStrucureId, name,
			description, xsd, serviceContext);
	}

	public void deleteStructureEntry(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.DELETE);

		ddmStructureEntryLocalService.deleteStructureEntry(
			groupId, structureKey);
	}

	public DDMStructureEntry getStructureEntry(
			long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.VIEW);

		return ddmStructureEntryPersistence.findByG_S(groupId, structureKey);
	}

	public DDMStructureEntry updateStructureEntry(
			long groupId, String structureKey, String name, String description,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.UPDATE);

		return ddmStructureEntryLocalService.updateStructureEntry(
			groupId, structureKey, name, description, xsd, serviceContext);
	}

}