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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeExpando.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateTables();
	}

	protected void updateColumns(
			long scTableId, long snTableId, long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoColumn where tableId = ?");

			ps.setLong(1, wolTableId);

			rs = ps.executeQuery();

			long scColumnId = 0;
			long snColumnId = 0;

			while (rs.next()) {
				long wolColumnId = rs.getLong("columnId");
				String name = rs.getString("name");

				long newTableId = 0;

				if (name.equals("aboutMe")) {
					newTableId = snTableId;
					snColumnId = wolColumnId;
				}
				else if (name.equals("jiraUserId")) {
					newTableId = scTableId;
					scColumnId = wolColumnId;
				}

				runSQL(
					"update ExpandoColumn set tableId = " + newTableId +
						" where tableId = " + wolTableId + " and name = '" +
							name + "'");
			}

			updateRows(
				scColumnId, scTableId, snColumnId, snTableId, wolTableId);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateRows(
			long scColumnId, long scTableId, long snColumnId, long snTableId,
			long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoRow where tableId = ?");

			ps.setLong(1, wolTableId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long wolRowId = rs.getLong("rowId_");
				long companyId = rs.getLong("companyId");
				long classPK = rs.getLong("classPK");

				long scRowId = increment();

				runSQL(
					"insert into ExpandoRow (rowId_, companyId, tableId, " +
						"classPK) values (" + scRowId + ", " + companyId +
							", " + scTableId + ", " + classPK + ")");

				long snRowId = increment();

				runSQL(
					"insert into ExpandoRow (rowId_, companyId, tableId, " +
						"classPK) values (" + snRowId + ", " + companyId +
							", " + snTableId + ", " + classPK + ")");

				runSQL("delete from ExpandoRow where tableId = " + wolTableId);

				updateValues(
					scColumnId, scRowId, scTableId, snColumnId, snRowId,
					snTableId, wolRowId, wolTableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTables() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoTable where name = ?");

			ps.setString(1, "WOL");

			rs = ps.executeQuery();

			while (rs.next()) {
				long wolTableId = rs.getLong("tableId");
				long companyId = rs.getLong("companyId");
				long classNameId = rs.getLong("classNameId");

				long scTableId = increment();

				runSQL(
					"insert into ExpandoTable (tableId, companyId, " +
						"classNameId, name) values (" + scTableId + ", " +
							companyId + ", " + classNameId + ", 'SC')");

				long snTableId = increment();

				runSQL(
					"insert into ExpandoTable (tableId, companyId, " +
						"classNameId, name) values (" + snTableId + ", " +
							companyId + ", " + classNameId + ", 'SN')");

				runSQL(
					"delete from ExpandoTable where tableId = " + wolTableId);

				updateColumns(scTableId, snTableId, wolTableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateValues(
			long scColumnId, long scRowId, long scTableId, long snColumnId,
			long snRowId, long snTableId, long wolRowId, long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoValue where tableId = ? and rowId_ = ?");

			ps.setLong(1, wolTableId);
			ps.setLong(2, wolRowId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long valueId = rs.getLong("valueId");
				long columnId = rs.getLong("columnId");

				long newTableId = 0;
				long newRowId = 0;

				if (columnId == scColumnId) {
					newRowId = scRowId;
					newTableId = scTableId;
				}
				else if (columnId == snColumnId) {
					newRowId = snRowId;
					newTableId = snTableId;
				}

				runSQL(
					"update ExpandoValue set tableId = " + newTableId +
						", rowId_ = " + newRowId + " where " + "valueId = " +
							valueId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}