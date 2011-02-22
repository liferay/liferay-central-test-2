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
public class PollsVoteTable {

	public static String TABLE_NAME = "PollsVote";

	public static Object[][] TABLE_COLUMNS = {
		{"voteId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"questionId", Types.BIGINT},
		{"choiceId", Types.BIGINT},
		{"voteDate", Types.TIMESTAMP}
	};

	public static String TABLE_SQL_CREATE = "create table PollsVote (voteId LONG not null primary key,userId LONG,questionId LONG,choiceId LONG,voteDate DATE null)";

	public static String TABLE_SQL_DROP = "drop table PollsVote";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_D5DF7B54 on PollsVote (choiceId)",
		"create index IX_12112599 on PollsVote (questionId)",
		"create index IX_1BBFD4D3 on PollsVote (questionId, userId)"
	};

}