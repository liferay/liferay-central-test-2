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
public class SocialActivityTable {

	public static final String TABLE_NAME = "SocialActivity";

	public static final Object[][] TABLE_COLUMNS = {
		{"activityId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.BIGINT)},
		{"mirrorActivityId", new Integer(Types.BIGINT)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"type_", new Integer(Types.INTEGER)},
		{"extraData", new Integer(Types.VARCHAR)},
		{"receiverUserId", new Integer(Types.BIGINT)}
	};

	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,mirrorActivityId LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG)";

	public static final String TABLE_SQL_DROP = "drop table SocialActivity";

}