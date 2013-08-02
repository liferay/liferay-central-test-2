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

package com.liferay.portalweb.portlet.webcontentsearch.webcontent.searchwcwebcontentwcs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchWCWebContentQuotesWCSTest extends BaseTestCase {
	public void testSearchWCWebContentQuotesWCS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Search Test Page",
			RuntimeVariables.replace("Web Content Search Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_77_keywords']",
			RuntimeVariables.replace("\"WC WebContent Title\""));
		selenium.clickAt("//input[@type='image']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//td[@id='_77_ocerSearchContainer_col-name_row-1']"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_77_ocerSearchContainer_col-name_row-1']",
				"WC WebContent"));
		assertTrue(selenium.isPartialText(
				"//td[@id='_77_ocerSearchContainer_col-name_row-1']", "Content"));
		assertTrue(selenium.isTextPresent("WC WebContent Content"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Search Test Page",
			RuntimeVariables.replace("Web Content Search Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_77_keywords']",
			RuntimeVariables.replace("\"WC1 WebContent1 Title1\""));
		selenium.clickAt("//input[@type='image']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("WC WebContent Title"));
		assertFalse(selenium.isTextPresent("WC WebContent Content"));
	}
}