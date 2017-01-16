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
 */
public class RebaseFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_REBASE_END_STRING) ||
			!consoleOutput.contains(_REBASE_START_STRING) ||
			!consoleOutput.contains("CONFLICT")) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please fix <strong>rebase errors</strong> on <strong>");
		sb.append("<a href=\"https://github.com/");
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

		int end = consoleOutput.indexOf(_REBASE_END_STRING);

		end = consoleOutput.lastIndexOf("\n", end);

		int start = consoleOutput.lastIndexOf(_REBASE_START_STRING, end);

		start = consoleOutput.lastIndexOf("\n", start);

		sb.append(getConsoleOutputSnippet(consoleOutput, true, start, end));

		return sb.toString();
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_REBASE_END_STRING) ||
			!consoleText.contains(_REBASE_START_STRING) ||
			!consoleText.contains("CONFLICT")) {

			return null;
		}

		Element messageElement = new DefaultElement("div");

		Dom4JUtil.addToElement(
			Dom4JUtil.getNewElement("p", messageElement), "Please fix ",
			Dom4JUtil.wrapWithNewElement("rebase errors", "strong"), " on ",
			Dom4JUtil.wrapWithNewElement(
				getBaseBranchAnchorElement(build.getTopLevelBuild()),
				"strong"));

		int end = consoleText.indexOf(_REBASE_END_STRING);

		end = consoleText.lastIndexOf("\n", end);

		int start = consoleText.lastIndexOf(_REBASE_START_STRING, end);

		start = consoleText.lastIndexOf("\n", start);

		messageElement.add(
			getConsoleOutputSnippetElement(consoleText, true, start, end));

		return messageElement;
	}

	private static final String _REBASE_END_STRING =
		"The copy of the patch that failed is found in";

	private static final String _REBASE_START_STRING =
		"First, rewinding head to replay your work on top of it...";

}