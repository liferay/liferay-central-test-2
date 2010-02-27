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

package com.liferay.portlet.bookmarks.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;

import javax.portlet.PortletURL;

/**
 * <a href="BookmarksEntryAssetRendererFactory.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Julio Camarero
 */
public class BookmarksEntryAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String CLASS_NAME =BookmarksEntry.class.getName();

	public static final String TYPE = "bookmark";

	public AssetRenderer getAssetRenderer(long classPK) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return new BookmarksEntryAssetRenderer(entry);
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

		if (BookmarksPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			addAssetURL = liferayPortletResponse.createRenderURL(
				PortletKeys.BOOKMARKS);

			addAssetURL.setParameter(
				"struts_action", "/bookmarks/edit_entry");
			addAssetURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
			addAssetURL.setParameter(
				"folderId",
				String.valueOf(
					AssetPublisherUtil.getRecentFolderId(
						liferayPortletRequest, CLASS_NAME)));
		}

		return addAssetURL;
	}

}