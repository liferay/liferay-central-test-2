/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.taglib.journal;

import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="TemplateSearchCountByKeywordsTag.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Prakash Reddy
 *
 */
public class TemplateSearchCountByKeywordsTag extends TagSupport {

	public int doStartTag()	throws JspException {
		try {
			int count = JournalTemplateLocalServiceUtil.searchCount(
				_companyId, _groupId, _keywords, _structureId,
				_structureIdComparator);

			pageContext.setAttribute(_var, count);

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public void setStructureIdComparator(String structureIdComparator) {
		_structureIdComparator = structureIdComparator;
	}

	public void setVar(String var) {
		_var = var;
	}

	private long _companyId;
	private long _groupId;
	private String _keywords;
	private String _structureId;
	private String _structureIdComparator;
	private String _var;

}