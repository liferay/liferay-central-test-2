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

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="ShoppingCouponModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.shopping.model.ShoppingCoupon"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COUPONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.couponId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LIMITCATEGORIES = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.limitCategories"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LIMITSKUS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.limitSkus"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DISCOUNTTYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingCoupon.discountType"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingCouponModel"));

	public ShoppingCouponModel() {
	}

	public String getPrimaryKey() {
		return _couponId;
	}

	public void setPrimaryKey(String pk) {
		setCouponId(pk);
	}

	public String getCouponId() {
		return GetterUtil.getString(_couponId);
	}

	public void setCouponId(String couponId) {
		if (((couponId == null) && (_couponId != null)) ||
				((couponId != null) && (_couponId == null)) ||
				((couponId != null) && (_couponId != null) &&
				!couponId.equals(_couponId))) {
			if (!XSS_ALLOW_COUPONID) {
				couponId = XSSUtil.strip(couponId);
			}

			_couponId = couponId;
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

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		if (((startDate == null) && (_startDate != null)) ||
				((startDate != null) && (_startDate == null)) ||
				((startDate != null) && (_startDate != null) &&
				!startDate.equals(_startDate))) {
			_startDate = startDate;
		}
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		if (((endDate == null) && (_endDate != null)) ||
				((endDate != null) && (_endDate == null)) ||
				((endDate != null) && (_endDate != null) &&
				!endDate.equals(_endDate))) {
			_endDate = endDate;
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
		}
	}

	public String getLimitCategories() {
		return GetterUtil.getString(_limitCategories);
	}

	public void setLimitCategories(String limitCategories) {
		if (((limitCategories == null) && (_limitCategories != null)) ||
				((limitCategories != null) && (_limitCategories == null)) ||
				((limitCategories != null) && (_limitCategories != null) &&
				!limitCategories.equals(_limitCategories))) {
			if (!XSS_ALLOW_LIMITCATEGORIES) {
				limitCategories = XSSUtil.strip(limitCategories);
			}

			_limitCategories = limitCategories;
		}
	}

	public String getLimitSkus() {
		return GetterUtil.getString(_limitSkus);
	}

	public void setLimitSkus(String limitSkus) {
		if (((limitSkus == null) && (_limitSkus != null)) ||
				((limitSkus != null) && (_limitSkus == null)) ||
				((limitSkus != null) && (_limitSkus != null) &&
				!limitSkus.equals(_limitSkus))) {
			if (!XSS_ALLOW_LIMITSKUS) {
				limitSkus = XSSUtil.strip(limitSkus);
			}

			_limitSkus = limitSkus;
		}
	}

	public double getMinOrder() {
		return _minOrder;
	}

	public void setMinOrder(double minOrder) {
		if (minOrder != _minOrder) {
			_minOrder = minOrder;
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

	public String getDiscountType() {
		return GetterUtil.getString(_discountType);
	}

	public void setDiscountType(String discountType) {
		if (((discountType == null) && (_discountType != null)) ||
				((discountType != null) && (_discountType == null)) ||
				((discountType != null) && (_discountType != null) &&
				!discountType.equals(_discountType))) {
			if (!XSS_ALLOW_DISCOUNTTYPE) {
				discountType = XSSUtil.strip(discountType);
			}

			_discountType = discountType;
		}
	}

	public Object clone() {
		ShoppingCoupon clone = new ShoppingCoupon();
		clone.setCouponId(getCouponId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setStartDate(getStartDate());
		clone.setEndDate(getEndDate());
		clone.setActive(getActive());
		clone.setLimitCategories(getLimitCategories());
		clone.setLimitSkus(getLimitSkus());
		clone.setMinOrder(getMinOrder());
		clone.setDiscount(getDiscount());
		clone.setDiscountType(getDiscountType());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingCoupon shoppingCoupon = (ShoppingCoupon)obj;
		int value = 0;
		value = DateUtil.compareTo(getCreateDate(),
				shoppingCoupon.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingCoupon shoppingCoupon = null;

		try {
			shoppingCoupon = (ShoppingCoupon)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = shoppingCoupon.getPrimaryKey();

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

	private String _couponId;
	private String _groupId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private Date _startDate;
	private Date _endDate;
	private boolean _active;
	private String _limitCategories;
	private String _limitSkus;
	private double _minOrder;
	private double _discount;
	private String _discountType;
}