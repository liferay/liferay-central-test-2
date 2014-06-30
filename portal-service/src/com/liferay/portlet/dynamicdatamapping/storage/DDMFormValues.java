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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormValues {

	public void addDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {
		_ddmFormFieldValues.add(ddmFormFieldValue);
	}

	public List<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public List<DDMFormFieldValue> getDDMFormFieldValues() {
		return _ddmFormFieldValues;
	}

	public Map<String, DDMFormFieldValue> getDDMFormFieldValuesMap() {
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap =
			new HashMap<String, DDMFormFieldValue>();

		for (DDMFormFieldValue ddmFormFieldValue : _ddmFormFieldValues) {
			ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValue);
		}

		return ddmFormFieldValuesMap;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public void setAvailableLocales(List<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDDMFormFieldValues(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		_ddmFormFieldValues = ddmFormFieldValues;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private List<Locale> _availableLocales;
	private List<DDMFormFieldValue> _ddmFormFieldValues =
		new ArrayList<DDMFormFieldValue>();
	private Locale _defaultLocale;

}