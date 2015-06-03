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

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Ryan Park
 */
public interface Indexer {

	public static final int DEFAULT_INTERVAL = 10000;

	public void delete(long companyId, String uid) throws SearchException;

	public void delete(Object obj) throws SearchException;

	public String getClassName();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSearchClassNames}
	 */
	@Deprecated
	public String[] getClassNames();

	public Document getDocument(Object obj) throws SearchException;

	public BooleanFilter getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception;

	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException;

	public IndexerPostProcessor[] getIndexerPostProcessors();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getClassName}
	 */
	@Deprecated
	public String getPortletId();

	public String[] getSearchClassNames();

	public String getSearchEngineId();

	public String getSortField(String orderByCol);

	public String getSortField(String orderByCol, int sortType);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(Document, String,
	 *             PortletRequest, PortletResponse)}
	 */
	@Deprecated
	public Summary getSummary(Document document, Locale locale, String snippet)
		throws SearchException;

	public Summary getSummary(
			Document document, String snippet, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws SearchException;

	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception;

	public boolean isCommitImmediately();

	public boolean isFilterSearch();

	public boolean isPermissionAware();

	public boolean isStagingAware();

	public boolean isVisible(long classPK, int status) throws Exception;

	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #postProcessContextBooleanFilter(
	 *             BooleanFilter, SearchContext)}
	 */
	@Deprecated
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception;

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #postProcessSearchQuery(
	 *             BooleanQuery, BooleanFilter, SearchContext)}
	 */
	@Deprecated
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception;

	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor);

	public void reindex(Object obj) throws SearchException;

	public void reindex(String className, long classPK) throws SearchException;

	public void reindex(String[] ids) throws SearchException;

	public Hits search(SearchContext searchContext) throws SearchException;

	public Hits search(
			SearchContext searchContext, String... selectedFieldNames)
		throws SearchException;

	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor);

}