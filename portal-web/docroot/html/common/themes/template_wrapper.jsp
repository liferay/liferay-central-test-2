<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%
String path = (String)request.getAttribute("path");

if (_log.isDebugEnabled()) {
	_log.debug("Processing path: " + path);
}
%>

<c:choose>
	<c:when test="<%= Validator.isNull(path) %>">
		<%
		_log.error("Attempted processing invalid path");
		%>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="<%= path %>" />
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.template_wrapper_jsp");
%>