/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="MVCPortletAutoDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MVCPortletAutoDeployer extends PortletAutoDeployer {

	protected void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		Map<String, String> filterMap = new HashMap<String, String>();

		String pluginName = displayName;

		if (pluginPackage != null) {
			pluginName = pluginPackage.getName();
		}

		filterMap.put(
			"portlet_class", "com.liferay.util.bridges.mvc.MVCPortlet");
		filterMap.put("portlet_name", pluginName);
		filterMap.put("portlet_title", pluginName);
		filterMap.put("restore_current_view", "false");
		filterMap.put("friendly_url_mapper_class", "");
		filterMap.put("init_param_name_0", "view-jsp");
		filterMap.put("init_param_value_0", "/index_mvc.jsp");

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"portlet.xml", srcFile + "/WEB-INF", filterMap);
	}

}