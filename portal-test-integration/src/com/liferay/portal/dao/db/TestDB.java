/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

/**
 * @author Manuel de la Peña
 */
public class TestDB extends BaseDB {

	public TestDB(DBType dbType, int majorVersion, int minorVersion) {
		super(dbType, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		return StringPool.BLANK;
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		return StringPool.BLANK;
	}

	@Override
	protected String getServerName() {
		return StringPool.BLANK;
	}

	@Override
	protected String[] getTemplate() {
		return new String[] {
			"##", "TRUE", "FALSE", "'01/01/1970'", "CURRENT_TIMESTAMP", " BLOB",
			" SBLOB", " BOOLEAN", " DATE", " DOUBLE", " INTEGER", " LONG",
			" STRING", " TEXT", " VARCHAR", " IDENTITY", "COMMIT_TRANSACTION"
		};
	}

	@Override
	protected String reword(String data) throws IOException {
		return data;
	}

}