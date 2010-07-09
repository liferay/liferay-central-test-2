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

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectValuePair<K, V> implements Serializable {

	public ObjectValuePair() {
	}

	public ObjectValuePair(K key, V value) {
		_key = key;
		_value = value;
	}

	public K getKey() {
		return _key;
	}

	public void setKey(K key) {
		_key = key;
	}

	public V getValue() {
		return _value;
	}

	public void setValue(V value) {
		_value = value;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ObjectValuePair<K, V> ovp = (ObjectValuePair<K, V>)obj;

		K key = ovp.getKey();

		if (_key.equals(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return _key.hashCode();
	}

	private K _key;
	private V _value;

}