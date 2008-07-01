/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.impl.RoleImpl;
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
				userId, companyId, RoleImpl.ADMINISTRATOR, true)) {

			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.enterprise_admin.error");

			return;
		}

		PortletPreferences prefs = PrefsPropsUtil.getPreferences(companyId);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("updateCAS")) {
			updateCAS(actionRequest, companyId, prefs);
		}
		else if (cmd.equals("updateDefaultGroupsAndRoles")) {
			updateDefaultGroupsAndRoles(actionRequest, prefs);
		}
		else if (cmd.equals("updateEmails")) {
			updateEmails(actionRequest, prefs);
		}
		else if (cmd.equals("updateLdap")) {
			updateLdap(actionRequest, companyId, prefs);
		}
		else if (cmd.equals("updateMailHostNames")) {
			updateMailHostNames(actionRequest, prefs);
		}
		else if (cmd.equals("updateNtlm")) {
			updateNtlm(actionRequest, companyId, prefs);
		}
		else if (cmd.equals("updateOpenId")) {
			updateOpenId(actionRequest, prefs);
		}
		else if (cmd.equals("updateOpenSSO")) {
			updateOpenSSO(actionRequest, companyId, prefs);
		}
		else if (cmd.equals("updateReservedUsers")) {
			updateReservedUsers(actionRequest, prefs);
		}
		else if (cmd.equals("updateSecurity")) {
			updateSecurity(actionRequest);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			if (!cmd.equals("updateLdap") && !cmd.equals("updateSecurity")) {
				prefs.store();
			}

			sendRedirect(actionRequest, actionResponse);
		}
		else {
			setForward(actionRequest, "portlet.enterprise_admin.view");
		}
	}

	protected void updateCAS(
			ActionRequest actionRequest, long companyId,
			PortletPreferences prefs)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		boolean importFromLdap = ParamUtil.getBoolean(
			actionRequest, "importFromLdap");
		String loginUrl = ParamUtil.getString(actionRequest, "loginUrl");
		String logoutUrl = ParamUtil.getString(actionRequest, "logoutUrl");
		String serverName = ParamUtil.getString(actionRequest, "serverName");
		String serviceUrl = ParamUtil.getString(actionRequest, "serviceUrl");
		String validateUrl = ParamUtil.getString(actionRequest, "validateUrl");

		prefs.setValue(
			PropsKeys.CAS_AUTH_ENABLED, String.valueOf(enabled));
		prefs.setValue(
			PropsKeys.CAS_IMPORT_FROM_LDAP, String.valueOf(importFromLdap));
		prefs.setValue(PropsKeys.CAS_LOGIN_URL, loginUrl);
		prefs.setValue(PropsKeys.CAS_LOGOUT_URL, logoutUrl);
		prefs.setValue(PropsKeys.CAS_SERVER_NAME, serverName);
		prefs.setValue(PropsKeys.CAS_SERVICE_URL, serviceUrl);
		prefs.setValue(PropsKeys.CAS_VALIDATE_URL, validateUrl);

		prefs.store();

		CASFilter.reload(companyId);
	}

	protected void updateDefaultGroupsAndRoles(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String defaultGroupNames = ParamUtil.getString(
			actionRequest, "defaultGroupNames");
		String defaultRoleNames = ParamUtil.getString(
			actionRequest, "defaultRoleNames");
		String defaultUserGroupNames = ParamUtil.getString(
			actionRequest, "defaultUserGroupNames");

		prefs.setValue(PropsKeys.ADMIN_DEFAULT_GROUP_NAMES, defaultGroupNames);
		prefs.setValue(PropsKeys.ADMIN_DEFAULT_ROLE_NAMES, defaultRoleNames);
		prefs.setValue(
			PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES, defaultUserGroupNames);
	}

	protected void updateEmails(
			ActionRequest actionRequest, PortletPreferences prefs)
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
				prefs.setValue(
					PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED,
					emailUserAddedEnabled);
				prefs.setValue(
					PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT,
					emailUserAddedSubject);
				prefs.setValue(
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
				prefs.setValue(
					PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_ENABLED,
					emailPasswordSentEnabled);
				prefs.setValue(
					PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT,
					emailPasswordSentSubject);
				prefs.setValue(
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
				prefs.setValue(PropsKeys.ADMIN_EMAIL_FROM_NAME, emailFromName);
				prefs.setValue(
					PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, emailFromAddress);
			}
		}
	}

	protected void updateLdap(
			ActionRequest actionRequest, long companyId,
			PortletPreferences prefs)
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

		prefs.setValue(PropsKeys.LDAP_AUTH_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsKeys.LDAP_AUTH_REQUIRED, String.valueOf(required));
		prefs.setValue(PropsKeys.LDAP_BASE_PROVIDER_URL, baseProviderURL);
		prefs.setValue(PropsKeys.LDAP_BASE_DN, baseDN);
		prefs.setValue(PropsKeys.LDAP_SECURITY_PRINCIPAL, principal);
		prefs.setValue(PropsKeys.LDAP_SECURITY_CREDENTIALS, credentials);
		prefs.setValue(PropsKeys.LDAP_AUTH_SEARCH_FILTER, searchFilter);
		prefs.setValue(
			PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES,
			userDefaultObjectClasses);
		prefs.setValue(PropsKeys.LDAP_USER_MAPPINGS, userMappings);
		prefs.setValue(PropsKeys.LDAP_GROUP_MAPPINGS, groupMappings);
		prefs.setValue(
			PropsKeys.LDAP_IMPORT_ENABLED, String.valueOf(importEnabled));
		prefs.setValue(
			PropsKeys.LDAP_IMPORT_ON_STARTUP, String.valueOf(importOnStartup));
		prefs.setValue(
			PropsKeys.LDAP_IMPORT_INTERVAL, String.valueOf(importInterval));
		prefs.setValue(
			PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER, importUserSearchFilter);
		prefs.setValue(
			PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER, importGroupSearchFilter);
		prefs.setValue(
			PropsKeys.LDAP_EXPORT_ENABLED, String.valueOf(exportEnabled));
		prefs.setValue(PropsKeys.LDAP_USERS_DN, usersDN);
		prefs.setValue(PropsKeys.LDAP_GROUPS_DN, groupsDN);
		prefs.setValue(
			PropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
			String.valueOf(passwordPolicyEnabled));

		prefs.store();
	}

	protected void updateMailHostNames(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String mailHostNames = ParamUtil.getString(
			actionRequest, "mailHostNames");

		prefs.setValue(PropsKeys.ADMIN_MAIL_HOST_NAMES, mailHostNames);
	}

	protected void updateNtlm(
			ActionRequest actionRequest, long companyId,
			PortletPreferences prefs)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String domainController = ParamUtil.getString(
			actionRequest, "domainController");
		String domain = ParamUtil.getString(actionRequest, "domain");

		prefs.setValue(
			PropsKeys.NTLM_AUTH_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsKeys.NTLM_DOMAIN_CONTROLLER, domainController);
		prefs.setValue(PropsKeys.NTLM_DOMAIN, domain);

		prefs.store();
	}

	protected void updateOpenId(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");

		prefs.setValue(PropsKeys.OPEN_ID_AUTH_ENABLED, String.valueOf(enabled));

		prefs.store();
	}

	protected void updateOpenSSO(
			ActionRequest actionRequest, long companyId,
			PortletPreferences prefs)
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

		prefs.setValue(
			PropsKeys.OPEN_SSO_AUTH_ENABLED, String.valueOf(enabled));
		prefs.setValue(PropsKeys.OPEN_SSO_LOGIN_URL, loginUrl);
		prefs.setValue(PropsKeys.OPEN_SSO_LOGOUT_URL, logoutUrl);
		prefs.setValue(PropsKeys.OPEN_SSO_SERVICE_URL, serviceUrl);
		prefs.setValue(PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR, screenName);
		prefs.setValue(PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR, emailAddress);
		prefs.setValue(PropsKeys.OPEN_SSO_FIRST_NAME_ATTR, firstName);
		prefs.setValue(PropsKeys.OPEN_SSO_LAST_NAME_ATTR, lastName);

		prefs.store();
	}

	protected void updateReservedUsers(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String reservedScreenNames = ParamUtil.getString(
			actionRequest, "reservedScreenNames");
		String reservedEmailAddresses = ParamUtil.getString(
			actionRequest, "reservedEmailAddresses");

		prefs.setValue(
			PropsKeys.ADMIN_RESERVED_SCREEN_NAMES, reservedScreenNames);
		prefs.setValue(
			PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES, reservedEmailAddresses);
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

}