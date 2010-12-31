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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalFeedTable {

	public static final String TABLE_NAME = "JournalFeed";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"id_", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"feedId", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.VARCHAR)},
		{"structureId", new Integer(Types.VARCHAR)},
		{"templateId", new Integer(Types.VARCHAR)},
		{"rendererTemplateId", new Integer(Types.VARCHAR)},
		{"delta", new Integer(Types.INTEGER)},
		{"orderByCol", new Integer(Types.VARCHAR)},
		{"orderByType", new Integer(Types.VARCHAR)},
		{"targetLayoutFriendlyUrl", new Integer(Types.VARCHAR)},
		{"targetPortletId", new Integer(Types.VARCHAR)},
		{"contentField", new Integer(Types.VARCHAR)},
		{"feedType", new Integer(Types.VARCHAR)},
		{"feedVersion", new Integer(Types.DOUBLE)}
	};

	public static final String TABLE_SQL_CREATE = "create table JournalFeed (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,feedId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,type_ VARCHAR(75) null,structureId VARCHAR(75) null,templateId VARCHAR(75) null,rendererTemplateId VARCHAR(75) null,delta INTEGER,orderByCol VARCHAR(75) null,orderByType VARCHAR(75) null,targetLayoutFriendlyUrl VARCHAR(255) null,targetPortletId VARCHAR(75) null,contentField VARCHAR(75) null,feedType VARCHAR(75) null,feedVersion DOUBLE)";

	public static final String TABLE_SQL_DROP = "drop table JournalFeed";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_35A2DB2F on JournalFeed (groupId)",
		"create unique index IX_65576CBC on JournalFeed (groupId, feedId)",
		"create index IX_50C36D79 on JournalFeed (uuid_)",
		"create unique index IX_39031F51 on JournalFeed (uuid_, groupId)"
	};

}