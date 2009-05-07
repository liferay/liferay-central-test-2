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
String companySecurityAuthType = ParamUtil.getString(request, "settings(" + PropsKeys.COMPANY_SECURITY_AUTH_TYPE +")", company.getAuthType());
boolean companySecurityAutoLogin = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_AUTO_LOGIN +")", company.isAutoLogin());
boolean companySecuritySendPassword = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD +")", company.isSendPassword());
boolean companySecurityStrangers = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS +")", company.isStrangers());
boolean companySecurityStrangersWithMx = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX +")", company.isStrangersWithMx());
boolean companySecurityStrangersVerify = ParamUtil.getBoolean(request, "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY +")", company.isStrangersVerify());

boolean casAuthEnabled = ParamUtil.getBoolean(request, "settings(" + PropsKeys.CAS_AUTH_ENABLED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED, PropsValues.CAS_AUTH_ENABLED));
boolean casImportFromLdap = ParamUtil.getBoolean(request, "settings(" + PropsKeys.CAS_IMPORT_FROM_LDAP +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_IMPORT_FROM_LDAP, PropsValues.CAS_IMPORT_FROM_LDAP));
String casLoginUrl = ParamUtil.getString(request, "settings(" + PropsKeys.CAS_LOGIN_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGIN_URL, PropsKeys.CAS_LOGIN_URL));
String casLogoutUrl = ParamUtil.getString(request, "settings(" + PropsKeys.CAS_LOGOUT_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGOUT_URL, PropsKeys.CAS_LOGOUT_URL));
String casServerName = ParamUtil.getString(request, "settings(" + PropsKeys.CAS_SERVER_NAME +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_NAME, PropsKeys.CAS_SERVER_NAME));
String casServiceUrl = ParamUtil.getString(request, "settings(" + PropsKeys.CAS_SERVICE_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVICE_URL, PropsKeys.CAS_SERVICE_URL));
String casValidateUrl = ParamUtil.getString(request, "settings(" + PropsKeys.CAS_VALIDATE_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_VALIDATE_URL, PropsKeys.CAS_VALIDATE_URL));

boolean ntlmAuthEnabled = ParamUtil.getBoolean(request, "settings(" + PropsKeys.NTLM_AUTH_ENABLED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED));
String ntlmDomainController = ParamUtil.getString(request, "settings(" + PropsKeys.NTLM_DOMAIN_CONTROLLER +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER, PropsValues.NTLM_DOMAIN_CONTROLLER));
String ntlmDomain = ParamUtil.getString(request, "settings(" + PropsKeys.NTLM_DOMAIN +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN, PropsValues.NTLM_DOMAIN));

boolean openIdAuthEnabled = ParamUtil.getBoolean(request, "settings(" + PropsKeys.OPEN_ID_AUTH_ENABLED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_ID_AUTH_ENABLED, PropsValues.OPEN_ID_AUTH_ENABLED));

boolean openSsoAuthEnabled = ParamUtil.getBoolean(request, "settings(" + PropsKeys.OPEN_SSO_AUTH_ENABLED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_SSO_AUTH_ENABLED, PropsValues.OPEN_SSO_AUTH_ENABLED));
String openSsoLoginUrl = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_LOGIN_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGIN_URL, PropsValues.OPEN_SSO_LOGIN_URL));
String openSsoLogoutUrl = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_LOGOUT_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LOGOUT_URL, PropsValues.OPEN_SSO_LOGOUT_URL));
String openSsoServiceUrl = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_SERVICE_URL +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SERVICE_URL, PropsValues.OPEN_SSO_SERVICE_URL));
String openSsoScreenNameAttr = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR, PropsValues.OPEN_SSO_SCREEN_NAME_ATTR));
String openSsoEmailAddressAttr = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR, PropsValues.OPEN_SSO_EMAIL_ADDRESS_ATTR));
String openSsoFirstNameAttr = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_FIRST_NAME_ATTR +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_FIRST_NAME_ATTR, PropsValues.OPEN_SSO_FIRST_NAME_ATTR));
String openSsoLastNameAttr = ParamUtil.getString(request, "settings(" + PropsKeys.OPEN_SSO_LAST_NAME_ATTR +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.OPEN_SSO_LAST_NAME_ATTR, PropsValues.OPEN_SSO_LAST_NAME_ATTR));

boolean siteminderAuthEnabled = ParamUtil.getBoolean(request, "settings(" + PropsKeys.SITEMINDER_AUTH_ENABLED +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_AUTH_ENABLED, PropsValues.SITEMINDER_AUTH_ENABLED));
boolean siteminderImportFromLdap = ParamUtil.getBoolean(request, "settings(" + PropsKeys.SITEMINDER_IMPORT_FROM_LDAP +")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITEMINDER_IMPORT_FROM_LDAP, PropsValues.SITEMINDER_IMPORT_FROM_LDAP));
String siteminderUserHeader = ParamUtil.getString(request, "settings(" + PropsKeys.SITEMINDER_USER_HEADER +")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.SITEMINDER_USER_HEADER, PropsValues.SITEMINDER_USER_HEADER));
%>

<h3><liferay-ui:message key="authentication" /></h3>

<liferay-ui:tabs
	names="general,ldap,cas,ntlm,open-id,open-sso,siteminder"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_AUTH_TYPE %>)"><liferay-ui:message key="how-do-users-authenticate" /></label>

				<select name="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_AUTH_TYPE %>)">
					<option <%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_EA) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_EA %>"><liferay-ui:message key="by-email-address" /></option>
					<option <%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_SN) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_SN %>"><liferay-ui:message key="by-screen-name" /></option>
					<option <%= companySecurityAuthType.equals(CompanyConstants.AUTH_TYPE_ID) ? "selected" : "" %> value="<%= CompanyConstants.AUTH_TYPE_ID %>"><liferay-ui:message key="by-user-id" /></option>
				</select>
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_AUTO_LOGIN %>)"><liferay-ui:message key="allow-users-to-automatically-login" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_AUTO_LOGIN + ")" %>' defaultValue="<%= companySecurityAutoLogin %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_SEND_PASSWORD %>)"><liferay-ui:message key="allow-users-to-request-forgotten-passwords" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_SEND_PASSWORD + ")" %>' defaultValue="<%= companySecuritySendPassword %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_STRANGERS %>)"><liferay-ui:message key="allow-strangers-to-create-accounts" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS + ")" %>' defaultValue="<%= companySecurityStrangers %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX %>)"><liferay-ui:message key="allow-strangers-to-create-accounts-with-a-company-email-address" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX + ")" %>' defaultValue="<%= companySecurityStrangersWithMx %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY %>)"><liferay-ui:message key="require-strangers-to-verify-their-email-address" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY + ")" %>' defaultValue="<%= companySecurityStrangersVerify %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<%@ include file="/html/portlet/enterprise_admin/settings/authentication_ldap.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.CAS_AUTH_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.CAS_AUTH_ENABLED + ")" %>' defaultValue="<%= casAuthEnabled %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.CAS_IMPORT_FROM_LDAP %>)"><liferay-ui:message key="import-cas-users-from-ldap" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.CAS_IMPORT_FROM_LDAP + ")" %>' defaultValue="<%= casImportFromLdap %>" />

				<liferay-ui:icon-help message="import-cas-users-from-ldap-help" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.CAS_LOGIN_URL %>)"><liferay-ui:message key="login-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.CAS_LOGIN_URL %>)" type="text" value="<%= casLoginUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.CAS_LOGOUT_URL %>)"><liferay-ui:message key="logout-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.CAS_LOGOUT_URL %>)" type="text" value="<%= casLogoutUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.CAS_SERVER_NAME %>)"><liferay-ui:message key="server-name" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.CAS_SERVER_NAME %>)" type="text" value="<%= casServerName %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.CAS_SERVICE_URL %>)"><liferay-ui:message key="service-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.CAS_SERVICE_URL %>)" type="text" value="<%= casServiceUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.CAS_VALIDATE_URL %>)"><liferay-ui:message key="validate-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.CAS_VALIDATE_URL %>)" type="text" value="<%= casValidateUrl %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.NTLM_AUTH_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.NTLM_AUTH_ENABLED + ")" %>' defaultValue="<%= ntlmAuthEnabled %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.NTLM_DOMAIN_CONTROLLER %>)"><liferay-ui:message key="domain-controller" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.NTLM_DOMAIN_CONTROLLER %>)" type="text" value="<%= ntlmDomainController %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.NTLM_DOMAIN %>)"><liferay-ui:message key="domain" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.NTLM_DOMAIN %>)" type="text" value="<%= ntlmDomain %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.OPEN_ID_AUTH_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.OPEN_ID_AUTH_ENABLED + ")" %>' defaultValue="<%= openIdAuthEnabled %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
			<div class="exp-ctrl-holder">
				<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_AUTH_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

				<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.OPEN_SSO_AUTH_ENABLED + ")" %>' defaultValue="<%= openSsoAuthEnabled %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LOGIN_URL %>)"><liferay-ui:message key="login-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LOGIN_URL %>)" type="text" value="<%= openSsoLoginUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LOGOUT_URL %>)"><liferay-ui:message key="logout-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LOGOUT_URL %>)" type="text" value="<%= openSsoLogoutUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_SERVICE_URL %>)"><liferay-ui:message key="service-url" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_SERVICE_URL %>)" type="text" value="<%= openSsoServiceUrl %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR %>)"><liferay-ui:message key="screen-name-attribute" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR %>)" type="text" value="<%= openSsoScreenNameAttr %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR %>)"><liferay-ui:message key="email-address-attribute" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR %>)" type="text" value="<%= openSsoEmailAddressAttr %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_FIRST_NAME_ATTR %>)"><liferay-ui:message key="first-name-attribute" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_FIRST_NAME_ATTR %>)" type="text" value="<%= openSsoFirstNameAttr %>" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LAST_NAME_ATTR %>)"><liferay-ui:message key="last-name-attribute" /></label>

				<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.OPEN_SSO_LAST_NAME_ATTR %>)" type="text" value="<%= openSsoLastNameAttr %>" />
			</div>
		</fieldset>
	</liferay-ui:section>

	<liferay-ui:section>
		<fieldset class="exp-block-labels exp-form-column">
				<div class="exp-ctrl-holder">
					<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.SITEMINDER_AUTH_ENABLED %>)"><liferay-ui:message key="enabled" /></label>

					<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.SITEMINDER_AUTH_ENABLED + ")" %>' defaultValue="<%= siteminderAuthEnabled %>" />
				</div>

				<div class="exp-ctrl-holder">
					<label class="inline-label" for="<portlet:namespace />settings(<%= PropsKeys.SITEMINDER_IMPORT_FROM_LDAP %>)"><liferay-ui:message key="import-siteminder-users-from-ldap" /></label>

					<liferay-ui:input-checkbox param='<%= "settings(" + PropsKeys.SITEMINDER_IMPORT_FROM_LDAP + ")" %>' defaultValue="<%= siteminderImportFromLdap %>" />

					<liferay-ui:icon-help message="import-siteminder-users-from-ldap-help" />
				</div>

				<div class="exp-ctrl-holder">
					<label for="<portlet:namespace />settings(<%= PropsKeys.SITEMINDER_USER_HEADER %>)"><liferay-ui:message key="user-header" /></label>

					<input class="lfr-input-text" name="<portlet:namespace />settings(<%= PropsKeys.SITEMINDER_USER_HEADER %>)" type="text" value="<%= siteminderUserHeader %>" />
				</div>
		</fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>