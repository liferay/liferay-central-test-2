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

package com.liferay.wiki.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiPagePermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"search.asset.type=com.liferay.wiki.model.WikiPage"
	},
	service = AssetRendererFactory.class
)
public class WikiPageAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "wiki";

	public WikiPageAssetRendererFactory() {
		setLinkable(true);
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException {

		WikiPage page = WikiPageLocalServiceUtil.fetchWikiPage(classPK);

		if (page == null) {
			if (type == TYPE_LATEST_APPROVED) {
				page = WikiPageLocalServiceUtil.getPage(classPK);
			}
			else {
				WikiPageResource pageResource =
					WikiPageResourceLocalServiceUtil.getPageResource(classPK);

				page = WikiPageLocalServiceUtil.getPage(
					pageResource.getNodeId(), pageResource.getTitle(), null);
			}
		}

		WikiPageAssetRenderer wikiPageAssetRenderer = new WikiPageAssetRenderer(
			page);

		wikiPageAssetRenderer.setAssetRendererType(type);

		return wikiPageAssetRenderer;
	}

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-file-alt";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				WikiPortletKeys.WIKI, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return WikiPagePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

}