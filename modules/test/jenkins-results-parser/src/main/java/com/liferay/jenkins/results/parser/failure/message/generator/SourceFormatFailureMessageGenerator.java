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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 * @author Yi-Chen Tsai
 */
public class SourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_TOKEN_SOURCE_FORMAT)) {
			return null;
		}

		int start = consoleOutput.lastIndexOf(_TOKEN_FORMAT_SOURCE);

		start = consoleOutput.indexOf(_TOKEN_UTIL_SYSTEM_EXT_PROPERTIES, start);

		start = consoleOutput.indexOf("\n", start);

		int end = consoleOutput.indexOf(_TOKEN_MERGE_TEST_RESULTS, start);

		end = consoleOutput.lastIndexOf(_TOKEN_SOURCE_FORMAT, end);

		end = consoleOutput.indexOf("\n", end);

		return getConsoleOutputSnippet(consoleOutput, true, start, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_SOURCE_FORMAT)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_FORMAT_SOURCE);

		start = consoleText.indexOf(_TOKEN_UTIL_SYSTEM_EXT_PROPERTIES, start);

		start = consoleText.indexOf("\n", start);

		int end = consoleText.indexOf(_TOKEN_MERGE_TEST_RESULTS, start);

		end = consoleText.lastIndexOf(_TOKEN_SOURCE_FORMAT, end);

		end = consoleText.indexOf("\n", end);

		return getConsoleOutputSnippetElement(consoleText, true, start, end);
	}

	private static final String _TOKEN_FORMAT_SOURCE = "format-source:";

	private static final String _TOKEN_MERGE_TEST_RESULTS =
		"merge-test-results:";

	private static final String _TOKEN_SOURCE_FORMAT =
		"at com.liferay.source.formatter";

	private static final String _TOKEN_UTIL_SYSTEM_EXT_PROPERTIES =
		"util-java/test-classes/unit/system-ext.properties";

}