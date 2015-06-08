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
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Istvan Andras Dezsi
 */
@OSGiBeanProperties
public class AssetVocabularyIndexer extends BaseIndexer {

	public static final String CLASS_NAME = AssetVocabulary.class.getName();

	public AssetVocabularyIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.ASSET_VOCABULARY_ID, Field.COMPANY_ID, Field.GROUP_ID,
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

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(entryClassPK);

		return AssetVocabularyPermission.contains(
			permissionChecker, vocabulary, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.TITLE, true);

			searchQuery.add(localizedQuery, BooleanClauseOccur.SHOULD);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		AssetVocabulary vocabulary = (AssetVocabulary)obj;

		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, vocabulary.getVocabularyId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), vocabulary.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetVocabulary vocabulary = (AssetVocabulary)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing vocabulary " + vocabulary);
		}

		Document document = getBaseModelDocument(CLASS_NAME, vocabulary);

		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, vocabulary.getVocabularyId());
		document.addLocalizedText(
			Field.DESCRIPTION, vocabulary.getDescriptionMap());
		document.addText(Field.NAME, vocabulary.getName());
		document.addLocalizedText(Field.TITLE, vocabulary.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + vocabulary + " indexed successfully");
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
		AssetVocabulary vocabulary = (AssetVocabulary)obj;

		Document document = getDocument(vocabulary);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), vocabulary.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(classPK);

		doReindex(vocabulary);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexVocabularies(companyId);
	}

	protected void reindexVocabularies(final long companyId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			AssetVocabularyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					AssetVocabulary assetVocabulary = (AssetVocabulary)object;

					Document document = getDocument(assetVocabulary);

					if (document != null) {
						actionableDynamicQuery.addDocument(document);
					}
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyIndexer.class);

}