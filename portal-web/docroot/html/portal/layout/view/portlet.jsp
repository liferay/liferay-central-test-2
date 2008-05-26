<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

	String content = null;

	if (themeDisplay.isFacebook() || themeDisplay.isStateExclusive()) {
		content = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
		content = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
%>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, ppid, content) %>

<%
}
else {
	String content = LayoutTemplateLocalServiceUtil.getContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());
%>

	<c:if test="<%= PropsValues.TAGS_COMPILER_ENABLED %>">
		<liferay-portlet:runtime portletName="<%= PortletKeys.TAGS_COMPILER %>" />
	</c:if>

	<%= RuntimePortletUtil.processTemplate(application, request, response, pageContext, content) %>

	<c:if test="<%= PropsValues.PORTLET_CSS_ENABLED && themeDisplay.isSignedIn() %>">
		<liferay-portlet:runtime portletName="<%= PortletKeys.PORTLET_CSS %>" />
	</c:if>

<%
}
%>