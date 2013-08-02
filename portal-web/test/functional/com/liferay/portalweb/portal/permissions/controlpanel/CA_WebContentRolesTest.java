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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CA_WebContentRolesTest extends BaseTestCase {
	public void testCA_WebContentRoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleDELETE']");
		selenium.check(
			"//input[@value='com.liferay.portlet.journal.model.JournalArticleDELETE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleEXPIRE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticlePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleUPDATE']");
		selenium.check(
			"//input[@value='com.liferay.portlet.journal.model.JournalArticleUPDATE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalArticleVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalFeedVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalADD_ARTICLE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalADD_FEED']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalADD_STRUCTURE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalADD_TEMPLATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructurePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalStructureVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplatePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journal.model.JournalTemplateVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_128_add-permissions",
			RuntimeVariables.replace("wwwwwwwwww"));
		selenium.keyPress("_128_add-permissions",
			RuntimeVariables.replace("\\13"));
		selenium.waitForPageToLoad("30000");
		selenium.check("//input[@value='15ACCESS_IN_CONTROL_PANEL']");
		selenium.check("//input[@value='15ADD_TO_PAGE']");
		selenium.check("//input[@value='15CONFIGURATION']");
		selenium.check("//input[@value='15VIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}