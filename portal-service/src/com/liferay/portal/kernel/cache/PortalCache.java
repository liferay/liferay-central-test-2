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

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortalCache {

	public void destroy();

	public Collection<Object> get(Collection<String> keys);

	public Object get(String key);

	public void put(String key, Object obj);

	public void put(String key, Object obj, int timeToLive);

	public void put(String key, Serializable obj);

	public void put(String key, Serializable obj, int timeToLive);

	public void remove(String key);

	public void removeAll();

	public void setDebug(boolean debug);

}