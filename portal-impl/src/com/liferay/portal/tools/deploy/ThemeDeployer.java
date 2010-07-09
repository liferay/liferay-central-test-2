/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.TextFormatter;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeDeployer extends BaseDeployer {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		List<String> wars = new ArrayList<String>();
		List<String> jars = new ArrayList<String>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new ThemeDeployer(wars, jars);
	}

	protected ThemeDeployer() {
	}

	protected ThemeDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	protected void checkArguments() {
		super.checkArguments();

		if (Validator.isNull(themeTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.theme.taglib.dtd is not set");
		}

		if (Validator.isNull(utilTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.util.taglib.dtd is not set");
		}
	}

	protected String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		sb.append(extraContent);

		// ThemeContextListener

		sb.append("<listener>");
		sb.append("<listener-class>");
		sb.append("com.liferay.portal.kernel.servlet.ThemeContextListener");
		sb.append("</listener-class>");
		sb.append("</listener>");

		// Speed filters

		sb.append(getSpeedFiltersContent(srcFile));

		return sb.toString();
	}

	protected void processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (pluginPackage == null) {
			return;
		}

		Properties properties = getPluginPackageProperties(srcFile);

		if ((properties == null) || (properties.size() == 0)) {
			return;
		}

		String moduleGroupId = pluginPackage.getGroupId();
		String moduleArtifactId = pluginPackage.getArtifactId();
		String moduleVersion = pluginPackage.getVersion();

		String pluginName = pluginPackage.getName();
		String pluginType = pluginPackage.getTypes().get(0);
		String pluginTypeName = TextFormatter.format(
			pluginType, TextFormatter.J);

		if (!pluginType.equals(Plugin.TYPE_THEME)) {
			return;
		}

		String tags = getPluginPackageTagsXml(pluginPackage.getTags());
		String shortDescription = pluginPackage.getShortDescription();
		String longDescription = pluginPackage.getLongDescription();
		String changeLog = pluginPackage.getChangeLog();
		String pageURL = pluginPackage.getPageURL();
		String author = pluginPackage.getAuthor();
		String licenses = getPluginPackageLicensesXml(
			pluginPackage.getLicenses());
		String liferayVersions = getPluginPackageLiferayVersionsXml(
			pluginPackage.getLiferayVersions());

		int pos = moduleArtifactId.indexOf("-theme");

		String themeId = moduleArtifactId.substring(0, pos);
		String themeName = pluginName;

		Map<String, String> filterMap = new HashMap<String, String>();

		filterMap.put("module_group_id", moduleGroupId);
		filterMap.put("module_artifact_id", moduleArtifactId);
		filterMap.put("module_version", moduleVersion);

		filterMap.put("plugin_name", pluginName);
		filterMap.put("plugin_type", pluginType);
		filterMap.put("plugin_type_name", pluginTypeName);

		filterMap.put("tags", tags);
		filterMap.put("short_description", shortDescription);
		filterMap.put("long_description", longDescription);
		filterMap.put("change_log", changeLog);
		filterMap.put("page_url", pageURL);
		filterMap.put("author", author);
		filterMap.put("licenses", licenses);
		filterMap.put("liferay_versions", liferayVersions);

		filterMap.put("theme_id", themeId);
		filterMap.put("theme_name", themeName);
		filterMap.put(
			"theme_versions",
			StringUtil.replace(liferayVersions, "liferay-version", "version"));

		copyDependencyXml(
			"liferay-look-and-feel.xml", srcFile + "/WEB-INF", filterMap, true);
		copyDependencyXml(
			"liferay-plugin-package.xml", srcFile + "/WEB-INF", filterMap,
			true);
	}

}