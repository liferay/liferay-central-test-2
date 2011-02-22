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
public class JournalContentSearchTable {

	public static String TABLE_NAME = "JournalContentSearch";

	public static Object[][] TABLE_COLUMNS = {
		{"contentSearchId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"privateLayout", Types.BOOLEAN},
		{"layoutId", Types.BIGINT},
		{"portletId", Types.VARCHAR},
		{"articleId", Types.VARCHAR}
	};

	public static String TABLE_SQL_CREATE = "create table JournalContentSearch (contentSearchId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,portletId VARCHAR(200) null,articleId VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table JournalContentSearch";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6838E427 on JournalContentSearch (groupId, articleId)",
		"create index IX_20962903 on JournalContentSearch (groupId, privateLayout)",
		"create index IX_7CC7D73E on JournalContentSearch (groupId, privateLayout, articleId)",
		"create index IX_B3B318DC on JournalContentSearch (groupId, privateLayout, layoutId)",
		"create index IX_C3AA93B8 on JournalContentSearch (groupId, privateLayout, layoutId, portletId, articleId)"
	};

}