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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="SearchContainerResultsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerResultsTag extends TagSupport {

	public static final String DEFAULT_VAR = "results";

	public int doEndTag() throws JspException {
		try {
			SearchContainerTag parentTag =
				(SearchContainerTag)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer searchContainer = parentTag.getSearchContainer();

			searchContainer.setResults(_results);
			searchContainer.setTotal(_total);

			parentTag.setHasResults(true);

			pageContext.setAttribute(getVar(), _results);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_results = null;
			_total = 0;
			_var = DEFAULT_VAR;
		}
	}

	public int doStartTag() throws JspException {
		SearchContainerTag parentTag =
			(SearchContainerTag)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (parentTag == null) {
			throw new JspTagException("Requires liferay-ui:search-container");
		}

		return SKIP_BODY;
	}

	public List getResults() {
		return _results;
	}

	public int getTotal() {
		return _total;
	}

	public String getVar() {
		return _var;
	}

	public void setResults(List results) {
		_results = results;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setVar(String var) {
		_var = var;
	}

	private List _results;
	private int _total;
	private String _var = DEFAULT_VAR;

}