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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.SortField;

/**
 * <a href="LuceneIndexSearcherImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class LuceneIndexSearcherImpl implements IndexSearcher {

	public Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query " + query);
		}

		Hits hits = null;

		org.apache.lucene.search.IndexSearcher searcher = null;
		org.apache.lucene.search.Sort luceneSort = null;

		try {
			searcher = LuceneHelperUtil.getSearcher(companyId, true);

			if (sorts != null) {
				SortField[] sortFields = new SortField[sorts.length];

				for (int i = 0; i < sorts.length; i++) {
					Sort sort = sorts[i];

					sortFields[i] = new SortField(
						sort.getFieldName(), sort.getType(), sort.isReverse());
				}

				luceneSort = new org.apache.lucene.search.Sort(sortFields);
			}

			long startTime = System.currentTimeMillis();

			org.apache.lucene.search.Hits luceneHits = searcher.search(
				QueryTranslator.translate(query), luceneSort);

			long endTime = System.currentTimeMillis();

			float searchTime = (float)(endTime - startTime) / Time.SECOND;

			hits = subset(luceneHits, query, startTime, searchTime, start, end);
		}
		catch (BooleanQuery.TooManyClauses tmc) {
			int maxClauseCount = BooleanQuery.getMaxClauseCount();

			BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);

			try {
				long startTime = System.currentTimeMillis();

				org.apache.lucene.search.Hits luceneHits = searcher.search(
					QueryTranslator.translate(query), luceneSort);

				long endTime = System.currentTimeMillis();

				float searchTime = (float)(endTime - startTime) / Time.SECOND;

				hits = subset(
					luceneHits, query, startTime, searchTime, start, end);
			}
			catch (Exception e) {
				throw new SearchException(e);
			}
			finally {
				BooleanQuery.setMaxClauseCount(maxClauseCount);
			}
		}
		catch (ParseException pe) {
			_log.error("Query: " + query, pe);

			return new HitsImpl();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
		finally {
			try {
				if (searcher != null) {
					searcher.close();
				}
			}
			catch (IOException ioe) {
				throw new SearchException(ioe);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search found " + hits.getLength() + " results in " +
					hits.getSearchTime() + "ms");
		}

		return hits;
	}

	protected DocumentImpl getDocument(
		org.apache.lucene.document.Document oldDoc) {

		DocumentImpl newDoc = new DocumentImpl();

		List<org.apache.lucene.document.Field> oldFields = oldDoc.getFields();

		for (org.apache.lucene.document.Field oldField : oldFields) {
			String[] values = oldDoc.getValues(oldField.name());

			if ((values != null) && (values.length > 1)) {
				Field newField = new Field(
					oldField.name(), values, oldField.isTokenized());

				newDoc.add(newField);
			}
			else {
				Field newField = new Field(
					oldField.name(), oldField.stringValue(),
					oldField.isTokenized());

				newDoc.add(newField);
			}
		}

		return newDoc;
	}

	protected String[] getQueryTerms(Query query) {
		String[] queryTerms = new String[0];

		try {
			queryTerms = LuceneHelperUtil.getQueryTerms(
				QueryTranslator.translate(query));
		}
		catch (ParseException pe) {
			_log.error("Query: " + query, pe);
		}

		return queryTerms;
	}

	protected String getSnippet(
			org.apache.lucene.document.Document doc, Query query, String field)
		throws IOException {

		String[] values = doc.getValues(field);

		String snippet = null;

		if (Validator.isNull(values)) {
			return snippet;
		}

		String s = StringUtil.merge(values);

		try {
			snippet = LuceneHelperUtil.getSnippet(
				QueryTranslator.translate(query), field, s);
		}
		catch (ParseException pe) {
			_log.error("Query: " + query, pe);
		}

		return snippet;
	}

	protected Hits subset(
			org.apache.lucene.search.Hits luceneHits, Query query,
			long startTime, float searchTime, int start, int end)
		throws IOException {

		int length = luceneHits.length();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			start = 0;
			end = length;
		}

		String[] queryTerms = getQueryTerms(query);

		Hits subset = new HitsImpl();

		if ((start > - 1) && (start <= end)) {
			if (end > length) {
				end = length;
			}

			int subsetTotal = end - start;

			Document[] subsetDocs = new DocumentImpl[subsetTotal];
			String[] subsetSnippets = new String[subsetTotal];
			float[] subsetScores = new float[subsetTotal];

			int j = 0;

			for (int i = start; i < end; i++, j++) {
				org.apache.lucene.document.Document doc = luceneHits.doc(i);

				subsetDocs[j] = getDocument(doc);
				subsetSnippets[j] = getSnippet(doc, query, Field.CONTENT);
				subsetScores[j] = luceneHits.score(i);
			}

			subset.setStart(startTime);
			subset.setSearchTime(searchTime);
			subset.setQueryTerms(queryTerms);
			subset.setDocs(subsetDocs);
			subset.setLength(length);
			subset.setSnippets(subsetSnippets);
			subset.setScores(subsetScores);
		}

		return subset;
	}

	private static Log _log =
		LogFactoryUtil.getLog(LuceneIndexSearcherImpl.class);

}