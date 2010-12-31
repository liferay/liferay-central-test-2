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
public class PermissionTable {

	public static String TABLE_NAME = "Permission_";

	public static Object[][] TABLE_COLUMNS = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"actionId", new Integer(Types.VARCHAR)},
		{"resourceId", new Integer(Types.BIGINT)}
	};

	public static String TABLE_SQL_CREATE = "create table Permission_ (permissionId LONG not null primary key,companyId LONG,actionId VARCHAR(75) null,resourceId LONG)";

	public static String TABLE_SQL_DROP = "drop table Permission_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4D19C2B8 on Permission_ (actionId, resourceId)",
		"create index IX_F090C113 on Permission_ (resourceId)"
	};

}