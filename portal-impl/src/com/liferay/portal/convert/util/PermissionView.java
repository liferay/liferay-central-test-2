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

package com.liferay.portal.convert.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.util.Table;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PermissionView.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class PermissionView extends Table {

	public static String getActionId(String[] values) {
		return values[values.length - 4];
	}

	public static long getCompanyId(String[] values) {
		return Long.parseLong(values[values.length - 5]);
	}

	public static String getNameId(String[] values) {
		return values[values.length - 2];
	}

	public static long getPermissionId(String[] values) {
		return Long.parseLong(values[values.length - 6]);
	}

	public static long getPrimaryKey(String[] values) {
		return Long.parseLong(values[0]);
	}

	public static long getResourceId(String[] values) {
		return Long.parseLong(values[values.length - 3]);
	}

	public static int getScopeId(String[] values) {
		return Integer.parseInt(values[values.length - 1]);
	}

	public PermissionView(String tableName, String[] primKeys) {
		super(tableName);

		List<Object[]> columns = new ArrayList<Object[]>();

		for (String primKey : primKeys) {
			columns.add(new Object[] {primKey, Types.BIGINT});
		}

		columns.add(new Object[] {"permissionId", Types.BIGINT});
		columns.add(new Object[] {"companyId", Types.BIGINT});
		columns.add(new Object[] {"actionId", Types.VARCHAR});
		columns.add(new Object[] {"resourceId", Types.BIGINT});
		columns.add(new Object[] {"name", Types.VARCHAR});
		columns.add(new Object[] {"scope", Types.INTEGER});

		setColumns(columns.toArray(new Object[0][]));
	}

	public String getSelectSQL() throws Exception {
		return StringUtil.replace(_SELECT_SQL, "_OLD_TABLE_", getTableName());
	}

	private static final String _SELECT_SQL =
		"SELECT _OLD_TABLE_.*, Permission_.companyId, Permission_.actionId, " +
		"Resource_.resourceId, ResourceCode.name, ResourceCode.scope FROM " +
		"_OLD_TABLE_, Permission_, Resource_, ResourceCode WHERE " +
		"_OLD_TABLE_.permissionId = Permission_.permissionId AND " +
		"Permission_.resourceId = Resource_.resourceId AND " +
		"Resource_.codeId = ResourceCode.codeId ORDER BY " +
		"Resource_.resourceId";

}