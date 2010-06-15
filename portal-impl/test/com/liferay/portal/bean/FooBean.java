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
 * <a href="FooBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class FooBean {

	public Integer getInteger() {
		return _Integer;
	}

	public void setInteger(Integer v) {
		_Integer = v;
	}

	public int getInt() {
		return _int;
	}

	public void setInt(int v) {
		_int = v;
	}

	public Long getBigLong() {
		return _bigLong;
	}

	public void setBigLong(Long v) {
		_bigLong = v;
	}

	public long getLong() {
		return _long;
	}

	public void setLong(long v) {
		_long = v;
	}

	public Byte getBigByte() {
		return _bigByte;
	}

	public void setBigByte(Byte v) {
		_bigByte = v;
	}

	public byte getByte() {
		return _byte;
	}

	public void setByte(byte v) {
		_byte = v;
	}

	public Character getCharacter() {
		return _character;
	}

	public void setCharacter(Character v) {
		_character = v;
	}

	public char getChar() {
		return _char;
	}

	public void setChar(char v) {
		_char = v;
	}

	public Boolean getBigBoolean() {
		return _bigBoolean;
	}

	public void setBigBoolean(Boolean v) {
		_bigBoolean = v;
	}

	public boolean getBoolean() {
		return _boolean;
	}

	public void setBoolean(boolean v) {
		_boolean = v;
	}

	public Float getBigFloat() {
		return _bigFloat;
	}

	public void setBigFloat(Float v) {
		_bigFloat = v;
	}

	public float getFloat() {
		return _float;
	}

	public void setFloat(float v) {
		_float = v;
	}

	public Double getBigDouble() {
		return _bigDouble;
	}

	public void setBigDouble(Double v) {
		_bigDouble = v;
	}

	public double getDouble() {
		return _double;
	}

	public void setDouble(double v) {
		_double = v;
	}

	public String getString() {
		return _string;
	}

	public void setString(String v) {
		_string = v;
	}

	public String[] getStringArray() {
		return _stringArray;
	}

	public void setStringArray(String[] v) {
		_stringArray = v;
	}

	public Map getMap() {
		return _map;
	}

	public void setMap(Map v) {
		this._map = v;
	}

	public List getList() {
		return _list;
	}

	public void setList(List v) {
		this._list = v;
	}

	private Boolean _bigBoolean;
	private boolean _boolean;
	private Byte _bigByte;
	private byte _byte;
	private Character _character;
	private char _char;
	private Double _bigDouble;
	private double _double;
	private Float _bigFloat;
	private float _float;
	private Integer _Integer;
	private int _int;
	private List _list;
	private Long _bigLong;
	private long _long;
	private Map _map;
	private String _string;
	private String[] _stringArray;

}
