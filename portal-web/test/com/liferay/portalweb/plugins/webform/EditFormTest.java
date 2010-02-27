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

package com.liferay.portalweb.plugins.webform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="EditFormTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditFormTest extends BaseTestCase {
	public void testEditForm() throws Exception {
		selenium.click(RuntimeVariables.replace("//img[@alt='Configuration']"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_86_title", RuntimeVariables.replace("Feed Back"));
		selenium.type("_86_description",
			RuntimeVariables.replace("Please let us know what you think!"));
		selenium.type("_86_fieldLabel1", RuntimeVariables.replace("Your Name"));
		selenium.type("_86_fieldLabel2", RuntimeVariables.replace("Rate Us!"));
		selenium.type("_86_fieldLabel3",
			RuntimeVariables.replace("Additional Comments"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Return to Full Page"));
		selenium.waitForPageToLoad("30000");
	}
}