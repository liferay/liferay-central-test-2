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
public class SocialRequestTable {

	public static final String TABLE_NAME = "SocialRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"requestId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.BIGINT)},
		{"modifiedDate", new Integer(Types.BIGINT)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"type_", new Integer(Types.INTEGER)},
		{"extraData", new Integer(Types.VARCHAR)},
		{"receiverUserId", new Integer(Types.BIGINT)},
		{"status", new Integer(Types.INTEGER)}
	};

	public static final String TABLE_SQL_CREATE = "create table SocialRequest (uuid_ VARCHAR(75) null,requestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,modifiedDate LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table SocialRequest";

}