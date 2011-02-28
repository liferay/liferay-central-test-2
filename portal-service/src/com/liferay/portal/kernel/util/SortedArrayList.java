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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * <a href="SortedArrayList<E>.java.html"><b><i>View Source</i></b></a>
 */
public class SortedArrayList<E> extends ArrayList<E> {

	public SortedArrayList() {
		_comparator = null;
	}

	public SortedArrayList(Comparator<E> c) {
		_comparator = c;
	}

	public SortedArrayList(Collection<? extends E> c) {
		_comparator = null;
		addAll(c);
	}

	@Override
	public boolean add(E o) {
		int idx = 0;

		if (isEmpty() == false) {
			idx = _findInsertionPoint(o);
		}
		super.add(idx, o);

		return true;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<? extends E> i = c.iterator();

		boolean changed = false;

		while (i.hasNext()) {
			boolean ret = add(i.next());

			if (!changed) {
				changed = ret;
			}
		}

		return changed;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings( {"unchecked"})
	protected int compare(E k1, E k2) {
		if (_comparator == null) {
			return ((Comparable)k1).compareTo(k2);
		}
		return _comparator.compare(k1, k2);
	}

	private int _findInsertionPoint(E o) {
		return _findInsertionPoint(o, 0, size() - 1);
	}

	private int _findInsertionPoint(E o, int low, int high) {
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int delta = compare(get(mid), o);

			if (delta > 0) {
				high = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}

		return low;
	}

	private final Comparator<E> _comparator;

}