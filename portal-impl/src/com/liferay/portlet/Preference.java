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

package com.liferay.portlet;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class Preference implements Cloneable, Serializable {

	public Preference(String name, String value) {
		this(name, new String[] {value});
	}

	public Preference(String name, String value, boolean readOnly) {
		this(name, new String[] {value}, readOnly);
	}

	public Preference(String name, String[] values) {
		this(name, values, false);
	}

	public Preference(String name, String[] values, boolean readOnly) {
		_name = name;
		_values = values;
		_readOnly = readOnly;
	}

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public Object clone() {
		return new Preference(_name, _values, _readOnly);
	}

	private String _name;
	private String[] _values;
	private boolean _readOnly;

}