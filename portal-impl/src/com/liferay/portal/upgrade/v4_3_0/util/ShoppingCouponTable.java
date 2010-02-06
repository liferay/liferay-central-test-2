/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * <a href="ShoppingCouponTable.java.html"><b><i>View Source</i></b></a>
 *
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

}