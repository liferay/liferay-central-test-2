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

package com.liferay.portlet.assetpublisher.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * <a href="AssetDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class AssetDisplayTerms extends DisplayTerms {

	public static final String GROUP_ID = "groupId";

	public static final String TITLE = "title";

	public static final String DESCRIPTION = "description";

	public static final String USER_NAME = "user-name";

	public AssetDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		groupId = ParamUtil.getLong(portletRequest, GROUP_ID);
		title = ParamUtil.getString(portletRequest, TITLE);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		userName = ParamUtil.getString(portletRequest, USER_NAME);
	}

	public long getGroupId() {
		return groupId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUserName() {
		return userName;
	}

	protected long groupId;
	protected String title;
	protected String description;
	protected String userName;

}