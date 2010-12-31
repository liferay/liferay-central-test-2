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
public class WikiNodeTable {

	public static String TABLE_NAME = "WikiNode";

	public static Object[][] TABLE_COLUMNS = {
		{"nodeId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"lastPostDate", new Integer(Types.TIMESTAMP)}
	};

	public static String TABLE_SQL_CREATE = "create table WikiNode (nodeId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description STRING null,lastPostDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table WikiNode";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5D6FE3F0 on WikiNode (companyId)",
		"create index IX_B480A672 on WikiNode (groupId)"
	};

}