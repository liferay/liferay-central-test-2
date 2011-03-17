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

	public int compareTo(DDMStorageLink ddmStorageLink) {
		return _ddmStorageLink.compareTo(ddmStorageLink);
	}

	public int hashCode() {
		return _ddmStorageLink.hashCode();
	}

	public DDMStorageLink toEscapedModel() {
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