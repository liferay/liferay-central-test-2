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
public class ShoppingOrderItemTable {

	public static String TABLE_NAME = "ShoppingOrderItem";

	public static Object[][] TABLE_COLUMNS = {
		{"orderItemId", new Integer(Types.BIGINT)},
		{"orderId", new Integer(Types.BIGINT)},
		{"itemId", new Integer(Types.VARCHAR)},
		{"sku", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"properties", new Integer(Types.VARCHAR)},
		{"price", new Integer(Types.DOUBLE)},
		{"quantity", new Integer(Types.INTEGER)},
		{"shippedDate", new Integer(Types.TIMESTAMP)}
	};

	public static String TABLE_SQL_CREATE = "create table ShoppingOrderItem (orderItemId LONG not null primary key,orderId LONG,itemId VARCHAR(75) null,sku VARCHAR(75) null,name VARCHAR(200) null,description STRING null,properties STRING null,price DOUBLE,quantity INTEGER,shippedDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table ShoppingOrderItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B5F82C7A on ShoppingOrderItem (orderId)",
	};

}