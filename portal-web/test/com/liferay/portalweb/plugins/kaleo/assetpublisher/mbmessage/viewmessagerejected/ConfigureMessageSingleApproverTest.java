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

package com.liferay.portalweb.plugins.kaleo.assetpublisher.mbmessage.viewmessagerejected;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureMessageSingleApproverTest extends BaseTestCase {
	public void testConfigureMessageSingleApprover() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.click(RuntimeVariables.replace("link=Workflow Configuration"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("label=Single Approver (Version 1)"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Single Approver (Version 1)",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']"));
	}
}