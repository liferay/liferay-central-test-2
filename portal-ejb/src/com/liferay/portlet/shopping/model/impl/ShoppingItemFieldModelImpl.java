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
 * <a href="ShoppingItemFieldModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ShoppingItemField</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.model.ShoppingItemField
 * @see com.liferay.portlet.shopping.service.model.ShoppingItemFieldModel
 * @see com.liferay.portlet.shopping.service.model.impl.ShoppingItemFieldImpl
 *
 */
public class ShoppingItemFieldModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "ShoppingItemField";
	public static Object[][] TABLE_COLUMNS = {
			{ "itemFieldId", new Integer(Types.VARCHAR) },
			{ "itemId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "values_", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_ITEMFIELDID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField.itemFieldId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ITEMID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField.itemId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VALUES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField.values"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingItemField.description"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingItemFieldModel"));

	public ShoppingItemFieldModelImpl() {
	}

	public String getPrimaryKey() {
		return _itemFieldId;
	}

	public void setPrimaryKey(String pk) {
		setItemFieldId(pk);
	}

	public String getItemFieldId() {
		return GetterUtil.getString(_itemFieldId);
	}

	public void setItemFieldId(String itemFieldId) {
		if (((itemFieldId == null) && (_itemFieldId != null)) ||
				((itemFieldId != null) && (_itemFieldId == null)) ||
				((itemFieldId != null) && (_itemFieldId != null) &&
				!itemFieldId.equals(_itemFieldId))) {
			if (!XSS_ALLOW_ITEMFIELDID) {
				itemFieldId = XSSUtil.strip(itemFieldId);
			}

			_itemFieldId = itemFieldId;
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

	public String getValues() {
		return GetterUtil.getString(_values);
	}

	public void setValues(String values) {
		if (((values == null) && (_values != null)) ||
				((values != null) && (_values == null)) ||
				((values != null) && (_values != null) &&
				!values.equals(_values))) {
			if (!XSS_ALLOW_VALUES) {
				values = XSSUtil.strip(values);
			}

			_values = values;
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

	public Object clone() {
		ShoppingItemFieldImpl clone = new ShoppingItemFieldImpl();
		clone.setItemFieldId(getItemFieldId());
		clone.setItemId(getItemId());
		clone.setName(getName());
		clone.setValues(getValues());
		clone.setDescription(getDescription());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingItemFieldImpl shoppingItemField = (ShoppingItemFieldImpl)obj;
		int value = 0;
		value = getItemId().compareTo(shoppingItemField.getItemId());

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase().compareTo(shoppingItemField.getName()
																   .toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingItemFieldImpl shoppingItemField = null;

		try {
			shoppingItemField = (ShoppingItemFieldImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = shoppingItemField.getPrimaryKey();

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

	private String _itemFieldId;
	private String _itemId;
	private String _name;
	private String _values;
	private String _description;
}