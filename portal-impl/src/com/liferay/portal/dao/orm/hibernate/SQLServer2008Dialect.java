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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.dialect.SQLServerDialect;

/**
 * @author Steven Cao
 */
public class SQLServer2008Dialect extends SQLServerDialect {

	public String getLimitString(String sql, int offset, int limit) {
		if ((offset == 0) || sql.endsWith(StringPool.CLOSE_PARENTHESIS)) {
			return super.getLimitString(sql, offset, limit);
		}

		String sqlLowerCase = sql.toLowerCase();

		int orderByPos = sqlLowerCase.lastIndexOf("order by");

		if (orderByPos < 0) {
			return super.getLimitString(sql, offset, limit);
		}

		String orderByString = sql.substring(orderByPos + 9, sql.length());

		String[] orderByArray = StringUtil.split(
			orderByString, StringPool.COMMA);

		for (int i = 0; i < orderByArray.length; i++) {
			String orderBy = orderByArray[i].trim();

			String orderByColumn = null;
			String orderByType = null;

			int columnPos = orderBy.indexOf(CharPool.SPACE);

			if (columnPos == -1) {
				orderByColumn = orderBy;
				orderByType = "ASC";
			}
			else {
				orderByColumn = orderBy.substring(0, columnPos);
				orderByType = orderBy.substring(columnPos + 1);
			}

			Pattern pattern = Pattern.compile(
				"(\\S+) as \\Q".concat(orderByColumn).concat("\\E\\W"),
				Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(sql);

			if (matcher.find()) {
				orderByColumn = matcher.group(1);
			}

			orderByArray[i] = orderByColumn.concat(
				StringPool.SPACE).concat(orderByType);
		}

		int fromPos = sqlLowerCase.indexOf("from");

		String selectFrom = sql.substring(0, fromPos);

		String selectFromWhere = sql.substring(fromPos, orderByPos);

		StringBundler sb = new StringBundler(10);

		sb.append("select * from (");
		sb.append(selectFrom);
		sb.append(", row_number() over (order by ");
		sb.append(StringUtil.merge(orderByArray, StringPool.COMMA));
		sb.append(") as _page_row_num ");
		sb.append(selectFromWhere);
		sb.append(" ) temp where _page_row_num between ");
		sb.append(offset + 1);
		sb.append(" and ");
		sb.append(limit);

		return sb.toString();
	}

	public boolean supportsLimitOffset() {
		return _SUPPORTS_LIMIT_OFFSET;
	}

	private static final boolean _SUPPORTS_LIMIT_OFFSET = true;

}