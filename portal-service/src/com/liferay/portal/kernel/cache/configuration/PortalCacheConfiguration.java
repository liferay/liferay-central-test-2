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

package com.liferay.portal.kernel.cache.configuration;

import com.liferay.portal.kernel.cache.PortalCacheListenerScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class PortalCacheConfiguration {

	public static final String DEFAULT_PORTAL_CACHE_NAME =
		"DEFAULT_PORTAL_CACHE_NAME";

	public PortalCacheConfiguration(
		String portalCacheName,
		Map<CallbackConfiguration, PortalCacheListenerScope>
			cacheListenerConfigurations,
		CallbackConfiguration bootstrapLoaderConfiguration) {

		if (portalCacheName == null) {
			throw new NullPointerException("Portal cache name is null");
		}

		_portalCacheName = portalCacheName;

		if (cacheListenerConfigurations == null) {
			_cacheListenerConfigurations = Collections.emptyMap();
		}
		else {
			_cacheListenerConfigurations = new HashMap<>(
				cacheListenerConfigurations);
		}

		_bootstrapLoaderConfiguration = bootstrapLoaderConfiguration;
	}

	public CallbackConfiguration getBootstrapLoaderConfiguration() {
		return _bootstrapLoaderConfiguration;
	}

	public Map<CallbackConfiguration, PortalCacheListenerScope>
		getCacheListenerConfigurations() {

		return Collections.unmodifiableMap(_cacheListenerConfigurations);
	}

	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new PortalCacheConfiguration(
			portalCacheName, _cacheListenerConfigurations,
			_bootstrapLoaderConfiguration);
	}

	private final CallbackConfiguration _bootstrapLoaderConfiguration;
	private final Map<CallbackConfiguration, PortalCacheListenerScope>
		_cacheListenerConfigurations;
	private final String _portalCacheName;

}