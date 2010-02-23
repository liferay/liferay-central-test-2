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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PostgreSQLDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class PostgreSQLDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);

		return template;
	}

	public List<Index> getIndexes() throws SQLException {
		List<Index> indexes = new ArrayList<Index>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("select indexname, tablename, indexdef from pg_indexes ");
			sb.append("where indexname like 'liferay_%' or indexname like ");
			sb.append("'ix_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("indexname");
				String tableName = rs.getString("tablename");
				String indexSQL = rs.getString("indexdef").toLowerCase().trim();

				boolean unique = true;

				if (indexSQL.startsWith("create index ")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return indexes;
	}

	protected PostgreSQLDB() {
		super(TYPE_POSTGRESQL);
	}

	protected String buildCreateFileContent(
			String databaseName, int population, String sqlDir)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(14);

		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(" encoding = 'UNICODE';\n");
		sb.append("\\c ");
		sb.append(databaseName);
		sb.append(";\n\n");
		sb.append(
			FileUtil.read(
				sqlDir + "/portal" + suffix + "/portal" + suffix +
					"-postgresql.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read(sqlDir + "/indexes/indexes-postgresql.sql"));
		sb.append("\n\n");
		sb.append(
			FileUtil.read(sqlDir + "/sequences/sequences-postgresql.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "postgresql";
	}

	protected String[] getTemplate() {
		return _POSTGRESQL;
	}

	protected String reword(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ rename @old-column@ to @new-column@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter @old-column@ type @type@ " +
						"using @old-column@::@type@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.indexOf(DROP_INDEX) != -1) {
				String[] tokens = StringUtil.split(line, " ");

				line = StringUtil.replace(
					"drop index @index@;", "@index@", tokens[2]);
			}
			else if (line.indexOf(DROP_PRIMARY_KEY) != -1) {
				String[] tokens = StringUtil.split(line, " ");

				line = StringUtil.replace(
					"alter table @table@ drop constraint @table@_pkey;",
					"@table@", tokens[2]);
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	private static String[] _POSTGRESQL = {
		"--", "true", "false",
		"'01/01/1970'", "current_timestamp",
		" bytea", " bool", " timestamp",
		" double precision", " integer", " bigint",
		" text", " text", " varchar",
		"", "commit"
	};

	private static PostgreSQLDB _instance = new PostgreSQLDB();

}