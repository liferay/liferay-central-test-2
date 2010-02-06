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

package com.liferay.portal.action;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LogoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LogoutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			HttpSession session = request.getSession();

			EventsProcessorUtil.process(
				PropsKeys.LOGOUT_EVENTS_PRE, PropsValues.LOGOUT_EVENTS_PRE,
				request, response);

			String domain = CookieKeys.getDomain(request);

			Cookie companyIdCookie = new Cookie(
				CookieKeys.COMPANY_ID, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				companyIdCookie.setDomain(domain);
			}

			companyIdCookie.setMaxAge(0);
			companyIdCookie.setPath(StringPool.SLASH);

			Cookie idCookie = new Cookie(CookieKeys.ID, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				idCookie.setDomain(domain);
			}

			idCookie.setMaxAge(0);
			idCookie.setPath(StringPool.SLASH);

			Cookie passwordCookie = new Cookie(
				CookieKeys.PASSWORD, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				passwordCookie.setDomain(domain);
			}

			passwordCookie.setMaxAge(0);
			passwordCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, companyIdCookie);
			CookieKeys.addCookie(request, response, idCookie);
			CookieKeys.addCookie(request, response, passwordCookie);

			try {
				session.invalidate();
			}
			catch (Exception e) {
			}

			EventsProcessorUtil.process(
				PropsKeys.LOGOUT_EVENTS_POST, PropsValues.LOGOUT_EVENTS_POST,
				request, response);

			return mapping.findForward(ActionConstants.COMMON_REFERER);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

}