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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class IconListTag extends BaseBodyTagSupport {

	public int doAfterBody() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-list:icon-count");

		Boolean singleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-list:single-icon");

		if ((iconCount != null) && (iconCount.getValue() == 1) &&
			(singleIcon == null)) {

			bodyContent.clearBody();

			request.setAttribute(
				"liferay-ui:icon-list:single-icon", Boolean.TRUE);

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
				"liferay-ui:icon-list:icon-count");

			request.removeAttribute("liferay-ui:icon-list:icon-count");

			Boolean singleIcon = (Boolean)request.getAttribute(
				"liferay-ui:icon-list:single-icon");

			request.removeAttribute("liferay-ui:icon-list:single-icon");

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				PortalIncludeUtil.include(pageContext, getStartPage());
			}

			writeBodyContent(pageContext.getOut());

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			request.removeAttribute("liferay-ui:icon-list:showWhenSingleIcon");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_endPage = null;
				_showWhenSingleIcon = false;
				_startPage = null;
			}
		}
	}

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:icon-list:icon-count", new IntegerWrapper());
		request.setAttribute(
			"liferay-ui:icon-list:showWhenSingleIcon",
			String.valueOf(_showWhenSingleIcon));

		return EVAL_BODY_BUFFERED;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private static final String _END_PAGE = "/html/taglib/ui/icon_list/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/icon_list/start.jsp";

	private String _endPage;
	private boolean _showWhenSingleIcon = false;
	private String _startPage;

}