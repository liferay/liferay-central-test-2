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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Writer_WebContentRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Writer_WebContentRolesTest extends BaseTestCase {
	public void testWriter_WebContentRoles() throws Exception {
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleEXPIRE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticlePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleVIEW']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalADD_ARTICLE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructurePERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureVIEW']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplatePERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("add-permissions", RuntimeVariables.replace("www"));
		selenium.keyPress("add-permissions", RuntimeVariables.replace("\\13"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='15CONFIGURATION']");
		selenium.check("//input[@name='_128_rowIds' and @value='15VIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}