/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.shopping.model;


/**
 * <a href="ShoppingItemFieldSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public long getPrimaryKey() {
		return _shoppingItemField.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingItemField.setPrimaryKey(pk);
	}

	public long getItemFieldId() {
		return _shoppingItemField.getItemFieldId();
	}

	public void setItemFieldId(long itemFieldId) {
		_shoppingItemField.setItemFieldId(itemFieldId);
	}

	public long getItemId() {
		return _shoppingItemField.getItemId();
	}

	public void setItemId(long itemId) {
		_shoppingItemField.setItemId(itemId);
	}

	public java.lang.String getName() {
		return _shoppingItemField.getName();
	}

	public void setName(java.lang.String name) {
		_shoppingItemField.setName(name);
	}

	public java.lang.String getValues() {
		return _shoppingItemField.getValues();
	}

	public void setValues(java.lang.String values) {
		_shoppingItemField.setValues(values);
	}

	public java.lang.String getDescription() {
		return _shoppingItemField.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_shoppingItemField.setDescription(description);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemField toEscapedModel() {
		return _shoppingItemField.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingItemField.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingItemField.setNew(n);
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
		return _shoppingItemField.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemField.compareTo(shoppingItemField);
	}

	public int hashCode() {
		return _shoppingItemField.hashCode();
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