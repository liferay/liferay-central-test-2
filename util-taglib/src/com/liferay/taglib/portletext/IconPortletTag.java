/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.portletext;

import com.liferay.portal.model.Portlet;
import com.liferay.taglib.ui.IconTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IconPortletTag extends IconTag {

	protected void cleanUp() {
		_portlet = null;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-portlet:icon_portlet:portlet", _portlet);
	}

	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_portlet/page.jsp";

	private Portlet _portlet;

}