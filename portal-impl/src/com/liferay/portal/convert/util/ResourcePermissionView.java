/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.util.Table;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ResourcePermissionView.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ResourcePermissionView extends Table {

	public static String getActionId(String[] values) {
		return values[4];
	}

	public static long getCompanyId(String[] values) {
		return Long.parseLong(values[0]);
	}

	public static String getPrimaryKey(String[] values) {
		return values[2];
	}

	public static long getRoleId(String[] values) {
		return Long.parseLong(values[3]);
	}

	public static int getScope(String[] values) {
		return Integer.parseInt(values[1]);
	}

	public ResourcePermissionView(String name) {
		super("ResourcePermissionView");

		List<Object[]> columns = new ArrayList<Object[]>();

		columns.add(new Object[] {"companyId", Types.BIGINT});
		columns.add(new Object[] {"scope", Types.INTEGER});
		columns.add(new Object[] {"primKey", Types.VARCHAR});
		columns.add(new Object[] {"roleId", Types.BIGINT});
		columns.add(new Object[] {"actionId", Types.VARCHAR});

		setColumns(columns.toArray(new Object[0][]));

		_name = name;
	}

	public String getSelectSQL() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append(_SELECT_SQL);
		sb.append(StringPool.APOSTROPHE);
		sb.append(_name);
		sb.append(StringPool.APOSTROPHE);

		return sb.toString();
	}

	private String _name = StringPool.BLANK;

	private static final String _SELECT_SQL =
		"SELECT Permission_.companyId, ResourceCode.scope, " +
		"Resource_.primKey, Roles_Permissions.roleId, Permission_.actionId " +
		"FROM Roles_Permissions, Permission_, Resource_, ResourceCode WHERE " +
		"Permission_.permissionId = Roles_Permissions.permissionId AND " +
		"Permission_.resourceId = Resource_.resourceId AND " +
		"Resource_.codeId = ResourceCode.codeId AND ResourceCode.name = ";

}