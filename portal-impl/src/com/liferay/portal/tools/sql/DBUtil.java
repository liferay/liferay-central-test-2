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

package com.liferay.portal.tools.sql;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityUtil;
import com.liferay.util.SimpleCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

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
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.Oracle9Dialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.dialect.OracleDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.SAPDBDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.SybaseDialect;

public abstract class DBUtil {

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

	public static DBUtil getInstance() {
		if (_dbUtil == null) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Using dialect " + PropsValues.HIBERNATE_DIALECT);
				}

				Dialect dialect = (Dialect)Class.forName(
					PropsValues.HIBERNATE_DIALECT).newInstance();

				setInstance(dialect);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _dbUtil;
	}

	public static DBUtil getInstance(String type) {
		DBUtil dbUtil = null;

		if (type.equals(TYPE_DB2)) {
			dbUtil = DB2Util.getInstance();
		}
		else if (type.equals(TYPE_DERBY)) {
			dbUtil = DerbyUtil.getInstance();
		}
		else if (type.equals(TYPE_FIREBIRD)) {
			dbUtil = FirebirdUtil.getInstance();
		}
		else if (type.equals(TYPE_HYPERSONIC)) {
			dbUtil = HypersonicUtil.getInstance();
		}
		else if (type.equals(TYPE_INFORMIX)) {
			dbUtil = InformixUtil.getInstance();
		}
		else if (type.equals(TYPE_INGRES)) {
			dbUtil = IngresUtil.getInstance();
		}
		else if (type.equals(TYPE_INTERBASE)) {
			dbUtil = InterBaseUtil.getInstance();
		}
		else if (type.equals(TYPE_JDATASTORE)) {
			dbUtil = JDataStoreUtil.getInstance();
		}
		else if (type.equals(TYPE_MYSQL)) {
			dbUtil = MySQLUtil.getInstance();
		}
		else if (type.equals(TYPE_ORACLE)) {
			dbUtil = OracleUtil.getInstance();
		}
		else if (type.equals(TYPE_POSTGRESQL)) {
			dbUtil = PostgreSQLUtil.getInstance();
		}
		else if (type.equals(TYPE_SAP)) {
			dbUtil = SAPUtil.getInstance();
		}
		else if (type.equals(TYPE_SQLSERVER)) {
			dbUtil = SQLServerUtil.getInstance();
		}
		else if (type.equals(TYPE_SYBASE)) {
			dbUtil = SybaseUtil.getInstance();
		}

		return dbUtil;
	}

	public static void setInstance(Dialect dialect) {
		if (_dbUtil != null) {
			return;
		}

		if (dialect instanceof DB2Dialect) {
			if (dialect instanceof DerbyDialect) {
				_dbUtil = DerbyUtil.getInstance();
			}
			else {
				_dbUtil = DB2Util.getInstance();
			}
		}
		else if (dialect instanceof HSQLDialect) {
			_dbUtil = HypersonicUtil.getInstance();
		}
		else if (dialect instanceof InformixDialect) {
			_dbUtil = InformixUtil.getInstance();
		}
		else if (dialect instanceof IngresDialect) {
			_dbUtil = IngresUtil.getInstance();
		}
		else if (dialect instanceof InterbaseDialect) {
			if (dialect instanceof FirebirdDialect) {
				_dbUtil = FirebirdUtil.getInstance();
			}
			else {
				_dbUtil = InterBaseUtil.getInstance();
			}
		}
		else if (dialect instanceof JDataStoreDialect) {
			_dbUtil = JDataStoreUtil.getInstance();
		}
		else if (dialect instanceof MySQLDialect) {
			_dbUtil = MySQLUtil.getInstance();
		}
		else if (dialect instanceof OracleDialect ||
				 dialect instanceof Oracle8iDialect ||
				 dialect instanceof Oracle9Dialect ||
				 dialect instanceof Oracle9iDialect ||
				 dialect instanceof Oracle10gDialect) {

			_dbUtil = OracleUtil.getInstance();
		}
		else if (dialect instanceof PostgreSQLDialect) {
			_dbUtil = PostgreSQLUtil.getInstance();
		}
		else if (dialect instanceof SAPDBDialect) {
			_dbUtil = SAPUtil.getInstance();
		}
		else if (dialect instanceof SybaseDialect) {
			if (dialect instanceof SQLServerDialect) {
				_dbUtil = SQLServerUtil.getInstance();
			}
			else {
				_dbUtil = SybaseUtil.getInstance();
			}
		}
	}

	public void buildCreateFile(String databaseName) throws IOException {
		buildCreateFile(databaseName, true);
		buildCreateFile(databaseName, false);
	}

	public abstract String buildSQL(String template) throws IOException;

	public void buildSQLFile(String fileName) throws IOException {
		String template = buildTemplate(fileName);

		template = buildSQL(template);

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-" + getServerName() +
				".sql",
			template);
	}

	public String getTemplateFalse() {
		return getTemplate()[2];
	}

	public String getTemplateTrue() {
		return getTemplate()[1];
	}

	public String getType() {
		return _type;
	}

	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	public boolean isSupportsStringCaseSensitiveQuery() {
		return _supportsStringCaseSensitiveQuery;
	}

	public boolean isSupportsUpdateWithInnerJoin() {
		return _SUPPORTS_UPDATE_WITH_INNER_JOIN;
	}

	public void runSQL(String sql) throws IOException, SQLException {
		runSQL(new String[] {sql});
	}

	public void runSQL(Connection con, String sql)
		throws IOException, SQLException {

		runSQL(con, new String[] {sql});
	}

	public void runSQL(String[] sqls) throws IOException, SQLException {
		Connection con = DataAccess.getConnection();

		try {
			runSQL(con, sqls);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException {

		Statement s = null;

		try {
			s = con.createStatement();

			for (int i = 0; i < sqls.length; i++) {
				String sql = buildSQL(sqls[i]);

				sql = sql.trim();

				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}

				if (sql.endsWith("go")) {
					sql = sql.substring(0, sql.length() - 2);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(sql);
				}

				try {
					s.executeUpdate(sql);
				}
				catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		finally {
			DataAccess.cleanUp(s);
		}
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		runSQLTemplate(path, true);
	}

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream is = classLoader.getResourceAsStream(
			"com/liferay/portal/tools/sql/dependencies/" + path);

		if (is == null) {
			is = classLoader.getResourceAsStream(path);
		}

		if (is == null) {
			_log.error("Invalid path " + path);
		}

		String template = StringUtil.read(is);

		is.close();

		boolean evaluate = path.endsWith(".vm");

		runSQLTemplateString(template, evaluate, failOnError);
	}

	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException {

		if (evaluate) {
			try {
				template = evaluateVM(template);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new StringReader(template));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("##")) {
				if (line.startsWith("@include ")) {
					int pos = line.indexOf(" ");

					String includeFileName = line.substring(pos + 1);

					Thread currentThread = Thread.currentThread();

					ClassLoader classLoader =
						currentThread.getContextClassLoader();

					InputStream is = classLoader.getResourceAsStream(
						"com/liferay/portal/tools/sql/dependencies/" +
							includeFileName);

					if (is == null) {
						is = classLoader.getResourceAsStream(includeFileName);
					}

					String include = StringUtil.read(is);

					is.close();

					if (includeFileName.endsWith(".vm")) {
						try {
							include = evaluateVM(include);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}

					include = convertTimestamp(include);
					include = replaceTemplate(include, getTemplate());

					runSQLTemplateString(include, false, true);
				}
				else{
					sb.append(line);

					if (line.endsWith(";")) {
						String sql = sb.toString();

						sb = new StringBuilder();

						try {
							if (!sql.equals("COMMIT_TRANSACTION;")) {
								runSQL(sql);
							}
							else {
								if (_log.isDebugEnabled()) {
									_log.debug("Skip commit sql");
								}
							}
						}
						catch (IOException ioe) {
							if (failOnError) {
								throw ioe;
							}
							else if (_log.isWarnEnabled()) {
								_log.warn(ioe.getMessage());
							}
						}
						catch (SQLException sqle) {
							if (failOnError) {
								throw sqle;
							}
							else if (_log.isWarnEnabled()) {
								String message = GetterUtil.getString(
									sqle.getMessage());

								if (!message.startsWith("Duplicate key name")) {
									_log.warn(message + ": " + sql);
								}

								if (message.startsWith("Duplicate entry") ||
									message.startsWith(
										"Specified key was too long")) {

									_log.error(line);
								}
							}
						}
					}
				}
			}
		}

		br.close();
	}

	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery) {

		if (_log.isInfoEnabled()) {
			if (supportsStringCaseSensitiveQuery) {
				_log.info("Database supports case sensitive queries");
			}
			else {
				_log.info("Database does not support case sensitive queries");
			}
		}

		_supportsStringCaseSensitiveQuery = supportsStringCaseSensitiveQuery;
	}

	protected DBUtil(String type) {
		_type = type;
	}

	protected abstract void buildCreateFile(
			String databaseName, boolean minimal)
		throws IOException;

	protected String[] buildColumnNameTokens(String line) {
		String[] words = StringUtil.split(line, " ");

		if (words.length == 7) {
			words[5] = "not null;";
		}

		String[] template = {
			words[1], words[2], words[3], words[4], words[5]
		};

		return template;
	}

	protected String[] buildColumnTypeTokens(String line) {
		String[] words = StringUtil.split(line, " ");

		String nullable = "";

		if (words.length == 6) {
			nullable = "not null;";
		}
		else if (words.length == 5) {
			nullable = words[4];
		}
		else if (words.length == 4) {
			nullable = "not null;";

			if (words[3].endsWith(";")) {
				words[3] = words[3].substring(0, words[3].length() - 1);
			}
		}

		String[] template = {
			words[1], words[2], "", words[3], nullable
		};

		return template;
	}

	protected String buildTemplate(String fileName) throws IOException {
		File file = new File("../sql/" + fileName + ".sql");

		String template = FileUtil.read(file);

		if (fileName.equals("portal") || fileName.equals("portal-minimal") ||
			fileName.equals("update-5.0.1-5.1.0")) {

			BufferedReader br = new BufferedReader(new StringReader(template));

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = br.readLine()) != null) {
				if (line.startsWith("@include ")) {
					int pos = line.indexOf(" ");

					String includeFileName = line.substring(pos + 1);

					File includeFile = new File("../sql/" + includeFileName);

					if (!includeFile.exists()) {
						continue;
					}

					String include = FileUtil.read(includeFile);

					if (includeFileName.endsWith(".vm")) {
						try {
							include = evaluateVM(include);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}

					include = convertTimestamp(include);
					include = replaceTemplate(include, getTemplate());

					sb.append(include);
					sb.append("\n\n");
				}
				else {
					sb.append(line);
					sb.append("\n");
				}
			}

			br.close();

			template = sb.toString();
		}

		if (fileName.equals("indexes") && (this instanceof SybaseUtil)) {
			template = removeBooleanIndexes(template);
		}

		return template;
	}

	protected String convertTimestamp(String data) {
		String s = null;

		if (this instanceof MySQLUtil) {
			s = StringUtil.replace(data, "SPECIFIC_TIMESTAMP_", "");
		}
		else {
			s = data.replaceAll(
				"SPECIFIC_TIMESTAMP_" + "\\d+", "CURRENT_TIMESTAMP");
		}

		return s;
	}

	protected String evaluateVM(String template) throws Exception {
		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("counter", new SimpleCounter());

		template = VelocityUtil.evaluate(template, variables);

		// Trim insert statements because it breaks MySQL Query Browser

		BufferedReader br = new BufferedReader(new StringReader(template));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			line = line.trim();

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		template = sb.toString();
		template = StringUtil.replace(template, "\n\n\n", "\n\n");

		return template;
	}

	protected String getMinimalSuffix(boolean minimal) {
		if (minimal) {
			return "-minimal";
		}
		else {
			return StringPool.BLANK;
		}
	}

	protected abstract String getServerName();

	protected abstract String[] getTemplate();

	protected String readSQL(String fileName, String comments, String eol)
		throws IOException {

		BufferedReader br = new BufferedReader(
			new FileReader(new File(fileName)));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith(comments)) {
				line = StringUtil.replace(
					line,
					new String[] {"\n", "\t"},
					new String[] {"", ""});

				if (line.endsWith(";")) {
					sb.append(line.substring(0, line.length() - 1));
					sb.append(eol);
				}
				else {
					sb.append(line);
				}
			}
		}

		br.close();

		return sb.toString();
	}

	protected String removeBooleanIndexes(String data) throws IOException {
		String portalData = FileUtil.read("../sql/portal-tables.sql");

		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			boolean append = true;

			int x = line.indexOf(" on ");

			if (x != -1) {
				int y = line.indexOf(" (", x);

				String table = line.substring(x + 4, y);

				x = y + 2;
				y = line.indexOf(")", x);

				String[] columns = StringUtil.split(line.substring(x, y));

				x = portalData.indexOf("create table " + table + " (");
				y = portalData.indexOf(");", x);

				String portalTableData = portalData.substring(x, y);

				for (int i = 0; i < columns.length; i++) {
					if (portalTableData.indexOf(
							columns[i].trim() + " BOOLEAN") != -1) {

						append = false;

						break;
					}
				}
			}

			if (append) {
				sb.append(line);
				sb.append("\n");
			}
		}

		br.close();

		return sb.toString();
	}

	protected String removeInserts(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("insert into ") &&
				!line.startsWith("update ")) {

				sb.append(line);
				sb.append("\n");
			}
		}

		br.close();

		return sb.toString();
	}

	protected String removeLongInserts(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("insert into Image (") &&
				!line.startsWith("insert into JournalArticle (") &&
				!line.startsWith("insert into JournalStructure (") &&
				!line.startsWith("insert into JournalTemplate (")) {

				sb.append(line);
				sb.append("\n");
			}
		}

		br.close();

		return sb.toString();
	}

	protected String removeNull(String content) {
		content = StringUtil.replace(content, " not null", " not_null");
		content = StringUtil.replace(content, " null", "");
		content = StringUtil.replace(content, " not_null", " not null");

		return content;
	}

	protected String replaceTemplate(String template, String[] actual) {
		if ((template == null) || (TEMPLATE == null) || (actual == null)) {
			return null;
		}

		if (TEMPLATE.length != actual.length) {
			return template;
		}

		for (int i = 0; i < TEMPLATE.length; i++) {
			if (TEMPLATE[i].equals("##") ||
				TEMPLATE[i].equals("'01/01/1970'")) {

				template = template.replaceAll(TEMPLATE[i], actual[i]);
			}
			else {
				template = template.replaceAll(
					"\\b" + TEMPLATE[i] + "\\b", actual[i]);
			}
		}

		return template;
	}

	protected abstract String reword(String data) throws IOException;

	protected static String ALTER_COLUMN_TYPE = "alter_column_type ";

	protected static String ALTER_COLUMN_NAME = "alter_column_name ";

	protected static String DROP_PRIMARY_KEY = "drop primary key";

	protected static String[] REWORD_TEMPLATE = {
		"@table@", "@old-column@", "@new-column@", "@type@", "@nullable@"
	};

	protected static String[] TEMPLATE = {
		"##", "TRUE", "FALSE",
		"'01/01/1970'", "CURRENT_TIMESTAMP",
		" BLOB", " BOOLEAN", " DATE",
		" DOUBLE", " INTEGER", " LONG",
		" STRING", " TEXT", " VARCHAR",
		" IDENTITY", "COMMIT_TRANSACTION"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_NAME = true;

	private static boolean _SUPPORTS_ALTER_COLUMN_TYPE = true;

	private static boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN;

	private static Log _log = LogFactoryUtil.getLog(DBUtil.class);

	private static DBUtil _dbUtil;

	private String _type;
	private boolean _supportsStringCaseSensitiveQuery;

}