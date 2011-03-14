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
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexer;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Ryan Park
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

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		Summary summary = doGetSummary(document, snippet, portletURL);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessSummary(
				summary, document, snippet, portletURL);
		}

		return summary;
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
			String className = getClassName(searchContext);

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			addSearchAssetCategoryIds(contextQuery, searchContext);
			addSearchAssetTagNames(contextQuery, searchContext);
			addSearchGroupId(contextQuery, searchContext);
			addSearchStagingGroup(contextQuery, searchContext);
			addSearchOwnerUserId(contextQuery, searchContext);
			addSearchCategoryIds(contextQuery, searchContext);
			addSearchNodeIds(contextQuery, searchContext);
			addSearchFolderIds(contextQuery, searchContext);
			addSearchPortletIds(contextQuery, searchContext);

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

			Hits hits = SearchEngineUtil.search(
				searchContext.getCompanyId(), searchContext.getGroupIds(),
				searchContext.getUserId(), className, fullQuery,
				searchContext.getSorts(), start, end);

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

	protected void addSearchAssetCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] assetCategoryIds = searchContext.getAssetCategoryIds();

		if ((assetCategoryIds == null) || (assetCategoryIds.length == 0)) {
			return;
		}

		BooleanQuery assetCategoryIdsQuery = BooleanQueryFactoryUtil.create();

		for (long assetCategoryId : assetCategoryIds) {
			if (searchContext.getUserId() > 0) {
				try {
					AssetCategoryServiceUtil.getCategory(assetCategoryId);
				}
				catch (Exception e) {
					continue;
				}
			}

			TermQuery termQuery = TermQueryFactoryUtil.create(
				Field.ASSET_CATEGORY_IDS, assetCategoryId);

			 assetCategoryIdsQuery.add(termQuery, BooleanClauseOccur.MUST);
		}

		if (!assetCategoryIdsQuery.clauses().isEmpty()) {
			contextQuery.add(assetCategoryIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addSearchAssetTagNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		String[] assetTagNames = searchContext.getAssetTagNames();

		if ((assetTagNames == null) || (assetTagNames.length == 0)) {
			return;
		}

		BooleanQuery assetTagNamesQuery = BooleanQueryFactoryUtil.create();

		for (String assetTagName : assetTagNames) {
			TermQuery termQuery = TermQueryFactoryUtil.create(
				Field.ASSET_TAG_NAMES, assetTagName);

			assetTagNamesQuery.add(termQuery, BooleanClauseOccur.MUST);
		}

		if (!assetTagNamesQuery.clauses().isEmpty()) {
			contextQuery.add(assetTagNamesQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addSearchCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] categoryIds = searchContext.getCategoryIds();

		if ((categoryIds == null) || (categoryIds.length == 0)) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create();

		for (long categoryId : categoryIds) {
			if (searchContext.getUserId() > 0) {
				try {
					checkSearchCategoryId(categoryId, searchContext);
				}
				catch (Exception e) {
					continue;
				}
			}

			TermQuery termQuery = TermQueryFactoryUtil.create(
				Field.CATEGORY_ID, categoryId);

			categoryIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		if (!categoryIdsQuery.clauses().isEmpty()) {
			contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
		}
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

			if (GetterUtil.getBoolean(
					properties.getProperty(ExpandoBridgeIndexer.INDEXABLE))) {

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

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds == null) || (folderIds.length == 0)) {
			return;
		}

		BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create();

		for (long folderId : folderIds) {
			if (searchContext.getUserId() > 0) {
				try {
					checkSearchFolderId(folderId, searchContext);
				}
				catch (Exception e) {
					continue;
				}
			}

			TermQuery termQuery = TermQueryFactoryUtil.create(
				Field.FOLDER_ID, folderId);

			folderIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		if (!folderIdsQuery.clauses().isEmpty()) {
			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addSearchGroupId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds == null) || (groupIds.length == 0) ||
			((groupIds.length == 1) && (groupIds[0] == 0))){

			return;
		}

		BooleanQuery groupIdsQuery = BooleanQueryFactoryUtil.create();
		BooleanQuery scopeGroupIdsQuery = BooleanQueryFactoryUtil.create();

		for (int i = 0; i < groupIds.length; i ++) {
			long groupId = groupIds[i];

			if (groupId <= 0) {
				continue;
			}

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				long parentGroupId = groupId;

				if (group.isLayout()) {
					parentGroupId = group.getParentGroupId();
				}

				groupIdsQuery.addTerm(Field.GROUP_ID, parentGroupId);

				groupIds[i] = parentGroupId;

				if (group.isLayout() || searchContext.isScopeStrict()) {
					scopeGroupIdsQuery.addTerm(Field.SCOPE_GROUP_ID, groupId);
				}
			}
			catch (Exception e) {
				continue;
			}
		}

		searchContext.setGroupIds(groupIds);

		if (!groupIdsQuery.clauses().isEmpty()) {
			contextQuery.add(groupIdsQuery, BooleanClauseOccur.MUST);
		}

		if (!scopeGroupIdsQuery.clauses().isEmpty()) {
			contextQuery.add(scopeGroupIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		searchQuery.addTerms(_KEYWORDS_FIELDS, keywords);

		searchQuery.addExactTerm(Field.ASSET_CATEGORY_NAMES, keywords);
		searchQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);

		addSearchExpando(searchQuery, searchContext, keywords);
	}

	protected void addSearchNodeIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] nodeIds = searchContext.getNodeIds();

		if ((nodeIds == null) || (nodeIds.length == 0)) {
			return;
		}

		BooleanQuery nodeIdsQuery = BooleanQueryFactoryUtil.create();

		for (long nodeId : nodeIds) {
			if (searchContext.getUserId() > 0) {
				try {
					checkSearchNodeId(nodeId, searchContext);
				}
				catch (Exception e) {
					continue;
				}
			}

			TermQuery termQuery = TermQueryFactoryUtil.create(
				Field.NODE_ID, nodeId);

			nodeIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		if (!nodeIdsQuery.clauses().isEmpty()) {
			contextQuery.add(nodeIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void addSearchStagingGroup(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (!isStagingAware()) {
			return;
		}

		if (!searchContext.isIncludeLiveGroups() &&
			searchContext.isIncludeStagingGroups()) {

			contextQuery.addRequiredTerm(Field.STAGING_GROUP, true);
		}
		else if (searchContext.isIncludeLiveGroups() &&
				 !searchContext.isIncludeStagingGroups()) {

			contextQuery.addRequiredTerm(Field.STAGING_GROUP, false);
		}
	}

	protected void addSearchOwnerUserId(
		BooleanQuery contextQuery, SearchContext searchContext) {

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			contextQuery.addRequiredTerm(Field.USER_ID, ownerUserId);
		}
	}

	protected void addSearchPortletIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		String[] portletIds = searchContext.getPortletIds();

		if ((portletIds == null) || (portletIds.length == 0)) {
			contextQuery.addRequiredTerm(
				Field.PORTLET_ID, getPortletId(searchContext));
		}
		else {
			BooleanQuery portletIdsQuery = BooleanQueryFactoryUtil.create();

			for (String portletId : portletIds) {
				if (Validator.isNull(portletId)) {
					continue;
				}

				TermQuery termQuery = TermQueryFactoryUtil.create(
					Field.PORTLET_ID, portletId);

				portletIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
			}

			if (!portletIdsQuery.clauses().isEmpty()) {
				contextQuery.add(portletIdsQuery, BooleanClauseOccur.MUST);
			}
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

	protected void checkSearchCategoryId(
			long categoryId, SearchContext searchContext)
		throws Exception {
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

		postProcessContextQuery(contextQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessContextQuery(
				contextQuery, searchContext);
		}

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

		addSearchKeywords(searchQuery, searchContext);
		postProcessSearchQuery(searchQuery, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				_indexerPostProcessors) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, searchContext);
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

	protected abstract Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL);

	protected abstract void doReindex(Object obj) throws Exception;

	protected abstract void doReindex(String className, long classPK)
		throws Exception;

	protected abstract void doReindex(String[] ids) throws Exception;

	protected Hits filterSearch(
		Hits hits, PermissionChecker permissionChecker,
		SearchContext searchContext) {

		List<Document> docs = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			long entryClassPK = GetterUtil.getLong(
				doc.get(Field.ENTRY_CLASS_PK));

			try {
				if (hasPermission(
						permissionChecker, entryClassPK, ActionKeys.VIEW)) {

					docs.add(hits.doc(i));
					scores.add(hits.score(i));
				}
			}
			catch (Exception e) {
			}
		}

		int length = docs.size();

		hits.setLength(length);

		int start = searchContext.getStart();
		int end = searchContext.getEnd();

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

	protected boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return true;
	}

	protected boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	protected boolean isStagingAware() {
		return _stagingAware;
	}

	protected void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	protected void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private static final boolean _FILTER_SEARCH = false;

	public static final int INDEX_FILTER_SEARCH_LIMIT = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INDEX_FILTER_SEARCH_LIMIT));

	private static final String[] _KEYWORDS_FIELDS = {
		Field.COMMENTS, Field.CONTENT, Field.DESCRIPTION, Field.PROPERTIES,
		Field.TITLE, Field.URL, Field.USER_NAME
	};

	private static Log _log = LogFactoryUtil.getLog(BaseIndexer.class);

	private IndexerPostProcessor[] _indexerPostProcessors =
		new IndexerPostProcessor[0];
	private boolean _stagingAware = true;

}