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
 * This class is a wrapper for {@link FormStructure}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormStructure
 * @generated
 */
public class FormStructureWrapper implements FormStructure {
	public FormStructureWrapper(FormStructure formStructure) {
		_formStructure = formStructure;
	}

	/**
	* Gets the primary key of this form structure.
	*
	* @return the primary key of this form structure
	*/
	public long getPrimaryKey() {
		return _formStructure.getPrimaryKey();
	}

	/**
	* Sets the primary key of this form structure
	*
	* @param pk the primary key of this form structure
	*/
	public void setPrimaryKey(long pk) {
		_formStructure.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this form structure.
	*
	* @return the uuid of this form structure
	*/
	public java.lang.String getUuid() {
		return _formStructure.getUuid();
	}

	/**
	* Sets the uuid of this form structure.
	*
	* @param uuid the uuid of this form structure
	*/
	public void setUuid(java.lang.String uuid) {
		_formStructure.setUuid(uuid);
	}

	/**
	* Gets the ID of this form structure.
	*
	* @return the ID of this form structure
	*/
	public long getId() {
		return _formStructure.getId();
	}

	/**
	* Sets the ID of this form structure.
	*
	* @param id the ID of this form structure
	*/
	public void setId(long id) {
		_formStructure.setId(id);
	}

	/**
	* Gets the group ID of this form structure.
	*
	* @return the group ID of this form structure
	*/
	public long getGroupId() {
		return _formStructure.getGroupId();
	}

	/**
	* Sets the group ID of this form structure.
	*
	* @param groupId the group ID of this form structure
	*/
	public void setGroupId(long groupId) {
		_formStructure.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this form structure.
	*
	* @return the company ID of this form structure
	*/
	public long getCompanyId() {
		return _formStructure.getCompanyId();
	}

	/**
	* Sets the company ID of this form structure.
	*
	* @param companyId the company ID of this form structure
	*/
	public void setCompanyId(long companyId) {
		_formStructure.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this form structure.
	*
	* @return the user ID of this form structure
	*/
	public long getUserId() {
		return _formStructure.getUserId();
	}

	/**
	* Sets the user ID of this form structure.
	*
	* @param userId the user ID of this form structure
	*/
	public void setUserId(long userId) {
		_formStructure.setUserId(userId);
	}

	/**
	* Gets the user uuid of this form structure.
	*
	* @return the user uuid of this form structure
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructure.getUserUuid();
	}

	/**
	* Sets the user uuid of this form structure.
	*
	* @param userUuid the user uuid of this form structure
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_formStructure.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this form structure.
	*
	* @return the user name of this form structure
	*/
	public java.lang.String getUserName() {
		return _formStructure.getUserName();
	}

	/**
	* Sets the user name of this form structure.
	*
	* @param userName the user name of this form structure
	*/
	public void setUserName(java.lang.String userName) {
		_formStructure.setUserName(userName);
	}

	/**
	* Gets the create date of this form structure.
	*
	* @return the create date of this form structure
	*/
	public java.util.Date getCreateDate() {
		return _formStructure.getCreateDate();
	}

	/**
	* Sets the create date of this form structure.
	*
	* @param createDate the create date of this form structure
	*/
	public void setCreateDate(java.util.Date createDate) {
		_formStructure.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this form structure.
	*
	* @return the modified date of this form structure
	*/
	public java.util.Date getModifiedDate() {
		return _formStructure.getModifiedDate();
	}

	/**
	* Sets the modified date of this form structure.
	*
	* @param modifiedDate the modified date of this form structure
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_formStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the form structure ID of this form structure.
	*
	* @return the form structure ID of this form structure
	*/
	public java.lang.String getFormStructureId() {
		return _formStructure.getFormStructureId();
	}

	/**
	* Sets the form structure ID of this form structure.
	*
	* @param formStructureId the form structure ID of this form structure
	*/
	public void setFormStructureId(java.lang.String formStructureId) {
		_formStructure.setFormStructureId(formStructureId);
	}

	/**
	* Gets the name of this form structure.
	*
	* @return the name of this form structure
	*/
	public java.lang.String getName() {
		return _formStructure.getName();
	}

	/**
	* Sets the name of this form structure.
	*
	* @param name the name of this form structure
	*/
	public void setName(java.lang.String name) {
		_formStructure.setName(name);
	}

	/**
	* Gets the description of this form structure.
	*
	* @return the description of this form structure
	*/
	public java.lang.String getDescription() {
		return _formStructure.getDescription();
	}

	/**
	* Sets the description of this form structure.
	*
	* @param description the description of this form structure
	*/
	public void setDescription(java.lang.String description) {
		_formStructure.setDescription(description);
	}

	/**
	* Gets the xsd of this form structure.
	*
	* @return the xsd of this form structure
	*/
	public java.lang.String getXsd() {
		return _formStructure.getXsd();
	}

	/**
	* Sets the xsd of this form structure.
	*
	* @param xsd the xsd of this form structure
	*/
	public void setXsd(java.lang.String xsd) {
		_formStructure.setXsd(xsd);
	}

	public boolean isNew() {
		return _formStructure.isNew();
	}

	public void setNew(boolean n) {
		_formStructure.setNew(n);
	}

	public boolean isCachedModel() {
		return _formStructure.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_formStructure.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _formStructure.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_formStructure.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _formStructure.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _formStructure.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_formStructure.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new FormStructureWrapper((FormStructure)_formStructure.clone());
	}

	public int compareTo(
		com.liferay.portlet.forms.model.FormStructure formStructure) {
		return _formStructure.compareTo(formStructure);
	}

	public int hashCode() {
		return _formStructure.hashCode();
	}

	public com.liferay.portlet.forms.model.FormStructure toEscapedModel() {
		return new FormStructureWrapper(_formStructure.toEscapedModel());
	}

	public java.lang.String toString() {
		return _formStructure.toString();
	}

	public java.lang.String toXmlString() {
		return _formStructure.toXmlString();
	}

	public FormStructure getWrappedFormStructure() {
		return _formStructure;
	}

	private FormStructure _formStructure;
}