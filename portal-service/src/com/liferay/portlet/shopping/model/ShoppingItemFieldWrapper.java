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

package com.liferay.portlet.shopping.model;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItemField}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemField
 * @generated
 */
public class ShoppingItemFieldWrapper implements ShoppingItemField {
	public ShoppingItemFieldWrapper(ShoppingItemField shoppingItemField) {
		_shoppingItemField = shoppingItemField;
	}

	/**
	* Gets the primary key of this shopping item field.
	*
	* @return the primary key of this shopping item field
	*/
	public long getPrimaryKey() {
		return _shoppingItemField.getPrimaryKey();
	}

	/**
	* Sets the primary key of this shopping item field
	*
	* @param pk the primary key of this shopping item field
	*/
	public void setPrimaryKey(long pk) {
		_shoppingItemField.setPrimaryKey(pk);
	}

	/**
	* Gets the item field ID of this shopping item field.
	*
	* @return the item field ID of this shopping item field
	*/
	public long getItemFieldId() {
		return _shoppingItemField.getItemFieldId();
	}

	/**
	* Sets the item field ID of this shopping item field.
	*
	* @param itemFieldId the item field ID of this shopping item field
	*/
	public void setItemFieldId(long itemFieldId) {
		_shoppingItemField.setItemFieldId(itemFieldId);
	}

	/**
	* Gets the item ID of this shopping item field.
	*
	* @return the item ID of this shopping item field
	*/
	public long getItemId() {
		return _shoppingItemField.getItemId();
	}

	/**
	* Sets the item ID of this shopping item field.
	*
	* @param itemId the item ID of this shopping item field
	*/
	public void setItemId(long itemId) {
		_shoppingItemField.setItemId(itemId);
	}

	/**
	* Gets the name of this shopping item field.
	*
	* @return the name of this shopping item field
	*/
	public java.lang.String getName() {
		return _shoppingItemField.getName();
	}

	/**
	* Sets the name of this shopping item field.
	*
	* @param name the name of this shopping item field
	*/
	public void setName(java.lang.String name) {
		_shoppingItemField.setName(name);
	}

	/**
	* Gets the values of this shopping item field.
	*
	* @return the values of this shopping item field
	*/
	public java.lang.String getValues() {
		return _shoppingItemField.getValues();
	}

	/**
	* Sets the values of this shopping item field.
	*
	* @param values the values of this shopping item field
	*/
	public void setValues(java.lang.String values) {
		_shoppingItemField.setValues(values);
	}

	/**
	* Gets the description of this shopping item field.
	*
	* @return the description of this shopping item field
	*/
	public java.lang.String getDescription() {
		return _shoppingItemField.getDescription();
	}

	/**
	* Sets the description of this shopping item field.
	*
	* @param description the description of this shopping item field
	*/
	public void setDescription(java.lang.String description) {
		_shoppingItemField.setDescription(description);
	}

	public boolean isNew() {
		return _shoppingItemField.isNew();
	}

	public void setNew(boolean n) {
		_shoppingItemField.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingItemField.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingItemField.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingItemField.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingItemField.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingItemField.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingItemField.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingItemField.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ShoppingItemFieldWrapper((ShoppingItemField)_shoppingItemField.clone());
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemField.compareTo(shoppingItemField);
	}

	public int hashCode() {
		return _shoppingItemField.hashCode();
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField toEscapedModel() {
		return new ShoppingItemFieldWrapper(_shoppingItemField.toEscapedModel());
	}

	public java.lang.String toString() {
		return _shoppingItemField.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingItemField.toXmlString();
	}

	public java.lang.String[] getValuesArray() {
		return _shoppingItemField.getValuesArray();
	}

	public void setValuesArray(java.lang.String[] valuesArray) {
		_shoppingItemField.setValuesArray(valuesArray);
	}

	public ShoppingItemField getWrappedShoppingItemField() {
		return _shoppingItemField;
	}

	private ShoppingItemField _shoppingItemField;
}