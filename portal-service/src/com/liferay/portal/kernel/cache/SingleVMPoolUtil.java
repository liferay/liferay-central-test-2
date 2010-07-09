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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class SingleVMPoolUtil {

	public static void clear() {
		getSingleVMPool().clear();
	}

	public static void clear(String name) {
		getSingleVMPool().clear(name);
	}

	public static Object get(String name, String key) {
		return getSingleVMPool().get(name, key);
	}

	/**
	 * @deprecated
	 */
	public static Object get(PortalCache portalCache, String key) {
		return getSingleVMPool().get(portalCache, key);
	}

	public static PortalCache getCache(String name) {
		return getSingleVMPool().getCache(name);
	}

	public static PortalCache getCache(String name, boolean blocking) {
		return getSingleVMPool().getCache(name, blocking);
	}

	public static SingleVMPool getSingleVMPool() {
		return _singleVMPool;
	}

	public static void put(String name, String key, Object obj) {
		getSingleVMPool().put(name, key, obj);
	}

	/**
	 * @deprecated
	 */
	public static void put(PortalCache portalCache, String key, Object obj) {
		getSingleVMPool().put(portalCache, key, obj);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Object obj, int timeToLive) {

		getSingleVMPool().put(portalCache, key, obj, timeToLive);
	}

	public static void put(String name, String key, Serializable obj) {
		getSingleVMPool().put(name, key, obj);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Serializable obj) {

		getSingleVMPool().put(portalCache, key, obj);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Serializable obj, int timeToLive) {

		getSingleVMPool().put(portalCache, key, obj, timeToLive);
	}

	public static void remove(String name, String key) {
		getSingleVMPool().remove(name, key);
	}

	/**
	 * @deprecated
	 */
	public static void remove(PortalCache portalCache, String key) {
		getSingleVMPool().remove(portalCache, key);
	}

	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_singleVMPool = singleVMPool;
	}

	private static SingleVMPool _singleVMPool;

}