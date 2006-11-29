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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.CouponDateException;
import com.liferay.portlet.shopping.CouponDescriptionException;
import com.liferay.portlet.shopping.CouponEndDateException;
import com.liferay.portlet.shopping.CouponIdException;
import com.liferay.portlet.shopping.CouponLimitCategoriesException;
import com.liferay.portlet.shopping.CouponLimitSKUsException;
import com.liferay.portlet.shopping.CouponNameException;
import com.liferay.portlet.shopping.CouponStartDateException;
import com.liferay.portlet.shopping.DuplicateCouponIdException;
import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalService;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemUtil;
import com.liferay.util.PwdGenerator;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingCouponLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponLocalServiceImpl
	implements ShoppingCouponLocalService {

	public ShoppingCoupon addCoupon(
			String userId, String plid, String couponId, boolean autoCouponId,
			String name, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType)
		throws PortalException, SystemException {

		// Coupon

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);

		couponId = couponId.trim().toUpperCase();

		if (autoCouponId) {
			couponId = getCouponId();
		}

		Date now = new Date();

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
			user.getCompanyId(), groupId, couponId, autoCouponId, name,
			description, limitCategories, limitSkus);

		ShoppingCoupon coupon = ShoppingCouponUtil.create(couponId);

		coupon.setGroupId(groupId);
		coupon.setCompanyId(user.getCompanyId());
		coupon.setUserId(user.getUserId());
		coupon.setUserName(user.getFullName());
		coupon.setCreateDate(now);
		coupon.setModifiedDate(now);
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

		ShoppingCouponUtil.update(coupon);

		return coupon;
	}

	public void deleteCoupon(String couponId)
		throws PortalException, SystemException {

		couponId = couponId.trim().toUpperCase();

		ShoppingCouponUtil.remove(couponId);
	}

	public void deleteCoupons(String groupId) throws SystemException {
		ShoppingCouponUtil.removeByGroupId(groupId);
	}

	public ShoppingCoupon getCoupon(String couponId)
		throws PortalException, SystemException {

		couponId = couponId.trim().toUpperCase();

		return ShoppingCouponUtil.findByPrimaryKey(couponId);
	}

	public List search(
			String couponId, String plid, String companyId, boolean active,
			String discountType, boolean andOperator, int begin, int end)
		throws SystemException {

		String groupId = PortalUtil.getPortletGroupId(plid);

		return ShoppingCouponFinder.findByC_G_C_A_DT(
			couponId, groupId, companyId, active, discountType, andOperator,
			begin, end);
	}

	public int searchCount(
			String couponId, String groupId, String companyId, boolean active,
			String discountType, boolean andOperator)
		throws SystemException {

		return ShoppingCouponFinder.countByC_G_C_A_DT(
			couponId, groupId, companyId, active, discountType, andOperator);
	}

	public ShoppingCoupon updateCoupon(
			String userId, String couponId, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		couponId = couponId.trim().toUpperCase();

		ShoppingCoupon coupon = ShoppingCouponUtil.findByPrimaryKey(couponId);

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
			limitCategories, limitSkus);

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

		ShoppingCouponUtil.update(coupon);

		return coupon;
	}

	protected String getCouponId() throws SystemException {
		String couponId =
			PwdGenerator.getPassword(PwdGenerator.KEY1 + PwdGenerator.KEY2, 8);

		try {
			ShoppingCouponUtil.findByPrimaryKey(couponId);

			return getCouponId();
		}
		catch (NoSuchCouponException nsce) {
			return couponId;
		}
	}

	protected void validate(
			String companyId, String groupId, String couponId,
			boolean autoCouponId, String name, String description,
			String limitCategories, String limitSkus)
		throws PortalException, SystemException {

		if (!autoCouponId) {
			if ((Validator.isNull(couponId)) ||
				(Validator.isNumber(couponId)) ||
				(couponId.indexOf(StringPool.SPACE) != -1)) {

				throw new CouponIdException();
			}

			try {
				ShoppingCouponUtil.findByPrimaryKey(couponId);

				throw new DuplicateCouponIdException();
			}
			catch (NoSuchCouponException nsce) {
			}
		}

		validate(
			companyId, groupId, name, description, limitCategories, limitSkus);
	}

	protected void validate(
			String companyId, String groupId, String name, String description,
			String limitCategories, String limitSkus)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new CouponNameException();
		}
		else if (Validator.isNull(description)) {
			throw new CouponDescriptionException();
		}

		// Category IDs

		String[] categoryIds = StringUtil.split(limitCategories);

		List invalidCategoryIds = new ArrayList();

		for (int i = 0; i < categoryIds.length; i++) {
			try {
				ShoppingCategory category =
					ShoppingCategoryUtil.findByPrimaryKey(categoryIds[i]);

				if (!category.getGroupId().equals(groupId)) {
					invalidCategoryIds.add(categoryIds[i]);
				}
			}
			catch (NoSuchCategoryException nsce) {
				invalidCategoryIds.add(categoryIds[i]);
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

		List invalidSkus = new ArrayList();

		for (int i = 0; i < skus.length; i++) {
			try {
				ShoppingItem item =
					ShoppingItemUtil.findByC_S(companyId, skus[i]);

				ShoppingCategory category = item.getCategory();

				if (!category.getGroupId().equals(groupId)) {
					invalidSkus.add(skus[i]);
				}
			}
			catch (NoSuchItemException nsie) {
				invalidSkus.add(skus[i]);
			}
		}

		if (invalidSkus.size() > 0) {
			CouponLimitSKUsException clskue = new CouponLimitSKUsException();

			clskue.setSkus(invalidSkus);

			throw clskue;
		}
	}

}