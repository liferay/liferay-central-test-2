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

package com.liferay.portal.model.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.NullSafeProperties;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.StringReader;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="LayoutImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutImpl extends LayoutModelImpl implements Layout {

	public static final String PRIVATE = "PRI.";

	public static final String PUBLIC = "PUB.";

	public static final String DEFAULT_PLID = "default";

	public static final String DEFAULT_PARENT_LAYOUT_ID = "-1";

	public static final String[] TYPES =
		PropsUtil.getArray(PropsUtil.LAYOUT_TYPES);

	public static final String TYPE_PORTLET = "portlet";

	public static final String TYPE_EMBEDDED = "embedded";

	public static final String TYPE_URL = "url";

	public static final String TYPE_ARTICLE = "article";

	public static String getLayoutId(String plid) {
		if (plid != null) {
			int pos = plid.lastIndexOf(StringPool.PERIOD);

			if (pos != -1) {
				return plid.substring(pos + 1, plid.length());
			}
		}

		return null;
	}

	public static String getOwnerId(String plid) {
		if (plid != null) {
			int pos = plid.lastIndexOf(StringPool.PERIOD);

			if (pos != -1) {
				return plid.substring(0, pos);
			}
		}

		return null;
	}

	public static long getGroupId(String ownerId) {
		if ((ownerId != null) &&
			(ownerId.startsWith(PRIVATE) || ownerId.startsWith(PUBLIC))) {

			return GetterUtil.getLong(
				ownerId.substring(PRIVATE.length(), ownerId.length()));
		}
		else {
			return 0;
		}
	}

	public static boolean isPrivateLayout(String ownerId) {
		if (ownerId.startsWith(PRIVATE)) {
			return true;
		}
		else {
			return false;
		}
	}

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

		if (friendlyURL.indexOf("//") != -1) {
			return LayoutFriendlyURLException.ADJACENT_SLASHES;
		}

		char[] c = friendlyURL.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if ((!Validator.isChar(c[i])) && (!Validator.isDigit(c[i])) &&
				(c[i] != '/') && (c[i] != '-') && (c[i] != '_')) {

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

	public void setOwnerId(String ownerId) {
		_plid = ownerId + StringPool.PERIOD + getLayoutId();
		_groupId = getGroupId(ownerId);
		_privateLayout = isPrivateLayout(ownerId);

		super.setOwnerId(ownerId);
	}

	public String getPlid() {
		return _plid;
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

	public long getGroupId() {
		return _groupId;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isShared() {
		if (!_privateLayout) {
			return true;
		}
		else {
			Group group = getGroup();

			String className = group.getClassName();

			if (className.equals(User.class.getName())) {
				return false;
			}
			else {
				return true;
			}
		}
	}

	public String getAncestorLayoutId()
		throws PortalException, SystemException {

		String ancestorLayoutId = StringPool.BLANK;

		Layout ancestorLayout = this;

		while (true) {
			if (!ancestorLayout.getParentLayoutId().equals(
					LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {

				ancestorLayout = LayoutLocalServiceUtil.getLayout(
					ancestorLayout.getParentLayoutId(),
					ancestorLayout.getOwnerId());
			}
			else {
				ancestorLayoutId = ancestorLayout.getLayoutId();

				break;
			}
		}

		return ancestorLayoutId;
	}

	public boolean hasAncestor(String layoutId)
		throws PortalException, SystemException {

		String parentLayoutId = getParentLayoutId();

		while (!parentLayoutId.equals(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {
			if (parentLayoutId.equals(layoutId)) {
				return true;
			}
			else {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					parentLayoutId, getOwnerId());

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		return false;
	}

	public List getChildren() throws PortalException, SystemException {
		return LayoutLocalServiceUtil.getLayouts(getOwnerId(), getLayoutId());
	}

	public String getName(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getName(localeLanguageId);
	}

	public String getName(String localeLanguageId) {
		return _parseLocalizedXml(getName(), localeLanguageId);
	}

	public void setName(String name, Locale locale) {
		try {
			setName(_updateLocalizedXml(getName(), "name", name, locale));
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	public String getTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getTitle(localeLanguageId);
	}

	public String getTitle(String localeLanguageId) {
		return _parseLocalizedXml(getTitle(), localeLanguageId);
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
		try {
			setTitle(_updateLocalizedXml(getTitle(), "title", title, locale));
		}
		catch (Exception e) {
			_log.warn(e);
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
			_typeSettingsProperties = new NullSafeProperties();

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

	public String getIconImageId() {
		return getCompanyId() + ".layout." + getPlid();
	}

	public LayoutSet getLayoutSet() {
		LayoutSet layoutSet = null;

		try {
			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(getOwnerId());
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
			return ThemeLocalUtil.getTheme(getCompanyId(), getThemeId());
		}
	}

	public ColorScheme getColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getColorScheme();
		}
		else {
			return ThemeLocalUtil.getColorScheme(
				getCompanyId(), getTheme().getThemeId(), getColorSchemeId());
		}
	}

	private String _parseLocalizedXml(String xml, String localeLanguageId) {
		String value = StringPool.BLANK;

		try {
			String defaultLanguageId =
				LocaleUtil.toLanguageId(Locale.getDefault());

			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			Iterator itr = root.elements().iterator();

			while (itr.hasNext()) {
				Element el = (Element)itr.next();

				String languageId =
					el.attributeValue("language-id", defaultLanguageId);

				if (languageId.equals(defaultLanguageId)) {
					value = el.getText();
				}

				if (languageId.equals(localeLanguageId)) {
					value = el.getText();

					break;
				}
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return value;
	}

	private String _updateLocalizedXml(
			String xml, String key, String value, Locale locale)
		throws DocumentException, IOException {

		if (Validator.isNull(xml) || (xml.indexOf("<root") == -1)) {
			xml = "<root />";
		}

		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		String defaultLanguageId = LocaleUtil.toLanguageId(Locale.getDefault());

		SAXReader reader = new SAXReader();

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		String availableLocales = root.attributeValue("available-locales");

		Element localeEl = null;

		Iterator itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element el = (Element) itr.next();

			String languageId =
				el.attributeValue("language-id", defaultLanguageId);

			if (languageId.equals(localeLanguageId)) {
				localeEl = el;

				break;
			}
		}

		if (localeEl != null) {
			localeEl.setText(value);
		}
		else {
			localeEl = root.addElement(key);

			if (!localeLanguageId.equals(defaultLanguageId)) {
				localeEl.addAttribute("language-id", localeLanguageId);

				if (availableLocales == null) {
					availableLocales = defaultLanguageId;
				}

				availableLocales += StringPool.COMMA + localeLanguageId;

				root.addAttribute("available-locales", availableLocales);
			}

			localeEl.setText(value);
		}

		return XMLFormatter.toString(doc, "  ");
	}

	private static Log _log = LogFactory.getLog(LayoutImpl.class);

	private String _plid;
	private long _groupId;
	private boolean _privateLayout;
	private Properties _typeSettingsProperties = null;

}