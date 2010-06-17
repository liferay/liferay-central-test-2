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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="FooParent.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class FooParent {

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FooParent)) {
			return false;
		}

		FooParent fooParent = (FooParent)obj;

		if (Validator.equals(_int, fooParent._int) &&
			Validator.equals(_integer, fooParent._integer) &&
			Validator.equals(_long, fooParent._long) &&
			Validator.equals(_longObject, fooParent._longObject)) {

			return true;
		}

		return false;
	}

	public int getInt() {
		return _int;
	}

	public Integer getInteger() {
		return _integer;
	}

	public long getLong() {
		return _long;
	}

	public Long getLongObject() {
		return _longObject;
	}

	public void setInt(int v) {
		_int = v;
	}

	public void setInteger(Integer v) {
		_integer = v;
	}

	public void setLong(long v) {
		_long = v;
	}

	public void setLongObject(Long v) {
		_longObject = v;
	}

	private int _int;
	private Integer _integer;
	private long _long;
	private Long _longObject;

}