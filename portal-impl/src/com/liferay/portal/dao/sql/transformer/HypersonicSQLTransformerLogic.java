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

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class HypersonicSQLTransformerLogic extends BaseSQLTransformerLogic {

	public HypersonicSQLTransformerLogic(DB db) {
		super(db);
	}

	@Override
	public Function<String, String>[] getFunctions() {
		return new Function[] {
			getBooleanFunction(), getCastClobTextFunction(),
			getCastLongFunction(), getCastTextFunction(),
			getIntegerDivisionFunction(), getNullDateFunction()
		};
	}

	@Override
	protected String replaceCastLong(Matcher matcher) {
		return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

}