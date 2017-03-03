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

		_sqlTransformer = SQLTransformerFactory.getSQLTransformer(
			new HypersonicSQLTransformerLogic(db));
	}

	@Override
	@Test
	public void testReplaceBitwiseCheck() {
		Assert.assertEquals(getBitwiseCheckSQL(), _sqlTransformer.transform(getBitwiseCheckSQL()));
	}

	@Override
	@Test
	public void testReplaceCastClobText() {
		Assert.assertEquals(
			getCastClobTextSQL(),
			_sqlTransformer.transform("select CAST_CLOB_TEXT(foo) from Foo"));
	}

	@Override
	@Test
	public void testReplaceCastLong() {
		Assert.assertEquals(
			getCastLongSQL(),
			_sqlTransformer.transform(
				"select CAST_LONG(foo) from Foo"));
	}

	@Override
	@Test
	public void testReplaceCrossJoin() {
		Assert.assertEquals(getCrossJoinSQL(), _sqlTransformer.transform(getCrossJoinSQL()));
	}

	@Override
	@Test
	public void testReplaceInstr() {
		Assert.assertEquals(getInstrSQL(), _sqlTransformer.transform(getInstrSQL()));
	}

	@Override
	@Test
	public void testReplaceIntegerDivision() {
		Assert.assertEquals("select foo /  bar from Foo", _sqlTransformer.transform(getIntegerDivisionSQL()));
	}

	@Override
	@Test
	public void testReplaceMod() {
		Assert.assertEquals(getModSQL(), _sqlTransformer.transform(getModSQL()));
	}

	@Override
	@Test
	public void testReplaceNullDate() {
		Assert.assertEquals("select NULL from Foo", _sqlTransformer.transform(getNullDateSQL()));
	}

	@Override
	@Test
	public void testReplaceReplace() {
		Assert.assertEquals(getReplaceSQL(), _sqlTransformer.transform(getReplaceSQL()));
	}

	@Override
	@Test
	public void testReplaceSubst() {
		Assert.assertEquals(getSubstSQL(), _sqlTransformer.transform(getSubstSQL()));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, _sqlTransformer.transform(sql));
	}

	private SQLTransformer _sqlTransformer;

}