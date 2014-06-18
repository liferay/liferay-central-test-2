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

package com.liferay.taglib.staging;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Levente Hudák
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(request, "groupId");

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			group = (Group)request.getAttribute(WebKeys.GROUP);
		}

		if (group == null) {
			group = themeDisplay.getScopeGroup();
		}

		if (group == null) {
			return SKIP_BODY;
		}

		long liveGroupId = group.getGroupId();
		long stagingGroupId = group.getGroupId();

		Group liveGroup = group;
		Group stagingGroup = group;

		if (!group.isStagedRemotely()) {
			if (group.isStagingGroup()) {
				liveGroup = group.getLiveGroup();

				liveGroupId = liveGroup.getGroupId();
			}

			if (group.hasStagingGroup()) {
				stagingGroup = group.getStagingGroup();

				stagingGroupId = stagingGroup.getGroupId();
			}
		}

		pageContext.setAttribute("group", group);
		pageContext.setAttribute("groupId", groupId);
		pageContext.setAttribute("liveGroup", liveGroup);
		pageContext.setAttribute("liveGroupId", liveGroupId);
		pageContext.setAttribute(
			"privateLayout", ParamUtil.getBoolean(request, "privateLayout"));
		pageContext.setAttribute("stagingGroup", stagingGroup);
		pageContext.setAttribute("stagingGroupId", stagingGroupId);

		return SKIP_BODY;
	}

}