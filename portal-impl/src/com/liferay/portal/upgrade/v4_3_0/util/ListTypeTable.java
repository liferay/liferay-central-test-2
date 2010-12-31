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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ListTypeTable {

	public static String TABLE_NAME = "ListType";

	public static Object[][] TABLE_COLUMNS = {
		{"listTypeId", new Integer(Types.INTEGER)},
		{"name", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table ListType (listTypeId INTEGER not null primary key,name VARCHAR(75) null,type_ VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table ListType";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2932DD37 on ListType (type_)"
	};

}