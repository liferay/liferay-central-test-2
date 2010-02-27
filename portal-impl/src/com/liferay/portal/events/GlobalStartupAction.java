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

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.util.BrowserLauncher;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="GlobalStartupAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GlobalStartupAction extends SimpleAction {

	public static List<AutoDeployListener> getAutoDeployListeners() {
		List<AutoDeployListener> list = new ArrayList<AutoDeployListener>();

		String[] autoDeployListeners =
			PropsUtil.getArray(PropsKeys.AUTO_DEPLOY_LISTENERS);

		for (int i = 0; i < autoDeployListeners.length; i++) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Instantiating " + autoDeployListeners[i]);
				}

				AutoDeployListener autoDeployListener =
					(AutoDeployListener)Class.forName(
						autoDeployListeners[i]).newInstance();

				list.add(autoDeployListener);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return list;
	}

	public static List<HotDeployListener> getHotDeployListeners() {
		List<HotDeployListener> list = new ArrayList<HotDeployListener>();

		String[] hotDeployListeners =
			PropsUtil.getArray(PropsKeys.HOT_DEPLOY_LISTENERS);

		for (int i = 0; i < hotDeployListeners.length; i++) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Instantiating " + hotDeployListeners[i]);
				}

				HotDeployListener hotDeployListener =
					(HotDeployListener)Class.forName(
						hotDeployListeners[i]).newInstance();

				list.add(hotDeployListener);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return list;
	}

	public void run(String[] ids) {

		// Hot deploy

		if (_log.isDebugEnabled()) {
			_log.debug("Registering hot deploy listeners");
		}

		for (HotDeployListener hotDeployListener : getHotDeployListeners()) {
			HotDeployUtil.registerListener(hotDeployListener);
		}

		// Auto deploy

		try {
			if (PrefsPropsUtil.getBoolean(
					PropsKeys.AUTO_DEPLOY_ENABLED,
					PropsValues.AUTO_DEPLOY_ENABLED)) {

				if (_log.isInfoEnabled()) {
					_log.info("Registering auto deploy directories");
				}

				File deployDir = new File(
					PrefsPropsUtil.getString(
						PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
						PropsValues.AUTO_DEPLOY_DEPLOY_DIR));
				File destDir = new File(DeployUtil.getAutoDeployDestDir());
				long interval = PrefsPropsUtil.getLong(
					PropsKeys.AUTO_DEPLOY_INTERVAL,
					PropsValues.AUTO_DEPLOY_INTERVAL);
				int blacklistThreshold = PrefsPropsUtil.getInteger(
					PropsKeys.AUTO_DEPLOY_BLACKLIST_THRESHOLD,
					PropsValues.AUTO_DEPLOY_BLACKLIST_THRESHOLD);

				List<AutoDeployListener> autoDeployListeners =
					getAutoDeployListeners();

				AutoDeployDir autoDeployDir = new AutoDeployDir(
					AutoDeployDir.DEFAULT_NAME, deployDir, destDir, interval,
					blacklistThreshold, autoDeployListeners);

				AutoDeployUtil.registerDir(autoDeployDir);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info("Not registering auto deploy directories");
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		// JCR

		try {
			JCRFactoryUtil.prepare();

			if (GetterUtil.getBoolean(PropsUtil.get(
					PropsKeys.JCR_INITIALIZE_ON_STARTUP))) {

				JCRFactoryUtil.initialize();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		// JNDI

		try {
			InfrastructureUtil.getDataSource();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			if (!ServerDetector.isJOnAS()) {
				InfrastructureUtil.getMailSession();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		// POP server

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			POPServerUtil.start();
		}

		// Launch browser

		Thread browserLauncherThread = new Thread(new BrowserLauncher());

		browserLauncherThread.start();
	}

	private static Log _log = LogFactoryUtil.getLog(GlobalStartupAction.class);

}