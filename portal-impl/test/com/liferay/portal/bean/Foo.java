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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <a href="Foo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class Foo extends FooParent {

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Foo)) {
			return false;
		}

		Foo foo = (Foo)obj;

		if (super.equals(foo) && Validator.equals(_boolean, foo._boolean) &&
			Validator.equals(_booleanObject, foo._booleanObject) &&
			Validator.equals(_byte, foo._byte) &&
			Validator.equals(_byteObject, foo._byteObject) &&
			Validator.equals(_char, foo._char) &&
			Validator.equals(_character, foo._character) &&
			Validator.equals(_double, foo._double) &&
			Validator.equals(_doubleObject, foo._doubleObject) &&
			Validator.equals(_float, foo._float) &&
			Validator.equals(_floatObject, foo._floatObject) &&
			Validator.equals(_list, foo._list) &&
			Validator.equals(_map, foo._map) &&
			Validator.equals(_short, foo._short) &&
			Validator.equals(_shortObject, foo._shortObject) &&
			Validator.equals(_string, foo._string) &&
			Arrays.equals(_stringArray, foo._stringArray)) {

			return true;
		}

		return false;
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

	public short getShort() {
		return _short;
	}

	public Short getShortObject() {
		return _shortObject;
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

	public void setShort(short s) {
		_short = s;
	}

	public void setShortObject(Short shortObject) {
		_shortObject = shortObject;
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
	private short _short;
	private Short _shortObject;
	private String _string;
	private String[] _stringArray;

}