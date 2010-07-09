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

package com.liferay.portal.upgrade.v5_1_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryTable {

	public static final String TABLE_NAME = "BlogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"entryId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"title", new Integer(Types.VARCHAR)},
		{"urlTitle", new Integer(Types.VARCHAR)},
		{"content", new Integer(Types.CLOB)},
		{"displayDate", new Integer(Types.TIMESTAMP)},
		{"draft", new Integer(Types.BOOLEAN)},
		{"allowTrackbacks", new Integer(Types.BOOLEAN)},
		{"trackbacks", new Integer(Types.CLOB)}
	};

	public static final String TABLE_SQL_CREATE = "create table BlogsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(150) null,urlTitle VARCHAR(150) null,content TEXT null,displayDate DATE null,draft BOOLEAN,allowTrackbacks BOOLEAN,trackbacks TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table BlogsEntry";

}