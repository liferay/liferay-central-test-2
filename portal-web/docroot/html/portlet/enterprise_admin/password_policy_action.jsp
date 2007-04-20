<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

PasswordPolicy passwordPolicy = (PasswordPolicy)row.getObject();
%>

<c:if test="<%= true %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL ">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" url="<%= editURL %>" />
</c:if>

<c:if test="<%= false %>">
	<liferay-security:permissionsURL
		modelResource="<%= PasswordPolicy.class.getName() %>"
		modelResourceDescription="<%= passwordPolicy.getName() %>"
		resourcePrimKey="<%= String.valueOf(passwordPolicy.getPrimaryKey()) %>"
		var="permissionsURL"
	/>

	<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
</c:if>

<c:if test="<%= true %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="assignUsersURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy_assignments" />
		<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="assign" message="assign-members" url="<%= assignUsersURL %>" />
</c:if>

<c:if test="<%= true %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="passwordPolicyId" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />
</c:if>