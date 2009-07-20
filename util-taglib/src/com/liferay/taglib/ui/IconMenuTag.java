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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class IconMenuTag extends BodyTagSupport {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:icon-menu:message", _message);
		request.setAttribute(
			"liferay-ui:icon-menu:showWhenSingleIcon",
			String.valueOf(_showWhenSingleIcon));
		request.setAttribute("liferay-ui:icon-menu:align", _align);
		request.setAttribute("liferay-ui:icon-menu:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:icon-menu:icon-count", new IntegerWrapper());

		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		BodyContent bodyContent = getBodyContent();

		_bodyContentString = bodyContent.getString();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-menu:icon-count");

		Boolean singleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		if ((iconCount != null) && (iconCount.getValue() == 1) &&
			(singleIcon == null)) {

			bodyContent.clearBody();

			request.setAttribute(
				"liferay-ui:icon-menu:single-icon", Boolean.TRUE);

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
				"liferay-ui:icon-menu:icon-count");

			request.removeAttribute("liferay-ui:icon-menu:icon-count");

			Boolean singleIcon = (Boolean)request.getAttribute(
				"liferay-ui:icon-menu:single-icon");

			request.removeAttribute("liferay-ui:icon-menu:single-icon");

			if ((iconCount != null) && (iconCount.getValue() >= 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				PortalIncludeUtil.include(pageContext, getStartPage());
			}

			pageContext.getOut().print(_bodyContentString);

			if ((iconCount != null) && (iconCount.getValue() >= 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			request.removeAttribute("liferay-ui:icon-menu:message");
			request.removeAttribute("liferay-ui:icon-menu:showWhenSingleIcon");
			request.removeAttribute("liferay-ui:icon-menu:align");
			request.removeAttribute("liferay-ui:icon-menu:cssClass");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_startPage = null;
				_endPage = null;
				_message = "actions";
				_showWhenSingleIcon = false;
				_align = "right";
				_cssClass = null;
				_bodyContentString = StringPool.BLANK;
			}
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

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	private static final String _START_PAGE =
		"/html/taglib/ui/icon_menu/start.jsp";

	private static final String _END_PAGE = "/html/taglib/ui/icon_menu/end.jsp";

	private String _startPage;
	private String _endPage;
	private String _message = "actions";
	private boolean _showWhenSingleIcon = false;
	private String _align = "right";
	private String _cssClass;
	private String _bodyContentString = StringPool.BLANK;

}