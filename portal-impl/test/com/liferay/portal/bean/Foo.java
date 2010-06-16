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

import java.util.List;
import java.util.Map;

/**
 * <a href="Foo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class Foo {

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

	public int getInt() {
		return _int;
	}

	public Integer getInteger() {
		return _integer;
	}

	public List<?> getList() {
		return _list;
	}

	public long getLong() {
		return _long;
	}

	public Long getLongObject() {
		return _longObject;
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

	public void setInt(int i) {
		_int = i;
	}

	public void setInteger(Integer integer) {
		_integer = integer;
	}

	public void setList(List<?> list) {
		_list = list;
	}

	public void setLong(long l) {
		_long = l;
	}

	public void setLongObject(Long longObject) {
		_longObject = longObject;
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
	private int _int;
	private Integer _integer;
	private List<?> _list;
	private long _long;
	private Long _longObject;
	private Map<?, ?> _map;
	private String _string;
	private String[] _stringArray;

}