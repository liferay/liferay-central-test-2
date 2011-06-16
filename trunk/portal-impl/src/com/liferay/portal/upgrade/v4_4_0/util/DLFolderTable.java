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

package com.liferay.portal.upgrade.v4_4_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLFolderTable {

	public static final String TABLE_NAME = "DLFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"parentFolderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"lastPostDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentFolderId LONG,name VARCHAR(100) null,description STRING null,lastPostDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A74DB14C on DLFolder (companyId)",
		"create index IX_F2EA1ACE on DLFolder (groupId)",
		"create index IX_49C37475 on DLFolder (groupId, parentFolderId)",
		"create index IX_902FD874 on DLFolder (groupId, parentFolderId, name)",
		"create index IX_51556082 on DLFolder (parentFolderId, name)",
		"create index IX_CBC408D8 on DLFolder (uuid_)",
		"create index IX_3CC1DED2 on DLFolder (uuid_, groupId)"
	};

}