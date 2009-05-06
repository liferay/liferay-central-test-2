/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_DocumentLibraryRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CA_DocumentLibraryRolesTest extends BaseTestCase {
	public void testCA_DocumentLibraryRoles() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Portlet Permissions']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//tr[20]/td/a"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_scope20CONFIGURATION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scope20VIEW",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibraryADD_FOLDER",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryADD_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryDELETE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryDELETE_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryPERMISSIONS",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryUPDATE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryUPDATE_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileEntryVIEW",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderADD_DOCUMENT",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderADD_SHORTCUT",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderADD_SUBFOLDER",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderDELETE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderPERMISSIONS",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderUPDATE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFolderVIEW",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutADD_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutDELETE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutPERMISSIONS",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutUPDATE",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutDELETE_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutUPDATE_DISCUSSION",
			RuntimeVariables.replace("label=Portal"));
		selenium.select("_128_scopecom.liferay.portlet.documentlibrary.model.DLFileShortcutVIEW",
			RuntimeVariables.replace("label=Portal"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}