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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class UpgradeDataProviderInstance extends UpgradeProcess {

	public UpgradeDataProviderInstance(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	protected void addDefaultOutputParameter(DDMFormValues definitionDDMForm) {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			definitionDDMForm.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValueList =
			ddmFormFieldValuesMap.get("key");

		DDMFormFieldValue keyDDMFormFieldValue = ddmFormFieldValueList.get(0);

		ddmFormFieldValueList = ddmFormFieldValuesMap.get("value");

		DDMFormFieldValue valueDDMFormFieldValue = ddmFormFieldValueList.get(0);

		String outputPath = createOutputPathValue(
			definitionDDMForm.getDefaultLocale(),
			keyDDMFormFieldValue.getValue(), valueDDMFormFieldValue.getValue());

		definitionDDMForm.addDDMFormFieldValue(
			createDefaultOutputParameter(definitionDDMForm, outputPath));
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		DDMFormValues ddmFormValues, String name, String value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(name);

		if (Validator.isNotNull(value)) {
			ddmFormFieldValue.setValue(new UnlocalizedValue(value));
		}

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValue createDefaultOutputParameter(
		DDMFormValues ddmFormValues, String outputPath) {

		DDMFormFieldValue outputParameters = createDDMFormFieldValue(
			ddmFormValues, "outputParameters", null);

		outputParameters.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterName",
				_DEFAULT_OUTPUT_PARAMETER_NAME));

		outputParameters.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterPath", outputPath));

		outputParameters.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				ddmFormValues, "outputParameterType", "text"));

		return outputParameters;
	}

	protected String createOutputPathValue(
		Locale locale, Value key, Value value) {

		StringBundler sb = new StringBundler(3);

		sb.append(key.getString(locale));
		sb.append(CharPool.SEMICOLON);
		sb.append(value.getString(locale));

		return sb.toString();
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select dataProviderInstanceId, definition from " +
					"DDMDataProviderInstance");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMDataProviderInstance set definition = ? where " +
						"dataProviderInstanceId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long dataProviderInstanceId = rs.getLong(1);
				String dataProviderInstanceDefinition = rs.getString(2);

				String newDefinition = upgradeDataProviderInstanceDefinition(
					dataProviderInstanceDefinition);

				ps2.setString(1, newDefinition);

				ps2.setLong(2, dataProviderInstanceId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected String upgradeDataProviderInstanceDefinition(
			String dataProviderInstanceDefinition)
		throws Exception {

		DDMFormValues definitionDDMForm =
			_ddmFormValuesJSONDeserializer.deserialize(
				null, dataProviderInstanceDefinition);

		addDefaultOutputParameter(definitionDDMForm);

		return _ddmFormValuesJSONSerializer.serialize(definitionDDMForm);
	}

	private static final String _DEFAULT_OUTPUT_PARAMETER_NAME =
		"Default-Output";

	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

}