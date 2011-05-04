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
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordSetServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLRecordSetServiceImpl extends DDLRecordSetServiceBaseImpl {

	public DDLRecordSet addRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			boolean autoRecordSetKey, Map<Locale, String> nameMap,
			String description, int minDisplayRows,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_RECORD_SET);

		return ddlRecordSetLocalService.addRecordSet(
			getUserId(), groupId, ddmStructureId, recordSetKey,
			autoRecordSetKey, nameMap, description, minDisplayRows,
			serviceContext);
	}

	public void deleteRecordSet(long recordSetId)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.DELETE);

		ddlRecordSetLocalService.deleteRecordSet(recordSetId);
	}

	public void deleteRecordSet(long groupId, String recordSetKey)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), groupId, recordSetKey, ActionKeys.DELETE);

		ddlRecordSetLocalService.deleteRecordSet(groupId, recordSetKey);
	}

	public DDLRecordSet getRecordSet(long recordSetId)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetLocalService.getRecordSet(recordSetId);
	}

	public DDLRecordSet getRecordSet(long groupId, String recordSetKey)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), groupId, recordSetKey, ActionKeys.VIEW);

		return ddlRecordSetLocalService.getRecordSet(groupId, recordSetKey);
	}

	public DDLRecordSet updateMinDisplayRows(
			long recordSetId, int minDisplayRows,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateMinDisplayRows(
			recordSetId, minDisplayRows, serviceContext);
	}

	public DDLRecordSet updateRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, String description,
			int minDisplayRows, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), groupId, recordSetKey, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateRecordSet(
			groupId, ddmStructureId, recordSetKey, nameMap, description,
			minDisplayRows, serviceContext);
	}

}