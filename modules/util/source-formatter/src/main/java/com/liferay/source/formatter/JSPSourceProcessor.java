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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.util.FileUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	protected void addImportCounts(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			String importName = matcher.group(1);

			int count = 0;

			if (_importCountMap.containsKey(importName)) {
				count = _importCountMap.get(importName);
			}
			else {
				int pos = importName.lastIndexOf(CharPool.PERIOD);

				String importClassName = importName.substring(pos + 1);

				if (_importClassNames.contains(importClassName)) {
					_duplicateImportClassNames.add(importClassName);
				}
				else {
					_importClassNames.add(importClassName);
				}
			}

			_importCountMap.put(importName, count + 1);
		}
	}

	protected List<String> addIncludedAndReferencedFileNames(
		List<String> fileNames, Set<String> checkedFileNames) {

		Set<String> includedAndReferencedFileNames = new HashSet<>();

		for (String fileName : fileNames) {
			if (!checkedFileNames.add(fileName)) {
				continue;
			}

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			includedAndReferencedFileNames.addAll(
				getJSPIncludeFileNames(fileName, fileNames));
			includedAndReferencedFileNames.addAll(
				getJSPReferenceFileNames(fileName, fileNames));
		}

		if (includedAndReferencedFileNames.isEmpty()) {
			return fileNames;
		}

		for (String fileName : includedAndReferencedFileNames) {
			fileName = StringUtil.replace(
				fileName, StringPool.SLASH, StringPool.BACK_SLASH);

			if (!fileNames.contains(fileName)) {
				fileNames.add(fileName);
			}
		}

		return addIncludedAndReferencedFileNames(fileNames, checkedFileNames);
	}

	protected void addJSPUnusedImports(
		String fileName, List<String> importLines,
		List<String> unneededImports) {

		for (String importLine : importLines) {
			int x = importLine.indexOf(CharPool.QUOTE);
			int y = importLine.indexOf(CharPool.QUOTE, x + 1);

			if ((x == -1) || (y == -1)) {
				continue;
			}

			String className = importLine.substring(x + 1, y);

			className = className.substring(
				className.lastIndexOf(CharPool.PERIOD) + 1);

			String regex = "[^A-Za-z0-9_\"]" + className + "[^A-Za-z0-9_\"]";

			if (hasUnusedJSPTerm(fileName, regex, "class")) {
				unneededImports.add(importLine);
			}
		}
	}

	protected String buildFullPathIncludeFileName(
		String fileName, String includeFileName) {

		String path = fileName;

		while (true) {
			int y = path.lastIndexOf(CharPool.SLASH);

			if (y == -1) {
				return StringPool.BLANK;
			}

			String fullPathIncludeFileName =
				path.substring(0, y) + includeFileName;

			if (_jspContents.containsKey(fullPathIncludeFileName) &&
				!fullPathIncludeFileName.equals(fileName)) {

				return fullPathIncludeFileName;
			}

			path = path.substring(0, y);
		}
	}

	protected void checkDefineObjectsVariable(
		String line, String fileName, int lineCount, String objectType,
		String variableName, String value, String tag) {

		if (line.contains(objectType + " " + variableName + " = " + value)) {
			processErrorMessage(
				fileName,
				"Use '" + tag + ":defineObjects' or rename var: " + fileName +
					" " + lineCount);
		}
	}

	protected void checkDefineObjectsVariables(
		String line, String fileName, int lineCount) {

		if (portalSource) {
			for (String[] defineObject : _LIFERAY_FRONTEND_DEFINE_OBJECTS) {
				checkDefineObjectsVariable(
					line, fileName, lineCount, defineObject[0], defineObject[1],
					defineObject[2], "liferay-frontend");
			}
		}

		for (String[] defineObject : _LIFERAY_THEME_DEFINE_OBJECTS) {
			checkDefineObjectsVariable(
				line, fileName, lineCount, defineObject[0], defineObject[1],
				defineObject[2], "liferay-theme");
		}

		for (String[] defineObject : _PORTLET_DEFINE_OBJECTS) {
			checkDefineObjectsVariable(
				line, fileName, lineCount, defineObject[0], defineObject[1],
				defineObject[2], "portlet");
		}
	}

	protected boolean checkTaglibVulnerability(
		String jspContent, String vulnerability) {

		int pos1 = -1;

		do {
			pos1 = jspContent.indexOf(vulnerability, pos1 + 1);

			if (pos1 != -1) {
				int pos2 = jspContent.lastIndexOf(CharPool.LESS_THAN, pos1);

				while ((pos2 > 0) &&
					   (jspContent.charAt(pos2 + 1) == CharPool.PERCENT)) {

					pos2 = jspContent.lastIndexOf(CharPool.LESS_THAN, pos2 - 1);
				}

				String tagContent = jspContent.substring(pos2, pos1);

				if (!tagContent.startsWith("<aui:") &&
					!tagContent.startsWith("<liferay-portlet:") &&
					!tagContent.startsWith("<liferay-util:") &&
					!tagContent.startsWith("<portlet:")) {

					return true;
				}
			}
		}
		while (pos1 != -1);

		return false;
	}

	protected void checkXSS(String fileName, String jspContent) {
		Matcher matcher = _xssPattern.matcher(jspContent);

		while (matcher.find()) {
			boolean xssVulnerable = false;

			String jspVariable = matcher.group(1);

			String anchorVulnerability = " href=\"<%= " + jspVariable + " %>";

			if (checkTaglibVulnerability(jspContent, anchorVulnerability)) {
				xssVulnerable = true;
			}

			String inputVulnerability = " value=\"<%= " + jspVariable + " %>";

			if (checkTaglibVulnerability(jspContent, inputVulnerability)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability1 = "'<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability1)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability2 = "(\"<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability2)) {
				xssVulnerable = true;
			}

			String inlineStringVulnerability3 = " \"<%= " + jspVariable + " %>";

			if (jspContent.contains(inlineStringVulnerability3)) {
				xssVulnerable = true;
			}

			String documentIdVulnerability = ".<%= " + jspVariable + " %>";

			if (jspContent.contains(documentIdVulnerability)) {
				xssVulnerable = true;
			}

			if (xssVulnerable) {
				processErrorMessage(
					fileName, "(xss): " + fileName + " (" + jspVariable + ")");
			}
		}
	}

	protected String compressImportsOrTaglibs(
		String fileName, String content, String attributePrefix) {

		if (!fileName.endsWith("init.jsp") && !fileName.endsWith("init.jspf")) {
			return content;
		}

		int x = content.indexOf(attributePrefix);

		int y = content.lastIndexOf(attributePrefix);

		y = content.indexOf("%>", y);

		if ((x == -1) || (y == -1) || (x > y)) {
			return content;
		}

		String importsOrTaglibs = content.substring(x, y);

		importsOrTaglibs = StringUtil.replace(
			importsOrTaglibs, new String[] {"%>\r\n<%@ ", "%>\n<%@ "},
			new String[] {"%><%@\r\n", "%><%@\n"});

		return content.substring(0, x) + importsOrTaglibs +
			content.substring(y);
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = formatJSP(fileName, absolutePath, content);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"<br/>", "@page import", "\"%>", ")%>", "function (",
				"javascript: ", "){\n", ";;\n", "\n\n\n"
			},
			new String[] {
				"<br />", "@ page import", "\" %>", ") %>", "function(",
				"javascript:", ") {\n", ";\n", "\n\n"
			});

		newContent = fixRedirectBackURL(newContent);

		newContent = fixCompatClassImports(absolutePath, newContent);

		newContent = fixEmptyLineInNestedTags(
			newContent, _emptyLineInNestedTagsPattern1, true);
		newContent = fixEmptyLineInNestedTags(
			newContent, _emptyLineInNestedTagsPattern2, false);
		newContent = fixEmptyLineInNestedTags(
			newContent, _emptyLineInNestedTagsPattern3, false);

		newContent = fixMissingEmptyLinesBetweenTags(newContent);

		if (_stripJSPImports && !_jspContents.isEmpty()) {
			try {
				newContent = formatJSPImportsOrTaglibs(
					fileName, newContent, _compressedJSPImportPattern,
					_uncompressedJSPImportPattern, true);
				newContent = formatJSPImportsOrTaglibs(
					fileName, newContent, _compressedJSPTaglibPattern,
					_uncompressedJSPTaglibPattern, false);
			}
			catch (RuntimeException re) {
				_stripJSPImports = false;
			}
		}

		if (portalSource && content.contains("page import=") &&
			!fileName.contains("init.jsp") &&
			!fileName.contains("init-ext.jsp") &&
			!fileName.contains("/taglib/aui/") &&
			!fileName.endsWith("touch.jsp") &&
			(fileName.endsWith(".jspf") || content.contains("include file="))) {

			processErrorMessage(
				fileName, "move imports to init.jsp: " + fileName);
		}

		newContent = fixCopyright(newContent, absolutePath, fileName);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"alert('<%= LanguageUtil.", "alert(\"<%= LanguageUtil.",
				"confirm('<%= LanguageUtil.", "confirm(\"<%= LanguageUtil."
			},
			new String[] {
				"alert('<%= UnicodeLanguageUtil.",
				"alert(\"<%= UnicodeLanguageUtil.",
				"confirm('<%= UnicodeLanguageUtil.",
				"confirm(\"<%= UnicodeLanguageUtil."
			});

		if (newContent.contains("    ")) {
			if (!fileName.matches(".*template.*\\.vm$")) {
				processErrorMessage(fileName, "tab: " + fileName);
			}
		}

		newContent = compressImportsOrTaglibs(
			fileName, newContent, "<%@ page import=");
		newContent = compressImportsOrTaglibs(
			fileName, newContent, "<%@ taglib uri=");

		newContent = fixSessionKey(fileName, newContent, sessionKeyPattern);
		newContent = fixSessionKey(
			fileName, newContent, taglibSessionKeyPattern);

		checkLanguageKeys(
			fileName, absolutePath, newContent, languageKeyPattern);
		checkLanguageKeys(
			fileName, absolutePath, newContent, _taglibLanguageKeyPattern1);
		checkLanguageKeys(
			fileName, absolutePath, newContent, _taglibLanguageKeyPattern2);
		checkLanguageKeys(
			fileName, absolutePath, newContent, _taglibLanguageKeyPattern3);

		newContent = formatJSONObject(newContent);

		newContent = formatStringBundler(fileName, newContent, -1);

		checkXSS(fileName, newContent);

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, true, fileName);

		// LPS-48156

		newContent = checkPrincipalException(newContent);

		newContent = formatLogFileName(absolutePath, newContent);

		newContent = formatDefineObjects(newContent);

		// LPS-59076

		if (portalSource && isModulesFile(absolutePath) &&
			newContent.contains("import=\"com.liferay.registry.Registry")) {

			processErrorMessage(
				fileName, "Do not use Registry in modules: " + fileName);
		}

		// LPS-62786

		checkPropertyUtils(fileName, newContent);

		// LPS-63953

		checkStringUtilReplace(fileName, newContent);

		Matcher matcher = _javaClassPattern.matcher(newContent);

		if (matcher.find()) {
			String javaClassContent = matcher.group();

			javaClassContent = javaClassContent.substring(1);

			String javaClassName = matcher.group(2);

			int javaClassLineCount = getLineCount(
				newContent, matcher.start() + 1);

			newContent = formatJavaTerms(
				javaClassName, null, file, fileName, absolutePath, newContent,
				javaClassContent, javaClassLineCount, null, null, null, null);
		}

		if (!content.equals(newContent)) {
			_jspContents.put(fileName, newContent);
		}

		return newContent;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		_moveFrequentlyUsedImportsToCommonInit = GetterUtil.getBoolean(
			getProperty("move.frequently.used.imports.to.common.init"));
		_unusedVariablesExcludes = getPropertyList(
			"jsp.unused.variables.excludes");

		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty()) {
			return fileNames;
		}

		List<String> allFileNames = null;

		if (sourceFormatterArgs.isFormatCurrentBranch() ||
			sourceFormatterArgs.isFormatLatestAuthor() ||
			sourceFormatterArgs.isFormatLocalChanges()) {

			allFileNames = getFileNames(
				sourceFormatterArgs.getBaseDirName(), null, excludes,
				getIncludes());
		}
		else {
			allFileNames = fileNames;
		}

		try {
			Pattern pattern = Pattern.compile(
				"\\s*@\\s*include\\s*file=['\"](.*)['\"]");

			for (String fileName : allFileNames) {
				File file = new File(fileName);

				fileName = StringUtil.replace(
					fileName, StringPool.BACK_SLASH, StringPool.SLASH);

				String absolutePath = getAbsolutePath(file);

				String content = FileUtil.read(file);

				Matcher matcher = pattern.matcher(content);

				String newContent = content;

				while (matcher.find()) {
					newContent = StringUtil.replaceFirst(
						newContent, matcher.group(),
						"@ include file=\"" + matcher.group(1) + "\"",
						matcher.start());
				}

				processFormattedFile(file, fileName, content, newContent);

				if (portalSource && _moveFrequentlyUsedImportsToCommonInit &&
					fileName.endsWith("/init.jsp") &&
					!isModulesFile(absolutePath) &&
					!fileName.endsWith("/common/init.jsp")) {

					addImportCounts(content);
				}

				_jspContents.put(fileName, newContent);
			}

			if (portalSource && _moveFrequentlyUsedImportsToCommonInit) {
				moveFrequentlyUsedImportsToCommonInit(4);
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		if (!sourceFormatterArgs.isFormatCurrentBranch() &&
			!sourceFormatterArgs.isFormatLatestAuthor() &&
			!sourceFormatterArgs.isFormatLocalChanges()) {

			return fileNames;
		}

		return addIncludedAndReferencedFileNames(
			fileNames, new HashSet<String>());
	}

	protected String fixEmptyLineInNestedTags(
		String content, Pattern pattern, boolean startTag) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(2);

			if ((startTag && ((tabs1.length() + 1) == tabs2.length())) ||
				(!startTag && ((tabs1.length() - 1) == tabs2.length()))) {

				content = StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.end(1));
			}
		}

		return content;
	}

	protected String fixMissingEmptyLinesBetweenTags(String content) {
		Matcher matcher = _missingEmptyLineBetweenTagsPattern.matcher(content);

		while (matcher.find()) {
			String tabs1 = matcher.group(1);
			String tabs2 = matcher.group(3);
			String tagName = matcher.group(2);

			if (tabs1.equals(tabs2) && !tagName.equals("when")) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.end(1));
			}
		}

		return content;
	}

	protected String fixRedirectBackURL(String content) {
		Matcher matcher = _redirectBackURLPattern.matcher(content);

		String newContent = content;

		while (matcher.find()) {
			newContent = StringUtil.replaceFirst(
				newContent, matcher.group(),
				matcher.group(1) + "\n\n" + matcher.group(2), matcher.start());
		}

		return newContent;
	}

	protected String formatDefineObjects(String content) {
		Matcher matcher = _missingEmptyLineBetweenDefineOjbectsPattern.matcher(
			content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		String previousDefineObjectsTag = null;

		matcher = _defineObjectsPattern.matcher(content);

		while (matcher.find()) {
			String defineObjectsTag = matcher.group(1);

			if (Validator.isNotNull(previousDefineObjectsTag) &&
				(previousDefineObjectsTag.compareTo(defineObjectsTag) > 0)) {

				content = StringUtil.replaceFirst(
					content, previousDefineObjectsTag, defineObjectsTag);
				content = StringUtil.replaceLast(
					content, defineObjectsTag, previousDefineObjectsTag);

				return content;
			}

			previousDefineObjectsTag = defineObjectsTag;
		}

		return content;
	}

	protected String formatJSP(
			String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		String currentAttributeAndValue = null;
		String previousAttribute = null;
		String previousAttributeAndValue = null;
		String tag = null;

		String currentException = null;
		String previousException = null;

		boolean hasUnsortedExceptions = false;

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			_checkedForIncludesFileNames = new HashSet<>();
			_includeFileNames = new HashSet<>();

			int lineCount = 0;

			String line = null;

			String previousLine = StringPool.BLANK;

			boolean readAttributes = false;

			boolean javaSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (portalSource && hasUnusedTaglib(fileName, line)) {
					continue;
				}

				if (!fileName.contains("jsonw") ||
					!fileName.endsWith("action.jsp")) {

					line = trimLine(line, false);
				}

				if (line.contains("<aui:button ") &&
					line.contains("type=\"button\"")) {

					processErrorMessage(
						fileName, "aui:button " + fileName + " " + lineCount);
				}

				if (line.contains("debugger.")) {
					processErrorMessage(
						fileName, "debugger " + fileName + " " + lineCount);
				}

				String trimmedLine = StringUtil.trimLeading(line);
				String trimmedPreviousLine = StringUtil.trimLeading(
					previousLine);

				if (line.matches(".*\\WgetClass\\(\\)\\..+")) {
					processErrorMessage(
						fileName, "chaining: " + fileName + " " + lineCount);
				}

				checkEmptyCollection(trimmedLine, fileName, lineCount);

				line = formatEmptyArray(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}

				if (javaSource || trimmedLine.contains("<%= ")) {
					checkInefficientStringMethods(
						line, fileName, absolutePath, lineCount);
				}

				if (javaSource) {
					if (portalSource &&
						!isExcludedPath(
							_unusedVariablesExcludes, absolutePath,
							lineCount) &&
						!_jspContents.isEmpty() &&
						hasUnusedVariable(fileName, trimmedLine)) {

						continue;
					}
				}

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					line = formatWhitespace(line, javaSource);

					if (line.endsWith(">")) {
						if (line.endsWith("/>")) {
							if (!trimmedLine.equals("/>") &&
								!line.endsWith(" />")) {

								line = StringUtil.replaceLast(
									line, "/>", " />");
							}
						}
						else if (line.endsWith(" >")) {
							line = StringUtil.replaceLast(line, " >", ">");
						}
					}
				}

				// LPS-47179

				if (line.contains(".sendRedirect(") &&
					!fileName.endsWith("_jsp.jsp")) {

					processErrorMessage(
						fileName,
						"Do not use sendRedirect in jsp: " + fileName + " " +
							lineCount);
				}

				// LPS-55341

				if (!javaSource) {
					line = StringUtil.replace(
						line, "LanguageUtil.get(locale,",
						"LanguageUtil.get(request,");
				}

				// LPS-58529

				checkResourceUtil(line, fileName, lineCount);

				checkDefineObjectsVariables(line, fileName, lineCount);

				if (!fileName.endsWith("test.jsp") &&
					line.contains("System.out.print")) {

					processErrorMessage(
						fileName,
						"System.out.print: " + fileName + " " + lineCount);
				}

				if (!trimmedLine.equals("%>") && line.contains("%>") &&
					!line.contains("--%>") && !line.contains(" %>")) {

					line = StringUtil.replace(line, "%>", " %>");
				}

				if (line.contains("<%=") && !line.contains("<%= ")) {
					line = StringUtil.replace(line, "<%=", "<%= ");
				}

				if (trimmedPreviousLine.equals("%>") &&
					Validator.isNotNull(line) && !trimmedLine.equals("-->")) {

					sb.append("\n");
				}
				else if (Validator.isNotNull(previousLine) &&
						 !trimmedPreviousLine.equals("<!--") &&
						 trimmedLine.equals("<%")) {

					sb.append("\n");
				}
				else if (trimmedPreviousLine.equals("<%") &&
						 Validator.isNull(line)) {

					continue;
				}
				else if (trimmedPreviousLine.equals("<%") &&
						 trimmedLine.startsWith("//")) {

					sb.append("\n");
				}
				else if (Validator.isNull(previousLine) &&
						 trimmedLine.equals("%>") && (sb.index() > 2)) {

					String lineBeforePreviousLine = sb.stringAt(sb.index() - 3);

					if (!lineBeforePreviousLine.startsWith("//")) {
						sb.setIndex(sb.index() - 1);
					}
				}

				if (javaSource &&
					(trimmedLine.startsWith("if (") ||
					 trimmedLine.startsWith("else if (") ||
					 trimmedLine.startsWith("while (")) &&
					trimmedLine.endsWith(") {")) {

					checkIfClauseParentheses(trimmedLine, fileName, lineCount);
				}

				Matcher matcher = _ifTagPattern.matcher(trimmedLine);

				if (matcher.find()) {
					String ifClause = "if (" + matcher.group(2) + ") {";

					checkIfClauseParentheses(ifClause, fileName, lineCount);
				}

				matcher = _jspTaglibPattern.matcher(line);

				while (matcher.find()) {
					line = sortAttributes(
						fileName, line, matcher.group(), lineCount, false);
				}

				if (readAttributes) {
					if (!trimmedLine.startsWith(StringPool.FORWARD_SLASH) &&
						!trimmedLine.startsWith(StringPool.GREATER_THAN)) {

						int pos = trimmedLine.indexOf(CharPool.EQUAL);

						if (pos != -1) {
							String attribute = trimmedLine.substring(0, pos);
							String newLine = formatTagAttributeType(
								line, tag, trimmedLine);

							if (!newLine.equals(line)) {
								line = newLine;

								readAttributes = false;
							}
							else if (!trimmedLine.endsWith(
										StringPool.APOSTROPHE) &&
									 !trimmedLine.endsWith(
										 StringPool.GREATER_THAN) &&
									 !trimmedLine.endsWith(StringPool.QUOTE)) {

								processErrorMessage(
									fileName,
									"attribute: " + fileName + " " + lineCount);

								readAttributes = false;
							}
							else if (trimmedLine.endsWith(
										StringPool.APOSTROPHE) &&
									 (!trimmedLine.contains(StringPool.QUOTE) ||
									  !tag.contains(StringPool.COLON))) {

								line = StringUtil.replace(
									line, StringPool.APOSTROPHE,
									StringPool.QUOTE);

								readAttributes = false;
							}
							else if (trimmedLine.endsWith(StringPool.QUOTE) &&
									 tag.contains(StringPool.COLON) &&
									 (StringUtil.count(
										 trimmedLine, CharPool.QUOTE) > 2)) {

								processErrorMessage(
									fileName,
									"attribute delimeter: " + fileName + " " +
										lineCount);

								readAttributes = false;
							}
							else if (Validator.isNotNull(previousAttribute)) {
								if (!isAttributName(attribute) &&
									!attribute.startsWith(
										StringPool.LESS_THAN)) {

									processErrorMessage(
										fileName,
										"attribute: " + fileName + " " +
											lineCount);

									readAttributes = false;
								}
								else if (Validator.isNull(
											previousAttributeAndValue) &&
										 (previousAttribute.compareToIgnoreCase(
											 attribute) > 0)) {

									previousAttributeAndValue = previousLine;
									currentAttributeAndValue = line;
								}
							}

							if (!readAttributes) {
								previousAttribute = null;
								previousAttributeAndValue = null;
							}
							else {
								previousAttribute = attribute;
							}
						}
					}
					else {
						previousAttribute = null;

						readAttributes = false;
					}
				}

				if (!hasUnsortedExceptions) {
					int x = line.indexOf("<liferay-ui:error exception=\"<%=");

					if (x != -1) {
						int y = line.indexOf(".class %>", x);

						if (y != -1) {
							currentException = line.substring(x, y);

							if (Validator.isNotNull(previousException) &&
								(previousException.compareToIgnoreCase(
									currentException) > 0)) {

								currentException = line;
								previousException = previousLine;

								hasUnsortedExceptions = true;
							}
						}
					}

					if (!hasUnsortedExceptions) {
						previousException = currentException;
						currentException = null;
					}
				}

				if (trimmedLine.startsWith(StringPool.LESS_THAN) &&
					!trimmedLine.startsWith("<%") &&
					!trimmedLine.startsWith("<!")) {

					if (!trimmedLine.contains(StringPool.GREATER_THAN) &&
						!trimmedLine.contains(StringPool.SPACE)) {

						tag = trimmedLine.substring(1);

						readAttributes = true;
					}
					else {
						line = sortAttributes(
							fileName, line, trimmedLine, lineCount, false);
					}
				}

				if (!trimmedLine.contains(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					while (trimmedLine.contains(StringPool.TAB)) {
						line = StringUtil.replaceLast(
							line, StringPool.TAB, StringPool.SPACE);

						trimmedLine = StringUtil.replaceLast(
							trimmedLine, StringPool.TAB, StringPool.SPACE);
					}

					while (trimmedLine.contains(StringPool.DOUBLE_SPACE) &&
						   !trimmedLine.contains(
							   StringPool.QUOTE + StringPool.DOUBLE_SPACE) &&
						   !fileName.endsWith(".vm")) {

						line = StringUtil.replaceLast(
							line, StringPool.DOUBLE_SPACE, StringPool.SPACE);

						trimmedLine = StringUtil.replaceLast(
							trimmedLine, StringPool.DOUBLE_SPACE,
							StringPool.SPACE);
					}
				}

				if (!fileName.endsWith("/touch.jsp")) {
					int x = line.indexOf("<%@ include file");

					if (x != -1) {
						x = line.indexOf(CharPool.QUOTE, x);

						int y = line.indexOf(CharPool.QUOTE, x + 1);

						if (y != -1) {
							String includeFileName = line.substring(x + 1, y);

							matcher = _jspIncludeFilePattern.matcher(
								includeFileName);

							if (!matcher.find()) {
								processErrorMessage(
									fileName,
									"include: " + fileName + " " + lineCount);
							}
						}
					}
				}

				line = replacePrimitiveWrapperInstantiation(line);

				previousLine = line;

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		if (Validator.isNotNull(previousAttributeAndValue)) {
			content = StringUtil.replaceFirst(
				content,
				previousAttributeAndValue + "\n" + currentAttributeAndValue,
				currentAttributeAndValue + "\n" + previousAttributeAndValue);
		}

		if (hasUnsortedExceptions) {
			if ((StringUtil.count(content, currentException) > 1) ||
				(StringUtil.count(content, previousException) > 1)) {

				processErrorMessage(
					fileName, "unsorted exceptions: " + fileName);
			}
			else {
				content = StringUtil.replaceFirst(
					content, previousException, currentException);

				content = StringUtil.replaceLast(
					content, currentException, previousException);
			}
		}

		return content;
	}

	protected String formatJSPImportsOrTaglibs(
			String fileName, String content, Pattern compressedPattern,
			Pattern uncompressedPattern, boolean checkUnusedImports)
		throws IOException {

		if (fileName.endsWith("init-ext.jsp")) {
			return content;
		}

		Matcher matcher = compressedPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String imports = matcher.group();

		String newImports = StringUtil.replace(
			imports, new String[] {"<%@\r\n", "<%@\n", " %><%@ "},
			new String[] {"\r\n<%@ ", "\n<%@ ", " %>\n<%@ "});

		if (checkUnusedImports) {
			List<String> importLines = new ArrayList<>();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(newImports));

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains("import=")) {
					importLines.add(line);
				}
			}

			List<String> unneededImports = getJSPDuplicateImports(
				fileName, content, importLines);

			addJSPUnusedImports(fileName, importLines, unneededImports);

			for (String unneededImport : unneededImports) {
				newImports = StringUtil.replace(
					newImports, unneededImport, StringPool.BLANK);
			}
		}

		content = StringUtil.replaceFirst(content, imports, newImports);

		ImportsFormatter importsFormatter = new JSPImportsFormatter();

		return importsFormatter.format(content, uncompressedPattern);
	}

	protected String formatLogFileName(String absolutePath, String content) {
		if (!isModulesFile(absolutePath) &&
			!absolutePath.contains("/portal-web/")) {

			return content;
		}

		Matcher matcher = _logPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String logFileName = StringUtil.replace(
			absolutePath, StringPool.PERIOD, StringPool.UNDERLINE);

		logFileName = StringUtil.replace(
			logFileName, StringPool.SLASH, StringPool.PERIOD);

		logFileName = StringUtil.replace(
			logFileName, StringPool.DASH, StringPool.UNDERLINE);

		int x = logFileName.lastIndexOf(".portal_web.");

		if (x != -1) {
			logFileName = logFileName.substring(x + 1);
		}
		else {
			x = logFileName.lastIndexOf(".docroot.");

			if (x == -1) {
				x = Math.max(
					logFileName.lastIndexOf(
						".src.main.resources.META_INF.resources."),
					logFileName.lastIndexOf(".src.META_INF.resources."));
			}

			if (x == -1) {
				return content;
			}

			x = logFileName.lastIndexOf(StringPool.PERIOD, x - 1);

			logFileName = "com_liferay_" + logFileName.substring(x + 1);

			logFileName = StringUtil.replace(
				logFileName,
				new String[] {
					".docroot.", ".src.main.resources.META_INF.resources.",
					".src.META_INF.resources."
				},
				new String[] {
					StringPool.PERIOD, StringPool.PERIOD, StringPool.PERIOD
				});
		}

		return StringUtil.replace(
			content, matcher.group(),
			"Log _log = LogFactoryUtil.getLog(\"" + logFileName + "\")");
	}

	@Override
	protected String formatTagAttributeType(
			String line, String tagName, String attributeAndValue)
		throws Exception {

		if (!attributeAndValue.endsWith(StringPool.QUOTE) ||
			attributeAndValue.contains("\"<%=")) {

			return line;
		}

		if (tagName.startsWith("liferay-")) {
			tagName = tagName.substring(8);
		}

		JavaClass tagJavaClass = getTagJavaClass(tagName);

		if (tagJavaClass == null) {
			return line;
		}

		int pos = attributeAndValue.indexOf("=\"");

		String attribute = attributeAndValue.substring(0, pos);

		String setAttributeMethodName =
			"set" + TextFormatter.format(attribute, TextFormatter.G);

		for (String dataType : getPrimitiveTagAttributeDataTypes()) {
			Type javaType = new Type(dataType);

			JavaMethod setAttributeMethod = tagJavaClass.getMethodBySignature(
				setAttributeMethodName, new Type[] {javaType}, true);

			if (setAttributeMethod != null) {
				String value = attributeAndValue.substring(
					pos + 2, attributeAndValue.length() - 1);

				if (!isValidTagAttributeValue(value, dataType)) {
					return line;
				}

				String newAttributeAndValue = StringUtil.replace(
					attributeAndValue,
					StringPool.QUOTE + value + StringPool.QUOTE,
					"\"<%= " + value + " %>\"");

				return StringUtil.replace(
					line, attributeAndValue, newAttributeAndValue);
			}
		}

		return line;
	}

	protected List<String> getJSPDuplicateImports(
		String fileName, String content, List<String> importLines) {

		List<String> duplicateImports = new ArrayList<>();

		for (String importLine : importLines) {
			int x = content.indexOf("<%@ include file=");

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("<%@ page import=");

			if (y == -1) {
				continue;
			}

			if ((x < y) && isJSPDuplicateImport(fileName, importLine, false)) {
				duplicateImports.add(importLine);
			}
		}

		return duplicateImports;
	}

	protected Set<String> getJSPIncludeFileNames(
		String fileName, Collection<String> fileNames) {

		Set<String> includeFileNames = new HashSet<>();

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return includeFileNames;
		}

		for (int x = 0;;) {
			x = content.indexOf("<%@ include file=", x);

			if (x == -1) {
				break;
			}

			x = content.indexOf(CharPool.QUOTE, x);

			if (x == -1) {
				break;
			}

			int y = content.indexOf(CharPool.QUOTE, x + 1);

			if (y == -1) {
				break;
			}

			String includeFileName = content.substring(x + 1, y);

			if (!includeFileName.startsWith(StringPool.SLASH)) {
				includeFileName = StringPool.SLASH + includeFileName;
			}

			Matcher matcher = _jspIncludeFilePattern.matcher(includeFileName);

			if (!matcher.find()) {
				throw new RuntimeException(
					"Invalid include " + includeFileName);
			}

			String extension = matcher.group(1);

			if (extension.equals("svg")) {
				x = y;

				continue;
			}

			includeFileName = buildFullPathIncludeFileName(
				fileName, includeFileName);

			if ((includeFileName.endsWith("jsp") ||
				 includeFileName.endsWith("jspf")) &&
				!includeFileName.endsWith("html/common/init.jsp") &&
				!includeFileName.endsWith("html/portlet/init.jsp") &&
				!includeFileName.endsWith("html/taglib/init.jsp") &&
				!fileNames.contains(includeFileName)) {

				includeFileNames.add(includeFileName);
			}

			x = y;
		}

		return includeFileNames;
	}

	protected Set<String> getJSPReferenceFileNames(
		String fileName, Collection<String> fileNames) {

		Set<String> referenceFileNames = new HashSet<>();

		if (!fileName.endsWith("init.jsp") && !fileName.endsWith("init.jspf") &&
			!fileName.contains("init-ext.jsp")) {

			return referenceFileNames;
		}

		for (Map.Entry<String, String> entry : _jspContents.entrySet()) {
			String referenceFileName = entry.getKey();

			if (fileNames.contains(referenceFileName)) {
				continue;
			}

			String sharedPath = fileName.substring(
				0, StringUtil.startsWithWeight(referenceFileName, fileName));

			if (Validator.isNull(sharedPath) ||
				!sharedPath.contains(StringPool.SLASH)) {

				continue;
			}

			if (!sharedPath.endsWith(StringPool.SLASH)) {
				sharedPath = sharedPath.substring(
					0, sharedPath.lastIndexOf(CharPool.SLASH) + 1);
			}

			String content = null;

			for (int x = -1;;) {
				x = sharedPath.indexOf(CharPool.SLASH, x + 1);

				if (x == -1) {
					break;
				}

				if (content == null) {
					content = entry.getValue();
				}

				if (content.contains(
						"<%@ include file=\"" + fileName.substring(x)) ||
					content.contains(
						"<%@ include file=\"" + fileName.substring(x + 1))) {

					referenceFileNames.add(referenceFileName);

					break;
				}
			}
		}

		return referenceFileNames;
	}

	protected Set<String> getPrimitiveTagAttributeDataTypes() {
		if (_primitiveTagAttributeDataTypes != null) {
			return _primitiveTagAttributeDataTypes;
		}

		_primitiveTagAttributeDataTypes = SetUtil.fromArray(
			new String[] {"boolean", "double", "int", "long"});

		return _primitiveTagAttributeDataTypes;
	}

	protected JavaClass getTagJavaClass(String tag) throws Exception {
		JavaClass tagJavaClass = _tagJavaClassesMap.get(tag);

		if (tagJavaClass != null) {
			return tagJavaClass;
		}

		String[] tagParts = StringUtil.split(tag, CharPool.COLON);

		if (tagParts.length != 2) {
			return null;
		}

		String utilTaglibDirName = getUtilTaglibDirName();

		if (Validator.isNull(utilTaglibDirName)) {
			return null;
		}

		String tagName = tagParts[1];

		String tagJavaClassName = TextFormatter.format(
			tagName, TextFormatter.M);

		tagJavaClassName =
			TextFormatter.format(tagJavaClassName, TextFormatter.G) + "Tag";

		String tagCategory = tagParts[0];

		StringBundler sb = new StringBundler(6);

		sb.append(utilTaglibDirName);
		sb.append("/src/com/liferay/taglib/");
		sb.append(tagCategory);
		sb.append(StringPool.SLASH);
		sb.append(tagJavaClassName);
		sb.append(".java");

		File tagJavaFile = new File(sb.toString());

		if (!tagJavaFile.exists()) {
			return null;
		}

		JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		javaDocBuilder.addSource(tagJavaFile);

		sb = new StringBundler(4);

		sb.append("com.liferay.taglib.");
		sb.append(tagCategory);
		sb.append(StringPool.PERIOD);
		sb.append(tagJavaClassName);

		tagJavaClass = javaDocBuilder.getClassByName(sb.toString());

		_tagJavaClassesMap.put(tag, tagJavaClass);

		return tagJavaClass;
	}

	protected String getUtilTaglibDirName() {
		if (_utilTaglibDirName != null) {
			return _utilTaglibDirName;
		}

		File utilTaglibDir = getFile("util-taglib", PORTAL_MAX_DIR_LEVEL);

		if (utilTaglibDir != null) {
			_utilTaglibDirName = utilTaglibDir.getAbsolutePath();

			_utilTaglibDirName = StringUtil.replace(
				_utilTaglibDirName, StringPool.BACK_SLASH, StringPool.SLASH);
		}
		else {
			_utilTaglibDirName = StringPool.BLANK;
		}

		return _utilTaglibDirName;
	}

	protected String getVariableName(String line) {
		if (!line.endsWith(";") || line.startsWith("//")) {
			return null;
		}

		String variableName = null;

		int x = line.indexOf(" = ");

		if (x == -1) {
			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1, line.length() - 1);
			}
		}
		else {
			line = line.substring(0, x);

			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1);
			}
		}

		if (Validator.isVariableName(variableName)) {
			return variableName;
		}

		return null;
	}

	protected boolean hasUnusedJSPTerm(
		String fileName, String regex, String type) {

		_includeFileNames.add(fileName);

		Set<String> checkedForUnusedJSPTerm = new HashSet<>();

		return !isJSPTermRequired(
			fileName, regex, type, checkedForUnusedJSPTerm);
	}

	protected boolean hasUnusedTaglib(String fileName, String line) {
		if (!line.startsWith("<%@ taglib uri=")) {
			return false;
		}

		int x = line.indexOf(" prefix=");

		if (x == -1) {
			return false;
		}

		x = line.indexOf(CharPool.QUOTE, x);

		int y = line.indexOf(CharPool.QUOTE, x + 1);

		if ((x == -1) || (y == -1)) {
			return false;
		}

		String taglibPrefix = line.substring(x + 1, y);

		String regex = StringPool.LESS_THAN + taglibPrefix + StringPool.COLON;

		return hasUnusedJSPTerm(fileName, regex, "taglib");
	}

	protected boolean hasUnusedVariable(String fileName, String line) {
		if (line.contains(": ")) {
			return false;
		}

		String variableName = getVariableName(line);

		if (Validator.isNull(variableName) || variableName.equals("false") ||
			variableName.equals("true")) {

			return false;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("((/)|(\\*)|(\\+(\\+)?)|(-(-)?)|\\(|=)?( )?");
		sb.append(variableName);
		sb.append("( )?(\\.");
		sb.append("|(((\\+)|(-)|(\\*)|(/)|(%)|(\\|)|(&)|(\\^))?(=))");
		sb.append("|(\\+(\\+)?)|(-(-)?)");
		sb.append("|(\\)))?");

		return hasUnusedJSPTerm(fileName, sb.toString(), "variable");
	}

	protected boolean isJSPDuplicateImport(
		String fileName, String importLine, boolean checkFile) {

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		int x = importLine.indexOf("page");

		if (x == -1) {
			return false;
		}

		if (checkFile && content.contains(importLine.substring(x))) {
			return true;
		}

		int y = content.indexOf("<%@ include file=");

		if (y == -1) {
			return false;
		}

		y = content.indexOf(CharPool.QUOTE, y);

		if (y == -1) {
			return false;
		}

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		if (z == -1) {
			return false;
		}

		String includeFileName = content.substring(y + 1, z);

		includeFileName = buildFullPathIncludeFileName(
			fileName, includeFileName);

		return isJSPDuplicateImport(includeFileName, importLine, true);
	}

	protected boolean isJSPTermRequired(
		String fileName, String regex, String type,
		Set<String> checkedForUnusedJSPTerm) {

		if (checkedForUnusedJSPTerm.contains(fileName)) {
			return false;
		}

		checkedForUnusedJSPTerm.add(fileName);

		String content = _jspContents.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find() &&
			(!type.equals("variable") || (checkedForUnusedJSPTerm.size() > 1) ||
			 matcher.find())) {

			return true;
		}

		if (!_checkedForIncludesFileNames.contains(fileName)) {
			_includeFileNames.addAll(
				getJSPIncludeFileNames(fileName, _includeFileNames));
			_includeFileNames.addAll(
				getJSPReferenceFileNames(fileName, _includeFileNames));
		}

		_checkedForIncludesFileNames.add(fileName);

		String[] includeFileNamesArray = _includeFileNames.toArray(
			new String[_includeFileNames.size()]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedForUnusedJSPTerm.contains(includeFileName) &&
				isJSPTermRequired(
					includeFileName, regex, type, checkedForUnusedJSPTerm)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isValidTagAttributeValue(String value, String dataType) {
		if (dataType.equals("boolean")) {
			return Validator.isBoolean(value);
		}

		if (dataType.equals("double")) {
			try {
				Double.parseDouble(value);
			}
			catch (NumberFormatException nfe) {
				return false;
			}

			return true;
		}

		if (dataType.equals("int") || dataType.equals("long")) {
			return Validator.isNumber(value);
		}

		return false;
	}

	protected void moveFrequentlyUsedImportsToCommonInit(int minCount)
		throws IOException {

		if (_importCountMap.isEmpty()) {
			return;
		}

		String commonInitFileName = "portal-web/docroot/html/common/init.jsp";

		File commonInitFile = null;
		String commonInitFileContent = null;

		int x = -1;

		for (Map.Entry<String, Integer> importCount :
				_importCountMap.entrySet()) {

			Integer count = importCount.getValue();

			if (count < minCount) {
				continue;
			}

			String importName = importCount.getKey();

			int y = importName.lastIndexOf(CharPool.PERIOD);

			String importClassName = importName.substring(y + 1);

			if (_duplicateImportClassNames.contains(importClassName)) {
				continue;
			}

			if (commonInitFileContent == null) {
				commonInitFile = new File(commonInitFileName);

				commonInitFileContent = FileUtil.read(commonInitFile);

				x = commonInitFileContent.indexOf("<%@ page import");
			}

			commonInitFileContent = StringUtil.insert(
				commonInitFileContent,
				"<%@ page import=\"" + importName + "\" %>\n", x);
		}

		if (commonInitFileContent != null) {
			FileUtil.write(commonInitFile, commonInitFileContent);

			_jspContents.put(commonInitFileName, commonInitFileContent);
		}
	}

	@Override
	protected String sortHTMLAttributes(
		String line, String value, String attributeAndValue) {

		if (!value.matches("([-a-z0-9]+ )+[-a-z0-9]+")) {
			return line;
		}

		List<String> htmlAttributes = ListUtil.fromArray(
			StringUtil.split(value, StringPool.SPACE));

		Collections.sort(htmlAttributes);

		String newValue = StringUtil.merge(htmlAttributes, StringPool.SPACE);

		if (value.equals(newValue)) {
			return line;
		}

		String newAttributeAndValue = StringUtil.replace(
			attributeAndValue, value, newValue);

		return StringUtil.replace(
			line, attributeAndValue, newAttributeAndValue);
	}

	private static final String[] _INCLUDES = new String[] {
		"**/*.jsp", "**/*.jspf", "**/*.vm"
	};

	private static final String[][] _LIFERAY_FRONTEND_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"String", "currentURL", "currentURLObj.toString()"},
			new String[] {
				"PortletURL", "currentURLObj",
				"PortletURLUtil.getCurrent(liferayPortletRequest, " +
					"liferayPortletResponse)"
			},
			new String[] {
				"ResourceBundle", "resourceBundle",
				"ResourceBundleUtil.getBundle(\"content.Language\", locale, " +
					"getClass()"
			},
			new String[] {
				"WindowState", "windowState",
				"liferayPortletRequest.getWindowState()"
			}
	};

	private static final String[][] _LIFERAY_THEME_DEFINE_OBJECTS =
		new String[][] {
			new String[] {"Account", "account", "themeDisplay.getAccount()"},
			new String[] {
				"ColorScheme", "colorScheme", "themeDisplay.getColorScheme()"
			},
			new String[] {"Company", "company", "themeDisplay.getCompany()"},
			new String[] {"Contact", "contact", "themeDisplay.getContact()"},
			new String[] {"Layout", "layout", "themeDisplay.getLayout()"},
			new String[] {
				"List<Layout>", "layouts", "themeDisplay.getLayouts()"
			},
			new String[] {
				"LayoutTypePortlet", "layoutTypePortlet",
				"themeDisplay.getLayoutTypePortlet()"
			},
			new String[] {"Locale", "locale", "themeDisplay.getLocale()"},
			new String[] {
				"PermissionChecker", "permissionChecker",
				"themeDisplay.getPermissionChecker()"
			},
			new String[] {"long", "plid", "themeDisplay.getPlid()"},
			new String[] {
				"PortletDisplay", "portletDisplay",
				"themeDisplay.getPortletDisplay()"
			},
			new String[] {"User", "realUser", "themeDisplay.getRealUser()"},
			new String[] {
				"long", "scopeGroupId", "themeDisplay.getScopeGroupId()"
			},
			new String[] {"Theme", "theme", "themeDisplay.getTheme()"},
			new String[] {
				"ThemeDisplay", "themeDisplay",
				"(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY)"
			},
			new String[] {"TimeZone", "timeZone", "themeDisplay.getTimeZone()"},
			new String[] {"User", "user", "themeDisplay.getUser()"},
			new String[] {
				"long", "portletGroupId", "themeDisplay.getScopeGroupId()"
			}
		};

	private static final String[][] _PORTLET_DEFINE_OBJECTS = new String[][] {
		new String[] {
			"PortletConfig", "portletConfig",
			"(PortletConfig)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_CONFIG)"
		},
		new String[] {
			"String", "portletName", "portletConfig.getPortletName()"
		},
		new String[] {
			"LiferayPortletRequest", "liferayPortletRequest",
			"PortalUtil.getLiferayPortletRequest(portletRequest)"
		},
		new String[] {
			"PortletRequest", "actionRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "eventRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "renderRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletRequest", "resourceRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		new String[] {
			"PortletPreferences", "portletPreferences",
			"portletRequest.getPreferences()"
		},
		new String[] {
			"Map<String, String[]>", "portletPreferencesValues",
			"portletPreferences.getMap()"
		},
		new String[] {
			"PortletSession", "portletSession",
			"portletRequest.getPortletSession()"
		},
		new String[] {
			"Map<String, Object>", "portletSessionScope",
			"portletSession.getAttributeMap()"
		},
		new String[] {
			"LiferayPortletResponse", "liferayPortletResponse",
			"PortalUtil.getLiferayPortletResponse(portletResponse)"
		},
		new String[] {
			"PortletResponse", "actionResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "eventResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "renderResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"PortletResponse", "resourceResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		new String[] {
			"SearchContainerReference", "searchContainerReference",
			"(SearchContainerReference)request.getAttribute(WebKeys." +
				"SEARCH_CONTAINER_REFERENCE)"
		}
	};

	private Set<String> _checkedForIncludesFileNames = new HashSet<>();
	private final Pattern _compressedJSPImportPattern = Pattern.compile(
		"(<.*\n*page.import=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _compressedJSPTaglibPattern = Pattern.compile(
		"(<.*\n*taglib uri=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _defineObjectsPattern = Pattern.compile(
		"\n\t*(<.*:defineObjects />)\n");
	private final List<String> _duplicateImportClassNames = new ArrayList<>();
	private final Pattern _emptyLineInNestedTagsPattern1 = Pattern.compile(
		"\n(\t*)<[a-z-]*:.*[^/]>\n\n(\t*)<[a-z-]*:.*>\n");
	private final Pattern _emptyLineInNestedTagsPattern2 = Pattern.compile(
		"\n(\t*)/>\n\n(\t*)</[a-z-]*:[a-z-]*>\n");
	private final Pattern _emptyLineInNestedTagsPattern3 = Pattern.compile(
		"\n(\t*)</[a-z-]*:[a-z-]*>\n\n(\t*)</[a-z-]*:[a-z-]*>\n");
	private final Pattern _ifTagPattern = Pattern.compile(
		"^<c:if test=('|\")<%= (.+) %>('|\")>$");
	private final List<String> _importClassNames = new ArrayList<>();
	private final Map<String, Integer> _importCountMap = new HashMap<>();
	private final Pattern _importsPattern = Pattern.compile(
		"page import=\"(.+)\"");
	private Set<String> _includeFileNames = new HashSet<>();
	private Pattern _javaClassPattern = Pattern.compile(
		"\n(private|protected|public).* class ([A-Za-z0-9]+) " +
			"([\\s\\S]*?)\n\\}\n");
	private final Map<String, String> _jspContents = new HashMap<>();
	private final Pattern _jspIncludeFilePattern = Pattern.compile(
		"/.*\\.(jsp[f]?|svg)");
	private final Pattern _jspTaglibPattern = Pattern.compile(
		"<[-\\w]+:[-\\w]+ (.*?[^%])>");
	private final Pattern _logPattern = Pattern.compile(
		"Log _log = LogFactoryUtil\\.getLog\\(\"(.*?)\"\\)");
	private final Pattern _missingEmptyLineBetweenDefineOjbectsPattern =
		Pattern.compile("<.*:defineObjects />\n<.*:defineObjects />\n");
	private final Pattern _missingEmptyLineBetweenTagsPattern = Pattern.compile(
		"\n(\t*)</[a-z-]+:([a-z-]+)>\n(\t*)<[a-z-]+");
	private boolean _moveFrequentlyUsedImportsToCommonInit;
	private Set<String> _primitiveTagAttributeDataTypes;
	private final Pattern _redirectBackURLPattern = Pattern.compile(
		"(String redirect = ParamUtil\\.getString\\(request, \"redirect\".*" +
			"\\);)\n(String backURL = ParamUtil\\.getString\\(request, \"" +
				"backURL\", redirect\\);)");
	private boolean _stripJSPImports = true;
	private final Map<String, JavaClass> _tagJavaClassesMap = new HashMap<>();
	private final Pattern _taglibLanguageKeyPattern1 = Pattern.compile(
		"(?:confirmation|label|(?:M|m)essage|message key|names|title)=\"[^A-Z" +
			"<=%\\[\\s]+\"");
	private final Pattern _taglibLanguageKeyPattern2 = Pattern.compile(
		"(aui:)(?:input|select|field-wrapper) (?!.*label=(?:'|\").*(?:'|\").*" +
			"name=\"[^<=%\\[\\s]+\")(?!.*name=\"[^<=%\\[\\s]+\".*title=" +
				"(?:'|\").+(?:'|\"))(?!.*name=\"[^<=%\\[\\s]+\".*type=\"" +
					"hidden\").*name=\"([^<=%\\[\\s]+)\"");
	private final Pattern _taglibLanguageKeyPattern3 = Pattern.compile(
		"(liferay-ui:)(?:input-resource) .*id=\"([^<=%\\[\\s]+)\"(?!.*title=" +
			"(?:'|\").+(?:'|\"))");
	private final Pattern _uncompressedJSPImportPattern = Pattern.compile(
		"(<.*page.import=\".*>\n*)+", Pattern.MULTILINE);
	private final Pattern _uncompressedJSPTaglibPattern = Pattern.compile(
		"(<.*taglib uri=\".*>\n*)+", Pattern.MULTILINE);
	private List<String> _unusedVariablesExcludes;
	private String _utilTaglibDirName;
	private final Pattern _xssPattern = Pattern.compile(
		"\\s+([^\\s]+)\\s*=\\s*(Bean)?ParamUtil\\.getString\\(");

}