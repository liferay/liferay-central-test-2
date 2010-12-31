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

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3A1E834E on User_ (companyId)",
		"create index IX_6EF03E4E on User_ (companyId, defaultUser)",
		"create index IX_615E9F7A on User_ (companyId, emailAddress)",
		"create index IX_765A87C6 on User_ (companyId, password_)",
		"create index IX_C5806019 on User_ (companyId, screenName)",
		"create index IX_9782AD88 on User_ (companyId, userId)",
		"create index IX_5ADBE171 on User_ (contactId)"
	};

}