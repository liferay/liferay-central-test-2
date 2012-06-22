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

package com.liferay.portal.upgrade.v5_1_7_to_5_2_7;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v5_2_3.util.DependencyManager;
import com.liferay.portal.upgrade.v5_2_3.util.ExpandoColumnDependencyManager;
import com.liferay.portal.upgrade.v5_2_3.util.ExpandoRowDependencyManager;
import com.liferay.portal.upgrade.v5_2_3.util.ExpandoTableDependencyManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeDuplicates extends UpgradeProcess {

	protected void deleteDuplicateExpando() throws Exception {
		DependencyManager expandoTableDependencyManager =
			new ExpandoTableDependencyManager();

		deleteDuplicates(
			"ExpandoTable", "tableId",
			new Object[][] {
				{"companyId", Types.BIGINT}, {"classNameId", Types.BIGINT},
				{"name", Types.VARCHAR}
			},
			expandoTableDependencyManager);

		DependencyManager expandoRowDependencyManager =
			new ExpandoRowDependencyManager();

		deleteDuplicates(
			"ExpandoRow", "rowId_",
			new Object[][] {
				{"tableId", Types.BIGINT}, {"classPK", Types.BIGINT}
			},
			expandoRowDependencyManager);

		DependencyManager expandoColumnDependencyManager =
			new ExpandoColumnDependencyManager();

		deleteDuplicates(
			"ExpandoColumn", "columnId",
			new Object[][] {
				{"tableId", Types.BIGINT}, {"name", Types.VARCHAR}
			},
			expandoColumnDependencyManager);

		deleteDuplicates(
			"ExpandoValue", "valueId",
			new Object[][] {
				{"columnId", Types.BIGINT}, {"rowId_", Types.BIGINT}
			});

		deleteDuplicates(
			"ExpandoValue", "valueId",
			new Object[][] {
				{"tableId", Types.BIGINT}, {"columnId", Types.BIGINT},
				{"classPK", Types.BIGINT}
			});
	}

	protected void deleteDuplicates(
			String tableName, String primaryKeyName, Object[][] columns)
		throws Exception {

		deleteDuplicates(tableName, primaryKeyName, columns, null, null);
	}

	protected void deleteDuplicates(
			String tableName, String primaryKeyName, Object[][] columns,
			DependencyManager dependencyManager)
		throws Exception {

		deleteDuplicates(
			tableName, primaryKeyName, columns, null, dependencyManager);
	}

	protected void deleteDuplicates(
			String tableName, String primaryKeyName, Object[][] columns,
			Object[][] extraColumns)
		throws Exception {

		deleteDuplicates(
			tableName, primaryKeyName, columns, extraColumns, null);
	}

	protected void deleteDuplicates(
			String tableName, String primaryKeyName, Object[][] columns,
			Object[][] extraColumns, DependencyManager dependencyManager)
		throws Exception {

		if (_log.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Checking for duplicate data from ");
			sb.append(tableName);
			sb.append(" for unique index (");

			for (int i = 0; i < columns.length; i++) {
				sb.append(columns[i][0]);

				if ((i + 1) < columns.length) {
					sb.append(", ");
				}
			}

			sb.append(")");

			_log.info(sb.toString());
		}

		if (dependencyManager != null) {
			dependencyManager.setTableName(tableName);
			dependencyManager.setPrimaryKeyName(primaryKeyName);
			dependencyManager.setColumns(columns);
			dependencyManager.setExtraColumns(extraColumns);
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select ");
			sb.append(primaryKeyName);

			for (int i = 0; i < columns.length; i++) {
				sb.append(", ");
				sb.append(columns[i][0]);
			}

			if (extraColumns != null) {
				for (int i = 0; i < extraColumns.length; i++) {
					sb.append(", ");
					sb.append(extraColumns[i][0]);
				}
			}

			sb.append(" from ");
			sb.append(tableName);
			sb.append(" order by ");

			for (int i = 0; i < columns.length; i++) {
				sb.append(columns[i][0]);
				sb.append(", ");
			}

			sb.append(primaryKeyName);

			String sql = sb.toString();

			if (_log.isDebugEnabled()) {
				_log.debug("Execute SQL " + sql);
			}

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			boolean supportsStringCaseSensitiveQuery =
				isSupportsStringCaseSensitiveQuery();

			long previousPrimaryKeyValue = 0;
			Object[] previousColumnValues = new Object[columns.length];

			Object[] previousExtraColumnValues = null;

			if (extraColumns != null) {
				previousExtraColumnValues = new Object[extraColumns.length];
			}

			while (rs.next()) {
				long primaryKeyValue = rs.getLong(primaryKeyName);

				Object[] columnValues = getColumnValues(rs, columns);
				Object[] extraColumnValues = getColumnValues(rs, extraColumns);

				boolean duplicate = true;

				for (int i = 0; i < columnValues.length; i++) {
					Object columnValue = columnValues[i];
					Object previousColumnValue = previousColumnValues[i];

					if ((columnValue == null) ||
						(previousColumnValue == null)) {

						duplicate = false;
					}
					else if (!supportsStringCaseSensitiveQuery &&
							 columns[i][1].equals(Types.VARCHAR)) {

						String columnValueString = (String)columnValue;
						String previousColumnValueString =
							(String)previousColumnValue;

						if (!columnValueString.equalsIgnoreCase(
								previousColumnValueString)) {

							duplicate = false;
						}
					}
					else {
						if (!columnValue.equals(previousColumnValue)) {
							duplicate = false;
						}
					}

					if (!duplicate) {
						break;
					}
				}

				if (duplicate) {
					runSQL(
						"delete from " + tableName + " where " +
							primaryKeyName + " = " + primaryKeyValue);

					if (dependencyManager != null) {
						if (_log.isInfoEnabled()) {
							sb = new StringBuilder();

							sb.append("Resolving duplicate data from ");
							sb.append(tableName);
							sb.append(" with primary keys ");
							sb.append(primaryKeyValue);
							sb.append(" and ");
							sb.append(previousPrimaryKeyValue);

							_log.info(sb.toString());
						}

						dependencyManager.update(
							previousPrimaryKeyValue, previousColumnValues,
							previousExtraColumnValues, primaryKeyValue,
							columnValues, extraColumnValues);
					}
				}
				else {
					previousPrimaryKeyValue = primaryKeyValue;

					for (int i = 0; i < columnValues.length; i++) {
						previousColumnValues[i] = columnValues[i];
					}

					if (extraColumnValues != null) {
						for (int i = 0; i < extraColumnValues.length; i++) {
							previousExtraColumnValues[i] = extraColumnValues[i];
						}
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteDuplicateExpando();
	}

	protected Object[] getColumnValues(ResultSet rs, Object[][] columns)
		throws Exception {

		if (columns == null) {
			return null;
		}

		Object[] columnValues = new Object[columns.length];

		for (int i = 0; i < columns.length; i++) {
			String columnName = (String)columns[i][0];
			Integer columnType = (Integer)columns[i][1];

			if (columnType.intValue() == Types.BIGINT) {
				columnValues[i] = rs.getLong(columnName);
			}
			else if (columnType.intValue() == Types.BOOLEAN) {
				columnValues[i] = rs.getBoolean(columnName);
			}
			else if (columnType.intValue() == Types.DOUBLE) {
				columnValues[i] = rs.getDouble(columnName);
			}
			else if (columnType.intValue() == Types.INTEGER) {
				columnValues[i] = rs.getInt(columnName);
			}
			else if (columnType.intValue() == Types.TIMESTAMP) {
				columnValues[i] = rs.getTimestamp(columnName);
			}
			else if (columnType.intValue() == Types.VARCHAR) {
				columnValues[i] = rs.getString(columnName);
			}
			else {
				throw new UpgradeException(
					"Upgrade code using unsupported class type " + columnType);
			}
		}

		return columnValues;
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeDuplicates.class);

}