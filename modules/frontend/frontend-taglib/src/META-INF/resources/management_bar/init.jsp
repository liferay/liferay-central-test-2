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
String checkBoxCssClass = (String)request.getAttribute("liferay-frontend:management-bar:checkBoxCssClass");
String cssClass = (String)request.getAttribute("liferay-frontend:management-bar:cssClass");
String id = (String)request.getAttribute("liferay-frontend:management-bar:id");
boolean includeCheckBox = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar:includeCheckBox"));

String bodyContentString = StringPool.BLANK;

Object bodyContent = request.getAttribute("liferay-frontend:management-bar:bodyContent");

if (bodyContent != null) {
	bodyContentString = bodyContent.toString();
}
%>