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

package com.liferay.dynamic.data.lists.exporter.impl;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = DDLExporter.class)
public class DDLCSVExporter extends BaseDDLExporter {

	@Override
	public String getFormat() {
		return "csv";
	}

	@Override
	protected byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		StringBundler sb = new StringBundler();

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		List<DDMFormField> ddmFormFields = getDDMFormFields(ddmStructure);

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue label = ddmFormField.getLabel();

			sb.append(CSVUtil.encode(label.getString(getLocale())));

			sb.append(CharPool.COMMA);
		}

		sb.append(LanguageUtil.get(getLocale(), "status"));
		sb.append(StringPool.NEW_LINE);

		List<DDLRecord> records = _ddlRecordLocalService.getRecords(
			recordSetId, status, start, end, orderByComparator);

		Iterator<DDLRecord> iterator = records.iterator();

		while (iterator.hasNext()) {
			DDLRecord record = iterator.next();

			DDLRecordVersion recordVersion = record.getRecordVersion();

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				recordVersion.getDDMStorageId());

			List<String> values = null;

			int scope = recordSet.getScope();

			if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
				values = getDDLFormRecordValues(ddmFormValues, ddmFormFields);
			}
			else {
				Fields fields = _ddmFormValuesToFieldsConverter.convert(
					ddmStructure, ddmFormValues);

				values = getDDLRecordValues(fields, ddmFormFields);
			}

			for (String value : values) {
				sb.append(CSVUtil.encode(value));
				sb.append(CharPool.COMMA);
			}

			sb.append(getStatusMessage(recordVersion.getStatus()));

			if (iterator.hasNext()) {
				sb.append(StringPool.NEW_LINE);
			}
		}

		String csv = sb.toString();

		return csv.getBytes();
	}

	protected List<String> getDDLFormRecordValues(
			DDMFormValues ddmFormValues, List<DDMFormField> ddmFormFields)
		throws Exception {

		List<String> ddlFormValues = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			String name = ddmFormField.getName();
			String value = StringPool.BLANK;

			String fieldType = ddmFormField.getType();

			DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
					fieldType);

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			if (ddmFormFieldValueMap.containsKey(name)) {
				List<DDMFormFieldValue> ddmForFieldValueList =
					ddmFormFieldValueMap.get(name);

				if (ddmForFieldValueList.size() == 1) {
					DDMFormFieldValue ddmFormFieldValue =
						ddmForFieldValueList.get(0);

					value = ddmFormFieldValueRenderer.render(
						ddmFormFieldValue, getLocale());
				}
			}

			ddlFormValues.add(value);
		}

		return ddlFormValues;
	}

	protected List<String> getDDLRecordValues(
			Fields fields, List<DDMFormField> ddmFormFields)
		throws Exception {

		List<String> ddlFormValues = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			String name = ddmFormField.getName();
			String value = StringPool.BLANK;

			if (fields.contains(name)) {
				Field field = fields.get(name);

				value = field.getRenderedValue(getLocale());
			}

			ddlFormValues.add(value);
		}

		return ddlFormValues;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesToFieldsConverter(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordSetService _ddlRecordSetService;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private StorageEngine _storageEngine;

}