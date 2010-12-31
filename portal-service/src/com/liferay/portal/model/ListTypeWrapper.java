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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link ListType}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListType
 * @generated
 */
public class ListTypeWrapper implements ListType {
	public ListTypeWrapper(ListType listType) {
		_listType = listType;
	}

	/**
	* Gets the primary key of this list type.
	*
	* @return the primary key of this list type
	*/
	public int getPrimaryKey() {
		return _listType.getPrimaryKey();
	}

	/**
	* Sets the primary key of this list type
	*
	* @param pk the primary key of this list type
	*/
	public void setPrimaryKey(int pk) {
		_listType.setPrimaryKey(pk);
	}

	/**
	* Gets the list type ID of this list type.
	*
	* @return the list type ID of this list type
	*/
	public int getListTypeId() {
		return _listType.getListTypeId();
	}

	/**
	* Sets the list type ID of this list type.
	*
	* @param listTypeId the list type ID of this list type
	*/
	public void setListTypeId(int listTypeId) {
		_listType.setListTypeId(listTypeId);
	}

	/**
	* Gets the name of this list type.
	*
	* @return the name of this list type
	*/
	public java.lang.String getName() {
		return _listType.getName();
	}

	/**
	* Sets the name of this list type.
	*
	* @param name the name of this list type
	*/
	public void setName(java.lang.String name) {
		_listType.setName(name);
	}

	/**
	* Gets the type of this list type.
	*
	* @return the type of this list type
	*/
	public java.lang.String getType() {
		return _listType.getType();
	}

	/**
	* Sets the type of this list type.
	*
	* @param type the type of this list type
	*/
	public void setType(java.lang.String type) {
		_listType.setType(type);
	}

	public boolean isNew() {
		return _listType.isNew();
	}

	public void setNew(boolean n) {
		_listType.setNew(n);
	}

	public boolean isCachedModel() {
		return _listType.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_listType.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _listType.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_listType.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _listType.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _listType.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_listType.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ListTypeWrapper((ListType)_listType.clone());
	}

	public int compareTo(com.liferay.portal.model.ListType listType) {
		return _listType.compareTo(listType);
	}

	public int hashCode() {
		return _listType.hashCode();
	}

	public com.liferay.portal.model.ListType toEscapedModel() {
		return new ListTypeWrapper(_listType.toEscapedModel());
	}

	public java.lang.String toString() {
		return _listType.toString();
	}

	public java.lang.String toXmlString() {
		return _listType.toXmlString();
	}

	public ListType getWrappedListType() {
		return _listType;
	}

	private ListType _listType;
}