<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">
	<div id="content-wrapper">
		<table class="lfr-panel-page">
		<tr>
			<td class="lfr-top panel-page-menu" width="200">
				<liferay-portlet:runtime portletName="87" />
			</td>
			<td class="lfr-top panel-page-content <%= (!layoutTypePortlet.hasStateMax()) ? "panel-page-frontpage" : "panel-page-application" %>">
				<span id="mainContent"></span>
</c:if>

<%
if (themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
	String ppid = ParamUtil.getString(request, "p_p_id");

	String velocityTemplateId = null;
	String velocityTemplateContent = null;

	if (themeDisplay.isStateExclusive()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "exclusive";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp()) {
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
	String description = layout.getTypeSettingsProperties().getProperty("description");

	if (Validator.isNull(description)) {
		description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
	}
%>

	<h2>
		<%= layout.getName(locale) %>
	</h2>

	<div class="portlet-msg-info">
		<%= description %>
	</div>

<%
}
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">
			</td>
		</tr>
		</table>
	</div>
</c:if>

<%@ include file="/html/portal/layout/view/common.jspf" %>