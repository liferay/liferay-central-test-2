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
public class PollsVoteTable {

	public static String TABLE_NAME = "PollsVote";

	public static Object[][] TABLE_COLUMNS = {
		{"voteId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"questionId", new Integer(Types.BIGINT)},
		{"choiceId", new Integer(Types.BIGINT)},
		{"voteDate", new Integer(Types.TIMESTAMP)}
	};

	public static String TABLE_SQL_CREATE = "create table PollsVote (voteId LONG not null primary key,userId LONG,questionId LONG,choiceId LONG,voteDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table PollsVote";

}