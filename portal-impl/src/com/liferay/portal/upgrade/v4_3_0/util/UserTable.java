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
 * <a href="UserTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class UserTable {

	public static String TABLE_NAME = "User_";

	public static Object[][] TABLE_COLUMNS = {
		{"userId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"defaultUser", new Integer(Types.BOOLEAN)},
		{"contactId", new Integer(Types.BIGINT)},
		{"password_", new Integer(Types.VARCHAR)},
		{"passwordEncrypted", new Integer(Types.BOOLEAN)},
		{"passwordReset", new Integer(Types.BOOLEAN)},
		{"passwordModifiedDate", new Integer(Types.TIMESTAMP)},
		{"graceLoginCount", new Integer(Types.INTEGER)},
		{"screenName", new Integer(Types.VARCHAR)},
		{"emailAddress", new Integer(Types.VARCHAR)},
		{"portraitId", new Integer(Types.BIGINT)},
		{"languageId", new Integer(Types.VARCHAR)},
		{"timeZoneId", new Integer(Types.VARCHAR)},
		{"greeting", new Integer(Types.VARCHAR)},
		{"comments", new Integer(Types.VARCHAR)},
		{"loginDate", new Integer(Types.TIMESTAMP)},
		{"loginIP", new Integer(Types.VARCHAR)},
		{"lastLoginDate", new Integer(Types.TIMESTAMP)},
		{"lastLoginIP", new Integer(Types.VARCHAR)},
		{"lastFailedLoginDate", new Integer(Types.TIMESTAMP)},
		{"failedLoginAttempts", new Integer(Types.INTEGER)},
		{"lockout", new Integer(Types.BOOLEAN)},
		{"lockoutDate", new Integer(Types.TIMESTAMP)},
		{"agreedToTermsOfUse", new Integer(Types.BOOLEAN)},
		{"active_", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table User_ (userId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,defaultUser BOOLEAN,contactId LONG,password_ VARCHAR(75) null,passwordEncrypted BOOLEAN,passwordReset BOOLEAN,passwordModifiedDate DATE null,graceLoginCount INTEGER,screenName VARCHAR(75) null,emailAddress VARCHAR(75) null,portraitId LONG,languageId VARCHAR(75) null,timeZoneId VARCHAR(75) null,greeting VARCHAR(75) null,comments STRING null,loginDate DATE null,loginIP VARCHAR(75) null,lastLoginDate DATE null,lastLoginIP VARCHAR(75) null,lastFailedLoginDate DATE null,failedLoginAttempts INTEGER,lockout BOOLEAN,lockoutDate DATE null,agreedToTermsOfUse BOOLEAN,active_ BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table User_";

}