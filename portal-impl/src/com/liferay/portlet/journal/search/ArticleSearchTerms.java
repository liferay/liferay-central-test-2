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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleSearchTerms extends ArticleDisplayTerms {

	public ArticleSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		articleId = DAOParamUtil.getLike(portletRequest, ARTICLE_ID, false);
		content = DAOParamUtil.getLike(portletRequest, CONTENT);
		description = DAOParamUtil.getLike(portletRequest, DESCRIPTION);
		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		status = ParamUtil.getString(portletRequest, STATUS);
		structureId = DAOParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = DAOParamUtil.getString(portletRequest, TEMPLATE_ID);
		title = DAOParamUtil.getLike(portletRequest, TITLE);
		type = DAOParamUtil.getString(portletRequest, TYPE);
		version = ParamUtil.getDouble(portletRequest, VERSION, -1);
	}

	public Date getReviewDate() {
		if (status.equals("review")) {
			return new Date();
		}
		else {
			return null;
		}
	}

	public int getStatusCode() {
		if (status.equals("approved")) {
			return WorkflowConstants.STATUS_APPROVED;
		}
		else if (status.equals("draft")) {
			return WorkflowConstants.STATUS_DRAFT;
		}
		else if (status.equals("expired")) {
			return WorkflowConstants.STATUS_EXPIRED;
		}
		else if (status.equals("pending")) {
			return WorkflowConstants.STATUS_PENDING;
		}
		else {
			return WorkflowConstants.STATUS_ANY;
		}
	}

	public Double getVersionObj() {
		if (version == 0) {
			return null;
		}
		else {
			return new Double(version);
		}
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(double version) {
		this.version = version;
	}

}