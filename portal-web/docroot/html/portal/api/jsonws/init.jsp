<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.javadoc.JavadocManagerUtil" %><%@
page import="com.liferay.portal.kernel.javadoc.JavadocMethod" %><%@
page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping" %><%@
page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil" %><%@
page import="com.liferay.portal.kernel.util.ContextPathUtil" %><%@
page import="com.liferay.portal.kernel.util.MethodParameter" %>

<%@ page import="java.io.File" %>

<%@ page import="java.lang.reflect.Method" %>

<%
String contextPath = request.getParameter("contextPath");

if (contextPath == null) {
	contextPath = ContextPathUtil.getContextPath(application);
}
%>