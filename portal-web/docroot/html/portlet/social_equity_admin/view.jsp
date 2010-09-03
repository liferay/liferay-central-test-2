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

<%@ include file="/html/portlet/social_equity_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "configuration");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/social_equity_admin/view");
portletURL.setParameter("tabs1", tabs1);
%>

<portlet:actionURL var="editEquityActionMappingsURL">
	<portlet:param name="struts_action" value="/social_equity_admin/view" />
</portlet:actionURL>

<liferay-ui:tabs
	names="configuration,ranking"
	url="<%= portletURL.toString() %>"
/>

<aui:form action="<%= editEquityActionMappingsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" value="<%= currentURL %>" type="hidden" />
	<aui:input name="tabs1" value="<%= tabs1 %>" type="hidden" />

	<c:choose>
		<c:when test='<%= tabs1.equals("configuration") %>'>
			<%@ include file="/html/portlet/social_equity_admin/configuration.jspf" %>
		</c:when>
		<c:when test='<%= tabs1.equals("ranking") %>'>
			<%@ include file="/html/portlet/social_equity_admin/ranking.jspf" %>
		</c:when>
	</c:choose>
</aui:form>

<aui:script position="inline">
	function <portlet:namespace />saveServer(cmd) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>