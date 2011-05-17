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
 * This class is a wrapper for {@link DDLRecord}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecord
 * @generated
 */
public class DDLRecordWrapper implements DDLRecord {
	public DDLRecordWrapper(DDLRecord ddlRecord) {
		_ddlRecord = ddlRecord;
	}

	public Class<?> getModelClass() {
		return DDLRecord.class;
	}

	public String getModelClassName() {
		return DDLRecord.class.getName();
	}

	/**
	* Gets the primary key of this d d l record.
	*
	* @return the primary key of this d d l record
	*/
	public long getPrimaryKey() {
		return _ddlRecord.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l record
	*
	* @param primaryKey the primary key of this d d l record
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddlRecord.setPrimaryKey(primaryKey);
	}

	/**
	* Gets the uuid of this d d l record.
	*
	* @return the uuid of this d d l record
	*/
	public java.lang.String getUuid() {
		return _ddlRecord.getUuid();
	}

	/**
	* Sets the uuid of this d d l record.
	*
	* @param uuid the uuid of this d d l record
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlRecord.setUuid(uuid);
	}

	/**
	* Gets the record ID of this d d l record.
	*
	* @return the record ID of this d d l record
	*/
	public long getRecordId() {
		return _ddlRecord.getRecordId();
	}

	/**
	* Sets the record ID of this d d l record.
	*
	* @param recordId the record ID of this d d l record
	*/
	public void setRecordId(long recordId) {
		_ddlRecord.setRecordId(recordId);
	}

	/**
	* Gets the group ID of this d d l record.
	*
	* @return the group ID of this d d l record
	*/
	public long getGroupId() {
		return _ddlRecord.getGroupId();
	}

	/**
	* Sets the group ID of this d d l record.
	*
	* @param groupId the group ID of this d d l record
	*/
	public void setGroupId(long groupId) {
		_ddlRecord.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d l record.
	*
	* @return the company ID of this d d l record
	*/
	public long getCompanyId() {
		return _ddlRecord.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l record.
	*
	* @param companyId the company ID of this d d l record
	*/
	public void setCompanyId(long companyId) {
		_ddlRecord.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d l record.
	*
	* @return the user ID of this d d l record
	*/
	public long getUserId() {
		return _ddlRecord.getUserId();
	}

	/**
	* Sets the user ID of this d d l record.
	*
	* @param userId the user ID of this d d l record
	*/
	public void setUserId(long userId) {
		_ddlRecord.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d l record.
	*
	* @return the user uuid of this d d l record
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l record.
	*
	* @param userUuid the user uuid of this d d l record
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecord.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d l record.
	*
	* @return the user name of this d d l record
	*/
	public java.lang.String getUserName() {
		return _ddlRecord.getUserName();
	}

	/**
	* Sets the user name of this d d l record.
	*
	* @param userName the user name of this d d l record
	*/
	public void setUserName(java.lang.String userName) {
		_ddlRecord.setUserName(userName);
	}

	/**
	* Gets the create date of this d d l record.
	*
	* @return the create date of this d d l record
	*/
	public java.util.Date getCreateDate() {
		return _ddlRecord.getCreateDate();
	}

	/**
	* Sets the create date of this d d l record.
	*
	* @param createDate the create date of this d d l record
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddlRecord.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d l record.
	*
	* @return the modified date of this d d l record
	*/
	public java.util.Date getModifiedDate() {
		return _ddlRecord.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d l record.
	*
	* @param modifiedDate the modified date of this d d l record
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddlRecord.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this d d l record is polymorphically associated with.
	*
	* @return the class name of the model instance this d d l record is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ddlRecord.getClassName();
	}

	/**
	* Gets the class name ID of this d d l record.
	*
	* @return the class name ID of this d d l record
	*/
	public long getClassNameId() {
		return _ddlRecord.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d l record.
	*
	* @param classNameId the class name ID of this d d l record
	*/
	public void setClassNameId(long classNameId) {
		_ddlRecord.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this d d l record.
	*
	* @return the class p k of this d d l record
	*/
	public long getClassPK() {
		return _ddlRecord.getClassPK();
	}

	/**
	* Sets the class p k of this d d l record.
	*
	* @param classPK the class p k of this d d l record
	*/
	public void setClassPK(long classPK) {
		_ddlRecord.setClassPK(classPK);
	}

	/**
	* Gets the record set ID of this d d l record.
	*
	* @return the record set ID of this d d l record
	*/
	public long getRecordSetId() {
		return _ddlRecord.getRecordSetId();
	}

	/**
	* Sets the record set ID of this d d l record.
	*
	* @param recordSetId the record set ID of this d d l record
	*/
	public void setRecordSetId(long recordSetId) {
		_ddlRecord.setRecordSetId(recordSetId);
	}

	/**
	* Gets the display index of this d d l record.
	*
	* @return the display index of this d d l record
	*/
	public int getDisplayIndex() {
		return _ddlRecord.getDisplayIndex();
	}

	/**
	* Sets the display index of this d d l record.
	*
	* @param displayIndex the display index of this d d l record
	*/
	public void setDisplayIndex(int displayIndex) {
		_ddlRecord.setDisplayIndex(displayIndex);
	}

	/**
	* Gets the status of this d d l record.
	*
	* @return the status of this d d l record
	*/
	public int getStatus() {
		return _ddlRecord.getStatus();
	}

	/**
	* Sets the status of this d d l record.
	*
	* @param status the status of this d d l record
	*/
	public void setStatus(int status) {
		_ddlRecord.setStatus(status);
	}

	/**
	* Gets the status by user ID of this d d l record.
	*
	* @return the status by user ID of this d d l record
	*/
	public long getStatusByUserId() {
		return _ddlRecord.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this d d l record.
	*
	* @param statusByUserId the status by user ID of this d d l record
	*/
	public void setStatusByUserId(long statusByUserId) {
		_ddlRecord.setStatusByUserId(statusByUserId);
	}

	/**
	* Gets the status by user uuid of this d d l record.
	*
	* @return the status by user uuid of this d d l record
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this d d l record.
	*
	* @param statusByUserUuid the status by user uuid of this d d l record
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddlRecord.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Gets the status by user name of this d d l record.
	*
	* @return the status by user name of this d d l record
	*/
	public java.lang.String getStatusByUserName() {
		return _ddlRecord.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this d d l record.
	*
	* @param statusByUserName the status by user name of this d d l record
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddlRecord.setStatusByUserName(statusByUserName);
	}

	/**
	* Gets the status date of this d d l record.
	*
	* @return the status date of this d d l record
	*/
	public java.util.Date getStatusDate() {
		return _ddlRecord.getStatusDate();
	}

	/**
	* Sets the status date of this d d l record.
	*
	* @param statusDate the status date of this d d l record
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_ddlRecord.setStatusDate(statusDate);
	}

	/**
	* @deprecated {@link #isApproved}
	*/
	public boolean getApproved() {
		return _ddlRecord.getApproved();
	}

	/**
	* Determines if this d d l record is approved.
	*
	* @return <code>true</code> if this d d l record is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _ddlRecord.isApproved();
	}

	/**
	* Determines if this d d l record is a draft.
	*
	* @return <code>true</code> if this d d l record is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _ddlRecord.isDraft();
	}

	/**
	* Determines if this d d l record is expired.
	*
	* @return <code>true</code> if this d d l record is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _ddlRecord.isExpired();
	}

	/**
	* Determines if this d d l record is pending.
	*
	* @return <code>true</code> if this d d l record is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _ddlRecord.isPending();
	}

	public boolean isNew() {
		return _ddlRecord.isNew();
	}

	public void setNew(boolean n) {
		_ddlRecord.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlRecord.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlRecord.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlRecord.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddlRecord.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecord.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecord.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecord.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecord.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDLRecordWrapper((DDLRecord)_ddlRecord.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord) {
		return _ddlRecord.compareTo(ddlRecord);
	}

	public int hashCode() {
		return _ddlRecord.hashCode();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecord toEscapedModel() {
		return new DDLRecordWrapper(_ddlRecord.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddlRecord.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlRecord.toXmlString();
	}

	public com.liferay.portlet.dynamicdatamapping.storage.Field getField(
		java.lang.String fieldName)
		throws com.liferay.portlet.dynamicdatamapping.StorageException {
		return _ddlRecord.getField(fieldName);
	}

	public java.io.Serializable getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getFieldDataType(fieldName);
	}

	public com.liferay.portlet.dynamicdatamapping.storage.Fields getFields()
		throws com.liferay.portlet.dynamicdatamapping.StorageException {
		return _ddlRecord.getFields();
	}

	public java.io.Serializable getFieldType(java.lang.String fieldName)
		throws java.lang.Exception {
		return _ddlRecord.getFieldType(fieldName);
	}

	public java.io.Serializable getFieldValue(java.lang.String fieldName)
		throws com.liferay.portlet.dynamicdatamapping.StorageException {
		return _ddlRecord.getFieldValue(fieldName);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecord.getRecordSet();
	}

	public DDLRecord getWrappedDDLRecord() {
		return _ddlRecord;
	}

	public void resetOriginalValues() {
		_ddlRecord.resetOriginalValues();
	}

	private DDLRecord _ddlRecord;
}