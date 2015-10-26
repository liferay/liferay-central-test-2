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

package com.liferay.blogs.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortletConfigurationIconFactory.class)
public class PermissionsPortletConfigurationIconFactory
	extends BasePortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new PermissionsPortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 101;
	}

}