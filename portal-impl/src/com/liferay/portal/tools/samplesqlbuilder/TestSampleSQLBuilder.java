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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;

import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.Map;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class TestSampleSQLBuilder {

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String sqlDir = arguments.get("sql.dir");
		String workDir = arguments.get("sample.sql.output.dir");

		SampleSQLBuilder.main(args);

		new TestSampleSQLBuilder(sqlDir, workDir);
	}

	public TestSampleSQLBuilder(String sqlDir, String workDir) {
		try {
			_sqlDir = sqlDir;
			_workDir = workDir;

			_loadHypersonic();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _loadHypersonic() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		Connection con = DriverManager.getConnection(
			"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa", "");

		_loadHypersonic(
			con, _sqlDir + "/portal-minimal/portal-minimal-hypersonic.sql");
		_loadHypersonic(con, _sqlDir + "/indexes/indexes-hypersonic.sql");
		_loadHypersonic(con, _workDir + "/sample-hypersonic.sql");

		// Shutdown Hypersonic

		Statement statement = con.createStatement();

		statement.execute("SHUTDOWN COMPACT");

		statement.close();

		con.close();
	}

	private void _loadHypersonic(Connection con, String fileName)
		throws Exception {

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(fileName));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("//")) {
				sb.append(line);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sb.setIndex(0);

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

					PreparedStatement ps = con.prepareStatement(sql);

					ps.executeUpdate();

					ps.close();
				}
			}
		}

		unsyncBufferedReader.close();
	}

	private String _sqlDir;
	private String _workDir;

}