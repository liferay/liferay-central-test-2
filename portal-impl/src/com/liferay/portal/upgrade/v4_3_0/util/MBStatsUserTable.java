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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class MBStatsUserTable {

	public static String TABLE_NAME = "MBStatsUser";

	public static Object[][] TABLE_COLUMNS = {
		{"statsUserId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"messageCount", new Integer(Types.INTEGER)},
		{"lastPostDate", new Integer(Types.TIMESTAMP)}
	};

	public static String TABLE_SQL_CREATE = "create table MBStatsUser (statsUserId LONG not null primary key,groupId LONG,userId LONG,messageCount INTEGER,lastPostDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table MBStatsUser";

}