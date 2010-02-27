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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * <a href="TemplateSearchTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TemplateSearchTerms extends TemplateDisplayTerms {

	public TemplateSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		templateId = DAOParamUtil.getLike(portletRequest, TEMPLATE_ID);
		structureId = DAOParamUtil.getString(portletRequest, STRUCTURE_ID);
		name = DAOParamUtil.getLike(portletRequest, NAME);
		description = DAOParamUtil.getLike(portletRequest, DESCRIPTION);
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public String getStructureIdComparator() {
		return structureIdComparator;
	}

	public void setStructureIdComparator(String structureIdComparator) {
		this.structureIdComparator = structureIdComparator;
	}

	protected String structureIdComparator;

}