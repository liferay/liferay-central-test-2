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

package com.liferay.portal.events;

import com.liferay.portal.im.AIMConnector;
import com.liferay.portal.im.ICQConnector;
import com.liferay.portal.im.MSNConnector;
import com.liferay.portal.im.YMConnector;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.concurrent.ThreadLocalRegistry;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.job.JobSchedulerUtil;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.sql.Connection;
import java.sql.Statement;

/**
 * <a href="GlobalShutdownAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GlobalShutdownAction extends SimpleAction {

	public void run(String[] ids) {

		// Hot deploy

		HotDeployUtil.unregisterListeners();

		// Instant messenger AIM

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Shutting down AIM");
			}

			AIMConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Instant messenger ICQ

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Shutting down ICQ");
			}

			ICQConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Instant messenger MSN

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Shutting down MSN");
			}

			MSNConnector.disconnect();
		}
		catch (Exception e) {
		}

		// Instant messenger YM

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Shutting down YM");
			}

			YMConnector.disconnect();
		}
		catch (Exception e) {
		}

		// JCR

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Shutting down JCR");
			}

			JCRFactoryUtil.shutdown();
		}
		catch (Exception e) {
		}

		// Lucene

		LuceneHelperUtil.shutdown();

		// OpenOffice

		DocumentConversionUtil.disconnect();

		// POP server

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			POPServerUtil.stop();
		}

		// Scheduler

		try {
			JobSchedulerUtil.shutdown();
		}
		catch (Exception e) {
		}

		try {
			SchedulerEngineUtil.shutdown();
		}
		catch (Exception e) {
		}

		// Thread local registry

		ThreadLocalRegistry.resetThreadLocals();

		// Hypersonic

		DB db = DBFactoryUtil.getDB();

		if (db.getType().equals(DB.TYPE_HYPERSONIC)) {
			try {
				Connection connection = DataAccess.getConnection();

				Statement statement = connection.createStatement();

				statement.executeUpdate("SHUTDOWN");

				statement.close();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		// Reset log to default JDK 1.4 logger. This will allow WARs dependent
		// on the portal to still log events after the portal WAR has been
		// destroyed.

		try {
			LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());
		}
		catch (Exception e) {
		}

		// Wait 1 second so Quartz threads can cleanly shutdown

		try {
			Thread.sleep(1000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Programmatically exit

		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.SHUTDOWN_PROGRAMMATICALLY_EXIT))) {

			Thread currentThread = Thread.currentThread();

			ThreadGroup threadGroup = currentThread.getThreadGroup();

			for (int i = 0; i < 10; i++) {
				if (threadGroup.getParent() == null) {
					break;
				}
				else {
					threadGroup = threadGroup.getParent();
				}
			}

			Thread[] threads = new Thread[threadGroup.activeCount() * 2];

			threadGroup.enumerate(threads);

			for (Thread thread : threads) {
				if ((thread == null) || (thread == currentThread)) {
					continue;
				}

				try {
					thread.interrupt();
				}
				catch (Exception e) {
				}
			}

			threadGroup.destroy();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GlobalShutdownAction.class);

}