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
 * This class is a wrapper for {@link DLDocumentMetadataSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLDocumentMetadataSet
 * @generated
 */
public class DLDocumentMetadataSetWrapper implements DLDocumentMetadataSet {
	public DLDocumentMetadataSetWrapper(
		DLDocumentMetadataSet dlDocumentMetadataSet) {
		_dlDocumentMetadataSet = dlDocumentMetadataSet;
	}

	public Class<?> getModelClass() {
		return DLDocumentMetadataSet.class;
	}

	public String getModelClassName() {
		return DLDocumentMetadataSet.class.getName();
	}

	/**
	* Returns the primary key of this d l document metadata set.
	*
	* @return the primary key of this d l document metadata set
	*/
	public long getPrimaryKey() {
		return _dlDocumentMetadataSet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l document metadata set.
	*
	* @param primaryKey the primary key of this d l document metadata set
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlDocumentMetadataSet.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d l document metadata set.
	*
	* @return the uuid of this d l document metadata set
	*/
	public java.lang.String getUuid() {
		return _dlDocumentMetadataSet.getUuid();
	}

	/**
	* Sets the uuid of this d l document metadata set.
	*
	* @param uuid the uuid of this d l document metadata set
	*/
	public void setUuid(java.lang.String uuid) {
		_dlDocumentMetadataSet.setUuid(uuid);
	}

	/**
	* Returns the document metadata set ID of this d l document metadata set.
	*
	* @return the document metadata set ID of this d l document metadata set
	*/
	public long getDocumentMetadataSetId() {
		return _dlDocumentMetadataSet.getDocumentMetadataSetId();
	}

	/**
	* Sets the document metadata set ID of this d l document metadata set.
	*
	* @param documentMetadataSetId the document metadata set ID of this d l document metadata set
	*/
	public void setDocumentMetadataSetId(long documentMetadataSetId) {
		_dlDocumentMetadataSet.setDocumentMetadataSetId(documentMetadataSetId);
	}

	/**
	* Returns the fully qualified class name of this d l document metadata set.
	*
	* @return the fully qualified class name of this d l document metadata set
	*/
	public java.lang.String getClassName() {
		return _dlDocumentMetadataSet.getClassName();
	}

	/**
	* Returns the class name ID of this d l document metadata set.
	*
	* @return the class name ID of this d l document metadata set
	*/
	public long getClassNameId() {
		return _dlDocumentMetadataSet.getClassNameId();
	}

	/**
	* Sets the class name ID of this d l document metadata set.
	*
	* @param classNameId the class name ID of this d l document metadata set
	*/
	public void setClassNameId(long classNameId) {
		_dlDocumentMetadataSet.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this d l document metadata set.
	*
	* @return the class p k of this d l document metadata set
	*/
	public long getClassPK() {
		return _dlDocumentMetadataSet.getClassPK();
	}

	/**
	* Sets the class p k of this d l document metadata set.
	*
	* @param classPK the class p k of this d l document metadata set
	*/
	public void setClassPK(long classPK) {
		_dlDocumentMetadataSet.setClassPK(classPK);
	}

	/**
	* Returns the d d m structure ID of this d l document metadata set.
	*
	* @return the d d m structure ID of this d l document metadata set
	*/
	public long getDDMStructureId() {
		return _dlDocumentMetadataSet.getDDMStructureId();
	}

	/**
	* Sets the d d m structure ID of this d l document metadata set.
	*
	* @param DDMStructureId the d d m structure ID of this d l document metadata set
	*/
	public void setDDMStructureId(long DDMStructureId) {
		_dlDocumentMetadataSet.setDDMStructureId(DDMStructureId);
	}

	/**
	* Returns the document type ID of this d l document metadata set.
	*
	* @return the document type ID of this d l document metadata set
	*/
	public long getDocumentTypeId() {
		return _dlDocumentMetadataSet.getDocumentTypeId();
	}

	/**
	* Sets the document type ID of this d l document metadata set.
	*
	* @param documentTypeId the document type ID of this d l document metadata set
	*/
	public void setDocumentTypeId(long documentTypeId) {
		_dlDocumentMetadataSet.setDocumentTypeId(documentTypeId);
	}

	/**
	* Returns the file version ID of this d l document metadata set.
	*
	* @return the file version ID of this d l document metadata set
	*/
	public long getFileVersionId() {
		return _dlDocumentMetadataSet.getFileVersionId();
	}

	/**
	* Sets the file version ID of this d l document metadata set.
	*
	* @param fileVersionId the file version ID of this d l document metadata set
	*/
	public void setFileVersionId(long fileVersionId) {
		_dlDocumentMetadataSet.setFileVersionId(fileVersionId);
	}

	public boolean isNew() {
		return _dlDocumentMetadataSet.isNew();
	}

	public void setNew(boolean n) {
		_dlDocumentMetadataSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlDocumentMetadataSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlDocumentMetadataSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlDocumentMetadataSet.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlDocumentMetadataSet.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlDocumentMetadataSet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlDocumentMetadataSet.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlDocumentMetadataSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlDocumentMetadataSet.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DLDocumentMetadataSetWrapper((DLDocumentMetadataSet)_dlDocumentMetadataSet.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet) {
		return _dlDocumentMetadataSet.compareTo(dlDocumentMetadataSet);
	}

	public int hashCode() {
		return _dlDocumentMetadataSet.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet toEscapedModel() {
		return new DLDocumentMetadataSetWrapper(_dlDocumentMetadataSet.toEscapedModel());
	}

	public java.lang.String toString() {
		return _dlDocumentMetadataSet.toString();
	}

	public java.lang.String toXmlString() {
		return _dlDocumentMetadataSet.toXmlString();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentMetadataSet.getDDMStructure();
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType getDocumentType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentMetadataSet.getDocumentType();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentMetadataSet.getFileVersion();
	}

	public DLDocumentMetadataSet getWrappedDLDocumentMetadataSet() {
		return _dlDocumentMetadataSet;
	}

	public void resetOriginalValues() {
		_dlDocumentMetadataSet.resetOriginalValues();
	}

	private DLDocumentMetadataSet _dlDocumentMetadataSet;
}