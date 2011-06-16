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
public class ShoppingItemPriceTable {

	public static String TABLE_NAME = "ShoppingItemPrice";

	public static Object[][] TABLE_COLUMNS = {
		{"itemPriceId", Types.BIGINT},
		{"itemId", Types.BIGINT},
		{"minQuantity", Types.INTEGER},
		{"maxQuantity", Types.INTEGER},
		{"price", Types.DOUBLE},
		{"discount", Types.DOUBLE},
		{"taxable", Types.BOOLEAN},
		{"shipping", Types.DOUBLE},
		{"useShippingFormula", Types.BOOLEAN},
		{"status", Types.INTEGER}
	};

	public static String TABLE_SQL_CREATE = "create table ShoppingItemPrice (itemPriceId LONG not null primary key,itemId LONG,minQuantity INTEGER,maxQuantity INTEGER,price DOUBLE,discount DOUBLE,taxable BOOLEAN,shipping DOUBLE,useShippingFormula BOOLEAN,status INTEGER)";

	public static String TABLE_SQL_DROP = "drop table ShoppingItemPrice";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EA6FD516 on ShoppingItemPrice (itemId)"
	};

}