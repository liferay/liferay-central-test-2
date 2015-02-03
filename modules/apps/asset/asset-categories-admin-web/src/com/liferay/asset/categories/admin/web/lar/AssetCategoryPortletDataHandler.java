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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyImpl;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN
	},
	service = PortletDataHandler.class
)
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(AssetCategory.class),
			new StagedModelType(AssetVocabulary.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "categories", true, false, null,
				AssetCategory.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "vocabularies", true, false, null,
				AssetVocabulary.class.getName()));
		setPublishToLiveByDefault(true);

		XStreamAliasRegistryUtil.register(
			AssetCategoryImpl.class, "AssetCategory");
		XStreamAliasRegistryUtil.register(
			AssetVocabularyImpl.class, "AssetVocabulary");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AssetCategoryPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		AssetVocabularyLocalServiceUtil.deleteVocabularies(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories")) {
			ActionableDynamicQuery categoryActionableDynamicQuery =
				getCategoryActionableDynamicQuery(portletDataContext);

			categoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "vocabularies")) {
			ActionableDynamicQuery vocabularyActionableDynamicQuery =
				getVocabularyActionableDynamicQuery(portletDataContext);

			vocabularyActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories")) {
			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(
					AssetCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "vocabularies")) {
			Element vocabulariesElement =
				portletDataContext.getImportDataGroupElement(
					AssetVocabulary.class);

			List<Element> vocabularyElements = vocabulariesElement.elements();

			for (Element vocabularyElement : vocabularyElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, vocabularyElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery categoryActionableDynamicQuery =
			getCategoryActionableDynamicQuery(portletDataContext);

		categoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery vocabularyActionableDynamicQuery =
			getVocabularyActionableDynamicQuery(portletDataContext);

		vocabularyActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getCategoryActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			AssetCategoryLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		// Override date range criteria

		actionableDynamicQuery.setAddCriteriaMethod(null);

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getVocabularyActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			AssetVocabularyLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		// Override date range criteria

		actionableDynamicQuery.setAddCriteriaMethod(null);

		return actionableDynamicQuery;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}