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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_SignOutPublicPagesSiteBWCTest extends BaseTestCase {
	public void testSA_SignOutPublicPagesSiteBWC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.openWindow("http://www.able.com:8080",
			RuntimeVariables.replace("home"));
		selenium.waitForPopUp("home", RuntimeVariables.replace(""));
		selenium.selectWindow("home");
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.waitForElementPresent("link=Sign Out");
		selenium.click(RuntimeVariables.replace("link=Sign Out"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.close();
		selenium.selectWindow("null");
	}
}