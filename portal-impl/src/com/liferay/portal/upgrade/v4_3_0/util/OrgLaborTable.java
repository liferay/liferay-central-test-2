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
public class OrgLaborTable {

	public static String TABLE_NAME = "OrgLabor";

	public static Object[][] TABLE_COLUMNS = {
		{"orgLaborId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)},
		{"typeId", new Integer(Types.INTEGER)},
		{"sunOpen", new Integer(Types.INTEGER)},
		{"sunClose", new Integer(Types.INTEGER)},
		{"monOpen", new Integer(Types.INTEGER)},
		{"monClose", new Integer(Types.INTEGER)},
		{"tueOpen", new Integer(Types.INTEGER)},
		{"tueClose", new Integer(Types.INTEGER)},
		{"wedOpen", new Integer(Types.INTEGER)},
		{"wedClose", new Integer(Types.INTEGER)},
		{"thuOpen", new Integer(Types.INTEGER)},
		{"thuClose", new Integer(Types.INTEGER)},
		{"friOpen", new Integer(Types.INTEGER)},
		{"friClose", new Integer(Types.INTEGER)},
		{"satOpen", new Integer(Types.INTEGER)},
		{"satClose", new Integer(Types.INTEGER)}
	};

	public static String TABLE_SQL_CREATE = "create table OrgLabor (orgLaborId LONG not null primary key,organizationId LONG,typeId INTEGER,sunOpen INTEGER,sunClose INTEGER,monOpen INTEGER,monClose INTEGER,tueOpen INTEGER,tueClose INTEGER,wedOpen INTEGER,wedClose INTEGER,thuOpen INTEGER,thuClose INTEGER,friOpen INTEGER,friClose INTEGER,satOpen INTEGER,satClose INTEGER)";

	public static String TABLE_SQL_DROP = "drop table OrgLabor";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6AF0D434 on OrgLabor (organizationId)"
	};

}