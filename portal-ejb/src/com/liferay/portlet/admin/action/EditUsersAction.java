/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.action;

import com.liferay.portal.model.Role;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.spring.CompanyServiceUtil;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.LDAPUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditUsersAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditUsersAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String companyId = PortalUtil.getCompanyId(req);

		if (!RoleLocalServiceUtil.hasUserRole(
				req.getRemoteUser(), companyId, Role.ADMINISTRATOR)) {

			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.admin.error");

			return;
		}

		PortletPreferences prefs = PrefsPropsUtil.getPreferences(companyId);

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("updateDefaultGroupsAndRoles")) {
			updateDefaultGroupsAndRoles(req, prefs);
		}
		else if (cmd.equals("updateEmails")) {
			updateEmails(req, prefs);
		}
		else if (cmd.equals("updateLdap")) {
			updateLdap(req, prefs);
		}
		else if (cmd.equals("updateMailHostNames")) {
			updateMailHostNames(req, prefs);
		}
		else if (cmd.equals("updateReservedUsers")) {
			updateReservedUsers(req, prefs);
		}
		else if (cmd.equals("updateSecurity")) {
			updateSecurity(req);
		}

		if (SessionErrors.isEmpty(req)) {
			if (!cmd.equals("updateLdap") && !cmd.equals("updateSecurity")) {
				prefs.store();
			}

			sendRedirect(req, res);
		}
		else {
			setForward(req, "portlet.admin.view");
		}
	}

	protected void updateDefaultGroupsAndRoles(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String defaultGroupNames = ParamUtil.getString(
			req, "defaultGroupNames");
		String defaultRoleNames = ParamUtil.getString(req, "defaultRoleNames");
		String defaultUserGroupNames = ParamUtil.getString(
			req, "defaultUserGroupNames");

		prefs.setValue(PropsUtil.ADMIN_DEFAULT_GROUP_NAMES, defaultGroupNames);
		prefs.setValue(PropsUtil.ADMIN_DEFAULT_ROLE_NAMES, defaultRoleNames);
		prefs.setValue(
			PropsUtil.ADMIN_DEFAULT_USER_GROUP_NAMES, defaultUserGroupNames);
	}

	protected void updateEmails(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String tabs3 = ParamUtil.getString(req, "tabs3");

		if (tabs3.equals("user-added-email")) {
			String emailUserAddedEnabled = ParamUtil.getString(
				req, "emailUserAddedEnabled");
			String emailUserAddedSubject = ParamUtil.getString(
				req, "emailUserAddedSubject");
			String emailUserAddedBody = ParamUtil.getString(
				req, "emailUserAddedBody");

			if (Validator.isNull(emailUserAddedSubject)) {
				SessionErrors.add(req, "emailUserAddedSubject");
			}
			else if (Validator.isNull(emailUserAddedBody)) {
				SessionErrors.add(req, "emailUserAddedBody");
			}
			else {
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_USER_ADDED_ENABLED,
					emailUserAddedEnabled);
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_USER_ADDED_SUBJECT,
					emailUserAddedSubject);
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_USER_ADDED_BODY, emailUserAddedBody);
			}
		}
		else if (tabs3.equals("password-sent-email")) {
			String emailPasswordSentEnabled = ParamUtil.getString(
				req, "emailPasswordSentEnabled");
			String emailPasswordSentSubject = ParamUtil.getString(
				req, "emailPasswordSentSubject");
			String emailPasswordSentBody = ParamUtil.getString(
				req, "emailPasswordSentBody");

			if (Validator.isNull(emailPasswordSentSubject)) {
				SessionErrors.add(req, "emailPasswordSentSubject");
			}
			else if (Validator.isNull(emailPasswordSentBody)) {
				SessionErrors.add(req, "emailPasswordSentBody");
			}
			else {
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_ENABLED,
					emailPasswordSentEnabled);
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT,
					emailPasswordSentSubject);
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_PASSWORD_SENT_BODY,
					emailPasswordSentBody);
			}
		}
		else {
			String emailFromName = ParamUtil.getString(req, "emailFromName");
			String emailFromAddress = ParamUtil.getString(
				req, "emailFromAddress");

			if (Validator.isNull(emailFromName)) {
				SessionErrors.add(req, "emailFromName");
			}
			else if (!Validator.isEmailAddress(emailFromAddress)) {
				SessionErrors.add(req, "emailFromAddress");
			}
			else {
				prefs.setValue(PropsUtil.ADMIN_EMAIL_FROM_NAME, emailFromName);
				prefs.setValue(
					PropsUtil.ADMIN_EMAIL_FROM_ADDRESS, emailFromAddress);
			}
		}
	}

	protected void updateLdap(ActionRequest req, PortletPreferences prefs)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(req, "enabled");
		boolean required = ParamUtil.getBoolean(req, "required");
		String baseProviderURL = ParamUtil.getString(req, "base_provider_url");
		String baseDN = ParamUtil.getString(req, "base_dn");
		String principal = ParamUtil.getString(req, "principal");
		String credentials = ParamUtil.getString(req, "credentials");
		String searchFilter = ParamUtil.getString(req, "searchFilter");
		String passwordEncryptionAlgorithm = ParamUtil.getString(
			req, "passwordEncryptionAlgorithm");
		String userMappings = ParamUtil.getString(req, "userMappings");

		try {
			if (enabled) {
				Properties env = new Properties();

				env.put(
					Context.INITIAL_CONTEXT_FACTORY,
					PrefsPropsUtil.getString(
						PropsUtil.AUTH_IMPL_LDAP_FACTORY_INITIAL));
				env.put(Context.PROVIDER_URL, LDAPUtil.getFullProviderURL(
					baseProviderURL, baseDN));
				env.put(Context.SECURITY_PRINCIPAL, principal);
				env.put(Context.SECURITY_CREDENTIALS, credentials);

				if (_log.isDebugEnabled()) {
					StringWriter sw = new StringWriter();

					env.list(new PrintWriter(sw));

					_log.debug(sw.getBuffer().toString());
				}

				new InitialLdapContext(env, null);
			}
		}
		catch (Exception e) {
			SessionErrors.add(req, "ldapAuthentication");

			return;
		}

		prefs.setValue(
			PropsUtil.AUTH_IMPL_LDAP_ENABLED, Boolean.toString(enabled));
		prefs.setValue(
			PropsUtil.AUTH_IMPL_LDAP_REQUIRED, Boolean.toString(required));
		prefs.setValue(PropsUtil.AUTH_IMPL_LDAP_BASE_PROVIDER_URL, 
			baseProviderURL);
		prefs.setValue(PropsUtil.AUTH_IMPL_LDAP_BASE_DN, baseDN);
		prefs.setValue(PropsUtil.AUTH_IMPL_LDAP_SECURITY_PRINCIPAL, principal);
		prefs.setValue(
			PropsUtil.AUTH_IMPL_LDAP_SECURITY_CREDENTIALS, credentials);
		prefs.setValue(PropsUtil.AUTH_IMPL_LDAP_SEARCH_FILTER, searchFilter);
		prefs.setValue(
			PropsUtil.AUTH_IMPL_LDAP_PASSWORD_ENCRYPTION_ALGORITHM,
			passwordEncryptionAlgorithm);
		prefs.setValue(PropsUtil.AUTH_IMPL_LDAP_USER_MAPPINGS, userMappings);

		prefs.store();
	}

	protected void updateMailHostNames(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String mailHostNames = ParamUtil.getString(req, "mailHostNames");

		prefs.setValue(PropsUtil.ADMIN_MAIL_HOST_NAMES, mailHostNames);
	}

	protected void updateReservedUsers(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String reservedUserIds = ParamUtil.getString(req, "reservedUserIds");
		String reservedEmailAddresses = ParamUtil.getString(
			req, "reservedEmailAddresses");

		prefs.setValue(PropsUtil.ADMIN_RESERVED_USER_IDS, reservedUserIds);
		prefs.setValue(
			PropsUtil.ADMIN_RESERVED_EMAIL_ADDRESSES, reservedEmailAddresses);
	}

	protected void updateSecurity(ActionRequest req) throws Exception {
		String companyId = PortalUtil.getCompanyId(req);

		String authType = ParamUtil.getString(req, "authType");
		boolean autoLogin = ParamUtil.getBoolean(req, "autoLogin");
		boolean sendPassword = ParamUtil.getBoolean(req, "sendPassword");
		boolean strangers = ParamUtil.getBoolean(req, "strangers");

		CompanyServiceUtil.updateSecurity(
			companyId, authType, autoLogin, sendPassword, strangers);
	}

	private static Log _log = LogFactory.getLog(EditUsersAction.class);

}