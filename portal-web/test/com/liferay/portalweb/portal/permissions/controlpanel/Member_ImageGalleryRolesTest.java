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
 * <a href="Member_ImageGalleryRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Member_ImageGalleryRolesTest extends BaseTestCase {
	public void testMember_ImageGalleryRoles() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Portlet Permissions']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Next"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//tr[8]/td/a"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_scope31CONFIGURATION",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scope31VIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_128_scopecom.liferay.portlet.imagegalleryADD_FOLDER",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderADD_IMAGE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderADD_SUBFOLDER",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderPERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGFolderVIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGImageDELETE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGImagePERMISSIONS",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGImageUPDATE",
			RuntimeVariables.replace("label="));
		selenium.select("_128_scopecom.liferay.portlet.imagegallery.model.IGImageVIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}