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

package com.liferay.counter.model;

import java.io.Serializable;

import java.sql.Types;

/**
 * <a href="Counter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Counter implements Serializable {

	public static final Object[][] TABLE_COLUMNS = {
		{"name", new Integer(Types.VARCHAR)},
		{"currentId", new Integer(Types.BIGINT)},
	};

	public static final String TABLE_NAME = "Counter";

	public static final String TABLE_SQL_CREATE =
		"create table Counter (name VARCHAR(75) not null primary key, " +
			"currentId LONG)";

	public Counter() {
	}

	public long getCurrentId() {
		return _currentId;
	}

	public String getName() {
		return _name;
	}

	public void setCurrentId(long currentId) {
		_currentId = currentId;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _currentId;
	private String _name;

}