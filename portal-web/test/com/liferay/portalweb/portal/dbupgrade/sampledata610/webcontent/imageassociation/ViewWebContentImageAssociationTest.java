/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledata610.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentImageAssociationTest extends BaseTestCase {
	public void testViewWebContentImageAssociation() throws Exception {
		selenium.open("/web/web-content-image-association-community/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Web Content Display Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Web Content Display Page",
			RuntimeVariables.replace("Web Content Display Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Text Test"),
			selenium.getText("//h1[@id='web-content-title']"));
		assertEquals(RuntimeVariables.replace("Image Test:"),
			selenium.getText("//h2[@id='image-title']"));
		assertTrue(selenium.isVisible("//a[@id='image']/img"));
		assertEquals(RuntimeVariables.replace("Image Gallery Image Test:"),
			selenium.getText("//h2[@id='image-gallery-title']"));
		assertTrue(selenium.isVisible("//a[@id='image-gallery']/img"));
	}
}