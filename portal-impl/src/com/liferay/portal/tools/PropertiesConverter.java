/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse Rao
 * @author James Hinkey
 */
public class PropertiesConverter {

	public static void main(String[] args) {
		try {
			new PropertiesConverter(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertiesConverter(String[] args) throws IOException {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String propertiesDestDir = GetterUtil.getString(
			arguments.get("properties.dest.dir"));
		String propertiesFileName = GetterUtil.getString(
			arguments.get("properties.file"));
		String title = GetterUtil.getString(arguments.get("properties.title"));
		boolean toc = GetterUtil.getBoolean(arguments.get("properties.toc"));

		System.out.println("Converting " + propertiesFileName + " to HTML");

		File propertiesFile = new File(propertiesFileName);

		// Create a data model for Freemarker

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("pageTitle", title);
		context.put("toc", toc);

		int pos = propertiesFileName.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			propertiesFileName = propertiesFileName.substring(pos + 1);
		}

		context.put("propertiesFileName", propertiesFileName);

		String propertiesString = _fileUtil.read(propertiesFile);

		String[] sectionStrings = propertiesString.split("\n\n");

		List<PropertiesSection> sections = new ArrayList<PropertiesSection>(
			sectionStrings.length);

		for (String sectionString : sectionStrings) {
			sectionString = StringUtil.trimLeading(
				sectionString, CharPool.SPACE);

			PropertiesSection section = new PropertiesSection(sectionString);

			if (sectionString.startsWith("##")) {
				int lineCount = _getLineCount(sectionString);

				if (lineCount == 3) {
					section.setTitle(_extractTitle(section));

					sections.add(section);
				}
				else if (lineCount > 3) {
					section.setComments(_extractComments(section));

					sections.add(section);
				}
				else {
					StringBundler sb = new StringBundler(8);

					sb.append("Error: PropertiesSection should consist of 3 ");
					sb.append("or more lines:");
					sb.append(StringPool.NEW_LINE);
					sb.append("##");
					sb.append(StringPool.NEW_LINE);
					sb.append("## Comment(s)");
					sb.append(StringPool.NEW_LINE);
					sb.append("##");

					System.out.println(sb.toString());

					return;
				}
			}
			else {
				section.setDefaultProperties(
					_extractDefaultProperties(section));
				section.setExampleProperties(
					_extractExampleProperties(section));
				section.setPropertyComments(_extractPropertyComments(section));

				sections.add(section);
			}
		}

		context.put("sections", sections);

		try {
			StringBundler sb = new StringBundler(4);

			sb.append(propertiesDestDir);
			sb.append(StringPool.SLASH);
			sb.append(propertiesFileName);
			sb.append(".html");

			String htmlFileName = sb.toString();

			System.out.println("Writing " + htmlFileName);

			File propertiesHTMLFile = new File(htmlFileName);

			Writer writer = new FileWriter(propertiesHTMLFile);

			// Get the Freemarker template and merge it with the data model

			try {
				FreeMarkerUtil.process(_TPL_PROPERTIES_HTML, context, writer);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public class PropertyComment {

		public PropertyComment(String comment) {
			_comment = comment;

			String[] lines = StringUtil.split(comment, CharPool.NEW_LINE);

			for (String line : lines) {
				if (line.startsWith(_INDENT)) {
					_isPreFormatted = true;

					return;
				}
			}
		}

		public String getComment() {
			return _comment;
		}

		public boolean isPreFormatted() {
			return _isPreFormatted;
		}

		private String _comment;
		private boolean _isPreFormatted;

	}

	private void _addParagraph(
		List<PropertyComment> propertyComments, String paragraph) {

		if (Validator.isNotNull(paragraph)) {
			propertyComments.add(new PropertyComment(paragraph));
		}
	}

	private int _getLineCount(String s) {
		String[] lines = s.split("\r\n|\r|\n");

		return lines.length;
	}

	private List<String> _extractComments(PropertiesSection section) {
		String sectionText = section.getText();

		String[] lines = StringUtil.split(sectionText, CharPool.NEW_LINE);

		List<String> comments = new ArrayList<String>();

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			String trimmedLine = line.trim();

			if (trimmedLine.startsWith("## ")) {
				trimmedLine = trimmedLine.substring(2);

				sb.append(trimmedLine.trim());
			}

			if (trimmedLine.length() < 3) {
				if (sb.index() == 0) {
					continue;
				}

				comments.add(sb.toString());

				sb = new StringBundler();
			}
		}

		return comments;
	}

	private String _extractDefaultProperties(PropertiesSection section) {
		String sectionText = section.getText();

		String[] lines = StringUtil.split(sectionText, CharPool.NEW_LINE);

		StringBundler sb = new StringBundler();

		boolean previousLineIsDefaultProperty = false;

		for (String line : lines) {
			if (!previousLineIsDefaultProperty) {
				if (!line.startsWith("#") && !line.startsWith(_INDENT + "#")) {
					previousLineIsDefaultProperty = true;

					sb.append(line);
					sb.append(StringPool.NEW_LINE);
				}
			}
			else {
				if (line.startsWith("#") || line.startsWith(_INDENT + "#")) {
					previousLineIsDefaultProperty = false;

					continue;
				}

				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private String _extractExampleProperties(PropertiesSection section) {
		String sectionText = section.getText();

		String[] lines = StringUtil.split(sectionText, CharPool.NEW_LINE);

		StringBundler sb = new StringBundler();

		boolean previousLineIsExample = false;

		for (String line : lines) {
			String trimmedLine = line.trim();

			if (!previousLineIsExample) {
				if (line.startsWith(_INDENT + "# ") ||
					trimmedLine.equals("#")) {

					continue;
				}

				if (line.startsWith(_INDENT + "#")) {
					previousLineIsExample = true;

					String exampleProperty =
						StringUtil.replaceFirst(line, "#", StringPool.BLANK) +
							StringPool.NEW_LINE;

					sb.append(exampleProperty);
				}
			}
			else {
				if (!trimmedLine.startsWith("#")) {
					previousLineIsExample = false;

					continue;
				}

				String exampleProperty =
					line.replaceFirst("#", StringPool.BLANK) +
						StringPool.NEW_LINE;

				sb.append(exampleProperty);
			}
		}

		return sb.toString();
	}

	private List<PropertyComment> _extractPropertyComments(
		PropertiesSection section) {

		String sectionText = section.getText();

		String[] lines = StringUtil.split(sectionText, CharPool.NEW_LINE);

		List<PropertyComment> propertyComments =
			new ArrayList<PropertyComment>();

		StringBundler sb = new StringBundler();

		boolean previousLineIsPreformatted = false;

		// Add property comments as paragraphs. Stop on the first property
		// assignment.

		for (String line : lines) {
			line = StringUtil.trimTrailing(line);

			if (line.startsWith(_DOUBLE_INDENT + "#")) {
				break;
			}

			String trimmedLine = line.trim();

			if (trimmedLine.startsWith("# " + _INDENT)) {
				if (previousLineIsPreformatted) {
					sb.append(
						StringUtil.replaceFirst(
							trimmedLine, "#", StringPool.BLANK));
				}
				else {
					_addParagraph(propertyComments, sb.toString());

					sb = new StringBundler();

					sb.append(
						StringUtil.replaceFirst(
							trimmedLine, "#", StringPool.BLANK));
				}

				sb.append(StringPool.NEW_LINE);
				previousLineIsPreformatted = true;
			}
			else if (trimmedLine.startsWith("# ")) {
				if (previousLineIsPreformatted) {
					_addParagraph(propertyComments, sb.toString());

					sb = new StringBundler();

					trimmedLine = StringUtil.replaceFirst(
						trimmedLine, "#", StringPool.BLANK);

					sb.append(trimmedLine.trim());
				}
				else {
					if (sb.length() > 0) {
						sb.append(StringPool.SPACE);
					}

					line = StringUtil.replaceFirst(line, "#", StringPool.BLANK);

					sb.append(line.trim());
				}

				sb.append(StringPool.NEW_LINE);

				previousLineIsPreformatted = false;
			}
			else if (trimmedLine.startsWith("#") &&
					 (trimmedLine.length() < 2)) {

				_addParagraph(propertyComments, sb.toString());

				sb = new StringBundler();
			}
			else {
				_addParagraph(propertyComments, sb.toString());

				break;
			}
		}

		return propertyComments;
	}

	private String _extractTitle(PropertiesSection section) {
		String sectionText = section.getText();

		String[] lines = StringUtil.split(sectionText, CharPool.NEW_LINE);

		if ((lines == null) || (lines.length <= 1)) {
			return null;
		}

		String title = lines[1];

		title = StringUtil.replaceFirst(title, "##", StringPool.BLANK);

		return title.trim();
	}

	private static final String _DOUBLE_INDENT =
		PropertiesConverter._INDENT + PropertiesConverter._INDENT;

	private static final String _INDENT = StringPool.FOUR_SPACES;

	private static final String _TPL_PROPERTIES_HTML =
		"com/liferay/portal/tools/dependencies/properties_html.ftl";

	private static FileImpl _fileUtil = FileImpl.getInstance();

}