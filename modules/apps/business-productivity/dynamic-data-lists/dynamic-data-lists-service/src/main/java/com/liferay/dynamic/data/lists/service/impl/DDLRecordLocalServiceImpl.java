/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordLocalServiceBaseImpl;
import com.liferay.dynamic.data.lists.util.DDL;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngineUtil;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) records.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class DDLRecordLocalServiceImpl extends DDLRecordLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		long recordId = counterLocalService.increment();

		DDLRecord record = ddlRecordPersistence.create(recordId);

		record.setUuid(serviceContext.getUuid());
		record.setGroupId(groupId);
		record.setCompanyId(user.getCompanyId());
		record.setUserId(user.getUserId());
		record.setUserName(user.getFullName());
		record.setVersionUserId(user.getUserId());
		record.setVersionUserName(user.getFullName());

		long ddmStorageId = StorageEngineUtil.create(
			recordSet.getCompanyId(), recordSet.getDDMStructureId(),
			ddmFormValues, serviceContext);

		record.setDDMStorageId(ddmStorageId);

		record.setRecordSetId(recordSetId);
		record.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		record.setDisplayIndex(displayIndex);

		ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = addRecordVersion(
			user, record, ddmStorageId, DDLRecordConstants.VERSION_DEFAULT,
			displayIndex, WorkflowConstants.STATUS_DRAFT);

		// Asset

		Locale locale = serviceContext.getLocale();

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), locale,
			serviceContext.getAssetPriority());

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId,
			getWorkflowAssetClassName(recordSet),
			recordVersion.getRecordVersionId(), recordVersion, serviceContext);

		return record;
	}

	@Override
	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			Fields fields, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		DDMFormValues ddmFormValues =
			FieldsToDDMFormValuesConverterUtil.convert(
				recordSet.getDDMStructure(), fields);

		return ddlRecordLocalService.addRecord(
			userId, groupId, recordSetId, displayIndex, ddmFormValues,
			serviceContext);
	}

	@Override
	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			Map<String, Serializable> fieldsMap, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = toFields(
			ddmStructure.getStructureId(), fieldsMap,
			serviceContext.getLocale(), LocaleUtil.getSiteDefault(), true);

		return ddlRecordLocalService.addRecord(
			userId, groupId, recordSetId, displayIndex, fields, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public DDLRecord deleteRecord(DDLRecord record) throws PortalException {

		// Record

		ddlRecordPersistence.remove(record);

		// Record Versions

		List<DDLRecordVersion> recordVersions =
			ddlRecordVersionPersistence.findByRecordId(record.getRecordId());

		for (DDLRecordVersion recordVersion : recordVersions) {
			ddlRecordVersionPersistence.remove(recordVersion);

			// Dynamic data mapping storage

			StorageEngineUtil.deleteByClass(recordVersion.getDDMStorageId());

			// Workflow

			deleteWorkflowInstanceLink(
				record.getCompanyId(), record.getGroupId(),
				recordVersion.getPrimaryKey());
		}

		// Asset

		deleteAssetEntry(record.getRecordId());

		// Ratings

		deleteRatingsStats(record.getRecordId());

		return record;
	}

	@Override
	public void deleteRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		ddlRecordLocalService.deleteRecord(record);
	}

	@Override
	public DDLRecord deleteRecordLocale(
			long recordId, Locale locale, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		DDLRecordSet recordSet = record.getRecordSet();

		DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(
			record.getDDMStorageId());

		Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
			recordSet.getDDMStructure(), ddmFormValues);

		for (Field field : fields) {
			Map<Locale, List<Serializable>> valuesMap = field.getValuesMap();

			valuesMap.remove(locale);
		}

		return ddlRecordLocalService.updateRecord(
			serviceContext.getUserId(), recordId, false,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields, false,
			serviceContext);
	}

	@Override
	public void deleteRecords(long recordSetId) throws PortalException {
		List<DDLRecord> records = ddlRecordPersistence.findByRecordSetId(
			recordSetId);

		for (DDLRecord record : records) {
			ddlRecordLocalService.deleteRecord(record);
		}
	}

	@Override
	public DDLRecord fetchRecord(long recordId) {
		return ddlRecordPersistence.fetchByPrimaryKey(recordId);
	}

	@Override
	public List<DDLRecord> getCompanyRecords(
		long companyId, int status, int scope, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return ddlRecordFinder.findByC_S_S(
			companyId, status, scope, start, end, orderByComparator);
	}

	@Override
	public int getCompanyRecordsCount(long companyId, int status, int scope) {
		return ddlRecordFinder.countByC_S_S(companyId, status, scope);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getLatestRecordVersion(
	 *             long)}
	 */
	@Deprecated
	@Override
	public DDLRecordVersion getLatestRecordVersion(long recordId)
		throws PortalException {

		return ddlRecordVersionLocalService.getLatestRecordVersion(recordId);
	}

	@Override
	public Long[] getMinAndMaxCompanyRecordIds(
		long companyId, int status, int scope) {

		return ddlRecordFinder.findByC_S_S_MinAndMax(companyId, status, scope);
	}

	@Override
	public List<DDLRecord> getMinAndMaxCompanyRecords(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId) {

		return ddlRecordFinder.findByC_S_S_MinAndMax(
			companyId, status, scope, minRecordId, maxRecordId);
	}

	@Override
	public DDLRecord getRecord(long recordId) throws PortalException {
		return ddlRecordPersistence.findByPrimaryKey(recordId);
	}

	@Override
	public List<DDLRecord> getRecords(long recordSetId) {
		return ddlRecordPersistence.findByRecordSetId(recordSetId);
	}

	@Override
	public List<DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return ddlRecordFinder.findByR_S(
			recordSetId, status, start, end, orderByComparator);
	}

	@Override
	public List<DDLRecord> getRecords(long recordSetId, long userId) {
		return ddlRecordPersistence.findByR_U(recordSetId, userId);
	}

	@Override
	public int getRecordsCount(long recordSetId, int status) {
		return ddlRecordFinder.countByR_S(recordSetId, status);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	 *             long)}
	 */
	@Deprecated
	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	 *             long, String)}
	 */
	@Deprecated
	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersions(
	 *             long, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersionsCount(
	 *             long)}
	 */
	@Deprecated
	@Override
	public int getRecordVersionsCount(long recordId) {
		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

	@Override
	public void revertRecord(
			long userId, long recordId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordVersion recordVersion =
			ddlRecordVersionLocalService.getRecordVersion(recordId, version);

		if (!recordVersion.isApproved()) {
			return;
		}

		DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(
			recordVersion.getDDMStorageId());

		serviceContext.setCommand(Constants.REVERT);

		ddlRecordLocalService.updateRecord(
			userId, recordId, true, recordVersion.getDisplayIndex(),
			ddmFormValues, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void revertRecordVersion(
			long userId, long recordId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		revertRecord(userId, recordId, version, serviceContext);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<DDLRecord> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DDLRecord.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<DDLRecord> searchDDLRecords(
		SearchContext searchContext) {

		try {
			Indexer<DDLRecord> indexer = getDDLRecordIndexer();

			Hits hits = indexer.search(searchContext, DDL.SELECTED_FIELD_NAMES);

			List<DDLRecord> records = getRecords(hits);

			return new BaseModelSearchResult<>(records, hits.getLength());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public void updateAsset(
			long userId, DDLRecord record, DDLRecordVersion recordVersion,
			long[] assetCategoryIds, String[] assetTagNames, Locale locale,
			Double priority)
		throws PortalException {

		DDLRecordSet recordSet = record.getRecordSet();

		int scope = recordSet.getScope();

		if ((scope != DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) &&
			(scope != DDLRecordSetConstants.SCOPE_FORMS)) {

			return;
		}

		boolean addDraftAssetEntry = false;
		boolean visible = true;

		if ((recordVersion != null) && !recordVersion.isApproved()) {
			String version = recordVersion.getVersion();

			if (!version.equals(DDLRecordConstants.VERSION_DEFAULT)) {
				int approvedRecordVersionsCount =
					ddlRecordVersionPersistence.countByR_S(
						record.getRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (approvedRecordVersionsCount > 0) {
					addDraftAssetEntry = true;
				}
			}

			visible = false;
		}

		if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
			visible = false;
		}

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		String ddmStructureName = ddmStructure.getName(locale);

		String recordSetName = recordSet.getName(locale);

		String title = LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, recordSetName}, false);

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(),
				DDLRecordConstants.getClassName(scope),
				recordVersion.getRecordVersionId(), record.getUuid(), 0,
				assetCategoryIds, assetTagNames, true, false, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, priority);
		}
		else {
			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(),
				DDLRecordConstants.getClassName(scope), record.getRecordId(),
				record.getUuid(), 0, assetCategoryIds, assetTagNames, true,
				visible, null, null, null, ContentTypes.TEXT_HTML, title, null,
				StringPool.BLANK, null, null, 0, 0, priority);
		}
	}

	@Override
	public DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));

		ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		if (recordVersion.isApproved()) {
			DDLRecordSet recordSet = record.getRecordSet();

			long ddmStorageId = StorageEngineUtil.create(
				recordSet.getCompanyId(), recordSet.getDDMStructureId(),
				ddmFormValues, serviceContext);
			String version = getNextVersion(
				recordVersion.getVersion(), majorVersion,
				serviceContext.getWorkflowAction());

			recordVersion = addRecordVersion(
				user, record, ddmStorageId, version, displayIndex,
				WorkflowConstants.STATUS_DRAFT);
		}
		else {
			StorageEngineUtil.update(
				recordVersion.getDDMStorageId(), ddmFormValues, serviceContext);

			String version = recordVersion.getVersion();

			updateRecordVersion(
				user, recordVersion, version, displayIndex,
				recordVersion.getStatus(), serviceContext);
		}

		if (isKeepRecordVersionLabel(
				record.getRecordVersion(), recordVersion, serviceContext)) {

			ddlRecordVersionPersistence.remove(recordVersion);

			// Dynamic data mapping storage

			StorageEngineUtil.deleteByClass(recordVersion.getDDMStorageId());

			return record;
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), record.getGroupId(), userId,
			DDLRecord.class.getName(), recordVersion.getRecordVersionId(),
			recordVersion, serviceContext);

		return record;
	}

	@Override
	public DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			Fields fields, boolean mergeFields, ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		DDLRecordSet recordSet = record.getRecordSet();

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		if (mergeFields) {
			DDMFormValues existingDDMFormValues =
				StorageEngineUtil.getDDMFormValues(
					recordVersion.getDDMStorageId());

			Fields existingFields = DDMFormValuesToFieldsConverterUtil.convert(
				recordSet.getDDMStructure(), existingDDMFormValues);

			fields = ddm.mergeFields(fields, existingFields);
		}

		DDMFormValues ddmFormValues =
			FieldsToDDMFormValuesConverterUtil.convert(
				recordSet.getDDMStructure(), fields);

		return ddlRecordLocalService.updateRecord(
			userId, recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
	}

	@Override
	public DDLRecord updateRecord(
			long userId, long recordId, int displayIndex,
			Map<String, Serializable> fieldsMap, boolean mergeFields,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		DDMFormValues oldDDMFormValues = record.getDDMFormValues();

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = toFields(
			ddmStructure.getStructureId(), fieldsMap,
			serviceContext.getLocale(), oldDDMFormValues.getDefaultLocale(),
			false);

		return ddlRecordLocalService.updateRecord(
			userId, recordId, false, displayIndex, fields, mergeFields,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord updateStatus(
			long userId, long recordVersionId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Record version

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecordVersion recordVersion =
			ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);

		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(new Date());

		ddlRecordVersionPersistence.update(recordVersion);

		// Record

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(
			recordVersion.getRecordId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (DLUtil.compareVersions(
					record.getVersion(), recordVersion.getVersion()) <= 0) {

				record.setDDMStorageId(recordVersion.getDDMStorageId());
				record.setVersion(recordVersion.getVersion());
				record.setRecordSetId(recordVersion.getRecordSetId());
				record.setDisplayIndex(recordVersion.getDisplayIndex());
				record.setVersion(recordVersion.getVersion());
				record.setVersionUserId(recordVersion.getUserId());
				record.setVersionUserName(recordVersion.getUserName());

				ddlRecordPersistence.update(record);
			}
		}
		else {
			if (Validator.equals(
					record.getVersion(), recordVersion.getVersion())) {

				String newVersion = DDLRecordConstants.VERSION_DEFAULT;

				List<DDLRecordVersion> approvedRecordVersions =
					ddlRecordVersionPersistence.findByR_S(
						record.getRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedRecordVersions.isEmpty()) {
					newVersion = approvedRecordVersions.get(0).getVersion();
				}

				record.setVersion(newVersion);

				ddlRecordPersistence.update(record);
			}
		}

		// Asset

		Locale locale = serviceContext.getLocale();

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), locale,
			serviceContext.getAssetPriority());

		return record;
	}

	protected DDLRecordVersion addRecordVersion(
		User user, DDLRecord record, long ddmStorageId, String version,
		int displayIndex, int status) {

		long recordVersionId = counterLocalService.increment();

		DDLRecordVersion recordVersion = ddlRecordVersionPersistence.create(
			recordVersionId);

		recordVersion.setGroupId(record.getGroupId());
		recordVersion.setCompanyId(record.getCompanyId());
		recordVersion.setUserId(user.getUserId());
		recordVersion.setUserName(user.getFullName());
		recordVersion.setCreateDate(record.getModifiedDate());
		recordVersion.setDDMStorageId(ddmStorageId);
		recordVersion.setRecordSetId(record.getRecordSetId());
		recordVersion.setRecordId(record.getRecordId());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(record.getModifiedDate());

		ddlRecordVersionPersistence.update(recordVersion);

		return recordVersion;
	}

	protected void deleteAssetEntry(long recordId) throws PortalException {
		assetEntryLocalService.deleteEntry(
			DDLFormRecord.class.getName(), recordId);

		assetEntryLocalService.deleteEntry(DDLRecord.class.getName(), recordId);
	}

	protected void deleteRatingsStats(long recordId) throws PortalException {
		ratingsStatsLocalService.deleteStats(
			DDLFormRecord.class.getName(), recordId);

		ratingsStatsLocalService.deleteStats(
			DDLRecord.class.getName(), recordId);
	}

	protected void deleteWorkflowInstanceLink(
			long companyId, long groupId, long recordVersionId)
		throws PortalException {

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDLFormRecord.class.getName(), recordVersionId);

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDLRecord.class.getName(), recordVersionId);
	}

	protected Indexer<DDLRecord> getDDLRecordIndexer() {
		Indexer<DDLRecord> indexer = indexerRegistry.nullSafeGetIndexer(
			DDLRecord.class);

		return indexer;
	}

	protected String getNextVersion(
		String version, boolean majorVersion, int workflowAction) {

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected List<DDLRecord> getRecords(Hits hits) throws PortalException {
		List<DDLRecord> records = new ArrayList<>();

		for (Document document : hits.toList()) {
			long recordId = GetterUtil.getLong(
				document.get(
					com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK));

			try {
				DDLRecord record = getRecord(recordId);

				records.add(record);
			}
			catch (NoSuchRecordException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDL record index is stale and contains record " +
							recordId,
						nsre);
				}

				long companyId = GetterUtil.getLong(
					document.get(
						com.liferay.portal.kernel.search.Field.COMPANY_ID));

				Indexer<DDLRecord> indexer = getDDLRecordIndexer();

				indexer.delete(companyId, document.getUID());
			}
		}

		return records;
	}

	protected String getWorkflowAssetClassName(DDLRecordSet recordSet) {
		if (recordSet.getScope() == DDLRecordSetConstants.SCOPE_FORMS) {
			return DDLFormRecord.class.getName();
		}

		return DDLRecord.class.getName();
	}

	/**
	 * @see com.liferay.portlet.documentlibrary.util.DLFileVersionPolicyImpl#isKeepFileVersionLabel(
	 *      com.liferay.portlet.documentlibrary.model.DLFileEntry,
	 *      com.liferay.portlet.documentlibrary.model.DLFileVersion,
	 *      com.liferay.portlet.documentlibrary.model.DLFileVersion,
	 *      ServiceContext)
	 */
	protected boolean isKeepRecordVersionLabel(
			DDLRecordVersion lastRecordVersion,
			DDLRecordVersion latestRecordVersion, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.equals(serviceContext.getCommand(), Constants.REVERT)) {
			return false;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			return false;
		}

		if (Validator.equals(
				lastRecordVersion.getVersion(),
				latestRecordVersion.getVersion())) {

			return false;
		}

		DDMFormValues lastDDMFormValues = StorageEngineUtil.getDDMFormValues(
			lastRecordVersion.getDDMStorageId());
		DDMFormValues latestDDMFormValues = StorageEngineUtil.getDDMFormValues(
			latestRecordVersion.getDDMStorageId());

		if (!lastDDMFormValues.equals(latestDDMFormValues)) {
			return false;
		}

		ExpandoBridge lastExpandoBridge = lastRecordVersion.getExpandoBridge();
		ExpandoBridge latestExpandoBridge =
			latestRecordVersion.getExpandoBridge();

		Map<String, Serializable> lastAttributes =
			lastExpandoBridge.getAttributes();
		Map<String, Serializable> latestAttributes =
			latestExpandoBridge.getAttributes();

		if (!lastAttributes.equals(latestAttributes)) {
			return false;
		}

		return true;
	}

	protected Fields toFields(
		long ddmStructureId, Map<String, Serializable> fieldsMap, Locale locale,
		Locale defaultLocale, boolean create) {

		Fields fields = new Fields();

		for (Map.Entry<String, Serializable> entry : fieldsMap.entrySet()) {
			Field field = new Field();

			field.setDDMStructureId(ddmStructureId);
			field.setName(entry.getKey());
			field.addValue(locale, String.valueOf(entry.getValue()));

			if (create && !locale.equals(defaultLocale)) {
				field.addValue(defaultLocale, String.valueOf(entry.getValue()));
			}

			field.setDefaultLocale(defaultLocale);

			fields.put(field);
		}

		return fields;
	}

	protected void updateRecordVersion(
		User user, DDLRecordVersion recordVersion, String version,
		int displayIndex, int status, ServiceContext serviceContext) {

		recordVersion.setUserId(user.getUserId());
		recordVersion.setUserName(user.getFullName());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(serviceContext.getModifiedDate(null));

		ddlRecordVersionPersistence.update(recordVersion);
	}

	@ServiceReference(type = DDM.class)
	protected DDM ddm;

	@ServiceReference(type = IndexerRegistry.class)
	protected IndexerRegistry indexerRegistry;

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordLocalServiceImpl.class);

}