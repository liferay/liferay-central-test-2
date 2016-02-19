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
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "exportImport");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("tabs2", "export");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="export"
			selected='<%= tabs2.equals("export") %>'
		/>

		<%
		portletURL.setParameter("tabs2", "import");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="import"
			selected='<%= tabs2.equals("import") %>'
		/>
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280" id="<portlet:namespace />exportImportPortletContainer">
	<liferay-util:include page="/export_import_error.jsp" servletContext="<%= application %>" />

	<c:choose>
		<c:when test='<%= tabs2.equals("export") %>'>
			<liferay-util:include page="/export_portlet.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("import") %>'>
			<liferay-util:include page="/import_portlet.jsp" servletContext="<%= application %>" />
		</c:when>
	</c:choose>
</div>