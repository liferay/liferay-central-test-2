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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="WAIAutoDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class WAIAutoDeployer extends PortletAutoDeployer {

	protected WAIAutoDeployer() throws AutoDeployException {
		try {
			addRequiredJar(jars, "portals-bridges.jar");
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	protected void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		String portletName = displayName;

		if (pluginPackage != null) {
			portletName = pluginPackage.getName();
		}

		Map<String, String> filterMap = new HashMap<String, String>();

		filterMap.put("portlet_name", displayName);
		filterMap.put("portlet_title", portletName);
		filterMap.put("restore_current_view", "false");

		if (pluginPackage != null) {
			Properties settings = pluginPackage.getDeploymentSettings();

			filterMap.put(
				"portlet_class",
				settings.getProperty(
					"wai.portlet", "com.liferay.util.bridges.wai.WAIPortlet"));

			filterMap.put(
				"friendly_url_mapper_class",
				settings.getProperty(
					"wai.friendly.url.mapper",
					"com.liferay.util.bridges.wai.WAIFriendlyURLMapper"));
		}
		else {
			filterMap.put(
				"portlet_class", "com.liferay.util.bridges.wai.WAIPortlet");

			filterMap.put(
				"friendly_url_mapper_class",
				"com.liferay.util.bridges.wai.WAIFriendlyURLMapper");
		}

		_setInitParams(filterMap, pluginPackage);

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"normal_window_state.jsp", srcFile + "/WEB-INF/jsp/liferay/wai");
		copyDependencyXml("iframe.jsp", srcFile + "/WEB-INF/jsp/liferay/wai");

	}

	private void _setInitParams(
		Map<String, String> filterMap, PluginPackage pluginPackage) {

		for (int i = 0; i < _INIT_PARAM_NAMES.length; i++) {
			String name = _INIT_PARAM_NAMES[i];

			String value = null;

			if (pluginPackage != null) {
				pluginPackage.getDeploymentSettings().getProperty(name);
			}

			if (Validator.isNull(value)) {
				value = _INIT_PARAM_DEFAULT_VALUES[i];
			}

			filterMap.put("init_param_name_" + i, name);
			filterMap.put("init_param_value_" + i, value);
		}
	}

	private static String[] _INIT_PARAM_NAMES = new String[] {
		"wai.connector", "wai.connector.iframe.height.extra"
	};

	private static String[] _INIT_PARAM_DEFAULT_VALUES = new String[] {
		"iframe", "40"
	};

}