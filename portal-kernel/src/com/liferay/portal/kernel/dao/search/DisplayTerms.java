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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="DisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DisplayTerms {

	public static final String KEYWORDS = "keywords";

	public static final String ADVANCED_SEARCH = "advancedSearch";

	public static final String AND_OPERATOR = "andOperator";

	public DisplayTerms(HttpServletRequest request) {
		keywords = ParamUtil.getString(request, KEYWORDS);
		advancedSearch = ParamUtil.getBoolean(request, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(request, AND_OPERATOR, true);
	}

	public DisplayTerms(PortletRequest portletRequest) {
		keywords = ParamUtil.getString(portletRequest, KEYWORDS);
		advancedSearch = ParamUtil.getBoolean(portletRequest, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(portletRequest, AND_OPERATOR, true);
	}

	public String getKeywords() {
		return keywords;
	}

	public boolean isAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(boolean advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public boolean isAndOperator() {
		return andOperator;
	}

	protected String keywords;
	protected boolean advancedSearch;
	protected boolean andOperator;

}