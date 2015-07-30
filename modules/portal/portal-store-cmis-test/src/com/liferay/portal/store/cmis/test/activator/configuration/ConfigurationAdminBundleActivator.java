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

package com.liferay.portal.store.cmis.test.activator.configuration;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.Store;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Manuel de la Peña
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	public static final String STORE_CMIS_CREDENTIALS_PASSWORD = "test";

	public static final String STORE_CMIS_CREDENTIALS_USERNAME = "test";

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		String cmisStoreClassName = "com.liferay.portal.store.cmis.CMISStore";

		if (!dlStoreImpl.equals(cmisStoreClassName)) {
			return;
		}

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			_cmisConfiguration = configurationAdmin.getConfiguration(
				"com.liferay.portal.store.cmis.configuration." +
					"CMISConfiguration", null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put(
				"credentialsPassword", STORE_CMIS_CREDENTIALS_PASSWORD);
			properties.put(
				"credentialsUsername", STORE_CMIS_CREDENTIALS_USERNAME);
			properties.put(
				"repositoryUrl",
				"http://alfresco.liferay.org.es/alfresco/service/api/cmis");
			properties.put("systemRootDir", "testStore");

			_cmisConfiguration.update(properties);

			Filter filter = bundleContext.createFilter(
				"(&(objectClass=" + Store.class.getName() +
					")(store.type=com.liferay.portal.store.cmis.CMISStore))");

			ServiceTracker<?, ?> serviceTracker = new ServiceTracker<>(
				bundleContext, filter, null);

			serviceTracker.open();

			Object cmisStore = serviceTracker.waitForService(10000);

			serviceTracker.close();

			if (cmisStore == null) {
				_cmisConfiguration.delete();

				throw new IllegalStateException(
					"CMISStore was not registered within 10 seconds");
			}
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_cmisConfiguration.delete();
		}
		catch (Exception e) {
		}
	}

	private Configuration _cmisConfiguration;

}