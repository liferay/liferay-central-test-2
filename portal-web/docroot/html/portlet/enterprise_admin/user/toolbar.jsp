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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
String backURL = ParamUtil.getString(request, "backURL");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewUsersURL">
		<portlet:param name="struts_action" value="/enterprise_admin/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewUsersURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) %>">

		<%
		String organizationId = ParamUtil.getString(request, "organizationId");
		String roleId = ParamUtil.getString(request, "roleId");
		String userGroupId = ParamUtil.getString(request, "userGroupId");
		%>

		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="organizationId" value="<%= organizationId %>" />
			<portlet:param name="roleId" value="<%= roleId %>" />
			<portlet:param name="userGroupId" value="<%= userGroupId %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>"><a href="<%= addUserURL %>"><liferay-ui:message key="add" /></a></span>
	</c:if>

	<c:if test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">
		<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="expandoURL" portletName="<%= PortletKeys.EXPANDO %>">
			<portlet:param name="struts_action" value="/expando/view_attributes" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="modelResource" value="<%= User.class.getName() %>" />
		</liferay-portlet:renderURL>

		<span class="lfr-toolbar-button custom-attributes-button"><a href="<%= expandoURL %>"><liferay-ui:message key="custom-fields" /></a></span>

		<span class="lfr-toolbar-button export-button"><a href="javascript:submitForm(document.hrefFm, '<%= themeDisplay.getPathMain() %>/enterprise_admin/export_users');"><liferay-ui:message key="export" /></a></span>
	</c:if>

	<c:if test="<%= Validator.isNotNull(backURL) %>">
		<span class="lfr-toolbar-button back-button">
			<a href="<%= HtmlUtil.escape(PortalUtil.escapeRedirect(backURL)) %>">&laquo; <liferay-ui:message key="back" /></a>
		</span>
	</c:if>
</div>