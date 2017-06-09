/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.solr.internal.query;

import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.solr.query.MatchQueryTranslator;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.TermQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@Component(immediate = true, service = MatchQueryTranslator.class)
public class MatchQueryTranslatorImpl implements MatchQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(MatchQuery matchQuery) {
		org.apache.lucene.search.Query query = translateMatchQuery(matchQuery);

		if (!matchQuery.isDefaultBoost()) {
			query.setBoost(matchQuery.getBoost());
		}

		return query;
	}

	protected org.apache.lucene.search.Query translateMatchQuery(
		MatchQuery matchQuery) {

		String field = matchQuery.getField();
		MatchQuery.Type matchQueryType = matchQuery.getType();
		String value = matchQuery.getValue();

		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			matchQueryType = MatchQuery.Type.PHRASE;

			value = StringUtil.unquote(value);

			if (value.endsWith(StringPool.STAR)) {
				matchQueryType = MatchQuery.Type.PHRASE_PREFIX;
			}
		}

		value = _trimStars(value);

		value = QueryParser.escape(value);

		value = _defuseUpperCaseLuceneBooleanOperators(value);

		if (matchQueryType == null) {
			matchQueryType = MatchQuery.Type.BOOLEAN;
		}

		if (matchQueryType == MatchQuery.Type.BOOLEAN) {
			return translateQueryTypeBoolean(field, value);
		}
		else if (matchQueryType == MatchQuery.Type.PHRASE) {
			return translateQueryTypePhrase(field, value, matchQuery.getSlop());
		}
		else if (matchQueryType == MatchQuery.Type.PHRASE_PREFIX) {
			return translateQueryTypePhrasePrefix(field, value);
		}

		throw new IllegalArgumentException(
			"Invalid match query type: " + matchQueryType);
	}

	protected org.apache.lucene.search.Query translateQueryTypeBoolean(
		String field, String value) {

		value = _encloseMultiword(
			value, StringPool.OPEN_PARENTHESIS, StringPool.CLOSE_PARENTHESIS);

		return new TermQuery(new Term(field, value));
	}

	protected org.apache.lucene.search.Query translateQueryTypePhrase(
		String field, String value, Integer slop) {

		PhraseQuery phraseQuery = new PhraseQuery();

		phraseQuery.add(new Term(field, value));

		if (slop != null) {
			phraseQuery.setSlop(slop);
		}

		return phraseQuery;
	}

	protected org.apache.lucene.search.Query translateQueryTypePhrasePrefix(
		String field, String value) {

		value = value.concat(StringPool.STAR);

		value = _encloseMultiword(
			value, StringPool.OPEN_PARENTHESIS + StringPool.PLUS,
			StringPool.CLOSE_PARENTHESIS);

		return new TermQuery(new Term(field, value));
	}

	private String _defuseUpperCaseLuceneBooleanOperators(String value) {
		return StringUtil.toLowerCase(value);
	}

	private String _encloseMultiword(String value, String open, String close) {
		if (value.indexOf(CharPool.SPACE) == -1) {
			return value;
		}

		return open + value + close;
	}

	private String _trimStars(String value) {
		if (value.equals(StringPool.STAR)) {
			return value;
		}

		if (value.startsWith(StringPool.STAR)) {
			value = value.substring(1);
		}

		if (value.endsWith(StringPool.STAR)) {
			value = value.substring(0, value.length() - 1);
		}

		return value;
	}

}