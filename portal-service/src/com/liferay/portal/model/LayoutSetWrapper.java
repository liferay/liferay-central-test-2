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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSet
 * @generated
 */
public class LayoutSetWrapper implements LayoutSet {
	public LayoutSetWrapper(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	/**
	* Gets the primary key of this layout set.
	*
	* @return the primary key of this layout set
	*/
	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout set
	*
	* @param pk the primary key of this layout set
	*/
	public void setPrimaryKey(long pk) {
		_layoutSet.setPrimaryKey(pk);
	}

	/**
	* Gets the layout set id of this layout set.
	*
	* @return the layout set id of this layout set
	*/
	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	/**
	* Sets the layout set id of this layout set.
	*
	* @param layoutSetId the layout set id of this layout set
	*/
	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	/**
	* Gets the group id of this layout set.
	*
	* @return the group id of this layout set
	*/
	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	/**
	* Sets the group id of this layout set.
	*
	* @param groupId the group id of this layout set
	*/
	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	/**
	* Gets the company id of this layout set.
	*
	* @return the company id of this layout set
	*/
	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	/**
	* Sets the company id of this layout set.
	*
	* @param companyId the company id of this layout set
	*/
	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	/**
	* Gets the private layout of this layout set.
	*
	* @return the private layout of this layout set
	*/
	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	/**
	* Determines if this layout set is private layout.
	*
	* @return <code>true</code> if this layout set is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	/**
	* Sets whether this layout set is private layout.
	*
	* @param privateLayout the private layout of this layout set
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	/**
	* Gets the logo of this layout set.
	*
	* @return the logo of this layout set
	*/
	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	/**
	* Determines if this layout set is logo.
	*
	* @return <code>true</code> if this layout set is logo; <code>false</code> otherwise
	*/
	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	/**
	* Sets whether this layout set is logo.
	*
	* @param logo the logo of this layout set
	*/
	public void setLogo(boolean logo) {
		_layoutSet.setLogo(logo);
	}

	/**
	* Gets the logo id of this layout set.
	*
	* @return the logo id of this layout set
	*/
	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	/**
	* Sets the logo id of this layout set.
	*
	* @param logoId the logo id of this layout set
	*/
	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	/**
	* Gets the theme id of this layout set.
	*
	* @return the theme id of this layout set
	*/
	public java.lang.String getThemeId() {
		return _layoutSet.getThemeId();
	}

	/**
	* Sets the theme id of this layout set.
	*
	* @param themeId the theme id of this layout set
	*/
	public void setThemeId(java.lang.String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	/**
	* Gets the color scheme id of this layout set.
	*
	* @return the color scheme id of this layout set
	*/
	public java.lang.String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	/**
	* Sets the color scheme id of this layout set.
	*
	* @param colorSchemeId the color scheme id of this layout set
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	/**
	* Gets the wap theme id of this layout set.
	*
	* @return the wap theme id of this layout set
	*/
	public java.lang.String getWapThemeId() {
		return _layoutSet.getWapThemeId();
	}

	/**
	* Sets the wap theme id of this layout set.
	*
	* @param wapThemeId the wap theme id of this layout set
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutSet.setWapThemeId(wapThemeId);
	}

	/**
	* Gets the wap color scheme id of this layout set.
	*
	* @return the wap color scheme id of this layout set
	*/
	public java.lang.String getWapColorSchemeId() {
		return _layoutSet.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme id of this layout set.
	*
	* @param wapColorSchemeId the wap color scheme id of this layout set
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutSet.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Gets the css of this layout set.
	*
	* @return the css of this layout set
	*/
	public java.lang.String getCss() {
		return _layoutSet.getCss();
	}

	/**
	* Sets the css of this layout set.
	*
	* @param css the css of this layout set
	*/
	public void setCss(java.lang.String css) {
		_layoutSet.setCss(css);
	}

	/**
	* Gets the page count of this layout set.
	*
	* @return the page count of this layout set
	*/
	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	/**
	* Sets the page count of this layout set.
	*
	* @param pageCount the page count of this layout set
	*/
	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	/**
	* Gets the settings of this layout set.
	*
	* @return the settings of this layout set
	*/
	public java.lang.String getSettings() {
		return _layoutSet.getSettings();
	}

	/**
	* Sets the settings of this layout set.
	*
	* @param settings the settings of this layout set
	*/
	public void setSettings(java.lang.String settings) {
		_layoutSet.setSettings(settings);
	}

	/**
	* Gets the layout set prototype id of this layout set.
	*
	* @return the layout set prototype id of this layout set
	*/
	public long getLayoutSetPrototypeId() {
		return _layoutSet.getLayoutSetPrototypeId();
	}

	/**
	* Sets the layout set prototype id of this layout set.
	*
	* @param layoutSetPrototypeId the layout set prototype id of this layout set
	*/
	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSet.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	public boolean isNew() {
		return _layoutSet.isNew();
	}

	public void setNew(boolean n) {
		_layoutSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSet.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutSet.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSet.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutSetWrapper((LayoutSet)_layoutSet.clone());
	}

	public int compareTo(com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	public int hashCode() {
		return _layoutSet.hashCode();
	}

	public com.liferay.portal.model.LayoutSet toEscapedModel() {
		return new LayoutSetWrapper(_layoutSet.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layoutSet.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSet.toXmlString();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getTheme();
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getColorScheme();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getGroup();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _layoutSet.getSettingsProperties();
	}

	public java.lang.String getSettingsProperty(java.lang.String key) {
		return _layoutSet.getSettingsProperty(key);
	}

	public java.lang.String getVirtualHostname() {
		return _layoutSet.getVirtualHostname();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapTheme();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapColorScheme();
	}

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSet.setSettingsProperties(settingsProperties);
	}

	public LayoutSet getWrappedLayoutSet() {
		return _layoutSet;
	}

	private LayoutSet _layoutSet;
}