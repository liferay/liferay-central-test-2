/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.derby.tools.ij;

/**
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

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(_fileUtil.read(fileName)));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
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

					sb.setIndex(0);

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

		unsyncBufferedReader.close();
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

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(_fileUtil.read(fileName)));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
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

					sb.setIndex(0);

					PreparedStatement ps = con.prepareStatement(sql);

					ps.executeUpdate();

					ps.close();
				}
			}
		}

		unsyncBufferedReader.close();
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

	private String _databaseType;
	private String _databaseName;
	private String _fileName;

}