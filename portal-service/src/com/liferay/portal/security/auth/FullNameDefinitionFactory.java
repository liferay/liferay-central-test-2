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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class FullNameDefinitionFactory {

	public static FullNameDefinition getInstance(Locale locale) {
		return _instance._getInstance(locale);
	}

	private FullNameDefinitionFactory() {
	}

	private FullNameDefinition _getInstance(Locale locale) {
		FullNameDefinition fullNameDefinition = _fullNameDefinitions.get(
			locale);

		if (fullNameDefinition != null) {
			return fullNameDefinition;
		}

		fullNameDefinition = new FullNameDefinition();

		String[] fieldNames = StringUtil.split(
			LanguageUtil.get(locale, "lang.user.name.field.names"));

		for (String userNameField : fieldNames) {
			FullNameField fullNameField = new FullNameField();

			fullNameField.setName(userNameField);

			String[] values = StringUtil.split(
				LanguageUtil.get(
					locale, "lang.user.name." + userNameField + ".values",
					StringPool.BLANK));

			fullNameField.setValues(values);

			String requiredFieldNames = LanguageUtil.get(
				locale, "lang.user.name.required.field.names");

			boolean required = StringUtil.contains(
				requiredFieldNames, userNameField);

			fullNameField.setRequired(required);

			fullNameDefinition.addFullNameField(fullNameField);
		}

		_fullNameDefinitions.put(locale, fullNameDefinition);

		return fullNameDefinition;
	}

	private static final FullNameDefinitionFactory _instance =
		new FullNameDefinitionFactory();

	private final Map<Locale, FullNameDefinition> _fullNameDefinitions =
		new ConcurrentHashMap<>();

}