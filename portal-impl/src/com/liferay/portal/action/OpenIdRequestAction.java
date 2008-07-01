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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.sreg.SRegRequest;

/**
 * <a href="OpenIdRequestAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class OpenIdRequestAction extends Action {

	public static void sendOpenIdRequest(
			ThemeDisplay themeDisplay, HttpServletRequest request,
			HttpServletResponse response, String openId)
		throws Exception {

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			return;
		}

		HttpSession ses = request.getSession();

		String returnURL =
			PortalUtil.getPortalURL(request) + themeDisplay.getPathMain() +
				"/portal/open_id_response";

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		List<DiscoveryInformation> discoveries = manager.discover(openId);

		DiscoveryInformation discovered = manager.associate(discoveries);

		ses.setAttribute(WebKeys.OPEN_ID_DISCO, discovered);

		AuthRequest authRequest = manager.authenticate(discovered, returnURL);

		try {
			UserLocalServiceUtil.getUserByOpenId(openId);
		}
		catch (NoSuchUserException nsue1) {
			String screenName = OpenIdUtil.getScreenName(openId);

			try {
				User user = UserLocalServiceUtil.getUserByScreenName(
					themeDisplay.getCompanyId(), screenName);

				UserLocalServiceUtil.updateOpenId(user.getUserId(), openId);
			}
			catch (NoSuchUserException nsue2) {
				FetchRequest fetch = FetchRequest.createFetchRequest();

				fetch.addAttribute(
					"email", "http://schema.openid.net/contact/email", true);
				fetch.addAttribute(
					"firstName", "http://schema.openid.net/namePerson/first",
					true);
				fetch.addAttribute(
					"lastName", "http://schema.openid.net/namePerson/last",
					true);

				authRequest.addExtension(fetch);

				SRegRequest sregRequest = SRegRequest.createFetchRequest();

				sregRequest.addAttribute("fullname", true);
				sregRequest.addAttribute("email", true);

				authRequest.addExtension(sregRequest);
			}
		}

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			return null;
		}

		try {
			String openId = ParamUtil.getString(request, "openId");

			sendOpenIdRequest(themeDisplay, request, response, openId);
		}
		catch (Exception e) {
			if (e instanceof ConsumerException ||
				e instanceof DiscoveryException ||
				e instanceof MessageException) {

				SessionErrors.add(request, e.getClass().getName());

				return mapping.findForward("portal.login");
			}
			else {
				PortalUtil.sendError(e, request, response);

				return null;
			}
		}

		return mapping.findForward("portal.login");
	}

}