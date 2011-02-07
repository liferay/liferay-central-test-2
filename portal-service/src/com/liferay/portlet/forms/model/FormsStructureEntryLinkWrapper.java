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
 * This class is a wrapper for {@link FormsStructureEntryLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormsStructureEntryLink
 * @generated
 */
public class FormsStructureEntryLinkWrapper implements FormsStructureEntryLink {
	public FormsStructureEntryLinkWrapper(
		FormsStructureEntryLink formsStructureEntryLink) {
		_formsStructureEntryLink = formsStructureEntryLink;
	}

	/**
	* Gets the primary key of this forms structure entry link.
	*
	* @return the primary key of this forms structure entry link
	*/
	public long getPrimaryKey() {
		return _formsStructureEntryLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this forms structure entry link
	*
	* @param pk the primary key of this forms structure entry link
	*/
	public void setPrimaryKey(long pk) {
		_formsStructureEntryLink.setPrimaryKey(pk);
	}

	/**
	* Gets the form structure link ID of this forms structure entry link.
	*
	* @return the form structure link ID of this forms structure entry link
	*/
	public long getFormStructureLinkId() {
		return _formsStructureEntryLink.getFormStructureLinkId();
	}

	/**
	* Sets the form structure link ID of this forms structure entry link.
	*
	* @param formStructureLinkId the form structure link ID of this forms structure entry link
	*/
	public void setFormStructureLinkId(long formStructureLinkId) {
		_formsStructureEntryLink.setFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Gets the form structure ID of this forms structure entry link.
	*
	* @return the form structure ID of this forms structure entry link
	*/
	public java.lang.String getFormStructureId() {
		return _formsStructureEntryLink.getFormStructureId();
	}

	/**
	* Sets the form structure ID of this forms structure entry link.
	*
	* @param formStructureId the form structure ID of this forms structure entry link
	*/
	public void setFormStructureId(java.lang.String formStructureId) {
		_formsStructureEntryLink.setFormStructureId(formStructureId);
	}

	/**
	* Gets the class name of this forms structure entry link.
	*
	* @return the class name of this forms structure entry link
	*/
	public java.lang.String getClassName() {
		return _formsStructureEntryLink.getClassName();
	}

	/**
	* Sets the class name of this forms structure entry link.
	*
	* @param className the class name of this forms structure entry link
	*/
	public void setClassName(java.lang.String className) {
		_formsStructureEntryLink.setClassName(className);
	}

	/**
	* Gets the class p k of this forms structure entry link.
	*
	* @return the class p k of this forms structure entry link
	*/
	public long getClassPK() {
		return _formsStructureEntryLink.getClassPK();
	}

	/**
	* Sets the class p k of this forms structure entry link.
	*
	* @param classPK the class p k of this forms structure entry link
	*/
	public void setClassPK(long classPK) {
		_formsStructureEntryLink.setClassPK(classPK);
	}

	public boolean isNew() {
		return _formsStructureEntryLink.isNew();
	}

	public void setNew(boolean n) {
		_formsStructureEntryLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _formsStructureEntryLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_formsStructureEntryLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _formsStructureEntryLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_formsStructureEntryLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _formsStructureEntryLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _formsStructureEntryLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_formsStructureEntryLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new FormsStructureEntryLinkWrapper((FormsStructureEntryLink)_formsStructureEntryLink.clone());
	}

	public int compareTo(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink) {
		return _formsStructureEntryLink.compareTo(formsStructureEntryLink);
	}

	public int hashCode() {
		return _formsStructureEntryLink.hashCode();
	}

	public com.liferay.portlet.forms.model.FormsStructureEntryLink toEscapedModel() {
		return new FormsStructureEntryLinkWrapper(_formsStructureEntryLink.toEscapedModel());
	}

	public java.lang.String toString() {
		return _formsStructureEntryLink.toString();
	}

	public java.lang.String toXmlString() {
		return _formsStructureEntryLink.toXmlString();
	}

	public FormsStructureEntryLink getWrappedFormsStructureEntryLink() {
		return _formsStructureEntryLink;
	}

	private FormsStructureEntryLink _formsStructureEntryLink;
}