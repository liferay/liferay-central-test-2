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

package com.liferay.portal.kernel.util;

import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ResourceBundleLoaderUtil {

	public static ResourceBundleLoader getPortalResourceBundleLoader() {
		return _portalResourceBundleLoader;
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByBundleSymbolicName(String bundleSymbolicName) {

		return _instance._resourceBundleLoaderByBundleSymbolicName.getService(
			bundleSymbolicName);
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByServletContextName(String servletContextName) {

		return _instance._resourceBundleLoaderByServletName.getService(
			servletContextName);
	}

	public static ResourceBundleLoader
		getResourceBundleLoaderByServletContextNameAndBaseName(
			String servletContextName, String baseName) {

		return _instance._resourceBundleLoaderByServletContextNameAndBaseName.
			getService(servletContextName + ":" + baseName);
	}

	public static void setPortalResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_portalResourceBundleLoader = resourceBundleLoader;
	}

	private ResourceBundleLoaderUtil() {
		_resourceBundleLoaderByBundleSymbolicName =
			ServiceTrackerCollections.openSingleValueMap(
				ResourceBundleLoader.class, "bundle.symbolic.name");
		_resourceBundleLoaderByServletContextNameAndBaseName =
			ServiceTrackerCollections.openSingleValueMap(
				ResourceBundleLoader.class,
				"(&(servlet.context.name=*)(baseName=*))",
				new ServiceReferenceMapper<String, ResourceBundleLoader>() {

					@Override
					public void map(
						ServiceReference<ResourceBundleLoader> serviceReference,
						Emitter<String> emitter) {

						Object servletContextName =
							serviceReference.getProperty(
								"servlet.context.name");
						Object baseName = serviceReference.getProperty(
							"baseName");

						emitter.emit(servletContextName + ":" + baseName);
					}

				});
		_resourceBundleLoaderByServletName =
			ServiceTrackerCollections.openSingleValueMap(
				ResourceBundleLoader.class, "servlet.context.name");
	}

	private static final ResourceBundleLoaderUtil _instance =
		new ResourceBundleLoaderUtil();

	private static ResourceBundleLoader _portalResourceBundleLoader;

	private final ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaderByBundleSymbolicName;
	private final ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaderByServletContextNameAndBaseName;
	private final ServiceTrackerMap<String, ResourceBundleLoader>
		_resourceBundleLoaderByServletName;

}