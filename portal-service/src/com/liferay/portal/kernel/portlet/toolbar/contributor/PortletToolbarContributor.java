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

package com.liferay.portal.kernel.portlet.toolbar.contributor;

import com.liferay.portal.kernel.servlet.taglib.ui.Menu;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Provides the PortletToolbarContributor interface which is responsible for
 * extending the portlet toolbar by adding more elements.
 *
 * <p>
 * Implementations of this class are OSGI components that are registered in the
 * OSGI Registry. The way this component is registered in OSGI Registry needs
 * to be consistent with the way the PortletToolbarContributorLocator
 * implementations searches in the registry.
 * </p>
 *
 * @author Sergio González
 */
public interface PortletToolbarContributor {

	/**
	 * Returns a list of Menu that will be rendered in the portlet toolbar
	 *
	 * @return a list of PortletToolbarContributor for a particular request and
	 *         portletId
	 */
	public List<Menu> getPortletTitleMenus(PortletRequest portletRequest);

}