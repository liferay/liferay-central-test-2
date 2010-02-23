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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * <a href="ShoppingItemTable.java.html"><b><i>View Source</i></b></a>
 *
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

}