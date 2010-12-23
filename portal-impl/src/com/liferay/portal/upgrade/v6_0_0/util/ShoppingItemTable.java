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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ShoppingItemTable {

	public static final String TABLE_NAME = "ShoppingItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"itemId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"categoryId", new Integer(Types.BIGINT)},
		{"sku", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"properties", new Integer(Types.VARCHAR)},
		{"fields_", new Integer(Types.BOOLEAN)},
		{"fieldsQuantities", new Integer(Types.VARCHAR)},
		{"minQuantity", new Integer(Types.INTEGER)},
		{"maxQuantity", new Integer(Types.INTEGER)},
		{"price", new Integer(Types.DOUBLE)},
		{"discount", new Integer(Types.DOUBLE)},
		{"taxable", new Integer(Types.BOOLEAN)},
		{"shipping", new Integer(Types.DOUBLE)},
		{"useShippingFormula", new Integer(Types.BOOLEAN)},
		{"requiresShipping", new Integer(Types.BOOLEAN)},
		{"stockQuantity", new Integer(Types.INTEGER)},
		{"featured_", new Integer(Types.BOOLEAN)},
		{"sale_", new Integer(Types.BOOLEAN)},
		{"smallImage", new Integer(Types.BOOLEAN)},
		{"smallImageId", new Integer(Types.BIGINT)},
		{"smallImageURL", new Integer(Types.VARCHAR)},
		{"mediumImage", new Integer(Types.BOOLEAN)},
		{"mediumImageId", new Integer(Types.BIGINT)},
		{"mediumImageURL", new Integer(Types.VARCHAR)},
		{"largeImage", new Integer(Types.BOOLEAN)},
		{"largeImageId", new Integer(Types.BIGINT)},
		{"largeImageURL", new Integer(Types.VARCHAR)}
	};

	public static final String TABLE_SQL_CREATE = "create table ShoppingItem (itemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,sku VARCHAR(75) null,name VARCHAR(200) null,description STRING null,properties STRING null,fields_ BOOLEAN,fieldsQuantities STRING null,minQuantity INTEGER,maxQuantity INTEGER,price DOUBLE,discount DOUBLE,taxable BOOLEAN,shipping DOUBLE,useShippingFormula BOOLEAN,requiresShipping BOOLEAN,stockQuantity INTEGER,featured_ BOOLEAN,sale_ BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,mediumImage BOOLEAN,mediumImageId LONG,mediumImageURL STRING null,largeImage BOOLEAN,largeImageId LONG,largeImageURL STRING null)";

	public static final String TABLE_SQL_DROP = "drop table ShoppingItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_1C717CA6 on ShoppingItem (companyId, sku)",
		"create index IX_FEFE7D76 on ShoppingItem (groupId, categoryId)",
		"create index IX_903DC750 on ShoppingItem (largeImageId)",
		"create index IX_D217AB30 on ShoppingItem (mediumImageId)",
		"create index IX_FF203304 on ShoppingItem (smallImageId)",
	};

}