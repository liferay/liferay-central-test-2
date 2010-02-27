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

package com.liferay.portalweb.portal.dbfailover;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddMessage3Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddMessage3Test extends BaseTestCase {
	public void testAddMessage3() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Test Category"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value=\"Post New Thread\"]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_19_subject", RuntimeVariables.replace("Test Message 3"));
		selenium.type("_19_textArea",
			RuntimeVariables.replace("This is Test Message 3."));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("This is Test Message 3."));
		assertTrue(selenium.isElementPresent("link=Test Message 3"));
		selenium.click(RuntimeVariables.replace("link=Test Category"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Message 3"));
		System.out.println("Sample data 3 added successfully.\n");
	}
}