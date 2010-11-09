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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;

import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 */
public class PluginPackageHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error registering plugins for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering plugins for ", t);
		}
	}

	protected void destroyServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws Exception {

		ServiceComponentLocalServiceUtil.destroyServiceComponent(
			servletContext, classLoader);
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		if (servletContext.getResource(
				"/WEB-INF/liferay-theme-loader.xml") != null) {

			return;
		}

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageServletContext(servletContext);

		if (pluginPackage == null) {
			return;
		}

		pluginPackage.setContext(servletContextName);

		event.setPluginPackage(pluginPackage);

		PluginPackageUtil.registerInstalledPluginPackage(pluginPackage);

		ClassLoader portletClassLoader = event.getContextClassLoader();

		servletContext.setAttribute(
			PortletServlet.PORTLET_CLASS_LOADER, portletClassLoader);

		ServletContextPool.put(servletContextName, servletContext);

		initServiceComponent(servletContext, portletClassLoader);

		registerClpMessageListeners(servletContext, portletClassLoader);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" registered successfully. It's now ready to be used.");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageServletContext(servletContext);

		if (pluginPackage == null) {
			return;
		}

		event.setPluginPackage(pluginPackage);

		PluginPackageUtil.unregisterInstalledPluginPackage(pluginPackage);

		ServletContextPool.remove(servletContextName);

		destroyServiceComponent(servletContext, event.getContextClassLoader());

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" unregistered successfully");
		}
	}

	protected void initServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws Exception {

		Configuration serviceBuilderPropertiesConfiguration = null;

		try {
			serviceBuilderPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return;
		}

		Properties serviceBuilderProperties =
			serviceBuilderPropertiesConfiguration.getProperties();

		if (serviceBuilderProperties.size() == 0) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			serviceBuilderProperties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.date"));
		boolean buildAutoUpgrade = GetterUtil.getBoolean(
			serviceBuilderProperties.getProperty("build.auto.upgrade"), true);

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
			_log.debug("Build auto upgrade " + buildAutoUpgrade);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		ServiceComponentLocalServiceUtil.initServiceComponent(
			servletContext, classLoader, buildNamespace, buildNumber,
			buildDate, buildAutoUpgrade);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PluginPackageHotDeployListener.class);

}