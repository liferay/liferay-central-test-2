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

package com.liferay.portlet.shopping.model;


/**
 * <a href="ShoppingCouponSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCoupon}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCoupon
 * @generated
 */
public class ShoppingCouponWrapper implements ShoppingCoupon {
	public ShoppingCouponWrapper(ShoppingCoupon shoppingCoupon) {
		_shoppingCoupon = shoppingCoupon;
	}

	public long getPrimaryKey() {
		return _shoppingCoupon.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingCoupon.setPrimaryKey(pk);
	}

	public long getCouponId() {
		return _shoppingCoupon.getCouponId();
	}

	public void setCouponId(long couponId) {
		_shoppingCoupon.setCouponId(couponId);
	}

	public long getGroupId() {
		return _shoppingCoupon.getGroupId();
	}

	public void setGroupId(long groupId) {
		_shoppingCoupon.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _shoppingCoupon.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_shoppingCoupon.setCompanyId(companyId);
	}

	public long getUserId() {
		return _shoppingCoupon.getUserId();
	}

	public void setUserId(long userId) {
		_shoppingCoupon.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCoupon.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCoupon.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _shoppingCoupon.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_shoppingCoupon.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _shoppingCoupon.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_shoppingCoupon.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _shoppingCoupon.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingCoupon.setModifiedDate(modifiedDate);
	}

	public java.lang.String getCode() {
		return _shoppingCoupon.getCode();
	}

	public void setCode(java.lang.String code) {
		_shoppingCoupon.setCode(code);
	}

	public java.lang.String getName() {
		return _shoppingCoupon.getName();
	}

	public void setName(java.lang.String name) {
		_shoppingCoupon.setName(name);
	}

	public java.lang.String getDescription() {
		return _shoppingCoupon.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_shoppingCoupon.setDescription(description);
	}

	public java.util.Date getStartDate() {
		return _shoppingCoupon.getStartDate();
	}

	public void setStartDate(java.util.Date startDate) {
		_shoppingCoupon.setStartDate(startDate);
	}

	public java.util.Date getEndDate() {
		return _shoppingCoupon.getEndDate();
	}

	public void setEndDate(java.util.Date endDate) {
		_shoppingCoupon.setEndDate(endDate);
	}

	public boolean getActive() {
		return _shoppingCoupon.getActive();
	}

	public boolean isActive() {
		return _shoppingCoupon.isActive();
	}

	public void setActive(boolean active) {
		_shoppingCoupon.setActive(active);
	}

	public java.lang.String getLimitCategories() {
		return _shoppingCoupon.getLimitCategories();
	}

	public void setLimitCategories(java.lang.String limitCategories) {
		_shoppingCoupon.setLimitCategories(limitCategories);
	}

	public java.lang.String getLimitSkus() {
		return _shoppingCoupon.getLimitSkus();
	}

	public void setLimitSkus(java.lang.String limitSkus) {
		_shoppingCoupon.setLimitSkus(limitSkus);
	}

	public double getMinOrder() {
		return _shoppingCoupon.getMinOrder();
	}

	public void setMinOrder(double minOrder) {
		_shoppingCoupon.setMinOrder(minOrder);
	}

	public double getDiscount() {
		return _shoppingCoupon.getDiscount();
	}

	public void setDiscount(double discount) {
		_shoppingCoupon.setDiscount(discount);
	}

	public java.lang.String getDiscountType() {
		return _shoppingCoupon.getDiscountType();
	}

	public void setDiscountType(java.lang.String discountType) {
		_shoppingCoupon.setDiscountType(discountType);
	}

	public com.liferay.portlet.shopping.model.ShoppingCoupon toEscapedModel() {
		return _shoppingCoupon.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingCoupon.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingCoupon.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingCoupon.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingCoupon.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingCoupon.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingCoupon.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCoupon.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCoupon.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingCoupon.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shoppingCoupon.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon) {
		return _shoppingCoupon.compareTo(shoppingCoupon);
	}

	public int hashCode() {
		return _shoppingCoupon.hashCode();
	}

	public java.lang.String toString() {
		return _shoppingCoupon.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingCoupon.toXmlString();
	}

	public boolean hasValidDateRange() {
		return _shoppingCoupon.hasValidDateRange();
	}

	public boolean hasValidEndDate() {
		return _shoppingCoupon.hasValidEndDate();
	}

	public boolean hasValidStartDate() {
		return _shoppingCoupon.hasValidStartDate();
	}

	public ShoppingCoupon getWrappedShoppingCoupon() {
		return _shoppingCoupon;
	}

	private ShoppingCoupon _shoppingCoupon;
}