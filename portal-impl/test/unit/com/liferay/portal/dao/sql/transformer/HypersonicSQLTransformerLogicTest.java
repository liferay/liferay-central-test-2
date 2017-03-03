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
	implements SQLTransformerLogicTestCase {

	@Before
	public void setUp() {
		DB db = new HypersonicDB(1, 0);

		mock(db);

		_sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			new HypersonicSQLTransformerLogic(db));
	}

	@Override
	@Test
	public void testReplaceBitwiseCheck() {
		String sql = "select BITAND(foo, bar) from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceCastClobText() {
		Assert.assertEquals(
			"select CONVERT(foo, SQL_VARCHAR) from Foo",
			_sqlTransformer.transform("select CAST_CLOB_TEXT(foo) from Foo"));
	}

	@Override
	@Test
	public void testReplaceCastLong() {
		Assert.assertEquals(
			"select CONVERT(foo, SQL_BIGINT) from Foo",
			_sqlTransformer.transform(
				"select CAST_LONG(foo) from Foo"));
	}

	@Override
	@Test
	public void testReplaceCrossJoin() {
		String sql = "select * from Foo CROSS JOIN Bar";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceInstr() {
		String sql = "select INSTR(foo) from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceIntegerDivision() {
		String sql = "select INTEGER_DIV(foo, bar) from Foo";

		Assert.assertEquals("select foo /  bar from Foo", _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceMod() {
		String sql = "select MOD(foo, bar) from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceNullDate() {
		Assert.assertEquals("select NULL from Foo", _sqlTransformer.transform("select [$NULL_DATE$] from Foo"));
	}

	@Override
	@Test
	public void testReplaceReplace() {
		String sql = "select replace(foo) from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Override
	@Test
	public void testReplaceSubst() {
		String sql = "select foo from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	private SQLTransformer _sqlTransformer;

}