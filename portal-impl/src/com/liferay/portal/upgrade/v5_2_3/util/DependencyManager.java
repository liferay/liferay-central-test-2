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

package com.liferay.portal.upgrade.v5_2_3.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * <a href="DependencyManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class DependencyManager {

	public void setColumns(Object[][] columns) {
		this.columns = columns;
	}

	public void setExtraColumns(Object[][] extraColumns) {
		this.extraColumns = extraColumns;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void update(long newPrimaryKeyValue) throws Exception {
		update(0, null, null, newPrimaryKeyValue, null, null);
	}

	public abstract void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception;

	protected void deleteDuplicateData(String tableName, long primaryKeyValue)
		throws Exception {

		deleteDuplicateData(tableName, primaryKeyName, primaryKeyValue);
	}

	protected void deleteDuplicateData(
			String tableName, String columnName, long columnValue)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("delete from ");
			sb.append(tableName);
			sb.append(" where ");
			sb.append(columnName);
			sb.append(" = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, columnValue);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateDuplicateData(
			String tableName, long oldPrimaryKeyValue, long newPrimaryKeyValue)
		throws Exception {

		updateDuplicateData(
			tableName, primaryKeyName, oldPrimaryKeyValue, newPrimaryKeyValue);
	}

	protected void updateDuplicateData(
			String tableName, String columnName, long oldColumnValue,
			long newColumnValue)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set ");
			sb.append(columnName);
			sb.append(" = ? where ");
			sb.append(columnName);
			sb.append(" = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, newColumnValue);
			ps.setLong(2, oldColumnValue);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected Object[][] columns;
	protected Object[][] extraColumns;
	protected String primaryKeyName;
	protected String tableName;

}