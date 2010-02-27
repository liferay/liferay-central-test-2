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

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="ConcurrentHashSet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> {

	public ConcurrentHashSet() {
		_map = new ConcurrentHashMap<E, String>();
	}

	public ConcurrentHashSet(int capacity) {
		_map = new ConcurrentHashMap<E, String>(capacity);
	}

	public ConcurrentHashSet(Set<E> set) {
		Iterator<E> itr = set.iterator();

		while (itr.hasNext()) {
			E e = itr.next();

			_map.put(e, StringPool.BLANK);
		}
	}

	public boolean add(E e) {
		if (_map.put(e, StringPool.BLANK) == null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void clear() {
		_map.clear();
	}

	public boolean contains(Object obj) {
		if (_map.containsKey(obj)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Iterator<E> iterator() {
		return _map.keySet().iterator();
	}

	public boolean remove(Object obj) {
		if (_map.remove(obj) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public int size() {
		return _map.size();
	}

	private Map<E, String> _map;

}