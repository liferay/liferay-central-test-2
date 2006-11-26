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

package com.liferay.portal.events;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.ReverseAjax;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.persistence.UserTrackerUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messaging.util.MessagingUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.HttpHeaders;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="LoginPostAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LoginPostAction extends Action {

	public void run(HttpServletRequest req, HttpServletResponse res)
		throws ActionException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Running " + req.getRemoteUser());
			}

			HttpSession ses = req.getSession();

			String companyId = PortalUtil.getCompanyId(req);
			String userId = PortalUtil.getUserId(req);

			if (GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.REVERSE_AJAX_ENABLED))) {

				ses.setAttribute(WebKeys.REVERSE_AJAX, new ReverseAjax());
			}

			MessagingUtil.createXMPPConnection(ses, userId);

			Map currentUsers =
				(Map)WebAppPool.get(companyId, WebKeys.CURRENT_USERS);

			boolean simultaenousLogins = GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.AUTH_SIMULTANEOUS_LOGINS), true);

			if (!simultaenousLogins) {
				Map.Entry[] currentUsersArray =
					(Map.Entry[])currentUsers.entrySet().toArray(
						new Map.Entry[0]);

				for (int i = 0; i < currentUsersArray.length; i++) {
					Map.Entry mapEntry = currentUsersArray[i];

					UserTracker userTracker = (UserTracker)mapEntry.getValue();

					if (userTracker.getUserId().equals(userId)) {

						// Disable old login

						userTracker.getHttpSession().setAttribute(
							WebKeys.STALE_SESSION, Boolean.TRUE);
					}
				}
			}

			UserTracker userTracker =
				(UserTracker)currentUsers.get(ses.getId());

			if ((userTracker == null) &&
				(GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.SESSION_TRACKER_MEMORY_ENABLED)))) {

				userTracker = UserTrackerUtil.create(ses.getId());

				userTracker.setCompanyId(companyId);
				userTracker.setUserId(req.getRemoteUser());
				userTracker.setModifiedDate(new Date());
				userTracker.setRemoteAddr(req.getRemoteAddr());
				userTracker.setRemoteHost(req.getRemoteHost());
				userTracker.setUserAgent(req.getHeader(HttpHeaders.USER_AGENT));
				userTracker.setHttpSession(ses);

				currentUsers.put(ses.getId(), userTracker);
			}

			if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
					LAYOUT_REMEMBER_SESSION_WINDOW_STATE_MAXIMIZED))) {

				Group group = GroupLocalServiceUtil.getUserGroup(
					companyId, userId);

				Iterator itr = LayoutLocalServiceUtil.getLayouts(
					Layout.PRIVATE + group.getGroupId()).iterator();

				while (itr.hasNext()) {
					Layout layout = (Layout)itr.next();

					LayoutTypePortlet layoutType =
						(LayoutTypePortlet)layout.getLayoutType();

					if (layoutType.hasStateMax()) {

						// Set the window state to normal for the maximized
						// portlet

						layoutType.resetStates();

						// Set the portlet mode to view because other modes may
						// require a maximized window state

						layoutType.resetModes();

						LayoutLocalServiceUtil.updateLayout(
							layout.getLayoutId(), layout.getOwnerId(),
							layout.getTypeSettings());
					}
				}
			}

			// Reset the locale

			ses.removeAttribute(Globals.LOCALE_KEY);

			// To manually set a path for the user to forward to, edit
			// portal.properties and set auth.forward.by.last.path to true.

			/*Map params = new HashMap();

			params.put("p_l_id", new String[] {"PRI.3.1"});

			LastPath lastPath = new LastPath("/c", "/portal/layout", params);

			ses.setAttribute(WebKeys.LAST_PATH, lastPath);*/
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static Log _log = LogFactory.getLog(LoginPostAction.class);

}