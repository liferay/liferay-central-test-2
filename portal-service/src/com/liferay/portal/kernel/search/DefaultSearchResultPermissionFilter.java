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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tina Tian
 */
public class DefaultSearchResultPermissionFilter
	extends BaseSearchResultPermissionFilter {

	public DefaultSearchResultPermissionFilter(
		BaseIndexer<?> baseIndexer, PermissionChecker permissionChecker) {

		_baseIndexer = baseIndexer;
		_permissionChecker = permissionChecker;
	}

	@Override
	protected void filterHits(Hits hits, SearchContext searchContext) {
		List<Document> docs = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		Document[] documents = hits.getDocs();

		int excludeDocsSize = 0;

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		long companyId = _permissionChecker.getCompanyId();

		boolean isCompanyAdmin = _permissionChecker.isCompanyAdmin(companyId);

		for (int i = 0; i < documents.length; i++) {
			if (_isIncludeDocument(
					documents[i], status, isCompanyAdmin, companyId)) {

				docs.add(documents[i]);
				scores.add(hits.score(i));
			}
			else {
				excludeDocsSize++;
			}
		}

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(ArrayUtil.toFloatArray(scores));
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
		hits.setLength(hits.getLength() - excludeDocsSize);
	}

	@Override
	protected Hits getHits(SearchContext searchContext) throws SearchException {
		return _baseIndexer.doSearch(searchContext);
	}

	@Override
	protected boolean isGroupAdmin(SearchContext searchContext) {
		long[] groupIds = searchContext.getGroupIds();

		if (groupIds == null) {
			return false;
		}

		for (long groupId : groupIds) {
			if (!_permissionChecker.isGroupAdmin(groupId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _isIncludeDocument(
		Document document, int status, boolean isCompanyAdmin, long companyId) {

		long entryCompanyId = GetterUtil.getLong(
			document.get(Field.COMPANY_ID));

		if (entryCompanyId != companyId) {
			return false;
		}

		if (isCompanyAdmin) {
			return true;
		}

		String entryClassName = document.get(Field.ENTRY_CLASS_NAME);

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

		if (indexer == null) {
			return true;
		}

		if (!indexer.isFilterSearch()) {
			return true;
		}

		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		try {
			if (indexer.hasPermission(
					_permissionChecker, entryClassName, entryClassPK,
					ActionKeys.VIEW) &&
				indexer.isVisibleRelatedEntry(entryClassPK, status)) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultSearchResultPermissionFilter.class);

	private final BaseIndexer<?> _baseIndexer;
	private final PermissionChecker _permissionChecker;

}