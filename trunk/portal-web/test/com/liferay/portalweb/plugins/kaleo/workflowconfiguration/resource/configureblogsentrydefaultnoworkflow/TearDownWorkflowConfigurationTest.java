/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.kaleo.workflowconfiguration.resource.configureblogsentrydefaultnoworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWorkflowConfigurationTest extends BaseTestCase {
	public void testTearDownWorkflowConfiguration() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Workflow", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Default Configuration",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry",
			RuntimeVariables.replace("label=No workflow"));
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion",
			RuntimeVariables.replace("label=No workflow"));
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage",
			RuntimeVariables.replace("label=No workflow"));
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle",
			RuntimeVariables.replace("label=No workflow"));
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.documentlibrary.model.DLFileEntry",
			RuntimeVariables.replace("label=No workflow"));
		selenium.select("_151_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage",
			RuntimeVariables.replace("label=No workflow"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Workflow Configuration",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.documentlibrary.model.DLFileEntry",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.select("_152_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage",
			RuntimeVariables.replace("label=Default: No workflow"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
	}
}