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
public class IGFolderTable {

	public static String TABLE_NAME = "IGFolder";

	public static Object[][] TABLE_COLUMNS = {
		{"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"parentFolderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR}
	};

	public static String TABLE_SQL_CREATE = "create table IGFolder (folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,parentFolderId LONG,name VARCHAR(75) null,description STRING null)";

	public static String TABLE_SQL_DROP = "drop table IGFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_206498F8 on IGFolder (groupId)",
		"create index IX_1A605E9F on IGFolder (groupId, parentFolderId)"
	};

}