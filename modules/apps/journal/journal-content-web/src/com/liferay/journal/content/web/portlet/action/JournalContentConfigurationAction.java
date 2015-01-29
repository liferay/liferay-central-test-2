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

package com.liferay.journal.content.web.portlet.action;

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	},
	service = ConfigurationAction.class
)
public class JournalContentConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String articleId = getArticleId(actionRequest);

		setPreference(actionRequest, "articleId", articleId);

		long articleGroupId = getArticleGroupId(actionRequest);

		setPreference(
			actionRequest, "articleGroupId", String.valueOf(articleGroupId));

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected long getArticleGroupId(PortletRequest portletRequest) {
		long assetEntryId = GetterUtil.getLong(
			getParameter(portletRequest, "assetEntryId"));

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getGroupId();
	}

	protected String getArticleId(PortletRequest portletRequest)
		throws PortalException {

		long assetEntryId = GetterUtil.getLong(
			getParameter(portletRequest, "assetEntryId"));

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		if (assetEntry == null) {
			return StringPool.BLANK;
		}

		JournalArticleAssetRendererFactory articleAssetRendererFactory =
			(JournalArticleAssetRendererFactory)
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						JournalArticle.class.getName());

		JournalArticleAssetRenderer articleAssetRenderer =
			(JournalArticleAssetRenderer)articleAssetRendererFactory.
				getAssetRenderer(assetEntry.getClassPK());

		JournalArticle article = articleAssetRenderer.getArticle();

		return StringUtil.toUpperCase(article.getArticleId());
	}

	@Reference
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void unsetAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = null;
	}

	private AssetEntryLocalService _assetEntryLocalService;

}