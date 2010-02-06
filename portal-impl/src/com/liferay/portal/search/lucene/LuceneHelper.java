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

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

/**
 * <a href="LuceneHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public interface LuceneHelper {

	public void addDocument(long companyId, Document document)
		throws IOException;

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public void addTerm(
			BooleanQuery booleanQuery, String field, String value, boolean like)
		throws ParseException;

	public void delete(long companyId);

	public void deleteDocuments(long companyId, Term term) throws IOException;

	public Analyzer getAnalyzer();

	public String[] getQueryTerms(Query query);

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException;

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, String preTag,
			String postTag)
		throws IOException;

	public void shutdown();

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException;

}