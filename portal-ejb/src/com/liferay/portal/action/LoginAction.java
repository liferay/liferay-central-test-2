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

package com.liferay.portal.action;

import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SendPasswordException;
import com.liferay.portal.SystemException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.PrincipalFinder;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.CookieUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.GetterUtil;
import com.liferay.util.HttpHeaders;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.XSSUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;
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
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LoginAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LoginAction extends Action {

	public static String getLogin(
			HttpServletRequest req, String paramName, Company company)
		throws PortalException, SystemException {

		String login = req.getParameter(paramName);

		if ((login == null) || (login.equals(StringPool.NULL))) {
			login = GetterUtil.getString(
				CookieUtil.get(req.getCookies(), CookieKeys.LOGIN));

			if (Validator.isNull(login) &&
				company.getAuthType().equals(CompanyImpl.AUTH_TYPE_EA)) {

				login = "@" + company.getMx();
			}
		}

		login = XSSUtil.strip(login);

		return login;
	}

	public static void login(
			HttpServletRequest req, HttpServletResponse res, String login,
			String password, boolean rememberMe)
		throws Exception {

		CookieKeys.validateSupportCookie(req);

		HttpSession ses = req.getSession();

		String userId = login;

		int authResult = Authenticator.FAILURE;

		Company company = PortalUtil.getCompany(req);

		Map headerMap = new HashMap();

		Enumeration enu1 = req.getHeaderNames();

		while (enu1.hasMoreElements()) {
			String name = (String)enu1.nextElement();

			Enumeration enu2 = req.getHeaders(name);

			List headers = new ArrayList();

			while (enu2.hasMoreElements()) {
				String value = (String)enu2.nextElement();

				headers.add(value);
			}

			headerMap.put(name, (String[])headers.toArray(new String[0]));
		}

		Map parameterMap = req.getParameterMap();

		if (company.getAuthType().equals(CompanyImpl.AUTH_TYPE_EA)) {
			authResult = UserLocalServiceUtil.authenticateByEmailAddress(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);

			userId = UserLocalServiceUtil.getUserId(
				company.getCompanyId(), login);
		}
		else {
			authResult = UserLocalServiceUtil.authenticateByUserId(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);
		}

		try {
			PrincipalFinder principalFinder =
				(PrincipalFinder)InstancePool.get(
					PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));

			userId = principalFinder.fromLiferay(userId);
		}
		catch (Exception e) {
		}

		if (authResult == Authenticator.SUCCESS) {

			// Invalidate the previous session to prevent phishing

			LastPath lastPath = (LastPath)ses.getAttribute(WebKeys.LAST_PATH);

			ses.invalidate();
			ses = req.getSession(true);

			if (lastPath != null) {
				ses.setAttribute(WebKeys.LAST_PATH, lastPath);
			}

			// Set cookies

			User user = UserLocalServiceUtil.getUserById(userId);

			ses.setAttribute("j_username", userId);
			ses.setAttribute("j_password", user.getPassword());
			ses.setAttribute("j_remoteuser", userId);

			ses.setAttribute(WebKeys.USER_PASSWORD, password);

			Cookie idCookie = new Cookie(
				CookieKeys.ID,
				UserLocalServiceUtil.encryptUserId(userId));

			idCookie.setPath("/");

			Cookie passwordCookie = new Cookie(
				CookieKeys.PASSWORD,
				Encryptor.encrypt(company.getKeyObj(), password));

			passwordCookie.setPath("/");

			if (rememberMe) {
				idCookie.setMaxAge(CookieKeys.MAX_AGE);
				passwordCookie.setMaxAge(CookieKeys.MAX_AGE);
			}
			else {
				idCookie.setMaxAge(0);
				passwordCookie.setMaxAge(0);
			}

			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

			loginCookie.setPath("/");
			loginCookie.setMaxAge(CookieKeys.MAX_AGE);

			CookieKeys.addCookie(res, idCookie);
			CookieKeys.addCookie(res, passwordCookie);
			CookieKeys.addCookie(res, loginCookie);
		}
		else {
			throw new AuthException();
		}
	}

	public static void processAuthenticatedUser(
			HttpServletRequest req, HttpServletResponse res, Company company,
			String login, String userId, String password, boolean rememberMe)
		throws EncryptorException, PortalException, SystemException {

		HttpSession ses = req.getSession();

		User user = UserLocalServiceUtil.getUserById(userId);

		ses.setAttribute("j_username", userId);
		ses.setAttribute("j_password", user.getPassword());
		ses.setAttribute("j_remoteuser", userId);

		ses.setAttribute(WebKeys.USER_PASSWORD, password);

		Cookie idCookie = new Cookie(
			CookieKeys.ID,
			UserLocalServiceUtil.encryptUserId(userId));

		idCookie.setPath("/");

		Cookie passwordCookie = new Cookie(
			CookieKeys.PASSWORD,
			Encryptor.encrypt(company.getKeyObj(), password));

		passwordCookie.setPath("/");

		if (rememberMe) {
			idCookie.setMaxAge(CookieKeys.MAX_AGE);
			passwordCookie.setMaxAge(CookieKeys.MAX_AGE);
		}
		else {
			idCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}

		Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

		loginCookie.setPath("/");
		loginCookie.setMaxAge(CookieKeys.MAX_AGE);

		CookieKeys.addCookie(res, idCookie);
		CookieKeys.addCookie(res, passwordCookie);
		CookieKeys.addCookie(res, loginCookie);
	}

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		HttpSession ses = req.getSession();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		if (ses.getAttribute("j_username") != null &&
			ses.getAttribute("j_password") != null) {

			if (GetterUtil.getBoolean(
					PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE))) {

				return mapping.findForward("/portal/touch_protected.jsp");
			}
			else {
				res.sendRedirect(themeDisplay.getPathMain());

				return null;
			}
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals("already-registered")) {
			try {
				login(req, res);

				if (GetterUtil.getBoolean(
						PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE))) {

					return mapping.findForward("/portal/touch_protected.jsp");
				}
				else {
					res.sendRedirect(themeDisplay.getPathMain());

					return null;
				}
			}
			catch (Exception e) {
				if (e instanceof AuthException ||
					e instanceof CookieNotSupportedException ||
					e instanceof NoSuchUserException ||
					e instanceof UserEmailAddressException ||
					e instanceof UserIdException ||
					e instanceof UserPasswordException) {

					SessionErrors.add(req, e.getClass().getName());

					return mapping.findForward("portal.login");
				}
				else {
					req.setAttribute(PageContext.EXCEPTION, e);

					return mapping.findForward(Constants.COMMON_ERROR);
				}
			}
		}
		else if (cmd.equals("forgot-password")) {
			try {
				sendPassword(req);

				return mapping.findForward("portal.login");
			}
			catch (Exception e) {
				if (e instanceof NoSuchUserException ||
					e instanceof SendPasswordException ||
					e instanceof UserEmailAddressException) {

					SessionErrors.add(req, e.getClass().getName());

					return mapping.findForward("portal.login");
				}
				else {
					req.setAttribute(PageContext.EXCEPTION, e);

					return mapping.findForward(Constants.COMMON_ERROR);
				}
			}
		}
		else {
			return mapping.findForward("portal.login");
		}
	}

	protected void login(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		String login = ParamUtil.getString(req, "login").toLowerCase();
		String password = ParamUtil.getString(
			req, SessionParameters.get(req, "password"));
		boolean rememberMe = ParamUtil.getBoolean(req, "rememberMe");

		login(req, res, login, password, rememberMe);
	}

	protected void sendPassword(HttpServletRequest req) throws Exception {
		String emailAddress = ParamUtil.getString(req, "emailAddress");

		String remoteAddr = req.getRemoteAddr();
		String remoteHost = req.getRemoteHost();
		String userAgent = req.getHeader(HttpHeaders.USER_AGENT);

		UserLocalServiceUtil.sendPassword(
			PortalUtil.getCompanyId(req), emailAddress, remoteAddr, remoteHost,
			userAgent);

		SessionMessages.add(req, "request_processed", emailAddress);
	}

}