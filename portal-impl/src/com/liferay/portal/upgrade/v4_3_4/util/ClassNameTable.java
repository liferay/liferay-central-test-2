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

package com.liferay.portal.upgrade.v4_3_4.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ClassNameTable {

	public static String TABLE_NAME = "ClassName_";

	public static Object[][] TABLE_COLUMNS = {
		{"classNameId", new Integer(Types.BIGINT)},
		{"value", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table ClassName_ (classNameId LONG not null primary key,value VARCHAR(200) null)";

	public static String TABLE_SQL_DROP = "drop table ClassName_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B27A301F on ClassName_ (value)",
	};

}