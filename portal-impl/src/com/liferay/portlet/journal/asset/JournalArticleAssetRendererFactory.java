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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import javax.portlet.PortletURL;

/**
 * <a href="JournalArticleAssetRendererFactory.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Julio Camarero
 */
public class JournalArticleAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = JournalArticle.class.getName();

	public static final String TYPE = "content";

	public AssetRenderer getAssetRenderer(long classPK) throws Exception {
		JournalArticleResource articleResource =
			JournalArticleResourceLocalServiceUtil.getArticleResource(classPK);

		JournalArticle article =
			JournalArticleLocalServiceUtil.getArticle(
				articleResource.getGroupId(), articleResource.getArticleId());

		return new JournalArticleAssetRenderer(article);
	}

	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws Exception {

		JournalArticle article = JournalArticleServiceUtil.getArticleByUrlTitle(
			groupId, urlTitle);

		return new JournalArticleAssetRenderer(article);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE;
	}

	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL addAssetURL = null;

		if (JournalPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ARTICLE)) {

			addAssetURL = liferayPortletResponse.createRenderURL(
				PortletKeys.JOURNAL);

			addAssetURL.setParameter("struts_action", "/journal/edit_article");
			addAssetURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
		}

		return addAssetURL;
	}

}