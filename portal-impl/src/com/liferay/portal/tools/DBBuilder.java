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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.InitUtil;

import java.io.IOException;

/**
 * <a href="DBBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 */
public class DBBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 1) {
			new DBBuilder(args[0], DB.TYPE_ALL);
		}
		else if (args.length == 2) {
			new DBBuilder(args[0], StringUtil.split(args[1]));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public DBBuilder(String databaseName, String[] databaseTypes) {
		try {
			_databaseName = databaseName;
			_databaseTypes = databaseTypes;

			String sqlDir = System.getProperty("sql.dir", "../sql");

			_buildSQLFile(sqlDir, "portal");
			_buildSQLFile(sqlDir, "portal-minimal");
			_buildSQLFile(sqlDir, "indexes");
			_buildSQLFile(sqlDir, "sequences");
			_buildSQLFile(sqlDir, "update-4.2.0-4.3.0");
			_buildSQLFile(sqlDir, "update-4.3.0-4.3.1");
			_buildSQLFile(sqlDir, "update-4.3.1-4.3.2");
			_buildSQLFile(sqlDir, "update-4.3.2-4.3.3");
			_buildSQLFile(sqlDir, "update-4.3.3-4.3.4");
			_buildSQLFile(sqlDir, "update-4.3.6-4.4.0");
			_buildSQLFile(sqlDir, "update-4.4.0-5.0.0");
			_buildSQLFile(sqlDir, "update-5.0.1-5.1.0");
			_buildSQLFile(sqlDir, "update-5.1.1-5.1.2");
			_buildSQLFile(sqlDir, "update-5.1.2-5.2.0");
			_buildSQLFile(sqlDir, "update-5.2.0-5.2.1");
			_buildSQLFile(sqlDir, "update-5.2.2-5.2.3");
			_buildSQLFile(sqlDir, "update-5.2.3-6.0.0");

			_buildCreateFile(sqlDir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildCreateFile(String sqlDir) throws IOException {
		for (int i = 0; i < _databaseTypes.length; i++) {
			String databaseType = _databaseTypes[i];

			if (databaseType.equals(DB.TYPE_HYPERSONIC) ||
				databaseType.equals(DB.TYPE_INTERBASE) ||
				databaseType.equals(DB.TYPE_JDATASTORE) ||
				databaseType.equals(DB.TYPE_SAP)) {

				continue;
			}

			DB db = DBFactoryUtil.getDB(_databaseTypes[i]);

			if (db != null) {
				db.buildCreateFile(sqlDir, _databaseName);
			}
		}
	}

	private void _buildSQLFile(String sqlDir, String fileName)
		throws IOException {

		if (!FileUtil.exists(sqlDir + "/" + fileName + ".sql")) {
			return;
		}

		for (int i = 0; i < _databaseTypes.length; i++) {
			DB db = DBFactoryUtil.getDB(_databaseTypes[i]);

			if (db != null) {
				db.buildSQLFile(sqlDir, fileName);
			}
		}
	}

	private String _databaseName;
	private String[] _databaseTypes;

}