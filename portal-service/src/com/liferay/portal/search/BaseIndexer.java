/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * <a href="BaseIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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
			return doGetDocument(obj);
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
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

			contextQuery.addRequiredTerm(
				Field.PORTLET_ID, getPortletId(searchContext));

			addSearchGroupId(contextQuery, searchContext);
			addSearchOwnerUserId(contextQuery, searchContext);
			addSearchCategoryIds(contextQuery, searchContext);
			addSearchNodeIds(contextQuery, searchContext);
			addSearchFolderIds(contextQuery, searchContext);

			BooleanQuery fullQuery = createFullQuery(
				contextQuery, searchContext);

			return SearchEngineUtil.search(
				searchContext.getCompanyId(), searchContext.getGroupId(),
				searchContext.getUserId(), className, fullQuery,
				searchContext.getSorts(), searchContext.getStart(),
				searchContext.getEnd());
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
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
				"categoryId", categoryId);

			categoryIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
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
				"folderId", folderId);

			folderIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchGroupId(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long groupId = searchContext.getGroupId();

		if (groupId <= 0) {
			return;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long parentGroupId = groupId;

		if (group.isLayout() || searchContext.isScopeStrict()) {
			contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);
		}

		if (group.isLayout()) {
			parentGroupId = group.getParentGroupId();
		}

		contextQuery.addRequiredTerm(Field.GROUP_ID, parentGroupId);

		searchContext.setGroupId(parentGroupId);
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		searchQuery.addTerm(Field.USER_NAME, keywords);
		searchQuery.addTerm(Field.TITLE, keywords);
		searchQuery.addTerm(Field.CONTENT, keywords);
		searchQuery.addTerm(Field.DESCRIPTION, keywords);
		searchQuery.addTerm(Field.PROPERTIES, keywords);
		searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
		searchQuery.addTerm(Field.URL, keywords);
		searchQuery.addTerm(Field.COMMENTS, keywords);
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
				"nodeId", nodeId);

			nodeIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
		}

		contextQuery.add(nodeIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchOwnerUserId(
		BooleanQuery contextQuery, SearchContext searchContext) {

		long ownerUserId = searchContext.getOwnerUserId();

		if (ownerUserId > 0) {
			contextQuery.addRequiredTerm(Field.USER_ID, ownerUserId);
		}
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

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

		addSearchKeywords(searchQuery, searchContext);
		postProcessSearchQuery(searchQuery, searchContext);

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

		fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

		if (searchQuery.clauses().size() > 0) {
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

		return fullQuery;
	}

	protected abstract void doDelete(Object obj) throws Exception;

	protected abstract Document doGetDocument(Object obj) throws Exception;

	protected abstract void doReindex(Object obj) throws Exception;

	protected abstract void doReindex(String className, long classPK)
		throws Exception;

	protected abstract void doReindex(String[] ids) throws Exception;
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

	private static Log _log = LogFactoryUtil.getLog(BaseIndexer.class);

}