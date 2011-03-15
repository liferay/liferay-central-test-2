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
 * This class is a wrapper for {@link DDMStructureEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureEntry
 * @generated
 */
public class DDMStructureEntryWrapper implements DDMStructureEntry {
	public DDMStructureEntryWrapper(DDMStructureEntry ddmStructureEntry) {
		_ddmStructureEntry = ddmStructureEntry;
	}

	/**
	* Gets the primary key of this d d m structure entry.
	*
	* @return the primary key of this d d m structure entry
	*/
	public long getPrimaryKey() {
		return _ddmStructureEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m structure entry
	*
	* @param pk the primary key of this d d m structure entry
	*/
	public void setPrimaryKey(long pk) {
		_ddmStructureEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m structure entry.
	*
	* @return the uuid of this d d m structure entry
	*/
	public java.lang.String getUuid() {
		return _ddmStructureEntry.getUuid();
	}

	/**
	* Sets the uuid of this d d m structure entry.
	*
	* @param uuid the uuid of this d d m structure entry
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmStructureEntry.setUuid(uuid);
	}

	/**
	* Gets the structure entry ID of this d d m structure entry.
	*
	* @return the structure entry ID of this d d m structure entry
	*/
	public long getStructureEntryId() {
		return _ddmStructureEntry.getStructureEntryId();
	}

	/**
	* Sets the structure entry ID of this d d m structure entry.
	*
	* @param structureEntryId the structure entry ID of this d d m structure entry
	*/
	public void setStructureEntryId(long structureEntryId) {
		_ddmStructureEntry.setStructureEntryId(structureEntryId);
	}

	/**
	* Gets the group ID of this d d m structure entry.
	*
	* @return the group ID of this d d m structure entry
	*/
	public long getGroupId() {
		return _ddmStructureEntry.getGroupId();
	}

	/**
	* Sets the group ID of this d d m structure entry.
	*
	* @param groupId the group ID of this d d m structure entry
	*/
	public void setGroupId(long groupId) {
		_ddmStructureEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d m structure entry.
	*
	* @return the company ID of this d d m structure entry
	*/
	public long getCompanyId() {
		return _ddmStructureEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m structure entry.
	*
	* @param companyId the company ID of this d d m structure entry
	*/
	public void setCompanyId(long companyId) {
		_ddmStructureEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d m structure entry.
	*
	* @return the user ID of this d d m structure entry
	*/
	public long getUserId() {
		return _ddmStructureEntry.getUserId();
	}

	/**
	* Sets the user ID of this d d m structure entry.
	*
	* @param userId the user ID of this d d m structure entry
	*/
	public void setUserId(long userId) {
		_ddmStructureEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d m structure entry.
	*
	* @return the user uuid of this d d m structure entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m structure entry.
	*
	* @param userUuid the user uuid of this d d m structure entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmStructureEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d m structure entry.
	*
	* @return the user name of this d d m structure entry
	*/
	public java.lang.String getUserName() {
		return _ddmStructureEntry.getUserName();
	}

	/**
	* Sets the user name of this d d m structure entry.
	*
	* @param userName the user name of this d d m structure entry
	*/
	public void setUserName(java.lang.String userName) {
		_ddmStructureEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this d d m structure entry.
	*
	* @return the create date of this d d m structure entry
	*/
	public java.util.Date getCreateDate() {
		return _ddmStructureEntry.getCreateDate();
	}

	/**
	* Sets the create date of this d d m structure entry.
	*
	* @param createDate the create date of this d d m structure entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmStructureEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d m structure entry.
	*
	* @return the modified date of this d d m structure entry
	*/
	public java.util.Date getModifiedDate() {
		return _ddmStructureEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m structure entry.
	*
	* @param modifiedDate the modified date of this d d m structure entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmStructureEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the structure ID of this d d m structure entry.
	*
	* @return the structure ID of this d d m structure entry
	*/
	public java.lang.String getStructureId() {
		return _ddmStructureEntry.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m structure entry.
	*
	* @param structureId the structure ID of this d d m structure entry
	*/
	public void setStructureId(java.lang.String structureId) {
		_ddmStructureEntry.setStructureId(structureId);
	}

	/**
	* Gets the name of this d d m structure entry.
	*
	* @return the name of this d d m structure entry
	*/
	public java.lang.String getName() {
		return _ddmStructureEntry.getName();
	}

	/**
	* Sets the name of this d d m structure entry.
	*
	* @param name the name of this d d m structure entry
	*/
	public void setName(java.lang.String name) {
		_ddmStructureEntry.setName(name);
	}

	/**
	* Gets the description of this d d m structure entry.
	*
	* @return the description of this d d m structure entry
	*/
	public java.lang.String getDescription() {
		return _ddmStructureEntry.getDescription();
	}

	/**
	* Sets the description of this d d m structure entry.
	*
	* @param description the description of this d d m structure entry
	*/
	public void setDescription(java.lang.String description) {
		_ddmStructureEntry.setDescription(description);
	}

	/**
	* Gets the xsd of this d d m structure entry.
	*
	* @return the xsd of this d d m structure entry
	*/
	public java.lang.String getXsd() {
		return _ddmStructureEntry.getXsd();
	}

	/**
	* Sets the xsd of this d d m structure entry.
	*
	* @param xsd the xsd of this d d m structure entry
	*/
	public void setXsd(java.lang.String xsd) {
		_ddmStructureEntry.setXsd(xsd);
	}

	public boolean isNew() {
		return _ddmStructureEntry.isNew();
	}

	public void setNew(boolean n) {
		_ddmStructureEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmStructureEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmStructureEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmStructureEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmStructureEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructureEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructureEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructureEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMStructureEntryWrapper((DDMStructureEntry)_ddmStructureEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry) {
		return _ddmStructureEntry.compareTo(ddmStructureEntry);
	}

	public int hashCode() {
		return _ddmStructureEntry.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry toEscapedModel() {
		return new DDMStructureEntryWrapper(_ddmStructureEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmStructureEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmStructureEntry.toXmlString();
	}

	public DDMStructureEntry getWrappedDDMStructureEntry() {
		return _ddmStructureEntry;
	}

	private DDMStructureEntry _ddmStructureEntry;
}