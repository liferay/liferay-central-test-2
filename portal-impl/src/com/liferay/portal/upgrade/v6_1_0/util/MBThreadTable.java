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

package com.liferay.portal.upgrade.v6_1_0.util;

import java.sql.Types;

/**
 * @author	  Shuyang Zhou
 * @generated
 */
public class MBThreadTable {

	public static final String TABLE_NAME = "MBThread";

	public static final Object[][] TABLE_COLUMNS = {
		{"threadId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"categoryId", new Integer(Types.BIGINT)},
		{"rootMessageId", new Integer(Types.BIGINT)},
		{"rootMessageUserId", new Integer(Types.BIGINT)},
		{"messageCount", new Integer(Types.INTEGER)},
		{"viewCount", new Integer(Types.INTEGER)},
		{"lastPostByUserId", new Integer(Types.BIGINT)},
		{"lastPostDate", new Integer(Types.TIMESTAMP)},
		{"priority", new Integer(Types.DOUBLE)},
		{"status", new Integer(Types.INTEGER)},
		{"statusByUserId", new Integer(Types.BIGINT)},
		{"statusByUserName", new Integer(Types.VARCHAR)},
		{"statusDate", new Integer(Types.TIMESTAMP)}
	};

	public static final String TABLE_SQL_CREATE = "create table MBThread (threadId LONG not null primary key,groupId LONG,companyId LONG,categoryId LONG,rootMessageId LONG,rootMessageUserId LONG,messageCount INTEGER,viewCount INTEGER,lastPostByUserId LONG,lastPostDate DATE null,priority DOUBLE,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table MBThread";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_41F6DC8A on MBThread (categoryId, priority)",
		"create index IX_95C0EA45 on MBThread (groupId)",
		"create index IX_9A2D11B2 on MBThread (groupId, categoryId)",
		"create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate)",
		"create index IX_485F7E98 on MBThread (groupId, categoryId, status)",
		"create index IX_E1E7142B on MBThread (groupId, status)"
	};

}