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

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="ShoppingOrderItemModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingOrderItem table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrderItem
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingOrderItemImpl
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingOrderItemModelImpl
 * @generated
 */
public interface ShoppingOrderItemModel extends BaseModel<ShoppingOrderItem> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrderItemId();

	public void setOrderItemId(long orderItemId);

	public long getOrderId();

	public void setOrderId(long orderId);

	@AutoEscape
	public String getItemId();

	public void setItemId(String itemId);

	@AutoEscape
	public String getSku();

	public void setSku(String sku);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getDescription();

	public void setDescription(String description);

	@AutoEscape
	public String getProperties();

	public void setProperties(String properties);

	public double getPrice();

	public void setPrice(double price);

	public int getQuantity();

	public void setQuantity(int quantity);

	public Date getShippedDate();

	public void setShippedDate(Date shippedDate);

	public ShoppingOrderItem toEscapedModel();

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

	public int compareTo(ShoppingOrderItem shoppingOrderItem);

	public int hashCode();

	public String toString();

	public String toXmlString();
}