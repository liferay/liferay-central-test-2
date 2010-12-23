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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SocialRelationTable {

	public static final String TABLE_NAME = "SocialRelation";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"relationId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.BIGINT)},
		{"userId1", new Integer(Types.BIGINT)},
		{"userId2", new Integer(Types.BIGINT)},
		{"type_", new Integer(Types.INTEGER)}
	};

	public static final String TABLE_SQL_CREATE = "create table SocialRelation (uuid_ VARCHAR(75) null,relationId LONG not null primary key,companyId LONG,createDate LONG,userId1 LONG,userId2 LONG,type_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table SocialRelation";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_61171E99 on SocialRelation (companyId)",
		"create index IX_95135D1C on SocialRelation (companyId, type_)",
		"create index IX_C31A64C6 on SocialRelation (type_)",
		"create index IX_5A40CDCC on SocialRelation (userId1)",
		"create index IX_4B52BE89 on SocialRelation (userId1, type_)",
		"create unique index IX_12A92145 on SocialRelation (userId1, userId2, type_)",
		"create index IX_5A40D18D on SocialRelation (userId2)",
		"create index IX_3F9C2FA8 on SocialRelation (userId2, type_)",
		"create index IX_F0CA24A5 on SocialRelation (uuid_)"
	};

}