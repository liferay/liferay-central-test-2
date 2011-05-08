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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class SearchEngineUtil {

	/**
	 * @deprecated Use {@link
	 *             com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}.
	 */
	public static final int ALL_POS = -1;

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	public static void addDocument(long companyId, Document document)
		throws SearchException {

		addDocument(SYSTEM_ENGINE_ID, companyId, document);
	}

	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Add document " + document.toString());
		}

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().addDocument(companyId, document);
	}

	public static void addDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {

		addDocuments(SYSTEM_ENGINE_ID, companyId, documents);
	}

	public static void addDocuments(
			String searchEngineId, long companyId, Collection<Document> documents)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Add document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().addDocuments(companyId, documents);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		deleteDocument(SYSTEM_ENGINE_ID, companyId, uid);
	}

	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().deleteDocument(companyId, uid);
	}

	public static void deleteDocuments(long companyId, Collection<String> uids)
		throws SearchException {

		deleteDocuments(SYSTEM_ENGINE_ID, companyId, uids);
	}

	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		if (isIndexReadOnly() || (uids == null) || uids.isEmpty()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().deleteDocuments(companyId, uids);
	}

	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

	}

	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().deletePortletDocuments(companyId, portletId);
	}

	public static PortalSearchEngine getPortalSearchEngine() {
		return _portalSearchEngine;
	}

	public static SearchEngine getSearchEngine() {
		return getSearchEngine(SYSTEM_ENGINE_ID);
	}

	public static SearchEngine getSearchEngine(String searchEngineId) {
		return _searchEngines.get(searchEngineId);
	}

	public static SearchPermissionChecker getSearchPermissionChecker() {
		return _searchPermissionChecker;
	}

	public static boolean isIndexReadOnly() {
		return _portalSearchEngine.isIndexReadOnly();
	}

	public static Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(
			searchContext.getSearchEngineId());

		return searchEngine.getSearcher().search(searchContext, query);
	}

	public static Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, int start,
			int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		return searchEngine.getSearcher().search(
			companyId, query, SortFactoryUtil.getDefaultSorts(), start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, sort, start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort sort,
			int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		return searchEngine.getSearcher().search(
			companyId, query, new Sort[] {sort}, start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		return search(SYSTEM_ENGINE_ID, companyId, query, sorts, start, end);
	}

	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort[] sorts,
			int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + query.toString());
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		return searchEngine.getSearcher().search(
			companyId, query, sorts, start, end);
	}

	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query);
		}

		return search(
			companyId, query, SortFactoryUtil.getDefaultSorts(), start, end);
	}

	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort sort, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query);
		}

		return search(companyId, query, sort, start, end);
	}

	public static Hits search(
			long companyId, long[] groupIds, long userId, String className,
			Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupIds, userId, className, query);
		}

		return search(companyId, query, sorts, start, end);
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_portalSearchEngine.setIndexReadOnly(indexReadOnly);
	}

	public static void updateDocument(long companyId, Document document)
		throws SearchException {

		updateDocument(SYSTEM_ENGINE_ID, companyId, document);
	}

	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document.toString());
		}

		_searchPermissionChecker.addPermissionFields(companyId, document);

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().updateDocument(companyId, document);
	}

	public static void updateDocuments(
			long companyId, Collection<Document> documents)
		throws SearchException {

		updateDocuments(SYSTEM_ENGINE_ID, companyId, documents);
	}

	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		if (isIndexReadOnly() || (documents == null) || documents.isEmpty()) {
			return;
		}

		for (Document document : documents) {
			if (_log.isDebugEnabled()) {
				_log.debug("Document " + document.toString());
			}

			_searchPermissionChecker.addPermissionFields(companyId, document);
		}

		SearchEngine searchEngine = _searchEngines.get(searchEngineId);

		searchEngine.getWriter().updateDocuments(companyId, documents);
	}

	public static void updatePermissionFields(long resourceId) {
		if (isIndexReadOnly()) {
			return;
		}

		_searchPermissionChecker.updatePermissionFields(resourceId);
	}

	public static void updatePermissionFields(String name, String primKey) {
		if (isIndexReadOnly()) {
			return;
		}

		_searchPermissionChecker.updatePermissionFields(name, primKey);
	}

	public void setPortalSearchEngine(PortalSearchEngine portalSearchEngine) {
		_portalSearchEngine = portalSearchEngine;
	}

	public void setSearchEngine(SearchEngine searchEngine) {
		_searchEngines.put(SYSTEM_ENGINE_ID, searchEngine);
	}

	public void setSearchEngines(Map<String,SearchEngine> searchEngines) {
		_searchEngines.putAll(searchEngines);
	}

	public void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static Log _log = LogFactoryUtil.getLog(SearchEngineUtil.class);

	private static PortalSearchEngine _portalSearchEngine;
	private static Map<String,SearchEngine> _searchEngines =
		new ConcurrentHashMap<String,SearchEngine>();
	private static SearchPermissionChecker _searchPermissionChecker;

}