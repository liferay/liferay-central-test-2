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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.shopping.model.ShoppingCoupon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingCouponJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.shopping.service.http.ShoppingCouponServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.http.ShoppingCouponServiceJSON
 *
 */
public class ShoppingCouponJSONSerializer {
	public static JSONObject toJSONObject(ShoppingCoupon model) {
		JSONObject jsonObj = new JSONObject();
		String couponId = model.getCouponId();

		if (couponId == null) {
			jsonObj.put("couponId", StringPool.BLANK);
		}
		else {
			jsonObj.put("couponId", couponId.toString());
		}

		jsonObj.put("groupId", model.getGroupId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		jsonObj.put("userId", model.getUserId());

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String description = model.getDescription();

		if (description == null) {
			jsonObj.put("description", StringPool.BLANK);
		}
		else {
			jsonObj.put("description", description.toString());
		}

		Date startDate = model.getStartDate();

		if (startDate == null) {
			jsonObj.put("startDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("startDate", startDate.toString());
		}

		Date endDate = model.getEndDate();

		if (endDate == null) {
			jsonObj.put("endDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("endDate", endDate.toString());
		}

		jsonObj.put("active", model.getActive());

		String limitCategories = model.getLimitCategories();

		if (limitCategories == null) {
			jsonObj.put("limitCategories", StringPool.BLANK);
		}
		else {
			jsonObj.put("limitCategories", limitCategories.toString());
		}

		String limitSkus = model.getLimitSkus();

		if (limitSkus == null) {
			jsonObj.put("limitSkus", StringPool.BLANK);
		}
		else {
			jsonObj.put("limitSkus", limitSkus.toString());
		}

		jsonObj.put("minOrder", model.getMinOrder());
		jsonObj.put("discount", model.getDiscount());

		String discountType = model.getDiscountType();

		if (discountType == null) {
			jsonObj.put("discountType", StringPool.BLANK);
		}
		else {
			jsonObj.put("discountType", discountType.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			ShoppingCoupon model = (ShoppingCoupon)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}