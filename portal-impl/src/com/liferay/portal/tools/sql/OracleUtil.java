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
 * <a href="OracleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class OracleUtil extends DBUtil {

	public static DBUtil getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = _preBuildSQL(template);
		template = _postBuildSQL(template);

		return template;
	}

	public void buildSQLFile(String fileName) throws IOException {
		String oracle = buildTemplate(fileName);

		oracle = _preBuildSQL(oracle);

		BufferedReader br = new BufferedReader(new StringReader(oracle));

		StringBuilder imageSB = new StringBuilder();
		StringBuilder journalArticleSB = new StringBuilder();
		StringBuilder journalStructureSB = new StringBuilder();
		StringBuilder journalTemplateSB = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith("insert into Image")) {
				_convertToOracleCSV(line, imageSB);
			}
			else if (line.startsWith("insert into JournalArticle (")) {
				_convertToOracleCSV(line, journalArticleSB);
			}
			else if (line.startsWith("insert into JournalStructure (")) {
				_convertToOracleCSV(line, journalStructureSB);
			}
			else if (line.startsWith("insert into JournalTemplate (")) {
				_convertToOracleCSV(line, journalTemplateSB);
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

		oracle = _postBuildSQL(oracle);

		FileUtil.write(
			"../sql/" + fileName + "/" + fileName + "-oracle.sql", oracle);
	}

	public boolean isSupportsAlterColumnType() {

		// LPS-3566

		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	protected OracleUtil() {
		super(TYPE_ORACLE);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBuilder sb = new StringBuilder();

		sb.append("drop user &1 cascade;\n");
		sb.append("create user &1 identified by &2;\n");
		sb.append("grant connect,resource to &1;\n");
		sb.append("connect &1/&2;\n");
		sb.append("set define off;\n");
		sb.append("\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + suffix + "/portal" + suffix + "-oracle.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-oracle.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-oracle.sql"));
		sb.append("\n");
		sb.append("quit");

		return sb.toString();
	}

	protected String getServerName() {
		return "oracle";
	}

	protected String[] getTemplate() {
		return _ORACLE;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ rename column @old-column@ to " +
						"@new-column@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify @old-column@ @type@;",
					REWORD_TEMPLATE, template);
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private void _convertToOracleCSV(String line, StringBuilder sb) {
		int x = line.indexOf("values (");
		int y = line.lastIndexOf(");");

		line = line.substring(x + 8, y);

		line = StringUtil.replace(line, "sysdate, ", "20050101, ");

		sb.append(line);
		sb.append("\n");
	}

	private String _preBuildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(
			template,
			new String[] {"\\\\", "\\'", "\\\""},
			new String[] {"\\", "''", "\""});

		return template;
	}

	private String _postBuildSQL(String template) throws IOException {
		template = removeLongInserts(template);
		template = StringUtil.replace(template, "\\n", "'||CHR(10)||'");

		return template;
	}

	private static String[] _ORACLE = {
		"--", "1", "0",
		"to_date('1970-01-01 00:00:00','YYYY-MM-DD HH24:MI:SS')", "sysdate",
		" blob", " number(1, 0)", " timestamp",
		" number(30,20)", " number(30,0)", " number(30,0)",
		" varchar2(4000)", " clob", " varchar2",
		"", "commit"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_TYPE;

	private static OracleUtil _instance = new OracleUtil();

}