/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMContentTable {

	public static final String TABLE_NAME = "DDMContent";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"contentId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"data_", Types.CLOB}
	};

	public static final String TABLE_SQL_CREATE = "create table DDMContent (uuid_ VARCHAR(75) null,contentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name STRING null,description STRING null,data_ TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table DDMContent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_E3BAF436 on DDMContent (companyId)",
		"create index IX_50BF1038 on DDMContent (groupId)",
		"create index IX_3A9C0626 on DDMContent (uuid_, companyId)",
		"create unique index IX_EB9BDE28 on DDMContent (uuid_, groupId)"
	};

}