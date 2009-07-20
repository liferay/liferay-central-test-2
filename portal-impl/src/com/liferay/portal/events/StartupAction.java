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

import com.liferay.lock.service.LockServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Release;
import com.liferay.portal.scheduler.SchedulerEngineProxy;
import com.liferay.portal.search.lucene.LuceneUtil;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

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
			LuceneUtil.checkLuceneDir(CompanyConstants.SYSTEM);
		}
	}

	protected void doRun(String[] ids) throws PortalException, SystemException {

		// Print release information

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo());

		// Clear locks

		try {
			LockServiceUtil.clear();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Shutdown hook

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		// Security manager

		if ((System.getSecurityManager() == null) &&
			(PropsValues.PORTAL_SECURITY_MANAGER_ENABLE)) {

			System.setSecurityManager(new PortalSecurityManager());
		}

		// Velocity

		VelocityEngineUtil.init();

		// Disable database caching before upgrade

		CacheRegistry.setActive(false);

		// Upgrade

		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		if (buildNumber < ReleaseInfo.RELEASE_4_2_1_BUILD_NUMBER) {
			String msg = "You must first upgrade to Liferay Portal 4.2.1";

			_log.fatal(msg);

			throw new RuntimeException(msg);
		}

		StartupHelperUtil.upgradeProcess(buildNumber);

		// Class names

		ClassNameLocalServiceUtil.checkClassNames();

		// Resource actions

		ResourceActionsUtil.init();

		ResourceActionLocalServiceUtil.checkResourceActions();

		// Resource codes

		ResourceCodeLocalServiceUtil.checkResourceCodes();

		// Delete temporary images

		StartupHelperUtil.deleteTempImages();

		// Clear the caches only if the upgrade process was run

		if (StartupHelperUtil.isUpgraded()) {
			MultiVMPoolUtil.clear();
		}

		// Messaging

		MessageBus messageBus = (MessageBus)PortalBeanLocatorUtil.locate(
			MessageBus.class.getName());
		MessageSender messageSender =
			(MessageSender)PortalBeanLocatorUtil.locate(
				MessageSender.class.getName());
		SynchronousMessageSender synchronousMessageSender =
			(SynchronousMessageSender)PortalBeanLocatorUtil.locate(
				SynchronousMessageSender.class.getName());

		MessageBusUtil.init(
			messageBus, messageSender, synchronousMessageSender);

		// Scheduler

		SchedulerEngineUtil.init(new SchedulerEngineProxy());

		SchedulerEngineUtil.start();

		// Verify

		Release release = ReleaseLocalServiceUtil.getRelease();

		StartupHelperUtil.verifyProcess(release.isVerified());

		// Update indexes

		if (StartupHelperUtil.isUpgraded()) {
			StartupHelperUtil.updateIndexes();
		}

		// Update release

		boolean verified = StartupHelperUtil.isVerified();

		if (release.isVerified()) {
			verified = true;
		}

		ReleaseLocalServiceUtil.updateRelease(verified);

		// Enable database caching after verify

		CacheRegistry.setActive(true);
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}