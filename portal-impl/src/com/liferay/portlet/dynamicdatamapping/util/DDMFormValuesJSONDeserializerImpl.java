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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONDeserializerImpl
	implements DDMFormValuesJSONDeserializer {

	@Override
	public DDMFormValues deserialize(String serializedDDMFormValues)
		throws PortalException {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				serializedDDMFormValues);

			DDMFormValues ddmFormValues = new DDMFormValues();

			setDDMFormValuesAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmFormValues);
			setDDMFormValuesDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmFormValues);
			setDDMFormFieldValues(
				jsonObject.getJSONArray("fieldValues"), ddmFormValues);
			setDDMFormLocalizedValuesDefaultLocale(ddmFormValues);

			return ddmFormValues;
		}
		catch (JSONException jsone) {
			throw new PortalException(jsone);
		}
	}

	protected List<Locale> getAvailableLocales(JSONArray jsonArray) {
		List<Locale> availableLocales = new ArrayList<Locale>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected DDMFormFieldValue getDDMFormFieldValue(JSONObject jsonObject) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(jsonObject.getString("name"));

		setDDMFormFieldValueValues(
			jsonObject.getJSONArray("values"), ddmFormFieldValue);

		setNestedDDMFormFieldValues(
			jsonObject.getJSONArray("nestedFieldValues"), ddmFormFieldValue);

		return ddmFormFieldValue;
	}

	protected LocalizedValue getDDMFormFieldValueLocalizedValue(
		JSONObject jsonObject) {

		LocalizedValue localizedValue = new LocalizedValue();

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			localizedValue.addValue(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected List<DDMFormFieldValue> getDDMFormFieldValues(
		JSONArray jsonArray) {

		List<DDMFormFieldValue> ddmFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
				jsonArray.getJSONObject(i));

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		return ddmFormFieldValues;
	}

	protected List<Value> getValues(JSONArray jsonArray) {
		List<Value> values = new ArrayList<Value>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Value value = null;

			if (isLocalized(jsonObject)) {
				value = getDDMFormFieldValueLocalizedValue(jsonObject);
			}
			else {
				value = new UnlocalizedValue(jsonArray.getString(i));
			}

			values.add(value);
		}

		return values;
	}

	protected boolean isLocalized(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			if (!LanguageUtil.isAvailableLanguageCode(key)) {
				return false;
			}
		}

		return true;
	}

	protected void setDDMFormFieldValueLocalizedValueDefaultLocale(
		DDMFormFieldValue ddmFormFieldValue, Locale defaultLocale) {

		List<Value> values = ddmFormFieldValue.getValues();

		for (Value value : values) {
			if (!value.isLocalized()) {
				break;
			}

			value.setDefaultLocale(defaultLocale);
		}

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			setDDMFormFieldValueLocalizedValueDefaultLocale(
				nestedDDMFormFieldValue, defaultLocale);
		}
	}

	protected void setDDMFormFieldValues(
		JSONArray jsonArray, DDMFormValues ddmFormValues) {

		List<DDMFormFieldValue> ddmFormFieldValues = getDDMFormFieldValues(
			jsonArray);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
	}

	protected void setDDMFormFieldValueValues(
		JSONArray jsonArray, DDMFormFieldValue ddmFormFieldValue) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<Value> values = getValues(jsonArray);

		ddmFormFieldValue.setValues(values);
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(
		DDMFormValues ddmFormValues) {

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			setDDMFormFieldValueLocalizedValueDefaultLocale(
				ddmFormFieldValue, ddmFormValues.getDefaultLocale());
		}
	}

	protected void setDDMFormValuesAvailableLocales(
		JSONArray jsonArray, DDMFormValues ddmFormValues) {

		List<Locale> availableLocales = getAvailableLocales(jsonArray);

		ddmFormValues.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormValuesDefaultLocale(
		String defaultLanguageId, DDMFormValues ddmFormValues) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmFormValues.setDefaultLocale(defaultLocale);
	}

	protected void setNestedDDMFormFieldValues(
		JSONArray jsonArray, DDMFormFieldValue ddmFormFieldValue) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			getDDMFormFieldValues(jsonArray);

		ddmFormFieldValue.setNestedDDMFormFields(nestedDDMFormFieldValues);
	}

}