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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.aui.ScriptTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
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
			return processEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_align = "right";
				_cssClass = StringPool.BLANK;
				_endPage = null;
				_icon = StringPool.BLANK;
				_id = StringPool.BLANK;
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

		if (Validator.isNull(_icon)) {
			_icon = themeDisplay.getPathThemeImages() + "/common/tool.png";
		}

		if (Validator.isNull(_id)) {
			String randomKey = PortalUtil.generateRandomKey(
				request, IconMenuTag.class.getName());

			_id = randomKey + StringPool.UNDERLINE;
		}

		request.setAttribute(
			"liferay-ui:icon-menu:icon-count", new IntegerWrapper());
		request.setAttribute(
			"liferay-ui:icon-menu:showWhenSingleIcon",
			String.valueOf(_showWhenSingleIcon));

		return EVAL_BODY_BUFFERED;
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

	protected int processEndTag() throws Exception {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-menu:icon-count");

		request.removeAttribute("liferay-ui:icon-menu:icon-count");

		Boolean singleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		request.removeAttribute("liferay-ui:icon-menu:single-icon");

		JspWriter jspWriter = pageContext.getOut();

		if ((iconCount != null) && (iconCount.getValue() >= 1) &&
			((singleIcon == null) || _showWhenSingleIcon)) {

			if (Validator.isNull(_startPage)) {
				if (_showExpanded) {
					jspWriter.write("<div class=\"lfr-component ");
					jspWriter.write("lfr-menu-list lfr-menu-expanded ");
					jspWriter.write(_align);
					jspWriter.write(" ");
					jspWriter.write(_cssClass);
					jspWriter.write("\" id=\"");
					jspWriter.write(_id);
					jspWriter.write("menu\">");
				}
				else {
					jspWriter.write("<ul class='lfr-component lfr-actions ");
					jspWriter.write(_align);
					jspWriter.write(" ");
					jspWriter.write(_cssClass);
					jspWriter.write(" ");

					if (_showArrow) {
						jspWriter.write("show-arrow");
					}

					jspWriter.write("'>");
					jspWriter.write("<li class=\"lfr-trigger\"><strong>");
					jspWriter.write("<a class=\"nobr\" href=\"javascript:;\">");

					if (Validator.isNotNull(_icon)) {
						jspWriter.write("<img alt=\"\" src=\"");
						jspWriter.write(_icon);
						jspWriter.write("\" />");
					}

					jspWriter.write(LanguageUtil.get(pageContext, _message));
					jspWriter.write("</a></strong>");
				}

				jspWriter.write("<ul>");
			}
			else {
				PortalIncludeUtil.include(pageContext, _startPage);
			}
		}

		writeBodyContent(jspWriter);

		if ((iconCount != null) && (iconCount.getValue() >= 1) &&
			((singleIcon == null) || _showWhenSingleIcon)) {

			if (Validator.isNull(_endPage)) {
				jspWriter.write("</ul>");

				if (_showExpanded) {
					jspWriter.write("</div>");
					ScriptTag.doTag(
						null, "liferay-menu",
						"Liferay.Menu.handleFocus('#" + _id + "menu');",
						pageContext);
				}
				else {
					jspWriter.write("</li></ul>");
				}
			}
			else {
				PortalIncludeUtil.include(pageContext, _endPage);
			}
		}

		request.removeAttribute("liferay-ui:icon-menu:showWhenSingleIcon");

		return EVAL_PAGE;
	}

	private String _align = "right";
	private String _cssClass = StringPool.BLANK;
	private String _endPage;
	private String _icon = StringPool.BLANK;
	private String _id = StringPool.BLANK;
	private String _message = "actions";
	private boolean _showArrow = true;
	private boolean _showExpanded;
	private boolean _showWhenSingleIcon;
	private String _startPage;

}