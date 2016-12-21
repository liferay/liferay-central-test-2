/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mariano Alvaro Saiz
 */
public class SqlQueryTest {

	@Test
	public void testWhenMatchingDBTypeMatchingIsReturned() {
		SqlQuery sqlQuery = new SqlQuery(_DEFAULT_SQL);

		sqlQuery.addSpecificSQL(DBType.MYSQL, _DEFAULT_MYSQL_SQL);

		String sql = sqlQuery.getDBSQL(DBType.MYSQL);

		Assert.assertEquals(_DEFAULT_MYSQL_SQL, sql);
	}

	@Test
	public void testWhenNoMatchingDBTypeDefaultIsReturned() {
		SqlQuery sqlQuery = new SqlQuery(_DEFAULT_SQL);

		sqlQuery.addSpecificSQL(DBType.MYSQL, _DEFAULT_MYSQL_SQL);

		String sql = sqlQuery.getDBSQL(DBType.ORACLE);

		Assert.assertEquals(_DEFAULT_SQL, sql);
	}

	private static final String _DEFAULT_MYSQL_SQL = "select * from myTable";

	private static final String _DEFAULT_SQL = "select * from table";

}