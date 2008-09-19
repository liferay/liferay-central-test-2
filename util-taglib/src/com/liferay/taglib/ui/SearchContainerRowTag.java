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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.lang.reflect.Method;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SearchContainerRowTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerRowTag extends ParamAndPropertyAncestorTagImpl {

	public static final String DEFAULT_INDEX_VAR = "index";

	public static final String DEFAULT_MODEL_VAR = "model";

	public static final String DEFAULT_ROW_VAR = "row";

	public void addParam(String name, String value) {
		if (name.equals("className")) {
			_row.setClassName(value);
		}
		else if (name.equals("classHoverName")) {
			_row.setClassHoverName(value);
		}
		else if (name.equals("restricted")) {
			_row.setRestricted(GetterUtil.getBoolean(value, false));
		}
		else {
			_row.setParameter(name, value);
		}
	}

	public int doAfterBody() throws JspException {
		_resultRows.add(_row);

		_headerNamesAssigned = true;

		_rowIndex++;

		if (_rowIndex < (_results.size())) {
			processRow();

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	public int doEndTag() {
		_bold = false;
		_className = null;
		_escapedModel = false;
		_headerNamesAssigned = false;
		_indexVar = DEFAULT_INDEX_VAR;
		_keyProperty = null;
		_modelVar = DEFAULT_MODEL_VAR;
		_resultRows = null;
		_rowIndex = 0;
		_rowVar = DEFAULT_ROW_VAR;
		_row = null;
		_stringKey = false;

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		SearchContainerTag parentTag =
			(SearchContainerTag)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (parentTag == null) {
			throw new JspException("Requires liferay-ui:search-container");
		}
		else if (!parentTag.isHasResults()) {
			throw new JspException(
				"Requires liferay-ui:search-container-results");
		}

		_resultRows = parentTag.getSearchContainer().getResultRows();
		_results = parentTag.getSearchContainer().getResults();

		if ((_results != null) && (!_results.isEmpty())) {
			processRow();

			return EVAL_BODY_INCLUDE;
		}
		else {
			return SKIP_BODY;
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getIndexVar() {
		return _indexVar;
	}

	public String getKeyProperty() {
		return _keyProperty;
	}

	public String getModelVar() {
		return _modelVar;
	}

	public ResultRow getRow() {
		return _row;
	}

	public String getRowVar() {
		return _rowVar;
	}

	public boolean isBold() {
		return _bold;
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isHeaderNamesAssigned() {
		return _headerNamesAssigned;
	}

	public boolean isStringKey() {
		return _stringKey;
	}

	public void setBold(boolean bold) {
		_bold = bold;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setEscapedModel(boolean escapedModel) {
		_escapedModel = escapedModel;
	}

	public void setHeaderNamesAssigned(boolean headerNamesAssigned) {
		_headerNamesAssigned = headerNamesAssigned;
	}

	public void setIndexVar(String indexVar) {
		_indexVar = indexVar;
	}

	public void setKeyProperty(String keyProperty) {
		_keyProperty = keyProperty;
	}

	public void setModelVar(String var) {
		_modelVar = var;
	}

	public void setRow(ResultRow row) {
		_row = row;
	}

	public void setRowVar(String rowVar) {
		_rowVar = rowVar;
	}

	public void setStringKey(boolean stringKey) {
		_stringKey = stringKey;
	}

	protected void processRow() throws JspException {
		HttpServletRequest request = getServletRequest();

		Object model = _results.get(_rowIndex);

		if (isEscapedModel()) {
			try {
				ClassLoader contextClassLoader =
					Thread.currentThread().getContextClassLoader();

				Class<?> classObj = contextClassLoader.loadClass(_className);

				Method method = classObj.getMethod(
					"toEscapedModel", new Class[0]);

				model = method.invoke(model, new Object[0]);
			}
			catch (Exception e) {
				throw new JspException(e.getMessage());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				BeanParamUtil.getBoolean(model, request, "escapedModel"));
		}

		if (isStringKey()) {
			String primaryKey = BeanParamUtil.getString(
				model, request, _keyProperty);

			_row = new ResultRow(model, primaryKey, _rowIndex, _bold);
		}
		else {
			long primaryKey = BeanParamUtil.getLong(
				model, request, _keyProperty);

			_row = new ResultRow(model, primaryKey, _rowIndex, _bold);
		}

		pageContext.setAttribute(getIndexVar(), _rowIndex);
		pageContext.setAttribute(getModelVar(), model);
		pageContext.setAttribute(getRowVar(), _row);
	}

	private static Log _log = LogFactory.getLog(SearchContainerRowTag.class);

	private boolean _bold;
	private String _className;
	private boolean _escapedModel;
	private boolean _headerNamesAssigned;
	private String _indexVar = DEFAULT_INDEX_VAR;
	private String _keyProperty;
	private String _modelVar = DEFAULT_MODEL_VAR;
	private List _results;
	private List<ResultRow> _resultRows;
	private int _rowIndex;
	private String _rowVar = DEFAULT_ROW_VAR;
	private ResultRow _row;
	private boolean _stringKey = false;

}