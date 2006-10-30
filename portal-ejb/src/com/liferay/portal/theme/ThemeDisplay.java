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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.util.StringPool;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ThemeDisplay.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ThemeDisplay {

	public Company getCompany() {
		return _company;
	}

	public void setCompany(Company company) {
		_company = company;

		setAccount(company.getAccount());
	}

	public String getCompanyId() {
		return _company.getCompanyId();
	}

	public String getCompanyLogo() {
		return _companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		_companyLogo = companyLogo;
	}

	public Account getAccount() {
		return _account;
	}

	public void setAccount(Account account) {
		_account = account;
	}

	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		_user = user;

		setContact(user.getContact());
	}

	public String getUserId() {
		return _user.getUserId();
	}

	public Contact getContact() {
		return _contact;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public Layout getLayout() {
		return _layout;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	public List getLayouts() {
		return _layouts;
	}

	public void setLayouts(List layouts) {
		_layouts = layouts;
	}

	public String getPlid() {
		return _plid;
	}

	public void setPlid(String plid) {
		_plid = plid;
	}

	public LayoutTypePortlet getLayoutTypePortlet() {
		return _layoutTypePortlet;
	}

	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet) {
		_layoutTypePortlet = layoutTypePortlet;
	}

	public String getPortletGroupId() {
		return _portletGroupId;
	}

	public void setPortletGroupId(String portletGroupId) {
		_portletGroupId = portletGroupId;
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

	public void setLookAndFeel(Theme theme, ColorScheme colorScheme) {
		setLookAndFeel(getPathContext(), theme, colorScheme);
	}

	public void setLookAndFeel(
		String contextPath, Theme theme, ColorScheme colorScheme) {

		_theme = theme;
		_colorScheme = colorScheme;

		if ((theme != null) && (colorScheme != null)) {
			String themeContextPath = contextPath;

			if (theme.isWARFile()) {
				themeContextPath =
					StringPool.SLASH + theme.getServletContextName();
			}

			setPathColorSchemeImage(
				themeContextPath + theme.getRootPath() + "/color_schemes/" +
					colorScheme.getColorSchemeId());

			setPathThemeImage(themeContextPath + theme.getImagesPath());
			setPathThemeRoot(themeContextPath + theme.getRootPath());
		}
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

	public int getResolution() {
		return _resolution;
	}

	public void setResolution(int resolution) {
		_resolution = resolution;
	}

	public boolean isStatePopUp() {
		return _statePopUp;
	}

	public void setStatePopUp(boolean statePopUp) {
		_statePopUp = statePopUp;
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

	public String getPathColorSchemeImage() {
		return _pathColorSchemeImage;
	}

	public void setPathColorSchemeImage(String pathColorSchemeImage) {
		_pathColorSchemeImage = pathColorSchemeImage;
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

	public String getPathFriendlyURLPrivate() {
		return _pathFriendlyURLPrivate;
	}

	public void setPathFriendlyURLPrivate(String pathFriendlyURLPrivate) {
		_pathFriendlyURLPrivate = pathFriendlyURLPrivate;
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

	public String getPathRoot() {
		return _pathRoot;
	}

	public void setPathRoot(String pathRoot) {
		_pathRoot = pathRoot;
	}

	public String getPathSound() {
		return _pathSound;
	}

	public void setPathSound(String pathSound) {
		_pathSound = pathSound;
	}

	public String getPathThemeImage() {
		return _pathThemeImage;
	}

	public void setPathThemeImage(String pathThemeImage) {
		_pathThemeImage = pathThemeImage;
	}

	public String getPathThemeRoot() {
		return _pathThemeRoot;
	}

	public void setPathThemeRoot(String pathThemeRoot) {
		_pathThemeRoot = pathThemeRoot;
	}

	public boolean isShowAddContentIcon() {
		return _showAddContentIcon;
	}

	public void setShowAddContentIcon(boolean showAddContentIcon) {
		_showAddContentIcon = showAddContentIcon;
	}

	public boolean isShowHomeIcon() {
		return _showHomeIcon;
	}

	public void setShowHomeIcon(boolean showHomeIcon) {
		_showHomeIcon = showHomeIcon;
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

	public String getURLAddContent() {
		return _urlAddContent;
	}

	public void setURLAddContent(String urlAddContent) {
		_urlAddContent = urlAddContent;
	}

	public PortletURL getURLCreateAccount() {
		return _urlCreateAccount;
	}

	public void setURLCreateAccount(PortletURL urlCreateAccount) {
		_urlCreateAccount = urlCreateAccount;
	}

	public String getURLHome() {
		return _urlHome;
	}

	public void setURLHome(String urlHome) {
		_urlHome = urlHome;
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

	public PortletDisplay getPortletDisplay() {
		return _portletDisplay;
	}

	public void setPortletDisplay(PortletDisplay portletDisplay) {
		_portletDisplay = portletDisplay;
	}

	public void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		_company = null;
		_companyLogo = null;
		_user = null;
		_layout = null;
		_layouts = null;
		_plid = null;
		_layoutTypePortlet = null;
		_portletGroupId = null;
		_signedIn = false;
		_permissionChecker = null;
		_locale = null;
		_timeZone = null;
		_theme = null;
		_colorScheme = null;
		_serverPort = 0;
		_secure = false;
		_resolution = 0;
		_pathApplet = null;
		_pathCms = null;
		_pathColorSchemeImage = null;
		_pathContext = null;
		_pathFlash = null;
		_pathFriendlyURLPrivate = null;
		_pathFriendlyURLPublic = null;
		_pathImage = null;
		_pathJavaScript = null;
		_pathMain = null;
		_pathRoot = null;
		_pathSound = null;
		_pathThemeImage = null;
		_pathThemeRoot = null;
		_showAddContentIcon = false;
		_showHomeIcon = false;
		_showMyAccountIcon = false;
		_showPageSettingsIcon = false;
		_showPortalIcon = false;
		_showSignInIcon = false;
		_showSignOutIcon = false;
		_urlAddContent = null;
		_urlCreateAccount = null;
		_urlHome = null;
		_urlMyAccount = null;
		_urlPageSettings = null;
		_urlPortal = null;
		_urlSignIn = null;
		_urlSignOut = null;
		_portletDisplay.recycle();
	}

	protected ThemeDisplay() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	private static Log _log = LogFactory.getLog(ThemeDisplay.class);

	private Company _company;
	private String _companyLogo;
	private Account _account;
	private User _user;
	private Contact _contact;
	private Layout _layout;
	private List _layouts;
	private String _plid;
	private LayoutTypePortlet _layoutTypePortlet;
	private String _portletGroupId;
	private boolean _signedIn;
	private PermissionChecker _permissionChecker;
	private Locale _locale;
	private TimeZone _timeZone;
	private Theme _theme;
	private ColorScheme _colorScheme;
	private int _serverPort;
	private boolean _secure;
	private int _resolution;
	private boolean _statePopUp;
	private String _pathApplet;
	private String _pathCms;
	private String _pathColorSchemeImage;
	private String _pathContext;
	private String _pathFlash;
	private String _pathFriendlyURLPrivate;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathJavaScript;
	private String _pathMain;
	private String _pathRoot;
	private String _pathSound;
	private String _pathThemeImage;
	private String _pathThemeRoot;
	private boolean _showAddContentIcon;
	private boolean _showHomeIcon;
	private boolean _showMyAccountIcon;
	private boolean _showPageSettingsIcon;
	private boolean _showPortalIcon;
	private boolean _showSignInIcon;
	private boolean _showSignOutIcon;
	private String _urlAddContent = null;
	private PortletURL _urlCreateAccount = null;
	private String _urlHome = null;
	private PortletURL _urlMyAccount = null;
	private PortletURL _urlPageSettings = null;
	private String _urlPortal = null;
	private String _urlSignIn = null;
	private String _urlSignOut = null;
	private PortletDisplay _portletDisplay = new PortletDisplay();

}