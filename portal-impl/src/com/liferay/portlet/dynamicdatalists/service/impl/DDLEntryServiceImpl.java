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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.service.base.DDLEntryServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLEntryPermission;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLEntryServiceImpl extends DDLEntryServiceBaseImpl {

	public DDLEntry addEntry(
			long groupId, long ddmStructureId, String entryKey,
			boolean autoEntryKey, Map<Locale, String> nameMap,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ENTRY);

		return ddlEntryLocalService.addEntry(
			getUserId(), groupId, ddmStructureId, entryKey, autoEntryKey,
			nameMap, description, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		DDLEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		ddlEntryLocalService.deleteEntry(entryId);
	}

	public void deleteEntry(long groupId, String entryKey)
		throws PortalException, SystemException {

		DDLEntryPermission.check(
			getPermissionChecker(), groupId, entryKey, ActionKeys.DELETE);

		ddlEntryLocalService.deleteEntry(groupId, entryKey);
	}

	public DDLEntry getEntry(long entryId)
		throws PortalException, SystemException {

		DDLEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return ddlEntryLocalService.getEntry(entryId);
	}

	public DDLEntry getEntry(long groupId, String entryKey)
		throws PortalException, SystemException {

		DDLEntryPermission.check(
			getPermissionChecker(), groupId, entryKey, ActionKeys.VIEW);

		return ddlEntryLocalService.getEntry(groupId, entryKey);
	}

	public DDLEntry updateEntry(
			long groupId, long ddmStructureId, String entryKey,
			Map<Locale, String> nameMap, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLEntryPermission.check(
			getPermissionChecker(), groupId, entryKey, ActionKeys.UPDATE);

		return ddlEntryLocalService.updateEntry(
			groupId, ddmStructureId, entryKey, nameMap, description,
			serviceContext);
	}

}