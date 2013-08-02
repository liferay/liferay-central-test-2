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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.expando.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CreateAccountExpando2Test extends BaseTestCase {
	public void testCreateAccountExpando2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/expando-web-content-community/");
		selenium.clickAt("link=Web Content Display Page",
			RuntimeVariables.replace("Web Content Display Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Create Account']",
			RuntimeVariables.replace("Create Account"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//table[@class='lfr-table']/tbody/tr[1]/td[2]/input",
			RuntimeVariables.replace("JR"));
		selenium.type("//table[@class='lfr-table']/tbody/tr[2]/td[2]/input",
			RuntimeVariables.replace("Houn"));
		selenium.type("//table[@class='lfr-table']/tbody/tr[3]/td[2]/input",
			RuntimeVariables.replace("1000000"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("JR"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Houn"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("$1,000,000.00"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[4]"));
	}
}