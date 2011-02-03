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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String facebookConnectRedirectURL =	FacebookConnectUtil.getRedirectURL(themeDisplay.getCompanyId());

facebookConnectRedirectURL = HttpUtil.addParameter(facebookConnectRedirectURL, "redirect", redirect);
%>

<aui:form action="<%= FacebookConnectUtil.getAuthURL(themeDisplay.getCompanyId()) %>" name="fm" useNamespace="false">
	<aui:input name="client_id" type="hidden" value="<%= FacebookConnectUtil.getAppId(themeDisplay.getCompanyId()) %>" />
	<aui:input name="redirect_uri" type="hidden" value="<%= facebookConnectRedirectURL %>" />
	<aui:input name="scope" type="hidden" value="email" />

	<div class="facebook-login">
		<aui:button cssClass="button" type="submit" value="log-in-through-facebook" />
	</div>
</aui:form>