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

package com.liferay.portal.action;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.enterpriseadmin.util.EnterpriseAdminUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ChangeCredentialsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero Puras
 *
 */
public class ChangeCredentialsAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (cmd.equals(Constants.UPDATE)) {
			try {
				if (ParamUtil.getBoolean(request, "updatePassword")) {
					updatePassword(request, response);
				}

				if (ParamUtil.getBoolean(request, "updateReminderQuery")) {
					updateReminderQuery(request, response);
				}

				return mapping.findForward(ActionConstants.COMMON_REFERER);
			}
			catch (Exception e) {
				if (e instanceof UserPasswordException ||
					e instanceof UserReminderQueryException) {

					SessionErrors.add(request, e.getClass().getName(), e);

					return mapping.findForward("portal.change_credentials");
				}
				else if (e instanceof NoSuchUserException ||
						 e instanceof PrincipalException) {

					SessionErrors.add(request, e.getClass().getName());

					return mapping.findForward("portal.error");
				}
				else {
					PortalUtil.sendError(e, request, response);

					return null;
				}
			}
		}
		else {
			return mapping.findForward("portal.change_credentials");
		}
	}

	protected void updatePassword(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		long userId = PortalUtil.getUserId(request);
		String password1 = ParamUtil.getString(request, "password1");
		String password2 = ParamUtil.getString(request, "password2");
		boolean passwordReset = ParamUtil.getBoolean(request, "passwordReset");

		UserServiceUtil.updatePassword(
			userId, password1, password2, passwordReset);

		session.setAttribute(WebKeys.USER_PASSWORD, password1);
	}

	protected void updateReminderQuery(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long userId = PortalUtil.getUserId(request);
		String question = ParamUtil.getString(request, "reminderQueryQuestion");
		String answer = ParamUtil.getString(request, "reminderQueryAnswer");

		if (question.equals(EnterpriseAdminUtil.CUSTOM_QUESTION)) {
			question = ParamUtil.getString(
				request, "reminderQueryCustomQuestion");
		}

		UserServiceUtil.updateReminderQuery(userId, question, answer);
	}

}