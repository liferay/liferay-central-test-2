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
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.ui.IconTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconPortletTag extends IconTag {

	protected void cleanUp() {
		super.cleanUp();

		_portlet = null;
	}

	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("liferay-portlet:icon_portlet:portlet", _portlet);
	}

	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	protected String getPage() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)pageContext.getAttribute("themeDisplay");
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean showPortletIcon = true;
		String message = null;
		String src = null;

		if (_portlet != null) {
			message = PortalUtil.getPortletTitle(
				_portlet, pageContext.getServletContext(),
				themeDisplay.getLocale());
			src = _portlet.getStaticResourcePath().concat(_portlet.getIcon());
		}
		else {
			showPortletIcon = portletDisplay.isShowPortletIcon();
			message = portletDisplay.getTitle();
			src = portletDisplay.getURLPortlet();
		}

		if (!showPortletIcon) {
			return null;
		}

		setMessage(message);
		setSrc(src);

		return super.getPage();
	}

	private Portlet _portlet;

}