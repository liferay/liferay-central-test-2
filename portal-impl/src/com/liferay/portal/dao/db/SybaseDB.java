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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * <a href="SybaseDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class SybaseDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(template, ");\n", ")\ngo\n");
		template = StringUtil.replace(template, "\ngo;\n", "\ngo\n");
		template = StringUtil.replace(
			template,
			new String[] {"\\\\", "\\'", "\\\"", "\\n", "\\r"},
			new String[] {"\\", "''", "\"", "\n", "\r"});

		return template;
	}

	protected SybaseDB() {
		super(TYPE_SYBASE);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBuilder sb = new StringBuilder();

		sb = new StringBuilder();

		sb.append("use master\n");
		sb.append(
			"exec sp_dboption '" + databaseName + "', " +
				"'allow nulls by default' , true\n");
		sb.append("go\n\n");
		sb.append(
			"exec sp_dboption '" + databaseName + "', " +
				"'select into/bulkcopy/pllsort' , true\n");
		sb.append("go\n\n");

		sb.append("use " + databaseName + "\n\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + suffix + "/portal" + suffix + "-sybase.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-sybase.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-sybase.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "sybase";
	}

	protected String[] getTemplate() {
		return _SYBASE;
	}

	protected String reword(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.indexOf(DROP_COLUMN) != -1) {
				line = StringUtil.replace(line, " drop column ", " drop ");
			}

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
			else if (line.indexOf(DROP_INDEX) != -1) {
				String[] tokens = StringUtil.split(line, " ");

				line = StringUtil.replace(
					"drop index @table@.@index@;", "@table@", tokens[4]);
				line = StringUtil.replace(line, "@index@", tokens[2]);
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	protected static String DROP_COLUMN = "drop column";

	private static String[] _SYBASE = {
		"--", "1", "0",
		"'19700101'", "getdate()",
		" image", " int", " datetime",
		" float", " int", " decimal(20,0)",
		" varchar(1000)", " text", " varchar",
		"  identity(1,1)", "go"
	};

	private static SybaseDB _instance = new SybaseDB();

}