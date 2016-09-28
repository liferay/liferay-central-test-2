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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2;

import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class UpgradeCheckboxFieldToCheckboxMultipleField
	extends UpgradeProcess {

	public UpgradeCheckboxFieldToCheckboxMultipleField(
		DDMFormJSONDeserializer ddmFormJSONDeserializer,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer,
		JSONFactory jsonFactory) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select DDMStructure.definition, DDLRecordSet.recordSetId, ");
		sb.append("DDMStructure.structureId from DDLRecordSet inner join ");
		sb.append("DDMStructure on DDLRecordSet.DDMStructureId = ");
		sb.append("DDMStructure.structureId where DDLRecordSet.scope = ? and ");
		sb.append("DDMStructure.definition like ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			ps1.setInt(1, _SCOPE_FORMS);
			ps1.setString(2, "%checkbox%");

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long recordSetId = rs.getLong(2);
					long structureId = rs.getLong(3);

					String newDefinition = upgradeRecordSetStructureDefinition(
						definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureId);

					ps2.addBatch();

					DDMForm ddmForm = _ddmFormJSONDeserializer.deserialize(
						definition);

					updateRecords(ddmForm, recordSetId);
				}

				ps2.executeBatch();
			}
		}
	}

	protected JSONArray getOptionsJSONArray(
		JSONObject checkboxFieldJSONObject) {

		JSONArray optionsJSONArray = _jsonFactory.createJSONArray();

		JSONObject optionJSONObject = _jsonFactory.createJSONObject();

		optionJSONObject.put(
			"label", checkboxFieldJSONObject.getJSONObject("label"));
		optionJSONObject.put(
			"value", checkboxFieldJSONObject.getString("name"));

		optionsJSONArray.put(optionJSONObject);

		return optionsJSONArray;
	}

	protected JSONObject getPredefinedValue(
		JSONObject checkboxFieldJSONObject) {

		JSONObject oldPredefinedValueJSONObject =
			checkboxFieldJSONObject.getJSONObject("predefinedValue");

		JSONObject newPredefinedValueJSONObject =
			_jsonFactory.createJSONObject();

		Iterator<String> languageKeys = oldPredefinedValueJSONObject.keys();

		while (languageKeys.hasNext()) {
			String languageKey = languageKeys.next();

			String predefinedValue = oldPredefinedValueJSONObject.getString(
				languageKey);

			if (Objects.equals(predefinedValue, "true")) {
				predefinedValue = checkboxFieldJSONObject.getString("name");
			}
			else {
				predefinedValue = StringPool.BLANK;
			}

			newPredefinedValueJSONObject.put(languageKey, predefinedValue);
		}

		return newPredefinedValueJSONObject;
	}

	protected void transformCheckboxDDMFormField(
		JSONObject checkboxFieldJSONObject) {

		checkboxFieldJSONObject.put("dataType", "string");
		checkboxFieldJSONObject.put(
			"options", getOptionsJSONArray(checkboxFieldJSONObject));
		checkboxFieldJSONObject.put(
			"predefinedValue", getPredefinedValue(checkboxFieldJSONObject));
		checkboxFieldJSONObject.put("type", "checkbox_multiple");
	}

	protected void transformCheckboxDDMFormFieldValues(
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new CheckboxDDMFormFieldValueTransformer(_jsonFactory));

		ddmFormValuesTransformer.transform();
	}

	protected void updateRecords(DDMForm ddmForm, long recordSetId)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("select DDLRecordVersion.ddmStorageId, DDMContent.data_ ");
		sb.append("from DDLRecordVersion inner join DDLRecordSet on ");
		sb.append("DDLRecordVersion.recordSetId = DDLRecordSet.recordSetId ");
		sb.append("inner join DDMContent on DDLRecordVersion.DDMStorageId = ");
		sb.append("DDMContent.contentId where DDLRecordSet.recordSetId = ? ");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_= ? where contentId = ? ")) {

			ps1.setLong(1, recordSetId);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long contentId = rs.getLong("ddmStorageId");
					String data_ = rs.getString("data_");

					DDMFormValues ddmFormValues =
						_ddmFormValuesJSONDeserializer.deserialize(
							ddmForm, data_);

					transformCheckboxDDMFormFieldValues(ddmFormValues);

					ps2.setString(
						1,
						_ddmFormValuesJSONSerializer.serialize(ddmFormValues));

					ps2.setLong(2, contentId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected String upgradeRecordSetStructureDefinition(String definition)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		upgradeRecordSetStructureFields(fieldsJSONArray);

		return definitionJSONObject.toString();
	}

	protected void upgradeRecordSetStructureFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String type = fieldJSONObject.getString("type");

			if (type.equals("checkbox")) {
				transformCheckboxDDMFormField(fieldJSONObject);
			}

			JSONArray nestedFieldsJSONArray = fieldJSONObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				upgradeRecordSetStructureFields(nestedFieldsJSONArray);
			}
		}
	}

	private static final int _SCOPE_FORMS = 2;

	private final DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;
	private final JSONFactory _jsonFactory;

	private static class CheckboxDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public CheckboxDDMFormFieldValueTransformer(JSONFactory jsonFactory) {
			_jsonFactory = jsonFactory;
		}

		@Override
		public String getFieldType() {
			return "checkbox";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONArray valueJSONArray = _jsonFactory.createJSONArray();

				if (Objects.equals(valueString, "true")) {
					DDMFormField ddmFormField =
						ddmFormFieldValue.getDDMFormField();

					valueJSONArray.put(ddmFormField.getName());
				}

				value.addString(locale, valueJSONArray.toString());
			}
		}

		private JSONFactory _jsonFactory;

	}

}