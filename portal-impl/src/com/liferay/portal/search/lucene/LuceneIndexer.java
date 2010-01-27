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

package com.liferay.portal.search.lucene;

import com.liferay.portal.dao.shard.ShardUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.PortletLuceneComparator;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="LuceneIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LuceneIndexer implements Runnable {

	public LuceneIndexer(long companyId) {
		_companyId = companyId;
	}

	public void halt() {
	}

	public boolean isFinished() {
		return _finished;
	}

	public void run() {
		reindex(PropsValues.INDEX_ON_STARTUP_DELAY);
	}

	public void reindex() {
		reindex(0);
	}

	public void reindex(int delay) {
		ShardUtil.pushCompanyService(_companyId);

		try {
			doReIndex(delay);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	protected void doReIndex(int delay) {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing Lucene started");
		}

		if (delay < 0) {
			delay = 0;
		}

		try {
			if (delay > 0) {
				Thread.sleep(Time.SECOND * delay);
			}
		}
		catch (InterruptedException ie) {
		}

		StopWatch stopWatch1 = null;

		if (_log.isInfoEnabled()) {
			stopWatch1 = new StopWatch();

			stopWatch1.start();
		}

		try {
			LuceneHelperUtil.delete(_companyId);

			List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(
				_companyId);

			portlets = ListUtil.sort(portlets, new PortletLuceneComparator());

			for (Portlet portlet : portlets) {
				if (!portlet.isActive()) {
					continue;
				}

				Indexer indexer = portlet.getIndexerInstance();

				if (indexer == null) {
					continue;
				}

				String indexerClass = portlet.getIndexerClass();

				StopWatch stopWatch2 = null;

				if (_log.isInfoEnabled()) {
					stopWatch2 = new StopWatch();

					stopWatch2.start();
				}

				if (_log.isInfoEnabled()) {
					_log.info("Reindexing with " + indexerClass + " started");
				}

				indexer.reindex(new String[] {String.valueOf(_companyId)});

				if (_log.isInfoEnabled()) {
					_log.info(
						"Reindexing with " + indexerClass + " completed in " +
							(stopWatch2.getTime() / Time.SECOND) + " seconds");
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Reindexing Lucene completed in " +
						(stopWatch1.getTime() / Time.SECOND) + " seconds");
			}
		}
		catch (Exception e) {
			_log.error("Error encountered while reindexing", e);

			if (_log.isInfoEnabled()) {
				_log.info("Reindexing Lucene failed");
			}
		}

		_finished = true;
	}

	private static Log _log = LogFactoryUtil.getLog(LuceneIndexer.class);

	private long _companyId;
	private boolean _finished;

}