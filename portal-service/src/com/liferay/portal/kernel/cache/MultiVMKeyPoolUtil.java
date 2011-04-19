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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

/**
 * @author Edward Han
 */
public class MultiVMKeyPoolUtil {

	public static void clear() {
		_multiVMPool.clear();
	}

	public static void clear(String name) {
		_multiVMPool.clear(name);
	}

	public static Object get(String name, String key) {
		return _multiVMPool.get(name, key);
	}

	public static PortalCache getCache(String name) {
		return _multiVMPool.getCache(name);
	}

	public static PortalCache getCache(String name, boolean blocking) {
		return _multiVMPool.getCache(name, blocking);
	}

	public static void put(String name, String key, Object obj) {
		_multiVMPool.put(name, key, obj);
	}

	public static void put(String name, String key, Serializable obj) {
		_multiVMPool.put(name, key, obj);
	}

	public static void remove(String name, String key) {
		_multiVMPool.remove(name, key);
	}

	public static void removeCache(String name) {
		_multiVMPool.removeCache(name);
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private static MultiVMPool _multiVMPool;
}