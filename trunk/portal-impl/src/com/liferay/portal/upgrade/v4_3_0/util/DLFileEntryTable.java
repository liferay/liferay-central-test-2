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
public class DLFileEntryTable {

	public static String TABLE_NAME = "DLFileEntry";

	public static Object[][] TABLE_COLUMNS = {
		{"fileEntryId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT},
		{"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"version", Types.DOUBLE},
		{"size_", Types.INTEGER},
		{"readCount", Types.INTEGER},
		{"extraSettings", Types.CLOB}
	};

	public static String TABLE_SQL_CREATE = "create table DLFileEntry (fileEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(300) null,title VARCHAR(300) null,description STRING null,version DOUBLE,size_ INTEGER,readCount INTEGER,extraSettings TEXT null)";

	public static String TABLE_SQL_DROP = "drop table DLFileEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_24A846D1 on DLFileEntry (folderId)",
		"create index IX_8F6C75D0 on DLFileEntry (folderId, name)"
	};

}