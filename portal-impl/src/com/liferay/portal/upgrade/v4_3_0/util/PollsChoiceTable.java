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
public class PollsChoiceTable {

	public static String TABLE_NAME = "PollsChoice";

	public static Object[][] TABLE_COLUMNS = {
		{"choiceId", new Integer(Types.BIGINT)},
		{"questionId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)}
	};

	public static String TABLE_SQL_CREATE = "create table PollsChoice (choiceId LONG not null primary key,questionId LONG,name VARCHAR(75) null,description VARCHAR(1000) null)";

	public static String TABLE_SQL_DROP = "drop table PollsChoice";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EC370F10 on PollsChoice (questionId)",
		"create index IX_D76DD2CF on PollsChoice (questionId, name)",
	};

}