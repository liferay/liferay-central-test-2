/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.shopping.item.addcategoryitem;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCategoryItem3Test extends BaseTestCase {
	public void testAddCategoryItem3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Description"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace(
				"Shopping Category Name Shopping Category Description"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Item']",
			RuntimeVariables.replace("Add Item"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_34_sku']", RuntimeVariables.replace("3333"));
		selenium.type("//input[@id='_34_name']",
			RuntimeVariables.replace("Shopping Category Item3 Name"));
		selenium.type("//textarea[@id='_34_description']",
			RuntimeVariables.replace("Shopping Category Item3 Description"));
		selenium.type("//textarea[@id='_34_properties']",
			RuntimeVariables.replace("Shopping Category Item3 Properties"));
		assertFalse(selenium.isChecked(
				"//input[@id='_34_requiresShippingCheckbox']"));
		selenium.clickAt("//input[@id='_34_requiresShippingCheckbox']",
			RuntimeVariables.replace("Requires Shipping"));
		assertTrue(selenium.isChecked(
				"//input[@id='_34_requiresShippingCheckbox']"));
		selenium.type("//input[@id='_34_stockQuantity']",
			RuntimeVariables.replace("50"));
		selenium.type("//input[@id='_34_minQuantity0']",
			RuntimeVariables.replace("1"));
		selenium.type("//input[@id='_34_maxQuantity0']",
			RuntimeVariables.replace("1"));
		selenium.type("//input[@id='_34_price0']",
			RuntimeVariables.replace("$9.99"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]/a",
				"Shopping Category Item1 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]/a",
				"Shopping Category Item1 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]/a",
				"Shopping: Category Item1 Properties"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]/a",
				"Shopping Category Item2 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]/a",
				"Shopping Category Item2 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]/a",
				"Shopping: Category Item2 Properties"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Category Item3 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Category Item3 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping: Category Item3 Properties"));
	}
}