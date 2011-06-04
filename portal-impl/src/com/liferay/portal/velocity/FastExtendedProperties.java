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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.ExtendedProperties;

/**
 * @author Shuyang Zhou
 */
public class FastExtendedProperties extends ExtendedProperties {

	public FastExtendedProperties() {
	}

	public FastExtendedProperties(ExtendedProperties extendedProperties)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		extendedProperties.save(unsyncByteArrayOutputStream, StringPool.BLANK);

		InputStream inputStream = new UnsyncByteArrayInputStream(
			unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
			unsyncByteArrayOutputStream.size());

		load(inputStream);
	}

	public void clear() {
		_map.clear();
	}

	public Object clone() {
		FastExtendedProperties fastExtendedProperties =
			(FastExtendedProperties)super.clone();

		fastExtendedProperties._map = new ConcurrentHashMap<Object, Object>(
			_map);

		return fastExtendedProperties;
	}

	public boolean contains(Object value) {
		return _map.containsKey(value);
	}

	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration elements() {
		return Collections.enumeration(_map.values());
	}

	@SuppressWarnings("rawtypes")
	public Set entrySet() {
		return _map.entrySet();
	}

	public boolean equals(Object o) {
		return _map.equals(o);
	}

	public Object get(Object key) {
		return _map.get(key);
	}

	public int hashCode() {
		return _map.hashCode();
	}

	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	public Enumeration keys() {
		return Collections.enumeration(_map.keySet());
	}

	@SuppressWarnings("rawtypes")
	public Set keySet() {
		return _map.keySet();
	}

	public Object put(Object key, Object value) {
		return _map.put(key, value);
	}

	@SuppressWarnings("rawtypes")
	public void putAll(Map t) {
		_map.putAll(t);
	}

	public Object remove(Object key) {
		return _map.remove(key);
	}

	public int size() {
		return _map.size();
	}

	public String toString() {
		return _map.toString();
	}

	@SuppressWarnings("rawtypes")
	public Collection values() {
		return _map.values();
	}

	private Map<Object, Object> _map = new ConcurrentHashMap<Object, Object>();

}