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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class StringFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		String fieldType = getFieldType(field);

		List<String> values = new ArrayList<>();

		for (Serializable value : field.getValues(locale)) {
			String valueString = String.valueOf(value);

			if (Validator.isNull(valueString)) {
				continue;
			}

			if (fieldType.equals(DDMImpl.TYPE_RADIO) ||
				fieldType.equals(DDMImpl.TYPE_SELECT)) {

				valueString = handleJSON(field, valueString, locale);
			}

			values.add(valueString);
		}

		return StringUtil.merge(values, StringPool.COMMA_AND_SPACE);
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex)
		throws Exception {

		Serializable value = field.getValue(locale, valueIndex);

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		String fieldType = getFieldType(field);

		if (fieldType.equals(DDMImpl.TYPE_RADIO) ||
			fieldType.equals(DDMImpl.TYPE_SELECT)) {

			return handleJSON(field, String.valueOf(value), locale);
		}

		return String.valueOf(value);
	}

	protected LocalizedValue getFieldOptionLabel(
			Field field, String optionValue)
		throws Exception {

		DDMStructure ddmStructure = field.getDDMStructure();

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			field.getName());

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		return ddmFormFieldOptions.getOptionLabels(optionValue);
	}

	protected String getFieldType(Field field) throws Exception {
		DDMStructure ddmStructure = field.getDDMStructure();

		return ddmStructure.getFieldType(field.getName());
	}

	protected String handleJSON(Field field, String json, Locale locale)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		StringBundler sb = new StringBundler(jsonArray.length() * 2);

		for (int i = 0; i < jsonArray.length(); i++) {
			LocalizedValue label = getFieldOptionLabel(
				field, jsonArray.getString(i));

			if (label == null) {
				continue;
			}

			sb.append(label.getString(locale));

			if ((i + 1) < jsonArray.length()) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		return sb.toString();
	}

}