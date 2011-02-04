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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String casLoginURL = ParamUtil.getString(request, "casLoginURL");
String casLogoutURL = ParamUtil.getString(request, "casLogoutURL");
String casServerURL = ParamUtil.getString(request, "casServerURL");
String casServiceURL = ParamUtil.getString(request, "casServiceURL");
%>

<table class="lfr-table">
<tr>
	<td>
		<b><liferay-ui:message key="login-url" />:</b>
	</td>
	<td>
		<liferay-ui:message key="<%= _testURL(casLoginURL) %>" />
	</td>
</tr>
<tr>
	<td>
		<b><liferay-ui:message key="logout-url" />:</b>
	</td>
	<td>
		<liferay-ui:message key="<%= _testURL(casLogoutURL) %>" />
	</td>
</tr>

<c:if test="<%= Validator.isNotNull(casServerURL) %>">
	<tr>
		<td>
			<b><liferay-ui:message key="server-url" />:</b>
		</td>
		<td>
			<liferay-ui:message key="<%= _testURL(casServerURL) %>" />
		</td>
	</tr>
</c:if>

<c:if test="<%= Validator.isNotNull(casServiceURL) %>">
	<tr>
		<td>
			<b><liferay-ui:message key="service-url" />:</b>
		</td>
		<td>
			<liferay-ui:message key="<%= _testURL(casServiceURL) %>" />
		</td>
	</tr>
</c:if>

<%!
private String _testURL(String url) {
	try {
		URL urlObj = new URL(url);

		HttpURLConnection httpURLConnection = (HttpURLConnection)urlObj.openConnection();

		httpURLConnection.setConnectTimeout(3000);

		httpURLConnection.getResponseCode();
	}
	catch (MalformedURLException e) {
		return "fail";
	}
	catch (Exception e) {
		String message = GetterUtil.getString(e.getMessage());

		if (message.contains("PKIX")) {
			return "ssl-error";
		}
		else {
			return "unreachable";
		}
	}

	return "pass";
}
%>