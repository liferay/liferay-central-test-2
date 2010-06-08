<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String openSsoLoginUrl = ParamUtil.getString(request, "openSsoLoginUrl");
String openSsoLogoutUrl = ParamUtil.getString(request, "openSsoLogoutUrl");
String openSsoServiceUrl = ParamUtil.getString(request, "openSsoServiceUrl");

List<String> urls = new ArrayList<String>();

urls.add(openSsoLoginUrl);
urls.add(openSsoLogoutUrl);
urls.add(openSsoServiceUrl);

boolean validateUrls = OpenSSOUtil.isValidUrls(urls.toArray(new String[urls.size()]));

if (!validateUrls) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-opensso-server" />

<%
	return;
}

boolean validateService = OpenSSOUtil.isValidService(openSsoServiceUrl);

if (!validateService) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-opensso-services" />

<%
	return;
}

if (Validator.isNull(ParamUtil.getString(request, "openSsoScreenNameAttr")) || Validator.isNull(ParamUtil.getString(request, "openSsoEmailAddressAttr")) || Validator.isNull(ParamUtil.getString(request, "openSsoFirstNameAttr")) || Validator.isNull(ParamUtil.getString(request, "openSsoLastNameAttr"))) {
%>

	<liferay-ui:message key="please-map-each-of-the-user-properties-screen-name,-email-address,-first-name,-and-last-name-to-an-opensso-attribute" />

<%
	return;
}
%>

	<liferay-ui:message key="liferay-has-successfully-connected-to-the-opensso-server" />