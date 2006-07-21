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

package com.liferay.portal.events;

import com.liferay.portal.LayoutPermissionException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.Resolution;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.ListUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;
import com.liferay.util.servlet.SessionErrors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="ServicePreAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Felix Ventero
 *
 */
public class ServicePreAction extends Action {

	public void run(HttpServletRequest req, HttpServletResponse res)
		throws ActionException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		try {
			HttpSession ses = req.getSession();

			// Company

			Company company = PortalUtil.getCompany(req);

			String companyId = company.getCompanyId();

			// Paths

			String contextPath = PrefsPropsUtil.getString(
				companyId, PropsUtil.PORTAL_CTX);

			if (contextPath.equals(StringPool.SLASH)) {
				contextPath = StringPool.BLANK;
			}

			String rootPath = (String)req.getAttribute(WebKeys.ROOT_PATH);
			String mainPath = (String)req.getAttribute(WebKeys.MAIN_PATH);
			String friendlyURLPrivatePath =
				(String)req.getAttribute(WebKeys.FRIENDLY_URL_PRIVATE_PATH);
			String friendlyURLPublicPath =
				(String)req.getAttribute(WebKeys.FRIENDLY_URL_PUBLIC_PATH);
			String imagePath = (String)req.getAttribute(WebKeys.IMAGE_PATH);

			// Company logo

			String companyLogo =
				imagePath + "/company_logo?img_id=" + companyId;

			// User

			User user = null;

			try {
				user = PortalUtil.getUser(req);
			}
			catch (NoSuchUserException nsue) {
				return;
			}

			// Is the user signed in?

			boolean signedIn = false;

			if (user == null) {
				user = company.getDefaultUser();
			}
			else {
				signedIn = true;
			}

			// Permission checker

			PermissionChecker permissionChecker =
				PermissionCheckerFactory.create(user, signedIn);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			// Locale

			Locale locale = (Locale)ses.getAttribute(Globals.LOCALE_KEY);

			if (locale == null) {
				if (signedIn) {
					locale = user.getLocale();
				}
				else {

					// User previously set their preferred language

					String languageId = CookieUtil.get(
						req.getCookies(), CookieKeys.GUEST_LANGUAGE_ID);

					if (Validator.isNotNull(languageId)) {
						locale = LocaleUtil.fromLanguageId(languageId);
					}

					// Get locale from the request

					if ((locale == null) &&
						GetterUtil.getBoolean(
							PropsUtil.get(PropsUtil.LOCALE_DEFAULT_REQUEST))) {

						locale = req.getLocale();
					}

					// Get locale from the default user

					if (locale == null) {
						locale = user.getLocale();
					}

					if (Validator.isNull(locale.getCountry())) {

						// Locales must contain the country code

						locale = LanguageUtil.getLocale(locale.getLanguage());
					}

					List availableLocales = ListUtil.fromArray(
						LanguageUtil.getAvailableLocales());

					if (!availableLocales.contains(locale)) {
						locale = user.getLocale();
					}
				}

				ses.setAttribute(Globals.LOCALE_KEY, locale);

				LanguageUtil.updateCookie(res, locale);
			}

			// Cookie support

			CookieKeys.addSupportCookie(res);

			// Time zone

			TimeZone timeZone = user.getTimeZone();

			if (timeZone == null) {
				timeZone = company.getTimeZone();
			}

			// Layouts

			if (signedIn) {
				boolean layoutsRequired = user.isLayoutsRequired();

				if (layoutsRequired) {
					addDefaultLayouts(user);
				}
				else {
					deleteDefaultLayouts(user);
				}
			}

			Layout layout = null;
			List layouts = null;

			String plid = ParamUtil.getString(req, "p_l_id");

			String layoutId = Layout.getLayoutId(plid);
			String ownerId = Layout.getOwnerId(plid);

			if ((layoutId != null) && (ownerId != null)) {
				try {
					layout = LayoutLocalServiceUtil.getLayout(
						layoutId, ownerId);

					if (!isViewableCommunity(user, ownerId)) {
						layout = null;
					}
					else {
						layouts = LayoutLocalServiceUtil.getLayouts(
							ownerId, Layout.DEFAULT_PARENT_LAYOUT_ID);
					}
				}
				catch (NoSuchLayoutException nsle) {
				}
			}

			if (layout == null) {
				Object[] defaultLayout = getDefaultLayout(user, signedIn);

				layout = (Layout)defaultLayout[0];
				layouts = (List)defaultLayout[1];
			}

			Object[] viewableLayouts = getViewableLayouts(
				layout, layouts, permissionChecker, req);

			layout = (Layout)viewableLayouts[0];
			layouts = (List)viewableLayouts[1];

			if (layout != null) {
				plid = layout.getPlid();

				layoutId = layout.getLayoutId();
				ownerId = layout.getOwnerId();
			}

			if ((layout != null) && layout.isShared()) {

				// Updates to shared layouts are not reflected until the next
				// time the user logs in because group layouts are cached in the
				// session

				layout = (Layout)layout.clone();
			}

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout != null) {
				req.setAttribute(WebKeys.LAYOUT, layout);
				req.setAttribute(WebKeys.LAYOUTS, layouts);

				layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
			}

			String portletGroupId = PortalUtil.getPortletGroupId(plid);

			// Theme and color scheme

			Theme theme = null;
			ColorScheme colorScheme = null;

			if (layout != null) {
				theme = layout.getTheme();
				colorScheme = layout.getColorScheme();
			}
			else {
				theme = ThemeLocalUtil.getTheme(
					companyId,
					PrefsPropsUtil.getString(
						companyId, PropsUtil.DEFAULT_THEME_ID));
				colorScheme = ThemeLocalUtil.getColorScheme(
					companyId, theme.getThemeId(),
					PrefsPropsUtil.getString(
						companyId, PropsUtil.DEFAULT_COLOR_SCHEME_ID));
			}

			req.setAttribute(WebKeys.THEME, theme);
			req.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

			// Resolution

			int resolution = Resolution.FULL_RESOLUTION;

			String resolutionKey = user.getResolution();

			if (resolutionKey.equals(Resolution.S1024X768_KEY)) {
				resolution = Resolution.S1024X768_RESOLUTION;
			}
			else if (resolutionKey.equals(Resolution.S800X600_KEY)) {
				resolution = Resolution.S800X600_RESOLUTION;
			}

			// Theme display

			String protocol = Http.getProtocol(req) + "://";

			ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

			themeDisplay.setCompany(company);
			themeDisplay.setCompanyLogo(companyLogo);
			themeDisplay.setUser(user);
			themeDisplay.setLayout(layout);
			themeDisplay.setLayouts(layouts);
			themeDisplay.setPlid(plid);
			themeDisplay.setLayoutTypePortlet(layoutTypePortlet);
			themeDisplay.setPortletGroupId(portletGroupId);
			themeDisplay.setSignedIn(signedIn);
			themeDisplay.setPermissionChecker(permissionChecker);
			themeDisplay.setLocale(locale);
			themeDisplay.setTimeZone(timeZone);
			themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
			themeDisplay.setResolution(resolution);
			themeDisplay.setStatePopUp(LiferayWindowState.isPopUp(req));
			themeDisplay.setPathApplet(contextPath + "/applets");
			themeDisplay.setPathContext(contextPath);
			themeDisplay.setPathFlash(contextPath + "/flash");
			themeDisplay.setPathFriendlyURLPrivate(friendlyURLPrivatePath);
			themeDisplay.setPathFriendlyURLPublic(friendlyURLPublicPath);
			themeDisplay.setPathImage(imagePath);
			themeDisplay.setPathJavaScript(contextPath + "/html/js");
			themeDisplay.setPathMain(mainPath);
			themeDisplay.setPathRoot(rootPath);
			themeDisplay.setPathSound(contextPath + "/html/sound");

			// URLs

			themeDisplay.setShowAddContentIcon(false);
			themeDisplay.setShowHomeIcon(true);
			themeDisplay.setShowMyAccountIcon(signedIn);
			themeDisplay.setShowPageSettingsIcon(false);
			themeDisplay.setShowPortalIcon(true);
			themeDisplay.setShowSignInIcon(!signedIn);
			themeDisplay.setShowSignOutIcon(signedIn);

			themeDisplay.setURLHome(protocol + company.getHomeURL());

			if (layout != null) {
				boolean hasManageLayoutsPermission =
					GroupPermission.contains(
						permissionChecker, portletGroupId,
						ActionKeys.MANAGE_LAYOUTS);

				boolean hasUpdateLayoutPermission =
					LayoutPermission.contains(
						permissionChecker, layout, ActionKeys.UPDATE);

				if (hasManageLayoutsPermission || hasUpdateLayoutPermission) {
					themeDisplay.setShowAddContentIcon(true);

					themeDisplay.setURLAddContent(
						"LayoutConfiguration.toggle('" +
							PortletKeys.LAYOUT_CONFIGURATION + "', '" + plid +
								"', '" + mainPath + "','" +
									themeDisplay.getPathThemeImage() + "');");
				}

				if (hasManageLayoutsPermission) {
					themeDisplay.setShowPageSettingsIcon(true);

					PortletURL pageSettingsURL = new PortletURLImpl(
						req, PortletKeys.LAYOUT_MANAGEMENT, plid, false);

					pageSettingsURL.setWindowState(WindowState.MAXIMIZED);
					pageSettingsURL.setPortletMode(PortletMode.VIEW);

					pageSettingsURL.setParameter(
						"struts_action", "/layout_management/edit_pages");

					if (layout.isPrivateLayout()) {
						pageSettingsURL.setParameter("tabs2", "private");
					}
					else {
						pageSettingsURL.setParameter("tabs2", "public");
					}

					pageSettingsURL.setParameter("groupId", portletGroupId);
					pageSettingsURL.setParameter("selPlid", plid);

					themeDisplay.setURLPageSettings(pageSettingsURL);
				}

				PortletURL myAccountURL = new PortletURLImpl(
					req, PortletKeys.MY_ACCOUNT, plid, false);

				myAccountURL.setWindowState(WindowState.MAXIMIZED);
				myAccountURL.setPortletMode(PortletMode.VIEW);

				myAccountURL.setParameter(
					"struts_action", "/my_account/edit_user");

				themeDisplay.setURLMyAccount(myAccountURL);
			}

			themeDisplay.setURLPortal(protocol + company.getPortalURL());
			themeDisplay.setURLSignIn(mainPath + "/portal/login");
			themeDisplay.setURLSignOut(
				mainPath + "/portal/logout?referer=" + mainPath);

			req.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

			// Fix state

			fixState(req, themeDisplay);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new ActionException(e);
		}

		if (_log.isDebugEnabled()) {
			long end = System.currentTimeMillis();

			_log.debug("Running takes " + (end - start) + " ms");
		}
	}

	protected void addDefaultLayouts(User user)
		throws PortalException, SystemException {

		if (user.hasPrivateLayouts()) {
			return;
		}

		Group userGroup = user.getGroup();

		String name = PropsUtil.get(PropsUtil.DEFAULT_USER_LAYOUT_NAME);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			userGroup.getGroupId(), user.getUserId(), true,
			Layout.DEFAULT_PARENT_LAYOUT_ID, name, Layout.TYPE_PORTLET, false,
			null);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String layoutTemplateId = PropsUtil.get(
			PropsUtil.DEFAULT_USER_LAYOUT_TEMPLATE_ID);

		layoutTypePortlet.setLayoutTemplateId(layoutTemplateId);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsUtil.DEFAULT_USER_LAYOUT_COLUMN + i);

			if (portletIds != null) {
				layoutTypePortlet.setPortletIds(columnId, portletIds);
			}
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getLayoutId(), layout.getOwnerId(),
			layout.getTypeSettings());
	}

	protected void deleteDefaultLayouts(User user)
		throws PortalException, SystemException {

		if (user.hasPrivateLayouts()) {
			Group userGroup = user.getGroup();

			LayoutLocalServiceUtil.deleteLayouts(
				Layout.PRIVATE + userGroup.getGroupId());
		}

		if (user.hasPublicLayouts()) {
			Group userGroup = user.getGroup();

			LayoutLocalServiceUtil.deleteLayouts(
				Layout.PUBLIC + userGroup.getGroupId());
		}
	}

	protected void fixState(HttpServletRequest req, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String requestURI = GetterUtil.getString(req.getRequestURI());

		if (!requestURI.endsWith("/portal/layout")) {
			return;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout == null) ||
			(!layout.getType().equals(Layout.TYPE_PORTLET))) {

			return;
		}

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		// Fix pop up state

		String stateMaxPrevious = layoutTypePortlet.getStateMaxPrevious();

		if (stateMaxPrevious != null && !themeDisplay.isStatePopUp()) {
			layoutTypePortlet.removeStateMaxPrevious();

			if (stateMaxPrevious.equals(StringPool.BLANK)) {
				layoutTypePortlet.setStateMax(StringPool.BLANK);
			}
			else {
				layoutTypePortlet.setStateMax(stateMaxPrevious);
			}

			if (!layout.isShared()) {
				LayoutLocalServiceUtil.updateLayout(
					layout.getLayoutId(), layout.getOwnerId(),
					layout.getTypeSettings());
			}
		}

		// Fix maximized state

		if (layoutTypePortlet.hasStateMax()) {
			String portletId =
				StringUtil.split(layoutTypePortlet.getStateMax())[0];

			boolean removeStateMax = false;

			if (!GetterUtil.getBoolean(PropsUtil.get(PropsUtil.
					LAYOUT_REMEMBER_REQUEST_WINDOW_STATE_MAXIMIZED)) &&
				Validator.isNotNull(portletId)) {

				removeStateMax = true;
			}

			// If a user accesses a maximized portlet that is not a part of the
			// layout, the maximized portlet should be removed the next time the
			// layout is accessed.

			if (!layoutTypePortlet.hasPortletId(portletId)) {
				removeStateMax = true;
			}

			if (removeStateMax) {
				String ppState = ParamUtil.getString(req, "p_p_state");

				if (Validator.isNull(ppState) ||
					ppState.equals(WindowState.NORMAL.toString())) {

					layoutTypePortlet.removeStateMaxPortletId(portletId);

					String typeSettings = layout.getTypeSettings();

					if (!layout.isShared()) {
						LayoutLocalServiceUtil.updateLayout(
							layout.getLayoutId(), layout.getOwnerId(),
							typeSettings);
					}
				}
			}
		}
	}

	protected Object[] getDefaultLayout(User user, boolean signedIn)
		throws PortalException, SystemException {

		Layout layout = null;
		List layouts = null;

		if (signedIn) {

			// Check the user's personal layouts

			Group userGroup = user.getGroup();

			layouts = LayoutLocalServiceUtil.getLayouts(
				Layout.PRIVATE + userGroup.getGroupId(),
				Layout.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() == 0) {
				layouts = LayoutLocalServiceUtil.getLayouts(
					Layout.PUBLIC + userGroup.getGroupId(),
					Layout.DEFAULT_PARENT_LAYOUT_ID);
			}

			if (layouts.size() > 0) {
				layout = (Layout)layouts.get(0);
			}

			// Check the user's communities

			if (layout == null) {
				Map groupParams = new HashMap();

				groupParams.put("usersGroups", user.getUserId());

				List groups = GroupLocalServiceUtil.search(
					user.getCompanyId(), null, null, groupParams,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (int i = 0; i < groups.size(); i++) {
					Group group = (Group)groups.get(i);

					layouts = LayoutLocalServiceUtil.getLayouts(
						Layout.PRIVATE + group.getGroupId(),
						Layout.DEFAULT_PARENT_LAYOUT_ID);

					if (layouts.size() == 0) {
						layouts = LayoutLocalServiceUtil.getLayouts(
							Layout.PUBLIC + group.getGroupId(),
							Layout.DEFAULT_PARENT_LAYOUT_ID);
					}

					if (layouts.size() > 0) {
						layout = (Layout)layouts.get(0);

						break;
					}
				}
			}
		}
		else {

			// Check the guest community

			Group guestGroup = GroupLocalServiceUtil.getGroup(
				user.getActualCompanyId(), Group.GUEST);

			layouts = LayoutLocalServiceUtil.getLayouts(
				Layout.PUBLIC + guestGroup.getGroupId(),
				Layout.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				layout = (Layout)layouts.get(0);
			}
		}

		return new Object[] {layout, layouts};
	}

	protected Object[] getViewableLayouts(
			Layout layout, List layouts, PermissionChecker permissionChecker,
			HttpServletRequest req)
		throws PortalException, SystemException {

		if ((layouts != null) && (layouts.size() > 0)) {
			boolean replaceLayout = true;

			if (LayoutPermission.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				replaceLayout = false;
			}

			List accessibleLayouts = new ArrayList();

			for (int i = 0; i < layouts.size(); i++) {
				Layout curLayout = (Layout)layouts.get(i);

				if (LayoutPermission.contains(
						permissionChecker, curLayout, ActionKeys.VIEW)) {

					if ((accessibleLayouts.size() == 0) && replaceLayout) {
						layout = curLayout;
					}

					accessibleLayouts.add(curLayout);
				}
			}

			if (accessibleLayouts.size() == 0) {
				layouts = null;

				SessionErrors.add(
					req, LayoutPermissionException.class.getName());
			}
			else {
				layouts = accessibleLayouts;
			}
		}

		return new Object[] {layout, layouts};
	}

	protected boolean isViewableCommunity(User user, String ownerId)
		throws PortalException, SystemException {

		// Public layouts are always viewable

		if (!Layout.isPrivateLayout(ownerId)) {
			return true;
		}

		// Users can only see their own private layouts

		String groupId = Layout.getGroupId(ownerId);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			if (group.getClassPK().equals(user.getUserId())) {
				return true;
			}
			else {
				return false;
			}
		}

		// Authenticated users can only see group layouts if they belong to the
		// group

		if (GroupLocalServiceUtil.hasUserGroup(user.getUserId(), groupId)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(ServicePreAction.class);

}