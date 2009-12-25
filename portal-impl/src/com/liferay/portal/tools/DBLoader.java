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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.BufferedReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.derby.tools.ij;

/**
 * <a href="DBLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DBLoader {

	public static void main(String[] args) {
		if (args.length == 2) {
			new DBLoader(args[0], args[1], StringPool.BLANK);
		}
		else if (args.length == 3) {
			new DBLoader(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public DBLoader(String databaseType, String databaseName, String fileName) {
		try {
			_databaseType = databaseType;
			_databaseName = databaseName;
			_fileName = fileName;

			if (_databaseType.equals("derby")) {
				_loadDerby();
			}
			else if (_databaseType.equals("hypersonic")) {
				_loadHypersonic();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _loadDerby() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		Connection con = DriverManager.getConnection(
			"jdbc:derby:" + _databaseName + ";create=true", "", "");

		if (Validator.isNull(_fileName)) {
			_loadDerby(con, "../sql/portal/portal-derby.sql");
			_loadDerby(con, "../sql/indexes.sql");
		}
		else {
			_loadDerby(con, _fileName);
		}
	}

	private void _loadDerby(Connection con, String fileName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(
			new UnsyncStringReader(_fileUtil.read(fileName)));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("--")) {
				sb.append(line);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sql =
						StringUtil.replace(
							sql,
							new String[] {
								"\\'",
								"\\\"",
								"\\\\",
								"\\n",
								"\\r"
							},
							new String[] {
								"''",
								"\"",
								"\\",
								"\n",
								"\r"
							});

					sql = sql.substring(0, sql.length() - 1);

					sb = new StringBuilder();

					if (sql.startsWith("commit")) {
						continue;
					}

					ij.runScript(
						con,
						new UnsyncByteArrayInputStream(
							sql.getBytes(StringPool.UTF8)),
						StringPool.UTF8, new UnsyncByteArrayOutputStream(),
						StringPool.UTF8);
				}
			}
		}

		br.close();
	}

	private void _loadHypersonic() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		// See LEP-2927. Appending ;shutdown=true to the database connection URL
		// guarantees that ${_databaseName}.log is purged.

		Connection con = DriverManager.getConnection(
			"jdbc:hsqldb:" + _databaseName + ";shutdown=true", "sa", "");

		if (Validator.isNull(_fileName)) {
			_loadHypersonic(con, "../sql/portal/portal-hypersonic.sql");
			_loadHypersonic(con, "../sql/indexes.sql");
		}
		else {
			_loadHypersonic(con, _fileName);
		}

		// Shutdown Hypersonic

		Statement statement = con.createStatement();

		statement.execute("SHUTDOWN COMPACT");

		statement.close();

		con.close();

		// Hypersonic will encode unicode characters twice, this will undo
		// it

		String content = _fileUtil.read(_databaseName + ".script");

		content = StringUtil.replace(content, "\\u005cu", "\\u");

		_fileUtil.write(_databaseName + ".script", content);
	}

	private void _loadHypersonic(Connection con, String fileName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(
			new UnsyncStringReader(_fileUtil.read(fileName)));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("//")) {
				sb.append(line);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sql =
						StringUtil.replace(
							sql,
							new String[] {
								"\\\"",
								"\\\\",
								"\\n",
								"\\r"
							},
							new String[] {
								"\"",
								"\\",
								"\\u000a",
								"\\u000a"
							});

					sb = new StringBuilder();

					PreparedStatement ps = con.prepareStatement(sql);

					ps.executeUpdate();

					ps.close();
				}
			}
		}

		br.close();
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private String _databaseType;
	private String _databaseName;
	private String _fileName;

}