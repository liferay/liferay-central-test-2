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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;

/**
 * <a href="StartupHelperImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 *
 */
public class StartupHelperImpl implements StartupHelper {

	public void upgradeProcess(int buildNumber) throws UpgradeException {
		String[] upgradeProcesses = PropsUtil.getArray(
			PropsKeys.UPGRADE_PROCESSES);

		for (int i = 0; i < upgradeProcesses.length; i++) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing upgrade " + upgradeProcesses[i]);
			}

			UpgradeProcess upgradeProcess = (UpgradeProcess)InstancePool.get(
				upgradeProcesses[i]);

			if (upgradeProcess == null) {
				_log.error(upgradeProcesses[i] + " cannot be found");

				continue;
			}

			if ((upgradeProcess.getThreshold() == 0) ||
				(upgradeProcess.getThreshold() > buildNumber)) {

				if (_log.isDebugEnabled()) {
					_log.debug("Running upgrade " + upgradeProcesses[i]);
				}

				upgradeProcess.upgrade();

				if (_log.isDebugEnabled()) {
					_log.debug("Finished upgrade " + upgradeProcesses[i]);
				}

				_upgraded = true;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Upgrade threshold " + upgradeProcess.getThreshold() +
							" will not trigger upgrade");

					_log.debug("Skipping upgrade " + upgradeProcesses[i]);
				}
			}
		}
	}

	public void updateIndexes() {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Adding indexes");
			}

			DBUtil.getInstance().runSQLTemplate("indexes.sql", false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void verifyProcess(boolean verified) throws VerifyException {

		// LPS-1880

		boolean tempIndexReadOnly = PropsValues.INDEX_READ_ONLY;

		PropsValues.INDEX_READ_ONLY = true;

		int verifyFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VERIFY_FREQUENCY));

		if ((verifyFrequency == VerifyProcess.ALWAYS) ||
			((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
			(_upgraded)) {

			if (!_upgraded) {
				PropsUtil.set(PropsKeys.INDEX_ON_STARTUP, "true");
			}

			String[] verifyProcesses = PropsUtil.getArray(
				PropsKeys.VERIFY_PROCESSES);

			for (int i = 0; i < verifyProcesses.length; i++) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Initializing verification " + verifyProcesses[i]);
				}

				try {
					VerifyProcess verifyProcess = (VerifyProcess)Class.forName(
						verifyProcesses[i]).newInstance();

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Running verification " + verifyProcesses[i]);
					}

					verifyProcess.verify();

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Finished verification " + verifyProcesses[i]);
					}

					_verified = true;
				}
				catch (ClassNotFoundException cnfe) {
					_log.error(verifyProcesses[i] + " cannot be found");
				}
				catch (IllegalAccessException iae) {
					_log.error(verifyProcesses[i] + " cannot be accessed");
				}
				catch (InstantiationException ie) {
					_log.error(verifyProcesses[i] + " cannot be initiated");
				}
			}
		}

		PropsValues.INDEX_READ_ONLY = tempIndexReadOnly;
	}

	public void deleteTempImages() {
		try {
			DBUtil dbUtil = DBUtil.getInstance();

			dbUtil.runSQL(_DELETE_TEMP_IMAGES_1);
			dbUtil.runSQL(_DELETE_TEMP_IMAGES_2);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public boolean isUpgraded() {
		return _upgraded;
	}

	public boolean isVerified() {
		return _verified;
	}

	private boolean _upgraded = false;
	private boolean _verified = false;

	private static final String _DELETE_TEMP_IMAGES_1 =
		"DELETE FROM Image WHERE imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage WHERE tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"DELETE FROM JournalArticleImage where tempImage = TRUE";

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}