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

package com.liferay.portlet.directory.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Peter Fellwock
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DIRECTORY,
		"javax.portlet.name=" + PortletKeys.FRIENDS_DIRECTORY,
		"javax.portlet.name=" + PortletKeys.MY_SITES_DIRECTORY,
		"javax.portlet.name=" + PortletKeys.SITE_MEMBERS_DIRECTORY,
		"mvc.command.name=/directory/view_user"
	},
	service = MVCRenderCommand.class
)
public class ViewOrganizationDirectoryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return "/html/portlet/directory/view_user.jsp";
	}

}