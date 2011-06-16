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
public class PhoneTable {

	public static String TABLE_NAME = "Phone";

	public static Object[][] TABLE_COLUMNS = {
		{"phoneId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"number_", Types.VARCHAR},
		{"extension", Types.VARCHAR},
		{"typeId", Types.INTEGER},
		{"primary_", Types.BOOLEAN}
	};

	public static String TABLE_SQL_CREATE = "create table Phone (phoneId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,number_ VARCHAR(75) null,extension VARCHAR(75) null,typeId INTEGER,primary_ BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table Phone";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_9F704A14 on Phone (companyId)",
		"create index IX_A2E4AFBA on Phone (companyId, classNameId)",
		"create index IX_9A53569 on Phone (companyId, classNameId, classPK)",
		"create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_)",
		"create index IX_F202B9CE on Phone (userId)"
	};

}