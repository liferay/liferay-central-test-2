/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="ShoppingOrderItemModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_ORDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.orderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ITEMID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.itemId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SKU = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.sku"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PROPERTIES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrderItem.properties"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingOrderItemModel"));

	public ShoppingOrderItemModel() {
	}

	public ShoppingOrderItemPK getPrimaryKey() {
		return new ShoppingOrderItemPK(_orderId, _itemId);
	}

	public void setPrimaryKey(ShoppingOrderItemPK pk) {
		setOrderId(pk.orderId);
		setItemId(pk.itemId);
	}

	public String getOrderId() {
		return GetterUtil.getString(_orderId);
	}

	public void setOrderId(String orderId) {
		if (((orderId == null) && (_orderId != null)) ||
				((orderId != null) && (_orderId == null)) ||
				((orderId != null) && (_orderId != null) &&
				!orderId.equals(_orderId))) {
			if (!XSS_ALLOW_ORDERID) {
				orderId = XSSUtil.strip(orderId);
			}

			_orderId = orderId;
		}
	}

	public String getItemId() {
		return GetterUtil.getString(_itemId);
	}

	public void setItemId(String itemId) {
		if (((itemId == null) && (_itemId != null)) ||
				((itemId != null) && (_itemId == null)) ||
				((itemId != null) && (_itemId != null) &&
				!itemId.equals(_itemId))) {
			if (!XSS_ALLOW_ITEMID) {
				itemId = XSSUtil.strip(itemId);
			}

			_itemId = itemId;
		}
	}

	public String getSku() {
		return GetterUtil.getString(_sku);
	}

	public void setSku(String sku) {
		if (((sku == null) && (_sku != null)) ||
				((sku != null) && (_sku == null)) ||
				((sku != null) && (_sku != null) && !sku.equals(_sku))) {
			if (!XSS_ALLOW_SKU) {
				sku = XSSUtil.strip(sku);
			}

			_sku = sku;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public String getProperties() {
		return GetterUtil.getString(_properties);
	}

	public void setProperties(String properties) {
		if (((properties == null) && (_properties != null)) ||
				((properties != null) && (_properties == null)) ||
				((properties != null) && (_properties != null) &&
				!properties.equals(_properties))) {
			if (!XSS_ALLOW_PROPERTIES) {
				properties = XSSUtil.strip(properties);
			}

			_properties = properties;
		}
	}

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		if (price != _price) {
			_price = price;
		}
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity != _quantity) {
			_quantity = quantity;
		}
	}

	public Date getShippedDate() {
		return _shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		if (((shippedDate == null) && (_shippedDate != null)) ||
				((shippedDate != null) && (_shippedDate == null)) ||
				((shippedDate != null) && (_shippedDate != null) &&
				!shippedDate.equals(_shippedDate))) {
			_shippedDate = shippedDate;
		}
	}

	public Object clone() {
		ShoppingOrderItem clone = new ShoppingOrderItem();
		clone.setOrderId(getOrderId());
		clone.setItemId(getItemId());
		clone.setSku(getSku());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setProperties(getProperties());
		clone.setPrice(getPrice());
		clone.setQuantity(getQuantity());
		clone.setShippedDate(getShippedDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)obj;
		int value = 0;
		value = getName().compareTo(shoppingOrderItem.getName());

		if (value != 0) {
			return value;
		}

		value = getDescription().compareTo(shoppingOrderItem.getDescription());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingOrderItem shoppingOrderItem = null;

		try {
			shoppingOrderItem = (ShoppingOrderItem)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		ShoppingOrderItemPK pk = shoppingOrderItem.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _orderId;
	private String _itemId;
	private String _sku;
	private String _name;
	private String _description;
	private String _properties;
	private double _price;
	private int _quantity;
	private Date _shippedDate;
}