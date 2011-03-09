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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Layout}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Layout
 * @generated
 */
public class LayoutWrapper implements Layout {
	public LayoutWrapper(Layout layout) {
		_layout = layout;
	}

	/**
	* Gets the primary key of this layout.
	*
	* @return the primary key of this layout
	*/
	public long getPrimaryKey() {
		return _layout.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout
	*
	* @param pk the primary key of this layout
	*/
	public void setPrimaryKey(long pk) {
		_layout.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this layout.
	*
	* @return the uuid of this layout
	*/
	public java.lang.String getUuid() {
		return _layout.getUuid();
	}

	/**
	* Sets the uuid of this layout.
	*
	* @param uuid the uuid of this layout
	*/
	public void setUuid(java.lang.String uuid) {
		_layout.setUuid(uuid);
	}

	/**
	* Gets the plid of this layout.
	*
	* @return the plid of this layout
	*/
	public long getPlid() {
		return _layout.getPlid();
	}

	/**
	* Sets the plid of this layout.
	*
	* @param plid the plid of this layout
	*/
	public void setPlid(long plid) {
		_layout.setPlid(plid);
	}

	/**
	* Gets the group ID of this layout.
	*
	* @return the group ID of this layout
	*/
	public long getGroupId() {
		return _layout.getGroupId();
	}

	/**
	* Sets the group ID of this layout.
	*
	* @param groupId the group ID of this layout
	*/
	public void setGroupId(long groupId) {
		_layout.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this layout.
	*
	* @return the company ID of this layout
	*/
	public long getCompanyId() {
		return _layout.getCompanyId();
	}

	/**
	* Sets the company ID of this layout.
	*
	* @param companyId the company ID of this layout
	*/
	public void setCompanyId(long companyId) {
		_layout.setCompanyId(companyId);
	}

	/**
	* Gets the private layout of this layout.
	*
	* @return the private layout of this layout
	*/
	public boolean getPrivateLayout() {
		return _layout.getPrivateLayout();
	}

	/**
	* Determines if this layout is private layout.
	*
	* @return <code>true</code> if this layout is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layout.isPrivateLayout();
	}

	/**
	* Sets whether this layout is private layout.
	*
	* @param privateLayout the private layout of this layout
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layout.setPrivateLayout(privateLayout);
	}

	/**
	* Gets the layout ID of this layout.
	*
	* @return the layout ID of this layout
	*/
	public long getLayoutId() {
		return _layout.getLayoutId();
	}

	/**
	* Sets the layout ID of this layout.
	*
	* @param layoutId the layout ID of this layout
	*/
	public void setLayoutId(long layoutId) {
		_layout.setLayoutId(layoutId);
	}

	/**
	* Gets the parent layout ID of this layout.
	*
	* @return the parent layout ID of this layout
	*/
	public long getParentLayoutId() {
		return _layout.getParentLayoutId();
	}

	/**
	* Sets the parent layout ID of this layout.
	*
	* @param parentLayoutId the parent layout ID of this layout
	*/
	public void setParentLayoutId(long parentLayoutId) {
		_layout.setParentLayoutId(parentLayoutId);
	}

	/**
	* Gets the name of this layout.
	*
	* @return the name of this layout
	*/
	public java.lang.String getName() {
		return _layout.getName();
	}

	/**
	* Gets the localized name of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this layout
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _layout.getName(locale);
	}

	/**
	* Gets the localized name of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layout.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this layout
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _layout.getName(languageId);
	}

	/**
	* Gets the localized name of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this layout.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layout.getNameMap();
	}

	/**
	* Sets the name of this layout.
	*
	* @param name the name of this layout
	*/
	public void setName(java.lang.String name) {
		_layout.setName(name);
	}

	/**
	* Sets the localized name of this layout.
	*
	* @param name the localized name of this layout
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layout.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this layout from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layout.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layout.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the title of this layout.
	*
	* @return the title of this layout
	*/
	public java.lang.String getTitle() {
		return _layout.getTitle();
	}

	/**
	* Gets the localized title of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized title for
	* @return the localized title of this layout
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _layout.getTitle(locale);
	}

	/**
	* Gets the localized title of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _layout.getTitle(locale, useDefault);
	}

	/**
	* Gets the localized title of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @return the localized title of this layout
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _layout.getTitle(languageId);
	}

	/**
	* Gets the localized title of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getTitle(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized title of this layout.
	*
	* @return the locales and localized title
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _layout.getTitleMap();
	}

	/**
	* Sets the title of this layout.
	*
	* @param title the title of this layout
	*/
	public void setTitle(java.lang.String title) {
		_layout.setTitle(title);
	}

	/**
	* Sets the localized title of this layout.
	*
	* @param title the localized title of this layout
	* @param locale the locale to set the localized title for
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_layout.setTitle(title, locale);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setTitle(title, locale, defaultLocale);
	}

	/**
	* Sets the localized titles of this layout from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this layout
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_layout.setTitleMap(titleMap);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_layout.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Gets the description of this layout.
	*
	* @return the description of this layout
	*/
	public java.lang.String getDescription() {
		return _layout.getDescription();
	}

	/**
	* Gets the localized description of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized description for
	* @return the localized description of this layout
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _layout.getDescription(locale);
	}

	/**
	* Gets the localized description of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getDescription(locale, useDefault);
	}

	/**
	* Gets the localized description of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @return the localized description of this layout
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _layout.getDescription(languageId);
	}

	/**
	* Gets the localized description of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized description for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getDescription(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized description of this layout.
	*
	* @return the locales and localized description
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _layout.getDescriptionMap();
	}

	/**
	* Sets the description of this layout.
	*
	* @param description the description of this layout
	*/
	public void setDescription(java.lang.String description) {
		_layout.setDescription(description);
	}

	/**
	* Sets the localized description of this layout.
	*
	* @param description the localized description of this layout
	* @param locale the locale to set the localized description for
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_layout.setDescription(description, locale);
	}

	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_layout.setDescription(description, locale, defaultLocale);
	}

	/**
	* Sets the localized descriptions of this layout from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this layout
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_layout.setDescriptionMap(descriptionMap);
	}

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_layout.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Gets the keywords of this layout.
	*
	* @return the keywords of this layout
	*/
	public java.lang.String getKeywords() {
		return _layout.getKeywords();
	}

	/**
	* Gets the localized keywords of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized keywords for
	* @return the localized keywords of this layout
	*/
	public java.lang.String getKeywords(java.util.Locale locale) {
		return _layout.getKeywords(locale);
	}

	/**
	* Gets the localized keywords of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized keywords for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getKeywords(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getKeywords(locale, useDefault);
	}

	/**
	* Gets the localized keywords of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized keywords for
	* @return the localized keywords of this layout
	*/
	public java.lang.String getKeywords(java.lang.String languageId) {
		return _layout.getKeywords(languageId);
	}

	/**
	* Gets the localized keywords of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized keywords for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout
	*/
	public java.lang.String getKeywords(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getKeywords(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized keywords of this layout.
	*
	* @return the locales and localized keywords
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getKeywordsMap() {
		return _layout.getKeywordsMap();
	}

	/**
	* Sets the keywords of this layout.
	*
	* @param keywords the keywords of this layout
	*/
	public void setKeywords(java.lang.String keywords) {
		_layout.setKeywords(keywords);
	}

	/**
	* Sets the localized keywords of this layout.
	*
	* @param keywords the localized keywords of this layout
	* @param locale the locale to set the localized keywords for
	*/
	public void setKeywords(java.lang.String keywords, java.util.Locale locale) {
		_layout.setKeywords(keywords, locale);
	}

	public void setKeywords(java.lang.String keywords, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setKeywords(keywords, locale, defaultLocale);
	}

	/**
	* Sets the localized keywordses of this layout from the map of locales and localized keywordses.
	*
	* @param keywordsMap the locales and localized keywordses of this layout
	*/
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap) {
		_layout.setKeywordsMap(keywordsMap);
	}

	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Locale defaultLocale) {
		_layout.setKeywordsMap(keywordsMap, defaultLocale);
	}

	/**
	* Gets the robots of this layout.
	*
	* @return the robots of this layout
	*/
	public java.lang.String getRobots() {
		return _layout.getRobots();
	}

	/**
	* Gets the localized robots of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized robots for
	* @return the localized robots of this layout
	*/
	public java.lang.String getRobots(java.util.Locale locale) {
		return _layout.getRobots(locale);
	}

	/**
	* Gets the localized robots of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized robots for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getRobots(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getRobots(locale, useDefault);
	}

	/**
	* Gets the localized robots of this layout. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized robots for
	* @return the localized robots of this layout
	*/
	public java.lang.String getRobots(java.lang.String languageId) {
		return _layout.getRobots(languageId);
	}

	/**
	* Gets the localized robots of this layout, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized robots for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout
	*/
	public java.lang.String getRobots(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getRobots(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized robots of this layout.
	*
	* @return the locales and localized robots
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getRobotsMap() {
		return _layout.getRobotsMap();
	}

	/**
	* Sets the robots of this layout.
	*
	* @param robots the robots of this layout
	*/
	public void setRobots(java.lang.String robots) {
		_layout.setRobots(robots);
	}

	/**
	* Sets the localized robots of this layout.
	*
	* @param robots the localized robots of this layout
	* @param locale the locale to set the localized robots for
	*/
	public void setRobots(java.lang.String robots, java.util.Locale locale) {
		_layout.setRobots(robots, locale);
	}

	public void setRobots(java.lang.String robots, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setRobots(robots, locale, defaultLocale);
	}

	/**
	* Sets the localized robotses of this layout from the map of locales and localized robotses.
	*
	* @param robotsMap the locales and localized robotses of this layout
	*/
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap) {
		_layout.setRobotsMap(robotsMap);
	}

	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.util.Locale defaultLocale) {
		_layout.setRobotsMap(robotsMap, defaultLocale);
	}

	/**
	* Gets the type of this layout.
	*
	* @return the type of this layout
	*/
	public java.lang.String getType() {
		return _layout.getType();
	}

	/**
	* Sets the type of this layout.
	*
	* @param type the type of this layout
	*/
	public void setType(java.lang.String type) {
		_layout.setType(type);
	}

	/**
	* Gets the type settings of this layout.
	*
	* @return the type settings of this layout
	*/
	public java.lang.String getTypeSettings() {
		return _layout.getTypeSettings();
	}

	/**
	* Sets the type settings of this layout.
	*
	* @param typeSettings the type settings of this layout
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_layout.setTypeSettings(typeSettings);
	}

	/**
	* Gets the hidden of this layout.
	*
	* @return the hidden of this layout
	*/
	public boolean getHidden() {
		return _layout.getHidden();
	}

	/**
	* Determines if this layout is hidden.
	*
	* @return <code>true</code> if this layout is hidden; <code>false</code> otherwise
	*/
	public boolean isHidden() {
		return _layout.isHidden();
	}

	/**
	* Sets whether this layout is hidden.
	*
	* @param hidden the hidden of this layout
	*/
	public void setHidden(boolean hidden) {
		_layout.setHidden(hidden);
	}

	/**
	* Gets the friendly u r l of this layout.
	*
	* @return the friendly u r l of this layout
	*/
	public java.lang.String getFriendlyURL() {
		return _layout.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this layout.
	*
	* @param friendlyURL the friendly u r l of this layout
	*/
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_layout.setFriendlyURL(friendlyURL);
	}

	/**
	* Gets the icon image of this layout.
	*
	* @return the icon image of this layout
	*/
	public boolean getIconImage() {
		return _layout.getIconImage();
	}

	/**
	* Determines if this layout is icon image.
	*
	* @return <code>true</code> if this layout is icon image; <code>false</code> otherwise
	*/
	public boolean isIconImage() {
		return _layout.isIconImage();
	}

	/**
	* Sets whether this layout is icon image.
	*
	* @param iconImage the icon image of this layout
	*/
	public void setIconImage(boolean iconImage) {
		_layout.setIconImage(iconImage);
	}

	/**
	* Gets the icon image ID of this layout.
	*
	* @return the icon image ID of this layout
	*/
	public long getIconImageId() {
		return _layout.getIconImageId();
	}

	/**
	* Sets the icon image ID of this layout.
	*
	* @param iconImageId the icon image ID of this layout
	*/
	public void setIconImageId(long iconImageId) {
		_layout.setIconImageId(iconImageId);
	}

	/**
	* Gets the theme ID of this layout.
	*
	* @return the theme ID of this layout
	*/
	public java.lang.String getThemeId() {
		return _layout.getThemeId();
	}

	/**
	* Sets the theme ID of this layout.
	*
	* @param themeId the theme ID of this layout
	*/
	public void setThemeId(java.lang.String themeId) {
		_layout.setThemeId(themeId);
	}

	/**
	* Gets the color scheme ID of this layout.
	*
	* @return the color scheme ID of this layout
	*/
	public java.lang.String getColorSchemeId() {
		return _layout.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout.
	*
	* @param colorSchemeId the color scheme ID of this layout
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layout.setColorSchemeId(colorSchemeId);
	}

	/**
	* Gets the wap theme ID of this layout.
	*
	* @return the wap theme ID of this layout
	*/
	public java.lang.String getWapThemeId() {
		return _layout.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout.
	*
	* @param wapThemeId the wap theme ID of this layout
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layout.setWapThemeId(wapThemeId);
	}

	/**
	* Gets the wap color scheme ID of this layout.
	*
	* @return the wap color scheme ID of this layout
	*/
	public java.lang.String getWapColorSchemeId() {
		return _layout.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layout.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Gets the css of this layout.
	*
	* @return the css of this layout
	*/
	public java.lang.String getCss() {
		return _layout.getCss();
	}

	/**
	* Sets the css of this layout.
	*
	* @param css the css of this layout
	*/
	public void setCss(java.lang.String css) {
		_layout.setCss(css);
	}

	/**
	* Gets the priority of this layout.
	*
	* @return the priority of this layout
	*/
	public int getPriority() {
		return _layout.getPriority();
	}

	/**
	* Sets the priority of this layout.
	*
	* @param priority the priority of this layout
	*/
	public void setPriority(int priority) {
		_layout.setPriority(priority);
	}

	/**
	* Gets the layout prototype ID of this layout.
	*
	* @return the layout prototype ID of this layout
	*/
	public long getLayoutPrototypeId() {
		return _layout.getLayoutPrototypeId();
	}

	/**
	* Sets the layout prototype ID of this layout.
	*
	* @param layoutPrototypeId the layout prototype ID of this layout
	*/
	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layout.setLayoutPrototypeId(layoutPrototypeId);
	}

	public boolean isNew() {
		return _layout.isNew();
	}

	public void setNew(boolean n) {
		_layout.setNew(n);
	}

	public boolean isCachedModel() {
		return _layout.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layout.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layout.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layout.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layout.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layout.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layout.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutWrapper((Layout)_layout.clone());
	}

	public int compareTo(com.liferay.portal.model.Layout layout) {
		return _layout.compareTo(layout);
	}

	public int hashCode() {
		return _layout.hashCode();
	}

	public com.liferay.portal.model.Layout toEscapedModel() {
		return new LayoutWrapper(_layout.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layout.toString();
	}

	public java.lang.String toXmlString() {
		return _layout.toXmlString();
	}

	public java.util.List<com.liferay.portal.model.Layout> getAllChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAllChildren();
	}

	public long getAncestorLayoutId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestorLayoutId();
	}

	public long getAncestorPlid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestorPlid();
	}

	public java.util.List<com.liferay.portal.model.Layout> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestors();
	}

	public java.util.List<com.liferay.portal.model.Layout> getChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getChildren();
	}

	public java.util.List<com.liferay.portal.model.Layout> getChildren(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getChildren(permissionChecker);
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getColorScheme();
	}

	public java.lang.String getCssText()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getCssText();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getGroup();
	}

	public java.lang.String getHTMLTitle(java.util.Locale locale) {
		return _layout.getHTMLTitle(locale);
	}

	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId) {
		return _layout.getHTMLTitle(localeLanguageId);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getLayoutSet();
	}

	public com.liferay.portal.model.LayoutType getLayoutType() {
		return _layout.getLayoutType();
	}

	public long getParentPlid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getParentPlid();
	}

	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getRegularURL(request);
	}

	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getResetLayoutURL(request);
	}

	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getResetMaxStateURL(request);
	}

	public com.liferay.portal.model.Group getScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getScopeGroup();
	}

	public java.lang.String getTarget() {
		return _layout.getTarget();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getTheme();
	}

	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device) {
		return _layout.getThemeSetting(key, device);
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getWapColorScheme();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getWapTheme();
	}

	public boolean hasAncestor(long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasAncestor(layoutId);
	}

	public boolean hasChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasChildren();
	}

	public boolean hasScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasScopeGroup();
	}

	public boolean isChildSelected(boolean selectable,
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.isChildSelected(selectable, layout);
	}

	public boolean isFirstChild() {
		return _layout.isFirstChild();
	}

	public boolean isFirstParent() {
		return _layout.isFirstParent();
	}

	public boolean isInheritLookAndFeel() {
		return _layout.isInheritLookAndFeel();
	}

	public boolean isInheritWapLookAndFeel() {
		return _layout.isInheritWapLookAndFeel();
	}

	public boolean isPublicLayout() {
		return _layout.isPublicLayout();
	}

	public boolean isRootLayout() {
		return _layout.isRootLayout();
	}

	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout, long ancestorPlid) {
		return _layout.isSelected(selectable, layout, ancestorPlid);
	}

	public boolean isTypeArticle() {
		return _layout.isTypeArticle();
	}

	public boolean isTypeControlPanel() {
		return _layout.isTypeControlPanel();
	}

	public boolean isTypeEmbedded() {
		return _layout.isTypeEmbedded();
	}

	public boolean isTypeLinkToLayout() {
		return _layout.isTypeLinkToLayout();
	}

	public boolean isTypePanel() {
		return _layout.isTypePanel();
	}

	public boolean isTypePortlet() {
		return _layout.isTypePortlet();
	}

	public boolean isTypeURL() {
		return _layout.isTypeURL();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_layout.setTypeSettingsProperties(typeSettingsProperties);
	}

	public Layout getWrappedLayout() {
		return _layout;
	}

	private Layout _layout;
}