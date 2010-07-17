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

package com.liferay.portlet.expando.model;

/**
 * <p>
 * This class is a wrapper for {@link ExpandoColumn}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumn
 * @generated
 */
public class ExpandoColumnWrapper implements ExpandoColumn {
	public ExpandoColumnWrapper(ExpandoColumn expandoColumn) {
		_expandoColumn = expandoColumn;
	}

	public long getPrimaryKey() {
		return _expandoColumn.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoColumn.setPrimaryKey(pk);
	}

	public long getColumnId() {
		return _expandoColumn.getColumnId();
	}

	public void setColumnId(long columnId) {
		_expandoColumn.setColumnId(columnId);
	}

	public long getCompanyId() {
		return _expandoColumn.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoColumn.setCompanyId(companyId);
	}

	public long getTableId() {
		return _expandoColumn.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoColumn.setTableId(tableId);
	}

	public java.lang.String getName() {
		return _expandoColumn.getName();
	}

	public void setName(java.lang.String name) {
		_expandoColumn.setName(name);
	}

	public int getType() {
		return _expandoColumn.getType();
	}

	public void setType(int type) {
		_expandoColumn.setType(type);
	}

	public java.lang.String getDefaultData() {
		return _expandoColumn.getDefaultData();
	}

	public void setDefaultData(java.lang.String defaultData) {
		_expandoColumn.setDefaultData(defaultData);
	}

	public java.lang.String getTypeSettings() {
		return _expandoColumn.getTypeSettings();
	}

	public void setTypeSettings(java.lang.String typeSettings) {
		_expandoColumn.setTypeSettings(typeSettings);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn toEscapedModel() {
		return _expandoColumn.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoColumn.isNew();
	}

	public void setNew(boolean n) {
		_expandoColumn.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoColumn.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoColumn.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoColumn.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoColumn.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoColumn.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoColumn.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoColumn.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoColumn.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumn.compareTo(expandoColumn);
	}

	public int hashCode() {
		return _expandoColumn.hashCode();
	}

	public java.lang.String toString() {
		return _expandoColumn.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoColumn.toXmlString();
	}

	public java.io.Serializable getDefaultValue() {
		return _expandoColumn.getDefaultValue();
	}

	public java.lang.String getDisplayName(java.util.Locale locale) {
		return _expandoColumn.getDisplayName(locale);
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _expandoColumn.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_expandoColumn.setTypeSettingsProperties(typeSettingsProperties);
	}

	public ExpandoColumn getWrappedExpandoColumn() {
		return _expandoColumn;
	}

	private ExpandoColumn _expandoColumn;
}