/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.CouponCodeException;
import com.liferay.portlet.shopping.CouponDateException;
import com.liferay.portlet.shopping.CouponDescriptionException;
import com.liferay.portlet.shopping.CouponDiscountException;
import com.liferay.portlet.shopping.CouponEndDateException;
import com.liferay.portlet.shopping.CouponLimitCategoriesException;
import com.liferay.portlet.shopping.CouponLimitSKUsException;
import com.liferay.portlet.shopping.CouponMinimumOrderException;
import com.liferay.portlet.shopping.CouponNameException;
import com.liferay.portlet.shopping.CouponStartDateException;
import com.liferay.portlet.shopping.DuplicateCouponCodeException;
import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.base.ShoppingCouponLocalServiceBaseImpl;
import com.liferay.util.PwdGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingCouponLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Huang Jie
 */
public class ShoppingCouponLocalServiceImpl
	extends ShoppingCouponLocalServiceBaseImpl {

	public ShoppingCoupon addCoupon(
			long userId, String code, boolean autoCode, String name,
			String description, int startDateMonth, int startDateDay,
			int startDateYear, int startDateHour, int startDateMinute,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverExpire, boolean active,
			String limitCategories, String limitSkus, double minOrder,
			double discount, String discountType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		code = code.trim().toUpperCase();

		if (autoCode) {
			code = getCode();
		}

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new CouponStartDateException());

		Date endDate = null;

		if (!neverExpire) {
			endDate = PortalUtil.getDate(
				endDateMonth, endDateDay, endDateYear, endDateHour,
				endDateMinute, user.getTimeZone(),
				new CouponEndDateException());
		}

		if ((endDate != null) && (startDate.after(endDate))) {
			throw new CouponDateException();
		}

		Date now = new Date();

		validate(
			user.getCompanyId(), groupId, code, autoCode, name, description,
			limitCategories, limitSkus, minOrder, discount);

		long couponId = counterLocalService.increment();

		ShoppingCoupon coupon = shoppingCouponPersistence.create(couponId);

		coupon.setGroupId(groupId);
		coupon.setCompanyId(user.getCompanyId());
		coupon.setUserId(user.getUserId());
		coupon.setUserName(user.getFullName());
		coupon.setCreateDate(now);
		coupon.setModifiedDate(now);
		coupon.setCode(code);
		coupon.setName(name);
		coupon.setDescription(description);
		coupon.setStartDate(startDate);
		coupon.setEndDate(endDate);
		coupon.setActive(active);
		coupon.setLimitCategories(limitCategories);
		coupon.setLimitSkus(limitSkus);
		coupon.setMinOrder(minOrder);
		coupon.setDiscount(discount);
		coupon.setDiscountType(discountType);

		shoppingCouponPersistence.update(coupon, false);

		return coupon;
	}

	public void deleteCoupon(long couponId)
		throws PortalException, SystemException {

		shoppingCouponPersistence.remove(couponId);
	}

	public void deleteCoupons(long groupId) throws SystemException {
		shoppingCouponPersistence.removeByGroupId(groupId);
	}

	public ShoppingCoupon getCoupon(long couponId)
		throws PortalException, SystemException {

		return shoppingCouponPersistence.findByPrimaryKey(couponId);
	}

	public ShoppingCoupon getCoupon(String code)
		throws PortalException, SystemException {

		code = code.trim().toUpperCase();

		return shoppingCouponPersistence.findByCode(code);
	}

	public List<ShoppingCoupon> search(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator, int start, int end)
		throws SystemException {

		return shoppingCouponFinder.findByG_C_C_A_DT(
			groupId, companyId, code, active, discountType, andOperator,
			start, end);
	}

	public int searchCount(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator)
		throws SystemException {

		return shoppingCouponFinder.countByG_C_C_A_DT(
			groupId, companyId, code, active, discountType, andOperator);
	}

	public ShoppingCoupon updateCoupon(
			long userId, long couponId, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		ShoppingCoupon coupon = shoppingCouponPersistence.findByPrimaryKey(
			couponId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new CouponStartDateException());

		Date endDate = null;

		if (!neverExpire) {
			endDate = PortalUtil.getDate(
				endDateMonth, endDateDay, endDateYear, endDateHour,
				endDateMinute, user.getTimeZone(),
				new CouponEndDateException());
		}

		if ((endDate != null) && (startDate.after(endDate))) {
			throw new CouponDateException();
		}

		validate(
			coupon.getCompanyId(), coupon.getGroupId(), name, description,
			limitCategories, limitSkus, minOrder, discount);

		coupon.setModifiedDate(new Date());
		coupon.setName(name);
		coupon.setDescription(description);
		coupon.setStartDate(startDate);
		coupon.setEndDate(endDate);
		coupon.setActive(active);
		coupon.setLimitCategories(limitCategories);
		coupon.setLimitSkus(limitSkus);
		coupon.setMinOrder(minOrder);
		coupon.setDiscount(discount);
		coupon.setDiscountType(discountType);

		shoppingCouponPersistence.update(coupon, false);

		return coupon;
	}

	protected String getCode() throws SystemException {
		String code =
			PwdGenerator.getPassword(PwdGenerator.KEY1 + PwdGenerator.KEY2, 8);

		try {
			shoppingCouponPersistence.findByCode(code);

			return getCode();
		}
		catch (NoSuchCouponException nsce) {
			return code;
		}
	}

	protected void validate(
			long companyId, long groupId, String code, boolean autoCode,
			String name, String description, String limitCategories,
			String limitSkus, double minOrder, double discount)
		throws PortalException, SystemException {

		if (!autoCode) {
			if ((Validator.isNull(code)) ||
				(Validator.isNumber(code)) ||
				(code.indexOf(StringPool.SPACE) != -1)) {

				throw new CouponCodeException();
			}

			if (shoppingCouponPersistence.fetchByCode(code) != null) {
				throw new DuplicateCouponCodeException();
			}
		}

		validate(
			companyId, groupId, name, description, limitCategories, limitSkus,
			minOrder, discount);
	}

	protected void validate(
			long companyId, long groupId, String name, String description,
			String limitCategories, String limitSkus, double minOrder,
			double discount)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new CouponNameException();
		}
		else if (Validator.isNull(description)) {
			throw new CouponDescriptionException();
		}

		// Category IDs

		long[] categoryIds = StringUtil.split(limitCategories, 0L);

		List<Long> invalidCategoryIds = new ArrayList<Long>();

		for (long categoryId : categoryIds) {
			ShoppingCategory category =
				shoppingCategoryPersistence.fetchByPrimaryKey(categoryId);

			if ((category == null) || (category.getGroupId() != groupId)) {
				invalidCategoryIds.add(categoryId);
			}
		}

		if (invalidCategoryIds.size() > 0) {
			CouponLimitCategoriesException clce =
				new CouponLimitCategoriesException();

			clce.setCategoryIds(invalidCategoryIds);

			throw clce;
		}

		// SKUs

		String[] skus = StringUtil.split(limitSkus);

		List<String> invalidSkus = new ArrayList<String>();

		for (String sku : skus) {
			ShoppingItem item = shoppingItemPersistence.fetchByC_S(
				companyId, sku);

			if (item != null) {
				ShoppingCategory category = item.getCategory();

				if (category.getGroupId() != groupId) {
					invalidSkus.add(sku);
				}
			}
			else {
				invalidSkus.add(sku);
			}
		}

		if (invalidSkus.size() > 0) {
			CouponLimitSKUsException clskue = new CouponLimitSKUsException();

			clskue.setSkus(invalidSkus);

			throw clskue;
		}

		if (minOrder < 0) {
			throw new CouponMinimumOrderException();
		}

		if (discount < 0) {
			throw new CouponDiscountException();
		}
	}

}