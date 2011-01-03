/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public interface Portal {

	public static final String FRIENDLY_URL_SEPARATOR = "/-/";

	public static final String PATH_IMAGE = "/image";

	public static final String PATH_MAIN = "/c";

	public static final String PATH_PORTAL_LAYOUT = "/portal/layout";

	public static final String PORTAL_REALM = "PortalRealm";

	public static final String PORTLET_XML_FILE_NAME_CUSTOM =
		"portlet-custom.xml";

	public static final String PORTLET_XML_FILE_NAME_STANDARD = "portlet.xml";

	public static final String TEMP_OBFUSCATION_VALUE =
		"TEMP_OBFUSCATION_VALUE";

	/**
	 * Adds the description for a page. This appends to the existing page
	 * description.
	 */
	public void addPageDescription(
		String description, HttpServletRequest request);

	/**
	 * Adds the keywords for a page. This appends to the existing page keywords.
	 */
	public void addPageKeywords(String keywords, HttpServletRequest request);

	/**
	 * Adds the subtitle for a page. This appends to the existing page subtitle.
	 */
	public void addPageSubtitle(String subtitle, HttpServletRequest request);

	/**
	 * Adds the whole title for a page. This appends to the existing page whole
	 * title.
	 */
	public void addPageTitle(String title, HttpServletRequest request);

	public void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener);

	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url);

	public void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException;

	/**
	 * Adds preserved parameters such as doAsGroupId, doAsUserId,
	 * doAsUserLanguageId, and referrerPlid that should always be preserved as
	 * the user navigates through the portal. If doAsUser is <code>false</code>,
	 * then doAsUserId and doAsUserLanguageId will never be added.
	 */
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url, boolean doAsUser);

	/**
	 * Adds preserved parameters such as doAsGroupId, doAsUserId,
	 * doAsUserLanguageId, and referrerPlid that should always be preserved as
	 * the user navigates through the portal.
	 */
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url);

	public void clearRequestParameters(RenderRequest renderRequest);

	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse);

	public String escapeRedirect(String url);

	public String generateRandomKey(HttpServletRequest request, String input);

	public Set<String> getAuthTokenIgnoreActions();

	public Set<String> getAuthTokenIgnorePortlets();

	public BaseModel<?> getBaseModel(Resource resource)
		throws PortalException, SystemException;

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException;

	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException;

	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException;

	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException, SystemException;

	/**
	 * @deprecated {@link #getCDNHost(boolean)}
	 */
	public String getCDNHost();

	public String getCDNHost(boolean secure);

	public String getCDNHostHttp();

	public String getCDNHostHttps();

	public String getClassName(long classNameId);

	public long getClassNameId(Class<?> classObj);

	public long getClassNameId(String value);

	public String getClassNamePortletId(String className);

	public String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String[] getCommunityPermissions(HttpServletRequest request);

	public String[] getCommunityPermissions(PortletRequest portletRequest);

	public Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException;

	public Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public long getCompanyId(HttpServletRequest requestuest);

	public long getCompanyId(PortletRequest portletRequest);

	public long[] getCompanyIds();

	public String getComputerAddress();

	public String getComputerName();

	public String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException;

	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException;

	public List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException;

	public String getCurrentCompleteURL(HttpServletRequest request);

	public String getCurrentURL(HttpServletRequest request);

	public String getCurrentURL(PortletRequest portletRequest);

	public String getCustomSQLFunctionIsNotNull();

	public String getCustomSQLFunctionIsNull();

	/**
	 * Gets the date object for the specified month, day, and year.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return the date object
	 */
	public Date getDate(int month, int day, int year);

	/**
	 * Gets the date object for the specified month, day, year, hour, and
	 * minute, optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  hour the hour (0-24)
	 * @param  min the minute of the hour
	 * @param  pe the exception to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException;

	/**
	 * Gets the date object for the specified month, day, year, hour, minute,
	 * and time zone, optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  hour the hour (0-24)
	 * @param  min the minute of the hour
	 * @param  timeZone the time zone of the date
	 * @param  pe the exception to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException;

	/**
	 * Gets the date object for the specified month, day, and year, optionally
	 * throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  pe the exception to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException;

	/**
	 * Gets the date object for the specified month, day, year, and time zone,
	 * optionally throwing an exception if the date is invalid.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @param  timeZone the time zone of the date
	 * @param  pe the exception to throw if the date is invalid. If
	 *         <code>null</code>, no exception will be thrown for an invalid
	 *         date.
	 * @return the date object, or <code>null</code> if the date is invalid and
	 *         no exception to throw was provided
	 * @throws PortalException if the date was invalid and <code>pe</code> was
	 *         not <code>null</code>
	 */
	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException;

	public long getDefaultCompanyId();

	public long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException;

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException;

	public String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getFirstPageLayoutTypes(PageContext pageContext);

	public String getGlobalLibDir();

	public String getGoogleGadgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String[] getGuestPermissions(HttpServletRequest request);

	public String[] getGuestPermissions(PortletRequest portletRequest);

	public String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException;

	public String getHost(HttpServletRequest request);

	public String getHost(PortletRequest portletRequest);

	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest);

	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse);

	public String getJsSafePortletId(String portletId) ;

	public String getLayoutActualURL(Layout layout);

	public String getLayoutActualURL(Layout layout, String mainPath);

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException;

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException;

	public String getLayoutEditPage(Layout layout);

	public String getLayoutEditPage(String type);

	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException, SystemException;

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException;

	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException;

	public String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutTarget(Layout layout);

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException;

	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutViewPage(Layout layout);

	public String getLayoutViewPage(String type);

	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse);

	public Locale getLocale(HttpServletRequest request);

	public Locale getLocale(RenderRequest renderRequest);

	public String getMailId(String mx, String popPortletPrefix, Object... ids);

	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request);

	public String getOuterPortletId(HttpServletRequest request);

	public long getParentGroupId(long scopeGroupId)
		throws SystemException, PortalException;

	public String getPathContext();

	public String getPathFriendlyURLPrivateGroup();

	public String getPathFriendlyURLPrivateUser();

	public String getPathFriendlyURLPublic();

	public String getPathImage();

	public String getPathMain();

	public String getPathProxy();

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL);

	public long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException;

	public long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException, SystemException;

	public String getPortalLibDir();

	public int getPortalPort();

	public Properties getPortalProperties();

	public String getPortalURL(HttpServletRequest request);

	public String getPortalURL(HttpServletRequest request, boolean secure);

	public String getPortalURL(PortletRequest portletRequest);

	public String getPortalURL(PortletRequest portletRequest, boolean secure);

	public String getPortalURL(
		String serverName, int serverPort, boolean secure);

	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getPortalWebDir();

	public Set<String> getPortletAddDefaultResourceCheckWhitelist();

	/**
	 * @deprecated {@link #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	public List<KeyValuePair> getPortletBreadcrumbList(
		HttpServletRequest request);

	public List<KeyValuePair> getPortletBreadcrumbs(HttpServletRequest request);

	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletDescription(Portlet portlet, User user);

	public String getPortletDescription(String portletId, Locale locale);

	public String getPortletDescription(String portletId, String languageId);

	public String getPortletDescription(String portletId, User user);

	public String getPortletId(HttpServletRequest request);

	public String getPortletId(PortletRequest portletRequest);

	public String getPortletNamespace(String portletId);

	public String getPortletTitle(Portlet portlet, Locale locale);

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletTitle(Portlet portlet, String languageId);

	public String getPortletTitle(Portlet portlet, User user);

	public String getPortletTitle(RenderResponse renderResponse);

	public String getPortletTitle(String portletId, Locale locale);

	public String getPortletTitle(String portletId, String languageId);

	public String getPortletTitle(String portletId, User user);

	public String getPortletXmlFileName() throws SystemException;

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(
		Portlet portlet);

	public long getScopeGroupId(HttpServletRequest request)
		throws PortalException, SystemException;

	public long getScopeGroupId(HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public long getScopeGroupId(Layout layout);

	public long getScopeGroupId(Layout layout, String portletId);

	public long getScopeGroupId(long plid);

	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException;

	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException;

	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException;

	public ServletContext getServletContext(
		Portlet portlet, ServletContext servletContext);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString);

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp);

	public String getStrutsAction(HttpServletRequest request);

	public String[] getSystemCommunityRoles();

	public String[] getSystemGroups();

	public String[] getSystemOrganizationRoles();

	public String[] getSystemRoles();

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request);

	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest);

	public Date getUptime();

	public String getURLWithSessionId(String url, String sessionId);

	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException;

	public User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public String getUserEmailAddress(long userId) throws SystemException;

	public long getUserId(HttpServletRequest request);

	public long getUserId(PortletRequest portletRequest);

	public String getUserName(long userId, String defaultUserName);

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request);

	public String getUserPassword(HttpServletRequest request);

	public String getUserPassword(HttpSession session);

	public String getUserPassword(PortletRequest portletRequest);

	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException;

	public long getValidUserId(long companyId, long userId)
		throws PortalException, SystemException;

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException;

	public boolean isCommunityAdmin(User user, long groupId) throws Exception;

	public boolean isCommunityOwner(User user, long groupId) throws Exception;

	public boolean isCompanyAdmin(User user) throws Exception;

	public boolean isControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException;

	public boolean isControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException;

	public boolean isLayoutFirstPageable(Layout layout);

	public boolean isLayoutFirstPageable(String type);

	public boolean isLayoutFriendliable(Layout layout);

	public boolean isLayoutFriendliable(String type);

	public boolean isLayoutParentable(Layout layout);

	public boolean isLayoutParentable(String type);

	public boolean isLayoutSitemapable(Layout layout);

	public boolean isMethodGet(PortletRequest portletRequest);

	public boolean isMethodPost(PortletRequest portletRequest);

	public boolean isOmniadmin(long userId);

	public boolean isReservedParameter(String name);

	public boolean isSystemGroup(String groupName);

	public boolean isSystemRole(String roleName);

	public boolean isUpdateAvailable() throws SystemException;

	public boolean isValidResourceId(String resourceId);

	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener);

	public String renderPage(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path)
		throws IOException, ServletException;

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			boolean writeOutput)
		throws IOException, ServletException;

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws IOException, ServletException;

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws IOException, ServletException;

	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException;

	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException;

	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

	/**
	 * Sets the description for a page. This overrides the existing page
	 * description.
	 */
	public void setPageDescription(
		String description, HttpServletRequest request);

	/**
	 * Sets the keywords for a page. This overrides the existing page keywords.
	 */
	public void setPageKeywords(String keywords, HttpServletRequest request);

	/**
	 * Sets the subtitle for a page. This overrides the existing page subtitle.
	 */
	public void setPageSubtitle(String subtitle, HttpServletRequest request);

	/**
	 * Sets the whole title for a page. This overrides the existing page whole
	 * title.
	 */
	public void setPageTitle(String title, HttpServletRequest request);

	/**
	 * Sets the port obtained on the first request to the portal.
	 */
	public void setPortalPort(HttpServletRequest request);

	public void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException;

	public String transformCustomSQL(String sql);

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request);

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request);

}