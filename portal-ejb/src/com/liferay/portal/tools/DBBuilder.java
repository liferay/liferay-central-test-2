/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.velocity.VelocityUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * <a href="DBBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Charles May
 *
 */
public class DBBuilder {

	public static void main(String[] args) {
		new DBBuilder();
	}

	public DBBuilder() {
		try {
			_buildSQL("portal");
			_buildSQL("update-1.7.5-1.8.0");
			_buildSQL("update-1.8.0-1.9.0");
			_buildSQL("update-1.9.1-1.9.5");
			_buildSQL("update-1.9.5-2.0.0");
			_buildSQL("update-2.0.3-2.1.0");
			_buildSQL("update-2.1.1-2.2.0");
			_buildSQL("update-2.2.1-2.2.5");
			_buildSQL("update-3.1.0-3.2.0");
			_buildSQL("update-3.2.0-3.5.0");
			_buildSQL("update-3.5.0-3.6.0");
			_buildSQL("update-3.6.0-4.0.0");
			_buildCreate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildCreate() throws IOException {

		// DB2

		File file = new File("../sql/create/create-db2.sql");

		StringBuffer sb = new StringBuffer();

		sb.append("drop database lportal\n");
		sb.append("create database lportal\n");
		sb.append("connect to lportal\n");
		sb.append(_readSQL("../sql/portal/portal-db2.sql", _DB2[0], "\n"));

		/*sb.append(
			StringUtil.replace(
				FileUtil.read("../sql/indexes.sql"),
				new String[] {"\n", ";"},
				new String[] {"", "\n"}));*/

		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences.sql"));

		FileUtil.write(file, sb.toString());

		// Firebird

		file = new File("../sql/create/create-firebird.sql");

		sb = new StringBuffer();

		sb.append("create database 'lportal.gdb' page_size 8192 user 'sysdba' password 'masterkey';\n");
		sb.append("connect 'lportal.gdb' user 'sysdba' password 'masterkey';\n");
		sb.append(
			_readSQL("../sql/portal/portal-firebird.sql", _FIREBIRD[0], ";\n"));
		sb.append("commit;\n");

		FileUtil.write(file, sb.toString());

		// MySQL

		file = new File("../sql/create/create-mysql.sql");

		sb = new StringBuffer();

		sb.append("drop database if exists lportal;\n");
		sb.append("create database lportal character set utf8;\n");
		sb.append("use lportal;\n");
		sb.append("\n");
		sb.append(FileUtil.read("../sql/portal/portal-mysql.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes.sql"));
		sb.append("\n\n");
		sb.append("commit;");

		FileUtil.write(file, sb.toString());

		// Oracle

		file = new File("../sql/create/create-oracle.sql");

		sb = new StringBuffer();

		sb.append("drop user &1 cascade;\n");
		sb.append("create user &1 identified by &2;\n");
		sb.append("grant connect,resource to &1;\n");
		sb.append("connect &1/&2;\n");
		sb.append("\n");
		sb.append(FileUtil.read("../sql/portal/portal-oracle.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences.sql"));
		sb.append("\n\n");
		sb.append("commit;\n");
		sb.append("\n");
		sb.append("quit");

		FileUtil.write(file, sb.toString());

		// PostgreSQL

		file = new File("../sql/create/create-postgresql.sql");

		sb = new StringBuffer();

		sb.append("drop database lportal;\n");
		sb.append("create database lportal encoding = 'UNICODE';\n");
		sb.append("\\c lportal;\n");
		sb.append("\n");
		sb.append(FileUtil.read("../sql/portal/portal-postgresql.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences.sql"));

		FileUtil.write(file, sb.toString());

		// SQL Server

		file = new File("../sql/create/create-sql-server.sql");

		sb = new StringBuffer();

		sb.append("drop database lportal;\n");
		sb.append("create database lportal;\n");
		sb.append("\n");
		sb.append("go\n");
		sb.append("\n");
		sb.append("use lportal;\n");
		sb.append("\n");
		sb.append(FileUtil.read("../sql/portal/portal-sql-server.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes.sql"));
		sb.append("\n\n");
		sb.append("go");

		FileUtil.write(file, sb.toString());
	}

	private static String[] _buildColumnNameTokens(String line) {
		String[] words = StringUtil.split(line, " ");

		if (words.length == 7) {
			words[5] = "not null;";
		}

		String[] template = {
			words[1], words[2], words[3], words[4], words[5]
		};

		return template;
	}

	private static String[] _buildColumnTypeTokens(String line) {
		String[] words = StringUtil.split(line, " ");

		if (words.length == 6) {
			words[4] = "not null;";
		}

		String[] template = {
			words[1], words[2], "", words[3], words[4]
		};

		return template;
	}

	private void _buildSQL(String fileName) throws IOException {
		File file = new File("../sql/" + fileName + ".sql");

		if (!file.exists()) {
			return;
		}

		// DB2

		String db2 = _buildTemplate(fileName, _DB2);

		db2 = _rewordDb2(db2);
		db2 = _removeLongInserts(db2);
		db2 = _removeNull(db2);
		db2 = StringUtil.replace(
			db2,
			new String[] {"\\'"},
			new String[] {"''"});

		FileUtil.write("../sql/" + fileName + "/" + fileName + "-db2.sql", db2);

		// Firebird

		String firebird = _buildTemplate(fileName, _FIREBIRD);

		firebird = _rewordFirebird(firebird);
		firebird = _removeInserts(firebird);
		firebird = _removeNull(firebird);

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-firebird.sql", firebird);

		// Hypersonic

		String hypersonic = _buildTemplate(fileName, _HYPERSONIC);

		hypersonic = _rewordHypersonic(hypersonic);
		hypersonic = StringUtil.replace(hypersonic, "\\'", "''");

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-hypersonic.sql",
			hypersonic);

		// InterBase

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-interbase.sql", firebird);

		// JDataStore

		String jDataStore = _buildTemplate(fileName, _JDATASTORE);

		jDataStore = _rewordFirebird(jDataStore);
		jDataStore = StringUtil.replace(
			jDataStore,
			new String[] {"\\'", "\\\"","\\\\",  "\\n", "\\r"},
			new String[] {"''", "\"", "\\", "\n", "\r"});

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-jdatastore.sql",
			jDataStore);

		// MySQL

		String mysql = _buildTemplate(fileName, _MYSQL, true);

		mysql = _rewordMysql(mysql);
		mysql = StringUtil.replace(
			mysql,
			new String[] {"\\'"},
			new String[] {"''"});

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-mysql.sql", mysql);

		// Oracle

		String oracle = _buildTemplate(fileName, _ORACLE);

		oracle = _rewordOracle(oracle);
		oracle = StringUtil.replace(
			oracle,
			new String[] {"\\\\", "\\'", "\\\""},
			new String[] {"\\", "''", "\""});

		BufferedReader br = new BufferedReader(new StringReader(oracle));

		StringBuffer imageSB = new StringBuffer();
		StringBuffer journalArticleSB = new StringBuffer();
		StringBuffer journalStructureSB = new StringBuffer();
		StringBuffer journalTemplateSB = new StringBuffer();
		StringBuffer shoppingItemSB = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith("insert into Image")) {
				_convertToOracleCSV(line, imageSB);
			}
			else if (line.startsWith("insert into JournalArticle")) {
				_convertToOracleCSV(line, journalArticleSB);
			}
			else if (line.startsWith("insert into JournalStructure")) {
				_convertToOracleCSV(line, journalStructureSB);
			}
			else if (line.startsWith("insert into JournalTemplate")) {
				_convertToOracleCSV(line, journalTemplateSB);
			}
			else if (line.startsWith("insert into ShoppingItem")) {
				_convertToOracleCSV(line, shoppingItemSB);
			}
		}

		br.close();

		if (imageSB.length() > 0) {
			FileUtil.write(
				"../sql/" + fileName + "/" + fileName + "-oracle-image.csv",
				imageSB.toString());
		}

		if (journalArticleSB.length() > 0) {
			FileUtil.write(
				"../sql/" + fileName + "/" + fileName +
					"-oracle-journalarticle.csv",
				journalArticleSB.toString());
		}

		if (journalStructureSB.length() > 0) {
			FileUtil.write(
				"../sql/" + fileName + "/" + fileName +
					"-oracle-journalstructure.csv",
				journalStructureSB.toString());
		}

		if (journalTemplateSB.length() > 0) {
			FileUtil.write(
				"../sql/" + fileName + "/" + fileName +
					"-oracle-journaltemplate.csv",
				journalTemplateSB.toString());
		}

		if (shoppingItemSB.length() > 0) {
			FileUtil.write(
				"../sql/" + fileName + "/" + fileName +
					"-oracle-shoppingitem.csv",
				shoppingItemSB.toString());
		}

		oracle = _removeLongInserts(oracle);
		/*oracle = StringUtil.replace(
			oracle,
			new String[] {"\\'", "\\n", "\\r"},
			new String[] {"''", "\n", "\r"});*/
		oracle = StringUtil.replace(oracle, "\\n", "'||CHR(10)||'");

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-oracle.sql", oracle);

		// PostgreSQL

		String postgresql = _buildTemplate(fileName, _POSTGRESQL);

		postgresql = _rewordPostgreSQL(postgresql);

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-postgresql.sql",
			postgresql);

		// SAP

		String sap = _buildTemplate(fileName, _SAP);

		sap = _rewordSAP(sap);

		//sap = StringUtil.replace(sap, "varchar(100)", "varchar(75)");

		FileUtil.write("../sql/" + fileName + "/" + fileName + "-sap.sql", sap);

		// SQL Server

		String sqlServer = _buildTemplate(fileName, _SQL_SERVER);

		sqlServer = _rewordSQLServer(sqlServer);
		sqlServer = StringUtil.replace(
			sqlServer,
			new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-sql-server.sql",
			sqlServer);
	}

	private String _buildTemplate(String fileName, String[] replace)
		throws IOException {
		
		return _buildTemplate(fileName, replace, false);
	}

	private String _buildTemplate(
			String fileName, String[] replace, boolean mysql)
		throws IOException {

		File file = new File("../sql/" + fileName + ".sql");

		String template = FileUtil.read(file);

		if (fileName.equals("portal") ||
			fileName.equals("update-3.6.0-4.0.0")) {

			BufferedReader br = new BufferedReader(new StringReader(template));

			StringBuffer sb = new StringBuffer();

			String line = null;

			while ((line = br.readLine()) != null) {
				if (line.startsWith("@include ")) {
					int pos = line.indexOf(" ");

					String includeFileName =
						line.substring(pos + 1, line.length());

					String include = FileUtil.read("../sql/" + includeFileName);

					if (includeFileName.endsWith(".vm")) {
						try {
							include = _evaluateVM(include);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}

					//if (line.indexOf("portal-tables.sql") != -1) {
						include = _convertTimestamp(include, mysql);
						include = StringUtil.replace(
							include, _TEMPLATE, replace);
					//}

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

		if (fileName.startsWith("update-")) {
			template = _convertTimestamp(template, mysql);
			template = StringUtil.replace(template, _TEMPLATE, replace);
		}

		return template;
	}

	private String _convertTimestamp(String data, boolean mysql) {
		String s = null;
		if (mysql) {
			s = StringUtil.replace(data, _SPECIFIC_TIMESTAMP, "");
		}
		else {
			s = data.replaceAll(
				_SPECIFIC_TIMESTAMP + "\\d+", "CURRENT_TIMESTAMP");
		}
		return s;
	}

	private void _convertToOracleCSV(String line, StringBuffer sb) {
		int x = line.indexOf("values (");
		int y = line.lastIndexOf(");");

		line = line.substring(x + 8, y);

		line = StringUtil.replace(line, "sysdate, ", "20050101, ");

		sb.append(line);
		sb.append("\n");
	}

	private String _evaluateVM(String template) throws Exception {
		template = VelocityUtil.evaluate(template);

		// Trim insert statements because it breaks MySQL Query Browser

		BufferedReader br = new BufferedReader(new StringReader(template));

		StringBuffer sb = new StringBuffer();

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

	private String _readSQL(String fileName, String comments, String eol)
		throws IOException {

		BufferedReader br = new BufferedReader(
			new FileReader(new File(fileName)));

		StringBuffer sb = new StringBuffer();

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

	private String _removeInserts(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

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

	private String _removeLongInserts(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("insert into Image") &&
				!line.startsWith("insert into JournalArticle") &&
				!line.startsWith("insert into JournalStructure") &&
				!line.startsWith("insert into JournalTemplate") &&
				!line.startsWith("insert into ShoppingItem")) {

				sb.append(line);
				sb.append("\n");
			}
		}

		br.close();

		return sb.toString();
	}

	private String _removeNull(String content) {
		content = StringUtil.replace(content, " not null", " not_null");
		content = StringUtil.replace(content, " null", "");
		content = StringUtil.replace(content, " not_null", " not null");

		return content;
	}

	private String _rewordDb2(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE) ||
				line.startsWith(_ALTER_COLUMN_NAME)) {

				line = "-- " + line;
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordFirebird(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column \"@old-column@\" type @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column \"@old-column@\" to \"@new-column@\";",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordHypersonic(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column @type@ @nullable@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column @old-column@ rename to @new-column@;",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordMysql(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify @old-column@ @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ change column @old-column@ @new-column@ @type@;",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordOracle(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify @old-column@ @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ rename column @old-column@ to @new-column@;",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordPostgreSQL(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter @old-column@ type @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ rename @old-column@ to @new-column@;",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordSAP(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify @old-column@ @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"rename column @table@.@old-column@ to @new-column@;",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private String _rewordSQLServer(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuffer sb = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(_ALTER_COLUMN_TYPE)) {
				String[] template = _buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column @old-column@ @type@;",
					_REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(_ALTER_COLUMN_NAME)) {
				String[] template = _buildColumnNameTokens(line);

				line = StringUtil.replace(
					"exec sp_rename '@table@.@old-column@', '@new-column@', 'column';",
					_REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private static String _ALTER_COLUMN_TYPE = "alter_column_type ";

	private static String _ALTER_COLUMN_NAME = "alter_column_name ";

	private static String[] _REWORD_TEMPLATE = {
		"@table@", "@old-column@", "@new-column@", "@type@", "@nullable@"
	};

	private static String _SPECIFIC_TIMESTAMP = "SPECIFIC_TIMESTAMP_";

	private static String[] _TEMPLATE = {
		"##", "TRUE", "FALSE",
		"'01/01/1970'", "CURRENT_TIMESTAMP",
		" BOOLEAN", " DATE", " DOUBLE", " INTEGER", " STRING", " TEXT",
		" VARCHAR", " IDENTITY"
	};

	private static String[] _DB2 = {
		"--", "1", "0",
		"'1970-01-01-00.00.00.000000'", "current timestamp",
		" smallint", " timestamp", " double", " integer", " long varchar",
		" long varchar", " varchar", " generated always as identity"
	};

	private static String[] _FIREBIRD = {
		"--", "1", "0",
		"'01/01/1970'", "current_timestamp",
		" smallint", " timestamp", " double precision", " integer", " varchar(4000)",
		" blob", " varchar", ""
	};

	private static String[] _HYPERSONIC = {
		"//", "true", "false",
		"'1970-01-01'", "now()",
		" bit", " timestamp", " double", " int", " longvarchar", " longvarchar",
		" varchar", ""
	};

	private static String[] _JDATASTORE = {
		"--", "TRUE", "FALSE",
		"'1970-01-01'", "current_timestamp",
		" boolean", " date", " double", " integer", " long varchar",
		" long varchar", " varchar", ""
	};

	private static String[] _MYSQL = {
		"##", "1", "0",
		"'1970-01-01'", "now()",
		" tinyint", " datetime", " double", " integer", " longtext",
		" longtext", " varchar", "  auto_increment"
	};

	private static String[] _ORACLE = {
		"--", "1", "0",
		"to_date('1970-01-01 00:00:00','YYYY-MM-DD HH24:MI:SS')", "sysdate",
		" number(1, 0)", " timestamp", " number(30,20)", " number(30,0)",
		" varchar2(4000)", " clob", " varchar2", ""
	};

	private static String[] _POSTGRESQL = {
		"--", "true", "false",
		"'01/01/1970'", "current_timestamp",
		" bool", " timestamp", " double precision", " integer", " text", " text",
		" varchar", ""
	};

	private static String[] _SAP = {
		"##", "TRUE", "FALSE",
		"'1970-01-01 00:00:00.000000'", "timestamp",
		" boolean", " timestamp", " float", " int", " long", " long",
		" varchar", ""
	};

	private static String[] _SQL_SERVER = {
		"--", "1", "0",
		"'19700101'", "GetDate()",
		" bit", " datetime", " float", " int", " varchar(1000)", " text",
		" varchar", "  identity(1,1)"
	};

}