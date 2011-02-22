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

package com.liferay.portal.upgrade.v4_3_3.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SCProductVersionTable {

	public static String TABLE_NAME = "SCProductVersion";

	public static Object[][] TABLE_COLUMNS = {
		{"productVersionId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"productEntryId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"changeLog", Types.VARCHAR},
		{"downloadPageURL", Types.VARCHAR},
		{"directDownloadURL", Types.VARCHAR},
		{"repoStoreArtifact", Types.BOOLEAN}
	};

	public static String TABLE_SQL_CREATE = "create table SCProductVersion (productVersionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,productEntryId LONG,version VARCHAR(75) null,changeLog STRING null,downloadPageURL STRING null,directDownloadURL STRING null,repoStoreArtifact BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table SCProductVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8377A211 on SCProductVersion (productEntryId)"
	};

}