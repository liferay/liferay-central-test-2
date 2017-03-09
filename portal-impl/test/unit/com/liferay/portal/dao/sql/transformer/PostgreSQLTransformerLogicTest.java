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

import com.liferay.portal.dao.db.PostgreSQLDB;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformerLogicTest
	extends BaseSQLTransformerLogicTestCase {

	public PostgreSQLTransformerLogicTest() {
		super(new PostgreSQLDB(1, 0));
	}

	@Override
	@Test
	public void testReplaceModWithWhitespacesSurroundingCommas() {
		Assert.assertEquals(
			getModTransformedSQL(),
			sqlTransformer.transform(getModOriginalSQL()));
	}

	@Test
	public void testTransformReplaceNegativeComparison() {
		String sql = "select * from Foo where foo != -1";

		String transformedSql = sqlTransformer.transform(sql);

		Assert.assertEquals(
			"select * from Foo where foo != ( -1)", transformedSql);
	}

	@Override
	protected String getBitwiseCheckTransformedSQL() {
		return "select (foo & bar) from Foo";
	}

	@Override
	protected String getCastClobTextTransformedSQL() {
		return "select CAST(foo AS TEXT) from Foo";
	}

	@Override
	protected String getCastLongOriginalSQL() {
		return "select CAST_LONG(foo) from Foo";
	}

	@Override
	protected String getCastLongTransformedSQL() {
		return "select foo from Foo";
	}

	@Override
	protected String getIntegerDivisionTransformedSQL() {
		return "select foo / bar from Foo";
	}

	@Override
	protected String getNullDateTransformedSQL() {
		return "select CAST(NULL AS TIMESTAMP) from Foo";
	}

}