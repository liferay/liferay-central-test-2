/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginsSummaryBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		File pluginsDir = new File(System.getProperty("plugins.dir"));

		new PluginsSummaryBuilder(pluginsDir);
	}

	public PluginsSummaryBuilder(File pluginsDir) {
		try {
			_pluginsDir = pluginsDir;

			_createPluginsSummary();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createPluginsSummary() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("<plugins-summary>\n");

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_pluginsDir);
		directoryScanner.setExcludes(
			new String[] {"**\\tmp\\**", "**\\tools\\**"});
		directoryScanner.setIncludes(
			new String[] {
				"**\\liferay-plugin-package.properties",
				"**\\liferay-plugin-package.xml"
			});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		Arrays.sort(fileNames);

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			_createPluginSummary(fileName, sb);
		}

		for (String author : _distinctAuthors) {
			sb.append("\t<author>");
			sb.append(author);
			sb.append("</author>\n");
		}

		for (String license : _distinctLicenses) {
			sb.append("\t<license>");
			sb.append(license);
			sb.append("</license>\n");
		}

		sb.append("</plugins-summary>");

		FileUtil.write(_pluginsDir + "/summary.xml", sb.toString());
	}

	private void _createPluginSummary(String fileName, StringBundler sb)
		throws Exception {

		String content = FileUtil.read(fileName);

		int x = fileName.indexOf(StringPool.SLASH);

		String type = fileName.substring(0, x);

		if (type.endsWith("s")) {
			type = type.substring(0, type.length() - 1);
		}

		x = fileName.indexOf(StringPool.SLASH, x) + 1;

		int y = fileName.indexOf(StringPool.SLASH, x);

		String artifactId = fileName.substring(x, y);

		String name = StringPool.BLANK;
		String tags = StringPool.BLANK;
		String shortDescription = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		String pageURL = StringPool.BLANK;
		String author = StringPool.BLANK;
		String licenses = StringPool.BLANK;

		if (fileName.endsWith(".properties")) {
			Properties properties = PropertiesUtil.load(content);

			name = _readProperty(properties, "name");
			tags = _readProperty(properties, "tags");
			shortDescription = _readProperty(properties, "short-description");
			changeLog = _readProperty(properties, "change-log");
			pageURL = _readProperty(properties, "page-url");
			author = _readProperty(properties, "author");
			licenses = _readProperty(properties, "licenses");
		}
		else {
			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			name = rootElement.elementText("name");
			tags = _readList(rootElement.element("tags"), "tag");
			shortDescription = rootElement.elementText("short-description");
			changeLog = rootElement.elementText("change-log");
			pageURL = rootElement.elementText("page-url");
			author = rootElement.elementText("author");
			licenses = _readList(rootElement.element("licenses"), "license");
		}

		_distinctAuthors.add(author);
		_distinctLicenses.add(licenses);

		sb.append("\t<plugin>\n");

		_writeElement(sb, "artifact-id", artifactId, 2);
		_writeElement(sb, "name", name, 2);
		_writeElement(sb, "type", type, 2);
		_writeElement(sb, "tags", tags, 2);
		_writeElement(sb, "short-description", shortDescription, 2);
		_writeElement(sb, "change-log", changeLog, 2);
		_writeElement(sb, "page-url", pageURL, 2);
		_writeElement(sb, "author", author, 2);
		_writeElement(sb, "licenses", licenses, 2);

		sb.append("\t\t<releng>\n");
		sb.append(_readReleng(fileName));
		sb.append("\t\t</releng>\n");
		sb.append("\t</plugin>\n");
	}

	private String _readList(Element parentElement, String name) {
		if (parentElement == null) {
			return StringPool.BLANK;
		}

		List<Element> elements = parentElement.elements(name);

		if (elements.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			parentElement.elements(name).size() * 2 - 1);

		Iterator<Element> itr = elements.iterator();

		while (itr.hasNext()) {
			Element element = itr.next();

			String text = element.getText().trim();

			sb.append(text);

			if (itr.hasNext()) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	private String _readProperty(Properties properties, String key) {
		return GetterUtil.getString(properties.getProperty(key));
	}

	private String _readReleng(String fileName) throws Exception {
		int x = fileName.indexOf("WEB-INF");

		String relengPropertiesFileName =
			_pluginsDir + StringPool.SLASH + fileName.substring(0, x + 8) +
				"liferay-releng.properties";

		Properties relengProperties = null;

		if (FileUtil.exists(relengPropertiesFileName)) {
			String relengPropertiesContent = FileUtil.read(
				relengPropertiesFileName);

			relengProperties = PropertiesUtil.load(relengPropertiesContent);
		}
		else {
			relengProperties = new Properties();
		}

		String relengPropertiesContent = _updateRelengPropertiesFile(
			relengPropertiesFileName, relengProperties);

		relengProperties = PropertiesUtil.load(relengPropertiesContent);

		StringBundler sb = new StringBundler();

		_writeElement(sb, "bundle", relengProperties, 3);
		_writeElement(sb, "labs", relengProperties, 3);
		_writeElement(sb, "marketplace", relengProperties, 3);
		_writeElement(sb, "parent-app", relengProperties, 3);
		_writeElement(sb, "public", relengProperties, 3);
		_writeElement(sb, "standalone-app", relengProperties, 3);
		_writeElement(sb, "supported", relengProperties, 3);

		return sb.toString();
	}

	private String _updateRelengPropertiesFile(
			String relengPropertiesFileName, Properties relengProperties)
		throws Exception {

		StringBundler sb = new StringBundler();

		_writeProperty(sb, relengProperties, "bundle", "false");
		_writeProperty(sb, relengProperties, "labs", "true");
		_writeProperty(sb, relengProperties, "marketplace", "false");
		_writeProperty(sb, relengProperties, "parent-app", "");
		_writeProperty(sb, relengProperties, "public", "true");
		_writeProperty(sb, relengProperties, "standalone-app", "true");
		_writeProperty(sb, relengProperties, "supported", "false");

		String relengPropertiesContent = sb.toString();

		FileUtil.write(relengPropertiesFileName, relengPropertiesContent);

		return relengPropertiesContent;
	}

	private void _writeElement(
		StringBundler sb, String name, Properties properties, int tabsCount) {

		_writeElement(sb, name, _readProperty(properties, name), tabsCount);
	}

	private void _writeElement(
		StringBundler sb, String name, String value, int tabsCount) {

		for (int i = 0; i < tabsCount; i++) {
			sb.append("\t");
		}

		sb.append("<");
		sb.append(name);
		sb.append(">");
		sb.append(value);
		sb.append("</");
		sb.append(name);
		sb.append(">\n");
	}

	private void _writeProperty(
		StringBundler sb, Properties properties, String key,
		String defaultValue) {

		String value = GetterUtil.getString(
			properties.getProperty(key), defaultValue);

		if (sb.length() > 0) {
			sb.append(StringPool.NEW_LINE);
		}

		sb.append(key);
		sb.append(StringPool.EQUAL);
		sb.append(value);
	}

	private Set<String> _distinctAuthors = new TreeSet<String>();
	private Set<String> _distinctLicenses = new TreeSet<String>();
	private File _pluginsDir;

}