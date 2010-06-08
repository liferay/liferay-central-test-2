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

package com.liferay.util;

/**
 * <a href="ConcurrentCounter.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Zsolt Berentey
 */
public class ConcurrentCounter {

	public ConcurrentCounter(double value) {
		_value = value;
	}

	public ConcurrentCounter(int value) {
		_value = value;
	}
	
	public ConcurrentCounter(long value) {
		_value = value;
	}

	public ConcurrentCounter(Number value) {
		if (value instanceof Integer) {
			_value = new Integer(value.intValue());
		}
		else if (value instanceof Long) {
			_value = new Long(value.longValue());
		}
		else if (value instanceof Double) {
			_value = new Double(value.doubleValue());
		}
		else if (value instanceof SocialEquity) {
			_value = value;
		}
	}
	
	public synchronized void decrement(Number count) {
		if (count instanceof Integer) {
			_value = getIntValue() - count.intValue();
		}
		else if (count instanceof Long) {
			_value = getLongValue() - count.longValue();
		}
		else if (count instanceof Double) {
			_value = getDoubleValue() - count.doubleValue();
		}
		else if (count instanceof SocialEquity) {
			((SocialEquity)_value).subtract((SocialEquity)count);
		}
	}
	
	public synchronized void increment(double count) {
		_value = getDoubleValue() + count;
	}

	public synchronized void increment(int count) {
		_value = getIntValue() + count;
	}
	
	public synchronized void increment(long count) {
		_value = getLongValue() + count;
	}

	public synchronized void increment(Number count) {
		if (count instanceof Integer) {
			_value = getIntValue() + count.intValue();
		}
		else if (count instanceof Long) {
			_value = getLongValue() + count.longValue();
		}
		else if (count instanceof Double) {
			_value = getDoubleValue() + count.doubleValue();
		}
		else if (count instanceof SocialEquity) {
			((SocialEquity)_value).add((SocialEquity)count);
		}
	}

	public double getDoubleValue() {
		return _value.doubleValue();
	}

	public int getIntValue() {
		return _value.intValue();
	}
	
	public long getLongValue() {
		return _value.longValue();
	}
	
	public Number getValue() {
		return _value;
	}
	
	private Number _value = 0;

}
