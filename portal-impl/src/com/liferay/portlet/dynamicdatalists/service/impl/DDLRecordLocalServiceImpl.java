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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class DDLRecordLocalServiceImpl
	extends DDLRecordLocalServiceBaseImpl {

	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, Fields fields,
			int displayIndex, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		long recordId = counterLocalService.increment();

		DDLRecord record = ddlRecordPersistence.create(recordId);

		record.setUuid(serviceContext.getUuid());
		record.setGroupId(groupId);
		record.setCompanyId(user.getCompanyId());
		record.setUserId(user.getUserId());
		record.setUserName(user.getFullName());
		record.setCreateDate(serviceContext.getCreateDate(now));
		record.setModifiedDate(serviceContext.getModifiedDate(now));

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		record.setClassNameId(ddmStructure.getClassNameId());

		long classPK = StorageEngineUtil.create(
			recordSet.getCompanyId(), recordSet.getDDMStructureId(), fields,
			serviceContext);

		record.setClassPK(classPK);

		record.setRecordSetId(recordSetId);
		record.setDisplayIndex(displayIndex);
		record.setStatus(WorkflowConstants.STATUS_DRAFT);
		record.setStatusDate(serviceContext.getModifiedDate(now));

		ddlRecordPersistence.update(record, false);

		// Asset

		Locale locale = ServiceContextUtil.getLocale(serviceContext);

		updateAsset(
			userId, record, locale,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, DDLRecord.class.getName(),
			record.getRecordId(), record, serviceContext);

		return record;
	}

	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId,
			Map<String, Serializable> fieldsMap, int displayIndex,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Fields fields = toFields(fieldsMap);

		return addRecord(
			userId, groupId, recordSetId, fields, displayIndex, serviceContext);
	}

	public void deleteRecord(DDLRecord record)
		throws PortalException, SystemException {

		// Record

		ddlRecordPersistence.remove(record);

		// Dynamic data mapping storage

		StorageEngineUtil.deleteByClass(record.getClassPK());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			record.getCompanyId(), record.getGroupId(),
			DDLRecord.class.getName(), record.getRecordId());
	}

	public void deleteRecord(long recordId)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(
			recordId);

		deleteRecord(record);
	}

	public void deleteRecords(long recordSetId)
		throws PortalException, SystemException {

		List<DDLRecord> records = ddlRecordPersistence.findByRecordSetId(
			recordSetId);

		for (DDLRecord record : records) {
			deleteRecord(record);
		}
	}

	public DDLRecord getRecord(long recordId)
		throws PortalException, SystemException {

		return ddlRecordPersistence.findByPrimaryKey(recordId);
	}

	public List<DDLRecord> getRecords(long recordSetId)
		throws SystemException {

		return ddlRecordPersistence.findByRecordSetId(recordSetId);
	}

	public List<DDLRecord> getRecords(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return ddlRecordPersistence.findByRecordSetId(
				recordSetId, start, end, orderByComparator);
		}
		else {
			return ddlRecordPersistence.findByR_S(
				recordSetId, status, start, end, orderByComparator);
		}
	}

	public int getRecordsCount(long recordSetId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return ddlRecordPersistence.countByRecordSetId(recordSetId);
		}
		else {
			return ddlRecordPersistence.countByR_S(recordSetId, status);
		}
	}

	public void updateAsset(
			long userId, DDLRecord record, Locale locale,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		boolean visible = false;

		if (record.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			visible = true;
		}

		DDLRecordSet recordSet = record.getRecordSet();

		String title = LanguageUtil.format(
			locale, "new-record-for-list-x", recordSet.getName(locale));

		assetEntryLocalService.updateEntry(
			userId, record.getGroupId(), DDLRecord.class.getName(),
			record.getRecordId(), record.getUuid(), assetCategoryIds,
			assetTagNames, visible, null, null, null, null,
			ContentTypes.TEXT_HTML, title, null, StringPool.BLANK,
			null, null, 0, 0, null, false);
	}

	public DDLRecord updateRecord(
			long userId, long recordId, Fields fields, int displayIndex,
			boolean mergeFields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));
		record.setDisplayIndex(displayIndex);

		if (!record.isPending()) {
			record.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		ddlRecordPersistence.update(record, false);

		// Dynamic data mapping storage

		StorageEngineUtil.update(
			record.getClassPK(), fields, mergeFields, serviceContext);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), record.getGroupId(), userId,
			DDLRecord.class.getName(), record.getRecordId(), record,
			serviceContext);

		return record;
	}

	public DDLRecord updateRecord(
			long userId, long recordId, Map<String, Serializable> fieldsMap,
			int displayIndex, boolean mergeFields,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Fields fields = toFields(fieldsMap);

		return updateRecord(
			userId, recordId, fields, displayIndex, mergeFields,
			serviceContext);
	}

	public DDLRecord updateStatus(
			long userId, long recordId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(now));
		record.setStatus(status);
		record.setStatusByUserId(user.getUserId());
		record.setStatusByUserName(user.getFullName());
		record.setStatusDate(serviceContext.getModifiedDate(now));

		return ddlRecordPersistence.update(record, false);
	}

	protected Fields toFields(Map<String, Serializable> fieldsMap) {
		Fields fields = new Fields();

		for (String name : fieldsMap.keySet()) {
			String value = String.valueOf(fieldsMap.get(name));

			Field field = new Field(name, value);

			fields.put(field);
		}

		return fields;
	}

}