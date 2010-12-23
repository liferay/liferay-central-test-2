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
public class WikiPageTable {

	public static String TABLE_NAME = "WikiPage";

	public static Object[][] TABLE_COLUMNS = {
		{"pageId", new Integer(Types.BIGINT)},
		{"resourcePrimKey", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"nodeId", new Integer(Types.BIGINT)},
		{"title", new Integer(Types.VARCHAR)},
		{"version", new Integer(Types.DOUBLE)},
		{"content", new Integer(Types.CLOB)},
		{"format", new Integer(Types.VARCHAR)},
		{"head", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table WikiPage (pageId LONG not null primary key,resourcePrimKey LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,nodeId LONG,title VARCHAR(75) null,version DOUBLE,content TEXT null,format VARCHAR(75) null,head BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table WikiPage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C8A9C476 on WikiPage (nodeId)",
		"create index IX_E7F635CA on WikiPage (nodeId, head)",
		"create index IX_997EEDD2 on WikiPage (nodeId, title)",
		"create index IX_E745EA26 on WikiPage (nodeId, title, head)",
		"create index IX_3D4AF476 on WikiPage (nodeId, title, version)",
	};

}