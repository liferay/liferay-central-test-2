/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="SQLTransformer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SQLTransformer {

	public static String transform(String sql) {
		return _instance._transform(sql);
	}

	private SQLTransformer() {
		_sqlMap = new HashMap<String, String>();

		DB db = DBFactoryUtil.getDB();

		if (db.getType().equals(DB.TYPE_MYSQL)) {
			_vendorMySQL = true;
		}
	}

	private String _removeLower(String sql) {
		int x = sql.indexOf(_LOWER_OPEN);

		if (x == -1) {
			return sql;
		}

		String newSQL = _sqlMap.get(sql);

		if (newSQL != null) {
			return newSQL;
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

		newSQL = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL " + sql);
			_log.debug("Modified SQL " + newSQL);
		}

		_sqlMap.put(sql, newSQL);

		return newSQL;
	}

	private String _transform(String sql) {
		if (sql == null) {
			return sql;
		}

		if (_vendorMySQL &&
			!PropsValues.DATABASE_MYSQL_FUNCTION_LOWER_ENABLED) {

			sql = _removeLower(sql);
		}

		return sql;
	}

	private static final String _LOWER_CLOSE = StringPool.CLOSE_PARENTHESIS;

	private static final String _LOWER_OPEN = "lower(";

	private static Log _log = LogFactoryUtil.getLog(SQLTransformer.class);

	private static SQLTransformer _instance = new SQLTransformer();

	private Map<String, String> _sqlMap;
	private boolean _vendorMySQL;

}