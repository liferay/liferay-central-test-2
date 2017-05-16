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
String clientId = ParamUtil.getString(request, "clientId");

String redirectURI = ParamUtil.getString(request, "redirectURI");
%>

<div class="container-fluid-1280">
	<div class="button-holder">
		<portlet:actionURL name="/wedeploy_auth/authorize_user" var="allowAuthorizeUserURL">
			<portlet:param name="<%= Constants.CMD %>" value="allow" />
			<portlet:param name="clientId" value="<%= clientId %>" />
			<portlet:param name="redirectURI" value="<%= redirectURI %>" />
		</portlet:actionURL>

		<aui:button cssClass="btn btn-lg btn-primary" href="<%= allowAuthorizeUserURL %>" value='<%= LanguageUtil.get(request, "allow") %>' />

		<portlet:actionURL name="/wedeploy_auth/authorize_user" var="denyAuthorizeUserURL">
			<portlet:param name="<%= Constants.CMD %>" value="deny" />
			<portlet:param name="clientId" value="<%= clientId %>" />
			<portlet:param name="redirectURI" value="<%= redirectURI %>" />
		</portlet:actionURL>

		<aui:button cssClass="btn btn-danger btn-lg" href="<%= denyAuthorizeUserURL %>" value='<%= LanguageUtil.get(request, "deny") %>' />
	</div>
</div>