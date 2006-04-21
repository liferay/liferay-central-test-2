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

import com.liferay.portal.util.PropsUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * <a href="LuceneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Harry Mark
 *
 */
public class LuceneUtil {

	static Class analyzerClass = WhitespaceAnalyzer.class;

	static {
		String analyzerName = PropsUtil.get(PropsUtil.LUCENE_ANALYZER);

		if (Validator.isNotNull(analyzerName)) {
			try {
				analyzerClass = Class.forName(analyzerName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, String text)
		throws ParseException {

		if (Validator.isNotNull(text)) {
			if (text.indexOf(StringPool.SPACE) == -1) {
				text = KeywordsUtil.toWildcard(text);
			}

			QueryParser queryParser = new QueryParser(field, getAnalyzer());

			Query query = queryParser.parse(text);

			booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		}
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String text) {

		text = KeywordsUtil.escape(text);

		Term term = new Term(field, text);
		TermQuery termQuery = new TermQuery(term);

		booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
	}

	public static Analyzer getAnalyzer() {
		try {
			return (Analyzer)analyzerClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getLuceneDir(String companyId) {
		return PropsUtil.get(PropsUtil.LUCENE_DIR) + companyId +
			StringPool.SLASH;
	}

	public static IndexReader getReader(String companyId) throws IOException {
		return IndexReader.open(getLuceneDir(companyId));
	}

	public static IndexSearcher getSearcher(String companyId)
		throws IOException {

		return new IndexSearcher(getLuceneDir(companyId));
	}

	public static IndexWriter getWriter(String companyId) throws IOException {
		return getWriter(companyId, false);
	}

	public static IndexWriter getWriter(String companyId, boolean create)
		throws IOException {

		return new IndexWriter(
			getLuceneDir(companyId), getAnalyzer(), create);
	}

	public static void write(IndexWriter writer) throws IOException {
		writer.optimize();
		writer.close();
	}

}