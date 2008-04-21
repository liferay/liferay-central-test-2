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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="Portal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface Portal {

	public static final String PATH_IMAGE = "/image";

	public static final String PATH_MAIN = "/c";

	public static final String PATH_PORTAL_LAYOUT = "/portal/layout";

	public static final String PORTLET_XML_FILE_NAME_STANDARD = "portlet.xml";

	public static final String PORTLET_XML_FILE_NAME_CUSTOM =
		"portlet-custom.xml";

	public static final Date UP_TIME = new Date();

	public void clearRequestParameters(RenderRequest req);

	public void copyRequestParameters(ActionRequest req, ActionResponse res);

	public String getCDNHost();

	public String getClassName(long classNameId);

	public long getClassNameId(Class<?> classObj);

	public long getClassNameId(String value);

	public String getClassNamePortletId(String className);

	public String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public Company getCompany(HttpServletRequest req)
		throws PortalException, SystemException;

	public Company getCompany(ActionRequest req)
		throws PortalException, SystemException;

	public Company getCompany(RenderRequest req)
		throws PortalException, SystemException;

	public long getCompanyId(HttpServletRequest req);

	public long getCompanyId(ActionRequest req);

	public long getCompanyId(PortletRequest req);

	public long getCompanyId(RenderRequest req);

	public long getCompanyIdByWebId(ServletContext ctx);

	public long getCompanyIdByWebId(String webId);

	public String getComputerName();

	public String getCurrentURL(HttpServletRequest req);

	public String getCurrentURL(PortletRequest req);

	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException;

	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException;

	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException;

	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException;

	public String getHost(HttpServletRequest req);

	public String getHost(ActionRequest req);

	public String getHost(RenderRequest req);

	public HttpServletRequest getHttpServletRequest(PortletRequest req);

	public HttpServletResponse getHttpServletResponse(PortletResponse res);

	public String getLayoutEditPage(Layout layout);

	public String getLayoutViewPage(Layout layout);

	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException;

	public String getLayoutActualURL(Layout layout)
		throws PortalException, SystemException;

	public String getLayoutActualURL(Layout layout, String mainPath)
		throws PortalException, SystemException;

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException;

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException;

	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutTarget(Layout layout);

	public String getJsSafePortletId(String portletId) ;

	public Locale getLocale(HttpServletRequest req);

	public Locale getLocale(RenderRequest req);

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest req);

	public String getPathContext();

	public String getPathFriendlyURLPrivateGroup();

	public String getPathFriendlyURLPrivateUser();

	public String getPathFriendlyURLPublic();

	public String getPathImage();

	public String getPathMain();

	public long getPlidIdFromFriendlyURL(long companyId, String friendlyURL);

	public String getPortalLibDir();

	public int getPortalPort();

	public String getPortalURL(ThemeDisplay themeDisplay);

	public String getPortalURL(HttpServletRequest req);

	public String getPortalURL(HttpServletRequest req, boolean secure);

	public String getPortalURL(PortletRequest req);

	public String getPortalURL(PortletRequest req, boolean secure);

	public String getPortalURL(
		String serverName, int serverPort, boolean secure);

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException;

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException;

	public long getPortletGroupId(long plid);

	public long getPortletGroupId(Layout layout);

	public long getPortletGroupId(HttpServletRequest req);

	public long getPortletGroupId(ActionRequest req);

	public long getPortletGroupId(RenderRequest req);

	public String getPortletNamespace(String portletId);

	public String getPortletTitle(
		String portletId, long companyId, String languageId);

	public String getPortletTitle(
		String portletId, long companyId, Locale locale);

	public String getPortletTitle(String portletId, User user);

	public String getPortletTitle(
		Portlet portlet, ServletContext ctx, Locale locale);

	public String getPortletXmlFileName()
		throws PortalException, SystemException;

	public PortletPreferences getPreferences(HttpServletRequest req);

	public PreferencesValidator getPreferencesValidator(
		Portlet portlet);

	public User getSelectedUser(HttpServletRequest req)
		throws PortalException, RemoteException, SystemException;

	public User getSelectedUser(HttpServletRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException;

	public User getSelectedUser(ActionRequest req)
		throws PortalException, RemoteException, SystemException;

	public User getSelectedUser(ActionRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException;

	public User getSelectedUser(RenderRequest req)
		throws PortalException, RemoteException, SystemException;

	public User getSelectedUser(RenderRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException;

	public String getStrutsAction(HttpServletRequest req);

	public String[] getSystemCommunityRoles();

	public String[] getSystemGroups();

	public String[] getSystemOrganizationRoles();

	public String[] getSystemRoles();

	public Date getUptime();

	public String getURLWithSessionId(String url, String sessionId);

	public User getUser(HttpServletRequest req)
		throws PortalException, SystemException;

	public User getUser(ActionRequest req)
		throws PortalException, SystemException;

	public User getUser(RenderRequest req)
		throws PortalException, SystemException;

	public long getUserId(HttpServletRequest req);

	public long getUserId(ActionRequest req);

	public long getUserId(RenderRequest req);

	public String getUserName(long userId, String defaultUserName);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute);

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest req);

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest req);

	public String getUserPassword(HttpSession ses);

	public String getUserPassword(HttpServletRequest req);

	public String getUserPassword(ActionRequest req);

	public String getUserPassword(RenderRequest req);

	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException;

	public boolean isMethodGet(PortletRequest req);

	public boolean isMethodPost(PortletRequest req);

	public boolean isLayoutFriendliable(Layout layout);

	public boolean isLayoutParentable(Layout layout);

	public boolean isLayoutParentable(String type);

	public boolean isLayoutSitemapable(Layout layout);

	public boolean isReservedParameter(String name);

	public boolean isSystemGroup(String groupName);

	public boolean isSystemRole(String roleName);

	public boolean isUpdateAvailable() throws PortalException, SystemException;

	public void renderPage(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, String path)
		throws IOException, ServletException;

	public void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString)
		throws IOException, ServletException;

	public void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount)
		throws IOException, ServletException;

	public void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path)
		throws IOException, ServletException;

	public void sendError(
			int status, Exception e, HttpServletRequest req,
			HttpServletResponse res)
		throws IOException, ServletException;

	/**
	 * Sets the subtitle for a page. This is just a hint and can be overridden
	 * by subsequent calls. The last call to this method wins.
	 *
	 * @param		subtitle the subtitle for a page
	 * @param		req the HTTP servlet request
	 */
	public void setPageSubtitle(String subtitle, HttpServletRequest req);

	/**
	 * Sets the whole title for a page. This is just a hint and can be
	 * overridden by subsequent calls. The last call to this method wins.
	 *
	 * @param		title the whole title for a page
	 * @param		req the HTTP servlet request
	 */
	public void setPageTitle(String title, HttpServletRequest req);

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @param		req the HTTP servlet request
	 */
	public void setPortalPort(HttpServletRequest req);

	public void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException;

	public PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException;

	public WindowState updateWindowState(
			String portletId, User user, Layout layout, WindowState windowState,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException;

}