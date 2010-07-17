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

	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_layoutSet.setPrimaryKey(pk);
	}

	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	public void setLogo(boolean logo) {
		_layoutSet.setLogo(logo);
	}

	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	public java.lang.String getThemeId() {
		return _layoutSet.getThemeId();
	}

	public void setThemeId(java.lang.String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	public java.lang.String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	public java.lang.String getWapThemeId() {
		return _layoutSet.getWapThemeId();
	}

	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutSet.setWapThemeId(wapThemeId);
	}

	public java.lang.String getWapColorSchemeId() {
		return _layoutSet.getWapColorSchemeId();
	}

	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutSet.setWapColorSchemeId(wapColorSchemeId);
	}

	public java.lang.String getCss() {
		return _layoutSet.getCss();
	}

	public void setCss(java.lang.String css) {
		_layoutSet.setCss(css);
	}

	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	public java.lang.String getVirtualHost() {
		return _layoutSet.getVirtualHost();
	}

	public void setVirtualHost(java.lang.String virtualHost) {
		_layoutSet.setVirtualHost(virtualHost);
	}

	public java.lang.String getSettings() {
		return _layoutSet.getSettings();
	}

	public void setSettings(java.lang.String settings) {
		_layoutSet.setSettings(settings);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSet.getLayoutSetPrototypeId();
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSet.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	public com.liferay.portal.model.LayoutSet toEscapedModel() {
		return _layoutSet.toEscapedModel();
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
		return _layoutSet.clone();
	}

	public int compareTo(com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	public int hashCode() {
		return _layoutSet.hashCode();
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