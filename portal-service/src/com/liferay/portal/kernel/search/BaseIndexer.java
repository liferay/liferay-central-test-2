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

package com.liferay.portal.kernel.search;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Ryan Park
 * @author Raymond Augé
 */
public abstract class BaseIndexer implements Indexer {

	public void delete(Object obj) throws SearchException {
		try {
			doDelete(obj);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public String getSortField(String orderByCol) {
		return doGetSortField(orderByCol);
	}

	public Document getDocument(Object obj) throws SearchException {
		try {
			Document document = doGetDocument(obj);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessDocument(document, obj);
			}

			if (document == null) {
				return null;
			}

			Map<String, Field> fields = document.getFields();

			Field groupIdField = fields.get(Field.GROUP_ID);

			if (groupIdField != null) {
				long groupId = GetterUtil.getLong(groupIdField.getValue());

				addStagingGroupKeyword(document, groupId);
			}

			return document;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public String getSearchEngineId() {
		return SearchEngineUtil.SYSTEM_ENGINE_ID;
	}

	public BooleanQuery getFacetQuery(
			String className, SearchContext searchContext)
		throws Exception {

		BooleanQuery facetQuery = BooleanQueryFactoryUtil.create();

		facetQuery.addExactTerm(Field.PORTLET_ID, getPortletId(searchContext));
		facetQuery.addExactTerm(Field.ENTRY_CLASS_NAME, className);

		if (searchContext.getUserId() > 0) {
			SearchPermissionChecker searchPermissionChecker =
				SearchEngineUtil.getSearchPermissionChecker();

			facetQuery =
				(BooleanQuery)searchPermissionChecker.getPermissionQuery(
					searchContext.getCompanyId(), searchContext.getGroupIds(),
					searchContext.getUserId(), className, facetQuery);
		}

		addSearchCategoryIds(facetQuery, searchContext);
		addSearchFolderIds(facetQuery, searchContext);
		addSearchNodeIds(facetQuery, searchContext);

		return facetQuery;
	}

	public IndexerPostProcessor[] getIndexerPostProcessors() {
		return _indexerPostProcessors;
	}

	public Summary getSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws SearchException {

		try {
			Summary summary = doGetSummary(
				document, locale, snippet, portletURL);

			for (IndexerPostProcessor indexerPostProcessor :
					_indexerPostProcessors) {

				indexerPostProcessor.postProcessSummary(
					summary, document, locale, snippet, portletURL);
			}

			return summary;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return true;
	}

	public boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	public boolean isStagingAware() {
		return _stagingAware;
	}

	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		indexerPostProcessorsList.add(indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[indexerPostProcessorsList.size()]);
	}

	public void reindex(Object obj) throws SearchException {
		try {
			if (SearchEngineUtil.isIndexReadOnly()) {
				return;
			}

			doReindex(obj);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reindex(String className, long classPK) throws SearchException {
		try {
			if (SearchEngineUtil.isIndexReadOnly()) {
				return;
			}

			doReindex(className, classPK);
		}
		catch (NoSuchModelException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index " + className + " " + classPK);
			}
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reindex(String[] ids) throws SearchException {
		try {
			if (SearchEngineUtil.isIndexReadOnly()) {
				return;
			}

			doReindex(ids);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public Hits search(SearchContext searchContext) throws SearchException {
		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			searchContext.setEntryClassNames(
				new String[] {getClassName(searchContext)});

			AssetEntriesFacet assetEntryFacet = new AssetEntriesFacet(
				searchContext);
			assetEntryFacet.setStatic(true);

			searchContext.addFacet(assetEntryFacet);

			ScopeFacet scopeFacet = new ScopeFacet(searchContext);
			scopeFacet.setStatic(true);

			searchContext.addFacet(scopeFacet);

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			addSearchAssetCategoryIds(contextQuery, searchContext);
			addSearchAssetTagNames(contextQuery, searchContext);
			addSearchGroupId(contextQuery, searchContext);

			BooleanQuery fullQuery = createFullQuery(
				contextQuery, searchContext);

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			int start = searchContext.getStart();
			int end = searchContext.getEnd();

			if (isFilterSearch() && (permissionChecker != null)) {
				start = 0;
				end = end + INDEX_FILTER_SEARCH_LIMIT;
			}

			Hits hits = SearchEngineUtil.search(searchContext, fullQuery);

			searchContext.setStart(start);
			searchContext.setEnd(end);

			if (isFilterSearch() && (permissionChecker != null)) {
				hits = filterSearch(hits, permissionChecker, searchContext);
			}

			return hits;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		List<IndexerPostProcessor> indexerPostProcessorsList =
			ListUtil.fromArray(_indexerPostProcessors);

		ListUtil.remove(indexerPostProcessorsList, indexerPostProcessor);

		_indexerPostProcessors = indexerPostProcessorsList.toArray(
			new IndexerPostProcessor[indexerPostProcessorsList.size()]);
	}

	protected void addLocalizedSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext,
			String field, boolean like)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, field, like);
		addSearchTerm(
			searchQuery, searchContext,
			DocumentImpl.getLocalizedName(searchContext.getLocale(), field),
			like);
	}

	protected void addSearchAssetCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet assetCategoryIdsFacet = new MultiValueFacet(
			searchContext);

		assetCategoryIdsFacet.setFieldName(Field.ASSET_CATEGORY_IDS);
		assetCategoryIdsFacet.setStatic(true);

		searchContext.addFacet(assetCategoryIdsFacet);
	}

	protected void addSearchAssetTagNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet assetTagNamesFacet = new MultiValueFacet(searchContext);

		assetTagNamesFacet.setFieldName(Field.ASSET_TAG_NAMES);
		assetTagNamesFacet.setStatic(true);

		searchContext.addFacet(assetTagNamesFacet);
	}

	protected void addSearchCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void addSearchExpando(
			BooleanQuery searchQuery, SearchContext searchContext,
			String keywords)
		throws Exception {

		ExpandoBridge expandoBridge =
			ExpandoBridgeFactoryUtil.getExpandoBridge(
				searchContext.getCompanyId(), getClassName(searchContext));

		Set<String> attributeNames = SetUtil.fromEnumeration(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			int indexType = GetterUtil.getInteger(
				properties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

			if (indexType != ExpandoColumnConstants.INDEX_TYPE_NONE) {
				String fieldName = ExpandoBridgeIndexerUtil.encodeFieldName(
					attributeName);

				if (Validator.isNotNull(keywords)) {
					if (searchContext.isAndSearch()) {
						searchQuery.addRequiredTerm(fieldName, keywords, true);
					}
					else {
						searchQuery.addTerm(fieldName, keywords, true);
					}
				}
			}
		}
	}

	protected void addSearchFolderIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void addSearchGroupId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		ScopeFacet scopeFacet = new ScopeFacet(searchContext);

		searchContext.addFacet(scopeFacet);
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		searchQuery.addTerms(Field.KEYWORDS, keywords, true);

		searchQuery.addExactTerm(Field.ASSET_CATEGORY_NAMES, keywords);
		searchQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);

		addSearchExpando(searchQuery, searchContext, keywords);
	}

	protected void addSearchNodeIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void addSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext,
			String field, boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		String value = String.valueOf(searchContext.getAttribute(field));

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		if (searchContext.isAndSearch()) {
			searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			searchQuery.addTerm(field, value, like);
		}
	}

	protected void addStagingGroupKeyword(Document document, long groupId)
		throws Exception {

		if (!isStagingAware()) {
			return;
		}

		boolean stagingGroup = false;

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			group = GroupLocalServiceUtil.getGroup(group.getParentGroupId());
		}

		if (group.isStagingGroup()) {
			stagingGroup = true;
		}

		document.addKeyword(Field.STAGING_GROUP, stagingGroup);
	}

	protected void checkSearchFolderId(
			long folderId, SearchContext searchContext)
		throws Exception {
	}

	protected void checkSearchNodeId(
			long nodeId, SearchContext searchContext)
		throws Exception {
	}

	protected BooleanQuery createFullQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

		addSearchKeywords(searchQuery, searchContext);
		postProcessSearchQuery(searchQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, searchContext);
		}

		Map<String,Facet> facets = searchContext.getFacets();

		for (Facet facet : facets.values()) {
			BooleanClause facetClause = facet.getFacetClause();

			if (facetClause != null) {
				contextQuery.add(
					facetClause.getQuery(),
					facetClause.getBooleanClauseOccur());
			}
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

		if (!searchQuery.clauses().isEmpty()) {
			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause[] booleanClauses = searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause booleanClause : booleanClauses) {
				fullQuery.add(
					booleanClause.getQuery(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		postProcessFullQuery(fullQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessFullQuery(fullQuery, searchContext);
		}

		return fullQuery;
	}

	protected abstract void doDelete(Object obj) throws Exception;

	protected abstract Document doGetDocument(Object obj) throws Exception;

	protected String doGetSortField(String orderByCol) {
		return orderByCol;
	}

	protected abstract Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception;

	protected abstract void doReindex(Object obj) throws Exception;

	protected abstract void doReindex(String className, long classPK)
		throws Exception;

	protected abstract void doReindex(String[] ids) throws Exception;

	protected Hits filterSearch(
		Hits hits, PermissionChecker permissionChecker,
		SearchContext searchContext) {

		List<Document> docs = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		int start = searchContext.getStart();
		int end = searchContext.getEnd();

		String paginationType = GetterUtil.getString(
			searchContext.getAttribute("paginationType"), "more");

		boolean hasMore = false;

		Document[] documents = hits.getDocs();

		for (int i = 0; i < documents.length; i++) {
			Document document = documents[i];

			String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			try {
				if (indexer.hasPermission(
						permissionChecker, entryClassPK, ActionKeys.VIEW)) {

					docs.add(document);
					scores.add(hits.score(i));
				}
			}
			catch (Exception e) {
			}

			if (paginationType.equals("more") && (docs.size() > end)) {
				hasMore = true;

				break;
			}
		}

		int length = docs.size();

		if (hasMore) {
			length = length + (end - start);
		}

		hits.setLength(length);

		if ((start != QueryUtil.ALL_POS) && (end != QueryUtil.ALL_POS)) {
			if (end > length) {
				end = length;
			}

			docs = docs.subList(start, end);
		}

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(scores.toArray(new Float[docs.size()]));

		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);

		return hits;
	}

	protected String getClassName(SearchContext searchContext) {
		String[] classNames = getClassNames();

		if (classNames.length != 1) {
			throw new UnsupportedOperationException(
				"Search method needs to be manually implemented for " +
					"indexers with more than one class name");
		}

		return classNames[0];
	}

	protected long getParentGroupId(long groupId) {
		long parentGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				parentGroupId = group.getParentGroupId();
			}
		}
		catch (Exception e) {
		}

		return parentGroupId;
	}

	protected abstract String getPortletId(SearchContext searchContext);

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private static final boolean _FILTER_SEARCH = false;

	public static final int INDEX_FILTER_SEARCH_LIMIT = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INDEX_FILTER_SEARCH_LIMIT));

	private static Log _log = LogFactoryUtil.getLog(BaseIndexer.class);

	private IndexerPostProcessor[] _indexerPostProcessors =
		new IndexerPostProcessor[0];
	private boolean _stagingAware = true;

}