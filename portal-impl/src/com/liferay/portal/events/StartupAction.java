/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageSender;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Release;
import com.liferay.portal.search.IndexSearcherImpl;
import com.liferay.portal.search.IndexWriterImpl;
import com.liferay.portal.search.lucene.LuceneSearchEngineUtil;
import com.liferay.portal.search.lucene.LuceneUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.velocity.LiferayResourceLoader;
import com.liferay.portal.verify.VerifyProcess;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

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
			doRun(ids);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		finally {

			// Lucene

			LuceneUtil.checkLuceneDir(CompanyConstants.SYSTEM);
		}
	}

	protected void deleteTemporaryImages() throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		dbUtil.runSQL(_DELETE_TEMP_IMAGES_1);
		dbUtil.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	protected void doRun(String[] ids) throws Exception {

		// Print release information

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo());

		// Clear locks

		try {
			LockServiceUtil.clear();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Add shutdown hook

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		// Velocity

		LiferayResourceLoader.setListeners(PropsUtil.getArray(
			PropsKeys.VELOCITY_ENGINE_RESOURCE_LISTENERS));

		ExtendedProperties props = new ExtendedProperties();

		props.setProperty(RuntimeConstants.RESOURCE_LOADER, "servlet");

		props.setProperty(
			"servlet." + RuntimeConstants.RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		props.setProperty(
			RuntimeConstants.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		props.setProperty(
			RuntimeConstants.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		props.setProperty(
			"velocimacro.library",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		props.setProperty(
			RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		props.setProperty(
			"runtime.log.logsystem.log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		Velocity.setExtendedProperties(props);

		try {
			Velocity.init();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Disable database caching before upgrade

		CacheRegistry.setActive(false);

		// Upgrade

		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		if (buildNumber < ReleaseInfo.RELEASE_4_2_1_BUILD_NUMBER) {
			String msg = "You must first upgrade to Liferay Portal 4.2.1";

			_log.fatal(msg);

			throw new RuntimeException(msg);
		}

		boolean ranUpgradeProcess = false;

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

				if (_log.isInfoEnabled()) {
					_log.info("Running upgrade " + upgradeProcesses[i]);
				}

				upgradeProcess.upgrade();

				if (_log.isInfoEnabled()) {
					_log.info("Finished upgrade " + upgradeProcesses[i]);
				}

				ranUpgradeProcess = true;
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

		// Clear the caches only if the upgrade process was run

		if (ranUpgradeProcess) {
			MultiVMPoolUtil.clear();
		}

		// Messaging

		MessageBus messageBus = (MessageBus)PortalBeanLocatorUtil.locate(
			MessageBus.class.getName());
		MessageSender messageSender =
			(MessageSender)PortalBeanLocatorUtil.locate(
				MessageSender.class.getName());

		MessageBusUtil.init(messageBus, messageSender);

		// Search

		LuceneSearchEngineUtil.init();

		SearchEngineUtil.init(new IndexSearcherImpl(), new IndexWriterImpl());

		// Verify

		Release release = ReleaseLocalServiceUtil.getRelease();

		int verifyFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VERIFY_FREQUENCY));
		boolean verified = release.isVerified();

		if ((verifyFrequency == VerifyProcess.ALWAYS) ||
			((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
			(ranUpgradeProcess)) {

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

					if (_log.isInfoEnabled()) {
						_log.info("Running verification " + verifyProcesses[i]);
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

	private static final String _DELETE_TEMP_IMAGES_1 =
		"DELETE FROM Image WHERE imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage WHERE tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"DELETE FROM JournalArticleImage where tempImage = TRUE";

	private static Log _log = LogFactory.getLog(StartupAction.class);

}