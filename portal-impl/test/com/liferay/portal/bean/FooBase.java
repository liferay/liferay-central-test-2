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

/**
 * <a href="FooBeanInterface.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class FooBase {

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FooBase)) {
			return false;
		}

		FooBase that = (FooBase) o;
		if (_int != that._int) {
			return false;
		}
		if (_long != that._long) {
			return false;
		}
		if (_longObject != null ? !_longObject.equals(that._longObject) :
			that._longObject != null) {
			return false;
		}
		if (_integer != null ? !_integer.equals(that._integer) :
			that._integer != null) {
			return false;
		}

		return true;
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