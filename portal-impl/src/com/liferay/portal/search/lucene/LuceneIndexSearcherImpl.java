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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.QueryTranslatorUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.BoboFacetCollector;
import com.liferay.portal.util.PropsValues;

import com.browseengine.bobo.api.BoboBrowser;
import com.browseengine.bobo.api.BoboIndexReader;
import com.browseengine.bobo.api.Browsable;
import com.browseengine.bobo.api.BrowseHit;
import com.browseengine.bobo.api.BrowseRequest;
import com.browseengine.bobo.api.BrowseResult;
import com.browseengine.bobo.api.FacetAccessible;
import com.browseengine.bobo.api.FacetSpec;
import com.browseengine.bobo.api.FacetSpec.FacetSortSpec;
import com.browseengine.bobo.facets.FacetHandler;
import com.browseengine.bobo.facets.FacetHandler.TermCountSize;
import com.browseengine.bobo.facets.impl.MultiValueFacetHandler;
import com.browseengine.bobo.facets.impl.RangeFacetHandler;
import com.browseengine.bobo.facets.impl.SimpleFacetHandler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;

/**
 * @author Bruno Farache
 */
public class LuceneIndexSearcherImpl implements IndexSearcher {

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query " + query);
		}

		long companyId = searchContext.getCompanyId();
		Sort[] sorts = searchContext.getSorts();
		int start = searchContext.getStart();
		int end = searchContext.getEnd();

		Hits hits = null;

		org.apache.lucene.search.IndexSearcher indexSearcher = null;
		BoboIndexReader boboReader = null;
		Browsable browser = null;
		BrowseRequest browseRequest = null;
		Map<String, Facet> facets = null;

		try {
			indexSearcher = LuceneHelperUtil.getSearcher(companyId, true);

			List<FacetHandler<?>> handlerList =
				new ArrayList<FacetHandler<?>>();

			facets = searchContext.getFacets();

			for (Facet facet : facets.values()) {
				if (facet.isStatic()) {
					continue;
				}

				FacetConfiguration facetConfiguration =
					facet.getFacetConfiguration();

				if (facet instanceof MultiValueFacet) {
					MultiValueFacetHandler multiValueFacetHandler =
						new MultiValueFacetHandler(
							facetConfiguration.getFieldName(),
							facetConfiguration.getFieldName());

					handlerList.add(multiValueFacetHandler);
				}
				else if (facet instanceof RangeFacet) {
					List<String> ranges = new ArrayList<String>();

					JSONArray rangesJSONArray =
						facetConfiguration.getData().getJSONArray("ranges");

					if (rangesJSONArray != null) {
						for (int i = 0; i < rangesJSONArray.length(); i++) {
							JSONObject rangeJSONObject =
								rangesJSONArray.getJSONObject(i);

							ranges.add(rangeJSONObject.getString("range"));
						}
					}

					RangeFacetHandler rangeFacetHandler =
						new RangeFacetHandler(
							facetConfiguration.getFieldName(),
							facetConfiguration.getFieldName(), ranges);

					rangeFacetHandler.setTermCountSize(TermCountSize.large);

					handlerList.add(rangeFacetHandler);
				}
				else if (facet instanceof SimpleFacet) {
					SimpleFacetHandler simpleFacetHandler =
						new SimpleFacetHandler(
							facetConfiguration.getFieldName(),
							facetConfiguration.getFieldName());

					handlerList.add(simpleFacetHandler);
				}
			}

			boboReader = BoboIndexReader.getInstance(
				indexSearcher.getIndexReader(), handlerList);

			SortField[] sortFields = new SortField[0];

			if (sorts != null) {
				sortFields = new SortField[sorts.length];

				for (int i = 0; i < sorts.length; i++) {
					Sort sort = sorts[i];

					sortFields[i] = new SortField(
						sort.getFieldName(), sort.getType(), sort.isReverse());
				}
			}

			browseRequest = new BrowseRequest();

			for (Facet facet : facets.values()) {
				if (facet.isStatic()) {
					continue;
				}

				FacetConfiguration facetConfiguration =
					facet.getFacetConfiguration();

				FacetSpec spec = new FacetSpec();
				spec.setOrderBy(
					FacetSortSpec.valueOf(facetConfiguration.getOrder()));

				browseRequest.setFacetSpec(facet.getFieldName(), spec);
			}

			browseRequest.setCount(PropsValues.INDEX_SEARCH_LIMIT);
			browseRequest.setOffset(0);
			browseRequest.setSort(sortFields);
			browseRequest.setQuery(
				(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
					query));

			browser = new BoboBrowser(boboReader);

			long startTime = System.currentTimeMillis();

			BrowseResult result = browser.browse(browseRequest);

			BrowseHit[] browseHits = result.getHits();

			long endTime = System.currentTimeMillis();

			float searchTime = (float)(endTime - startTime) / Time.SECOND;

			hits = toHits(
				indexSearcher, new HitDocs(browseHits), query, startTime,
				searchTime, start, end);

			Map<String,FacetAccessible> facetMap = result.getFacetMap();

			for (Map.Entry<String,FacetAccessible> facetEntry :
					facetMap.entrySet()) {

				Facet facet = facets.get(facetEntry.getKey());

				FacetAccessible facetAccessible = facetEntry.getValue();

				facet.setFacetCollector(
					new BoboFacetCollector(
						facetEntry.getKey(), facetAccessible));
			}
		}
		catch (BooleanQuery.TooManyClauses tmc) {
			int maxClauseCount = BooleanQuery.getMaxClauseCount();

			BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);

			try {
				long startTime = System.currentTimeMillis();

				BrowseResult result = browser.browse(browseRequest);

				BrowseHit[] browseHits = result.getHits();

				long endTime = System.currentTimeMillis();

				float searchTime = (float)(endTime - startTime) / Time.SECOND;

				hits = toHits(
					indexSearcher, new HitDocs(browseHits), query, startTime,
					searchTime, start, end);

				Map<String,FacetAccessible> facetMap = result.getFacetMap();

				for (Map.Entry<String,FacetAccessible> facetEntry :
						facetMap.entrySet()) {

					Facet facet = facets.get(facetEntry.getKey());

					FacetAccessible facetAccessible = facetEntry.getValue();

					facet.setFacetCollector(
						new BoboFacetCollector(facetEntry.getKey(), facetAccessible));
				}
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
			if (browser != null) {
				try {
					browser.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search found " + hits.getLength() + " results in " +
					hits.getSearchTime() + "ms");
		}

		return hits;
	}

	public Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query " + query);
		}

		Hits hits = null;

		org.apache.lucene.search.IndexSearcher indexSearcher = null;
		org.apache.lucene.search.Sort luceneSort = null;

		try {
			indexSearcher = LuceneHelperUtil.getSearcher(companyId, true);

			if (sorts != null) {
				SortField[] sortFields = new SortField[sorts.length];

				for (int i = 0; i < sorts.length; i++) {
					Sort sort = sorts[i];

					sortFields[i] = new SortField(
						sort.getFieldName(), sort.getType(), sort.isReverse());
				}

				luceneSort = new org.apache.lucene.search.Sort(sortFields);
			}
			else {
				luceneSort = new org.apache.lucene.search.Sort();
			}

			long startTime = System.currentTimeMillis();

			TopFieldDocs topFieldDocs = indexSearcher.search(
				(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
					query),
				null, PropsValues.INDEX_SEARCH_LIMIT, luceneSort);

			long endTime = System.currentTimeMillis();

			float searchTime = (float)(endTime - startTime) / Time.SECOND;

			hits = toHits(
				indexSearcher, new HitDocs(topFieldDocs), query, startTime,
				searchTime, start, end);
		}
		catch (BooleanQuery.TooManyClauses tmc) {
			int maxClauseCount = BooleanQuery.getMaxClauseCount();

			BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);

			try {
				long startTime = System.currentTimeMillis();

				TopFieldDocs topFieldDocs = indexSearcher.search(
					(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
						query),
					null, PropsValues.INDEX_SEARCH_LIMIT, luceneSort);

				long endTime = System.currentTimeMillis();

				float searchTime = (float)(endTime - startTime) / Time.SECOND;

				hits = toHits(
					indexSearcher, new HitDocs(topFieldDocs), query, startTime,
					searchTime, start, end);
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
			if (indexSearcher != null) {
				try {
					indexSearcher.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
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
		org.apache.lucene.document.Document oldDocument) {

		DocumentImpl newDocument = new DocumentImpl();

		List<org.apache.lucene.document.Fieldable> oldFieldables =
			oldDocument.getFields();

		for (org.apache.lucene.document.Fieldable oldFieldable :
				oldFieldables) {

			Field newField = null;

			String[] values = oldDocument.getValues(oldFieldable.name());

			if ((values != null) && (values.length > 1)) {
				newField = new Field(oldFieldable.name(), values);
			}
			else {
				newField = new Field(
					oldFieldable.name(), oldFieldable.stringValue());
			}

			newField.setNumeric(oldFieldable instanceof NumericField);
			newField.setTokenized(oldFieldable.isTokenized());

			newDocument.add(newField);
		}

		return newDocument;
	}

	protected String[] getQueryTerms(Query query) {
		String[] queryTerms = new String[0];

		try {
			queryTerms = LuceneHelperUtil.getQueryTerms(
				(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
					query));
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
				(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
					query), field, s);
		}
		catch (ParseException pe) {
			_log.error("Query: " + query, pe);
		}

		return snippet;
	}

	protected Hits toHits(
			org.apache.lucene.search.IndexSearcher indexSearcher,
			HitDocs hitDocs, Query query, long startTime,
			float searchTime, int start, int end)
		throws IOException, ParseException {

		int length = hitDocs.getTotalHits();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			start = 0;
			end = length;
		}

		String[] queryTerms = getQueryTerms(query);

		IndexReader indexReader = indexSearcher.getIndexReader();

		List<String> indexedFieldNames = new ArrayList<String> (
			indexReader.getFieldNames(IndexReader.FieldOption.INDEXED));

		org.apache.lucene.search.Query luceneQuery =
			(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
				query);

		int scoredFieldNamesCount = LuceneHelperUtil.countScoredFieldNames(
			luceneQuery, ArrayUtil.toStringArray(indexedFieldNames.toArray()));

		Hits hits = new HitsImpl();

		if ((start > -1) && (start <= end)) {
			if (end > length) {
				end = length;
			}

			if (start > end) {
				start = end;
			}

			int subsetTotal = end - start;

			if (subsetTotal > PropsValues.INDEX_SEARCH_LIMIT) {
				subsetTotal = PropsValues.INDEX_SEARCH_LIMIT;
			}

			List<Document> subsetDocs = new ArrayList<Document>(subsetTotal);
			List<String> subsetSnippets = new ArrayList<String>(subsetTotal);
			List<Float> subsetScores = new ArrayList<Float>(subsetTotal);

			QueryConfig queryConfig = query.getQueryConfig();

			boolean highlightEnabled = queryConfig.isHighlightEnabled();

			for (int i = start; i < end; i++) {
				if (i >= PropsValues.INDEX_SEARCH_LIMIT) {
					break;
				}

				org.apache.lucene.document.Document document =
					indexSearcher.doc(hitDocs.getDocId(i));

				Document subsetDocument = getDocument(document);

				subsetDocs.add(subsetDocument);

				if (highlightEnabled) {
					String subsetSnippet = getSnippet(
						document, query, Field.CONTENT);

					subsetSnippets.add(subsetSnippet);
				}
				else {
					subsetSnippets.add(StringPool.BLANK);
				}

				Float subsetScore =
					hitDocs.getScore(i) / scoredFieldNamesCount;

				subsetScores.add(subsetScore);

				if (_log.isDebugEnabled()) {
					try {
						Explanation explanation = indexSearcher.explain(
							luceneQuery, hitDocs.getDocId(i));

						_log.debug(explanation.toString());
					}
					catch (Exception e) {
					}
				}
			}

			hits.setStart(startTime);
			hits.setSearchTime(searchTime);
			hits.setQuery(query);
			hits.setQueryTerms(queryTerms);
			hits.setDocs(subsetDocs.toArray(new Document[subsetDocs.size()]));
			hits.setLength(length);
			hits.setSnippets(
				subsetSnippets.toArray(new String[subsetSnippets.size()]));
			hits.setScores(
				subsetScores.toArray(new Float[subsetScores.size()]));
		}

		return hits;
	}

	public class HitDocs {

		public HitDocs(BrowseHit[] browseHits) {
			_browseHits = browseHits;
		}

		public HitDocs(TopFieldDocs topFieldDocs) {
			_topFieldDocs = topFieldDocs;
		}

		public int getDocId(int i) {
			if (_topFieldDocs != null) {
				return _topFieldDocs.scoreDocs[i].doc;
			}
			else if (_browseHits != null) {
				return _browseHits[i].getDocid();
			}

			throw new IllegalStateException();
		}

		public float getScore(int i) {
			if (_topFieldDocs != null) {
				return _topFieldDocs.scoreDocs[i].score;
			}
			else if (_browseHits != null) {
				return _browseHits[i].getScore();
			}

			throw new IllegalStateException();
		}

		public int getTotalHits() {
			if (_topFieldDocs != null) {
				return _topFieldDocs.totalHits;
			}
			else if (_browseHits != null) {
				return _browseHits.length;
			}

			throw new IllegalStateException();
		}

		private BrowseHit[] _browseHits;
		private TopFieldDocs _topFieldDocs;

	}

	private static Log _log = LogFactoryUtil.getLog(
		LuceneIndexSearcherImpl.class);

}