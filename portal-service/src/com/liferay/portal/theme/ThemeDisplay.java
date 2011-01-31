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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeDisplay implements Serializable {

	public ThemeDisplay() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}

		_portletDisplay.setThemeDisplay(this);
	}

	public Company getCompany() {
		return _company;
	}

	public void setCompany(Company company)
		throws PortalException, SystemException {

		_company = company;
		_companyGroupId = company.getGroup().getGroupId();

		setAccount(company.getAccount());
	}

	public long getCompanyId() {
		return _company.getCompanyId();
	}

	public long getCompanyGroupId() {
		return _companyGroupId;
	}

	public String getCompanyLogo() {
		return _companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		_companyLogo = companyLogo;
	}

	public int getCompanyLogoHeight() {
		return _companyLogoHeight;
	}

	public void setCompanyLogoHeight(int companyLogoHeight) {
		_companyLogoHeight = companyLogoHeight;
	}

	public int getCompanyLogoWidth() {
		return _companyLogoWidth;
	}

	public void setCompanyLogoWidth(int companyLogoWidth) {
		_companyLogoWidth = companyLogoWidth;
	}

	public String getRealCompanyLogo() {
		return _realCompanyLogo;
	}

	public void setRealCompanyLogo(String realCompanyLogo) {
		_realCompanyLogo = realCompanyLogo;
	}

	public int getRealCompanyLogoHeight() {
		return _realCompanyLogoHeight;
	}

	public void setRealCompanyLogoHeight(int realCompanyLogoHeight) {
		_realCompanyLogoHeight = realCompanyLogoHeight;
	}

	public int getRealCompanyLogoWidth() {
		return _realCompanyLogoWidth;
	}

	public void setRealCompanyLogoWidth(int realCompanyLogoWidth) {
		_realCompanyLogoWidth = realCompanyLogoWidth;
	}

	public Account getAccount() {
		return _account;
	}

	public void setAccount(Account account) {
		_account = account;
	}

	public User getDefaultUser() throws PortalException, SystemException {
		if (_defaultUser == null) {
			_defaultUser = _company.getDefaultUser();
		}

		return _defaultUser;
	}

	public long getDefaultUserId() throws PortalException, SystemException {
		return getDefaultUser().getUserId();
	}

	public User getUser() {
		return _user;
	}

	public void setUser(User user) throws PortalException, SystemException {
		_user = user;

		setContact(user.getContact());
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public User getRealUser() {
		return _realUser;
	}

	public void setRealUser(User realUser) {
		_realUser = realUser;
	}

	public long getRealUserId() {
		return _realUser.getUserId();
	}

	public String getDoAsUserId() {
		return _doAsUserId;
	}

	public void setDoAsUserId(String doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public String getDoAsUserLanguageId() {
		return _doAsUserLanguageId;
	}

	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		_doAsUserLanguageId = doAsUserLanguageId;
	}

	public long getDoAsGroupId() {
		return _doAsGroupId;
	}

	public void setDoAsGroupId(long doAsGroupId) {
		_doAsGroupId = doAsGroupId;
	}

	public long getRefererPlid() {
		return _refererPlid;
	}

	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;
	}

	public boolean isImpersonated() {
		if (getUserId() == getRealUserId()) {
			return false;
		}
		else {
			return true;
		}
	}

	public Contact getContact() {
		return _contact;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public String getLayoutSetLogo() {
		return _layoutSetLogo;
	}

	public void setLayoutSetLogo(String layoutSetLogo) {
		_layoutSetLogo = layoutSetLogo;
	}

	public Layout getLayout() {
		return _layout;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	public Layout getScopeLayout() throws PortalException, SystemException {
		if (_layout.hasScopeGroup()) {
			return _layout;
		}
		else if (_scopeGroup.isLayout()) {
			return LayoutLocalServiceUtil.getLayout(_scopeGroup.getClassPK());
		}
		else {
			return null;
		}
	}

	public List<Layout> getLayouts() {
		return _layouts;
	}

	public void setLayouts(List<Layout> layouts) {
		_layouts = layouts;
	}

	public List<Layout> getUnfilteredLayouts() {
		return _unfilteredLayouts;
	}

	public void setUnfilteredLayouts(List<Layout> unfilteredLayouts) {
		_unfilteredLayouts = unfilteredLayouts;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public LayoutTypePortlet getLayoutTypePortlet() {
		return _layoutTypePortlet;
	}

	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet) {
		_layoutTypePortlet = layoutTypePortlet;
	}

	public Group getScopeGroup() {
		return _scopeGroup;
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId() {
		return getScopeGroupId();
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;

		if (_scopeGroupId > 0) {
			try {
				_scopeGroup = GroupLocalServiceUtil.getGroup(_scopeGroupId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public String getScopeGroupName() throws PortalException, SystemException {
		if (_scopeGroup == null) {
			return StringPool.BLANK;
		}
		else {
			return _scopeGroup.getDescriptiveName();
		}
	}

	public long getParentGroupId() {
		return _parentGroupId;
	}

	public void setParentGroupId(long parentGroupId) {
		_parentGroupId = parentGroupId;

		if (_parentGroupId > 0) {
			try {
				_parentGroup = GroupLocalServiceUtil.getGroup(_parentGroupId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public String getParentGroupName() throws PortalException, SystemException {
		if (_parentGroup == null) {
			return StringPool.BLANK;
		}
		else {
			return _parentGroup.getDescriptiveName();
		}
	}

	public boolean isSignedIn() {
		return _signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	public PermissionChecker getPermissionChecker() {
		return _permissionChecker;
	}

	public void setPermissionChecker(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public boolean isI18n() {
		return _i18n;
	}

	public String getI18nLanguageId() {
		return _i18nLanguageId;
	}

	public void setI18nLanguageId(String i18nLanguageId) {
		_i18nLanguageId = i18nLanguageId;

		if (Validator.isNotNull(i18nLanguageId)) {
			_i18n = true;
		}
		else {
			_i18n = false;
		}
	}

	public String getI18nPath() {
		return _i18nPath;
	}

	public void setI18nPath(String i18nPath) {
		_i18nPath = i18nPath;

		if (Validator.isNotNull(i18nPath)) {
			_i18n = true;
		}
		else {
			_i18n = false;
		}
	}

	public String translate(String key) {
		return LanguageUtil.get(getLocale(), key);
	}

	public String translate(String pattern, Object argument) {
		return LanguageUtil.format(getLocale(), pattern, argument);
	}

	public String translate(String pattern, Object[] arguments) {
		return LanguageUtil.format(getLocale(), pattern, arguments);
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	public Theme getTheme() {
		return _theme;
	}

	public String getThemeId() {
		return _theme.getThemeId();
	}

	public ColorScheme getColorScheme() {
		return _colorScheme;
	}

	public String getColorSchemeId() {
		return _colorScheme.getColorSchemeId();
	}

	public boolean isWapTheme() {
		return _theme.isWapTheme();
	}

	public void setLookAndFeel(Theme theme, ColorScheme colorScheme) {
		setLookAndFeel(getPathContext(), theme, colorScheme);
	}

	public void setLookAndFeel(
		String contextPath, Theme theme, ColorScheme colorScheme) {

		_theme = theme;
		_colorScheme = colorScheme;

		if ((theme != null) && (colorScheme != null)) {
			String themeStaticResourcePath = theme.getStaticResourcePath();

			String host = getCDNHost();

			if (Validator.isNull(host) && isFacebook()) {
				host = getPortalURL();
			}

			setPathColorSchemeImages(
				host + themeStaticResourcePath +
					colorScheme.getColorSchemeImagesPath());

			setPathThemeCss(
				host + themeStaticResourcePath + theme.getCssPath());
			setPathThemeImages(
				host + themeStaticResourcePath + theme.getImagesPath());
			setPathThemeJavaScript(
				host + themeStaticResourcePath + theme.getJavaScriptPath());
			setPathThemeRoot(themeStaticResourcePath + theme.getRootPath());
			setPathThemeTemplates(
				host + themeStaticResourcePath + theme.getTemplatesPath());
		}
	}

	public boolean isThemeCssFastLoad() {
		return _themeCssFastLoad;
	}

	public void setThemeCssFastLoad(boolean themeCssFastLoad) {
		_themeCssFastLoad = themeCssFastLoad;
	}

	public boolean isThemeImagesFastLoad() {
		return _themeImagesFastLoad;
	}

	public void setThemeImagesFastLoad(boolean themeImagesFastLoad) {
		_themeImagesFastLoad = themeImagesFastLoad;
	}

	public boolean isThemeJsBarebone() {
		return _themeJsBarebone;
	}

	public void setThemeJsBarebone(boolean themeJsBarebone) {
		_themeJsBarebone = themeJsBarebone;
	}

	public boolean isThemeJsFastLoad() {
		return _themeJsFastLoad;
	}

	public void setThemeJsFastLoad(boolean themeJsFastLoad) {
		_themeJsFastLoad = themeJsFastLoad;
	}

	public boolean isFreeformLayout() {
		return _freeformLayout;
	}

	public void setFreeformLayout(boolean freeformLayout) {
		_freeformLayout = freeformLayout;
	}

	public String getServerName() {
		return _serverName;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public boolean isSecure() {
		return _secure;
	}

	public void setSecure(boolean secure) {
		_secure = secure;
	}

	public String getLifecycle() {
		return _lifecycle;
	}

	public void setLifecycle(String lifecycle) {
		_lifecycle = lifecycle;
	}

	public boolean isLifecycleAction() {
		return _lifecycleAction;
	}

	public void setLifecycleAction(boolean lifecycleAction) {
		_lifecycleAction = lifecycleAction;
	}

	public boolean isLifecycleRender() {
		return _lifecycleRender;
	}

	public void setLifecycleRender(boolean lifecycleRender) {
		_lifecycleRender = lifecycleRender;
	}

	public boolean isLifecycleResource() {
		return _lifecycleResource;
	}

	public void setLifecycleResource(boolean lifecycleResource) {
		_lifecycleResource = lifecycleResource;
	}

	public boolean isStateExclusive() {
		return _stateExclusive;
	}

	public void setStateExclusive(boolean stateExclusive) {
		_stateExclusive = stateExclusive;
	}

	public boolean isStateMaximized() {
		return _stateMaximized;
	}

	public void setStateMaximized(boolean stateMaximized) {
		_stateMaximized = stateMaximized;
	}

	public boolean isStatePopUp() {
		return _statePopUp;
	}

	public void setStatePopUp(boolean statePopUp) {
		_statePopUp = statePopUp;
	}

	public boolean isIsolated() {
		return _isolated;
	}

	public void setIsolated(boolean isolated) {
		_isolated = isolated;
	}

	public boolean isFacebook() {
		return _facebook;
	}

	public String getFacebookCanvasPageURL() {
		return _facebookCanvasPageURL;
	}

	public void setFacebookCanvasPageURL(String facebookCanvasPageURL) {
		_facebookCanvasPageURL = facebookCanvasPageURL;

		if (Validator.isNotNull(facebookCanvasPageURL)) {
			_facebook = true;
		}
	}

	public boolean isWidget() {
		return _widget;
	}

	public void setWidget(boolean widget) {
		_widget = widget;
	}

	public boolean isAddSessionIdToURL() {
		return _addSessionIdToURL;
	}

	public void setAddSessionIdToURL(boolean addSessionIdToURL) {
		_addSessionIdToURL = addSessionIdToURL;
	}

	public String getCDNHost() {
		return _cdnHost;
	}

	public void setCDNHost(String cdnHost) {
		_cdnHost = cdnHost;
	}

	public String getPortalURL() {
		return _portalURL;
	}

	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	public String getPathApplet() {
		return _pathApplet;
	}

	public void setPathApplet(String pathApplet) {
		_pathApplet = pathApplet;
	}

	public String getPathCms() {
		return _pathCms;
	}

	public void setPathCms(String pathCms) {
		_pathCms = pathCms;
	}

	public String getPathColorSchemeImages() {
		return _pathColorSchemeImages;
	}

	public void setPathColorSchemeImages(String pathColorSchemeImages) {
		_pathColorSchemeImages = pathColorSchemeImages;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public void setPathContext(String pathContext) {
		_pathContext = pathContext;
	}

	public String getPathFlash() {
		return _pathFlash;
	}

	public void setPathFlash(String pathFlash) {
		_pathFlash = pathFlash;
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public void setPathFriendlyURLPrivateGroup(
		String pathFriendlyURLPrivateGroup) {

		_pathFriendlyURLPrivateGroup = pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public void setPathFriendlyURLPrivateUser(
		String pathFriendlyURLPrivateUser) {

		_pathFriendlyURLPrivateUser = pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	public void setPathFriendlyURLPublic(String pathFriendlyURLPublic) {
		_pathFriendlyURLPublic = pathFriendlyURLPublic;
	}

	public String getPathImage() {
		return _pathImage;
	}

	public void setPathImage(String pathImage) {
		if (isFacebook() &&
			!pathImage.startsWith(Http.HTTP_WITH_SLASH) &&
			!pathImage.startsWith(Http.HTTPS_WITH_SLASH)) {

			pathImage = getPortalURL() + pathImage;
		}

		_pathImage = pathImage;
	}

	public String getPathJavaScript() {
		return _pathJavaScript;
	}

	public void setPathJavaScript(String pathJavaScript) {
		_pathJavaScript = pathJavaScript;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public void setPathMain(String pathMain) {
		_pathMain = pathMain;
	}

	public String getPathSound() {
		return _pathSound;
	}

	public void setPathSound(String pathSound) {
		_pathSound = pathSound;
	}

	public String getPathThemeCss() {
		return _pathThemeCss;
	}

	public void setPathThemeCss(String pathThemeCss) {
		_pathThemeCss = pathThemeCss;
	}

	/**
	 * @deprecated Use <code>getPathThemeImages</code>.
	 */
	public String getPathThemeImage() {
		return getPathThemeImages();
	}

	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	public void setPathThemeImages(String pathThemeImages) {
		_pathThemeImages = pathThemeImages;
	}

	public String getPathThemeJavaScript() {
		return _pathThemeJavaScript;
	}

	public void setPathThemeJavaScript(String pathThemeJavaScript) {
		_pathThemeJavaScript = pathThemeJavaScript;
	}

	public String getPathThemeRoot() {
		return _pathThemeRoot;
	}

	public void setPathThemeRoot(String pathThemeRoot) {
		_pathThemeRoot = pathThemeRoot;
	}

	public String getPathThemeTemplates() {
		return _pathThemeTemplates;
	}

	public void setPathThemeTemplates(String pathThemeTemplates) {
		_pathThemeTemplates = pathThemeTemplates;
	}

	public boolean isShowAddContentIcon() {
		return _showAddContentIcon;
	}

	public void setShowAddContentIcon(boolean showAddContentIcon) {
		_showAddContentIcon = showAddContentIcon;
	}

	public boolean isShowAddContentIconPermission() {
		return _showAddContentIconPermission;
	}

	public void setShowAddContentIconPermission(
		boolean showAddContentIconPermission) {

		_showAddContentIconPermission = showAddContentIconPermission;
	}

	public boolean isShowControlPanelIcon() {
		return _showControlPanelIcon;
	}

	public void setShowControlPanelIcon(boolean showControlPanelIcon) {
		_showControlPanelIcon = showControlPanelIcon;
	}

	public boolean isShowHomeIcon() {
		return _showHomeIcon;
	}

	public void setShowHomeIcon(boolean showHomeIcon) {
		_showHomeIcon = showHomeIcon;
	}

	public boolean isShowLayoutTemplatesIcon() {
		return _showLayoutTemplatesIcon;
	}

	public void setShowLayoutTemplatesIcon(boolean showLayoutTemplatesIcon) {
		_showLayoutTemplatesIcon = showLayoutTemplatesIcon;
	}

	public boolean isShowMyAccountIcon() {
		return _showMyAccountIcon;
	}

	public void setShowMyAccountIcon(boolean showMyAccountIcon) {
		_showMyAccountIcon = showMyAccountIcon;
	}

	public boolean isShowPageSettingsIcon() {
		return _showPageSettingsIcon;
	}

	public void setShowPageSettingsIcon(boolean showPageSettingsIcon) {
		_showPageSettingsIcon = showPageSettingsIcon;
	}

	public boolean isShowPortalIcon() {
		return _showPortalIcon;
	}

	public void setShowPortalIcon(boolean showPortalIcon) {
		_showPortalIcon = showPortalIcon;
	}

	public boolean isShowSignInIcon() {
		return _showSignInIcon;
	}

	public void setShowSignInIcon(boolean showSignInIcon) {
		_showSignInIcon = showSignInIcon;
	}

	public boolean isShowSignOutIcon() {
		return _showSignOutIcon;
	}

	public void setShowSignOutIcon(boolean showSignOutIcon) {
		_showSignOutIcon = showSignOutIcon;
	}

	public boolean isShowStagingIcon() {
		return _showStagingIcon;
	}

	public void setShowStagingIcon(boolean showStagingIcon) {
		_showStagingIcon = showStagingIcon;
	}

	public String getURLAddContent() {
		return _urlAddContent;
	}

	public void setURLAddContent(String urlAddContent) {
		_urlAddContent = urlAddContent;
	}

	public String getURLControlPanel() {
		return _urlControlPanel;
	}

	public void setURLControlPanel(String urlControlPanel) {
		_urlControlPanel = urlControlPanel;
	}

	public PortletURL getURLCreateAccount() {
		return _urlCreateAccount;
	}

	public void setURLCreateAccount(PortletURL urlCreateAccount) {
		_urlCreateAccount = urlCreateAccount;
	}

	public String getURLCurrent() {
		return _urlCurrent;
	}

	public void setURLCurrent(String urlCurrent) {
		_urlCurrent = urlCurrent;
	}

	public String getURLHome() {
		return _urlHome;
	}

	public void setURLHome(String urlHome) {
		_urlHome = urlHome;
	}

	public String getURLLayoutTemplates() {
		return _urlLayoutTemplates;
	}

	public void setURLLayoutTemplates(String urlLayoutTemplates) {
		_urlLayoutTemplates = urlLayoutTemplates;
	}

	public PortletURL getURLMyAccount() {
		return _urlMyAccount;
	}

	public void setURLMyAccount(PortletURL urlMyAccount) {
		_urlMyAccount = urlMyAccount;
	}

	public PortletURL getURLPageSettings() {
		return _urlPageSettings;
	}

	public void setURLPageSettings(PortletURL urlPageSettings) {
		_urlPageSettings = urlPageSettings;
	}

	public String getURLPortal() {
		return _urlPortal;
	}

	public void setURLPortal(String urlPortal) {
		_urlPortal = urlPortal;
	}

	public PortletURL getURLPublishToLive() {
		return _urlPublishToLive;
	}

	public void setURLPublishToLive(PortletURL urlPublishToLive) {
		_urlPublishToLive = urlPublishToLive;
	}

	public String getURLSignIn() {
		return _urlSignIn;
	}

	public void setURLSignIn(String urlSignIn) {
		_urlSignIn = urlSignIn;
	}

	public String getURLSignOut() {
		return _urlSignOut;
	}

	public void setURLSignOut(String urlSignOut) {
		_urlSignOut = urlSignOut;
	}

	public PortletURL getURLUpdateManager() {
		return _urlUpdateManager;
	}

	public void setURLUpdateManager(PortletURL urlUpdateManager) {
		_urlUpdateManager = urlUpdateManager;
	}

	public String getSessionId() {
		return _sessionId;
	}

	public void setSessionId(String sessionId) {
		_sessionId = sessionId;
	}

	public String getTilesTitle() {
		return _tilesTitle;
	}

	public void setTilesTitle(String tilesTitle) {
		_tilesTitle = tilesTitle;
	}

	public String getTilesContent() {
		return _tilesContent;
	}

	public void setTilesContent(String tilesContent) {
		_tilesContent = tilesContent;
	}

	public boolean isTilesSelectable() {
		return _tilesSelectable;
	}

	public void setTilesSelectable(boolean tilesSelectable) {
		_tilesSelectable = tilesSelectable;
	}

	public boolean isIncludePortletCssJs() {
		return _includePortletCssJs;
	}

	public void setIncludePortletCssJs(boolean includePortletCssJs) {
		_includePortletCssJs = includePortletCssJs;
	}

	public boolean isIncludeServiceJs() {
		return _includeServiceJs;
	}

	public void setIncludeServiceJs(boolean includeServiceJs) {
		_includeServiceJs = includeServiceJs;
	}

	public boolean isIncludedJs(String js) {
		String path = getPathJavaScript();

		if (isIncludePortletCssJs() &&
			js.startsWith(path + "/liferay/portlet_css.js")) {

			return true;
		}
		else if (isIncludeServiceJs() &&
				 js.startsWith(path + "/liferay/service.js")) {

			return true;
		}
		else {
			return false;
		}
	}

	public PortletDisplay getPortletDisplay() {
		return _portletDisplay;
	}

	/*public void setPortletDisplay(PortletDisplay portletDisplay) {
		_portletDisplay = portletDisplay;
	}*/

	public void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		_company = null;
		_companyGroupId = 0;
		_companyLogo = StringPool.BLANK;
		_companyLogoHeight = 0;
		_companyLogoWidth = 0;
		_realCompanyLogo = StringPool.BLANK;
		_realCompanyLogoHeight = 0;
		_realCompanyLogoWidth = 0;
		_account = null;
		_defaultUser = null;
		_user = null;
		_realUser = null;
		_doAsUserId = StringPool.BLANK;
		_doAsUserLanguageId = StringPool.BLANK;
		_doAsGroupId = 0;
		_refererPlid = 0;
		_layoutSetLogo = StringPool.BLANK;
		_layout = null;
		_layouts = null;
		_unfilteredLayouts = null;
		_plid = 0;
		_layoutTypePortlet = null;
		_scopeGroup = null;
		_scopeGroupId = 0;
		_signedIn = false;
		_permissionChecker = null;
		_locale = null;
		_languageId = null;
		_i18n = false;
		_i18nLanguageId = null;
		_i18nPath = null;
		_timeZone = null;
		_theme = null;
		_colorScheme = null;
		_themeCssFastLoad = false;
		_themeImagesFastLoad = false;
		_themeJsBarebone = false;
		_themeJsFastLoad = false;
		_freeformLayout = false;
		_serverName = StringPool.BLANK;
		_serverPort = 0;
		_secure = false;
		_lifecycle = StringPool.BLANK;
		_lifecycleAction = false;
		_lifecycleRender = false;
		_lifecycleResource = false;
		_stateExclusive = false;
		_stateMaximized = false;
		_statePopUp = false;
		_isolated = false;
		_facebook = false;
		_facebookCanvasPageURL = StringPool.BLANK;
		_widget = false;
		_cdnHost = StringPool.BLANK;
		_portalURL = StringPool.BLANK;
		_pathApplet = StringPool.BLANK;
		_pathCms = StringPool.BLANK;
		_pathColorSchemeImages = StringPool.BLANK;
		_pathContext = StringPool.BLANK;
		_pathFlash = StringPool.BLANK;
		_pathFriendlyURLPrivateGroup = StringPool.BLANK;
		_pathFriendlyURLPrivateUser = StringPool.BLANK;
		_pathFriendlyURLPublic = StringPool.BLANK;
		_pathImage = StringPool.BLANK;
		_pathJavaScript = StringPool.BLANK;
		_pathMain = StringPool.BLANK;
		_pathSound = StringPool.BLANK;
		_pathThemeCss = StringPool.BLANK;
		_pathThemeImages = StringPool.BLANK;
		_pathThemeJavaScript = StringPool.BLANK;
		_pathThemeRoot = StringPool.BLANK;
		_pathThemeTemplates = StringPool.BLANK;
		_showAddContentIcon = false;
		_showAddContentIconPermission = false;
		_showControlPanelIcon = false;
		_showHomeIcon = false;
		_showLayoutTemplatesIcon = false;
		_showMyAccountIcon = false;
		_showPageSettingsIcon = false;
		_showPortalIcon = false;
		_showSignInIcon = false;
		_showSignOutIcon = false;
		_showStagingIcon = false;
		_urlAddContent = StringPool.BLANK;
		_urlControlPanel = StringPool.BLANK;
		_urlCreateAccount = null;
		_urlCurrent = StringPool.BLANK;
		_urlHome = StringPool.BLANK;
		_urlLayoutTemplates = StringPool.BLANK;
		_urlMyAccount = null;
		_urlPageSettings = null;
		_urlPortal = StringPool.BLANK;
		_urlPublishToLive = null;
		_urlSignIn = StringPool.BLANK;
		_urlSignOut = StringPool.BLANK;
		_urlUpdateManager = null;
		_sessionId = StringPool.BLANK;
		_tilesTitle = StringPool.BLANK;
		_tilesContent = StringPool.BLANK;
		_tilesSelectable = false;
		_includePortletCssJs = false;
		_includeServiceJs = false;
		_portletDisplay.recycle();
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeDisplay.class);

	private Company _company;
	private long _companyGroupId;
	private String _companyLogo = StringPool.BLANK;
	private int _companyLogoHeight;
	private int _companyLogoWidth;
	private String _realCompanyLogo = StringPool.BLANK;
	private int _realCompanyLogoHeight;
	private int _realCompanyLogoWidth;
	private Account _account;
	private User _defaultUser;
	private User _user;
	private User _realUser;
	private String _doAsUserId = StringPool.BLANK;
	private String _doAsUserLanguageId = StringPool.BLANK;
	private long _doAsGroupId = 0;
	private long _refererPlid;
	private Contact _contact;
	private String _layoutSetLogo = StringPool.BLANK;
	private Layout _layout;
	private List<Layout> _layouts;
	private List<Layout> _unfilteredLayouts;
	private long _plid;
	private LayoutTypePortlet _layoutTypePortlet;
	private Group _scopeGroup;
	private long _scopeGroupId;
	private Group _parentGroup;
	private long _parentGroupId;
	private boolean _signedIn;
	private transient PermissionChecker _permissionChecker;
	private Locale _locale;
	private String _languageId;
	private boolean _i18n;
	private String _i18nLanguageId;
	private String _i18nPath;
	private TimeZone _timeZone;
	private Theme _theme;
	private ColorScheme _colorScheme;
	private boolean _themeCssFastLoad;
	private boolean _themeImagesFastLoad;
	private boolean _themeJsBarebone;
	private boolean _themeJsFastLoad;
	private boolean _freeformLayout;
	private String _serverName;
	private int _serverPort;
	private boolean _secure;
	private String _lifecycle;
	private boolean _lifecycleAction;
	private boolean _lifecycleRender;
	private boolean _lifecycleResource;
	private boolean _stateExclusive;
	private boolean _stateMaximized;
	private boolean _statePopUp;
	private boolean _isolated;
	private boolean _facebook;
	private String _facebookCanvasPageURL;
	private boolean _widget;
	private boolean _addSessionIdToURL = false;
	private String _cdnHost = StringPool.BLANK;
	private String _portalURL = StringPool.BLANK;
	private String _pathApplet = StringPool.BLANK;
	private String _pathCms = StringPool.BLANK;
	private String _pathColorSchemeImages = StringPool.BLANK;
	private String _pathContext = StringPool.BLANK;
	private String _pathFlash = StringPool.BLANK;
	private String _pathFriendlyURLPrivateGroup = StringPool.BLANK;
	private String _pathFriendlyURLPrivateUser = StringPool.BLANK;
	private String _pathFriendlyURLPublic = StringPool.BLANK;
	private String _pathImage = StringPool.BLANK;
	private String _pathJavaScript = StringPool.BLANK;
	private String _pathMain = StringPool.BLANK;
	private String _pathSound = StringPool.BLANK;
	private String _pathThemeCss = StringPool.BLANK;
	private String _pathThemeImages = StringPool.BLANK;
	private String _pathThemeJavaScript = StringPool.BLANK;
	private String _pathThemeRoot = StringPool.BLANK;
	private String _pathThemeTemplates = StringPool.BLANK;
	private boolean _showAddContentIcon;
	private boolean _showAddContentIconPermission;
	private boolean _showControlPanelIcon;
	private boolean _showHomeIcon;
	private boolean _showLayoutTemplatesIcon;
	private boolean _showMyAccountIcon;
	private boolean _showPageSettingsIcon;
	private boolean _showPortalIcon;
	private boolean _showSignInIcon;
	private boolean _showSignOutIcon;
	private boolean _showStagingIcon;
	private String _urlAddContent = StringPool.BLANK;
	private String _urlControlPanel = StringPool.BLANK;
	private transient PortletURL _urlCreateAccount = null;
	private String _urlCurrent = StringPool.BLANK;
	private String _urlHome = StringPool.BLANK;
	private String _urlLayoutTemplates = StringPool.BLANK;
	private transient PortletURL _urlMyAccount = null;
	private transient PortletURL _urlPageSettings = null;
	private String _urlPortal = StringPool.BLANK;
	private transient PortletURL _urlPublishToLive = null;
	private String _urlSignIn = StringPool.BLANK;
	private String _urlSignOut = StringPool.BLANK;
	private transient PortletURL _urlUpdateManager = null;
	private String _sessionId = StringPool.BLANK;
	private String _tilesTitle = StringPool.BLANK;
	private String _tilesContent = StringPool.BLANK;
	private boolean _tilesSelectable;
	private boolean _includePortletCssJs;
	private boolean _includeServiceJs;
	private PortletDisplay _portletDisplay = new PortletDisplay();

}