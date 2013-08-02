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

package com.liferay.portalweb.portal.dbupgrade.sampledata6120.shopping.item;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCategoryItemTest extends BaseTestCase {
	public void testViewCategoryItem() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/shopping-item-community/");
		selenium.waitForVisible("link=Shopping Item Page");
		selenium.clickAt("link=Shopping Item Page",
			RuntimeVariables.replace("Shopping Item Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//td[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//td[2]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1111"),
			selenium.getText("//td[1]/strong"));
		assertTrue(selenium.isPartialText("//td[3]", "Item Test"));
		assertTrue(selenium.isPartialText("//td[3]", "This is an item test."));
		assertTrue(selenium.isPartialText("//td[3]", "Limited: Time Only"));
		assertTrue(selenium.isPartialText("//td[3]",
				"Price for 1 Items and Above: "));
		assertTrue(selenium.isPartialText("//td[3]", "$"));
		assertTrue(selenium.isPartialText("//td[3]", "9.99"));
		assertTrue(selenium.isPartialText("//td[3]", "Availability:"));
		assertEquals(RuntimeVariables.replace("In Stock"),
			selenium.getText(
				"//div[.='In Stock' and @class='portlet-msg-success']"));
	}
}