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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.addwdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWDFrontPageChildPage1Test extends BaseTestCase {
	public void testAddWDFrontPageChildPage1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText(
				"//div[@class='article-actions']/span/a[contains(.,'Add Child Page')]"));
		selenium.clickAt("//div[@class='article-actions']/span/a[contains(.,'Add Child Page')]",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[contains(@id,'_title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"));
		Thread.sleep(1000);
		selenium.waitForVisible("//a[@class='cke_button_cut cke_disabled']");
		selenium.waitForVisible("//iframe[contains(@title,'Rich text editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich text editor')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Content"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText(
				"//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage1 Title')]"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage1 Title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}