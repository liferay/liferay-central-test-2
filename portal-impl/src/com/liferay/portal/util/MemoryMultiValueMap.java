/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.MultiValueMap;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="MemoryMultiValueMap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class MemoryMultiValueMap<K extends Serializable, V extends Serializable>
	extends MultiValueMap<K, V> {

	public void clear() {
		_map.clear();
	}

	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		for (K key : keySet()) {
			Set<V> values = getAll(key);

			if (values.contains(value)) {
				return true;
			}
		}

		return false;
	}

	public Set<V> getAll(Object key) {
		return _map.get(key);
	}

	public boolean isEmpty() {
		return _map.isEmpty();
	}

	public Set<K> keySet() {
		return _map.keySet();
	}

	public V put(K key, V value) {
		Set<V> values = _map.get(key);

		if (values == null) {
			values = new HashSet<V>();
		}

		values.add(value);

		_map.put(key, values);

		return value;
	}

	public Set<V> putAll(K key, Collection<? extends V> values) {
		Set<V> oldValues = _map.get(key);

		if (oldValues == null) {
			oldValues = new HashSet<V>();
		}

		oldValues.addAll(values);

		_map.put(key, oldValues);

		return oldValues;
	}

	public V remove(Object key) {
		V value = null;

		Set<V> values = _map.remove(key);

		if ((values != null) && !values.isEmpty()) {
			value = values.iterator().next();
		}

		return value;
	}

	private Map<K, Set<V>> _map = new HashMap<K, Set<V>>();

}