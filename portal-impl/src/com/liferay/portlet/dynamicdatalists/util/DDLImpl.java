/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcelllus Tavares
 * @author Eduardo Lundgren
 */
public class DDLImpl implements DDL {

	public void addAllReservedEls(
		Element rootElement, Map<String, String> tokens,
		DDLRecordSet recordSet) {

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_ID,
			String.valueOf(recordSet.getRecordSetId()));

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			String.valueOf(recordSet.getDDMStructureId()));
	}

	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception {
		return getRecordJSONObject(record, false);
	}

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

		Fields fields = StorageEngineUtil.getFields(
			recordVersion.getDDMStorageId());

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

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

	public JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Map<String, Map<String, String>> fieldsMap =
			ddmStructure.getFieldsMap();

		for (Map<String, String> fields : fieldsMap.values()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String dataType = fields.get(FieldConstants.DATA_TYPE);

			jsonObject.put("dataType", dataType);

			boolean editable = GetterUtil.getBoolean(
				fields.get(FieldConstants.EDITABLE), true);

			jsonObject.put("editable", editable);

			String label = fields.get(FieldConstants.LABEL);

			jsonObject.put("label", label);

			String name = fields.get(FieldConstants.NAME);

			jsonObject.put("name", name);

			boolean required = GetterUtil.getBoolean(
				fields.get(FieldConstants.REQUIRED));

			jsonObject.put("required", required);

			boolean sortable = GetterUtil.getBoolean(
				fields.get(FieldConstants.SORTABLE), true);

			jsonObject.put("sortable", sortable);

			String type = fields.get(FieldConstants.TYPE);

			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception {

		return getRecordsJSONArray(recordSet.getRecords(), false);
	}

	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(record);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

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

	public String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		String viewMode = ParamUtil.getString(renderRequest, "viewMode");

		String languageId = LanguageUtil.getLanguageId(renderRequest);

		String xmlRequest = PortletRequestUtil.toXML(
			renderRequest, renderResponse);

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			recordSet.getGroupId(), themeDisplay, xmlRequest);

		tokens.put("template_id", StringUtil.valueOf(ddmTemplateId));

		String xml = StringPool.BLANK;

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Document requestDocument = SAXReaderUtil.read(xmlRequest);

		rootElement.add(requestDocument.getRootElement().createCopy());

		addAllReservedEls(rootElement, tokens, recordSet);

		xml = DDMXMLUtil.formatXML(document);

		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		return _transformer.transform(
			themeDisplay, tokens, viewMode, languageId, xml,
			template.getScript(), template.getLanguage());
	}

	public void sendRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			DDLRecord record, String fieldName)
		throws Exception {

		Field field = record.getField(fieldName);

		DDMUtil.sendFieldFile(request, response, field);
	}

	public void sendRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			long recordId, String fieldName)
		throws Exception {

		DDLRecord record = DDLRecordServiceUtil.getRecord(recordId);

		sendRecordFileUpload(request, response, record, fieldName);
	}

	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception {

		boolean majorVersion = ParamUtil.getBoolean(
			serviceContext, "majorVersion");

		DDLRecord record = DDLRecordLocalServiceUtil.fetchRecord(recordId);

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

		uploadRecordFieldFiles(record, serviceContext);

		return record;
	}

	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		return updateRecord(
			recordId, recordSetId, mergeFields, true, serviceContext);
	}

	public String uploadRecordFieldFile(
			DDLRecord record, String fieldName, ServiceContext serviceContext)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		return DDMUtil.uploadFieldFile(
			ddmStructure.getStructureId(), recordVersion.getDDMStorageId(),
			record, fieldName, serviceContext);
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
				LocaleUtil.getDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	protected void uploadRecordFieldFiles(
			DDLRecord record, ServiceContext serviceContext)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		for (String fieldName : ddmStructure.getFieldNames()) {
			String fieldDataType = ddmStructure.getFieldDataType(fieldName);

			if (fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {
				uploadRecordFieldFile(record, fieldName, serviceContext);
			}
		}
	}

	private Transformer _transformer = new DDLTransformer();

}