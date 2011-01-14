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

package com.liferay.portlet.deletion.model;

/**
 * <p>
 * This class is a wrapper for {@link DeletionEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DeletionEntry
 * @generated
 */
public class DeletionEntryWrapper implements DeletionEntry {
	public DeletionEntryWrapper(DeletionEntry deletionEntry) {
		_deletionEntry = deletionEntry;
	}

	/**
	* Gets the primary key of this deletion entry.
	*
	* @return the primary key of this deletion entry
	*/
	public long getPrimaryKey() {
		return _deletionEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this deletion entry
	*
	* @param pk the primary key of this deletion entry
	*/
	public void setPrimaryKey(long pk) {
		_deletionEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the entry ID of this deletion entry.
	*
	* @return the entry ID of this deletion entry
	*/
	public long getEntryId() {
		return _deletionEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this deletion entry.
	*
	* @param entryId the entry ID of this deletion entry
	*/
	public void setEntryId(long entryId) {
		_deletionEntry.setEntryId(entryId);
	}

	/**
	* Gets the group ID of this deletion entry.
	*
	* @return the group ID of this deletion entry
	*/
	public long getGroupId() {
		return _deletionEntry.getGroupId();
	}

	/**
	* Sets the group ID of this deletion entry.
	*
	* @param groupId the group ID of this deletion entry
	*/
	public void setGroupId(long groupId) {
		_deletionEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this deletion entry.
	*
	* @return the company ID of this deletion entry
	*/
	public long getCompanyId() {
		return _deletionEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this deletion entry.
	*
	* @param companyId the company ID of this deletion entry
	*/
	public void setCompanyId(long companyId) {
		_deletionEntry.setCompanyId(companyId);
	}

	/**
	* Gets the create date of this deletion entry.
	*
	* @return the create date of this deletion entry
	*/
	public java.util.Date getCreateDate() {
		return _deletionEntry.getCreateDate();
	}

	/**
	* Sets the create date of this deletion entry.
	*
	* @param createDate the create date of this deletion entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_deletionEntry.setCreateDate(createDate);
	}

	/**
	* Gets the class name of the model instance this deletion entry is polymorphically associated with.
	*
	* @return the class name of the model instance this deletion entry is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _deletionEntry.getClassName();
	}

	/**
	* Gets the class name ID of this deletion entry.
	*
	* @return the class name ID of this deletion entry
	*/
	public long getClassNameId() {
		return _deletionEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this deletion entry.
	*
	* @param classNameId the class name ID of this deletion entry
	*/
	public void setClassNameId(long classNameId) {
		_deletionEntry.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this deletion entry.
	*
	* @return the class p k of this deletion entry
	*/
	public long getClassPK() {
		return _deletionEntry.getClassPK();
	}

	/**
	* Sets the class p k of this deletion entry.
	*
	* @param classPK the class p k of this deletion entry
	*/
	public void setClassPK(long classPK) {
		_deletionEntry.setClassPK(classPK);
	}

	/**
	* Gets the class uuid of this deletion entry.
	*
	* @return the class uuid of this deletion entry
	*/
	public java.lang.String getClassUuid() {
		return _deletionEntry.getClassUuid();
	}

	/**
	* Sets the class uuid of this deletion entry.
	*
	* @param classUuid the class uuid of this deletion entry
	*/
	public void setClassUuid(java.lang.String classUuid) {
		_deletionEntry.setClassUuid(classUuid);
	}

	/**
	* Gets the parent ID of this deletion entry.
	*
	* @return the parent ID of this deletion entry
	*/
	public long getParentId() {
		return _deletionEntry.getParentId();
	}

	/**
	* Sets the parent ID of this deletion entry.
	*
	* @param parentId the parent ID of this deletion entry
	*/
	public void setParentId(long parentId) {
		_deletionEntry.setParentId(parentId);
	}

	public boolean isNew() {
		return _deletionEntry.isNew();
	}

	public void setNew(boolean n) {
		_deletionEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _deletionEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_deletionEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _deletionEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_deletionEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _deletionEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _deletionEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_deletionEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DeletionEntryWrapper((DeletionEntry)_deletionEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry) {
		return _deletionEntry.compareTo(deletionEntry);
	}

	public int hashCode() {
		return _deletionEntry.hashCode();
	}

	public com.liferay.portlet.deletion.model.DeletionEntry toEscapedModel() {
		return new DeletionEntryWrapper(_deletionEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _deletionEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _deletionEntry.toXmlString();
	}

	public DeletionEntry getWrappedDeletionEntry() {
		return _deletionEntry;
	}

	private DeletionEntry _deletionEntry;
}