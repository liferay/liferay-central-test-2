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
 * This class is a wrapper for {@link DDMStructureEntryLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureEntryLink
 * @generated
 */
public class DDMStructureEntryLinkWrapper implements DDMStructureEntryLink {
	public DDMStructureEntryLinkWrapper(
		DDMStructureEntryLink ddmStructureEntryLink) {
		_ddmStructureEntryLink = ddmStructureEntryLink;
	}

	/**
	* Gets the primary key of this d d m structure entry link.
	*
	* @return the primary key of this d d m structure entry link
	*/
	public long getPrimaryKey() {
		return _ddmStructureEntryLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m structure entry link
	*
	* @param pk the primary key of this d d m structure entry link
	*/
	public void setPrimaryKey(long pk) {
		_ddmStructureEntryLink.setPrimaryKey(pk);
	}

	/**
	* Gets the structure entry link ID of this d d m structure entry link.
	*
	* @return the structure entry link ID of this d d m structure entry link
	*/
	public long getStructureEntryLinkId() {
		return _ddmStructureEntryLink.getStructureEntryLinkId();
	}

	/**
	* Sets the structure entry link ID of this d d m structure entry link.
	*
	* @param structureEntryLinkId the structure entry link ID of this d d m structure entry link
	*/
	public void setStructureEntryLinkId(long structureEntryLinkId) {
		_ddmStructureEntryLink.setStructureEntryLinkId(structureEntryLinkId);
	}

	/**
	* Gets the structure ID of this d d m structure entry link.
	*
	* @return the structure ID of this d d m structure entry link
	*/
	public java.lang.String getStructureId() {
		return _ddmStructureEntryLink.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m structure entry link.
	*
	* @param structureId the structure ID of this d d m structure entry link
	*/
	public void setStructureId(java.lang.String structureId) {
		_ddmStructureEntryLink.setStructureId(structureId);
	}

	/**
	* Gets the class name of this d d m structure entry link.
	*
	* @return the class name of this d d m structure entry link
	*/
	public java.lang.String getClassName() {
		return _ddmStructureEntryLink.getClassName();
	}

	/**
	* Sets the class name of this d d m structure entry link.
	*
	* @param className the class name of this d d m structure entry link
	*/
	public void setClassName(java.lang.String className) {
		_ddmStructureEntryLink.setClassName(className);
	}

	/**
	* Gets the class p k of this d d m structure entry link.
	*
	* @return the class p k of this d d m structure entry link
	*/
	public long getClassPK() {
		return _ddmStructureEntryLink.getClassPK();
	}

	/**
	* Sets the class p k of this d d m structure entry link.
	*
	* @param classPK the class p k of this d d m structure entry link
	*/
	public void setClassPK(long classPK) {
		_ddmStructureEntryLink.setClassPK(classPK);
	}

	public boolean isNew() {
		return _ddmStructureEntryLink.isNew();
	}

	public void setNew(boolean n) {
		_ddmStructureEntryLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmStructureEntryLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmStructureEntryLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmStructureEntryLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmStructureEntryLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructureEntryLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructureEntryLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructureEntryLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMStructureEntryLinkWrapper((DDMStructureEntryLink)_ddmStructureEntryLink.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink) {
		return _ddmStructureEntryLink.compareTo(ddmStructureEntryLink);
	}

	public int hashCode() {
		return _ddmStructureEntryLink.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink toEscapedModel() {
		return new DDMStructureEntryLinkWrapper(_ddmStructureEntryLink.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmStructureEntryLink.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmStructureEntryLink.toXmlString();
	}

	public DDMStructureEntryLink getWrappedDDMStructureEntryLink() {
		return _ddmStructureEntryLink;
	}

	private DDMStructureEntryLink _ddmStructureEntryLink;
}