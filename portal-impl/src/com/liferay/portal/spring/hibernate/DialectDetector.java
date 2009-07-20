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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.dao.orm.hibernate.DB2Dialect;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.tools.sql.DBUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import org.hibernate.dialect.SybaseDialect;

public class DialectDetector {

	public static String determineDialect(DataSource dataSource) {
		Dialect dialect = null;

		Connection con = null;

		try {
			con = dataSource.getConnection();

			DatabaseMetaData metaData = con.getMetaData();

			String dbName = metaData.getDatabaseProductName();
			int dbMajorVersion = metaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining dialect for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.startsWith("HSQL")) {
				if (_log.isWarnEnabled()) {
					StringBuilder sb = new StringBuilder();

					sb.append("Liferay is configured to use Hypersonic as ");
					sb.append("its database. Do NOT use Hypersonic in ");
					sb.append("production. Hypersonic is an embedded ");
					sb.append("database useful for development and demo'ing ");
					sb.append("purposes. The database settings can be ");
					sb.append("changed in portal.properties.");

					_log.warn(sb.toString());
				}
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				dialect = new SybaseDialect();
			}
			else if (dbName.startsWith("DB2") && (dbMajorVersion == 9)) {
				dialect = new DB2Dialect();
			}
			else {
				dialect = DialectFactory.determineDialect(
					dbName, dbMajorVersion);
			}

			DBUtil.setInstance(dialect);

			if (_log.isInfoEnabled()) {
				_log.info("Using dialect " + dialect.getClass().getName());
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.indexOf("explicitly set for database: DB2") != -1) {
				dialect = new DB2400Dialect();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DB2400Dialect was dynamically chosen as the " +
							"Hibernate dialect for DB2. This can be " +
								"overriden in portal.properties");
				}
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(con);
		}

		if (dialect == null) {
			throw new RuntimeException("No dialect found");
		}

		return dialect.getClass().getName();
	}

	private static Log _log = LogFactoryUtil.getLog(DialectDetector.class);

}