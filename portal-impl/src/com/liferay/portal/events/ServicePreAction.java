/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;
import com.liferay.portal.service.permission.OrganizationPermission;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.NullSafeProperties;
import com.liferay.util.ParamUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;
import com.liferay.util.servlet.SessionErrors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

/**
 * <a href="ServicePreAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Felix Ventero
 *
 */
public class ServicePreAction extends Action {

	public void run(HttpServletRequest req, HttpServletResponse res)
		throws ActionException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			servicePre(req, res);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ActionException(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Running takes " + stopWatch.getTime() + " ms");
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
			user.getUserId(), userGroup.getGroupId(), true,
			LayoutImpl.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
			StringPool.BLANK, LayoutImpl.TYPE_PORTLET, false, StringPool.BLANK);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String layoutTemplateId = PropsUtil.get(
			PropsUtil.DEFAULT_USER_LAYOUT_TEMPLATE_ID);

		layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsUtil.DEFAULT_USER_LAYOUT_COLUMN + i);

			String[] portletIdsArray = StringUtil.split(portletIds);

			layoutTypePortlet.addPortletIds(
				0, portletIdsArray, columnId, false);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected void deleteDefaultLayouts(User user)
		throws PortalException, SystemException {

		if (user.hasPrivateLayouts()) {
			Group userGroup = user.getGroup();

			LayoutLocalServiceUtil.deleteLayouts(userGroup.getGroupId(), true);
		}

		if (user.hasPublicLayouts()) {
			Group userGroup = user.getGroup();

			LayoutLocalServiceUtil.deleteLayouts(userGroup.getGroupId(), false);
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
			(!layout.getType().equals(LayoutImpl.TYPE_PORTLET))) {

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
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), layout.getTypeSettings());
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
							layout.getGroupId(), layout.isPrivateLayout(),
							layout.getLayoutId(), typeSettings);
					}
				}
			}
		}
	}

	protected Object[] getDefaultLayout(
			HttpServletRequest req, User user, boolean signedIn)
		throws PortalException, SystemException {

		// Check the virtual host

		LayoutSet layoutSet = (LayoutSet)req.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (layoutSet != null) {
			List layouts = LayoutLocalServiceUtil.getLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				Layout layout = (Layout)layouts.get(0);

				return new Object[] {layout, layouts};
			}
		}

		Layout layout = null;
		List layouts = null;

		if (signedIn) {

			// Check the user's personal layouts

			Group userGroup = user.getGroup();

			layouts = LayoutLocalServiceUtil.getLayouts(
				userGroup.getGroupId(), true,
				LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() == 0) {
				layouts = LayoutLocalServiceUtil.getLayouts(
					userGroup.getGroupId(), false,
					LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
			}

			if (layouts.size() > 0) {
				layout = (Layout)layouts.get(0);
			}

			// Check the user's communities

			if (layout == null) {
				LinkedHashMap groupParams = new LinkedHashMap();

				groupParams.put("usersGroups", new Long(user.getUserId()));

				List groups = GroupLocalServiceUtil.search(
					user.getCompanyId(), null, null, groupParams,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (int i = 0; i < groups.size(); i++) {
					Group group = (Group)groups.get(i);

					layouts = LayoutLocalServiceUtil.getLayouts(
						group.getGroupId(), true,
						LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

					if (layouts.size() == 0) {
						layouts = LayoutLocalServiceUtil.getLayouts(
							group.getGroupId(), false,
							LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
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
				user.getCompanyId(), GroupImpl.GUEST);

			layouts = LayoutLocalServiceUtil.getLayouts(
				guestGroup.getGroupId(), false,
				LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

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

				if (!curLayout.isHidden() &&
					LayoutPermission.contains(
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

	protected boolean isViewableCommunity(
			User user, long groupId, boolean privateLayout,
			PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		// Public layouts are always viewable

		if (!privateLayout) {
			return true;
		}

		// User private layouts are only viewable by the user and anyone who can
		// update the user

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (groupUserId == user.getUserId()) {
				return true;
			}
			else {
				User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

				if (UserPermission.contains(
						permissionChecker, groupUserId,
						groupUser.getOrganization().getOrganizationId(),
						groupUser.getLocation().getOrganizationId(),
						ActionKeys.UPDATE)) {

					return true;
				}
				else {
					return false;
				}
			}
		}

		// If the current group is staging, the live group should be checked
		// for membership instead

		if (group.isStagingGroup()) {
			groupId = group.getLiveGroupId();
		}

		// Community or organization layhous are only viewable by users who
		// belong to the community or organization, or by users who can update
		// the community or organization

		if (group.isCommunity()) {
			if (GroupLocalServiceUtil.hasUserGroup(user.getUserId(), groupId)) {
				return true;
			}
			else if (GroupPermission.contains(
						permissionChecker, groupId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getClassPK();

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					user.getUserId(), organizationId)) {

				return true;
			}
			else if (OrganizationPermission.contains(
						permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}
		}

		return false;
	}

	protected void servicePre(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		HttpSession ses = req.getSession();

		// Company

		Company company = PortalUtil.getCompany(req);

		long companyId = company.getCompanyId();

		// Paths

		String contextPath = PortalUtil.getPathContext();
		String friendlyURLPrivateGroupPath =
			PortalUtil.getPathFriendlyURLPrivateGroup();
		String friendlyURLPrivateUserPath =
			PortalUtil.getPathFriendlyURLPrivateUser();
		String friendlyURLPublicPath = PortalUtil.getPathFriendlyURLPublic();
		String imagePath = PortalUtil.getPathImage();
		String mainPath = PortalUtil.getPathMain();

		// Company logo

		String companyLogo =
			imagePath + "/company_logo?img_id=" + company.getLogoId();

		Image companyLogoImage = ImageLocalUtil.getCompanyLogo(
			company.getLogoId());

		int companyLogoHeight = companyLogoImage.getHeight();
		int companyLogoWidth = companyLogoImage.getWidth();

		String realCompanyLogo = companyLogo;
		int realCompanyLogoHeight = companyLogoHeight;
		int realCompanyLogoWidth = companyLogoWidth;

		// User

		User user = PortalUtil.getUser(req);

		boolean signedIn = false;

		if (user == null) {
			user = company.getDefaultUser();
		}
		else if (!user.isDefaultUser()) {
			signedIn = true;
		}

		User realUser = user;

		Long realUserId = (Long)ses.getAttribute(WebKeys.USER_ID);

		if (realUserId != null) {
			if (user.getUserId() != realUserId.longValue()) {
				realUser = UserLocalServiceUtil.getUserById(
					realUserId.longValue());
			}
		}

		String doAsUserId = ParamUtil.getString(req, "doAsUserId");

		// Permission checker

		PermissionCheckerImpl permissionChecker =
			PermissionCheckerFactory.create(user, true);

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

		long plid = ParamUtil.getLong(req, "p_l_id");

		if (plid > 0) {
			try {
				layout = LayoutLocalServiceUtil.getLayout(plid);

				if (!signedIn &&
					GetterUtil.getBoolean(PropsUtil.get(
						PropsUtil.AUTH_FORWARD_BY_REDIRECT))) {

					req.setAttribute(WebKeys.REQUESTED_LAYOUT, layout);
				}

				if (!isViewableCommunity(
						user, layout.getGroupId(), layout.isPrivateLayout(),
						permissionChecker)) {

					layout = null;
				}
				else {
					layouts = LayoutLocalServiceUtil.getLayouts(
						layout.getGroupId(), layout.isPrivateLayout(),
						LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		if (layout == null) {
			Object[] defaultLayout = getDefaultLayout(req, user, signedIn);

			layout = (Layout)defaultLayout[0];
			layouts = (List)defaultLayout[1];

			req.setAttribute(WebKeys.LAYOUT_DEFAULT, Boolean.TRUE);
		}

		Object[] viewableLayouts = getViewableLayouts(
			layout, layouts, permissionChecker, req);

		String layoutSetLogo = null;

		layout = (Layout)viewableLayouts[0];
		layouts = (List)viewableLayouts[1];

		if (layout != null) {
			if (company.isCommunityLogo()) {
				LayoutSet layoutSet = layout.getLayoutSet();

				if (layoutSet.isLogo()) {
					long logoId = layoutSet.getLogoId();

					layoutSetLogo =
						imagePath + "/layout_set_logo?img_id=" + logoId;

					Image layoutSetLogoImage = ImageLocalUtil.getCompanyLogo(
						logoId);

					companyLogo = layoutSetLogo;
					companyLogoHeight = layoutSetLogoImage.getHeight();
					companyLogoWidth = layoutSetLogoImage.getWidth();
				}
			}

			plid = layout.getPlid();
		}

		if ((layout != null) && layout.isShared()) {

			// Updates to shared layouts are not reflected until the next time
			// the user logs in because group layouts are cached in the session

			layout = (Layout)((LayoutImpl)layout).clone();

			LayoutClone layoutClone = LayoutCloneFactory.getInstance();

			if (layoutClone != null) {
				String typeSettings = layoutClone.get(req, layout.getPlid());

				if (typeSettings != null) {
					Properties props = new NullSafeProperties();

					PropertiesUtil.load(props, typeSettings);

					String stateMax = props.getProperty(
						LayoutTypePortletImpl.STATE_MAX);
					String stateMin = props.getProperty(
						LayoutTypePortletImpl.STATE_MIN);

					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					layoutTypePortlet.setStateMax(stateMax);
					layoutTypePortlet.setStateMin(stateMin);
				}
			}
		}

		LayoutTypePortlet layoutTypePortlet = null;

		if (layout != null) {
			req.setAttribute(WebKeys.LAYOUT, layout);
			req.setAttribute(WebKeys.LAYOUTS, layouts);

			layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

			if (layout.isPrivateLayout()) {
				permissionChecker.setCheckGuest(false);
			}
		}

		long portletGroupId = PortalUtil.getPortletGroupId(layout);

		// Theme and color scheme

		Theme theme = null;
		ColorScheme colorScheme = null;

		boolean wapTheme = BrowserSniffer.is_wap_xhtml(req);

		if (layout != null) {
			if (wapTheme) {
				theme = layout.getWapTheme();
				colorScheme = layout.getWapColorScheme();
			}
			else {
				theme = layout.getTheme();
				colorScheme = layout.getColorScheme();
			}
		}
		else {
			String themeId = null;
			String colorSchemeId = null;

			if (wapTheme) {
				themeId = ThemeImpl.getDefaultWapThemeId();
				colorSchemeId = ColorSchemeImpl.getDefaultWapColorSchemeId();
			}
			else {
				themeId = ThemeImpl.getDefaultRegularThemeId();
				colorSchemeId =
					ColorSchemeImpl.getDefaultRegularColorSchemeId();
			}

			theme = ThemeLocalUtil.getTheme(companyId, themeId, wapTheme);
			colorScheme = ThemeLocalUtil.getColorScheme(
				companyId, theme.getThemeId(), colorSchemeId, wapTheme);
		}

		req.setAttribute(WebKeys.THEME, theme);
		req.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		// Theme display

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		themeDisplay.setCompany(company);
		themeDisplay.setCompanyLogo(companyLogo);
		themeDisplay.setCompanyLogoHeight(companyLogoHeight);
		themeDisplay.setCompanyLogoWidth(companyLogoWidth);
		themeDisplay.setRealCompanyLogo(realCompanyLogo);
		themeDisplay.setRealCompanyLogoHeight(realCompanyLogoHeight);
		themeDisplay.setRealCompanyLogoWidth(realCompanyLogoWidth);
		themeDisplay.setUser(user);
		themeDisplay.setRealUser(realUser);
		themeDisplay.setDoAsUserId(doAsUserId);
		themeDisplay.setLayoutSetLogo(layoutSetLogo);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayouts(layouts);
		themeDisplay.setPlid(plid);
		themeDisplay.setLayoutTypePortlet(layoutTypePortlet);
		themeDisplay.setPortletGroupId(portletGroupId);
		themeDisplay.setSignedIn(signedIn);
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setLocale(locale);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setTimeZone(timeZone);
		themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
		themeDisplay.setServerPort(req.getServerPort());
		themeDisplay.setSecure(req.isSecure());
		themeDisplay.setStateExclusive(LiferayWindowState.isExclusive(req));
		themeDisplay.setStateMaximized(LiferayWindowState.isMaximized(req));
		themeDisplay.setStatePopUp(LiferayWindowState.isPopUp(req));
		themeDisplay.setPathApplet(contextPath + "/applets");
		themeDisplay.setPathCms(contextPath + "/cms");
		themeDisplay.setPathContext(contextPath);
		themeDisplay.setPathFlash(contextPath + "/flash");
		themeDisplay.setPathFriendlyURLPrivateGroup(
			friendlyURLPrivateGroupPath);
		themeDisplay.setPathFriendlyURLPrivateUser(friendlyURLPrivateUserPath);
		themeDisplay.setPathFriendlyURLPublic(friendlyURLPublicPath);
		themeDisplay.setPathImage(imagePath);
		themeDisplay.setPathJavaScript(contextPath + "/html/js");
		themeDisplay.setPathMain(mainPath);
		themeDisplay.setPathSound(contextPath + "/html/sound");

		// URLs

		themeDisplay.setShowAddContentIcon(false);
		themeDisplay.setShowHomeIcon(true);
		themeDisplay.setShowMyAccountIcon(signedIn);
		themeDisplay.setShowPageSettingsIcon(false);
		themeDisplay.setShowPortalIcon(true);
		themeDisplay.setShowSignInIcon(!signedIn);
		themeDisplay.setShowSignOutIcon(signedIn);

		PortletURL createAccountURL = new PortletURLImpl(
			req, PortletKeys.MY_ACCOUNT, plid, true);

		createAccountURL.setWindowState(WindowState.MAXIMIZED);
		createAccountURL.setPortletMode(PortletMode.VIEW);

		createAccountURL.setParameter(
			"struts_action", "/my_account/create_account");

		themeDisplay.setURLCreateAccount(createAccountURL);

		themeDisplay.setURLHome(PortalUtil.getPortalURL(req) + contextPath);

		if (layout != null) {
			if (layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				boolean freeformLayout =
					layoutTypePortlet.getLayoutTemplateId().equals(
						"freeform");

				themeDisplay.setFreeformLayout(freeformLayout);

				boolean hasUpdateLayoutPermission =
					LayoutPermission.contains(
						permissionChecker, layout, ActionKeys.UPDATE);

				if (hasUpdateLayoutPermission) {
					if (!LiferayWindowState.isMaximized(req)) {
						themeDisplay.setShowAddContentIcon(true);
					}

					themeDisplay.setShowLayoutTemplatesIcon(true);

					themeDisplay.setURLAddContent(
						"LayoutConfiguration.toggle('" + plid + "', '" +
							PortletKeys.LAYOUT_CONFIGURATION + "', '" +
								doAsUserId + "');");

					themeDisplay.setURLLayoutTemplates(
						"showLayoutTemplates();");
				}
			}

			boolean hasManageLayoutsPermission =
				GroupPermission.contains(
					permissionChecker, portletGroupId,
					ActionKeys.MANAGE_LAYOUTS);

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

				pageSettingsURL.setParameter(
					"groupId", String.valueOf(portletGroupId));
				pageSettingsURL.setParameter("selPlid", String.valueOf(plid));

				themeDisplay.setURLPageSettings(pageSettingsURL);
			}

			PortletURL myAccountURL = new PortletURLImpl(
				req, PortletKeys.MY_ACCOUNT, plid, false);

			myAccountURL.setWindowState(WindowState.MAXIMIZED);
			myAccountURL.setPortletMode(PortletMode.VIEW);

			myAccountURL.setParameter("struts_action", "/my_account/edit_user");

			themeDisplay.setURLMyAccount(myAccountURL);
		}

		boolean termsOfUseRequired = GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.TERMS_OF_USE_REQUIRED), true);

		if (!user.isActive() ||
			(termsOfUseRequired && !user.isAgreedToTermsOfUse())) {

			themeDisplay.setShowAddContentIcon(false);
			themeDisplay.setShowMyAccountIcon(false);
			themeDisplay.setShowPageSettingsIcon(false);
		}

		themeDisplay.setURLPortal(themeDisplay.getURLHome());
		themeDisplay.setURLSignIn(mainPath + "/portal/login");
		themeDisplay.setURLSignOut(mainPath + "/portal/logout");

		PortletURL updateManagerURL = new PortletURLImpl(
			req, PortletKeys.UPDATE_MANAGER, plid, false);

		updateManagerURL.setWindowState(WindowState.MAXIMIZED);
		updateManagerURL.setPortletMode(PortletMode.VIEW);

		updateManagerURL.setParameter("struts_action", "/update_manager/view");

		themeDisplay.setURLUpdateManager(updateManagerURL);

		req.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		// Parallel render

		req.setAttribute(
			WebKeys.PORTLET_PARALLEL_RENDER,
			new Boolean(ParamUtil.getBoolean(req, "p_p_parallel", true)));

		// Fix state

		fixState(req, themeDisplay);
	}

	private static Log _log = LogFactory.getLog(ServicePreAction.class);

}