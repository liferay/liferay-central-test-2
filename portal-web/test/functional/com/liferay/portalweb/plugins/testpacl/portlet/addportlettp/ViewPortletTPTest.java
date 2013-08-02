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

package com.liferay.portalweb.plugins.testpacl.portlet.addportlettp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTPTest extends BaseTestCase {
	public void testViewPortletTP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Test PACL Test Page",
			RuntimeVariables.replace("Test PACL Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("Test PACL"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertFalse(selenium.isTextPresent("FAILED"));
		assertEquals(RuntimeVariables.replace("Bean Property"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
	}
}