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
public class MBMessageTable {

	public static String TABLE_NAME = "MBMessage";

	public static Object[][] TABLE_COLUMNS = {
		{"messageId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"categoryId", new Integer(Types.BIGINT)},
		{"threadId", new Integer(Types.BIGINT)},
		{"parentMessageId", new Integer(Types.BIGINT)},
		{"subject", new Integer(Types.VARCHAR)},
		{"body", new Integer(Types.CLOB)},
		{"attachments", new Integer(Types.BOOLEAN)},
		{"anonymous", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table MBMessage (messageId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,threadId LONG,parentMessageId LONG,subject VARCHAR(75) null,body TEXT null,attachments BOOLEAN,anonymous BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table MBMessage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3C865EE5 on MBMessage (categoryId)",
		"create index IX_138C7F1E on MBMessage (categoryId, threadId)",
		"create index IX_75B95071 on MBMessage (threadId)",
		"create index IX_A7038CD7 on MBMessage (threadId, parentMessageId)",
	};

}