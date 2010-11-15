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
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.jsp.JspWriter;

/**
 * @author Scott Lee
 * @author Shuyang Zhou
 */
public class IconHelpTag extends IconTag {

	protected String getPage() {
		return null;
	}

	protected int processEndTag() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)pageContext.getAttribute(
			"themeDisplay");

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<span class=\"taglib-icon-help\"><img alt=\"\" ");
		jspWriter.write("onMouseOver=\"Liferay.Portal.ToolTip.show(this, '");
		jspWriter.write(UnicodeLanguageUtil.get(pageContext, getMessage()));
		jspWriter.write("');\" src=\"");
		jspWriter.write(themeDisplay.getPathThemeImages());
		jspWriter.write("/portlet/help.png\" />");
		jspWriter.write("<span class=\"aui-helper-hidden-accessible\">");
		jspWriter.write(LanguageUtil.get(pageContext, getMessage()));
		jspWriter.write("</span></span>");

		return EVAL_PAGE;
	}

}