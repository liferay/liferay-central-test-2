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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewScopeCurrentPageWCWebContentNullPage2Test extends BaseTestCase {
	public void testViewScopeCurrentPageWCWebContentNullPage2()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Web Content Display Test Page3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Web Content Display Test Page3",
			RuntimeVariables.replace("Web Content Display Test Page3"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Web Content Display (Web Content Display Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertFalse(selenium.isTextPresent("WC WebContent Content"));
		assertEquals(RuntimeVariables.replace(
				"Select existing web content or add some web content to be displayed in this portlet."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}