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

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.lucene.messaging.CleanUpMessageListener;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.QueryTermExtractor;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.WeightedTerm;
import org.apache.lucene.util.Version;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class LuceneHelperImpl implements LuceneHelper {

	public void addDocument(long companyId, Document document)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.addDocument(document);
	}

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		//text = KeywordsUtil.escape(value);

		Query query = new TermQuery(new Term(field, value));

		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
	}

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		NumericRangeQuery<?> numericRangeQuery = NumericRangeQuery.newLongRange(
			field, GetterUtil.getLong(startValue), GetterUtil.getLong(endValue),
			true, true);

		booleanQuery.add(numericRangeQuery, BooleanClause.Occur.SHOULD);
	}

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, startValue, endValue, true, true);

		booleanQuery.add(termRangeQuery, BooleanClause.Occur.SHOULD);
	}

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		if (like) {
			value = StringUtil.replace(
				value, CharPool.PERCENT, CharPool.STAR);

			value = value.toLowerCase();

			WildcardQuery wildcardQuery = new WildcardQuery(
				new Term(field, value));

			booleanQuery.add(wildcardQuery, BooleanClause.Occur.MUST);
		}
		else {
			//text = KeywordsUtil.escape(value);

			Term term = new Term(field, value);
			TermQuery termQuery = new TermQuery(term);

			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
		}
	}

	public void addTerm(
			BooleanQuery booleanQuery, String field, String value, boolean like)
		throws ParseException {

		if (Validator.isNull(value)) {
			return;
		}

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);

			value = value.toLowerCase();

			Term term = new Term(
				field, StringPool.STAR.concat(value).concat(StringPool.STAR));

			WildcardQuery wildcardQuery = new WildcardQuery(term);

			booleanQuery.add(wildcardQuery, BooleanClause.Occur.SHOULD);
		}
		else {
			QueryParser queryParser = new QueryParser(
				_version, field, getAnalyzer());

			try {
				Query query = queryParser.parse(value);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
			catch (ParseException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"ParseException thrown, reverting to literal search",
						pe);
				}

				value = KeywordsUtil.escape(value);

				Query query = queryParser.parse(value);

				booleanQuery.add(query, BooleanClause.Occur.SHOULD);
			}
		}
	}

	public int countScoredFieldNames(Query query, String[] filedNames) {
		int count = 0;

		for (String fieldName : filedNames) {
			WeightedTerm[] weightedTerms = QueryTermExtractor.getTerms(
				query, false, fieldName);

			if ((weightedTerms.length > 0) &&
				!ArrayUtil.contains(Field.UNSCORED_FIELD_NAMES, fieldName)) {

				count++;
			}
		}

		return count;
	}

	public void delete(long companyId) {
		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.delete();
	}

	public void deleteDocuments(long companyId, Term term) throws IOException {
		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.deleteDocuments(term);
	}

	public Analyzer getAnalyzer() {
		try {
			if (_analyzerClass.equals(StandardAnalyzer.class)) {
				Constructor<?> constructor = _analyzerClass.getConstructor(
					Version.class);

				return (Analyzer)constructor.newInstance(Version.LUCENE_30);
			}
			else {
				return (Analyzer)_analyzerClass.newInstance();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String[] getQueryTerms(Query query) {
		String[] fieldNames = new String[] {
			Field.CONTENT, Field.DESCRIPTION, Field.PROPERTIES, Field.TITLE,
			Field.USER_NAME
		};

		WeightedTerm[] weightedTerms = null;

		for (String fieldName : fieldNames) {
			weightedTerms = QueryTermExtractor.getTerms(
				query, false, fieldName);

			if (weightedTerms.length > 0) {
				break;
			}
		}

		Set<String> queryTerms = new HashSet<String>();

		for (WeightedTerm weightedTerm : weightedTerms) {
			queryTerms.add(weightedTerm.getTerm());
		}

		return queryTerms.toArray(new String[queryTerms.size()]);
	}

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		return new IndexSearcher(indexAccessor.getLuceneDir(), readOnly);
	}

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, String preTag,
			String postTag)
		throws IOException {

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
			preTag, postTag);

		QueryScorer queryScorer = new QueryScorer(query, field);

		Highlighter highlighter = new Highlighter(
			simpleHTMLFormatter, queryScorer);

		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));

		TokenStream tokenStream = getAnalyzer().tokenStream(
			field, new UnsyncStringReader(s));

		try {
			String snippet = highlighter.getBestFragments(
				tokenStream, s, maxNumFragments, fragmentSuffix);

			if (Validator.isNotNull(snippet) &&
				!StringUtil.endsWith(snippet, fragmentSuffix)) {

				snippet = snippet.concat(fragmentSuffix);
			}

			return snippet;
		}
		catch (InvalidTokenOffsetsException itoe) {
			throw new IOException(itoe.getMessage());
		}
	}

	public Version getVersion() {
		return _version;
	}

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.updateDocument(term, document);
	}

	public void shutdown() {
		if (_luceneIndexThreadPoolExecutor != null) {
			_luceneIndexThreadPoolExecutor.shutdownNow();

			try {
				_luceneIndexThreadPoolExecutor.awaitTermination(
					60, java.util.concurrent.TimeUnit.SECONDS);
			}
			catch (InterruptedException ie) {
				_log.error("Lucene indexer shutdown interrupted", ie);
			}
		}

		for (IndexAccessor indexAccessor : _indexAccessorMap.values()) {
			indexAccessor.close();
		}
	}

	public void startup() {
		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			_startup(companyId);
		}
	}

	private LuceneHelperImpl() {
		String analyzerName = PropsUtil.get(PropsKeys.LUCENE_ANALYZER);

		if (Validator.isNotNull(analyzerName)) {
			try {
				_analyzerClass = Class.forName(analyzerName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (PropsValues.INDEX_ON_STARTUP && PropsValues.INDEX_WITH_THREAD) {
			_luceneIndexThreadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					LuceneHelperImpl.class.getName());
		}
	}

	private IndexAccessor _getIndexAccessor(long companyId) {
		IndexAccessor indexAccessor = _indexAccessorMap.get(companyId);

		if (indexAccessor == null) {
			synchronized (this) {
				indexAccessor = _indexAccessorMap.get(companyId);

				if (indexAccessor == null) {
					indexAccessor = new IndexAccessorImpl(companyId);

					_indexAccessorMap.put(companyId, indexAccessor);
				}
			}
		}

		return indexAccessor;
	}

	private void _startup(long companyId) {
		if (PropsValues.INDEX_ON_STARTUP) {
			if (_log.isInfoEnabled()) {
				_log.info("Indexing Lucene on startup");
			}

			LuceneIndexer luceneIndexer = new LuceneIndexer(companyId);

			if (PropsValues.INDEX_WITH_THREAD) {
				_luceneIndexThreadPoolExecutor.execute(luceneIndexer);
			}
			else {
				luceneIndexer.reindex();
			}
		}

		if (PropsValues.LUCENE_STORE_JDBC_AUTO_CLEAN_UP_ENABLED) {
			SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

			schedulerEntry.setEventListenerClass(
				CleanUpMessageListener.class.getName());
			schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
			schedulerEntry.setTriggerType(TriggerType.SIMPLE);
			schedulerEntry.setTriggerValue(
				PropsValues.LUCENE_STORE_JDBC_AUTO_CLEAN_UP_INTERVAL);

			try {
				SchedulerEngineUtil.schedule(
					schedulerEntry, StorageType.MEMORY,
					PortalClassLoaderUtil.getClassLoader(), 0);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LuceneHelperImpl.class);

	private Class<?> _analyzerClass = WhitespaceAnalyzer.class;
	private Map<Long, IndexAccessor> _indexAccessorMap =
		new ConcurrentHashMap<Long, IndexAccessor>();
	private ThreadPoolExecutor _luceneIndexThreadPoolExecutor;
	private Version _version = Version.LUCENE_30;

}