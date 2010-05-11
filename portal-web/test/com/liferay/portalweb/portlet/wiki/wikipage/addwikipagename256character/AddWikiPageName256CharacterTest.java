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

package com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename256character;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddWikiPageName256CharacterTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageName256CharacterTest extends BaseTestCase {
	public void testAddWikiPageName256Character() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_36_title",
			RuntimeVariables.replace(
				"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15||||||||16||||||||17||||||||18||||||||19||||||||20||||||||21||||||||22||||||||23||||||||24||||||||25|||||X"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a wiki page test."));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace(
				"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15||||||||16||||||||17||||||||18||||||||19||||||||20||||||||21||||||||22||||||||23||||||||24||||||||25|||||"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//h1[@class='page-title']",
				"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15||||||||16||||||||17||||||||18||||||||19||||||||20||||||||21||||||||22||||||||23||||||||24||||||||25|||||"));
		assertEquals(RuntimeVariables.replace("This is a wiki page test."),
			selenium.getText("//div[@class='wiki-body']"));
		assertFalse(selenium.isPartialText("//h1[@class='page-title']",
				"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15||||||||16||||||||17||||||||18||||||||19||||||||20||||||||21||||||||22||||||||23||||||||24||||||||25|||||X"));
	}
}