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
 * This class is a wrapper for {@link LayoutSetPrototype}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototype
 * @generated
 */
public class LayoutSetPrototypeWrapper implements LayoutSetPrototype {
	public LayoutSetPrototypeWrapper(LayoutSetPrototype layoutSetPrototype) {
		_layoutSetPrototype = layoutSetPrototype;
	}

	public long getPrimaryKey() {
		return _layoutSetPrototype.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_layoutSetPrototype.setPrimaryKey(pk);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototype.getLayoutSetPrototypeId();
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototype.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	public long getCompanyId() {
		return _layoutSetPrototype.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layoutSetPrototype.setCompanyId(companyId);
	}

	public java.lang.String getName() {
		return _layoutSetPrototype.getName();
	}

	public java.lang.String getName(java.util.Locale locale) {
		return _layoutSetPrototype.getName(locale);
	}

	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutSetPrototype.getName(locale, useDefault);
	}

	public java.lang.String getName(java.lang.String languageId) {
		return _layoutSetPrototype.getName(languageId);
	}

	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutSetPrototype.getName(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutSetPrototype.getNameMap();
	}

	public void setName(java.lang.String name) {
		_layoutSetPrototype.setName(name);
	}

	public void setName(java.util.Locale locale, java.lang.String name) {
		_layoutSetPrototype.setName(locale, name);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutSetPrototype.setNameMap(nameMap);
	}

	public java.lang.String getDescription() {
		return _layoutSetPrototype.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_layoutSetPrototype.setDescription(description);
	}

	public java.lang.String getSettings() {
		return _layoutSetPrototype.getSettings();
	}

	public void setSettings(java.lang.String settings) {
		_layoutSetPrototype.setSettings(settings);
	}

	public boolean getActive() {
		return _layoutSetPrototype.getActive();
	}

	public boolean isActive() {
		return _layoutSetPrototype.isActive();
	}

	public void setActive(boolean active) {
		_layoutSetPrototype.setActive(active);
	}

	public com.liferay.portal.model.LayoutSetPrototype toEscapedModel() {
		return _layoutSetPrototype.toEscapedModel();
	}

	public boolean isNew() {
		return _layoutSetPrototype.isNew();
	}

	public void setNew(boolean n) {
		_layoutSetPrototype.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSetPrototype.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSetPrototype.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSetPrototype.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutSetPrototype.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetPrototype.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetPrototype.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSetPrototype.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _layoutSetPrototype.clone();
	}

	public int compareTo(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		return _layoutSetPrototype.compareTo(layoutSetPrototype);
	}

	public int hashCode() {
		return _layoutSetPrototype.hashCode();
	}

	public java.lang.String toString() {
		return _layoutSetPrototype.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSetPrototype.toXmlString();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototype.getGroup();
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototype.getLayoutSet();
	}

	public LayoutSetPrototype getWrappedLayoutSetPrototype() {
		return _layoutSetPrototype;
	}

	private LayoutSetPrototype _layoutSetPrototype;
}