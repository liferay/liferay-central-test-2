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
 * This class is a wrapper for {@link DDMListEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMListEntry
 * @generated
 */
public class DDMListEntryWrapper implements DDMListEntry {
	public DDMListEntryWrapper(DDMListEntry ddmListEntry) {
		_ddmListEntry = ddmListEntry;
	}

	public Class<?> getModelClass() {
		return DDMListEntry.class;
	}

	public String getModelClassName() {
		return DDMListEntry.class.getName();
	}

	/**
	* Gets the primary key of this d d m list entry.
	*
	* @return the primary key of this d d m list entry
	*/
	public long getPrimaryKey() {
		return _ddmListEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m list entry
	*
	* @param pk the primary key of this d d m list entry
	*/
	public void setPrimaryKey(long pk) {
		_ddmListEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m list entry.
	*
	* @return the uuid of this d d m list entry
	*/
	public java.lang.String getUuid() {
		return _ddmListEntry.getUuid();
	}

	/**
	* Sets the uuid of this d d m list entry.
	*
	* @param uuid the uuid of this d d m list entry
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmListEntry.setUuid(uuid);
	}

	/**
	* Gets the list entry ID of this d d m list entry.
	*
	* @return the list entry ID of this d d m list entry
	*/
	public long getListEntryId() {
		return _ddmListEntry.getListEntryId();
	}

	/**
	* Sets the list entry ID of this d d m list entry.
	*
	* @param listEntryId the list entry ID of this d d m list entry
	*/
	public void setListEntryId(long listEntryId) {
		_ddmListEntry.setListEntryId(listEntryId);
	}

	/**
	* Gets the list ID of this d d m list entry.
	*
	* @return the list ID of this d d m list entry
	*/
	public long getListId() {
		return _ddmListEntry.getListId();
	}

	/**
	* Sets the list ID of this d d m list entry.
	*
	* @param listId the list ID of this d d m list entry
	*/
	public void setListId(long listId) {
		_ddmListEntry.setListId(listId);
	}

	/**
	* Gets the class p k of this d d m list entry.
	*
	* @return the class p k of this d d m list entry
	*/
	public long getClassPK() {
		return _ddmListEntry.getClassPK();
	}

	/**
	* Sets the class p k of this d d m list entry.
	*
	* @param classPK the class p k of this d d m list entry
	*/
	public void setClassPK(long classPK) {
		_ddmListEntry.setClassPK(classPK);
	}

	public boolean isNew() {
		return _ddmListEntry.isNew();
	}

	public void setNew(boolean n) {
		_ddmListEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmListEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmListEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmListEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmListEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmListEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmListEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmListEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMListEntryWrapper((DDMListEntry)_ddmListEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry) {
		return _ddmListEntry.compareTo(ddmListEntry);
	}

	public int hashCode() {
		return _ddmListEntry.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMListEntry toEscapedModel() {
		return new DDMListEntryWrapper(_ddmListEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmListEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmListEntry.toXmlString();
	}

	public DDMListEntry getWrappedDDMListEntry() {
		return _ddmListEntry;
	}

	public void resetOriginalValues() {
		_ddmListEntry.resetOriginalValues();
	}

	private DDMListEntry _ddmListEntry;
}