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
public class PasswordTrackerTable {

	public static String TABLE_NAME = "PasswordTracker";

	public static Object[][] TABLE_COLUMNS = {
		{"passwordTrackerId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"password_", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table PasswordTracker (passwordTrackerId LONG not null primary key,userId LONG,createDate DATE null,password_ VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table PasswordTracker";

}