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
public class PortletPreferencesTable {

	public static String TABLE_NAME = "PortletPreferences";

	public static Object[][] TABLE_COLUMNS = {
		{"portletPreferencesId", new Integer(Types.BIGINT)},
		{"ownerId", new Integer(Types.BIGINT)},
		{"ownerType", new Integer(Types.INTEGER)},
		{"plid", new Integer(Types.BIGINT)},
		{"portletId", new Integer(Types.VARCHAR)},
		{"preferences", new Integer(Types.CLOB)}
	};

	public static String TABLE_SQL_CREATE = "create table PortletPreferences (portletPreferencesId LONG not null primary key,ownerId LONG,ownerType INTEGER,plid LONG,portletId VARCHAR(200) null,preferences TEXT null)";

	public static String TABLE_SQL_DROP = "drop table PortletPreferences";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_E4F13E6E on PortletPreferences (ownerId, ownerType, plid)",
		"create index IX_C7057FF7 on PortletPreferences (ownerId, ownerType, plid, portletId)",
		"create index IX_F15C1C4F on PortletPreferences (plid)",
	};

}