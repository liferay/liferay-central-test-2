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

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * <a href="SearchContainerResultRowTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerResultRowTag
	extends ParamAndPropertyAncestorTagImpl {

	public int doStartTag() throws JspTagException {
		SearchContainerTag parent = (SearchContainerTag)findAncestorWithClass(
			this, SearchContainerTag.class);

		if (parent == null) {
			throw new JspTagException(
				"Requires the liferay-ui:search-container tag");
		}
		else if (!parent.getHasResults()) {
			throw new JspTagException(
				"Requires the liferay-ui:search-container-results tag");
		}

		_resultRows = parent.getSearchContainer().getResultRows();
		_results = parent.getResults();

		if ((_results != null) && (!_results.isEmpty())) {
			_processRow();

			return EVAL_BODY_INCLUDE;
		}
		else {
			return SKIP_BODY;
		}
	}

	public int doAfterBody() {
	    _resultRows.add(_row);

	    _rowIndex++;

		if (_rowIndex < (_results.size())) {

		    _processRow();

		    return EVAL_BODY_AGAIN;
		}
		else {
		    return SKIP_BODY;
		}
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.setAttribute(
				SearchContainerResultsTag.RESULTS_VAR, _results);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_bold = false;
			_primaryKeyProperty = null;
			_stringPrimaryKey = false;
			_results = null;
			_resultRows = null;
			_rowIndex = 0;
			_row = null;
		}
	}

	private void _processRow() {
		HttpServletRequest request = getServletRequest();

		Object model = _results.get(_rowIndex);

		if (isStringPrimaryKey()) {
			String pKey = BeanParamUtil.getString(
				model, request, _primaryKeyProperty);

			_row = new ResultRow(model, pKey, _rowIndex, _bold);
		}
		else {
			long pKey = BeanParamUtil.getLong(
				model, request, _primaryKeyProperty);

			_row = new ResultRow(model, pKey, _rowIndex, _bold);
		}

		pageContext.setAttribute(INDEX_VAR, _rowIndex);
		pageContext.setAttribute(MODEL_VAR, model);
		pageContext.setAttribute(ROW_VAR, _row);
	}

	public boolean isBold() {
		return _bold;
	}

	public void setBold(boolean bold) {
		_bold = bold;
	}

	public String getPrimaryKeyProperty() {
		return _primaryKeyProperty;
	}

	public void setPrimaryKeyProperty(String primaryKeyProperty) {
		_primaryKeyProperty = primaryKeyProperty;
	}

	public List getResults() {
		return _results;
	}

	public void setResults(List results) {
		_results = results;
	}

	public boolean isStringPrimaryKey() {
		return _stringPrimaryKey;
	}

	public void setStringPrimaryKey(boolean stringPrimaryKey) {
		_stringPrimaryKey = stringPrimaryKey;
	}

	public static final String INDEX_VAR = "index";
	public static final String MODEL_VAR = "model";
	public static final String ROW_VAR = "row";

	private boolean _bold = false;
	private String _primaryKeyProperty;
	private boolean _stringPrimaryKey = false;
	private List _results;
	private List _resultRows;
	private int _rowIndex = 0;
	private ResultRow _row;
}
