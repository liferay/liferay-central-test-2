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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.hibernate.dialect.SQLServerDialect;

/**
 * @author Steven Cao
 */
public class SQLServer2008Dialect extends SQLServerDialect {

	public String getLimitString(String sql, int offset, int limit) {
		String lowerCaseSql = sql.toLowerCase();

		int lastOrderByPos = lowerCaseSql.lastIndexOf("order by");

		if ((lastOrderByPos < 0) || (offset == 0) ||
			StringUtil.endsWith(sql, StringPool.CLOSE_PARENTHESIS)) {

			return super.getLimitString(sql, 0, limit);
		}
		else {
			int fromPos = lowerCaseSql.indexOf("from");

			String orderBy = sql.substring(lastOrderByPos, sql.length());

			String selectFrom = sql.substring(0, fromPos);

			String selectFromWhere = sql.substring(fromPos, lastOrderByPos);

			StringBundler sb = new StringBundler(10);

			sb.append("select * from (");
			sb.append(selectFrom);
			sb.append(", _page_row_num = row_number() over(");
			sb.append(orderBy);
			sb.append(")");
			sb.append(selectFromWhere);
			sb.append(" ) temp where _page_row_num between ");
			sb.append(offset + 1);
			sb.append(" and ");
			sb.append(limit);

			return sb.toString();
		}
	}

	public boolean supportsLimitOffset() {
		return _SUPPORTS_LIMIT_OFFSET;
	}

	private static final boolean _SUPPORTS_LIMIT_OFFSET = true;

}