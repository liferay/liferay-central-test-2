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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordSetVersionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
@ProviderType
public abstract class BaseDDLExporter implements DDLExporter {

	@Override
	public byte[] export(long recordSetId) throws Exception {
		return doExport(
			recordSetId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long recordSetId, int status) throws Exception {
		return doExport(
			recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public byte[] export(long recordSetId, int status, int start, int end)
		throws Exception {

		return doExport(recordSetId, status, start, end, null);
	}

	@Override
	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		return doExport(recordSetId, status, start, end, orderByComparator);
	}

	@Override
	public Locale getLocale() {
		if (_locale == null) {
			_locale = LocaleUtil.getSiteDefault();
		}

		return _locale;
	}

	@Override
	public void setLocale(Locale locale) {
		_locale = locale;
	}

	protected abstract byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception;

	protected abstract DDLRecordSetVersionService
		getDDLRecordSetVersionService();

	protected DDMFormFieldRenderedValue getDDMFormFieldRenderedValue(
		DDMFormField ddmFormField, Fields fields) {

		Field field = fields.get(ddmFormField.getName());

		try {
			String value = field.getRenderedValue(getLocale());

			return new DDMFormFieldRenderedValue(
				ddmFormField.getLabel(), value);
		}
		catch (Exception e) {
			return new DDMFormFieldRenderedValue(null, StringPool.BLANK);
		}
	}

	protected DDMFormFieldRenderedValue getDDMFormFieldRenderedValue(
		DDMFormField ddmFormField,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap) {

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			getDDMFormFieldTypeServicesTracker().getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		List<DDMFormFieldValue> ddmForFieldValues = ddmFormFieldValueMap.get(
			ddmFormField.getName());

		String value = ddmFormFieldValueRenderer.render(
			ddmForFieldValues.get(0), getLocale());

		return new DDMFormFieldRenderedValue(ddmFormField.getLabel(), value);
	}

	protected List<DDMFormFieldRenderedValue> getDDMFormFieldRenderedValues(
			DDMFormValues ddmFormValues, List<DDMFormField> ddmFormFields)
		throws Exception {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream().filter(
			ddmFormField ->
				ddmFormFieldValueMap.containsKey(ddmFormField.getName()));

		Stream<DDMFormFieldRenderedValue> valueStream = ddmFormFieldStream.map(
			ddmFormField -> getDDMFormFieldRenderedValue(
				ddmFormField, ddmFormFieldValueMap));

		return valueStream.collect(Collectors.toList());
	}

	protected List<DDMFormFieldRenderedValue> getDDMFormFieldRenderedValues(
			Fields fields, List<DDMFormField> ddmFormFields)
		throws Exception {

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream().filter(
			ddmFormField -> fields.contains(ddmFormField.getName()));

		Stream<DDMFormFieldRenderedValue> valueStream = ddmFormFieldStream.map(
			ddmFormField -> getDDMFormFieldRenderedValue(ddmFormField, fields));

		valueStream = valueStream.filter(value -> value._label != null);

		return valueStream.collect(Collectors.toList());
	}

	protected List<DDMFormField> getDDMFormFields(DDMStructure ddmStructure)
		throws Exception {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmStructure.getDDMFormFields(false)) {
			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected abstract
		DDMFormFieldTypeServicesTracker getDDMFormFieldTypeServicesTracker();

	protected abstract
		DDMFormValuesToFieldsConverter getDDMFormValuesToFieldsConverter();

	protected Map<String, DDMFormField> getDistinctFields(long recordSetId)
		throws Exception {

		List<DDMStructureVersion> ddmStructureVersions = getStructureVersions(
			recordSetId);

		Map<String, DDMFormField> ddmFormFields = new HashMap<>();

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			DDMForm ddmForm = ddmStructureVersion.getDDMForm();

			ddmFormFields.putAll(ddmForm.getDDMFormFieldsMap(true));
		}

		return ddmFormFields;
	}

	protected List<DDMFormFieldRenderedValue> getRenderedValues(
			int scope, List<DDMFormField> ddmFormFields,
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws Exception {

		if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
			return getDDMFormFieldRenderedValues(ddmFormValues, ddmFormFields);
		}
		else {
			Fields fields = getDDMFormValuesToFieldsConverter().convert(
				ddmStructure, ddmFormValues);

			return getDDMFormFieldRenderedValues(fields, ddmFormFields);
		}
	}

	protected String getStatusMessage(int status) {
		String statusLabel = WorkflowConstants.getStatusLabel(status);

		return LanguageUtil.get(_locale, statusLabel);
	}

	protected List<DDMStructureVersion> getStructureVersions(long recordSetId)
		throws Exception {

		DDLRecordSetVersionService recordSetVersionService =
			getDDLRecordSetVersionService();

		List<DDLRecordSetVersion> recordSetVersions =
			recordSetVersionService.getRecordSetVersions(
				recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<DDMStructureVersion> ddmStructureVersions = new ArrayList<>();

		for (DDLRecordSetVersion recordSetVersion : recordSetVersions) {
			ddmStructureVersions.add(recordSetVersion.getDDMStructureVersion());
		}

		return ddmStructureVersions;
	}

	protected static class DDMFormFieldRenderedValue {

		protected DDMFormFieldRenderedValue(
			LocalizedValue label, String value) {

			_label = label;
			_value = value;
		}

		protected LocalizedValue getLabel() {
			return _label;
		}

		protected String getValue() {
			return _value;
		}

		private final LocalizedValue _label;
		private final String _value;

	}

	private Locale _locale;

}