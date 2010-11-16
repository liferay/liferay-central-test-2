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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconListTag extends BaseBodyTagSupport implements BodyTag {

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

			JspWriter jspWriter = pageContext.getOut();

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				if (Validator.isNull(_startPage)) {
					jspWriter.write(
						"<ul class=\"lfr-component taglib-icon-list\">");
				}
				else {
					PortalIncludeUtil.include(pageContext, _startPage);
				}
			}

			writeBodyContent(jspWriter);

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				if (Validator.isNull(_endPage)) {
					jspWriter.write("</ul>");
				}
				else {
					PortalIncludeUtil.include(pageContext, _endPage);
				}
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

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private String _endPage;
	private boolean _showWhenSingleIcon = false;
	private String _startPage;

}