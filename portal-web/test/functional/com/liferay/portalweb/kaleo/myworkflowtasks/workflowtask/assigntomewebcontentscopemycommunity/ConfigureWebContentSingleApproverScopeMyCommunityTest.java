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

package com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopemycommunity;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureWebContentSingleApproverScopeMyCommunityTest
	extends BaseTestCase {
	public void testConfigureWebContentSingleApproverScopeMyCommunity()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//strong/a/span",
				"Joe Bloggs's Personal"));
		selenium.clickAt("link=Workflow Configuration",
			RuntimeVariables.replace("Workflow Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']",
			RuntimeVariables.replace("label=Single Approver (Version 1)"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Single Approver (Version 1)",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']"));
	}
}