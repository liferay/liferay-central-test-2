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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.dao.db.HypersonicDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest(DBManagerUtil.class)
@RunWith(PowerMockRunner.class)
public class HypersonicSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	@Before
	public void setUp() {
		DB db = new HypersonicDB(1, 0);

		mock(db);

		sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			new HypersonicSQLTransformerLogic(db));
	}

	@Override
	protected String getCastClobTextOutputSQL() {
		return "select CONVERT(foo, SQL_VARCHAR) from Foo";
	}

	@Override
	protected String getCastLongOutputSQL() {
		return "select CONVERT(foo, SQL_BIGINT) from Foo";
	}

	@Override
	protected String getIntegerDivisionOutputSQL() {
		return "select foo /  bar from Foo";
	}

	@Override
	protected String getNullDateOutputSQL() {
		return "select NULL from Foo";
	}

}