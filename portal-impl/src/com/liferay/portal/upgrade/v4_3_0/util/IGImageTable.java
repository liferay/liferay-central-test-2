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
public class IGImageTable {

	public static String TABLE_NAME = "IGImage";

	public static Object[][] TABLE_COLUMNS = {
		{"imageId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"folderId", new Integer(Types.BIGINT)},
		{"description", new Integer(Types.VARCHAR)},
		{"smallImageId", new Integer(Types.BIGINT)},
		{"largeImageId", new Integer(Types.BIGINT)}
	};

	public static String TABLE_SQL_CREATE = "create table IGImage (imageId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,folderId LONG,description STRING null,smallImageId LONG,largeImageId LONG)";

	public static String TABLE_SQL_DROP = "drop table IGImage";

}