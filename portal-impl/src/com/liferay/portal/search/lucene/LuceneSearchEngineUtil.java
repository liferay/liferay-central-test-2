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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.search.lucene.messaging.LuceneMessageListener;

/**
 * <a href="LuceneSearchEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneSearchEngineUtil {

	public static void addDocument(long companyId, Document doc)
		throws SearchException {

		_engine.getWriter().addDocument(companyId, doc);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		_engine.getWriter().deleteDocument(companyId, uid);
	}

	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		_engine.getWriter().deletePortletDocuments(companyId, portletId);
	}

	public static SearchEngine getSearchEngine() {
		return _engine;
	}

	public static void init() {
		if (_engine != null) {
			return;
		}

		_engine = new LuceneSearchEngineImpl();

		Destination destination = new ParallelDestination(
			DestinationNames.SEARCH);

		MessageBusUtil.addDestination(destination);

		destination.register(new LuceneMessageListener());
	}

	public static boolean isIndexReadOnly() {
		return _engine.isIndexReadOnly();
	}

	public static Hits search(long companyId, String query, int start, int end)
		throws SearchException {

		return _engine.getSearcher().search(companyId, query, start, end);
	}

	public static Hits search(
			long companyId, String query, Sort sort, int start, int end)
		throws SearchException {

		return _engine.getSearcher().search(companyId, query, sort, start, end);
	}

	public static void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		_engine.getWriter().updateDocument(companyId, uid, doc);
	}

	private static SearchEngine _engine;

}