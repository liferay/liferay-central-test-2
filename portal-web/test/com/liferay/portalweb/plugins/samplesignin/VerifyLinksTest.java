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

package com.liferay.portalweb.plugins.samplesignin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="VerifyLinksTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VerifyLinksTest extends BaseTestCase {
	public void testVerifyLinks() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//div[2]/div/div/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		verifyTrue(selenium.isTextPresent("You are signed in as Joe Bloggs."));
		selenium.click(RuntimeVariables.replace("//div[2]/div/div/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals("test@liferay.com", selenium.getValue("_2_emailAddress"));
		selenium.click(RuntimeVariables.replace("link=Back to My Community"));
		selenium.waitForPageToLoad("30000");
	}
}