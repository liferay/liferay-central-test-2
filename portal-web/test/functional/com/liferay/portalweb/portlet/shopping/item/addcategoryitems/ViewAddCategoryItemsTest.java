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

package com.liferay.portalweb.portlet.shopping.item.addcategoryitems;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAddCategoryItemsTest extends BaseTestCase {
	public void testViewAddCategoryItems() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//div[@id='shoppingCategoriesPanel']/div[1]/div"));
		assertEquals("Add Subcategory",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[1]/span/input"));
		assertEquals("Permissions",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[2]/span/input"));
		assertEquals(RuntimeVariables.replace("Items"),
			selenium.getText("//div[@id='shoppingItemsPanel']/div[1]/div/span"));
		assertEquals(RuntimeVariables.replace("Search"),
			selenium.getText(
				"//div[@id='shoppingItemsPanel']/div[2]/fieldset/div/span/span/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='shoppingItemsPanel']/div[2]/fieldset/div/span/span/span"));
		assertEquals("Search This Category",
			selenium.getValue(
				"//div[@id='shoppingItemsPanel']/div[2]/div/span[1]/span/input"));
		assertEquals("Add Item",
			selenium.getValue(
				"//div[@id='shoppingItemsPanel']/div[2]/div/span[2]/span/input"));
		assertEquals(RuntimeVariables.replace("SKU"),
			selenium.getText(
				"xPath=(//tr[@class='portlet-section-header results-header']/th[1])[2]"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText(
				"xPath=(//tr[@class='portlet-section-header results-header']/th[2])[2]"));
		assertEquals(RuntimeVariables.replace("Min Qty"),
			selenium.getText(
				"xPath=(//tr[@class='portlet-section-header results-header']/th[3])[2]"));
		assertEquals(RuntimeVariables.replace("Price"),
			selenium.getText(
				"xPath=(//tr[@class='portlet-section-header results-header']/th[4])[2]"));
		assertEquals(RuntimeVariables.replace("1111"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[1]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]",
				"Shopping Category Item1 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]",
				"Shopping Category Item1 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[2]",
				"Shopping: Category Item1 Properties"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[3]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[4]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[5]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("2222"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]",
				"Shopping Category Item2 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]",
				"Shopping Category Item2 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]",
				"Shopping: Category Item2 Properties"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[3]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[4]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[5]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("3333"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[1]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]",
				"Shopping Category Item3 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]",
				"Shopping Category Item3 Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]",
				"Shopping: Category Item3 Properties"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[5]/span/ul/li/strong/a"));
	}
}