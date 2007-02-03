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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ActionUtil {

	public static void getCategory(ActionRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getCategory(httpReq);
	}

	public static void getCategory(RenderRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getCategory(httpReq);
	}

	public static void getCategory(HttpServletRequest req) throws Exception {
		String categoryId = ParamUtil.getString(req, "categoryId");

		ShoppingCategory category = null;

		if (Validator.isNotNull(categoryId) &&
			!categoryId.equals(
				ShoppingCategoryImpl.DEFAULT_PARENT_CATEGORY_ID)) {

			category = ShoppingCategoryServiceUtil.getCategory(categoryId);
		}

		req.setAttribute(WebKeys.SHOPPING_CATEGORY, category);
	}

	public static void getCoupon(ActionRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getCoupon(httpReq);
	}

	public static void getCoupon(RenderRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getCoupon(httpReq);
	}

	public static void getCoupon(HttpServletRequest req) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String couponId = ParamUtil.getString(req, "couponId");

		ShoppingCoupon coupon = null;

		if (Validator.isNotNull(couponId)) {
			coupon = ShoppingCouponServiceUtil.getCoupon(
				themeDisplay.getPlid(), couponId);
		}

		req.setAttribute(WebKeys.SHOPPING_COUPON, coupon);
	}

	public static void getItem(ActionRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getItem(httpReq);
	}

	public static void getItem(RenderRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getItem(httpReq);
	}

	public static void getItem(HttpServletRequest req) throws Exception {
		String itemId = ParamUtil.getString(req, "itemId");

		ShoppingItem item = null;

		if (Validator.isNotNull(itemId)) {
			item = ShoppingItemServiceUtil.getItem(itemId);
		}

		req.setAttribute(WebKeys.SHOPPING_ITEM, item);
	}

	public static void getOrder(ActionRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getOrder(httpReq);
	}

	public static void getOrder(RenderRequest req) throws Exception {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		getOrder(httpReq);
	}

	public static void getOrder(HttpServletRequest req) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String orderId = ParamUtil.getString(req, "orderId");

		ShoppingOrder order = null;

		if (Validator.isNotNull(orderId)) {
			order = ShoppingOrderServiceUtil.getOrder(
				themeDisplay.getPlid(), orderId);
		}

		req.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

}