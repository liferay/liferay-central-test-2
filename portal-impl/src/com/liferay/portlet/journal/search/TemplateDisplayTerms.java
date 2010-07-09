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
 * @author Brian Wing Shun Chan
 */
public class TemplateDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String GROUP_ID = "groupId";

	public static final String NAME = "name";

	public static final String STRUCTURE_ID = "structureId";

	public static final String TEMPLATE_ID = "searchTemplateId";

	public TemplateDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		name = ParamUtil.getString(portletRequest, NAME);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
	}

	public String getDescription() {
		return description;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public String getStructureId() {
		return structureId;
	}

	public String getTemplateId() {
		return templateId;
	}

	protected String description;
	protected long groupId;
	protected String name;
	protected String structureId;
	protected String templateId;

}