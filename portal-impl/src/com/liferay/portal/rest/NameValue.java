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

package com.liferay.portal.rest;

public class NameValue<E> {

	public NameValue() {
	}

	public NameValue(String name, E value) {
		this._name = name;
		this._value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		NameValue nameValue = (NameValue)o;

		if (_name != null ?
			!_name.equals(nameValue._name) : nameValue._name != null) {
			return false;
		}
		if (_value != null ?
			!_value.equals(nameValue._value) : nameValue._value != null) {
			return false;
		}

		return true;
	}

	public String getName() {
		return _name;
	}

	public E getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		int result = _name != null ? _name.hashCode() : 0;
		result = 31 * result + (_value != null ? _value.hashCode() : 0);
		return result;
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setValue(E value) {
		this._value = value;
	}

	private String _name;

	private E _value;

}