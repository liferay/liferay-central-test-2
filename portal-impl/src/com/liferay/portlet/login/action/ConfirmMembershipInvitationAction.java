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

package com.liferay.portlet.login.action;

import com.liferay.portal.MembershipInvitationAlreadyUsedException;
import com.liferay.portal.MembershipInvitationUserNotInvitedException;
import com.liferay.portal.NoSuchMembershipInvitationException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.MembershipInvitationLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ConfirmMembershipInvitationAction.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brett Swaim
 *
 */
public class ConfirmMembershipInvitationAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		boolean accept = ParamUtil.getBoolean(actionRequest, "accept", false);
		String key = ParamUtil.getString(actionRequest, "key");
		long userId = PortalUtil.getUserId(actionRequest);

		try {
			if (accept) {
				MembershipInvitationLocalServiceUtil.accept(key, userId);
			}
			else {
				MembershipInvitationLocalServiceUtil.decline(key, userId);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				actionResponse.sendRedirect(StringPool.SLASH);
			}
		}
		catch (Exception e) {
			if (e instanceof MembershipInvitationAlreadyUsedException ||
				e instanceof MembershipInvitationUserNotInvitedException ||
				e instanceof NoSuchMembershipInvitationException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);

				actionResponse.setWindowState(WindowState.MAXIMIZED);
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

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.login.confirm_membership_invitation"));
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;

	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}