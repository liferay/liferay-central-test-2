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

package com.liferay.product.navigation.simulation.web.control;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.product.navigation.simulation.application.list.SimulationPanelCategory;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.USER,
		"service.ranking:Integer=200"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class SimulationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getBodyJspPath() {
		return "/portlet/control_menu/simulation_control_menu_body.jsp";
	}

	@Override
	public String getIconJspPath() {
		return "/portlet/control_menu/simulation_control_menu_entry_icon.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (!hasUpdateLayoutPermission(themeDisplay)) {
			return false;
		}

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			SimulationPanelCategory.SIMULATION,
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());

		if (panelApps.isEmpty()) {
			return false;
		}

		return super.isShow(request);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.simulation.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected boolean hasUpdateLayoutPermission(ThemeDisplay themeDisplay)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			ActionKeys.UPDATE);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	private PanelAppRegistry _panelAppRegistry;

}