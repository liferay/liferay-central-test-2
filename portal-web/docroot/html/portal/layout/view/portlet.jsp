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

<%
if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) {
	String ppid = ParamUtil.getString(request, "p_p_id");

	String velocityTemplateId = null;
	String velocityTemplateContent = null;

	if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "exclusive";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, ppid, velocityTemplateId, velocityTemplateContent) %>

<%
}
else {
	LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());

	String themeId = theme.getThemeId();

	if (!layoutTemplate.getServletContextName().equals(theme.getServletContextName())) {
		themeId = "null";
	}

	String velocityTemplateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + layoutTypePortlet.getLayoutTemplateId();
	String velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());
%>

	<c:if test="<%= PropsValues.TAGS_COMPILER_ENABLED %>">
		<liferay-portlet:runtime portletName="<%= PortletKeys.TAGS_COMPILER %>" />
	</c:if>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, velocityTemplateId, velocityTemplateContent) %>

<%
}
%>

<c:if test="<%= !themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() && !themeDisplay.isWidget() %>">

	<%
	for (String portletId : PropsValues.LAYOUT_STATIC_PORTLETS_ALL) {
		if (PortletLocalServiceUtil.hasPortlet(company.getCompanyId(), portletId)) {
	%>

			<liferay-portlet:runtime portletName="<%= portletId %>" />

	<%
		}
	}
	%>

</c:if>

<%@ include file="/html/portal/layout/view/common.jspf" %>