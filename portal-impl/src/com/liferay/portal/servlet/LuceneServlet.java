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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.job.JobSchedulerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.lucene.CleanUpJob;
import com.liferay.portal.search.lucene.LuceneIndexer;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * <a href="LuceneServlet.java.html"><b><i>View Source</i></b></a>
 *
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

			if (PropsValues.LUCENE_STORE_JDBC_AUTO_CLEAN_UP) {
				JobSchedulerUtil.schedule(new CleanUpJob());
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