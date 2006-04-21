/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

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

		PortletPreferences prefs = AdminUtil.getPreferences(companyId);

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("updateDefaultGroupsAndRoles")) {
			updateDefaultGroupsAndRoles(req, prefs);
		}
		else if (cmd.equals("updateEmails")) {
			updateEmails(req, prefs);
		}
		else if (cmd.equals("updateMailHostNames")) {
			updateMailHostNames(req, prefs);
		}
		else if (cmd.equals("updateReservedUsers")) {
			updateReservedUsers(req, prefs);
		}

		if (SessionErrors.isEmpty(req)) {
			prefs.store();

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

		prefs.setValue("defaultGroupNames", defaultGroupNames);
		prefs.setValue("defaultRoleNames", defaultRoleNames);
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
					"email-user-added-enabled", emailUserAddedEnabled);
				prefs.setValue(
					"email-user-added-subject", emailUserAddedSubject);
				prefs.setValue("email-user-added-body", emailUserAddedBody);
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
					"email-password-sent-enabled", emailPasswordSentEnabled);
				prefs.setValue(
					"email-password-sent-subject", emailPasswordSentSubject);
				prefs.setValue(
					"email-password-sent-body", emailPasswordSentBody);
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
				prefs.setValue("email-from-name", emailFromName);
				prefs.setValue("email-from-address", emailFromAddress);
			}
		}
	}

	protected void updateMailHostNames(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String mailHostNames = ParamUtil.getString(req, "mailHostNames");

		prefs.setValue("mailHostNames", mailHostNames);
	}

	protected void updateReservedUsers(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String reservedUserIds = ParamUtil.getString(req, "reservedUserIds");
		String reservedEmailAddresses = ParamUtil.getString(
			req, "reservedEmailAddresses");

		prefs.setValue("reservedUserIds", reservedUserIds);
		prefs.setValue("reservedEmailAddresses", reservedEmailAddresses);
	}

	private static Log _log = LogFactory.getLog(EditUsersAction.class);

}