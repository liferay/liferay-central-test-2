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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Young
 * @author Connor McKay
 */
public class InheritableMap<K, V> extends HashMap<K, V> {

	public InheritableMap() {
		super();
	}

	public InheritableMap(Map<? extends K, ? extends V> map) {
		super(map);
	}

	public void clear() {
		super.clear();

		_parentMap = null;
	}

	public boolean containsKey(Object key) {
		if (_parentMap != null && _parentMap.containsKey(key)) {
		 	return true;
		}
		else {
			return super.containsKey(key);
		}
	}

	public boolean containsValue(Object value) {
		if ((_parentMap != null) && _parentMap.containsValue(value)) {
			return true;
		}
		else {
			return super.containsValue(value);
		}
	}

	public V get(Object key) {
	 	if (super.containsKey(key)) {
			return super.get(key);
		}
		else if (_parentMap != null) {
			return _parentMap.get(key);
		}

		return null;
	}

	public Map<K, V> getParentMap() {
		return _parentMap;
	}

	public V remove(Object key) {
		if (super.containsKey(key)) {
			return super.remove(key);
		}
		else if (_parentMap != null) {
			return _parentMap.remove(key);
		}

		return null;
	}

	public void setParentMap(Map<? extends K, ? extends V> parentMap) {
		_parentMap = (Map<K, V>)parentMap;
	}

	private Map<K, V> _parentMap;

}