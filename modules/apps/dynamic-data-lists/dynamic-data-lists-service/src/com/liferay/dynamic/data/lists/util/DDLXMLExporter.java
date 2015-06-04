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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;

import java.io.Serializable;

import java.util.List;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public class DDLXMLExporter extends BaseDDLExporter {

	protected void addFieldElement(
		Element fieldsElement, String label, Serializable value) {

		Element fieldElement = fieldsElement.addElement("field");

		Element labelElement = fieldElement.addElement("label");

		labelElement.addText(label);

		Element valueElement = fieldElement.addElement("value");

		valueElement.addText(String.valueOf(value));
	}

	@Override
	protected byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		DDMStructure ddmStructure = getDDMStructure(recordSetId);

		List<DDMFormField> ddmFormFields = getDDMFormFields(ddmStructure);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		List<DDLRecord> records = DDLRecordLocalServiceUtil.getRecords(
			recordSetId, status, start, end, orderByComparator);

		for (DDLRecord record : records) {
			Element fieldsElement = rootElement.addElement("fields");

			DDLRecordVersion recordVersion = record.getRecordVersion();

			DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(
				recordVersion.getDDMStorageId());

			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);

			for (DDMFormField ddmFormField : ddmFormFields) {
				LocalizedValue label = ddmFormField.getLabel();

				String name = ddmFormField.getName();

				String value = StringPool.BLANK;

				if (fields.contains(name)) {
					Field field = fields.get(name);

					value = field.getRenderedValue(getLocale());
				}

				addFieldElement(
					fieldsElement, label.getString(getLocale()), value);
			}

			addFieldElement(
				fieldsElement, LanguageUtil.get(getLocale(), "status"),
				getStatusMessage(recordVersion.getStatus()));
		}

		String xml = document.asXML();

		return xml.getBytes();
	}

}