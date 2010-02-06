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
 * <a href="ShoppingOrderTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ShoppingOrderTable {

	public static String TABLE_NAME = "ShoppingOrder";

	public static Object[][] TABLE_COLUMNS = {
		{"orderId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"number_", new Integer(Types.VARCHAR)},
		{"tax", new Integer(Types.DOUBLE)},
		{"shipping", new Integer(Types.DOUBLE)},
		{"altShipping", new Integer(Types.VARCHAR)},
		{"requiresShipping", new Integer(Types.BOOLEAN)},
		{"insure", new Integer(Types.BOOLEAN)},
		{"insurance", new Integer(Types.DOUBLE)},
		{"couponCodes", new Integer(Types.VARCHAR)},
		{"couponDiscount", new Integer(Types.DOUBLE)},
		{"billingFirstName", new Integer(Types.VARCHAR)},
		{"billingLastName", new Integer(Types.VARCHAR)},
		{"billingEmailAddress", new Integer(Types.VARCHAR)},
		{"billingCompany", new Integer(Types.VARCHAR)},
		{"billingStreet", new Integer(Types.VARCHAR)},
		{"billingCity", new Integer(Types.VARCHAR)},
		{"billingState", new Integer(Types.VARCHAR)},
		{"billingZip", new Integer(Types.VARCHAR)},
		{"billingCountry", new Integer(Types.VARCHAR)},
		{"billingPhone", new Integer(Types.VARCHAR)},
		{"shipToBilling", new Integer(Types.BOOLEAN)},
		{"shippingFirstName", new Integer(Types.VARCHAR)},
		{"shippingLastName", new Integer(Types.VARCHAR)},
		{"shippingEmailAddress", new Integer(Types.VARCHAR)},
		{"shippingCompany", new Integer(Types.VARCHAR)},
		{"shippingStreet", new Integer(Types.VARCHAR)},
		{"shippingCity", new Integer(Types.VARCHAR)},
		{"shippingState", new Integer(Types.VARCHAR)},
		{"shippingZip", new Integer(Types.VARCHAR)},
		{"shippingCountry", new Integer(Types.VARCHAR)},
		{"shippingPhone", new Integer(Types.VARCHAR)},
		{"ccName", new Integer(Types.VARCHAR)},
		{"ccType", new Integer(Types.VARCHAR)},
		{"ccNumber", new Integer(Types.VARCHAR)},
		{"ccExpMonth", new Integer(Types.INTEGER)},
		{"ccExpYear", new Integer(Types.INTEGER)},
		{"ccVerNumber", new Integer(Types.VARCHAR)},
		{"comments", new Integer(Types.VARCHAR)},
		{"ppTxnId", new Integer(Types.VARCHAR)},
		{"ppPaymentStatus", new Integer(Types.VARCHAR)},
		{"ppPaymentGross", new Integer(Types.DOUBLE)},
		{"ppReceiverEmail", new Integer(Types.VARCHAR)},
		{"ppPayerEmail", new Integer(Types.VARCHAR)},
		{"sendOrderEmail", new Integer(Types.BOOLEAN)},
		{"sendShippingEmail", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table ShoppingOrder (orderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,number_ VARCHAR(75) null,tax DOUBLE,shipping DOUBLE,altShipping VARCHAR(75) null,requiresShipping BOOLEAN,insure BOOLEAN,insurance DOUBLE,couponCodes VARCHAR(75) null,couponDiscount DOUBLE,billingFirstName VARCHAR(75) null,billingLastName VARCHAR(75) null,billingEmailAddress VARCHAR(75) null,billingCompany VARCHAR(75) null,billingStreet VARCHAR(75) null,billingCity VARCHAR(75) null,billingState VARCHAR(75) null,billingZip VARCHAR(75) null,billingCountry VARCHAR(75) null,billingPhone VARCHAR(75) null,shipToBilling BOOLEAN,shippingFirstName VARCHAR(75) null,shippingLastName VARCHAR(75) null,shippingEmailAddress VARCHAR(75) null,shippingCompany VARCHAR(75) null,shippingStreet VARCHAR(75) null,shippingCity VARCHAR(75) null,shippingState VARCHAR(75) null,shippingZip VARCHAR(75) null,shippingCountry VARCHAR(75) null,shippingPhone VARCHAR(75) null,ccName VARCHAR(75) null,ccType VARCHAR(75) null,ccNumber VARCHAR(75) null,ccExpMonth INTEGER,ccExpYear INTEGER,ccVerNumber VARCHAR(75) null,comments STRING null,ppTxnId VARCHAR(75) null,ppPaymentStatus VARCHAR(75) null,ppPaymentGross DOUBLE,ppReceiverEmail VARCHAR(75) null,ppPayerEmail VARCHAR(75) null,sendOrderEmail BOOLEAN,sendShippingEmail BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table ShoppingOrder";

}