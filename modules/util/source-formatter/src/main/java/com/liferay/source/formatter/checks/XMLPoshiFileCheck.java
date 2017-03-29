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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPoshiFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith(".action") || fileName.endsWith(".function") ||
			fileName.endsWith(".macro") || fileName.endsWith(".testcase")) {

			content = _formatPoshiXML(fileName, content);
		}

		return content;
	}

	private void _checkPoshiCharactersAfterDefinition(
		String fileName, String content) {

		if (content.contains("/definition>") &&
			!content.endsWith("/definition>")) {

			addMessage(fileName, "Characters found after definition element");
		}
	}

	private void _checkPoshiCharactersBeforeDefinition(
		String fileName, String content) {

		if (!content.startsWith("<definition")) {
			addMessage(fileName, "Characters found before definition element");
		}
	}

	private String _fixPoshiXMLElementWithNoChild(String content) {
		Matcher matcher = _poshiElementWithNoChildPattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replace(content, matcher.group(), "\" />");
		}

		return content;
	}

	private String _fixPoshiXMLEndLines(String content) {
		Matcher matcher = _poshiEndLinesPattern.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(), ">\n\n" + matcher.group(1));

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	private String _fixPoshiXMLEndLinesAfterClosingElement(String content) {
		Matcher matcher = _poshiEndLinesAfterClosingElementPattern.matcher(
			content);

		while (matcher.find()) {
			String statement = matcher.group();

			String closingElementName = matcher.group(1);

			if (StringUtil.equalsIgnoreCase("</and>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</elseif>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</not>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</or>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</then>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
			else if (!StringUtil.equalsIgnoreCase(
						"</var>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
		}

		return content;
	}

	private String _fixPoshiXMLEndLinesBeforeClosingElement(String content) {
		Matcher matcher = _poshiEndLinesBeforeClosingElementPattern.matcher(
			content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(1), "\n");

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	private String _fixPoshiXMLNumberOfTabs(String content) {
		Matcher matcher = _poshiTabsPattern.matcher(content);

		int tabCount = 0;

		boolean ignoredCdataBlock = false;
		boolean ignoredCommentBlock = false;

		while (matcher.find()) {
			String statement = matcher.group();

			Matcher quoteWithSlashMatcher = _poshiQuoteWithSlashPattern.matcher(
				statement);

			String fixedQuoteStatement = statement;

			if (quoteWithSlashMatcher.find()) {
				fixedQuoteStatement = StringUtil.replace(
					statement, quoteWithSlashMatcher.group(), "\"\"");
			}

			Matcher closingTagMatcher = _poshiClosingTagPattern.matcher(
				fixedQuoteStatement);
			Matcher openingTagMatcher = _poshiOpeningTagPattern.matcher(
				fixedQuoteStatement);
			Matcher wholeTagMatcher = _poshiWholeTagPattern.matcher(
				fixedQuoteStatement);

			if (closingTagMatcher.find() && !openingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount--;
			}

			if (statement.contains("]]>")) {
				ignoredCdataBlock = false;
			}
			else if (statement.contains("<![CDATA[")) {
				ignoredCdataBlock = true;
			}

			if (statement.contains("-->")) {
				ignoredCommentBlock = false;
			}
			else if (statement.contains("<!--")) {
				ignoredCommentBlock = true;
			}

			if (!ignoredCommentBlock && !ignoredCdataBlock) {
				StringBundler sb = new StringBundler(tabCount + 1);

				for (int i = 0; i < tabCount; i++) {
					sb.append(StringPool.TAB);
				}

				sb.append(StringPool.LESS_THAN);

				String replacement = sb.toString();

				if (!replacement.equals(matcher.group(1))) {
					String newStatement = StringUtil.replace(
						statement, matcher.group(1), replacement);

					return StringUtil.replaceFirst(
						content, statement, newStatement, matcher.start());
				}
			}

			if (openingTagMatcher.find() && !closingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount++;
			}
		}

		return content;
	}

	private String _formatPoshiXML(String fileName, String content)
		throws Exception {

		_checkPoshiCharactersAfterDefinition(fileName, content);
		_checkPoshiCharactersBeforeDefinition(fileName, content);

		try {
			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				checkElementOrder(
					fileName, commandElement, "property", null,
					new ElementComparator());
			}
		}
		catch (Exception e) {
		}

		content = _sortPoshiCommands(content);
		content = _sortPoshiVariables(content);

		content = _fixPoshiXMLElementWithNoChild(content);

		content = _fixPoshiXMLEndLinesAfterClosingElement(content);

		content = _fixPoshiXMLEndLinesBeforeClosingElement(content);

		content = _fixPoshiXMLEndLines(content);

		return _fixPoshiXMLNumberOfTabs(content);
	}

	private String _sortPoshiCommands(String content) {
		Matcher matcher = _poshiCommandsPattern.matcher(content);

		Map<String, String> commandBlocksMap = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		String previousName = StringPool.BLANK;

		boolean hasUnsortedCommands = false;

		while (matcher.find()) {
			String commandBlock = matcher.group();
			String commandName = matcher.group(1);

			commandBlocksMap.put(commandName, commandBlock);

			if (!hasUnsortedCommands &&
				(commandName.compareToIgnoreCase(previousName) < 0)) {

				hasUnsortedCommands = true;
			}

			previousName = commandName;
		}

		if (!hasUnsortedCommands) {
			return content;
		}

		StringBundler sb = new StringBundler();

		matcher = _poshiSetUpPattern.matcher(content);

		if (matcher.find()) {
			String setUpBlock = matcher.group();

			content = content.replace(setUpBlock, "");

			sb.append(setUpBlock);
		}

		matcher = _poshiTearDownPattern.matcher(content);

		if (matcher.find()) {
			String tearDownBlock = matcher.group();

			content = content.replace(tearDownBlock, "");

			sb.append(tearDownBlock);
		}

		for (Map.Entry<String, String> entry : commandBlocksMap.entrySet()) {
			sb.append("\n\t");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		int x = content.indexOf("<command");
		int y = content.lastIndexOf("</command>");

		String commandBlock = content.substring(x, y);

		commandBlock = "\n\t" + commandBlock + "</command>\n";

		String newCommandBlock = sb.toString();

		return StringUtil.replaceFirst(content, commandBlock, newCommandBlock);
	}

	private String _sortPoshiVariables(String content) {
		Matcher matcher = _poshiVariablesBlockPattern.matcher(content);

		while (matcher.find()) {
			String previousName = StringPool.BLANK;
			String tabs = StringPool.BLANK;

			Map<String, String> variableLinesMap = new TreeMap<>(
				String.CASE_INSENSITIVE_ORDER);

			String variableBlock = matcher.group(1);

			variableBlock = variableBlock.trim();

			Matcher variableLineMatcher = _poshiVariableLinePattern.matcher(
				variableBlock);

			boolean hasUnsortedVariables = false;

			while (variableLineMatcher.find()) {
				if (tabs.equals(StringPool.BLANK)) {
					tabs = variableLineMatcher.group(1);
				}

				String variableLine = variableLineMatcher.group(2);
				String variableName = variableLineMatcher.group(3);

				variableLinesMap.put(variableName, variableLine);

				if (!hasUnsortedVariables &&
					(variableName.compareToIgnoreCase(previousName) < 0)) {

					hasUnsortedVariables = true;
				}

				previousName = variableName;
			}

			if (!hasUnsortedVariables) {
				continue;
			}

			StringBundler sb = new StringBundler();

			for (Map.Entry<String, String> entry :
					variableLinesMap.entrySet()) {

				sb.append(tabs);
				sb.append(entry.getValue());
				sb.append("\n");
			}

			String newVariableBlock = sb.toString();

			newVariableBlock = newVariableBlock.trim();

			content = StringUtil.replaceFirst(
				content, variableBlock, newVariableBlock);
		}

		return content;
	}

	private final Pattern _poshiClosingTagPattern = Pattern.compile(
		"</[^>/]*>");
	private final Pattern _poshiCommandsPattern = Pattern.compile(
		"\\<command.*name=\\\"([^\\\"]*)\\\".*\\>[\\s\\S]*?\\</command\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+");
	private final Pattern _poshiElementWithNoChildPattern = Pattern.compile(
		"\\\"[\\s]*\\>[\\n\\s\\t]*\\</[a-z\\-]+>");
	private final Pattern _poshiEndLinesAfterClosingElementPattern =
		Pattern.compile("(\\</[a-z\\-]+>)(\\n+)\\t*\\<[a-z]+");
	private final Pattern _poshiEndLinesBeforeClosingElementPattern =
		Pattern.compile("(\\n+)(\\t*</[a-z\\-]+>)");
	private final Pattern _poshiEndLinesPattern = Pattern.compile(
		"\\>\\n\\n\\n+(\\t*\\<)");
	private final Pattern _poshiOpeningTagPattern = Pattern.compile(
		"<[^/][^>]*[^/]>");
	private final Pattern _poshiQuoteWithSlashPattern = Pattern.compile(
		"\"[^\"]*\\>[^\"]*\"");
	private final Pattern _poshiSetUpPattern = Pattern.compile(
		"\\n[\\t]++\\<set-up\\>([\\s\\S]*?)\\</set-up\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private final Pattern _poshiTabsPattern = Pattern.compile(
		"\\n*([ \\t]*<).*");
	private final Pattern _poshiTearDownPattern = Pattern.compile(
		"\\n[\\t]++\\<tear-down\\>([\\s\\S]*?)\\</tear-down\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private final Pattern _poshiVariableLinePattern = Pattern.compile(
		"([\\t]*+)(\\<var.*?name=\\\"([^\\\"]*)\\\".*?/\\>" +
			".*+(?:\\</var\\>)??)");
	private final Pattern _poshiVariablesBlockPattern = Pattern.compile(
		"((?:[\\t]*+\\<var.*?\\>\\n[\\t]*+){2,}?)" +
			"(?:(?:\\n){1,}+|\\</execute\\>)");
	private final Pattern _poshiWholeTagPattern = Pattern.compile(
		"<[^\\>^/]*\\/>");

}