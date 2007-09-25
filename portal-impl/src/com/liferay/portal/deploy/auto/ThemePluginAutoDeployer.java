/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.util.FileUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="ThemePluginAutoDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ThemePluginAutoDeployer extends ThemeAutoDeployer {

	protected void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		Map filterMap = new HashMap();

		String pluginName = displayName;

		int pos = pluginName.indexOf("-theme");

		String themeId = pluginName.substring(0, pos);
		String moduleArtifactId = themeId + "-theme";
		String moduleVersion = pluginName.substring(pos + 7);

		String propsString = FileUtil.read(
			srcFile + "/WEB-INF/liferay-theme-plugin.properties");

		Properties props = PropertiesUtil.load(propsString);

		String moduleGroupId = props.getProperty("module-group-id");
		String themeName = props.getProperty("name");

		String[] tags = StringUtil.split(props.getProperty("tags"));

		StringMaker sm = new StringMaker();

		for (int i = 0; i < tags.length; i++) {
			if (i == 0) {
				sm.append("\r\n");
			}

			sm.append("\t\t<tag>");
			sm.append(tags[i].trim());
			sm.append("</tag>\r\n");

			if ((i + 1) == tags.length) {
				sm.append("\t");
			}
		}

		String tagsString = sm.toString();

		String shortDescription = props.getProperty("short-description");
		String changeLog = props.getProperty("change-log");
		String pageURL = props.getProperty("page-url");
		String author = props.getProperty("author");

		String[] licenses = StringUtil.split(props.getProperty("licenses"));

		sm = new StringMaker();

		for (int i = 0; i < licenses.length; i++) {
			if (i == 0) {
				sm.append("\r\n");
			}

			sm.append("\t\t<license>");
			sm.append(licenses[i].trim());
			sm.append("</license>\r\n");

			if ((i + 1) == licenses.length) {
				sm.append("\t");
			}
		}

		String licensesString = sm.toString();

		filterMap.put("liferay_version", ReleaseInfo.getVersion());
		filterMap.put("module_group_id", moduleGroupId);
		filterMap.put("module_artifact_id", moduleArtifactId);
		filterMap.put("module_version", moduleVersion);
		filterMap.put("theme_id", themeId);
		filterMap.put("theme_name", themeName);
		filterMap.put("tags", tagsString);
		filterMap.put("short_description", shortDescription);
		filterMap.put("change_log", changeLog);
		filterMap.put("page_url", pageURL);
		filterMap.put("author", author);
		filterMap.put("licenses", licensesString);

		copyDependencyXml(
			"liferay-look-and-feel.xml", srcFile + "/WEB-INF", filterMap, true);
		copyDependencyXml(
			"liferay-plugin-package.xml", srcFile + "/WEB-INF", filterMap,
			true);
	}

}