/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.util.LiveUsers;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messaging.util.MessagingUtil;
import com.liferay.util.GetterUtil;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="LoginPostAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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

			long companyId = PortalUtil.getCompanyId(req);
			long userId = PortalUtil.getUserId(req);

			if (GetterUtil.getBoolean(
					PropsUtil.get(PropsUtil.REVERSE_AJAX_ENABLED))) {

				ses.setAttribute(WebKeys.REVERSE_AJAX, new ReverseAjax());
			}

			MessagingUtil.createXMPPConnection(ses, userId);

			LiveUsers.signIn(req);

			if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
					LAYOUT_REMEMBER_SESSION_WINDOW_STATE_MAXIMIZED))) {

				Group group = GroupLocalServiceUtil.getUserGroup(
					companyId, userId);

				Iterator itr = LayoutLocalServiceUtil.getLayouts(
					LayoutImpl.PRIVATE + group.getGroupId()).iterator();

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
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static Log _log = LogFactory.getLog(LoginPostAction.class);

}