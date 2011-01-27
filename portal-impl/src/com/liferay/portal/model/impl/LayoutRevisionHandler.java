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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutWrapper;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.util.Locale;
import java.util.Map;

/**
 * <a href="RevisionLayoutImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class LayoutRevisionHandler extends LayoutWrapper {

	public LayoutRevisionHandler(Layout layout) {
		super(layout);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long layoutRevisionId = ParamUtil.getLong(
			serviceContext, "layoutRevisionId");

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		try {
			if (layoutRevisionId > 0) {
				_layoutRevision =
					LayoutRevisionLocalServiceUtil.getLayoutRevision(
						layoutRevisionId);
			}
			else if (layoutSetBranchId > 0) {
				_layoutRevision =
					LayoutRevisionLocalServiceUtil.getLayoutRevision(
						layoutSetBranchId, layout.getPlid(), true);
			}
			else {
				LayoutSetBranch branch =
					LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
						layout.getGroupId(), layout.isPrivateLayout());

				_layoutRevision =
					LayoutRevisionLocalServiceUtil.getLayoutRevision(
						branch.getLayoutSetBranchId(), layout.getPlid(), true);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException();
		}
	}

	public LayoutRevisionHandler(Layout layout, LayoutRevision layoutRevision) {
		super(layout);

		_layoutRevision = layoutRevision;
	}

	public Object clone() {
		return new LayoutRevisionHandler((Layout)getWrappedLayout().clone());
	}

	public ColorScheme getColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getColorScheme();
		}
		else {
			return ThemeLocalServiceUtil.getColorScheme(
				getCompanyId(), getTheme().getThemeId(), getColorSchemeId(),
				false);
		}
	}

	public String getColorSchemeId() {
		if (_layoutRevision == null) {
			return super.getColorSchemeId();
		}

		return _layoutRevision.getColorSchemeId();
	}

	public String getCss() {
		if (_layoutRevision == null) {
			return super.getCss();
		}

		return _layoutRevision.getCss();
	}

	public String getCssText() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getCss();
		}
		else {
			return getCss();
		}
	}

	public String getDescription() {
		if (_layoutRevision == null) {
			return super.getDescription();
		}

		return _layoutRevision.getDescription();
	}

	public String getDescription(Locale locale) {
		if (_layoutRevision == null) {
			return super.getDescription(locale);
		}

		return _layoutRevision.getDescription(locale);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getDescription(locale, useDefault);
		}

		return _layoutRevision.getDescription(locale, useDefault);
	}

	public String getDescription(String languageId) {
		if (_layoutRevision == null) {
			return super.getDescription(languageId);
		}

		return _layoutRevision.getDescription(languageId);
	}

	public String getDescription(String languageId, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getDescription(languageId, useDefault);
		}

		return _layoutRevision.getDescription(languageId, useDefault);
	}

	public Map<Locale, String> getDescriptionMap() {
		if (_layoutRevision == null) {
			return super.getDescriptionMap();
		}

		return _layoutRevision.getDescriptionMap();
	}

	public ExpandoBridge getExpandoBridge() {
		if (_layoutRevision == null) {
			return super.getExpandoBridge();
		}

		return _layoutRevision.getExpandoBridge();
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

	public boolean getIconImage() {
		if (_layoutRevision == null) {
			return super.getIconImage();
		}

		return _layoutRevision.getIconImage();
	}

	public long getIconImageId() {
		if (_layoutRevision == null) {
			return super.getIconImageId();
		}

		return _layoutRevision.getIconImageId();
	}

	public String getKeywords() {
		if (_layoutRevision == null) {
			return super.getKeywords();
		}

		return _layoutRevision.getKeywords();
	}

	public String getKeywords(Locale locale) {
		if (_layoutRevision == null) {
			return super.getKeywords(locale);
		}

		return _layoutRevision.getKeywords(locale);
	}

	public String getKeywords(Locale locale, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getKeywords(locale, useDefault);
		}

		return _layoutRevision.getKeywords(locale, useDefault);
	}

	public String getKeywords(String languageId) {
		if (_layoutRevision == null) {
			return super.getKeywords(languageId);
		}

		return _layoutRevision.getKeywords(languageId);
	}

	public String getKeywords(String languageId, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getKeywords(languageId, useDefault);
		}

		return _layoutRevision.getKeywords(languageId, useDefault);
	}

	public Map<Locale, String> getKeywordsMap() {
		if (_layoutRevision == null) {
			return super.getKeywordsMap();
		}

		return _layoutRevision.getKeywordsMap();
	}

	public String getName() {
		if (_layoutRevision == null) {
			return super.getName();
		}

		return _layoutRevision.getName();
	}

	public String getName(Locale locale) {
		if (_layoutRevision == null) {
			return super.getName(locale);
		}

		return _layoutRevision.getName(locale);
	}

	public String getName(Locale locale, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getName(locale, useDefault);
		}

		return _layoutRevision.getName(locale, useDefault);
	}

	public String getName(String languageId) {
		if (_layoutRevision == null) {
			return super.getName(languageId);
		}

		return _layoutRevision.getName(languageId);
	}

	public String getName(String languageId, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getName(languageId, useDefault);
		}

		return _layoutRevision.getName(languageId, useDefault);
	}

	public Map<Locale, String> getNameMap() {
		if (_layoutRevision == null) {
			return super.getNameMap();
		}

		return _layoutRevision.getNameMap();
	}

	public String getRobots() {
		if (_layoutRevision == null) {
			return super.getRobots();
		}

		return _layoutRevision.getRobots();
	}

	public String getRobots(Locale locale) {
		if (_layoutRevision == null) {
			return super.getRobots(locale);
		}

		return _layoutRevision.getRobots(locale);
	}

	public String getRobots(Locale locale, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getRobots(locale, useDefault);
		}

		return _layoutRevision.getRobots(locale, useDefault);
	}

	public String getRobots(String languageId) {
		if (_layoutRevision == null) {
			return super.getRobots(languageId);
		}

		return _layoutRevision.getRobots(languageId);
	}

	public String getRobots(String languageId, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getRobots(languageId, useDefault);
		}

		return _layoutRevision.getRobots(languageId, useDefault);
	}

	public Map<Locale, String> getRobotsMap() {
		if (_layoutRevision == null) {
			return super.getRobotsMap();
		}

		return _layoutRevision.getRobotsMap();
	}

	public Theme getTheme() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getTheme();
		}
		else {
			return ThemeLocalServiceUtil.getTheme(
				getCompanyId(), getThemeId(), false);
		}
	}

	public String getThemeId() {
		if (_layoutRevision == null) {
			return super.getThemeId();
		}

		return _layoutRevision.getThemeId();
	}

	public String getTitle() {
		if (_layoutRevision == null) {
			return super.getTitle();
		}

		return _layoutRevision.getTitle();
	}

	public String getTitle(Locale locale) {
		if (_layoutRevision == null) {
			return super.getTitle(locale);
		}

		return _layoutRevision.getTitle(locale);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getTitle(locale, useDefault);
		}

		return _layoutRevision.getTitle(locale, useDefault);
	}

	public String getTitle(String languageId) {
		if (_layoutRevision == null) {
			return super.getTitle(languageId);
		}

		return _layoutRevision.getTitle(languageId);
	}

	public String getTitle(String languageId, boolean useDefault) {
		if (_layoutRevision == null) {
			return super.getTitle(languageId, useDefault);
		}

		return _layoutRevision.getTitle(languageId, useDefault);
	}

	public Map<Locale, String> getTitleMap() {
		if (_layoutRevision == null) {
			return super.getTitleMap();
		}

		return _layoutRevision.getTitleMap();
	}

	public String getTypeSettings() {
		if (_layoutRevision == null) {
			return super.getTypeSettings();
		}

		if (_typeSettingsProperties == null) {
			return _layoutRevision.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_layoutRevision == null) {
			return super.getTypeSettingsProperties();
		}

		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(_layoutRevision.getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	public ColorScheme getWapColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getWapColorScheme();
		}
		else {
			return ThemeLocalServiceUtil.getColorScheme(
				getCompanyId(), getWapTheme().getThemeId(),
				getWapColorSchemeId(), true);
		}
	}

	public String getWapColorSchemeId() {
		if (_layoutRevision == null) {
			return super.getWapColorSchemeId();
		}

		return _layoutRevision.getWapColorSchemeId();
	}

	public Theme getWapTheme() throws PortalException, SystemException {
		if (isInheritWapLookAndFeel()) {
			return getLayoutSet().getWapTheme();
		}
		else {
			return ThemeLocalServiceUtil.getTheme(
				getCompanyId(), getWapThemeId(), true);
		}
	}

	public String getWapThemeId() {
		if (_layoutRevision == null) {
			return super.getWapThemeId();
		}

		return _layoutRevision.getWapThemeId();
	}

	public boolean isEscapedModel() {
		if (_layoutRevision == null) {
			return super.isEscapedModel();
		}

		return _layoutRevision.isEscapedModel();
	}

	public boolean isIconImage() {
		if (_layoutRevision == null) {
			return super.isIconImage();
		}

		return _layoutRevision.isIconImage();
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

	public boolean isInheritWapLookAndFeel() {
		if (Validator.isNull(getWapThemeId()) ||
			Validator.isNull(getWapColorSchemeId())) {

			return true;
		}
		else {
			return false;
		}
	}

	public void setColorSchemeId(String colorSchemeId) {
		if (_layoutRevision == null) {
			super.setColorSchemeId(colorSchemeId);

			return;
		}

		_layoutRevision.setColorSchemeId(colorSchemeId);
	}

	public void setCss(String css) {
		if (_layoutRevision == null) {
			super.setCss(css);

			return;
		}

		_layoutRevision.setCss(css);
	}

	public void setDescription(Locale locale, String description) {
		if (_layoutRevision == null) {
			super.setDescription(locale, description);

			return;
		}

		_layoutRevision.setDescription(locale, description);
	}

	public void setDescription(String description) {
		if (_layoutRevision == null) {
			super.setDescription(description);

			return;
		}

		_layoutRevision.setDescription(description);
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		if (_layoutRevision == null) {
			super.setDescriptionMap(descriptionMap);

			return;
		}

		_layoutRevision.setDescriptionMap(descriptionMap);
	}

	public void setEscapedModel(boolean escapedModel) {
		if (_layoutRevision == null) {
			super.setEscapedModel(escapedModel);

			return;
		}

		_layoutRevision.setEscapedModel(escapedModel);

		super.setEscapedModel(escapedModel);
	}

	public void setIconImage(boolean iconImage) {
		if (_layoutRevision == null) {
			super.setIconImage(iconImage);

			return;
		}

		_layoutRevision.setIconImage(iconImage);
	}

	public void setIconImageId(long iconImageId) {
		if (_layoutRevision == null) {
			super.setIconImageId(iconImageId);

			return;
		}

		_layoutRevision.setIconImageId(iconImageId);
	}

	public void setKeywords(Locale locale, String keywords) {
		if (_layoutRevision == null) {
			super.setKeywords(locale, keywords);

			return;
		}

		_layoutRevision.setKeywords(locale, keywords);
	}

	public void setKeywords(String keywords) {
		if (_layoutRevision == null) {
			super.setKeywords(keywords);

			return;
		}

		_layoutRevision.setKeywords(keywords);
	}

	public void setKeywordsMap(Map<Locale, String> keywordsMap) {
		if (_layoutRevision == null) {
			super.setKeywordsMap(keywordsMap);

			return;
		}

		_layoutRevision.setKeywordsMap(keywordsMap);
	}

	public void setName(Locale locale, String name) {
		if (_layoutRevision == null) {
			super.setName(locale, name);

			return;
		}

		_layoutRevision.setName(locale, name);
	}

	public void setName(String name) {
		if (_layoutRevision == null) {
			super.setName(name);

			return;
		}

		_layoutRevision.setName(name);
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		if (_layoutRevision == null) {
			super.setNameMap(nameMap);

			return;
		}

		_layoutRevision.setNameMap(nameMap);
	}

	public void setRobots(Locale locale, String robots) {
		if (_layoutRevision == null) {
			super.setRobots(locale, robots);

			return;
		}

		_layoutRevision.setRobots(locale, robots);
	}

	public void setRobots(String robots) {
		if (_layoutRevision == null) {
			super.setRobots(robots);

			return;
		}

		_layoutRevision.setRobots(robots);
	}

	public void setRobotsMap(Map<Locale, String> robotsMap) {
		if (_layoutRevision == null) {
			super.setRobotsMap(robotsMap);

			return;
		}

		_layoutRevision.setRobotsMap(robotsMap);
	}

	public void setThemeId(String themeId) {
		if (_layoutRevision == null) {
			super.setThemeId(themeId);

			return;
		}

		_layoutRevision.setThemeId(themeId);
	}

	public void setTitle(Locale locale, String title) {
		if (_layoutRevision == null) {
			super.setTitle(locale, title);

			return;
		}

		_layoutRevision.setTitle(locale, title);
	}

	public void setTitle(String title) {
		if (_layoutRevision == null) {
			super.setTitle(title);

			return;
		}

		_layoutRevision.setTitle(title);
	}

	public void setTitleMap(Map<Locale, String> titleMap) {
		if (_layoutRevision == null) {
			super.setTitleMap(titleMap);

			return;
		}

		_layoutRevision.setTitleMap(titleMap);
	}

	public void setTypeSettings(String typeSettings) {
		if (_layoutRevision == null) {
			super.setTypeSettings(typeSettings);

			return;
		}

		_typeSettingsProperties = null;

		_layoutRevision.setTypeSettings(typeSettings);
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		if (_layoutRevision == null) {
			super.setTypeSettingsProperties(typeSettingsProperties);

			return;
		}

		_typeSettingsProperties = typeSettingsProperties;

		_layoutRevision.setTypeSettings(_typeSettingsProperties.toString());
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		if (_layoutRevision == null) {
			super.setWapColorSchemeId(wapColorSchemeId);

			return;
		}

		_layoutRevision.setWapColorSchemeId(wapColorSchemeId);
	}

	public void setWapThemeId(String wapThemeId) {
		if (_layoutRevision == null) {
			super.setWapThemeId(wapThemeId);

			return;
		}

		_layoutRevision.setWapThemeId(wapThemeId);
	}

	public Layout toEscapedModel() {
		return new LayoutRevisionHandler(
			getWrappedLayout().toEscapedModel(),
			_layoutRevision.toEscapedModel());
	}

	public String toString() {
		StringBundler sb = new StringBundler(51);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", plid=");
		sb.append(getPlid());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
		sb.append(", layoutId=");
		sb.append(getLayoutId());
		sb.append(", parentLayoutId=");
		sb.append(getParentLayoutId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", keywords=");
		sb.append(getKeywords());
		sb.append(", robots=");
		sb.append(getRobots());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", hidden=");
		sb.append(getHidden());
		sb.append(", friendlyURL=");
		sb.append(getFriendlyURL());
		sb.append(", iconImage=");
		sb.append(getIconImage());
		sb.append(", iconImageId=");
		sb.append(getIconImageId());
		sb.append(", themeId=");
		sb.append(getThemeId());
		sb.append(", colorSchemeId=");
		sb.append(getColorSchemeId());
		sb.append(", wapThemeId=");
		sb.append(getWapThemeId());
		sb.append(", wapColorSchemeId=");
		sb.append(getWapColorSchemeId());
		sb.append(", css=");
		sb.append(getCss());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", layoutPrototypeId=");
		sb.append(getLayoutPrototypeId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(79);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Layout");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(getPrivateLayout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutId</column-name><column-value><![CDATA[");
		sb.append(getLayoutId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentLayoutId</column-name><column-value><![CDATA[");
		sb.append(getParentLayoutId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>keywords</column-name><column-value><![CDATA[");
		sb.append(getKeywords());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>robots</column-name><column-value><![CDATA[");
		sb.append(getRobots());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hidden</column-name><column-value><![CDATA[");
		sb.append(getHidden());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friendlyURL</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>iconImage</column-name><column-value><![CDATA[");
		sb.append(getIconImage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>iconImageId</column-name><column-value><![CDATA[");
		sb.append(getIconImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>themeId</column-name><column-value><![CDATA[");
		sb.append(getThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>colorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wapThemeId</column-name><column-value><![CDATA[");
		sb.append(getWapThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wapColorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getWapColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>css</column-name><column-value><![CDATA[");
		sb.append(getCss());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutPrototypeId</column-name><column-value><![CDATA[");
		sb.append(getLayoutPrototypeId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionHandler.class);

	private LayoutRevision _layoutRevision;
	private UnicodeProperties _typeSettingsProperties;

}