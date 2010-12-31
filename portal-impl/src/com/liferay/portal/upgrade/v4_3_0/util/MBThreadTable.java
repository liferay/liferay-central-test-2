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
public class MBThreadTable {

	public static String TABLE_NAME = "MBThread";

	public static Object[][] TABLE_COLUMNS = {
		{"threadId", new Integer(Types.BIGINT)},
		{"categoryId", new Integer(Types.BIGINT)},
		{"rootMessageId", new Integer(Types.BIGINT)},
		{"messageCount", new Integer(Types.INTEGER)},
		{"viewCount", new Integer(Types.INTEGER)},
		{"lastPostByUserId", new Integer(Types.BIGINT)},
		{"lastPostDate", new Integer(Types.TIMESTAMP)},
		{"priority", new Integer(Types.DOUBLE)}
	};

	public static String TABLE_SQL_CREATE = "create table MBThread (threadId LONG not null primary key,categoryId LONG,rootMessageId LONG,messageCount INTEGER,viewCount INTEGER,lastPostByUserId LONG,lastPostDate DATE null,priority DOUBLE)";

	public static String TABLE_SQL_DROP = "drop table MBThread";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_CB854772 on MBThread (categoryId)"
	};

}