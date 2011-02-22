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
public class MBBanTable {

	public static String TABLE_NAME = "MBBan";

	public static Object[][] TABLE_COLUMNS = {
		{"banId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"banUserId", Types.BIGINT}
	};

	public static String TABLE_SQL_CREATE = "create table MBBan (banId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,banUserId LONG)";

	public static String TABLE_SQL_DROP = "drop table MBBan";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_69951A25 on MBBan (banUserId)",
		"create index IX_5C3FF12A on MBBan (groupId)",
		"create index IX_8ABC4E3B on MBBan (groupId, banUserId)",
		"create index IX_48814BBA on MBBan (userId)"
	};

}