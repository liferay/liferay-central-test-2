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

package com.liferay.portal.upgrade.v5_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OrganizationTable {

	public static final String TABLE_NAME = "Organization_";

	public static final Object[][] TABLE_COLUMNS = {
		{"organizationId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"parentOrganizationId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.VARCHAR)},
		{"recursable", new Integer(Types.BOOLEAN)},
		{"regionId", new Integer(Types.BIGINT)},
		{"countryId", new Integer(Types.BIGINT)},
		{"statusId", new Integer(Types.INTEGER)},
		{"comments", new Integer(Types.VARCHAR)}
	};

	public static final String TABLE_SQL_CREATE = "create table Organization_ (organizationId LONG not null primary key,companyId LONG,parentOrganizationId LONG,name VARCHAR(100) null,type_ VARCHAR(75) null,recursable BOOLEAN,regionId LONG,countryId LONG,statusId INTEGER,comments STRING null)";

	public static final String TABLE_SQL_DROP = "drop table Organization_";

}