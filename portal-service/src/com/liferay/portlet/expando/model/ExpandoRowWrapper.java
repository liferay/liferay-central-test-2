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
 * This class is a wrapper for {@link ExpandoRow}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRow
 * @generated
 */
public class ExpandoRowWrapper implements ExpandoRow {
	public ExpandoRowWrapper(ExpandoRow expandoRow) {
		_expandoRow = expandoRow;
	}

	public long getPrimaryKey() {
		return _expandoRow.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoRow.setPrimaryKey(pk);
	}

	public long getRowId() {
		return _expandoRow.getRowId();
	}

	public void setRowId(long rowId) {
		_expandoRow.setRowId(rowId);
	}

	public long getCompanyId() {
		return _expandoRow.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoRow.setCompanyId(companyId);
	}

	public long getTableId() {
		return _expandoRow.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoRow.setTableId(tableId);
	}

	public long getClassPK() {
		return _expandoRow.getClassPK();
	}

	public void setClassPK(long classPK) {
		_expandoRow.setClassPK(classPK);
	}

	public com.liferay.portlet.expando.model.ExpandoRow toEscapedModel() {
		return _expandoRow.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoRow.isNew();
	}

	public void setNew(boolean n) {
		_expandoRow.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoRow.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoRow.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoRow.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoRow.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoRow.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoRow.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoRow.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoRow.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRow.compareTo(expandoRow);
	}

	public int hashCode() {
		return _expandoRow.hashCode();
	}

	public java.lang.String toString() {
		return _expandoRow.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoRow.toXmlString();
	}

	public ExpandoRow getWrappedExpandoRow() {
		return _expandoRow;
	}

	private ExpandoRow _expandoRow;
}