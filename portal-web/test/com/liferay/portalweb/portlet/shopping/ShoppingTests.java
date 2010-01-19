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

package com.liferay.portalweb.portlet.shopping;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ShoppingTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(AddCategoryTest.class);
		testSuite.addTestSuite(EditCategoryTest.class);
		testSuite.addTestSuite(AddTemporaryCategoryTest.class);
		testSuite.addTestSuite(DeleteTemporaryCategoryTest.class);
		testSuite.addTestSuite(AddSecondCategoryTest.class);
		testSuite.addTestSuite(AddNullCategoryTest.class);
		testSuite.addTestSuite(AddItemTest.class);
		testSuite.addTestSuite(EditItemTest.class);
		testSuite.addTestSuite(AddTemporaryItemTest.class);
		testSuite.addTestSuite(DeleteTemporaryItemTest.class);
		testSuite.addTestSuite(AddSecondItemTest.class);
		testSuite.addTestSuite(SearchItemsTest.class);
		testSuite.addTestSuite(MoveItemTest.class);
		testSuite.addTestSuite(AddNullItemTest.class);
		testSuite.addTestSuite(AddCouponTest.class);
		testSuite.addTestSuite(EditCouponTest.class);
		testSuite.addTestSuite(AddTemporaryCouponTest.class);
		testSuite.addTestSuite(DeleteTemporaryCouponTest.class);
		testSuite.addTestSuite(SearchCouponsTest.class);
		testSuite.addTestSuite(AddNullCouponTest.class);
		testSuite.addTestSuite(AddItemToCartTest.class);
		testSuite.addTestSuite(UpdateCartQuantityTest.class);
		testSuite.addTestSuite(ConfirmCartTest.class);
		testSuite.addTestSuite(RemoveItemFromCartTest.class);
		testSuite.addTestSuite(AddSecondItemToCartTest.class);
		testSuite.addTestSuite(AddNullItemSKUTest.class);
		testSuite.addTestSuite(AddNullItemNameTest.class);
		testSuite.addTestSuite(AddNumberCouponCodeTest.class);
		testSuite.addTestSuite(AddSpaceCouponCodeTest.class);
		testSuite.addTestSuite(AddSymbolCategoryNameTest.class);
		testSuite.addTestSuite(AddNullBillingAddressFirstNameTest.class);
		testSuite.addTestSuite(AddNullBillingAddressLastNameTest.class);
		testSuite.addTestSuite(AddNullBillingEmailAddressTest.class);
		testSuite.addTestSuite(AddNumberBillingEmailAddressTest.class);
		testSuite.addTestSuite(AddFormatBilllingEmailAddressTest.class);
		testSuite.addTestSuite(AddSymbolBillingEmailAddressTest.class);
		testSuite.addTestSuite(AddNullBillingStreetAddressTest.class);
		testSuite.addTestSuite(AddNullBillingCityTest.class);
		testSuite.addTestSuite(AddNullBillingStateTest.class);
		testSuite.addTestSuite(AddNullBillingZipTest.class);
		testSuite.addTestSuite(AddNullBillingCountryTest.class);
		testSuite.addTestSuite(AddNullBillingPhoneTest.class);
		testSuite.addTestSuite(AddNullShippingAddressFirstNameTest.class);
		testSuite.addTestSuite(AddNullShippingAddressLastNameTest.class);
		testSuite.addTestSuite(AddNullShippingEmailAddressTest.class);
		testSuite.addTestSuite(AddNumberShippingEmailAddressTest.class);
		testSuite.addTestSuite(AddFormatShippingEmailAddressTest.class);
		testSuite.addTestSuite(AddSymbolShippingEmailAddressTest.class);
		testSuite.addTestSuite(AddNullShippingStreetTest.class);
		testSuite.addTestSuite(AddNullShippingCityTest.class);
		testSuite.addTestSuite(AddNullShippingStateTest.class);
		testSuite.addTestSuite(AddNullShippingZipTest.class);
		testSuite.addTestSuite(AddNullShippingCountryTest.class);
		testSuite.addTestSuite(AddNullShippingPhoneTest.class);
		testSuite.addTestSuite(AddNullCreditCardNameTest.class);
		testSuite.addTestSuite(AddNullCreditCardTypeTest.class);
		testSuite.addTestSuite(AddNullCreditCardNumberTest.class);
		testSuite.addTestSuite(AddVisaCreditCardTest.class);
		testSuite.addTestSuite(AddMasterCardCreditCardTest.class);
		testSuite.addTestSuite(AddDiscoverCreditCardTest.class);
		testSuite.addTestSuite(AddAmexCreditCardTest.class);
		testSuite.addTestSuite(CheckoutTest.class);
		testSuite.addTestSuite(ConfirmOrderTest.class);
		testSuite.addTestSuite(ViewInvoiceTest.class);
		testSuite.addTestSuite(ConfigureAcceptedCCTest.class);
		testSuite.addTestSuite(VerifyAcceptedCCTest.class);
		testSuite.addTestSuite(ConfigureStateTaxTest.class);
		testSuite.addTestSuite(AddThirdItemToCartTest.class);
		testSuite.addTestSuite(VerifyStateTaxTest.class);
		testSuite.addTestSuite(ConfigureFlatRateShippingCostTest.class);
		testSuite.addTestSuite(VerifyFlatRateShippingCostTest.class);
		testSuite.addTestSuite(ConfigurePercentageShippingTest.class);
		testSuite.addTestSuite(VerifyPercentageShippingTest.class);
		testSuite.addTestSuite(ConfigureFlatRateInsuranceTest.class);
		testSuite.addTestSuite(VerifyFlatRateInsuranceTest.class);
		testSuite.addTestSuite(ConfigurePercentageInsuranceTest.class);
		testSuite.addTestSuite(VerifyPercentageInsuranceTest.class);
		testSuite.addTestSuite(SaveCurrentSetupTest.class);
		testSuite.addTestSuite(ChangeCurrentSetupTest.class);
		testSuite.addTestSuite(RestoreArchivedSetupTest.class);
		testSuite.addTestSuite(AddSubcategoryTest.class);
		testSuite.addTestSuite(AddSecondSubcategoryTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}