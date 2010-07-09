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

package com.liferay.portal.upgrade.v5_2_3.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class TagsPropertyTable {

	public static final String TABLE_NAME = "TagsProperty";

	public static final Object[][] TABLE_COLUMNS = {
		{"propertyId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"entryId", new Integer(Types.BIGINT)},
		{"key_", new Integer(Types.VARCHAR)},
		{"value", new Integer(Types.VARCHAR)}
	};

	public static final String TABLE_SQL_CREATE = "create table TagsProperty (propertyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,entryId LONG,key_ VARCHAR(75) null,value VARCHAR(255) null)";

	public static final String TABLE_SQL_DROP = "drop table TagsProperty";

}