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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.lucene.LuceneIndexer;
import com.liferay.portal.search.lucene.messaging.CleanUpMessageListener;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LuceneServlet extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		long[] companyIds = PortalInstances.getCompanyIds();

		for (int i = 0; i < companyIds.length; i++) {
			long companyId = companyIds[i];

			if (GetterUtil.getBoolean(
					PropsUtil.get(PropsKeys.INDEX_ON_STARTUP))) {

				if (_log.isInfoEnabled()) {
					_log.info("Indexing Lucene on startup");
				}

				LuceneIndexer indexer = new LuceneIndexer(companyId);
				Thread indexerThread = null;

				if (GetterUtil.getBoolean(
						PropsUtil.get(PropsKeys.INDEX_WITH_THREAD))) {

					indexerThread = new Thread(
						indexer, THREAD_NAME + "." + companyId);

					indexerThread.setPriority(THREAD_PRIORITY);

					indexerThread.start();
				}
				else {
					indexer.reindex();
				}

				_indexers.add(
					new ObjectValuePair<LuceneIndexer, Thread>(
						indexer, indexerThread));
			}

			if (PropsValues.LUCENE_STORE_JDBC_AUTO_CLEAN_UP_ENABLED) {
				SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

				schedulerEntry.setEventListenerClass(
					CleanUpMessageListener.class.getName());
				schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
				schedulerEntry.setTriggerType(TriggerType.SIMPLE);
				schedulerEntry.setTriggerValue(
					PropsValues.LUCENE_STORE_JDBC_AUTO_CLEAN_UP_INTERVAL);

				try {
					SchedulerEngineUtil.schedule(
						schedulerEntry, PortalClassLoaderUtil.getClassLoader());
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	public void destroy() {

		// Wait for indexer to be gracefully interrupted

		for (int i = 0; i < _indexers.size(); i++) {
			ObjectValuePair<LuceneIndexer, Thread> ovp = _indexers.get(i);

			LuceneIndexer indexer = ovp.getKey();
			Thread indexerThread = ovp.getValue();

			if ((indexer != null) && (!indexer.isFinished()) &&
				(indexerThread != null)) {

				if (_log.isWarnEnabled()) {
					_log.warn("Waiting for Lucene indexer to shutdown");
				}

				indexer.halt();

				try {
					indexerThread.join(THREAD_TIMEOUT);
				}
				catch (InterruptedException e) {
					_log.error("Lucene indexer shutdown interrupted", e);
				}
			}
		}

		// Parent

		super.destroy();
	}

	private static final String THREAD_NAME = LuceneIndexer.class.getName();

	private static final int THREAD_PRIORITY = Thread.MIN_PRIORITY;

	private static final int THREAD_TIMEOUT = 60000;

	private static Log _log = LogFactoryUtil.getLog(LuceneServlet.class);

	private List<ObjectValuePair<LuceneIndexer, Thread>> _indexers =
		new ArrayList<ObjectValuePair<LuceneIndexer, Thread>>();

}