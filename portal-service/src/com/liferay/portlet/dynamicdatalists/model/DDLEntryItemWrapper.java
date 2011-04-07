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

package com.liferay.portlet.dynamicdatalists.model;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntryItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntryItem
 * @generated
 */
public class DDLEntryItemWrapper implements DDLEntryItem {
	public DDLEntryItemWrapper(DDLEntryItem ddlEntryItem) {
		_ddlEntryItem = ddlEntryItem;
	}

	public Class<?> getModelClass() {
		return DDLEntryItem.class;
	}

	public String getModelClassName() {
		return DDLEntryItem.class.getName();
	}

	/**
	* Gets the primary key of this d d l entry item.
	*
	* @return the primary key of this d d l entry item
	*/
	public long getPrimaryKey() {
		return _ddlEntryItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l entry item
	*
	* @param pk the primary key of this d d l entry item
	*/
	public void setPrimaryKey(long pk) {
		_ddlEntryItem.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d l entry item.
	*
	* @return the uuid of this d d l entry item
	*/
	public java.lang.String getUuid() {
		return _ddlEntryItem.getUuid();
	}

	/**
	* Sets the uuid of this d d l entry item.
	*
	* @param uuid the uuid of this d d l entry item
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlEntryItem.setUuid(uuid);
	}

	/**
	* Gets the entry item ID of this d d l entry item.
	*
	* @return the entry item ID of this d d l entry item
	*/
	public long getEntryItemId() {
		return _ddlEntryItem.getEntryItemId();
	}

	/**
	* Sets the entry item ID of this d d l entry item.
	*
	* @param entryItemId the entry item ID of this d d l entry item
	*/
	public void setEntryItemId(long entryItemId) {
		_ddlEntryItem.setEntryItemId(entryItemId);
	}

	/**
	* Gets the class name of the model instance this d d l entry item is polymorphically associated with.
	*
	* @return the class name of the model instance this d d l entry item is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ddlEntryItem.getClassName();
	}

	/**
	* Gets the class name ID of this d d l entry item.
	*
	* @return the class name ID of this d d l entry item
	*/
	public long getClassNameId() {
		return _ddlEntryItem.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d l entry item.
	*
	* @param classNameId the class name ID of this d d l entry item
	*/
	public void setClassNameId(long classNameId) {
		_ddlEntryItem.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this d d l entry item.
	*
	* @return the class p k of this d d l entry item
	*/
	public long getClassPK() {
		return _ddlEntryItem.getClassPK();
	}

	/**
	* Sets the class p k of this d d l entry item.
	*
	* @param classPK the class p k of this d d l entry item
	*/
	public void setClassPK(long classPK) {
		_ddlEntryItem.setClassPK(classPK);
	}

	/**
	* Gets the entry ID of this d d l entry item.
	*
	* @return the entry ID of this d d l entry item
	*/
	public long getEntryId() {
		return _ddlEntryItem.getEntryId();
	}

	/**
	* Sets the entry ID of this d d l entry item.
	*
	* @param entryId the entry ID of this d d l entry item
	*/
	public void setEntryId(long entryId) {
		_ddlEntryItem.setEntryId(entryId);
	}

	public boolean isNew() {
		return _ddlEntryItem.isNew();
	}

	public void setNew(boolean n) {
		_ddlEntryItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlEntryItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlEntryItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlEntryItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddlEntryItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlEntryItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlEntryItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlEntryItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDLEntryItemWrapper((DDLEntryItem)_ddlEntryItem.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem) {
		return _ddlEntryItem.compareTo(ddlEntryItem);
	}

	public int hashCode() {
		return _ddlEntryItem.hashCode();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem toEscapedModel() {
		return new DDLEntryItemWrapper(_ddlEntryItem.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddlEntryItem.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlEntryItem.toXmlString();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItem.getEntry();
	}

	public DDLEntryItem getWrappedDDLEntryItem() {
		return _ddlEntryItem;
	}

	public void resetOriginalValues() {
		_ddlEntryItem.resetOriginalValues();
	}

	private DDLEntryItem _ddlEntryItem;
}