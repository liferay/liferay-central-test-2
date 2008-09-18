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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.lang.reflect.Method;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SearchContainerResultRowTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerRowTag
	extends ParamAndPropertyAncestorTagImpl {

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

	public int doStartTag() throws JspException {
		SearchContainerTag parent = (SearchContainerTag)findAncestorWithClass(
			this, SearchContainerTag.class);

		if (parent == null) {
			throw new JspException(
				"Requires the liferay-ui:search-container tag");
		}
		else if (!parent.getHasResults()) {
			throw new JspException(
				"Requires the liferay-ui:search-container-results tag");
		}

		_resultRows = parent.getSearchContainer().getResultRows();
		_results = parent.getSearchContainer().getResults();

		if ((_results != null) && (!_results.isEmpty())) {
			_processRow();

			return EVAL_BODY_INCLUDE;
		}
		else {
			return SKIP_BODY;
		}
	}

	public int doAfterBody() throws JspException {
	    _resultRows.add(_row);

	    _headerNamesAssigned = true;

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
		_bold = false;
		_className = null;
		_escapedModel = false;
		_headerNamesAssigned = false;
		_indexVar = null;
		_keyProperty = null;
		_modelVar = null;
		_resultRows = null;
		_rowIndex = 0;
		_rowVar = null;
		_row = null;
		_stringKey = false;

		return EVAL_PAGE;
	}

	private void _processRow() throws JspException {
		HttpServletRequest request = getServletRequest();

		Object model = _results.get(_rowIndex);

		if (isEscapedModel()) {
			try {
				ClassLoader contextClassLoader =
					Thread.currentThread().getContextClassLoader();

				Class clazz = contextClassLoader.loadClass(_className);

				Method method = clazz.getMethod("toEscapedModel", new Class[0]);

				model = method.invoke(model, new Object[0]);
			}
			catch (Exception e) {
				throw new JspException(
					"Class|Interface identified by attribute \"className\" " +
						"must implement a toEscapedModel() method");
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				BeanParamUtil.getBoolean(model, request, "escapedModel"));
		}

		if (isStringKey()) {
			String pKey = BeanParamUtil.getString(
				model, request, _keyProperty);

			_row = new ResultRow(model, pKey, _rowIndex, _bold);
		}
		else {
			long pKey = BeanParamUtil.getLong(
				model, request, _keyProperty);

			_row = new ResultRow(model, pKey, _rowIndex, _bold);
		}

		pageContext.setAttribute(getModelVar(), model);
		pageContext.setAttribute(getIndexVar(), _rowIndex);
		pageContext.setAttribute(getRowVar(), _row);
	}

	public boolean isBold() {
		return _bold;
	}

	public void setBold(boolean bold) {
		_bold = bold;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public void setEscapedModel(boolean escapedModel) {
		_escapedModel = escapedModel;
	}

	public boolean isHeaderNamesAssigned() {
		return _headerNamesAssigned;
	}

	public void setHeaderNamesAssigned(boolean headerNamesAssigned) {
		_headerNamesAssigned = headerNamesAssigned;
	}

	public String getIndexVar() {
		if (Validator.isNull(_indexVar)) {
			return INDEX_VAR;
		}
		else {
			return _indexVar;
		}
	}

	public void setIndexVar(String indexVar) {
		_indexVar = indexVar;
	}

	public String getKeyProperty() {
		return _keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		_keyProperty = keyProperty;
	}

	public String getModelVar() {
		if (Validator.isNull(_modelVar)) {
			return MODEL_VAR;
		}
		else {
			return _modelVar;
		}
	}

	public void setModelVar(String var) {
		_modelVar = var;
	}

	public ResultRow getRow() {
		return _row;
	}

	public void setRow(ResultRow row) {
		_row = row;
	}

	public String getRowVar() {
		if (Validator.isNull(_rowVar)) {
			return ROW_VAR;
		}
		else {
			return _rowVar;
		}
	}

	public void setRowVar(String rowVar) {
		_rowVar = rowVar;
	}

	public boolean isStringKey() {
		return _stringKey;
	}

	public void setStringKey(boolean stringKey) {
		_stringKey = stringKey;
	}

	public static final String INDEX_VAR = "index";
	public static final String MODEL_VAR = "model";
	public static final String ROW_VAR = "row";

	private static Log _log = LogFactory.getLog(
		SearchContainerRowTag.class);

	private boolean _bold = false;
	private String _className;
	private boolean _escapedModel = false;
	private boolean _headerNamesAssigned = false;
	private String _indexVar;
	private String _keyProperty;
	private String _modelVar;
	private List _results;
	private List _resultRows;
	private int _rowIndex = 0;
	private String _rowVar;
	private ResultRow _row;
	private boolean _stringKey = false;

}
