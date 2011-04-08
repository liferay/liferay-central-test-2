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

<%@ include file="/html/common/init.jsp" %>

<%
String referer = null;

String refererParam = PortalUtil.escapeRedirect(request.getParameter(WebKeys.REFERER));
String refererRequest = (String)request.getAttribute(WebKeys.REFERER);
String refererSession = (String)session.getAttribute(WebKeys.REFERER);

if (Validator.isNotNull(refererParam)) {
	if (themeDisplay.isAddSessionIdToURL()) {
		refererParam = PortalUtil.getURLWithSessionId(refererParam, themeDisplay.getSessionId());
	}

	referer = refererParam;
}
else if (Validator.isNotNull(refererRequest)) {
	if (themeDisplay.isAddSessionIdToURL()) {
		refererRequest = PortalUtil.getURLWithSessionId(refererRequest, themeDisplay.getSessionId());
	}

	referer = refererRequest;
}
else if (Validator.isNotNull(refererSession)) {
	if (themeDisplay.isAddSessionIdToURL()) {
		refererSession = PortalUtil.getURLWithSessionId(refererSession, themeDisplay.getSessionId());
	}

	referer = refererSession;
}
else {
	referer = PortalUtil.getPathMain();
}
%>