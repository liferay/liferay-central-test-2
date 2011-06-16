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
public class MBCategoryTable {

	public static String TABLE_NAME = "MBCategory";

	public static Object[][] TABLE_COLUMNS = {
		{"categoryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"parentCategoryId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"lastPostDate", Types.TIMESTAMP}
	};

	public static String TABLE_SQL_CREATE = "create table MBCategory (categoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentCategoryId LONG,name VARCHAR(75) null,description STRING null,lastPostDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table MBCategory";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_BC735DCF on MBCategory (companyId)",
		"create index IX_BB870C11 on MBCategory (groupId)",
		"create index IX_ED292508 on MBCategory (groupId, parentCategoryId)"
	};

}