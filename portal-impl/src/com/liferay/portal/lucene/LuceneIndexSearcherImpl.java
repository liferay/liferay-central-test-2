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

package com.liferay.portal.lucene;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.util.search.QueryImpl;

import java.io.IOException;

/**
 * <a href="LuceneIndexSearcherImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneIndexSearcherImpl implements IndexSearcher {

	public Hits close(long companyId, String keywords, Exception e)
		throws SearchException {

		Hits hits = new LuceneHitsImpl();

		org.apache.lucene.search.IndexSearcher searcher = null;

		try {
			searcher = LuceneUtil.getSearcher(companyId);
			hits = LuceneUtil.closeSearcher(searcher, keywords, e);
		}
		catch (Exception e1) {
			throw new SearchException(e1);
		}

		return hits;
	}

	public Hits search(long companyId, Query query)
		throws SearchException {

		LuceneHitsImpl hits = new LuceneHitsImpl();

		try {
			org.apache.lucene.search.IndexSearcher searcher =
				LuceneUtil.getSearcher(companyId);

			hits.recordHits(
				searcher.search(((QueryImpl)query).getQuery()), searcher);
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}

		return hits;
	}

}