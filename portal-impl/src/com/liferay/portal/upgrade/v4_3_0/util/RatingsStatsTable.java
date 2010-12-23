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
public class RatingsStatsTable {

	public static String TABLE_NAME = "RatingsStats";

	public static Object[][] TABLE_COLUMNS = {
		{"statsId", new Integer(Types.BIGINT)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"totalEntries", new Integer(Types.INTEGER)},
		{"totalScore", new Integer(Types.DOUBLE)},
		{"averageScore", new Integer(Types.DOUBLE)}
	};

	public static String TABLE_SQL_CREATE = "create table RatingsStats (statsId LONG not null primary key,classNameId LONG,classPK LONG,totalEntries INTEGER,totalScore DOUBLE,averageScore DOUBLE)";

	public static String TABLE_SQL_DROP = "drop table RatingsStats";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A6E99284 on RatingsStats (classNameId, classPK)",
	};

}