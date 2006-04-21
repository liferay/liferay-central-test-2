/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.lucene;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.spring.PortletServiceUtil;
import com.liferay.portal.util.comparator.PortletLuceneComparator;
import com.liferay.util.FileUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.ServerDetector;
import com.liferay.util.Time;
import com.liferay.util.lucene.Indexer;

import java.io.IOException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;

/**
 * <a href="LuceneIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LuceneIndexer implements Runnable {

	public LuceneIndexer(String companyId) {
		_companyId = companyId;
	}

	public void halt() {
		_halt = true;
	}

	public boolean isFinished() {
		return _finished;
	}

	public void run() {
		reIndex();
	}

	public void reIndex() {
		_log.info("Re-indexing Lucene started");

		if (ServerDetector.isOrion()) {

			// Wait 30 seconds because Orion 2.0.7 initiates LuceneServlet
			// before it initiates MainServlet.

			try {

				Thread.sleep(Time.SECOND * 30);
			}
			catch (InterruptedException ie) {
			}
		}

		long start = System.currentTimeMillis();

		String luceneDir = LuceneUtil.getLuceneDir(_companyId);

		FileUtil.deltree(luceneDir);

		try {
			IndexWriter writer = LuceneUtil.getWriter(_companyId, true);

			LuceneUtil.write(writer);
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		String[] indexIds = new String[] {_companyId};

		try {
			List portlets = PortletServiceUtil.getPortlets(_companyId);

			Collections.sort(portlets, new PortletLuceneComparator());

			Iterator itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				String className = portlet.getIndexerClass();

				if (portlet.isActive() && className != null) {
					_log.debug("Re-indexing with " + className + " started");

					Indexer indexer = (Indexer)InstancePool.get(className);

					indexer.reIndex(indexIds);

					_log.debug("Re-indexing with " + className + " completed");
				}
			}

			long end = System.currentTimeMillis();

			_log.info(
				"Re-indexing Lucene completed in " +
					((end - start) / Time.SECOND) + " seconds");
		}
		catch (Exception e) {
			_log.error(e);

			_log.info("Re-indexing Lucene failed");
		}

		_finished = true;
	}

	private static Log _log = LogFactory.getLog(LuceneIndexer.class);

	private String _companyId;
	private boolean _halt;
	private boolean _finished;

}