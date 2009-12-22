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

package com.liferay.portal.events;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.UpgradeProcessUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcessUtil;

/**
 * <a href="StartupHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class StartupHelper {

	public void setDropIndexes(boolean dropIndexes) {
		_dropIndexes = dropIndexes;
	}

	public void updateIndexes() {
		try {
			DB db = DBFactoryUtil.getDB();

			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			String tablesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

			String indexesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");

			String indexesProperties = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/indexes.properties");

			db.updateIndexes(
				tablesSQL, indexesSQL, indexesProperties, _dropIndexes);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void upgradeProcess(int buildNumber) throws UpgradeException {
		String[] upgradeProcessClassNames = PropsUtil.getArray(
			PropsKeys.UPGRADE_PROCESSES);

		_upgraded = UpgradeProcessUtil.upgradeProcess(
			buildNumber, upgradeProcessClassNames,
			PortalClassLoaderUtil.getClassLoader());
	}

	public void verifyProcess(boolean verified) throws VerifyException {
		_verified = VerifyProcessUtil.verifyProcess(_upgraded, verified);
	}

	public boolean isUpgraded() {
		return _upgraded;
	}

	public boolean isVerified() {
		return _verified;
	}

	private static Log _log = LogFactoryUtil.getLog(StartupHelper.class);

	private boolean _dropIndexes;
	private boolean _upgraded;
	private boolean _verified;

}