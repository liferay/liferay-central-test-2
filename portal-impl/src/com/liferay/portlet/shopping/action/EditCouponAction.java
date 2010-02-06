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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
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
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditCouponAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Huang Jie
 */
public class EditCouponAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCoupon(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCoupons(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCouponException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.shopping.error");
			}
			else if (e instanceof CouponCodeException ||
					 e instanceof CouponDateException ||
					 e instanceof CouponDescriptionException ||
					 e instanceof CouponDiscountException ||
					 e instanceof CouponEndDateException ||
					 e instanceof CouponLimitCategoriesException ||
					 e instanceof CouponLimitSKUsException ||
					 e instanceof CouponMinimumOrderException ||
					 e instanceof CouponNameException ||
					 e instanceof CouponStartDateException ||
					 e instanceof DuplicateCouponCodeException) {

				if (e instanceof CouponLimitCategoriesException) {
					CouponLimitCategoriesException clce =
						(CouponLimitCategoriesException)e;

					SessionErrors.add(
						actionRequest, e.getClass().getName(),
						clce.getCategoryIds());
				}
				else if (e instanceof CouponLimitSKUsException) {
					CouponLimitSKUsException clskue =
						(CouponLimitSKUsException)e;

					SessionErrors.add(
						actionRequest, e.getClass().getName(),
						clskue.getSkus());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getCoupon(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCouponException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.shopping.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.shopping.edit_coupon"));
	}

	protected void deleteCoupons(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteCouponIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteCouponIds"), 0L);

		for (int i = 0; i < deleteCouponIds.length; i++) {
			ShoppingCouponServiceUtil.deleteCoupon(
				themeDisplay.getScopeGroupId(), deleteCouponIds[i]);
		}
	}

	protected void updateCoupon(ActionRequest actionRequest) throws Exception {
		long couponId = ParamUtil.getLong(actionRequest, "couponId");

		String code = ParamUtil.getString(actionRequest, "code");
		boolean autoCode = ParamUtil.getBoolean(actionRequest, "autoCode");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		int startDateMonth = ParamUtil.getInteger(
			actionRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(actionRequest, "startDateDay");
		int startDateYear = ParamUtil.getInteger(
			actionRequest, "startDateYear");
		int startDateHour = ParamUtil.getInteger(
			actionRequest, "startDateHour");
		int startDateMinute = ParamUtil.getInteger(
			actionRequest, "startDateMinute");
		int startDateAmPm = ParamUtil.getInteger(
			actionRequest, "startDateAmPm");

		if (startDateAmPm == Calendar.PM) {
			startDateHour += 12;
		}

		int endDateMonth = ParamUtil.getInteger(actionRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(actionRequest, "endDateDay");
		int endDateYear = ParamUtil.getInteger(actionRequest, "endDateYear");
		int endDateHour = ParamUtil.getInteger(actionRequest, "endDateHour");
		int endDateMinute = ParamUtil.getInteger(
			actionRequest, "endDateMinute");
		int endDateAmPm = ParamUtil.getInteger(actionRequest, "endDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		if (endDateAmPm == Calendar.PM) {
			endDateHour += 12;
		}

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String limitCategories = ParamUtil.getString(
			actionRequest, "limitCategories");
		String limitSkus = ParamUtil.getString(actionRequest, "limitSkus");
		double minOrder = ParamUtil.getDouble(actionRequest, "minOrder", -1.0);
		double discount = ParamUtil.getDouble(actionRequest, "discount", -1.0);
		String discountType = ParamUtil.getString(
			actionRequest, "discountType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ShoppingCoupon.class.getName(), actionRequest);

		if (couponId <= 0) {

			// Add coupon

			ShoppingCouponServiceUtil.addCoupon(
				code, autoCode, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);
		}
		else {

			// Update coupon

			ShoppingCouponServiceUtil.updateCoupon(
				couponId, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);
		}
	}

}