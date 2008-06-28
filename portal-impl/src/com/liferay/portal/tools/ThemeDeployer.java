/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Plugin;
import com.liferay.util.TextFormatter;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="ThemeDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ThemeDeployer extends BaseDeployer {

	public static void main(String[] args) {
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

		StringBuilder sb = new StringBuilder();

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		sb.append(extraContent);

		// HeaderFilter

		sb.append("<filter>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<filter-class>");
		sb.append("com.liferay.portal.kernel.servlet.PortalClassLoaderFilter");
		sb.append("</filter-class>");
		sb.append("<init-param>");
		sb.append("<param-name>filter-class</param-name>");
		sb.append("<param-value>");
		sb.append("com.liferay.portal.servlet.filters.header.HeaderFilter");
		sb.append("</param-value>");
		sb.append("</init-param>");
		sb.append("<init-param>");
		sb.append("<param-name>Cache-Control</param-name>");
		sb.append("<param-value>max-age=172801, public</param-value>");
		sb.append("</init-param>");
		sb.append("<init-param>");
		sb.append("<param-name>Expires</param-name>");
		sb.append("<param-value>172801</param-value>");
		sb.append("</init-param>");
		sb.append("</filter>");

		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.css</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.gif</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.html</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.jpg</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.js</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Header Filter</filter-name>");
		sb.append("<url-pattern>*.png</url-pattern>");
		sb.append("</filter-mapping>");

		// CompressionFilter

		sb.append("<filter>");
		sb.append("<filter-name>Compression Filter</filter-name>");
		sb.append("<filter-class>");
		sb.append("com.liferay.portal.kernel.servlet.PortalClassLoaderFilter");
		sb.append("</filter-class>");
		sb.append("<init-param>");
		sb.append("<param-name>filter-class</param-name>");
		sb.append("<param-value>");
		sb.append(
			"com.liferay.portal.servlet.filters.compression.CompressionFilter");
		sb.append("</param-value>");
		sb.append("</init-param>");
		sb.append("</filter>");

		sb.append("<filter-mapping>");
		sb.append("<filter-name>Compression Filter</filter-name>");
		sb.append("<url-pattern>*.css</url-pattern>");
		sb.append("</filter-mapping>");
		sb.append("<filter-mapping>");
		sb.append("<filter-name>Compression Filter</filter-name>");
		sb.append("<url-pattern>*.js</url-pattern>");
		sb.append("</filter-mapping>");

		// VelocityFilter

		sb.append("<filter>");
		sb.append("<filter-name>Velocity Filter</filter-name>");
		sb.append("<filter-class>");
		sb.append("com.liferay.portal.kernel.servlet.PortalClassLoaderFilter");
		sb.append("</filter-class>");
		sb.append("<init-param>");
		sb.append("<param-name>filter-class</param-name>");
		sb.append("<param-value>");
		sb.append("com.liferay.portal.servlet.filters.velocity.VelocityFilter");
		sb.append("</param-value>");
		sb.append("</init-param>");
		sb.append("<init-param>");
		sb.append("<param-name>pattern</param-name>");
		sb.append("<param-value>(.+)/css/main.css(.+)</param-value>");
		sb.append("</init-param>");
		sb.append("</filter>");

		/*sb.append("<filter-mapping>");
		sb.append("<filter-name>Velocity Filter</filter-name>");
		sb.append("<url-pattern>*.css</url-pattern>");

		if (webXmlVersion > 2.3) {
			sb.append("<dispatcher>FORWARD</dispatcher>");
			sb.append("<dispatcher>INCLUDE</dispatcher>");
			sb.append("<dispatcher>REQUEST</dispatcher>");
		}

		sb.append("</filter-mapping>");*/

		// ThemeContextListener

		sb.append("<listener>");
		sb.append("<listener-class>");
		sb.append("com.liferay.portal.kernel.servlet.ThemeContextListener");
		sb.append("</listener-class>");
		sb.append("</listener>");

		return sb.toString();
	}

	protected void processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (pluginPackage == null) {
			return;
		}

		Properties props = getPluginPackageProperties(srcFile);

		if ((props == null) || (props.size() == 0)) {
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