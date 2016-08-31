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

package com.liferay.product.navigation.control.menu;

import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface that defines categories to be used by a
 * <code>product-navigation:control-menu</code> tag instance to render a new
 * Control Menu category. Control Menu categories include Control Menu entries
 * defined by {@link ProductNavigationControlMenuEntry} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * Control Menu categories is determined by the
 * <code>product.navigation.control.menu.category.order</code> property value.
 * The parent Control Menu category key can be defined by the
 * <code>product.navigation.control.menu.category.key</code> property value.
 * </p>
 *
 * @author Julio Camarero
 */
public interface ProductNavigationControlMenuCategory {

	/**
	 * Returns the Control Menu category's key. This key must be unique in the
	 * scope of the Control Menu, since it is referred to by Control Menu
	 * entries to be added to this Control Menu category.
	 *
	 * @return the Control Menu category's key
	 */
	public String getKey();

	/**
	 * Returns <code>true</code> if the Control Menu category should be
	 * displayed in the request's context.
	 *
	 * @param  request the request that renders the Control Menu category
	 * @return <code>true</code> if the Control Menu category should be
	 *         displayed in the request's context; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException;

}