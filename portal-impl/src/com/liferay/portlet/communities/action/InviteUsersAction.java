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

package com.liferay.portlet.communities.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.MembershipInvitation;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.MembershipInvitationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="InviteUsersAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brett Swaim
 *
 */
public class InviteUsersAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		String[] addEmailAddresses = StringUtil.split(
			ParamUtil.getString(actionRequest, "addEmailAddresses"), ",");

		Map<String, String> entries = new HashMap<String, String>();

		for (String email : addEmailAddresses) {
			User invitee = UserLocalServiceUtil.getUserByEmailAddress(
				themeDisplay.getCompanyId(), email);

			MembershipInvitation invitation =
				MembershipInvitationLocalServiceUtil.addMembershipInvitation(
				themeDisplay.getCompanyId(), groupId, themeDisplay.getUserId(),
				invitee.getUserId());

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			Group guestGroup = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.GUEST);

			long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(
				guestGroup.getGroupId());

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.LOGIN, defaultPlid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("key", invitation.getKey());
			portletURL.setParameter("struts_action", "/login/login");

			portletURL.setWindowState(WindowState.MAXIMIZED);

			entries.put(email, portletURL.toString());
		}

		MembershipInvitationLocalServiceUtil.invite(
			themeDisplay.getCompanyId(), groupId, entries);

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(e);
				}

				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.communities.invite_users"));
	}

	private static Log _log = LogFactoryUtil.getLog(InviteUsersAction.class);

}