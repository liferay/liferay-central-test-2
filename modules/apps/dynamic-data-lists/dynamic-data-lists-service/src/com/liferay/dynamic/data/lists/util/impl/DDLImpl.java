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

package com.liferay.dynamic.data.lists.util.impl;

import com.liferay.dynamic.data.lists.configuration.DynamicDataListServiceConfigurationUtil;
import com.liferay.dynamic.data.lists.configuration.DynamicDataListServiceConfigurationValues;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.util.DDL;
import com.liferay.dynamic.data.lists.util.DDLConstants;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(immediate = true)
public class DDLImpl implements DDL {

	@Override
	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception {
		return getRecordJSONObject(record, false);
	}

	@Override
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String fieldName : ddmStructure.getFieldNames()) {
			jsonObject.put(fieldName, StringPool.BLANK);
		}

		jsonObject.put("displayIndex", record.getDisplayIndex());
		jsonObject.put("recordId", record.getRecordId());

		DDLRecordVersion recordVersion = record.getRecordVersion();

		if (latestRecordVersion) {
			recordVersion = record.getLatestRecordVersion();
		}

		DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(
			recordVersion.getDDMStorageId());

		Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
			ddmStructure, ddmFormValues);

		for (Field field : fields) {
			String fieldName = field.getName();
			String fieldType = field.getType();
			Object fieldValue = field.getValue();

			if (fieldValue instanceof Date) {
				jsonObject.put(fieldName, ((Date)fieldValue).getTime());
			}
			else if (fieldType.equals(DDMImpl.TYPE_DDM_DOCUMENTLIBRARY) &&
					 Validator.isNotNull(fieldValue)) {

				JSONObject fieldValueJSONObject =
					JSONFactoryUtil.createJSONObject(
						String.valueOf(fieldValue));

				String uuid = fieldValueJSONObject.getString("uuid");
				long groupId = fieldValueJSONObject.getLong("groupId");

				fieldValueJSONObject.put(
					"title", getFileEntryTitle(uuid, groupId));

				jsonObject.put(fieldName, fieldValueJSONObject.toString());
			}
			else if (fieldType.equals(DDMImpl.TYPE_DDM_LINK_TO_PAGE) &&
					 Validator.isNotNull(fieldValue)) {

				JSONObject fieldValueJSONObject =
					JSONFactoryUtil.createJSONObject(
						String.valueOf(fieldValue));

				long groupId = fieldValueJSONObject.getLong("groupId");
				boolean privateLayout = fieldValueJSONObject.getBoolean(
					"privateLayout");
				long layoutId = fieldValueJSONObject.getLong("layoutId");
				Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

				String layoutName = getLayoutName(
					groupId, privateLayout, layoutId,
					LanguageUtil.getLanguageId(locale));

				fieldValueJSONObject.put("name", layoutName);

				jsonObject.put(fieldName, fieldValueJSONObject.toString());
			}
			else if ((fieldType.equals(DDMImpl.TYPE_RADIO) ||
					  fieldType.equals(DDMImpl.TYPE_SELECT)) &&
					 Validator.isNotNull(fieldValue)) {

				fieldValue = JSONFactoryUtil.createJSONArray(
					String.valueOf(fieldValue));

				jsonObject.put(fieldName, (JSONArray)fieldValue);
			}
			else {
				jsonObject.put(fieldName, String.valueOf(fieldValue));
			}
		}

		return jsonObject;
	}

	@Override
	public List<DDLRecord> getRecords(Hits hits) throws Exception {
		List<DDLRecord> records = new ArrayList<>();

		List<com.liferay.portal.kernel.search.Document> documents =
			hits.toList();

		for (com.liferay.portal.kernel.search.Document document : documents) {
			long recordId = GetterUtil.getLong(
				document.get(
					com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK));

			try {
				DDLRecord record = DDLRecordLocalServiceUtil.getRecord(
					recordId);

				records.add(record);
			}
			catch (NoSuchRecordException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDL record index is stale and contains record " +
							recordId,
						nsre);
				}

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					DDLRecord.class);

				long companyId = GetterUtil.getLong(
					document.get(
						com.liferay.portal.kernel.search.Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
		}

		return records;
	}

	@Override
	public JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Locale locale = LocaleUtil.fromLanguageId(
			ddmStructure.getDefaultLanguageId());

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			String name = ddmFormField.getName();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String dataType = ddmFormField.getDataType();

			jsonObject.put("dataType", dataType);

			boolean readOnly = ddmFormField.isReadOnly();

			jsonObject.put("editable", !readOnly);

			LocalizedValue label = ddmFormField.getLabel();

			jsonObject.put("label", label.getString(locale));

			jsonObject.put("name", name);

			boolean required = ddmFormField.isRequired();

			jsonObject.put("required", required);

			jsonObject.put("sortable", true);

			String type = ddmFormField.getType();

			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception {

		return getRecordsJSONArray(recordSet.getRecords(), false);
	}

	@Override
	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(record);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(
				record, latestRecordVersion);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		Transformer transformer = TransformerHolder.getTransformer();

		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put(
			DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			recordSet.getDDMStructureId());
		contextObjects.put(
			DDLConstants.RESERVED_DDM_TEMPLATE_ID, ddmTemplateId);
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription(themeDisplay.getLocale()));
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_ID, recordSet.getRecordSetId());
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName(themeDisplay.getLocale()));
		contextObjects.put(TemplateConstants.TEMPLATE_ID, ddmTemplateId);

		String viewMode = Constants.VIEW;

		if (renderRequest != null) {
			viewMode = ParamUtil.getString(
				renderRequest, "viewMode", Constants.VIEW);
		}

		contextObjects.put("viewMode", viewMode);

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		contextObjects.put(
			TemplateConstants.CLASS_NAME_ID, ddmTemplate.getClassNameId());

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(ddmTemplate.getLanguage());

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				DDLRecordSet.class.getName());

		templateManager.addContextObjects(
			contextObjects, templateHandler.getCustomContextObjects());

		return transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			ddmTemplate.getLanguage(), new UnsyncStringWriter());
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception {

		return true;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
		throws Exception {

		return true;
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception {

		DDLRecord record = DDLRecordLocalServiceUtil.fetchRecord(recordId);

		boolean majorVersion = ParamUtil.getBoolean(
			serviceContext, "majorVersion");

		DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getDDLRecordSet(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = DDMUtil.getFields(
			ddmStructure.getStructureId(), serviceContext);

		if (record != null) {
			if (checkPermission) {
				record = DDLRecordServiceUtil.updateRecord(
					recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					mergeFields, serviceContext);
			}
			else {
				record = DDLRecordLocalServiceUtil.updateRecord(
					serviceContext.getUserId(), recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					mergeFields, serviceContext);
			}
		}
		else {
			if (checkPermission) {
				record = DDLRecordServiceUtil.addRecord(
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					serviceContext);
			}
			else {
				record = DDLRecordLocalServiceUtil.addRecord(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					serviceContext);
			}
		}

		return record;
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		return updateRecord(
			recordId, recordSetId, mergeFields, true, serviceContext);
	}

	protected String getFileEntryTitle(String uuid, long groupId) {
		try {
			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

			return fileEntry.getTitle();
		}
		catch (Exception e) {
			return LanguageUtil.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	protected String getLayoutName(
		long groupId, boolean privateLayout, long layoutId, String languageId) {

		try {
			return LayoutServiceUtil.getLayoutName(
				groupId, privateLayout, layoutId, languageId);
		}
		catch (Exception e) {
			return LanguageUtil.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DDLImpl.class);

	private static class TransformerHolder {

		public static Transformer getTransformer() {
			return _transformer;
		}

		private static final Transformer _transformer = new Transformer(
				DynamicDataListServiceConfigurationValues.DYNAMIC_DATA_LISTS_ERROR_TEMPLATE, true){
			
			@Override
			protected void loadErrorTemplateIds(String errorTemplatePropertyKey) {
				
				Set<String> langTypes = TemplateManagerUtil.getTemplateManagerNames();

					for (String langType : langTypes) {
						String errorTemplateId = DynamicDataListServiceConfigurationUtil.get(
							errorTemplatePropertyKey, new Filter(langType));

						if (Validator.isNotNull(errorTemplateId)) {
							_errorTemplateIds.put(langType, errorTemplateId);
						}
					}
			};
			
		};

	}

}