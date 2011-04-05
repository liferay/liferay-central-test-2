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
 * This class is a wrapper for {@link DDMListItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMListItem
 * @generated
 */
public class DDMListItemWrapper implements DDMListItem {
	public DDMListItemWrapper(DDMListItem ddmListItem) {
		_ddmListItem = ddmListItem;
	}

	public Class<?> getModelClass() {
		return DDMListItem.class;
	}

	public String getModelClassName() {
		return DDMListItem.class.getName();
	}

	/**
	* Gets the primary key of this d d m list item.
	*
	* @return the primary key of this d d m list item
	*/
	public long getPrimaryKey() {
		return _ddmListItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m list item
	*
	* @param pk the primary key of this d d m list item
	*/
	public void setPrimaryKey(long pk) {
		_ddmListItem.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m list item.
	*
	* @return the uuid of this d d m list item
	*/
	public java.lang.String getUuid() {
		return _ddmListItem.getUuid();
	}

	/**
	* Sets the uuid of this d d m list item.
	*
	* @param uuid the uuid of this d d m list item
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmListItem.setUuid(uuid);
	}

	/**
	* Gets the list item ID of this d d m list item.
	*
	* @return the list item ID of this d d m list item
	*/
	public long getListItemId() {
		return _ddmListItem.getListItemId();
	}

	/**
	* Sets the list item ID of this d d m list item.
	*
	* @param listItemId the list item ID of this d d m list item
	*/
	public void setListItemId(long listItemId) {
		_ddmListItem.setListItemId(listItemId);
	}

	/**
	* Gets the class name of the model instance this d d m list item is polymorphically associated with.
	*
	* @return the class name of the model instance this d d m list item is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ddmListItem.getClassName();
	}

	/**
	* Gets the class name ID of this d d m list item.
	*
	* @return the class name ID of this d d m list item
	*/
	public long getClassNameId() {
		return _ddmListItem.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d m list item.
	*
	* @param classNameId the class name ID of this d d m list item
	*/
	public void setClassNameId(long classNameId) {
		_ddmListItem.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this d d m list item.
	*
	* @return the class p k of this d d m list item
	*/
	public long getClassPK() {
		return _ddmListItem.getClassPK();
	}

	/**
	* Sets the class p k of this d d m list item.
	*
	* @param classPK the class p k of this d d m list item
	*/
	public void setClassPK(long classPK) {
		_ddmListItem.setClassPK(classPK);
	}

	/**
	* Gets the list ID of this d d m list item.
	*
	* @return the list ID of this d d m list item
	*/
	public long getListId() {
		return _ddmListItem.getListId();
	}

	/**
	* Sets the list ID of this d d m list item.
	*
	* @param listId the list ID of this d d m list item
	*/
	public void setListId(long listId) {
		_ddmListItem.setListId(listId);
	}

	public boolean isNew() {
		return _ddmListItem.isNew();
	}

	public void setNew(boolean n) {
		_ddmListItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmListItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmListItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmListItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmListItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmListItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmListItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmListItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMListItemWrapper((DDMListItem)_ddmListItem.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem ddmListItem) {
		return _ddmListItem.compareTo(ddmListItem);
	}

	public int hashCode() {
		return _ddmListItem.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMListItem toEscapedModel() {
		return new DDMListItemWrapper(_ddmListItem.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmListItem.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmListItem.toXmlString();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList getList()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListItem.getList();
	}

	public DDMListItem getWrappedDDMListItem() {
		return _ddmListItem;
	}

	public void resetOriginalValues() {
		_ddmListItem.resetOriginalValues();
	}

	private DDMListItem _ddmListItem;
}