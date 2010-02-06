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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.DuplicatePasswordPolicyException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.PasswordPolicyNameException;
import com.liferay.portal.RequiredPasswordPolicyException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PasswordPolicyServiceUtil;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPasswordPolicyAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Scott Lee
 */
public class EditPasswordPolicyAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updatePasswordPolicy(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deletePasswordPolicy(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else if (e instanceof DuplicatePasswordPolicyException ||
					 e instanceof PasswordPolicyNameException ||
					 e instanceof NoSuchPasswordPolicyException ||
					 e instanceof RequiredPasswordPolicyException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				if (cmd.equals(Constants.DELETE)) {
					actionResponse.sendRedirect(
						ParamUtil.getString(actionRequest, "redirect"));
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getPasswordPolicy(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPasswordPolicyException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_password_policy"));
	}

	protected void deletePasswordPolicy(ActionRequest actionRequest)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
	}

	protected void updatePasswordPolicy(ActionRequest actionRequest)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean changeable = ParamUtil.getBoolean(actionRequest, "changeable");
		boolean changeRequired = ParamUtil.getBoolean(
			actionRequest, "changeRequired");
		long minAge = ParamUtil.getLong(actionRequest, "minAge");
		boolean checkSyntax = ParamUtil.getBoolean(
			actionRequest, "checkSyntax");
		boolean allowDictionaryWords = ParamUtil.getBoolean(
			actionRequest, "allowDictionaryWords");
		int minLength = ParamUtil.getInteger(actionRequest, "minLength");
		boolean history = ParamUtil.getBoolean(actionRequest, "history");
		int historyCount = ParamUtil.getInteger(actionRequest, "historyCount");
		boolean expireable = ParamUtil.getBoolean(actionRequest, "expireable");
		long maxAge = ParamUtil.getLong(actionRequest, "maxAge");
		long warningTime = ParamUtil.getLong(actionRequest, "warningTime");
		int graceLimit = ParamUtil.getInteger(actionRequest, "graceLimit");
		boolean lockout = ParamUtil.getBoolean(actionRequest, "lockout");
		int maxFailure = ParamUtil.getInteger(actionRequest, "maxFailure");
		long lockoutDuration = ParamUtil.getLong(
			actionRequest, "lockoutDuration");
		long resetFailureCount = ParamUtil.getLong(
			actionRequest, "resetFailureCount");

		if (passwordPolicyId <= 0) {

			// Add password policy

			PasswordPolicyServiceUtil.addPasswordPolicy(
				name, description, changeable, changeRequired, minAge,
				checkSyntax, allowDictionaryWords, minLength, history,
				historyCount, expireable, maxAge, warningTime, graceLimit,
				lockout, maxFailure, lockoutDuration, resetFailureCount);
		}
		else {

			// Update password policy

			PasswordPolicyServiceUtil.updatePasswordPolicy(
				passwordPolicyId, name, description, changeable, changeRequired,
				minAge, checkSyntax, allowDictionaryWords, minLength, history,
				historyCount, expireable, maxAge, warningTime, graceLimit,
				lockout, maxFailure, lockoutDuration, resetFailureCount);
		}
	}

}