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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseSQLTransformerLogicTestCase {

	protected void mock(DB db) {
		PowerMockito.mockStatic(DBManagerUtil.class);

		PowerMockito.when(
			DBManagerUtil.getDB()
		).thenReturn(
			db
		);
	}
	
	protected String getBitwiseCheckSQL() {
		return "select BITAND(foo, bar) from Foo";
	}

	protected String getCastClobTextSQL() {
		return "select CAST_CLOB_TEXT(foo) from Foo";
	}

	protected String getCastLongSQL() {
		return "select CONVERT(foo, SQL_BIGINT) from Foo";
	}

	protected String getCrossJoinSQL() {
		return "select * from Foo CROSS JOIN Bar";
	}

	protected String getInstrSQL() {
		return "select INSTR(foo) from Foo";
	}

	protected String getIntegerDivisionSQL() {
		return "select INTEGER_DIV(foo, bar) from Foo";
	}

	protected String getModSQL() {
		return "select MOD(foo, bar) from Foo";
	}

	protected String getNullDateSQL() {
		return "select [$NULL_DATE$] from Foo";
	}

	protected String getReplaceSQL() {
		return "select replace(foo) from Foo";
	}

	protected String getSubstSQL() {
		return "select foo from Foo";
	}

	@Test
	public void testReplaceBitwiseCheck() {
		Assert.assertEquals(getBitwiseCheckSQL(), sqlTransformer.transform(getBitwiseCheckSQL()));
	}

	public abstract void testReplaceCastClobText();

	public abstract void testReplaceCastLong();

	@Test
	public void testReplaceCrossJoin() {
		Assert.assertEquals(getCrossJoinSQL(), sqlTransformer.transform(getCrossJoinSQL()));
	}

	@Test
	public void testReplaceInstr() {
		Assert.assertEquals(getInstrSQL(), sqlTransformer.transform(getInstrSQL()));
	}

	public abstract void testReplaceIntegerDivision();

	@Test
	public void testReplaceMod() {
		Assert.assertEquals(getModSQL(), sqlTransformer.transform(getModSQL()));
	}

	public abstract void testReplaceNullDate();

	@Test
	public void testReplaceReplace() {
		Assert.assertEquals(getReplaceSQL(), sqlTransformer.transform(getReplaceSQL()));
	}

	@Test
	public void testReplaceSubst() {
		Assert.assertEquals(getSubstSQL(), sqlTransformer.transform(getSubstSQL()));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	protected SQLTransformer sqlTransformer;


}