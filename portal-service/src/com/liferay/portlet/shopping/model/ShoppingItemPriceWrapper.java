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
 * <a href="ShoppingItemPriceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingItemPrice}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPrice
 * @generated
 */
public class ShoppingItemPriceWrapper implements ShoppingItemPrice {
	public ShoppingItemPriceWrapper(ShoppingItemPrice shoppingItemPrice) {
		_shoppingItemPrice = shoppingItemPrice;
	}

	public long getPrimaryKey() {
		return _shoppingItemPrice.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingItemPrice.setPrimaryKey(pk);
	}

	public long getItemPriceId() {
		return _shoppingItemPrice.getItemPriceId();
	}

	public void setItemPriceId(long itemPriceId) {
		_shoppingItemPrice.setItemPriceId(itemPriceId);
	}

	public long getItemId() {
		return _shoppingItemPrice.getItemId();
	}

	public void setItemId(long itemId) {
		_shoppingItemPrice.setItemId(itemId);
	}

	public int getMinQuantity() {
		return _shoppingItemPrice.getMinQuantity();
	}

	public void setMinQuantity(int minQuantity) {
		_shoppingItemPrice.setMinQuantity(minQuantity);
	}

	public int getMaxQuantity() {
		return _shoppingItemPrice.getMaxQuantity();
	}

	public void setMaxQuantity(int maxQuantity) {
		_shoppingItemPrice.setMaxQuantity(maxQuantity);
	}

	public double getPrice() {
		return _shoppingItemPrice.getPrice();
	}

	public void setPrice(double price) {
		_shoppingItemPrice.setPrice(price);
	}

	public double getDiscount() {
		return _shoppingItemPrice.getDiscount();
	}

	public void setDiscount(double discount) {
		_shoppingItemPrice.setDiscount(discount);
	}

	public boolean getTaxable() {
		return _shoppingItemPrice.getTaxable();
	}

	public boolean isTaxable() {
		return _shoppingItemPrice.isTaxable();
	}

	public void setTaxable(boolean taxable) {
		_shoppingItemPrice.setTaxable(taxable);
	}

	public double getShipping() {
		return _shoppingItemPrice.getShipping();
	}

	public void setShipping(double shipping) {
		_shoppingItemPrice.setShipping(shipping);
	}

	public boolean getUseShippingFormula() {
		return _shoppingItemPrice.getUseShippingFormula();
	}

	public boolean isUseShippingFormula() {
		return _shoppingItemPrice.isUseShippingFormula();
	}

	public void setUseShippingFormula(boolean useShippingFormula) {
		_shoppingItemPrice.setUseShippingFormula(useShippingFormula);
	}

	public int getStatus() {
		return _shoppingItemPrice.getStatus();
	}

	public void setStatus(int status) {
		_shoppingItemPrice.setStatus(status);
	}

	public com.liferay.portlet.shopping.model.ShoppingItemPrice toEscapedModel() {
		return _shoppingItemPrice.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingItemPrice.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingItemPrice.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingItemPrice.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingItemPrice.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingItemPrice.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingItemPrice.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingItemPrice.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingItemPrice.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingItemPrice.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shoppingItemPrice.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		return _shoppingItemPrice.compareTo(shoppingItemPrice);
	}

	public int hashCode() {
		return _shoppingItemPrice.hashCode();
	}

	public java.lang.String toString() {
		return _shoppingItemPrice.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingItemPrice.toXmlString();
	}

	public ShoppingItemPrice getWrappedShoppingItemPrice() {
		return _shoppingItemPrice;
	}

	private ShoppingItemPrice _shoppingItemPrice;
}