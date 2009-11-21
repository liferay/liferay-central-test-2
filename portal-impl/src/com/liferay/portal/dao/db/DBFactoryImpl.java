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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.InformixDialect;
import org.hibernate.dialect.IngresDialect;
import org.hibernate.dialect.InterbaseDialect;
import org.hibernate.dialect.JDataStoreDialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.Oracle9Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.SAPDBDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.Sybase11Dialect;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.hibernate.dialect.SybaseDialect;

/**
 * <a href="DBFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public class DBFactoryImpl implements DBFactory {

	public DB getDB() {
		if (_db == null) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Using dialect " + PropsValues.HIBERNATE_DIALECT);
				}

				Dialect dialect = (Dialect)Class.forName(
					PropsValues.HIBERNATE_DIALECT).newInstance();

				setDB(dialect);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _db;
	}

	public DB getDB(Object dialect) {
		DB db = null;

		if (dialect instanceof DB2Dialect) {
			if (dialect instanceof DerbyDialect) {
				db = DerbyDB.getInstance();
			}
			else {
				db = DB2DB.getInstance();
			}
		}
		else if (dialect instanceof HSQLDialect) {
			db = HypersonicDB.getInstance();
		}
		else if (dialect instanceof InformixDialect) {
			db = InformixDB.getInstance();
		}
		else if (dialect instanceof IngresDialect) {
			db = IngresDB.getInstance();
		}
		else if (dialect instanceof InterbaseDialect) {
			if (dialect instanceof FirebirdDialect) {
				db = FirebirdDB.getInstance();
			}
			else {
				db = InterBaseDB.getInstance();
			}
		}
		else if (dialect instanceof JDataStoreDialect) {
			db = JDataStoreDB.getInstance();
		}
		else if (dialect instanceof MySQLDialect) {
			db = MySQLDB.getInstance();
		}
		else if (dialect instanceof Oracle8iDialect ||
				 dialect instanceof Oracle9Dialect) {

			db = OracleDB.getInstance();
		}
		else if (dialect instanceof PostgreSQLDialect) {
			db = PostgreSQLDB.getInstance();
		}
		else if (dialect instanceof SAPDBDialect) {
			db = SAPDB.getInstance();
		}
		else if (dialect instanceof SQLServerDialect) {
			db = SQLServerDB.getInstance();
		}
		else if (dialect instanceof SybaseDialect ||
				 dialect instanceof Sybase11Dialect ||
				 dialect instanceof SybaseAnywhereDialect ||
				 dialect instanceof SybaseASE15Dialect) {

			db = SybaseDB.getInstance();
		}

		return db;
	}

	public DB getDB(String type) {
		DB db = null;

		if (type.equals(DB.TYPE_DB2)) {
			db = DB2DB.getInstance();
		}
		else if (type.equals(DB.TYPE_DERBY)) {
			db = DerbyDB.getInstance();
		}
		else if (type.equals(DB.TYPE_FIREBIRD)) {
			db = FirebirdDB.getInstance();
		}
		else if (type.equals(DB.TYPE_HYPERSONIC)) {
			db = HypersonicDB.getInstance();
		}
		else if (type.equals(DB.TYPE_INFORMIX)) {
			db = InformixDB.getInstance();
		}
		else if (type.equals(DB.TYPE_INGRES)) {
			db = IngresDB.getInstance();
		}
		else if (type.equals(DB.TYPE_INTERBASE)) {
			db = InterBaseDB.getInstance();
		}
		else if (type.equals(DB.TYPE_JDATASTORE)) {
			db = JDataStoreDB.getInstance();
		}
		else if (type.equals(DB.TYPE_MYSQL)) {
			db = MySQLDB.getInstance();
		}
		else if (type.equals(DB.TYPE_ORACLE)) {
			db = OracleDB.getInstance();
		}
		else if (type.equals(DB.TYPE_POSTGRESQL)) {
			db = PostgreSQLDB.getInstance();
		}
		else if (type.equals(DB.TYPE_SAP)) {
			db = SAPDB.getInstance();
		}
		else if (type.equals(DB.TYPE_SQLSERVER)) {
			db = SQLServerDB.getInstance();
		}
		else if (type.equals(DB.TYPE_SYBASE)) {
			db = SybaseDB.getInstance();
		}

		return db;
	}

	public void setDB(Object dialect) {
		if (_db == null) {
			_db = getDB(dialect);

			if (_db == null) {
				_log.error(
					"No DB implementation exists for " +
						dialect.getClass().getName());
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using DB implementation " + _db.getClass().getName() +
							" for " + dialect.getClass().getName());
				}
			}
		}
	}

	public void setDB(String type) {
		if (_db == null) {
			_db = getDB(type);

			if (_db == null) {
				_log.error("No DB implementation exists for " + type);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using DB implementation " + _db.getClass().getName() +
							" for " + type);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DBFactoryImpl.class);

	private static DB _db;

}