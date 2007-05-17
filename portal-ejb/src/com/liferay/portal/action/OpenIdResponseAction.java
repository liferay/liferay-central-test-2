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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.io.IOException;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegResponse;

/**
 * <a href="OpenIdResponseAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class OpenIdResponseAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws IOException {

		try {
			User user = _readResponse(req);
		}
		catch (Exception e) {
			if (e instanceof ConsumerException ||
				e instanceof AssociationException ||
				e instanceof MessageException ||
				e instanceof DiscoveryException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portal.login");
			}
			else {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(Constants.COMMON_ERROR);
			}

		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String loginURL = PortalUtil.getPortalURL(req) +
			themeDisplay.getPathMain() + "/portal/login";

		res.sendRedirect(loginURL);

		return null;
	}

	private User _readResponse(
		HttpServletRequest req)
		throws ConsumerException, AssociationException, MessageException,
		DiscoveryException, SystemException, PortalException {

		HttpSession ses = req.getSession();

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		ParameterList params =
			new ParameterList(req.getParameterMap());

		DiscoveryInformation discovered =
			(DiscoveryInformation) ses.getAttribute("openid-disco");

		if (discovered == null) {
			return null;
		}

		StringBuffer receivingURL = req.getRequestURL();
		String queryString = req.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(req.getQueryString());
		}

		VerificationResult verification = manager.verify(
			receivingURL.toString(),
			params, discovered);

		Identifier verified = verification.getVerifiedId();

		if (verified == null) {
			return null;
		}

		AuthSuccess authSuccess =
			(AuthSuccess) verification.getAuthResponse();

		String firstName = null;
		String lastName = null;
		String email = null;

		if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext =
				authSuccess.getExtension(SRegMessage.OPENID_NS_SREG);

			if (ext instanceof SRegResponse) {
				SRegResponse sregResp = (SRegResponse) ext;

				String fullName = sregResp.
					getAttributeValue("fullname");
				firstName = fullName.substring(
					0, fullName.indexOf(StringPool.SPACE));
				lastName = fullName.substring(
					fullName.indexOf(StringPool.SPACE) + 1);

				email = sregResp.getAttributeValue("email");
			}
		}

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension ext =
				authSuccess.getExtension(AxMessage.OPENID_NS_AX);

			if (ext instanceof FetchResponse) {
				FetchResponse fetchResp = (FetchResponse) ext;

				firstName = _getFirstValue(
					fetchResp.getAttributeValues("firstName"));
				lastName = _getFirstValue(
					fetchResp.getAttributeValues("lastName"));
				email = _getFirstValue(
					fetchResp.getAttributeValues("email"));
			}
		}

		String screenName = OpenIdUtil.getScreenName(authSuccess.getIdentity());

		long companyId = PortalUtil.getCompanyId(req);
		Locale locale = req.getLocale();
		User user = null;

		try {
			user = UserLocalServiceUtil.getUserByScreenName(
				companyId, screenName);
		}
		catch (NoSuchUserException nsue) {
			if ((Validator.isNull(firstName) || Validator.isNull(lastName)
				|| Validator.isNull(email) )) {
				// TODO: Create form that asks for them
				_log.error("The OpenID provider did not send the required "
					+ "attributes to create an account");
				return null;
			}

			user = createUserAccount(
				companyId, firstName, lastName, email, screenName,
				locale);
		}

		ses.setAttribute(WebKeys.OPEN_ID_LOGIN, new Long(user.getUserId()));

		return user;
	}

	protected User createUserAccount(
		long companyId, String firstName, String lastName, String email,
		String screenName, Locale locale)
		throws PortalException, SystemException {
		long creatorUserId = 0;

		boolean autoPassword = false;
		String password1 = _PASSWORD;
		String password2 = _PASSWORD;

		boolean autoScreenName = false;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long organizationId = 0;
		long locationId = 0;
		boolean sendEmail = false;

		String middleName = StringPool.BLANK;

		return UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, email, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			sendEmail);
	}

	private String _getFirstValue(List values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return (String) values.get(0);
	}

	private static final String _PASSWORD = "password";

	private static Log _log = LogFactory.getLog(OpenIdResponseAction.class);

}