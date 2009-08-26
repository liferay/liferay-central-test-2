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
import com.liferay.portal.util.PropsValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * <a href="MySQLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class MySQLUtil extends DBUtil {

	public static DBUtil getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\\'", "''");

		return template;
	}

	public boolean isSupportsUpdateWithInnerJoin() {
		return _SUPPORTS_UPDATE_WITH_INNER_JOIN;
	}

	protected MySQLUtil() {
		super(TYPE_MYSQL);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBuilder sb = new StringBuilder();

		sb.append("drop database if exists " + databaseName + ";\n");
		sb.append("create database " + databaseName + " character set utf8;\n");
		sb.append("use ");
		sb.append(databaseName);
		sb.append(";\n\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + suffix + "/portal" + suffix + "-mysql.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-mysql.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-mysql.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "mysql";
	}

	protected String[] getTemplate() {
		return _MYSQL;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		boolean createTable = false;

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (StringUtil.startsWith(line, "create table")) {
				createTable = true;
			}
			else if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ change column @old-column@ " +
						"@new-column@ @type@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify @old-column@ @type@;",
					REWORD_TEMPLATE, template);
			}

			int pos = line.indexOf(";");

			if (createTable && (pos != -1)) {
				createTable = false;

				line =
					line.substring(0, pos) + " engine " +
						PropsValues.DATABASE_MYSQL_ENGINE + line.substring(pos);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private static String[] _MYSQL = {
		"##", "1", "0",
		"'1970-01-01'", "now()",
		" blob", " tinyint", " datetime",
		" double", " integer", " bigint",
		" longtext", " longtext", " varchar",
		"  auto_increment", "commit"
	};

	private static boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN = true;

	private static MySQLUtil _instance = new MySQLUtil();

}