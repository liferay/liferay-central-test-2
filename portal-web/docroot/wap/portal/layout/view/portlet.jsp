<%
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
%>

<%@ include file="/html/portal/init.jsp" %>

<liferay-portlet:runtime portletName="<%= PortletKeys.TAGS_COMPILER %>" />

<%
boolean layoutMaximized = layoutTypePortlet.hasStateMax();

if (!layoutMaximized) {
	LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());

	String themeId = theme.getThemeId();

	if ((layoutTemplate != null) && (!layoutTemplate.getServletContextName().equals(theme.getServletContextName()))) {
		themeId = "null";
	}

	String velocityTemplateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + layoutTypePortlet.getLayoutTemplateId();
	String velocityTemplateContent = LayoutTemplateLocalServiceUtil.getWapContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, velocityTemplateId, velocityTemplateContent) %>

<%
}
else {
	String velocityTemplateId = null;
	String velocityTemplateContent = null;

	if (themeDisplay.isStateExclusive()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "exclusive";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getWapContent("exclusive", true, theme.getThemeId());
	}
	else {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getWapContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, StringUtil.split(layoutTypePortlet.getStateMax())[0], velocityTemplateId, velocityTemplateContent) %>

<%
}
%>