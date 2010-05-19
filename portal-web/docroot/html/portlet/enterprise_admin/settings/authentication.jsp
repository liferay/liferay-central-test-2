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
String companySecurityAuthType = ParamUtil.getString(request, "settings--" + PropsKeys.COMPANY_SECURITY_AUTH_TYPE + "--", company.getAuthType());
boolean companySecurityAutoLogin = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_AUTO_LOGIN + "--", company.isAutoLogin());
boolean companySecuritySendPassword = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD + "--", company.isSendPassword());
boolean companySecuritySendPasswordResetLink = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK + "--", company.isSendPasswordResetLink());
boolean companySecurityStrangers = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS + "--", company.isStrangers());
boolean companySecurityStrangersWithMx = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX + "--", company.isStrangersWithMx());
boolean companySecurityStrangersVerify = ParamUtil.getBoolean(request, "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY + "--", company.isStrangersVerify());

boolean casAuthEnabled = ParamUtil.getBoolean(request, "settings--" + PropsKeys.CAS_AUTH_ENABLED + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED, PropsValues.CAS_AUTH_ENABLED));
boolean casImportFromLdap = ParamUtil.getBoolean(request, "settings--" + PropsKeys.CAS_IMPORT_FROM_LDAP + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_IMPORT_FROM_LDAP, PropsValues.CAS_IMPORT_FROM_LDAP));
String casLoginUrl = ParamUtil.getString(request, "settings--" + PropsKeys.CAS_LOGIN_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL));
String casLogoutUrl = ParamUtil.getString(request, "settings--" + PropsKeys.CAS_LOGOUT_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGOUT_URL, PropsValues.CAS_LOGOUT_URL));
String casServerName = ParamUtil.getString(request, "settings--" + PropsKeys.CAS_SERVER_NAME + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_NAME, PropsValues.CAS_SERVER_NAME));
String casServerUrl = ParamUtil.getString(request, "settings--" + PropsKeys.CAS_SERVER_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_URL, PropsValues.CAS_SERVER_URL));
String casServiceUrl = ParamUtil.getString(request, "settings--" + PropsKeys.CAS_SERVICE_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVICE_URL, PropsValues.CAS_SERVICE_URL));

boolean ntlmAuthEnabled = ParamUtil.getBoolean(request, "settings--" + PropsKeys.NTLM_AUTH_ENABLED + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED));
String ntlmDomainController = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER, PropsValues.NTLM_DOMAIN_CONTROLLER));
String ntlmDomainControllerName = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME, PropsValues.NTLM_DOMAIN_CONTROLLER_NAME));
String ntlmDomain = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_DOMAIN + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN, PropsValues.NTLM_DOMAIN));
String ntlmServiceAccount = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_SERVICE_ACCOUNT + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_ACCOUNT, PropsValues.NTLM_SERVICE_ACCOUNT));
String ntlmServicePassword = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_SERVICE_PASSWORD + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_PASSWORD, PropsValues.NTLM_SERVICE_PASSWORD));

boolean openIdAuthEnabled = ParamUtil.getBoolean(request, "settings--" + PropsKeys.OPEN_ID_AUTH_ENABLED + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_ID_AUTH_ENABLED, PropsValues.OPEN_ID_AUTH_ENABLED));

boolean openSsoAuthEnabled = ParamUtil.getBoolean(request, "settings--" + PropsKeys.OPEN_SSO_AUTH_ENABLED + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_SSO_AUTH_ENABLED, PropsValues.OPEN_SSO_AUTH_ENABLED));
String openSsoLoginUrl = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_LOGIN_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGIN_URL, PropsValues.OPEN_SSO_LOGIN_URL));
String openSsoLogoutUrl = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_LOGOUT_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGOUT_URL, PropsValues.OPEN_SSO_LOGOUT_URL));
String openSsoServiceUrl = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_SERVICE_URL + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SERVICE_URL, PropsValues.OPEN_SSO_SERVICE_URL));
String openSsoScreenNameAttr = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR, PropsValues.OPEN_SSO_SCREEN_NAME_ATTR));
String openSsoEmailAddressAttr = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR, PropsValues.OPEN_SSO_EMAIL_ADDRESS_ATTR));
String openSsoFirstNameAttr = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_FIRST_NAME_ATTR + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_FIRST_NAME_ATTR, PropsValues.OPEN_SSO_FIRST_NAME_ATTR));
String openSsoLastNameAttr = ParamUtil.getString(request, "settings--" + PropsKeys.OPEN_SSO_LAST_NAME_ATTR + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LAST_NAME_ATTR, PropsValues.OPEN_SSO_LAST_NAME_ATTR));

boolean siteminderAuthEnabled = ParamUtil.getBoolean(request, "settings--" + PropsKeys.SITEMINDER_AUTH_ENABLED + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_AUTH_ENABLED, PropsValues.SITEMINDER_AUTH_ENABLED));
boolean siteminderImportFromLdap = ParamUtil.getBoolean(request, "settings--" + PropsKeys.SITEMINDER_IMPORT_FROM_LDAP + "--", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_IMPORT_FROM_LDAP, PropsValues.SITEMINDER_IMPORT_FROM_LDAP));
String siteminderUserHeader = ParamUtil.getString(request, "settings--" + PropsKeys.SITEMINDER_USER_HEADER + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.SITEMINDER_USER_HEADER, PropsValues.SITEMINDER_USER_HEADER));
%>

<h3><liferay-ui:message key="authentication" /></h3>

<liferay-ui:tabs
	names="general,ldap,cas,ntlm,open-id,open-sso,siteminder"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:select label="how-do-users-authenticate" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_AUTH_TYPE + "--" %>'>
				<aui:option label="by-email-address" selected="<%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_EA) %>" value="<%= CompanyConstants.AUTH_TYPE_EA %>" />
				<aui:option label="by-screen-name" selected="<%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_SN) %>" value="<%= CompanyConstants.AUTH_TYPE_SN %>" />
				<aui:option label="by-user-id" selected="<%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_ID) %>" value="<%= CompanyConstants.AUTH_TYPE_ID %>" />
			</aui:select>

			<aui:input inlineLabel="left" label="allow-users-to-automatically-login" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_AUTO_LOGIN + "--" %>' type="checkbox" value="<%= companySecurityAutoLogin %>" />

			<aui:input inlineLabel="left" helpMessage="allow-users-to-request-forgotten-passwords-help" label="allow-users-to-request-forgotten-passwords" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD + "--" %>' type="checkbox" value="<%= companySecuritySendPassword %>" />

			<aui:input inlineLabel="left" helpMessage="allow-users-to-request-password-reset-links-help" label="allow-users-to-request-password-reset-links" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK + "--" %>' type="checkbox" value="<%= companySecuritySendPasswordResetLink %>" />

			<aui:input inlineLabel="left" label="allow-strangers-to-create-accounts" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS + "--" %>' type="checkbox" value="<%= companySecurityStrangers %>" />

			<aui:input inlineLabel="left" label="allow-strangers-to-create-accounts-with-a-company-email-address" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX + "--" %>' type="checkbox" value="<%= companySecurityStrangersWithMx %>" />

			<aui:input inlineLabel="left" label="require-strangers-to-verify-their-email-address" name='<%= "settings--" + PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY + "--" %>' type="checkbox" value="<%= companySecurityStrangersVerify %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<%@ include file="/html/portlet/enterprise_admin/settings/authentication_ldap.jspf" %>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.CAS_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= casAuthEnabled %>" />

			<aui:input inlineLabel="left" helpMessage="import-cas-users-from-ldap-help" label="import-cas-users-from-ldap" name='<%= "settings--" + PropsKeys.CAS_IMPORT_FROM_LDAP + "--" %>' type="checkbox" value="<%= casImportFromLdap %>" />

			<aui:input cssClass="lfr-input-text-container" label="login-url" name='<%= "settings--" + PropsKeys.CAS_LOGIN_URL + "--" %>' type="text" value="<%= casLoginUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="logout-url" name='<%= "settings--" + PropsKeys.CAS_LOGOUT_URL + "--" %>' type="text" value="<%= casLogoutUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="server-name" name='<%= "settings--" + PropsKeys.CAS_SERVER_NAME + "--" %>' type="text" value="<%= casServerName %>" />

			<aui:input cssClass="lfr-input-text-container" label="server-url" name='<%= "settings--" + PropsKeys.CAS_SERVER_URL + "--" %>' type="text" value="<%= casServerUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="service-url" name='<%= "settings--" + PropsKeys.CAS_SERVICE_URL + "--" %>' type="text" value="<%= casServiceUrl %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.NTLM_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= ntlmAuthEnabled %>" />

			<aui:input cssClass="lfr-input-text-container" label="domain-controller" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER + "--" %>' type="text" value="<%= ntlmDomainController %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="domain-controller-name-help" label="domain-controller-name" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME + "--" %>' type="text" value="<%= ntlmDomainControllerName %>" />

			<aui:input cssClass="lfr-input-text-container" label="domain" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN + "--" %>' type="text" value="<%= ntlmDomain %>" />

			<aui:input cssClass="lfr-input-text-container" label="service-account" name='<%= "settings--" + PropsKeys.NTLM_SERVICE_ACCOUNT + "--" %>' type="text" value="<%= ntlmServiceAccount %>" />

			<aui:input cssClass="lfr-input-text-container" label="service-password" name='<%= "settings--" + PropsKeys.NTLM_SERVICE_PASSWORD + "--" %>' type="text" value="<%= ntlmServicePassword %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.OPEN_ID_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= openIdAuthEnabled %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.OPEN_SSO_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= openSsoAuthEnabled %>" />

			<aui:input cssClass="lfr-input-text-container" label="login-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_LOGIN_URL + "--" %>' type="text" value="<%= openSsoLoginUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="logout-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_LOGOUT_URL + "--" %>' type="text" value="<%= openSsoLogoutUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="service-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_SERVICE_URL + "--" %>' type="text" value="<%= openSsoServiceUrl %>" />

			<aui:input cssClass="lfr-input-text-container" label="screen-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR + "--" %>' type="text" value="<%= openSsoScreenNameAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="email-address-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR + "--" %>' type="text" value="<%= openSsoEmailAddressAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="first-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_FIRST_NAME_ATTR + "--" %>' type="text" value="<%= openSsoFirstNameAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="last-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_LAST_NAME_ATTR + "--" %>' type="text" value="<%= openSsoLastNameAttr %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.SITEMINDER_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= siteminderAuthEnabled %>" />

			<aui:input inlineLabel="left" helpMessage="import-siteminder-users-from-ldap-help" label="import-siteminder-users-from-ldap" name='<%= "settings--" + PropsKeys.SITEMINDER_IMPORT_FROM_LDAP + "--" %>' type="checkbox" value="<%= siteminderImportFromLdap %>" />

			<aui:input cssClass="lfr-input-text-container" label="user-header" name='<%= "settings--" + PropsKeys.SITEMINDER_USER_HEADER + "--" %>' type="text" value="<%= siteminderUserHeader %>" />
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>