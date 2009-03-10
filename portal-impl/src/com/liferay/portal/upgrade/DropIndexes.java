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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.tools.sql.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <a href="DropIndexes.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DropIndexes extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		if (_alreadyUpgraded) {
			return;
		}

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		if (dbUtil.getType().equals(DBUtil.TYPE_MYSQL)) {
			dropMySQLIndexes();
		}

		_alreadyUpgraded = true;
	}

	protected void dropMySQLIndexes() throws Exception {
		_log.info("Dropping MySQL indexes");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select distinct(index_name), table_name from " +
					"information_schema.statistics where index_schema = " +
						"database() and index_name like 'LIFERAY_%' or " +
							"index_name like 'IX_%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");

				ps = con.prepareStatement(
					"drop index " + indexName + " on " + tableName);

				try{
					ps.executeUpdate();
				}
				catch (SQLException sqle) {
					if (_log.isWarnEnabled()) {
						_log.warn(sqle.getMessage());
					}
				}
				finally {
					ps.close();
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DropIndexes.class);

	private boolean _alreadyUpgraded;

}