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
public class UserGroupTable {

	public static String TABLE_NAME = "UserGroup";

	public static Object[][] TABLE_COLUMNS = {
		{"userGroupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"parentUserGroupId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table UserGroup (userGroupId LONG not null primary key,companyId LONG,parentUserGroupId LONG,name VARCHAR(75) null,description STRING null)";

	public static String TABLE_SQL_DROP = "drop table UserGroup";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_524FEFCE on UserGroup (companyId)",
		"create index IX_23EAD0D on UserGroup (companyId, name)",
		"create index IX_69771487 on UserGroup (companyId, parentUserGroupId)"
	};

}