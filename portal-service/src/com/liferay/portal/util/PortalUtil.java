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
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
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
 * <a href="PortalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalUtil {

	public static void clearRequestParameters(RenderRequest req) {
		getPortal().clearRequestParameters(req);
	}

	public static void copyRequestParameters(
		ActionRequest req, ActionResponse res) {

		getPortal().copyRequestParameters(req, res);
	}

	public static String getCDNHost() {
		return getPortal().getCDNHost();
	}

	public static String getClassName(long classNameId) {
		return getPortal().getClassName(classNameId);
	}

	public static long getClassNameId(Class<?> classObj) {
		return getPortal().getClassNameId(classObj);
	}

	public static long getClassNameId(String value) {
		return getPortal().getClassNameId(value);
	}

	public static String getClassNamePortletId(String className) {
		return getPortal().getClassNamePortletId(className);
	}

	public static String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getCommunityLoginURL(themeDisplay);
	}

	public static Company getCompany(HttpServletRequest req)
		throws PortalException, SystemException {

		return getPortal().getCompany(req);
	}

	public static Company getCompany(ActionRequest req)
		throws PortalException, SystemException {

		return getPortal().getCompany(req);
	}

	public static Company getCompany(RenderRequest req)
		throws PortalException, SystemException {

		return getPortal().getCompany(req);
	}

	public static long getCompanyId(HttpServletRequest req) {
		return getPortal().getCompanyId(req);
	}

	public static long getCompanyId(ActionRequest req) {
		return getPortal().getCompanyId(req);
	}

	public static long getCompanyId(PortletRequest req) {
		return getPortal().getCompanyId(req);
	}

	public static long getCompanyId(RenderRequest req) {
		return getPortal().getCompanyId(req);
	}

	public static long getCompanyIdByWebId(ServletContext ctx) {
		return getPortal().getCompanyIdByWebId(ctx);
	}

	public static long getCompanyIdByWebId(String webId) {
		return getPortal().getCompanyIdByWebId(webId);
	}

	public static String getComputerAddress() {
		return getPortal().getComputerAddress();
	}

	public static String getComputerName() {
		return getPortal().getComputerName();
	}

	public static String getCurrentURL(HttpServletRequest req) {
		return getPortal().getCurrentURL(req);
	}

	public static String getCurrentURL(PortletRequest req) {
		return getPortal().getCurrentURL(req);
	}

	public static Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, timeZone, pe);
	}

	public static String getHost(HttpServletRequest req) {
		return getPortal().getHost(req);
	}

	public static String getHost(ActionRequest req) {
		return getPortal().getHost(req);
	}

	public static String getHost(RenderRequest req) {
		return getPortal().getHost(req);
	}

	public static HttpServletRequest getHttpServletRequest(PortletRequest req) {
		return getPortal().getHttpServletRequest(req);
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse res) {

		return getPortal().getHttpServletResponse(res);
	}

	public static String getLayoutEditPage(Layout layout) {
		return getPortal().getLayoutEditPage(layout);
	}

	public static String getLayoutViewPage(Layout layout) {
		return getPortal().getLayoutViewPage(layout);
	}

	public static String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(themeDisplay);
	}

	public static String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(layout, themeDisplay);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		return getPortal().getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutActualURL(Layout layout)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(layout);
	}

	public static String getLayoutActualURL(Layout layout, String mainPath)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(layout, mainPath);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params);
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public static String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getLayoutTarget(Layout layout) {
		return getPortal().getLayoutTarget(layout);
	}

	public static String getJsSafePortletId(String portletId)  {
		return getPortal().getJsSafePortletId(portletId);
	}

	public static Locale getLocale(HttpServletRequest req) {
		return getPortal().getLocale(req);
	}

	public static Locale getLocale(RenderRequest req) {
		return getPortal().getLocale(req);
	}

	public static HttpServletRequest getOriginalServletRequest(
		HttpServletRequest req) {

		return getPortal().getOriginalServletRequest(req);
	}

	public static String getPathContext() {
		return getPortal().getPathContext();
	}

	public static String getPathFriendlyURLPrivateGroup() {
		return getPortal().getPathFriendlyURLPrivateGroup();
	}

	public static String getPathFriendlyURLPrivateUser() {
		return getPortal().getPathFriendlyURLPrivateUser();
	}

	public static String getPathFriendlyURLPublic() {
		return getPortal().getPathFriendlyURLPublic();
	}

	public static String getPathImage() {
		return getPortal().getPathImage();
	}

	public static String getPathMain() {
		return getPortal().getPathMain();
	}

	public static long getPlidFromFriendlyURL(
		long companyId, String friendlyURL) {

		return getPortal().getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	public static long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		return getPortal().getPlidFromPortletId(
			groupId, privateLayout, portletId);
	}

	public static Portal getPortal() {
		return _getUtil()._portal;
	}

	public static String getPortalLibDir() {
		return getPortal().getPortalLibDir();
	}

	public static String getPortalURL(ThemeDisplay themeDisplay) {
		return getPortal().getPortalURL(themeDisplay);
	}

	public static String getPortalURL(HttpServletRequest req) {
		return getPortal().getPortalURL(req);
	}

	public static String getPortalURL(HttpServletRequest req, boolean secure) {
		return getPortal().getPortalURL(req, secure);
	}

	public static String getPortalURL(PortletRequest req) {
		return getPortal().getPortalURL(req);
	}

	public static String getPortalURL(PortletRequest req, boolean secure) {
		return getPortal().getPortalURL(req, secure);
	}

	public static String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		return getPortal().getPortalURL(serverName, serverPort, secure);
	}

	public static int getPortalPort() {
		return getPortal().getPortalPort();
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException {

		return getPortal().getPortletFriendlyURLMapper(
			groupId, privateLayout, url);
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException {

		return getPortal().getPortletFriendlyURLMapper(
			groupId, privateLayout, url, params);
	}

	public static long getPortletGroupId(long plid) {
		return getPortal().getPortletGroupId(plid);
	}

	public static long getPortletGroupId(Layout layout) {
		return getPortal().getPortletGroupId(layout);
	}

	public static long getPortletGroupId(HttpServletRequest req) {
		return getPortal().getPortletGroupId(req);
	}

	public static long getPortletGroupId(ActionRequest req) {
		return getPortal().getPortletGroupId(req);
	}

	public static long getPortletGroupId(RenderRequest req) {
		return getPortal().getPortletGroupId(req);
	}

	public static String getPortletId(HttpServletRequest req) {
		return getPortal().getPortletId(req);
	}

	public static String getPortletId(ActionRequest req) {
		return getPortal().getPortletId(req);
	}

	public static String getPortletId(RenderRequest req) {
		return getPortal().getPortletId(req);
	}

	public static String getPortletNamespace(String portletId) {
		return getPortal().getPortletNamespace(portletId);
	}

	public static String getPortletTitle(
		String portletId, long companyId, String languageId) {

		return getPortal().getPortletTitle(portletId, companyId, languageId);
	}

	public static String getPortletTitle(
		String portletId, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portletId, companyId, locale);
	}

	public static String getPortletTitle(String portletId, User user) {
		return getPortal().getPortletTitle(portletId, user);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, String languageId) {

		return getPortletTitle(portlet, companyId, languageId);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portlet, companyId, locale);
	}

	public static String getPortletTitle(Portlet portlet, User user) {
		return getPortal().getPortletTitle(portlet, user);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext ctx, Locale locale) {

		return getPortal().getPortletTitle(portlet, ctx, locale);
	}

	public static String getPortletXmlFileName()
		throws PortalException, SystemException {

		return getPortal().getPortletXmlFileName();
	}

	public static PortletPreferences getPreferences(HttpServletRequest req) {
		return getPortal().getPreferences(req);
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		return getPortal().getPreferencesValidator(portlet);
	}

	public static User getSelectedUser(HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req);
	}

	public static User getSelectedUser(
			HttpServletRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req, checkPermission);
	}

	public static User getSelectedUser(ActionRequest req)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req);
	}

	public static User getSelectedUser(
			ActionRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req, checkPermission);
	}

	public static User getSelectedUser(RenderRequest req)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req);
	}

	public static User getSelectedUser(
			RenderRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(req, checkPermission);
	}

	public static String getStrutsAction(HttpServletRequest req) {
		return getPortal().getStrutsAction(req);
	}

	public static String[] getSystemCommunityRoles() {
		return getPortal().getSystemCommunityRoles();
	}

	public static String[] getSystemGroups() {
		return getPortal().getSystemGroups();
	}

	public static String[] getSystemOrganizationRoles() {
		return getPortal().getSystemOrganizationRoles();
	}

	public static String[] getSystemRoles() {
		return getPortal().getSystemRoles();
	}

	public static Date getUptime() {
		return getPortal().getUptime();
	}

	public static String getURLWithSessionId(String url, String sessionId) {
		return getPortal().getURLWithSessionId(url, sessionId);
	}

	public static User getUser(HttpServletRequest req)
		throws PortalException, SystemException {

		return getPortal().getUser(req);
	}

	public static User getUser(ActionRequest req)
		throws PortalException, SystemException {

		return getPortal().getUser(req);
	}

	public static User getUser(RenderRequest req)
		throws PortalException, SystemException {

		return getPortal().getUser(req);
	}

	public static long getUserId(HttpServletRequest req) {
		return getPortal().getUserId(req);
	}

	public static long getUserId(ActionRequest req) {
		return getPortal().getUserId(req);
	}

	public static long getUserId(RenderRequest req) {
		return getPortal().getUserId(req);
	}

	public static String getUserName(long userId, String defaultUserName) {
		return getPortal().getUserName(userId, defaultUserName);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getPortal().getUserName(userId, defaultUserName, userAttribute);
	}

	public static String getUserName(
		long userId, String defaultUserName, HttpServletRequest req) {

		return getPortal().getUserName(userId, defaultUserName, req);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest req) {

		return getPortal().getUserName(
			userId, defaultUserName, userAttribute, req);
	}

	public static String getUserPassword(HttpSession ses) {
		return getPortal().getUserPassword(ses);
	}

	public static String getUserPassword(HttpServletRequest req) {
		return getPortal().getUserPassword(req);
	}

	public static String getUserPassword(ActionRequest req) {
		return getPortal().getUserPassword(req);
	}

	public static String getUserPassword(RenderRequest req) {
		return getPortal().getUserPassword(req);
	}

	public static String getUserValue(
			long userId, String param, String defaultValue)
		throws SystemException {

		return getPortal().getUserValue(userId, param, defaultValue);
	}

	public static boolean isMethodGet(PortletRequest req) {
		return getPortal().isMethodGet(req);
	}

	public static boolean isMethodPost(PortletRequest req) {
		return getPortal().isMethodPost(req);
	}

	public static boolean isLayoutFriendliable(Layout layout) {
		return getPortal().isLayoutFriendliable(layout);
	}

	public static boolean isLayoutParentable(Layout layout) {
		return getPortal().isLayoutParentable(layout);
	}

	public static boolean isLayoutParentable(String type) {
		return getPortal().isLayoutParentable(type);
	}

	public static boolean isLayoutSitemapable(Layout layout) {
		return getPortal().isLayoutSitemapable(layout);
	}

	public static boolean isReservedParameter(String name) {
		return getPortal().isReservedParameter(name);
	}

	public static boolean isSystemGroup(String groupName) {
		return getPortal().isSystemGroup(groupName);
	}

	public static boolean isSystemRole(String roleName) {
		return getPortal().isSystemRole(roleName);
	}

	public static boolean isUpdateAvailable()
		throws PortalException, SystemException {

		return getPortal().isUpdateAvailable();
	}

	public static void renderPage(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, String path)
		throws IOException, ServletException {

		getPortal().renderPage(sm, ctx, req, res, path);
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sm, ctx, req, res, portlet, queryString);
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sm, ctx, req, res, portlet, queryString, columnId, columnPos,
			columnCount);
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sm, ctx, req, res, portlet, queryString, columnId, columnPos,
			columnCount, path);
	}

	public static void sendError(
			int status, Exception e, HttpServletRequest req,
			HttpServletResponse res)
		throws IOException, ServletException {

		getPortal().sendError(status, e, req, res);
	}

	/**
	 * Sets the subtitle for a page. This is just a hint and can be overridden
	 * by subsequent calls. The last call to this method wins.
	 *
	 * @param		subtitle the subtitle for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest req) {

		getPortal().setPageSubtitle(subtitle, req);
	}

	/**
	 * Sets the whole title for a page. This is just a hint and can be
	 * overridden by subsequent calls. The last call to this method wins.
	 *
	 * @param		title the whole title for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageTitle(String title, HttpServletRequest req) {
		getPortal().setPageTitle(title, req);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @param		req the HTTP servlet request
	 */
	public static void setPortalPort(HttpServletRequest req) {
		getPortal().setPortalPort(req);
	}

	public static void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException {

		getPortal().storePreferences(prefs);
	}

	public static String transformCustomSQL(String sql) {
		return getPortal().transformCustomSQL(sql);
	}

	public static PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		return getPortal().updatePortletMode(
			portletId, user, layout, portletMode, req);
	}

	public static WindowState updateWindowState(
			String portletId, User user, Layout layout, WindowState windowState,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		return getPortal().updateWindowState(
			portletId, user, layout, windowState, req);
	}

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	private static PortalUtil _getUtil() {
		if (_util == null) {
			_util = (PortalUtil)BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = PortalUtil.class.getName();

	private static PortalUtil _util;

	private Portal _portal;

}