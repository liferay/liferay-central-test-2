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

import com.liferay.portal.theme.PortletDisplay;
import com.liferay.taglib.ui.IconTag;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class IconRefreshTag extends IconTag {

	protected String getPage() {
		PortletDisplay portletDisplay =
			(PortletDisplay)pageContext.getAttribute("portletDisplay");

		if (!portletDisplay.isShowRefreshIcon()) {
			return null;
		}

		setCssClass("portlet-refresh portlet-refresh-icon");
		setImage("../portlet/refresh");
		setMessage("refresh");
		setToolTip(false);
		setUrl(portletDisplay.getURLRefresh());

		return super.getPage();
	}

}