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

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.springframework.orm.jpa.vendor.Database;

/**
 * <a href="DatabaseDetector.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class DatabaseDetector {

	public static Database determineDatabase(DataSource dataSource) {
		Database database = null;
		String type = null;

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			String dbName = databaseMetaData.getDatabaseProductName();
			int dbMajorVersion = databaseMetaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining DB type for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.equals("Apache Derby")) {
				database = Database.DERBY;
				type = DB.TYPE_DERBY;
			}
			else if (dbName.startsWith("DB2/")) {
				database = Database.DB2;
				type = DB.TYPE_DB2;
			}
			else if (dbName.equals("HSQL Database Engine")) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(6);

					sb.append("Liferay is configured to use Hypersonic as ");
					sb.append("its database. Do NOT use Hypersonic in ");
					sb.append("production. Hypersonic is an embedded ");
					sb.append("database useful for development and demo'ing ");
					sb.append("purposes. The database settings can be ");
					sb.append("changed in portal.properties.");

					_log.warn(sb.toString());
				}

				database = Database.HSQL;
				type = DB.TYPE_HYPERSONIC;
			}
			else if (dbName.equals("Informix Dynamic Server")) {
				database = Database.INFORMIX;
				type = DB.TYPE_INFORMIX;
			}
			else if (dbName.startsWith("Microsoft SQL Server")) {
				database = Database.SQL_SERVER;
				type = DB.TYPE_SQLSERVER;
			}
			else if (dbName.equals("MySQL")) {
				database = Database.MYSQL;
				type = DB.TYPE_MYSQL;
			}
			else if (dbName.equals("Oracle")) {
				database = Database.ORACLE;
				type = DB.TYPE_ORACLE;
			}
			else if (dbName.equals("PostgreSQL")) {
				database = Database.POSTGRESQL;
				type = DB.TYPE_POSTGRESQL;
			}
			else if (dbName.equals("Sybase SQL Server")) {
				database = Database.SYBASE;
				type = DB.TYPE_SYBASE;
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				database = Database.SYBASE;
				type = DB.TYPE_SYBASE;
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.indexOf("explicitly set for database: DB2") != -1) {
				database = Database.DB2;

				type = DB.TYPE_DB2;
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		if (database == null) {
			throw new RuntimeException("Unable to detect the database");
		}

		if (_log.isInfoEnabled()) {
			_log.info("Detected database " + database.toString());
		}

		if (Validator.isNotNull(PropsValues.JPA_DATABASE_TYPE)) {
			DBFactoryUtil.setDB(PropsValues.JPA_DATABASE_TYPE);
		}
		else {
			DBFactoryUtil.setDB(type);
		}

		return database;
	}

	private static Log _log = LogFactoryUtil.getLog(DatabaseDetector.class);

}