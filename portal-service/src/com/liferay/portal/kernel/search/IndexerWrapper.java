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

package com.liferay.portal.kernel.search;

import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class IndexerWrapper implements Indexer {

	public IndexerWrapper(Indexer indexer) {
		_indexer = indexer;
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		_indexer.addRelatedEntryFields(document, obj);
	}

	@Override
	public void delete(long companyId, String uid) throws SearchException {
		_indexer.delete(companyId, uid);
	}

	@Override
	public void delete(Object obj) throws SearchException {
		_indexer.delete(obj);
	}

	public String getClassName() {
		return _indexer.getClassName();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getClassName()}
	 */
	@Deprecated
	@Override
	public String[] getClassNames() {
		return _indexer.getClassNames();
	}

	@Override
	public int getDatabaseCount() throws Exception {
		return _indexer.getDatabaseCount();
	}

	@Override
	public Document getDocument(Object obj) throws SearchException {
		return _indexer.getDocument(obj);
	}

	@Override
	public BooleanQuery getFacetQuery(
			String className, SearchContext searchContext)
		throws Exception {

		return _indexer.getFacetQuery(className, searchContext);
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		return _indexer.getFullQuery(searchContext);
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		return _indexer.getIndexerPostProcessors();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getClassName()}
	 */
	@Deprecated
	@Override
	public String getPortletId() {
		return _indexer.getPortletId();
	}

	@Override
	public String[] getSearchClassNames() {
		return _indexer.getSearchClassNames();
	}

	@Override
	public String getSearchEngineId() {
		return _indexer.getSearchEngineId();
	}

	@Override
	public String getSortField(String orderByCol) {
		return _indexer.getSortField(orderByCol);
	}

	@Override
	public String getSortField(String orderByCol, int sortType) {
		return _indexer.getSortField(orderByCol, sortType);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(Document, String,
	 *             PortletURL, PortletRequest, PortletResponse)}
	 */
	@Deprecated
	@Override
	public Summary getSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws SearchException {

		return _indexer.getSummary(document, locale, snippet, portletURL);
	}

	@Override
	public Summary getSummary(
			Document document, String snippet, PortletURL portletURL,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws SearchException {

		return _indexer.getSummary(
			document, snippet, portletURL, portletRequest, portletResponse);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _indexer.hasPermission(
			permissionChecker, entryClassName, entryClassPK, actionId);
	}

	@Override
	public boolean isCommitImmediately() {
		return _indexer.isCommitImmediately();
	}

	@Override
	public boolean isFilterSearch() {
		return _indexer.isFilterSearch();
	}

	@Override
	public boolean isPermissionAware() {
		return _indexer.isPermissionAware();
	}

	@Override
	public boolean isStagingAware() {
		return _indexer.isStagingAware();
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		return _indexer.isVisible(classPK, status);
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		return _indexer.isVisibleRelatedEntry(classPK, status);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		_indexer.postProcessContextQuery(contextQuery, searchContext);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		_indexer.postProcessSearchQuery(searchQuery, searchContext);
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexer.registerIndexerPostProcessor(indexerPostProcessor);
	}

	@Override
	public void reindex(Object obj) throws SearchException {
		_indexer.reindex(obj);
	}

	@Override
	public void reindex(String className, long classPK) throws SearchException {
		_indexer.reindex(className, classPK);
	}

	@Override
	public void reindex(String[] ids) throws SearchException {
		_indexer.reindex(ids);
	}

	@Override
	public void reindexDDMStructures(List<Long> ddmStructureIds)
		throws SearchException {

		_indexer.reindexDDMStructures(ddmStructureIds);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		return _indexer.search(searchContext);
	}

	@Override
	public Hits search(
			SearchContext searchContext, String... selectedFieldNames)
		throws SearchException {

		return _indexer.search(searchContext, selectedFieldNames);
	}

	@Override
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexer.unregisterIndexerPostProcessor(indexerPostProcessor);
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
		_indexer.updateFullQuery(searchContext);
	}

	private final Indexer _indexer;

}