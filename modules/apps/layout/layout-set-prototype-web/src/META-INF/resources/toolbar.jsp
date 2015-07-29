<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE) %>">
			<portlet:renderURL var="viewLayoutSetPrototypesURL" />

			<portlet:renderURL var="addLayoutSetPrototypeURL">
				<portlet:param name="mvcPath" value="/edit_layout_set_prototype.jsp" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addLayoutSetPrototypeURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>