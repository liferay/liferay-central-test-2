<%
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

User user2 = (User)objArray[0];
Role role = (Role)objArray[1];
String redirect = (String)objArray[2];
%>

<liferay-ui:icon-menu>
	<c:if test="<%= showUnlinkUserRoleIcon %>">
		<portlet:actionURL var="portletURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="<%= Constants.CMD %>" value="deleteRole" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon image="unlink" message="remove" url="<%= portletURL %>" />
	</c:if>
</liferay-ui:icon-menu>