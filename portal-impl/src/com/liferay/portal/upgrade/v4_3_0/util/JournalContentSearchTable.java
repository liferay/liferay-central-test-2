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
public class JournalContentSearchTable {

	public static String TABLE_NAME = "JournalContentSearch";

	public static Object[][] TABLE_COLUMNS = {
		{"contentSearchId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"privateLayout", new Integer(Types.BOOLEAN)},
		{"layoutId", new Integer(Types.BIGINT)},
		{"portletId", new Integer(Types.VARCHAR)},
		{"articleId", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table JournalContentSearch (contentSearchId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,portletId VARCHAR(200) null,articleId VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table JournalContentSearch";

}