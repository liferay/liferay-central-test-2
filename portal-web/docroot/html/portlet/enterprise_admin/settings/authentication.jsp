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
String companySecurityAuthType = company.getAuthType();
boolean companySecurityAutoLogin = company.isAutoLogin();
boolean companySecuritySendPassword = company.isSendPassword();
boolean companySecuritySendPasswordResetLink = company.isSendPasswordResetLink();
boolean companySecurityStrangers = company.isStrangers();
boolean companySecurityStrangersWithMx = company.isStrangersWithMx();
boolean companySecurityStrangersVerify = company.isStrangersVerify();

boolean casAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED, PropsValues.CAS_AUTH_ENABLED);
boolean casImportFromLdap = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_IMPORT_FROM_LDAP, PropsValues.CAS_IMPORT_FROM_LDAP);
String casLoginUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);
String casLogoutUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGOUT_URL, PropsValues.CAS_LOGOUT_URL);
String casServerName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_NAME, PropsValues.CAS_SERVER_NAME);
String casServerUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_URL, PropsValues.CAS_SERVER_URL);
String casServiceUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVICE_URL, PropsValues.CAS_SERVICE_URL);

boolean facebookConnectAuthEnabled = FacebookConnectUtil.isEnabled(company.getCompanyId());
String facebookConnectAppId = FacebookConnectUtil.getAppId(company.getCompanyId());
String facebookConnectAppSecret = FacebookConnectUtil.getAppSecret(company.getCompanyId());
String facebookConnectGraphURL = FacebookConnectUtil.getGraphURL(company.getCompanyId());
String facebookConnectOauthAuthURL = FacebookConnectUtil.getAuthURL(company.getCompanyId());
String facebookConnectOauthTokenURL = FacebookConnectUtil.getAccessTokenURL(company.getCompanyId());
String facebookConnectRedirectURL = FacebookConnectUtil.getRedirectURL(company.getCompanyId());

boolean ntlmAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED);
String ntlmDomainController = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER, PropsValues.NTLM_DOMAIN_CONTROLLER);
String ntlmDomainControllerName = ParamUtil.getString(request, "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME + "--", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME, PropsValues.NTLM_DOMAIN_CONTROLLER_NAME));
String ntlmDomain = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN, PropsValues.NTLM_DOMAIN);
String ntlmServiceAccount = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_ACCOUNT, PropsValues.NTLM_SERVICE_ACCOUNT);
String ntlmServicePassword = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_PASSWORD, PropsValues.NTLM_SERVICE_PASSWORD);

boolean openIdAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_ID_AUTH_ENABLED, PropsValues.OPEN_ID_AUTH_ENABLED);

boolean openSsoAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_SSO_AUTH_ENABLED, PropsValues.OPEN_SSO_AUTH_ENABLED);
boolean openSsoLdapImportEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_SSO_LDAP_IMPORT_ENABLED, PropsValues.OPEN_SSO_LDAP_IMPORT_ENABLED);
String openSsoLoginUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGIN_URL, PropsValues.OPEN_SSO_LOGIN_URL);
String openSsoLogoutUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGOUT_URL, PropsValues.OPEN_SSO_LOGOUT_URL);
String openSsoServiceUrl = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SERVICE_URL, PropsValues.OPEN_SSO_SERVICE_URL);
String openSsoScreenNameAttr = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR, PropsValues.OPEN_SSO_SCREEN_NAME_ATTR);
String openSsoEmailAddressAttr = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR, PropsValues.OPEN_SSO_EMAIL_ADDRESS_ATTR);
String openSsoFirstNameAttr = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_FIRST_NAME_ATTR, PropsValues.OPEN_SSO_FIRST_NAME_ATTR);
String openSsoLastNameAttr = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LAST_NAME_ATTR, PropsValues.OPEN_SSO_LAST_NAME_ATTR);

boolean siteminderAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_AUTH_ENABLED, PropsValues.SITEMINDER_AUTH_ENABLED);
boolean siteminderImportFromLdap = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_IMPORT_FROM_LDAP, PropsValues.SITEMINDER_IMPORT_FROM_LDAP);
String siteminderUserHeader = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.SITEMINDER_USER_HEADER, PropsValues.SITEMINDER_USER_HEADER);
%>

<h3><liferay-ui:message key="authentication" /></h3>

<liferay-ui:tabs
	names="general,ldap,cas,facebook,ntlm,open-id,open-sso,siteminder"
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
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= facebookConnectAuthEnabled %>" />

			<aui:input cssClass="lfr-input-text-container" label="application-id" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_APP_ID + "--" %>' type="text" value="<%= facebookConnectAppId %>" />

			<aui:input cssClass="lfr-input-text-container" label="application-secret" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_APP_SECRET + "--" %>' type="text" value="<%= facebookConnectAppSecret %>" />

			<aui:input cssClass="lfr-input-text-container" label="graph-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_GRAPH_URL + "--" %>' type="text" value="<%= facebookConnectGraphURL %>" />

			<aui:input cssClass="lfr-input-text-container" label="oauth-authentication-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL + "--" %>' type="text" value="<%= facebookConnectOauthAuthURL %>" />

			<aui:input cssClass="lfr-input-text-container" label="oauth-token-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL + "--" %>' type="text" value="<%= facebookConnectOauthTokenURL %>" />

			<aui:input cssClass="lfr-input-text-container" label="redirect-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL + "--" %>' type="text" value="<%= facebookConnectRedirectURL %>" />
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

			<aui:input inlineLabel="left" label="ldap-import-enabled" name='<%= "settings--" + PropsKeys.OPEN_SSO_LDAP_IMPORT_ENABLED + "--" %>' type="checkbox" value="<%= openSsoLdapImportEnabled %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="login-url-for-opensso-help" label="login-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_LOGIN_URL + "--" %>' type="text" value="<%= openSsoLoginUrl %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="logout-url-for-opensso-help" label="logout-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_LOGOUT_URL + "--" %>' type="text" value="<%= openSsoLogoutUrl %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="service-url-for-opensso-help" label="service-url" name='<%= "settings--" + PropsKeys.OPEN_SSO_SERVICE_URL + "--" %>' type="text" value="<%= openSsoServiceUrl %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="mappings-for-opensso-help" label="screen-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR + "--" %>' type="text" value="<%= openSsoScreenNameAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="email-address-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR + "--" %>' type="text" value="<%= openSsoEmailAddressAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="first-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_FIRST_NAME_ATTR + "--" %>' type="text" value="<%= openSsoFirstNameAttr %>" />

			<aui:input cssClass="lfr-input-text-container" label="last-name-attribute" name='<%= "settings--" + PropsKeys.OPEN_SSO_LAST_NAME_ATTR + "--" %>' type="text" value="<%= openSsoLastNameAttr %>" />

			<aui:button-row>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "testSettings('openssoConfiguration');";
				%>

				<aui:button onClick='<%= taglibOnClick %>' value="test-opensso-configuration" />
			</aui:button-row>
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

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />testSettings',
		function(type) {
			var A = AUI();

			var url = null;

			var data = {};

			if (type == "openssoConfiguration") {
				url = "<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/test_opensso_configuration" /></portlet:renderURL>";

				data.<portlet:namespace />openSsoLoginUrl = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_LOGIN_URL %>--'].value;
				data.<portlet:namespace />openSsoLogoutUrl = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_LOGOUT_URL %>--'].value;
				data.<portlet:namespace />openSsoServiceUrl = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_SERVICE_URL %>--'].value;
				data.<portlet:namespace />openSsoScreenNameAttr = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR %>--'].value;
				data.<portlet:namespace />openSsoEmailAddressAttr = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR %>--'].value;
				data.<portlet:namespace />openSsoFirstNameAttr = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_FIRST_NAME_ATTR %>--'].value;
				data.<portlet:namespace />openSsoLastNameAttr = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.OPEN_SSO_LAST_NAME_ATTR %>--'].value;
			}

			if (url != null) {
				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: Liferay.Language.get('OpenSSO'),
						width: 600
					}
				).render();

				dialog.plug(
					A.Plugin.IO,
					{
						data: data,
						uri: url
					}
				);
			}
		},
		['aui-dialog', 'aui-io']
	);
</aui:script>