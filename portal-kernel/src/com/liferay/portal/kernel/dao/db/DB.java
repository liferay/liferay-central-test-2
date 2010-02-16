/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import javax.naming.NamingException;

/**
 * <a href="DB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface DB {

	public static final int MINIMAL = 1;

	public static final int POPULATED = 0;

	public static final int SHARDED = 2;

	public static final String TYPE_DB2 = "db2";

	public static final String TYPE_DERBY = "derby";

	public static final String TYPE_FIREBIRD = "firebird";

	public static final String TYPE_HYPERSONIC = "hypersonic";

	public static final String TYPE_INFORMIX = "informix";

	public static final String TYPE_INGRES = "ingres";

	public static final String TYPE_INTERBASE = "interbase";

	public static final String TYPE_JDATASTORE = "jdatastore";

	public static final String TYPE_MYSQL = "mysql";

	public static final String TYPE_ORACLE = "oracle";

	public static final String TYPE_POSTGRESQL = "postgresql";

	public static final String TYPE_SAP = "sap";

	public static final String TYPE_SQLSERVER = "sqlserver";

	public static final String TYPE_SYBASE = "sybase";

	public static final String[] TYPE_ALL = {
		TYPE_DB2, TYPE_DERBY, TYPE_FIREBIRD, TYPE_HYPERSONIC, TYPE_INFORMIX,
		TYPE_INGRES, TYPE_INTERBASE, TYPE_JDATASTORE, TYPE_MYSQL, TYPE_ORACLE,
		TYPE_POSTGRESQL, TYPE_SAP, TYPE_SQLSERVER, TYPE_SYBASE
	};

	public void buildCreateFile(String databaseName) throws IOException;

	public void buildCreateFile(String databaseName, int population)
		throws IOException;

	public String buildSQL(String template) throws IOException;

	public void buildSQLFile(String fileName) throws IOException;

	public List<Index> getIndexes() throws SQLException;

	public String getTemplateFalse();

	public String getTemplateTrue();

	public String getType();

	public long increment() throws SystemException;

	public boolean isSupportsAlterColumnName();

	public boolean isSupportsAlterColumnType();

	public boolean isSupportsDateMilliseconds();

	public boolean isSupportsScrollableResults();

	public boolean isSupportsStringCaseSensitiveQuery();

	public boolean isSupportsUpdateWithInnerJoin();

	public void runSQL(String sql) throws IOException, SQLException;

	public void runSQL(Connection con, String sql)
		throws IOException, SQLException;

	public void runSQL(String[] sqls) throws IOException, SQLException;

	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException;

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery);

	public void updateIndexes(
			String tablesSQL, String indexesSQL, String indexesProperties,
			boolean dropStaleIndexes)
		throws IOException, SQLException;

}