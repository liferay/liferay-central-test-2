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

package com.liferay.portal.theme;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.ImageLocalUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

/**
 * <a href="ThemeDisplayFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class ThemeDisplayFactory {

	public static ThemeDisplay create() throws Exception {
		if (PropsValues.COMMONS_POOL_ENABLED) {
			return (ThemeDisplay)_instance._pool.borrowObject();
		}
		else {
			return new ThemeDisplay();
		}
	}

	public static void recycle(ThemeDisplay themeDisplay) throws Exception {
		if (PropsValues.COMMONS_POOL_ENABLED) {
			_instance._pool.returnObject(themeDisplay);
		}
		else if (themeDisplay != null) {
			themeDisplay.recycle();
		}
	}

	public static ThemeDisplay setup(long companyId)
		throws PortalException, SystemException {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		return setup(null, company, company.getLocale(), null, false);
	}

	public static ThemeDisplay setup(
			String cdnHost, Company company, Locale locale, Layout layout,
			boolean wapTheme)
		throws PortalException, SystemException {
		// Company

		long companyId = company.getCompanyId();

		String serverName = company.getVirtualHost();
		int serverPort = PortalUtil.getPortalPort();
		boolean secure = false;

		// CDN host

		cdnHost = GetterUtil.getString(cdnHost, PortalUtil.getCDNHost());

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
			imagePath + "/company_logo?img_id=" + company.getLogoId() + "&t=" +
				ImageServletTokenUtil.getToken(company.getLogoId());

		Image companyLogoImage = ImageLocalUtil.getCompanyLogo(
			company.getLogoId());

		int companyLogoHeight = companyLogoImage.getHeight();
		int companyLogoWidth = companyLogoImage.getWidth();

		String realCompanyLogo = companyLogo;
		int realCompanyLogoHeight = companyLogoHeight;
		int realCompanyLogoWidth = companyLogoWidth;

		String layoutSetLogo = null;

		if (layout != null) {
			if (company.isCommunityLogo()) {
				LayoutSet layoutSet = layout.getLayoutSet();

				if (layoutSet.isLogo()) {
					long logoId = layoutSet.getLogoId();

					layoutSetLogo =
						imagePath + "/layout_set_logo?img_id=" + logoId +
							"&t=" + ImageServletTokenUtil.getToken(logoId);

					Image layoutSetLogoImage = ImageLocalUtil.getCompanyLogo(
						logoId);

					companyLogo = layoutSetLogo;
					companyLogoHeight = layoutSetLogoImage.getHeight();
					companyLogoWidth = layoutSetLogoImage.getWidth();
				}
			}

		}

		// Theme and color scheme

		Theme theme = null;
		ColorScheme colorScheme = null;

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

		ThemeDisplay themeDisplay = null;

		try {
			themeDisplay = create();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		// Set the CDN host first because other methods (setLookAndFeel) depend
		// on it being set

		themeDisplay.setCDNHost(
			GetterUtil.getString(cdnHost, PortalUtil.getCDNHost()));

		themeDisplay.setCompany(company);
		themeDisplay.setCompanyLogo(companyLogo);
		themeDisplay.setCompanyLogoHeight(companyLogoHeight);
		themeDisplay.setCompanyLogoWidth(companyLogoWidth);
		themeDisplay.setRealCompanyLogo(realCompanyLogo);
		themeDisplay.setRealCompanyLogoHeight(realCompanyLogoHeight);
		themeDisplay.setRealCompanyLogoWidth(realCompanyLogoWidth);
		themeDisplay.setLayoutSetLogo(layoutSetLogo);
		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(locale);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
		themeDisplay.setServerName(serverName);
		themeDisplay.setServerPort(serverPort);
		themeDisplay.setSecure(secure);
		themeDisplay.setPathApplet(contextPath + "/applets");
		themeDisplay.setPathCms(contextPath + "/cms");
		themeDisplay.setPathContext(contextPath);
		themeDisplay.setPathFlash(contextPath + "/flash");
		themeDisplay.setPathFriendlyURLPrivateGroup(
			friendlyURLPrivateGroupPath);
		themeDisplay.setPathFriendlyURLPrivateUser(friendlyURLPrivateUserPath);
		themeDisplay.setPathFriendlyURLPublic(friendlyURLPublicPath);
		themeDisplay.setPathImage(imagePath);
		themeDisplay.setPathJavaScript(cdnHost + contextPath + "/html/js");
		themeDisplay.setPathMain(mainPath);
		themeDisplay.setPathSound(contextPath + "/html/sound");

		themeDisplay.setURLPortal(PortalUtil.getPortalURL(
			serverName, serverPort, secure));

		return themeDisplay;
	}

	protected static boolean isViewableCommunity(
			User user, long groupId, boolean privateLayout,
			PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		// Inactive communities are not viewable

		if (!group.isActive()) {
			return false;
		}
		else if (group.isStagingGroup()) {
			Group liveGroup = group.getLiveGroup();

			if (!liveGroup.isActive()) {
				return false;
			}
		}

		// User private layouts are only viewable by the user and anyone who can
		// update the user. The user must also be active.

		if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (groupUserId == user.getUserId()) {
				return true;
			}
			else {
				User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

				if (!groupUser.isActive()) {
					return false;
				}

				if (privateLayout) {
					if (UserPermissionUtil.contains(
							permissionChecker, groupUserId,
							groupUser.getOrganizationIds(),
							ActionKeys.UPDATE)) {

						return true;
					}
					else {
						return false;
					}
				}
			}
		}

		// Most public layouts are viewable

		if (!privateLayout) {
			return true;
		}

		// If the current group is staging, the live group should be checked
		// for membership instead

		if (group.isStagingGroup()) {
			groupId = group.getLiveGroupId();
		}

		// Community or organization layouts are only viewable by users who
		// belong to the community or organization, or by users who can update
		// the community or organization

		if (group.isCommunity()) {
			if (GroupLocalServiceUtil.hasUserGroup(user.getUserId(), groupId)) {
				return true;
			}
			else if (GroupPermissionUtil.contains(
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
			else if (OrganizationPermissionUtil.contains(
						permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}
		}

		return false;
	}


	private ThemeDisplayFactory() {
		_pool = new StackObjectPool(new Factory());
	}

	private static ThemeDisplayFactory _instance = new ThemeDisplayFactory();

	private ObjectPool _pool;

	private class Factory extends BasePoolableObjectFactory {

		public Object makeObject() {
			return new ThemeDisplay();
		}

		public void passivateObject(Object obj) {
			ThemeDisplay themeDisplay = (ThemeDisplay)obj;

			themeDisplay.recycle();
		}

	}

}