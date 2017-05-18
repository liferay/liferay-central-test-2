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

package com.liferay.dynamic.data.mapping.type.grid.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=grid",
	service = DDMFormFieldValueValidator.class
)
public class GridDDMFormFieldValueValidator
	implements DDMFormFieldValueValidator {

	@Override
	public void validate(DDMFormField ddmFormField, Value value)
		throws DDMFormFieldValueValidationException {

		DDMFormFieldOptions rows =
			(DDMFormFieldOptions)ddmFormField.getProperty("rows");
		DDMFormFieldOptions columns =
			(DDMFormFieldOptions)ddmFormField.getProperty("columns");

		if ((rows == null) || (columns == null)) {
			throw new DDMFormFieldValueValidationException(
				String.format(
					"Rows and columns must be set for grid field \"%s\"",
					ddmFormField.getName()));
		}

		Set<String> rowValues = rows.getOptionsValues();

		Set<String> columnValues = columns.getOptionsValues();

		if (rowValues.isEmpty() || columnValues.isEmpty()) {
			throw new DDMFormFieldValueValidationException(
				"Rows and columns must contain at least one alternative each");
		}

		DDMForm ddmForm = ddmFormField.getDDMForm();

		Locale defaultLocale = ddmForm.getDefaultLocale();

		validateSelectedValue(
			ddmFormField, rowValues, columnValues,
			value.getString(defaultLocale));
	}

	protected JSONObject createJSONObject(String fieldName, String json) {
		try {
			return jsonFactory.createJSONObject(json);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			throw new IllegalStateException(
				String.format(
					"Invalid data stored for grid field \"%s\"", fieldName));
		}
	}

	protected void validateSelectedValue(
			DDMFormField ddmFormField, Set<String> rowValues,
			Set<String> columnValues, String selectedValues)
		throws DDMFormFieldValueValidationException {

		String ddmFormFieldName = ddmFormField.getName();

		JSONObject jsonObject = createJSONObject(
			ddmFormFieldName, selectedValues);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			String value = jsonObject.getString(key);

			if (!rowValues.contains(key) || !columnValues.contains(value)) {
				throw new DDMFormFieldValueValidationException(
					String.format(
						"The selected option \"%s\" is not a valid choice",
						value));
			}
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		GridDDMFormFieldValueValidator.class);

}