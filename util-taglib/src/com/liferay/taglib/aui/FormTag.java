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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="FormTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FormTag extends IncludeTag {

	public int doEndTag() throws JspException {
		try {
			return super.doEndTag();
		}
		finally {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.removeAttribute("aui:form:useNamespace");
		}
	}

	public void setAction(PortletURL portletURL) {
		if (portletURL != null) {
			_action = portletURL.toString();
		}
	}

	public void setAction(String action) {
		_action = action;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = escapeXml;
	}

	public void setInlineLabel(boolean inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnSubmit(String onSubmit) {
		_onSubmit = onSubmit;
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;
	}

	protected void cleanUp() {
		_action = null;
		_cssClass = null;
		_escapeXml = true;
		_inlineLabel = false;
		_name = "fm";
		_onSubmit = null;
		_useNamespace = true;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		String action = _action;

		if (_escapeXml) {
			action = HtmlUtil.escape(action);
		}

		request.setAttribute("aui:form:action", action);
		request.setAttribute("aui:form:cssClass", _cssClass);
		request.setAttribute(
			"aui:form:dynamicAttributes", getDynamicAttributes());
		request.setAttribute(
			"aui:form:inlineLabel", String.valueOf(_inlineLabel));
		request.setAttribute("aui:form:name", _name);
		request.setAttribute("aui:form:onSubmit", _onSubmit);
		request.setAttribute(
			"aui:form:useNamespace", String.valueOf(_useNamespace));
	}

	private static final String _END_PAGE = "/html/taglib/aui/form/end.jsp";

	private static final String _START_PAGE = "/html/taglib/aui/form/start.jsp";

	private String _action;
	private String _cssClass;
	private boolean _escapeXml = true;
	private boolean _inlineLabel;
	private String _name = "fm";
	private String _onSubmit;
	private boolean _useNamespace = true;

}