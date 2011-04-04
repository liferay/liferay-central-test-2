/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class TemplateDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String GROUP_IDS = "groupIds";

	public static final String NAME = "name";

	public static final String STRUCTURE_ID = "structureId";

	public static final String TEMPLATE_ID = "searchTemplateId";

	public TemplateDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		groupIds = obtainGroupIds(portletRequest);
		name = ParamUtil.getString(portletRequest, NAME);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
	}

	public String getDescription() {
		return description;
	}

	public long[] getGroupIds() {
		return groupIds;
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

	public String getDefaultGroupIds(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String strutsAction = ParamUtil.getString(
			portletRequest, "struts_action");

		StringBundler sb = new StringBundler();

		sb.append(themeDisplay.getScopeGroupId());

		if (strutsAction.equalsIgnoreCase("/journal/select_template")) {
			sb.append(StringPool.COMMA);
			sb.append(themeDisplay.getCompanyGroupId());
		}

		return sb.toString();
	}

	protected long[] obtainGroupIds(PortletRequest portletRequest) {
		String groupIdsParams = ParamUtil.getString(
			portletRequest, GROUP_IDS, getDefaultGroupIds(portletRequest));

		return StringUtil.split(groupIdsParams, 0L);
	}

	protected String description;
	protected long[] groupIds;
	protected String name;
	protected String structureId;
	protected String templateId;

}