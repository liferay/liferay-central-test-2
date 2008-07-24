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

import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SendPasswordException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.captcha.CaptchaUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.Encryptor;
import com.liferay.util.servlet.SessionParameters;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LoginAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class LoginAction extends Action {

	public static String getLogin(
			HttpServletRequest request, String paramName, Company company)
		throws PortalException, SystemException {

		String login = request.getParameter(paramName);

		if ((login == null) || (login.equals(StringPool.NULL))) {
			login = GetterUtil.getString(
				CookieKeys.getCookie(request, CookieKeys.LOGIN));

			if (Validator.isNull(login) &&
				company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {

				login = "@" + company.getMx();
			}
		}

		return login;
	}

	public static void login(
			HttpServletRequest request, HttpServletResponse response,
			String login, String password, boolean rememberMe)
		throws Exception {

		CookieKeys.validateSupportCookie(request);

		HttpSession session = request.getSession();

		long userId = GetterUtil.getLong(login);

		int authResult = Authenticator.FAILURE;

		Company company = PortalUtil.getCompany(request);

		Map<String, String[]> headerMap = new HashMap<String, String[]>();

		Enumeration<String> enu1 = request.getHeaderNames();

		while (enu1.hasMoreElements()) {
			String name = enu1.nextElement();

			Enumeration<String> enu2 = request.getHeaders(name);

			List<String> headers = new ArrayList<String>();

			while (enu2.hasMoreElements()) {
				String value = enu2.nextElement();

				headers.add(value);
			}

			headerMap.put(name, headers.toArray(new String[headers.size()]));
		}

		Map<String, String[]> parameterMap = request.getParameterMap();

		if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {
			authResult = UserLocalServiceUtil.authenticateByEmailAddress(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);

			userId = UserLocalServiceUtil.getUserIdByEmailAddress(
				company.getCompanyId(), login);
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_SN)) {
			authResult = UserLocalServiceUtil.authenticateByScreenName(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);

			userId = UserLocalServiceUtil.getUserIdByScreenName(
				company.getCompanyId(), login);
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_ID)) {
			authResult = UserLocalServiceUtil.authenticateByUserId(
				company.getCompanyId(), userId, password, headerMap,
				parameterMap);
		}

		if (authResult == Authenticator.SUCCESS) {
			if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {

				// Invalidate the previous session to prevent phishing

				Boolean httpsInitial = (Boolean)session.getAttribute(
					WebKeys.HTTPS_INITIAL);

				LastPath lastPath = (LastPath)session.getAttribute(
					WebKeys.LAST_PATH);

				try {
					session.invalidate();
				}
				catch (IllegalStateException ise) {

					// This only happens in Geronimo

					if (_log.isWarnEnabled()) {
						_log.warn(ise.getMessage());
					}
				}

				session = request.getSession(true);

				if (httpsInitial != null) {
					session.setAttribute(WebKeys.HTTPS_INITIAL, httpsInitial);
				}

				if (lastPath != null) {
					session.setAttribute(WebKeys.LAST_PATH, lastPath);
				}
			}

			//Adding RUON hooks

			JSONObject ruonJSON =
					JSONFactoryUtil.createJSONObject();

			JSONObject setPresenceStatusRequestJSON =
					JSONFactoryUtil.createJSONObject();

			setPresenceStatusRequestJSON.put("userId",userId);
			setPresenceStatusRequestJSON.put("status","online");

			ruonJSON.put(
					"setPresenceStatusRequest",setPresenceStatusRequestJSON);

			MessageBusUtil.sendSynchronizedMessage(
							DestinationNames.RUON_WEB, ruonJSON.toString());

			// Set cookies

			String domain = CookieKeys.getDomain(request);

			User user = UserLocalServiceUtil.getUserById(userId);

			String userIdString = String.valueOf(userId);

			session.setAttribute("j_username", userIdString);
			session.setAttribute("j_password", user.getPassword());
			session.setAttribute("j_remoteuser", userIdString);

			session.setAttribute(WebKeys.USER_PASSWORD, password);

			Cookie companyIdCookie = new Cookie(
				CookieKeys.COMPANY_ID, String.valueOf(company.getCompanyId()));

			if (Validator.isNotNull(domain)) {
				companyIdCookie.setDomain(domain);
			}

			companyIdCookie.setPath(StringPool.SLASH);

			Cookie idCookie = new Cookie(
				CookieKeys.ID,
				UserLocalServiceUtil.encryptUserId(userIdString));

			if (Validator.isNotNull(domain)) {
				idCookie.setDomain(domain);
			}

			idCookie.setPath(StringPool.SLASH);

			Cookie passwordCookie = new Cookie(
				CookieKeys.PASSWORD,
				Encryptor.encrypt(company.getKeyObj(), password));

			if (Validator.isNotNull(domain)) {
				passwordCookie.setDomain(domain);
			}

			passwordCookie.setPath(StringPool.SLASH);

			Cookie rememberMeCookie = new Cookie(
				CookieKeys.REMEMBER_ME, Boolean.TRUE.toString());

			if (Validator.isNotNull(domain)) {
				rememberMeCookie.setDomain(domain);
			}

			rememberMeCookie.setPath(StringPool.SLASH);

			int loginMaxAge = PropsValues.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE;

			if (PropsValues.SESSION_DISABLED) {
				rememberMe = true;
			}

			if (rememberMe) {
				companyIdCookie.setMaxAge(loginMaxAge);
				idCookie.setMaxAge(loginMaxAge);
				passwordCookie.setMaxAge(loginMaxAge);
				rememberMeCookie.setMaxAge(loginMaxAge);
			}
			else {

				// This was explicitly changed from 0 to -1 so that the cookie
				// lasts as long as the browser. This allows an external servlet
				// wrapped in AutoLoginFilter to work throughout the client
				// connection. The cookies ARE removed on an actual logout, so
				// there is no security issue. See LEP-4678 and LEP-5177.

				companyIdCookie.setMaxAge(-1);
				idCookie.setMaxAge(-1);
				passwordCookie.setMaxAge(-1);
				rememberMeCookie.setMaxAge(0);
			}

			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

			if (Validator.isNotNull(domain)) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(loginMaxAge);
			loginCookie.setPath(StringPool.SLASH);

			Cookie screenNameCookie = new Cookie(
				CookieKeys.SCREEN_NAME,
				Encryptor.encrypt(company.getKeyObj(), user.getScreenName()));

			if (Validator.isNotNull(domain)) {
				screenNameCookie.setDomain(domain);
			}

			screenNameCookie.setMaxAge(loginMaxAge);
			screenNameCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(response, companyIdCookie);
			CookieKeys.addCookie(response, idCookie);
			CookieKeys.addCookie(response, passwordCookie);
			CookieKeys.addCookie(response, rememberMeCookie);
			CookieKeys.addCookie(response, loginCookie);
			CookieKeys.addCookie(response, screenNameCookie);
		}
		else {
			throw new AuthException();
		}
	}

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!request.isSecure()) {

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			StringBuilder sb = new StringBuilder();

			sb.append(PortalUtil.getPortalURL(request, true));
			sb.append(themeDisplay.getURLSignIn());

			response.sendRedirect(sb.toString());

			return null;
		}

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

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (cmd.equals("already-registered")) {
			try {
				login(request, response);

				if (PropsValues.PORTAL_JAAS_ENABLE) {
					return mapping.findForward("/portal/touch_protected.jsp");
				}
				else {
					String redirect = ParamUtil.getString(request, "redirect");

					if (Validator.isNotNull(redirect)) {
						response.sendRedirect(redirect);
					}
					else {
						response.sendRedirect(themeDisplay.getPathMain());
					}

					return null;
				}
			}
			catch (Exception e) {
				if (e instanceof AuthException) {
					Throwable cause = e.getCause();

					if (cause instanceof PasswordExpiredException ||
						cause instanceof UserLockoutException) {

						SessionErrors.add(request, cause.getClass().getName());
					}
					else {
						SessionErrors.add(request, e.getClass().getName());
					}

					return mapping.findForward("portal.login");
				}
				else if (e instanceof CookieNotSupportedException ||
						 e instanceof NoSuchUserException ||
						 e instanceof PasswordExpiredException ||
						 e instanceof UserEmailAddressException ||
						 e instanceof UserIdException ||
						 e instanceof UserLockoutException ||
						 e instanceof UserPasswordException ||
						 e instanceof UserScreenNameException) {

					SessionErrors.add(request, e.getClass().getName());

					return mapping.findForward("portal.login");
				}
				else {
					PortalUtil.sendError(e, request, response);

					return null;
				}
			}
		}
		else if (cmd.equals("forgot-password")) {
			try {
				sendPassword(request);

				return mapping.findForward("portal.login");
			}
			catch (Exception e) {
				if (e instanceof CaptchaTextException ||
					e instanceof NoSuchUserException ||
					e instanceof SendPasswordException ||
					e instanceof UserEmailAddressException) {

					SessionErrors.add(request, e.getClass().getName());

					return mapping.findForward("portal.login");
				}
				else {
					PortalUtil.sendError(e, request, response);

					return null;
				}
			}
		}
		else {
			String authLoginURL = PortalUtil.getCommunityLoginURL(themeDisplay);

			if (Validator.isNull(authLoginURL)) {
				authLoginURL = PropsValues.AUTH_LOGIN_URL;
			}

			if (Validator.isNotNull(authLoginURL)) {
				response.sendRedirect(authLoginURL);

				return null;
			}
			else {
				return mapping.findForward("portal.login");
			}
		}
	}

	protected void login(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String login = ParamUtil.getString(request, "login").toLowerCase();
		String password = ParamUtil.getString(
			request, SessionParameters.get(request, "password"));
		boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");

		login(request, response, login, password, rememberMe);
	}

	protected void sendPassword(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPassword()) {
			return;
		}

		if (PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD) {
			CaptchaUtil.check(request);
		}

		String emailAddress = ParamUtil.getString(request, "emailAddress");

		String remoteAddr = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		UserLocalServiceUtil.sendPassword(
			PortalUtil.getCompanyId(request), emailAddress, remoteAddr,
			remoteHost, userAgent);

		SessionMessages.add(request, "request_processed", emailAddress);
	}

	private static Log _log = LogFactory.getLog(LoginAction.class);

}