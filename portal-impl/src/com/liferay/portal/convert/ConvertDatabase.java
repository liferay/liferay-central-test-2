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

package com.liferay.portal.convert;

import com.liferay.counter.model.Counter;
import com.liferay.mail.model.CyrusUser;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.dao.jdbc.util.DataSourceFactoryBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.ShutdownUtil;

import java.lang.reflect.Field;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

/**
 * <a href="ConvertDatabase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ConvertDatabase extends ConvertProcess {

	public String getDescription() {
		return "migrate-data-from-one-database-to-another";
	}

	public String getParameterDescription() {
		return "please-enter-jdbc-information-for-new-database";
	}

	public String[] getParameterNames() {
		return new String[] {
			"jdbc-driver-class-name", "jdbc-url", "jdbc-user-name",
			"jdbc-password"
		};
	}

	public boolean isEnabled() {
		return true;
	}

	protected void doConvert() throws Exception {
		DataSource dataSource = getDataSource();

		Dialect dialect = DialectDetector.getDialect(dataSource);

		DB db = DBFactoryUtil.getDB(dialect);

		List<String> modelNames = ModelHintsUtil.getModels();

		List<Tuple> tableDetails = new ArrayList<Tuple>();

		Connection connection = dataSource.getConnection();

		try {
			MaintenanceUtil.appendStatus(
				"Collecting information for database tables to migration");

			for (String modelName : modelNames) {
				String implClassName = modelName.replaceFirst(
					"(\\.model\\.)(\\p{Upper}.*)", "$1impl.$2Impl");

				Class<?> implClass = InstancePool.get(implClassName).getClass();

				Field[] fields = implClass.getFields();

				for (Field field : fields) {
					Tuple tuple = null;

					String fieldName = field.getName();

					if (fieldName.equals("TABLE_NAME")) {
						tuple = getTableDetails(implClass, field, fieldName);
					}
					else if (fieldName.startsWith("MAPPING_TABLE_") &&
							 fieldName.endsWith("_NAME")) {

						tuple = getTableDetails(implClass, field, fieldName);
					}

					if (tuple != null) {
						tableDetails.add(tuple);
					}
				}
			}

			for (Tuple tuple : _UNMAPPED_TABLES) {
				tableDetails.add(tuple);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Migrating database tables");
			}

			for (int i = 0; i < tableDetails.size(); i++) {
				if ((i > 0) && (i % (tableDetails.size() / 4) == 0)) {
					MaintenanceUtil.appendStatus(
						 (i * 100 / tableDetails.size()) + "%");
				}

				Tuple tuple = tableDetails.get(i);

				String table = (String)tuple.getObject(0);
				Object[][] columns = (Object[][])tuple.getObject(1);
				String sqlCreate = (String)tuple.getObject(2);

				migrateTable(db, connection, table, columns, sqlCreate);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		MaintenanceUtil.appendStatus(
			"Please change your JDBC settings before restarting server");

		ShutdownUtil.shutdown(0);
	}

	protected DataSource getDataSource() throws Exception {
		String[] values = getParameterValues();

		String jdbcDriverClassName = values[0];
		String jdbcURL = values[1];
		String jdbcUserName = values[2];
		String jdbcPassword = values[3];

		Properties properties = new Properties();

		properties.setProperty(
			_JDBC_PREFIX + "driverClassName", jdbcDriverClassName);
		properties.setProperty(_JDBC_PREFIX + "url", jdbcURL);
		properties.setProperty(_JDBC_PREFIX + "username", jdbcUserName);
		properties.setProperty(_JDBC_PREFIX + "password", jdbcPassword);

		DataSourceFactoryBean dataSourceFactory = new DataSourceFactoryBean();

		dataSourceFactory.setProperties(properties);
		dataSourceFactory.setPropertyPrefix(_JDBC_PREFIX);

		return dataSourceFactory.createInstance();
	}

	protected Tuple getTableDetails(
		Class<?> implClass, Field tableField, String tableFieldVar) {

		try {
			String columnsFieldVar = StringUtil.replace(
				tableFieldVar, "_NAME", "_COLUMNS");
			String sqlCreateFieldVar = StringUtil.replace(
				tableFieldVar, "_NAME", "_SQL_CREATE");

			Field columnsField = implClass.getField(columnsFieldVar);
			Field sqlCreateField = implClass.getField(sqlCreateFieldVar);

			String table = (String)tableField.get(StringPool.BLANK);
			Object[][] columns = (Object[][])columnsField.get(new Object[0][0]);
			String sqlCreate = (String)sqlCreateField.get(StringPool.BLANK);

			return new Tuple(table, columns, sqlCreate);
		}
		catch (Exception e) {
		}

		return null;
	}

	protected void migrateTable(
			DB db, Connection connection, String tableName, Object[][] columns,
			String sqlCreate)
		throws Exception {

		Table table = new Table(tableName, columns);

		String tempFileName = table.generateTempFile();

		db.runSQL(connection, sqlCreate);

		if (tempFileName != null) {
			table.populateTable(tempFileName, connection);
		}
	}

	private static final String _JDBC_PREFIX = "jdbc.upgrade.";

	private static final Tuple[] _UNMAPPED_TABLES = new Tuple[] {
		new Tuple(
			Counter.TABLE_NAME, Counter.TABLE_COLUMNS,
			Counter.TABLE_SQL_CREATE),
		new Tuple(
			CyrusUser.TABLE_NAME, CyrusUser.TABLE_COLUMNS,
			CyrusUser.TABLE_SQL_CREATE),
		new Tuple(
			CyrusVirtual.TABLE_NAME, CyrusVirtual.TABLE_COLUMNS,
			CyrusVirtual.TABLE_SQL_CREATE)
	};

	private static Log _log = LogFactoryUtil.getLog(ConvertDatabase.class);

}