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

package com.liferay.knowledge.base.web.asset;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.knowledge.base.constants.ActionKeys;
import com.liferay.knowledge.base.constants.PortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.permission.AdminPermission;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.knowledge.base.web.constants.WebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Peter Shin
 */
public class KBArticleAssetRendererFactory
	extends BaseAssetRendererFactory<KBArticle> {

	public static final String TYPE = "article";

	public KBArticleAssetRendererFactory() {
		setLinkable(true);
		setSearchable(true);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException {

		KBArticle kbArticle = getKBArticle(
			classPK, WorkflowConstants.STATUS_ANY);

		return super.getAssetEntry(className, kbArticle.getKbArticleId());
	}

	@Override
	public AssetRenderer<KBArticle> getAssetRenderer(long classPK, int type)
		throws PortalException {

		KBArticle kbArticle = null;

		if (type == TYPE_LATEST_APPROVED) {
			kbArticle = getKBArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);
		}
		else {
			kbArticle = getKBArticle(classPK, WorkflowConstants.STATUS_ANY);
		}

		KBArticleAssetRenderer kbArticleAssetRenderer =
			new KBArticleAssetRenderer(kbArticle);

		kbArticleAssetRenderer.setAssetRendererType(type);

		return kbArticleAssetRenderer;
	}

	@Override
	public String getClassName() {
		return KBArticle.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-file";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.KNOWLEDGE_BASE_DISPLAY;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, getGroup(liferayPortletRequest),
			PortletKeys.KNOWLEDGE_BASE_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/admin/edit_article.jsp");

		return portletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return AdminPermission.contains(
			permissionChecker, groupId, ActionKeys.ADD_KB_ARTICLE);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return KBArticlePermission.contains(
			permissionChecker, classPK, actionId);
	}

	protected KBArticle getKBArticle(long classPK, int status)
		throws PortalException {

		KBArticle kbArticle = null;

		try {
			kbArticle = KBArticleLocalServiceUtil.getKBArticle(classPK);
		}
		catch (NoSuchArticleException nsae) {
			kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				classPK, status);
		}

		return kbArticle;
	}

}