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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Istvan Andras Dezsi
 */
@OSGiBeanProperties
public class AssetCategoryIndexer extends BaseIndexer {

	public static final String CLASS_NAME = AssetCategory.class.getName();

	public AssetCategoryIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.ASSET_CATEGORY_ID, Field.COMPANY_ID, Field.GROUP_ID,
			Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
			entryClassPK);

		return AssetCategoryPermission.contains(
			permissionChecker, category, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] parentCategoryIds = (long[])searchContext.getAttribute(
			Field.ASSET_PARENT_CATEGORY_IDS);

		if (!ArrayUtil.isEmpty(parentCategoryIds)) {
			TermsFilter parentCategoryTermsFilter = new TermsFilter(
				Field.ASSET_PARENT_CATEGORY_ID);

			parentCategoryTermsFilter.addValues(
				ArrayUtil.toStringArray(parentCategoryIds));

			contextBooleanFilter.add(
				parentCategoryTermsFilter, BooleanClauseOccur.MUST);
		}

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			Field.ASSET_VOCABULARY_IDS);

		if (!ArrayUtil.isEmpty(vocabularyIds)) {
			TermsFilter vocabularyTermsFilter = new TermsFilter(
				Field.ASSET_VOCABULARY_ID);

			vocabularyTermsFilter.addValues(
				ArrayUtil.toStringArray(vocabularyIds));

			contextBooleanFilter.add(
				vocabularyTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			searchContext.setAttribute(Field.ASSET_CATEGORY_TITLE, title);

			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.ASSET_CATEGORY_TITLE,
				true);
			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.TITLE, true);

			searchQuery.add(localizedQuery, BooleanClauseOccur.SHOULD);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		AssetCategory assetCategory = (AssetCategory)obj;

		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, assetCategory.getCategoryId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), assetCategory.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetCategory category = (AssetCategory)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing category " + category);
		}

		Document document = getBaseModelDocument(CLASS_NAME, category);

		document.addKeyword(Field.ASSET_CATEGORY_ID, category.getCategoryId());

		List<AssetCategory> categories = new ArrayList<>(1);

		categories.add(category);

		addSearchAssetCategoryTitles(
			document, Field.ASSET_CATEGORY_TITLE, categories);

		document.addKeyword(
			Field.ASSET_PARENT_CATEGORY_ID, category.getParentCategoryId());
		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, category.getVocabularyId());
		document.addLocalizedText(
			Field.DESCRIPTION, category.getDescriptionMap());
		document.addText(Field.NAME, category.getName());
		document.addLocalizedText(Field.TITLE, category.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + category + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		AssetCategory category = (AssetCategory)obj;

		Document document = getDocument(category);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), category.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
			classPK);

		doReindex(category);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
	}

	protected void reindexCategories(final long companyId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			AssetCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					AssetCategory category = (AssetCategory)object;

					Document document = getDocument(category);

					if (document != null) {
						actionableDynamicQuery.addDocument(document);
					}
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryIndexer.class);

}