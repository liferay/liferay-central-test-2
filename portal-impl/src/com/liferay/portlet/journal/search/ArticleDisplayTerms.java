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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * <a href="ArticleDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ArticleDisplayTerms extends DisplayTerms {

	public static final String GROUP_ID = "groupId";

	public static final String ARTICLE_ID = "searchArticleId";

	public static final String VERSION = "version";

	public static final String TITLE = "title";

	public static final String DESCRIPTION = "description";

	public static final String CONTENT = "content";

	public static final String TYPE = "type";

	public static final String STRUCTURE_ID = "structureId";

	public static final String TEMPLATE_ID = "templateId";

	public static final String DISPLAY_DATE_GT = "displayDateGT";

	public static final String DISPLAY_DATE_LT = "displayDateLT";

	public static final String STATUS = "status";

	public ArticleDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		groupId = ParamUtil.getLong(
			portletRequest, GROUP_ID, themeDisplay.getScopeGroupId());
		articleId = ParamUtil.getString(portletRequest, ARTICLE_ID);
		version = ParamUtil.getDouble(portletRequest, VERSION);
		title = ParamUtil.getString(portletRequest, TITLE);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		content = ParamUtil.getString(portletRequest, CONTENT);
		type = ParamUtil.getString(portletRequest, TYPE);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
		status = ParamUtil.getString(portletRequest, STATUS);
	}

	public long getGroupId() {
		return groupId;
	}

	public String getArticleId() {
		return articleId;
	}

	public double getVersion() {
		return version;
	}

	public String getVersionString() {
		if (version != 0) {
			return String.valueOf(version);
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public String getStructureId() {
		return structureId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public Date getDisplayDateGT() {
		return displayDateGT;
	}

	public void setDisplayDateGT(Date displayDateGT) {
		this.displayDateGT = displayDateGT;
	}

	public Date getDisplayDateLT() {
		return displayDateLT;
	}

	public void setDisplayDateLT(Date displayDateLT) {
		this.displayDateLT = displayDateLT;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected long groupId;
	protected String articleId;
	protected double version;
	protected String title;
	protected String description;
	protected String content;
	protected String type;
	protected String structureId;
	protected String templateId;
	protected Date displayDateGT;
	protected Date displayDateLT;
	protected String status;

}