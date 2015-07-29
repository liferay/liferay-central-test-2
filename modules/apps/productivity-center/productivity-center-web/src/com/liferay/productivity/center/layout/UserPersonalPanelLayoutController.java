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

package com.liferay.productivity.center.layout;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypeController;
import com.liferay.portal.model.impl.BasePanelLayoutControllerImpl;
import com.liferay.productivity.center.panel.PanelAppRegistry;
import com.liferay.productivity.center.panel.PanelCategoryRegistry;
import com.liferay.productivity.center.taglib.constants.ProductivityCenterWebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"layout.type=" + LayoutConstants.TYPE_USER_PERSONAL_PANEL},
	service = LayoutTypeController.class
)
public class UserPersonalPanelLayoutController
	extends BasePanelLayoutControllerImpl {

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isParentable() {
		return false;
	}

	@Override
	public boolean isSitemapable() {
		return _SITEMAPABLE;
	}

	@Override
	public boolean isURLFriendliable() {
		return _URL_FRIENDLIABLE;
	}

	@Override
	protected void addAttributes(HttpServletRequest request) {
		request.setAttribute(
			ProductivityCenterWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);
		request.setAttribute(
			ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(response, unsyncStringWriter);
	}

	@Override
	protected String getEditPage() {
		return _EDIT_PAGE;
	}

	@Override
	protected String getViewPage() {
		return _VIEW_PAGE;
	}

	@Override
	protected void removeAttributes(HttpServletRequest request) {
		request.removeAttribute(ProductivityCenterWebKeys.PANEL_APP_REGISTRY);
		request.removeAttribute(
			ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.productivity.center.web)"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _EDIT_PAGE =
		"/layout/edit/user_personal_panel.jsp";

	private static final boolean _SITEMAPABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE), true);

	private static final String _URL = GetterUtil.getString(
		PropsUtil.get(PropsKeys.LAYOUT_URL));

	private static final boolean _URL_FRIENDLIABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE), true);

	private static final String _VIEW_PAGE =
		"/layout/view/user_personal_panel.jsp";

	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;

}