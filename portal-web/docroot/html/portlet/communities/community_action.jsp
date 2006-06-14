<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String tabs1 = (String)request.getAttribute("tabs1");

Group group = (Group)row.getObject();
%>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.UPDATE) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_community" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.PERMISSIONS) %>">
	<liferay-security:permissionsURL
		modelResource="<%= Group.class.getName() %>"
		modelResourceDescription="<%= group.getName() %>"
		resourcePrimKey="<%= group.getPrimaryKey().toString() %>"
		var="portletURL"
	/>

	<liferay-ui:icon image="permissions" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.DELEGATE) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_user_permissions" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="delegate" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_LAYOUTS) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_pages" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="pages" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_USERS) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_community_assignments" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="assign" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.DELETE) %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_community" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= portletURL %>" />
</c:if>

<c:if test="<%= Group.COMMUNITY_OPEN.equals(group.getType()) && tabs1.equals("available") %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_community_assignments" />
		<portlet:param name="<%= Constants.CMD %>" value="community_users" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
		<portlet:param name="addUserIds" value="<%= user.getUserId() %>" />		
	</portlet:actionURL>

	<liferay-ui:icon image="join" url="<%= portletURL %>" />
</c:if>

<c:if test="<%= Group.COMMUNITY_OPEN.equals(group.getType()) && tabs1.equals("current") %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
		<portlet:param name="struts_action" value="/communities/edit_community_assignments" />
		<portlet:param name="<%= Constants.CMD %>" value="community_users" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= group.getGroupId() %>" />
		<portlet:param name="removeUserIds" value="<%= user.getUserId() %>" />		
	</portlet:actionURL>

	<liferay-ui:icon image="leave" url="<%= portletURL %>" />
</c:if>