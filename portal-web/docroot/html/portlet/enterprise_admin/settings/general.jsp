<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<fieldset class="aui-block-labels aui-form-column">
	<liferay-ui:error exception="<%= AccountNameException.class %>" message="please-enter-a-valid-name" />

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />name"><liferay-ui:message key="name" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="name" />
	</div>

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />mailDomain"><liferay-ui:message key="mail-domain" /></label>

		<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company %>" field="mx" disabled="<%= !PropsValues.MAIL_MX_UPDATE %>" />
	</div>
</fieldset>

<fieldset class="aui-block-labels aui-form-column">
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />virtualHost"><liferay-ui:message key="virtual-host" /></label>

		<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company %>" field="virtualHost" />
	</div>
</fieldset>

<h3><liferay-ui:message key="navigation" /></h3>

<fieldset class="aui-block-labels aui-form-column">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />homeURL"><liferay-ui:message key="home-url" /><liferay-ui:icon-help message="home-url-help" /></label>

		<liferay-ui:input-field model="<%= Company.class %>" bean="<%= company %>" field="homeURL" />
	</div>
</fieldset>

<fieldset class="aui-block-labels aui-form-column">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_LANDING_PAGE_PATH %>)"><liferay-ui:message key="default-landing-page" /><liferay-ui:icon-help message="default-landing-page-help" /></label>

		<input name="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_LANDING_PAGE_PATH %>)" type="text" value="<%= defaultLandingPagePath %>" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_LOGOUT_PAGE_PATH %>)"><liferay-ui:message key="default-logout-page" /><liferay-ui:icon-help message="default-logout-page-help" /></label>

		<input name="<portlet:namespace />settings(<%= PropsKeys.DEFAULT_LOGOUT_PAGE_PATH %>)" type="text" value="<%= defaultLogoutPagePath %>" />
	</div>
</fieldset>

<h3><liferay-ui:message key="additional-information" /></h3>

<fieldset class="aui-block-labels aui-form-column">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />legalName"><liferay-ui:message key="legal-name" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="legalName" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />legalId"><liferay-ui:message key="legal-id" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="legalId" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />legalType"><liferay-ui:message key="legal-type" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="legalType" />
	</div>
</fieldset>

<fieldset class="aui-block-labels aui-form-column">
	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />sicCode"><liferay-ui:message key="sic-code" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="sicCode" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />tickerSymbol"><liferay-ui:message key="ticker-symbol" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="tickerSymbol" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />indsutry"><liferay-ui:message key="industry" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="industry" />
	</div>

	<div class="aui-ctrl-holder">
		<label for="<portlet:namespace />type"><liferay-ui:message key="type" /></label>

		<liferay-ui:input-field model="<%= Account.class %>" bean="<%= account %>" field="type" />
	</div>
</fieldset>