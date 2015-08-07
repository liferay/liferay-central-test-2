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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "manage");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("toolbarItem", toolbarItem);
%>

<liferay-ui:tabs
	names="manage,install"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<%@ include file="/manage.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<%@ include file="/install_apps.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>