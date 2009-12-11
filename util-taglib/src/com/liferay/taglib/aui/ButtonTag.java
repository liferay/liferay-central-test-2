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

import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="ButtonTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ButtonTag extends IncludeTag implements DynamicAttributes {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (_value == null) {
			if (_type.equals("submit")) {
				_value = "save";
			}
			else if (_type.equals("cancel")) {
				_value = "cancel";
			}
			else if (_type.equals("reset")) {
				_value = "reset";
			}
		}

		request.setAttribute("aui:button:cssClass", _cssClass);
		request.setAttribute("aui:button:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:button:dynamicAttributes", _dynamicAttributes);
		request.setAttribute("aui:button:name", _name);
		request.setAttribute("aui:button:onClick", _onClick);
		request.setAttribute("aui:button:type", _type);
		request.setAttribute("aui:button:value", _value);

		return EVAL_BODY_BUFFERED;
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

	public void setName(String name) {
		_name = name;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(String value) {
		_value = value;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/aui/button/page.jsp";

	private String _cssClass;
	private boolean _disabled;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _name;
	private String _onClick;
	private String _type = "button";
	private String _value;

}