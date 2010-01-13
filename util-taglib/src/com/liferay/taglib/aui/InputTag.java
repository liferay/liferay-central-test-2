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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;
import com.liferay.util.TextFormatter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="InputTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class InputTag extends IncludeTag implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try {
			return super.doEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_bean = null;
				_checked = false;
				_cssClass = null;
				_disabled = false;
				_dynamicAttributes.clear();
				_field = null;
				_first = false;
				_helpMessage = null;
				_id = null;
				_inlineField = false;
				_inlineLabel = null;
				_label = null;
				_last = false;
				_model = null;
				_name = null;
				_prefix = null;
				_suffix = null;
				_type = null;
				_value = null;
			}
		}
	}

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (_bean == null) {
			_bean = pageContext.getAttribute("aui:model-context:bean");
		}

		if (Validator.isNull(_field)) {
			_field = _name;
		}

		if (Validator.isNull(_id)) {
			if (!Validator.equals(_type, "radio")) {
				_id = _name;
			}
			else {
				_id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
			}
		}

		if (_label == null) {
			_label = TextFormatter.format(_name, TextFormatter.K);
		}

		if (_model == null) {
			_model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		request.setAttribute("aui:input:bean", _bean);
		request.setAttribute("aui:input:checked", String.valueOf(_checked));
		request.setAttribute("aui:input:cssClass", _cssClass);
		request.setAttribute("aui:input:disabled", String.valueOf(_disabled));
		request.setAttribute("aui:input:field", _field);
		request.setAttribute("aui:input:first", String.valueOf(_first));
		request.setAttribute("aui:input:helpMessage", _helpMessage);
		request.setAttribute("aui:input:id", _id);
		request.setAttribute(
			"aui:input:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:input:inlineLabel", _inlineLabel);
		request.setAttribute("aui:input:label", _label);
		request.setAttribute("aui:input:last", String.valueOf(_last));
		request.setAttribute("aui:input:model", _model);
		request.setAttribute("aui:input:name", _name);
		request.setAttribute("aui:input:prefix", _prefix);
		request.setAttribute("aui:input:suffix", _suffix);
		request.setAttribute("aui:input:type", _type);
		request.setAttribute("aui:input:value", _value);

		request.setAttribute("aui:input:dynamicAttributes", _dynamicAttributes);

		return EVAL_BODY_BUFFERED;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setChecked(boolean checked) {
		_checked = checked;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrefix(String prefix) {
		_prefix = prefix;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(Object value) {
		_value = value;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/aui/input/page.jsp";

	private Object _bean;
	private boolean _checked;
	private String _cssClass;
	private boolean _disabled;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _field;
	private boolean _first;
	private String _helpMessage;
	private String _id;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private Class<?> _model;
	private String _name;
	private String _prefix;
	private String _suffix;
	private String _type;
	private Object _value;

}