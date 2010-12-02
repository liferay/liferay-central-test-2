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
 * @author Douglas Wong
 */
public class VerifySQLServer extends VerifyProcess {

	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		if (!db.getType().equals(DB.TYPE_SQLSERVER)) {
			return;
		}

		_convertColumnsToUnicode();
	}

	private void _convertColumnsToUnicode() {
		_dropNonUnicodeTableIndexes();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_SQL_GET_NON_UNICODE_COLUMNS);

			rs = ps.executeQuery();

			while (rs.next()) {
				String columnName = rs.getString("column_name");
				String dataType = rs.getString("data_type");
				boolean isNullable = rs.getBoolean("is_nullable");
				int length = rs.getInt("length");
				String tableName = rs.getString("table_name");

				if (dataType.equals("varchar")) {
					_convertVarcharColumn(
						tableName, columnName, length, isNullable);
				}
				else if (dataType.equals("text")) {
					_convertTextColumn(
						tableName, columnName, length, isNullable);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _convertVarcharColumn(
			String tableName, String columnName, int length, boolean isNullable)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updating " + tableName + "." + columnName +
					" to use nvarchar");
		}

		StringBundler sb = new StringBundler();

		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ALTER COLUMN ");
		sb.append(columnName);
		sb.append(" NVARCHAR(");
		sb.append(length);
		sb.append(")");

		if (!isNullable) {
			sb.append(" NOT NULL");
		}

		_executeMsSqlCommand(sb.toString());
	}

	private void _convertTextColumn(
			String tableName, String columnName, int length, boolean isNullable)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updating " + tableName + "." + columnName +
					" to use ntext");
		}

		StringBundler sb = new StringBundler();

		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ADD temp NTEXT");

		if (!isNullable) {
			sb.append(" NOT NULL");
		}

		_executeMsSqlCommand(sb.toString());

		_executeMsSqlCommand(
			"UPDATE " + tableName + " SET temp = " + columnName);

		_executeMsSqlCommand(
			"ALTER TABLE " + tableName + " DROP COLUMN " + columnName);

		_executeMsSqlCommand(
			"EXEC SP_RENAME \'" + tableName + ".temp\', \'" + columnName +
				"\', \'column\';");
	}

	private void _dropNonUnicodeTableIndexes() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_SQL_GET_NON_UNICODE_TABLE_INDEXES);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");

				if (_log.isInfoEnabled()) {
					_log.info(
						"Dropping index " + tableName + "." + indexName);
				}

				_executeMsSqlCommand(
					"DROP INDEX " + indexName + " ON " + tableName);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _executeMsSqlCommand(String sql) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(sql);

			ps.executeUpdate();
		}
		catch (SQLException se) {
			_log.error(se, se);
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _FILTER_EXCLUDED_TABLES =
		"(sysobjects.name not like 'Counter') AND " +
		"(sysobjects.name not like 'Cyrus%') AND " +
		"(sysobjects.name not like 'QUARTZ%')";

	private static final String _FILTER_NON_UNICODE_DATA_TYPES =
		"((systypes.name = 'varchar') OR (systypes.name = 'text'))";

	private static final String _SQL_GET_NON_UNICODE_COLUMNS =
		"SELECT sysobjects.name AS table_name, syscolumns.name AS " +
		"column_name, systypes.name AS data_type, syscolumns.length, " +
		"syscolumns.isnullable AS is_nullable FROM sysobjects INNER JOIN " +
		"syscolumns ON sysobjects.id = syscolumns.id INNER JOIN systypes ON " +
		"syscolumns.xtype = systypes.xtype WHERE (sysobjects.xtype = 'U') " +
		"AND " + _FILTER_NON_UNICODE_DATA_TYPES + " AND " +
		_FILTER_EXCLUDED_TABLES + " ORDER BY sysobjects.name, syscolumns.colid";

	private static final String _SQL_GET_NON_UNICODE_TABLE_INDEXES =
		"SELECT DISTINCT sysobjects.name AS table_name, sysindexes.name AS " +
		"index_name FROM sysobjects INNER JOIN sysindexes ON " +
		"sysobjects.id = sysindexes.id INNER JOIN syscolumns ON " +
		"sysobjects.id = syscolumns.id INNER JOIN sysindexkeys ON " +
		"((sysobjects.id = sysindexkeys.id) AND " +
		"(syscolumns.colid = sysindexkeys.colid) AND " +
		"(sysindexes.indid = sysindexkeys.indid)) INNER JOIN systypes ON " +
		"syscolumns.xtype = systypes.xtype WHERE sysobjects.type = 'U' AND " +
		_FILTER_NON_UNICODE_DATA_TYPES + " AND " + _FILTER_EXCLUDED_TABLES +
		" ORDER BY sysobjects.name, sysindexes.name";

	private static Log _log = LogFactoryUtil.getLog(VerifySQLServer.class);

}