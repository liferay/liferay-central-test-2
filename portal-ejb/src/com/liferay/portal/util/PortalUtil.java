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

package com.liferay.portal.util;

import com.germinus.easyconf.Filter;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.servlet.FriendlyURLPortletPlugin;
import com.liferay.portal.servlet.PortletContextPool;
import com.liferay.portal.servlet.PortletContextWrapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.LiferayPortletMode;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.wsrp.URLGeneratorImpl;
import com.liferay.util.CollectionFactory;
import com.liferay.util.Encryptor;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringComparator;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.StringServletResponse;
import com.liferay.util.servlet.UploadPortletRequest;
import com.liferay.util.servlet.UploadServletRequest;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import org.hibernate.util.FastHashMap;

/**
 * <a href="PortalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 * @author  Jorge Ferrer
 *
 */
public class PortalUtil {

	public static void clearRequestParameters(RenderRequest req) {

		// Clear the render parameters if they were set during processAction

		boolean action = ParamUtil.getBoolean(req, "p_p_action");

		if (action) {
			((RenderRequestImpl)req).getRenderParameters().clear();
		}
	}

	public static void copyRequestParameters(
		ActionRequest req, ActionResponse res) {

		try {
			ActionRequestImpl reqImpl = (ActionRequestImpl)req;

			HttpServletRequest originalReq = getOriginalServletRequest(
				reqImpl.getHttpServletRequest());

			ActionResponseImpl resImpl = (ActionResponseImpl)res;

			Map renderParameters = resImpl.getRenderParameters();

			res.setRenderParameter("p_p_action", "1");

			Enumeration enu = req.getParameterNames();

			while (enu.hasMoreElements()) {
				String param = (String)enu.nextElement();
				String[] values = req.getParameterValues(param);

				if (renderParameters.get(
						resImpl.getNamespace() + param) == null) {

					res.setRenderParameter(param, values);
				}
			}
		}
		catch (IllegalStateException ise) {

			// This should only happen if the developer called
			// sendRedirect of javax.portlet.ActionResponse

		}
	}

	public static String createSecureProxyURL(String url, boolean secure) {

		// Use this method to fetch external content that may not be available
		// in secure mode. See how the Weather portlet fetches images.

		if (!secure) {
			return url;
		}
		else {
			Map params = CollectionFactory.getHashMap();

			params.put(org.apache.wsrp4j.util.Constants.URL, url);

			return URLGeneratorImpl.getResourceProxyURL(params);
		}
	}

	public static Company getCompany(HttpServletRequest req)
		throws PortalException, SystemException {

		String companyId = getCompanyId(req);

		if (companyId == null) {
			return null;
		}

		Company company = (Company)req.getAttribute(WebKeys.COMPANY);

		if (company == null) {
			company = CompanyLocalServiceUtil.getCompany(companyId);

			req.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public static Company getCompany(ActionRequest req)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getCompany(reqImpl.getHttpServletRequest());
	}

	public static Company getCompany(RenderRequest req)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getCompany(reqImpl.getHttpServletRequest());
	}

	public static String getCompanyId(HttpServletRequest req) {
		String companyId =
			(String)req.getSession().getAttribute(WebKeys.COMPANY_ID);

		if (companyId == null) {
			companyId = (String)req.getAttribute(WebKeys.COMPANY_ID);
		}

		return companyId;
	}

	public static String getCompanyId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getCompanyId(reqImpl.getHttpServletRequest());
	}

	public static String getCompanyId(PortletRequest req) {
		String companyId = null;

		if (req instanceof ActionRequest) {
			companyId = getCompanyId((ActionRequest)req);
		}
		else {
			companyId = getCompanyId((RenderRequest)req);
		}

		return companyId;
	}

	public static String getCompanyId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getCompanyId(reqImpl.getHttpServletRequest());
	}

	public static Date getDate(
			int month, int day, int year, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, null, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			throw pe;
		}
		else {
			Calendar cal = null;

			if (timeZone == null) {
				cal = new GregorianCalendar();
			}
			else {
				cal = new GregorianCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(new GregorianCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public static String getHost(HttpServletRequest req) {
		String host = req.getHeader("Host");

		if (host != null) {
			int pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
		}
		else {
			host = null;
		}

		return host;
	}

	public static String getHost(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getHost(reqImpl.getHttpServletRequest());
	}

	public static String getHost(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getHost(reqImpl.getHttpServletRequest());
	}

	public static String getLayoutEditPage(Layout layout) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_EDIT_PAGE, Filter.by(layout.getType()));
	}

	public static String getLayoutViewPage(Layout layout) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_VIEW_PAGE, Filter.by(layout.getType()));
	}

	public static String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutURL(layout, themeDisplay, true);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		String layoutFriendlyURL = getLayoutFriendlyURL(layout, themeDisplay);

		if (Validator.isNotNull(layoutFriendlyURL)) {
			if (doAsUser && Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				layoutFriendlyURL = Http.addParameter(
					layoutFriendlyURL, "doAsUserId",
					themeDisplay.getDoAsUserId());
			}

			return layoutFriendlyURL;
		}

		String layoutURL = getLayoutActualURL(layout, themeDisplay);

		if (doAsUser && Validator.isNotNull(themeDisplay.getDoAsUserId())) {
			layoutURL = Http.addParameter(
				layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		return layoutURL;
	}

	public static String getLayoutActualURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutActualURL(layout, themeDisplay.getPathMain());
	}

	public static String getLayoutActualURL(Layout layout, String mainPath)
		throws PortalException, SystemException {

		Map vars = new FastHashMap();

		vars.put("liferay:mainPath", mainPath);
		vars.put("liferay:plid", layout.getPlid());
		vars.putAll(layout.getLayoutType().getTypeSettingsProperties());

		String href = PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_URL,
			Filter.by(layout.getType()).setVariables(vars));

		return href;
	}

    public static String getLayoutActualURL(
			String ownerId, String mainPath, String friendlyURL)
		throws PortalException, SystemException {

		Layout layout = null;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {
			List layouts = LayoutLocalServiceUtil.getLayouts(
				ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				layout = (Layout)layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					ownerId + " does not have any layouts");
			}
		}
		else {
			Object[] portletPlugin =
				getPortletFriendlyURLPlugin(ownerId, friendlyURL);

			layout = (Layout)portletPlugin[0];
			queryString = (String)portletPlugin[1];
		}

		String layoutActualURL =
			PortalUtil.getLayoutActualURL(layout, mainPath);

		if (Validator.isNotNull(queryString)) {
			layoutActualURL = layoutActualURL + queryString;
		}

		return layoutActualURL;
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		if (Validator.isNotNull(layoutFriendlyURL)) {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (Validator.isNotNull(layoutSet.getVirtualHost())) {
				String portalURL = PortalUtil.getPortalURL(
					layoutSet.getVirtualHost(), themeDisplay.getServerPort(),
					themeDisplay.isSecure());

				return portalURL + layoutFriendlyURL;
			}

			Group group = GroupLocalServiceUtil.getGroup(
				layout.getGroupId());

			String parentFriendlyURL = group.getFriendlyURL();

			if (Validator.isNotNull(parentFriendlyURL)) {
				String friendlyURL = null;

				if (layout.isPrivateLayout()) {
					friendlyURL = themeDisplay.getPathFriendlyURLPrivate();
				}
				else {
					friendlyURL = themeDisplay.getPathFriendlyURLPublic();
				}

				return friendlyURL + parentFriendlyURL + layoutFriendlyURL;
			}
		}

		return null;
	}

	public static String getLayoutTarget(Layout layout) {
		Properties typeSettingsProps = layout.getTypeSettingsProperties();

		String target = typeSettingsProps.getProperty("target");

		if (Validator.isNull(target)) {
			target = StringPool.BLANK;
		}
		else {
			target = "target=\"" + target + "\"";
		}

		return target;
	}

	public static String getJsSafePortletName(String portletName) {
		String jsSafePortletName =
			StringUtil.replace(
				portletName,
				new String[] {
					StringPool.SPACE, StringPool.DASH
				},
				new String[] {
					StringPool.BLANK, StringPool.BLANK
				});

		return jsSafePortletName;
	}

	public static Locale getLocale(HttpServletRequest req) {
		return (Locale)req.getSession().getAttribute(Globals.LOCALE_KEY);
	}

	public static Locale getLocale(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getLocale(reqImpl.getHttpServletRequest());
	}

	public static HttpServletRequest getOriginalServletRequest(
		HttpServletRequest req) {

		HttpServletRequest originalReq = req;

		while (originalReq.getClass().getName().startsWith("com.liferay.")) {

			// Get original request so that portlets inside portlets render
			// properly

			originalReq = (HttpServletRequest)
				((HttpServletRequestWrapper)originalReq).getRequest();
		}

		return originalReq;
	}

	public static String getPortalURL(HttpServletRequest req, boolean secure) {
		return getPortalURL(req.getServerName(), req.getServerPort(), secure);
	}

	public static String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBuffer sb = new StringBuffer();

		String serverProtocol = GetterUtil.getString(
			PropsUtil.get(PropsUtil.WEB_SERVER_PROTOCOL));

		if (secure || Http.HTTPS.equals(serverProtocol)) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		String serverHost = PropsUtil.get(PropsUtil.WEB_SERVER_HOST);

		if (Validator.isNull(serverHost)) {
			sb.append(serverName);
		}
		else {
			sb.append(serverHost);
		}

		int serverHttpPort = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTP_PORT), -1);

		if (serverHttpPort == -1) {
			if (!secure && (serverPort != Http.HTTP_PORT)) {
				sb.append(StringPool.COLON);
				sb.append(serverPort);
			}
		}
		else {
			if (!secure && (serverPort != serverHttpPort)) {
				if (serverHttpPort != Http.HTTP_PORT) {
					sb.append(StringPool.COLON);
					sb.append(serverHttpPort);
				}
			}
		}

		int serverHttpsPort = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTPS_PORT), -1);

		if (serverHttpsPort == -1) {
			if (secure && (serverPort != Http.HTTPS_PORT)) {
				sb.append(StringPool.COLON);
				sb.append(serverPort);
			}
		}
		else {
			if (secure && (serverPort != serverHttpsPort)) {
				if (serverHttpsPort != Http.HTTPS_PORT) {
					sb.append(StringPool.COLON);
					sb.append(serverHttpsPort);
				}
			}
		}

		return sb.toString();
	}

	public static Object[] getPortletFriendlyURLPlugin(
			String ownerId, String url)
		throws PortalException, SystemException {

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		Map portletPlugins = PortletLocalServiceUtil.getFriendlyURLPlugins();

		Iterator itr = portletPlugins.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String className = (String)entry.getValue();

			FriendlyURLPortletPlugin portletPlugin =
				(FriendlyURLPortletPlugin)InstancePool.get(className);

			int pos = url.indexOf(
				StringPool.SLASH + portletPlugin.getMapping() +
					StringPool.SLASH);

			if (pos != -1) {
				String[] values = portletPlugin.getValues(url, pos);

				friendlyURL = values[0];
				queryString = values[1];

				break;
			}
		}

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			ownerId, friendlyURL);

		return new Object[] {layout, queryString};
	}

	public static String getPortletGroupId(String plid) {
		String ownerId = LayoutImpl.getOwnerId(plid);

		return GetterUtil.getString(
			LayoutImpl.getGroupId(ownerId), GroupImpl.DEFAULT_PARENT_GROUP_ID);
	}

	public static String getPortletGroupId(HttpServletRequest req) {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		return getPortletGroupId(layout.getPlid());
	}

	public static String getPortletGroupId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getPortletGroupId(reqImpl.getHttpServletRequest());
	}

	public static String getPortletGroupId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getPortletGroupId(reqImpl.getHttpServletRequest());
	}

	public static String getPortletNamespace(String portletName) {
		return StringPool.UNDERLINE + portletName + StringPool.UNDERLINE;
	}

	public static String getPortletTitle(String portletId, User user)
		throws LanguageException {

		return LanguageUtil.get(
			user, WebKeys.JAVAX_PORTLET_TITLE + StringPool.PERIOD + portletId);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext ctx, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(WebKeys.JAVAX_PORTLET_TITLE);
	}

	public static PortletPreferences getPreferences(HttpServletRequest req) {
		RenderRequest renderRequest =
			(RenderRequest)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

		PortletPreferences prefs = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper prefsWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			prefs = prefsWrapper.getPreferencesImpl();
		}

		return prefs;
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		if (portlet.isWARFile()) {
			PortletContextWrapper pcw =
				PortletContextPool.get(portlet.getRootPortletId());

			return pcw.getPreferencesValidator();
		}
		else {
			PreferencesValidator prefsValidator = null;

			if (Validator.isNotNull(portlet.getPreferencesValidator())) {
				prefsValidator =
					(PreferencesValidator)InstancePool.get(
						portlet.getPreferencesValidator());
			}

			return prefsValidator;
		}
	}

	public static User getSelectedUser(HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		String userId = ParamUtil.getString(req, "p_u_i_d");

		User user = null;

		try {
			user = UserServiceUtil.getUserById(userId);
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public static User getSelectedUser(ActionRequest req)
		throws PortalException, RemoteException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getSelectedUser(reqImpl.getHttpServletRequest());
	}

	public static User getSelectedUser(RenderRequest req)
		throws PortalException, RemoteException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getSelectedUser(reqImpl.getHttpServletRequest());
	}

	public static String[] getSystemGroups() {
		return _instance._getSystemGroups();
	}

	public static String[] getSystemRoles() {
		return _instance._getSystemRoles();
	}

	public static UploadPortletRequest getUploadPortletRequest(
		ActionRequest req) {

		ActionRequestImpl actionReq = (ActionRequestImpl)req;

		DynamicServletRequest dynamicReq =
			(DynamicServletRequest)actionReq.getHttpServletRequest();

		HttpServletRequestWrapper reqWrapper =
			(HttpServletRequestWrapper)dynamicReq.getRequest();

		UploadServletRequest uploadReq = getUploadServletRequest(reqWrapper);

		return new UploadPortletRequest(
			uploadReq, getPortletNamespace(actionReq.getPortletName()));
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpReq) {

		HttpServletRequestWrapper httpReqWrapper = null;

		if (httpReq instanceof HttpServletRequestWrapper) {
			httpReqWrapper = (HttpServletRequestWrapper)httpReq;
		}

		UploadServletRequest uploadReq = null;

		while (uploadReq == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (httpReqWrapper instanceof UploadServletRequest) {
				uploadReq = (UploadServletRequest)httpReqWrapper;
			}
			else {
				ServletRequest req = httpReqWrapper.getRequest();

				if (!(req instanceof HttpServletRequestWrapper)) {
					break;
				}
				else {
					httpReqWrapper =
						(HttpServletRequestWrapper)httpReqWrapper.getRequest();
				}
			}
		}

		return uploadReq;
	}

	public static User getUser(HttpServletRequest req)
		throws PortalException, SystemException {

		String userId = getUserId(req);

		if (userId == null) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			userId = req.getRemoteUser();

			if (userId == null) {
				return null;
			}
		}

		User user = (User)req.getAttribute(WebKeys.USER);

		if (user == null) {
			user = UserLocalServiceUtil.getUserById(userId);

			req.setAttribute(WebKeys.USER, user);
		}

		return user;
	}

	public static User getUser(ActionRequest req)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUser(reqImpl.getHttpServletRequest());
	}

	public static User getUser(RenderRequest req)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUser(reqImpl.getHttpServletRequest());
	}

	public static String getUserId(HttpServletRequest req) {
		String userId = (String)req.getAttribute(WebKeys.USER_ID);

		if (userId != null) {
			return userId;
		}

		if (!GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE)) &&
			GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PORTAL_IMPERSONATION_ENABLE))) {

			String doAsUserId = ParamUtil.getString(req, "doAsUserId");

			try {
				doAsUserId = _getDoAsUserId(req, doAsUserId);

				if (doAsUserId != null) {
					return doAsUserId;
				}
			}
			catch (Exception e) {
				_log.error("Unable to impersonate user " + doAsUserId, e);
			}
		}

		HttpSession ses = req.getSession();

		userId = (String)ses.getAttribute(WebKeys.USER_ID);

		if (userId != null) {
			req.setAttribute(WebKeys.USER_ID, userId);
		}

		return userId;
	}

	public static String getUserId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUserId(reqImpl.getHttpServletRequest());
	}

	public static String getUserId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUserId(reqImpl.getHttpServletRequest());
	}

	public static String getUserName(String userId, String defaultUserName) {
		return getUserName(userId, defaultUserName, null);
	}

	public static String getUserName(
		String userId, String defaultUserName, HttpServletRequest req) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			userName = user.getFullName();

			if (req != null) {
				Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					req, PortletKeys.DIRECTORY, layout.getPlid(), false);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter("struts_action", "/directory/edit_user");
				portletURL.setParameter("p_u_i_d", user.getUserId());

				userName =
					"<a href=\"" + portletURL.toString() + "\">" + userName +
						"</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public static String getUserPassword(HttpSession ses) {
		return (String)ses.getAttribute(WebKeys.USER_PASSWORD);
	}

	public static String getUserPassword(HttpServletRequest req) {
		return getUserPassword(req.getSession());
	}

	public static String getUserPassword(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUserPassword(reqImpl.getHttpServletRequest());
	}

	public static String getUserPassword(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUserPassword(reqImpl.getHttpServletRequest());
	}

	public static boolean isLayoutFriendliable(Layout layout) {
		return PropsUtil.getComponentProperties().getBoolean(
			PropsUtil.LAYOUT_URL_FRIENDLIABLE,
			Filter.by(layout.getType()), true);
	}

	public static boolean isLayoutParentable(Layout layout) {
		return isLayoutParentable(layout.getType());
	}

	public static boolean isLayoutParentable(String type) {
		return PropsUtil.getComponentProperties().getBoolean(
			PropsUtil.LAYOUT_PARENTABLE,
			Filter.by(type), true);
	}

	public static boolean isReservedParameter(String name) {
		return _instance._reservedParams.contains(name);
	}

	public static boolean isSystemGroup(String groupName) {
		return _instance._isSystemGroup(groupName);
	}

	public static boolean isSystemRole(String roleName) {
		return _instance._isSystemRole(roleName);
	}

	public static void renderPage(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, String path)
		throws IOException, ServletException {

		RequestDispatcher rd = ctx.getRequestDispatcher(path);

		StringServletResponse stringServletRes =
			new StringServletResponse(res);

		rd.include(req, stringServletRes);

		sb.append(stringServletRes.getString());
	}

	public static void renderPortlet(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString)
		throws IOException, ServletException {

		renderPortlet(
			sb, ctx, req, res, portlet, queryString, null, null, null);
	}

	public static void renderPortlet(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		renderPortlet(
			sb, ctx, req, res, portlet, queryString, columnId, columnPos,
			columnCount, null);
	}

	public static void renderPortlet(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path)
		throws IOException, ServletException {

		queryString = GetterUtil.getString(queryString);
		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = new Integer(0);
		}

		if (columnCount == null) {
			columnCount = new Integer(0);
		}

		req.setAttribute(WebKeys.RENDER_PORTLET, portlet);
		req.setAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING, queryString);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher rd = ctx.getRequestDispatcher(path);

		if (sb != null) {
			StringServletResponse stringServletRes =
				new StringServletResponse(res);

			rd.include(req, stringServletRes);

			sb.append(stringServletRes.getString());
		}
		else {

			// LEP-766

			String strutsCharEncoding =
				PropsUtil.get(PropsUtil.STRUTS_CHAR_ENCODING);

			req.setCharacterEncoding(strutsCharEncoding);

			res.setContentType(Constants.TEXT_HTML + "; charset=UTF-8");

			rd.include(req, res);
		}
	}

	public static void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper prefsWrapper =
			(PortletPreferencesWrapper)prefs;

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)prefsWrapper.getPreferencesImpl();

		prefsImpl.store();
	}

	public static PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode)
		throws PortalException, RemoteException, SystemException {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if (portletMode == null || Validator.isNull(portletMode.toString())) {
			if (layoutType.hasModeAboutPortletId(portletId)) {
				return LiferayPortletMode.ABOUT;
			}
			else if (layoutType.hasModeConfigPortletId(portletId)) {
				return LiferayPortletMode.CONFIG;
			}
			else if (layoutType.hasModeEditPortletId(portletId)) {
				return PortletMode.EDIT;
			}
			else if (layoutType.hasModeEditDefaultsPortletId(portletId)) {
				return LiferayPortletMode.EDIT_DEFAULTS;
			}
			else if (layoutType.hasModeEditGuestPortletId(portletId)) {
				return LiferayPortletMode.EDIT_GUEST;
			}
			else if (layoutType.hasModeHelpPortletId(portletId)) {
				return PortletMode.HELP;
			}
			else if (layoutType.hasModePreviewPortletId(portletId)) {
				return LiferayPortletMode.PREVIEW;
			}
			else if (layoutType.hasModePrintPortletId(portletId)) {
				return LiferayPortletMode.PRINT;
			}
			else {
				return PortletMode.VIEW;
			}
		}
		else {
			boolean updateLayout = false;

			if (portletMode.equals(LiferayPortletMode.ABOUT) &&
				!layoutType.hasModeAboutPortletId(portletId)) {

				layoutType.addModeAboutPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.CONFIG) &&
					 !layoutType.hasModeConfigPortletId(portletId)) {

				layoutType.addModeConfigPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.EDIT) &&
					 !layoutType.hasModeEditPortletId(portletId)) {

				layoutType.addModeEditPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS) &&
					 !layoutType.hasModeEditDefaultsPortletId(portletId)) {

				layoutType.addModeEditDefaultsPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST) &&
					 !layoutType.hasModeEditGuestPortletId(portletId)) {

				layoutType.addModeEditGuestPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.HELP) &&
					 !layoutType.hasModeHelpPortletId(portletId)) {

				layoutType.addModeHelpPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PREVIEW) &&
					 !layoutType.hasModePreviewPortletId(portletId)) {

				layoutType.addModePreviewPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PRINT) &&
					 !layoutType.hasModePrintPortletId(portletId)) {

				layoutType.addModePrintPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.VIEW) &&
					 !layoutType.hasModeViewPortletId(portletId)) {

				layoutType.removeModesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				if ((user != null) && !layout.isShared()) {
					LayoutServiceUtil.updateLayout(
						layout.getLayoutId(), layout.getOwnerId(),
						layout.getTypeSettings());
				}
			}

			return portletMode;
		}
	}

	public static WindowState updateWindowState(
			String portletId, User user, Layout layout, WindowState windowState,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) ||
			(Validator.isNull(windowState.toString()))) {

			if (layoutType.hasStateMaxPortletId(portletId)) {
				return WindowState.MAXIMIZED;
			}
			else if (layoutType.hasStateMinPortletId(portletId)) {
				return WindowState.MINIMIZED;
			}
			else {
				return WindowState.NORMAL;
			}
		}
		else {
			boolean updateLayout = false;

			if ((windowState.equals(WindowState.MAXIMIZED)) ||
				(windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
				(windowState.equals(LiferayWindowState.POP_UP))) {

				if (layoutType.hasStateMax()) {
					String curMaxPortletId =
						StringUtil.split(layoutType.getStateMax())[0];

					// Clear cache and render parameters for the previous
					// portlet that had a maximum window state

					CachePortlet.clearResponse(
						req.getSession(), layout.getPrimaryKey(),
						curMaxPortletId);

					/*RenderParametersPool.clear(
						req, layout.getPlid(), curMaxPortletId);*/

					if ((windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
						(windowState.equals(LiferayWindowState.POP_UP))) {

						String stateMaxPrevious =
							layoutType.getStateMaxPrevious();

						if (stateMaxPrevious == null) {
							layoutType.setStateMaxPrevious(curMaxPortletId);

							updateLayout = true;
						}
					}
				}
				else {
					if ((windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
						(windowState.equals(LiferayWindowState.POP_UP))) {

						String stateMaxPrevious =
							layoutType.getStateMaxPrevious();

						if (stateMaxPrevious == null) {
							layoutType.setStateMaxPrevious(StringPool.BLANK);

							updateLayout = true;
						}
					}
				}

				if (!layoutType.hasStateMaxPortletId(portletId)) {
					layoutType.addStateMaxPortletId(portletId);

					updateLayout = true;
				}
			}
			else if (windowState.equals(WindowState.MINIMIZED) &&
					 !layoutType.hasStateMinPortletId(portletId)) {

				layoutType.addStateMinPortletId(portletId);

				updateLayout = true;
			}
			else if (windowState.equals(WindowState.NORMAL) &&
					 !layoutType.hasStateNormalPortletId(portletId)) {

				layoutType.removeStatesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				if ((user != null) && !layout.isShared()) {
					LayoutServiceUtil.updateLayout(
						layout.getLayoutId(), layout.getOwnerId(),
						layout.getTypeSettings());
				}
			}

			return windowState;
		}
	}

	private static String _getDoAsUserId(
			HttpServletRequest req, String doAsUserId)
		throws Exception {

		if (Validator.isNull(doAsUserId)) {
			return null;
		}

		HttpSession ses = req.getSession();

		String realUserId = (String)ses.getAttribute(WebKeys.USER_ID);

		if (realUserId == null) {
			return null;
		}

		Company company = getCompany(req);

		doAsUserId = Encryptor.decrypt(company.getKeyObj(), doAsUserId);

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		String organizationId = doAsUser.getOrganization().getOrganizationId();
		String locationId = doAsUser.getLocation().getOrganizationId();

		User realUser = UserLocalServiceUtil.getUserById(realUserId);
		boolean signedIn = true;
		boolean checkGuest = true;

		PermissionCheckerImpl permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactory.create(
				realUser, signedIn, checkGuest);

			if (doAsUser.isDefaultUser() ||
				UserPermission.contains(
					permissionChecker, doAsUserId, organizationId, locationId,
					ActionKeys.IMPERSONATE)) {

				req.setAttribute(WebKeys.USER_ID, doAsUserId);

				return doAsUserId;
			}
			else {
				_log.error(
					"User " + realUserId + " does not have the permission to " +
						"impersonate " + doAsUserId);

				return null;
			}
		}
		finally {
			try {
				PermissionCheckerFactory.recycle(permissionChecker);
			}
			catch (Exception e) {
			}
		}
	}

	private PortalUtil() {

		// Groups

		String customSystemGroups[] =
			PropsUtil.getArray(PropsUtil.SYSTEM_GROUPS);

		if (customSystemGroups == null || customSystemGroups.length == 0) {
			_allSystemGroups = GroupImpl.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = new String[
				GroupImpl.SYSTEM_GROUPS.length + customSystemGroups.length];

			System.arraycopy(
				GroupImpl.SYSTEM_GROUPS, 0, _allSystemGroups, 0,
				GroupImpl.SYSTEM_GROUPS.length);

			System.arraycopy(
				customSystemGroups, 0, _allSystemGroups,
				GroupImpl.SYSTEM_GROUPS.length, customSystemGroups.length);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Roles

		String customSystemRoles[] = PropsUtil.getArray(PropsUtil.SYSTEM_ROLES);

		if (customSystemRoles == null || customSystemRoles.length == 0) {
			_allSystemRoles = RoleImpl.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = new String[
				RoleImpl.SYSTEM_ROLES.length + customSystemRoles.length];

			System.arraycopy(
				RoleImpl.SYSTEM_ROLES, 0, _allSystemRoles, 0,
				RoleImpl.SYSTEM_ROLES.length);

			System.arraycopy(
				customSystemRoles, 0, _allSystemRoles,
				RoleImpl.SYSTEM_ROLES.length, customSystemRoles.length);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = CollectionFactory.getHashSet();

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_action");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
	}

	private String[] _getSystemGroups() {
		return _allSystemGroups;
	}

	private String[] _getSystemRoles() {
		return _allSystemRoles;
	}

	private boolean _isSystemGroup(String groupName) {
		if (groupName == null) {
			return false;
		}

		groupName = groupName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemGroups, groupName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isSystemRole(String roleName) {
		if (roleName == null) {
			return false;
		}

		roleName = roleName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(PortalUtil.class);

	private static PortalUtil _instance = new PortalUtil();

	private String[] _allSystemGroups;
	private String[] _allSystemRoles;
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemRoles;
	private Set _reservedParams;

}