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

package com.liferay.portalweb.plugins.ddlform.portlet.removeportletddlform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePortletDDLFormTest extends BaseTestCase {
	public void testRemovePortletDDLForm() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Dynamic Data List Form Test Page");
		selenium.clickAt("link=Dynamic Data List Form Test Page",
			RuntimeVariables.replace("Dynamic Data List Form Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//img[@alt='Remove']");
		selenium.click("//img[@alt='Remove']");
		Thread.sleep(5000);
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
		selenium.waitForElementNotPresent("//section");
		assertTrue(selenium.isElementNotPresent("//section"));
	}
}