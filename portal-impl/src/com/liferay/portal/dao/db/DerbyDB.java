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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * <a href="DerbyDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class DerbyDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template );
		//template = _removeLongInserts(derby);
		template = removeNull(template);
		template = StringUtil.replace(template , "\\'", "''");

		return template;
	}

	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	protected DerbyDB() {
		super(TYPE_DERBY);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(14);

		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("connect to ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + suffix + "/portal" + suffix + "-derby.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-derby.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-derby.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "derby";
	}

	protected String[] getTemplate() {
		return _DERBY;
	}

	protected String reword(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME) ||
				line.startsWith(ALTER_COLUMN_TYPE)) {

				line = "-- " + line;

				if (_log.isWarnEnabled()) {
					_log.warn(
						"This statement is not supported by Derby: " + line);
				}
			}
			else if (line.indexOf(DROP_INDEX) != -1) {
				String[] tokens = StringUtil.split(line, " ");

				line = StringUtil.replace(
					"drop index @index@;", "@index@", tokens[2]);
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	private static String[] _DERBY = {
		"--", "1", "0",
		"'1970-01-01-00.00.00.000000'", "current timestamp",
		" blob", " smallint", " timestamp",
		" double", " integer", " bigint",
		" varchar(4000)", " clob", " varchar",
		" generated always as identity", "commit"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_NAME;

	private static boolean _SUPPORTS_ALTER_COLUMN_TYPE;

	private static Log _log = LogFactoryUtil.getLog(DerbyDB.class);

	private static DerbyDB _instance = new DerbyDB();

}