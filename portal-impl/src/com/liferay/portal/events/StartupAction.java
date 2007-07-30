/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.lock.service.LockServiceUtil;
import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.cache.MultiVMPool;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.model.Release;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.spring.hibernate.CacheRegistry;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.util.GetterUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="StartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class StartupAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {

			// Print release information

			System.out.println("Starting " + ReleaseInfo.getReleaseInfo());

			// Bean locator

			BeanLocatorUtil.setBeanLocator(new BeanLocatorImpl());

			// Clear locks

			try {
				LockServiceUtil.clear();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// Add shutdown hook

			Runtime.getRuntime().addShutdownHook(
				new Thread(new ShutdownHook()));

			// Disable database caching before upgrade

			CacheRegistry.setActive(false);

			// Upgrade

			int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

			if (buildNumber < ReleaseInfo.RELEASE_4_2_1_BUILD_NUMBER) {
				_log.error("You must first upgrade to Liferay Portal 4.2.1");

				System.exit(0);
			}

			boolean ranUpgradeProcess = false;

			String[] upgradeProcesses =
				PropsUtil.getArray(PropsUtil.UPGRADE_PROCESSES);

			for (int i = 0; i < upgradeProcesses.length; i++) {
				if (_log.isDebugEnabled()) {
					_log.debug("Initializing upgrade " + upgradeProcesses[i]);
				}

				UpgradeProcess upgradeProcess =
					(UpgradeProcess)InstancePool.get(upgradeProcesses[i]);

				if (upgradeProcess != null) {
					if ((upgradeProcess.getThreshold() == 0) ||
						(upgradeProcess.getThreshold() > buildNumber)) {

						if (_log.isInfoEnabled()) {
							_log.info(
								"Running upgrade " + upgradeProcesses[i]);
						}

						upgradeProcess.upgrade();

						if (_log.isInfoEnabled()) {
							_log.info(
								"Finished upgrade " + upgradeProcesses[i]);
						}

						ranUpgradeProcess = true;
					}
					else {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Upgrade threshold " +
									upgradeProcess.getThreshold() +
										" will not trigger upgrade");

							_log.debug(
								"Skipping upgrade " + upgradeProcesses[i]);
						}
					}
				}
				else {
					_log.error(upgradeProcesses[i] + " cannot be found");
				}
			}

			// Class names

			ClassNameLocalServiceUtil.checkClassNames();

			// Delete temporary images

			deleteTemporaryImages();

			// Update indexes

			if (ranUpgradeProcess) {
				DBUtil.getInstance().runSQLTemplate("indexes.sql", false);
			}

			// Enable database caching after upgrade

			CacheRegistry.setActive(true);

			MultiVMPool.clear();

			// Verify

			Release release = ReleaseLocalServiceUtil.getRelease();

			int verifyFrequency = GetterUtil.getInteger(
				PropsUtil.get(PropsUtil.VERIFY_FREQUENCY));
			boolean verified = release.isVerified();

			if ((verifyFrequency == VerifyProcess.ALWAYS) ||
				((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
				(ranUpgradeProcess)) {

				String[] verifyProcesses =
					PropsUtil.getArray(PropsUtil.VERIFY_PROCESSES);

				for (int i = 0; i < verifyProcesses.length; i++) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Initializing verification " + verifyProcesses[i]);
					}

					try {
						VerifyProcess verifyProcess =
							(VerifyProcess)Class.forName(
								verifyProcesses[i]).newInstance();

						if (_log.isInfoEnabled()) {
							_log.info(
								"Running verification " + verifyProcesses[i]);
						}

						verifyProcess.verify();

						if (_log.isInfoEnabled()) {
							_log.info(
								"Finished verification " + verifyProcesses[i]);
						}

						verified = true;
					}
					catch (ClassNotFoundException cnfe) {
						_log.error(verifyProcesses[i] + " cannot be found");
					}
					catch (InstantiationException ie) {
						_log.error(verifyProcesses[i] + " cannot be initiated");
					}
				}
			}

			// Update release

			ReleaseLocalServiceUtil.updateRelease(verified);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void deleteTemporaryImages() throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		dbUtil.runSQL(_DELETE_TEMP_IMAGES_1);
		dbUtil.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"DELETE FROM Image WHERE imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage WHERE tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"DELETE FROM JournalArticleImage where tempImage = TRUE";

	private static Log _log = LogFactory.getLog(StartupAction.class);

}