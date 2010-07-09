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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 */
public class WikiPageAssetRenderer extends BaseAssetRenderer {

	public WikiPageAssetRenderer(WikiPage page) {
		_page = page;
	}

	public long getClassPK() {
		if (!_page.isApproved() &&
			(_page.getVersion() != WikiPageConstants.DEFAULT_VERSION)) {

			return _page.getPageId();
		}
		else {
			return _page.getResourcePrimKey();
		}
	}

	public String getDiscussionPath() {
		if (PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			return "edit_page_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _page.getGroupId();
	}

	public String getSummary() {
		String content = _page.getContent();

		if (_page.getFormat().equals("html")) {
			content = HtmlUtil.stripHtml(content);
		}

		return content;
	}

	public String getTitle() {
		return _page.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL editPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.WIKI);

		editPortletURL.setParameter("struts_action", "/wiki/edit_page");
		editPortletURL.setParameter(
			"nodeId", String.valueOf(_page.getNodeId()));
		editPortletURL.setParameter("title", _page.getTitle());

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/export_wiki_page");
		exportPortletURL.setParameter(
			"nodeId", String.valueOf(_page.getNodeId()));
		exportPortletURL.setParameter("title", _page.getTitle());

		return exportPortletURL;
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() +
			"/wiki/find_page?pageResourcePrimKey=" + _page.getResourcePrimKey();
	}

	public long getUserId() {
		return _page.getUserId();
	}

	public String getUuid() {
		return _page.getUuid();
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.VIEW);
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.WIKI_PAGE, _page);

			return "/html/portlet/wiki/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

	private WikiPage _page;

}