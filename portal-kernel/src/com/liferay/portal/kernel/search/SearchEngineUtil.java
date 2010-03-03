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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * <a href="SearchEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class SearchEngineUtil {

	/**
	 * @deprecated Use {@link
	 *			   com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}.
	 */
	public static final int ALL_POS = -1;

	public static void addDocument(long companyId, Document doc)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		_searchPermissionChecker.addPermissionFields(companyId, doc);

		_searchEngine.getWriter().addDocument(companyId, doc);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		_searchEngine.getWriter().deleteDocument(companyId, uid);
	}

	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		_searchEngine.getWriter().deletePortletDocuments(companyId, portletId);
	}

	public static PortalSearchEngine getPortalSearchEngine() {
		return _portalSearchEngine;
	}

	public static SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	public static boolean isIndexReadOnly() {
		return _portalSearchEngine.isIndexReadOnly();
	}

	public static Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query string " + query.toString());
		}

		return _searchEngine.getSearcher().search(
			companyId, query, _DEFAULT_SORT, start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query string " + query.toString());
		}

		return _searchEngine.getSearcher().search(
			companyId, query, new Sort[] {sort}, start, end);
	}

	public static Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query string " + query.toString());
		}

		return _searchEngine.getSearcher().search(
			companyId, query, sorts, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, _DEFAULT_SORT, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, Sort sort, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, sort, start, end);
	}

	public static Hits search(
			long companyId, long groupId, long userId, String className,
			Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		if (userId > 0) {
			query = _searchPermissionChecker.getPermissionQuery(
				companyId, groupId, userId, className, query);
		}

		return search(companyId, query, sorts, start, end);
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_portalSearchEngine.setIndexReadOnly(indexReadOnly);
	}

	public static void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		if (isIndexReadOnly()) {
			return;
		}

		_searchPermissionChecker.addPermissionFields(companyId, doc);

		_searchEngine.getWriter().updateDocument(companyId, uid, doc);
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
		_searchEngine = searchEngine;
	}

	public void setSearchPermissionChecker(
		SearchPermissionChecker searchPermissionChecker) {

		_searchPermissionChecker = searchPermissionChecker;
	}

	private static final Sort[] _DEFAULT_SORT = new Sort[] {
		new Sort(null, Sort.SCORE_TYPE, false),
		new Sort(Field.MODIFIED, Sort.LONG_TYPE, true)
	};

	private static Log _log = LogFactoryUtil.getLog(SearchEngineUtil.class);

	private static PortalSearchEngine _portalSearchEngine;
	private static SearchEngine _searchEngine;
	private static SearchPermissionChecker _searchPermissionChecker;

}