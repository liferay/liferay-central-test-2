/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate.region;

import com.liferay.portal.cache.ehcache.ModifiableEhcacheWrapper;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;

import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.regions.EhcacheDataRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.Region;

/**
 * @author Edward Han
 */
public abstract class BaseRegionWrapper implements Region, CacheRegistryItem {

	public BaseRegionWrapper(EhcacheDataRegion ehcacheDataRegion) {

		_ehcacheDataRegion = ehcacheDataRegion;

		Ehcache cache = _ehcacheDataRegion.getEhcache();

		if (cache instanceof ModifiableEhcacheWrapper) {
			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper) cache;

			modifiableEhcacheWrapper.addReference();
		}

		CacheRegistryUtil.register(this);
	}

	public boolean contains(Object o) {
		return _ehcacheDataRegion.contains(o);
	}

	public void destroy() throws CacheException {
		Ehcache cache = getEhcacheDataRegion().getEhcache();

		if (cache instanceof ModifiableEhcacheWrapper) {
			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper) cache;

			modifiableEhcacheWrapper.removeReference();

			if (modifiableEhcacheWrapper.getActiveReferenceCount() == 0) {
				doDestroy();
			}
		}
		else {
			doDestroy();
		}
	}

	public String getName() {
		return _ehcacheDataRegion.getName();
	}

	public long getElementCountInMemory() {
		return _ehcacheDataRegion.getElementCountInMemory();
	}

	public long getElementCountOnDisk() {
		return _ehcacheDataRegion.getElementCountOnDisk();
	}

	public String getRegistryName() {
		return getName();
	}

	public long getSizeInMemory() {
		return _ehcacheDataRegion.getSizeInMemory();
	}

	public int getTimeout() {
		return _ehcacheDataRegion.getTimeout();
	}

	public long nextTimestamp() {
		return _ehcacheDataRegion.nextTimestamp();
	}

	public Map toMap() {
		return _ehcacheDataRegion.toMap();
	}

	public String toString() {
		return _ehcacheDataRegion.toString();
	}

	protected void doDestroy() {
		_ehcacheDataRegion.destroy();

		CacheRegistryUtil.unregister(getRegistryName());
	}

	protected EhcacheDataRegion getEhcacheDataRegion() {
		return _ehcacheDataRegion;
	}

	private EhcacheDataRegion _ehcacheDataRegion;
}