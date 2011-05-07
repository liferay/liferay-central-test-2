/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTranslator;
import com.liferay.portal.kernel.search.StringQueryImpl;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Field;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

/**
 * @author Brian Wing Shun Chan
 */
public class QueryTranslatorImpl implements QueryTranslator {

	public Object translate(Query query) throws ParseException {
		if (query instanceof BooleanQueryImpl) {
			return ((BooleanQueryImpl)query).getBooleanQuery();
		}
		else if (query instanceof LuceneQueryImpl) {
			return ((LuceneQueryImpl)query).getQuery();
		}
		else if (query instanceof StringQueryImpl) {
			QueryParser parser = new QueryParser(
				LuceneHelperUtil.getVersion(), StringPool.BLANK,
				LuceneHelperUtil.getAnalyzer());

			try {
				return parser.parse(query.toString());
			}
			catch (org.apache.lucene.queryParser.ParseException pe) {
				throw new ParseException(pe);
			}
		}
		else if (query instanceof TermQueryImpl) {
			return ((TermQueryImpl)query).getTermQuery();
		}
		else if (query instanceof TermRangeQueryImpl) {
			return ((TermRangeQueryImpl)query).getTermRangeQuery();
		}
		else {
			return null;
		}
	}

	public Object translateForSolr(Query query) throws ParseException {
		Object queryObject = query.getWrappedQuery();

		if (queryObject instanceof org.apache.lucene.search.Query) {
			_adjustQuery((org.apache.lucene.search.Query)queryObject);
		}

		return query;
	}

	protected void _adjustQuery(org.apache.lucene.search.Query query) {
		if (query instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query;

			for (BooleanClause clause : booleanQuery.getClauses()) {
				_adjustQuery(clause.getQuery());
			}
		}
		else if (query instanceof TermQuery) {
			TermQuery termQuery = (TermQuery)query;

			Term term = termQuery.getTerm();

			try {

				String termText = term.text();

				if (termText.matches("^\\s*[^\"].*\\s+.*[^\"]\\s*$(?m)")) {
					termText = StringPool.QUOTE.concat(termText).concat(
						StringPool.QUOTE);

					_termField.set(term, termText);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (query instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)query;

			Term term = wildcardQuery.getTerm();

			try {
				String termText = term.text();

				if (termText.matches("^\\s*\\*.*(?m)")) {
					termText = term.text().replaceFirst(
						"\\*", StringPool.BLANK);

					_termField.set(term, termText);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Field _termField = null;

	static {
		try {
			_termField = Term.class.getDeclaredField("text");
			_termField.setAccessible(true);
		}
		catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

}