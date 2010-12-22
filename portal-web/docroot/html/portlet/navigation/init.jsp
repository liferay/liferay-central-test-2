<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String bulletStyle = PrefsParamUtil.getString(preferences, renderRequest, "bullet-style", "dots");
String displayStyle = PrefsParamUtil.getString(preferences, renderRequest, "display-style", "relative-with-breadcrumb");

String headerType = PrefsParamUtil.getString(preferences, renderRequest, "header-type", "root-layout");

String rootLayoutType = PrefsParamUtil.getString(preferences, renderRequest, "root-layout-type", "absolute");
int rootLayoutLevel = PrefsParamUtil.getInteger(preferences, renderRequest, "root-layout-level", 1);

String includedLayouts = PrefsParamUtil.getString(preferences, renderRequest, "included-layouts", "current");

boolean nestedChildren = PrefsParamUtil.getBoolean(preferences, renderRequest, "nested-children", true);
%>