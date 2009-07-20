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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.Database;
import com.liferay.portal.tools.sql.*;

import java.io.IOException;

import java.sql.SQLException;

import javax.naming.NamingException;

public class DatabaseImpl implements Database {

	public String getType() {
		DBUtil dbUtil = DBUtil.getInstance();

		if (dbUtil instanceof MySQLUtil) {
			return DBUtil.TYPE_MYSQL;
		}
		else if (dbUtil instanceof HypersonicUtil) {
			return DBUtil.TYPE_HYPERSONIC;
		}
		else if (dbUtil instanceof DB2Util) {
			return DBUtil.TYPE_DB2;
		}
		else if (dbUtil instanceof DerbyUtil) {
			return DBUtil.TYPE_DERBY;
		}
		else if (dbUtil instanceof FirebirdUtil) {
			return DBUtil.TYPE_FIREBIRD;
		}
		else if (dbUtil instanceof InformixUtil) {
			return DBUtil.TYPE_INFORMIX;
		}
		else if (dbUtil instanceof InterBaseUtil) {
			return DBUtil.TYPE_INTERBASE;
		}
		else if (dbUtil instanceof JDataStoreUtil) {
			return DBUtil.TYPE_JDATASTORE;
		}
		else if (dbUtil instanceof OracleUtil) {
			return DBUtil.TYPE_ORACLE;
		}
		else if (dbUtil instanceof PostgreSQLUtil) {
			return DBUtil.TYPE_POSTGRESQL;
		}
		else if (dbUtil instanceof SAPUtil) {
			return DBUtil.TYPE_SAP;
		}
		else if (dbUtil instanceof SQLServerUtil) {
			return DBUtil.TYPE_SQLSERVER;
		}
		else if (dbUtil instanceof SybaseUtil) {
			return DBUtil.TYPE_SYBASE;
		}
		else {
			return null;
		}
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		DBUtil dbUtil = DBUtil.getInstance();

		dbUtil.runSQLTemplate(path);
	}

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		DBUtil dbUtil = DBUtil.getInstance();

		dbUtil.runSQLTemplate(path, failOnError);
	}

}