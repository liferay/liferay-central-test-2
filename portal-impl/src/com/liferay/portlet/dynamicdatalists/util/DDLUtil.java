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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldConstants;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcelllus Tavares
 * @author Eduardo Lundgren
 */
public class DDLUtil {

	public static void addAllReservedEls(
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

	public static JSONArray getJSONArrayColumnSet(DDLRecordSet recordSet)
		throws Exception {

		DDMStructure structure = recordSet.getDDMStructure();

		Map<String, Map<String, String>> fieldsMap = structure.getFieldsMap();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map<String, String> fields : fieldsMap.values()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			boolean editable = GetterUtil.getBoolean(
				fields.get(DDMFieldConstants.EDITABLE), true);

			boolean required = GetterUtil.getBoolean(
				fields.get(DDMFieldConstants.REQUIRED), false);

			boolean sortable = GetterUtil.getBoolean(
				fields.get(DDMFieldConstants.SORTABLE), true);

			String dataType = fields.get(DDMFieldConstants.DATA_TYPE);
			String label = fields.get(DDMFieldConstants.LABEL);
			String name = fields.get(DDMFieldConstants.NAME);
			String type = fields.get(DDMFieldConstants.TYPE);

			jsonObject.put("editable", editable);
			jsonObject.put("required", required);
			jsonObject.put("sortable", sortable);
			jsonObject.put("dataType", dataType);
			jsonObject.put("key", name);
			jsonObject.put("label", label);
			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public static JSONArray getJSONArrayData(DDLRecordSet recordSet)
		throws Exception {

		return getJSONArrayData(recordSet.getRecords());
	}

	public static JSONArray getJSONArrayData(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			jsonArray.put(getJSONObjectData(record));
		}

		return jsonArray;
	}

	public static JSONObject getJSONObjectData(DDLRecord record)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();
		DDMStructure structure = recordSet.getDDMStructure();

		Fields fields = record.getFields();

		Iterator<Field> itr = fields.iterator();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Iterator<String> fieldNames = structure.getFieldNames().iterator();

		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();

			jsonObject.put(fieldName, StringPool.BLANK);
		}

		jsonObject.put("displayIndex", record.getDisplayIndex());
		jsonObject.put("recordId", record.getRecordId());

		while (itr.hasNext()) {
			Field field = itr.next();

			String fieldName = field.getName();
			String fieldValue = String.valueOf(field.getValue());

			jsonObject.put(fieldName, fieldValue);
		}

		return jsonObject;
	}

	public static String getTemplateContent(
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

	private static Transformer _transformer = new DDLTransformer();

}