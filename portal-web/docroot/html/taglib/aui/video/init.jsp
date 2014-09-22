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

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:cssClass"));
java.lang.String flashPlayerVersion = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:flashPlayerVersion"), "9,0,0,0");
java.lang.Number height = GetterUtil.getNumber(String.valueOf(request.getAttribute("aui:video:height")), null);
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:id"));
java.lang.String ogvURL = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:ogvURL"));
java.lang.String poster = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:poster"));
java.lang.String swfURL = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:swfURL"));
java.lang.String url = GetterUtil.getString((java.lang.String)request.getAttribute("aui:video:url"));
java.lang.Number width = GetterUtil.getNumber(String.valueOf(request.getAttribute("aui:video:width")), null);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:video:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:video:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/video/init-ext.jspf" %>