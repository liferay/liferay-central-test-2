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

package com.liferay.portal.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="TCKAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TCKAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			if (!PropsValues.TCK_URL) {
				throw new PrincipalException("TCK testing is disabled");
			}

			User user = _getUser(request);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String[] portletIds = request.getParameterValues("portletId");

			if (portletIds == null) {
				portletIds = request.getParameterValues("portletName");
			}

			for (int i = 0; i < portletIds.length; i++) {
				String[] nameAndWar = StringUtil.split(portletIds[i], "/");

				portletIds[i] = PortalUtil.getJsSafePortletId(
					nameAndWar[1] + PortletConstants.WAR_SEPARATOR +
						nameAndWar[0]);
			}

			long userId = user.getUserId();
			long groupId = user.getGroup().getGroupId();

			ServiceContext serviceContext = new ServiceContext();

			Layout layout = LayoutLocalServiceUtil.addLayout(
				userId, groupId, false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "TCKAction",
				StringPool.BLANK, StringPool.BLANK,
				LayoutConstants.TYPE_PORTLET, false, StringPool.BLANK,
				serviceContext);

			LayoutTypePortlet layoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			for (String portletId : portletIds) {
				layoutType.addPortletId(userId, portletId, false);

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

				ResourceLocalServiceUtil.addResources(
					user.getCompanyId(), groupId, 0, rootPortletId,
					portletPrimaryKey, true, true, true);
			}

			LayoutLocalServiceUtil.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());

			request.setAttribute(
				WebKeys.FORWARD_URL,
				themeDisplay.getPathMain() + "/portal/layout?p_l_id=" +
					layout.getPlid());

			return mapping.findForward(ActionConstants.COMMON_FORWARD_JSP);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	private User _getUser(HttpServletRequest request) throws Exception {
		long companyId = PortalUtil.getCompanyId(request);

		try {
			return UserLocalServiceUtil.getUserByScreenName(companyId, "tck");
		}
		catch (Exception e) {
			long creatorUserId = 0;
			boolean autoPassword = false;
			String password1 = "password";
			String password2 = password1;
			boolean autoScreenName = false;
			String screenName = "tck";
			String emailAddress = "tck@liferay.com";
			String openId = StringPool.BLANK;
			Locale locale = Locale.US;
			String firstName = "TCK";
			String middleName = StringPool.BLANK;
			String lastName = "User";
			int prefixId = 0;
			int suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;
			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			long[] userGroupIds = null;
			boolean sendEmail = false;

			ServiceContext serviceContext = new ServiceContext();

			return UserLocalServiceUtil.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, openId, locale,
				firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
				organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TCKAction.class);

}