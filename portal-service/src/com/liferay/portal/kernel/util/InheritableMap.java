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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="InheritableMap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 */
public class InheritableMap<K,V> extends HashMap<K,V> {

	public InheritableMap() {
		super();
	}

	public InheritableMap(Map<? extends K,? extends V> map) {
		super(map);
	}

	public V get(Object key) {
		if (super.containsKey(key)) {
			return super.get(key);
		}
		else {
			return _childMap.get(key);
		}
	}

	public Map<K, V> getChildMap() {
		return _childMap;
	}

	public V put(K key, V value) {
		return _childMap.put(key, value);
	}

	private Map<K, V> _childMap = new HashMap<K,V>();

}