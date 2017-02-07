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

package com.liferay.dynamic.data.mapping.internal.test.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;

import java.util.Locale;

/**
 * @author Lino Alves
 */
public class DDMFormFieldValueBuilder {

	public DDMFormFieldValue build() {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		localizedValue.addString(_locale, _value);

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			_name, localizedValue);
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(String value) {
		_value = value;
	}

	private Locale _defaultLocale;
	private Locale _locale;
	private String _name;
	private String _value;

}