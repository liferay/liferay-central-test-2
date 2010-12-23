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

package com.liferay.portal.upgrade.v4_3_3.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SCFrameworkVersionTable {

	public static String TABLE_NAME = "SCFrameworkVersion";

	public static Object[][] TABLE_COLUMNS = {
		{"frameworkVersionId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"name", new Integer(Types.VARCHAR)},
		{"url", new Integer(Types.VARCHAR)},
		{"active_", new Integer(Types.BOOLEAN)},
		{"priority", new Integer(Types.INTEGER)}
	};

	public static String TABLE_SQL_CREATE = "create table SCFrameworkVersion (frameworkVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,url STRING null,active_ BOOLEAN,priority INTEGER)";

	public static String TABLE_SQL_DROP = "drop table SCFrameworkVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C98C0D78 on SCFrameworkVersion (companyId)",
		"create index IX_272991FA on SCFrameworkVersion (groupId)",
		"create index IX_6E1764F on SCFrameworkVersion (groupId, active_)",
	};

}