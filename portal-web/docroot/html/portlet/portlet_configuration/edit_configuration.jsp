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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

String path = (String)request.getAttribute(WebKeys.CONFIGURATION_ACTION_PATH);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="setup" />
</liferay-util:include>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layout.getGroupId(), ActionKeys.MANAGE_ARCHIVED_SETUPS) %>">
	<liferay-util:include page="/html/portlet/portlet_configuration/tabs2.jsp" />
</c:if>

<c:if test="<%= (selPortlet != null) && Validator.isNotNull(path) %>">

	<%
	PortletApp selPortletApp = selPortlet.getPortletApp();
	%>

	<c:choose>
		<c:when test="<%= selPortletApp.isWARFile() %>">

			<%
			PortletConfig selPortletConfig = PortletConfigFactory.create(selPortlet, application);
			PortletContextImpl selPortletCtx = (PortletContextImpl)selPortletConfig.getPortletContext();

			RequestDispatcher selRd = selPortletCtx.getServletContext().getRequestDispatcher(path);

			StringServletResponse stringResponse = new StringServletResponse(response);

			selRd.include(request, stringResponse);
			%>

			<%= stringResponse.getString() %>
		</c:when>
		<c:otherwise>
			<liferay-util:include page="<%= path %>" />
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test='<%= themeDisplay.isStatePopUp() && SessionMessages.contains(renderRequest, portletName + ".doConfigure") %>'>
	<aui:script use="node">
		var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

		if (window.parent) {
			window.parent.Liferay.Portlet.refresh(curPortletBoundaryId);
		}
	</aui:script>
</c:if>