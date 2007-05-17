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

package com.liferay.portal.action;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

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

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
	{

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String openId = ParamUtil.getString(req, "openId");

		try {
			sendOpenIdRequest(themeDisplay, req, res, openId);
		}
		catch (Exception e) {
			if (e instanceof ConsumerException ||
				e instanceof DiscoveryException ||
				e instanceof MessageException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portal.login");
			}
			else {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(Constants.COMMON_ERROR);
			}

		}

		return mapping.findForward("portal.login");
	}

	public static void sendOpenIdRequest(
		ThemeDisplay themeDisplay, HttpServletRequest req ,
		HttpServletResponse res, String openId)
		throws ConsumerException, DiscoveryException, IOException,
		MessageException, PortalException, SystemException {

		String returnToUrl = PortalUtil.getPortalURL(req) +
			themeDisplay.getPathMain() + "/portal/open_id_response";

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		List discoveries = manager.discover(openId);

		DiscoveryInformation discovered = manager.associate(discoveries);

		req.getSession().setAttribute("openid-disco", discovered);

		AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

		if (_processUser(themeDisplay.getCompanyId(), openId)) {
			FetchRequest fetch = FetchRequest.createFetchRequest();
			fetch.addAttribute(
				"email", "http://schema.openid.net/contact/email", true);
			fetch.addAttribute(
				"firstName", "http://schema.openid.net/namePerson/first", true);
			fetch.addAttribute(
				"lastName", "http://schema.openid.net/namePerson/last", true);

			authReq.addExtension(fetch);

			SRegRequest sregReq = SRegRequest.createFetchRequest();

			sregReq.addAttribute("fullname", true);
			sregReq.addAttribute("email", true);

			authReq.addExtension(sregReq);
		}

		res.sendRedirect(authReq.getDestinationUrl(true));
	}

	private static boolean _processUser(long companyId, String openId)
		throws SystemException, PortalException {
		String screenName = OpenIdUtil.getScreenName(openId);

		try {
			UserLocalServiceUtil.getUserByScreenName(
				companyId, screenName);
		}
		catch (NoSuchUserException nsue) {
			return true;
		}
		return false;

	}

}