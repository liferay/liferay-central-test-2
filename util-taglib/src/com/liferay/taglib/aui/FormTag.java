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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="FormTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FormTag extends TagSupport implements DynamicAttributes {

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
				_action = null;
				_cssClass = null;
				_dynamicAttributes.clear();
				_endPage = null;
				_escapeXml = true;
				_inlineLabel = false;
				_name = "fm";
				_onSubmit = null;
				_startPage = null;
			}
		}
	}

	public int doStartTag() throws JspException{
		try{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (_escapeXml) {
				_action = HtmlUtil.escape(_action);
			}

			request.setAttribute("aui:form:action", _action);
			request.setAttribute("aui:form:cssClass", _cssClass);
			request.setAttribute(
				"aui:form:dynamicAttributes", _dynamicAttributes);
			request.setAttribute(
				"aui:form:inlineLabel", String.valueOf(_inlineLabel));
			request.setAttribute("aui:form:name", _name);
			request.setAttribute("aui:form:onSubmit", _onSubmit);

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

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
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

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private static final String _END_PAGE = "/html/taglib/aui/form/end.jsp";

	private static final String _START_PAGE = "/html/taglib/aui/form/start.jsp";

	private String _action;
	private String _cssClass;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _endPage;
	private boolean _escapeXml = true;
	private boolean _inlineLabel;
	private String _name = "fm";
	private String _onSubmit;
	private String _startPage;

}