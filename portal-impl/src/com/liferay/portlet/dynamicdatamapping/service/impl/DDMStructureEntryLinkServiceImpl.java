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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryLinkServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructureEntryPermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureEntryLinkServiceImpl
	extends DDMStructureEntryLinkServiceBaseImpl {

	public DDMStructureEntryLink addStructureEntryLink(
			String structureKey, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE_ENTRY);

		return ddmStructureEntryLinkLocalService.addStructureEntryLink(
			structureKey, className, classPK, serviceContext);
	}

	public void deleteStructureEntryLink(
			long groupId, String structureKey, long structureEntryLinkId)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.DELETE);

		ddmStructureEntryLinkLocalService.deleteStructureEntryLink(
			structureEntryLinkId);
	}

	public DDMStructureEntryLink getStructureEntryLink(
			long groupId, String structureKey, String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.VIEW);

		return ddmStructureEntryLinkLocalService.getStructureEntryLink(
			structureKey, className, classPK);
	}

	public DDMStructureEntryLink updateStructureEntryLink(
			long structureEntryLinkId, String structureKey, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.UPDATE);

		return ddmStructureEntryLinkLocalService.updateStructureEntryLink(
			structureEntryLinkId, structureKey, groupId, className, classPK);
	}

}