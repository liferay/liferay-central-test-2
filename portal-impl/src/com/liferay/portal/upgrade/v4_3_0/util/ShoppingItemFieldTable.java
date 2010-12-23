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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ShoppingItemFieldTable {

	public static String TABLE_NAME = "ShoppingItemField";

	public static Object[][] TABLE_COLUMNS = {
		{"itemFieldId", new Integer(Types.BIGINT)},
		{"itemId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"values_", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table ShoppingItemField (itemFieldId LONG not null primary key,itemId LONG,name VARCHAR(75) null,values_ STRING null,description STRING null)";

	public static String TABLE_SQL_DROP = "drop table ShoppingItemField";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6D5F9B87 on ShoppingItemField (itemId)",
	};

}