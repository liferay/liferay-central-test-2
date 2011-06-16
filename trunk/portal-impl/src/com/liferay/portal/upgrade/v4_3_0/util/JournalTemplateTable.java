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
public class JournalTemplateTable {

	public static String TABLE_NAME = "JournalTemplate";

	public static Object[][] TABLE_COLUMNS = {
		{"id_", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"templateId", Types.VARCHAR},
		{"structureId", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"xsl", Types.CLOB},
		{"langType", Types.VARCHAR},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR}
	};

	public static String TABLE_SQL_CREATE = "create table JournalTemplate (id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,templateId VARCHAR(75) null,structureId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,xsl TEXT null,langType VARCHAR(75) null,smallImage BOOLEAN,smallImageId LONG,smallImageURL VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table JournalTemplate";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_77923653 on JournalTemplate (groupId)",
		"create index IX_1701CB2B on JournalTemplate (groupId, structureId)",
		"create index IX_E802AA3C on JournalTemplate (groupId, templateId)",
		"create index IX_1B12CA20 on JournalTemplate (templateId)"
	};

}