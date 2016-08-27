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

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Miguel Pastor
 */
public class PortletMessagesTag extends com.liferay.taglib.util.IncludeTag {

	public void setGroup(Group group) {
		_group = group;
	}

	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-theme:portlet-messages:group", _group);
		request.setAttribute(
			"liferay-theme:portlet-messages:portlet", _portlet);
	}

	private static final String _PAGE =
		"/html/taglib/theme/portlet_messages/page.jsp";

	private Group _group;
	private Portlet _portlet;

}