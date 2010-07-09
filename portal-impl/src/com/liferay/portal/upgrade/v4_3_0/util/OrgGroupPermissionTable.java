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
public class OrgGroupPermissionTable {

	public static String TABLE_NAME = "OrgGroupPermission";

	public static Object[][] TABLE_COLUMNS = {
		{"organizationId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	public static String TABLE_SQL_CREATE = "create table OrgGroupPermission (organizationId LONG not null,groupId LONG not null,permissionId LONG not null,primary key (organizationId, groupId, permissionId))";

	public static String TABLE_SQL_DROP = "drop table OrgGroupPermission";

}