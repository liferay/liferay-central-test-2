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

package com.liferay.portal.kernel.memory;

import java.lang.ref.SoftReference;

/**
 * @author Shuyang Zhou
 */
public class SoftReferenceThreadLocal<T> extends ThreadLocal<T> {

	public T get() {
		SoftReference<T> softReference = _softReferenceThreadLocal.get();
		if (softReference == _nullPlaceHolder) {
			return null;
		}

		T value = null;
		if (softReference != null) {
			value = softReference.get();
		}

		if (value == null) {
			value = initialValue();
			set(value);
		}
		return value;
	}

	public void remove() {
		_softReferenceThreadLocal.remove();
	}

	public void set(T value) {
		if (value == null) {
			_softReferenceThreadLocal.set((SoftReference<T>) _nullPlaceHolder);
		}
		else {
			_softReferenceThreadLocal.set(new SoftReference<T>(value));
		}
	}

	private static final SoftReference<Object> _nullPlaceHolder =
		new SoftReference<Object>(null);

	private final ThreadLocal<SoftReference<T>> _softReferenceThreadLocal =
		new ThreadLocal<SoftReference<T>>();

}