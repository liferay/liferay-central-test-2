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
 * This class is a wrapper for {@link FormStructureLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormStructureLink
 * @generated
 */
public class FormStructureLinkWrapper implements FormStructureLink {
	public FormStructureLinkWrapper(FormStructureLink formStructureLink) {
		_formStructureLink = formStructureLink;
	}

	/**
	* Gets the primary key of this form structure link.
	*
	* @return the primary key of this form structure link
	*/
	public long getPrimaryKey() {
		return _formStructureLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this form structure link
	*
	* @param pk the primary key of this form structure link
	*/
	public void setPrimaryKey(long pk) {
		_formStructureLink.setPrimaryKey(pk);
	}

	/**
	* Gets the form structure link ID of this form structure link.
	*
	* @return the form structure link ID of this form structure link
	*/
	public long getFormStructureLinkId() {
		return _formStructureLink.getFormStructureLinkId();
	}

	/**
	* Sets the form structure link ID of this form structure link.
	*
	* @param formStructureLinkId the form structure link ID of this form structure link
	*/
	public void setFormStructureLinkId(long formStructureLinkId) {
		_formStructureLink.setFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Gets the form structure ID of this form structure link.
	*
	* @return the form structure ID of this form structure link
	*/
	public java.lang.String getFormStructureId() {
		return _formStructureLink.getFormStructureId();
	}

	/**
	* Sets the form structure ID of this form structure link.
	*
	* @param formStructureId the form structure ID of this form structure link
	*/
	public void setFormStructureId(java.lang.String formStructureId) {
		_formStructureLink.setFormStructureId(formStructureId);
	}

	/**
	* Gets the class name of this form structure link.
	*
	* @return the class name of this form structure link
	*/
	public java.lang.String getClassName() {
		return _formStructureLink.getClassName();
	}

	/**
	* Sets the class name of this form structure link.
	*
	* @param className the class name of this form structure link
	*/
	public void setClassName(java.lang.String className) {
		_formStructureLink.setClassName(className);
	}

	/**
	* Gets the class p k of this form structure link.
	*
	* @return the class p k of this form structure link
	*/
	public long getClassPK() {
		return _formStructureLink.getClassPK();
	}

	/**
	* Sets the class p k of this form structure link.
	*
	* @param classPK the class p k of this form structure link
	*/
	public void setClassPK(long classPK) {
		_formStructureLink.setClassPK(classPK);
	}

	public boolean isNew() {
		return _formStructureLink.isNew();
	}

	public void setNew(boolean n) {
		_formStructureLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _formStructureLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_formStructureLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _formStructureLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_formStructureLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _formStructureLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _formStructureLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_formStructureLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new FormStructureLinkWrapper((FormStructureLink)_formStructureLink.clone());
	}

	public int compareTo(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink) {
		return _formStructureLink.compareTo(formStructureLink);
	}

	public int hashCode() {
		return _formStructureLink.hashCode();
	}

	public com.liferay.portlet.forms.model.FormStructureLink toEscapedModel() {
		return new FormStructureLinkWrapper(_formStructureLink.toEscapedModel());
	}

	public java.lang.String toString() {
		return _formStructureLink.toString();
	}

	public java.lang.String toXmlString() {
		return _formStructureLink.toXmlString();
	}

	public FormStructureLink getWrappedFormStructureLink() {
		return _formStructureLink;
	}

	private FormStructureLink _formStructureLink;
}