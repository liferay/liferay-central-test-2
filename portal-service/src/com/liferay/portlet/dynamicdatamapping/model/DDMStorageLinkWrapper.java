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

package com.liferay.portlet.dynamicdatamapping.model;

/**
 * <p>
 * This class is a wrapper for {@link DDMStorageLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStorageLink
 * @generated
 */
public class DDMStorageLinkWrapper implements DDMStorageLink {
	public DDMStorageLinkWrapper(DDMStorageLink ddmStorageLink) {
		_ddmStorageLink = ddmStorageLink;
	}

	/**
	* Gets the primary key of this d d m storage link.
	*
	* @return the primary key of this d d m storage link
	*/
	public long getPrimaryKey() {
		return _ddmStorageLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m storage link
	*
	* @param pk the primary key of this d d m storage link
	*/
	public void setPrimaryKey(long pk) {
		_ddmStorageLink.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m storage link.
	*
	* @return the uuid of this d d m storage link
	*/
	public java.lang.String getUuid() {
		return _ddmStorageLink.getUuid();
	}

	/**
	* Sets the uuid of this d d m storage link.
	*
	* @param uuid the uuid of this d d m storage link
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmStorageLink.setUuid(uuid);
	}

	/**
	* Gets the storage link ID of this d d m storage link.
	*
	* @return the storage link ID of this d d m storage link
	*/
	public long getStorageLinkId() {
		return _ddmStorageLink.getStorageLinkId();
	}

	/**
	* Sets the storage link ID of this d d m storage link.
	*
	* @param storageLinkId the storage link ID of this d d m storage link
	*/
	public void setStorageLinkId(long storageLinkId) {
		_ddmStorageLink.setStorageLinkId(storageLinkId);
	}

	/**
	* Gets the class name of the model instance this d d m storage link is polymorphically associated with.
	*
	* @return the class name of the model instance this d d m storage link is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ddmStorageLink.getClassName();
	}

	/**
	* Gets the class name ID of this d d m storage link.
	*
	* @return the class name ID of this d d m storage link
	*/
	public long getClassNameId() {
		return _ddmStorageLink.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d m storage link.
	*
	* @param classNameId the class name ID of this d d m storage link
	*/
	public void setClassNameId(long classNameId) {
		_ddmStorageLink.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this d d m storage link.
	*
	* @return the class p k of this d d m storage link
	*/
	public long getClassPK() {
		return _ddmStorageLink.getClassPK();
	}

	/**
	* Sets the class p k of this d d m storage link.
	*
	* @param classPK the class p k of this d d m storage link
	*/
	public void setClassPK(long classPK) {
		_ddmStorageLink.setClassPK(classPK);
	}

	/**
	* Gets the type of this d d m storage link.
	*
	* @return the type of this d d m storage link
	*/
	public java.lang.String getType() {
		return _ddmStorageLink.getType();
	}

	/**
	* Sets the type of this d d m storage link.
	*
	* @param type the type of this d d m storage link
	*/
	public void setType(java.lang.String type) {
		_ddmStorageLink.setType(type);
	}

	public boolean isNew() {
		return _ddmStorageLink.isNew();
	}

	public void setNew(boolean n) {
		_ddmStorageLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmStorageLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmStorageLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmStorageLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmStorageLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStorageLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStorageLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStorageLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMStorageLinkWrapper((DDMStorageLink)_ddmStorageLink.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink ddmStorageLink) {
		return _ddmStorageLink.compareTo(ddmStorageLink);
	}

	public int hashCode() {
		return _ddmStorageLink.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink toEscapedModel() {
		return new DDMStorageLinkWrapper(_ddmStorageLink.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmStorageLink.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmStorageLink.toXmlString();
	}

	public DDMStorageLink getWrappedDDMStorageLink() {
		return _ddmStorageLink;
	}

	private DDMStorageLink _ddmStorageLink;
}