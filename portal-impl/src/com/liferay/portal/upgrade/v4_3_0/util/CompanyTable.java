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
public class CompanyTable {

	public static String TABLE_NAME = "Company";

	public static Object[][] TABLE_COLUMNS = {
		{"companyId", Types.BIGINT},
		{"accountId", Types.BIGINT},
		{"webId", Types.VARCHAR},
		{"key_", Types.CLOB},
		{"virtualHost", Types.VARCHAR},
		{"mx", Types.VARCHAR},
		{"logoId", Types.BIGINT}
	};

	public static String TABLE_SQL_CREATE = "create table Company (companyId LONG not null primary key,accountId LONG,webId VARCHAR(75) null,key_ TEXT null,virtualHost VARCHAR(75) null,mx VARCHAR(75) null,logoId LONG)";

	public static String TABLE_SQL_DROP = "drop table Company";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_12566EC2 on Company (mx)",
		"create index IX_975996C0 on Company (virtualHost)",
		"create index IX_EC00543C on Company (webId)"
	};

}