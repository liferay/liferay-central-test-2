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

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * <a href="LuceneHelperWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LuceneHelperWrapper implements LuceneHelper {

	public LuceneHelperWrapper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		_luceneHelper.addExactTerm(booleanQuery, field, value);
	}

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		_luceneHelper.addRequiredTerm(booleanQuery, field, value, like);
	}

	public void addTerm(
			BooleanQuery booleanQuery, String field, String value, boolean like)
		throws ParseException {

		_luceneHelper.addTerm(booleanQuery, field, value, like);
	}

	public void checkLuceneDir(long companyId) {
		_luceneHelper.checkLuceneDir(companyId);
	}

	public void delete(long companyId) {
		_luceneHelper.delete(companyId);
	}

	public void deleteDocuments(long companyId, Term term) throws IOException {
		_luceneHelper.deleteDocuments(companyId, term);
	}

	public Analyzer getAnalyzer() {
		return _luceneHelper.getAnalyzer();
	}

	public FSDirectory getDirectory(String path) throws IOException {
		return _luceneHelper.getDirectory(path);
	}

	public Directory getLuceneDir(long companyId) {
		return _luceneHelper.getLuceneDir(companyId);
	}

	public String[] getQueryTerms(Query query) {
		return _luceneHelper.getQueryTerms(query);
	}

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		return _luceneHelper.getSearcher(companyId, readOnly);
	}

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, String preTag,
			String postTag)
		throws IOException {

		return _luceneHelper.getSnippet(
			query, field, s, maxNumFragments, fragmentLength, fragmentSuffix,
			preTag, postTag);
	}

	public void write(long companyId, Document document) throws IOException {
		_luceneHelper.write(companyId, document);
	}

	private LuceneHelper _luceneHelper;

}