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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="MultiValueMap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public abstract class MultiValueMap
	<K extends Serializable, V extends Serializable> implements Map<K, V> {

	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	public V get(Object key) {
		throw new UnsupportedOperationException();
	}

	public abstract Set<V> getAll(Object key);

	public abstract Set<V> putAll(K key, Collection<? extends V> values);

	public void putAll(Map<? extends K, ? extends V> map) {
		MultiValueMap<? extends K, ? extends V> multiValueMap = null;

		if (map instanceof MultiValueMap<?, ?>) {
			multiValueMap = (MultiValueMap<? extends K, ? extends V>)map;
		}

		for (K key : map.keySet()) {
			if (multiValueMap != null) {
				putAll(key, multiValueMap.getAll(key));
			}
			else {
				put(key, map.get(key));
			}
		}
	}

	public int size() {
		int size = 0;

		for (K key : keySet()) {
			size += size(key);
		}

		return size;
	}

	public int size(Object key) {
		int size = 0;

		Collection<V> values = getAll(key);

		if (values != null) {
			size = values.size();
		}

		return size;
	}

	public Collection<V> values() {
		Set<V> values = new HashSet<V>();

		Set<K> keys = keySet();

		for (K key : keys) {
			values.addAll(getAll(key));
		}

		return values;
	}

}