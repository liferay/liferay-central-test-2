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
public class RoleTable {

	public static String TABLE_NAME = "Role_";

	public static Object[][] TABLE_COLUMNS = {
		{"roleId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.INTEGER)}
	};

	public static String TABLE_SQL_CREATE = "create table Role_ (roleId LONG not null primary key,companyId LONG,classNameId LONG,classPK LONG,name VARCHAR(75) null,description STRING null,type_ INTEGER)";

	public static String TABLE_SQL_DROP = "drop table Role_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_449A10B9 on Role_ (companyId)",
		"create index IX_A88E424E on Role_ (companyId, classNameId, classPK)",
		"create index IX_EBC931B8 on Role_ (companyId, name)"
	};

}