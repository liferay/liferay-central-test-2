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

package com.liferay.portlet.shopping.search;

import com.liferay.util.ParamUtil;
import com.liferay.util.dao.search.DisplayTerms;

import javax.portlet.RenderRequest;

/**
 * <a href="CouponDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CouponDisplayTerms extends DisplayTerms {

	public static final String COUPON_ID = "searchCouponId";

	public static final String DISCOUNT_TYPE = "discountType";

	public static final String ACTIVE = "active";

	public CouponDisplayTerms(RenderRequest req) {
		super(req);

		couponId = ParamUtil.getString(req, COUPON_ID);
		discountType = ParamUtil.getString(req, DISCOUNT_TYPE);
		active = ParamUtil.getBoolean(req, ACTIVE, true);
	}

	public String getCouponId() {
		return couponId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public boolean isActive() {
		return active;
	}

	protected String couponId;
	protected String discountType;
	protected boolean active;

}