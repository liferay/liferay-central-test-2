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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <a href="Foo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class Foo extends FooBase {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Foo foo = (Foo) o;
		if (super.equals(foo) == false) {
			return false;
		}

		if (_boolean != foo._boolean) {
			return false;
		}
		if (_byte != foo._byte) {
			return false;
		}
		if (_char != foo._char) {
			return false;
		}
		if (Double.compare(foo._double, _double) != 0) {
			return false;
		}
		if (Float.compare(foo._float, _float) != 0) {
			return false;
		}
		if (_booleanObject != null ?
			!_booleanObject.equals(foo._booleanObject) :
			foo._booleanObject != null) {
			return false;
		}
		if (_byteObject != null ? !_byteObject.equals(foo._byteObject) :
			foo._byteObject != null) {
			return false;
		}
		if (_doubleObject != null ? !_doubleObject.equals(foo._doubleObject) :
			foo._doubleObject != null) {
			return false;
		}
		if (_floatObject != null ? !_floatObject.equals(foo._floatObject) :
			foo._floatObject != null) {
			return false;
		}
		if (_character != null ? !_character.equals(foo._character) :
			foo._character != null) {
			return false;
		}
		if (_list != null ? !_list.equals(foo._list) :
			foo._list != null) {
			return false;
		}
		if (_map != null ? !_map.equals(foo._map) : foo._map != null) {
			return false;
		}
		if (_string != null ? !_string.equals(foo._string) :
			foo._string != null) {
			return false;
		}
		if (!Arrays.equals(_stringArray, foo._stringArray)) {
			return false;
		}
		return true;
	}

	public boolean getBoolean() {
		return _boolean;
	}

	public Boolean getBooleanObject() {
		return _booleanObject;
	}

	public byte getByte() {
		return _byte;
	}

	public Byte getByteObject() {
		return _byteObject;
	}

	public char getChar() {
		return _char;
	}

	public Character getCharacter() {
		return _character;
	}

	public double getDouble() {
		return _double;
	}

	public Double getDoubleObject() {
		return _doubleObject;
	}

	public float getFloat() {
		return _float;
	}

	public Float getFloatObject() {
		return _floatObject;
	}

	public List<?> getList() {
		return _list;
	}

	public Map<?, ?> getMap() {
		return _map;
	}

	public String getString() {
		return _string;
	}

	public String[] getStringArray() {
		return _stringArray;
	}

	public void setBoolean(boolean b) {
		_boolean = b;
	}

	public void setBooleanObject(Boolean booleanObject) {
		_booleanObject = booleanObject;
	}

	public void setByte(byte b) {
		_byte = b;
	}

	public void setByteObject(Byte byteObject) {
		_byteObject = byteObject;
	}

	public void setChar(char c) {
		_char = c;
	}

	public void setCharacter(Character character) {
		_character = character;
	}

	public void setDouble(double d) {
		_double = d;
	}

	public void setDoubleObject(Double doubleObject) {
		_doubleObject = doubleObject;
	}

	public void setFloat(float f) {
		_float = f;
	}

	public void setFloatObject(Float floatObject) {
		_floatObject = floatObject;
	}

	public void setList(List<?> list) {
		_list = list;
	}

	public void setMap(Map<?, ?> map) {
		this._map = map;
	}

	public void setString(String string) {
		_string = string;
	}

	public void setStringArray(String[] stringArray) {
		_stringArray = stringArray;
	}

	private boolean _boolean;
	private Boolean _booleanObject;
	private byte _byte;
	private Byte _byteObject;
	private char _char;
	private Character _character;
	private double _double;
	private Double _doubleObject;
	private float _float;
	private Float _floatObject;
	private List<?> _list;
	private Map<?, ?> _map;
	private String _string;
	private String[] _stringArray;

}