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

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectValuePairComparator<K, V>
	implements Comparator<ObjectValuePair<K, V>> {

	public ObjectValuePairComparator() {
		this(true);
	}

	public ObjectValuePairComparator(boolean ascending) {
		this(true, ascending);
	}

	public ObjectValuePairComparator(boolean byKey, boolean ascending) {
		_byKey = byKey;
		_ascending = ascending;
	}

	public int compare(ObjectValuePair<K, V> ovp1, ObjectValuePair<K, V> ovp2) {
		if (_byKey) {
			Comparable key1 = (Comparable)ovp1.getKey();
			Comparable key2 = (Comparable)ovp2.getKey();

			if (_ascending) {
				return key1.compareTo(key2);
			}
			else {
				return -(key1.compareTo(key2));
			}
		}
		else {
			Comparable value1 = (Comparable)ovp1.getValue();
			Comparable value2 = (Comparable)ovp2.getValue();

			if (_ascending) {
				return value1.compareTo(value2);
			}
			else {
				return -(value1.compareTo(value2));
			}
		}
	}

	private boolean _byKey;
	private boolean _ascending;

}