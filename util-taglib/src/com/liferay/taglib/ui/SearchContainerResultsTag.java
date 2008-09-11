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

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="SearchContainerResultsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerResultsTag extends TagSupport {

	public int doStartTag() throws JspTagException {
		SearchContainerTag parent = (SearchContainerTag)findAncestorWithClass(
			this, SearchContainerTag.class);

		if (parent == null) {
			throw new JspTagException(
				"Requires the liferay-ui:search-container tag.");
		}

		parent.getSearchContainer().setResults(_results);
		parent.setResults(_results);
		parent.setHasResults(true);

		pageContext.setAttribute(RESULTS_VAR, _results);

		return SKIP_BODY;
	}

	public int doEndTag() {
		_results = null;

		return EVAL_PAGE;
	}

	public List getResults() {
		return _results;
	}

	public void setResults(List results) {
		_results = results;
	}

	public static final String RESULTS_VAR = "results";

	private List _results;

}
