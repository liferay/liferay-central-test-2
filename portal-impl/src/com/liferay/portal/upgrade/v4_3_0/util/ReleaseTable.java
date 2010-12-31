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
public class ReleaseTable {

	public static String TABLE_NAME = "Release_";

	public static Object[][] TABLE_COLUMNS = {
		{"releaseId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"buildNumber", new Integer(Types.INTEGER)},
		{"buildDate", new Integer(Types.TIMESTAMP)},
		{"verified", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table Release_ (releaseId LONG not null primary key,createDate DATE null,modifiedDate DATE null,buildNumber INTEGER,buildDate DATE null,verified BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table Release_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}