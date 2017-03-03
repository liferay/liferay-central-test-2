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
	
	protected String getBitwiseCheckInputSQL() {
		return "select BITAND(foo, bar) from Foo";
	}

	protected String getBitwiseCheckOutputSQL() {
		return getBitwiseCheckInputSQL();
	}

	protected String getCastClobTextInputSQL() {
		return "select CAST_CLOB_TEXT(foo) from Foo";
	}

	protected String getCastClobTextOutputSQL() {
		return getCastClobTextInputSQL();
	}

	protected String getCastLongInputSQL() {
		return "select CONVERT(foo, SQL_BIGINT) from Foo";
	}

	protected String getCastLongOutputSQL() {
		return getCastLongInputSQL();
	}

	protected String getCrossJoinInputSQL() {
		return "select * from Foo CROSS JOIN Bar";
	}

	protected String getCrossJoinOutputSQL() {
		return getCrossJoinInputSQL();
	}

	protected String getInstrInputSQL() {
		return "select INSTR(foo) from Foo";
	}

	protected String getInstrOutputSQL() {
		return getInstrInputSQL();
	}

	protected String getIntegerDivisionInputSQL() {
		return "select INTEGER_DIV(foo, bar) from Foo";
	}

	protected String getIntegerDivisionOutputSQL() {
		return getIntegerDivisionInputSQL();
	}

	protected String getModInputSQL() {
		return "select MOD(foo, bar) from Foo";
	}

	protected String getModOutputSQL() {
		return getModInputSQL();
	}

	protected String getNullDateInputSQL() {
		return "select [$NULL_DATE$] from Foo";
	}

	protected String getNullDateOutputSQL() {
		return getNullDateInputSQL();
	}

	protected String getReplaceInputSQL() {
		return "select replace(foo) from Foo";
	}

	protected String getReplaceOutputSQL() {
		return getReplaceInputSQL();
	}

	protected String getSubstInputSQL() {
		return "select foo from Foo";
	}

	protected String getSubstOutputSQL() {
		return getSubstInputSQL();
	}

	@Test
	public void testReplaceBitwiseCheck() {
		Assert.assertEquals(getBitwiseCheckOutputSQL(), sqlTransformer.transform(getBitwiseCheckInputSQL()));
	}

	@Test
	public void testReplaceCastClobText() {
		Assert.assertEquals(getCastClobTextOutputSQL(), sqlTransformer.transform(getCastClobTextInputSQL()));
	}

	@Test
	public void testReplaceCastLong() {
		Assert.assertEquals(getCastLongOutputSQL(), sqlTransformer.transform(getCastLongInputSQL()));
	}

	@Test
	public void testReplaceCrossJoin() {
		Assert.assertEquals(getCrossJoinOutputSQL(), sqlTransformer.transform(getCrossJoinInputSQL()));
	}

	@Test
	public void testReplaceInstr() {
		Assert.assertEquals(getInstrOutputSQL(), sqlTransformer.transform(getInstrInputSQL()));
	}

	@Test
	public void testReplaceIntegerDivision() {
		Assert.assertEquals(getIntegerDivisionOutputSQL(), sqlTransformer.transform(getIntegerDivisionInputSQL()));
	}

	@Test
	public void testReplaceMod() {
		Assert.assertEquals(getModOutputSQL(), sqlTransformer.transform(getModInputSQL()));
	}

	@Test
	public void testReplaceNullDate() {
		Assert.assertEquals(getNullDateOutputSQL(), sqlTransformer.transform(getNullDateInputSQL()));
	}

	@Test
	public void testReplaceReplace() {
		Assert.assertEquals(getReplaceOutputSQL(), sqlTransformer.transform(getReplaceInputSQL()));
	}

	@Test
	public void testReplaceSubst() {
		Assert.assertEquals(getSubstOutputSQL(), sqlTransformer.transform(getSubstInputSQL()));
	}

	@Test
	public void testTransform() {
		String sql = "select * from Foo";

		Assert.assertEquals(sql, sqlTransformer.transform(sql));
	}

	protected SQLTransformer sqlTransformer;


}