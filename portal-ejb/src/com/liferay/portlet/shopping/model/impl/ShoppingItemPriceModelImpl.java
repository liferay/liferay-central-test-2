/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="ShoppingItemPriceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemPriceModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "ShoppingItemPrice";
	public static Object[][] TABLE_COLUMNS = {
			{ "itemPriceId", new Integer(Types.VARCHAR) },
			{ "itemId", new Integer(Types.VARCHAR) },
			{ "minQuantity", new Integer(Types.INTEGER) },
			{ "maxQuantity", new Integer(Types.INTEGER) },
			{ "price", new Integer(Types.DOUBLE) },
			{ "discount", new Integer(Types.DOUBLE) },
			{ "taxable", new Integer(Types.BOOLEAN) },
			{ "shipping", new Integer(Types.DOUBLE) },
			{ "useShippingFormula", new Integer(Types.BOOLEAN) },
			{ "status", new Integer(Types.INTEGER) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemPrice"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_ITEMPRICEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemPrice.itemPriceId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ITEMID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemPrice.itemId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingItemPriceModel"));

	public ShoppingItemPriceModelImpl() {
	}

	public String getPrimaryKey() {
		return _itemPriceId;
	}

	public void setPrimaryKey(String pk) {
		setItemPriceId(pk);
	}

	public String getItemPriceId() {
		return GetterUtil.getString(_itemPriceId);
	}

	public void setItemPriceId(String itemPriceId) {
		if (((itemPriceId == null) && (_itemPriceId != null)) ||
				((itemPriceId != null) && (_itemPriceId == null)) ||
				((itemPriceId != null) && (_itemPriceId != null) &&
				!itemPriceId.equals(_itemPriceId))) {
			if (!XSS_ALLOW_ITEMPRICEID) {
				itemPriceId = XSSUtil.strip(itemPriceId);
			}

			_itemPriceId = itemPriceId;
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

	public int getMinQuantity() {
		return _minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		if (minQuantity != _minQuantity) {
			_minQuantity = minQuantity;
		}
	}

	public int getMaxQuantity() {
		return _maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		if (maxQuantity != _maxQuantity) {
			_maxQuantity = maxQuantity;
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

	public double getDiscount() {
		return _discount;
	}

	public void setDiscount(double discount) {
		if (discount != _discount) {
			_discount = discount;
		}
	}

	public boolean getTaxable() {
		return _taxable;
	}

	public boolean isTaxable() {
		return _taxable;
	}

	public void setTaxable(boolean taxable) {
		if (taxable != _taxable) {
			_taxable = taxable;
		}
	}

	public double getShipping() {
		return _shipping;
	}

	public void setShipping(double shipping) {
		if (shipping != _shipping) {
			_shipping = shipping;
		}
	}

	public boolean getUseShippingFormula() {
		return _useShippingFormula;
	}

	public boolean isUseShippingFormula() {
		return _useShippingFormula;
	}

	public void setUseShippingFormula(boolean useShippingFormula) {
		if (useShippingFormula != _useShippingFormula) {
			_useShippingFormula = useShippingFormula;
		}
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		if (status != _status) {
			_status = status;
		}
	}

	public Object clone() {
		ShoppingItemPriceImpl clone = new ShoppingItemPriceImpl();
		clone.setItemPriceId(getItemPriceId());
		clone.setItemId(getItemId());
		clone.setMinQuantity(getMinQuantity());
		clone.setMaxQuantity(getMaxQuantity());
		clone.setPrice(getPrice());
		clone.setDiscount(getDiscount());
		clone.setTaxable(getTaxable());
		clone.setShipping(getShipping());
		clone.setUseShippingFormula(getUseShippingFormula());
		clone.setStatus(getStatus());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingItemPriceImpl shoppingItemPrice = (ShoppingItemPriceImpl)obj;
		int value = 0;
		value = getItemId().compareTo(shoppingItemPrice.getItemId());

		if (value != 0) {
			return value;
		}

		value = getItemPriceId().compareTo(shoppingItemPrice.getItemPriceId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingItemPriceImpl shoppingItemPrice = null;

		try {
			shoppingItemPrice = (ShoppingItemPriceImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = shoppingItemPrice.getPrimaryKey();

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

	private String _itemPriceId;
	private String _itemId;
	private int _minQuantity;
	private int _maxQuantity;
	private double _price;
	private double _discount;
	private boolean _taxable;
	private double _shipping;
	private boolean _useShippingFormula;
	private int _status;
}