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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * <a href="TemplateDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TemplateDisplayTerms extends DisplayTerms {

	public static final String GROUP_ID = "groupId";

	public static final String TEMPLATE_ID = "searchTemplateId";

	public static final String STRUCTURE_ID = "structureId";

	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	public TemplateDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		name = ParamUtil.getString(portletRequest, NAME);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
	}

	public long getGroupId() {
		return groupId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getStructureId() {
		return structureId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	protected long groupId;
	protected String templateId;
	protected String structureId;
	protected String name;
	protected String description;

}