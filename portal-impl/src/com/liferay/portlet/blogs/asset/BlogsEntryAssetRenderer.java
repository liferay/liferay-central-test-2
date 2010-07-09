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

package com.liferay.portlet.blogs.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public class BlogsEntryAssetRenderer extends BaseAssetRenderer {

	public BlogsEntryAssetRenderer(BlogsEntry entry) {
		_entry = entry;
	}

	public long getClassPK() {
		return _entry.getEntryId();
	}

	public String getDiscussionPath() {
		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			return "edit_entry_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _entry.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_entry.getContent());
	}

	public String getTitle() {
		return _entry.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL editPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.BLOGS);

		editPortletURL.setParameter("struts_action", "/blogs/edit_entry");
		editPortletURL.setParameter(
			"entryId", String.valueOf(_entry.getEntryId()));

		return editPortletURL;
	}

	public String getUrlTitle() {
		return _entry.getUrlTitle();
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
			"/blogs/find_entry?noSuchEntryRedirect=" +
				HttpUtil.encodeURL(noSuchEntryRedirect) + "&entryId=" +
					_entry.getEntryId();
	}

	public long getUserId() {
		return _entry.getUserId();
	}

	public String getUuid() {
		return _entry.getUuid();
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return BlogsEntryPermission.contains(
			permissionChecker, _entry, ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return BlogsEntryPermission.contains(
			permissionChecker, _entry, ActionKeys.VIEW);
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(WebKeys.BLOGS_ENTRY, _entry);

			return "/html/portlet/blogs/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/blogs/blogs.png";
	}

	private BlogsEntry _entry;

}