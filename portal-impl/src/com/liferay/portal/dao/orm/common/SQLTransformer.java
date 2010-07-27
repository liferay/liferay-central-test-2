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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class SQLTransformer {

	public static String transform(String sql) {
		return _instance._transform(sql);
	}

	private SQLTransformer() {
		DB db = DBFactoryUtil.getDB();

		if (db.getType().equals(DB.TYPE_MYSQL)) {
			_vendorMySQL = true;
		}
		else if (db.getType().equals(DB.TYPE_ORACLE)) {
			_vendorOracle = true;
		}
		else if (db.getType().equals(DB.TYPE_POSTGRESQL)) {
			_vendorPostgreSQL = true;
		}
		else if (db.getType().equals(DB.TYPE_SQLSERVER)) {
			_vendorSQLServer = true;
		}
	}

	private String _removeLower(String sql) {
		int x = sql.indexOf(_LOWER_OPEN);

		if (x == -1) {
			return sql;
		}

		StringBuilder sb = new StringBuilder(sql.length());

		int y = 0;

		while (true) {
			sb.append(sql.substring(y, x));

			y = sql.indexOf(_LOWER_CLOSE, x);

			if (y == -1) {
				sb.append(sql.substring(x));

				break;
			}

			sb.append(sql.substring(x + _LOWER_OPEN.length(), y));

			y++;

			x = sql.indexOf(_LOWER_OPEN, y);

			if (x == -1) {
				sb.append(sql.substring(y));

				break;
			}
		}

		sql = sb.toString();

		return sql;
	}

	private String _replaceCastText(String sql) {
		Matcher matcher = _castTextPattern.matcher(sql);

		if (_vendorPostgreSQL) {
			return matcher.replaceAll("CAST($1 AS TEXT)");
		}
		else if (_vendorSQLServer) {
			return matcher.replaceAll("CAST($1 AS NVARCHAR)");
		}
		else {
			return matcher.replaceAll("$1");
		}
	}

	private String _replaceMod(String sql) {
		Matcher matcher = _modPattern.matcher(sql);

		return matcher.replaceAll("$1 % $2");
	}

	private String _transform(String sql) {
		if (sql == null) {
			return sql;
		}

		String newSQL = sql;

		if (_vendorMySQL) {
			DB db = DBFactoryUtil.getDB();

			if (!db.isSupportsStringCaseSensitiveQuery()) {
				newSQL = _removeLower(newSQL);
			}
		}
		else if (_vendorOracle) {
			newSQL = _replaceMod(newSQL);
		}
		else if (_vendorSQLServer) {
			newSQL = _replaceMod(newSQL);
		}

		newSQL = _replaceCastText(newSQL);

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		return newSQL;
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private static Log _log = LogFactoryUtil.getLog(SQLTransformer.class);

	private static SQLTransformer _instance = new SQLTransformer();

	private static Pattern _castTextPattern = Pattern.compile(
		"CAST_TEXT\\((.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static Pattern _modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);

	private boolean _vendorMySQL;
	private boolean _vendorOracle;
	private boolean _vendorPostgreSQL;
	private boolean _vendorSQLServer;

}