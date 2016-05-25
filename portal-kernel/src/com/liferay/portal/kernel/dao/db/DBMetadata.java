/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class DBMetadata {

	public DBMetadata(Connection connection) {
		_connection = connection;
	}

	public boolean hasColumn(String tableName, String columnName)
		throws Exception {

		try (PreparedStatement ps = _connection.prepareStatement(
				"select * from " + tableName);
			ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String curColumnName = rsmd.getColumnName(i + 1);

				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public boolean hasTable(String tableName) throws Exception {
		return hasTable(tableName, false);
	}

	public boolean hasTable(String tableName, boolean caseSensitive)
		throws Exception {

		if (caseSensitive) {
			if (doHasTable(tableName)) {
				return true;
			}

			return false;
		}

		if (doHasTable(StringUtil.toLowerCase(tableName)) ||
			doHasTable(StringUtil.toUpperCase(tableName)) ||
			doHasTable(tableName)) {

			return true;
		}

		return false;
	}

	public boolean hasColumnType(
			Class<?> tableClass, String columnName, String typeName)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_NAME");

		String tableName = (String)tableNameField.get(null);

		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		try (ResultSet rs = databaseMetaData.getColumns(
				null, null, tableName, columnName)) {

			if (!rs.next()) {
				return false;
			}

			int columnType = getColumnType(tableClass, columnName);
			int columnTypeSize = parseColumnTypeSize(typeName);
			boolean columnTypeNullable = isColumnTypeNullable(typeName);

			int columnSize = rs.getInt("COLUMN_SIZE");
			int dataType = rs.getInt("DATA_TYPE");
			int nullable = rs.getInt("NULLABLE");

			if ((columnTypeSize != -1) && (columnTypeSize != columnSize)) {
				return false;
			}

			if (dataType != columnType) {
				return false;
			}

			if ((columnTypeNullable &&
				 (nullable != DatabaseMetaData.columnNullable)) ||
				(!columnTypeNullable &&
				 (nullable != DatabaseMetaData.columnNoNulls))) {

				return false;
			}

			return true;
		}
	}

	protected boolean doHasTable(String tableName) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DatabaseMetaData metadata = _connection.getMetaData();

			rs = metadata.getTables(null, null, tableName, null);

			while (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return false;
	}

	protected int getColumnType(Class<?> tableClass, String columnName)
		throws Exception {

		Field tableColumnsField = tableClass.getField("TABLE_COLUMNS");

		Object[][] tableColumns = (Object[][])tableColumnsField.get(null);

		for (Object[] tableColumn : tableColumns) {
			if (tableColumn[0].equals(columnName)) {
				return (int)tableColumn[1];
			}
		}

		throw new UpgradeException(
			"Table class " + tableClass +
				" has no row in TABLE_COLUMNS for column " + columnName);
	}

	protected boolean isColumnTypeNullable(String typeName) {
		typeName = typeName.trim();

		int i = typeName.indexOf("null");

		if (i == -1) {
			return false;
		}

		if ((i > 0) && !Character.isSpaceChar(typeName.charAt(i - 1))) {
			return false;
		}

		if ((i + 4) < typeName.length()) {
			return false;
		}

		return true;
	}

	protected int parseColumnTypeSize(String typeName) throws UpgradeException {
		Matcher matcher = _columnTypeSizePattern.matcher(typeName);

		if (!matcher.matches()) {
			return -1;
		}

		String size = matcher.group(1);

		if (Validator.isNotNull(size)) {
			try {
				return Integer.parseInt(size);
			}
			catch (NumberFormatException nfe) {
				throw new UpgradeException(
					"Invalid SQL type " + typeName +
						". Type size must be a non negative integer.",
					nfe);
			}
		}

		return -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(DBMetadata.class);

	private static final Pattern _columnTypeSizePattern = Pattern.compile(
		"^\\w+(?:\\((\\d+)\\))?.*", Pattern.CASE_INSENSITIVE);

	private final Connection _connection;

}