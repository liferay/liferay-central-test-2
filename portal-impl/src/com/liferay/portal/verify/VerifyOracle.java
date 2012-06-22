/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class VerifyOracle extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_ORACLE)) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select table_name, column_name, data_length from " +
					"user_tab_columns where data_type = 'VARCHAR2' and " +
						"char_used = 'B'");

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);
				String columnName = rs.getString(2);
				int dataLength = rs.getInt(3);

				if (dataLength != 4000) {
					dataLength = dataLength / 4;
				}

				try {
					runSQL(
						"alter table " + tableName + " modify " + columnName +
							" varchar2(" + dataLength + " char)");
				}
				catch (SQLException sqle) {
					if (sqle.getErrorCode() == 1441) {
						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler(6);

							sb.append("Unable to alter length of column ");
							sb.append(columnName);
							sb.append(" for table ");
							sb.append(tableName);
							sb.append("because it contains values that are ");
							sb.append("larger than the new column length");

							_log.warn(sb.toString());
						}
					}
					else {
						throw sqle;
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyOracle.class);

}