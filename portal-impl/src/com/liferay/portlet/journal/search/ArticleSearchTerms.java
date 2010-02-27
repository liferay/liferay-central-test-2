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
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * <a href="ArticleSearchTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ArticleSearchTerms extends ArticleDisplayTerms {

	public ArticleSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		articleId = DAOParamUtil.getLike(portletRequest, ARTICLE_ID, false);
		version = ParamUtil.getDouble(portletRequest, VERSION);
		title = DAOParamUtil.getLike(portletRequest, TITLE);
		description = DAOParamUtil.getLike(portletRequest, DESCRIPTION);
		content = DAOParamUtil.getLike(portletRequest, CONTENT);
		type = DAOParamUtil.getString(portletRequest, TYPE);
		structureId = DAOParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = DAOParamUtil.getString(portletRequest, TEMPLATE_ID);
		status = ParamUtil.getString(portletRequest, STATUS);
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public Double getVersionObj() {
		if (version == 0) {
			return null;
		}
		else {
			return new Double(version);
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		if (status.equals("approved")) {
			return StatusConstants.APPROVED;
		}
		else if (status.equals("not-approved")) {
			return StatusConstants.PENDING;
		}
		else if (status.equals("expired")) {
			return StatusConstants.EXPIRED;
		}
		else if (status.equals("review")) {
			return StatusConstants.ANY;
		}
		else {
			return StatusConstants.ANY;
		}
	}

	public Date getReviewDate() {
		if (status.equals("review")) {
			return new Date();
		}
		else {
			return null;
		}
	}

}