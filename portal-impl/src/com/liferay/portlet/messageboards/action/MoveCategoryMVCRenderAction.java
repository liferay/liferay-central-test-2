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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adolfo Pérez
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/move_category"
	},
	service = MVCRenderCommand.class
)
public class MoveCategoryMVCRenderAction implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ActionUtil.getCategory(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/message_boards/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/html/portlet/message_boards/move_category.jsp";
	}

}