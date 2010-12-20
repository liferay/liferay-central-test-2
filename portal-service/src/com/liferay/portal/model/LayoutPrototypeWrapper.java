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
 * This class is a wrapper for {@link LayoutPrototype}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPrototype
 * @generated
 */
public class LayoutPrototypeWrapper implements LayoutPrototype {
	public LayoutPrototypeWrapper(LayoutPrototype layoutPrototype) {
		_layoutPrototype = layoutPrototype;
	}

	/**
	* Gets the primary key of this layout prototype.
	*
	* @return the primary key of this layout prototype
	*/
	public long getPrimaryKey() {
		return _layoutPrototype.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout prototype
	*
	* @param pk the primary key of this layout prototype
	*/
	public void setPrimaryKey(long pk) {
		_layoutPrototype.setPrimaryKey(pk);
	}

	/**
	* Gets the layout prototype ID of this layout prototype.
	*
	* @return the layout prototype ID of this layout prototype
	*/
	public long getLayoutPrototypeId() {
		return _layoutPrototype.getLayoutPrototypeId();
	}

	/**
	* Sets the layout prototype ID of this layout prototype.
	*
	* @param layoutPrototypeId the layout prototype ID of this layout prototype
	*/
	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPrototype.setLayoutPrototypeId(layoutPrototypeId);
	}

	/**
	* Gets the company ID of this layout prototype.
	*
	* @return the company ID of this layout prototype
	*/
	public long getCompanyId() {
		return _layoutPrototype.getCompanyId();
	}

	/**
	* Sets the company ID of this layout prototype.
	*
	* @param companyId the company ID of this layout prototype
	*/
	public void setCompanyId(long companyId) {
		_layoutPrototype.setCompanyId(companyId);
	}

	/**
	* Gets the name of this layout prototype.
	*
	* @return the name of this layout prototype
	*/
	public java.lang.String getName() {
		return _layoutPrototype.getName();
	}

	/**
	* Gets the localized name of this layout prototype. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this layout prototype
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _layoutPrototype.getName(locale);
	}

	/**
	* Gets the localized name of this layout prototype, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutPrototype.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this layout prototype. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this layout prototype
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _layoutPrototype.getName(languageId);
	}

	/**
	* Gets the localized name of this layout prototype, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout prototype
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutPrototype.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this layout prototype.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutPrototype.getNameMap();
	}

	/**
	* Sets the name of this layout prototype.
	*
	* @param name the name of this layout prototype
	*/
	public void setName(java.lang.String name) {
		_layoutPrototype.setName(name);
	}

	/**
	* Sets the localized name of this layout prototype.
	*
	* @param locale the locale to set the localized name for
	* @param name the localized name of this layout prototype
	*/
	public void setName(java.util.Locale locale, java.lang.String name) {
		_layoutPrototype.setName(locale, name);
	}

	/**
	* Sets the localized names of this layout prototype from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout prototype
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutPrototype.setNameMap(nameMap);
	}

	/**
	* Gets the description of this layout prototype.
	*
	* @return the description of this layout prototype
	*/
	public java.lang.String getDescription() {
		return _layoutPrototype.getDescription();
	}

	/**
	* Sets the description of this layout prototype.
	*
	* @param description the description of this layout prototype
	*/
	public void setDescription(java.lang.String description) {
		_layoutPrototype.setDescription(description);
	}

	/**
	* Gets the settings of this layout prototype.
	*
	* @return the settings of this layout prototype
	*/
	public java.lang.String getSettings() {
		return _layoutPrototype.getSettings();
	}

	/**
	* Sets the settings of this layout prototype.
	*
	* @param settings the settings of this layout prototype
	*/
	public void setSettings(java.lang.String settings) {
		_layoutPrototype.setSettings(settings);
	}

	/**
	* Gets the active of this layout prototype.
	*
	* @return the active of this layout prototype
	*/
	public boolean getActive() {
		return _layoutPrototype.getActive();
	}

	/**
	* Determines if this layout prototype is active.
	*
	* @return <code>true</code> if this layout prototype is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _layoutPrototype.isActive();
	}

	/**
	* Sets whether this layout prototype is active.
	*
	* @param active the active of this layout prototype
	*/
	public void setActive(boolean active) {
		_layoutPrototype.setActive(active);
	}

	public boolean isNew() {
		return _layoutPrototype.isNew();
	}

	public void setNew(boolean n) {
		_layoutPrototype.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutPrototype.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutPrototype.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutPrototype.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutPrototype.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutPrototype.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutPrototype.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutPrototype.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new LayoutPrototypeWrapper((LayoutPrototype)_layoutPrototype.clone());
	}

	public int compareTo(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		return _layoutPrototype.compareTo(layoutPrototype);
	}

	public int hashCode() {
		return _layoutPrototype.hashCode();
	}

	public com.liferay.portal.model.LayoutPrototype toEscapedModel() {
		return new LayoutPrototypeWrapper(_layoutPrototype.toEscapedModel());
	}

	public java.lang.String toString() {
		return _layoutPrototype.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutPrototype.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getGroup();
	}

	public com.liferay.portal.model.Layout getLayout()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototype.getLayout();
	}

	public LayoutPrototype getWrappedLayoutPrototype() {
		return _layoutPrototype;
	}

	private LayoutPrototype _layoutPrototype;
}