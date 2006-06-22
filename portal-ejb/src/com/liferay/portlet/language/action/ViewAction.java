/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.language.action;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.ParamUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;
		ActionResponseImpl resImpl = (ActionResponseImpl)res;

		HttpServletRequest httpReq = reqImpl.getHttpServletRequest();
		HttpServletResponse httpRes = resImpl.getHttpServletResponse();

		HttpSession httpSes = httpReq.getSession();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String languageId = ParamUtil.getString(req, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		List availableLocales = ListUtil.fromArray(
			LanguageUtil.getAvailableLocales());

		if (availableLocales.contains(locale)) {
			if (themeDisplay.isSignedIn()) {
				User user = themeDisplay.getUser();

				Contact contact = user.getContact();

				AdminUtil.updateUser(
					req, user.getUserId(), user.getEmailAddress(), languageId,
					user.getTimeZoneId(), user.getGreeting(),
					user.getResolution(), user.getComments(),
					contact.getSmsSn(), contact.getAimSn(), contact.getIcqSn(),
					contact.getJabberSn(), contact.getMsnSn(),
					contact.getSkypeSn(), contact.getYmSn());
			}

			httpSes.setAttribute(Globals.LOCALE_KEY, locale);

			LanguageUtil.updateCookie(httpRes, locale);
		}

		// Send redirect

		res.sendRedirect(PortalUtil.getLayoutURL(layout, themeDisplay));
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward("portlet.language.view");
	}

}