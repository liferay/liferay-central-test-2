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

package com.liferay.portal.service.configuration.configurator.impl;

import com.liferay.portal.cache.configurator.PortalCacheConfigurator;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ServiceComponentLocalService;
import com.liferay.portal.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.service.configuration.configurator.ServiceConfigurator;
import com.liferay.util.log4j.Log4JUtil;

import java.net.URL;

import java.util.List;
import java.util.Properties;

/**
 * @author Miguel Pastor
 */
public class ServiceConfiguratorImpl implements ServiceConfigurator {

	@Override
	public void destroyServices(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		destroyServiceComponent(serviceComponentConfiguration, classLoader);
	}

	@Override
	public void initServices(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		initLog4J(classLoader);

		initServiceComponent(serviceComponentConfiguration, classLoader);

		reconfigureCaches(classLoader);

		readResourceActions(classLoader);
	}

	public void setPortalCacheConfigurator(
		PortalCacheConfigurator portalCacheConfigurator) {

		_portalCacheConfigurator = portalCacheConfigurator;
	}

	public void setServiceComponentLocalService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	protected void destroyServiceComponent(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader)
		throws Exception {

		_serviceComponentLocalService.destroyServiceComponent(
			serviceComponentConfiguration, classLoader);
	}

	protected URL getPortalCacheConfigurationURL(
		Configuration configuration, ClassLoader classLoader,
		String configLocation) {

		String cacheConfigurationLocation = configuration.get(configLocation);

		if (Validator.isNull(cacheConfigurationLocation)) {
			return null;
		}

		return classLoader.getResource(cacheConfigurationLocation);
	}

	protected void initLog4J(ClassLoader classLoader) {
		Log4JUtil.configureLog4J(
			classLoader.getResource("META-INF/portal-log4j.xml"));
	}

	protected void initServiceComponent(
		ServiceComponentConfiguration serviceComponentConfiguration,
		ClassLoader classLoader) {

		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
				classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return;
		}

		Properties properties = configuration.getProperties();

		if (properties.isEmpty()) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			properties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			properties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			properties.getProperty("build.date"));
		boolean buildAutoUpgrade = GetterUtil.getBoolean(
			properties.getProperty("build.auto.upgrade"), true);

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
			_log.debug("Build auto upgrade " + buildAutoUpgrade);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		try {
			_serviceComponentLocalService.initServiceComponent(
				serviceComponentConfiguration,
				classLoader, buildNamespace, buildNumber, buildDate,
				buildAutoUpgrade);
		}
		catch (PortalException pe) {
			_log.error("Unable to initialize service component", pe);
		}
	}

	protected void readResourceActions(ClassLoader classLoader) {
		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			classLoader, "portlet");

		String[] resourceActionsConfigs = StringUtil.split(
			configuration.get(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

		for (String resourceActionsConfig : resourceActionsConfigs) {
			try {
				ResourceActionsUtil.read(
					null, classLoader, resourceActionsConfig);
			}
			catch (Exception e) {
				_log.error(
					"Unable to read resource actions config in " +
						resourceActionsConfig,
					e);
			}
		}

		String[] portletIds = StringUtil.split(
			configuration.get("service.configurator.portlet.ids"));

		for (String portletId : portletIds) {
			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(portletId);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	protected void reconfigureCaches(ClassLoader classLoader) throws Exception {
		Configuration configuration = null;

		try {
			configuration = ConfigurationFactoryUtil.getConfiguration(
				classLoader, "portlet");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read portlet.properties");
			}

			return;
		}

		_portalCacheConfigurator.reconfigureCaches(
			classLoader,
			getPortalCacheConfigurationURL(
				configuration, classLoader,
				PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION));

		_portalCacheConfigurator.reconfigureCaches(
			classLoader,
			getPortalCacheConfigurationURL(
				configuration, classLoader,
				PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION));

		_portalCacheConfigurator.reconfigureHibernateCache(
			getPortalCacheConfigurationURL(
				configuration, classLoader,
				PropsKeys.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME));
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServiceConfiguratorImpl.class);

	private PortalCacheConfigurator _portalCacheConfigurator;
	private ServiceComponentLocalService _serviceComponentLocalService;

}