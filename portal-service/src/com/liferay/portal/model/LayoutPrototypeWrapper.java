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

	public long getPrimaryKey() {
		return _layoutPrototype.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_layoutPrototype.setPrimaryKey(pk);
	}

	public long getLayoutPrototypeId() {
		return _layoutPrototype.getLayoutPrototypeId();
	}

	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPrototype.setLayoutPrototypeId(layoutPrototypeId);
	}

	public long getCompanyId() {
		return _layoutPrototype.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layoutPrototype.setCompanyId(companyId);
	}

	public java.lang.String getName() {
		return _layoutPrototype.getName();
	}

	public java.lang.String getName(java.util.Locale locale) {
		return _layoutPrototype.getName(locale);
	}

	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutPrototype.getName(locale, useDefault);
	}

	public java.lang.String getName(java.lang.String languageId) {
		return _layoutPrototype.getName(languageId);
	}

	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutPrototype.getName(languageId, useDefault);
	}

	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutPrototype.getNameMap();
	}

	public void setName(java.lang.String name) {
		_layoutPrototype.setName(name);
	}

	public void setName(java.util.Locale locale, java.lang.String name) {
		_layoutPrototype.setName(locale, name);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutPrototype.setNameMap(nameMap);
	}

	public java.lang.String getDescription() {
		return _layoutPrototype.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_layoutPrototype.setDescription(description);
	}

	public java.lang.String getSettings() {
		return _layoutPrototype.getSettings();
	}

	public void setSettings(java.lang.String settings) {
		_layoutPrototype.setSettings(settings);
	}

	public boolean getActive() {
		return _layoutPrototype.getActive();
	}

	public boolean isActive() {
		return _layoutPrototype.isActive();
	}

	public void setActive(boolean active) {
		_layoutPrototype.setActive(active);
	}

	public com.liferay.portal.model.LayoutPrototype toEscapedModel() {
		return _layoutPrototype.toEscapedModel();
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
		return _layoutPrototype.clone();
	}

	public int compareTo(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		return _layoutPrototype.compareTo(layoutPrototype);
	}

	public int hashCode() {
		return _layoutPrototype.hashCode();
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