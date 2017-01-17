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

package com.liferay.jenkins.results.parser;

import java.util.Hashtable;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class SemanticVersioningFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_SEMVER_END_STRING) ||
			!consoleOutput.contains(_SEMVER_START_STRING)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please fix <strong>semantic versioning</strong> on ");
		sb.append("<strong><a href=\"https://github.com/");
		sb.append(properties.get("github.origin.name"));
		sb.append("/");
		sb.append(properties.get("repository"));
		sb.append("/tree/");
		sb.append(properties.get("github.sender.branch.name"));
		sb.append("\">");
		sb.append(properties.get("github.origin.name"));
		sb.append("/");
		sb.append(properties.get("github.sender.branch.name"));
		sb.append("</a></strong>.</p>");

		int end = consoleOutput.indexOf(_SEMVER_END_STRING);

		end = consoleOutput.indexOf("\n", end);

		int start = consoleOutput.lastIndexOf(_SEMVER_START_STRING, end);

		start = consoleOutput.lastIndexOf("\n", start);

		sb.append(getConsoleOutputSnippet(consoleOutput, true, start, end));

		return sb.toString();
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_SEMVER_END_STRING) ||
			!consoleText.contains(_SEMVER_START_STRING)) {

			return null;
		}

		Element messageElement = new DefaultElement("div");

		Dom4JUtil.addToElement(
			Dom4JUtil.getNewElement("p", messageElement), "Please fix ",
			Dom4JUtil.wrapWithNewElement("semantic versioning", "strong"),
			" on ",
			Dom4JUtil.wrapWithNewElement(
				getBaseBranchAnchorElement(build.getTopLevelBuild()),
				"strong"));

		int end = consoleText.indexOf(_SEMVER_END_STRING);

		end = consoleText.indexOf("\n", end);

		int start = consoleText.lastIndexOf(_SEMVER_START_STRING, end);

		start = consoleText.lastIndexOf("\n", start);

		messageElement.add(
			getConsoleOutputSnippetElement(consoleText, true, start, end));

		return messageElement;
	}

	private static final String _SEMVER_END_STRING = ":baseline FAILED";

	private static final String _SEMVER_START_STRING = "PACKAGE_NAME";

}