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

package com.liferay.portal.kernel.counter;

/**
 * <a href="IntegerCounter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
public class IntegerCounter implements Counter<Integer> {

	public IntegerCounter(Integer value) {
		_value = value;
	}

	public void decrement(Integer delta) {
		_value = _value - delta;
	}

	public Counter<Integer> decrementByCreate(Integer delta) {
		return new IntegerCounter(_value - delta);
	}

	public Counter<Integer> decrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof Integer) {
			return decrementByCreate((Integer)delta.getValue());
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is java.lang.Integer.");
	}

	public Integer getValue() {
		return _value;
	}

	public void increment(Integer delta) {
		_value = _value + delta;
	}

	public Counter<Integer> incrementByCreate(Integer delta) {
		return new IntegerCounter(_value + delta);
	}

	public Counter<Integer> incrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof Integer) {
			return incrementByCreate((Integer)delta.getValue());
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is java.lang.Integer.");
	}

	public void setValue(Integer value) {
		_value = value;
	}

	private int _value = 0;

}