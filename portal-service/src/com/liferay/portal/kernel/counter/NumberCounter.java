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
 * @author Zsolt Berentey
 */
public class NumberCounter implements Counter<Number> {

	public NumberCounter(Number value) {
		_value = value;
	}

	public void decrement(Number delta) {
		_value = _subtract(delta);
	}

	public Counter<Number> decrementByCreate(Number delta) {
		return new NumberCounter(_subtract(delta));
	}

	public Counter<Number> decrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof Number) {
			return decrementByCreate((Number)delta.getValue());
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is java.lang.Number.");
	}

	public Number getValue() {
		return _value;
	}

	public void increment(Number delta) {
		_value = _add(delta);
	}

	public Counter<Number> incrementByCreate(Number delta) {
		return new NumberCounter(_add(delta));
	}

	public Counter<Number> incrementByCreate(Counter<?> delta) {
		if (delta.getValue() instanceof Number) {
			return incrementByCreate((Number)delta.getValue());
		}

		throw new IllegalArgumentException("Invalid type " +
			delta.getValue().getClass().getName() +
				". Expected type is java.lang.Number.");
	}

	public void setValue(Number value) {
		_value = value;
	}

	private Number _add(Number delta) {
		if (delta instanceof Integer) {
			return _addAsInteger(delta);
		}
		else if (delta instanceof Long) {
			return _addAsLong(delta);
		}
		else if (delta instanceof Double) {
			return _addAsDouble(delta);
		}

		return _value;
	}

	private Number _addAsDouble(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.doubleValue() + delta.doubleValue();
	}

	private Number _addAsInteger(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.intValue() + delta.intValue();
	}

	private Number _addAsLong(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.longValue() + delta.longValue();
	}

	private Number _subtract(Number delta) {
		if (delta instanceof Integer) {
			return _subtractAsInteger(delta);
		}
		else if (delta instanceof Long) {
			return _subtractAsLong(delta);
		}
		else if (delta instanceof Double) {
			return _subtractAsDouble(delta);
		}

		return _value;
	}

	private Number _subtractAsDouble(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.doubleValue() - delta.doubleValue();
	}

	private Number _subtractAsInteger(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.intValue() - delta.intValue();
	}

	private Number _subtractAsLong(Number delta) {
		if (delta == null) {
			return _value;
		}

		return _value.longValue() - delta.longValue();
	}

	private Number _value = 0;

}