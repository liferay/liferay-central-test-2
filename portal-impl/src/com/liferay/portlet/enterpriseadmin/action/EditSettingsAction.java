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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.servlet.filters.sso.cas.CASFilter;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.util.ldap.LDAPUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditSettingsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class EditSettingsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(actionRequest);
		long userId = PortalUtil.getUserId(actionRequest);

		if (!RoleLocalServiceUtil.hasUserRole(
				userId, companyId, RoleConstants.ADMINISTRATOR, true)) {

			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.enterprise_admin.error");

			return;
		}

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("updateCAS")) {
			updateCAS(actionRequest, companyId, preferences);
		}
		else if (cmd.equals("updateDefaultGroupsAndRoles")) {
			updateDefaultGroupsAndRoles(actionRequest, preferences);
		}
		else if (cmd.equals("updateEmails")) {
			updateEmails(actionRequest, preferences);
		}
		else if (cmd.equals("updateLdap")) {
			updateLdap(actionRequest, companyId, preferences);
		}
		else if (cmd.equals("updateMailHostNames")) {
			updateMailHostNames(actionRequest, preferences);
		}
		else if (cmd.equals("updateNtlm")) {
			updateNtlm(actionRequest, companyId, preferences);
		}
		else if (cmd.equals("updateOpenId")) {
			updateOpenId(actionRequest, preferences);
		}
		else if (cmd.equals("updateOpenSSO")) {
			updateOpenSSO(actionRequest, companyId, preferences);
		}
		else if (cmd.equals("updateReservedUsers")) {
			updateReservedUsers(actionRequest, preferences);
		}
		else if (cmd.equals("updateSecurity")) {
			updateSecurity(actionRequest);
		}
		else if (cmd.equals("updateSiteMinder")) {
			updateSiteMinder(actionRequest, companyId, preferences);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			sendRedirect(actionRequest, actionResponse);
		}
		else {
			setForward(actionRequest, "portlet.enterprise_admin.view");
		}
	}

	protected void updateCAS(
			ActionRequest actionRequest, long companyId,
			PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		boolean importFromLdap = ParamUtil.getBoolean(
			actionRequest, "importFromLdap");
		String loginUrl = ParamUtil.getString(actionRequest, "loginUrl");
		String logoutUrl = ParamUtil.getString(actionRequest, "logoutUrl");
		String serverName = ParamUtil.getString(actionRequest, "serverName");
		String serviceUrl = ParamUtil.getString(actionRequest, "serviceUrl");
		String validateUrl = ParamUtil.getString(actionRequest, "validateUrl");

		preferences.setValue(
			PropsKeys.CAS_AUTH_ENABLED, String.valueOf(enabled));
		preferences.setValue(
			PropsKeys.CAS_IMPORT_FROM_LDAP, String.valueOf(importFromLdap));
		preferences.setValue(PropsKeys.CAS_LOGIN_URL, loginUrl);
		preferences.setValue(PropsKeys.CAS_LOGOUT_URL, logoutUrl);
		preferences.setValue(PropsKeys.CAS_SERVER_NAME, serverName);
		preferences.setValue(PropsKeys.CAS_SERVICE_URL, serviceUrl);
		preferences.setValue(PropsKeys.CAS_VALIDATE_URL, validateUrl);

		preferences.store();

		CASFilter.reload(companyId);
	}

	protected void updateDefaultGroupsAndRoles(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String defaultGroupNames = ParamUtil.getString(
			actionRequest, "defaultGroupNames");
		String defaultRoleNames = ParamUtil.getString(
			actionRequest, "defaultRoleNames");
		String defaultUserGroupNames = ParamUtil.getString(
			actionRequest, "defaultUserGroupNames");

		preferences.setValue(
			PropsKeys.ADMIN_DEFAULT_GROUP_NAMES, defaultGroupNames);
		preferences.setValue(
			PropsKeys.ADMIN_DEFAULT_ROLE_NAMES, defaultRoleNames);
		preferences.setValue(
			PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES, defaultUserGroupNames);

		preferences.store();
	}

	protected void updateEmails(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String tabs3 = ParamUtil.getString(actionRequest, "tabs3");

		if (tabs3.equals("account-created-notification")) {
			String emailUserAddedEnabled = ParamUtil.getString(
				actionRequest, "emailUserAddedEnabled");
			String emailUserAddedSubject = ParamUtil.getString(
				actionRequest, "emailUserAddedSubject");
			String emailUserAddedBody = ParamUtil.getString(
				actionRequest, "emailUserAddedBody");

			if (Validator.isNull(emailUserAddedSubject)) {
				SessionErrors.add(actionRequest, "emailUserAddedSubject");
			}
			else if (Validator.isNull(emailUserAddedBody)) {
				SessionErrors.add(actionRequest, "emailUserAddedBody");
			}
			else {
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED,
					emailUserAddedEnabled);
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT,
					emailUserAddedSubject);
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY, emailUserAddedBody);
			}
		}
		else if (tabs3.equals("password-changed-notification")) {
			String emailPasswordSentEnabled = ParamUtil.getString(
				actionRequest, "emailPasswordSentEnabled");
			String emailPasswordSentSubject = ParamUtil.getString(
				actionRequest, "emailPasswordSentSubject");
			String emailPasswordSentBody = ParamUtil.getString(
				actionRequest, "emailPasswordSentBody");

			if (Validator.isNull(emailPasswordSentSubject)) {
				SessionErrors.add(actionRequest, "emailPasswordSentSubject");
			}
			else if (Validator.isNull(emailPasswordSentBody)) {
				SessionErrors.add(actionRequest, "emailPasswordSentBody");
			}
			else {
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED,
					emailPasswordSentEnabled);
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT,
					emailPasswordSentSubject);
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY,
					emailPasswordSentBody);
			}
		}
		else {
			String emailFromName = ParamUtil.getString(
				actionRequest, "emailFromName");
			String emailFromAddress = ParamUtil.getString(
				actionRequest, "emailFromAddress");

			if (Validator.isNull(emailFromName)) {
				SessionErrors.add(actionRequest, "emailFromName");
			}
			else if (!Validator.isEmailAddress(emailFromAddress)) {
				SessionErrors.add(actionRequest, "emailFromAddress");
			}
			else {
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_FROM_NAME, emailFromName);
				preferences.setValue(
					PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, emailFromAddress);
			}
		}

		preferences.store();
	}

	protected void updateLdap(
			ActionRequest actionRequest, long companyId,
			PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		boolean required = ParamUtil.getBoolean(actionRequest, "required");
		String baseProviderURL = ParamUtil.getString(
			actionRequest, "baseProviderURL");
		String baseDN = ParamUtil.getString(actionRequest, "baseDN");
		String principal = ParamUtil.getString(actionRequest, "principal");
		String credentials = ParamUtil.getString(actionRequest, "credentials");
		String searchFilter = ParamUtil.getString(
			actionRequest, "searchFilter");
		String userDefaultObjectClasses = ParamUtil.getString(
			actionRequest, "userDefaultObjectClasses");

		String userMappings =
			"screenName=" +
				ParamUtil.getString(actionRequest, "userMappingScreenName") +
			"\npassword=" +
				ParamUtil.getString(actionRequest, "userMappingPassword") +
			"\nemailAddress=" +
				ParamUtil.getString(actionRequest, "userMappingEmailAddress") +
			"\nfullName=" +
				ParamUtil.getString(actionRequest, "userMappingFullName") +
			"\nfirstName=" +
				ParamUtil.getString(actionRequest, "userMappingFirstName") +
			"\nlastName=" +
				ParamUtil.getString(actionRequest, "userMappingLastName") +
			"\njobTitle=" +
				ParamUtil.getString(actionRequest, "userMappingJobTitle") +
			"\ngroup=" + ParamUtil.getString(actionRequest, "userMappingGroup");

		String groupMappings =
			"groupName=" +
				ParamUtil.getString(actionRequest, "groupMappingGroupName") +
			"\ndescription=" +
				ParamUtil.getString(actionRequest, "groupMappingDescription") +
			"\nuser=" + ParamUtil.getString(actionRequest, "groupMappingUser");

		boolean importEnabled = ParamUtil.getBoolean(
			actionRequest, "importEnabled");
		boolean importOnStartup = ParamUtil.getBoolean(
			actionRequest, "importOnStartup");
		long importInterval = ParamUtil.getLong(
			actionRequest, "importInterval");
		String importUserSearchFilter = ParamUtil.getString(
			actionRequest, "importUserSearchFilter");
		String importGroupSearchFilter = ParamUtil.getString(
			actionRequest, "importGroupSearchFilter");
		boolean exportEnabled = ParamUtil.getBoolean(
			actionRequest, "exportEnabled");
		String usersDN = ParamUtil.getString(actionRequest, "usersDN");
		String groupsDN = ParamUtil.getString(actionRequest, "groupsDN");
		boolean passwordPolicyEnabled = ParamUtil.getBoolean(
			actionRequest, "passwordPolicyEnabled");

		try {
			if (enabled) {
				String fullProviderURL = LDAPUtil.getFullProviderURL(
					baseProviderURL, baseDN);

				PortalLDAPUtil.getContext(
					companyId, fullProviderURL, principal, credentials);
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, "ldapAuthentication");

			return;
		}

		preferences.setValue(
			PropsKeys.LDAP_AUTH_ENABLED, String.valueOf(enabled));
		preferences.setValue(
			PropsKeys.LDAP_AUTH_REQUIRED, String.valueOf(required));
		preferences.setValue(PropsKeys.LDAP_BASE_PROVIDER_URL, baseProviderURL);
		preferences.setValue(PropsKeys.LDAP_BASE_DN, baseDN);
		preferences.setValue(PropsKeys.LDAP_SECURITY_PRINCIPAL, principal);
		preferences.setValue(PropsKeys.LDAP_SECURITY_CREDENTIALS, credentials);
		preferences.setValue(PropsKeys.LDAP_AUTH_SEARCH_FILTER, searchFilter);
		preferences.setValue(
			PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES,
			userDefaultObjectClasses);
		preferences.setValue(PropsKeys.LDAP_USER_MAPPINGS, userMappings);
		preferences.setValue(PropsKeys.LDAP_GROUP_MAPPINGS, groupMappings);
		preferences.setValue(
			PropsKeys.LDAP_IMPORT_ENABLED, String.valueOf(importEnabled));
		preferences.setValue(
			PropsKeys.LDAP_IMPORT_ON_STARTUP, String.valueOf(importOnStartup));
		preferences.setValue(
			PropsKeys.LDAP_IMPORT_INTERVAL, String.valueOf(importInterval));
		preferences.setValue(
			PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER, importUserSearchFilter);
		preferences.setValue(
			PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER, importGroupSearchFilter);
		preferences.setValue(
			PropsKeys.LDAP_EXPORT_ENABLED, String.valueOf(exportEnabled));
		preferences.setValue(PropsKeys.LDAP_USERS_DN, usersDN);
		preferences.setValue(PropsKeys.LDAP_GROUPS_DN, groupsDN);
		preferences.setValue(
			PropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
			String.valueOf(passwordPolicyEnabled));

		preferences.store();
	}

	protected void updateMailHostNames(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String mailHostNames = ParamUtil.getString(
			actionRequest, "mailHostNames");

		preferences.setValue(PropsKeys.ADMIN_MAIL_HOST_NAMES, mailHostNames);

		preferences.store();
	}

	protected void updateNtlm(
			ActionRequest actionRequest, long companyId,
			PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String domainController = ParamUtil.getString(
			actionRequest, "domainController");
		String domain = ParamUtil.getString(actionRequest, "domain");

		preferences.setValue(
			PropsKeys.NTLM_AUTH_ENABLED, String.valueOf(enabled));
		preferences.setValue(
			PropsKeys.NTLM_DOMAIN_CONTROLLER, domainController);
		preferences.setValue(PropsKeys.NTLM_DOMAIN, domain);

		preferences.store();
	}

	protected void updateOpenId(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");

		preferences.setValue(
			PropsKeys.OPEN_ID_AUTH_ENABLED, String.valueOf(enabled));

		preferences.store();
	}

	protected void updateOpenSSO(
			ActionRequest actionRequest, long companyId,
			PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String loginUrl = ParamUtil.getString(actionRequest, "loginUrl");
		String logoutUrl = ParamUtil.getString(actionRequest, "logoutUrl");
		String serviceUrl = ParamUtil.getString(actionRequest, "serviceUrl");
		String screenName = ParamUtil.getString(
			actionRequest, "screenNameAttr");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddressAttr");
		String firstName = ParamUtil.getString(actionRequest, "firstNameAttr");
		String lastName = ParamUtil.getString(actionRequest, "lastNameAttr");

		preferences.setValue(
			PropsKeys.OPEN_SSO_AUTH_ENABLED, String.valueOf(enabled));
		preferences.setValue(PropsKeys.OPEN_SSO_LOGIN_URL, loginUrl);
		preferences.setValue(PropsKeys.OPEN_SSO_LOGOUT_URL, logoutUrl);
		preferences.setValue(PropsKeys.OPEN_SSO_SERVICE_URL, serviceUrl);
		preferences.setValue(PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR, screenName);
		preferences.setValue(
			PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR, emailAddress);
		preferences.setValue(PropsKeys.OPEN_SSO_FIRST_NAME_ATTR, firstName);
		preferences.setValue(PropsKeys.OPEN_SSO_LAST_NAME_ATTR, lastName);

		preferences.store();
	}

	protected void updateReservedUsers(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String reservedScreenNames = ParamUtil.getString(
			actionRequest, "reservedScreenNames");
		String reservedEmailAddresses = ParamUtil.getString(
			actionRequest, "reservedEmailAddresses");

		preferences.setValue(
			PropsKeys.ADMIN_RESERVED_SCREEN_NAMES, reservedScreenNames);
		preferences.setValue(
			PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES, reservedEmailAddresses);

		preferences.store();
	}

	protected void updateSecurity(ActionRequest actionRequest)
		throws Exception {

		Company company = PortalUtil.getCompany(actionRequest);

		String authType = ParamUtil.getString(actionRequest, "authType");
		boolean autoLogin = ParamUtil.getBoolean(actionRequest, "autoLogin");
		boolean sendPassword = ParamUtil.getBoolean(
			actionRequest, "sendPassword");
		boolean strangers = ParamUtil.getBoolean(actionRequest, "strangers");
		boolean strangersWithMx = ParamUtil.getBoolean(
			actionRequest, "strangersWithMx");
		boolean strangersVerify = ParamUtil.getBoolean(
			actionRequest, "strangersVerify");

		CompanyServiceUtil.updateSecurity(
			company.getCompanyId(), authType, autoLogin, sendPassword,
			strangers, strangersWithMx, strangersVerify,
			company.isCommunityLogo());
	}

	protected void updateSiteMinder(
			ActionRequest actionRequest, long companyId,
			PortletPreferences preferences)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		boolean importFromLdap = ParamUtil.getBoolean(
			actionRequest, "importFromLdap");
		String userHeader = ParamUtil.getString(actionRequest, "userHeader");

		preferences.setValue(
			PropsKeys.SITEMINDER_AUTH_ENABLED, String.valueOf(enabled));
		preferences.setValue(
			PropsKeys.SITEMINDER_IMPORT_FROM_LDAP,
			String.valueOf(importFromLdap));
		preferences.setValue(PropsKeys.SITEMINDER_USER_HEADER, userHeader);

		preferences.store();
	}

}