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
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordServiceImpl extends DDLRecordServiceBaseImpl {

	public DDLRecord addRecord(
			long groupId, long recordSetId, Fields fields,
			int displayIndex, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getUserId(), groupId, recordSetId, fields, displayIndex,
			serviceContext);
	}

	public DDLRecord addRecord(
			long groupId, long recordSetId, Map<String, Serializable> fieldsMap,
			int displayIndex, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.ADD_RECORD);

		return ddlRecordLocalService.addRecord(
			getUserId(), groupId, recordSetId, fieldsMap, displayIndex,
			serviceContext);
	}

	public DDLRecord updateRecord(
			long recordId, Fields fields, int displayIndex, boolean merge,
			boolean majorVersion, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, fields, displayIndex, merge, majorVersion,
			serviceContext);
	}

	public DDLRecord updateRecord(
			long recordId, Map<String, Serializable> fieldsMap,
			int displayIndex, boolean merge, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordLocalService.getDDLRecord(recordId);

		DDLRecordSetPermission.check(
			getPermissionChecker(), record.getRecordSetId(), ActionKeys.UPDATE);

		return ddlRecordLocalService.updateRecord(
			getUserId(), recordId, fieldsMap, displayIndex, merge,
			serviceContext);
	}

}