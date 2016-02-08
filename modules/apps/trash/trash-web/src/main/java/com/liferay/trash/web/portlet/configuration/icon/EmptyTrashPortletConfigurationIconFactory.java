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

package com.liferay.trash.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;
import com.liferay.trash.web.constants.TrashPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + TrashPortletKeys.TRASH},
	service = PortletConfigurationIconFactory.class
)
public class EmptyTrashPortletConfigurationIconFactory
	extends BaseJSPPortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return new EmptyTrashPortletConfigurationIcon(
			getServletContext(), getJspPath(), portletRequest);
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/empty_trash.jsp";
	}

	@Override
	public double getWeight() {
		return 100.0;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.trash.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}