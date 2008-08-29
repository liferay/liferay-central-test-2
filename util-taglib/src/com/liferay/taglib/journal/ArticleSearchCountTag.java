/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.taglib.journal;

import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="ArticleSearchCountTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class ArticleSearchCountTag extends TagSupport {

	public int doStartTag()	throws JspException {
		try {
			int count = JournalArticleLocalServiceUtil.searchCount(
				_companyId, _groupId, _articleId, _version, _title,
				_description, _content, _type, _structureId, _templateId,
				_displayDateGT, _displayDateLT, _approved, _expired,
				_reviewDate, _andOperator);

			pageContext.setAttribute(_var, count);

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setAndOperator(boolean andOperator) {
		_andOperator = andOperator;
	}

	public void setApproved(Boolean approved) {
		_approved = approved;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDisplayDateGT(Date displayDateGT) {
		_displayDateGT = displayDateGT;
	}

	public void setDisplayDateLT(Date displayDateLT) {
		_displayDateLT = displayDateLT;
	}

	public void setExpired(Boolean expired) {
		_expired = expired;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setReviewDate(Date reviewDate) {
		_reviewDate = reviewDate;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setVar(String var) {
		_var = var;
	}

	public void setVersion(Double version) {
		_version = version;
	}

	private boolean _andOperator;
	private Boolean _approved;
	private String _articleId;
	private long _companyId;
	private String _content;
	private String _description;
	private Date _displayDateGT;
	private Date _displayDateLT;
	private Boolean _expired;
	private long _groupId;
	private Date _reviewDate;
	private String _structureId;
	private String _templateId;
	private String _title;
	private String _type;
	private String _var;
	private Double _version;

}