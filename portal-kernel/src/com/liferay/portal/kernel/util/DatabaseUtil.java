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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;

import java.io.IOException;

import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * <a href="DatabaseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author	   Ganesh Ram
 * @author	   Brian Wing Shun Chan
 * @deprecated {@link DBFactoryUtil}
 */
public class DatabaseUtil {

	public static Database getDatabase() {
		if (_database != null) {
			return _database;
		}

		_database = new Database() {

			public String getType() {
				DB db = DBFactoryUtil.getDB();

				return db.getType();
			}

			public void runSQLTemplate(String path)
				throws IOException, NamingException, SQLException {

				DB db = DBFactoryUtil.getDB();

				db.runSQLTemplate(path);
			}

			public void runSQLTemplate(String path, boolean failOnError)
				throws IOException, NamingException, SQLException {

				DB db = DBFactoryUtil.getDB();

				db.runSQLTemplate(path, failOnError);
			}

		};

		return _database;
	}

	public static String getType() {
		return getDatabase().getType();
	}

	public static void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		getDatabase().runSQLTemplate(path);
	}

	public static void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		getDatabase().runSQLTemplate(path, failOnError);
	}

	public void setDatabase(Database database) {
		_database = database;
	}

	private static Database _database;

}