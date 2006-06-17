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

package com.liferay.portal.struts;

import com.liferay.portal.LayoutPermissionException;
import com.liferay.portal.PortletActiveException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.UserActiveException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.AutoLoginException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.PortletLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * <a href="PortalRequestProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Jorge Ferrer
 *
 */
public class PortalRequestProcessor extends TilesRequestProcessor {

	public PortalRequestProcessor() {

		// auth.forward.last.path.

		_lastPaths = CollectionFactory.getHashSet();

		_lastPaths.add(_PATH_PORTAL_LAYOUT);

		for (int i = 0;; i++) {
			String lastPath =
				PropsUtil.get(PropsUtil.AUTH_FORWARD_LAST_PATH + i);

			if (lastPath == null) {
				break;
			}
			else {
				_lastPaths.add(lastPath);
			}
		}

		// auth.public.path.

		_publicPaths = CollectionFactory.getHashSet();

		_publicPaths.add(_PATH_C);
		_publicPaths.add(_PATH_PORTAL_CSS);
		_publicPaths.add(_PATH_PORTAL_CSS_CACHED);
		_publicPaths.add(_PATH_PORTAL_J_LOGIN);
		_publicPaths.add(_PATH_PORTAL_JAVASCRIPT);
		_publicPaths.add(_PATH_PORTAL_JAVASCRIPT_CACHED);
		_publicPaths.add(_PATH_PORTAL_LAYOUT);
		_publicPaths.add(_PATH_PORTAL_LOGIN);
		_publicPaths.add(_PATH_PORTAL_RENDER_PORTLET);
		_publicPaths.add(_PATH_PORTAL_TCK);

		for (int i = 0;; i++) {
			String publicPath = PropsUtil.get(PropsUtil.AUTH_PUBLIC_PATH + i);

			if (publicPath == null) {
				break;
			}
			else {
				_publicPaths.add(publicPath);
			}
		}

		// auto.login.disabled.path.

		_autoLoginDisabledPaths = CollectionFactory.getHashSet();

		for (int i = 0;; i++) {
			String autoLoginDisabledPath = PropsUtil.get(
				PropsUtil.AUTO_LOGIN_DISABLED_PATH + i);

			if (autoLoginDisabledPath == null) {
				break;
			}
			else {
				_autoLoginDisabledPaths.add(autoLoginDisabledPath);
			}
		}
	}

	public void process(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		String path = super.processPath(req, res);

		ActionMapping mapping =
			(ActionMapping)moduleConfig.findActionConfig(path);

		if ((mapping == null) && !path.startsWith(_PATH_WSRP)) {
			String lastPath = getLastPath(req);

			if (_log.isDebugEnabled()) {
				_log.debug("Last path " + lastPath);
			}

			res.sendRedirect(lastPath);

			return;
		}

		super.process(req, res);

		try {
			if (isPortletPath(path)) {
				cleanUp(req);
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			_log.error(e);
		}
	}

	protected void callParentDoForward(
			String uri, HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		super.doForward(uri, req, res);
	}

	protected void doForward(
			String uri, HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		StrutsUtil.forward(uri, getServletContext(), req, res);
	}

	protected HttpServletRequest callParentProcessMultipart(
		HttpServletRequest req) {

		return super.processMultipart(req);
	}

	protected HttpServletRequest processMultipart(HttpServletRequest req) {

		// Disable Struts from automatically wrapping a multipart request

		return req;
	}

	protected String callParentProcessPath(
			HttpServletRequest req, HttpServletResponse res)
		throws IOException {

		return super.processPath(req, res);
	}

	protected String processPath(
			HttpServletRequest req, HttpServletResponse res)
		throws IOException {

		String path = super.processPath(req, res);

		HttpSession ses = req.getSession();

		// Current users

		String companyId = PortalUtil.getCompanyId(req);

		Map currentUsers = (Map)WebAppPool.get(
			companyId, WebKeys.CURRENT_USERS);

		UserTracker userTracker = (UserTracker)currentUsers.get(ses.getId());

		if ((userTracker != null) &&
			((path != null) &&
				(!path.equals(_PATH_C)) &&
				(path.indexOf(_PATH_J_SECURITY_CHECK) == -1) &&
				(path.indexOf(_PATH_PORTAL_PROTECTED) == -1))) {

			/*Map parameterMap = null;

			if (req instanceof UploadServletRequest) {
				UploadServletRequest uploadServletReq =
					(UploadServletRequest)req;

				parameterMap = uploadServletReq.getRequest().getParameterMap();
			}
			else {
				parameterMap = req.getParameterMap();
			}*/

			StringBuffer fullPathSB = new StringBuffer();

			fullPathSB.append(path);
			//fullPathSB.append(Http.parameterMapToString(parameterMap));
			fullPathSB.append(StringPool.QUESTION);
			fullPathSB.append(req.getQueryString());

			UserTrackerPath userTrackerPath =new UserTrackerPath();

			userTrackerPath.setUserTrackerPathId(
				userTracker.getUserTrackerId());
			userTrackerPath.setUserTrackerId(userTracker.getUserTrackerId());
			userTrackerPath.setPath(fullPathSB.toString());
			userTrackerPath.setPathDate(new Date());

			userTracker.addPath(userTrackerPath);
		}

		String userId = req.getRemoteUser();

		User user = null;

		try {
			user = PortalUtil.getUser(req);
		}
		catch (Exception e) {
		}

		// Last path

		if ((path != null) && (_lastPaths.contains(path))) {
			boolean saveLastPath = true;

			// /login/view

			String strutsAction = req.getParameter("_58_struts_action");

			if ((strutsAction != null) &&
				(strutsAction.equals(_PATH_LOGIN_VIEW))) {

				saveLastPath = false;
			}

			// /my_account/create_account

			strutsAction = req.getParameter("_2_struts_action");

			if ((strutsAction != null) &&
				(strutsAction.equals(_PATH_MY_ACCOUNT_CREATE_ACCOUNT))) {

				saveLastPath = false;
			}

			// Pop up window states should never be set as the last path

			if (LiferayWindowState.isPopUp(req)) {
				saveLastPath = false;
			}

			// Save last path

			if (saveLastPath) {
				String mainPath = (String)req.getAttribute(WebKeys.MAIN_PATH);

				// Was a last path set by another servlet that dispatched to
				// the MainServlet? If so, use that last path instead.

				LastPath lastPath =
					(LastPath)req.getAttribute(WebKeys.LAST_PATH);

				if (lastPath == null) {
					lastPath = new LastPath(
						mainPath, path, req.getParameterMap());
				}

				ses.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}

		// Auto login

		if ((userId == null) && (ses.getAttribute("j_username") == null) &&
			(!isAutoLoginDisabledPaths(path))) {

			try {
				String[] autoLogins = PropsUtil.getArray(
					PropsUtil.AUTO_LOGIN_HOOKS);

				for (int i = 0; i < autoLogins.length; i++) {
					AutoLogin autoLogin =
						(AutoLogin)InstancePool.get(autoLogins[i]);

					String[] credentials = autoLogin.login(req, res);

					if ((credentials != null) && (credentials.length == 3)) {
						String jUsername = credentials[0];
						String jPassword = credentials[1];
						boolean encPwd = GetterUtil.getBoolean(credentials[2]);

						if (Validator.isNotNull(jUsername) &&
							Validator.isNotNull(jPassword)) {

							ses.setAttribute("j_username", jUsername);

							// Not having access to the unencrypted password
							// will not allow you to connect to external
							// resources that require it (mail server)

							if (encPwd) {
								ses.setAttribute("j_password", jPassword);
							}
							else {
								ses.setAttribute("j_password",
									PwdEncryptor.encrypt(jPassword));

								ses.setAttribute(
									WebKeys.USER_PASSWORD, jPassword);
							}

							ses.setAttribute("j_remoteuser", jUsername);

							return _PATH_PORTAL_LOGIN;
						}
					}
				}

			}
			catch (AutoLoginException ale) {
				_log.error(ale.getMessage());
			}
		}

		// Authenticated users can always log out

		if ((userId != null || user != null) && (path != null) &&
			(path.equals(_PATH_PORTAL_LOGOUT))) {

			return _PATH_PORTAL_LOGOUT;
		}

		// Authenticated users can retrieve CSS and JavaScript

		if ((userId != null || user != null) && (path != null) &&
			(path.equals(_PATH_PORTAL_CSS) ||
			 path.equals(_PATH_PORTAL_CSS_CACHED) ||
			 path.equals(_PATH_PORTAL_JAVASCRIPT) ||
			 path.equals(_PATH_PORTAL_JAVASCRIPT_CACHED))) {

			return path;
		}

		// Authenticated users can always agree to terms of use

		if ((userId != null || user != null) && (path != null) &&
			(path.equals(_PATH_PORTAL_UPDATE_TERMS_OF_USE))) {

			return _PATH_PORTAL_UPDATE_TERMS_OF_USE;
		}

		// Authenticated users must still exist in the system

		if ((userId != null) && (user == null)) {
			return _PATH_PORTAL_LOGOUT;
		}

		// Authenticated users must agree to Terms of Use

		if ((user != null) && (!user.isAgreedToTermsOfUse())) {
			boolean termsOfUseRequired = GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.TERMS_OF_USE_REQUIRED), true);

			if (termsOfUseRequired) {
				return _PATH_PORTAL_TERMS_OF_USE;
			}
		}

		// Authenticated users must be active

		if ((user != null) && (!user.isActive())) {
			SessionErrors.add(req, UserActiveException.class.getName());

			return _PATH_PORTAL_ERROR;
		}

		// Authenticated users may not be allowed to have simultaneous logins

		boolean simultaenousLogins = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_SIMULTANEOUS_LOGINS), true);

		if (!simultaenousLogins) {
			Boolean staleSession =
				(Boolean)ses.getAttribute(WebKeys.STALE_SESSION);

			if ((user != null) && (staleSession != null) &&
				(staleSession.booleanValue())) {

				return _PATH_PORTAL_ERROR;
			}
		}

		// Authenticated users must have a current password

		if ((user != null) && (user.isPasswordReset())) {
			return _PATH_PORTAL_CHANGE_PASSWORD;
		}

		// Authenticated users must have at least one personalized page

		if (user != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			List layouts = themeDisplay.getLayouts();

			if ((layouts == null) || (layouts.size() == 0)) {
				SessionErrors.add(
					req, RequiredLayoutException.class.getName());

				return _PATH_PORTAL_ERROR;
			}
		}

		// Users must sign in

		if (!isPublicPath(path)) {
			if (user == null) {
				SessionErrors.add(req, PrincipalException.class.getName());

				return _PATH_PORTAL_LOGIN;
			}
		}

		ActionMapping mapping =
			(ActionMapping)moduleConfig.findActionConfig(path);

		if (path.startsWith(_PATH_WSRP)) {
			path = _PATH_WSRP;
		}
		else {
			path = mapping.getPath();
		}

		// Authenticated users must have at least one role

		if (user != null) {
			try {

				// FIX ME

				if (false) {
					SessionErrors.add(
						req, RequiredRoleException.class.getName());

					return _PATH_PORTAL_ERROR;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Define the portlet objects

		if (isPortletPath(path)) {
			try {
				Portlet portlet = null;

				String portletId = ParamUtil.getString(req, "p_p_id");

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						companyId, portletId);
				}

				if (portlet == null) {
					String strutsPath = path.substring(
						1, path.lastIndexOf(StringPool.SLASH));

					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						companyId, strutsPath);
				}

				if (portlet != null && portlet.isActive()) {
					defineObjects(req, res, portlet);
				}
			}
			catch (Exception e) {
				req.setAttribute(PageContext.EXCEPTION, e);

				path = _PATH_COMMON_ERROR;
			}
		}

		// Authenticated users must have access to at least one layout

		if (SessionErrors.contains(
				req, LayoutPermissionException.class.getName())) {

			return _PATH_PORTAL_ERROR;
		}

		return path;
	}

	protected boolean callParentProcessRoles(
			HttpServletRequest req, HttpServletResponse res,
			ActionMapping mapping)
		throws IOException, ServletException {

		return super.processRoles(req, res, mapping);
	}

	protected boolean processRoles(
			HttpServletRequest req, HttpServletResponse res,
			ActionMapping mapping)
		throws IOException, ServletException {

		String path = mapping.getPath();

		if (isPublicPath(path)) {
			return true;
		}

		boolean authorized = true;

		User user = null;

		try {
			user = PortalUtil.getUser(req);
		}
		catch (Exception e) {
		}

		if ((user != null) && isPortletPath(path)) {
			try {

				// Authenticated users can always log out

				if (path.equals(_PATH_PORTAL_LOGOUT)) {
					return true;
				}

				Portlet portlet = null;

				String portletId = ParamUtil.getString(req, "p_p_id");

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						user.getCompanyId(), portletId);
				}

				String strutsPath = path.substring(
					1, path.lastIndexOf(StringPool.SLASH));

				if (portlet != null) {
					if (!strutsPath.equals(portlet.getStrutsPath())) {
						throw new PrincipalException();
					}
				}
				else {
					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						user.getCompanyId(), strutsPath);
				}

				if ((portlet != null) && portlet.isActive()) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

					Layout layout = themeDisplay.getLayout();
					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					if (!PortletPermission.contains(
							permissionChecker, layout.getPlid(),
							portlet.getPortletId(), ActionKeys.VIEW)) {

						throw new PrincipalException();
					}
				}
				else if (portlet != null && !portlet.isActive()) {
					SessionErrors.add(
						req, PortletActiveException.class.getName());

					authorized = false;
				}
			}
			catch (Exception e) {
				SessionErrors.add(req, PrincipalException.class.getName());

				authorized = false;
			}
		}

		if (!authorized) {
			ForwardConfig forwardConfig =
				mapping.findForward(_PATH_PORTAL_ERROR);

			processForwardConfig(req, res, forwardConfig);

			return false;
		}
		else {
			return true;
		}
	}

	protected String getLastPath(HttpServletRequest req) {
		HttpSession ses = req.getSession();

		String userId = PortalUtil.getUserId(req);

		String portalURL = PortalUtil.getPortalURL(req, req.isSecure());
		String mainPath = (String)req.getAttribute(WebKeys.MAIN_PATH);

		StringBuffer defaultPathSB = new StringBuffer();

		defaultPathSB.append(portalURL);
		defaultPathSB.append(mainPath);
		defaultPathSB.append(_PATH_PORTAL_LAYOUT);

		boolean forwardByLastPath = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.AUTH_FORWARD_BY_LAST_PATH), true);

		if (!forwardByLastPath) {
			if (req.getRemoteUser() != null) {

				// If we do not forward by last path and the user is logged in,
				// forward to the user's default layout to prevent a lagging
				// loop

				defaultPathSB.append(StringPool.QUESTION);
				defaultPathSB.append("p_l_id");
				defaultPathSB.append(StringPool.EQUAL);
				defaultPathSB.append(Layout.DEFAULT_PLID);
			}

			return defaultPathSB.toString();
		}

		LastPath lastPath = (LastPath)ses.getAttribute(WebKeys.LAST_PATH);

		if (lastPath == null) {
			return defaultPathSB.toString();
		}

		Map parameterMap = lastPath.getParameterMap();

		// Only test for existing mappings for last paths that were set when the
		// user accessed a layout directly instead of through its friendly URL

		if (lastPath.getContextPath().equals(mainPath)) {
			ActionMapping mapping =
				(ActionMapping)moduleConfig.findActionConfig(
					lastPath.getPath());

			if ((mapping == null) || (parameterMap == null)) {
				return defaultPathSB.toString();
			}
		}

		StringBuffer lastPathSB = new StringBuffer();

		lastPathSB.append(portalURL);
		lastPathSB.append(lastPath.getContextPath());
		lastPathSB.append(lastPath.getPath());
		lastPathSB.append(Http.parameterMapToString(parameterMap));

		return lastPathSB.toString();
	}

	protected boolean isAutoLoginDisabledPaths(String path) {
		if ((path != null) &&
			(_autoLoginDisabledPaths.contains(path))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPortletPath(String path) {
		if ((path != null) &&
			(!path.equals(_PATH_C)) &&
			(!path.startsWith(_PATH_COMMON)) &&
			(path.indexOf(_PATH_J_SECURITY_CHECK) == -1) &&
			(!path.startsWith(_PATH_PORTAL)) &&
			(!path.startsWith(_PATH_WSRP))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPublicPath(String path) {
		if ((path != null) &&
			(_publicPaths.contains(path)) ||
			(path.startsWith(_PATH_COMMON)) ||
			(path.startsWith(_PATH_WSRP))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void defineObjects(
			HttpServletRequest req, HttpServletResponse res, Portlet portlet)
		throws Exception {

		String portletId = portlet.getPortletId();

		ServletContext ctx =
			(ServletContext)req.getAttribute(WebKeys.CTX);

		CachePortlet cachePortlet = PortletInstanceFactory.create(portlet, ctx);

		PortletPreferencesPK prefsPK =
			PortletPreferencesFactory.getPortletPreferencesPK(req, portletId);

		PortletPreferences prefs =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portlet.getCompanyId(), prefsPK);

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);
		PortletContext portletCtx =
			portletConfig.getPortletContext();

		RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(
			req, portlet, cachePortlet, portletCtx, WindowState.MAXIMIZED,
			PortletMode.VIEW, prefs);

		RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(
			renderRequestImpl, res, portletId, portlet.getCompanyId());

		renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);
	}

	protected void cleanUp(HttpServletRequest req) throws Exception {

		// Clean up portlet objects that may have been created by defineObjects
		// for portlets that are called directly from a Struts path

		RenderRequestImpl renderRequestImpl =
			(RenderRequestImpl)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

		if (renderRequestImpl != null) {
			RenderRequestFactory.recycle(renderRequestImpl);
		}

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)req.getAttribute(
				WebKeys.JAVAX_PORTLET_RESPONSE);

		if (renderResponseImpl != null) {
			RenderResponseFactory.recycle(renderResponseImpl);
		}
	}

	private static String _PATH_C = "/c";

	private static String _PATH_COMMON = "/common";

	private static String _PATH_COMMON_ERROR = "/common/error";

	private static String _PATH_J_SECURITY_CHECK = "/j_security_check";

	private static String _PATH_LOGIN_VIEW = "/login/view";

	private static String _PATH_MY_ACCOUNT_CREATE_ACCOUNT =
		"/my_account/create_account";

	private static String _PATH_PORTAL = "/portal";

	private static String _PATH_PORTAL_CHANGE_PASSWORD =
		"/portal/change_password";

	private static String _PATH_PORTAL_CSS = "/portal/css";

	private static String _PATH_PORTAL_CSS_CACHED = "/portal/css_cached";

	private static String _PATH_PORTAL_ERROR = Constants.PORTAL_ERROR;

	private static String _PATH_PORTAL_J_LOGIN = "/portal/j_login";

	private static String _PATH_PORTAL_JAVASCRIPT = "/portal/javascript";

	private static String _PATH_PORTAL_JAVASCRIPT_CACHED =
		"/portal/javascript_cached";

	private static String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static String _PATH_PORTAL_LOGIN = "/portal/login";

	private static String _PATH_PORTAL_LOGOUT = "/portal/logout";

	private static String _PATH_PORTAL_PROTECTED = "/portal/protected";

	private static String _PATH_PORTAL_RENDER_PORTLET =
		"/portal/render_portlet";

	private static String _PATH_PORTAL_TCK = "/portal/tck";

	private static String _PATH_PORTAL_TERMS_OF_USE = "/portal/terms_of_use";

	private static String _PATH_PORTAL_UPDATE_TERMS_OF_USE =
		"/portal/update_terms_of_use";

	private static String _PATH_WSRP = "/wsrp";

	private static Log _log = LogFactory.getLog(PortalRequestProcessor.class);

	private Set _lastPaths;
	private Set _publicPaths;
	private Set _autoLoginDisabledPaths;

}