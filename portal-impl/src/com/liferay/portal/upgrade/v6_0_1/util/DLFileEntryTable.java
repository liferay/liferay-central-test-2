/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_1.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryTable {

	public static final String TABLE_NAME = "DLFileEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"fileEntryId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"versionUserId", new Integer(Types.BIGINT)},
		{"versionUserName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"folderId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"title", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"version", new Integer(Types.VARCHAR)},
		{"size_", new Integer(Types.BIGINT)},
		{"readCount", new Integer(Types.INTEGER)},
		{"extraSettings", new Integer(Types.CLOB)}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileEntry (uuid_ VARCHAR(75) null,fileEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,title VARCHAR(255) null,description STRING null,version VARCHAR(75) null,size_ LONG,readCount INTEGER,extraSettings TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";

}