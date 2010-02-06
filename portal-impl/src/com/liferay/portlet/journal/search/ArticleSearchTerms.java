/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
		articleId = DAOParamUtil.getLike(portletRequest, ARTICLE_ID);
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