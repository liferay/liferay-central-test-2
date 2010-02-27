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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.TextFormatter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="FieldWrapperTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldWrapperTag extends IncludeTag implements DynamicAttributes {

	public int doEndTag() throws JspException {
		try{
			PortalIncludeUtil.include(pageContext, getEndPage());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_cssClass = null;
				_dynamicAttributes.clear();
				_endPage = null;
				_first = false;
				_helpMessage = null;
				_inlineField = false;
				_inlineLabel = null;
				_label = null;
				_last = false;
				_name = null;
				_startPage = null;
			}
		}
	}

	public int doStartTag() throws JspException {
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (_label == null) {
				_label = TextFormatter.format(_name, TextFormatter.K);
			}

			request.setAttribute("aui:field-wrapper:cssClass", _cssClass);
			request.setAttribute(
				"aui:field-wrapper:dynamicAttributes", _dynamicAttributes);
			request.setAttribute(
				"aui:field-wrapper:first", String.valueOf(_first));
			request.setAttribute(
				"aui:field-wrapper:helpMessage", _helpMessage);
			request.setAttribute(
				"aui:field-wrapper:inlineField", String.valueOf(_inlineField));
			request.setAttribute("aui:field-wrapper:inlineLabel", _inlineLabel);
			request.setAttribute("aui:field-wrapper:label", _label);
			request.setAttribute(
				"aui:field-wrapper:last", String.valueOf(_last));
			request.setAttribute("aui:field-wrapper:name", _name);

			PortalIncludeUtil.include(pageContext, getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
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

	public void setName(String name) {
		_name = name;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private static final String _END_PAGE =
		"/html/taglib/aui/field_wrapper/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/field_wrapper/start.jsp";

	private String _cssClass;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _endPage;
	private boolean _first;
	private String _helpMessage;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private String _name;
	private String _startPage;

}