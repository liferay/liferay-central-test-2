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

import java.util.Comparator;
import java.util.List;

/**
 * <a href="BinarySearch<E>.java.html"><b><i>View Source</i></b></a>
 */
public abstract class BinarySearch<E> {

	public static <T extends Comparable> BinarySearch<T> forList(
		final List<T> list) {

		return new BinarySearch<T>() {
			@Override
			@SuppressWarnings({"unchecked"})
			protected int compare(int index, T element) {
				return list.get(index).compareTo(element);
			}

			@Override
			protected int getLastIndex() {
				return list.size() - 1;
			}
		};
	}

	public static <T> BinarySearch<T> forList(
		final List<T> list, final Comparator<T> comparator) {

		return new BinarySearch<T>() {
			@Override
			@SuppressWarnings( {"unchecked"})
			protected int compare(int index, T element) {
				return comparator.compare(list.get(index), element);
			}

			@Override
			protected int getLastIndex() {
				return list.size() - 1;
			}
		};
	}

	public int find(E element) {
		return find(element, 0, getLastIndex());
	}

	public int find(E element, int low) {
		return find(element, low, getLastIndex());
	}

	public int find(E element, int low, int high) {
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int delta = compare(mid, element);

			if (delta < 0) {
				low = mid + 1;
			}
			else if (delta > 0) {
				high = mid - 1;
			}
			else {
				return mid;
			}
		}
		return -(low + 1);
	}

	public int findFirst(E o) {
		return findFirst(o, 0, getLastIndex());
	}

	public int findFirst(E o, int low) {
		return findFirst(o, low, getLastIndex());
	}

	public int findFirst(E o, int low, int high) {

		int ndx = -1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int delta = compare(mid, o);

			if (delta < 0) {
				low = mid + 1;
			}
			else {
				if (delta == 0) {
					ndx = mid;
				}
				high = mid - 1;
			}
		}

		if (ndx == -1) {
			return -(low + 1);
		}

		return ndx;
	}

	public int findLast(E o) {
		return findLast(o, 0, getLastIndex());
	}

	public int findLast(E o, int low) {
		return findLast(o, low, getLastIndex());
	}

	public int findLast(E o, int low, int high) {
		int ndx = -1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int delta = compare(mid, o);

			if (delta > 0) {
				high = mid - 1;
			}
			else {
				if (delta == 0) {
					ndx = mid;
				}
				low = mid + 1;
			}
		}

		if (ndx == -1) {
			return -(low + 1);
		}

		return ndx;
	}

	protected abstract int compare(int index, E element);

	protected abstract int getLastIndex();

}