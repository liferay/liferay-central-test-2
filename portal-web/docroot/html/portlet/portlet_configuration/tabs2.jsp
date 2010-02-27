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
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

// Configuration

PortletURL configurationURL = renderResponse.createRenderURL();

configurationURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationURL.setParameter("redirect", redirect);
configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
configurationURL.setParameter("portletResource", portletResource);

// Archived setups

PortletURL archivedSetupsURL = renderResponse.createRenderURL();

archivedSetupsURL.setParameter("struts_action", "/portlet_configuration/edit_archived_setups");
archivedSetupsURL.setParameter("redirect", redirect);
archivedSetupsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
archivedSetupsURL.setParameter("portletResource", portletResource);
%>

<liferay-ui:tabs
	names="current,archived"
	param="tabs2"
	url0="<%= configurationURL.toString() %>"
	url1="<%= archivedSetupsURL.toString() %>"
/>