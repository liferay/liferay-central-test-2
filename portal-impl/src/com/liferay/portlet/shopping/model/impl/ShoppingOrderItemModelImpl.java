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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;
import com.liferay.portlet.shopping.model.ShoppingOrderItemSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingOrderItemModelImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see       ShoppingOrderItemImpl
 * @see       com.liferay.portlet.shopping.model.ShoppingOrderItem
 * @see       com.liferay.portlet.shopping.model.ShoppingOrderItemModel
 * @generated
 */
public class ShoppingOrderItemModelImpl extends BaseModelImpl<ShoppingOrderItem> {
	public static final String TABLE_NAME = "ShoppingOrderItem";
	public static final Object[][] TABLE_COLUMNS = {
			{ "orderItemId", new Integer(Types.BIGINT) },
			{ "orderId", new Integer(Types.BIGINT) },
			{ "itemId", new Integer(Types.VARCHAR) },
			{ "sku", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "properties", new Integer(Types.VARCHAR) },
			{ "price", new Integer(Types.DOUBLE) },
			{ "quantity", new Integer(Types.INTEGER) },
			{ "shippedDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingOrderItem (orderItemId LONG not null primary key,orderId LONG,itemId VARCHAR(75) null,sku VARCHAR(75) null,name VARCHAR(200) null,description STRING null,properties STRING null,price DOUBLE,quantity INTEGER,shippedDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingOrderItem";
	public static final String ORDER_BY_JPQL = " ORDER BY shoppingOrderItem.name ASC, shoppingOrderItem.description ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ShoppingOrderItem.name ASC, ShoppingOrderItem.description ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrderItem"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrderItem"),
			true);

	public static ShoppingOrderItem toModel(ShoppingOrderItemSoap soapModel) {
		ShoppingOrderItem model = new ShoppingOrderItemImpl();

		model.setOrderItemId(soapModel.getOrderItemId());
		model.setOrderId(soapModel.getOrderId());
		model.setItemId(soapModel.getItemId());
		model.setSku(soapModel.getSku());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setProperties(soapModel.getProperties());
		model.setPrice(soapModel.getPrice());
		model.setQuantity(soapModel.getQuantity());
		model.setShippedDate(soapModel.getShippedDate());

		return model;
	}

	public static List<ShoppingOrderItem> toModels(
		ShoppingOrderItemSoap[] soapModels) {
		List<ShoppingOrderItem> models = new ArrayList<ShoppingOrderItem>(soapModels.length);

		for (ShoppingOrderItemSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingOrderItem"));

	public ShoppingOrderItemModelImpl() {
	}

	public long getPrimaryKey() {
		return _orderItemId;
	}

	public void setPrimaryKey(long pk) {
		setOrderItemId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_orderItemId);
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		_orderItemId = orderItemId;
	}

	public long getOrderId() {
		return _orderId;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	public String getItemId() {
		return GetterUtil.getString(_itemId);
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public String getSku() {
		return GetterUtil.getString(_sku);
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getProperties() {
		return GetterUtil.getString(_properties);
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		_price = price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getShippedDate() {
		return _shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		_shippedDate = shippedDate;
	}

	public ShoppingOrderItem toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingOrderItem)this;
		}
		else {
			ShoppingOrderItem model = new ShoppingOrderItemImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setOrderItemId(getOrderItemId());
			model.setOrderId(getOrderId());
			model.setItemId(HtmlUtil.escape(getItemId()));
			model.setSku(HtmlUtil.escape(getSku()));
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setProperties(HtmlUtil.escape(getProperties()));
			model.setPrice(getPrice());
			model.setQuantity(getQuantity());
			model.setShippedDate(getShippedDate());

			model = (ShoppingOrderItem)Proxy.newProxyInstance(ShoppingOrderItem.class.getClassLoader(),
					new Class[] { ShoppingOrderItem.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					ShoppingOrderItem.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ShoppingOrderItemImpl clone = new ShoppingOrderItemImpl();

		clone.setOrderItemId(getOrderItemId());
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

	public int compareTo(ShoppingOrderItem shoppingOrderItem) {
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

		long pk = shoppingOrderItem.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{orderItemId=");
		sb.append(getOrderItemId());
		sb.append(", orderId=");
		sb.append(getOrderId());
		sb.append(", itemId=");
		sb.append(getItemId());
		sb.append(", sku=");
		sb.append(getSku());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", properties=");
		sb.append(getProperties());
		sb.append(", price=");
		sb.append(getPrice());
		sb.append(", quantity=");
		sb.append(getQuantity());
		sb.append(", shippedDate=");
		sb.append(getShippedDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.shopping.model.ShoppingOrderItem");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>orderItemId</column-name><column-value><![CDATA[");
		sb.append(getOrderItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>orderId</column-name><column-value><![CDATA[");
		sb.append(getOrderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>itemId</column-name><column-value><![CDATA[");
		sb.append(getItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sku</column-name><column-value><![CDATA[");
		sb.append(getSku());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>properties</column-name><column-value><![CDATA[");
		sb.append(getProperties());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>price</column-name><column-value><![CDATA[");
		sb.append(getPrice());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quantity</column-name><column-value><![CDATA[");
		sb.append(getQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>shippedDate</column-name><column-value><![CDATA[");
		sb.append(getShippedDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _orderItemId;
	private long _orderId;
	private String _itemId;
	private String _sku;
	private String _name;
	private String _description;
	private String _properties;
	private double _price;
	private int _quantity;
	private Date _shippedDate;
	private transient ExpandoBridge _expandoBridge;
}