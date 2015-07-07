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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.SingleVMPool;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@Component(immediate = true, service = SingleVMPool.class)
public class SingleVMPoolImpl implements SingleVMPool {

	@Override
	public void clear() {
		_portalCacheManager.clearAll();
	}

	@Override
	public PortalCache<? extends Serializable, ?> getCache(
		String portalCacheName) {

		return _portalCacheManager.getCache(portalCacheName);
	}

	@Override
	public PortalCache<? extends Serializable, ?> getCache(
		String portalCacheName, boolean blocking) {

		return _portalCacheManager.getCache(portalCacheName, blocking);
	}

	@Override
	public PortalCacheManager<? extends Serializable, ?> getCacheManager() {
		return _portalCacheManager;
	}

	@Override
	public void removeCache(String portalCacheName) {
		_portalCacheManager.removeCache(portalCacheName);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_portalCacheManager.clearAll();
	}

	@Deactivate
	protected void deactivate() {
		_portalCacheManager.clearAll();
	}

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.SINGLE_VM + ")",
		unbind = "-"
	)
	protected void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager) {

		_portalCacheManager = portalCacheManager;
	}

	private PortalCacheManager<? extends Serializable, ?> _portalCacheManager;

}