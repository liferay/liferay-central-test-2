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
 * <a href="ShoppingOrderItemSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingOrderItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderItem
 * @generated
 */
public class ShoppingOrderItemWrapper implements ShoppingOrderItem {
	public ShoppingOrderItemWrapper(ShoppingOrderItem shoppingOrderItem) {
		_shoppingOrderItem = shoppingOrderItem;
	}

	public long getPrimaryKey() {
		return _shoppingOrderItem.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingOrderItem.setPrimaryKey(pk);
	}

	public long getOrderItemId() {
		return _shoppingOrderItem.getOrderItemId();
	}

	public void setOrderItemId(long orderItemId) {
		_shoppingOrderItem.setOrderItemId(orderItemId);
	}

	public long getOrderId() {
		return _shoppingOrderItem.getOrderId();
	}

	public void setOrderId(long orderId) {
		_shoppingOrderItem.setOrderId(orderId);
	}

	public java.lang.String getItemId() {
		return _shoppingOrderItem.getItemId();
	}

	public void setItemId(java.lang.String itemId) {
		_shoppingOrderItem.setItemId(itemId);
	}

	public java.lang.String getSku() {
		return _shoppingOrderItem.getSku();
	}

	public void setSku(java.lang.String sku) {
		_shoppingOrderItem.setSku(sku);
	}

	public java.lang.String getName() {
		return _shoppingOrderItem.getName();
	}

	public void setName(java.lang.String name) {
		_shoppingOrderItem.setName(name);
	}

	public java.lang.String getDescription() {
		return _shoppingOrderItem.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_shoppingOrderItem.setDescription(description);
	}

	public java.lang.String getProperties() {
		return _shoppingOrderItem.getProperties();
	}

	public void setProperties(java.lang.String properties) {
		_shoppingOrderItem.setProperties(properties);
	}

	public double getPrice() {
		return _shoppingOrderItem.getPrice();
	}

	public void setPrice(double price) {
		_shoppingOrderItem.setPrice(price);
	}

	public int getQuantity() {
		return _shoppingOrderItem.getQuantity();
	}

	public void setQuantity(int quantity) {
		_shoppingOrderItem.setQuantity(quantity);
	}

	public java.util.Date getShippedDate() {
		return _shoppingOrderItem.getShippedDate();
	}

	public void setShippedDate(java.util.Date shippedDate) {
		_shoppingOrderItem.setShippedDate(shippedDate);
	}

	public com.liferay.portlet.shopping.model.ShoppingOrderItem toEscapedModel() {
		return _shoppingOrderItem.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingOrderItem.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingOrderItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingOrderItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingOrderItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingOrderItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingOrderItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingOrderItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingOrderItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shoppingOrderItem.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem) {
		return _shoppingOrderItem.compareTo(shoppingOrderItem);
	}

	public int hashCode() {
		return _shoppingOrderItem.hashCode();
	}

	public java.lang.String toString() {
		return _shoppingOrderItem.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingOrderItem.toXmlString();
	}

	public ShoppingOrderItem getWrappedShoppingOrderItem() {
		return _shoppingOrderItem;
	}

	private ShoppingOrderItem _shoppingOrderItem;
}