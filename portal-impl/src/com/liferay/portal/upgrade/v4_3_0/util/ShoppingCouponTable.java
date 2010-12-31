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
public class ShoppingCouponTable {

	public static String TABLE_NAME = "ShoppingCoupon";

	public static Object[][] TABLE_COLUMNS = {
		{"couponId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"code_", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"startDate", new Integer(Types.TIMESTAMP)},
		{"endDate", new Integer(Types.TIMESTAMP)},
		{"active_", new Integer(Types.BOOLEAN)},
		{"limitCategories", new Integer(Types.VARCHAR)},
		{"limitSkus", new Integer(Types.VARCHAR)},
		{"minOrder", new Integer(Types.DOUBLE)},
		{"discount", new Integer(Types.DOUBLE)},
		{"discountType", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table ShoppingCoupon (couponId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,code_ VARCHAR(75) null,name VARCHAR(75) null,description STRING null,startDate DATE null,endDate DATE null,active_ BOOLEAN,limitCategories STRING null,limitSkus STRING null,minOrder DOUBLE,discount DOUBLE,discountType VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table ShoppingCoupon";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DC60CFAE on ShoppingCoupon (code_)",
		"create index IX_3251AF16 on ShoppingCoupon (groupId)"
	};

}