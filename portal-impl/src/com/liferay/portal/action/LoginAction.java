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

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LoginAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (session.getAttribute("j_username") != null &&
			session.getAttribute("j_password") != null) {

			if (PropsValues.PORTAL_JAAS_ENABLE) {
				return mapping.findForward("/portal/touch_protected.jsp");
			}
			else {
				response.sendRedirect(themeDisplay.getPathMain());

				return null;
			}
		}

		String redirect = PortalUtil.getCommunityLoginURL(themeDisplay);

		if (Validator.isNull(redirect)) {
			redirect = PropsValues.AUTH_LOGIN_URL;
		}

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.LOGIN, themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("saveLastPath", "0");
			portletURL.setParameter("struts_action", "/login/login");

			redirect = portletURL.toString();
		}

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) {
			redirect = HttpUtil.protocolize(redirect, true);
		}

		String loginRedirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNotNull(loginRedirect)) {
			if (PrefsPropsUtil.getBoolean(
					themeDisplay.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED,
					PropsValues.CAS_AUTH_ENABLED)) {

				redirect = loginRedirect;
			}
			else {
				String loginPortletNamespace = PortalUtil.getPortletNamespace(
					PropsValues.AUTH_LOGIN_PORTLET_NAME);

				String loginRedirectParameter =
					loginPortletNamespace + "redirect";

				redirect = HttpUtil.setParameter(
					redirect, "p_p_id", PropsValues.AUTH_LOGIN_PORTLET_NAME);
				redirect = HttpUtil.setParameter(
					redirect, "p_p_lifecycle", "0");
				redirect = HttpUtil.setParameter(
					redirect, loginRedirectParameter, loginRedirect);
			}
		}

		response.sendRedirect(redirect);

		return null;
	}

}