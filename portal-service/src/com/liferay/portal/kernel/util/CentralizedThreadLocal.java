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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class CentralizedThreadLocal<T> extends ThreadLocal<T> {

	public static void clearLongLiveThreadLocals() {
		_longLiveThreadLocals.remove();
	}

	public static void clearPeriodicalThreadLocals() {
		_periodicalThreadLocals.remove();
	}

	public static Map<ThreadLocal<?>, Object> getLongLiveThreadLocals() {
		return _longLiveThreadLocals.get();
	}

	public static Map<ThreadLocal<?>, Object> getPeriodicalThreadLocals() {
		return _periodicalThreadLocals.get();
	}

	public static void setThreadLocals(
		Map<ThreadLocal<?>, Object> longLiveThreadLocals,
		Map<ThreadLocal<?>, Object> periodicalThreadLocals) {

		_longLiveThreadLocals.set(longLiveThreadLocals);
		_periodicalThreadLocals.set(periodicalThreadLocals);
	}

	public CentralizedThreadLocal(boolean periodical) {
		_periodical = periodical;

		if (periodical) {
			_hashCode = _periodicalNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
		else {
			_hashCode = _longLiveNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
	}

	public T get() {
		Map<ThreadLocal<?>, Object> threadLocals = _getThreadLocals();

		T value = (T)threadLocals.get(this);

		if (value == _nullHolder) {
			return null;
		}

		if (value == null) {
			value = initialValue();
			if (value == null) {
				threadLocals.put(this, _nullHolder);
			}
			else {
				threadLocals.put(this, value);
			}
		}

		return value;
	}

	public int hashCode() {
		return _hashCode;
	}

	public void remove() {
		_getThreadLocals().remove(this);
	}

	public void set(T value) {
		Map<ThreadLocal<?>, Object> threadLocals = _getThreadLocals();

		if (value == null) {
			threadLocals.put(this, _nullHolder);
		}
		else {
			threadLocals.put(this, value);
		}
	}

	private Map<ThreadLocal<?>, Object> _getThreadLocals() {
		if (_periodical) {
			return _periodicalThreadLocals.get();
		}
		else {
			return _longLiveThreadLocals.get();
		}
	}

	private static final int _HASH_INCREMENT = 0x61c88647;

	private static final AtomicInteger _longLiveNextHasCode =
		new AtomicInteger();

	private static final ThreadLocal<Map<ThreadLocal<?>, Object>>
		_longLiveThreadLocals =
		new ThreadLocal<Map<ThreadLocal<?>, Object>>() {

			protected Map<ThreadLocal<?>, Object> initialValue() {
				return new HashMap<ThreadLocal<?>, Object>();
			}

		};

	private static final Object _nullHolder = new Object();

	private static final AtomicInteger _periodicalNextHasCode =
		new AtomicInteger();

	private static final ThreadLocal<Map<ThreadLocal<?>, Object>>
		_periodicalThreadLocals =
		new ThreadLocal<Map<ThreadLocal<?>, Object>>() {

			protected Map<ThreadLocal<?>, Object> initialValue() {
				return new HashMap<ThreadLocal<?>, Object>();
			}

		};

	private final int _hashCode;
	private final boolean _periodical;

}