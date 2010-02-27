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

package com.liferay.portal.events;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.scheduler.SchedulerEngineProxy;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="StartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
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
	}

	protected void doRun(String[] ids) throws Exception {

		// Print release information

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo());

		// Clear locks

		try {
			LockLocalServiceUtil.clear();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clear locks because Lock table does not exist");
			}
		}

		// Shutdown hook

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		// Security manager

		if ((System.getSecurityManager() == null) &&
			(PropsValues.PORTAL_SECURITY_MANAGER_ENABLE)) {

			System.setSecurityManager(new PortalSecurityManager());
		}

		// FreeMarker

		FreeMarkerEngineUtil.init();

		// Velocity

		VelocityEngineUtil.init();

		// Upgrade

		DBUpgrader.upgrade();

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

		DBUpgrader.verify();
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}