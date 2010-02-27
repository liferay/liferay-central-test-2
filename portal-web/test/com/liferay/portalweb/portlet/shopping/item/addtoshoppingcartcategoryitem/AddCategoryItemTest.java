/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.shopping.item.addtoshoppingcartcategoryitem;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddCategoryItemTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddCategoryItemTest extends BaseTestCase {
	public void testAddCategoryItem() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Shopping Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Shopping Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Item']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_34_sku", RuntimeVariables.replace("1111"));
		selenium.type("_34_name", RuntimeVariables.replace("Item Test"));
		selenium.type("_34_description",
			RuntimeVariables.replace("This is an item test."));
		selenium.type("_34_stockQuantity", RuntimeVariables.replace("50"));
		selenium.type("_34_properties",
			RuntimeVariables.replace("Limited Time Only"));
		assertTrue(selenium.isElementPresent("_34_requiresShippingCheckbox"));
		selenium.clickAt("_34_requiresShippingCheckbox",
			RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked("_34_requiresShippingCheckbox"));
		selenium.type("_34_price0", RuntimeVariables.replace("$9.99"));
		selenium.type("_34_minQuantity0", RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isTextPresent(
				"Item Test\nThis is an item test.\nLimited: Time Only"));
	}
}