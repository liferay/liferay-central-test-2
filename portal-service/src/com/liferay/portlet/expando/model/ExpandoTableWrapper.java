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
 * This class is a wrapper for {@link ExpandoTable}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTable
 * @generated
 */
public class ExpandoTableWrapper implements ExpandoTable {
	public ExpandoTableWrapper(ExpandoTable expandoTable) {
		_expandoTable = expandoTable;
	}

	public long getPrimaryKey() {
		return _expandoTable.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoTable.setPrimaryKey(pk);
	}

	public long getTableId() {
		return _expandoTable.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoTable.setTableId(tableId);
	}

	public long getCompanyId() {
		return _expandoTable.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoTable.setCompanyId(companyId);
	}

	public java.lang.String getClassName() {
		return _expandoTable.getClassName();
	}

	public long getClassNameId() {
		return _expandoTable.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_expandoTable.setClassNameId(classNameId);
	}

	public java.lang.String getName() {
		return _expandoTable.getName();
	}

	public void setName(java.lang.String name) {
		_expandoTable.setName(name);
	}

	public com.liferay.portlet.expando.model.ExpandoTable toEscapedModel() {
		return _expandoTable.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoTable.isNew();
	}

	public void setNew(boolean n) {
		_expandoTable.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoTable.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoTable.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoTable.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoTable.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoTable.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoTable.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoTable.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoTable.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTable.compareTo(expandoTable);
	}

	public int hashCode() {
		return _expandoTable.hashCode();
	}

	public java.lang.String toString() {
		return _expandoTable.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoTable.toXmlString();
	}

	public boolean isDefaultTable() {
		return _expandoTable.isDefaultTable();
	}

	public ExpandoTable getWrappedExpandoTable() {
		return _expandoTable;
	}

	private ExpandoTable _expandoTable;
}