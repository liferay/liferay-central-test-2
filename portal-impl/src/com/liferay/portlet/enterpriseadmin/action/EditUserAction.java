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

import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.ReservedUserScreenNameException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditUserAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditUserAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			User user = null;
			String oldScreenName = StringPool.BLANK;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateUser(actionRequest);

				user = (User)returnValue[0];
				oldScreenName = ((String)returnValue[1]);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.RESTORE)) {

				deleteUsers(actionRequest);
			}
			else if (cmd.equals("deleteRole")) {
				deleteRole(actionRequest);
			}
			else if (cmd.equals("unlock")) {
				user = updateLockout(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (user != null) {
				if (Validator.isNotNull(oldScreenName)) {

					// This will fix the redirect if the user is on his personal
					// my account page and changes his screen name. A redirect
					// that references the old screen name no longer points to a
					// valid screen name and therefore needs to be updated.

					ThemeDisplay themeDisplay =
						(ThemeDisplay)actionRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					Group group = user.getGroup();

					if (group.getGroupId() ==
							themeDisplay.getPortletGroupId()) {

						Layout layout = themeDisplay.getLayout();

						String friendlyURLPath = group.getPathFriendlyURL(
							layout.isPrivateLayout(), themeDisplay);

						String oldPath =
							friendlyURLPath + StringPool.SLASH + oldScreenName;
						String newPath =
							friendlyURLPath + StringPool.SLASH +
								user.getScreenName();

						redirect = StringUtil.replace(
							redirect, oldPath, newPath);

						redirect = StringUtil.replace(
							redirect, HttpUtil.encodeURL(oldPath),
							HttpUtil.encodeURL(newPath));
					}
				}

				redirect += user.getUserId();
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else if (e instanceof ContactFirstNameException ||
					 e instanceof ContactLastNameException ||
					 e instanceof DuplicateUserEmailAddressException ||
					 e instanceof DuplicateUserScreenNameException ||
					 e instanceof RequiredUserException ||
					 e instanceof ReservedUserEmailAddressException ||
					 e instanceof ReservedUserScreenNameException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserIdException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserScreenNameException ||
					 e instanceof UserSmsException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);

				if (e instanceof RequiredUserException) {
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
			PortalUtil.getSelectedUser(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.enterprise_admin.edit_user"));
	}

	protected void deleteRole(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		UserServiceUtil.deleteRoleUser(roleId, user.getUserId());
	}

	protected void deleteUsers(ActionRequest actionRequest) throws Exception {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long[] deleteUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteUserIds"), 0L);

		for (int i = 0; i < deleteUserIds.length; i++) {
			if (cmd.equals(Constants.DEACTIVATE) ||
				cmd.equals(Constants.RESTORE)) {

				boolean active = !cmd.equals(Constants.DEACTIVATE);

				UserServiceUtil.updateActive(deleteUserIds[i], active);
			}
			else {
				UserServiceUtil.deleteUser(deleteUserIds[i]);
			}
		}
	}

	protected User updateLockout(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		UserServiceUtil.updateLockout(user.getUserId(), false);

		return user;
	}

	protected Object[] updateUser(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = ParamUtil.getBoolean(
			actionRequest, "autoPassword", true);
		String password1 = ParamUtil.getString(actionRequest, "password1");
		String password2 = ParamUtil.getString(actionRequest, "password2");
		boolean autoScreenName = ParamUtil.getBoolean(
			actionRequest, "autoScreenName");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		String greeting = ParamUtil.getString(actionRequest, "greeting");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		int prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		int suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String comments = ParamUtil.getString(actionRequest, "comments");
		String smsSn = ParamUtil.getString(actionRequest, "smsSn");
		String aimSn = ParamUtil.getString(actionRequest, "aimSn");
		String facebookSn = ParamUtil.getString(actionRequest, "facebookSn");
		String icqSn = ParamUtil.getString(actionRequest, "icqSn");
		String jabberSn = ParamUtil.getString(actionRequest, "jabberSn");
		String msnSn = ParamUtil.getString(actionRequest, "msnSn");
		String mySpaceSn = ParamUtil.getString(actionRequest, "mySpaceSn");
		String skypeSn = ParamUtil.getString(actionRequest, "skypeSn");
		String twitterSn = ParamUtil.getString(actionRequest, "twitterSn");
		String ymSn = ParamUtil.getString(actionRequest, "ymSn");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] organizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "organizationIds"),  0L);
		boolean sendEmail = true;

		User user = null;
		String oldScreenName = StringPool.BLANK;

		if (cmd.equals(Constants.ADD)) {

			// Add user

			user = UserServiceUtil.addUser(
				themeDisplay.getCompanyId(), autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress,
				themeDisplay.getLocale(), firstName, middleName, lastName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, organizationIds, sendEmail);
		}
		else {

			// Update user

			user = PortalUtil.getSelectedUser(actionRequest);

			String oldPassword = AdminUtil.getUpdateUserPassword(
				actionRequest, user.getUserId());
			String newPassword1 = ParamUtil.getString(
				actionRequest, "password1");
			String newPassword2 = ParamUtil.getString(
				actionRequest, "password2");
			boolean passwordReset = ParamUtil.getBoolean(
				actionRequest, "passwordReset");

			String tempOldScreenName = user.getScreenName();

			user = UserServiceUtil.updateUser(
				user.getUserId(), oldPassword, newPassword1, newPassword2,
				passwordReset, screenName, emailAddress, languageId, timeZoneId,
				greeting, comments, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear, smsSn,
				aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn,
				twitterSn, ymSn, jobTitle, organizationIds);

			String openId = ParamUtil.getString(actionRequest, "openId");

			if (!openId.equals(user.getOpenId())) {
				UserServiceUtil.updateOpenId(user.getUserId(), openId);
			}

			for (String type : AnnouncementsEntryImpl.TYPES) {
				boolean email = ParamUtil.getBoolean(
					actionRequest, "announcementsType" + type + "Email");
				boolean sms = ParamUtil.getBoolean(
					actionRequest, "announcementsType" + type + "Sms");
				boolean website = ParamUtil.getBoolean(
					actionRequest, "announcementsType" + type + "Website");

				AnnouncementsDeliveryServiceUtil.updateDelivery(
					user.getUserId(), type,	email, sms, website);
			}

			if (!tempOldScreenName.equals(user.getScreenName())) {
				oldScreenName = tempOldScreenName;
			}

			if (user.getUserId() == themeDisplay.getUserId()) {

				// Reset the locale

				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					actionRequest);
				HttpSession session = request.getSession();

				session.removeAttribute(Globals.LOCALE_KEY);

				// Clear cached portlet responses

				PortletSession portletSession =
					actionRequest.getPortletSession();

				InvokerPortlet.clearResponses(portletSession);

				// Password

				if (Validator.isNotNull(newPassword1)) {
					portletSession.setAttribute(
						WebKeys.USER_PASSWORD, newPassword1,
						PortletSession.APPLICATION_SCOPE);
				}
			}
		}

		return new Object[] {user, oldScreenName};
	}

}