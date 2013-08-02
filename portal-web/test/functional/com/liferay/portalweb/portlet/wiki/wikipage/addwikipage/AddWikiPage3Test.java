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

package com.liferay.portalweb.portlet.wiki.wikipage.addwikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPage3Test extends BaseTestCase {
	public void testAddWikiPage3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'All Pages')]"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'All Pages')]",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page does not exist yet. Use the form below to create it."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("Wiki Page3 Title"));
		Thread.sleep(1000);
		selenium.waitForVisible("//iframe[contains(@title,'Rich text editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich text editor')]",
			RuntimeVariables.replace("Wiki Page3 Content"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki Page1 Title"),
			selenium.getText("//tr[contains(.,'Wiki Page1 Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page2 Title"),
			selenium.getText("//tr[contains(.,'Wiki Page2 Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page3 Title"),
			selenium.getText("//tr[contains(.,'Wiki Page3 Title')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Wiki Page3 Title')]/td[1]/a",
			RuntimeVariables.replace("Wiki Page3 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page3 Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Wiki Page3 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}