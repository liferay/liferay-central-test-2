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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutTable {

	public static String TABLE_NAME = "Layout";

	public static Object[][] TABLE_COLUMNS = {
		{"plid", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"privateLayout", new Integer(Types.BOOLEAN)},
		{"layoutId", new Integer(Types.BIGINT)},
		{"parentLayoutId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"title", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.VARCHAR)},
		{"typeSettings", new Integer(Types.CLOB)},
		{"hidden_", new Integer(Types.BOOLEAN)},
		{"friendlyURL", new Integer(Types.VARCHAR)},
		{"iconImage", new Integer(Types.BOOLEAN)},
		{"iconImageId", new Integer(Types.BIGINT)},
		{"themeId", new Integer(Types.VARCHAR)},
		{"colorSchemeId", new Integer(Types.VARCHAR)},
		{"wapThemeId", new Integer(Types.VARCHAR)},
		{"wapColorSchemeId", new Integer(Types.VARCHAR)},
		{"css", new Integer(Types.VARCHAR)},
		{"priority", new Integer(Types.INTEGER)}
	};

	public static String TABLE_SQL_CREATE = "create table Layout (plid LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,name STRING null,title STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,friendlyURL VARCHAR(100) null,iconImage BOOLEAN,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,priority INTEGER)";

	public static String TABLE_SQL_DROP = "drop table Layout";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_705F5AA3 on Layout (groupId, privateLayout)",
		"create index IX_BC2C4231 on Layout (groupId, privateLayout, friendlyURL)",
		"create index IX_7162C27C on Layout (groupId, privateLayout, layoutId)",
		"create index IX_6DE88B06 on Layout (groupId, privateLayout, parentLayoutId)",
	};

}