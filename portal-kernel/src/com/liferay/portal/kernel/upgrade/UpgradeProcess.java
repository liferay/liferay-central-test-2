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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * <a href="UpgradeProcess.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class UpgradeProcess {

	public UpgradeProcess() {
	}

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public long increment() throws SystemException {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

	public boolean isSupportsAlterColumnName() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnName();
	}

	public boolean isSupportsAlterColumnType() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnType();
	}

	public boolean isSupportsStringCaseSensitiveQuery() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsStringCaseSensitiveQuery();
	}

	public boolean isSupportsUpdateWithInnerJoin() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsUpdateWithInnerJoin();
	}

	public void runSQL(String template) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(template);
	}

	public void runSQL(String[] templates) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(templates);
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

	public void upgrade() throws UpgradeException {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Upgrading " + getClass().getName());
			}

			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	public void upgrade(Class<?> upgradeProcessClass)
		throws UpgradeException {

		UpgradeProcess upgradeProcess = null;

		try {
			upgradeProcess = (UpgradeProcess)upgradeProcessClass.newInstance();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}

		upgradeProcess.upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess)
		throws UpgradeException {

		upgradeProcess.upgrade();
	}

	protected void doUpgrade() throws Exception {
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

}