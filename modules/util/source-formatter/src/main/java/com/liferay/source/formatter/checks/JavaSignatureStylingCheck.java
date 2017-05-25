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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSignatureStylingCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = javaTerm.getContent();

		String indent = SourceUtil.getIndent(javaTermContent);

		Pattern pattern = Pattern.compile(
			"(" + indent + javaTerm.getAccessModifier() +
				" .*?[;{]\n)((\n*)([^\n]+)\n)?",
			Pattern.DOTALL);

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return javaTermContent;
		}

		String signature = matcher.group(1);

		String[] signatureLines = StringUtil.splitLines(signature);

		String newLineChars = matcher.group(3);
		String nextLine = StringUtil.trim(matcher.group(4));

		if (signatureLines.length == 1) {
			return _formatSingleLineSignature(
				javaTermContent, signature, newLineChars, nextLine);
		}

		return _formatMultiLinesSignature(
			javaTermContent, signature, signatureLines, indent, newLineChars,
			nextLine);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _fixLeadingTabs(
		String content, String line, int expectedTabCount) {

		int leadingTabCount = getLeadingTabCount(line);

		String newLine = line;

		while (leadingTabCount != expectedTabCount) {
			if (leadingTabCount > expectedTabCount) {
				newLine = StringUtil.replaceFirst(
					newLine, CharPool.TAB, StringPool.BLANK);

				leadingTabCount--;
			}
			else {
				newLine = StringPool.TAB + newLine;

				leadingTabCount++;
			}
		}

		return StringUtil.replace(content, line, newLine);
	}

	private String _formatMultiLinesSignature(
		String javaTermContent, String signature, String[] signatureLines,
		String indent, String newLineChars, String nextLine) {

		if (signature.endsWith("{\n") && (newLineChars != null) &&
			(newLineChars.length() == 0) &&
			!nextLine.equals(StringPool.CLOSE_CURLY_BRACE)) {

			return StringUtil.replace(
				javaTermContent, signature, signature + "\n");
		}

		boolean throwsException = signature.contains(indent + "throws ");

		String newSignature = signature;

		int expectedTabCount = -1;

		for (int i = 0; i < signatureLines.length; i++) {
			String line = signatureLines[i];

			if (line.contains(indent + "throws ")) {
				newSignature = _fixLeadingTabs(
					newSignature, line, indent.length() + 1);

				break;
			}

			if (expectedTabCount == -1) {
				if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
					expectedTabCount = Math.max(
						getLeadingTabCount(line), indent.length()) + 1;

					if (throwsException &&
						(expectedTabCount == (indent.length() + 1))) {

						expectedTabCount += 1;
					}
				}
			}
			else {
				String previousLine = signatureLines[i - 1];

				if ((previousLine.endsWith(StringPool.COMMA) &&
					 (getLevel(previousLine, "<", ">") <= 0)) ||
					previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {

					newSignature = _fixLeadingTabs(
						newSignature, line, expectedTabCount);
				}
				else {
					newSignature = _fixLeadingTabs(
						newSignature, line,
						getLeadingTabCount(previousLine) + 1);
				}
			}
		}

		return StringUtil.replace(javaTermContent, signature, newSignature);
	}

	private String _formatSingleLineSignature(
		String javaTermContent, String signature, String newLineChars,
		String nextLine) {

		if ((newLineChars != null) && (newLineChars.length() > 0) &&
			!nextLine.matches("/[/*].*")) {

			return StringUtil.replace(
				javaTermContent, signature + "\n", signature);
		}

		return javaTermContent;
	}

}