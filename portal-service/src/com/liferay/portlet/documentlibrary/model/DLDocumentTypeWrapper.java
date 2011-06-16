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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This class is a wrapper for {@link DLDocumentType}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLDocumentType
 * @generated
 */
public class DLDocumentTypeWrapper implements DLDocumentType {
	public DLDocumentTypeWrapper(DLDocumentType dlDocumentType) {
		_dlDocumentType = dlDocumentType;
	}

	public Class<?> getModelClass() {
		return DLDocumentType.class;
	}

	public String getModelClassName() {
		return DLDocumentType.class.getName();
	}

	/**
	* Returns the primary key of this d l document type.
	*
	* @return the primary key of this d l document type
	*/
	public long getPrimaryKey() {
		return _dlDocumentType.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l document type.
	*
	* @param primaryKey the primary key of this d l document type
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlDocumentType.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the document type ID of this d l document type.
	*
	* @return the document type ID of this d l document type
	*/
	public long getDocumentTypeId() {
		return _dlDocumentType.getDocumentTypeId();
	}

	/**
	* Sets the document type ID of this d l document type.
	*
	* @param documentTypeId the document type ID of this d l document type
	*/
	public void setDocumentTypeId(long documentTypeId) {
		_dlDocumentType.setDocumentTypeId(documentTypeId);
	}

	/**
	* Returns the group ID of this d l document type.
	*
	* @return the group ID of this d l document type
	*/
	public long getGroupId() {
		return _dlDocumentType.getGroupId();
	}

	/**
	* Sets the group ID of this d l document type.
	*
	* @param groupId the group ID of this d l document type
	*/
	public void setGroupId(long groupId) {
		_dlDocumentType.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d l document type.
	*
	* @return the company ID of this d l document type
	*/
	public long getCompanyId() {
		return _dlDocumentType.getCompanyId();
	}

	/**
	* Sets the company ID of this d l document type.
	*
	* @param companyId the company ID of this d l document type
	*/
	public void setCompanyId(long companyId) {
		_dlDocumentType.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d l document type.
	*
	* @return the user ID of this d l document type
	*/
	public long getUserId() {
		return _dlDocumentType.getUserId();
	}

	/**
	* Sets the user ID of this d l document type.
	*
	* @param userId the user ID of this d l document type
	*/
	public void setUserId(long userId) {
		_dlDocumentType.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d l document type.
	*
	* @return the user uuid of this d l document type
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentType.getUserUuid();
	}

	/**
	* Sets the user uuid of this d l document type.
	*
	* @param userUuid the user uuid of this d l document type
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlDocumentType.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d l document type.
	*
	* @return the user name of this d l document type
	*/
	public java.lang.String getUserName() {
		return _dlDocumentType.getUserName();
	}

	/**
	* Sets the user name of this d l document type.
	*
	* @param userName the user name of this d l document type
	*/
	public void setUserName(java.lang.String userName) {
		_dlDocumentType.setUserName(userName);
	}

	/**
	* Returns the create date of this d l document type.
	*
	* @return the create date of this d l document type
	*/
	public java.util.Date getCreateDate() {
		return _dlDocumentType.getCreateDate();
	}

	/**
	* Sets the create date of this d l document type.
	*
	* @param createDate the create date of this d l document type
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlDocumentType.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d l document type.
	*
	* @return the modified date of this d l document type
	*/
	public java.util.Date getModifiedDate() {
		return _dlDocumentType.getModifiedDate();
	}

	/**
	* Sets the modified date of this d l document type.
	*
	* @param modifiedDate the modified date of this d l document type
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlDocumentType.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this d l document type.
	*
	* @return the name of this d l document type
	*/
	public java.lang.String getName() {
		return _dlDocumentType.getName();
	}

	/**
	* Sets the name of this d l document type.
	*
	* @param name the name of this d l document type
	*/
	public void setName(java.lang.String name) {
		_dlDocumentType.setName(name);
	}

	/**
	* Returns the description of this d l document type.
	*
	* @return the description of this d l document type
	*/
	public java.lang.String getDescription() {
		return _dlDocumentType.getDescription();
	}

	/**
	* Sets the description of this d l document type.
	*
	* @param description the description of this d l document type
	*/
	public void setDescription(java.lang.String description) {
		_dlDocumentType.setDescription(description);
	}

	public boolean isNew() {
		return _dlDocumentType.isNew();
	}

	public void setNew(boolean n) {
		_dlDocumentType.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlDocumentType.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlDocumentType.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlDocumentType.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlDocumentType.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlDocumentType.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlDocumentType.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlDocumentType.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlDocumentType.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLDocumentTypeWrapper((DLDocumentType)_dlDocumentType.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType) {
		return _dlDocumentType.compareTo(dlDocumentType);
	}

	@Override
	public int hashCode() {
		return _dlDocumentType.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType toEscapedModel() {
		return new DLDocumentTypeWrapper(_dlDocumentType.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlDocumentType.toString();
	}

	public java.lang.String toXmlString() {
		return _dlDocumentType.toXmlString();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentType.getDDMStructures();
	}

	public DLDocumentType getWrappedDLDocumentType() {
		return _dlDocumentType;
	}

	public void resetOriginalValues() {
		_dlDocumentType.resetOriginalValues();
	}

	private DLDocumentType _dlDocumentType;
}