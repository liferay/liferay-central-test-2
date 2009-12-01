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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import java.text.Format;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="InputFieldTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InputFieldTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-field:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-field:formName", _formName);
		request.setAttribute("liferay-ui:input-field:model", _model.getName());
		request.setAttribute("liferay-ui:input-field:bean", _bean);
		request.setAttribute("liferay-ui:input-field:field", _field);
		request.setAttribute("liferay-ui:input-field:fieldParam", _fieldParam);
		request.setAttribute(
			"liferay-ui:input-field:defaultValue", _defaultValue);
		request.setAttribute(
			"liferay-ui:input-field:disabled", String.valueOf(_disabled));
		request.setAttribute("liferay-ui:input-field:format", _format);

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFieldParam(String fieldParam) {
		_fieldParam = fieldParam;
	}

	public void setDefaultValue(Object defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setFormat(Format format) {
		_format = format;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/input_field/page.jsp";

	private String _cssClass;
	private String _formName = "fm";
	private Class<?> _model;
	private Object _bean;
	private String _field;
	private String _fieldParam;
	private Object _defaultValue;
	private boolean _disabled;
	private Format _format;

}