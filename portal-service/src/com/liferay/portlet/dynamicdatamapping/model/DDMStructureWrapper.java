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
 * This class is a wrapper for {@link DDMStructure}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructure
 * @generated
 */
public class DDMStructureWrapper implements DDMStructure {
	public DDMStructureWrapper(DDMStructure ddmStructure) {
		_ddmStructure = ddmStructure;
	}

	public Class<?> getModelClass() {
		return DDMStructure.class;
	}

	public String getModelClassName() {
		return DDMStructure.class.getName();
	}

	/**
	* Gets the primary key of this d d m structure.
	*
	* @return the primary key of this d d m structure
	*/
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m structure
	*
	* @param pk the primary key of this d d m structure
	*/
	public void setPrimaryKey(long pk) {
		_ddmStructure.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m structure.
	*
	* @return the uuid of this d d m structure
	*/
	public java.lang.String getUuid() {
		return _ddmStructure.getUuid();
	}

	/**
	* Sets the uuid of this d d m structure.
	*
	* @param uuid the uuid of this d d m structure
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	/**
	* Gets the structure ID of this d d m structure.
	*
	* @return the structure ID of this d d m structure
	*/
	public long getStructureId() {
		return _ddmStructure.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m structure.
	*
	* @param structureId the structure ID of this d d m structure
	*/
	public void setStructureId(long structureId) {
		_ddmStructure.setStructureId(structureId);
	}

	/**
	* Gets the group ID of this d d m structure.
	*
	* @return the group ID of this d d m structure
	*/
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	/**
	* Sets the group ID of this d d m structure.
	*
	* @param groupId the group ID of this d d m structure
	*/
	public void setGroupId(long groupId) {
		_ddmStructure.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d m structure.
	*
	* @return the company ID of this d d m structure
	*/
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m structure.
	*
	* @param companyId the company ID of this d d m structure
	*/
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d m structure.
	*
	* @return the user ID of this d d m structure
	*/
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	/**
	* Sets the user ID of this d d m structure.
	*
	* @param userId the user ID of this d d m structure
	*/
	public void setUserId(long userId) {
		_ddmStructure.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d m structure.
	*
	* @return the user uuid of this d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m structure.
	*
	* @param userUuid the user uuid of this d d m structure
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmStructure.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d m structure.
	*
	* @return the user name of this d d m structure
	*/
	public java.lang.String getUserName() {
		return _ddmStructure.getUserName();
	}

	/**
	* Sets the user name of this d d m structure.
	*
	* @param userName the user name of this d d m structure
	*/
	public void setUserName(java.lang.String userName) {
		_ddmStructure.setUserName(userName);
	}

	/**
	* Gets the create date of this d d m structure.
	*
	* @return the create date of this d d m structure
	*/
	public java.util.Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	/**
	* Sets the create date of this d d m structure.
	*
	* @param createDate the create date of this d d m structure
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d m structure.
	*
	* @return the modified date of this d d m structure
	*/
	public java.util.Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m structure.
	*
	* @param modifiedDate the modified date of this d d m structure
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the structure key of this d d m structure.
	*
	* @return the structure key of this d d m structure
	*/
	public java.lang.String getStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	/**
	* Sets the structure key of this d d m structure.
	*
	* @param structureKey the structure key of this d d m structure
	*/
	public void setStructureKey(java.lang.String structureKey) {
		_ddmStructure.setStructureKey(structureKey);
	}

	/**
	* Gets the name of this d d m structure.
	*
	* @return the name of this d d m structure
	*/
	public java.lang.String getName() {
		return _ddmStructure.getName();
	}

	/**
	* Sets the name of this d d m structure.
	*
	* @param name the name of this d d m structure
	*/
	public void setName(java.lang.String name) {
		_ddmStructure.setName(name);
	}

	/**
	* Gets the description of this d d m structure.
	*
	* @return the description of this d d m structure
	*/
	public java.lang.String getDescription() {
		return _ddmStructure.getDescription();
	}

	/**
	* Sets the description of this d d m structure.
	*
	* @param description the description of this d d m structure
	*/
	public void setDescription(java.lang.String description) {
		_ddmStructure.setDescription(description);
	}

	/**
	* Gets the xsd of this d d m structure.
	*
	* @return the xsd of this d d m structure
	*/
	public java.lang.String getXsd() {
		return _ddmStructure.getXsd();
	}

	/**
	* Sets the xsd of this d d m structure.
	*
	* @param xsd the xsd of this d d m structure
	*/
	public void setXsd(java.lang.String xsd) {
		_ddmStructure.setXsd(xsd);
	}

	/**
	* Gets the storage type of this d d m structure.
	*
	* @return the storage type of this d d m structure
	*/
	public java.lang.String getStorageType() {
		return _ddmStructure.getStorageType();
	}

	/**
	* Sets the storage type of this d d m structure.
	*
	* @param storageType the storage type of this d d m structure
	*/
	public void setStorageType(java.lang.String storageType) {
		_ddmStructure.setStorageType(storageType);
	}

	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	public void setNew(boolean n) {
		_ddmStructure.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmStructure.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmStructure.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmStructure.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmStructure.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructure.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMStructureWrapper((DDMStructure)_ddmStructure.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		return _ddmStructure.compareTo(ddmStructure);
	}

	public int hashCode() {
		return _ddmStructure.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure toEscapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmStructure.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmStructure.toXmlString();
	}

	public java.lang.String getFieldType(java.lang.String fieldName) {
		return _ddmStructure.getFieldType(fieldName);
	}

	public boolean hasField(java.lang.String fieldName) {
		return _ddmStructure.hasField(fieldName);
	}

	public DDMStructure getWrappedDDMStructure() {
		return _ddmStructure;
	}

	public void resetOriginalValues() {
		_ddmStructure.resetOriginalValues();
	}

	private DDMStructure _ddmStructure;
}