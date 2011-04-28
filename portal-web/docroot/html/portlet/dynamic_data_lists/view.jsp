<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "lists");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:tabs
	names="lists"
	portletURL="<%= portletURL %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("lists") %>'>
		<liferay-util:include page="/html/portlet/dynamic_data_lists/view_record_sets.jsp" />
	</c:when>
</c:choose>