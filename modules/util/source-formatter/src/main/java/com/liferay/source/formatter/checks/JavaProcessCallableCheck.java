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

package com.liferay.source.formatter.checks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaProcessCallableCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		// LPS-33070

		Matcher matcher = _processCallablePattern.matcher(content);

		if (!matcher.find() ||
			content.contains("private static final long serialVersionUID")) {

			return content;
		}

		addMessage(
			fileName,
			"Assign ProcessCallable implementation a serialVersionUID");

		return content;
	}

	private final Pattern _processCallablePattern = Pattern.compile(
		"implements ProcessCallable\\b");

}