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

package com.liferay.portalweb.portlet.shopping.item;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitem.AddCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemmultiple.AddCategoryItemMultipleTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemnamenull.AddCategoryItemNameNullTests;
import com.liferay.portalweb.portlet.shopping.item.addcategoryitemskunull.AddCategoryItemSKUNullTests;
import com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitem.AddToShoppingCartCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitemmultiple.AddToShoppingCartCategoryItemMultipleTests;
import com.liferay.portalweb.portlet.shopping.item.deletecategoryitem.DeleteCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.editcategoryitem.EditCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.emptycartcategoryitem.EmptyCartCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.movecategoryitemtocategory.MoveCategoryItemToCategoryTests;
import com.liferay.portalweb.portlet.shopping.item.searchcategoryitem.SearchCategoryItemTests;
import com.liferay.portalweb.portlet.shopping.item.updatecartcategoryitemquantity.UpdateCartCategoryItemQuantityTests;
import com.liferay.portalweb.portlet.shopping.item.updatecartcategoryitemquantity0.UpdateCartCategoryItemQuantity0Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ItemTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ItemTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryItemTests.suite());
		testSuite.addTest(AddCategoryItemMultipleTests.suite());
		testSuite.addTest(AddCategoryItemNameNullTests.suite());
		testSuite.addTest(AddCategoryItemSKUNullTests.suite());
		testSuite.addTest(AddToShoppingCartCategoryItemTests.suite());
		testSuite.addTest(AddToShoppingCartCategoryItemMultipleTests.suite());
		testSuite.addTest(DeleteCategoryItemTests.suite());
		testSuite.addTest(EditCategoryItemTests.suite());
		testSuite.addTest(EmptyCartCategoryItemTests.suite());
		testSuite.addTest(MoveCategoryItemToCategoryTests.suite());
		testSuite.addTest(SearchCategoryItemTests.suite());
		testSuite.addTest(UpdateCartCategoryItemQuantityTests.suite());
		testSuite.addTest(UpdateCartCategoryItemQuantity0Tests.suite());

		return testSuite;
	}

}