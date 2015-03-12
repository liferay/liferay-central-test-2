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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Mate Thurzo
 * @author Akos Thurzo
 */
@OSGiBeanProperties
public class ExportImportConfigurationIndexer extends BaseIndexer {

	public static final String CLASS_NAME =
		ExportImportConfiguration.class.getName();

	public ExportImportConfigurationIndexer() {
		setCommitImmediately(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addStatus(contextQuery, searchContext);

		contextQuery.addRequiredTerm(
			Field.COMPANY_ID, searchContext.getCompanyId());
		contextQuery.addRequiredTerm(
			Field.GROUP_ID,
			GetterUtil.getLong(searchContext.getAttribute(Field.GROUP_ID)));

		Serializable type = searchContext.getAttribute(Field.TYPE);

		if (type != null) {
			contextQuery.addRequiredTerm(
				Field.TYPE, GetterUtil.getInteger(type));
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, true);
		addSearchTerm(
			searchQuery, searchContext, "exportImportConfigurationId", false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, true);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)obj;

		deleteDocument(
			exportImportConfiguration.getCompanyId(),
			exportImportConfiguration.getExportImportConfigurationId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)obj;

		Document document = getBaseModelDocument(
			CLASS_NAME, exportImportConfiguration);

		document.addText(
			Field.DESCRIPTION, exportImportConfiguration.getDescription());
		document.addText(Field.NAME, exportImportConfiguration.getName());
		document.addKeyword(Field.TYPE, exportImportConfiguration.getType());
		document.addNumber(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		populateDates(document, settingsMap);
		populateLayoutIds(document, settingsMap);
		populateLocale(document, settingsMap);
		populateParameterMap(document, settingsMap);
		populateSiteInformation(document, settingsMap);
		populateTimeZone(document, settingsMap);

		document.addKeyword(
			_PREFIX_SETTING + Field.USER_ID,
			MapUtil.getLong(settingsMap, "userId"));

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)obj;

		Document document = getDocument(exportImportConfiguration);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), exportImportConfiguration.getCompanyId(),
			document, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(classPK);

		doReindex(exportImportConfiguration);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexExportImportConfigurations(companyId);
	}

	protected void populateDates(
		Document document, Map<String, Serializable> settingsMap) {

		if (settingsMap.containsKey("endDate")) {
			Date endDate = (Date)settingsMap.get("endDate");

			document.addDate(_PREFIX_SETTING + "endDate", endDate);
		}

		if (settingsMap.containsKey("startDate")) {
			Date startDate = (Date)settingsMap.get("startDate");

			document.addDate(_PREFIX_SETTING + "startDate", startDate);
		}
	}

	protected void populateLayoutIds(
		Document document, Map<String, Serializable> settingsMap) {

		if (!settingsMap.containsKey("layoutIdMap") &&
			!settingsMap.containsKey("layoutIds")) {

			return;
		}

		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));

		if (ArrayUtil.isEmpty(layoutIds)) {
			Map<Long, Boolean> layoutIdMap =
				(Map<Long, Boolean>)settingsMap.get("layoutIdMap");

			try {
				layoutIds = ExportImportHelperUtil.getLayoutIds(layoutIdMap);
			}
			catch (PortalException pe) {
			}
		}

		document.addKeyword("layoutIds", layoutIds);
	}

	protected void populateLocale(
		Document document, Map<String, Serializable> settingsMap) {

		Locale locale = (Locale)settingsMap.get("locale");

		document.addText(_PREFIX_SETTING + "locale", locale.toString());
	}

	protected void populateParameterMap(
		Document document, Map<String, Serializable> settingsMap) {

		if (!settingsMap.containsKey("parameterMap")) {
			return;
		}

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] parameterValues = ArrayUtil.clone(entry.getValue());

			for (int i = 0; i < parameterValues.length; i++) {
				String parameterValue = parameterValues[i];

				if (parameterValue.equals(StringPool.TRUE)) {
					parameterValues[i] = "on";
				}
				else if (parameterValue.equals(StringPool.FALSE)) {
					parameterValues[i] = "off";
				}
			}

			document.addKeyword(
				_PREFIX_PARAMETER + entry.getKey(), parameterValues);
		}
	}

	protected void populateSiteInformation(
		Document document, Map<String, Serializable> settingsMap) {

		document.addKeyword(
			_PREFIX_SETTING + "privateLayout",
			MapUtil.getBoolean(settingsMap, "privateLayout"));
		document.addKeyword(
			_PREFIX_SETTING + "sourceGroupId",
			MapUtil.getLong(settingsMap, "sourceGroupId"));
		document.addKeyword(
			_PREFIX_SETTING + "targetGroupId",
			MapUtil.getLong(settingsMap, "targetGroupId"));
	}

	protected void populateTimeZone(
		Document document, Map<String, Serializable> settingsMap) {

		TimeZone timeZone = (TimeZone)settingsMap.get("timeZone");

		if (timeZone != null) {
			document.addKeyword(
				_PREFIX_SETTING + "timeZone", timeZone.getDisplayName());
		}
	}

	protected void reindexExportImportConfigurations(long companyId)
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			ExportImportConfigurationLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					ExportImportConfiguration exportImportConfiguration =
						(ExportImportConfiguration)object;

					Document document = getDocument(exportImportConfiguration);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static final String _PREFIX_PARAMETER = "parameter_";

	private static final String _PREFIX_SETTING = "setting_";

}