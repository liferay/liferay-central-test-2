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

package com.liferay.portlet;

import com.liferay.portal.ccpp.EmptyProfile;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.servlet.NamespaceServletRequest;
import com.liferay.portal.servlet.SharedSessionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.QNameUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.SharedSessionServletRequest;

import com.sun.ccpp.ProfileFactoryImpl;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ccpp.Profile;
import javax.ccpp.ProfileFactory;
import javax.ccpp.ValidationMode;

import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="PortletRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Sergey Ponomarev
 *
 */
public abstract class PortletRequestImpl implements PortletRequest {

	public void defineObjects(
		PortletConfig portletConfig, PortletResponse res) {

		setAttribute(JavaConstants.JAVAX_PORTLET_CONFIG, portletConfig);
		setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, this);
		setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, res);
		setAttribute(PortletRequest.LIFECYCLE_PHASE, getLifecycle());
	}

	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (name.equals(PortletRequest.CCPP_PROFILE)) {
			return getCCPPProfile();
		}
		else if (name.equals(PortletRequest.USER_INFO)) {
			Object value = getUserInfo();

			if (value != null) {
				return value;
			}
		}

		return _req.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		List<String> names = new ArrayList<String>();

		Enumeration<String> enu = _req.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.equals(JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO)) {
				names.add(name);
			}
		}

		return Collections.enumeration(names);
	}

	public String getAuthType() {
		return _req.getAuthType();
	}

	public Profile getCCPPProfile() {
		if (_profile == null) {
			ProfileFactory profileFactory = ProfileFactory.getInstance();

			if (profileFactory == null) {
				profileFactory = ProfileFactoryImpl.getInstance();

				ProfileFactory.setInstance(profileFactory);
			}

			_profile = profileFactory.newProfile(
				_req, ValidationMode.VALIDATIONMODE_NONE);

			if (_profile == null) {
				_profile = _EMPTY_PROFILE;
			}
		}

		return _profile;
	}

	public String getContextPath() {
		//return StringPool.SLASH + _req.getContextPath();
		return StringPool.SLASH + _portletCtx.getPortletContextName();
	}

	public Cookie[] getCookies() {
		return null;
	}

	public String getETag() {
		return null;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _req;
	}

	public abstract String getLifecycle();

	public Locale getLocale() {
		Locale locale = _locale;

		if (locale == null) {
			locale = _req.getLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	public Enumeration<Locale> getLocales() {
		return _req.getLocales();
	}

	public String getMethod() {
		return _req.getMethod();
	}

	public String getParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _req.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return Collections.unmodifiableMap(_req.getParameterMap());
	}

	public Enumeration<String> getParameterNames() {
		return _req.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _req.getParameterValues(name);
	}

	public PortalContext getPortalContext() {
		return _portalCtx;
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public PortletContext getPortletContext() {
		return _portletCtx;
	}

	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public String getPortletName() {
		return _portletName;
	}

	public PortletSession getPortletSession() {
		return _ses;
	}

	public PortletSession getPortletSession(boolean create) {
		/*HttpSession httpSes = _req.getSession(create);

		if (httpSes == null) {
			return null;
		}
		else {
			if (create) {
				_ses = new PortletSessionImpl(
					_req, _portletName, _portletCtx, _portalSessionId,
					_plid);
			}

			return _ses;
		}*/

		/*if ((_ses == null) && create) {
			_req.getSession(create);

			_ses = new PortletSessionImpl(
				_req, _portletName, _portletCtx, _portalSessionId, _plid);
		}*/

		if (!create && _invalidSession) {
			return null;
		}

		return _ses;
	}

	public PortletPreferences getPreferences() {
		return new PortletPreferencesWrapper(
			getPreferencesImpl(), getLifecycle());
	}

	public PortletPreferencesImpl getPreferencesImpl() {
		return (PortletPreferencesImpl)_prefs;
	}

	public Map<String, String[]> getPrivateParameterMap() {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (_portlet.getPublicRenderParameter(name) == null) {
				parameterMap.put(name, getParameterValues(name));
			}
		}

		return parameterMap;
	}

	public Enumeration<String> getProperties(String name) {
		List<String> values = new ArrayList<String>();

		String value = _portalCtx.getProperty(name);

		if (value != null) {
			values.add(value);
		}

		return Collections.enumeration(values);
	}

	public String getProperty(String name) {
		return _portalCtx.getProperty(name);
	}

	public Enumeration<String> getPropertyNames() {
		return _portalCtx.getPropertyNames();
	}

	public Map<String, String[]> getPublicParameterMap() {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (_portlet.getPublicRenderParameter(name) != null) {
				parameterMap.put(name, getParameterValues(name));
			}
		}

		return parameterMap;
	}

	public String getRemoteUser() {
		return _remoteUser;
	}

	public Map<String, String[]> getRenderParameters() {
		return RenderParametersPool.get(_req, _plid, _portletName);
	}

	public String getRequestedSessionId() {
		return _req.getSession().getId();
	}

	public String getResponseContentType() {
		if (_wapTheme) {
			return ContentTypes.XHTML_MP;
		}
		else {
			return ContentTypes.TEXT_HTML;
		}
	}

	public Enumeration<String> getResponseContentTypes() {
		List<String> responseContentTypes = new ArrayList<String>();

		responseContentTypes.add(getResponseContentType());

		return Collections.enumeration(responseContentTypes);
	}

	public String getScheme() {
		return _req.getScheme();
	}

	public String getServerName() {
		return _req.getServerName();
	}

	public int getServerPort() {
		return _req.getServerPort();
	}

	public Object getUserInfo() {
		if (getRemoteUser() == null) {
			return null;
		}

		LinkedHashMap<String, String> userInfo =
			new LinkedHashMap<String, String>();

		PortletApp portletApp = _portlet.getPortletApp();

		// Liferay user attributes

		try {
			User user = PortalUtil.getUser(_req);

			UserAttributes userAttributes = new UserAttributes(user);

			// Mandatory user attributes

			userInfo.put(
				UserAttributes.LIFERAY_COMPANY_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_COMPANY_ID));

			userInfo.put(
				UserAttributes.LIFERAY_USER_ID,
				userAttributes.getValue(UserAttributes.LIFERAY_USER_ID));

			// Portlet user attributes

			for (String attrName : portletApp.getUserAttributes()) {
				String attrValue = userAttributes.getValue(attrName);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		Map<String, String> unmodifiableUserInfo =
			Collections.unmodifiableMap((Map<String, String>)userInfo.clone());

		// Custom user attributes

		Map<String, CustomUserAttributes> cuaInstances =
			new HashMap<String, CustomUserAttributes>();

		for (Map.Entry<String, String> entry :
				portletApp.getCustomUserAttributes().entrySet()) {

			String attrName = entry.getKey();
			String attrCustomClass = entry.getValue();

			CustomUserAttributes cua = cuaInstances.get(attrCustomClass);

			if (cua == null) {
				if (portletApp.isWARFile()) {
					PortletContextBag portletContextBag =
						PortletContextBagPool.get(
							portletApp.getServletContextName());

					cua = portletContextBag.getCustomUserAttributes().get(
						attrCustomClass);

					cua = (CustomUserAttributes)cua.clone();
				}
				else {
					try {
						cua = (CustomUserAttributes)Class.forName(
							attrCustomClass).newInstance();
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

				cuaInstances.put(attrCustomClass, cua);
			}

			if (cua != null) {
				String attrValue = cua.getValue(attrName, unmodifiableUserInfo);

				if (attrValue != null) {
					userInfo.put(attrName, attrValue);
				}
			}
		}

		return userInfo;
	}

	public Principal getUserPrincipal() {
		return _userPrincipal;
	}

	public String getWindowID() {
		StringMaker sm = new StringMaker();

		sm.append(_portletName);
		sm.append(PortletSessionImpl.LAYOUT_SEPARATOR);
		sm.append(_plid);

		return sm.toString();
	}

	public WindowState getWindowState() {
		return _windowState;
	}

	public void invalidateSession() {
		_invalidSession = true;
	}

	public boolean isPortletModeAllowed(PortletMode portletMode) {
		if ((portletMode == null) || Validator.isNull(portletMode.toString())) {
			return true;
		}
		else {
			return _portlet.hasPortletMode(
				getResponseContentType(), portletMode);
		}
	}

	public boolean isPrivateRequestAttributes() {
		return _portlet.isPrivateRequestAttributes();
	}

	public boolean isRequestedSessionIdValid() {
		if (_ses != null) {
			return _ses.isValid();
		}
		else {
			return _req.isRequestedSessionIdValid();
		}
	}

	public boolean isSecure() {
		return _req.isSecure();
	}

	public boolean isUserInRole(String role) {
		if (_remoteUserId <= 0) {
			return false;
		}
		else {
			try {
				long companyId = PortalUtil.getCompanyId(_req);

				return RoleLocalServiceUtil.hasUserRole(
					_remoteUserId, companyId, role, true);
			}
			catch (Exception e) {
				_log.error(e);
			}

			return _req.isUserInRole(role);
		}
	}

	public boolean isWindowStateAllowed(WindowState windowState) {
		return PortalContextImpl.isSupportedWindowState(windowState);
	}

	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_req.removeAttribute(name);
	}

	public void setAttribute(String name, Object obj) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (obj == null) {
			removeAttribute(name);
		}
		else {
			_req.setAttribute(name, obj);
		}
	}

	public void setPortletMode(PortletMode portletMode) {
		_portletMode = portletMode;
	}

	public void setWindowState(WindowState windowState) {
		_windowState = windowState;
	}

	protected void init(
		HttpServletRequest req, Portlet portlet, InvokerPortlet invokerPortlet,
		PortletContext portletCtx, WindowState windowState,
		PortletMode portletMode, PortletPreferences prefs, long plid) {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portlet = portlet;
		_portletName = portlet.getPortletId();

		String portletNamespace = PortalUtil.getPortletNamespace(_portletName);

		Map<String, Object> sharedSessionAttributes =
			SharedSessionUtil.getSharedSessionAttributes(req);

		boolean portalSessionShared = false;

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile() && !portlet.isPrivateSessionAttributes()) {
			portalSessionShared = true;
		}

		req = new SharedSessionServletRequest(
			req, sharedSessionAttributes, portalSessionShared);

		DynamicServletRequest dynamicReq = null;

		if (portlet.isPrivateRequestAttributes()) {
			dynamicReq = new NamespaceServletRequest(
				req, portletNamespace, portletNamespace, false);
		}
		else {
			dynamicReq = new DynamicServletRequest(req, false);
		}

		Enumeration<String> enu = null;

		boolean portletFocus = false;

		String ppid = ParamUtil.getString(req, "p_p_id");

		if (_portletName.equals(ppid)) {

			// Request was targeted to this portlet

			if (themeDisplay.isLifecycleRender() ||
				themeDisplay.isLifecycleResource()) {

				// Request was triggered by a render or resource URL

			   portletFocus = true;
			}
			else if (themeDisplay.isLifecycleAction() &&
					 getLifecycle().equals(PortletRequest.ACTION_PHASE)) {

				// Request was triggered by an action URL and is being processed
				// by com.liferay.portlet.ActionRequestImpl

			   portletFocus = true;
			}
		}

		Map<String, String[]> oldRenderParameters = RenderParametersPool.get(
			req, plid, _portletName);

		Map<String, String[]> newRenderParameters = null;

		if (portletFocus) {
			newRenderParameters = new HashMap<String, String[]>();

			if (getLifecycle().equals(PortletRequest.RENDER_PHASE) &&
				!LiferayWindowState.isExclusive(req) &&
				!LiferayWindowState.isPopUp(req)) {

				RenderParametersPool.put(
					req, plid, _portletName, newRenderParameters);
			}

			enu = req.getParameterNames();
		}
		else {
			if (!_portletName.equals(ppid)) {
				putPublicRenderParameters(req, plid, oldRenderParameters);
			}

			enu = Collections.enumeration(oldRenderParameters.keySet());
		}

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(portletNamespace) &&
				!invokerPortlet.isFacesPortlet()) {

				String shortName = name.substring(
					portletNamespace.length(), name.length());

				ObjectValuePair<String, String[]> ovp = getParameterValues(
					req, themeDisplay, portletFocus, oldRenderParameters,
					newRenderParameters, name);

				dynamicReq.setParameterValues(shortName, ovp.getValue());
			}
			else {

				// Do not allow reserved or null parameters to pass through.
				// Jetty has a bug that adds an additional null parameter
				// the enumeration of parameter names.

				if (!PortalUtil.isReservedParameter(name) &&
					Validator.isNotNull(name)) {

					ObjectValuePair<String, String[]> ovp = getParameterValues(
						req, themeDisplay, portletFocus, oldRenderParameters,
						newRenderParameters, name);

					dynamicReq.setParameterValues(ovp.getKey(), ovp.getValue());
				}
			}
		}

		_req = dynamicReq;
		_wapTheme = BrowserSniffer.is_wap_xhtml(_req);
		_portlet = portlet;
		_portalCtx = new PortalContextImpl();
		_portletCtx = portletCtx;
		_windowState = windowState;
		_portletMode = portletMode;
		_prefs = prefs;
		_portalSessionId = _req.getRequestedSessionId();
		_ses = new PortletSessionImpl(
			_req, _portletName, _portletCtx, _portalSessionId, plid);

		long userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();

		String userPrincipalStrategy = portlet.getUserPrincipalStrategy();

		if (userPrincipalStrategy.equals(
				PortletImpl.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(req);

				_remoteUser = user.getScreenName();
				_remoteUserId = user.getUserId();
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			if ((userId > 0) && (remoteUser == null)) {
				_remoteUser = String.valueOf(userId);
				_remoteUserId = userId;
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			else {
				_remoteUser = remoteUser;
				_remoteUserId = GetterUtil.getLong(remoteUser);
				_userPrincipal = req.getUserPrincipal();
			}
		}

		_locale = (Locale)_req.getSession().getAttribute(Globals.LOCALE_KEY);
		_plid = plid;
	}

	protected ObjectValuePair<String, String[]> getParameterValues(
		HttpServletRequest req, ThemeDisplay themeDisplay, boolean portletFocus,
		Map<String, String[]> oldRenderParameters,
		Map<String, String[]> newRenderParameters, String name) {

		String[] values = null;

		if (portletFocus) {
			values = req.getParameterValues(name);

			QName qName = QNameUtil.getQName(name);

			if (qName != null) {
				PublicRenderParameter publicRenderParameter =
					_portlet.getPublicRenderParameter(
						qName.getNamespaceURI(), qName.getLocalPart());

				if (publicRenderParameter != null) {
					name = publicRenderParameter.getIdentifier();
				}
			}

			if (themeDisplay.isLifecycleRender()) {
				newRenderParameters.put(name, values);
			}
		}
		else {
			values = oldRenderParameters.get(name);
		}

		return new ObjectValuePair<String, String[]>(name, values);
	}

	protected void putPublicRenderParameters(
		HttpServletRequest req, long plid,
		Map<String, String[]> renderParameters) {

		Enumeration<String> names = req.getParameterNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();

			QName qName = QNameUtil.getQName(name);

			if (qName == null) {
				continue;
			}

			PublicRenderParameter publicRenderParameter =
				_portlet.getPublicRenderParameter(
					qName.getNamespaceURI(), qName.getLocalPart());

			if (publicRenderParameter != null) {
				renderParameters.put(
					publicRenderParameter.getIdentifier(),
					req.getParameterValues(name));
			}
		}
	}

	protected void recycle() {
		_req.removeAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		_req.removeAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		_req.removeAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);
		_req.removeAttribute(PortletRequest.LIFECYCLE_PHASE);

		_req = null;
		_wapTheme = false;
		_portlet = null;
		_portletName = null;
		_portalCtx = null;
		_portletCtx = null;
		_windowState = null;
		_portletMode = null;
		_prefs = null;
		_ses = null;
		_portalSessionId = null;
		_remoteUser = null;
		_userPrincipal = null;
		_profile = null;
		_locale = null;
		_plid = 0;
	}

	private static Log _log = LogFactory.getLog(PortletRequestImpl.class);

	private DynamicServletRequest _req;
	private boolean _wapTheme;
	private Portlet _portlet;
	private String _portletName;
	private PortalContext _portalCtx;
	private PortletContext _portletCtx;
	private WindowState _windowState;
	private PortletMode _portletMode;
	private PortletPreferences _prefs;
	private PortletSessionImpl _ses;
	private String _portalSessionId;
	private boolean _invalidSession;
	private String _remoteUser;
	private long _remoteUserId;
	private Principal _userPrincipal;
	private Profile _profile;
	private Locale _locale;
	private long _plid;

	private static final Profile _EMPTY_PROFILE = new EmptyProfile();

}