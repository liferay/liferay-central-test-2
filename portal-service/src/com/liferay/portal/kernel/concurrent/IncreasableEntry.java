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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * <a href="IncreasableEntry.java.html"><b><i>View Source</i></b></a>
 *
 * <B>Semantic note:</B>
 * <P>
 * This base class provides a mechanism which allows modifying entry's value
 * (base one the current value) before first get happens. Once the value has
 * been got by get() method, all modification requests will be rejected.
 * </P>
 * <P>
 * Subclass should overwrite doIncrease(V originalValue, V deltaValue) to
 * provide actual increase logic, base class handles all thread safety
 * protection, so subclass can do any logic in doIncrease without worry about
 * synchronization. To protect internal logic, all fields and concrete methods
 * are final.
 * </P>
 * @author Shuyang Zhou
 */
public abstract class IncreasableEntry<K, V> {

	public IncreasableEntry(K key, V value) {
		_key = key;
		_markedValue = new AtomicMarkableReference<V>(value, false);
	}

	public abstract V doIncrease(V originalValue, V deltaValue);

	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IncreasableEntry)) {
			return false;
		}

		IncreasableEntry<K, V> entry = (IncreasableEntry<K, V>)obj;

		if (Validator.equals(_key, entry._key) &&
			Validator.equals(_markedValue.getReference(),
			entry._markedValue.getReference())) {

			return true;
		}

		return false;
	}

	public final K getKey() {
		return _key;
	}

	/**
	 * Get the value and mark the flag to stop modifying the value in the
	 * future.
	 * @return The value object
	 */
	public final V getValue() {
		while (true) {
			V value = _markedValue.getReference();
			if (_markedValue.attemptMark(value, true)) {
				return value;
			}
		}
	}

	public int hashCode() {
		int hash = 77;

		if (_key != null) {
			hash += _key.hashCode();
		}

		hash = 11 * hash;

		V value = _markedValue.getReference();

		if (value != null) {
			hash += value.hashCode();
		}

		return hash;
	}

	/**
	 * Modify the value object base on the given delta value, if the value has
	 * been marked to forbid modifying, this method will return false without
	 * doing any modification.
	 * @param deltaValue The delta value trying to add to the current one
	 * @return true if successfully increaes the value, otherwise false
	 */
	public final boolean increase(V deltaValue) {

		boolean[] marked = {false};

		while (true) {
			V originalValue = _markedValue.get(marked);

			if (marked[0]) {
				return false;
			}
			else {
				V newValue = doIncrease(originalValue, deltaValue);
				if (_markedValue.compareAndSet(
					originalValue, newValue, false, false)) {

					return true;
				}
			}
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(String.valueOf(_key.toString()));
		sb.append(", value=");
		sb.append(String.valueOf(_markedValue.getReference()));
		sb.append("}");

		return sb.toString();
	}

	private final K _key;

	private final AtomicMarkableReference<V> _markedValue;

}