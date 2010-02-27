/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="OptionTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class OptionTag extends IncludeTag implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try{
			PortalIncludeUtil.include(pageContext, _END_PAGE);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_cssClass = null;
				_dynamicAttributes.clear();
				_label = null;
				_selected = false;
				_value = null;
			}
		}
	}

	public int doStartTag() throws JspException {
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (_value == null) {
				_value = _label;
			}

			request.setAttribute("aui:option:cssClass", _cssClass);
			request.setAttribute(
				"aui:option:dynamicAttributes", _dynamicAttributes);
			request.setAttribute("aui:option:label", _label);
			request.setAttribute(
				"aui:option:selected", String.valueOf(_selected));
			request.setAttribute("aui:option:value", _value);

			PortalIncludeUtil.include(pageContext, _START_PAGE);

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setLabel(Object label) {
		_label = String.valueOf(label);
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setValue(Object value) {
		_value = String.valueOf(value);
	}

	private static final String _END_PAGE = "/html/taglib/aui/option/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/option/start.jsp";

	private String _cssClass;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _label;
	private boolean _selected;
	private String _value;

}