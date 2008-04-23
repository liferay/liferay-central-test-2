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

package com.liferay.portal.model.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.LocalizationUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutImpl extends LayoutModelImpl implements Layout {

	public static int validateFriendlyURL(String friendlyURL) {
		if (friendlyURL.length() < 2) {
			return LayoutFriendlyURLException.TOO_SHORT;
		}

		if (!friendlyURL.startsWith(StringPool.SLASH)) {
			return LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH;
		}

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			return LayoutFriendlyURLException.ENDS_WITH_SLASH;
		}

		if (friendlyURL.indexOf(StringPool.DOUBLE_SLASH) != -1) {
			return LayoutFriendlyURLException.ADJACENT_SLASHES;
		}

		char[] c = friendlyURL.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if ((!Validator.isChar(c[i])) && (!Validator.isDigit(c[i])) &&
				(c[i] != CharPool.DASH) && (c[i] != CharPool.PERIOD) &&
				(c[i] != CharPool.SLASH) && (c[i] != CharPool.UNDERLINE)) {

				return LayoutFriendlyURLException.INVALID_CHARACTERS;
			}
		}

		return -1;
	}

	public static void validateFriendlyURLKeyword(String friendlyURL)
		throws LayoutFriendlyURLException {

		String[] keywords = PropsUtil.getArray(
			PropsUtil.LAYOUT_FRIENDLY_URL_KEYWORDS);

		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];

			if ((friendlyURL.indexOf(
					StringPool.SLASH + keyword + StringPool.SLASH) != -1) ||
				(friendlyURL.endsWith(StringPool.SLASH + keyword))) {

				LayoutFriendlyURLException lfurle =
					new LayoutFriendlyURLException(
						LayoutFriendlyURLException.KEYWORD_CONFLICT);

				lfurle.setKeywordConflict(keyword);

				throw lfurle;
			}
		}
	}

	public LayoutImpl() {
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(getGroupId());
		}
		catch (Exception e) {
			group = new GroupImpl();

			_log.error(e);
		}

		return group;
	}

	/**
	 * @deprecated Will always return true.
	 */
	public boolean isShared() {
		return true;
	}

	public long getAncestorPlid() {
		long plid = 0;

		try {
			Layout layout = this;

			while (true) {
				if (!layout.isRootLayout()) {
					layout = LayoutLocalServiceUtil.getLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getParentLayoutId());
				}
				else {
					plid = layout.getPlid();

					break;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return plid;
	}

	public long getAncestorLayoutId() {
		long layoutId = 0;

		try {
			Layout layout = this;

			while (true) {
				if (!layout.isRootLayout()) {
					layout = LayoutLocalServiceUtil.getLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getParentLayoutId());
				}
				else {
					layoutId = layout.getLayoutId();

					break;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return layoutId;
	}

	public List<Layout> getAncestors() throws SystemException, PortalException {
		List<Layout> layouts = new ArrayList<Layout>();

		Layout layout = this;

		while (true) {
			if (!layout.isRootLayout()) {
				layout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getParentLayoutId());

				layouts.add(layout);
			}
			else {
				break;
			}
		}

		return layouts;
	}

	public boolean hasAncestor(long layoutId)
		throws PortalException, SystemException {

		long parentLayoutId = getParentLayoutId();

		while (isRootLayout()) {
			if (parentLayoutId == layoutId) {
				return true;
			}
			else {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					getGroupId(), isPrivateLayout(), parentLayoutId);

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		return false;
	}

	public boolean isFirstParent() {
		if (isFirstChild() && isRootLayout()) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFirstChild() {
		if (getPriority() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRootLayout() {
		if (this.getParentLayoutId() ==
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			return true;
		}
		else {
			return false;
		}
	}

	public List<Layout> getChildren() throws PortalException, SystemException {
		return LayoutLocalServiceUtil.getLayouts(
			getGroupId(), isPrivateLayout(), getLayoutId());
	}

	public List<Layout> getAllChildren()
		throws PortalException, SystemException {

		List<Layout> layouts = new ArrayList<Layout>();

		Iterator<Layout> itr = getChildren().iterator();

		while (itr.hasNext()) {
			Layout layout = itr.next();

			layouts.add(layout);
			layouts.addAll(layout.getChildren());
		}

		return layouts;
	}

	public List<Layout> getChildren(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		List<Layout> layouts = getChildren();

		Iterator<Layout> itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = itr.next();

			if (layout.isHidden() ||
				!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return layouts;
	}

	public String getName(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getName(localeLanguageId);
	}

	public String getName(String localeLanguageId) {
		return LocalizationUtil.getLocalization(getName(), localeLanguageId);
	}

	public String getName(Locale locale, boolean useDefault) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getName(localeLanguageId, useDefault);
	}

	public String getName(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getName(), localeLanguageId, useDefault);
	}

	public void setName(String name, Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNotNull(name)) {
			setName(
				LocalizationUtil.updateLocalization(
					getName(), "name", name, localeLanguageId));
		}
		else {
			setName(
				LocalizationUtil.removeLocalization(
					getName(), "name", localeLanguageId));
		}
	}

	public String getTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getTitle(localeLanguageId);
	}

	public String getTitle(String localeLanguageId) {
		return LocalizationUtil.getLocalization(getTitle(), localeLanguageId);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getTitle(localeLanguageId, useDefault);
	}

	public String getTitle(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getTitle(), localeLanguageId, useDefault);
	}

	public String getHTMLTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getHTMLTitle(localeLanguageId);
	}

	public String getHTMLTitle(String localeLanguageId) {
		String htmlTitle = getTitle(localeLanguageId);

		if (Validator.isNull(htmlTitle)) {
			htmlTitle = getName(localeLanguageId);
		}

		return htmlTitle;
	}

	public void setTitle(String title, Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNotNull(title)) {
			setTitle(
				LocalizationUtil.updateLocalization(
					getTitle(), "title", title, localeLanguageId));
		}
		else {
			setTitle(
				LocalizationUtil.removeLocalization(
					getTitle(), "title", localeLanguageId));
		}
	}

	public LayoutType getLayoutType() {
		return new LayoutTypePortletImpl(this);
	}

	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return PropertiesUtil.toString(_typeSettingsProperties);
		}
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	public Properties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new SafeProperties();

			try {
				PropertiesUtil.load(
					_typeSettingsProperties, super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return _typeSettingsProperties;
	}

	public void setTypeSettingsProperties(Properties typeSettingsProperties) {
		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(PropertiesUtil.toString(_typeSettingsProperties));
	}

	public LayoutSet getLayoutSet() {
		LayoutSet layoutSet = null;

		try {
			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), isPrivateLayout());
		}
		catch (Exception e) {
			layoutSet = new LayoutSetImpl();

			_log.error(e);
		}

		return layoutSet;
	}

	public boolean isInheritLookAndFeel() {
		if (Validator.isNull(getThemeId()) ||
			Validator.isNull(getColorSchemeId())) {

			return true;
		}
		else {
			return false;
		}
	}

	public Theme getTheme() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getTheme();
		}
		else {
			return ThemeLocalUtil.getTheme(getCompanyId(), getThemeId(), false);
		}
	}

	public ColorScheme getColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getColorScheme();
		}
		else {
			return ThemeLocalUtil.getColorScheme(
				getCompanyId(), getTheme().getThemeId(), getColorSchemeId(),
				false);
		}
	}

	public boolean isInheritWapLookAndFeel() {
		if (Validator.isNull(getWapThemeId()) ||
			Validator.isNull(getWapColorSchemeId())) {

			return true;
		}
		else {
			return false;
		}
	}

	public Theme getWapTheme() throws PortalException, SystemException {
		if (isInheritWapLookAndFeel()) {
			return getLayoutSet().getWapTheme();
		}
		else {
			return ThemeLocalUtil.getTheme(
				getCompanyId(), getWapThemeId(), true);
		}
	}

	public ColorScheme getWapColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getWapColorScheme();
		}
		else {
			return ThemeLocalUtil.getColorScheme(
				getCompanyId(), getWapTheme().getThemeId(),
				getWapColorSchemeId(), true);
		}
	}

	public String getCssText() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getCss();
		}
		else {
			return getCss();
		}
	}

	public String getRegularURL(HttpServletRequest req)
		throws PortalException, SystemException {

		return _getURL(req, false, false);
	}

	public String getResetMaxStateURL(HttpServletRequest req)
		throws PortalException, SystemException {

		return _getURL(req, true, false);
	}

	public String getResetLayoutURL(HttpServletRequest req)
		throws PortalException, SystemException {

		return _getURL(req, true, true);
	}

	public String getTarget() {
		return PortalUtil.getLayoutTarget(this);
	}

	public boolean isSelected(
		boolean selectable, Layout layout, long ancestorPlid) {

		if (selectable) {
			long plid = getPlid();

			if ((plid == layout.getPlid()) || (plid == ancestorPlid)) {
				return true;
			}
		}

		return false;
	}

	private LayoutTypePortlet _getLayoutTypePortletClone(
			HttpServletRequest req)
		throws IOException {

		LayoutTypePortlet layoutTypePortlet = null;

		LayoutClone layoutClone = LayoutCloneFactory.getInstance();

		if (layoutClone != null) {
			String typeSettings = layoutClone.get(req, getPlid());

			if (typeSettings != null) {
				Properties props = new SafeProperties();

				PropertiesUtil.load(props, typeSettings);

				String stateMax = props.getProperty(
					LayoutTypePortletImpl.STATE_MAX);
				String stateMin = props.getProperty(
					LayoutTypePortletImpl.STATE_MIN);

				Layout layout = (Layout)this.clone();

				layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.setStateMax(stateMax);
				layoutTypePortlet.setStateMin(stateMin);
			}
		}

		if (layoutTypePortlet == null) {
			layoutTypePortlet = (LayoutTypePortlet)getLayoutType();
		}

		return layoutTypePortlet;
	}

	private String _getURL(
			HttpServletRequest req, boolean resetMaxState,
			boolean resetRenderParameters)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		if (resetMaxState) {
			Layout layout = themeDisplay.getLayout();

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout.equals(this)) {
				layoutTypePortlet = themeDisplay.getLayoutTypePortlet();
			}
			else {
				try {
					layoutTypePortlet = _getLayoutTypePortletClone(req);
				}
				catch (IOException ioe) {
					_log.error("Unable to clone layout settings", ioe);

					layoutTypePortlet = (LayoutTypePortlet)getLayoutType();
				}
			}

			if (layoutTypePortlet.hasStateMax()) {
				String portletId =
					StringUtil.split(layoutTypePortlet.getStateMax())[0];

				PortletURLImpl portletURLImpl = new PortletURLImpl(
					req, portletId, getPlid(), PortletRequest.ACTION_PHASE);

				try {
					portletURLImpl.setWindowState(WindowState.NORMAL);
					portletURLImpl.setPortletMode(PortletMode.VIEW);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}

				portletURLImpl.setAnchor(false);

				if (PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
					!resetRenderParameters) {

					portletURLImpl.setParameter("p_l_reset", "0");
				}
				else if (!PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
						 resetRenderParameters) {

					portletURLImpl.setParameter("p_l_reset", "1");
				}

				return portletURLImpl.toString();
			}
		}

		String url = PortalUtil.getLayoutURL(this, themeDisplay);

		if (!CookieKeys.hasSessionId(req)) {
			url = PortalUtil.getURLWithSessionId(url, req.getSession().getId());
		}

		if (!resetMaxState && !resetMaxState) {
			return url;
		}

		if (PropsValues.LAYOUT_DEFAULT_P_L_RESET && !resetRenderParameters) {
			url = HttpUtil.addParameter(url, "p_l_reset", 0);
		}
		else if (!PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
				 resetRenderParameters) {

			url = HttpUtil.addParameter(url, "p_l_reset", 1);
		}

		return url;
	}

	private static Log _log = LogFactory.getLog(LayoutImpl.class);

	private Properties _typeSettingsProperties = null;

}