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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class IconMenuTag extends BaseBodyTagSupport {

	public int doAfterBody() {
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

			writeBodyContent(pageContext.getOut());

			if ((iconCount != null) && (iconCount.getValue() >= 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			request.removeAttribute("liferay-ui:icon-menu:align");
			request.removeAttribute("liferay-ui:icon-menu:cssClass");
			request.removeAttribute("liferay-ui:icon-menu:icon");
			request.removeAttribute("liferay-ui:icon-menu:id");
			request.removeAttribute("liferay-ui:icon-menu:message");
			request.removeAttribute("liferay-ui:icon-menu:showArrow");
			request.removeAttribute("liferay-ui:icon-menu:showExpanded");
			request.removeAttribute("liferay-ui:icon-menu:showWhenSingleIcon");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_align = "right";
				_cssClass = null;
				_endPage = null;
				_icon = null;
				_id = null;
				_message = "actions";
				_showArrow = true;
				_showExpanded = false;
				_showWhenSingleIcon = false;
				_startPage = null;
			}
		}
	}

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String icon = _icon;

		if (icon == null) {
			icon =  themeDisplay.getPathThemeImages() + "/common/tool.png";
		}

		String id = _id;

		if (Validator.isNull(id)) {
			String randomKey = PortalUtil.generateRandomKey(
				request, IconMenuTag.class.getName());

			id = randomKey + StringPool.UNDERLINE;
		}

		request.setAttribute("liferay-ui:icon-menu:align", _align);
		request.setAttribute("liferay-ui:icon-menu:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:icon-menu:icon-count", new IntegerWrapper());
		request.setAttribute("liferay-ui:icon-menu:icon", icon);
		request.setAttribute("liferay-ui:icon-menu:id", id);
		request.setAttribute("liferay-ui:icon-menu:message", _message);
		request.setAttribute(
			"liferay-ui:icon-menu:showArrow",String.valueOf(_showArrow));
		request.setAttribute(
			"liferay-ui:icon-menu:showExpanded",String.valueOf(_showExpanded));
		request.setAttribute(
			"liferay-ui:icon-menu:showWhenSingleIcon",
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

	public void setAlign(String align) {
		_align = align;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setShowArrow(boolean showArrow) {
		_showArrow = showArrow;
	}

	public void setShowExpanded(boolean showExpanded) {
		_showExpanded = showExpanded;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	private static final String _END_PAGE = "/html/taglib/ui/icon_menu/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/icon_menu/start.jsp";

	private String _align = "right";
	private String _cssClass;
	private String _endPage;
	private String _icon;
	private String _id;
	private String _message = "actions";
	private boolean _showArrow = true;
	private boolean _showExpanded;
	private boolean _showWhenSingleIcon;
	private String _startPage;

}