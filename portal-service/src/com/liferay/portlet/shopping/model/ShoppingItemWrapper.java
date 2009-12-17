/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * <a href="ShoppingItemSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItem
 * @generated
 */
public class ShoppingItemWrapper implements ShoppingItem {
	public ShoppingItemWrapper(ShoppingItem shoppingItem) {
		_shoppingItem = shoppingItem;
	}

	public long getPrimaryKey() {
		return _shoppingItem.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingItem.setPrimaryKey(pk);
	}

	public long getItemId() {
		return _shoppingItem.getItemId();
	}

	public void setItemId(long itemId) {
		_shoppingItem.setItemId(itemId);
	}

	public long getGroupId() {
		return _shoppingItem.getGroupId();
	}

	public void setGroupId(long groupId) {
		_shoppingItem.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _shoppingItem.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_shoppingItem.setCompanyId(companyId);
	}

	public long getUserId() {
		return _shoppingItem.getUserId();
	}

	public void setUserId(long userId) {
		_shoppingItem.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _shoppingItem.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_shoppingItem.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _shoppingItem.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_shoppingItem.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _shoppingItem.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_shoppingItem.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _shoppingItem.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingItem.setModifiedDate(modifiedDate);
	}

	public long getCategoryId() {
		return _shoppingItem.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_shoppingItem.setCategoryId(categoryId);
	}

	public java.lang.String getSku() {
		return _shoppingItem.getSku();
	}

	public void setSku(java.lang.String sku) {
		_shoppingItem.setSku(sku);
	}

	public java.lang.String getName() {
		return _shoppingItem.getName();
	}

	public void setName(java.lang.String name) {
		_shoppingItem.setName(name);
	}

	public java.lang.String getDescription() {
		return _shoppingItem.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_shoppingItem.setDescription(description);
	}

	public java.lang.String getProperties() {
		return _shoppingItem.getProperties();
	}

	public void setProperties(java.lang.String properties) {
		_shoppingItem.setProperties(properties);
	}

	public boolean getFields() {
		return _shoppingItem.getFields();
	}

	public boolean isFields() {
		return _shoppingItem.isFields();
	}

	public void setFields(boolean fields) {
		_shoppingItem.setFields(fields);
	}

	public java.lang.String getFieldsQuantities() {
		return _shoppingItem.getFieldsQuantities();
	}

	public void setFieldsQuantities(java.lang.String fieldsQuantities) {
		_shoppingItem.setFieldsQuantities(fieldsQuantities);
	}

	public int getMinQuantity() {
		return _shoppingItem.getMinQuantity();
	}

	public void setMinQuantity(int minQuantity) {
		_shoppingItem.setMinQuantity(minQuantity);
	}

	public int getMaxQuantity() {
		return _shoppingItem.getMaxQuantity();
	}

	public void setMaxQuantity(int maxQuantity) {
		_shoppingItem.setMaxQuantity(maxQuantity);
	}

	public double getPrice() {
		return _shoppingItem.getPrice();
	}

	public void setPrice(double price) {
		_shoppingItem.setPrice(price);
	}

	public double getDiscount() {
		return _shoppingItem.getDiscount();
	}

	public void setDiscount(double discount) {
		_shoppingItem.setDiscount(discount);
	}

	public boolean getTaxable() {
		return _shoppingItem.getTaxable();
	}

	public boolean isTaxable() {
		return _shoppingItem.isTaxable();
	}

	public void setTaxable(boolean taxable) {
		_shoppingItem.setTaxable(taxable);
	}

	public double getShipping() {
		return _shoppingItem.getShipping();
	}

	public void setShipping(double shipping) {
		_shoppingItem.setShipping(shipping);
	}

	public boolean getUseShippingFormula() {
		return _shoppingItem.getUseShippingFormula();
	}

	public boolean isUseShippingFormula() {
		return _shoppingItem.isUseShippingFormula();
	}

	public void setUseShippingFormula(boolean useShippingFormula) {
		_shoppingItem.setUseShippingFormula(useShippingFormula);
	}

	public boolean getRequiresShipping() {
		return _shoppingItem.getRequiresShipping();
	}

	public boolean isRequiresShipping() {
		return _shoppingItem.isRequiresShipping();
	}

	public void setRequiresShipping(boolean requiresShipping) {
		_shoppingItem.setRequiresShipping(requiresShipping);
	}

	public int getStockQuantity() {
		return _shoppingItem.getStockQuantity();
	}

	public void setStockQuantity(int stockQuantity) {
		_shoppingItem.setStockQuantity(stockQuantity);
	}

	public boolean getFeatured() {
		return _shoppingItem.getFeatured();
	}

	public boolean isFeatured() {
		return _shoppingItem.isFeatured();
	}

	public void setFeatured(boolean featured) {
		_shoppingItem.setFeatured(featured);
	}

	public boolean getSale() {
		return _shoppingItem.getSale();
	}

	public boolean isSale() {
		return _shoppingItem.isSale();
	}

	public void setSale(boolean sale) {
		_shoppingItem.setSale(sale);
	}

	public boolean getSmallImage() {
		return _shoppingItem.getSmallImage();
	}

	public boolean isSmallImage() {
		return _shoppingItem.isSmallImage();
	}

	public void setSmallImage(boolean smallImage) {
		_shoppingItem.setSmallImage(smallImage);
	}

	public long getSmallImageId() {
		return _shoppingItem.getSmallImageId();
	}

	public void setSmallImageId(long smallImageId) {
		_shoppingItem.setSmallImageId(smallImageId);
	}

	public java.lang.String getSmallImageURL() {
		return _shoppingItem.getSmallImageURL();
	}

	public void setSmallImageURL(java.lang.String smallImageURL) {
		_shoppingItem.setSmallImageURL(smallImageURL);
	}

	public boolean getMediumImage() {
		return _shoppingItem.getMediumImage();
	}

	public boolean isMediumImage() {
		return _shoppingItem.isMediumImage();
	}

	public void setMediumImage(boolean mediumImage) {
		_shoppingItem.setMediumImage(mediumImage);
	}

	public long getMediumImageId() {
		return _shoppingItem.getMediumImageId();
	}

	public void setMediumImageId(long mediumImageId) {
		_shoppingItem.setMediumImageId(mediumImageId);
	}

	public java.lang.String getMediumImageURL() {
		return _shoppingItem.getMediumImageURL();
	}

	public void setMediumImageURL(java.lang.String mediumImageURL) {
		_shoppingItem.setMediumImageURL(mediumImageURL);
	}

	public boolean getLargeImage() {
		return _shoppingItem.getLargeImage();
	}

	public boolean isLargeImage() {
		return _shoppingItem.isLargeImage();
	}

	public void setLargeImage(boolean largeImage) {
		_shoppingItem.setLargeImage(largeImage);
	}

	public long getLargeImageId() {
		return _shoppingItem.getLargeImageId();
	}

	public void setLargeImageId(long largeImageId) {
		_shoppingItem.setLargeImageId(largeImageId);
	}

	public java.lang.String getLargeImageURL() {
		return _shoppingItem.getLargeImageURL();
	}

	public void setLargeImageURL(java.lang.String largeImageURL) {
		_shoppingItem.setLargeImageURL(largeImageURL);
	}

	public com.liferay.portlet.shopping.model.ShoppingItem toEscapedModel() {
		return _shoppingItem.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingItem.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shoppingItem.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem) {
		return _shoppingItem.compareTo(shoppingItem);
	}

	public int hashCode() {
		return _shoppingItem.hashCode();
	}

	public java.lang.String toString() {
		return _shoppingItem.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingItem.toXmlString();
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory() {
		return _shoppingItem.getCategory();
	}

	public java.lang.String[] getFieldsQuantitiesArray() {
		return _shoppingItem.getFieldsQuantitiesArray();
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getItemPrices()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingItem.getItemPrices();
	}

	public void setFieldsQuantitiesArray(
		java.lang.String[] fieldsQuantitiesArray) {
		_shoppingItem.setFieldsQuantitiesArray(fieldsQuantitiesArray);
	}

	public ShoppingItem getWrappedShoppingItem() {
		return _shoppingItem;
	}

	private ShoppingItem _shoppingItem;
}