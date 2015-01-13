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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.xsl.content.web.configuration.XSLContentConfiguration" %><%@
page import="com.liferay.xsl.content.web.util.XSLContentUtil" %>

<%@ page import="java.net.URL" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
String xmlUrl = portletPreferences.getValue("xmlUrl", "@portal_url@" + request.getContextPath() + XSLContentUtil.DEFAULT_XML_URL);
String xslUrl = portletPreferences.getValue("xslUrl", "@portal_url@" + request.getContextPath() + XSLContentUtil.DEFAULT_XSL_URL);
%>

<%@ include file="/init-ext.jsp" %>