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

package com.liferay.portal.kernel.portlet.toolbar.contributor.locator;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Provides the PortletToolbarContributorLocator interface which is responsible
 * for providing PortletToolbarContributors that will extend the portlet toolbar
 * by adding more elements.
 *
 * <p>
 * Implementations of this class will need to use OSGI Registry to return
 * PortletToolbarContributor implementations. The way the
 * PortletToolbarContributor are registered in OSGI Registry needs to be synced
 * with the way PortletToolbarContributorLocator will search for them.
 * </p>
 *
 * <p>Typically, PortletToolbarContributorLocator will be implemented based on
 * the MVC pattern used the by the portlet. This will allow to have different
 * extensions to the portlet toolbar for different views of the portlet.
 * </p>
 *
 * <p>
 * Implementations of this class are required to be OSGI components.
 * </p>
 *
 * @author Sergio González
 */
public interface PortletToolbarContributorLocator {

	/**
	 * Returns a list of PortletToolbarContributor for a particular request and
	 * portletId.
	 *
	 * @return a list of PortletToolbarContributor for a particular request and
	 *         portletId
	 */
	public List<PortletToolbarContributor> getPortletToolbarContributors(
		String portletId, PortletRequest portletRequest);

}