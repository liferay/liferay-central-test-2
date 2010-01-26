/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <a href="ContactTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ContactTable {

	public static String TABLE_NAME = "Contact_";

	public static Object[][] TABLE_COLUMNS = {
		{"contactId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"accountId", new Integer(Types.BIGINT)},
		{"parentContactId", new Integer(Types.BIGINT)},
		{"firstName", new Integer(Types.VARCHAR)},
		{"middleName", new Integer(Types.VARCHAR)},
		{"lastName", new Integer(Types.VARCHAR)},
		{"prefixId", new Integer(Types.INTEGER)},
		{"suffixId", new Integer(Types.INTEGER)},
		{"male", new Integer(Types.BOOLEAN)},
		{"birthday", new Integer(Types.TIMESTAMP)},
		{"smsSn", new Integer(Types.VARCHAR)},
		{"aimSn", new Integer(Types.VARCHAR)},
		{"icqSn", new Integer(Types.VARCHAR)},
		{"jabberSn", new Integer(Types.VARCHAR)},
		{"msnSn", new Integer(Types.VARCHAR)},
		{"skypeSn", new Integer(Types.VARCHAR)},
		{"ymSn", new Integer(Types.VARCHAR)},
		{"employeeStatusId", new Integer(Types.VARCHAR)},
		{"employeeNumber", new Integer(Types.VARCHAR)},
		{"jobTitle", new Integer(Types.VARCHAR)},
		{"jobClass", new Integer(Types.VARCHAR)},
		{"hoursOfOperation", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table Contact_ (contactId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,accountId LONG,parentContactId LONG,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,prefixId INTEGER,suffixId INTEGER,male BOOLEAN,birthday DATE null,smsSn VARCHAR(75) null,aimSn VARCHAR(75) null,icqSn VARCHAR(75) null,jabberSn VARCHAR(75) null,msnSn VARCHAR(75) null,skypeSn VARCHAR(75) null,ymSn VARCHAR(75) null,employeeStatusId VARCHAR(75) null,employeeNumber VARCHAR(75) null,jobTitle VARCHAR(100) null,jobClass VARCHAR(75) null,hoursOfOperation VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table Contact_";

}