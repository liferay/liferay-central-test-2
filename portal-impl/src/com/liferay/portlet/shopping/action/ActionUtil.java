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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCategoryConstants;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getCategory(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getCategory(request);
	}

	public static void getCategory(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getCategory(request);
	}

	public static void getCategory(HttpServletRequest request)
		throws Exception {

		long categoryId = ParamUtil.getLong(request, "categoryId");

		ShoppingCategory category = null;

		if ((categoryId > 0) &&
			(categoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			category = ShoppingCategoryServiceUtil.getCategory(categoryId);
		}

		request.setAttribute(WebKeys.SHOPPING_CATEGORY, category);
	}

	public static void getCoupon(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getCoupon(request);
	}

	public static void getCoupon(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getCoupon(request);
	}

	public static void getCoupon(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long couponId = ParamUtil.getLong(request, "couponId");

		ShoppingCoupon coupon = null;

		if (couponId > 0) {
			coupon = ShoppingCouponServiceUtil.getCoupon(
				themeDisplay.getScopeGroupId(), couponId);
		}

		request.setAttribute(WebKeys.SHOPPING_COUPON, coupon);
	}

	public static void getItem(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getItem(request);
	}

	public static void getItem(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getItem(request);
	}

	public static void getItem(HttpServletRequest request) throws Exception {
		long itemId = ParamUtil.getLong(request, "itemId");

		ShoppingItem item = null;

		if (itemId > 0) {
			item = ShoppingItemServiceUtil.getItem(itemId);
		}

		request.setAttribute(WebKeys.SHOPPING_ITEM, item);
	}

	public static void getOrder(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getOrder(request);
	}

	public static void getOrder(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getOrder(request);
	}

	public static void getOrder(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long orderId = ParamUtil.getLong(request, "orderId");

		ShoppingOrder order = null;

		if (orderId > 0) {
			order = ShoppingOrderServiceUtil.getOrder(
				themeDisplay.getScopeGroupId(), orderId);
		}

		request.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

}