/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.shopping;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="ShoppingTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingTests extends BaseTests {

	public ShoppingTests() {
		addTestSuite(AddPageTest.class);
		addTestSuite(AddPortletTest.class);
		addTestSuite(AddCategoryTest.class);
		addTestSuite(EditCategoryTest.class);
		addTestSuite(AddTemporaryCategoryTest.class);
		addTestSuite(DeleteTemporaryCategoryTest.class);
		addTestSuite(AddSecondCategoryTest.class);
		addTestSuite(AddItemTest.class);
		addTestSuite(EditItemTest.class);
		addTestSuite(AddTemporaryItemTest.class);
		addTestSuite(DeleteTemporaryItemTest.class);
		addTestSuite(AddSecondItemTest.class);
		addTestSuite(SearchItemsTest.class);
		addTestSuite(MoveItemTest.class);
		addTestSuite(AddBlankItemTest.class);
		addTestSuite(AddCouponTest.class);
		addTestSuite(EditCouponTest.class);
		addTestSuite(AddBlankCouponTest.class);
		addTestSuite(AddTemporaryCouponTest.class);
		addTestSuite(DeleteTemporaryCouponTest.class);
		addTestSuite(SearchCouponsTest.class);
		addTestSuite(AddItemToCartTest.class);
		addTestSuite(ConfirmCartTest.class);
		addTestSuite(RemoveItemFromCartTest.class);
		addTestSuite(AddSecondItemToCartTest.class);
		//addTestSuite(UpdateCartQuantityTest.class);
		addTestSuite(CheckoutTest.class);
		addTestSuite(ConfirmOrderTest.class);
		addTestSuite(ViewInvoiceTest.class);
		addTestSuite(ConfigureAcceptedCCTest.class);
		addTestSuite(VerifyAcceptedCCTest.class);
		addTestSuite(ConfigureStateTaxTest.class);
		addTestSuite(AddThirdItemToCartTest.class);
		addTestSuite(VerifyStateTaxTest.class);
		addTestSuite(ConfigureFlatRateShippingCostTest.class);
		addTestSuite(VerifyFlatRateShippingCostTest.class);
		addTestSuite(ConfigurePercentageShippingTest.class);
		addTestSuite(VerifyPercentageShippingTest.class);
		addTestSuite(ConfigureFlatRateInsuranceTest.class);
		addTestSuite(VerifyFlatRateInsuranceTest.class);
		addTestSuite(ConfigurePercentageInsuranceTest.class);
		addTestSuite(VerifyPercentageInsuranceTest.class);
		addTestSuite(SaveCurrentSetupTest.class);
		addTestSuite(ChangeCurrentSetupTest.class);
		//addTestSuite(RestoreArchivedSetupTest.class);
	}

}