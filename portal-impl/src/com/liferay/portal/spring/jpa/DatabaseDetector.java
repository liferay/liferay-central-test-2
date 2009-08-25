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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.sql.DBUtil;
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
		Connection con = null;

		Database db = null;
		String type = null;

		try {
			con = dataSource.getConnection();

			DatabaseMetaData metaData = con.getMetaData();

			String dbName = metaData.getDatabaseProductName();
			int dbMajorVersion = metaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining DB type for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.startsWith("DB2/")) {
				db = Database.DB2;

				type = DBUtil.TYPE_DB2;
			}
			else if ("Apache Derby".equals(dbName)) {
				db = Database.DERBY;

				type = DBUtil.TYPE_DERBY;
			}
			else if ("HSQL Database Engine".equals(dbName)) {
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

				db = Database.HSQL;

				type = DBUtil.TYPE_HYPERSONIC;
			}
			else if ("Informix Dynamic Server".equals(dbName)) {
				db = Database.INFORMIX;

				type = DBUtil.TYPE_INFORMIX;
			}
			else if ("MySQL".equals(dbName)) {
				db = Database.MYSQL;

				type = DBUtil.TYPE_MYSQL;
			}
			else if ("Oracle".equals(dbName)) {
				db = Database.ORACLE;

				type = DBUtil.TYPE_ORACLE;
			}
			else if ("PostgreSQL".equals(dbName)) {
				db = Database.POSTGRESQL;

				type = DBUtil.TYPE_POSTGRESQL;
			}
			else if (dbName.startsWith("Microsoft SQL Server")) {
				db = Database.SQL_SERVER;

				type = DBUtil.TYPE_SQLSERVER;
			}
			else if ("Sybase SQL Server".equals(dbName)) {
				db = Database.SYBASE;

				type = DBUtil.TYPE_SYBASE;
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				db = Database.SYBASE;

				type = DBUtil.TYPE_SYBASE;
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.indexOf("explicitly set for database: DB2") != -1) {
				db = Database.DB2;

				type = DBUtil.TYPE_DB2;
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(con);
		}

		if (db == null) {
			throw new RuntimeException("Unable to detect the database");
		}

		if (_log.isInfoEnabled()) {
			_log.info("Detected database " + db.toString());
		}

		if (Validator.isNotNull(PropsValues.JPA_DATABASE_TYPE)) {
			DBUtil.setInstance(PropsValues.JPA_DATABASE_TYPE);
		}
		else {
			DBUtil.setInstance(type);
		}

		return db;
	}

	private static Log _log = LogFactoryUtil.getLog(DatabaseDetector.class);

}