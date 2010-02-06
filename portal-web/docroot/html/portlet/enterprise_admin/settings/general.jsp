<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String defaultLandingPagePath = ParamUtil.getString(request, "settings(" + PropsKeys.DEFAULT_LANDING_PAGE_PATH + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_LANDING_PAGE_PATH, PropsValues.DEFAULT_LANDING_PAGE_PATH));
String defaultLogoutPagePath = ParamUtil.getString(request, "settings(" + PropsKeys.DEFAULT_LOGOUT_PAGE_PATH + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_LOGOUT_PAGE_PATH, PropsValues.DEFAULT_LOGOUT_PAGE_PATH));
%>

<liferay-ui:error-marker key="errorSection" value="general" />

<h3><liferay-ui:message key="main-configuration" /></h3>

<aui:model-context bean="<%= account %>" model="<%= Account.class %>" />

<aui:fieldset column="<%= true %>">
	<liferay-ui:error exception="<%= AccountNameException.class %>" message="please-enter-a-valid-name" />

	<aui:input name="name" />

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />

	<aui:input bean="<%= company %>" disabled="<%= !PropsValues.MAIL_MX_UPDATE %>" label="mail-domain" name="mx" model="<%= Company.class %>" />
</aui:fieldset>

<aui:fieldset column="<%= true %>">
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />

	<aui:input bean="<%= company %>" name="virtualHost" model="<%= Company.class %>" />
</aui:fieldset>

<h3><liferay-ui:message key="navigation" /></h3>

<aui:fieldset column="<%= true %>">
	<aui:input bean="<%= company %>" helpMessage="home-url-help" label="home-url" name="homeURL" model="<%= Company.class %>" />
</aui:fieldset>

<aui:fieldset column="<%= true %>">
	<aui:input helpMessage="default-landing-page-help" label="default-landing-page" name='<%= "settings(" + PropsKeys.DEFAULT_LANDING_PAGE_PATH + ")" %>' type="text" value="<%= defaultLandingPagePath %>" />

	<aui:input helpMessage="default-logout-page-help" label="default-logout-page" name='<%= "settings(" + PropsKeys.DEFAULT_LOGOUT_PAGE_PATH + ")" %>' type="text" value="<%= defaultLogoutPagePath %>" />
</aui:fieldset>

<h3><liferay-ui:message key="additional-information" /></h3>

<aui:fieldset column="<%= true %>">
	<aui:input name="legalName" />

	<aui:input name="legalId" />

	<aui:input name="legalType" />
</aui:fieldset>

<aui:fieldset column="<%= true %>">
	<aui:input name="sicCode" />

	<aui:input name="tickerSymbol" />

	<aui:input name="industry" />

	<aui:input name="type" />
</aui:fieldset>