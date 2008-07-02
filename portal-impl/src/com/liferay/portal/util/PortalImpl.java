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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.service.ClassNameServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.PortletBag;
import com.liferay.portlet.PortletBagPool;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.UserAttributes;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.Encryptor;
import com.liferay.util.JS;
import com.liferay.util.servlet.DynamicServletRequest;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import java.rmi.RemoteException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.portlet.filter.PortletRequestWrapper;
import javax.portlet.filter.PortletResponseWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="PortalImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 *
 */
public class PortalImpl implements Portal {

	public PortalImpl() {

		// Computer name

		_computerName = System.getProperty("env.COMPUTERNAME");

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOST");
		}

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOSTNAME");
		}

		if (Validator.isNull(_computerName)) {
			try {
				_computerName = InetAddress.getLocalHost().getHostName();
			}
			catch (UnknownHostException uhe) {
			}
		}

		try {
			_computerAddress = InetAddress.getByName(
				_computerName).getHostAddress();
		}
		catch (UnknownHostException uhe) {
		}

		if (Validator.isNull(_computerAddress)) {
			try {
				_computerAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException uhe) {
			}
		}

		// Portal lib directory

		ClassLoader classLoader = getClass().getClassLoader();

		URL url = classLoader.getResource(
			"com/liferay/portal/util/PortalImpl.class");

		String file = null;

		try {
			file = new URI(url.getPath()).getPath();
		}
		catch (URISyntaxException urise) {
			file = url.getFile();
		}

		if (_log.isInfoEnabled()) {
			_log.info("Portal lib url " + file);
		}

		int pos = file.indexOf("/com/liferay/portal/util/");

		_portalLibDir =	file.substring(0, pos + 1);

		if (_portalLibDir.endsWith("/WEB-INF/classes/")) {
			_portalLibDir = _portalLibDir.substring(
				0, _portalLibDir.length() - 8) + "lib/";
		}
		else {
			pos = _portalLibDir.indexOf("/WEB-INF/lib/");

			if (pos != -1) {
				_portalLibDir =
					_portalLibDir.substring(0, pos) + "/WEB-INF/lib/";
			}
		}

		if (_portalLibDir.startsWith("file:/")) {
			_portalLibDir = _portalLibDir.substring(5, _portalLibDir.length());
		}

		if (_log.isInfoEnabled()) {
			_log.info("Portal lib directory " + _portalLibDir);
		}

		// CDN host

		_cdnHost = PropsUtil.get(PropsKeys.CDN_HOST);

		// Paths

		_pathContext = PropsUtil.get(PropsKeys.PORTAL_CTX);

		if (_pathContext.equals(StringPool.SLASH)) {
			_pathContext = StringPool.BLANK;
		}

		_pathFriendlyURLPrivateGroup =
			_pathContext +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		_pathFriendlyURLPrivateUser =
			_pathContext +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		_pathFriendlyURLPublic =
			_pathContext +
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;
		_pathImage = _cdnHost + _pathContext + PATH_IMAGE;
		_pathMain = _pathContext + PATH_MAIN;

		// Groups

		String customSystemGroups[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_GROUPS);

		if ((customSystemGroups == null) || (customSystemGroups.length == 0)) {
			_allSystemGroups = GroupImpl.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = ArrayUtil.append(
				GroupImpl.SYSTEM_GROUPS, customSystemGroups);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Regular roles

		String customSystemRoles[] = PropsUtil.getArray(PropsKeys.SYSTEM_ROLES);

		if ((customSystemRoles == null) || (customSystemRoles.length == 0)) {
			_allSystemRoles = RoleImpl.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = ArrayUtil.append(
				RoleImpl.SYSTEM_ROLES, customSystemRoles);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Community roles

		String customSystemCommunityRoles[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_COMMUNITY_ROLES);

		if ((customSystemCommunityRoles == null) ||
			(customSystemCommunityRoles.length == 0)) {

			_allSystemCommunityRoles = RoleImpl.SYSTEM_COMMUNITY_ROLES;
		}
		else {
			_allSystemCommunityRoles = ArrayUtil.append(
				RoleImpl.SYSTEM_COMMUNITY_ROLES, customSystemCommunityRoles);
		}

		_sortedSystemCommunityRoles =
			new String[_allSystemCommunityRoles.length];

		System.arraycopy(
			_allSystemCommunityRoles, 0, _sortedSystemCommunityRoles, 0,
				_allSystemCommunityRoles.length);

		Arrays.sort(_sortedSystemCommunityRoles, new StringComparator());

		// Organization Roles

		String customSystemOrganizationRoles[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_ORGANIZATION_ROLES);

		if ((customSystemOrganizationRoles == null) ||
			(customSystemOrganizationRoles.length == 0)) {

			_allSystemOrganizationRoles = RoleImpl.SYSTEM_ORGANIZATION_ROLES;
		}
		else {
			_allSystemOrganizationRoles = ArrayUtil.append(
				RoleImpl.SYSTEM_ORGANIZATION_ROLES,
				customSystemOrganizationRoles);
		}

		_sortedSystemOrganizationRoles =
			new String[_allSystemOrganizationRoles.length];

		System.arraycopy(
			_allSystemOrganizationRoles, 0, _sortedSystemOrganizationRoles, 0,
				_allSystemOrganizationRoles.length);

		Arrays.sort(_sortedSystemOrganizationRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = new HashSet<String>();

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_reset");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_lifecycle");
		_reservedParams.add("p_p_url_type");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_resource_id");
		_reservedParams.add("p_p_cacheability");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
		_reservedParams.add("p_p_static");
		_reservedParams.add("saveLastPath");
	}

	public void clearRequestParameters(RenderRequest renderRequest) {

		// Clear the render parameters if they were set during processAction

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleAction()) {
			((RenderRequestImpl)renderRequest).getRenderParameters().clear();
		}
	}

	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)actionResponse;

			Map<String, String[]> renderParameters =
				actionResponseImpl.getRenderParameterMap();

			actionResponse.setRenderParameter("p_p_lifecycle", "1");

			Enumeration<String> enu = actionRequest.getParameterNames();

			while (enu.hasMoreElements()) {
				String param = enu.nextElement();
				String[] values = actionRequest.getParameterValues(param);

				if (renderParameters.get(
						actionResponseImpl.getNamespace() + param) == null) {

					actionResponse.setRenderParameter(param, values);
				}
			}
		}
		catch (IllegalStateException ise) {

			// This should only happen if the developer called
			// sendRedirect of javax.portlet.ActionResponse

		}
	}

	public String getCDNHost() {
		return _cdnHost;
	}

	public String getClassName(long classNameId) {
		try {
			ClassName className = ClassNameServiceUtil.getClassName(
				classNameId);

			return className.getValue();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from id " + classNameId);
		}
	}

	public long getClassNameId(Class<?> classObj) {
		return getClassNameId(classObj.getName());
	}

	public long getClassNameId(String value) {
		try {
			ClassName className = ClassNameServiceUtil.getClassName(value);

			return className.getClassNameId();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from value " + value);
		}
	}

	public String getClassNamePortletId(String className) {
		String portletId = StringPool.BLANK;

		if (className.startsWith("com.liferay.portlet.blogs")) {
			portletId = PortletKeys.BLOGS;
		}
		else if (className.startsWith("com.liferay.portlet.bookmarks")) {
			portletId = PortletKeys.BOOKMARKS;
		}
		else if (className.startsWith("com.liferay.portlet.documentlibrary")) {
			portletId = PortletKeys.DOCUMENT_LIBRARY;
		}
		else if (className.startsWith("com.liferay.portlet.imagegallery")) {
			portletId = PortletKeys.IMAGE_GALLERY;
		}
		else if (className.startsWith("com.liferay.portlet.journal")) {
			portletId = PortletKeys.JOURNAL;
		}
		else if (className.startsWith("com.liferay.portlet.messageboards")) {
			portletId = PortletKeys.MESSAGE_BOARDS;
		}
		else if (className.startsWith("com.liferay.portlet.wiki")) {
			portletId = PortletKeys.WIKI;
		}

		return portletId;
	}

	public String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (Validator.isNull(PropsValues.AUTH_LOGIN_COMMUNITY_URL)) {
			return null;
		}

		for (Layout layout : themeDisplay.getLayouts()) {
			if (layout.getFriendlyURL().equals(
					PropsValues.AUTH_LOGIN_COMMUNITY_URL)) {

				if (themeDisplay.getLayout() != null) {
					String layoutSetFriendlyURL = getLayoutSetFriendlyURL(
						themeDisplay.getLayout().getLayoutSet(), themeDisplay);

					return layoutSetFriendlyURL +
						PropsValues.AUTH_LOGIN_COMMUNITY_URL;
				}

				break;
			}
		}

		return null;
	}

	public Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		long companyId = getCompanyId(request);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)request.getAttribute(WebKeys.COMPANY);

		if (company == null) {
			company = CompanyLocalServiceUtil.getCompanyById(companyId);

			request.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public Company getCompany(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(actionRequest));
	}

	public Company getCompany(RenderRequest renderRequest)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(renderRequest));
	}

	public long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	public long getCompanyId(ActionRequest actionRequest) {
		return getCompanyId(getHttpServletRequest(actionRequest));
	}

	public long getCompanyId(PortletRequest portletRequest) {
		long companyId = 0;

		if (portletRequest instanceof ActionRequest) {
			companyId = getCompanyId((ActionRequest)portletRequest);
		}
		else {
			companyId = getCompanyId((RenderRequest)portletRequest);
		}

		return companyId;
	}

	public long getCompanyId(RenderRequest renderRequest) {
		return getCompanyId(getHttpServletRequest(renderRequest));
	}

	public long getCompanyIdByWebId(ServletContext servletContext) {
		String webId = GetterUtil.getString(
			servletContext.getInitParameter("company_web_id"));

		return getCompanyIdByWebId(webId);
	}

	public long getCompanyIdByWebId(String webId) {
		long companyId = 0;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyByWebId(webId);

			companyId = company.getCompanyId();
		}
		catch (Exception e) {
			_log.error(e.getMessage());
		}

		return companyId;
	}

	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	public String getComputerAddress() {
		return _computerAddress;
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getCurrentURL(HttpServletRequest request) {
		String currentURL = (String)request.getAttribute(WebKeys.CURRENT_URL);

		if (currentURL == null) {
			currentURL = ParamUtil.getString(request, "currentURL");

			if (Validator.isNull(currentURL)) {
				if (true) {
					currentURL = HttpUtil.getCompleteURL(request);
				}
				else {

					// Do we need to trim redirects?

					currentURL = _getCurrentURL(request);
				}

				if ((Validator.isNotNull(currentURL)) &&
					(currentURL.indexOf("j_security_check") == -1)) {

					currentURL = currentURL.substring(
						currentURL.indexOf("://") + 3, currentURL.length());

					currentURL = currentURL.substring(
						currentURL.indexOf("/"), currentURL.length());
				}

				if (Validator.isNotNull(currentURL) &&
					FacebookUtil.isFacebook(currentURL)) {

					String[] facebookData = FacebookUtil.getFacebookData(
						request);

					currentURL =
						FacebookUtil.FACEBOOK_APPS_URL + facebookData[0] +
							facebookData[2];
				}
			}

			if (Validator.isNull(currentURL)) {
				currentURL = getPathMain();
			}

			request.setAttribute(WebKeys.CURRENT_URL, currentURL);
		}

		return currentURL;
	}

	public String getCurrentURL(PortletRequest portletRequest) {
		return (String)portletRequest.getAttribute(WebKeys.CURRENT_URL);
	}

	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, null, pe);
	}

	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, pe);
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, pe);
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			throw pe;
		}
		else {
			Calendar cal = null;

			if (timeZone == null) {
				cal = CalendarFactoryUtil.getCalendar();
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day, 0, 0, 0);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			cal.set(Calendar.MILLISECOND, 0);

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(CalendarFactoryUtil.getCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public String getHost(HttpServletRequest request) {
		request = getOriginalServletRequest(request);

		String host = request.getHeader("Host");

		if (host != null) {
			host = host.trim().toLowerCase();

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

	public String getHost(ActionRequest actionRequest) {
		return getHost(getHttpServletRequest(actionRequest));
	}

	public String getHost(RenderRequest renderRequest) {
		return getHost(getHttpServletRequest(renderRequest));
	}

	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		if (portletRequest instanceof PortletRequestImpl) {
			PortletRequestImpl portletRequestImpl =
				(PortletRequestImpl)portletRequest;

			return portletRequestImpl.getHttpServletRequest();
		}
		else if (portletRequest instanceof PortletRequestWrapper) {
			PortletRequestWrapper portletRequestWrapper =
				(PortletRequestWrapper)portletRequest;

			return getHttpServletRequest(portletRequestWrapper.getRequest());
		}
		else {
			throw new RuntimeException(
				"Unable to get the HTTP servlet request from " +
					portletRequest.getClass().getName());
		}
	}

	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		if (portletResponse instanceof ActionResponseImpl) {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)portletResponse;

			return actionResponseImpl.getHttpServletResponse();
		}
		else if (portletResponse instanceof RenderResponseImpl) {
			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)portletResponse;

			return renderResponseImpl.getHttpServletResponse();
		}
		else if (portletResponse instanceof PortletResponseWrapper) {
			PortletResponseWrapper portletResponseWrapper =
				(PortletResponseWrapper)portletResponse;

			return getHttpServletResponse(portletResponseWrapper.getResponse());
		}
		else {
			PortletResponseImpl portletResponseImpl =
				PortletResponseImpl.getPortletResponseImpl(portletResponse);

			return portletResponseImpl.getHttpServletResponse();
		}
	}

	public String getLayoutEditPage(Layout layout) {
		return PropsUtil.get(
			PropsKeys.LAYOUT_EDIT_PAGE, new Filter(layout.getType()));
	}

	public String getLayoutViewPage(Layout layout) {
		return PropsUtil.get(
			PropsKeys.LAYOUT_VIEW_PAGE, new Filter(layout.getType()));
	}

	public String getLayoutURL(ThemeDisplay themeDisplay) {
		return getLayoutURL(themeDisplay.getLayout(), themeDisplay);
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay) {
		return getLayoutURL(layout, themeDisplay, true);
	}

	public String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		if (layout == null) {
			return themeDisplay.getPathMain() + PATH_PORTAL_LAYOUT;
		}

		if (!layout.getType().equals(LayoutConstants.TYPE_URL)) {
			String layoutFriendlyURL = getLayoutFriendlyURL(
				layout, themeDisplay);

			if (Validator.isNotNull(layoutFriendlyURL)) {
				if (doAsUser &&
					Validator.isNotNull(themeDisplay.getDoAsUserId())) {

					layoutFriendlyURL = HttpUtil.addParameter(
						layoutFriendlyURL, "doAsUserId",
						themeDisplay.getDoAsUserId());
				}

				return layoutFriendlyURL;
			}
		}

		String layoutURL = getLayoutActualURL(layout);

		if (doAsUser && Validator.isNotNull(themeDisplay.getDoAsUserId())) {
			layoutURL = HttpUtil.addParameter(
				layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		return layoutURL;
	}

	public String getLayoutActualURL(Layout layout) {
		return getLayoutActualURL(layout, getPathMain());
	}

	public String getLayoutActualURL(Layout layout, String mainPath) {
		Map<String, String> variables = new HashMap<String, String>();

		variables.put("liferay:mainPath", mainPath);
		variables.put("liferay:plid", String.valueOf(layout.getPlid()));

		Properties typeSettingsProperties =
			layout.getLayoutType().getTypeSettingsProperties();

		Iterator<Map.Entry<Object, Object>> itr =
			typeSettingsProperties.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			variables.put(key, value);
		}

		String href = PropsUtil.get(
			PropsKeys.LAYOUT_URL, new Filter(layout.getType(), variables));

		return href;
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, null);
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException {

		Layout layout = null;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				layout = layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					"{groupId=" + groupId + ",privateLayout=" + privateLayout +
						"} does not have any layouts");
			}
		}
		else {
			Object[] friendlyURLMapper = getPortletFriendlyURLMapper(
				groupId, privateLayout, friendlyURL, params);

			layout = (Layout)friendlyURLMapper[0];
			queryString = (String)friendlyURLMapper[1];
		}

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		if (Validator.isNotNull(queryString)) {
			layoutActualURL = layoutActualURL + queryString;
		}

		return layoutActualURL;
	}

	public String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay) {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(layoutSet.getVirtualHost())) {
			String portalURL = getPortalURL(
				layoutSet.getVirtualHost(), themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
					(portalURL.startsWith(themeDisplay.getURLPortal()))) {

				return portalURL + getPathContext() + layoutFriendlyURL;
			}
		}

		Group group = layout.getGroup();

		String friendlyURL = null;

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = getPathFriendlyURLPrivateUser();
			}
			else {
				friendlyURL = getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			friendlyURL = getPathFriendlyURLPublic();
		}

		if (themeDisplay.isWidget()) {
			friendlyURL = "/widget" + friendlyURL;
		}

		if (themeDisplay.isI18n()) {
			friendlyURL =
				StringPool.SLASH + themeDisplay.getI18nLanguageId() +
					friendlyURL;
		}

		return friendlyURL + group.getFriendlyURL() + layoutFriendlyURL;
	}

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (Validator.isNotNull(layoutSet.getVirtualHost())) {
			String portalURL = getPortalURL(
				layoutSet.getVirtualHost(), themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
				(portalURL.startsWith(themeDisplay.getURLPortal()))) {

				return portalURL + getPathContext();
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(layoutSet.getGroupId());

		String friendlyURL = null;

		if (layoutSet.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = getPathFriendlyURLPrivateUser();
			}
			else {
				friendlyURL = getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			friendlyURL = getPathFriendlyURLPublic();
		}

		return friendlyURL + group.getFriendlyURL();
	}

	public String getLayoutTarget(Layout layout) {
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

	public String getJsSafePortletId(String portletId) {
		return JS.getSafeName(portletId);
	}

	public Locale getLocale(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return themeDisplay.getLocale();
		}
		else {
			HttpSession session = request.getSession();

			return (Locale)session.getAttribute(Globals.LOCALE_KEY);
		}
	}

	public Locale getLocale(RenderRequest renderRequest) {
		return getLocale(getHttpServletRequest(renderRequest));
	}

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		HttpServletRequest originalRequest = request;

		while (originalRequest.getClass().getName().startsWith(
					"com.liferay.")) {

			// Get original request so that portlets inside portlets render
			// properly

			originalRequest = (HttpServletRequest)
				((HttpServletRequestWrapper)originalRequest).getRequest();
		}

		return originalRequest;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	public String getPathImage() {
		return _pathImage;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return LayoutConstants.DEFAULT_PLID;
		}

		String[] urlParts = friendlyURL.split("\\/", 4);

		if ((friendlyURL.charAt(0) != CharPool.SLASH) &&
			(urlParts.length != 4)) {

			return LayoutConstants.DEFAULT_PLID;
		}

		boolean privateLayout = true;

		String urlPrefix = StringPool.SLASH + urlParts[1];

		if (getPathFriendlyURLPublic().equals(urlPrefix)) {
			privateLayout = false;
		}
		else if (getPathFriendlyURLPrivateGroup().equals(urlPrefix) ||
				 getPathFriendlyURLPrivateUser().equals(urlPrefix)) {

			privateLayout = true;
		}
		else {
			return LayoutConstants.DEFAULT_PLID;
		}

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + urlParts[2]);
		}
		catch (Exception e) {
		}

		if (group != null) {
			Layout layout = null;

			try {
				layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
					group.getGroupId(), privateLayout,
					StringPool.SLASH + urlParts[3]);

				return layout.getPlid();
			}
			catch (Exception e) {
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long plid = LayoutConstants.DEFAULT_PLID;

		StringBuilder sb = new StringBuilder();

		sb.append(groupId);
		sb.append(StringPool.SPACE);
		sb.append(privateLayout);
		sb.append(StringPool.SPACE);
		sb.append(portletId);

		String key = sb.toString();

		Long plidObj = _plidToPortletIdCache.get(key);

		if (plidObj == null) {
			plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

			if (plid != LayoutConstants.DEFAULT_PLID) {
				_plidToPortletIdCache.put(key, plid);
			}
		}
		else {
			plid = plidObj.longValue();

			boolean validPlid = false;

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(portletId)) {
					validPlid = true;
				}
			}
			catch (Exception e) {
			}

			if (!validPlid) {
				_plidToPortletIdCache.remove(key);

				plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					_plidToPortletIdCache.put(key, plid);
				}
			}
		}

		return plid;
	}

	public String getPortalLibDir() {
		return _portalLibDir;
	}

	public int getPortalPort() {
		return _portalPort.intValue();
	}

	public String getPortalURL(ThemeDisplay themeDisplay) {
		String serverName = themeDisplay.getServerName();

		Layout layout = themeDisplay.getLayout();

		if (layout != null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (Validator.isNotNull(layoutSet.getVirtualHost())) {
				serverName = layoutSet.getVirtualHost();
			}
		}

		return getPortalURL(
			serverName, themeDisplay.getServerPort(), themeDisplay.isSecure());
	}

	public String getPortalURL(HttpServletRequest request) {
		return getPortalURL(request, request.isSecure());
	}

	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return getPortalURL(
			request.getServerName(), request.getServerPort(), secure);
	}

	public String getPortalURL(PortletRequest portletRequest) {
		return getPortalURL(portletRequest, portletRequest.isSecure());
	}

	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getPortalURL(
			portletRequest.getServerName(), portletRequest.getServerPort(),
			secure);
	}

	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBuilder sb = new StringBuilder();

		if (secure || Http.HTTPS.equals(PropsValues.WEB_SERVER_PROTOCOL)) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		if (Validator.isNull(PropsValues.WEB_SERVER_HOST)) {
			sb.append(serverName);
		}
		else {
			sb.append(PropsValues.WEB_SERVER_HOST);
		}

		if (!secure) {
			if (PropsValues.WEB_SERVER_HTTP_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if ((PropsValues.WEB_SERVER_HTTP_PORT != serverPort) ||
					(PropsValues.WEB_SERVER_HTTP_PORT != Http.HTTP_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTP_PORT);
				}
			}
		}

		if (secure) {
			if (PropsValues.WEB_SERVER_HTTPS_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if ((PropsValues.WEB_SERVER_HTTPS_PORT != serverPort) ||
					(PropsValues.WEB_SERVER_HTTPS_PORT != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTPS_PORT);
				}
			}
		}

		return sb.toString();
	}

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException {

		return getPortletFriendlyURLMapper(groupId, privateLayout, url, null);
	}

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException {

		boolean foundFriendlyURLMapper = false;

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		List<FriendlyURLMapper> friendlyURLMappers =
			PortletLocalServiceUtil.getFriendlyURLMappers();

		Iterator<FriendlyURLMapper> itr = friendlyURLMappers.iterator();

		while (itr.hasNext()) {
			FriendlyURLMapper friendlyURLMapper = itr.next();

			if (url.endsWith(
					StringPool.SLASH + friendlyURLMapper.getMapping())) {

				url += StringPool.SLASH;
			}

			int pos = -1;

			if (friendlyURLMapper.isCheckMappingWithPrefix()) {
				pos = url.indexOf(
					"/-/" + friendlyURLMapper.getMapping() + StringPool.SLASH);
			}
			else {
				pos = url.indexOf(
					StringPool.SLASH + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}

			if (pos != -1) {
				foundFriendlyURLMapper = true;

				friendlyURL = url.substring(0, pos);

				Map<String, String[]> actualParams = null;

				if (params != null) {
					actualParams = new HashMap<String, String[]>(params);
				}
				else {
					actualParams = new HashMap<String, String[]>();
				}

				/*Object lifecycle = actualParams.get("p_p_lifecycle");

				if ((lifecycle == null) ||
					(((String[])lifecycle).length == 0)) {

					actualParams.put("p_p_lifecycle", "0");
				}

				Object state = actualParams.get("p_p_state");

				if ((state == null) || (((String[])state).length == 0)) {
					actualParams.put(
						"p_p_state", WindowState.MAXIMIZED.toString());
				}*/

				if (friendlyURLMapper.isCheckMappingWithPrefix()) {
					friendlyURLMapper.populateParams(
						url.substring(pos + 2), actualParams);
				}
				else {
					friendlyURLMapper.populateParams(
						url.substring(pos), actualParams);
				}

				queryString =
					StringPool.AMPERSAND +
						HttpUtil.parameterMapToString(actualParams, false);

				break;
			}
		}

		if (!foundFriendlyURLMapper) {
			int x = url.indexOf("/-/");

			if (x != -1) {
				int y = url.indexOf(StringPool.SLASH, x + 3);

				if (y == -1) {
					y = url.length();
				}

				String ppid = url.substring(x + 3, y);

				if (Validator.isNotNull(ppid)) {
					friendlyURL = url.substring(0, x);

					Map<String, String[]> actualParams = null;

					if (params != null) {
						actualParams = new HashMap<String, String[]>(params);
					}
					else {
						actualParams = new HashMap<String, String[]>();
					}

					actualParams.put("p_p_id", new String[] {ppid});
					actualParams.put("p_p_lifecycle", new String[] {"0"});
					actualParams.put(
						"p_p_state",
						new String[] {WindowState.MAXIMIZED.toString()});
					actualParams.put(
						"p_p_mode", new String[] {PortletMode.VIEW.toString()});

					queryString =
						StringPool.AMPERSAND +
							HttpUtil.parameterMapToString(actualParams, false);
				}
			}
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			groupId, privateLayout, friendlyURL);

		return new Object[] {layout, queryString};
	}

	public long getPortletGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getPortletGroupId(layout);
	}

	public long getPortletGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}
		else {
			return layout.getGroupId();
		}
	}

	public long getPortletGroupId(HttpServletRequest request) {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		return getPortletGroupId(layout);
	}

	public long getPortletGroupId(ActionRequest actionRequest) {
		return getPortletGroupId(getHttpServletRequest(actionRequest));
	}

	public long getPortletGroupId(RenderRequest renderRequest) {
		return getPortletGroupId(getHttpServletRequest(renderRequest));
	}

	public String getPortletId(HttpServletRequest request) {
		PortletConfigImpl configImpl = (PortletConfigImpl)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		return configImpl.getPortletId();
	}

	public String getPortletId(ActionRequest actionRequest) {
		PortletConfigImpl configImpl =
			(PortletConfigImpl)actionRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return configImpl.getPortletId();
	}

	public String getPortletId(RenderRequest renderRequest) {
		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return portletConfigImpl.getPortletId();
	}

	public String getPortletNamespace(String portletId) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.UNDERLINE);
		sb.append(portletId);
		sb.append(StringPool.UNDERLINE);

		return sb.toString();
	}

	public String getPortletTitle(
		String portletId, long companyId, String languageId) {

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletTitle(portletId, companyId, locale);
	}

	public String getPortletTitle(
		String portletId, long companyId, Locale locale) {

		StringBuilder sb = new StringBuilder();

		sb.append(JavaConstants.JAVAX_PORTLET_TITLE);
		sb.append(StringPool.PERIOD);
		sb.append(portletId);

		return LanguageUtil.get(companyId, locale, sb.toString());
	}

	public String getPortletTitle(String portletId, User user) {
		StringBuilder sb = new StringBuilder();

		sb.append(JavaConstants.JAVAX_PORTLET_TITLE);
		sb.append(StringPool.PERIOD);
		sb.append(portletId);

		return LanguageUtil.get(
			user.getCompanyId(), user.getLocale(), sb.toString());
	}

	public String getPortletTitle(
		Portlet portlet, long companyId, String languageId) {

		return getPortletTitle(portlet.getPortletId(), companyId, languageId);
	}

	public String getPortletTitle(
		Portlet portlet, long companyId, Locale locale) {

		return getPortletTitle(portlet.getPortletId(), companyId, locale);
	}

	public String getPortletTitle(Portlet portlet, User user) {
		return getPortletTitle(portlet.getPortletId(), user);
	}

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(JavaConstants.JAVAX_PORTLET_TITLE);
	}

	public String getPortletXmlFileName()
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
				PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

			return PORTLET_XML_FILE_NAME_CUSTOM;
		}
		else {
			return PORTLET_XML_FILE_NAME_STANDARD;
		}
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		RenderRequest renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences prefs = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper prefsWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			prefs = prefsWrapper.getPreferencesImpl();
		}

		return prefs;
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			return portletBag.getPreferencesValidatorInstance();
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

	public User getSelectedUser(HttpServletRequest request)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(request, true);
	}

	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		long userId = ParamUtil.getLong(request, "p_u_i_d");

		User user = null;

		try {
			if (checkPermission) {
				user = UserServiceUtil.getUserById(userId);
			}
			else {
				user = UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public User getSelectedUser(ActionRequest actionRequest)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(actionRequest, true);
	}

	public User getSelectedUser(
			ActionRequest actionRequest, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(
			getHttpServletRequest(actionRequest), checkPermission);
	}

	public User getSelectedUser(RenderRequest renderRequest)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(renderRequest, true);
	}

	public User getSelectedUser(
			RenderRequest renderRequest, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(
			getHttpServletRequest(renderRequest), checkPermission);
	}

	public String getStrutsAction(HttpServletRequest request) {
		String strutsAction = ParamUtil.getString(request, "struts_action");

		if (Validator.isNotNull(strutsAction)) {

			// This method should only return a Struts action if you're dealing
			// with a regular HTTP servlet request, not a portlet HTTP servlet
			// request.

			return StringPool.BLANK;
		}

		int strutsActionCount = 0;

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			int pos = name.indexOf("_struts_action");

			if (pos != -1) {
				strutsActionCount++;

				// There should never be more than one Struts action

				if (strutsActionCount > 1) {
					return StringPool.BLANK;
				}

				String curStrutsAction = ParamUtil.getString(request, name);

				if (Validator.isNotNull(curStrutsAction)) {

					// The Struts action must be for the correct portlet

					String portletId1 = name.substring(1, pos);
					String portletId2 = ParamUtil.getString(request, "p_p_id");

					if (portletId1.equals(portletId2)) {
						strutsAction = curStrutsAction;
					}
				}
			}
		}

		return strutsAction;
	}

	public String[] getSystemCommunityRoles() {
		return _allSystemCommunityRoles;
	}

	public String[] getSystemGroups() {
		return _allSystemGroups;
	}

	public String[] getSystemOrganizationRoles() {
		return _allSystemOrganizationRoles;
	}

	public String[] getSystemRoles() {
		return _allSystemRoles;
	}

	public UploadPortletRequest getUploadPortletRequest(
		ActionRequest actionRequest) {

		ActionRequestImpl actionRequestImpl = (ActionRequestImpl)actionRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)actionRequestImpl.getHttpServletRequest();

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)dynamicRequest.getRequest();

		UploadServletRequest uploadRequest = getUploadServletRequest(
			requestWrapper);

		return new UploadPortletRequestImpl(
			uploadRequest,
			PortalUtil.getPortletNamespace(actionRequestImpl.getPortletName()));
	}

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		HttpServletRequestWrapper requestWrapper = null;

		if (request instanceof HttpServletRequestWrapper) {
			requestWrapper = (HttpServletRequestWrapper)request;
		}

		UploadServletRequest uploadRequest = null;

		while (uploadRequest == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (requestWrapper instanceof UploadServletRequest) {
				uploadRequest = (UploadServletRequest)requestWrapper;
			}
			else {
				HttpServletRequest parentRequest =
					(HttpServletRequest)requestWrapper.getRequest();

				if (!(parentRequest instanceof HttpServletRequestWrapper)) {

					// This block should never be reached unless this method is
					// called from a hot deployable portlet. See LayoutAction.

					uploadRequest = new UploadServletRequestImpl(parentRequest);

					break;
				}
				else {
					requestWrapper = (HttpServletRequestWrapper)parentRequest;
				}
			}
		}

		return uploadRequest;
	}

	public Date getUptime() {
		return UP_TIME;
	}

	public String getURLWithSessionId(String url, String sessionId) {

		// LEP-4787

		int x = url.indexOf(StringPool.SEMICOLON);

		if (x != -1) {
			return url;
		}

		x = url.indexOf(StringPool.QUESTION);

		if (x != -1) {
			StringBuilder sb = new StringBuilder();

			sb.append(url.substring(0, x));
			sb.append(_JSESSIONID);
			sb.append(sessionId);
			sb.append(url.substring(x));

			return sb.toString();
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work,
		// but http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf(StringPool.DOUBLE_SLASH);

		StringBuilder sb = new StringBuilder();

		sb.append(url);

		if (x != -1) {
			int y = url.lastIndexOf(StringPool.SLASH);

			if (x + 1 == y) {
				sb.append(StringPool.SLASH);
			}
		}

		sb.append(_JSESSIONID);
		sb.append(sessionId);

		return sb.toString();
	}

	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		long userId = getUserId(request);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			String remoteUser = request.getRemoteUser();

			if (remoteUser == null) {
				return null;
			}

			userId = GetterUtil.getLong(remoteUser);
		}

		User user = (User)request.getAttribute(WebKeys.USER);

		if (user == null) {
			user = UserLocalServiceUtil.getUserById(userId);

			request.setAttribute(WebKeys.USER, user);
		}

		return user;
	}

	public User getUser(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(actionRequest));
	}

	public User getUser(RenderRequest renderRequest)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(renderRequest));
	}

	public long getUserId(HttpServletRequest request) {
		Long userIdObj = (Long)request.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		if (!PropsValues.PORTAL_JAAS_ENABLE &&
			PropsValues.PORTAL_IMPERSONATION_ENABLE) {

			String doAsUserIdString = ParamUtil.getString(
				request, "doAsUserId");

			try {
				long doAsUserId = getDoAsUserId(request, doAsUserIdString);

				if (doAsUserId > 0) {
					if (_log.isDebugEnabled()) {
						_log.debug("Impersonating user " + doAsUserId);
					}

					return doAsUserId;
				}
			}
			catch (Exception e) {
				_log.error("Unable to impersonate user " + doAsUserIdString, e);
			}
		}

		HttpSession session = request.getSession();

		userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			request.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	public long getUserId(ActionRequest actionRequest) {
		return getUserId(getHttpServletRequest(actionRequest));
	}

	public long getUserId(RenderRequest renderRequest) {
		return getUserId(getHttpServletRequest(renderRequest));
	}

	public String getUserName(long userId, String defaultUserName) {
		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL, request);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			if (userAttribute.equals(UserAttributes.USER_NAME_FULL)) {
				userName = user.getFullName();
			}
			else {
				userName = user.getScreenName();
			}

			if (request != null) {
				Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					request, PortletKeys.DIRECTORY, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter(
					"struts_action", "/directory/edit_user");
				portletURL.setParameter(
					"p_u_i_d", String.valueOf(user.getUserId()));

				userName =
					"<a href=\"" + portletURL.toString() + "\">" + userName +
						"</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public String getUserPassword(HttpSession session) {
		return (String)session.getAttribute(WebKeys.USER_PASSWORD);
	}

	public String getUserPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return getUserPassword(session);
	}

	public String getUserPassword(ActionRequest actionRequest) {
		return getUserPassword(getHttpServletRequest(actionRequest));
	}

	public String getUserPassword(RenderRequest renderRequest) {
		return getUserPassword(getHttpServletRequest(renderRequest));
	}

	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException {

		if (Validator.isNotNull(defaultValue)) {
			return defaultValue;
		}
		else {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				return BeanPropertiesUtil.getString(user, param, defaultValue);
			}
			catch (PortalException pe) {
				return StringPool.BLANK;
			}
		}
	}

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay) {
		String widgetURL =
			themeDisplay.getPortalURL() + "/widget" +
				getLayoutURL(themeDisplay) + "/-/";

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if (friendlyURLMapper != null) {
			widgetURL += friendlyURLMapper.getMapping();
		}
		else {
			widgetURL += portlet.getPortletId();
		}

		return widgetURL;
	}

	public boolean isMethodGet(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.GET)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMethodPost(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.POST)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLayoutFriendliable(Layout layout) {
		return GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.LAYOUT_URL_FRIENDLIABLE,
				new Filter(layout.getType())),
			true);
	}

	public boolean isLayoutParentable(Layout layout) {
		return isLayoutParentable(layout.getType());
	}

	public boolean isLayoutParentable(String type) {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, new Filter(type)), true);
	}

	public boolean isLayoutSitemapable(Layout layout) {
		if (layout.isPrivateLayout()) {
			return false;
		}

		return GetterUtil.getBoolean(PropsUtil.get(
			PropsKeys.LAYOUT_SITEMAPABLE, new Filter(layout.getType())), true);
	}

	public boolean isReservedParameter(String name) {
		return _reservedParams.contains(name);
	}

	public boolean isSystemGroup(String groupName) {
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

	public boolean isSystemRole(String roleName) {
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
			pos = Arrays.binarySearch(
				_sortedSystemCommunityRoles, roleName, new StringComparator());

			if (pos >= 0) {
				return true;
			}
			else {
				pos = Arrays.binarySearch(
					_sortedSystemOrganizationRoles, roleName,
					new StringComparator());

				if (pos >= 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isUpdateAvailable()
		throws PortalException, SystemException {

		return PluginPackageUtil.isUpdateAvailable();
	}

	public void renderPage(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		requestDispatcher.include(request, stringResponse);

		sb.append(stringResponse.getString());
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString)
		throws IOException, ServletException {

		renderPortlet(
			sb, servletContext, request, response, portlet, queryString, null,
			null, null);
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		renderPortlet(
			sb, servletContext, request, response, portlet, queryString,
			columnId, columnPos, columnCount, null);
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount, String path)
		throws IOException, ServletException {

		queryString = GetterUtil.getString(queryString);
		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = new Integer(0);
		}

		if (columnCount == null) {
			columnCount = new Integer(0);
		}

		request.setAttribute(WebKeys.RENDER_PORTLET, portlet);
		request.setAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING, queryString);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		if (sb != null) {
			StringServletResponse stringResponse = new StringServletResponse(
				response);

			requestDispatcher.include(request, stringResponse);

			sb.append(stringResponse.getString());
		}
		else {

			// LEP-766

			response.setContentType(ContentTypes.TEXT_HTML_UTF8);

			requestDispatcher.include(request, response);
		}
	}

	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		sendError(0, e, request, response);
	}

	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (status == 0) {
			if (e instanceof PrincipalException) {
				status = HttpServletResponse.SC_FORBIDDEN;
			}
			else {
				String name = e.getClass().getName();

				name = name.substring(name.lastIndexOf(StringPool.PERIOD) + 1);

				if (name.startsWith("NoSuch") && name.endsWith("Exception")) {
					status = HttpServletResponse.SC_NOT_FOUND;
				}
			}

			if (status == 0) {
				status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			}
		}

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String redirect = PATH_MAIN + "/portal/status";

		if (e instanceof NoSuchLayoutException &&
			Validator.isNotNull(
				PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			response.setStatus(status);

			redirect = PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND;

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else if (PropsValues.LAYOUT_SHOW_HTTP_STATUS) {
			response.setStatus(status);

			SessionErrors.add(request, e.getClass().getName(), e);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			if (e != null) {
				response.sendError(status, e.getMessage());
			}
			else {
				response.sendError(status);
			}
		}
	}

	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		sendError(0, e, actionRequest, actionResponse);
	}

	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append(PATH_MAIN);
		sb.append("/portal/status?status=");
		sb.append(status);
		sb.append("&exception=");
		sb.append(e.getClass().getName());
		sb.append("&previousURL=");
		sb.append(HttpUtil.encodeURL(PortalUtil.getCurrentURL(actionRequest)));

		actionResponse.sendRedirect(sb.toString());
	}

	/**
	 * Sets the subtitle for a page. This is just a hint and can be overridden
	 * by subsequent calls. The last call to this method wins.
	 *
	 * @param		subtitle the subtitle for a page
	 * @param		request the HTTP servlet request
	 */
	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	/**
	 * Sets the whole title for a page. This is just a hint and can be
	 * overridden by subsequent calls. The last call to this method wins.
	 *
	 * @param		title the whole title for a page
	 * @param		request the HTTP servlet request
	 */
	public void setPageTitle(String title, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @param		request the HTTP servlet request
	 */
	public void setPortalPort(HttpServletRequest request) {
		if (_portalPort.intValue() == -1) {
			synchronized (_portalPort) {
				_portalPort = new Integer(request.getServerPort());
			}
		}
	}

	public void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper prefsWrapper =
			(PortletPreferencesWrapper)prefs;

		PortletPreferencesImpl prefsImpl = prefsWrapper.getPreferencesImpl();

		prefsImpl.store();
	}

	public String transformCustomSQL(String sql) {
		if ((_customSqlClassNames == null) ||
			(_customSqlClassNameIds == null)) {

			_initCustomSQL();
		}

		return StringUtil.replace(
			sql, _customSqlClassNames, _customSqlClassNameIds);
	}

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

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
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}

			return portletMode;
		}
	}

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

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

			if (windowState.equals(WindowState.MAXIMIZED) &&
				!layoutType.hasStateMaxPortletId(portletId)) {

				layoutType.addStateMaxPortletId(portletId);

				updateLayout = false;
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
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}

			return windowState;
		}
	}

	protected long getDoAsUserId(
			HttpServletRequest request, String doAsUserIdString)
		throws Exception {

		if (Validator.isNull(doAsUserIdString)) {
			return 0;
		}

		long doAsUserId = 0;

		try {
			Company company = getCompany(request);

			doAsUserId = GetterUtil.getLong(
				Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to impersonate " + doAsUserIdString +
						" because the string cannot be decrypted",
					e);
			}

			return 0;
		}

		String path = GetterUtil.getString(request.getPathInfo());

		if (_log.isDebugEnabled()) {
			_log.debug("doAsUserId path " + path);
		}

		String strutsAction = getStrutsAction(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Struts action " + strutsAction);
		}

		boolean alwaysAllowDoAsUser = false;

		if (path.equals("/portal/fckeditor") ||
			strutsAction.equals("/document_library/edit_file_entry") ||
			strutsAction.equals("/image_gallery/edit_image") ||
			strutsAction.equals("/wiki/edit_page_attachment")) {

			alwaysAllowDoAsUser = true;
		}

		if (_log.isDebugEnabled()) {
			if (alwaysAllowDoAsUser) {
				_log.debug(
					"doAsUserId path or Struts action is always allowed");
			}
			else {
				_log.debug(
					"doAsUserId path is Struts action not always allowed");
			}
		}

		if (alwaysAllowDoAsUser) {
			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}

		HttpSession session = request.getSession();

		Long realUserIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (realUserIdObj == null) {
			return 0;
		}

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		long[] organizationIds = doAsUser.getOrganizationIds();

		User realUser = UserLocalServiceUtil.getUserById(
			realUserIdObj.longValue());
		boolean checkGuest = true;

		PermissionCheckerImpl permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactory.create(
				realUser, checkGuest);

			if (doAsUser.isDefaultUser() ||
				UserPermissionUtil.contains(
					permissionChecker, doAsUserId, organizationIds,
					ActionKeys.IMPERSONATE)) {

				request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

				return doAsUserId;
			}
			else {
				_log.error(
					"User " + realUserIdObj + " does not have the permission " +
						"to impersonate " + doAsUserId);

				return 0;
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

	private String _getCurrentURL(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		StringBuffer requestURL = request.getRequestURL();

		if (requestURL != null) {
			sb.append(requestURL.toString());
		}

		String queryString = request.getQueryString();

		if (Validator.isNull(queryString)) {
			return sb.toString();
		}

		String portletId = request.getParameter("p_p_id");

		String redirectParam = "redirect";

		if (Validator.isNotNull(portletId)) {
			redirectParam = getPortletNamespace(portletId) + redirectParam;
		}

		Map<String, String[]> parameterMap = HttpUtil.parameterMapFromString(
			queryString);

		String[] redirectValues = parameterMap.get(redirectParam);

		if ((redirectValues != null) && (redirectValues.length > 0)) {

			// Prevent the redirect for GET requests from growing indefinitely
			// and using up all the available space by remembering only the
			// first redirect.

			String redirect = HttpUtil.decodeURL(
				GetterUtil.getString(redirectValues[0]));

			int pos = redirect.indexOf(StringPool.QUESTION);

			if (pos != -1) {
				String subqueryString = redirect.substring(
					pos + 1, redirect.length());

				Map<String, String[]> subparameterMap =
					HttpUtil.parameterMapFromString(subqueryString);

				String[] subredirectValues = subparameterMap.get(redirectParam);

				if ((subredirectValues != null) &&
					(subredirectValues.length > 0)) {

					String subredirect = HttpUtil.decodeURL(
						GetterUtil.getString(subredirectValues[0]));

					parameterMap.put(redirectParam, new String[] {subredirect});

					queryString = HttpUtil.parameterMapToString(
						parameterMap, false);
				}
			}
		}

		sb.append(StringPool.QUESTION);
		sb.append(queryString);

		return sb.toString();
	}

	private long _getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long plid = LayoutConstants.DEFAULT_PLID;

		try {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, LayoutConstants.TYPE_PORTLET);

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(portletId)) {
					plid = layout.getPlid();

					break;
				}
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se.getMessage());
			}
		}

		return plid;
	}

	private void _initCustomSQL() {
		_customSqlClassNames = new String[] {
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ORGANIZATION$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USERGROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BLOGS.MODEL.BLOGSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BOOKMARKS.MODEL." +
				"BOOKMARKSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFILEENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.IMAGEGALLERY.MODEL.IGIMAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBMESSAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.WIKI.MODEL.WIKIPAGE$]",
			"[$FALSE$]",
			"[$TRUE$]"
		};

		DBUtil dbUtil = DBUtil.getInstance();

		_customSqlClassNameIds = new String[] {
			String.valueOf(PortalUtil.getClassNameId(Organization.class)),
			String.valueOf(PortalUtil.getClassNameId(User.class)),
			String.valueOf(PortalUtil.getClassNameId(UserGroup.class)),
			String.valueOf(PortalUtil.getClassNameId(BlogsEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(BookmarksEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(IGImage.class)),
			String.valueOf(PortalUtil.getClassNameId(MBMessage.class)),
			String.valueOf(PortalUtil.getClassNameId(WikiPage.class)),
			dbUtil.getTemplateFalse(),
			dbUtil.getTemplateTrue()
		};
	}

	private static final String _JSESSIONID = ";jsessionid=";

	private static Log _log = LogFactory.getLog(PortalImpl.class);

	private String _computerAddress;
	private String _computerName;
	private String _portalLibDir;
	private String _cdnHost;
	private String _pathContext;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathMain;
	private Integer _portalPort = new Integer(-1);
	private String[] _allSystemCommunityRoles;
	private String[] _allSystemGroups;
	private String[] _allSystemOrganizationRoles;
	private String[] _allSystemRoles;
	private String[] _sortedSystemCommunityRoles;
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemOrganizationRoles;
	private String[] _sortedSystemRoles;
	private Set<String> _reservedParams;
	private String[] _customSqlClassNames = null;
	private String[] _customSqlClassNameIds = null;
	private Map<String, Long> _plidToPortletIdCache =
		new ConcurrentHashMap<String, Long>();

}