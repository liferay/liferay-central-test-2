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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * <a href="SQLServerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class SQLServerUtil extends DBUtil {

	public static DBUtil getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template,
			new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	protected SQLServerUtil() {
		super(TYPE_SQLSERVER);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBuilder sb = new StringBuilder();

		sb.append("drop database " + databaseName + ";\n");
		sb.append("create database " + databaseName + ";\n");
		sb.append("\n");
		sb.append("go\n");
		sb.append("\n");
		sb.append("use " + databaseName + ";\n\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + suffix + "/portal" + suffix +
					"-sql-server.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-sql-server.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-sql-server.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "sql-server";
	}

	protected String[] getTemplate() {
		return _SQL_SERVER;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"exec sp_rename '@table@.@old-column@', '@new-column@', " +
						"'column';",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter column @old-column@ @type@;",
					REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private static String[] _SQL_SERVER = {
		"--", "1", "0",
		"'19700101'", "GetDate()",
		" image", " bit", " datetime",
		" float", " int", " bigint",
		" nvarchar(2000)", " nvarchar(max)", " nvarchar",
		"  identity(1,1)", "go"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_TYPE;

	private static SQLServerUtil _instance = new SQLServerUtil();

}