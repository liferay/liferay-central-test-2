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
public class ContactTable {

	public static String TABLE_NAME = "Contact_";

	public static Object[][] TABLE_COLUMNS = {
		{"contactId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"accountId", Types.BIGINT},
		{"parentContactId", Types.BIGINT},
		{"firstName", Types.VARCHAR},
		{"middleName", Types.VARCHAR},
		{"lastName", Types.VARCHAR},
		{"prefixId", Types.INTEGER},
		{"suffixId", Types.INTEGER},
		{"male", Types.BOOLEAN},
		{"birthday", Types.TIMESTAMP},
		{"smsSn", Types.VARCHAR},
		{"aimSn", Types.VARCHAR},
		{"icqSn", Types.VARCHAR},
		{"jabberSn", Types.VARCHAR},
		{"msnSn", Types.VARCHAR},
		{"skypeSn", Types.VARCHAR},
		{"ymSn", Types.VARCHAR},
		{"employeeStatusId", Types.VARCHAR},
		{"employeeNumber", Types.VARCHAR},
		{"jobTitle", Types.VARCHAR},
		{"jobClass", Types.VARCHAR},
		{"hoursOfOperation", Types.VARCHAR}
	};

	public static String TABLE_SQL_CREATE = "create table Contact_ (contactId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,accountId LONG,parentContactId LONG,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,prefixId INTEGER,suffixId INTEGER,male BOOLEAN,birthday DATE null,smsSn VARCHAR(75) null,aimSn VARCHAR(75) null,icqSn VARCHAR(75) null,jabberSn VARCHAR(75) null,msnSn VARCHAR(75) null,skypeSn VARCHAR(75) null,ymSn VARCHAR(75) null,employeeStatusId VARCHAR(75) null,employeeNumber VARCHAR(75) null,jobTitle VARCHAR(100) null,jobClass VARCHAR(75) null,hoursOfOperation VARCHAR(75) null)";

	public static String TABLE_SQL_DROP = "drop table Contact_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_66D496A3 on Contact_ (companyId)"
	};

}