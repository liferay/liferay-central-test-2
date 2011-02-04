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

package com.liferay.portlet.forms.model;

/**
 * <p>
 * This class is a wrapper for {@link FormsStructureEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormsStructureEntry
 * @generated
 */
public class FormsStructureEntryWrapper implements FormsStructureEntry {
	public FormsStructureEntryWrapper(FormsStructureEntry formsStructureEntry) {
		_formsStructureEntry = formsStructureEntry;
	}

	/**
	* Gets the primary key of this forms structure entry.
	*
	* @return the primary key of this forms structure entry
	*/
	public long getPrimaryKey() {
		return _formsStructureEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this forms structure entry
	*
	* @param pk the primary key of this forms structure entry
	*/
	public void setPrimaryKey(long pk) {
		_formsStructureEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this forms structure entry.
	*
	* @return the uuid of this forms structure entry
	*/
	public java.lang.String getUuid() {
		return _formsStructureEntry.getUuid();
	}

	/**
	* Sets the uuid of this forms structure entry.
	*
	* @param uuid the uuid of this forms structure entry
	*/
	public void setUuid(java.lang.String uuid) {
		_formsStructureEntry.setUuid(uuid);
	}

	/**
	* Gets the ID of this forms structure entry.
	*
	* @return the ID of this forms structure entry
	*/
	public long getId() {
		return _formsStructureEntry.getId();
	}

	/**
	* Sets the ID of this forms structure entry.
	*
	* @param id the ID of this forms structure entry
	*/
	public void setId(long id) {
		_formsStructureEntry.setId(id);
	}

	/**
	* Gets the group ID of this forms structure entry.
	*
	* @return the group ID of this forms structure entry
	*/
	public long getGroupId() {
		return _formsStructureEntry.getGroupId();
	}

	/**
	* Sets the group ID of this forms structure entry.
	*
	* @param groupId the group ID of this forms structure entry
	*/
	public void setGroupId(long groupId) {
		_formsStructureEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this forms structure entry.
	*
	* @return the company ID of this forms structure entry
	*/
	public long getCompanyId() {
		return _formsStructureEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this forms structure entry.
	*
	* @param companyId the company ID of this forms structure entry
	*/
	public void setCompanyId(long companyId) {
		_formsStructureEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this forms structure entry.
	*
	* @return the user ID of this forms structure entry
	*/
	public long getUserId() {
		return _formsStructureEntry.getUserId();
	}

	/**
	* Sets the user ID of this forms structure entry.
	*
	* @param userId the user ID of this forms structure entry
	*/
	public void setUserId(long userId) {
		_formsStructureEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this forms structure entry.
	*
	* @return the user uuid of this forms structure entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formsStructureEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this forms structure entry.
	*
	* @param userUuid the user uuid of this forms structure entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_formsStructureEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this forms structure entry.
	*
	* @return the user name of this forms structure entry
	*/
	public java.lang.String getUserName() {
		return _formsStructureEntry.getUserName();
	}

	/**
	* Sets the user name of this forms structure entry.
	*
	* @param userName the user name of this forms structure entry
	*/
	public void setUserName(java.lang.String userName) {
		_formsStructureEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this forms structure entry.
	*
	* @return the create date of this forms structure entry
	*/
	public java.util.Date getCreateDate() {
		return _formsStructureEntry.getCreateDate();
	}

	/**
	* Sets the create date of this forms structure entry.
	*
	* @param createDate the create date of this forms structure entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_formsStructureEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this forms structure entry.
	*
	* @return the modified date of this forms structure entry
	*/
	public java.util.Date getModifiedDate() {
		return _formsStructureEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this forms structure entry.
	*
	* @param modifiedDate the modified date of this forms structure entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_formsStructureEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the form structure ID of this forms structure entry.
	*
	* @return the form structure ID of this forms structure entry
	*/
	public java.lang.String getFormStructureId() {
		return _formsStructureEntry.getFormStructureId();
	}

	/**
	* Sets the form structure ID of this forms structure entry.
	*
	* @param formStructureId the form structure ID of this forms structure entry
	*/
	public void setFormStructureId(java.lang.String formStructureId) {
		_formsStructureEntry.setFormStructureId(formStructureId);
	}

	/**
	* Gets the name of this forms structure entry.
	*
	* @return the name of this forms structure entry
	*/
	public java.lang.String getName() {
		return _formsStructureEntry.getName();
	}

	/**
	* Sets the name of this forms structure entry.
	*
	* @param name the name of this forms structure entry
	*/
	public void setName(java.lang.String name) {
		_formsStructureEntry.setName(name);
	}

	/**
	* Gets the description of this forms structure entry.
	*
	* @return the description of this forms structure entry
	*/
	public java.lang.String getDescription() {
		return _formsStructureEntry.getDescription();
	}

	/**
	* Sets the description of this forms structure entry.
	*
	* @param description the description of this forms structure entry
	*/
	public void setDescription(java.lang.String description) {
		_formsStructureEntry.setDescription(description);
	}

	/**
	* Gets the xsd of this forms structure entry.
	*
	* @return the xsd of this forms structure entry
	*/
	public java.lang.String getXsd() {
		return _formsStructureEntry.getXsd();
	}

	/**
	* Sets the xsd of this forms structure entry.
	*
	* @param xsd the xsd of this forms structure entry
	*/
	public void setXsd(java.lang.String xsd) {
		_formsStructureEntry.setXsd(xsd);
	}

	public boolean isNew() {
		return _formsStructureEntry.isNew();
	}

	public void setNew(boolean n) {
		_formsStructureEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _formsStructureEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_formsStructureEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _formsStructureEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_formsStructureEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _formsStructureEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _formsStructureEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_formsStructureEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new FormsStructureEntryWrapper((FormsStructureEntry)_formsStructureEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry) {
		return _formsStructureEntry.compareTo(formsStructureEntry);
	}

	public int hashCode() {
		return _formsStructureEntry.hashCode();
	}

	public com.liferay.portlet.forms.model.FormsStructureEntry toEscapedModel() {
		return new FormsStructureEntryWrapper(_formsStructureEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _formsStructureEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _formsStructureEntry.toXmlString();
	}

	public FormsStructureEntry getWrappedFormsStructureEntry() {
		return _formsStructureEntry;
	}

	private FormsStructureEntry _formsStructureEntry;
}