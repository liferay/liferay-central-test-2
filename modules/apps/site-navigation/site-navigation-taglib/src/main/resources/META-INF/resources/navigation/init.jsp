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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.theme.NavItem" %><%@
page import="com.liferay.portlet.dynamicdatamapping.DDMTemplate" %>

<%
List<NavItem> branchNavItems = (List)request.getAttribute("liferay-ui:navigation:branchNavItems");
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ui:navigation:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:navigation:displayStyleGroupId"));
String includedLayouts = (String)request.getAttribute("liferay-ui:navigation:includedLayouts");
List<NavItem> navItems = (List)request.getAttribute("liferay-ui:navigation:navItems");
boolean preview = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:preview"));
int rootLayoutLevel = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:navigation:rootLayoutLevel"));
String rootLayoutType = (String)request.getAttribute("liferay-ui:navigation:rootLayoutType");
%>