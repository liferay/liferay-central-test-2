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
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * <a href="IngresDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author David Maier
 */
public class IngresDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, "\\n", "'+x'0a'+'");

		return template;
	}

	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	protected IngresDB() {
		super(TYPE_INGRES);
	}

	protected String buildCreateFileContent(
		String databaseName, int population) {

		return null;
	}

	protected String getServerName() {
		return "ingres";
	}

	protected String[] getTemplate() {
		return _INGRES;
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
			else if (TEMPLATE[i].equals("COMMIT_TRANSACTION")) {
				template = StringUtil.replace(
					template, TEMPLATE[i] + ";", actual[i]);
			}
			else {
				template = template.replaceAll(
					"\\b" + TEMPLATE[i] + "\\b", actual[i]);
			}
		}

		return template;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new UnsyncStringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				line = "-- " + line;

				if (_log.isWarnEnabled()) {
					_log.warn(
						"This statement is not supported by Ingres: " + line);
				}
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ alter @old-column@ @type@;",
					REWORD_TEMPLATE, template);
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

		br.close();

		return sb.toString();
	}

	private static String[] _INGRES = {
		"--", "1", "0",
		"'1970-01-01'", "date('now')",
		" byte varying", " tinyint", " timestamp",
		" float", " integer", " bigint",
		" varchar(1000)", " long varchar", " varchar",
		"", "commit;\\g"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_NAME;

	private static Log _log = LogFactoryUtil.getLog(IngresDB.class);

	private static IngresDB _instance = new IngresDB();

}