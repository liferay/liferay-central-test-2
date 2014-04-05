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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 */
public class JavaImportsFormatter extends ImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _javaImportPattern.matcher(line);

		if (!matcher.find()) {
			return null;
		}

		boolean isStatic = false;

		if (Validator.isNotNull(matcher.group(1))) {
			isStatic = true;
		}

		String importString = matcher.group(2);

		return new ImportPackage(importString, isStatic, line);
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
		"import( static)? ([^;]+);");

}