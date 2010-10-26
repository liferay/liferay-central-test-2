/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutWrapper;

/**
 * <a href="RevisionLayoutImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class LayoutRevisionHandler extends LayoutWrapper {

	public LayoutRevisionHandler(Layout layout) {
		super(layout);
	}

//	public String getCssText() throws PortalException, SystemException {
//		if (isInheritLookAndFeel()) {
//			return getLayoutSet().getCss();
//		}
//		else {
//			return _revision.getCss();
//		}
//	}
//
//	public String getDescription() {
//		return _revision.getDescription();
//	}
//
//	public String getHTMLTitle(Locale locale) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		return getHTMLTitle(localeLanguageId);
//	}
//
//	public String getHTMLTitle(String localeLanguageId) {
//		String htmlTitle = getTitle(localeLanguageId);
//
//		if (Validator.isNull(htmlTitle)) {
//			htmlTitle = getName(localeLanguageId);
//		}
//
//		return htmlTitle;
//	}
//
//	public String getName() {
//		return _revision.getName();
//	}
//
//	public String getName(Locale locale) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		return getName(localeLanguageId);
//	}
//
//	public String getName(Locale locale, boolean useDefault) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		return getName(localeLanguageId, useDefault);
//	}
//
//	public String getName(String localeLanguageId) {
//		return LocalizationUtil.getLocalization(getName(), localeLanguageId);
//	}
//
//	public String getName(String localeLanguageId, boolean useDefault) {
//		return LocalizationUtil.getLocalization(
//			getName(), localeLanguageId, useDefault);
//	}
//
//	public Theme getTheme() throws PortalException, SystemException {
//		if (isInheritLookAndFeel()) {
//			return getLayoutSet().getTheme();
//		}
//		else {
//			return ThemeLocalServiceUtil.getTheme(
//				getCompanyId(), getThemeId(), false);
//		}
//	}
//
//	public String getThemeId() {
//		return _revision.getThemeId();
//	}
//
//	public String getTitle() {
//		return _revision.getTitle();
//	}
//
//	public String getTitle(Locale locale) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		return getTitle(localeLanguageId);
//	}
//
//	public String getTitle(Locale locale, boolean useDefault) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		return getTitle(localeLanguageId, useDefault);
//	}
//
//	public String getTitle(String localeLanguageId) {
//		return LocalizationUtil.getLocalization(getTitle(), localeLanguageId);
//	}
//
//	public String getTitle(String localeLanguageId, boolean useDefault) {
//		return LocalizationUtil.getLocalization(
//			getTitle(), localeLanguageId, useDefault);
//	}
//
//	public String getTypeSettings() {
//		if (_typeSettingsProperties == null) {
//			return _revision.getTypeSettings();
//		}
//		else {
//			return _typeSettingsProperties.toString();
//		}
//	}
//
//	public UnicodeProperties getTypeSettingsProperties() {
//		if (_typeSettingsProperties == null) {
//			_typeSettingsProperties = new UnicodeProperties(true);
//
//			_typeSettingsProperties.fastLoad(_revision.getTypeSettings());
//		}
//
//		return _typeSettingsProperties;
//	}
//
//	public boolean getIconImage() {
//		return _revision.getIconImage();
//	}
//
//	public long getIconImageId() {
//		return _revision.getIconImageId();
//	}
//
//	public String getCss() {
//		return _revision.getCss();
//	}
//
//	public Revision getRevision() {
//		if (_revision == null) {
//			ServiceContext serviceContext =
//				ServiceContextThreadLocal.getServiceContext();
//
//			long branchId = ParamUtil.getLong(serviceContext, "branchId");
//
//			try {
//				_revision = RevisionLocalServiceUtil.checkLatestRevision(
//					branchId, getPlid(), serviceContext);
//			}
//			catch (Exception e) {
//				_log.error(e);
//			}
//		}
//
//		return _revision;
//	}
//
//	public boolean isIconImage() {
//		return _revision.isIconImage();
//	}
//
//	public void setColorSchemeId(String colorSchemeId) {
//		_revision.setColorSchemeId(colorSchemeId);
//	}
//
//	public void setCss(String css) {
//		_revision.setCss(css);
//	}
//
//	public void setDescription(String description) {
//		_revision.setDescription(description);
//	}
//
//	public void setIconImage(boolean iconImage) {
//		_revision.setIconImage(iconImage);
//	}
//
//	public void setIconImageId(long iconImageId) {
//		_revision.setIconImageId(iconImageId);
//	}
//
//	public void setName(String name) {
//		_revision.setName(name);
//	}
//
//	public void setName(String name, Locale locale) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		if (Validator.isNotNull(name)) {
//			setName(
//				LocalizationUtil.updateLocalization(
//					getName(), "name", name, localeLanguageId));
//		}
//		else {
//			setName(
//				LocalizationUtil.removeLocalization(
//					getName(), "name", localeLanguageId));
//		}
//	}
//
//	public void setThemeId(String themeId) {
//		_revision.setThemeId(themeId);
//	}
//
//	public void setTitle(String title) {
//		_revision.setTitle(title);
//	}
//
//	public void setTitle(String title, Locale locale) {
//		String localeLanguageId = LocaleUtil.toLanguageId(locale);
//
//		if (Validator.isNotNull(title)) {
//			setTitle(
//				LocalizationUtil.updateLocalization(
//					getTitle(), "title", title, localeLanguageId));
//		}
//		else {
//			setTitle(
//				LocalizationUtil.removeLocalization(
//					getTitle(), "title", localeLanguageId));
//		}
//	}
//
//	public void setTypeSettings(String typeSettings) {
//		_typeSettingsProperties = null;
//
//		_revision.setTypeSettings(typeSettings);
//	}
//
//	public void setTypeSettingsProperties(
//		UnicodeProperties typeSettingsProperties) {
//
//		_typeSettingsProperties = typeSettingsProperties;
//
//		_revision.setTypeSettings(_typeSettingsProperties.toString());
//	}
//
//	public void setWapThemeId(String wapThemeId) {
//		_revision.setWapThemeId(wapThemeId);
//	}
//
//	public void setWapColorSchemeId(String wapColorSchemeId) {
//		_revision.setWapColorSchemeId(wapColorSchemeId);
//	}
//
//	public void setRevision(Revision revision) {
//		_revision = revision;
//	}
//
//	private static final Log _log = LogFactory.getLog(LayoutRevisionHandler.class);
//
//	private Revision _revision;
//	private UnicodeProperties _typeSettingsProperties;

}