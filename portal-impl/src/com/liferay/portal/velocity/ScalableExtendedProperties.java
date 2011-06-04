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

import java.io.IOException;

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
public class ScalableExtendedProperties extends ExtendedProperties {

	public ScalableExtendedProperties() {
	}

	public ScalableExtendedProperties(ExtendedProperties extendedProperties)
		throws IOException {
		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream();

		extendedProperties.save(ubaos, "");

		load(new UnsyncByteArrayInputStream(ubaos.unsafeGetByteArray(), 0,
			ubaos.size()));
	}

	public void clear() {
		_scalableMap.clear();
	}

	public Object clone() {
		ScalableExtendedProperties scalableExtendedProperties =
			(ScalableExtendedProperties)super.clone();

		scalableExtendedProperties._scalableMap =
			new ConcurrentHashMap<Object, Object>(_scalableMap);

		return scalableExtendedProperties;
	}

	public boolean contains(Object value) {
		return _scalableMap.containsKey(value);
	}

	public boolean containsKey(Object key) {
		return _scalableMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return _scalableMap.containsValue(value);
	}

	public Enumeration elements() {
		return Collections.enumeration(_scalableMap.values());
	}

	public Set entrySet() {
		return _scalableMap.entrySet();
	}

	public boolean equals(Object o) {
		return _scalableMap.equals(o);
	}

	public Object get(Object key) {
		return _scalableMap.get(key);
	}

	public int hashCode() {
		return _scalableMap.hashCode();
	}

	public boolean isEmpty() {
		return _scalableMap.isEmpty();
	}

	public Set keySet() {
		return _scalableMap.keySet();
	}

	public Enumeration keys() {
		return Collections.enumeration(_scalableMap.keySet());
	}

	public Object put(Object key, Object value) {
		return _scalableMap.put(key, value);
	}

	public void putAll(Map t) {
		_scalableMap.putAll(t);
	}

	public Object remove(Object key) {
		return _scalableMap.remove(key);
	}

	public int size() {
		return _scalableMap.size();
	}

	public String toString() {
		return _scalableMap.toString();
	}

	public Collection values() {
		return _scalableMap.values();
	}

	private Map<Object, Object> _scalableMap =
		new ConcurrentHashMap<Object, Object>();

}