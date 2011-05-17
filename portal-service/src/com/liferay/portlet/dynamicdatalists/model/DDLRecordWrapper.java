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
	* @param pk the primary key of this d d l record
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