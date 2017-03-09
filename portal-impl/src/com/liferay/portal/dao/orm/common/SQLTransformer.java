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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.sql.transformer.SQLTransformerFactory;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SQLTransformer {

	public static void reloadSQLTransformer() {
		_instance._reloadSQLTransformer();
	}

	public static String transform(String sql) {
		com.liferay.portal.dao.sql.transformer.SQLTransformer sqlTransformer =
			_instance._getSQLTransformer();

		return sqlTransformer.transform(sql);
	}

	public static String transformFromHqlToJpql(String sql) {
		return _instance._transformFromHqlToJpql(sql);
	}

	public static String transformFromJpqlToHql(String sql) {
		return _instance._transformFromJpqlToHql(sql);
	}

	private SQLTransformer() {
		_reloadSQLTransformer();
	}

	private com.liferay.portal.dao.sql.transformer.SQLTransformer
		_getSQLTransformer() {

		return _sqlTransformer;
	}

	private void _reloadSQLTransformer() {
		if (_transformedSqls == null) {
			_transformedSqls = new ConcurrentHashMap<>();
		}
		else {
			_transformedSqls.clear();
		}

		DB db = DBManagerUtil.getDB();

		_sqlTransformer = SQLTransformerFactory.getSQLTransformer(db);
	}

	private String _transformFromHqlToJpql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		newSQL = _transformPositionalParams(newSQL);

		newSQL = StringUtil.replace(newSQL, _HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
		newSQL = StringUtil.replace(
			newSQL, _HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformFromJpqlToHql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _sqlTransformer.transform(sql);

		Matcher matcher = _jpqlCountPattern.matcher(newSQL);

		if (matcher.find()) {
			String countExpression = matcher.group(1);
			String entityAlias = matcher.group(3);

			if (entityAlias.equals(countExpression)) {
				newSQL = matcher.replaceFirst(_HQL_COUNT_SQL);
			}
		}

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformPositionalParams(String queryString) {
		if (queryString.indexOf(CharPool.QUESTION) == -1) {
			return queryString;
		}

		StringBundler sb = new StringBundler();

		int i = 1;
		int from = 0;
		int to = 0;

		while ((to = queryString.indexOf(CharPool.QUESTION, from)) != -1) {
			sb.append(queryString.substring(from, to));
			sb.append(StringPool.QUESTION);
			sb.append(i++);

			from = to + 1;
		}

		sb.append(queryString.substring(from));

		return sb.toString();
	}

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_COUNT_SQL = "SELECT COUNT(*) FROM $2 $3";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

	private static final SQLTransformer _instance = new SQLTransformer();

	private static final Pattern _jpqlCountPattern = Pattern.compile(
		"SELECT COUNT\\((\\S+)\\) FROM (\\S+) (\\S+)");

	private com.liferay.portal.dao.sql.transformer.SQLTransformer
		_sqlTransformer;
	private Map<String, String> _transformedSqls;

}