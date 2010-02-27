/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="ShoppingItemModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingItem table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItem
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingItemImpl
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl
 * @generated
 */
public interface ShoppingItemModel extends BaseModel<ShoppingItem> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getItemId();

	public void setItemId(long itemId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public long getCategoryId();

	public void setCategoryId(long categoryId);

	public String getSku();

	public void setSku(String sku);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public String getProperties();

	public void setProperties(String properties);

	public boolean getFields();

	public boolean isFields();

	public void setFields(boolean fields);

	public String getFieldsQuantities();

	public void setFieldsQuantities(String fieldsQuantities);

	public int getMinQuantity();

	public void setMinQuantity(int minQuantity);

	public int getMaxQuantity();

	public void setMaxQuantity(int maxQuantity);

	public double getPrice();

	public void setPrice(double price);

	public double getDiscount();

	public void setDiscount(double discount);

	public boolean getTaxable();

	public boolean isTaxable();

	public void setTaxable(boolean taxable);

	public double getShipping();

	public void setShipping(double shipping);

	public boolean getUseShippingFormula();

	public boolean isUseShippingFormula();

	public void setUseShippingFormula(boolean useShippingFormula);

	public boolean getRequiresShipping();

	public boolean isRequiresShipping();

	public void setRequiresShipping(boolean requiresShipping);

	public int getStockQuantity();

	public void setStockQuantity(int stockQuantity);

	public boolean getFeatured();

	public boolean isFeatured();

	public void setFeatured(boolean featured);

	public boolean getSale();

	public boolean isSale();

	public void setSale(boolean sale);

	public boolean getSmallImage();

	public boolean isSmallImage();

	public void setSmallImage(boolean smallImage);

	public long getSmallImageId();

	public void setSmallImageId(long smallImageId);

	public String getSmallImageURL();

	public void setSmallImageURL(String smallImageURL);

	public boolean getMediumImage();

	public boolean isMediumImage();

	public void setMediumImage(boolean mediumImage);

	public long getMediumImageId();

	public void setMediumImageId(long mediumImageId);

	public String getMediumImageURL();

	public void setMediumImageURL(String mediumImageURL);

	public boolean getLargeImage();

	public boolean isLargeImage();

	public void setLargeImage(boolean largeImage);

	public long getLargeImageId();

	public void setLargeImageId(long largeImageId);

	public String getLargeImageURL();

	public void setLargeImageURL(String largeImageURL);

	public ShoppingItem toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(ShoppingItem shoppingItem);

	public int hashCode();

	public String toString();

	public String toXmlString();
}