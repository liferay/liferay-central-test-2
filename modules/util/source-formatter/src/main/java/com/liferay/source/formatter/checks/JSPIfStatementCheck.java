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

import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPIfStatementCheck extends IfStatementCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			if (JSPSourceUtil.isJavaSource(content, matcher.start())) {
				checkIfClauseParentheses(
					matcher.group(), fileName,
					getLineCount(content, matcher.start(1)));
			}
		}

		matcher = _testTagPattern.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start())) {
				String ifClause = "if (" + matcher.group(2) + ") {";

				checkIfClauseParentheses(
					ifClause, fileName,
					getLineCount(content, matcher.start(2)));
			}
		}

		return content;
	}

	private final Pattern _ifStatementPattern = Pattern.compile(
		"[\t\n]((else )?if|while) .*\\) \\{\n");
	private final Pattern _testTagPattern = Pattern.compile(
		"[\t\n]<c:(if|when) test=['\"]<%= (.+) %>['\"]>\n");

}