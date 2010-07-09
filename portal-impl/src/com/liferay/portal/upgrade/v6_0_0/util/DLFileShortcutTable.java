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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLFileShortcutTable {

	public static final String TABLE_NAME = "DLFileShortcut";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"fileShortcutId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"folderId", new Integer(Types.BIGINT)},
		{"toFolderId", new Integer(Types.BIGINT)},
		{"toName", new Integer(Types.VARCHAR)},
		{"status", new Integer(Types.INTEGER)},
		{"statusByUserId", new Integer(Types.BIGINT)},
		{"statusByUserName", new Integer(Types.VARCHAR)},
		{"statusDate", new Integer(Types.TIMESTAMP)}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileShortcut (uuid_ VARCHAR(75) null,fileShortcutId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,toFolderId LONG,toName VARCHAR(255) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileShortcut";

}