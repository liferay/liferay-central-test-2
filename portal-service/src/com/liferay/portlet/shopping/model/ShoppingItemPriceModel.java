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

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="ShoppingItemPriceModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingItemPrice table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemPrice
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingItemPriceImpl
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl
 * @generated
 */
public interface ShoppingItemPriceModel extends BaseModel<ShoppingItemPrice> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getItemPriceId();

	public void setItemPriceId(long itemPriceId);

	public long getItemId();

	public void setItemId(long itemId);

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

	public int getStatus();

	public void setStatus(int status);

	public ShoppingItemPrice toEscapedModel();

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

	public int compareTo(ShoppingItemPrice shoppingItemPrice);

	public int hashCode();

	public String toString();

	public String toXmlString();
}