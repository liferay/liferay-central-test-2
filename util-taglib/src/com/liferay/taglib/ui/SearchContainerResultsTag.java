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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ServerDetector;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="SearchContainerResultsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class SearchContainerResultsTag extends TagSupport {

	public static final String DEFAULT_RESULTS_VAR = "results";

	public static final String DEFAULT_TOTAL_VAR = "total";

	public int doEndTag() throws JspException {
		try {
			if (_results == null) {
				_results = (List)pageContext.getAttribute(_resultsVar);
				_total = (Integer)pageContext.getAttribute(_totalVar);
			}

			if (_results != null) {
				if (_total < _results.size()) {
					_total = _results.size();
				}
			}

			SearchContainerTag parentTag =
				(SearchContainerTag)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer searchContainer = parentTag.getSearchContainer();

			searchContainer.setResults(_results);
			searchContainer.setTotal(_total);

			parentTag.setHasResults(true);

			pageContext.setAttribute(_resultsVar, _results);
			pageContext.setAttribute(_totalVar, _total);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_results = null;
				_resultsVar = DEFAULT_RESULTS_VAR;
				_total = 0;
				_totalVar = DEFAULT_TOTAL_VAR;
			}
		}
	}

	public int doStartTag() throws JspException {
		SearchContainerTag parentTag =
			(SearchContainerTag)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (parentTag == null) {
			throw new JspTagException("Requires liferay-ui:search-container");
		}

		if (_results == null) {
			pageContext.setAttribute(_resultsVar, new ArrayList());
			pageContext.setAttribute(_totalVar, 0);
		}

		return EVAL_BODY_INCLUDE;
	}

	public List getResults() {
		return _results;
	}

	public String getResultsVar() {
		return _resultsVar;
	}

	public int getTotal() {
		return _total;
	}

	public String getTotalVar() {
		return _totalVar;
	}

	public void setResults(List results) {
		_results = results;
	}

	public void setResultsVar(String resultsVar) {
		_resultsVar = resultsVar;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setTotalVar(String totalVar) {
		_totalVar = totalVar;
	}

	private List _results;
	private String _resultsVar = DEFAULT_RESULTS_VAR;
	private int _total;
	private String _totalVar = DEFAULT_TOTAL_VAR;

}