/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="ShoppingCartModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.shopping.model.ShoppingCart"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_CARTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.cartId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ITEMIDS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.itemIds"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COUPONIDS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCart.couponIds"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingCartModel"));

	public ShoppingCartModel() {
	}

	public String getPrimaryKey() {
		return _cartId;
	}

	public void setPrimaryKey(String pk) {
		setCartId(pk);
	}

	public String getCartId() {
		return GetterUtil.getString(_cartId);
	}

	public void setCartId(String cartId) {
		if (((cartId == null) && (_cartId != null)) ||
				((cartId != null) && (_cartId == null)) ||
				((cartId != null) && (_cartId != null) &&
				!cartId.equals(_cartId))) {
			if (!XSS_ALLOW_CARTID) {
				cartId = XSSUtil.strip(cartId);
			}

			_cartId = cartId;
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getItemIds() {
		return GetterUtil.getString(_itemIds);
	}

	public void setItemIds(String itemIds) {
		if (((itemIds == null) && (_itemIds != null)) ||
				((itemIds != null) && (_itemIds == null)) ||
				((itemIds != null) && (_itemIds != null) &&
				!itemIds.equals(_itemIds))) {
			if (!XSS_ALLOW_ITEMIDS) {
				itemIds = XSSUtil.strip(itemIds);
			}

			_itemIds = itemIds;
		}
	}

	public String getCouponIds() {
		return GetterUtil.getString(_couponIds);
	}

	public void setCouponIds(String couponIds) {
		if (((couponIds == null) && (_couponIds != null)) ||
				((couponIds != null) && (_couponIds == null)) ||
				((couponIds != null) && (_couponIds != null) &&
				!couponIds.equals(_couponIds))) {
			if (!XSS_ALLOW_COUPONIDS) {
				couponIds = XSSUtil.strip(couponIds);
			}

			_couponIds = couponIds;
		}
	}

	public int getAltShipping() {
		return _altShipping;
	}

	public void setAltShipping(int altShipping) {
		if (altShipping != _altShipping) {
			_altShipping = altShipping;
		}
	}

	public boolean getInsure() {
		return _insure;
	}

	public boolean isInsure() {
		return _insure;
	}

	public void setInsure(boolean insure) {
		if (insure != _insure) {
			_insure = insure;
		}
	}

	public Object clone() {
		ShoppingCart clone = new ShoppingCart();
		clone.setCartId(getCartId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setItemIds(getItemIds());
		clone.setCouponIds(getCouponIds());
		clone.setAltShipping(getAltShipping());
		clone.setInsure(getInsure());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingCart shoppingCart = (ShoppingCart)obj;
		String pk = shoppingCart.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingCart shoppingCart = null;

		try {
			shoppingCart = (ShoppingCart)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = shoppingCart.getPrimaryKey();

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

	private String _cartId;
	private String _groupId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _itemIds;
	private String _couponIds;
	private int _altShipping;
	private boolean _insure;
}