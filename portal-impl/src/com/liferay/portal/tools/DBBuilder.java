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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.InitUtil;

import java.io.IOException;

/**
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
			_buildSQLFile(sqlDir, "tables");
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
			_buildSQLFile(sqlDir, "update-5.2.5-6.0.0");
			_buildSQLFile(sqlDir, "update-5.2.7-6.0.0");
			_buildSQLFile(sqlDir, "update-5.2.8-6.0.5");
			_buildSQLFile(sqlDir, "update-6.0.0-6.0.1");
			_buildSQLFile(sqlDir, "update-6.0.1-6.0.2");
			_buildSQLFile(sqlDir, "update-6.0.2-6.0.3");
			_buildSQLFile(sqlDir, "update-6.0.4-6.0.5");

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
				if (sqlDir.equals("../sql")) {
					db.buildCreateFile(sqlDir, _databaseName);
				}
				else {
					db.buildCreateFile(sqlDir, _databaseName, DB.POPULATED);
				}
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