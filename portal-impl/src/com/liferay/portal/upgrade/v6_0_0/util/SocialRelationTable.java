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

}