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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public interface CallbackFactory<T extends PortalCacheManager<?, ?>> {

	public PortalCacheBootstrapLoader createPortalCacheBootstrapLoader(
		Properties properties);

	public <K extends Serializable, V> PortalCacheListener<K, V>
		createPortalCacheListener(Properties properties);

	public PortalCacheManagerListener createPortalCacheManagerListener(
		T portalCacheManager, Properties properties);

}