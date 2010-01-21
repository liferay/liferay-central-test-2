/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
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
import javax.servlet.jsp.PageContext;

/**
 * <a href="Portal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public interface Portal {

	public static final String FRIENDLY_URL_SEPARATOR = "/-/";

	public static final String PATH_IMAGE = "/image";

	public static final String PATH_MAIN = "/c";

	public static final String PATH_PORTAL_LAYOUT = "/portal/layout";

	public static final String PORTLET_XML_FILE_NAME_CUSTOM =
		"portlet-custom.xml";

	public static final String PORTLET_XML_FILE_NAME_STANDARD = "portlet.xml";

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

	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url);

	public void clearRequestParameters(RenderRequest renderRequest);

	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse);

	public String escapeRedirect(String url);

	public BaseModel<?> getBaseModel(Resource resource)
		throws PortalException, SystemException;

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException;

	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException;

	public BasePersistence<?> getBasePersistence(BaseModel<?> baseModel);

	public BasePersistence<?> getBasePersistence(
		String servletContextName, String className);

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

	public List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException;

	public String getCurrentCompleteURL(HttpServletRequest request);

	public String getCurrentURL(HttpServletRequest request);

	public String getCurrentURL(PortletRequest portletRequest);

	public String getCustomSQLFunctionIsNotNull();

	public String getCustomSQLFunctionIsNull();

	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException;

	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException;

	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException;

	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException;

	public long getDefaultCompanyId();

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException;

	public String getFirstPageLayoutTypes(PageContext pageContext);

	public String getGlobalLibDir();

	public String getGoogleGadgetURL(
		Portlet portlet, ThemeDisplay themeDisplay);

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
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException;

	public String getLayoutEditPage(Layout layout);

	public String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay);

	public String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay, Locale locale);

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay);

	public String getLayoutFullURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser);

	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException;

	public String getLayoutFullURL(ThemeDisplay themeDisplay);

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException;

	public String getLayoutTarget(Layout layout);

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay);

	public String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser);

	public String getLayoutURL(ThemeDisplay themeDisplay);

	public String getLayoutViewPage(Layout layout);

	public Locale getLocale(HttpServletRequest request);

	public Locale getLocale(RenderRequest renderRequest);

	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay);

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request);

	public String getPathContext();

	public String getPathFriendlyURLPrivateGroup();

	public String getPathFriendlyURLPrivateUser();

	public String getPathFriendlyURLPublic();

	public String getPathImage();

	public String getPathMain();

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL);

	public long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId);

	public long getPlidFromPortletId(long groupId, String portletId);

	public String getPortalLibDir();

	public int getPortalPort();

	public Properties getPortalProperties();

	public String getPortalURL(HttpServletRequest request);

	public String getPortalURL(HttpServletRequest request, boolean secure);

	public String getPortalURL(PortletRequest portletRequest);

	public String getPortalURL(PortletRequest portletRequest, boolean secure);

	public String getPortalURL(
		String serverName, int serverPort, boolean secure);

	public String getPortalURL(ThemeDisplay themeDisplay);

	public String getPortalWebDir();

	public List<KeyValuePair> getPortletBreadcrumbList(
		HttpServletRequest request);

	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletDescription(Portlet portlet, User user);

	public String getPortletDescription(String portletId, Locale locale);

	public String getPortletDescription(String portletId, String languageId);

	public String getPortletDescription(String portletId, User user);

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException;

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException;

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(ActionRequest actionRequest);

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(HttpServletRequest request);

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(Layout layout);

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(long plid);

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(RenderRequest renderRequest);

	public String getPortletId(HttpServletRequest request);

	public String getPortletId(PortletRequest portletRequest);

	public String getPortletNamespace(String portletId);

	public String getPortletTitle(Portlet portlet, Locale locale);

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale);

	public String getPortletTitle(Portlet portlet, String languageId);

	public String getPortletTitle(Portlet portlet, User user);

	public String getPortletTitle(String portletId, Locale locale);

	public String getPortletTitle(String portletId, String languageId);

	public String getPortletTitle(String portletId, User user);

	public String getPortletXmlFileName() throws SystemException;

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(
		Portlet portlet);

	public long getScopeGroupId(HttpServletRequest request);

	public long getScopeGroupId(HttpServletRequest request, String portletId);

	public long getScopeGroupId(Layout layout);

	public long getScopeGroupId(Layout layout, String portletId);

	public long getScopeGroupId(long plid);

	public long getScopeGroupId(PortletRequest portletRequest);

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

	public String getServletContextName(BaseModel<?> baseModel);

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

	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest);

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request);

	public Date getUptime();

	public String getURLWithSessionId(String url, String sessionId);

	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException;

	public User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException;

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

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay);

	public boolean isLayoutFirstPageable(String type);

	public boolean isLayoutFriendliable(Layout layout);

	public boolean isLayoutParentable(Layout layout);

	public boolean isLayoutParentable(String type);

	public boolean isLayoutSitemapable(Layout layout);

	public boolean isMethodGet(PortletRequest portletRequest);

	public boolean isMethodPost(PortletRequest portletRequest);

	public boolean isReservedParameter(String name);

	public boolean isSystemGroup(String groupName);

	public boolean isSystemRole(String roleName);

	public boolean isUpdateAvailable() throws SystemException;

	public void renderPage(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException, ServletException;

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString)
		throws IOException, ServletException;

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount)
		throws IOException, ServletException;

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount, String path)
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

	public void storePreferences(PortletPreferences preferences)
		throws IOException, ValidatorException;

	public String transformCustomSQL(String sql);

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request);

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request);

}