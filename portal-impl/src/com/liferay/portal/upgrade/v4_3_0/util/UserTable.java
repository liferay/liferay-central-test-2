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
		{"userId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"defaultUser", Types.BOOLEAN},
		{"contactId", Types.BIGINT},
		{"password_", Types.VARCHAR},
		{"passwordEncrypted", Types.BOOLEAN},
		{"passwordReset", Types.BOOLEAN},
		{"passwordModifiedDate", Types.TIMESTAMP},
		{"graceLoginCount", Types.INTEGER},
		{"screenName", Types.VARCHAR},
		{"emailAddress", Types.VARCHAR},
		{"portraitId", Types.BIGINT},
		{"languageId", Types.VARCHAR},
		{"timeZoneId", Types.VARCHAR},
		{"greeting", Types.VARCHAR},
		{"comments", Types.VARCHAR},
		{"loginDate", Types.TIMESTAMP},
		{"loginIP", Types.VARCHAR},
		{"lastLoginDate", Types.TIMESTAMP},
		{"lastLoginIP", Types.VARCHAR},
		{"lastFailedLoginDate", Types.TIMESTAMP},
		{"failedLoginAttempts", Types.INTEGER},
		{"lockout", Types.BOOLEAN},
		{"lockoutDate", Types.TIMESTAMP},
		{"agreedToTermsOfUse", Types.BOOLEAN},
		{"active_", Types.BOOLEAN}
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