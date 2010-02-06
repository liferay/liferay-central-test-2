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