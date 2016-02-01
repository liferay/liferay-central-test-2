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

package com.liferay.users.admin.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN},
	service = PortletConfigurationIconFactory.class
)
public class ExportUsersPortletConfigurationIconFactory
	extends BaseJSPPortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(PortletRequest portletRequest) {
		return new ExportUsersPortletConfigurationIcon(
			getServletContext(), getJspPath(), portletRequest);
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/export_users.jsp";
	}

	@Override
	public double getWeight() {
		return 101;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.users.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}