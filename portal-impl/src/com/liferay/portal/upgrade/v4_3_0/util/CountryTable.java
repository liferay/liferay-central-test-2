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
public class CountryTable {

	public static String TABLE_NAME = "Country";

	public static Object[][] TABLE_COLUMNS = {
		{"countryId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"a2", Types.VARCHAR},
		{"a3", Types.VARCHAR},
		{"number_", Types.VARCHAR},
		{"idd_", Types.VARCHAR},
		{"active_", Types.BOOLEAN}
	};

	public static String TABLE_SQL_CREATE = "create table Country (countryId LONG not null primary key,name VARCHAR(75) null,a2 VARCHAR(75) null,a3 VARCHAR(75) null,number_ VARCHAR(75) null,idd_ VARCHAR(75) null,active_ BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table Country";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_25D734CD on Country (active_)"
	};

}