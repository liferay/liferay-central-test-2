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
String ppid = ParamUtil.getString(request, "p_p_id");

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	ppid = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");
}

String category = PortalUtil.getControlPanelCategory(ppid);

if (Validator.isNull(category)) {
	category = "content";
}
%>

<c:if test="<%= !themeDisplay.isStateExclusive() && !themeDisplay.isStatePopUp() %>">
	<table class="lfr-panel <%= (!layoutTypePortlet.hasStateMax()) ? "panel-frontpage" : "panel-application" %>" width="100%">
	<tr>
		<td class="panel-menu" valign="top" width="200">
			<liferay-portlet:runtime portletName="87" />
		</td>
		<td class="panel-content" valign="top">
			<table width="100%">
			<tr>
				<td>

					<c:if test='<%= category.equals("content") %>'>

						<%
						Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

						String currentGroupLabel = "current-community";

						if (scopeGroup.isOrganization()) {
							currentGroupLabel = "current-organization";
						}
						else if (scopeGroup.isUser()) {
							currentGroupLabel = "current-user";
						}
						%>

						<h2>
							<liferay-ui:message key="<%= currentGroupLabel %>" />: <%= scopeGroup.getDescriptiveName() %>
						</h2>
					</c:if>
				</td>
				<td align="right">
					<c:if test='<%= !category.equals("server") %>'>
						<h4><liferay-ui:message key="current-portal-instance" />: <%= company.getName() %></h4>
					</c:if>
				</td>
			</tr>
			</table>
</c:if>

<%
if (themeDisplay.isStateExclusive() || themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
	String content = null;

	if (themeDisplay.isStateExclusive()) {
		content = LayoutTemplateLocalServiceUtil.getContent("exclusive", true, theme.getThemeId());
	}
	else if (themeDisplay.isStatePopUp()) {
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
	String description = StringPool.BLANK;

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
</c:if>